package tests.com.orangehrm.test;

import base.BaseClass;
import com.orangehrm.pages.orangehrm.Homepage;
import com.orangehrm.pages.orangehrm.LoginPage;
import com.orangehrm.pages.orangehrm.PimPage;
import utilities.DBConnection;
import utilities.DataProvidersUtil;
import utilities.ExtentManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Map;

public class DBVerificationTest extends BaseClass {
    private LoginPage loginPage;
    private PimPage pimPage;
    private Homepage homepage;

    @BeforeMethod
    public void setupPage(){
        loginPage = new LoginPage(getDriver());
        pimPage = new PimPage(getDriver());
        homepage = new Homepage(getDriver());
    }

    @Test(dataProvider = "emplVerification", dataProviderClass = DataProvidersUtil.class)
    public void verifyEmployeeNameFromDB(String employeeId, String employeeName){
        SoftAssert softAssert = getSoftAssert();

        ExtentManager.logStep("Logging with Admin Credentials");
        loginPage.login(prop.getProperty("username"),prop.getProperty("password"));

        ExtentManager.logStep("Click on PIM tab");
        homepage.clickOnPIMTab();

        ExtentManager.logStep("Search for Employee");
        pimPage.empNameSearch(employeeName);

        ExtentManager.logStep("Get the Employee Name from DB");

        //Fetch the data into a map
        Map<String,String> employeeDB = DBConnection.getEmployeeDetails(employeeId);

        String firstName = employeeDB.get("firstName");
        String middleName = employeeDB.get("middleName");
        String lastName = employeeDB.get("lastName");

        String empFirstAndMiddleName = (firstName + " " + middleName).trim();

        //Validation for first and middle name
        ExtentManager.logStep("Verify the employee first and middle name");
        softAssert.assertTrue(pimPage.verifyFirstAndMiddleName(empFirstAndMiddleName),"First and Middle Name are not matching");

        //Validation for last name
        ExtentManager.logStep("Verify the employee last name");
        softAssert.assertTrue(pimPage.verifyLastName(lastName), "Last name is not matching");

        ExtentManager.logStep("DB Validation done");

        softAssert.assertAll();
    }
}
