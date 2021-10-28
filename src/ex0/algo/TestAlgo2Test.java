package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.simulator.Simulator_A;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class TestAlgo2Test {
    Building b1;
    Building b2;
    TestAlgo2 algo1;
    TestAlgo2 algo2;

    public TestAlgo2Test(){
        Simulator_A.initData(1,null);
        b1 = Simulator_A.getBuilding();
        Simulator_A.initData(2,null);
        b2 = Simulator_A.getBuilding();
        algo1 = new TestAlgo2(b1);
        algo2 = new TestAlgo2(b2);
    }

    @Test
    void contains() {
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
        }; // -1 -> 5
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
        }; // 10 -> 9
        CallForElevator c3 = new CallForElevator() {
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
        }; // -1 -> 5
        int allocate = algo1.allocateAnElevator(c1);
        Assert.assertEquals(allocate, algo1.allocateAnElevator(c3));

    }

    @Test
    void allocateAnElevator() {




    }

    @Test
    void cmdElevator() {




    }

}