package registrationandloginapp;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTest {

    private Login login;

    @Before
    public void setUp() {
        login = new Login();
        login.setFirstName("Kagiso");
        login.setLastName("Mokoena");
    }

    // ==========================================
    // 1. BOOLEAN TESTS (assertTrue / assertFalse)
    // ==========================================
    @Test
    public void testUsernameCorrectlyFormatted() {
        login.setUsername("kyl_1");
        assertTrue(login.checkUserName());
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {
        login.setUsername("kyle!!!!!!");
        assertFalse(login.checkUserName());
    }

    @Test
    public void testPasswordMeetsComplexityRequirements() {
        login.setPassword("Ch€ckM8#");
        assertTrue(login.checkPasswordComplexity());
    }

    @Test
    public void testPasswordDoesNotMeetComplexityRequirements() {
        login.setPassword("password");
        assertFalse(login.checkPasswordComplexity());
    }

    @Test
    public void testCellPhoneNumberCorrectlyFormatted() {
        login.setCellPhoneNumber("+27838968976");
        assertTrue(login.checkCellPhoneNumber());
    }

    @Test
    public void testCellPhoneNumberIncorrectlyFormatted() {
        login.setCellPhoneNumber("08966553");
        assertFalse(login.checkCellPhoneNumber());
    }

    @Test
    public void testLoginSuccessful() {
        login.setUsername("kyl_1");
        login.setPassword("Ch€ckM8#");
        boolean loginResult = login.loginUser("kyl_1", "Ch€ckM8#");
        assertTrue(loginResult);
    }

    @Test
    public void testLoginFailed() {
        login.setUsername("kyl_1");
        login.setPassword("Ch€ckM8#");
        boolean loginResult = login.loginUser("wrong_u", "wrongPass1!");
        assertFalse(loginResult);
    }

    // ==========================================
    // 2. MESSAGE TESTS (assertEquals)
    // ==========================================
    @Test
    public void testCellPhoneCorrectlyFormattedMessage() {
        login.setCellPhoneNumber("+27838968976");
        String expected = "Cell number successfully captured.";
        assertEquals(expected, login.checkCellPhoneNumberMessage());
    }

    @Test
    public void testCellPhoneIncorrectlyFormattedMessage() {
        login.setCellPhoneNumber("08966553");
        String expected = "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        assertEquals(expected, login.checkCellPhoneNumberMessage());
    }

    @Test
    public void testRegisterUserInvalidUsernameMessage() {
        login.setUsername("kyle!!!!!!");
        login.setPassword("Ch€ckM8#");
        login.setCellPhoneNumber("+27838968976");
        String expected = "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        assertEquals(expected, login.registerUser());
    }

    @Test
    public void testRegisterUserInvalidPasswordMessage() {
        login.setUsername("kyl_1");
        login.setPassword("password");
        login.setCellPhoneNumber("+27838968976");
        String expected = "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        assertEquals(expected, login.registerUser());
    }

    @Test
    public void testRegisterUserInvalidCellPhoneMessage() {
        login.setUsername("kyl_1");
        login.setPassword("Ch€ckM8#");
        login.setCellPhoneNumber("08966553");
        String expected = "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        assertEquals(expected, login.registerUser());
    }

    @Test
    public void testReturnLoginStatusSuccessMessage() {
        login.setUsername("kyl_1");
        login.setPassword("Ch€ckM8#");
        boolean status = login.loginUser("kyl_1", "Ch€ckM8#");
        String expected = "Welcome " + login.getFirstName() + ", " + login.getLastName() + " it is great to see you again.";
        assertEquals(expected, login.returnLoginStatus(status));
    }

    @Test
    public void testReturnLoginStatusFailureMessage() {
        login.setUsername("kyl_1");
        login.setPassword("Ch€ckM8#");
        boolean status = login.loginUser("invalid", "invalid");
        String expected = "Username or password incorrect, please try again.";
        assertEquals(expected, login.returnLoginStatus(status));
    }
}
