#include <fstream>
#include <iomanip>
#include "Person.h"
using namespace std;

Person::Person(){ }

float Person::calculateBMI()
{ 
	return weight / pow(static_cast<float>(height) / 100, 2); 
}

void Person::fillContents(ifstream &file, int thresold_in)
{
	file >> id;
	file >> height;
	file >> weight;

	BMI = calculateBMI();
	thresoldDifference = abs(BMI - thresold_in);
}

void Person::writetoFile(ofstream &file)
{
	file << setprecision(6);
	file << fixed << left << setw(10) << id;
	file << fixed << left << setw(15) << height;
	file << fixed << left << setw(15) << weight;
	file << fixed << left << setw(15) << BMI;
	file << fixed << left << setw(10) << thresoldDifference << "\n";
}

Person::~Person(){ }
