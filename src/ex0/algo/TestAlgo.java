package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;

import java.util.ArrayList;

public class TestAlgo implements ElevatorAlgo {
    private Building building;
    private ArrayList<Integer>[] route;
    private ArrayList<CallForElevator>[] calls;
    private int FastElev;

    public TestAlgo(Building b) {
        this.building = b;
        route = new ArrayList[b.numberOfElevetors()];
        calls = new ArrayList[b.numberOfElevetors()];
        FastElev = 0;
        for (int i = 0; i < b.numberOfElevetors(); i++) {
            route[i] = new ArrayList<Integer>();
            calls[i] = new ArrayList<CallForElevator>();
            if (b.getElevetor(i).getSpeed() > b.getElevetor(FastElev).getSpeed()) {
                FastElev = i;
            }
        }
    }

    @Override
    public Building getBuilding() {return building;}

    @Override
    public String algoName() {return "TestAlgo";}

    @Override
    public int allocateAnElevator(CallForElevator c) {
        // Different approach for a building with a single elevator.
        if (building.numberOfElevetors() == 1){return allocateAnElevator(c, true);}
        double min = Integer.MAX_VALUE;
        int ind = 0;
        for (int i = 0; i < building.numberOfElevetors(); i++) {
            double numberOfFloorsInRoute = numberOfFloors(i, c);
            if (min > numberOfFloorsInRoute) {
                min = numberOfFloorsInRoute;
                ind = i;
                if (numberOfFloorsInRoute == -2) {
                    return ind;
                }
            }
        }
        route[ind].add(c.getSrc());
        route[ind].add(c.getDest());
        calls[ind].add(c);
        return ind;
    }

    // Different approach for a building with a single elevator.
    private int allocateAnElevator(CallForElevator c, boolean b) {
        int src = c.getSrc();
        int dest = c.getDest();
        if (route[0].size() == 0) {
            route[0].add(src);
            route[0].add(dest);
        }
        boolean flag = true;
        for (int i = 0; i < route[0].size() - 1; i++) {
            if (route[0].get(i) < src && dest < route[0].get(i + 1) && dest > src) {
                flag = false;
                route[0].add(i + 1, src);
                route[0].add(i + 2, dest);
            }
        }
        calls[0].add(c);
        return 0;
    }

    /**
     * @param c call
     * @param ind index of the elevator
     * @return True if this elevator has already the exact source
     * and destination in it's path, otherwise False.
     */
    public boolean containsA(CallForElevator c, int ind) {
        int src = -1;
        for (int i = 0; i < this.route[ind].size(); i++) {
            if (c.getSrc() == route[ind].get(i)) {
                src = i;
                break;
            }
        }
        if (src == -1)
            return false;
        for (int i = src; i < this.route[ind].size(); i++) {
            if (c.getDest() == route[ind].get(i)) {
                return true;
            }
        }
        return false;
    }


    public int dist(int a, int b){return Math.abs(a - b);}


    /**
     * @param ind
     * @param c
     * @return
     */
    public double numberOfFloors(int ind, CallForElevator c) {
        if (containsA(c, ind)){return -2;}
        int src = c.getSrc();
        int dest = c.getDest();
        double sum = 0;
        Elevator thisElev = this.building.getElevetor(ind);
        int thisElevSize = this.route[ind].size();
        int thisElevPos = this.building.getElevetor(ind).getPos();
        double floorTime = thisElev.getTimeForOpen() + thisElev.getTimeForClose();
        double speed = thisElev.getSpeed();
        if (thisElevSize == 0) {
            return (dist(dest, src) + dist(src, thisElevPos)) / (speed);
        }
        sum += dist(route[ind].get(0), thisElevPos);
        for (int j = 1; j < thisElevSize; j++) {
            sum += dist(route[ind].get(j), route[ind].get(j - 1));
        }
        sum += dist(dest, src) + dist(src, route[ind].get(thisElevSize - 1));
        // I don't know why the *10 is here but with it ,it works better.So it is here to stay
        sum = (sum / speed) * 10;//+ (thisElevSize * floorTime);
//        sum=sum/building.getElevator(ind).getSpeed()*10+thisElevSize*(building.getElevetor(ind).getTimeForOpen()+building.getElevetor(ind).getTimeForClose());
        return sum;
    }

    @Override
    public void cmdElevator(int elev) {
        if (Elevator.LEVEL == building.getElevetor(elev).getState() && route[elev].size() > 0) {
            if (building.getElevetor(elev).getPos() == route[elev].get(0) && building.getElevetor(elev).getState() == Elevator.LEVEL)
                route[elev].remove(0);
            if (route[elev].size() > 0)
                building.getElevetor(elev).goTo(route[elev].get(0));
        }
    }
}