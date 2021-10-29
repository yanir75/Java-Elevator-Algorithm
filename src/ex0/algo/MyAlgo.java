package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;
import java.util.ArrayList;
import java.util.Collections;

public class MyAlgo implements ElevatorAlgo {
    private final Building building;
    public final ArrayList<Integer>[] route;
    private final ArrayList<CallForElevator>[] calls;
    private final ArrayList<Integer> up;
    private final ArrayList<Integer> down;
    private int numberOfElevators;
    private final int[] count;
    private boolean start = true;
    private boolean goUp = false;
    private boolean goDown = false;
    private int flag = 2;

    public MyAlgo(Building b) {
        this.building = b;
        this.numberOfElevators = b.numberOfElevetors();
        this.route = new ArrayList[this.numberOfElevators];
        this.calls = new ArrayList[this.numberOfElevators];
        this.up = new ArrayList<Integer>();
        this.down = new ArrayList<Integer>();
        this.count = new int[this.numberOfElevators];
        for (int i = 0; i < this.numberOfElevators; i++) {
            this.route[i] = new ArrayList<Integer>();
            this.calls[i] = new ArrayList<CallForElevator>();
        }
        if (this.numberOfElevators == 1) {this.flag = 0;}
        int numbersOfFloors = (b.maxFloor() - b.minFloor()) + 1;
        double maxSpeed = Double.MIN_VALUE;
        for (int i = 0; i < this.numberOfElevators; i++) {
            if (b.getElevetor(i).getSpeed() > maxSpeed) {maxSpeed = b.getElevetor(i).getSpeed();}
        }
        if (this.numberOfElevators > 7 && numbersOfFloors > 100 && maxSpeed < 10) {this.flag = 1;}
    }


    @Override
    public Building getBuilding() {return building;}


    @Override
    public String algoName() {return "TestAlgo";}


    @Override
    public int allocateAnElevator(CallForElevator c) {
        double min = Integer.MAX_VALUE;
        int ind = 0;
        // Phase 1 -> building with a single elevator.
        if (this.flag == 0) {return allocateAnElevator(c, true);}
        int src = c.getSrc();
        int dest = c.getDest();
        int type = src < dest ? 1 : -1;
        // Phase 2 -> building with a multiple slow elevators with a lot of floors.
        if (this.flag == 1) {
            if (Math.abs(dest - src) > 60) {
                if (type == 1) {
                    this.up.add(src);
                    this.up.add(dest);
                } else {
                    this.down.add(src);
                    this.down.add(dest);
                }
                return building.numberOfElevetors() - 1;
            }
            for (int i = 0; i < building.numberOfElevetors() - 1; i++) {
                if (min > numberOfFloors(i, c)) {
                    min = numberOfFloors(i, c);
                    ind = i;
                    if (min == -2) {
                        calls[ind].add(c);
                        return ind;
                    }
                }
            }
            route[ind].add(src);
            route[ind].add(dest);
            calls[ind].add(c);
            return ind;
        }
        // Phase 3 -> building with a multiple elevators and a lot of floors, some elevators can be faster than the others.
        if (this.flag == 2) {
            for (int i = 0; i < building.numberOfElevetors(); i++) {
                if (min > numberOfFloors(i, c)) {
                    min = numberOfFloors(i, c);
                    ind = i;
                    if (min == -2) {
                        calls[ind].add(c);
                        return ind;
                    }
                }
            }
            route[ind].add(src);
            route[ind].add(dest);
            calls[ind].add(c);
            return ind;
        }
        return 0;
    }

