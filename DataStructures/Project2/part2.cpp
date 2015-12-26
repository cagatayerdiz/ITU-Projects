#include <iostream>
#include <fstream>
#include <string>

using namespace std;

typedef char queuedatatype;//�rnekte char olarak verilmi�

struct node{
	char data;
	int priority;
	node *next;
};

struct Queue {
	node *front; //ilk eleman
	node *back;  //son eleman
	void create();
	void close();
	void enqueue(queuedatatype , int );//back'ten ekler
	queuedatatype dequeue();           //front'tan ��kar�r
	bool isempty();
};


int main()
{
	string komut;// command de�i�kenini dynamic olarak almak i�in tan�mlanm��t�r
	char *command;
	int priority;
	char task_name;
	Queue kuyruk;
	
	ifstream input;
	ofstream output;
	
	kuyruk.create();
	input.open("input2.txt", ios::in );
	output.open("output2.txt", ios::out);

	if(input.is_open())
	{
		while (input >> komut /*&& !input.eof()*/){//son sat�r� iki kere almas�n� engellemek amac�yla b�yle yaz�lm��t�r
			//input >> komut;					//while'�n i�ine yaz�lsayd� son sat�r� iki kere alacakt�
			command = new char[komut.length() + 1];	  
			strcpy(command, komut.data());				  
			command[komut.length()] = '\0';
			
			if(strcmp(command, "enqueue") == 0){
				input >> priority;
				input >> task_name;
				kuyruk.enqueue(task_name, priority);//�nceli�e g�re ekler
			}
			
			else if(strcmp(command, "dequeue") == 0){
				if(output.is_open())
					output << kuyruk.dequeue() << endl;//front'u ��kar�r
			}
		delete command;//memory deallocation	
		}
		
	}

	else//if(!input.is_open())
		cerr << "Unable to open input2.txt" << endl;
    
	if(output.good())
		cout << "File has been written successfully.";// << endl;
			
	else if(output.fail())
		cerr << "Unable to open output2.txt" << endl;
 
	input.close();
	output.close();
	kuyruk.close();//memory deallocation

	getchar();
	return EXIT_SUCCESS;
}

void Queue::create(){

	front = NULL; 
	back = NULL;
}

void Queue::close(){

	node *p;
	while (front) {//front bo�sa zaten girmez
		p = front;
		front = front->next;
		delete p;
	}
}

void Queue::enqueue(queuedatatype newdata, int no){

	node *newnode = new node;
	node *traverse = front;
	node *tail;
	newnode->data = newdata;
	newnode->priority = no;
	newnode->next = NULL;
	bool check = false;

	if ( isempty() ) {// front == NULL
		back = newnode;
		front = back;
	}

	else{

		if(no > front->priority){//en ba�a ge�er
			newnode->next = front;
			front = newnode;
		}

		while(traverse != NULL && no < traverse->priority)//araya eklenecekse ordaki yerine kadar ilerler
		{
			tail = traverse;
			traverse = traverse->next;
			check = true;//araya eklenmek istedi�ini g�sterir
		}

		if(traverse && check)//araya eklenecekse ekleme i�lemini yapar
		{
			newnode->next = traverse;
			tail->next = newnode;
		}

		else if(back->priority > no){//sona eklenecekse son elemana ekler
			back->next = newnode;
			back = newnode;
		}
	}
}

queuedatatype Queue::dequeue() {

	node *traverse;
	queuedatatype temp;
	traverse = front;
	front = front->next;
	temp = traverse->data;
	delete traverse;
	return temp;
}

bool Queue::isempty() {

	if(front == NULL)
		return true;
	else
		return false;
}

/*
void print(ofstream &file)
{
	node *traverse;
	traverse = kuyruk.front;

	if(file.is_open())
	{
		while(traverse){
			file << traverse->data << endl;
			traverse = traverse->next;
		}
	}
}
*/
