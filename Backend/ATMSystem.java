package Backend;

import java.io.*;
import java.util.*;

public class ATMSystem {
    private List<User> users = new ArrayList<>();
    private final String FILE_NAME = "Detail.txt";

    // Load users from file into the list
    public void loadUsersFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String cardNumber = parts[0];
                    String pin = parts[1];
                    double balance = Double.parseDouble(parts[2]);
                    User user = new User(cardNumber, pin, balance);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    public void showAllUsers() {
        for (User user : users) {
            System.out.println("Card: " + user.getCardNum() + ", PIN: " + user.getPin() + ", Balance: " + user.getBalance());
        }
    }

    public User findUserByCardNum(String cardNum) {
        for (User user : users) {
            if (user.getCardNum().equals(cardNum)) {
                return user;
            }
        }
        return null;
    }

    public User loginExistingUser() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Your Card Number: ");
        String cardNum = sc.nextLine();

        User user = findUserByCardNum(cardNum);

        if (user == null) {
            System.out.println("Backend.User Not Found!");
            return null;
        }

        int attempts = 0;
        while (attempts < 3) {
            System.out.print("Enter Your 4 Digit PIN: ");
            String enteredPin = sc.nextLine();

            if (user.getPin().equals(enteredPin)) {
                System.out.println("Login Successful!");
                return user;
            } else {
                attempts++;
                System.out.println("Incorrect PIN. Attempts Left: " + (3 - attempts));
            }
        }

        System.out.println("Your Card is blocked. Contact Customer Care.");
        return null;
    }

    public void showMenu(User user) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n===== ATM MENU =====");
            System.out.println("1. Withdraw Money.");
            System.out.println("2. Check Balance.");
            System.out.println("3. Change PIN.");
            System.out.println("4. Exit.");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    withdraw(user);
                    break;
                case 2:
                    checkBalance(user);
                    break;
                case 3:
                    changePin(user);
                    break;
                case 4:
                    saveUsersToFile();
                    System.out.println("Thank you for using the ATM. Have a nice day!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void withdraw(User user) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the amount you want to withdraw: ");
        double amount = sc.nextDouble();

        if (amount <= 999) {
            System.out.println("Invalid amount. Withdraw atleast 1000 RS");
        } else if (amount > user.getBalance()) {
            System.out.println("Insufficient Balance.");
        } else {
            user.setBalance(user.getBalance() - amount);
            System.out.println("Withdrawal Successful. New Balance is: " + user.getBalance());
        }
    }

    public void checkBalance(User user) {
        System.out.println("Your current balance is: " + user.getBalance());
    }

    public void changePin(User user) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your current PIN: ");
        String oldPin = sc.nextLine();

        if (!user.getPin().equals(oldPin)) {
            System.out.println("Incorrect PIN.");
            return;
        }

        System.out.print("Enter new PIN: ");
        String newPin = sc.nextLine();
        user.setPin(newPin);

        System.out.println("PIN changed successfully!");
    }

    public void saveUsersToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (User user : users) {
                bw.write(user.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    public void registerNewUser() {
    Scanner sc = new Scanner(System.in);
    System.out.println("=== New Backend.User Registration ===");

    System.out.print("Enter a 16-digit card number: ");
    String cardNum = sc.nextLine().trim();

    // Check if card already exists
    if (findUserByCardNum(cardNum) != null) {
        System.out.println("This card number is already registerd.");
        return;
    }

    // Check card number length
    if (cardNum.length() != 16 || !cardNum.matches("\\d+")) {
        System.out.println("Invalid card number. It must be 16 digits.");
        return;
    }

    System.out.print("Set a 4-digit PIN: ");
    String pin = sc.nextLine().trim();

    if (pin.length() != 4 || !pin.matches("\\d+")) {
        System.out.println("Invalid PIN. It must be 4 digits.");
        return;
    }

    System.out.print("Enter initial deposit amount: ");
    double balance;
    try {
        balance = Double.parseDouble(sc.nextLine());
        if (balance < 0) {
            System.out.println("Initial balance cannot be negative.");
            return;
        }
    } catch (NumberFormatException e) {
        System.out.println("Invalid amount.");
        return;
    }

    // Create new user and save
    User newUser = new User(cardNum, pin, balance);
    users.add(newUser);
    saveUsersToFile();
    System.out.println("Backend.User registered successfully!");
    }
}
