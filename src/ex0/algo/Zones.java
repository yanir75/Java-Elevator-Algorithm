package ex0.algo;

import ex0.Building;

import java.util.ArrayList;
public class Zones {
    private ArrayList<Zone> _zones;

   public Zone getZone(int i)
   {
       return _zones.get(i);
   }

    public Zones(Building b) {
        int maxFloor = b.maxFloor();
        int minFloor = b.minFloor();
        int rangeOfPopularFloors = minFloor <= 0 ? 0 - minFloor + 1: minFloor;
        int numberOfElevators = b.numberOfElevetors();
        int numberOfFloors = maxFloor - minFloor + 1;
        _zones = new ArrayList<Zone>(numberOfElevators);
        if (numberOfElevators == 1){
            int[] arr1 = {minFloor, maxFloor};
            _zones.add(new Zone(arr1, 0, 0));
        }
        else {
        /* Each Zone in the Building is in the size of num of floors / num of elevators,
           Except the last zone which will have the reminder.
         */
            int sizeOfZone = (int) (Math.ceil(numberOfFloors / numberOfElevators)) + 1;
            int amountOfZones = numberOfElevators; // Amount of zones is equal to the amount of elevators.
            int i = 0;
            int reminderOfFloors = (numberOfFloors - ((numberOfElevators - 1) * sizeOfZone));
            if(reminderOfFloors < sizeOfZone) {
                int[] arr2 = {minFloor, minFloor + reminderOfFloors - 1};
                _zones.add(new Zone(arr2, 0, 0));
                minFloor = minFloor + reminderOfFloors;
                i += 1;
            }
            else{
                int[] arr2 = {minFloor, minFloor + sizeOfZone -1};
                _zones.add(new Zone(arr2, 0, 0));
                minFloor = minFloor + sizeOfZone;
                int[] arr3 = {minFloor, minFloor + reminderOfFloors - 1};
                _zones.add(new Zone(arr3, 1, 1));
                minFloor = minFloor + reminderOfFloors;
                i += 2;
            }
        /* First implementation simple zone dividing.
           If the number of floors % number of elevators isn't zero then: The first zone (number 1)
           will get the reminder of the divide above as his set of floors.
           Then/else: each zone will get equally amount of floors.
           Starting from Min floor to Max floor.
         */

            while (i < amountOfZones) {
                // arr[0] = starting floor of zone, arr[1] = ending floor of zone -> the range of floors.
                int tempArr[] = {minFloor, minFloor + sizeOfZone - 1};
                _zones.add(new Zone(tempArr, i, i));
                minFloor = minFloor + sizeOfZone;
                i++;
            }
        }
    }

     public int numberOfZones() {
         return _zones.size();
     }

     public int whichZone(int floor){
        for( int i = 0; i<this._zones.size(); i++){
            int[] setOfFloors = this._zones.get(i).get_setOfFloors();
            int startingFloor = setOfFloors[0];
            int endingFloor = setOfFloors[1];
            if(floor >= startingFloor && floor <= endingFloor){
                return i;
            }
        }
        return -1;
     }

     public int middleOfZone(int zoneNum){
         int[] setOfFloors = this._zones.get(zoneNum).get_setOfFloors();
         int startingFloor = setOfFloors[0];
         int endingFloor = setOfFloors[1];
        return (startingFloor + endingFloor) / 2;
    }




    public String toString(){
        String str = "[";
        for (Zone z: this._zones) {
          str+= z.toString()  + ", " ;
        }
        return str.substring(0,str.length()-2) + "]";
    }
}
