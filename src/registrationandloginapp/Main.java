package registrationandloginapp;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login appUser = new Login();

        System.out.println("=============================================");
        System.out.println("          USER REGISTRATION SYSTEM           ");
        System.out.println("=============================================");

        System.out.print("Enter First Name: ");
        appUser.setFirstName(scanner.nextLine().trim());

        System.out.print("Enter Last Name: ");
        appUser.setLastName(scanner.nextLine().trim());

        // Validate Username
        while (true) {
            System.out.print("Enter Username: ");
            String inputUser = scanner.nextLine().trim();
            appUser.setUsername(inputUser);

            if (appUser.checkUserName()) {
                System.out.println("Username successfully captured.");
                break;
            } else {
                System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
            }
        }

        // Validate Password
        while (true) {
            System.out.print("Enter Password: ");
            String inputPass = scanner.nextLine().trim();
            appUser.setPassword(inputPass);

            if (appUser.checkPasswordComplexity()) {
                System.out.println("Password successfully captured.");
                break;
            } else {
                System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            }
        }

        // Validate Cell Phone Number
        while (true) {
            System.out.print("Enter Cell Phone Number: ");
            String inputPhone = scanner.nextLine().trim();
            appUser.setCellPhoneNumber(inputPhone);

            if (appUser.checkCellPhoneNumber()) {
                System.out.println(appUser.checkCellPhoneNumberMessage());
                break;
            } else {
                System.out.println(appUser.checkCellPhoneNumberMessage());
            }
        }

        System.out.println("\nRegistration Result: " + appUser.registerUser());

        // Login Section
        System.out.println("\n=============================================");
        System.out.println("                 USER LOGIN                  ");
        System.out.println("=============================================");

        boolean isLoggedIn = false;
        while (!isLoggedIn) {
            System.out.print("Enter your username: ");
            String loginUser = scanner.nextLine().trim();

            System.out.print("Enter your password: ");
            String loginPass = scanner.nextLine().trim();

            isLoggedIn = appUser.loginUser(loginUser, loginPass);
            System.out.println(appUser.returnLoginStatus(isLoggedIn));
        }

        scanner.close();
    }
}
