package ex0.algo;

import ex0.CallForElevator;
import ex0.Elevator;

import java.util.ArrayList;

public class timeCalculator {
    private double time;
    private Elevator _el;
    private ArrayList<Integer> route;
    private ArrayList<CallForElevator> calls;

    public timeCalculator(Elevator el)
    {
        this._el=el;
        time=0;
        route=new ArrayList<Integer>();
        calls=new ArrayList<CallForElevator>();
    }

    public void calculateTime()
    {   int GTS=0;
        int GTD=0;
        for(int i=0;i<calls.size();i++)
        {
            if(calls.get(i).getState()==3)
                calls.remove(i);
            if(calls.get(i).getState()==2)
                GTD++;
            if(calls.get(i).getState()==1)
                GTS++;
        }
        double floorSpeed = _el.getSpeed();
        double floorTime = floorSpeed +_el.getStopTime()+_el.getStartTime()+_el.getTimeForOpen()+_el.getTimeForClose();
        if(route.size()>0)
        {
            time=time+time+Math.abs(route.get(0)-_el.getPos())*floorSpeed+floorTime;
        }
        for (int i=1;i<route.size();i++)
        {
            if(_el.getState()!=0)
            {
                time=time+(Math.abs(route.get(i)-route.get(i-1))*floorSpeed)*calls.size()+floorTime*GTD+2*floorTime*GTS;
            }
        }
        if(route.size()==0)
        {
            time=0;
        }
    }
    public double[] addedTimeToRoute(CallForElevator c)
    {   if(route.size()==0)
    {

    }
        double min=Double.MAX_VALUE;
        double beforeAdd=time;
        int source=c.getSrc();
        int dest=c.getDest();
        int indexSrc=-1;
        int indexDest=-1;
        for(int i=0;i<route.size();i++)
        {
            route.add(i,source);
            this.calculateTime();
            if(min>time)
            {
                min=time;
                indexSrc=i;
            }

            route.remove(i);
        }
        route.add(indexSrc,source);
        min=Double.MAX_VALUE;
        for(int i=0;i<route.size();i++)
        {
            route.add(i,dest);
            this.calculateTime();
            if(min>time)
            {
                min=time;
                indexDest=i;
            }

            route.remove(i);
        }
        route.remove(indexSrc);
        time=beforeAdd;
        double [] Dest={(double)indexSrc,(double)indexDest,min};
        return Dest;
    }

    public void addToRoute(int Src,int index1,int Dest,int index2)
    {
        route.add(index1,Src);
        route.add(index2,Dest);
        this.calculateTime();
    }
    public void deleteRoute()
    {
        try {
            int t=route.remove(0);
            while (route.get(0)==t)
            {route.remove(0);}
            this.calculateTime();
        }
        catch (Exception e)
        {}

    }
    public void addCall(CallForElevator c)
    {
        calls.add(c);
    }
}
