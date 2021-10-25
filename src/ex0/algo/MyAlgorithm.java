package ex0.algo;

import ex0.*;

import java.sql.Time;

public class MyAlgorithm implements ElevatorAlgo{
    private Building _building;
    private int [] zones;
    private TimeCalculator2[] TC;
    private int calls;

    public MyAlgorithm(Building building){
        this._building=building;
        TC=new TimeCalculator2[_building.numberOfElevetors()];
        for(int i=0;i<TC.length;i++)
        {
            TC[i]=new TimeCalculator2(_building.getElevetor(i));
        }
        calls=calls;
    }
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
        calls++;
        double min=Double.MAX_VALUE;
        double arr[] = new double[2];
        int index=-1;
    for(int i=0;i<TC.length;i++)
    {
        double [] a=TC[i].addedTimeToRoute(c);
        if(a[2]<min)
        {
            min=a[2];
            arr=a;
            index=i;
        }

    }
    TC[index].addToRoute(c,(int)arr[0],(int)arr[1]);
    return index;

    }

    @Override
    public void cmdElevator(int elev) {
        if(TC[elev].a)
        if(TC[elev].el.getState()==Elevator.LEVEL && TC[elev].route.size()>0)
        { if(TC[elev].route.get(0)==TC[elev].el.getPos())
            TC[elev].route.remove(0);
        }
         if(TC[elev].el.getState()==Elevator.LEVEL&& TC[elev].route.size()>0)
         {
             TC[elev].el.goTo(TC[elev].route.get(0));
             TC[elev].setCurrDest(TC[elev].route.get(0));
             TC[elev].route.remove(0);
         }
    }



}
