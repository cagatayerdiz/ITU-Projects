#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>

int main()
{
	int i, f;
    for (i = 1; i <= 3; i++)
    {
        f = fork();

        if (f < 0)
        {
            printf("Hata! \n");
            exit(-1); // hatalı sonlandı
        }
        
        else if (f == 0) {
           printf("\t (i = %d)Cocuk: Proses no: %d | Anne proses no: %d \n",i, getpid(),getppid());
           exit(0); // başarıyla sonlandı
        } 
        
        else {
			wait(NULL); // ekran çıktısının düzenli olmasını sağlar
			printf("(i = %d)Anne: Proses no: %d | Cocuk proses no: %d \n", i, getpid(), f);
        }
    }
    return 0;
}
