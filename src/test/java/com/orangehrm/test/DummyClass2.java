package com.orangehrm.test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DummyClass2 extends BaseClass {

    @Test
    public void dummyTest(){
//        ExtentManager.startTest("dummy Test 2");
        //
        Assert.assertTrue(true,"error");
        ExtentManager.logStep("Validation successful");
    }
}
