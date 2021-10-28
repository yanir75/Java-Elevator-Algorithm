package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.simulator.Simulator_A;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class TestAlgoTest {
    Building b1;
    Building b2;
    TestAlgo algo1;
    TestAlgo algo2;

    public TestAlgoTest(){
        Simulator_A.initData(1,null);
        b1 = Simulator_A.getBuilding();
        Simulator_A.initData(2,null);
        b2 = Simulator_A.getBuilding();
        algo1 = new TestAlgo(b1);
        algo2 =new TestAlgo(b2);
        CallForElevator c1 = new CallForElevator() {
            @Override
            public int getState() {
                return 0;
            }

            @Override
            public double getTime(int state) {
                return 0;
            }

            @Override
            public int getSrc() {
                return -1;
            }

            @Override
            public int getDest() {
                return 5;
            }

            @Override
            public int getType() {
                return 1;
            }

            @Override
            public int allocatedTo() {
                return 0;
            }
        };
        CallForElevator c2 = new CallForElevator() {
            @Override
            public int getState() {
                return 0;
            }

            @Override
            public double getTime(int state) {
                return 0;
            }

            @Override
            public int getSrc() {
                return 10;
            }

            @Override
            public int getDest() {
                return 9;
            }

            @Override
            public int getType() {
                return -1;
            }

            @Override
            public int allocatedTo() {
                return 0;
            }
        };
    }

    @Test
    void contains() {
    }

    @Test
    void allocateAnElevator() {




    }

    @Test
    void cmdElevator() {




    }

}