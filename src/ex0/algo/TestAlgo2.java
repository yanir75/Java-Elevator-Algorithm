package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;
import java.util.ArrayList;
import java.util.Collections;

public class TestAlgo2 implements ElevatorAlgo {
    private final Building building;
    private final ArrayList<Integer>[] route;
    private final ArrayList<CallForElevator>[] calls;
    private final ArrayList<Integer> up;
    private final ArrayList<Integer> down;
    private final int[] count;
    private boolean start = true;
    private boolean goUp = false;
    private boolean goDown = false;
    private int flag = 2;

    public TestAlgo2(Building b) {
        this.building = b;
        route = new ArrayList[b.numberOfElevetors()];
        calls = new ArrayList[b.numberOfElevetors()];
        up = new ArrayList<Integer>();
        down = new ArrayList<Integer>();
        count = new int[b.numberOfElevetors()];
        for (int i = 0; i < b.numberOfElevetors(); i++) {
            route[i] = new ArrayList<Integer>();
            calls[i] = new ArrayList<CallForElevator>();
        }
        if (b.numberOfElevetors() == 1) {
            flag = 0;
        }
        int numbersOfFloors = b.maxFloor() - b.minFloor() + 1;
        double maxSpeed = Double.MIN_VALUE;
        for (int i = 0; i < b.numberOfElevetors(); i++) {
            if (b.getElevetor(i).getSpeed() > maxSpeed) {
                maxSpeed = b.getElevetor(i).getSpeed();
            }
        }
        if (b.numberOfElevetors() > 7 && numbersOfFloors > 100 && maxSpeed < 10) {
            flag = 1;
        }

    }

    @Override
    public Building getBuilding() {
        return building;
    }

    @Override
    public String algoName() {
        return "TestAlgo";
    }

    @Override
    public int allocateAnElevator(CallForElevator c) {
        // seperate the cases which has 1 elevator.
        double min = Integer.MAX_VALUE;
        int ind = 0;
        if (flag == 0) {
            return allocateAnElevator(c, true);
        }

        int type = c.getSrc() < c.getDest() ? 1 : -1;
        if (flag == 1) {
            if (Math.abs(c.getDest() - c.getSrc()) > 60) {
                if (type == 1) {
                    up.add(c.getSrc());
                    up.add(c.getDest());
                } else {
                    down.add(c.getSrc());
                    down.add(c.getDest());
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
            route[ind].add(c.getSrc());
            route[ind].add(c.getDest());
            calls[ind].add(c);
            return ind;
        }
        if (flag == 2) {
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
            route[ind].add(c.getSrc());
            route[ind].add(c.getDest());
            calls[ind].add(c);
            return ind;
        }
        return 0;
    }

    public boolean contains(CallForElevator c, int ind) {
        int src = -1;
        for (int i = 0; i < route[ind].size(); i++) {
            if (c.getSrc() == route[ind].get(i)) {
                src = i;
                break;
            }
        }
        //5
        if (src == -1)
            return false;
        for (int i = src; i < route[ind].size(); i++) {
            if (c.getDest() == route[ind].get(i)) {
                return true;
            }
        }
        return false;
    }

    private int allocateAnElevator(CallForElevator c, boolean b) {
        if (route[0].size() == 0) {
            route[0].add(c.getSrc());
            route[0].add(c.getDest());
        }
        boolean f = true;
        for (int i = 0; i < route[0].size() - 1; i++) {
            if (route[0].get(i) < c.getSrc() && c.getDest() < route[0].get(i + 1) && c.getDest() > c.getSrc()) {
                f = false;
                route[0].add(i + 1, c.getSrc());
                route[0].add(i + 2, c.getDest());
            }
        }
        calls[0].add(c);
        return 0;
    }

    public double numberOfFloors(int i, CallForElevator c) {
        if (contains(c, i)) {
            return -2;
        }
//        if(containsB(c, i)){ return -1;}
        double sum = 0;
        Elevator thisElev = building.getElevetor(i);
        double floorTime = thisElev.getTimeForOpen() + thisElev.getTimeForClose();
        if (route[i].size() == 0) {
            return (Math.abs(c.getDest() - c.getSrc()) + Math.abs(c.getSrc() - building.getElevetor(i).getPos())) / (building.getElevetor(i).getSpeed());
        }
        sum += Math.abs(route[i].get(0) - building.getElevetor(i).getPos());
        for (int j = 1; j < route[i].size(); j++) {
            sum += Math.abs(route[i].get(j) - route[i].get(j - 1));
        }
        double speed = thisElev.getSpeed();
        sum += Math.abs(c.getDest() - c.getSrc()) + Math.abs(c.getSrc() - route[i].get(route[i].size() - 1));
        // I don't know why the *10 is here but with it ,it works better.So it is here to stay
        sum = (sum / speed) * 10;// + (route[i].size() * floorTime);
//        sum=sum/b.getElevetor(i).getSpeed()*10+route[i].size()*(b.getElevetor(i).getTimeForOpen()+b.getElevetor(i).getTimeForClose());
        return sum;
    }

    @Override
    public void cmdElevator(int elev) {
        if (flag == 0 || flag == 2) {
            if (Elevator.LEVEL == building.getElevetor(elev).getState() && route[elev].size() > 0) {
                if (building.getElevetor(elev).getPos() == route[elev].get(0) && building.getElevetor(elev).getState() == Elevator.LEVEL)
                    route[elev].remove(0);
                if (route[elev].size() > 0)
                    building.getElevetor(elev).goTo(route[elev].get(0));
            }
        }
        if (flag == 1) {
            if (building.numberOfElevetors() != 1) {
                if (elev != building.numberOfElevetors() - 1) {
                    if (Elevator.LEVEL == building.getElevetor(elev).getState() && route[elev].size() > 0) {
                        if (building.getElevetor(elev).getPos() == route[elev].get(0) && building.getElevetor(elev).getState() == Elevator.LEVEL)
                            route[elev].remove(0);
                        if (route[elev].size() > 0)
                            building.getElevetor(elev).goTo(route[elev].get(0));
                    }
                } else {
                    Elevator el = building.getElevetor(elev);
                    if (start) {
                        el.goTo(building.minFloor());
                        start = false;
                    }
                    if (el.getPos() == building.minFloor() && el.getState() == Elevator.LEVEL) {
                        count[elev]++;
                    }
                    if (count[elev] > 100 && el.getPos() == building.minFloor()) {
                        count[elev] = 0;
                        goUp = true;
                        Collections.sort(up);
                    }
                    if (goUp) {
                        if (up.size() > 0 && up.get(0) >= el.getPos()) {
                            if (el.getState() == Elevator.LEVEL) {
                                el.goTo(up.get(0));
                                up.remove(0);
                            }
                        } else {
                            if (el.getState() == Elevator.LEVEL) {
                                goUp = false;
                                el.goTo(building.maxFloor());
                            }
                        }
                    }
                    if (el.getPos() == building.maxFloor() && el.getState() == Elevator.LEVEL) {
                        count[elev]++;
                    }
                    if (count[elev] > 100 && el.getPos() == building.maxFloor()) {
                        count[elev] = 0;
                        goDown = true;
                        Collections.sort(down);
                        Collections.reverse(down);
                    }
                    if (goDown) {
                        if (down.size() > 0 && down.get(0) <= el.getPos()) {
                            if (el.getState() == Elevator.LEVEL) {
                                el.goTo(down.get(0));
                                down.remove(0);
                            }
                        } else {
                            if (el.getState() == Elevator.LEVEL) {
                                goDown = false;
                                el.goTo(building.minFloor());
                            }
                        }

                    }
                }
            } else {
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

