package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;

import java.util.ArrayList;

public class TestAlgo implements ElevatorAlgo {
    private Building b;
    private ArrayList<Integer>[] route;
    private ArrayList<CallForElevator>[] calls;

    public TestAlgo(Building b) {
        this.b = b;
        route = new ArrayList[b.numberOfElevetors()];
        calls = new ArrayList[b.numberOfElevetors()];
        for (int i = 0; i < b.numberOfElevetors(); i++) {
            route[i] = new ArrayList<Integer>();
            calls[i] = new ArrayList<CallForElevator>();
        }
    }

    @Override
    public Building getBuilding() {
        return b;
    }

    @Override
    public String algoName() {
        return "TestAlgo";
    }

    @Override
    public int allocateAnElevator(CallForElevator c) {

        double min = Integer.MAX_VALUE;
        int ind = 0;
        if (b.numberOfElevetors() == 1)
            return allocateAnElevator(c, true);

        for (int i = 0; i < b.numberOfElevetors(); i++) {
            if (min > numberOfFloors(i, c)) {
                min = numberOfFloors(i, c);
                ind = i;
            }
        }
        route[ind].add(c.getSrc());
        route[ind].add(c.getDest());
        calls[ind].add(c);
        return ind;
    }

    private int allocateAnElevator(CallForElevator c, boolean b) {
        if (route[0].size() == 0) {
            route[0].add(c.getSrc());
            route[0].add(c.getDest());
        }
        boolean f = true;
        for (int i = 0; i < route[0].size() - 1; i++) {
            if (route[0].get(i) < c.getSrc() && c.getDest() < route[0].get(i + 1) && c.getDest() > c.getSrc()) {
                f = false;
                route[0].add(i + 1, c.getSrc());
                route[0].add(i + 2, c.getDest());
            }
        }
        calls[0].add(c);

        return 0;
    }

    public void removeDuplicateFloors(ArrayList<Integer> route){
        if(route.size() > 1) {
            for (int i = 1; i < route.size(); i++) {
                if(route.get(i-1) == route.get(i)){
                    route.remove(i);
                    i--;
                }
            }
        }
    }

    public double numberOfFloors(int i, CallForElevator c) {
        double sum = 0;
        if (route[i].size() == 0) {
            return (Math.abs(c.getDest() - c.getSrc()) + Math.abs(c.getSrc() - b.getElevetor(i).getPos())) / b.getElevetor(i).getSpeed();
        }
        sum += Math.abs(route[i].get(0) - b.getElevetor(i).getPos());
        for (int j = 1; j < route[i].size(); j++) {
            sum += Math.abs(route[i].get(j) - route[i].get(j - 1));
        }
        Elevator thisElev = b.getElevetor(i);
        double speed = thisElev.getSpeed();
        double floorTime = thisElev.getTimeForOpen() + thisElev.getTimeForClose();
        sum += Math.abs(c.getDest() - c.getSrc()) + Math.abs(c.getSrc() - route[i].get(route[i].size() - 1));
        // I don't know why the *10 is here but with it ,it works better.So it is here to stay
        sum = (sum / speed) *10 + (route[i].size() * floorTime);
//        sum=sum/b.getElevetor(i).getSpeed()*10+route[i].size()*(b.getElevetor(i).getTimeForOpen()+b.getElevetor(i).getTimeForClose());
        return sum;
    }
        @Override
        public void cmdElevator ( int elev){
//         removeDuplicateFloors(route[elev]);
                if (Elevator.LEVEL == b.getElevetor(elev).getState() && route[elev].size() > 0) {
                    if (b.getElevetor(elev).getPos() == route[elev].get(0))
                        route[elev].remove(0);
                    if (route[elev].size() > 0)
                        b.getElevetor(elev).goTo(route[elev].get(0));

                }



//           if(numOfFloorElev>0 && elev<numOfFloorElev)
//           {
//               if (Elevator.LEVEL == b.getElevetor(elev).getState() && route[elev].size()==0)
//               {
//                   b.getElevetor(elev).goTo(0);
//               }
//           }
//            if(numOfFloorElev>0 && elev<numOfFloorElev)
//            {
//                if (Elevator.LEVEL != b.getElevetor(elev).getState() && route[elev].size()>0)
//                {
//                    b.getElevetor(elev).stop(b.getElevetor(elev).getPos()-1);
//                    b.getElevetor(elev).stop(b.getElevetor(elev).getPos()+1);
//                    b.getElevetor(elev).goTo(route[elev].get(0));
//                }
//            }
        }
    }

