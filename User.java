public class User {

    private String cardNum;
    private String pin;
    private double balance;

    public User(String cardNum, String pin, double balance) {
        this.cardNum = cardNum;
        this.pin = pin;
        this.balance = balance;
    }

    // Getter Methods
    public String getCardNum() {
        return cardNum;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    // Setter Methods
    public void setPin(String newPin) {
        this.pin = newPin;
    }

    public void setBalance(double newBalance) {
        this.balance = newBalance;
    }

    public String toFileString() {
        return cardNum + "," + pin + "," + balance;
    }
}
