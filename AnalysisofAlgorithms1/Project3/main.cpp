#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <cstdlib>
#include <ctime>
using namespace std;

/* Slot Class*/
class Slot
{
private:
	int nationalId;
	string mark; // empty, full, deleted
public:
	Slot();
	int getKey(){ return nationalId; }
	void setKey(int new_key){ nationalId = new_key; }
	string getMark(){ return mark; }
	void setMark(string new_mark){ mark = new_mark; }
	~Slot();
};

Slot::Slot(){ mark = "empty"; }

Slot::~Slot(){ }

/* Hash Class for Linear Probing, Double Hashing and Quadratic Hashing*/
class Hash
{
private:
	Slot *slot; // slot dizisi
	int m; // table size
	int totalCollusion;
	int removeCount;
public:
	Hash(int m);
	int hash(int k, int i, string hashType);
	int hashLinear(int key, int i);
	int hashDouble(int key, int i);
	int hashQuadratic(int key, int i);
	int getCollusion();
	void organizeTable(string hashType);
	void insert(int key, string hashType);
	void remove(int key, string hashType);
	void update(int key, int new_value, string hashType);
	int search(int key, string hashType);
	~Hash();
};

Hash::Hash(int m)
{
	totalCollusion = 0;
	removeCount = 0;
	this->m = m;
	slot = new Slot[m];
}

int Hash::hash(int k, int i, string hashType) // seçilen yönteme göre hash fonksiyonu çaðýrýr
{
	if (hashType == "Linear")
		return hashLinear(k, i);
	else if (hashType == "Double")
		return hashDouble(k, i);
	else if (hashType == "Quadratic")
		return hashQuadratic(k, i);
}

int Hash::hashLinear(int k, int i)
{
	// Linear Probing
	int index;
	index = (k + i) % m;
	return index;
}

int Hash::hashDouble(int k, int i)
{
	// Double Hashing
	int index;
	int h1 = k % m;
	int h2 = 1 + k % (m - 1);
	index = (h1 + i * h2) % m;
	return index;
}

int Hash::hashQuadratic(int k, int i)
{
	// Quadratic Hashing
	int index;
	int h = k % m;
	index = (h + i + i*i) % m;
	return index;
}

int Hash::getCollusion(){ return totalCollusion; }

void Hash::organizeTable(string hashType)
{
	Slot *ptr = new Slot[m];
	for (int i = 0; i < m; i++)
		ptr[i] = slot[i]; // ptrda dizinin kopyasý oldu

	delete[] slot;
	slot = new Slot[m]; // asýl dizinin içi temizlendi
	cout << endl;
	for (int i = 0; i < m; i++)
		if (ptr[i].getMark() == "full")
			insert(ptr[i].getKey(), hashType);

	delete[] ptr;
	removeCount = 0;
	cout << "\nTable has been organized(no deleted mark in table).\n" << endl;
}

void Hash::insert(int key, string hashType) // mark: full deðilse ekleyecek, fullse collusion
{
	int i = 0;
	int index = hash(key, i, hashType);

	while (slot[index].getMark() == "full") // fullse boþ yer aramaya devam eder.
	{
		i++;
		totalCollusion++;
		if (i == m){ cout << "Insertion: Key cannot be inserted(table overflow)" << endl; return; }
		index = hash(key, i, hashType);
	}

	if (slot[index].getMark() == "deleted") // silinen yere eklendiyse
		removeCount--;

	slot[index].setKey(key);
	slot[index].setMark("full");
	cout << "Insertion: Key " << key << " is inserted at index " << index << "." << endl;
}

void Hash::remove(int key, string hashType)
{
	int i = 0;
	int index = hash(key, i, hashType);

	if (removeCount == 50){ // 50 tane silindi
		cout << "\nTable will be organized." << endl;
		organizeTable(hashType);
		return;
	}

	while (i < m)
	{
		if (slot[index].getMark() == "full" && slot[index].getKey() == key){
			slot[index].setMark("deleted");
			removeCount++;
			cout << "Deletion: Key " << key << " is deleted from table." << endl;
			return;
		}
		else if (slot[index].getMark() == "empty"){
			cout << "Deletion: Key " << key << " cannot be deleted." << endl;
			return;
		}
		i++;
		index = hash(key, i, hashType);
	}
	cout << "Deletion: Key " << key << " cannot be deleted(table overflow)." << endl;
	return;
}

