package com.orangehrm.test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.Homepage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DataProviders;
import com.orangehrm.utilities.ExtentManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomepageTest extends BaseClass {
    private LoginPage loginPage;
    private Homepage homepage;

    @BeforeMethod
    public void setupPages(){
        loginPage = new LoginPage(getDriver());
        homepage = new Homepage(getDriver());
    }

    @Test(dataProvider = "validLoginData", dataProviderClass = DataProviders.class)
    public void verifyOrangeHRMLogo(String username, String password){
        //ExtentManager.startTest("Verify logo test");
        ExtentManager.logStep("Navigating to the login page entering username & password");
        loginPage.login(username, password);
        ExtentManager.logStep("Verify logo is visible or not");
        Assert.assertTrue(homepage.verifyOrangeHRMLogo(), "Logo is not visible");
        ExtentManager.logStep("Validation successful");
    }
}
