package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.simulator.Simulator_A;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class MyAlgoTest {
    Building b1;
    Building b2;
    Building b3;
    Building b0;
    MyAlgo algo1;
    MyAlgo algo2;
    MyAlgo algo3;
    MyAlgo algo4;
    MyAlgo algo5;




    public MyAlgoTest(){
        Simulator_A.initData(1,null);
        b1 = Simulator_A.getBuilding();
        Simulator_A.initData(2,null);
        b2 = Simulator_A.getBuilding();
        Simulator_A.initData(3,null);
        b3 = Simulator_A.getBuilding();
        Simulator_A.initData(0,null);
        b0 = Simulator_A.getBuilding();
        algo1 = new MyAlgo(b1);
        algo2 = new MyAlgo(b2);
        algo3 = new MyAlgo(b3);
        algo4 = new MyAlgo(b0);
        algo5 = new MyAlgo(b0);

    }

    @Test
    void contains() {
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
                return 1;
            }

            @Override
            public int allocatedTo() {
                return 0;
            }
        }; // 2 -> 3
        algo1.allocateAnElevator(c2);
        assertTrue(algo1.contains(c2,0));
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
                return 4;
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
        }; // 4 -> 0
        algo2.allocateAnElevator(c3);
        assertTrue(algo2.contains(c3,0));
    }


    @Test
    void numberOfFloors() {
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
        double x= algo4.numberOfFloors(0,c6);
        double y= algo5.numberOfFloors(0,c7);
        assertTrue(x>y);
        algo4.allocateAnElevator(c1);
        algo5.allocateAnElevator(c2);
        x= algo4.numberOfFloors(0,c6);
        y= algo5.numberOfFloors(0,c7);
        assertTrue(x>y);
        x= algo4.numberOfFloors(0,c7);
        y= algo5.numberOfFloors(0,c6);
        assertFalse(x>y);
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

            Simulator_A.initData(0, null);//choose case 0
            b1 = Simulator_A.getBuilding();
            MyAlgo myAlgo1 = new MyAlgo(b1);
            Simulator_A.initAlgo(myAlgo1);
                Simulator_A.runSim();
                // in case 0 our elevator should finish all the calls and be in floor 0
            assertEquals(0,myAlgo1.getBuilding().getElevetor(0).getPos());
        Simulator_A.initData(1, null);//choose case 9
        b1 = Simulator_A.getBuilding();
        myAlgo1 = new MyAlgo(b1);
        Simulator_A.initAlgo(myAlgo1);
        Simulator_A.runSim();
        // our elevator should finish at floor -2
        assertEquals(-2,myAlgo1.getBuilding().getElevetor(0).getPos());
        Simulator_A.initData(9, null);//choose case 9
        b1 = Simulator_A.getBuilding();
        myAlgo1 = new MyAlgo(b1);
        Simulator_A.initAlgo(myAlgo1);
        Simulator_A.runSim();
        for(int i=0;i<b1.numberOfElevetors();i++)
        {   // size of route should be below 10 in case 9
            assertTrue(myAlgo1.route[i].size()<10);
        }
    }




}