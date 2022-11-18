 # **CS301 : Database Management System**
Submitted By: Yash Mittal , Tanishk Gupta ,  Swapnil Saurav

Entry Number: 2019EEB1210 , 2019eb1205 , 2019eeb1204 

Date: 18.11.2022

---
**File Structure**

```
CS301_IRCTC_DB_PROJECT
│─── input
│    │─── train_search.txt
│    │─── Trainschedule.txt
│    │─── pool-1-thread-1_input.txt
│    :
│    └─── pool-2-thread-50_input.txt
│─── output
│    └─── .gitkeep
│─── client.java
│─── creds.java
│─── release_train.java 
│─── ServiceModule.java
│─── train_search.java
│─── init.sql
│─── org.jdbc_driver.jar
└─── README.md
```

---
**Add data into PGSQL Database**
* Copy and paste queries from the file ```./init.sql```
---
**Compile the credentials**
* Update the credentials in the ```./creds.java``` file.
* Compile the ```./creds.java``` file using
```
javac -d . creds.java
```
---

## Part 1 (Ticket Booking)


**How to run the code for Server Side** 

* Upload the release train input in ```./input/Trainschedule.txt``` file.

* Run the ```./release_train.java``` file using
  ```
  java -cp org.jdbc_driver.jar release_train.java 
  ```
  
* Run the ```./ServiceModule.java``` file using
  ```
  java -cp org.jdbc_driver.jar ServiceModule.java 
  ```
  
**How to run the code for Client Side** 

* Upload the input file in ```./input/``` directory.
* Run the ```./client.java``` file using
  ```
  java client.java
  ```


**Output Format**

* Released train status will be saved in ```./output/Trainschedule_status.txt```
  
  The output will be in the format : 

  ```<Train_No> <Date> <Status>```

* Booked ticket status will be saved in ```./output/``` folder
  
  The output will be in the format : 
  
  ```<Status> -- <PNR> ; <Train No> ; <Date> ; <Coach Type> ; <Passenger Name> - <Coach No>/<Seat No>/<Berth Type>```


---

## Part 2 (Train Search)
**How to run the code** 

* Upload the train search input in ```./input/train_search.txt``` file in the format
```<Source Station> <Destination Station>``` followed by ```#``` at the end of file.

* Run the ```./train_search.java``` file using
  ```
  java -cp org.jdbc_driver.jar train_search.java 
  ```

**Output Format**
* Train Search Data will be saved in ```./output/train_search.txt```
  
  The output will be in the format : 

  ```<Total Duration> | <Trains Info>```

*Note: Two trains are considered to be compatible if the time difference between the arrival of Train 1 and departure of Train 2 is in the range [10mins , 3hrs].*
