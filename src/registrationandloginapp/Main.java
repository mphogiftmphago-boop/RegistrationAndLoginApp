package registrationandloginapp;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login appUser = new Login();

        // ------------------------------------------------------------------
        // PART 1: REGISTRATION & LOGIN
        // ------------------------------------------------------------------
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

        // Validate South African Cell Phone Number
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

        // User Login Authentication
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

        // ------------------------------------------------------------------
        // PART 2: QUICKCHAT MESSAGING (Accessible only after login)
        // ------------------------------------------------------------------
        System.out.println("\n=============================================");
        System.out.println("          Welcome to QuickChat.              ");
        System.out.println("=============================================");

        boolean appRunning = true;

        while (appRunning) {
            System.out.println("\nChoose an option from the menu:");
            System.out.println("Option 1) Send Messages");
            System.out.println("Option 2) Show recently sent messages");
            System.out.println("Option 3) Quit");
            System.out.print("Select Option: ");

            String menuChoice = scanner.nextLine().trim();
            int choice = -1;
            try {
                choice = Integer.parseInt(menuChoice);
            } catch (NumberFormatException e) {
                System.out.println("Invalid numeric input. Please enter 1, 2, or 3.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("\nHow many messages would you like to enter? ");
                    int messageCount = 0;
                    try {
                        messageCount = Integer.parseInt(scanner.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number. Returning to main menu.");
                        break;
                    }

                    // Loop only for the user-specified count
                    for (int i = 0; i < messageCount; i++) {
                        System.out.println("\n--- Message " + (i + 1) + " of " + messageCount + " ---");
                        Message currentMsg = new Message();
                        currentMsg.setMessageNumber(i);

                        // Recipient Cell Input
                        while (true) {
                            System.out.print("Enter Recipient Cell Number (e.g. +27718693002): ");
                            String recipient = scanner.nextLine().trim();
                            currentMsg.setRecipient(recipient);
                            System.out.println(currentMsg.checkRecipientCell());

                            if (currentMsg.isRecipientValid()) {
                                break;
                            }
                        }

                        // Message Text Input (<= 250 characters)
                        while (true) {
                            System.out.print("Enter Message text: ");
                            String msgContent = scanner.nextLine().trim();
                            String validation = currentMsg.checkMessageLength(msgContent);

                            if ("Message ready to send.".equals(validation)) {
                                currentMsg.setMessageText(msgContent);
                                System.out.println("Message ready to send.");
                                break;
                            } else {
                                System.out.println("Please enter a message of less than 250 characters.");
                                System.out.println(validation);
                            }
                        }

                        // Autogenerate hash
                        currentMsg.createMessageHash();

                        // Delivery options
                        System.out.println("\nSelect an action for this message:");
                        System.out.println("1) Send Message");
                        System.out.println("2) Disregard Message");
                        System.out.println("3) Store Message to send later");
                        System.out.print("Select: ");

                        int deliveryOption = 1;
                        try {
                            deliveryOption = Integer.parseInt(scanner.nextLine().trim());
                        } catch (NumberFormatException e) {
                            deliveryOption = 1;
                        }

                        String deliveryFeedback = currentMsg.SentMessage(deliveryOption);
                        System.out.println(deliveryFeedback);

                        // Handle Disregard delete confirmation
                        if (deliveryOption == 2) {
                            System.out.print("Enter '0' to confirm delete: ");
                            String deleteConfirm = scanner.nextLine().trim();
                            if ("0".equals(deleteConfirm)) {
                                System.out.println("Message successfully deleted.");
                            }
                        }

                        // Display details in required order: Message ID, Message Hash, Recipient, Message
                        if (deliveryOption == 1) {
                            System.out.println("\n--- Message Details ---");
                            System.out.println(currentMsg.getMessageDetails());
                        }
                    }

                    // Display accumulated total messages sent
                    System.out.println("\nTotal number of messages sent so far: " + Message.returnTotalMessages());
                    break;

                case 2:
                    // Feature still in development
                    System.out.println("Coming Soon.");
                    break;

                case 3:
                    System.out.println("Exiting QuickChat. Total messages sent during session: " + Message.returnTotalMessages());
                    appRunning = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
                    break;
            }
        }

        scanner.close();
    }
}