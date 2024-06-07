//M. M. Kuttel 2024 mkuttel@gmail.com
//package barScheduling;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/*
 This is the basicclass, representing the patrons at the bar
 */

public class Patron extends Thread {
	
   private Random random = new Random();// for variation in Patron behaviour

   private CountDownLatch startSignal; //all start at once, actually shared
   private Barman theBarman; //the Barman is actually shared though
   private static int noPatrons;
   private int ID; //thread ID 
   private int lengthOfOrder;
   private long startTime, endTime, start, firstDrink; //for all the metrics
   private static int patronsServed = 0;
   private long totalPreparationTime =0;
   private long responseTime=0;
   private long throughput=0;
   
	
   public static FileWriter fileW;


   private DrinkOrder [] drinksOrder;
   
    static {
        noPatrons = SchedulingSimulation.noPatrons;
    }
	
   Patron( int ID,  CountDownLatch startSignal, Barman aBarman) {
      this.ID=ID;
      this.startSignal=startSignal;
      this.theBarman=aBarman;
      this.lengthOfOrder=random.nextInt(5)+1;//between 1 and 5 drinks
      drinksOrder=new DrinkOrder[lengthOfOrder];
   }
	
   public  void writeToFile(String data) throws IOException {
      synchronized (fileW) {
         fileW.write(data);
      }
   }
	
	

   public void run() {
      try {
      	//Do NOT change the block of code below - this is the arrival times
         startSignal.countDown(); //this patron is ready
         startSignal.await(); //wait till everyone is ready
         int arrivalTime = random.nextInt(300)+ID*100;  // patrons arrive gradually later
         sleep(arrivalTime);// Patrons arrive at staggered  times depending on ID 
         System.out.println("thirsty Patron "+ this.ID +" arrived");
      	//END do not change
      	
           //create drinks order
         for(int i=0;i<lengthOfOrder;i++) {
            drinksOrder[i]=new DrinkOrder(this.ID);}
            
            
         System.out.println("Patron "+ this.ID + " submitting order of " + lengthOfOrder +" drinks"); //output in standard format  - do not change this
         startTime = System.currentTimeMillis();//started placing orders
         for(int i=0;i<lengthOfOrder;i++) {
            System.out.println("Order placed by " + drinksOrder[i].toString());
            theBarman.placeDrinkOrder(drinksOrder[i]);
         }
         for(int i=0;i<lengthOfOrder;i++) {
            drinksOrder[i].waitForOrder();
            //first drink
            if(i==0){
            firstDrink = System.currentTimeMillis();}
            //total time it took for barman to make order
            totalPreparationTime += drinksOrder[i].getExecutionTime();
         
         }
         
        
         endTime = System.currentTimeMillis();
         patronsServed++; //try to count patrons served
         //totalTime==turnaroundtime
         long totalTime = endTime - startTime;
         //how long each patron waits while their order is not being worked on
         long waitingTime = totalPreparationTime;
         //response time
         responseTime = firstDrink - startTime;
         //throughput
         throughput = totalTime/patronsServed;
      	
         //writeToFile(String.format("Patron %d: Arrival Time: %d, turnaround Time: %d, Waiting Time: %d, Response Time: %d, ThroughPut: %d, Served: %d \n", ID,arrivalTime,totalTime,waitingTime,responseTime,throughput,patronsServed));
         writeToFile(String.format("%d %d %d %d %d %d %d \n", ID,arrivalTime,totalTime,waitingTime,responseTime,throughput,patronsServed));
         //writeToFile(String.format("%d \n",arrivalTime));
         System.out.println("Patron "+ this.ID + " got order in " + totalTime);
      	
      	
      } catch (InterruptedException e1) {  //do nothing
      } catch (IOException e) {
      	//  Auto-generated catch block
         e.printStackTrace();
      }
   }
}
	

