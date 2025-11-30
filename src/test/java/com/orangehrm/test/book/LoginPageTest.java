package com.orangehrm.test.book;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.bookPages.BookLoginPage;
import com.orangehrm.pages.bookPages.DashboardPage;
import com.orangehrm.utilities.DataProviders;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseClass {
    private BookLoginPage bookLogin;
    private DashboardPage dashboardPage;

    @BeforeClass
    public void setupPage(){
        bookLogin = new BookLoginPage();
        dashboardPage = new DashboardPage();
    }

    @Test(dataProvider = "bookLoginData", dataProviderClass = DataProviders.class)
    public void verifyValidLoginTest(String email, String password){
        bookLogin.login(email, password);
        Assert.assertTrue(dashboardPage.isDashboardPage());
    }
}
