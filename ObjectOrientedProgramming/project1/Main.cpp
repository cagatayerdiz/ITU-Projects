/******************* 
*   Çağatay Erdiz	*
*   150110040		*
*   OOP HW2			*	
*******************/

// Visual Studio 2012 de derlendi.
// g++ (GCC) 4.1.2 20080704 (Red Hat 4.1.2-54) de derlendi.(okulun sunucuları)

#include <iostream>
#include <cstring>
#include <string>       // getline
#include <fstream>
#include <cstdlib>
#include <ctime>

using namespace std;

class Ship{
protected:
	int length;		// gemilerin uzunlukları
	string name;    // geminin ismi
	int *coordinates;
	int other_coordinate;
	char position;
	int hit_count;   // vurulma sayısı 
public:
	int get_length(){ return length;};
	int get_hit_count(){ return hit_count;};
	Ship(int,int, int, string, char);
	virtual bool print(int, int) const = 0;  // bu sadece derived classlar için kullanılacak
	virtual bool damage(char, int) = 0;		 // bu sadece derived classlar için kullanılacak
	virtual ~Ship(){};						 /* virtual destructor kullanılmadan Ship'in içinde de silinebilir, 
											 fakat class abstract olduğundan bu yöntem daha anlamlıdır.*/
};

class Side {	
private:
	Ship **list;
	string attack_history[100];
	int counter;
public:
	Side(char *);
	void print() const;
	bool defeated() const;
	void damage(char, int);
	string attack();
	~Side();
};                        

class Submarine: public Ship{
public:
	Submarine(int, int, char);
	bool print(int, int) const;
	bool damage(char, int);
	~Submarine();
};

class Destroyer: public Ship{
public:
	Destroyer(int, int, char);
	bool print(int, int) const;
	bool damage(char, int);
	~Destroyer();
};

class Cruiser: public Ship{
public:
	Cruiser(int, int, char);
	bool print(int, int) const;
	bool damage(char, int);
	~Cruiser();
};

class Battleship: public Ship{
public:
	Battleship(int, int, char);
	bool print(int, int) const;
	bool damage(char, int);
	~Battleship();
};

// *** Side ***
Side::Side(char *read){

	string game; // her seferinde alınacak satır için tanımlanmıştır.
	ifstream input;
	int i = 0;		  // index of list
	int row_in;		  // row değeri
	int column_in;	  // column değeri;
	char position_in; // yatay mı dikey mi

	list = new Ship*[10]; // allocating memory
	counter = 0;	      // attack_list in indexi sıfırlanır

	input.open(read, ios::in);

	if(input.is_open())
	{
		while(getline(input,game) || !input.eof()){
			
			row_in = game[2] - 97;     // game[2] satır bilgisine denk düşer
			column_in = game[3] - 48;  // game[3] sütun bilgisine denk düşer
			position_in = game[5];     // game[5] pozisyon bilgisine denk düşer

			switch(game[0]){ // game[0] geminin tipi bilgisine denk düşer
				
				// list[i] de bir pointerdır ona da yer almak gereklidir.
			case 'S': list[i] = new Submarine(row_in,column_in,position_in);
				break;

			case 'D': list[i] = new Destroyer(row_in,column_in,position_in);
				break;

			case 'C': list[i] = new Cruiser(row_in,column_in,position_in);
				break;

			case 'B': list[i] = new Battleship(row_in,column_in,position_in);
				break;

			default: cout << "Error" << endl;
				break;
			}
			i++;
		}
	}
	else
		cout << "Error, the file cannot read at this time!" << endl;

	input.close();		
}

void Side::print() const{

	bool space_is_needed = true; // boşluk karakterini basmasını kontrol eder

	cout << "   0 1 2 3 4 5 6 7 8 9" << endl;
	for(int i = 0; i < 10; i++){  

		cout << static_cast<char>(i+97) << "  ";

		for(int j = 0; j < 10; j++){ 

			for(int k = 0; k < 10; k++){ //list[i] nin içine bakmak için
				if(list[k]->print(i, j)){
					space_is_needed = false; // burası bir kere bile false olursa boşluğa ihtiyaç yok demektir.
				}
			}

			if(space_is_needed)
				cout << "  "; 
			space_is_needed = true; // diğer bölgeler için baştan başlaması için true atanır
		}
		cout << endl;
	}
	cout << endl << endl;
}

