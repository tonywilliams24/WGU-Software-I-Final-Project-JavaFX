package Model;

public class InHouse extends Part {
    int machineID;

//    public InHouse(){
//        super(id, name, price, stock, min, max);
//        this.machineID = 0;
//    }

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID){
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
        System.out.println(this.toString());
    }
    public void setMachineID(int machineID){
        this.machineID = machineID;
    }
    public int getMachineID(){
        return machineID;
    }

    @Override
    public String toString() {
        return super.toString() + " " + "InHouse{" +
                "machineID=" + machineID +
                '}';
    }
}
