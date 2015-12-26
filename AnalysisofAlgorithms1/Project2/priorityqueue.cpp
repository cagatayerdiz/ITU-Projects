#include <iostream>
#include <fstream>
#include <string>
#include <ctime>
#include <cmath>
#include <cstdlib>
#include <iomanip>
#include <limits>
#include <time.h>
using namespace std;

class PriorityQueue{

private:
	float *A;
	int heapSize;
	int d; // d-ary
public:
	PriorityQueue(int d, int M);
	int getHeapSize();
	int daryParent(int i);
	int daryChild(int i, int j);
	void exchange(float *x, float *y);
	void maxHeapify(int index);
	void heapIncreaseKey(int i, float key);
	void INSERT(float key);
	void REMOVE(int i);
	void UPDATE_KEY(int i, int key);
	float EXTRACT_MAX();
	void output(int totalApplicants, int updateCount, int invitedCount, clock_t t);
	~PriorityQueue();
};

PriorityQueue::PriorityQueue(int d, int M)
{
	A = new float[M ]; // en fazla M operation olabilir. Hepsi INSERT olsa M lik yere ihtiyaç duyulur.  
	heapSize = 0;
	this->d = d;
}

int PriorityQueue::getHeapSize()
{
	return heapSize;
}

int PriorityQueue::daryParent(int i)
{
	return (i - 2) / d + 1;
}

int PriorityQueue::daryChild(int i, int j)
{
	return d*(i - 1) + 1 + j;
}

void PriorityQueue::exchange(float *x, float *y)
{
	float temp = *x;
	*x = *y;
	*y = temp;
}

void PriorityQueue::maxHeapify(int i)
{
	int *child;
	child = new int[d];
	int largest = i;

	for (int j = 0; j < d; j++)
		child[j] = daryChild(i + 1, j + 1) - 1;

	for (int j = 0; j < d; j++){
		if ((child[j] < heapSize-1) && (A[child[j]] > A[largest]))
			largest = child[j];
	}

	if (largest != i){
		exchange(&A[i], &A[largest]);
		maxHeapify(largest);
	}
}

void PriorityQueue::heapIncreaseKey(int i, float key)
{
	if (key < A[i])
		cout << "error: new key is smaller than current key" << endl;
		
	A[i] = key;
	while ((i > 0) && (A[daryParent(i + 1) - 1] < A[i])){
		exchange(&A[i], &A[daryParent(i + 1) - 1]);
		i = daryParent(i + 1) - 1;
	}
}

void PriorityQueue::INSERT(float key)
{
	heapSize++;
	A[heapSize - 1] = numeric_limits<float>::min(); // -sonsuz için kullanılmıştır.
	heapIncreaseKey(heapSize - 1, key);
}

void PriorityQueue::REMOVE(int i) // index
{
	exchange(&A[i], &A[heapSize - 1]);
	heapSize--;
	maxHeapify(i);
}

void PriorityQueue::UPDATE_KEY(int i, int key) // index ve key
{
	if (key > 0)
		heapIncreaseKey(i, A[i] + key); // parent a bakar
	else if (key < 0){
		A[i] += key; // atama işlemi
		maxHeapify(i); // max heap yapısını koruma, child a bakar
	}
}

float PriorityQueue::EXTRACT_MAX()
{
	if (heapSize < 1)
		cout << "heap underflow" << endl;

	float max = A[0];
	A[0] = A[heapSize - 1];
	heapSize--;
	maxHeapify(0); // ilk eleman max heapify yapılır.

	return max;
}

void PriorityQueue::output(int totalApplicants, int updateCount, int invitedCount, clock_t t)
{
	cout << "Number of total applicants:\n " << totalApplicants << endl;
	cout << "Number of updates:\n " << updateCount << endl;
	cout << "Number of applicants who will be invited to the interview:\n " << invitedCount << endl;
	cout << "Total running time in milliseconds:\n " << t << endl;
}

PriorityQueue::~PriorityQueue()
{
	delete[] A;
}

int main(int argc, char **argv)
{
	int d = atoi(argv[1]);
	int M = atoi(argv[2]);
	int N = atoi(argv[3]);
	float P = atoi(argv[4]);
	
	PriorityQueue applicants(d, M); // tüm işlemler constructor içinde yapılmıştır.

	ifstream input;
	string firstrow;
	float randomP, randomWithdraw;
	int randomApplicant1, randomApplicant2, randomIncrement, randIncDec;
	int updateCount = 0, insertCount = 0, removeCount = 0;
	int english, math, gpa;
	float finalScore;
	input.open("scores.txt", ios::in);
	srand(time(NULL));
	clock_t t;

	if (input.is_open()){
		getline(input, firstrow); // english-math-GPA yazısından kurtulmak için
		t = clock();
		while ((updateCount + insertCount + removeCount) <= M && !input.eof()){
			randomP = static_cast<float>(rand() % 50) / 50;
			if (randomP <= P && applicants.getHeapSize() > 0){
				randomApplicant1 = rand() % applicants.getHeapSize(); // index
				randIncDec = rand() % 2;	// 0 veya 1 buna göre de increment veya decrement seçilir.
				randIncDec == 0 ? randomIncrement = (rand() % 10) + 1 : randomIncrement = (rand() % 10) - 10;
				applicants.UPDATE_KEY(randomApplicant1, randomIncrement);
				updateCount++;
			}
			else if (randomP > P){
				input >> english;
				input >> math;
				input >> gpa;
				finalScore = static_cast<float>(0.25*english) + static_cast<float>(0.30*math) + static_cast<float>(0.45*gpa);
				applicants.INSERT(finalScore);
				insertCount++;
			}

			if (applicants.getHeapSize() > 0){
				randomWithdraw = static_cast<float>(rand() % 10) / 10;
				if (static_cast<int>(randomWithdraw * 10) <= 2){ // precision açısından sorun çıktığından böyle yapılmıştır.
					randomApplicant2 = rand() % applicants.getHeapSize();
					applicants.REMOVE(randomApplicant2);
					removeCount++;
				}
			}
		}
	}
	else
		cout << "Unable to open scores.txt" << endl;

	input.close();

	int limit = (N <= applicants.getHeapSize()) ? N : applicants.getHeapSize();
	int totalApplicants = insertCount - removeCount;
	
	for (int i = 0; i < limit; i++)
		applicants.EXTRACT_MAX();
	
	t = clock() - t;

	applicants.output(totalApplicants, updateCount, limit, t);

	getchar();
	return 0;
}
