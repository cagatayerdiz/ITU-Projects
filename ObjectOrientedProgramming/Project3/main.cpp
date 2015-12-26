#include <iostream>
#include <list>		// for Quote list
#include <string>
using namespace std;

class Quote{
private:
	string quote, owner, movie;
public:
	Quote();		// default constructor
	Quote(const string &, const string & = "", const string & = ""); // constructor with parameters last two parameters have default values
	void set_quote(const string &);
	void set_owner(const string &);
	void set_movie(const string &);
	string get_quote() const{ return quote; };
	string get_owner() const{ return owner; };
	string get_movie() const{ return movie; };
	~Quote();
};

class Movie{
private:
	string title;
	list<Quote> quote;		// list of quotes
public:
	Movie();				// default constructor
	Movie(const string &);	// constructor with parameters
	void operator+(const Quote &); // operator overloading
	void set_title(const string &);
	void print();
	~Movie();
};

// *** Movie Class ***
Movie::Movie():title("") { }

Movie::Movie(const string &title_in):title(title_in) { }

void Movie::operator+(const Quote &object_in)
{
	if(title == object_in.get_movie())
		quote.push_back(object_in);
	else // if titles mismatch
		throw "insertion error: titles mismatch";
}

void Movie::set_title(const string &title_in) { title = title_in; }

void Movie::print()
{
	if(quote.empty())
		cout << "There is no available quote for movie: " << title << endl;
	else{ // here, i holds current value of list(i has a ptr points that points to current quote)
		for(list<Quote>::iterator i = quote.begin() ; i != quote.end(); i++){
			cout << i->get_quote() << endl;
			cout << "\t\t(" << i->get_owner() << " - " 
				<< i->get_movie() << ")" << endl;}
	}
}

Movie::~Movie() { quote.clear(); }

// *** Quote Class ***
Quote::Quote():quote(""), owner(""), movie("") { }

Quote::Quote(const string &quote_in, const string &owner_in, const string &movie_in):quote(quote_in), owner(owner_in), movie(movie_in) { }

void Quote::set_quote(const string &quote_in) { quote = quote_in; }

void Quote::set_owner(const string &owner_in) { owner = owner_in; }

void Quote::set_movie(const string &movie_in) { movie = movie_in; }

Quote::~Quote() { }

int main(){
	Quote q1;
	q1.set_quote("it's a trap!");
	q1.set_owner("admiral ackbar");
	q1.set_movie("return of jedi");

	Quote q2("eight year olds dude...", "walter sobchak", "the big lebowski");
	Quote q3("if you want to survive out here, you've got to know where your towel is.");
	q3.set_owner("ford prefect");
	q3.set_movie("the hitchhiker's guide to the galaxy");
	Quote q4("this will all end in tears.", "marvin", "the hitchhiker's guide to the galaxy");
	Quote q5("what does marcellus wallace look like?", "jules winfield", "pulp fiction");

	Movie m1;
	m1.set_title("reservoir dogs");
	Movie m2("the hitchhiker's guide to the galaxy");
	Movie m3("the big lebowski");

	try { m2 + q3; } catch(const char *m) { cout << m << endl; }
	try { m2 + q4; } catch(const char *m) { cout << m << endl; }
	try { m3 + q2; } catch(const char *m) { cout << m << endl; }
	try { m1 + q5; } catch(const char *m) { cout << m << endl; }
	m2.print();

	getchar();
	return 0;
}
