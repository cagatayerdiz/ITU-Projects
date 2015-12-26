#include <iostream>
#include <cstdlib>	// atoi
#include <iomanip>	// setw, setprecision
#include <string>	// string
#include <cmath>	// pow
#include <fstream>	// ifstream, ofstream
#include <time.h>	// clock_t
#include "Person.h"
using namespace std;

void insertionSort(Person *list, int N)
{
	Person key;
	int j;
	for (int i = 1; i < N; i++){
		key = list[i];
		j = i - 1;
		while (j >= 0 && list[j].getthresoldDifference() < key.getthresoldDifference()){
			list[j + 1] = list[j];
			j--;
		}
		list[j + 1] = key;
	}
}

void mergeSort(Person *list, int low, int high)
{
	if (high > low){
		int middle = low + (high - low) / 2;
		mergeSort(list, low, middle);
		mergeSort(list, middle + 1, high);

		int n1 = middle - low + 1;
		int n2 = high - middle;

		Person *left;
		Person *right;

		left = new Person[n1 + 1];
		right = new Person[n2 + 1];

		for (int k = low; k <= middle; k++)
			left[k - low] = list[k];
		for (int k = middle + 1; k <= high; k++)
			right[k - middle - 1] = list[k];

		left[n1].setthresoldDifference(-1); // karşılaştırma yaparken sıkıntı olmaması için dizinin sonuna diğer tüm elemanlardan küçük değer atanması gerekir  
		right[n2].setthresoldDifference(-1);

		int i = 0, j = 0;
		for (int k = low; k <= high; k++){
			if (right[j].getthresoldDifference() < left[i].getthresoldDifference())
				list[k] = left[i++];
			else
				list[k] = right[j++];
		}
		delete[] left;
		delete[] right;
	}
	else return;
}

Person *linearSearch(Person *copy, int N, int M)
{
	Person *toreturn;
	int temp;
	float max;
	bool control;

	toreturn = new Person[M];

	for (int i = 0; i < M; i++){
		control = false;
		max = copy[i].getthresoldDifference();
		for (int j = 0; j < N; j++){
			if (copy[j].getthresoldDifference() > max){
				max = copy[j].getthresoldDifference();
				temp = j;
				control = true;
			}
		}
		if (control){ // girdiyse
			toreturn[i] = copy[temp];
			copy[temp].setthresoldDifference(-1);
		}
		else{ // girmediyse
			toreturn[i] = copy[i];
			copy[i].setthresoldDifference(-1);
		}
	}

	delete[] copy;

	return toreturn;
}

// command line algorithmtypes: InsertionSort, MergeSort, LinearSearch
int main()
{
	int N, M, thresold;
	string algorithmtype;
	cout << "N: "; // total
	cin >> N;
	cout << "M: ";
	cin >> M;     // desired
	cout << "algorithmtype: ";
	cin >> algorithmtype;
	cout << "thresold: ";
	cin >> thresold;
	Person *list = new Person[N];

	ifstream input;
	ofstream output;
	string firstrow;
	int index = 0;

	input.open("height_weight.txt", ios::in);
	if (input.is_open())
	{
		getline(input, firstrow);
		while (index < N && !input.eof()){
			list[index].fillContents(input, thresold);
			index++;
		}
	}
	else cout << "Unable to open height_weight.txt" << endl;
	input.close();

	Person *linearSearchList = new Person[M]; // linearSearch fonksiyonundan dönen listeyi tutacak

	clock_t t;
	if (algorithmtype == "InsertionSort"){
		t = clock();
		insertionSort(list, N);
		t = clock() - t;
	}
	else if (algorithmtype == "MergeSort"){
		t = clock();
		mergeSort(list, 0, N - 1);
		t = clock() - t;
	}
	else if (algorithmtype == "LinearSearch"){

		Person *copy = new Person[N]; // linearSearch fonksiyonu için list listesinin kopyası oluşturulur.
		for (int i = 0; i < N; i++)
			copy[i] = list[i];

		t = clock();
		linearSearchList = linearSearch(copy, N, M);
		t = clock() - t;
	}
	else{
		cout << "Invalid algorithm type" << endl; 
		return EXIT_FAILURE;
	}

	cout << algorithmtype << "\n clock ticks: " << t
		<< "\n seconds: " << static_cast<double>(t) / CLOCKS_PER_SEC << endl; // saniye cinsinden süre

	// Dosyaya yazma
	index = 0;
	output.open("output.txt", ios::out);
	if (output.is_open()){
		output << fixed << left << setw(10) << "id";
		output << fixed << left << setw(15) << "height(cm)";
		output << fixed << left << setw(15) << "weight(kg)";
		output << fixed << left << setw(15) << "BMI";
		output << fixed << left << setw(10) << "thresold difference" << endl;
		if (algorithmtype == "InsertionSort" || algorithmtype == "MergeSort"){
			while (index < M){
				list[index].writetoFile(output);
				index++;
			}
		}
		else if (algorithmtype == "LinearSearch"){
			while (index < M){
				linearSearchList[index].writetoFile(output); // linearSearch için farklı liste kullanılmıştır.
				index++;
			}
		}
	}
	else{
		cout << "Unable to write output.txt" << endl;
	}
	output.close();

	delete[] list;
	delete[] linearSearchList;

	getchar();
	return 0;
}
