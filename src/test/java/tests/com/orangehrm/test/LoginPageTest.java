package tests.com.orangehrm.test;

import base.BaseClass;
import com.orangehrm.pages.orangehrm.Homepage;
import com.orangehrm.pages.orangehrm.LoginPage;
import utilities.DataProvidersUtil;
import utilities.ExtentManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseClass {
   private LoginPage loginPage;
   private Homepage homepage;

   @BeforeClass
   public void setupPages(){
       loginPage = new LoginPage(getDriver());
       homepage = new Homepage(getDriver());
   }

   @Test(dataProvider="validLoginData", dataProviderClass = DataProvidersUtil.class)
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

    @Test(dataProvider="invalidLoginData", dataProviderClass = DataProvidersUtil.class)
    public void verifyInvalidLoginTest(String username, String password){
//        ExtentManager.startTest("Invalid Login Test");
        ExtentManager.logStep("Enter incorrect username & password");
        loginPage.login(username,password);
        ExtentManager.logStep("Invalid credential");
        Assert.assertTrue(loginPage.isCredentialErrorMessageDisplayed(), "Invalid credential should be displayed");
        ExtentManager.logStep("Validation successfully");
    }

    @Test
    public void failTest(){
       loginPage.wrongLocator();
    }
}
