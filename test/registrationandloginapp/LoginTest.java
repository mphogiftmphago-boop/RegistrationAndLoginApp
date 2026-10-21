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

}
