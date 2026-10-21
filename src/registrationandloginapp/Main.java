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

        
}
