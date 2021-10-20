package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;

public class MyAlgorithm implements ElevatorAlgo{
    private Building _building;
    private int [] zones;
    //The elevator next stop
    private int [] nextStop;
    //current passengers on the elevator
    private int [] passengers;
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
        return 0;
    }

    @Override
    public void cmdElevator(int elev) {

    }

    /**
     * Returns if the calls is going up or down true for up false for down
     * @param c
     * @return
     */
    private boolean upORdown(CallForElevator c) {return c.getDest()>c.getSrc();}

    /**
     * Checks if the elevator is going up or down
     * @param el
     * @param elIndex
     * @return
     */
    private boolean elevatorUpOrDown(Elevator el,int elIndex) {return nextStop[elIndex]>el.getPos();}

    /**
     * Returns if a call is on the same way as the elevator both up or both down
     * @param c
     * @param el
     * @param index
     * @return
     */
    private boolean onTheSameWay(CallForElevator c,Elevator el,int index)
    {
        return (elevatorUpOrDown(el,index)&&upORdown(c)) || (!elevatorUpOrDown(el,index)&&!upORdown(c));
    }

    /**
     * Checks if the call is between the goto of the elevator
     * @param c
     * @param el
     * @param index
     * @return
     */
    private boolean betweenFloors(CallForElevator c ,Elevator el ,int index)
    {
        int cSource = c.getSrc();
        int pos=el.getPos();
        int dest=nextStop[index];
        return (cSource>=pos && cSource<=dest)||(cSource<=pos && cSource>=dest);
    }
    /**
     * Calculates if a call is on the way of an elevator route
     * @param c
     * @param el
     * @param elevatorIndex
     * @return
     */

}
