package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Algo implements ElevatorAlgo{
    private Zones z;
    private Building b;
    private ArrayList<ElevatorD> el;
    public Algo(Building build)
    {
        el=new ArrayList<ElevatorD>();
        b=build;
        z=new Zones(b);
        for(int i=0;i<b.numberOfElevetors();i++)
        {
            el.add(new ElevatorD(b.getElevetor(i),i));
            el.get(i).getEl().goTo(z.getZone(i).get_setOfFloors()[0]);
        }
        for(int i=0;i<b.numberOfElevetors();i++)
        {
            System.out.println(b.getElevetor(i).getPos());
        }
    }
    @Override
    public Building getBuilding() {
        return b;
    }

    @Override
    public String algoName() {
        return "algo";
    }

    @Override
    public int allocateAnElevator(CallForElevator c) {
        int des=c.getDest();
        int src=c.getSrc();
        for(int i=0;i<z.numberOfZones();i++)
        {// elevator goes up call is on the way
            Elevator e = b.getElevetor(i);
            int pos = e.getPos();
            int currDest=pos;
            if(e.getState()== Elevator.UP && c.getType()==CallForElevator.UP && src>pos)
            {
                if(el.get(i).getRoute().size()>0)
                {currDest=el.get(i).getRoute().get(i);}
                if(currDest>=des)
                    el.get(i).addToRoute(0,des);
                el.get(i).addToRoute(0,src);
                e.stop(src);
                return i;
            }
            if(e.getState()== Elevator.DOWN && c.getType()==CallForElevator.DOWN && src<pos)
            {
                if(el.get(i).getRoute().size()>0)
                {currDest=el.get(i).getRoute().get(0);}
                if(currDest<=des)
                    el.get(i).addToRoute(0,des);
                el.get(i).addToRoute(0,src);
                e.stop(src);
                return i;
            }
        }
        for(int i=0;i<b.numberOfElevetors();i++)
        {// elevator goes up call is on the way
            Elevator e = b.getElevetor(i);
            int pos = e.getPos();
            int currDest=pos;
            int elZone=el.get(i).getZone();
            if(z.getZone(elZone).isInZone(src));
            {
                // in case doesn't work add a condition for route size 0
                el.get(i).getRoute().add(src);
                el.get(i).getRoute().add(des);
                return i;
            }
        }

        return 0;
    }

    @Override
    public void cmdElevator(int elev) {
        ArrayList<Integer> r=el.get(elev).getRoute();
        Elevator e=b.getElevetor(elev);
        while (r.size()>1 && r.get(0)==r.get(1))
        {
            r.remove(0);
        }
        if(r.size()>0&&e.getPos()==r.get(0)&&e.getState()==Elevator.LEVEL)
        {
            r.remove(0);
        }
        if(r.size()>0&&e.getState()==Elevator.LEVEL)
        {
            e.goTo(r.get(0));
        }
        if(r.size()==0 && e.getState()==Elevator.LEVEL)
        {
            e.goTo(z.getZone(elev).get_setOfFloors()[0]);
        }
    }
}