    /**
     * @param c
     * @param ind
     * @return This method returns True if a given call (source floor, destination floor)
     * is already located in the @ind elevator route, to save adding a floors which are
     * already in the route. Otherwise, returns False.
     */
    public boolean contains(CallForElevator c, int ind) {
        int src = -1;
        for (int i = 0; i < route[ind].size(); i++) {
            if (c.getSrc() == route[ind].get(i)) {
                src = i;
                break;
            }
        }
        if (src == -1)
            return false;
        for (int i = src; i < route[ind].size(); i++) {
            if (c.getDest() == route[ind].get(i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param c
     * @param b
     * @return This method returns an integer represents the index of the elevator that this call
     * was allocated to -> works only with Phase 1 -> Building with 1 elevator.
     */
    private int allocateAnElevator(CallForElevator c, boolean b) {
        int src = c.getSrc();
        int dest = c.getDest();
        if (this.route[0].size() == 0) {
            this.route[0].add(src);
            this.route[0].add(dest);
        }
        boolean f = true;
        for (int i = 0; i < this.route[0].size() - 1; i++) {
            if (this.route[0].get(i) < src && dest < this.route[0].get(i + 1) && dest > src) {
                f = false;
                this.route[0].add(i + 1, src);
                this.route[0].add(i + 2, dest);
            }
        }
        this.calls[0].add(c);
        return 0;
    }

    /**
     * @param i
     * @param c
     * @return This method is being used in allocateAnElevator method to help us calculate
     * the amount of estimated time that it will take to the @ith elevator to complete
     * its route after adding the new call. Returns the min estimated for this elevator.
     */
    public double numberOfFloors(int i, CallForElevator c) {
        if (contains(c, i)) {return -2;}
        int src = c.getSrc();
        int dest = c.getDest();
        double sum = 0;
        Elevator thisElev = this.building.getElevetor(i);
        double speed = thisElev.getSpeed();
        if (this.route[i].size() == 0) {
            return (Math.abs(dest - src) + Math.abs(src - thisElev.getPos())) / (speed);
        }
        sum += Math.abs(this.route[i].get(0) - thisElev.getPos());
        for (int j = 1; j < this.route[i].size(); j++) {
            sum += Math.abs(this.route[i].get(j) - this.route[i].get(j - 1));
        }
        sum += Math.abs(dest - src) + Math.abs(src - this.route[i].get(this.route[i].size() - 1));
        sum = (sum / speed) * 10;
        return sum;
    }

    @Override
    public void cmdElevator(int elev) {
        //This checks that the elevator is not moving and assign its next destination accordingly
        if (this.flag == 0 || this.flag == 2) {
            if (Elevator.LEVEL == building.getElevetor(elev).getState() && route[elev].size() > 0) {
                if (building.getElevetor(elev).getPos() == route[elev].get(0) && building.getElevetor(elev).getState() == Elevator.LEVEL)
                    route[elev].remove(0);
                if (route[elev].size() > 0)
                    building.getElevetor(elev).goTo(route[elev].get(0));
            }
        }
        if (this.flag == 1) {
            if (building.numberOfElevetors() != 1) {
                if (elev != building.numberOfElevetors() - 1) {
                    if (Elevator.LEVEL == building.getElevetor(elev).getState() && route[elev].size() > 0) {
                        if (building.getElevetor(elev).getPos() == route[elev].get(0) && building.getElevetor(elev).getState() == Elevator.LEVEL)
                            route[elev].remove(0);
                        if (route[elev].size() > 0)
                            building.getElevetor(elev).goTo(route[elev].get(0));
                    }
                    // this waits 100 seconds picks up all the calls which has a high amount of floors then goes and scatter them on their floor
                    // After it finished the above it does the same downwards.
                } else {
                    Elevator el = building.getElevetor(elev);
                    if (start) {
                        el.goTo(building.minFloor());
                        start = false;
                    }
                    if (el.getPos() == building.minFloor() && el.getState() == Elevator.LEVEL) {
                        this.count[elev]++;
                    }
                    if (this.count[elev] > 100 && el.getPos() == building.minFloor()) {
                        this.count[elev] = 0;
                        this.goUp = true;
                        Collections.sort(this.up);
                    }
                    if (this.goUp) {
                        if (this.up.size() > 0 && this.up.get(0) >= el.getPos()) {
                            if (el.getState() == Elevator.LEVEL) {
                                el.goTo(this.up.get(0));
                                this.up.remove(0);
                            }
                        } else {
                            if (el.getState() == Elevator.LEVEL) {
                                this.goUp = false;
                                el.goTo(building.maxFloor());
                            }
                        }
                    }
                    if (el.getPos() == building.maxFloor() && el.getState() == Elevator.LEVEL) {
                        this.count[elev]++;
                    }
                    if (this.count[elev] > 100 && el.getPos() == building.maxFloor()) {
                        this.count[elev] = 0;
                        this.goDown = true;
                        Collections.sort(this.down);
                        Collections.reverse(this.down);
                    }
                    if (this.goDown) {
                        if (this.down.size() > 0 && this.down.get(0) <= el.getPos()) {
                            if (el.getState() == Elevator.LEVEL) {
                                el.goTo(this.down.get(0));
                                this.down.remove(0);
                            }
                        } else {
                            if (el.getState() == Elevator.LEVEL) {
                                this.goDown = false;
                                el.goTo(building.minFloor());
                            }
                        }

                    }
                }
            } else {
                //This checks that the elevator is not moving and assign its next destination accordingly
                if (Elevator.LEVEL == building.getElevetor(elev).getState() && route[elev].size() > 0) {
                    if (building.getElevetor(elev).getPos() == route[elev].get(0) && building.getElevetor(elev).getState() == Elevator.LEVEL)
                        route[elev].remove(0);
                    if (route[elev].size() > 0)
                        building.getElevetor(elev).goTo(route[elev].get(0));
                }

            }
        }
    }
}

