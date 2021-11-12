# Assignment 0 - Object Oriented Programming

**Yanir Cohen**  
**Netanel Levine**  


## Sources:

  - https://www.youtube.com/watch?v=siqiJAJWUVg
  - https://github.com/00111000/Elevator-Scheduling-Simulator
  - https://www.researchgate.net/publication/31595225_Estimated_Time_of_Arrival_ETA_Based_Elevator_Group_Control_Algorithm_with_More_Accurate_Estimation



## Offline Algorithm:

Our offline algorithm is similar to the Look algorithm with some modifications.
First we will divide it into 2 arrays , one for all the calls which are going up and one for all the calls which are going down.
If there are 2 calls in the same direction the elevator will go to the furthest in the zone and will pick up both (furthest means the call whose source floor is further from the destination), calculates the travel time between picking them both at once or picking one taking him to the destination and returning and picking the other up.
Note: all that assuming there isn’t any other available elevator.

If there are any other calls in the route to the destination pick them up unless they are in the opposite direction.
When an elevator has reached the destination, go to the next and nearest call source floor and check 2.




## Online Algorithm:

1. First divide into 3 cases: flag 0, flag 1 and flag 2.

   - Flag 0 will be designed for a case in which the building has only one Elevator.

   - Flag 1 is designed for a building with slow Elevators and a high amount of floors.

   - Flag 2 is designed for a building with fast Elevators or a decent amount of floors.

2. Flag 0 will check if there is a stop in between the calls and pick them up otherwise it will complete its’ existing route.
     
3. Flag 1 will have an elevator waiting for long travels such as 60 floors
Meaning we will go over the route of each elevator in the building and will choose the elevator by which one will finish its route the fastest after adding the call.
This will be done by calculating the floors in each route and dividing by the speed, then adding to the one with the lowest time to finish.
However there will be an elevator which waits and picks up all the long calls and waits then travels again and again from top to bottom.

4. Flag 2 will go from the fastest to reach the call and finish it.
Meaning we will go over the route of each elevator in the building and will choose the elevator by which one will finish its route the fastest after adding the call.
  
    
---
  
   ![Elevator](https://user-images.githubusercontent.com/74298433/141529107-3a273664-8d12-4cb6-88b6-4b2427c20cf9.png)
   <p align="center">
    <img width="460" height="300" src="http://www.fillmurray.com/460/300">
   </p>
