package com.orangehrm.listeners;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;
import com.orangehrm.utilities.RetryAnalyzer;
import org.testng.IAnnotationTransformer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.IConfigurationAnnotation;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class TestListeners implements ITestListener, IAnnotationTransformer {

    //Triggered when a test starts
    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        ExtentManager.startTest(testName);
        ExtentManager.logStep("Test Started: " + testName);
    }

    //Triggered when a Test succeeds
    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        if (result.getTestClass().getName().toLowerCase().contains("api")) {
            ExtentManager.logPassAPI("Test Passed Successfully! Test End: " + testName + " - ✅ Test Passed");
        } else {
            ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Test Passed Successfully!", "Test End: " + testName + " - ✅ Test Passed");
        }
    }

    //Triggered when a Test Fails
    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String failureMessage = result.getThrowable().getMessage();
        ExtentManager.logStep(failureMessage);
        if (result.getTestClass().getName().toLowerCase().contains("api")) {
            ExtentManager.logFailureAPI("Test Failed! Test End: " + testName + " - ❌ Test Failed");
        } else {
            ExtentManager.logFailure(BaseClass.getDriver(), "Test Failed!", "Test End: " + testName + " - ❌ Test Failed");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        ExtentManager.logSkip("Test Skipped: "+ testName);
    }

    //Triggered when a suite starts
    @Override
    public void onStart(ITestContext context) {
        //Initialize the Extent Reports
        ExtentManager.getReporter();
    }

    //Triggered when the suite ends
    @Override
    public void onFinish(ITestContext context) {
        //Flush the Extent Reports
        ExtentManager.endTest();
    }

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}
