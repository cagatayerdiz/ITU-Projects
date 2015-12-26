#include <iostream>
#include <iomanip>
#include <string>
#include <cmath>

using namespace std;

class Treadmill{

private:
	static int numOfTreadmills; 
	int id;						 
	bool availability;
public:
	static void resetNumOfTreadmills(){ numOfTreadmills = 0; } // initializing to 0 once. 
	Treadmill();											   // constructor
	bool isAvailable();
	int getId();
	int run(bool, int, int, int);
	void unsetAvailability();
	~Treadmill();											   // destructor
};

class Trainee{ 

private:
	const string name;			 // string supposed not to be changed
	int age;
	const bool gender;			 // 1: female, 0: male Supposed not to be changed
	int height;					 // int in cm.s
	int weight;					 // int in gram.s
	int dailyActivityDuration;   // int in minute s
	Treadmill ** treadmillList;  // dynamic upper limit 3
	int totalCaloriesBurned;	 // int in kilocalories
	int numOfTreadmills;
public:
	Trainee(string ad, int yas,bool cinsiyet, int boy, int kilo, int zaman = 30);	//constructor
	Trainee(const Trainee &object);													// copy constructor
	bool addTreadmill(Treadmill *object);
	void looseWeight();
	void exercise();
	~Trainee();																		// destructor
};

int Treadmill::numOfTreadmills;

int main(int argv, char * argc[]) { 

	Treadmill::resetNumOfTreadmills(); // Resetting number of treadmills to 0.

	// Creating Treadmills for the gym: 
	Treadmill tM1; 
	Treadmill tM2; 
	Treadmill tM3; 
	Treadmill tM4; // Check constructor/destructor messages 
	{ 
		Treadmill tM5; 
	} 
	Treadmill tM6;

	// Creating a Trainee: (dailyActivityDuration is 30 minutes as default) // Check Constructor messages 
	Trainee trainee1 ("Sally Brown", 18, 1, 170, 55000);
	//Trainee trainee2 = trainee1;		// When uncommented, should work properly

	// Try a daily exercise before treadmill/s are related with this trainee: 
	trainee1.exercise(); // Check error message

	// Adding treadmill pointers to tradmill list of Trainee 
	if (!trainee1.addTreadmill(&tM1)) cout << "Add Operation 1 is not successfull" << endl; 
	if (!trainee1.addTreadmill(&tM2)) cout << "Add Operation 2 is not successfull" << endl;
	if (!trainee1.addTreadmill(&tM3)) cout << "Add Operation 3 is not successfull" << endl; 

	// Should print out error message since max list size is 3. 
	if (!trainee1.addTreadmill(&tM4)) cout << "Add Operation 4 is not successfull" << endl << endl; 

	// Try to loose weight before exercise : 
	trainee1.looseWeight(); // Check error message

	// SAMPLE Workout: 
	trainee1.exercise(); 

	// Update Weight: 
	trainee1.looseWeight();

	// What if all treadmills are unavailable? 
	tM1.unsetAvailability(); 
	tM2.unsetAvailability(); 
	tM3.unsetAvailability();

	trainee1.exercise(); // Check error message

	getchar();
	getchar();
	return 0; 
}

//**********Functionalities of Trainee Class**********

// Constructor
Trainee::Trainee(string ad, int yas,bool cinsiyet, int boy, int kilo, int zaman ):name(ad), gender(cinsiyet)
{
	age = yas;
	height = boy;
	weight = kilo;
	dailyActivityDuration = zaman;
	totalCaloriesBurned = 0;
	numOfTreadmills = 0;

	if(gender)		// female
		cout << "\nA female trainee is created:\n" << endl;
	else			// male
		cout << "\nA male trainee is created:\n" << endl;

	cout << fixed << left << setw(12) << "AGE" <<  " : " << age << endl;
	cout << fixed << left << setw(12) << "NAME" << " : " << name << endl;
	cout << fixed << left << setw(12) << "GENDER" << " : " << gender << endl;
	cout << fixed << left << setw(12) << "HEIGHT" << " : " << height << endl;
	cout << fixed << left << setw(12) << "WEIGHT" << " : " << weight << endl;
	cout << fixed << left << setw(12) << "DURATION" << " : " << dailyActivityDuration << endl;

	treadmillList = new Treadmill*[3]; //upper limit of 3, dynamic, required space taken by constructor
}

