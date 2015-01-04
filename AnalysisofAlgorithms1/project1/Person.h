#ifndef PERSON_H
#define PERSON_H

#include <fstream>
using namespace std;

class Person{
private:
	int id;
	int height;
	int weight;
	float BMI;
	float thresoldDifference;
public:
	Person();
	float calculateBMI();
	void fillContents(ifstream &, int);
	float getthresoldDifference(){ return thresoldDifference; }
	void setthresoldDifference(float value){ thresoldDifference = value; }
	void writetoFile(ofstream &);
	~Person();
};

#endif
