package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;

public class MyAlgorithm implements ElevatorAlgo{
    private Building _building;
    private int [] zones;
    @Override
    public Building getBuilding() {
        return _building;
    }

    @Override
    public String algoName() {
        return "algo";
    }

    @Override
    public int allocateAnElevator(CallForElevator c) {
        return 0;
    }

    @Override
    public void cmdElevator(int elev) {

    }
}
