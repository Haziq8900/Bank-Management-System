package Backend;

public class Customer {
    private int customer_id;
    private String name;
    private String cnic;
    private String phone;
    private String address;

    // Getter Methods
    public int getCustomer_id(){
        return customer_id;
    }

    public String getName(){
        return name;
    }

    public String getCnic(){
        return cnic;
    }

    public String getPhone(){
        return phone;
    }

    public String getAddress(){
        return address;
    }

    // Setter Methods
    public void setCustomer_id(int customer_id){
        this.customer_id = customer_id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setCnic(String cnic){
        this.cnic = cnic;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public void setAddress(String address){
        this.address = address;
    }

}
