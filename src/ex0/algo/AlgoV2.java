package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;

public class AlgoV2 implements ElevatorAlgo {
    private Building b;
    private PleaseBeGood[] please;
    @Override
    public Building getBuilding() {
        return b;
    }

    @Override
    public String algoName() {
        return "AlgoV2";
    }

    @Override
    public int allocateAnElevator(CallForElevator c) {
        int minPathElevInd = -1;
        int minPath = Integer.MAX_VALUE;
        for(int i = 0; i < please.length ; i++){
            if(please[i].pathSize() < minPath){
                minPath = please[i].pathSize();
                minPathElevInd = i;
            }
        }
        please[minPathElevInd].getPath().add(c.getSrc());
        please[minPathElevInd].getPath().add(c.getDest());
        return minPathElevInd;
    }

    public boolean canStop(PleaseBeGood p,int pos)
    {
        Elevator e=p.getElevator();
        if(e.getState()==Elevator.UP)
            {
            if(pos>e.getPos() && pos<p.getCurrDest())
                return true;
            }
        if(e.getState()==Elevator.DOWN) {
            if (pos < e.getPos() && pos > p.getCurrDest())
                return true;
        }
        return false;

    }
    @Override
    public void cmdElevator(int elev) {
        Elevator el = b.getElevetor(elev);
        PleaseBeGood p = please[elev];
        int routeSize=p.pathSize();
        if (routeSize>0)
        { int nextDest=p.getPath().get(0);
            if(p.getCurrDest()!=nextDest)
            {
               if(canStop(p,nextDest))
                {
                    el.stop(nextDest);
                }
            }
        }
        else if(Elevator.LEVEL==el.getState() && )
    }

}
