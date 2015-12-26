#include <iostream>
#include <ctime>
#include <fstream>
#include <string>
#include <cstdlib>
#include <time.h>
using namespace std;

class Data{
private:
	int *A;
public:
	Data(int N);
	void worstCaseofQuickSort(const int N);
	void averageCaseofQuickSort(const int N);
	void randomArray(const int N);
	void exchange(int *x, int *y);
	int partition(int p, int r);
	void quickSort(int p, int r); // p min index, r max index
	void writetoFile(int N); 
	~Data();
};

Data::Data(int N)
{
	A = new int[N];
}

void Data::worstCaseofQuickSort(const int N)
{
	for (int i = 0; i < N; i++)
		A[i] = i;
}

void Data::averageCaseofQuickSort(const int N)
{
	randomArray(N);
}

void Data::randomArray(const int N) 
{
	srand(time(NULL));   
	for (int i = 0; i < N; i++)
		A[i] = rand() % (N)+1;
}

void Data::exchange(int *x, int *y)
{
	int temp = *x;
	*x = *y;
	*y = temp;
}

int Data::partition(int p, int r)
{
	int x = A[r];
	int i = p - 1;
	for (int j = p; j < r; j++)
	{
		if (A[j] <= x){
			i = i + 1;
			exchange(&A[i], &A[j]);
		}
	}
	exchange(&A[i + 1], &A[r]);
	return i + 1;
}

void Data::quickSort(int p, int r)
{
	if (p < r){
		int q = partition(p, r);
		quickSort(p, q - 1);
		quickSort(q + 1, r);
	}
}

void Data::writetoFile(int N)
{
	ofstream output;
	output.open("sorted.txt", ios::out);
	int i = 0;

	if (output.is_open()){
		output << "sorted values for N:" << N << endl;
		while (i < N && !output.eof()){
			output << A[i] << endl;
			i++;
		}
	}
	else
		cout << "Unable to open and write sorted.txt" << endl;

	output.close();
}

Data::~Data(){ delete [] A;}

// Command line: 150110040_AoA1_P2 casetype N
// casetype : WorstCase or AverageCase		
int main(int argc, char **argv)
{
	string casetype = argv[1];
	int N = atoi(argv[2]);

	if (casetype == "WorstCase"){
		Data number(N);
		number.worstCaseofQuickSort(N); 
		number.writetoFile(N);	 
		clock_t t = clock();
		number.quickSort(0, N - 1);
		t = clock() - t;
		cout << "Running time of " << casetype << ":\n " << t << endl;
		number.writetoFile(N); // dizinin sıralı hali
	}
	else if (casetype == "AverageCase"){
		Data *number[100];
		for (int i = 0; i < 100; i++){
			number[i] = new Data(N);
			number[i]->averageCaseofQuickSort(N);
		}

		clock_t t = clock();
		for (int i = 0; i < 100; i++)
			number[i]->quickSort(0, N - 1);
		t = clock() - t;

		cout << "Running time of " << casetype << ":\n " << static_cast<double>(t) / 100 << endl;
		number[0]->writetoFile(N); // dizinin sıralı hali, 100 tane diziyi yazdırmak yerine örnek olarak ilk dizi yazdırılmıştır.
	}
	else
		cout << "There is no such case: " << casetype << endl;

	return 0;
}
