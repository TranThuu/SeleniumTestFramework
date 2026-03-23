package tests.com.orangehrm.test.book;

import base.BaseClass;
import com.orangehrm.pages.bookPages.BookLoginPage;
import com.orangehrm.pages.bookPages.DashboardPage;
import utilities.DataProvidersUtil;
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

    @Test(dataProvider = "bookLoginData", dataProviderClass = DataProvidersUtil.class)
    public void verifyValidLoginTest(String email, String password){
        bookLogin.login(email, password);
        Assert.assertTrue(dashboardPage.isDashboardPage());
    }
}
