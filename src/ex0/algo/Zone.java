package ex0.algo;



public class Zone {
    private int[] _setOfFloors;
    private int _elevatorIndex;
    private int _zoneNumber;


    public Zone(int[] setOfFloors, int elevatorIndex, int zoneNumber)
    {
        this._setOfFloors=setOfFloors;
        this._elevatorIndex= elevatorIndex;
        this._zoneNumber=zoneNumber;
    }

    public int get_elevatorIndex() {return _elevatorIndex;}

    public void set_elevatorIndex(int _elevatorIndex) {this._elevatorIndex = _elevatorIndex;}

    public int[] get_setOfFloors() {return _setOfFloors;}

    public int get_zoneNumber() {return _zoneNumber;}

    public int zoneSize() {return this._setOfFloors.length;}

    public boolean isInZone(int x){
        if(x>=_setOfFloors[0] && x<=_setOfFloors[1])
            return true;
        return false;
    }

    public String toString(){
        return "(" + _setOfFloors[0] + " -> " + _setOfFloors[1] + ")";
    }

}
