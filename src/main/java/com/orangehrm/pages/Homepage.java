package com.orangehrm.pages;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Homepage {
    private ActionDriver actionDriver;

    //Define locators using By class
    private By adminTab = By.xpath("//span[text() = 'Admin']");
    private By pimTab = By.xpath("//span[text() = 'PIM']");
    private By userIdButton = By.className("oxd-userdropdown-tab");
    private By logoutButton = By.xpath("//a[text()='Logout']");
    private By orangeHRMlogo = By.xpath("//div[@class='oxd-brand-banner']/img");

    //Initialize the ActionDriver object
    public Homepage(WebDriver driver){
        actionDriver = BaseClass.getActionDriver();
    }

    //Verify if Admin tab is visible
    public boolean isAdminTabVisible(){
        return actionDriver.isDisplayed(adminTab);
    }

    public boolean verifyOrangeHRMLogo(){
        return actionDriver.isDisplayed(orangeHRMlogo);
    }

    //Method to perform logout operation
    public void logout(){
        actionDriver.click(userIdButton);
        actionDriver.click(logoutButton);
    }

    //Method to navigate to PIM tab
    public void clickOnPIMTab(){
        actionDriver.click(pimTab);
    }
}
