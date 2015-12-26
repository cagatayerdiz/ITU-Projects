#include <iostream>
#include <fstream>
#include <limits>
using namespace std;

class Connection
{
private:
	int city1, city2;
	float cost;
public:
	Connection(){}
	Connection(int c1_in, int c2_in, float cost_in) :city1(c1_in), city2(c2_in), cost(cost_in){}
	int getCity1(){ return city1; }
	int getCity2(){ return city2; }
	float getCost(){ return cost; }
	~Connection(){}
};

class City{
private:
	int size, id;
	float *neighbourList, distance;
	bool visited;
public:
	City() :distance(numeric_limits<float>::max()), visited(false){} // başlangıç olarak dijkstraya göre bunlar atanır
	City(int id_in, int numberofCity, float *list_in);
	void operator=(const City &);
	float *getNeighbourList(){ return neighbourList; }
	float getDistance(){ return distance; }
	void setDistance(float distance_in){ distance = distance_in; }
	bool getVisited(){ return visited; }
	void setVisitedTrue(){ visited = true; }
	int getId(){ return id; }
	int getDegree();
	void initializeForDijkstra(){ distance = numeric_limits<float>::max(); visited = false; }
	~City();
};

City::City(int id_in, int numberofCity, float *list_in) :id(id_in),distance(numeric_limits<float>::max()), visited(false)
{
	neighbourList = new float[numberofCity];
	size = numberofCity;
	for (int i = 0; i < numberofCity; i++)
		neighbourList[i] = list_in[i];
}

void City::operator=(const City &object_in) // operator overloading(class içinde pointer olduğu için gerekli)
{
	id = object_in.id;
	distance = object_in.distance;
	visited = object_in.visited;
	size = object_in.size;
	neighbourList = new float[size];
	for (int i = 0; i < size; i++){
		neighbourList[i] = object_in.neighbourList[i];
	}
}

int City::getDegree()
{
	int degree = 0;

	for (int i = 0; i < size; i++)
		if (neighbourList[i] != 0){
			degree++;
		}
	return degree;
}

void swapCity(City *x, City *y)
{
	City temp = *x;
	*x = *y;
	*y = temp;
}

void bubbleSort(City *array_in, int size) // findCentralCity fonksiyonunda degree'ye göre sıralamak için çağrılır
{
	for (int i = 0; i < size - 1; i++){
		for (int j = 0; j < size - 1; j++)
			if (array_in[j].getDegree() < array_in[j + 1].getDegree())
				swapCity(&array_in[j], &array_in[j + 1]);
	}
}

City::~City()
{
	delete[] neighbourList;
}

class ITUTelecom{
private:
	int numberofCity, numberofConnection, centralCity;
	float **adjacencyList; // dijkstra için
	Connection *connectionList, *minimizedList; // kruskal için
	City *cityList; // dijkstra için
public:
	ITUTelecom(char *);
	void exchange(Connection *, Connection *);
	int partition(int, int);
	void quickSort(int, int);
	void kruskalAlgorithm();
	void printNetwork(ofstream &);
	float findTotalCost();
	int findMinCostCity();
	void dijkstraAlgorithm(int);
	int findCentralCity();
	float costToHeadOffice();
	void writetoFile(char *);
	~ITUTelecom();
};

ITUTelecom::ITUTelecom(char * inputFileName)
{
	ifstream input;
	int c1, c2, index = 0;
	float cost;

	input.open(inputFileName);
	if (input.is_open()){
		input >> numberofCity;
		input >> numberofConnection;

		// allocation
		adjacencyList = new float*[numberofCity];
		for (int i = 0; i < numberofCity; i++){
			adjacencyList[i] = new float[numberofCity];
			for (int j = 0; j < numberofCity; j++)
				adjacencyList[i][j] = 0;
		}
		connectionList = new Connection[numberofConnection];
		minimizedList = new Connection[numberofCity - 1]; // n tane city arasında n-1 yol bulunur
		cityList = new City[numberofCity];

		while (!input.eof()){
			input >> c1 >> c2 >> cost; // dosyadan okuma

			adjacencyList[c1][c2] = cost; // 1 atamak yerine cost atanır
			adjacencyList[c2][c1] = cost;
			Connection temp(c1, c2, cost);
			connectionList[index] = temp;
			index++;
		}
		input.close();
	}
	else
		cout << "Unable to open " << inputFileName << endl;

}

