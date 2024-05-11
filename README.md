# Operating-Systems 2 THE MASTER SCHEDULE: ANDRE STRIKES AGAIN 

Andre the Barman operates on a First Come First Served (FCFS) schedule. In this assignment, I implemented an alternative Shortest Job First (SJF) schedule

This is a Java program that simulates a system to schedule orders placed by patrons for drinks, which are then prepared by a barman. The program allows for customization of the number of patrons and supports multiple scheduling algorithms.

**To compile the Java files**: navigate to the directory that contains the Java files (`SchedulingSimulation.java`, `Barman.java`, `Patron.java`, `DrinkOrder.java`) using a terminal or command prompt. Then, compile the Java files using the `javac` command as follows:

**javac barScheduling/*.java**
will run the program using the default values set in the program

**java barScheduling.SchedulingSimulation <number_of_patrons> <scheduling_algorithm>**
option `1`: is First Come First Served
option `2`: is Shortest Job First

Alternatively, you can use the provided `makefile` to compile the Java files.
