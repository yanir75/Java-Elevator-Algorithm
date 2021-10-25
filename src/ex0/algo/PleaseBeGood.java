package ex0.algo;

import ex0.CallForElevator;
import ex0.Elevator;

import java.util.ArrayList;

public class PleaseBeGood {
   private ArrayList<Integer> path;
   private Elevator elevator;
   private int elevIndex;
   private int currDest;
   private ArrayList<CallForElevator> calls;


    public PleaseBeGood(Elevator e, int elevIndex){
    this.elevator = e;
    this.elevIndex = elevIndex;
    this.path = new ArrayList<Integer>();
    this.currDest = Integer.MAX_VALUE;
    this.calls = new ArrayList<CallForElevator>();
    }

    public ArrayList<CallForElevator> getCalls() {
        return calls;
    }

    public void setCalls(ArrayList<CallForElevator> calls) {
        this.calls = calls;
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

    public void removeDuplicateFloors(){
        if(this.path.size() > 1) {
            for (int i = 1; i < this.path.size(); i++) {
                if(this.path.get(i-1) == this.path.get(i)){
                    this.path.remove(i);
                    i--;
                }
            }
        }
    }

    public int pathSize(){
        removeDuplicateFloors();
        return this.path.size();
    }







}
