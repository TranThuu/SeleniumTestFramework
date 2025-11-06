package com.orangehrm.actiondriver;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ActionDriver {
    private WebDriver driver;
    private WebDriverWait wait;
    public static final Logger logger = BaseClass.logger;

    public ActionDriver(WebDriver driver){
        this.driver = driver;
        int explicitWait = Integer.parseInt(BaseClass.getProp().getProperty("explicitWait"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
    }

    //Method to click an element
    public void click(By by){
        try{
            waitForElementToBeClickable(by);
            String elementDescription = getElementDescription(by);
            logger.info("Element is clicked -> " + elementDescription);
            applyBorder(by, "green");
            ExtentManager.logStepWithScreenshot(BaseClass.getDriver(),"Before click an element: " + getElementDescription(by),"Before Click: " + elementDescription);
            driver.findElement(by).click();
            ExtentManager.logStepWithScreenshot(BaseClass.getDriver(),"After clicked an element: " + elementDescription,"After Click: " + elementDescription);
        }catch (Exception e){
            applyBorder(by, "red");
            ExtentManager.logFailure(BaseClass.getDriver(),"Unable to click an element: " + by.toString(), by.toString());
            logger.error("Unable to click element: "+ e.getMessage());
        }
    }

    //Method to enter text into an input field
    public void enterText(By by, String value){
        try{
            waitForElementToBeVisible(by);
            WebElement element = driver.findElement(by);
            element.clear();
            element.sendKeys(value);
            logger.info("Entered text: \"" + value + "\" to element: " + getElementDescription(by));
            ExtentManager.logStep("Enter text: " + value);
        }catch (Exception e){
            applyBorder(by, "red");
            logger.error("Unable to enter the text in to this field: " + e.getMessage());
            ExtentManager.logFailure(BaseClass.getDriver(),"Unable to enter text!",by.toString());
        }
    }
    //Method to get text from an input field
    public String getText(By by){
        try{
            waitForElementToBeVisible(by);
            return driver.findElement(by).getText();
        }catch (Exception e){
            logger.error("Unable to get text: " + e.getMessage());
            return "";
        }
    }

    //Method compare two text
    public boolean compareText(By by, String expectedText){
        try{
            waitForElementToBeVisible(by);
            String actualText = driver.findElement(by).getText();
            if(actualText.equals(expectedText)){
                logger.info("Text are matching");
                ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Text are matching", expectedText);
                return true;
            }else{
                logger.info("Text are not matching");
                ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Text are not matching! actualText: \""+ actualText+"\", expectedText: \""+expectedText+"\"", expectedText);
                return false;
            }
        }catch (Exception e){
            logger.error("Unable to compare: "+ e.getMessage());
        }
        return false;
    }

    //Method to check if an element is displayed
    public boolean isDisplayed(By by){
        try{
            waitForElementToBeVisible(by);
            logger.info("Element is displayed: " + getElementDescription(by));
            ExtentManager.logStep("Element is displayed: "+getElementDescription(by));
            applyBorder(by, "green");
            ExtentManager.logStepWithScreenshot(BaseClass.getDriver(),"Element is displayed",getElementDescription(by));
            return driver.findElement(by).isDisplayed();
        }catch (Exception e){
            applyBorder(by, "red");
            logger.error("There an error: "+ e.getMessage());
            ExtentManager.logFailure(BaseClass.getDriver(),"Element is not displayed: " + by.toString(),"Element is not Displayed: "+ by.toString());
            return false;
        }
    }

    //Scroll to an element
    public void scrollToElement(By by){
        try{
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement element = driver.findElement(by);
            js.executeScript("arguments[0].scrollIntoView(true);", element);
        }catch (Exception e){
            logger.error("Unable to scroll to an Element: "+ e.getMessage());
        }
    }

    //Wait for the page to load
    public void waitForPageLoad(int timeOutInSec){
        try{
            wait.withTimeout(Duration.ofSeconds(timeOutInSec)).until(WebDriver -> ((JavascriptExecutor) WebDriver)
                    .executeScript("return document.readySate").equals("complete"));
        }catch (Exception e){
            logger.error("Page did  not load within: "+ timeOutInSec + " seconds. Exception: " + e.getMessage());
        }
    }

    //Wait for element to be clickable
    private void waitForElementToBeClickable (By by){
        try{
            wait.until(ExpectedConditions.elementToBeClickable(by));
        }catch (Exception e){
            logger.error("Element is not clickable: "+ e.getMessage());
            throw e;
        }
    }

    //Wait for element to be visible
    private void waitForElementToBeVisible(By by){
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        }catch (Exception e){
            logger.error("Element is not visible: " + e.getMessage());
            ExtentManager.logFailure(BaseClass.getDriver(), "Element is not visible: " + by.toString(),"Element is not visible: " + by.toString());
            throw e;
        }
    }

    //Method to get element description of an element using By locator
    public String getElementDescription(By locator){
        //Check if null driver or locator to avoid NullPointer Exception
        if(driver == null){
            return "Driver is null";
        }
        if(locator == null){
            return "Locator is null";
        }
        try {
            //Find the element using the locator
            WebElement element = driver.findElement(locator);

            // Get Element Attributes
            String name = element.getDomAttribute("name");
            String id = element.getDomAttribute("id");
            String text = element.getText();
            String className = element.getDomAttribute("class");
            String placeholder = element.getDomAttribute("placeholder");

            //Return the description based on element attributes
            if(isNotEmpty(name))
                return "Element with name: "+name;
            if(isNotEmpty(id))
                return "Id with name: "+id;
            if(isNotEmpty(text))
                return "Text with name: "+ truncate(text, 50);
            if(isNotEmpty(className))
                return "Classname with name: "+ truncate(className, 50);
            return "Placeholder with name: "+ truncate(placeholder, 50);
        }catch (Exception e){
            logger.error("Unable to describe the element: " + e.getMessage());
            ExtentManager.logFailure(BaseClass.getDriver(), "Unable to describe element: "+ locator.toString(), "Unable to describe element: " + locator.toString());
            //throw e;
            return  "Unable to describe the element";
        }
        //return  "Unable to describe the element";
    }

    //Utility method to check a String is not null or empty
    private boolean isNotEmpty(String value){
        return value!=null && !value.isEmpty();
    }

    //Utility method to truncate long String
    private String truncate(String value, int maxLength){
        if(value == null || value.length()<=maxLength)
            return value;
        return value.substring(0,maxLength)+ "...";
    }

    //Utility method to border an element
    public void applyBorder(By by, String color){
        try {
            //Locate the element
            WebElement element = driver.findElement(by);
            //Apply the border
            String script = "arguments[0].style.border = '3px solid " + color + "'";
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(script, element);
            logger.info("Applied the border with color " + color + " to element " + getElementDescription(by));
        }catch (Exception e){
            logger.warn("Failed to apply the border to an element: " + getElementDescription(by));
        }
    }

    //---------------- JavaScript Utility Methods -----------------------
    //Method to click using JavaScript
    public void clickUsingJS(By by){
        try{
            WebElement element = driver.findElement(by);((JavascriptExecutor)driver).executeScript("arguments[0].click();",element);
            applyBorder(by, "green");
            logger.info("Clicked an element using JavaScript: "+ getElementDescription(by));
        }catch (Exception e){
             applyBorder(by, "red");
             logger.error("Unable to click using JavaScript", e);
        }
    }
}
