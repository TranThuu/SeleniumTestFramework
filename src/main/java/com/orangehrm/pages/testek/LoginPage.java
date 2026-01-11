package com.orangehrm.pages.testek;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;
import org.openqa.selenium.By;

public class LoginPage {
    private ActionDriver actionDriver;

    private By usernameInput = By.id("normal_login_username");
    private By passwordInput = By.id("normal_login_password");
    private By submitButton = By.xpath("//button[@type='submit']");
    private By systemNameLabel = By.xpath("(//div[contains(@class,'login-form')]/div/div)[1]");
    public LoginPage(){
        this.actionDriver = BaseClass.getActionDriver();
    }

    public void login(String username, String password){
        actionDriver.enterText(usernameInput, username);
        actionDriver.enterText(passwordInput, password);
        actionDriver.click(submitButton);
    }

    public boolean isLoginPage(){
        return actionDriver.compareText(systemNameLabel, "Hệ thống quản lý sản phẩm");
    }
}
