package registrationandloginapp;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * The Message class encapsulates message generation, validation rules,
 * hash algorithm creation, delivery action tracking, and JSON persistence.
 */
public class Message {

    // Instance attributes
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String messageStatus; // "Sent", "Disregarded", or "Stored"

    // Static variables to track message session state across the application
    private static int totalMessagesSent = 0;
    private static final List<Message> sentMessagesList = new ArrayList<>();

    /**
     * Default constructor: initializes a randomly generated 10-digit ID.
     */
    public Message() {
        this.messageID = generateRandomMessageID();
    }

    /**
     * Parameterized constructor: creates a message with designated tracking details.
     */
    public Message(int messageNumber, String recipient, String messageText) {
        this.messageID = generateRandomMessageID();
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
    }

    // ==========================================
    // GETTERS AND SETTERS
    // ==========================================

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(int messageNumber) {
        this.messageNumber = messageNumber;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    // ==========================================
    // REQUIRED ASSIGNMENT METHODS
    // ==========================================

    /**
     * Autogenerates a random 10-digit numeric ID.
     */
    public String generateRandomMessageID() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

    /**
     * Ensures that the message ID is not more than ten characters long.
     * 
     * @return true if ID <= 10 characters, false otherwise.
     */
    public boolean checkMessageID() {
        return this.messageID != null && this.messageID.length() <= 10;
    }

    /**
     * Ensures that the recipient cell number contains an international country code (+27)
     * and is no more than ten characters long after the country code.
     * 
     * @return Status string describing cell validity.
     */
    public String checkRecipientCell() {
        if (this.recipient == null) {
            return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        }
        // South African international format: +27 followed by 9 to 10 digits
        String regex = "^\\+27[0-9]{9,10}$";
        if (Pattern.matches(regex, this.recipient)) {
            return "Cell number successfully captured.";
        } else {
            return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        }
    }

    /**
     * Helper boolean to verify recipient validity.
     */
    public boolean isRecipientValid() {
        return "Cell number successfully captured.".equals(checkRecipientCell());
    }

    /**
     * Validates that the message text does not exceed 250 characters.
     * 
     * @param message Text to check.
     * @return Status message including exact character excess if invalid.
     */
    public String checkMessageLength(String message) {
        if (message != null && message.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = (message == null) ? 0 : (message.length() - 250);
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }

    /**
     * Autogenerates a Message Hash:
     * First two numbers of the message ID, a colon (:), the number of the message,
     * a colon (:), and the first and last words combined in uppercase.
     * Example: 00:0:HITHANKS
     * 
     * @return Formatted capitalized hash String.
     */
    public String createMessageHash() {
        if (this.messageID == null || this.messageID.length() < 2 || this.messageText == null || this.messageText.trim().isEmpty()) {
            return "";
        }

        // First two characters of message ID
        String idPrefix = this.messageID.substring(0, 2);

        // Extract first and last words
        String[] words = this.messageText.trim().split("\\s+");
        String firstWord = words[0].replaceAll("[^a-zA-Z0-9]", "");
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z0-9]", "");

        this.messageHash = (idPrefix + ":" + this.messageNumber + ":" + firstWord + lastWord).toUpperCase();
        return this.messageHash;
    }

    /**
     * Allows the user to choose to send, disregard, or store the message.
     * 1) Send Message -> "Message successfully sent."
     * 2) Disregard Message -> "Press 0 to delete the message."
     * 3) Store Message -> "Message successfully stored."
     * 
     * @param choice numeric choice (1, 2, or 3)
     * @return String status feedback.
     */
    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                this.messageStatus = "Sent";
                totalMessagesSent++;
                sentMessagesList.add(this);
                return "Message successfully sent.";
            case 2:
                this.messageStatus = "Disregarded";
                return "Press 0 to delete the message.";
            case 3:
                this.messageStatus = "Stored";
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option selected.";
        }
    }

    /**
     * Overload to allow parameterless call using internal messageStatus.
     */
    public String SentMessage() {
        return SentMessage(1);
    }

    /**
     * Displays all message details in the required order:
     * Message ID, Message Hash, Recipient, Message.
     */
    public String getMessageDetails() {
        return "Message ID: " + this.messageID + "\n"
                + "Message Hash: " + this.messageHash + "\n"
                + "Recipient: " + this.recipient + "\n"
                + "Message: " + this.messageText;
    }

    /**
     * Returns all the messages sent while the program is running.
     */
    public static String printMessages() {
        if (sentMessagesList.isEmpty()) {
            return "No messages sent yet.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\n====== SENT MESSAGES ======\n");
        for (int i = 0; i < sentMessagesList.size(); i++) {
            sb.append("\n--- Message ").append(i + 1).append(" ---\n");
            sb.append(sentMessagesList.get(i).getMessageDetails()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Returns the total number of messages sent.
     */
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    /**
     * Direct alias matching the exact method naming in the brief: Int: returnTotalMessagess()
     */
    public static int returnTotalMessagess() {
        return returnTotalMessages();
    }

    /**
     * Resets accumulator for clean test execution.
     */
    public static void resetSession() {
        totalMessagesSent = 0;
        sentMessagesList.clear();
    }

    /**
     * Stores the message details into a JSON formatted file.
     * 
     * Code Attribution:
     * File writing techniques adapted from Oracle Java Standard Documentation.
     * https://docs.oracle.com/javase/8/docs/api/java/io/FileWriter.html
     */
    public void storeMessage() {
        String jsonFormat = "{\n"
                + "  \"messageID\": \"" + this.messageID + "\",\n"
                + "  \"messageNumber\": " + this.messageNumber + ",\n"
                + "  \"recipient\": \"" + this.recipient + "\",\n"
                + "  \"message\": \"" + (this.messageText != null ? this.messageText.replace("\"", "\\\"") : "") + "\",\n"
                + "  \"messageHash\": \"" + this.messageHash + "\",\n"
                + "  \"status\": \"" + this.messageStatus + "\"\n"
                + "}\n";

        try (FileWriter writer = new FileWriter("messages.json", true)) {
            writer.write(jsonFormat);
            writer.flush();
        } catch (IOException e) {
            System.err.println("Error saving message to JSON: " + e.getMessage());
        }
    }
}