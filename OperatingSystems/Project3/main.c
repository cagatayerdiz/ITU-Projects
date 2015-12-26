#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <unistd.h>
#include <signal.h>
#include <sys/sem.h>
#include <sys/shm.h>
#include <string.h>
#include <time.h>

#define KEYMQ 5000 // restoran kuyruğu key
#define KEYSEM1 1000
#define KEYSEM2 1010
#define KEYSEM3 1020
#define KEYSEM4 1030
#define KEYSEM5 1040
#define KEYSEM6 1050
#define KEYSHM  2004
#define msgsz 256			// mesaj size
#define msgflg IPC_NOWAIT 	// mesaj flag

struct messagebuf{
		long mtype;
		char mtext[msgsz];
};

void mysignal(void){ }

// V(semafor)
void sem_signal(int semid, int val)
{
	struct sembuf semafor;
	
	semafor.sem_num = 0;
	semafor.sem_op = val;
	semafor.sem_flg = 1;
	semop(semid, &semafor, 1);
}

// P(semafor)	
void sem_wait(int semid, int val)
{
	struct sembuf semafor;
	semafor.sem_num = 0;
	semafor.sem_op = (-1*val);
	semafor.sem_flg = 1;
	semop(semid, &semafor, 1);
}

void mysigset(int num)
{
	struct sigaction mysigaction;

	mysigaction.sa_handler = (void*) mysignal;
	mysigaction.sa_flags = 0;
	sigaction(num, &mysigaction, NULL);
}

void printMasa(char **masa)
{
	int i;
	for(i = 0; i < 10; i++){
		printf("Masa %d: %s\n", i+1, masa[i]);
		//fflush(stdout);
	}
}

