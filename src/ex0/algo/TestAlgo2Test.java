package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.simulator.Simulator_A;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class TestAlgo2Test {
    Building b1;
    Building b2;
    Building b3;
    //    Building b4;
//    Building b5;
//    Building b6;
//    Building b7;
//    Building b8;
//    Building b9;
    TestAlgo2 algo1;
    TestAlgo2 algo2;
    TestAlgo2 algo3;
//    TestAlgo2 algo4;
//    TestAlgo2 algo5;
//    TestAlgo2 algo6;
//    TestAlgo2 algo7;
//    TestAlgo2 algo8;
//    TestAlgo2 algo9;



    public TestAlgo2Test(){
        Simulator_A.initData(1,null);
        b1 = Simulator_A.getBuilding();
        Simulator_A.initData(2,null);
        b2 = Simulator_A.getBuilding();
        Simulator_A.initData(3,null);
        b3 = Simulator_A.getBuilding();
//        Simulator_A.initData(4,null);
//        b4 = Simulator_A.getBuilding();
//        Simulator_A.initData(5,null);
//        b5 = Simulator_A.getBuilding();
//        Simulator_A.initData(6,null);
//        b6 = Simulator_A.getBuilding();
//        Simulator_A.initData(7,null);
//        b7 = Simulator_A.getBuilding();
//        Simulator_A.initData(8,null);
//        b8 = Simulator_A.getBuilding();
        algo1 = new TestAlgo2(b1);
        algo2 = new TestAlgo2(b2);
        algo3 = new TestAlgo2(b3);
//        algo4 = new TestAlgo2(b4);
//        algo5 = new TestAlgo2(b5);
//        algo6 = new TestAlgo2(b6);
//        algo7 = new TestAlgo2(b7);
//        algo8 = new TestAlgo2(b8);
//        algo9 = new TestAlgo2(b9);
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
                return 2;
            }

            @Override
            public int getDest() {
                return 3;
            }

            @Override
            public int getType() {
                return -1;
            }

            @Override
            public int allocatedTo() {
                return 0;
            }
        }; // 2 -> 3
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
        CallForElevator c4 = new CallForElevator() {
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
                return 0;
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
        }; // 0 -> 5
        CallForElevator c5 = new CallForElevator() {
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
                return -3;
            }

            @Override
            public int getDest() {
                return 20;
            }

            @Override
            public int getType() {
                return 1;
            }

            @Override
            public int allocatedTo() {
                return 0;
            }
        }; // -3 -> 20
        CallForElevator c6 = new CallForElevator() {
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
                return -3;
            }

            @Override
            public int getType() {
                return -1;
            }

            @Override
            public int allocatedTo() {
                return 0;
            }
        }; // 10 -> -3
        CallForElevator c7 = new CallForElevator() {
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
                return 5;
            }

            @Override
            public int getDest() {
                return 6;
            }

            @Override
            public int getType() {
                return 1;
            }

            @Override
            public int allocatedTo() {
                return 0;
            }
        }; // 5 -> 6
        CallForElevator c8 = new CallForElevator() {
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
                return 8;
            }

            @Override
            public int getDest() {
                return 0;
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
        CallForElevator c9 = new CallForElevator() {
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
                return 5;
            }

            @Override
            public int getDest() {
                return 6;
            }

            @Override
            public int getType() {
                return 1;
            }

            @Override
            public int allocatedTo() {
                return 0;
            }
        }; // 5 -> 6
        int allocate1 = algo1.allocateAnElevator(c1);
        algo1.allocateAnElevator(c2);
        assertEquals(allocate1, algo1.allocateAnElevator(c3));
        int allocate2 = algo1.allocateAnElevator(c7);
        assertEquals(allocate2, algo1.allocateAnElevator(c9));
        algo3.allocateAnElevator(c4);
    }


    @Test
    void numberOfFloors() {

    }

    @Test
    void allocateAnElevator() {
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
                return 2;
            }

            @Override
            public int getDest() {
                return 3;
            }

            @Override
            public int getType() {
                return -1;
            }

            @Override
            public int allocatedTo() {
                return 0;
            }
        }; // 2 -> 3
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
        algo1.allocateAnElevator(c1);
        algo1.allocateAnElevator(c2);
        assertEquals(0, algo1.allocateAnElevator(c3));
        CallForElevator c4 = new CallForElevator() {
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
                return 0;
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
        }; // 0 -> 5
        CallForElevator c5 = new CallForElevator() {
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
                return -3;
            }

            @Override
            public int getDest() {
                return 20;
            }

            @Override
            public int getType() {
                return 1;
            }

            @Override
            public int allocatedTo() {
                return 0;
            }
        }; // -3 -> 20
        CallForElevator c6 = new CallForElevator() {
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
                return -3;
            }

            @Override
            public int getType() {
                return -1;
            }

            @Override
            public int allocatedTo() {
                return 0;
            }
        }; // 10 -> -3
        assertEquals(0, algo3.allocateAnElevator(c4));
        assertEquals(1, algo3.allocateAnElevator(c5));
        assertEquals(2, algo3.allocateAnElevator(c6));



    }

    @Test
    void cmdElevator() {




    }

}