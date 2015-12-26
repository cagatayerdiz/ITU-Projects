#include <iostream>
#include <string>
#include <fstream>

using namespace std;

typedef char stackdatatype;//örnekte char olarak verilmiþ

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
	string satir;//line deðiþkenini dynamic olarak almak için tanýmlanmýþtýr
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
			strcpy(line, satir.data());				  //while eðer böyle yazýlsaydý satýr sayýsýndan bir fazla dönecekti
			line[satir.length()] = '\0';					
			for(i = strlen(line) - 1 ; i >= 0 ; i-- ){//saðdan sola tarama yapýlýyor
				if(line[i] != ' '){//boþluksa hiç girmesin

					if(line[i] != '*' && line[i] != '/' && line[i] != '+' && line[i] != '-')
					{
						if(line[i-1] >= '0' && line[i-1] <= '9'){//iki veya daha fazla basamaklýysa
							temp = i;
							while(line[temp] != 32){//32 boþluk karakterinin ascii kodu, 
								temp--;				//sayýnýn bittiði yere kadar gidip basamak sayýsýnýn bir eksiðini bulacak
								basamak++;}			

							for(k = 0 ; k < basamak ; k++){//basamak sayýsý kadar dönüp hesaplayacak
								if(k > 0)//pow fonksiyonu linuxta hata verdiðinden böyle bir yol kullanýldý
			                       product *= 10;
                                a += (int)(line[i] - 48) * product;//product burada pow(10, k) yerine kullanýlmýþtýr
								//hesaplama yapýlýrken integer'a dönüþütürülmelidir
                                i--;                                                
							}
							islem.push( (char)(a+48) );//push fonksiyonu char kabul eder
							a = 0;//
							basamak = 0;
							product = 1;
						}
						else{//tek basamaklýysa
							islem.push(line[i]);	
						}

					}

					else{//+ - * / ise iþlem stack'ine push
						sayi1 = islem.pop();
						sayi2 = islem.pop();
						
						s1 = sayi1 - 48;//iþlem yapmak için chardan integer'a çevrildi
						s2 = sayi2 - 48;//bir sayýnýn integer hali ile char arasýnda 48 fark vardýr
						
						if(line[i] == '+')//+ ise topla
							sonuc = s1 + s2;
						else if(line[i] == '-')//- ise çýkar
							sonuc = s1 - s2;
						else if(line[i] == '*')//* ise çarp
							sonuc = s1 * s2;
						else if(line[i] == '/')// / ise böl
							sonuc = s1 / s2;

						result = sonuc + 48;//push fonksiyonu char kabul ediyor
						islem.push(result);
					}
				}
			}//for bitti
			if(output.is_open())
				output << islem.pop() - 48 << endl;//output1.txt dosyasýna yazdýrýlýyor(yazdýrýlýrken integer deðeri yazdýrýlýr)
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

		if(traverse->next != NULL)//olmayan birþeyin previosu'una NULL atamamak için,stack'te tek eleman varken hata vermemesi amaçlý kullanýlmýþtýr
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