int main(int argc, char **argv)
{
	int i, t, f;
	time_t zaman;
	int musteri = 0; // müşteri sayısı
	int direkgelen = 0, rezervasyonlu = 0, hatirli = 0; // müşteri tipleri
	char *masa[10];
	int cocuk[3];
	int senkronizasyon = 0; // anne-çocuk arası senkronizasyon semaforu
	int kilit = 0, izin = 0, disla = 0; // hatırlı müşteri ile rezervasyon yapan arası dışlama ve senkronizasyon semaforları
	
	FILE *p = fopen("print.txt", "w");
	
	// masa dizisi için yer alma
	for(i = 0; i < 10; i++){
		masa[i] = (char *)malloc(50*sizeof(char));
		masa[i] = "Bos";
	}
	
	mysigset(12);
	
	// rastgele sayı üretimi
	srand((unsigned) time(&zaman));
	
	// mesaj kuyruğu oluşturma
	int msqid = msgget(KEYMQ, IPC_CREAT|0777); // mesaj kuyruğu id
	
	struct messagebuf msgp;
	
		for(t = 0; t < 5; t++)
		{
			printf("telefonla rezervasyon yapan: ");
			scanf("%d", &rezervasyonlu);
			printf("hatırlı musteri: ");
			scanf("%d", &hatirli);
			printf("rezervasyon yapmadan gelen: ");
			scanf("%d", &direkgelen);
			
			// her t anı için prosesler baştan yaratılacak
			for (i = 0; i < 3; i++)
			{
				f = fork();
				
				if(f == -1){
					printf("Fork hatasi\n");
					exit(1);
				}
				if(f == 0) // çocukların çocuk üretmesi engellenir.
					break;
					
				cocuk[i] = f;
			}
			
			if(f > 0) // anne
			{
				// semafor tanımlama işlemleri
				senkronizasyon = semget(KEYSEM1, 1, 0700|IPC_CREAT);
				semctl(senkronizasyon, 0, SETVAL, 0); // senkronizasyon olduğu için 0 atandı
				
				kilit = semget(KEYSEM2, 1, 0700|IPC_CREAT);
				semctl(kilit, 0, SETVAL, 1); 
				
				izin = semget(KEYSEM3, 1, 0700|IPC_CREAT);
				semctl(izin, 0, SETVAL, 0); 
					
				disla = semget(KEYSEM4, 1, 0700|IPC_CREAT);
				semctl(disla, 0, SETVAL, 1); 
						
				//cocuklara sinyal gönder
				// ANNE ÇOCUKLARI UYANDIRSIN DENMEDİĞİ İÇİN AKTİF EDİLMEDİ
				/*
				for (i = 0; i <= 3; i++){ 
					kill(cocuk[i], 12);
				}
				
				sem_wait(senkronizasyon, 3);
				*/
				
				// semafor ve paylaşılan bellek iade işlemleri
				semctl(senkronizasyon, 0, IPC_RMID, 0);
				semctl(kilit, 0, IPC_RMID, 0);
				semctl(izin, 0, IPC_RMID, 0);
			}
			else
			{
				if(i == 0) // restoran görevlisi - rezervasyonlu ile direk geleni düzenler, hatırlı VIP olduğu için kendi oturur.
				{
					//pause();
				
					senkronizasyon = semget(KEYSEM1, 1, 0);
					izin = semget(KEYSEM3, 1, 0);
					disla = semget(KEYSEM4, 1, 0);
					
					// hatırlı müşterinin işi bittiyse girebilir.
					sem_wait(izin, 1);
					
					// rezervasyon yaptırıp aslında gelen sayısı
					int gelen = rand()%(rezervasyonlu+1);
					int j;
					struct messagebuf mesaj;
					for (j = 1; j <= rezervasyonlu; j++)
					{	
						//sem_wait(disla, 1);
						
						long msgtyp = 0;
						msgrcv(msqid, &mesaj, msgsz, msgtyp, msgflg);// > 0 ? // mesaj alma
						//printf("Received: \"%s\" of type = %ld.\n", mesaj.mtext, mesaj.mtype):
						//printf("Cannot receive anything.\n");
						strcpy(masa[musteri], mesaj.mtext);
						musteri = musteri+1;
						printf("\nmasa[%d]: %s",musteri-1, masa[musteri-1]);
						fflush(stdout);
						if(musteri == 10)
							return 0; // burda direk gelenlerle ilgili birşeyler yap
						
						//sem_signal(disla, 1);
					}
					
					exit(0);
				}
				else if(i == 1) // rezervasyonlu müşteri
				{
					//pause();
					
					//printf("cocuk 1: %d\n", cocuk[i]);
					//fflush(stdout);
					int j;
					for (j = 1; j <= rezervasyonlu; j++)
					{
						// mesaj gönderme
						msgp.mtype = j*10; // mesaj tipi
						strcpy(msgp.mtext, "Rezervasyonlu musteri"); // mesaj içeriği
						msgsnd(msqid, &msgp, msgsz, msgflg); // mesaj gönderme
					}
					exit(0);
				}
				else if(i == 2) // hatırlı müşteri
				{
					//pause();
					
					// semaforlar
					senkronizasyon = semget(KEYSEM1, 1, 0);
					kilit = semget(KEYSEM2, 1, 0);
					izin = semget(KEYSEM3, 1, 0);
					disla = semget(KEYSEM4, 1, 0);
				
					int j;			
					for (j = 1; j <= hatirli; j++)
					{
						//sem_wait(kilit, 1);
						
						//*tampon = 2; // hatırlı
						masa[musteri] = "Hatirli musteri";
						musteri++;
						wait(NULL);
						printf("\nmasa[%d]: %s",musteri-1, masa[musteri-1]);
						fflush(stdout);
						// sonuncu hatırlı müşteri ise artık rezervasyonlu ve direk gelenlere izin var
						if(j == hatirli){ 
							sem_signal(izin, 1);
						}
						
						//sem_signal(kilit, 1);
					}
					exit(0);
				}
				
			}
			
		}
	
	return 0;
}
