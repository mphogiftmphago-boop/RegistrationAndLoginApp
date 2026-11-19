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


    // Getters for testing
    public List<String> getSentMessagesArray() { return sentMessagesArray; }
    public List<String> getDisregardedMessagesArray() { return disregardedMessagesArray; }
    public List<String> getStoredMessagesArray() { return storedMessagesArray; }
}