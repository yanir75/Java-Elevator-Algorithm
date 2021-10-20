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
        int sizeOfZone = (int)(Math.ceil(numberOfFloors / numberOfElevators));
        _zones = new ArrayList<Zone>(numberOfElevators);
        int amountOfZones = _zones.size();
//        ArrayList<ArrayList<Integer>> x = new ArrayList<ArrayList<Integer>>();
//        for (int i = 0; i < zoneLength; i++) {
//            ArrayList<Integer> y = new ArrayList<Integer>();
//            x.add(y);
//        }
        for (int i = 1; i <= amountOfZones; i++){
            int[] tempArr = {maxFloor, maxFloor - (sizeOfZone*i)};
//            -_zones[0] =
        }

    }

}
