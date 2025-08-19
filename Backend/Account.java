package Backend;

public class Account {


    private String account_number;
    private String name;
    private String cnic;
    private String phone;
    private String account_type;
    private double balance;

    
    // Getter Methods


    public String getName() {
        return name;
    }

    public String getCnic() {
        return cnic;
    }

    public String getPhone() {
        return phone;
    }

    public String getAccount_number() {
        return account_number;
    }

    public String getAccount_type() {
        return account_type;
    }

    public double getBalance() {
        return balance;
    }


    // Setter Methods


    public void setName(String name) {
        this.name = name;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}