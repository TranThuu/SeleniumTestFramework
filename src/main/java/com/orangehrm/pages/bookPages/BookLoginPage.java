package com.orangehrm.pages.bookPages;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;
import org.openqa.selenium.By;

public class BookLoginPage {
    ActionDriver actionDriver;
    private By emailInput = By.name("email");
    private By passwordInput = By.name("password");
    private By loginButton = By.xpath("//button[@type='submit']");
    public BookLoginPage(){
        actionDriver = BaseClass.getActionDriver();
    }

    public void login(String email, String password){
        actionDriver.enterText(emailInput,email);
        actionDriver.enterText(passwordInput, password);
        actionDriver.click(loginButton);
    }
}