void Hash::update(int key, int new_value, string hashType)
{
	int i1 = 0;
	int index1 = hash(key, i1, hashType);

	if (removeCount == 50){ // 50 tane silindi
		cout << "\nTable will be organized." << endl;
		organizeTable(hashType);
		return;
	}

	while (i1 < m)
	{
		if (slot[index1].getMark() == "full" && slot[index1].getKey() == key){
			slot[index1].setMark("deleted");
			removeCount++;

			int i2 = 0;
			int index2 = hash(new_value, i2, hashType);

			while (slot[index2].getMark() == "full") // fullse boþ yer aramaya devam eder.
			{
				i2++;
				totalCollusion++;
				if (i2 == m){ cout << "Update: Key " << key << " cannot be updated.(table overflow)" << endl; return; }
				index2 = hash(new_value, i2, hashType);
			}

			if (slot[index2].getMark() == "deleted") // silinen yere eklendiyse
				removeCount--;

			slot[index2].setKey(new_value);
			slot[index2].setMark("full");

			cout << "Update: Key " << key << " is updated with key " << new_value << ". Its new slot is " << index2 << "." << endl;
			return;
		}
		else if (slot[index1].getMark() == "empty"){
			cout << "Update: Key " << key << " cannot be updated." << endl;
			return;
		}
		i1++;
		index1 = hash(key, i1, hashType);
	}
	cout << "Update: Key " << key << " cannot be updated(table overflow)." << endl;
	return;
}

int Hash::search(int key, string hashType)
{
	int i = 0;
	int index = hash(key, 0, hashType);

	while (i < m)
	{
		if (slot[index].getMark() == "full" && slot[index].getKey() == key){
			cout << "Search: Key " << key << " is found at index " << index << "." << endl;
			return index;
		}
		else if (slot[index].getMark() == "empty"){
			cout << "Search: Key " << key << " is not found in the table." << endl;
			return -1;
		}
		i++;
		index = hash(key, i, hashType);
	}
	cout << "Search: Key " << key << " is not found in the table." << endl;
	return -1;
}

Hash::~Hash(){ delete[] slot; }

/* HashUniversal Class for Universal Hashing*/

class HashUniversal
{
private:
	Slot *slot; // slot dizisi
	int m;		// table size
	int totalCollusion;
	int removeCount;
	int a[3];
public:
	HashUniversal(int m);
	int hash(int k);
	int hashUniversalCollusion(int k, int i); // universal'da collusion olursa linear probing kullanýlýr
	int getCollusion();
	void organizeTable();
	void insert(int key);
	void remove(int key);
	void update(int key, int new_value);
	int search(int key);
	~HashUniversal();
};

HashUniversal::HashUniversal(int m)
{
	totalCollusion = 0;
	removeCount = 0;
	this->m = m;
	slot = new Slot[m];

	// random values
	srand(time(NULL));
	for (int i = 0; i < 3; i++)
		a[i] = rand() % 10000; // linuxta hata verdiðinden mod alýndý
}

string toString(int n)
{
	ostringstream ss;
	ss << n;
	return ss.str();
}

int HashUniversal::hash(int key)
{
	int k[3], index = 0;
	string strKey = toString(key);
	int length = strKey.length();
	int condition1 = (length < 6 ? 0 : length - 6); // stringin uzunluðuna göre ayar çekilir
	int condition2 = (length < 3 ? 0 : length - 3);

	string s3(strKey.begin() + condition2, strKey.end());					// last
	string s2(strKey.begin() + condition1, strKey.begin() + condition2);	// middle 
	string s1(strKey.begin(), strKey.begin() + condition1);					// first

	// atoi: char* to int
	char *k1, *k2, *k3;
	k1 = new char[s1.length() + 1];
	strcpy(k1, s1.data());

	k2 = new char[s2.length() + 1];
	strcpy(k1, s1.data());

	k3 = new char[s3.length() + 1];
	strcpy(k1, s1.data());

	k[0] = atoi(k1);
	k[1] = atoi(k2);
	k[2] = atoi(k3);

	for (int i = 0; i < 3; i++)
		index += k[i] * a[i];

	index = index % m;
	return index;
}

int HashUniversal::hashUniversalCollusion(int k, int i)
{
	// Linear Probing
	int index;
	index = (k + i) % m;
	return index;
}

int HashUniversal::getCollusion(){ return totalCollusion; }

void HashUniversal::organizeTable()
{
	Slot *ptr = new Slot[m];
	for (int i = 0; i < m; i++)
		ptr[i] = slot[i]; // ptrda dizinin kopyasý oldu

	delete[] slot;
	slot = new Slot[m]; // asýl dizinin içi temizlendi
	cout << endl;
	for (int i = 0; i < m; i++)
		if (ptr[i].getMark() == "full")
			insert(ptr[i].getKey());

	delete[] ptr;
	removeCount = 0;
	cout << "\nTable has been organized(no deleted mark in table).\n" << endl;
}

void HashUniversal::insert(int key) // mark: full deðilse ekleyecek, fullse collusion
{
	int i = 0;
	int index = hash(key);

	while (slot[index].getMark() == "full") // fullse boþ yer aramaya devam eder.
	{
		i++;
		totalCollusion++;
		if (i == m){ cout << "Insertion: Key cannot be inserted(table overflow)" << endl; return; }
		index = hashUniversalCollusion(key, i);
	}

	if (slot[index].getMark() == "deleted") // silinen yere eklendiyse
		removeCount--;

	slot[index].setKey(key);
	slot[index].setMark("full");
	cout << "Insertion: Key " << key << " is inserted at index " << index << "." << endl;
}

