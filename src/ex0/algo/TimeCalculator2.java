package ex0.algo;

import ex0.CallForElevator;
import ex0.Elevator;

import java.util.ArrayList;

public class TimeCalculator2 {

    private ArrayList<CallForElevator> calls;
    public ArrayList<Integer> route;
    public Elevator el;
    private int currDest;
    public boolean a;
    public boolean stop;
    public int floor;
    public boolean goToSrc;

    public TimeCalculator2(Elevator eli)
    {
        a=false;
        el=eli;
        calls= new ArrayList<CallForElevator>();
        route=new ArrayList<Integer>();
        currDest=el.getMinFloor()-1;
        stop=false;
        goToSrc=false;
    }
    private double dist(int src,int pos) {
        double ans = -1;
        Elevator thisElev = el;
        double speed = thisElev.getSpeed();
        int min = el.getMinFloor(), max = el.getMaxFloor();
        double floorTime = speed+thisElev.getStopTime()+thisElev.getStartTime()+thisElev.getTimeForOpen()+thisElev.getTimeForClose();
        ans=floorTime;
        return ans;
    }
    private int inElev() { int inEl=0;
        for(int i=0;i<calls.size();i++)
        {
            if(calls.get(i).getState()==2);
            inEl++;
        }
        return inEl;
    }
    private double sourceToDest(int src,int dest){return this.inElev()*this.dist(dest,src);}
    private double calculateRouteTime() {   double t=0;
        if(route.size()==0)
            return 0;
        t+=sourceToDest(el.getPos(),route.get(0));
        for(int i=1;i<route.size();i++)
        {
            t+=sourceToDest(route.get(i-1),route.get(i));
        }
        return t;
    }
    public double[] addedTimeToRoute(CallForElevator c) {
        if(route.size()==0)
        {
            double timeToAdd=sourceToDest(el.getPos(),c.getSrc());
            timeToAdd+=sourceToDest(c.getSrc(),c.getDest());
            return new double[]{0, 1, timeToAdd};
        }
        double timeBeforeAdd=calculateRouteTime();
        double minSource = Double.MAX_VALUE;
        int indOfSource=-1;
        double time=0;
        for(int i=0;i<route.size();i++)
        {
            route.add(i,c.getSrc());
            double timeAfterAdd=calculateRouteTime();
            time=timeAfterAdd-timeBeforeAdd;
            if(minSource >time)
            {
                minSource =time;
              indOfSource=i;
            }
            route.remove(i);
        }
        double minDest = Double.MAX_VALUE;
        route.add(indOfSource,c.getSrc());
        double timeBeforeDest=calculateRouteTime();
        int indOfDest = -1;
        if(indOfSource+1==route.size())
        {
            route.add(c.getDest());
            double t=calculateRouteTime();
            route.remove(indOfSource);
            route.remove(route.size()-1);
            t=t-timeBeforeAdd;
            return new double[]{indOfSource,route.size()-1,t};
        }
        double totalTime=0;
        for(int i=indOfSource+1;i<route.size();i++)
        {
            route.add(i,c.getDest());
            double timeAfterDest=calculateRouteTime();
            totalTime=timeAfterDest-timeBeforeDest;
                if(minDest >totalTime)
            {
                minDest =totalTime;
                indOfDest=i;
            }
            route.remove(i);
        }
        route.remove(indOfSource);
        return new double[]{indOfSource,indOfDest,totalTime+time};
    }
    public void addToRoute(CallForElevator c,int i,int j) {
        this.cleanDoneCalls();
        boolean f=false;
        if((el.getState()==Elevator.LEVEL && i==0) ||route.size()==0 && el.getState()==Elevator.LEVEL)
        {
            el.goTo(c.getSrc());
            currDest=c.getSrc();
        }
        else if(((el.getPos()>=c.getSrc() && c.getSrc()>=currDest )|| (c.getSrc()>= el.getPos() && c.getSrc()<=currDest))&& i==0 && el.getState()!=Elevator.LEVEL)
        {   route.add(0,currDest);
            stop=true;
            floor=c.getSrc();
            currDest=c.getSrc();
            a=true;
        }
        else if(i==0 && el.getState()!=Elevator.LEVEL)
        {   route.add(0,currDest);
            stop=true;
            floor=el.getPos();
            currDest=el.getPos();
            route.add(0, c.getSrc());
            currDest=c.getSrc();
            a=true;
        }
        else{
        route.add(i,c.getSrc());
        f=true;
        }
        if(f){
        route.add(j,c.getDest());}
        else{
            route.add(--j,c.getDest());
        }
        calls.add(c);
    }




    public void cleanDoneCalls(){
        for(int i=0;i<calls.size();i++)
            if(calls.get(i).getState()==CallForElevator.DONE)
                calls.remove(i);
    }
    public void setCurrDest(int i)
    {
        currDest=i;
    }
    public void addToCalls(CallForElevator c){calls.add(c);}
}
