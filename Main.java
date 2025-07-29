import java.util.*;

public class Main {
    public static void main(String[] args) {
        ATMSystem atm = new ATMSystem();
        atm.loadUsersFromFile();

        Scanner sc = new Scanner(System.in);

        System.out.println("***** WELCOME TO SONERI BANK ATM *****");
        System.out.println("1. Existing User");
        System.out.println("2. New User");
        System.out.print("Select Option: ");
        

        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            User loggedInUser = atm.loginExistingUser();
            if (loggedInUser != null) {
                atm.showMenu(loggedInUser);
            }
        } else if(choice == 2) {
            atm.registerNewUser();
        } else {
            System.out.println("Invalid option. Exiting...");
        }
    }
}
