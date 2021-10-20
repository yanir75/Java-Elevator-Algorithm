package ex0.algo;

import ex0.Building;
import java.math.*;

import java.util.ArrayList;
public class Zones {
    private ArrayList<Zone> _zones;


    public Zones(Building b) {
        int maxFloor = b.maxFloor();
        int minFloor = b.minFloor();
        int numberOfElevators = b.numberOfElevetors();
        int numberOfFloors = maxFloor - minFloor + 1;
        /* Each Zone in the Building is in the size of num of floors / num of elevators,
           Except the last zone which will have the reminder.
         */
        int sizeOfZone = (int)(Math.ceil(numberOfFloors / numberOfElevators));
        _zones = new ArrayList<Zone>(numberOfElevators);
        int amountOfZones = numberOfElevators; // Amount of zones is equal to the amount of elevators.
        int i  = 0;
        int reminderOfFloors = (numberOfFloors % numberOfElevators);
        if(reminderOfFloors != 0){
            int[] tempArr1 = {minFloor, minFloor + reminderOfFloors};
            _zones.add(new Zone(tempArr1, 0, 1));
            i += 1;
            minFloor = minFloor + reminderOfFloors;
        }
            while(i < amountOfZones){
                // arr[0] = starting floor of zone, arr[1] = ending floor of zone -> the range of floors.
                int tempArr[] = {minFloor, minFloor + sizeOfZone};
                _zones.add(new Zone(tempArr, i, i + 1));
                minFloor = minFloor + sizeOfZone;
                i++;
            }




    }

}
