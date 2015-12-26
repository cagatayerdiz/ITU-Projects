Project1 Description
====================


**Problem:** In this project, you are expected to find the set of M people among a set of N people, whose Body Mass Index BMI values are farthest from a given threshold.

In a region of city A, public health officials try to reveal if the people of this region have excessive fatness (obesity) or excessive weakness. For this purpose body mass index information of these people will be used and people who are farthest from a certain BMI threshold will be examined.

BMI is one of the determinative issues for the people who have weight problems. This index is calculated by dividing weight in kilograms to square of the height in meters.

`BMI=mass(kg)/((height(m))*(height(m)))`

For this work, weight and height data of N people were collected in a dataset (height_ weight.txt).

You will take the absolute value of the difference between the BMI value and threshold value for each person. This difference represents how close the BMI value is to the threshold value. The people whose BMI values are much higher (excessive fatness) or much lower (excessive weakness) than the threshold will be examined.

**Example:**

Threshold = 22

1.st person’s BMI= 30 

2.nd person’s BMI= 14

Both of these two people have the same distance to the threshold, because 
|22-30|=|22-14|

In this region, there is only one hospital and its public health clinic is able to give service to M patients at the same time (the rest of the patients will be send to another hospitals). Hence the first M people among N people whose BMI value has more difference are chosen. So you will sort the absolute differences between BMI values of the people and threshold. And you will choose M highest difference valued records.

**`N:`** Total number of people

**`M:`** Number of people whose BMI values are the farthest from the threshold.

**`algorithmType:`** Method to be used to solve the problem (Insertion Sort, Merge Sort or Linear Search)

**`threshold:`** Normal value of the BMI value.

After execution of your program, an output file should be created that lists M people with their ids, height, weight and BMI value.

All your code must be written in C++ using object oriented approach and able to compile and run using g++.


