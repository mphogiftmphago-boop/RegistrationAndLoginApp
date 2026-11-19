package registrationandloginapp;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

public class TaskTest {

    private Task task;
    private Message msg1, msg2, msg3, msg4, msg5;

    @Before
    public void setUp() {
        task = new Task();

        // Test Data Message 1
        msg1 = new Message(1, "+27834557896", "Did you get the cake?");
        msg1.setMessageID("ID00000001"); // Mock ID
        msg1.createMessageHash();
        msg1.SentMessage(1); // Sent
        task.addMessage(msg1);

        // Test Data Message 2
        msg2 = new Message(2, "+27838884567", "Where are you? You are late! I have asked you to be on time.");
        msg2.setMessageID("ID00000002");
        msg2.createMessageHash();
        msg2.SentMessage(3); // Stored
        task.addMessage(msg2);

        // Test Data Message 3
        msg3 = new Message(3, "+27834484567", "Yohoooo, I am at your gate.");
        msg3.setMessageID("ID00000003");
        msg3.createMessageHash();
        msg3.SentMessage(2); // Disregard
        task.addMessage(msg3);

        // Test Data Message 4 (Note: Rubric says "Developer" but likely Recipient. ID is specific for test)
        msg4 = new Message(4, "0838884567", "It is dinner time !");
        msg4.setMessageID("0838884567"); // Specific ID for test
        msg4.createMessageHash();
        msg4.SentMessage(1); // Sent
        task.addMessage(msg4);

        // Test Data Message 5
        msg5 = new Message(5, "+27838884567", "Ok, I am leaving without you.");
        msg5.setMessageID("ID00000005");
        msg5.createMessageHash();
        msg5.SentMessage(3); // Stored
        task.addMessage(msg5);
    }

    @Test
    public void testSentMessagesArrayPopulated() {
        List<String> sent = task.getSentMessagesArray();
        // Expected: "Did you get the cake?", "It is dinner time !"
        assertTrue(sent.contains("Did you get the cake?"));
        assertTrue(sent.contains("It is dinner time !"));
    }

    
}