package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;

import java.util.ArrayList;

public class myAlgo implements ElevatorAlgo {
    private Building b;
    private ArrayList<CallForElevator>[] numberOfReuqests;
    private int[] goingTo;
    private ArrayList<Integer>[] route;
    private Zones zon;

    public myAlgo(Building b) {
        this.b = b;
        numberOfReuqests = new ArrayList[b.numberOfElevetors()];
        route = new ArrayList[b.numberOfElevetors()];
        for (int i = 0; i < route.length; i++) {
            route[i] = new ArrayList<Integer>();
            numberOfReuqests[i] = new ArrayList<CallForElevator>();
        }
        goingTo = new int[b.numberOfElevetors()];
        zon = new Zones(b);
        for (int i = 0; i < b.numberOfElevetors(); i++) {
            b.getElevetor(i).goTo(zon.middleOfZone(i));
        }
        System.out.println(zon.toString());
    }

    @Override
    public Building getBuilding() {
        return b;
    }

    @Override
    public String algoName() {
        return "myAlgo";
    }

    @Override
    public int allocateAnElevator(CallForElevator c) {
        int i = route[0].size();
        int ind = 0;
        for (int j = 0; j < zon.numberOfZones(); j++) {
//            if(i>numberOfReuqests[j])
//            if (route[j].size() < i) {
//                i = route[j].size();
//                ind = j;
//            }
            if(isOnTheWay(c,j)){
                ind =j;
            }

        }
        numberOfReuqests[ind].add(c);
        if(isOnTheWay(c,ind))
        {
            b.getElevetor(ind).stop(c.getSrc());
            route[ind].add(0,goingTo[ind]);
            goingTo[ind]=c.getSrc();
            route[ind].add(c.getDest());
        }
        else {
            route[ind].add(c.getSrc());
            route[ind].add(c.getDest());
        }
        return ind;
    }

    @Override
    public void cmdElevator(int elev) {
        clearDoneCalls(elev);
        Elevator curr = this.getBuilding().getElevetor(elev);
        int a = curr.getState();
        if (curr.getState() == Elevator.LEVEL) {
            if (route[elev].size() > 0) {
                curr.goTo(route[elev].get(0));
                goingTo[elev] = route[elev].remove(0);
            }
        }
    }

    public void clearDoneCalls(int elev)
    {
        for (int i = 0; i < numberOfReuqests[elev].size(); i++) {
          if(numberOfReuqests[elev].get(i).getState()==CallForElevator.DONE)
          {
              numberOfReuqests[elev].remove(i);
          }
        }
    }
    /**
     * For netanel
     * @param c
     */
    public boolean isOnTheWay(CallForElevator c,int elev) {
        if(b.getElevetor(elev).getState() == 0){ return true;}
        int currentPosition = b.getElevetor(elev).getPos();
        int currentState = b.getElevetor(elev).getState();
        int nextStop = route[elev].get(0);
        // going up
        if (currentState == 1 && currentPosition < c.getSrc() && nextStop >= c.getSrc()){
            return true;
        }
        // going down
        else if(currentState == -1 && currentPosition > c.getSrc() && nextStop <= c.getSrc()){
            return true;
        }
      return false;
    }
}