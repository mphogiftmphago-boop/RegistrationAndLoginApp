package registrationandloginapp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Task class handles the storage, retrieval, and reporting of messages 
 * using Arrays/Lists as required by Part 3.
 */
public class Task {

    // Arrays (using ArrayList for dynamic sizing as per "no hard-coding" requirement)
    // to store specific categories of data.
    private List<String> sentMessagesArray = new ArrayList<>();
    private List<String> disregardedMessagesArray = new ArrayList<>();
    private List<String> storedMessagesArray = new ArrayList<>();
    private List<String> messageHashArray = new ArrayList<>();
    private List<String> messageIDArray = new ArrayList<>();
    
    // Parallel arrays to keep track of full message objects for searching
    private List<Message> allMessagesList = new ArrayList<>();

    /**
     * Adds a message to the appropriate arrays based on its status.
     */
    public void addMessage(Message msg) {
        allMessagesList.add(msg);
        messageHashArray.add(msg.getMessageHash());
        messageIDArray.add(msg.getMessageID());

        String status = msg.getMessageStatus();
        String msgText = msg.getMessageText();

        if ("Sent".equals(status)) {
            sentMessagesArray.add(msgText);
        } else if ("Disregarded".equals(status)) {
            disregardedMessagesArray.add(msgText);
        } else if ("Stored".equals(status)) {
            storedMessagesArray.add(msgText);
        }
    }

    /**
     * Reads the messages.json file and populates the storedMessagesArray.
     * Reference: Standard Java FileReader implementation.
     */
    public void loadStoredMessagesFromJSON() {
        storedMessagesArray.clear(); // Clear existing to avoid duplicates on reload
        try (BufferedReader br = new BufferedReader(new FileReader("messages.json"))) {
            String line;
            StringBuilder jsonBlock = new StringBuilder();
            while ((line = br.readLine()) != null) {
                jsonBlock.append(line).append("\n");
                if (line.trim().equals("}")) {
                    // End of a JSON object
                    String block = jsonBlock.toString();
                    // Simple parsing for the "message" field
                    if (block.contains("\"status\": \"Stored\"")) {
                        String msgContent = extractJsonValue(block, "message");
                        if (msgContent != null) {
                            storedMessagesArray.add(msgContent);
                        }
                    }
                    jsonBlock = new StringBuilder(); // Reset for next object
                }
            }
        } catch (IOException e) {
            System.out.println("No stored messages file found or error reading file.");
        }
    }

    // Helper to extract value from simple JSON string
    private String extractJsonValue(String json, String key) {
        String searchKey = "\"" + key + "\": \"";
        int start = json.indexOf(searchKey);
        if (start == -1) return null;
        start += searchKey.length();
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }

    // ==========================================
    // MENU FEATURES (2a - 2f)
    // ==========================================

    /**
     * 2a. Display sender and recipient of all stored messages.
     * Note: "Sender" is assumed to be the logged-in user (passed as parameter) or generic.
     */
    public String displaySenderRecipient(String senderName) {
        StringBuilder sb = new StringBuilder();
        for (Message msg : allMessagesList) {
            if ("Stored".equals(msg.getMessageStatus())) {
                sb.append("Sender: ").append(senderName).append(", Recipient: ").append(msg.getRecipient()).append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * 2b. Display the longest stored message.
     */
    public String displayLongestMessage() {
        String longest = "";
        for (String msg : storedMessagesArray) {
            if (msg.length() > longest.length()) {
                longest = msg;
            }
        }
        return longest;
    }

    /**
     * 2c. Search for a message ID and display corresponding recipient and message.
     */
    public String searchByID(String id) {
        for (Message msg : allMessagesList) {
            if (msg.getMessageID().equals(id)) {
                return "Recipient: " + msg.getRecipient() + "\nMessage: " + msg.getMessageText();
            }
        }
        return "Message ID not found.";
    }

    /**
     * 2d. Search for all messages stored for a particular recipient.
     */
    public String searchByRecipient(String recipient) {
        StringBuilder sb = new StringBuilder();
        for (Message msg : allMessagesList) {
            // Check both Sent and Stored as per test data requirement "Search all messages sent or stored"
            if (("Stored".equals(msg.getMessageStatus()) || "Sent".equals(msg.getMessageStatus())) 
                && msg.getRecipient().equals(recipient)) {
                sb.append(msg.getMessageText()).append(" ");
            }
        }
        return sb.toString().trim();
    }

    /**
     * 2e. Delete a message using the message hash.
     */
    public String deleteByHash(String hash) {
        for (int i = 0; i < allMessagesList.size(); i++) {
            Message msg = allMessagesList.get(i);
            if (msg.getMessageHash().equals(hash)) {
                String deletedMsgText = msg.getMessageText();
                // Remove from all lists
                allMessagesList.remove(i);
                messageHashArray.remove(i);
                messageIDArray.remove(i);
                sentMessagesArray.remove(deletedMsgText);
                disregardedMessagesArray.remove(deletedMsgText);
                storedMessagesArray.remove(deletedMsgText);
                
                return "Message: \"" + deletedMsgText + "\" successfully deleted.";
            }
        }
        return "Hash not found.";
    }

    /**
     * 2f. Display a report listing full details of all stored messages.
     */
    public String displayReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- MESSAGE REPORT ---\n");
        for (Message msg : allMessagesList) {
            if ("Stored".equals(msg.getMessageStatus())) {
                sb.append("Hash: ").append(msg.getMessageHash()).append("\n");
                sb.append("Recipient: ").append(msg.getRecipient()).append("\n");
                sb.append("Message: ").append(msg.getMessageText()).append("\n\n");
            }
        }
        return sb.toString();
    }

    // Getters for testing
    public List<String> getSentMessagesArray() { return sentMessagesArray; }
    public List<String> getDisregardedMessagesArray() { return disregardedMessagesArray; }
    public List<String> getStoredMessagesArray() { return storedMessagesArray; }
}