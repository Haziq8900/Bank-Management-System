package Model;

public class CustomerLogin {
    
    private int login_id;
    private int account_id;
    private String pin; 

    // Getter Methods
    public int getLogin_id(){
        return login_id;
    }

    public int getAccount_id(){
        return account_id;
    }

    public String getPin(){
        return pin;
    }

    // Setter Methods
    public void setLogin_id(int login_id){
        this.login_id = login_id;
    }

    public void setAccount_id(int account_id){
        this.account_id = account_id;
    }

    public void setPin(String pin){
        this.pin = pin;
    }
}
