package Model;

public class Outsourced extends Part {
    String companyName;

//    public Outsourced(){
//        super(id, name, price, stock, min, max);
//        this.companyName = "";
//    }

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
        System.out.println(this.toString());
    }
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }
    public String getCompanyName(){
        return companyName;
    }

    @Override
    public String toString() {
        return super.toString() + " " + "Outsourced{" +
                "companyName=" + companyName +
                '}';
    }
}