void ITUTelecom::exchange(Connection *x, Connection *y)
{
	Connection temp = *x;
	*x = *y;
	*y = temp;
}

int ITUTelecom::partition(int p, int r)
{
	float x = connectionList[r].getCost();
	int i = p - 1;
	for (int j = p; j < r; j++)
	{
		if (connectionList[j].getCost() <= x){
			i = i + 1;
			exchange(&connectionList[i], &connectionList[j]);
		}
	}
	exchange(&connectionList[i + 1], &connectionList[r]);
	return i + 1;
}

void ITUTelecom::quickSort(int p, int r)
{
	if (p < r){
		int q = partition(p, r);
		quickSort(p, q - 1);
		quickSort(q + 1, r);
	}
}

class Set{ 
private:
	int *parent; 
	int *rank;
public:
	Set(int);
	int findSet(int);
	void unionSet(int, int);
	~Set();
};

Set::Set(int numberofCity)
{
	parent = new int[numberofCity];
	rank = new int[numberofCity];

	for (int i = 0; i < numberofCity; i++){
		parent[i] = i;
		rank[i] = 0;
	}
}

// path compression tekniğiyle findSet
int Set::findSet(int i)
{
	if (parent[i] != i){
		parent[i] = findSet(parent[i]);
	}
	return parent[i];
}

 // rank kontrolüyle unionSet
void Set::unionSet(int x, int y)
{
	int xroot = findSet(x);
	int yroot = findSet(y);

	if (rank[xroot] < rank[yroot]){
		parent[xroot] = yroot;
	}
	else if (rank[xroot] > rank[yroot]){
		parent[yroot] = xroot;
	}
	else{
		parent[yroot] = xroot;
		rank[xroot]++;
	}
}

Set::~Set()
{
	delete[] parent;
	delete[] rank;
}

void ITUTelecom::kruskalAlgorithm()
{
	// connectionList yol uzunluklarına göre sıralandı
	quickSort(0, numberofConnection - 1);
	int index = 0;

	// MAKE-SET (rank ve parent değerlerini ata)
	Set s(numberofCity);

	for (int i = 0; i < numberofConnection; i++)
	{
		// u yolun başı, v yolun sonu
		int u = s.findSet(connectionList[i].getCity1());
		int v = s.findSet(connectionList[i].getCity2());

		// FIND-SET(city1) != FIND-SET(city2) (loop içermezse)
		if (u != v){
			// kruskal dizisine yolu attı
			minimizedList[index] = connectionList[i];
			// UNION(u, v) (o yolun kullanıldığı belirtildi)
			s.unionSet(u, v);
			index++;
		}
	}
}

void ITUTelecom::printNetwork(ofstream &file)
{
	for (int i = 0; i < numberofCity - 1; i++){
		file << "(" << minimizedList[i].getCity1() << "," << minimizedList[i].getCity2() << ")" << endl;
	}
	file << endl;
}

float ITUTelecom::findTotalCost()
{
	float totalCost = 0.0;

	for (int i = 0; i < numberofCity - 1; i++){
		totalCost += minimizedList[i].getCost();
	}
	return totalCost;
}

int ITUTelecom::findMinCostCity()
{
	int minIndex;
	float minCost = numeric_limits<float>::max();
	// başta hepsine sonsuz atamıştık değişen birşey var mı diye bakılır
	for (int i = 0; i < numberofCity; i++){
		if (cityList[i].getVisited() == false && cityList[i].getDistance() < minCost){
			minCost = cityList[i].getDistance();
			minIndex = i;
		}
	}
	return minIndex;
}

void ITUTelecom::dijkstraAlgorithm(int source)
{
	// distance = infinity, visited = false
	for (int i = 0; i < numberofCity; i++)
		cityList[i].initializeForDijkstra();

	// kaynağın distance değeri 0 yapılır
	cityList[source].setDistance(0);

	for (int i = 0; i < numberofCity; i++){
		// artık bu seçildi
		int u = findMinCostCity();

		// ziyaret edildi true
		cityList[u].setVisitedTrue();

		// u'nun komşularına bakılır
		for (int j = 0; j < numberofCity; j++){
			// u'nun komşuluk listesinde 0 olmayan indexler ona bağlıdır, ziyaret edilmediyse kullanılır.
			// mesafenin <float>max eşit olup olmadığı newDistance değeri hesaplanırken sıkıntı çıkmasın diye yapılır
			if ((!cityList[j].getVisited()) && (cityList[u].getNeighbourList()[j] != 0) && (cityList[u].getDistance() != numeric_limits<float>::max())){
				// o şehre ulaşılabilen yeni uzaklık hesaplanır
				// adjacency matrixte komşusu varsa 1 tutmak yerine cost tutulur
				float newDistance = cityList[u].getDistance() + cityList[u].getNeighbourList()[j];

				// yeni uzaklık eski uzaklıktan küçükse(bu genelede sourcecity diğerlerine direk bağlı değilse yapılır.)
				if (newDistance < cityList[j].getDistance()){
					// yeni uzaklık şehre atanır.
					cityList[j].setDistance(newDistance);
				}
			}
		}
	}
}

