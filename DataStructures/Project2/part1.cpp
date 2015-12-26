#include <iostream>
#include <string>
#include <fstream>

using namespace std;

typedef char stackdatatype;//�rnekte char olarak verilmi�

struct node{
	stackdatatype data;
	node *next;
	node *previous;
};

struct Stack{
	node *head;
	void create();
	void close();
	void push(stackdatatype );//
	stackdatatype pop();
	bool isempty();
};

int main()
{
	Stack islem;
	string satir;//line de�i�kenini dynamic olarak almak i�in tan�mlanm��t�r
	char *line;
	int i, k, product = 1;
	int s1, s2, sonuc;
	int temp, basamak = 0; 
	char sayi1, sayi2, result;
	double a = 0;

	ifstream input;
	ofstream output;
	islem.create();
	input.open("input1.txt", ios::in );
	output.open("output1.txt", ios::out);

	if(input.is_open()){
		while (getline(input, satir) || !input.eof()){//while (!input.eof())
			line = new char[satir.length() + 1];	  //  getline(input, satir);	
			strcpy(line, satir.data());				  //while e�er b�yle yaz�lsayd� sat�r say�s�ndan bir fazla d�necekti
			line[satir.length()] = '\0';					
			for(i = strlen(line) - 1 ; i >= 0 ; i-- ){//sa�dan sola tarama yap�l�yor
				if(line[i] != ' '){//bo�luksa hi� girmesin

					if(line[i] != '*' && line[i] != '/' && line[i] != '+' && line[i] != '-')
					{
						if(line[i-1] >= '0' && line[i-1] <= '9'){//iki veya daha fazla basamakl�ysa
							temp = i;
							while(line[temp] != 32){//32 bo�luk karakterinin ascii kodu, 
								temp--;				//say�n�n bitti�i yere kadar gidip basamak say�s�n�n bir eksi�ini bulacak
								basamak++;}			

							for(k = 0 ; k < basamak ; k++){//basamak say�s� kadar d�n�p hesaplayacak
								if(k > 0)//pow fonksiyonu linuxta hata verdi�inden b�yle bir yol kullan�ld�
			                       product *= 10;
                                a += (int)(line[i] - 48) * product;//product burada pow(10, k) yerine kullan�lm��t�r
								//hesaplama yap�l�rken integer'a d�n���t�r�lmelidir
                                i--;                                                
							}
							islem.push( (char)(a+48) );//push fonksiyonu char kabul eder
							a = 0;//
							basamak = 0;
							product = 1;
						}
						else{//tek basamakl�ysa
							islem.push(line[i]);	
						}

					}

					else{//+ - * / ise i�lem stack'ine push
						sayi1 = islem.pop();
						sayi2 = islem.pop();
						
						s1 = sayi1 - 48;//i�lem yapmak i�in chardan integer'a �evrildi
						s2 = sayi2 - 48;//bir say�n�n integer hali ile char aras�nda 48 fark vard�r
						
						if(line[i] == '+')//+ ise topla
							sonuc = s1 + s2;
						else if(line[i] == '-')//- ise ��kar
							sonuc = s1 - s2;
						else if(line[i] == '*')//* ise �arp
							sonuc = s1 * s2;
						else if(line[i] == '/')// / ise b�l
							sonuc = s1 / s2;

						result = sonuc + 48;//push fonksiyonu char kabul ediyor
						islem.push(result);
					}
				}
			}//for bitti
			if(output.is_open())
				output << islem.pop() - 48 << endl;//output1.txt dosyas�na yazd�r�l�yor(yazd�r�l�rken integer de�eri yazd�r�l�r)
			else
				cerr << "Unable to open output1.txt" << endl;

			delete line; //memory deallocation
		}
	}
	else
		cerr << "Unable to open input1.txt" << endl;
    
    if(output.good())
		cout << "File has been written successfully.";// << endl;
  
	input.close();
	output.close();
	islem.close();//memory deallocation

	getchar();
	return EXIT_SUCCESS;
}

void Stack::create(){
	
	head = NULL;
}

void Stack::close(){
	
	node *traverse;
	while (head){
		traverse = head;
		head = head->next;
		delete traverse;
	}
}
void Stack::push(stackdatatype newdata){
	
	node *newnode = new node;
	newnode->data = newdata;
	newnode->previous = NULL;
	newnode->next = NULL;

	if(head == NULL)
		head = newnode;

	else{
		newnode->next = head;
		head->previous = newnode;
		head = newnode;
	}
}

stackdatatype Stack::pop(){
	
	node *traverse;
	stackdatatype temp;
	traverse = head;

	if (head == NULL)
		return '\0';

	else{
		head = head->next;
		temp = traverse->data;
		traverse->next = NULL;

		if(traverse->next != NULL)//olmayan bir�eyin previosu'una NULL atamamak i�in,stack'te tek eleman varken hata vermemesi ama�l� kullan�lm��t�r
			head->previous = NULL;

		delete traverse;
		return temp;
	}
}

bool Stack::isempty(){
	
	if(head == NULL)
		return true;
	else
		return false;
}
