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
    private int[] currDest;


    public AlgoByZones(Building b) {
        this.building = b;
        this.numOfElevators = b.numberOfElevetors();
        this.zones = new Zones(this.building, this.numOfElevators);
        this.route_up = new ArrayList[this.numOfElevators];
        this.route_down = new ArrayList[this.numOfElevators];
        this.calls = new ArrayList[this.numOfElevators];
        this.goingToZone = new boolean[this.numOfElevators];
        this.elev = new Elevator[this.numOfElevators];
        this.currDest = new int[numOfElevators];
        for (int i = 0; i < this.numOfElevators; i++) {
            this.route_down[i] = new ArrayList<Integer>();
            this.route_up[i] = new ArrayList<Integer>();
            this.calls[i] = new ArrayList<CallForElevator>();
            this.elev[i] = b.getElevetor(i);
            this.currDest[i] = this.building.minFloor() - 1;
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


    public int whereToAdd(ArrayList<Integer> path, int floor, int minIndAllowed, int type) {
        if (path.size() - 1 == minIndAllowed) {
            return minIndAllowed + 1;
        }
        int minSub = Integer.MAX_VALUE;
        int bestInd = -1;
        for (int i = minIndAllowed + 1; i < path.size(); i++) {
            int sub = Math.abs(path.get(i) - floor);
            if (sub < minSub) {
                minSub = sub;
                bestInd = i;
            }
        }
        if (type == 1 && floor <= path.get(bestInd)) {
            return bestInd;
        } else if (type == 1 && floor > path.get(bestInd)) {
            return bestInd + 1;
        } else if (type == -1 && floor >= path.get(bestInd)) {
            return bestInd;
        } else if (type == -1 && floor < path.get(bestInd)) {
            return bestInd + 1;
        }
        return -1;
    }


    /** return array of 1/0 -> the index of the array represents the elevator number.
     *  1 -> for available , 0 -> for not.
     */
    public int[] countAvailableElevators() {
        int[] indexOfFreeElevators = new int[this.numOfElevators - 1];
        for (int i = 0; i < indexOfFreeElevators.length; i++) {
            if (this.route_up[i].size() == 0 && this.route_down[i].size() == 0 && this.building.getElevetor(i).getState() == Elevator.LEVEL) {
                indexOfFreeElevators[i] = 1;
            }
        }
        return indexOfFreeElevators;
    }


    public void changeZones(int[] indOfFreeElev){
        int count = 0;
        for(int i = 0 ; i < indOfFreeElev.length; i++){
            if(indOfFreeElev[i] == 1){
                count++;
            }
        }
        if(count == 0){
            return;
        }
        int maxFloor = this.building.maxFloor();
        int minFloor = this.building.minFloor();
        int numberOfFloors = maxFloor - minFloor + 1;
        int numOfFreeElev = 0;
        for(int i = 0; i < indOfFreeElev.length; i++){
            if(indOfFreeElev[i] == 1){
                numOfFreeElev += 1;
            }
        }

        if (numOfFreeElev == 1){
            int[] arr1 = {minFloor, maxFloor};
            this.zones.getZone(0).set_setOfFloors(this.zones.getZone(0), minFloor, maxFloor);
            return;
        }
        else {
            int reminderOfFloors = (numberOfFloors % numOfFreeElev);
            if (reminderOfFloors == 0) {
                int sizeOfZone = (numberOfFloors / numOfFreeElev);
                int start = minFloor;
                int end = minFloor + sizeOfZone;
                for (int i = 0; i < indOfFreeElev.length; i++) {
                    if (indOfFreeElev[i] == 1) {
                        this.zones.getZone(i).set_setOfFloors(this.zones.getZone(i), start, end);
                        start = end + 1;
                        end += sizeOfZone;
                    }
                }
            }
            else {
                int sizeOfZone = (int) (Math.ceil(numberOfFloors / numOfFreeElev)) + 1;
                reminderOfFloors = (numberOfFloors - ((numOfFreeElev - 1) * sizeOfZone));
                int sizeOfFirstZone = Math.min(sizeOfZone, reminderOfFloors);
                int sizeOfLastZone = Math.max(sizeOfZone, reminderOfFloors);
                boolean first = false;
                for (int i = 0; i < indOfFreeElev.length; i++) {
                    if (indOfFreeElev[i] == 1) {
                        if(!first){
                            this.zones.getZone(i).set_setOfFloors(this.zones.getZone(i), minFloor, minFloor + sizeOfFirstZone);
                            minFloor = minFloor + sizeOfFirstZone;
                            first = true;
                            continue;
                        }
                        else if(this.building.maxFloor() - sizeOfLastZone == minFloor){
                            this.zones.getZone(i).set_setOfFloors(this.zones.getZone(i), minFloor, minFloor + sizeOfLastZone);
                            break;
                        }
                        else{
                            this.zones.getZone(i).set_setOfFloors(this.zones.getZone(i), minFloor, minFloor + sizeOfZone);
                            minFloor = minFloor + sizeOfZone;
                        }
                    }
                }
            }
        }
    }

    public int dist(int a, int b){
        return Math.abs(a - b);
    }

    public double numberOfFloors(int i, CallForElevator c, ArrayList<Integer>[] route) {
        Elevator thisElev = this.building.getElevetor(i);
        double sum = 0;
        int src = c.getSrc();
        int dest = c.getDest();
        double speed = thisElev.getSpeed();
        double floorTime = thisElev.getTimeForOpen() + thisElev.getTimeForClose();
        if (route[i].size() == 0) {
            return ((dist(src, dest) + dist(src, thisElev.getPos())) / (thisElev.getSpeed()));
        }
        sum += dist(route[i].get(0), thisElev.getPos());
        for (int j = 1; j < route[i].size(); j++) {
            sum += dist(route[i].get(j), route[i].get(j - 1));
        }
        sum += dist(dest, src) + dist(src, route[i].get(route[i].size() - 1));
        // I don't know why the *10 is here but with it ,it works better.So it is here to stay
        sum = (sum / speed) *10 + (route[i].size() * floorTime);
//        sum=sum/b.getElevetor(i).getSpeed()*10+route[i].size()*(b.getElevetor(i).getTimeForOpen()+b.getElevetor(i).getTimeForClose());
        return sum;
    }


    @Override
    public int allocateAnElevator(CallForElevator c) {
        int src = c.getSrc();
        int dest = c.getDest();
        int state = c.getState();
        int type = src < dest ? 1 : -1;
        boolean changeZ = false;
        //Phase 1
//        for (int i = 0; i < this.numOfElevators; i++) {
//            //up
//            Elevator el = this.elev[i];
//            Zone z = this.zones.getZone(i);
//            if (type == el.getState() && el.getPos() < src && z.isInZone(dest)) {
//                this.route_up[i].add(src);
//                this.route_up[i].add(dest);
////                this.goingToZone[i] = true;
//                return i;
//            }
//            //Down
//            if (type == el.getState() && el.getPos() > src && z.isInZone(dest)) {
//                this.route_down[i].add(src);
//                this.route_down[i].add(dest);
////                this.goingToZone[i] = true;
//                return i;
//            }
//        }


        //Phase 2
        for (int i = 0; i < this.numOfElevators; i++) {
            if (this.goingToZone[i]) {
                //up
                Elevator el = this.elev[i];
                if(this.route_up[i].size() > 0) {
                    if (type == el.getState() && el.getPos() < src && dest <= this.route_up[i].get(0)) {
                        int s_ind = whereToAdd(this.route_up[i], src, 0, 1);
                        if (s_ind == this.route_up[i].size() - 1) {
                            this.route_up[i].add(src);
                            this.route_up[i].add(dest);
                        } else {
                            this.route_up[i].add(s_ind, src);
                            int d_ind = whereToAdd(this.route_up[i], dest, s_ind, 1);
                            this.route_up[i].add(d_ind, dest);
                        }
                        return i;
                    }
                }
                //Down
                if(this.route_down[i].size() > 0) {
                    if (type == el.getState() && el.getPos() > src && dest >= this.route_down[i].get(0)) {
                        int s_ind = whereToAdd(this.route_down[i], src, 0, -1);
                        if (s_ind == this.route_down[i].size() - 1) {
                            this.route_down[i].add(src);
                            this.route_down[i].add(dest);
                        } else {
                            this.route_down[i].add(s_ind, src);
                            int d_ind = whereToAdd(this.route_down[i], dest, s_ind, -1);
                            this.route_down[i].add(d_ind, dest);
                        }
                        return i;
                    }
                }
            }
        }


        //Phase 3
        for (int i = 0; i < this.numOfElevators; i++) {
            if (this.goingToZone[i]) {
                Elevator el = this.elev[i];
                Zone z = this.zones.getZone(i);
                if (0 == el.getState() && z.isInZone(src) && this.route_down[i].size() == 0 && this.route_up[i].size() == 0) {
                    //Up
                    if (type == 1) {
                        this.route_up[i].add(src);
                        this.route_up[i].add(dest);
                    }
                    //Down
                    else {
                        this.route_down[i].add(src);
                        this.route_down[i].add(dest);
                    }
                    return i;
                }
            }
        }


        //Phase 4
        Zone zoneNewCall = this.zones.getZone(this.zones.whichZone(src));
        for (int i = 0; i < this.numOfElevators; i++) {
            if (this.goingToZone[i]) {
                Elevator el = this.elev[i];
                Zone z = this.zones.getZone(i);
                //Up
                if(this.route_up[i].size() > 0 && type == 1) {
                    if (zoneNewCall.isInZone(this.route_up[i].get(0)) && type == 1) {
                        // if the route of this elevator to this zone contains only one floor
                        if (this.route_up[i].size() == 1) {
                            // if new src and new dest are before (smaller) this elev next dest add them before
                            if (this.route_up[i].get(0) > src && this.route_up[i].get(0) > dest) {
                                this.route_up[i].add(0, dest);
                                this.route_up[i].add(0, src);
                            }
                        /* if only the new src is smaller than this elev next dest then
                           add: src -> original dest -> new dest.
                         */
                            else if (this.route_up[i].get(0) > src) {
                                this.route_up[i].add(0, src);
                                this.route_up[i].add(dest);
                            }
                            // add it after you finish this current call.
                            else {
                                this.route_up[i].add(src);
                                this.route_up[i].add(dest);
                            }
                        }
                        // the route is bigger than 1 -> add the new call to the end of the route.
                        else {
                            this.route_up[i].add(src);
                            this.route_up[i].add(dest);
                        }
                        return i;
                    }
                }
                //Down
                if(this.route_down[i].size() > 0 && type == -1) {
                    if (zoneNewCall.isInZone(this.route_down[i].get(0)) && type == -1) {
                        // if the route of this elevator to this zone contains only one floor
                        if (this.route_down[i].size() == 1) {
                            // if new src and new dest are before (bigger) this elev next dest add them before
                            if (this.route_down[i].get(0) < src && this.route_down[i].get(0) < dest) {
                                this.route_down[i].add(0, dest);
                                this.route_down[i].add(0, src);
                            }
                        /* if only the new src is bigger than this elev next dest then
                           add: src -> original dest -> new dest.
                         */
                            else if (this.route_down[i].get(0) < src) {
                                this.route_down[i].add(0, src);
                                this.route_down[i].add(dest);
                            }
                            // add it after you finish this current call.
                            else {
                                this.route_down[i].add(src);
                                this.route_down[i].add(dest);
                            }
                        }
                        // the route is bigger the 1 -> add the new call to the end of the route.
                        else {
                            this.route_down[i].add(src);
                            this.route_down[i].add(dest);
                        }
                        return i;
                    }
                }
            }
        }


//        Phase 5
        int[] indOfFreeElev = countAvailableElevators();
        changeZones(indOfFreeElev);
        zoneNewCall = this.zones.getZone(this.zones.whichZone(src));
        for (int i = 0; i < this.numOfElevators; i++) {
            if (this.goingToZone[i]) {
                Elevator el = this.elev[i];
                Zone z = this.zones.getZone(i);
                //Up
                if(this.route_up[i].size() > 0 && type == 1) {
                    if (zoneNewCall.isInZone(this.route_up[i].get(0)) && type == 1) {
                        // if the route of this elevator to this zone contains only one floor
                        if (this.route_up[i].size() == 1) {
                            // if new src and new dest are before (smaller) this elev next dest add them before
                            if (this.route_up[i].get(0) > src && this.route_up[i].get(0) > dest) {
                                this.route_up[i].add(0, dest);
                                this.route_up[i].add(0, src);
                            }
                        /* if only the new src is smaller than this elev next dest then
                           add: src -> original dest -> new dest.
                         */
                            else if (this.route_up[i].get(0) > src) {
                                this.route_up[i].add(0, src);
                                this.route_up[i].add(dest);
                            }
                            // add it after you finish this current call.
                            else {
                                this.route_up[i].add(src);
                                this.route_up[i].add(dest);
                            }
                        }
                        // the route is bigger than 1 -> add the new call to the end of the route.
                        else {
                            this.route_up[i].add(src);
                            this.route_up[i].add(dest);
                        }
                        return i;
                    }
                }
                //Down
                if(this.route_down[i].size() > 0 && type == -1) {
                    if (zoneNewCall.isInZone(this.route_down[i].get(0)) && type == -1) {
                        // if the route of this elevator to this zone contains only one floor
                        if (this.route_down[i].size() == 1) {
                            // if new src and new dest are before (bigger) this elev next dest add them before
                            if (this.route_down[i].get(0) < src && this.route_down[i].get(0) < dest) {
                                this.route_down[i].add(0, dest);
                                this.route_down[i].add(0, src);
                            }
                        /* if only the new src is bigger than this elev next dest then
                           add: src -> original dest -> new dest.
                         */
                            else if (this.route_down[i].get(0) < src) {
                                this.route_down[i].add(0, src);
                                this.route_down[i].add(dest);
                            }
                            // add it after you finish this current call.
                            else {
                                this.route_down[i].add(src);
                                this.route_down[i].add(dest);
                            }
                        }
                        // the route is bigger the 1 -> add the new call to the end of the route.
                        else {
                            this.route_down[i].add(src);
                            this.route_down[i].add(dest);
                        }
                        return i;
                    }
                }
            }
        }


        //Phase 6
//        for (int i = 0; i < this.numOfElevators; i++) {
                //Up
                if (type == 1) {
                    double min = Integer.MAX_VALUE;
                    int ind = 0;
                    for (int j = 0; j < this.numOfElevators; j++) {
//                        if (this.goingToZone[j]) {
                            if (min > numberOfFloors(j, c, route_up)) {
                                min = numberOfFloors(j, c, route_up);
                                ind = j;
                            }
//                        }
                        route_up[ind].add(c.getSrc());
                        route_up[ind].add(c.getDest());
                        calls[ind].add(c);
                        return ind;
                    }
                }
                //Down
                else if (type == -1){
                    double min = Integer.MAX_VALUE;
                    int ind = 0;
                    for (int j = 0; j < this.numOfElevators; j++) {
//                        if (this.goingToZone[j]) {
                            if (min > numberOfFloors(j, c, route_down)) {
                                min = numberOfFloors(j, c, route_down);
                                ind = j;
                            }
//                        }
                    }
                    route_down[ind].add(c.getSrc());
                    route_down[ind].add(c.getDest());
                    calls[ind].add(c);
                    return ind;
                }

//        }
        return -1;
    }

    /**
     * 1. if the elevator is on the way to the zone then pick up the call, if it is on the way to the call it means that
     * it needs to be stopped at that floor and then go to the new direction.
     * <p>
     * 2. stop at the call and then goes to the next destination according to the sort.
     * <p>
     * 3.If it is in the zone and available great just take it.
     * <p>
     * 4.Go according to where Netanel adds the call, he adds in 0 go to it otherwise go to the continue according to
     * the same route
     * <p>
     * 5.Enlarging zones does not affect the cmd.
     * <p>
     * 6.The first to finish does not affect the cmd
     * <p>
     * Best idea is to keep the route in place 0 and if it is different go to the other place stop or goto , no reason
     * to change directions.
     *
     * @param elev the current Elevator index on which the operation is performs.
     */

    @Override
        public void cmdElevator(int elev) {
        int CurrDest=currDest[elev];
//        printDoneCalls(elev);
        Elevator el = building.getElevetor(elev);
        int pos = el.getPos();
        int state = el.getState();
        int sizeDown = route_down[elev].size();
        int sizeUp = route_up[elev].size();
        if(zones.getZone(elev).isInZone(el.getPos()))
        {
            goingToZone[elev] = true;
        }
        if(goingToZone[elev]==false && el.getState()==Elevator.LEVEL)
        {
            el.goTo(zones.middleOfZone(elev));
        }
         if(sizeUp == 0 && sizeDown > 0 && el.getState()==Elevator.LEVEL)
         {
             el.goTo(route_down[elev].get(0));
             currDest[elev]=route_down[elev].get(0);
             return;
         }
        if(sizeUp > 0 && sizeDown == 0 && el.getState()==Elevator.LEVEL)
        {
            el.goTo(route_up[elev].get(0));
            currDest[elev]=route_down[elev].get(0);
            return;
        }
    }













}
