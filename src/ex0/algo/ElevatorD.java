package ex0.algo;

import ex0.CallForElevator;
import ex0.Elevator;

import java.util.ArrayList;

public class ElevatorD {

    private Elevator el;
    private ArrayList<Integer> route;
    private ArrayList<CallForElevator> up;
    private ArrayList<CallForElevator> down;
    private int zone;

    public ElevatorD(Elevator e,int zone)
    {
        el=e;
        this.zone=zone;
        route=new ArrayList<Integer>();
        up=new ArrayList<CallForElevator>();
        down=new ArrayList<CallForElevator>();
    }

    public Elevator getEl() {
        return el;
    }

    public ArrayList<Integer> getRoute() {
        return route;
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    public void addToRoute(int i,int floor)
    {
        route.add(i,floor);
    }
}
