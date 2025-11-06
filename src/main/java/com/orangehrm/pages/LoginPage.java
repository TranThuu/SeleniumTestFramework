package com.orangehrm.pages;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private ActionDriver actionDriver;

    //Define locator using By class
    private By userNameField = By.xpath("//input[@name='username']");
    private By passwordField = By.xpath("//input[@name='password']");
    private By loginButton = By.xpath("//button[@type='submit']");
    private By forgotPasswordLink = By.xpath("//p[contains(., \"Forgot\")]");
    private By userNameErrorMessage = By.xpath("//form/div[1]/div/span[contains(., \"Required\")]");
    private By passwordErrorMessage = By.xpath("//form/div[2]/div/span[contains(., \"Required\")]");
    private By credentialErrorMessage = By.xpath("//p[contains(., \"Invalid credentials\")]");

    public LoginPage(WebDriver driver){
        this.actionDriver = BaseClass.getActionDriver();
    }
    //Method to perform login
    public void login(String username, String password){
        actionDriver.enterText(userNameField, username);
        actionDriver.enterText(passwordField, password);
        actionDriver.click(loginButton);
    }

    // Method to check if the Credential error message is displayed
    public boolean isCredentialErrorMessageDisplayed(){
        return actionDriver.isDisplayed(credentialErrorMessage);
    }

    //Method to get text from Credential error message
    public String getCredentialErrorMessage(){
        return actionDriver.getText(credentialErrorMessage);
    }

    // Verify Credential error message is correct or not
    public boolean verifyCredentialErrorMessage(String expectedMessage){
        return actionDriver.compareText(credentialErrorMessage,expectedMessage);
    }
}
