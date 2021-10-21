package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;

import java.util.ArrayList;

public class myAlgo implements ElevatorAlgo {
    private Building b;
    private int[] numberOfReuqests;
    private int[] goingTo;
    private ArrayList<Integer>[] route;
    private Zones zon;

    public myAlgo(Building b) {
        this.b = b;
        numberOfReuqests = new int[b.numberOfElevetors()];
        route = new ArrayList[b.numberOfElevetors()];
        for (int i = 0; i < route.length; i++) {
            route[i] = new ArrayList<Integer>();
        }
        goingTo = new int[b.numberOfElevetors()];
        zon=new Zones(b);
        for(int i=0;i<b.numberOfElevetors();i++)
        {
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
//        int i = route[0].size();
//        int ind = 0;
//        for (int j = 0; j < zon.numberOfZones(); j++) {
//            if(i>numberOfReuqests[j])
//            if (route[j].size() < i) {
//                i = route[j].size();
//                ind = j;
//            }
//            if(onTheWay(c,j)){
//                ind =j;
//            }

//        }
//        numberOfReuqests[ind]++;
        int ind = zon.whichZone(c.getSrc());

        route[ind].add(c.getSrc());
        route[ind].add(c.getDest());
        return ind;
    }

    @Override
    public void cmdElevator(int elev) {
        Elevator curr = this.getBuilding().getElevetor(elev);
        int a = curr.getState();
        if (curr.getState() == Elevator.LEVEL) {
            if (route[elev].size() > 0) {
                curr.goTo(route[elev].get(0));
                goingTo[elev] = route[elev].remove(0);
            }
            if(route[elev].size()==0)
                curr.goTo(zon.middleOfZone(elev));
        }
    }

    public static boolean sameDirection(CallForElevator c, Elevator e) {
        if (e.getState()==Elevator.LEVEL) {
            return true;
        }
        int type =c.getDest()-c.getSrc();
        if(type>0&&e.getState()==Elevator.UP)
            return true;
        if(type<0&&e.getState()==Elevator.DOWN)
            return true;
        return false;
    }
    public static boolean isBetween(int i, int a,int b)
    {
        return (i>a && i<b) || (i<a && i>b);
    }
    public boolean onTheWay(CallForElevator c,int elev)
    {   Elevator e =this.getBuilding().getElevetor(elev);
        if (sameDirection(c,e)&&isBetween(c.getSrc(),e.getPos(),goingTo[elev]))
        return true;
        return false;
    }
}