bool Side::defeated() const{

	bool is_all_sank = true;

	for(int k = 0; k < 10; k++){
		if(list[k]->get_hit_count() != list[k]->get_length()){ // buraya bir kere bile girse en az bir tane batmayan gemi var anlamına gelir
			is_all_sank = false;
			break;}
	}

	return is_all_sank;
}

void Side::damage(char row_in, int column_in){

	bool missed_is_needed = true;

	for(int k = 0; k < 10; k++){       //list[i] nin içine bakmak için
		if(list[k]->damage(row_in, column_in)){
			missed_is_needed = false;  // burası bir kere bile false olursa düşman vuruldu anlamına gelir
		}
	}

	if(missed_is_needed)               // düşman vurulduysa missed yazmasına gerek yok
		cout << "missed" << endl;
}

string Side::attack(){

	bool different = false;
	char hamle[3];      
	int row;
	int column;

	while (!different) // üretilen sayı farklı değilken while'a girecek, farklıysa zaten girmesine gerek yok
	{
		different = true;

		row = rand() % 10;		   // üretilmek istenen sayılar 0-9 arası
		column = rand() % 10;
		counter = row*10 + column; // her oluşturduğu attack değerini belli bir yere atar

		hamle[0] = row + 97;       //sayının harf karşılığı 0 için a, 1 için b, 2 için c ...
		hamle[1] = column + 48;	   //sayının char olarak karşılığı
		hamle[2] = '\0';

		if(hamle == attack_history[counter])
			different = false;
	}

	attack_history[counter] = hamle; 

	return hamle;
}

Side::~Side(){ // Deallocation of Ship **list

	for(int i = 0; i < 10; i++)
		delete list[i];
	
	delete [] list;
}

// *** Ship ***
Ship::Ship(int length_in, int row, int column, string name_in, char position_in)
	:length(length_in), name(name_in), position(position_in), hit_count(0){

	coordinates = new int[length]; // koordinata geminin uzunluğu kadar yer alır

	if(position == 'v'){           // dikey
		other_coordinate = column; // y değeri sabit

		for(int i = 0; i < length; i++)
			coordinates[i] = row + i; // yerleştiği koordinatlar atanır
	}

	else if(position == 'h'){    // yatay
		other_coordinate = row;  // x değeri sabit

		for(int i = 0; i < length; i++)
			coordinates[i] = column + i; // yerleştiği koordinatlar atanır
	}
}

bool Ship::print(int i_in, int j_in) const{

	if(other_coordinate == i_in && position == 'h') //yatayda ise
	{
		for(int i = 0; i < length; i++){ 
			if(coordinates[i] == j_in){ // koordinatlardan en fazla biri eşleşmelidir
				cout << static_cast<char>(toupper(name[0])) << " ";    // eşleşirse yazdırır
				return true;}		    // o koordinatta gemi olduğunu belli etmek için true döner
		}
		return false; // bu aynı other_coordinat'a sahip ama asıl dizi şeklindeki koordinatı tutmayanlar içindir ör: b satırındaki S ile B
	}
	else if(other_coordinate == j_in && position == 'v') // dikeyde ise
	{
		for(int i = 0; i < length; i++){ 
			if(coordinates[i] == i_in){  // koordinatlardan en fazla biri eşleşmelidir
				cout << static_cast<char>(toupper(name[0])) << " ";     // eşleşirse yazdırır
				return true;}		     // o koordinatta gemi olduğunu belli etmek için true döner
		}
		return false; // bu aynı other_coordinat'a sahip ama asıl dizi şeklindeki koordinatı tutmayanlar için
	}
	else return false; // other_coordinate' ları hiç eşleşmiyorsa false döndürülür
}