// Copy constructor
Trainee::Trainee(const Trainee &object):name(object.name), gender(object.gender)
{
	age = object.age;
	height = object.height;
	weight = object.weight;
	dailyActivityDuration = object.dailyActivityDuration;

	treadmillList = new Treadmill*[3];			// allocating memory
	*treadmillList = *object.treadmillList;		// copying values

	totalCaloriesBurned = object.totalCaloriesBurned;
	numOfTreadmills = object.numOfTreadmills;
}

bool Trainee::addTreadmill(Treadmill *object)
{
	if(numOfTreadmills >= 3){	// 0, 1, 2 are OK. But if >=3 return false.
		return false;
	}
	else{
		treadmillList[numOfTreadmills] = object; 
		numOfTreadmills++;		// incrementing added object
		return true;
	}
}

void Trainee::looseWeight()
{
	if(totalCaloriesBurned == 0){

		cout << "Sorry, NO workout = NO LOSS" << endl;
	}
	else{
		cout << "\n***** Weight UPDATE: *****" << endl;
		cout << fixed << setprecision(3);
		cout << fixed << left << setw(22) << "Previous weight was : " << static_cast<double>(weight)/1000 << endl;

		weight -= static_cast<int>( floor(totalCaloriesBurned * 1.5));  // rounding up

		cout << fixed << left << setw(22) << "NOW : " << static_cast<double>(weight)/1000 << " kg..." << endl;
	}

	totalCaloriesBurned = 0;				// After weight update, totalCaloriesBurned should be reset to 0
}

void Trainee::exercise()
{
	bool check = true;	

	if(numOfTreadmills == 0){		// if there is no treadmill, he/she does not have any related treadmills
		cout << "\nSorry, no treadmills are related to trainee: " << name << ", Can NOT exercise\n" <<endl;
		return;
	}
	else{
		for(int i = 0; i < numOfTreadmills; i++){

			if((treadmillList[i]->isAvailable())){ 
				check = false;	// this checking for error mesaage

				cout << "\nAn available treadmill is found having id: " << treadmillList[i]->getId() << endl;
				cout << "Exercisizing for " << dailyActivityDuration << " minutes ..." << endl;

				totalCaloriesBurned = treadmillList[i]->run(gender, age, weight, dailyActivityDuration);

				cout << "Total Kilocalories burned: " << totalCaloriesBurned << endl;

				return; //take the first marked as available
			}
		}
	}

	if(check)  //if none is available
		cout << "\nSorry, NO treadmills are AVAILABLE to: " << name << ", Can NOT exercise" << endl;
}

// Destructor
Trainee::~Trainee()
{
	delete [] treadmillList; //deallocating memory
	//numOfTreadmills = 0;
}

//**********Functionalities of Treadmill Class**********

// Default constructor
Treadmill::Treadmill() 
{
	availability = true;
	id = numOfTreadmills + 1; // id of the created object will be one greater than number of existing treadmill objects.
	numOfTreadmills++;

	cout << "Treadmill with id: " << id << " is created" << endl;
}

bool Treadmill::isAvailable() // returns status of availability
{
	return availability;
}

int Treadmill::getId()
{
	return id;
}

int Treadmill::run(bool cinsiyet, int yas, int kilo, int duration)
{
	availability = false;

	double kcalsPerMinutes;

	if(cinsiyet)			// female
		kcalsPerMinutes = (-20.4022 + 0.4472 * 100 - 0.1263 * static_cast<double>(kilo)/1000 + 0.074 * yas) / 4.184;
	else					// male
		kcalsPerMinutes = (-55.0969 + 0.6309 * 100 + 0.1988 * static_cast<double>(kilo)/1000 + 0.2017 * yas) / 4.184;

	availability = true;	// before return, sets availability to true

	return static_cast<int>( floor(kcalsPerMinutes * duration)); // rounding up
}

void Treadmill::unsetAvailability()
{
	availability = false;
}

//Destructor
Treadmill::~Treadmill() // there is no dynamic element in Treadmill class, so destructor just decrements numOfTreadmills
{
	cout << "Treadmill with id: " << id << " is deleted" << endl;
	numOfTreadmills--;
}
