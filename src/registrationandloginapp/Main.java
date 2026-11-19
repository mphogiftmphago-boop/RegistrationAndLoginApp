package registrationandloginapp;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login appUser = new Login();
        Task taskManager = new Task(); // Initialize Task manager for Part 3

        // ... [Keep Part 1 Registration and Login code exactly as before] ...
        // (I am omitting the top part to save space, paste your existing Part 1/2 Main code here up to the QuickChat menu)
        
        System.out.println("=============================================");
        System.out.println("          USER REGISTRATION SYSTEM           ");
        System.out.println("=============================================");
        // ... (Paste registration logic here) ...
        
        System.out.println("\n=============================================");
        System.out.println("                 USER LOGIN                  ");
        System.out.println("=============================================");
        // ... (Paste login logic here) ...

        // QuickChat Menu
        System.out.println("\n=============================================");
        System.out.println("          Welcome to QuickChat.              ");
        System.out.println("=============================================");

        boolean appRunning = true;
        while (appRunning) {
            System.out.println("\nChoose an option from the menu:");
            System.out.println("Option 1) Send Messages");
            System.out.println("Option 2) Show recently sent messages");
            System.out.println("Option 3) Quit");
            System.out.println("Option 4) Stored Messages"); // NEW OPTION
            System.out.print("Select Option: ");

            String menuChoice = scanner.nextLine().trim();
            int choice = -1;
            try { choice = Integer.parseInt(menuChoice); } catch (NumberFormatException e) { continue; }

            switch (choice) {
                case 1:
                    // ... [Keep existing Send Message logic] ...
                    // IMPORTANT: After creating the message and setting status, call:
                    // taskManager.addMessage(currentMsg);
                    break;
                case 2:
                    System.out.println("Coming Soon.");
                    break;
                case 3:
                    appRunning = false;
                    break;
                case 4:
                    handleStoredMessagesMenu(scanner, taskManager, appUser.getFirstName());
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
        scanner.close();
    }

    // Sub-menu for Option 4
    private static void handleStoredMessagesMenu(Scanner scanner, Task taskManager, String senderName) {
        boolean subMenuRunning = true;
        // Load from JSON first
        taskManager.loadStoredMessagesFromJSON();

        while (subMenuRunning) {
            System.out.println("\n--- Stored Messages Menu ---");
            System.out.println("a. Display sender and recipient of all stored messages.");
            System.out.println("b. Display the longest stored message.");
            System.out.println("c. Search for a message ID.");
            System.out.println("d. Search for messages by recipient.");
            System.out.println("e. Delete a message using hash.");
            System.out.println("f. Display Report.");
            System.out.println("g. Back to Main Menu.");
            System.out.print("Select: ");

            String opt = scanner.nextLine().trim().toLowerCase();

            switch (opt) {
                case "a":
                    System.out.println(taskManager.displaySenderRecipient(senderName));
                    break;
                case "b":
                    System.out.println("Longest Message: " + taskManager.displayLongestMessage());
                    break;
                case "c":
                    System.out.print("Enter Message ID: ");
                    String id = scanner.nextLine().trim();
                    System.out.println(taskManager.searchByID(id));
                    break;
                case "d":
                    System.out.print("Enter Recipient Number: ");
                    String rec = scanner.nextLine().trim();
                    System.out.println(taskManager.searchByRecipient(rec));
                    break;
                case "e":
                    System.out.print("Enter Message Hash to delete: ");
                    String hash = scanner.nextLine().trim();
                    System.out.println(taskManager.deleteByHash(hash));
                    break;
                case "f":
                    System.out.println(taskManager.displayReport());
                    break;
                case "g":
                    subMenuRunning = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}