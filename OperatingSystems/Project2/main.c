/**
 * BLG 312 - Ödev 2
 * Çağatay Erdiz
 * 150110040
 **/
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <signal.h>
#include <sys/ipc.h>
#include <sys/sem.h>
#include <sys/shm.h>
#include <string.h>

#define KEYSHM1 1000
#define KEYSHM2 1010
#define KEYSEM1 1020
#define KEYSEM2 1030
#define KEYSEM3 1040
#define KEYSEM4 1050
#define KEYSEM5 1060
#define KEYSEM6 1070
#define KEYSEM 1080

int value = 0;

int deger_al()
{ 
	value++;
	return (value == 1001)? 0 : value;
}

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

int main(int argc, char **argv)
{
	int i, f, temp = 1, temp2 = 1, temp3 = 1; // değişkenler
	int shmid1, shmid2; // paylaşılan bellek alanları için id
	int *tampon1 = NULL, *tampon2 = NULL; // paylaşılan bellek alanları için pointer
	int dolu1 = 0, bos1 = 0, disla1 = 0, dolu2 = 0, bos2 = 0, disla2 = 0; // semafor değişkenleri
	int annecocuk = 0; // cocuklar bittikten sonra anneyi bitirmek için semafor
	int cocuk[3]; // cocuk prosesler için id dizisi
	
	FILE *fp = fopen("sonuc.txt", "w+");
	char line[10000];
	
	mysigset(12);
	
	for (i = 0; i < 3; i++) // 3 çocuk üret
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
		annecocuk = semget(KEYSEM, 1, 0700|IPC_CREAT);
		semctl(annecocuk, 0, SETVAL, 0); // senkronizasyon olduğu için 0 atandı
		
		dolu1 = semget(KEYSEM1, 1, 0700|IPC_CREAT);
		semctl(dolu1, 0, SETVAL, 0); 
		
		bos1 = semget(KEYSEM2, 1, 0700|IPC_CREAT);
		semctl(bos1, 0, SETVAL, 1); 
		
		disla1 = semget(KEYSEM3, 1, 0700|IPC_CREAT);
		semctl(disla1, 0, SETVAL, 1); 
		
		dolu2 = semget(KEYSEM4, 1, 0700|IPC_CREAT);
		semctl(dolu2, 0, SETVAL, 0); 
		
		bos2 = semget(KEYSEM5, 1, 0700|IPC_CREAT);
		semctl(bos2, 0, SETVAL, 1); 
		
		disla2 = semget(KEYSEM6, 1, 0700|IPC_CREAT);
		semctl(disla2, 0, SETVAL, 1); 
		
		// tampon1
		shmid1 = shmget(KEYSHM1, sizeof(int), 0700|IPC_CREAT );
		tampon1 = (int *)shmat(shmid1, 0, 0);
		*tampon1 = 1; // ilk değeri 1
		shmdt(tampon1);
		
		// tampon2
		shmid2 = shmget(KEYSHM2, sizeof(int), 0700|IPC_CREAT );
		tampon2 = (int *)shmat(shmid2, 0, 0);
		*tampon2 = 1; // ilk değeri 1
		shmdt(tampon2);
		
		//cocuklara sinyal gönder
		for (i = 0; i < 3; i++){ 
			kill(cocuk[i], 12);
		}
		
		sem_wait(annecocuk, 3);
		
		// semafor ve paylaşılan bellek iade işlemleri
		semctl(annecocuk, 0, IPC_RMID, 0);
		semctl(dolu1, 0, IPC_RMID, 0);
		semctl(bos1, 0, IPC_RMID, 0);
		semctl(disla1, 0, IPC_RMID, 0);
		semctl(dolu2, 0, IPC_RMID, 0);
		semctl(bos2, 0, IPC_RMID, 0);
		semctl(disla2, 0, IPC_RMID, 0);
		shmctl(shmid1, IPC_RMID, 0);
		shmctl(shmid2, IPC_RMID, 0);
	}
	else // çocuk
	{	
		if(i == 0) // 1. çocuk
		{
			pause();

			// semaforlar
			annecocuk = semget(KEYSEM, 1, 0);
			dolu1 = semget(KEYSEM1, 1, 0);
			bos1 = semget(KEYSEM2, 1, 0);
			disla1 = semget(KEYSEM3, 1, 0);
			
			// paylaşılan bellek alanı
			shmid1 = shmget(KEYSHM1, sizeof(int), 0);
			tampon1 = (int *)shmat(shmid1, 0, 0);
			
			// tampon1 ile iligili işlemler
			while(temp != 0){
				sem_wait(bos1, 1);
				sem_wait(disla1, 1);
				
				temp = deger_al();
				*tampon1 = temp;
				
				printf("*P1\nP1, T1 alanina %d yazdi.\n", temp);
				//wait(NULL);
				
				sem_signal(disla1, 1);
				sem_signal(dolu1, 1);
			}
			
			shmdt(tampon1);

			sem_signal(annecocuk, 1);
		}
		else if(i == 1 ) // 2. çocuk
		{
			pause();
			
			// semaforlar
			annecocuk = semget(KEYSEM, 1, 0);
			dolu1 = semget(KEYSEM1, 1, 0);
			bos1 = semget(KEYSEM2, 1, 0);
			disla1 = semget(KEYSEM3, 1, 0);
			dolu2 = semget(KEYSEM4, 1, 0);
			bos2 = semget(KEYSEM5, 1, 0);
			disla2 = semget(KEYSEM6, 1, 0);

			// paylaşılan bellek alanı
			shmid1 = shmget(KEYSHM1, sizeof(int), 0);
			tampon1 = (int *)shmat(shmid1, 0, 0);
			shmid2 = shmget(KEYSHM2, sizeof(int), 0);
			tampon2 = (int *)shmat(shmid2, 0, 0);
			
			// tampon1 ve tampon2 ile iligili işlemler
			while(temp2 != 0)
			{
				sem_wait(dolu1, 1);
				sem_wait(disla1, 1);
				
				temp2 = *tampon1;
				
				sem_signal(disla1, 1);
				sem_signal(bos1, 1);
				
				if (temp2 % 2 == 1 || temp2 == 0)
				{
					sem_wait(bos2, 1);
					sem_wait(disla2, 1);
					
					*tampon2 = temp2;
					
					printf("**P2\nP2, T1 alanindan %d okudu ve T2 alanina %d yazdi.\n", temp2, temp2);	
					//wait(NULL);
					
					sem_signal(disla2, 1);
					sem_signal(dolu2, 1);
				}
			}
			
			shmdt(tampon1);
			shmdt(tampon2);

			sem_signal(annecocuk, 1);
		}
		else if(i == 2) // 3. çocuk
		{	
			pause();
			
			// semaforlar
			annecocuk = semget(KEYSEM, 1, 0);
			dolu2 = semget(KEYSEM4, 1, 0);
			bos2 = semget(KEYSEM5, 1, 0);
			disla2 = semget(KEYSEM6, 1, 0);
					
			// paylaşılan bellek alanı
			shmid2 = shmget(KEYSHM2, sizeof(int), 0);
			tampon2 = (int *)shmat(shmid2, 0, 0);
			
			fprintf(fp, "SONUC: 2 ve 3 ile bölünmeyen sayılar: ");
			
			// tampon2 ile iligili işlemler
			while(1) // ya da while(temp3 != 999) çünkü temp3 999 olduktan sonra while döngüsüne girmesi istenmez, çünkü sonraki değer 0'dır.
			{
				sem_wait(dolu2, 1);
				sem_wait(disla2, 1);
			
				temp3 = *tampon2;
				
				if (temp3 % 3 != 0 || temp3 == 0)
				{
					if(temp3 == 0) break;
					fprintf(fp, "%d, ", temp3);
					printf("***P3\nP3, T2 alanindan %d okudu ve T3 alanina %d yazdi.\n", temp3, temp3);
					//wait(NULL);
				}
				
				sem_signal(disla2, 1);
				sem_signal(bos2, 1);
			}
			
			printf("SONUC: 2 ve 3 ile bölünmeyen sayılar: ");
			//wait(NULL);
			
			while(fgets(line, sizeof(line), fp)){
				printf("%s", line);
			}
			
			shmdt(tampon2);

			sem_signal(annecocuk, 1);	
		}		
	}
	
	fclose(fp);
	
	return 0;
}
