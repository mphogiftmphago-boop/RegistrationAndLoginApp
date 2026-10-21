package registrationandloginapp;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit 4 Test file for Part 2 QuickChat messaging requirements.
 */
public class MessageTest {

    private Message message;

    @Before
    public void setUp() {
        Message.resetSession();
        message = new Message();
    }

    // ==========================================
    // 1. MESSAGE LENGTH TESTS (assertEquals)
    // ==========================================

    @Test
    public void testMessageLengthSuccess() {
        String validMsg = "Hi Mike, can you join us for dinner tonight?";
        String expected = "Message ready to send.";
        assertEquals(expected, message.checkMessageLength(validMsg));
    }

    @Test
    public void testMessageLengthFailure() {
        // Message of 260 characters (exceeds 250 by exactly 10)
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 260; i++) {
            sb.append("X");
        }
        String invalidMsg = sb.toString();
        String expected = "Message exceeds 250 characters by 10; please reduce the size.";
        assertEquals(expected, message.checkMessageLength(invalidMsg));
    }

    // ==========================================
    // 2. RECIPIENT NUMBER TESTS
    // ==========================================

    @Test
    public void testRecipientNumberTask1Valid() {
        // Test Data for Task 1: +27718693002
        message.setRecipient("+27718693002");
        String expected = "Cell number successfully captured.";
        assertEquals(expected, message.checkRecipientCell());
        assertTrue(message.isRecipientValid());
    }

    @Test
    public void testRecipientNumberMessage2Invalid() {
        // Test Data for Message 2: 08575975889 (no international code)
        message.setRecipient("08575975889");
        String expected = "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        assertEquals(expected, message.checkRecipientCell());
        assertFalse(message.isRecipientValid());
    }

    // ==========================================
    // 3. MESSAGE ID AND HASH TESTS
    // ==========================================

    @Test
    public void testMessageIDLength() {
        message.setMessageID("1234567890");
        assertTrue(message.checkMessageID());

        message.setMessageID("1234567890123");
        assertFalse(message.checkMessageID());
    }

    @Test
    public void testCreateMessageHash() {
        message.setMessageID("0012345678");
        message.setMessageNumber(0);
        message.setMessageText("Hi Thanks");

        String expectedHash = "00:0:HITHANKS";
        assertEquals(expectedHash, message.createMessageHash());
    }

    // ==========================================
    // 4. MESSAGE ACTIONS TESTS (Additional Test Data)
    // ==========================================

    @Test
    public void testSendMessageOption1() {
        String result = message.SentMessage(1);
        assertEquals("Message successfully sent.", result);
        assertEquals(1, Message.returnTotalMessages());
    }

    @Test
    public void testDisregardMessageOption2() {
        String result = message.SentMessage(2);
        assertEquals("Press 0 to delete the message.", result);
        assertEquals(0, Message.returnTotalMessages());
    }

    @Test
    public void testStoreMessageOption3() {
        message.setMessageID("1234567890");
        message.setMessageNumber(0);
        message.setRecipient("+27718693002");
        message.setMessageText("Storing message content.");
        message.createMessageHash();

        String result = message.SentMessage(3);
        assertEquals("Message successfully stored.", result);
        assertEquals(0, Message.returnTotalMessages());
    }

    // ==========================================
    // 5. TOTAL MESSAGES ACCUMULATOR TEST
    // ==========================================

    @Test
    public void testTotalMessagesSentAccumulated() {
        // Message 1: Sent (increments count to 1)
        Message msg1 = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        msg1.SentMessage(1);

        // Message 2: Discarded (count remains 1)
        Message msg2 = new Message(1, "08575975889", "Hi Keegan, did you receive the payment?");
        msg2.SentMessage(2);

        assertEquals(1, Message.returnTotalMessages());
        assertEquals(1, Message.returnTotalMessagess());
    }
}