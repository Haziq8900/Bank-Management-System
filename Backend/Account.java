package Backend;

public class Account {

    private int account_id;
    private int customer_id;
    private String account_number;
    private String account_type;
    private double balance;
    
    // Getter Methods
    public int getAccount_id() {
        return account_id;
    }

    public int getCustomer_id() {
        return customer_id;
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
    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
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