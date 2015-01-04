/*
* @Author
* Name: Çağatay Erdiz
* Number: 150110040
* Date: 2013-12-29
*/

#include <iostream>
#include <iomanip>
#include <fstream>
#include <cstring>

using namespace std;

struct node{
	char *data;
	int suspectcount;//search fonksiyonu için kullanılır, o anki node un kaç kez kullanıldığını belirtir
	node *left;
	node *right;
};

struct Tree{
	node *root;
	void create();
	void add(char *, char *);
	void search(node *, int );
	void makeempty(node *);
	void close(node *);
};

Tree suspect;

int main(){
	
	suspect.create();

	ifstream input;
	string satir;
	char *line;
	char *word;
	char *name;		//katillerin isimleri
	char *direction;//ağaca eklerkenki yönünü belirler
	int temp = 0;
	int length = 0;
	int wordcount;
	int steps = 0;//search fonksiyonuna yollanacak parametre katilin kaç adımda bulunduğunu gösterir

	input.open("suspects.txt", ios::in);

	if(input.is_open()){
		while(getline(input,satir) || !input.eof()){

			line = new char[satir.length() + 1];//dynamic allocation
			strcpy(line, satir.data());
			line[satir.length()] = '\0';

			wordcount = 0;//her satır için yeniden başlamalı
			direction = new char[8];//7 özellik var

			for(int i = 0; i < strlen(line); i++){//line[i] != '\0' koşulu da kullanılabilir
				
				if(line[i] != '\0' || isalpha(line[i])){//isalpha harf ise true döndürür

					temp = i;
					while(isalnum( line[temp] )){//burda kelimenin uzunluğu hesaplanır
						temp++;					 //ör:"ali" için temp indexi 3 ileriye bakacak length de en son 3 olur
						length++;
						}
				
					word = new char[length + 1];//dynamic allacation

					for(int index = 0 ; index < length ; index++){//burda harf harf bir diziye atanır
						word[index] = line[i];
						i++;
					}
					word[length] = '\0';//rastgele değer atamaması için
					length = 0;//her işlem için sıfırdan başlar
					
					switch(wordcount){//burada her şüphelinin ağaç içerisinde nasıl yerleştirileceği belirlenir
						case 0:
							name = new char[strlen(word) + 1];
							strcpy(name, word);
							name[strlen(word)] = '\0';
							break;
						case 1:
							if(!strcmp(word, "tall"))
								direction[wordcount - 1] = 'l';
							else if(!strcmp(word, "short"))
								direction[wordcount - 1] = 'r';
							break;
						case 2:
							if(!strcmp(word, "thin"))
								direction[wordcount - 1] = 'l';
							else if(!strcmp(word, "fat"))
								direction[wordcount - 1] = 'r';
							break;
						case 3:
							if(!strcmp(word, "female"))
								direction[wordcount - 1] = 'l';
							else if(!strcmp(word, "male"))
								direction[wordcount - 1] = 'r';
							break;
						case 4:
							if(!strcmp(word, "young"))
								direction[wordcount - 1] = 'l';
							else if(!strcmp(word, "old"))
								direction[wordcount - 1] = 'r';
							break;
						case 5:
							if(!strcmp(word, "caucasian"))
								direction[wordcount - 1] = 'l';
							else if(!strcmp(word, "asian"))
								direction[wordcount - 1] = 'r';
							break;
						case 6:
							if(!strcmp(word, "brown"))
								direction[wordcount - 1] = 'l';
							else if(!strcmp(word, "hazel"))
								direction[wordcount - 1] = 'r';
							break;
						case 7:
							if(!strcmp(word, "straight"))
								direction[wordcount - 1] = 'l';
							else if(!strcmp(word, "curly"))
								direction[wordcount - 1] = 'r';
							break;
						default:
							cout << "Error" << endl;
							break;	
					}

					wordcount++;

					delete  word;//deallocation
				}//if
			}//for

			direction[7] = '\0';//rastgele değer atamaması için

			//Adding on tree
			suspect.add(name, direction);
			
		}//while
	}//if

	else
		cerr << "Unable to open suspects.txt" << endl;

    cout << "Guess the murderer game\n" << endl;

	//Asıl görev yapan search fonksiyonu
	suspect.search(suspect.root, steps);
	getchar();

	//Deallocation
	suspect.close(suspect.root);
	getchar();

	return EXIT_SUCCESS;
}

