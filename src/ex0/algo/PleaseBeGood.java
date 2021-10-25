package ex0.algo;

import ex0.Elevator;

import java.util.ArrayList;

public class PleaseBeGood {
   private ArrayList<Integer> path;
   private Elevator elevator;
   private int elevIndex;
   private int currDest;

    public PleaseBeGood(){

    }

    public ArrayList<Integer> getPath() {
        return path;
    }

    public Elevator getElevator() {
        return elevator;
    }

    public void setElevator(Elevator elevator) {
        this.elevator = elevator;
    }

    public int getElevIndex() {
        return elevIndex;
    }

    public void setElevIndex(int elevIndex) {
        this.elevIndex = elevIndex;
    }

    public int getCurrDest() {
        return currDest;
    }

    public void setCurrDest(int currDest) {
        this.currDest = currDest;
    }

    public void setPath(ArrayList<Integer> path) {
        this.path = path;
    }
}
