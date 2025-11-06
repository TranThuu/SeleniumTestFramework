package com.orangehrm.test;

import com.orangehrm.utilities.ApiUtility;
import com.orangehrm.utilities.ExtentManager;
import com.orangehrm.utilities.RetryAnalyzer;
import com.sun.source.tree.AssertTree;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class APITest {
//    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Test
    public void verifyGetUserAPI(){
        //Step 1: Define API endpoint
        String endpoint = "https://jsonplaceholder.typicode.com/users/1";

        //Step 2: Send Get Request
        ExtentManager.logStep("Sending GET Request to the API");
        Response response = ApiUtility.sendGetRequest(endpoint);

        //Step 3: Validate status code
        ExtentManager.logStep("Validate API Response status code");
        Boolean isStatusCodeValid = ApiUtility.validateStatusCode(response,200);
        Assert.assertTrue(isStatusCodeValid, "Status code is not as Expected: ");
        if(isStatusCodeValid){
            ExtentManager.logPassAPI("Status code validation passed!");
        }else {
            ExtentManager.logFailureAPI("Status code validation failed!");
        }

        //Step 4: Validate username
        ExtentManager.logStep("Validating response body for username");
        String username = ApiUtility.getJsonValue(response, "username");
        Boolean isUserNameValid = "Bret".equals(username);
        Assert.assertTrue(isUserNameValid, "Username is not valid");
        if(isUserNameValid){
            ExtentManager.logPassAPI("Username validation passed!");
        }else{
            ExtentManager.logFailureAPI("Username validation Failed!");
        }
    }
}
