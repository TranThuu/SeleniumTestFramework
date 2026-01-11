package com.orangehrm.actiondriver;

import com.aventstack.extentreports.ExtentReports;
import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;
import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ser.Serializers;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.jar.Attributes;

public class ActionDriver {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private Actions actions;
    public static final Logger logger = BaseClass.logger;

    public ActionDriver(WebDriver driver){
        this.driver = driver;
        int explicitWait = Integer.parseInt(BaseClass.getProp().getProperty("explicitWait"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    //Method to click an element
    public void click(By by){
        try{
            waitForElementToBeClickable(by);
            String elementDescription = getElementDescription(by);
            applyBorder(by, "green");
            logger.info("Element is clicked: " + elementDescription);
            ExtentManager.logStepWithScreenshot(BaseClass.getDriver(),"Before click an element: " + elementDescription,"Before Click: " + elementDescription);
            driver.findElement(by).click();
            ExtentManager.logStepWithScreenshot(BaseClass.getDriver(),"After clicked an element: " + elementDescription,"After Click: " + elementDescription);
        }catch (Exception e){
            applyBorder(by, "red");
            ExtentManager.logFailure(BaseClass.getDriver(),"Unable to click an element: " + by.toString(), by.toString());
            logger.error("Unable to click element: "+ e.getMessage());
            throw e;
        }
    }

    public void clickByJs(By by){
        try{
            waitForElementToBeClickable(by);
            String elementDescription = getElementDescription(by);
            applyBorder(by,"green");
            logger.info("Element is clicked: " +elementDescription);
            ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Before click an element: "+elementDescription, "Before Click: " + elementDescription);
            js.executeScript("arguments[0].click()",by);
            ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "After click element: " + elementDescription, "After click: " + elementDescription);
        }catch (Exception e){
            applyBorder(by, "red");
            ExtentManager.logFailure(BaseClass.getDriver(),"Unable to click an element: " + by.toString(), by.toString());
            logger.error("Unable to click element: " + e.getMessage());
            throw e;
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
            ExtentManager.logFailure(BaseClass.getDriver(),"Unable to enter text: "+value, by.toString());
            throw e;
        }
    }

    public void selectOptionByText(By by, String value){
        try{
            waitForElementToBeVisible(by);
            String elementDescription = getElementDescription(by);
            WebElement element = driver.findElement(by);
            Select select = new Select(element);
            select.selectByValue(value);
            logger.info("Select value \"" + value + "\" for " + elementDescription);
            ExtentManager.logStep("Select value \"" + value + "\" for " + elementDescription);
        }catch (Exception e){
            applyBorder(by,"red");
            logger.error("Unable to select by value: " + value + " for: " + e.getMessage());
            ExtentManager.logFailure(BaseClass.getDriver(),"Unable to select by value: " + value + " for: " + by.toString(), "Unable to select by value: " + value + " for: " + by.toString());
            throw e;
        }
    }

    public void selectOptionByIndex(By by, int index){
        try{
            waitForElementToBeVisible(by);
            String elementDescription = getElementDescription(by);
            WebElement element = driver.findElement(by);
            Select select = new Select(element);
            select.selectByIndex(index);
            logger.info("Select index \"" + index + "\" for " + elementDescription);
            ExtentManager.logStep("Select index \"" + index + "\" for " + elementDescription);
        }catch (Exception e){
            applyBorder(by,"red");
            logger.error("Unable to select by index: " + index + " for: " + e.getMessage());
            ExtentManager.logFailure(BaseClass.getDriver(),"Unable to select by index: " + index + " for: " + by.toString(), "Unable to select by index: " + index + " for: " + by.toString());
            throw e;
        }
    }

    public void selectOptionByVisibleText(By by, String visibleText){
        try{
            waitForElementToBeVisible(by);
            String elementDescription = getElementDescription(by);
            WebElement element = driver.findElement(by);
            Select select = new Select(element);
            select.selectByVisibleText(visibleText);
            logger.info("Select visible text \"" + visibleText + "\" for " + elementDescription);
            ExtentManager.logStep("Select visible text \"" + visibleText + "\" for " + elementDescription);
        }catch (Exception e){
            applyBorder(by,"red");
            logger.error("Unable to select by index: " + visibleText + " for: " + e.getMessage());
            ExtentManager.logFailure(BaseClass.getDriver(),"Unable to select by visible text: " + visibleText + " for: " + by.toString(), "Unable to select by value: " + visibleText + " for: " + by.toString());
            throw e;
        }
    }

    public void doubleClick(By by){
        try{
            waitForElementToBeVisible(by);
            String elementDescription = getElementDescription(by);
            WebElement element = driver.findElement(by);
            applyBorder(by, "green");
            actions.doubleClick(element).perform();
            logger.info("Double click element: " + elementDescription);
            ExtentManager.logStep("Double click element " + elementDescription);
        }catch (Exception e){
            applyBorder(by, "red");
            logger.error("Unable to double click element: " + e.getMessage());
            ExtentManager.logFailure(BaseClass.getDriver(), "Unable to double click element: " + by.toString(), "Unable to double click element: " + by.toString());
            throw e;
        }
    }

    public void rightClick(By by){
        try{
            waitForElementToBeVisible(by);
            String elementDescription = getElementDescription(by);
            WebElement element = driver.findElement(by);
            applyBorder(by, "green");
            actions.contextClick(element).perform();
            logger.info("Right click element: " + elementDescription);
            ExtentManager.logStep("Right click element: " + elementDescription);
        }catch (Exception e){
            applyBorder(by, "red");
            logger.error("Unable to right click element: " + e.getMessage());
            ExtentManager.logFailure(BaseClass.getDriver(), "Unable to click element: " + by.toString(), "Unable to click element: " + by.toString());
            throw e;
        }
    }

    public void dragAndDrop(By source, By target) {
        try {
            waitForElementToBeClickable(source);
            waitForElementToBeVisible(target);
            String elementDescriptionSource = getElementDescription(source);
            String elementDescriptionTarget = getElementDescription(target);
            applyBorder(source, "green");
            applyBorder(target, "green");
            actions.dragAndDrop(driver.findElement(source), driver.findElement(target)).perform();
            logger.info("Drag element: " + elementDescriptionSource + " to element: " + elementDescriptionTarget);
            ExtentManager.logStep("Drag element: " + elementDescriptionSource + " to element: " + elementDescriptionTarget);
        }catch (Exception e){
            applyBorder(source, "red");
            applyBorder(target, "red");
            logger.error("Unable to drag and drop element: " + e.getMessage());
            ExtentManager.logFailure(BaseClass.getDriver(), "Unable to drag: " + source.toString() + " to: " + target.toString(), "Unable to drag and drop");
            throw e;
        }
    }

    public void hoverToElement(By by){
        try{
            waitForElementToBeVisible(by);
            String elementDescription = getElementDescription(by);
            applyBorder(by, "green");
            actions.moveToElement(driver.findElement(by));
            logger.info("Hover to element: " + elementDescription);
            ExtentManager.logStep("Hover to element: " + elementDescription);
        }catch (Exception e){
            applyBorder(by, "red");
            logger.error("Unable to hover element: " + e.getMessage());
            ExtentManager.logFailure(BaseClass.getDriver(), "Unable to hover element: " + by.toString(), "Unable to hover element" );
            throw e;
        }
    }

    public void acceptAlert(){
        try{
            waitForAlertPresent();
            Alert alert = driver.switchTo().alert();
            alert.accept();
            logger.info("Alert is accepted");
            ExtentManager.logStep("Alert is accepted");
        }catch (Exception e){
            logger.error("Unable to accept Alert: " + e.getMessage());
            ExtentManager.logFailure(BaseClass.getDriver(), "Unable to accept Alert", "Unable to accept Alert");
            throw e;
        }
    }

    public void dismissAlert(){
        try{
            waitForAlertPresent();
            Alert alert = driver.switchTo().alert();
            alert.dismiss();
            logger.info("Alert is dismissed");
            ExtentManager.logStep("Alert is dismissed");
        }catch (Exception e){
            logger.error("Unable to dismiss Alert: " + e.getMessage());
            ExtentManager.logFailure(BaseClass.getDriver(), "Unable to dismiss Alert", "Unable to dismiss Alert");
            throw e;
        }
    }

    public void sendKeysAndAcceptAlert(String value){
        try{
            waitForAlertPresent();
            Alert alert = driver.switchTo().alert();
            alert.sendKeys(value);
            alert.accept();
            logger.info("Alert is enter text " + value + " and accepted");
            ExtentManager.logStep("Alert is enter text " + value + " and accepted");
        }catch (Exception e){
            logger.error("Unable to enter text " + value +" and accept Alert: " + e.getMessage());
            ExtentManager.logFailure(BaseClass.getDriver(), "Unable to enter text " + value +"  and accept Alert", "Unable to enter text " + value +"  and accept Alert");
            throw e;
        }
    }

    //Method to get text from an input field
    public String getText(By by){
        try{
            waitForElementToBeVisible(by);
            return driver.findElement(by).getText();
        }catch (Exception e){
            logger.error("Unable to get text: " + e.getMessage());
            throw e;
        }
    }

    //Method to get value attribute from a tag
    public String getValueOfAttribute(By by, String attribute){
        try{
            waitForElementToBeVisible(by);
            waitForAttributeToBeNotEmpty(by,attribute);
            return driver.findElement(by).getAttribute(attribute);
        }catch (Exception e){
            logger.error("Unable to get  of \"" + attribute+"\" attribute: " + e.getMessage());
            throw e;
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

    public boolean compareTwoString(String value1, String value2){
        try{
            logger.info("Texts are matching! actualText: \""+ value1+"\"");
            return value1.equals(value2);
        }catch (Exception e){
            ExtentManager.logFailure(BaseClass.getDriver(), "Texts are not matching! actualText: \""+ value1+"\", expectedText: \""+value2+"\"","texts are not matched");
            return false;
        }
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
            wait.withTimeout(Duration.ofSeconds(timeOutInSec)).until(WebDriver -> js
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
    private void  waitForElementToBeVisible(By by){
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        }catch (Exception e){
            logger.error("Element is not visible: " + e.getMessage());
            ExtentManager.logFailure(BaseClass.getDriver(), "Element is not visible: " + by.toString(),"Element is not visible: " + by.toString());
            throw e;
        }
    }

    //Wait for attribute to be visible
    private void waitForAttributeToBeNotEmpty(By by, String attribute){
        try{
            wait.until(ExpectedConditions.attributeToBeNotEmpty(driver.findElement(by), attribute));
        }catch (Exception e){
            logger.error("Attribute is empty: " + e.getMessage());
            ExtentManager.logFailure(BaseClass.getDriver(), "Attribute is empty: " + by.toString(),"Attribute is empty: " + by.toString());
            throw e;
        }
    }

    //Wait for Alert is present
    private void waitForAlertPresent(){
        try{
            wait.until(ExpectedConditions.alertIsPresent());
        }catch (Exception e){
            logger.error("Alert is not present");
            ExtentManager.logFailure(BaseClass.getDriver(), "Alert is not present", "Alert is not present");
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
            logger.warn("Failed to apply the border to an element: " + by.toString());
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

    //Transform String to NodeJson
    public JsonNode stringToNodeJson(String json) throws JsonProcessingException {
        try{
            //Transform the JSON String to JsonNode
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(json);
        }catch (Exception e){
            logger.error("Failed to tranform a String to Json: " + e.getMessage());
            throw e;
        }

    }
}
