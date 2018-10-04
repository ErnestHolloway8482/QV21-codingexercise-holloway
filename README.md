Goal

The purpose of this project is to ascertain your level of skill in Android/Java development using a subset of the tools and models which we utilize at Qv21 Technologies, LLC.

The Project

The project involves the construction of a small standalone application to parse and show the contents of oil well data contained in a CSV file.  (the file is attached.)

Technologies

The application should be an Android application built in Android Studio utilizing MVC.

The project will be judged based on:

-	Code quality
-	Documentation
-	Sophistication of user interface


Requirements

-	Load the data file into memory (whatever construct you choose)
-	Display a grid containing Owner, Lease/Well Name, Tank Name, Tank Nbr
-	Allow drilling down into a row to edit all fields
-	This can be done by expanding the row or opening another fragment containing the information
-	Note: you do not need to store data back to the file, so simply editing in memory is sufficient.)
-	Be able to return to the main grid, showing any updated information if applicable

Data File

The data file consists of lines of data, much like the result of a SQL join query.

Each line contains data describing the well and each tank at the well.

There are duplicate data lines for each well if there is more than one tank at that well.
 
The data is structured as follows:

The hierarchy is Owner > Well > Tank.
a.       Owner
          i.      Well
1.       API
2.       Long
3.       Lat
4.       Property
5.       County
6.       Well Name
7.       SEC
8.       TWP
9.       RNG
10.   Tank
a.       Tank Name
b.      MID
c.       Tank Nbr
d.      Tank Size
e.      BBLS per inch
Final Project 

Return an Android Studio project that successfully builds to an APK and demonstrates the requirements as mentioned above.

