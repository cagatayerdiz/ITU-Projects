#include <iostream>
#include <string>
#include <iomanip>
using namespace std;

class EditDistance
{
private:
	int lengthofMisspelled, lengthofCorrected, *cost;
	int **distance;
public:
	EditDistance(string, string);
	int compare(string, string); // Damerau-Levenshtein distance
	void printMemoizationMatrix();
	~EditDistance();
};

int minimum(int first, int second, int third)
{
	if (first <= second && first <= third) return first;
	else if (second <= first && second <= third) return second;
	else return third;
}

int minimum(int first, int second)
{
	return first <= second ? first : second;
}

EditDistance::EditDistance(string misspelled_in, string corrected_in)
{
	// Assigning length
	lengthofMisspelled = misspelled_in.length();
	lengthofCorrected = corrected_in.length();
	cost = new int[3]; // insertion&deletion, substitution, transposition

	// Allocation
	distance = new int*[lengthofMisspelled + 1];
	for (int i = 0; i <= lengthofMisspelled; i++)
		distance[i] = new int[lengthofCorrected + 1];

	cout << "Minimum Edit Distance between " << misspelled_in << " and " << corrected_in << ": " << compare(misspelled_in, corrected_in) << endl;
}

int EditDistance::compare(string misspelled_in, string corrected_in)
{
	// insertion and deletion cost are equal for this question.
	cost[0] = 2;
	
	// initalize matrix using insertion/deletion cost
	for (int i = 0; i <= lengthofMisspelled; i++)
		distance[i][0] = i*cost[0];
	for (int j = 0; j <= lengthofCorrected; j++)
		distance[0][j] = j*cost[0];

	for (int i = 1; i <= lengthofMisspelled; i++){
		for (int j = 1; j <= lengthofCorrected; j++){
			if (misspelled_in[i - 1] == corrected_in[j - 1]) { 
				cost[1] = 0; cost[2] = 0; }
			else { cost[1] = 3; cost[2] = 2; }

			// deletion, insertion, substution
			distance[i][j] = minimum(distance[i - 1][j] + cost[0], distance[i][j - 1] + cost[0], distance[i - 1][j - 1] + cost[1]);
			
			if (i > 1 && j > 1 && misspelled_in[i - 1] == corrected_in[j - 2] && misspelled_in[i - 2] == corrected_in[j - 1]){
				// tranposition
				distance[i][j] = minimum(distance[i][j], distance[i - 2][j - 2] + cost[2]);
			}
		}
	}
	
	printMemoizationMatrix();
	
	return distance[lengthofMisspelled][lengthofCorrected];
}

void EditDistance::printMemoizationMatrix()
{
	cout << "Memoization Matrix:" << endl;
	for (int i = 0; i <= lengthofMisspelled; i++)
	{
		for (int j = 0; j <= lengthofCorrected; j++)
		{
			cout << left << setw(3) << distance[i][j];
		}
		cout << endl;
	}
	cout << endl;
}

EditDistance::~EditDistance()
{
	// Deallocation
	for (int i = 0; i <= lengthofMisspelled; i++)
	{
		delete[] distance[i];
	}
	delete[] distance;
	delete[] cost;
}

int main(int argc, char** argv)
{
	string misspelled = argv[1];
	string corrected = argv[2]; 
	
	EditDistance object(misspelled, corrected);

	return 0;
}