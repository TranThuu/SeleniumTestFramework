package com.orangehrm.test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.Homepage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DataProviders;
import com.orangehrm.utilities.ExtentManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.xml.crypto.Data;

public class LoginPageTest extends BaseClass {
   private LoginPage loginPage;
   private Homepage homepage;

   @BeforeMethod
   public void setupPages(){
       loginPage = new LoginPage(getDriver());
       homepage = new Homepage(getDriver());
   }

   @Test(dataProvider="validLoginData", dataProviderClass = DataProviders.class)
    public void verifyValidLoginTest(String username, String password){
//       ExtentManager.startTest("Valid Login Test");
       ExtentManager.logStep("Navigating to Login Page entering username and password");
       loginPage.login(username,password);
       ExtentManager.logStep("Verifying Admin tab is visible or not");
       Assert.assertTrue(homepage.isAdminTabVisible(), "Admin tab should be visible after successful login");
       ExtentManager.logStep("Validation successfully");
       homepage.logout();
       ExtentManager.logStep("Logged out successfully");
   }

    @Test(dataProvider="invalidLoginData", dataProviderClass = DataProviders.class)
    public void verifyInvalidLoginTest(String username, String password){
//        ExtentManager.startTest("Invalid Login Test");
        ExtentManager.logStep("Enter incorrect username & password");
        loginPage.login(username,password);
        ExtentManager.logStep("Invalid credential");
        Assert.assertTrue(loginPage.isCredentialErrorMessageDisplayed(), "Invalid credential should be displayed");
        ExtentManager.logStep("Validation successfully");
    }
}
