package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;

import java.util.ArrayList;

public class TestAlgo implements ElevatorAlgo{
    private Building b;
    private ArrayList<Integer>[] route;
    private ArrayList<Integer>[] src;
    private ArrayList<Integer>[] dest;
    private ArrayList<CallForElevator> calls;

    public TestAlgo(Building b)
    {
        this.b=b;
        route=new ArrayList[b.numberOfElevetors()];
        for(int i=0;i<b.numberOfElevetors();i++)
        {
            route[i]=new ArrayList<Integer>();
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
        for(int i=0;i<b.numberOfElevetors();i++)
        {
            if(min>numberOfFloors(i,c)) {
                min = numberOfFloors(i, c);
                ind=i;
            }
        }
        route[ind].add(c.getSrc());
        route[ind].add(c.getDest());
        return ind;
    }
    public double numberOfFloors(int i,CallForElevator c)
    {   double sum=0;
        if(route[i].size()==0)
        {
            return (Math.abs(c.getDest()-c.getSrc())+Math.abs(c.getSrc()-b.getElevetor(i).getPos()))/b.getElevetor(i).getSpeed();
        }
        sum+=Math.abs(route[i].get(0)-b.getElevetor(i).getPos());
        for(int j=1;j<route[i].size();j++)
        {
            sum+=Math.abs(route[i].get(j)-route[i].get(j-1));
        }
        sum+=Math.abs(c.getDest()-c.getSrc())+Math.abs(c.getSrc()-route[i].get(route[i].size()-1));
        sum=sum/b.getElevetor(i).getSpeed();
        return sum;
    }
    @Override
    public void cmdElevator(int elev) {
        if (Elevator.LEVEL == b.getElevetor(elev).getState()&&route[elev].size()>0)
        {
            if(b.getElevetor(elev).getPos()==route[elev].get(0))
                route[elev].remove(0);
            if(route[elev].size()>0)
            b.getElevetor(elev).goTo(route[elev].get(0));

        }
    }
}
