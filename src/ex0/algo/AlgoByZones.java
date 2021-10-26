package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;
import java.util.ArrayList;

public class AlgoByZones implements ElevatorAlgo {
    private Zones zones;
    private ArrayList<Integer>[] route_up;
    private ArrayList<Integer>[] route_down;
    private ArrayList<CallForElevator>[] calls;
    private Building building;
    private int numOfElevators;
    private boolean[] goingToZone;
    private Elevator[] elev;


    public AlgoByZones(Building b) {
        this.building = b;
        this.numOfElevators = b.numberOfElevetors();
        this.zones = new Zones(this.building);
        this.route_up = new ArrayList[ this.numOfElevators];
        this.route_down = new ArrayList[ this.numOfElevators];
        this.calls = new ArrayList[this.numOfElevators];
        this.goingToZone = new boolean[this.numOfElevators];
        this.elev = new Elevator[this.numOfElevators];
        for(int i = 0 ; i <  this.numOfElevators; i++){
            this.route_down[i] = new ArrayList<Integer>();
            this.route_up[i] = new ArrayList<Integer>();
            this.calls[i] = new ArrayList<CallForElevator>();
            this.elev[i] = b.getElevetor(i);
        }
    }

    @Override
    public Building getBuilding() {
        return this.building;
    }

    @Override
    public String algoName() {
        return "AlgoByZones";
    }


    public int whereToAdd(ArrayList<Integer> path, int floor, int minIndAllowed, int type){
        if(path.size()-1 == minIndAllowed){return minIndAllowed+1;}
        int minSub = Integer.MAX_VALUE;
        int bestInd = -1;
        for(int i = minIndAllowed + 1; i < this.numOfElevators; i++){
            int sub = Math.abs(path.get(i) - floor);
            if(sub < minSub){
                minSub = sub;
                bestInd = i;
            }
        }
        if(type == 1 && floor <= path.get(bestInd)){return bestInd;}
        else if(type == 1 && floor > path.get(bestInd)){return bestInd +1;}
        else if (type == -1 && floor >= path.get(bestInd)){return bestInd;}
        else if (type == -1 && floor < path.get(bestInd)){return bestInd +1;}
        return -1;
    }


    @Override
    public int allocateAnElevator(CallForElevator c) {
        int src = c.getSrc();
        int dest = c.getDest();
        int state = c.getState();
        int type = c.getType();
        //Phase 1
        for(int i = 0 ; i < this.numOfElevators; i++){
            //up
            Elevator el = this.elev[i];
            Zone z = this.zones.getZone(i);
            if(type == el.getState() && el.getPos() < src && z.isInZone(dest)){
                this.route_up[i].add(src);
                this.route_up[i].add(dest);
                this.goingToZone[i] = true;
                return i;
            }
            //Down
            if(type == el.getState() && el.getPos() > src && z.isInZone(dest)){
                this.route_down[i].add(src);
                this.route_down[i].add(dest);
                this.goingToZone[i] = true;
                return i;
            }
        }


        //Phase 2
        for(int i = 0 ; i < this.numOfElevators; i++){
            if(this.goingToZone[i]) {
                //up
                Elevator el = this.elev[i];
                if (type == el.getState() && el.getPos() < src && dest <= this.route_up[i].get(0)) {
                    this.route_up[i].add(0, dest);
                    this.route_up[i].add(0, src);
                    return i;
                }
                //Down
                if (type == el.getState() && el.getPos() > src && dest >= this.route_down[i].get(0)) {
                    this.route_down[i].add(0, dest);
                    this.route_down[i].add(0, src);
                    return i;
                }
            }
        }


        //Phase 3



        //Phase 4



        //Phase 5



        //Phase 6



        //Phase 7

        return 0;
    }

    @Override
    public void cmdElevator(int elev) {

    }
}
