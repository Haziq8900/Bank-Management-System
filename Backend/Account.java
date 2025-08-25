package Backend;

import Model.AccountDatabase;

import java.sql.SQLException;
import java.util.Random;

public class Account {
    private String account_number;
    private String name;
    private String cnic;
    private String phone;
    private byte account_type;
    private double balance;
    private String address;

    public Account(String name, String cnic, String phone, byte account_type, String address) {
        this.address = address;
        this.name = name;
        this.cnic = cnic;
        this.phone = phone;
        this.account_type = account_type;
        this.setAccount_number();
    }

    public Account(String account_number){
        this.account_number = account_number;
    }
    // Getter Methods

    public String getAddress() {
        return address;
    }

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

    public byte getAccount_type() {
        return account_type;
    }

    public double getBalance() {
        return balance;
    }


    // Setter Methods


    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private void setAccount_number() {
        int random = new Random().nextInt(1000);
        this.account_number = this.getCnic() + random;
    }

    public void setAccount_type(byte account_type) {
        this.account_type = account_type;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


    public void deposit(double amount) throws SQLException {
        this.balance = amount;
        AccountDatabase accountDatabase = new AccountDatabase();
        accountDatabase.depositFund(this);
    }

    public void fundTransfer(Account receiver, double amount) throws SQLException{
        AccountDatabase accountDatabase = new AccountDatabase();
        this.setBalance(accountDatabase.getBalance(this));
        if (this.getBalance() >= amount) {
            receiver.setBalance(amount);
            accountDatabase.depositFund(receiver);
            accountDatabase.deductBalance(this, amount);
        } else {
            throw new SQLException("Insufficient Funds");
        }
    }
}