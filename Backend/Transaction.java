package Backend;

import java.sql.Timestamp;

public class Transaction {
    private int transaction_id;
    private int account_number;
    private String type;
    private double amount;
    private Timestamp transaction_date;

    // Getter Methods

    public int getTransaction_id(){
        return transaction_id;
    }

    public int getAccountNumber(){
        return account_number;
    }

    public String getType(){
        return type;
    }

    public double getAmount(){
        return amount;
    }

    public Timestamp getTransaction_date(){
        return transaction_date;
    }

    // Setter Methods

    public void setTransaction_id(int transaction_id){
        this.transaction_id = transaction_id;
    }

    public void setAccountNumber(int account_number){
        this.account_number = account_number;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setAmount(double amount){
        this.amount = amount;
    }

    public void setTransaction_date(Timestamp transaction_date){
        this.transaction_date = transaction_date;
    }
}