void Tree::create(){

	root = NULL;
}

void Tree::add(char *name, char *direction){

	node *newnode;
	node *traverse;
	char *left_data[7] = {"tall", "thin", "female", "young", "caucasian", "brown", "straight"};//sol leaf 
	char *right_data[7] = {"short", "fat", "male", "old", "asian", "hazel", "curly"};		   //sağ leaf
	int index = 0;

	if(root == NULL){//root boşsa buraya girer
		newnode = new node;
		newnode->data = "";//root boş bırakılmalıdır çünkü önce 2 özellik eklenecek
		newnode->suspectcount = 0;
		newnode->left = NULL;		
		newnode->right = NULL;
		root = newnode;
	}

	root->suspectcount++;//eklenen her node root'tan itibaren yolunu çizer

	traverse = root;

	while(traverse != NULL){

		 newnode = new node;//new node kullanarak eklenecek her özellik için yer alınır
		 newnode->suspectcount = 1;
		 newnode->left = NULL;
		 newnode->right = NULL;

		if(direction[index] == 'l'){
			if(traverse->left == NULL){
				newnode->data = left_data[index];//özellikler newnode->data ya atanır
				traverse->left = newnode;
				traverse = traverse->left;
			}
			else//boş değilse aynı şeyi bir daha eklemesini engellemek için solundaki node'a ilerlenir
			{
				traverse->left->suspectcount++;//buraya geldiyse traverse->left önceden eklenmiş demektir
				traverse = traverse->left;
			}
			index++;//hem direction'ın hem de left_data[7] ve right_data[7] nın indexi olarak kullanılır
		}

		else if(direction[index] == 'r'){
			if(traverse->right == NULL){
				newnode->data = right_data[index];
				traverse->right = newnode;
				traverse = traverse->right;
			}
			else//boş değilse aynı şeyi bir daha eklemesini engellemek için sağındaki node'a ilerlenir
			{
				traverse->right->suspectcount++;//buraya geldiyse traverse->right önceden eklenmiş demektir
				traverse = traverse->right;
			}
			index++;
		}

		if(index == 7){//en son o özelliklere uygun isim atanır
			newnode = new node;
			newnode->left = NULL;
			newnode->right = NULL;
			newnode->data = name;
			newnode->suspectcount = 0;
			traverse->right = newnode;//sağ ya da sol olması farketmez ikisi de olur, o özelliklere ait kişiyi belirtir
			return;
		}
	}
}

void Tree::search(node *ptr, int steps){

	char c;
	char *array[7] = {"tall", "thin", "female", "young", "caucasian", "brown", "straight"};
	//suspectcount 1'den büyükse soru sorma işlemi devam eder
	//suspectcount = 1 ise katil o aşamada bulunmuştur
	//suspectcount = 0 ise isim yazdırma amaçlı kullanılır

	if(ptr){//eğer böyle biri varsa
		if(ptr->suspectcount == 1){//eğer buraya girerse katil bulundu anlamına gelir

			while(ptr->suspectcount){//bu aşamada suspectcount ya 1dir ya da 0 dır,1se döngü devam eder 0sa çıkar
				if(ptr->left != NULL)
					ptr = ptr->left;
				else if(ptr->right != NULL)
					ptr = ptr->right;

				if(ptr->suspectcount == 0){//isme ulaşıldığı anlamına gelir
					cout << endl;
					cout << ptr->data << " is the murderer." << endl;
					cout << "Determined in " << steps << " steps." << endl;
					getchar();
					return;
				}
			}
		}
	}

	else{//böyle biri yoksa
		cout << endl;
		cout << "No one has these physical properties" << endl;
		getchar();
		return;
	}

	cout << "Is the suspect " << left << setw(10) << array[steps] << " {Y/N}: ";
	cin >> c;
	c = toupper(c);

	steps++;//sorulan her soru bir aşamadır

	if(c == 'Y'){
		search(ptr->left,steps);
	}
	else if(c == 'N'){
		search(ptr->right,steps);
	}
	
}

//Deallocating memory
void Tree::makeempty(node *p){

	if(p){

		if(p->left){
			makeempty(p->left);
			p->left = NULL;
		}

		if(p->right){
			makeempty(p->right);
			p->right = NULL;
		}
		delete p;
	}
}

void Tree::close(node *p){

	makeempty(p);

	cout << "Memory has been successfully deallocated" << endl;
}