void HashUniversal::remove(int key)
{
	int i = 0;
	int index = hash(key);

	if (removeCount == 50){ // 50 tane silindi
		cout << "\nTable will be organized." << endl;
		organizeTable();
		return;
	}

	while (i < m)
	{
		if (slot[index].getMark() == "full" && slot[index].getKey() == key){
			slot[index].setMark("deleted");
			removeCount++;
			cout << "Deletion: Key " << key << " is deleted from table." << endl;
			return;
		}
		i++;
		index = hashUniversalCollusion(key, i); // buraya geldiyse collusion haliyle eklenmiþ demektir.
	}
	cout << "Deletion: Key " << key << " cannot be deleted(table overflow)." << endl;
	return;
}

void HashUniversal::update(int key, int new_value)
{
	int i1 = 0;
	int index1 = hash(key);

	if (removeCount == 50){ // 50 tane silindi
		cout << "\nTable will be organized." << endl;
		organizeTable();
		return;
	}

	while (i1 < m)
	{
		if (slot[index1].getMark() == "full" && slot[index1].getKey() == key){
			slot[index1].setMark("deleted");
			removeCount++;

			int i2 = 0;
			int index2 = hash(new_value);

			while (slot[index2].getMark() == "full") // fullse boþ yer aramaya devam eder.
			{
				i2++;
				totalCollusion++;
				if (i2 == m){ cout << "Update: Key " << key << " cannot be updated.(table overflow)" << endl; return; }
				index2 = hashUniversalCollusion(new_value, i2); // buraya geldiyse collusion haliyle eklenmiþ demektir.
			}

			if (slot[index2].getMark() == "deleted") // silinen yere eklendiyse
				removeCount--;

			slot[index2].setKey(new_value);
			slot[index2].setMark("full");

			cout << "Update: Key " << key << " is updated with key " << new_value << ". Its new slot is " << index2 << "." << endl;
			return;
		}
		i1++;
		index1 = hashUniversalCollusion(key, i1); // buraya geldiyse collusion haliyle eklenmiþ demektir.
	}
	cout << "Update: Key " << key << " cannot be updated(table overflow)." << endl;
	return;
}

int HashUniversal::search(int key)
{
	int i = 0;
	int index = hash(key);

	while (i < m)
	{
		if (slot[index].getMark() == "full" && slot[index].getKey() == key){
			cout << "Search: Key " << key << " is found at index " << index << "." << endl;
			return index;
		}
		i++;
		index = hashUniversalCollusion(key, i); // buraya geldiyse collusion haliyle eklenmiþ demektir.
	}
	cout << "Search: Key " << key << " is not found in the table." << endl;
	return -1;
}

HashUniversal::~HashUniversal(){ delete[] slot; }

// Command Line: ./program hashType m
// hashType: Linear, Double, Quadratic or Universal
int main(int arc, char **argv)
{
	string hashType = argv[1];
	int m = atoi(argv[2]);

	bool hashTypeCheck = (hashType == "Linear" || hashType == "Double" || hashType == "Quadratic");

	Hash hash1(m); // Linear, Double, Quadratic  
	HashUniversal hash2(m); // Universal

	string operations;
	int key, new_key;

	ifstream input;
	input.open("operations.txt");

	if (input.is_open())
	{
		while (!input.eof())
		{
			input >> operations;
			if (operations == "insert"){
				input >> key;
				if (hashTypeCheck)
					hash1.insert(key, hashType);
				else if (hashType == "Universal")
					hash2.insert(key);
				else{
					cout << "There is no hashtype: " << hashType << endl;
					break;
				}
			}
			else if (operations == "update"){
				input >> key;
				input >> new_key;
				if (hashTypeCheck)
					hash1.update(key, new_key, hashType);
				else if (hashType == "Universal")
					hash2.update(key, new_key);
				else{
					cout << "There is no hashtype: " << hashType << endl;
					break;
				}
			}
			else if (operations == "search"){
				input >> key;
				if (hashTypeCheck)
					hash1.search(key, hashType);
				else if (hashType == "Universal")
					hash2.search(key);
				else{
					cout << "There is no hashtype: " << hashType << endl;
					break;
				}
			}
			else if (operations == "delete"){
				input >> key;
				if (hashTypeCheck)
					hash1.remove(key, hashType);
				else if (hashType == "Universal")
					hash2.remove(key);
				else{
					cout << "There is no hashtype: " << hashType << endl;
					break;
				}
			}
			else{
				cout << "error: there is no " << operations << " operation." << endl;
				break;
			}
		}
		int collusion = hashTypeCheck ? hash1.getCollusion() : hash2.getCollusion();
		cout << "\n" << hashType << ": " << collusion << "  collusions have been occured (m = " << m << ")." << endl;
		input.close();
	}
	else
		cout << "Unable to open operations.txt" << endl;

	getchar();

	return 0;
}
