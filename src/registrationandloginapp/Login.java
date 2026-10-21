package registrationandloginapp;

import java.util.regex.Pattern;

public class Login {

    private String username;
    private String password;
    private String cellPhoneNumber;
    private String firstName;
    private String lastName;

    public Login() {
    }

    public Login(String username, String password, String cellPhoneNumber, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean checkUserName() {
        return this.username != null && this.username.contains("_") && this.username.length() <= 5;
    }

    public boolean checkPasswordComplexity() {
        if (this.password == null || this.password.length() < 8) {
            return false;
        }
        boolean hasCapital = Pattern.compile("[A-Z]").matcher(this.password).find();
        boolean hasDigit = Pattern.compile("[0-9]").matcher(this.password).find();
        boolean hasSpecial = Pattern.compile("[^a-zA-Z0-9]").matcher(this.password).find();

        return hasCapital && hasDigit && hasSpecial;
    }

    public boolean checkCellPhoneNumber() {
        if (this.cellPhoneNumber == null) {
            return false;
        }
        String regex = "^\\+27[0-9]{9,10}$";
        return Pattern.matches(regex, this.cellPhoneNumber);
    }

    public String checkCellPhoneNumberMessage() {
        if (checkCellPhoneNumber()) {
            return "Cell number successfully captured.";
        } else {
            return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        }
    }

    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        } else if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        } else if (!checkCellPhoneNumber()) {
            return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        } else {
            return "The two above conditions have been met, and the user has been registered successfully.";
        }
    }

    public boolean loginUser(String enteredUsername, String enteredPassword) {
        if (this.username == null || this.password == null) {
            return false;
        }
        return this.username.equals(enteredUsername) && this.password.equals(enteredPassword);
    }

    public String returnLoginStatus(boolean loginSuccess) {
        if (loginSuccess) {
            return "Welcome " + this.firstName + ", " + this.lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}