bool Ship::damage(char row_in, int column_in){

	if(row_in - 97 == other_coordinate && position == 'h') // yatayda
	{
		for(int i = 0; i < length; i++){
			if(coordinates[i] == column_in){
				coordinates[i] = -1;    // vurulduğu yere bir daha bakmaması için -1 atanır
				hit_count++;
				if(hit_count == length) // vurulma sayısı geminin uzunluğuna eşitse gemi batmıştır
					cout << name << " is sank" << endl;
				else if(name == "battleship")
					cout << name << " is hit- " << hit_count << "/4" << endl;
				else	
					cout << name << " is hit" << endl;
				return true;} 
		}
		return false; // bu aynı other_coordinat'a sahip ama asıl dizi şeklindeki koordinatı tutmayanlar içindir
	}
	else if(column_in == other_coordinate && position == 'v') // dikeyde
	{
		for(int i = 0; i < length; i++){
			if(coordinates[i] == row_in - 97){
				coordinates[i] = -1;    // vurulduğu yere bir daha bakmaması için -1 atanır
				hit_count++;
				if(hit_count == length) // vurulma sayısı geminin uzunluğuna eşitse gemi batmıştır
					cout << name << " is sank" << endl;
				else if(name == "battleship")
					cout << name << " is hit- " << hit_count << "/4" << endl;
				else
					cout << name << " is hit" << endl;
				return true;}
		}
		return false; // bu aynı other_coordinat'a sahip ama asıl dizi şeklindeki koordinatı tutmayanlar içindir
	}
	else return false; // other_coordinate' ları hiç eşleşmiyorsa false döndürülür		
}

// *** Submarine ***
Submarine::Submarine(int row, int column, char position_in)
	:Ship(1, row, column, "submarine", position_in){	}

bool Submarine::print(int i_in, int j_in)const 
{ return Ship::print(i_in, j_in); }

bool Submarine::damage(char row_in, int column_in)
{ return Ship::damage(row_in, column_in); }

Submarine::~Submarine()
{ delete [] coordinates; }

// *** Destroyer ***
Destroyer::Destroyer(int row, int column, char position_in)
	:Ship(2, row, column, "destroyer", position_in){  }

bool Destroyer::print(int i_in, int j_in)const 
{ return Ship::print(i_in, j_in); }

bool Destroyer::damage(char row_in, int column_in)
{ return Ship::damage(row_in, column_in); }

Destroyer::~Destroyer()
{  delete [] coordinates; }

// *** Cruiser ***
Cruiser::Cruiser(int row, int column, char position_in)
	:Ship(3, row, column, "cruiser", position_in){}

bool Cruiser::print(int i_in, int j_in)const 
{ return Ship::print(i_in, j_in); }

bool Cruiser::damage(char row_in, int column_in)
{ return Ship::damage(row_in, column_in); }

Cruiser::~Cruiser()
{ delete [] coordinates; }

// *** Battleship ***
Battleship::Battleship(int row, int column, char position_in)
	:Ship(4, row, column, "battleship", position_in){  }

bool Battleship::print(int i_in, int j_in)const 
{ return Ship::print(i_in, j_in); }

bool Battleship::damage(char row_in, int column_in)
{ return Ship::damage(row_in, column_in); }

Battleship::~Battleship()
{ delete [] coordinates; }

int main(){
	srand(time(NULL));
	Side player1("p1.bs");
	player1.print();
	Side player2("p2.bs");
	player2.print();

	while (true){
		string p1a = player1.attack();
		cout << "player1 - " << p1a << ": ";
		player2.damage(p1a[0], p1a[1]-48); 
		if (player2.defeated()){
		cout << "that's a player1 win!";
		getchar();
		return 0;
		}
		string p2a = player2.attack();
		cout << "player2 - " << p2a << ": ";
		player1.damage(p2a[0], p2a[1]-48);
		if (player1.defeated()){
		cout << "that's a player2 win!";
		getchar();
		return 0;
		}
	}
}
