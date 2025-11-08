package com.orangehrm.test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.Homepage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.ExtentManager;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DummyClass extends BaseClass {
    private LoginPage loginPage;
    private Homepage homepage;

    @BeforeMethod
    public void setupPages(){
        loginPage = new LoginPage(getDriver());
        homepage = new Homepage(getDriver());
    }

    @Test
    public void dummyTest(){
        //ExtentManager.startTest("dummy Test 1");
        Assert.assertTrue(true,"error");
        //ExtentManager.logSkip("this case is skipped");
        //throw  new SkipException("Skipping the test as part of Testing");
    }

    @Test
    public void testJenkins(){
        //hehe
        //ExtentManager.startTest("dummy Test 1");
        Assert.assertTrue(true,"error");
        //ExtentManager.logSkip("this casgit e is skipped");
        //throw  new SkipException("Skipping the test as part of Testing");
    }

//    @Test
//    public void failTest(){
//        Assert.assertTrue(loginPage.verifyCredentialErrorMessage("haha"), "FailTest");
//    }
}