int ITUTelecom::findCentralCity()
{
	float totalCost = numeric_limits<float>::max();
	int maxDegree, maxDegreeCityCount = 0;

	City *possibleHeadOfficeList; // degree'lere göre sıralı listeyi tutacak
	possibleHeadOfficeList = new City[numberofCity];

	// şehirlere ilgili bilgiler atanır
	for (int i = 0; i < numberofCity; i++){
		City c(i, numberofCity, adjacencyList[i]);
		cityList[i] = c; // operator overloading
		possibleHeadOfficeList[i] = c;
	}

	bubbleSort(possibleHeadOfficeList, numberofCity); // max degree'den min degree'ye sıralandı

	maxDegree = possibleHeadOfficeList[1].getDegree(); // max degree bulundu, şimdi bu degree'ye sahip kaç şehir var o bulunacak

	for (int i = 0; i < numberofCity; i++){
		if (possibleHeadOfficeList[i].getDegree() == maxDegree)
			maxDegreeCityCount++;
	}

	if (maxDegreeCityCount == 1) // max degree'ye sahip bir tane varsa dijkstra'ya burda ihtiyaç yok
	{
		centralCity = possibleHeadOfficeList[0].getId();
		return centralCity;
	}
	
	City *finalHeadOfficeList; // bu max degreeye sahip şehirleri tutar
	float *finalHeadOfficeCostList; 
	finalHeadOfficeList = new City[maxDegreeCityCount]; // max degreeye sahip olan şehirlerin total costu hesaplanacak
	finalHeadOfficeCostList = new float[maxDegreeCityCount]; // her birinin ayrı ayrı costu hesaplanacak
	for (int i = 0; i < maxDegreeCityCount; i++){
		finalHeadOfficeList[i] = possibleHeadOfficeList[i];
		finalHeadOfficeCostList[i] = 0.0;
	}

	for (int i = 0; i < maxDegreeCityCount; i++){
		dijkstraAlgorithm(finalHeadOfficeList[i].getId());
		for (int j = 0; j < numberofCity; j++){
			finalHeadOfficeCostList[i] += cityList[j].getDistance();
		}
		if (finalHeadOfficeCostList[i] < totalCost){ // costları eşit gelse bile en küçük indise sahip şehir alınır
			totalCost = finalHeadOfficeCostList[i];
			centralCity = finalHeadOfficeList[i].getId();
		}
	}
	
	return centralCity;
}

float ITUTelecom::costToHeadOffice()
{
	float costToHeadOffice = 0.0;

	dijkstraAlgorithm(centralCity); // findCentralCity ve costToHeadOffice ayrı ayrı fonksiyon istendiği için dijkstra çağrıldı

	for (int i = 0; i < numberofCity; i++){
		costToHeadOffice += cityList[i].getDistance();
	}
	return costToHeadOffice;
}

ITUTelecom::~ITUTelecom()
{
	for (int i = 0; i < numberofCity; i++){
		delete[] adjacencyList[i];
	}
	delete[] adjacencyList;

	delete[] connectionList;
	delete[] minimizedList;
	delete[] cityList;
}

int main(int argc, char *argv[])
{
	char *inputFileName = argv[1];
	char *outputFileName = argv[2];

	ofstream output;
	output.open(outputFileName);

	ITUTelecom network(inputFileName);

	if (output.is_open()){
		network.kruskalAlgorithm();
		network.printNetwork(output);
		output << "The network total cost :\t" << network.findTotalCost() << endl;

		output << "The central city	   :\t" << network.findCentralCity() << "th" << endl;
		output << "The total cost of communication between the head office :\t" << network.costToHeadOffice() << endl;
		
		output.close();
	}
	else
		cout << "Unable to open " << outputFileName << endl;

	return 0;
}
