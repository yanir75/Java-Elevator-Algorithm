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
        int[] tempArr = new int[2];
        for (int i = 0; i < amountOfZones; i++){
            tempArr[0] = maxFloor;
            tempArr[1] = maxFloor - (sizeOfZone*(i + 1));
            _zones.add(new Zone(tempArr, i, i));
        }

    }

}
