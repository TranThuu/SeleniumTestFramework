package com.orangehrm.base;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.utilities.ExtentManager;
import com.orangehrm.utilities.LoggerManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BaseClass {
    protected static Properties prop;
    //private static ActionDriver actionDriver;
    //protected static WebDriver driver;

    public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static ThreadLocal<ActionDriver> actionDriver = new ThreadLocal<>();
    //ThreadLocal.withInitial() mỗi thread khi chạy code này sẽ có 1 instance SoftAssert riêng biệt, không bị chia sẻ voi thread khác
    //:: = ()-> new SoftAssert()
    protected static ThreadLocal<SoftAssert> softAssert = ThreadLocal.withInitial(SoftAssert::new);
    public static final Logger logger = LoggerManager.getLogger(BaseClass.class);

    @BeforeSuite
    public void loadConfig() throws IOException {
        //load config.properties file
        prop = new Properties();
        FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
        prop.load(fis);
        logger.info("config.properties file loaded");

        //Start the ExtentReport
        //ExtentManager.getReporter(); -- This has been implemented in TestListener
    }

    @BeforeMethod
    public synchronized void setup() throws IOException {
        System.out.println("Setting up WebDriver for:" + this.getClass().getSimpleName());
        launchBrowser();
        configBrowser();
        logger.info("WenDriver Initialized and Browser Maximized");
        logger.trace("Trace message");
        logger.error("error message");
        logger.debug("debug message");
        logger.fatal("fatal message");
        logger.warn("warn message");
        //Initialize ActionDriver
//        if(actionDriver == null){
//            actionDriver = new ActionDriver(driver);
//            logger.info("ActionDriver instance is created "+ Thread.currentThread().getId());
//        }
        //Initialize ActionDriver for the current Thread
        if(actionDriver.get() == null){
            actionDriver.set(new ActionDriver(getDriver()));
        }
        logger.info("ActionDriver initialized for thread "+ Thread.currentThread().getId());
    }


    //Initialize the WebDriver based on browser defined in the config.properties file
    private  void launchBrowser(){
        String browser = prop.getProperty("browser");
        switch (browser.toLowerCase()){
            case "chrome":
                //Create ChromeOptions
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless=new"); //Run chrome in headless mode
                chromeOptions.addArguments("--disable-gpu"); //Disable GPU for headless mode
                chromeOptions.addArguments("--window-size=1920,1080"); //Set window size
                chromeOptions.addArguments("--disable-notifications"); //Disable browser notification
                chromeOptions.addArguments("--no-sandbox"); //Required for some CI environments
                chromeOptions.addArguments("--disable-dev-shm-usage"); //Resolve issues in resource

//                driver = new ChromeDriver();
                driver.set(new ChromeDriver(chromeOptions));
                ExtentManager.registerDriver(getDriver());
                logger.info("ChromeDriver instance is created");
                break;
            case "firefox":
                //Create FirefoxOptions
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--headless"); //Run chrome in headless mode
                firefoxOptions.addArguments("--disable-gpu"); //Disable GPU for headless mode
                firefoxOptions.addArguments("--width=1920"); //Set browser width
                firefoxOptions.addArguments("--height=1080"); //Set browser height
                firefoxOptions.addArguments("--disable-notifications"); //Disable browser notification
                firefoxOptions.addArguments("--no-sandbox"); //Required for some CI environments
                firefoxOptions.addArguments("--disable-dev-shm-usage"); //Resolve issues in resource

//                driver = new FirefoxDriver();
                driver.set(new FirefoxDriver(firefoxOptions));
                ExtentManager.registerDriver(getDriver());
                logger.info("FirefoxDriver instance is created");
                break;
            case "edge":
                //Create FirefoxOptions
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--headless"); //Run chrome in headless mode
                edgeOptions.addArguments("--disable-gpu"); //Disable GPU for headless mode
                edgeOptions.addArguments("--window-size=1920,1080"); //Set window size
                edgeOptions.addArguments("--disable-notifications"); //Disable browser notification
                edgeOptions.addArguments("--no-sandbox"); //Required for some CI environments
                edgeOptions.addArguments("--disable-dev-shm-usage"); //Resolve issues in resource

//                driver = new EdgeDriver();
                driver.set(new EdgeDriver(edgeOptions));
                ExtentManager.registerDriver(getDriver());
                logger.info("EdgeDriver instance is created");
                break;
            default:
                throw new IllegalArgumentException("Browser not supported: "+ browser);
        }
    }

    //Configure browser settings
    private void configBrowser(){
        //Implicit Wait
        int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

        //Maximize the browser
        getDriver().manage().window().maximize();
        getDriver().manage().window().setSize(new Dimension(1920,1080));

        //Navigate to URL
        try{
            getDriver().get(prop.getProperty("url"));
        }catch (Exception e){
            System.out.println("Failed to Navigate to the URL: " + e.getMessage());
        }

    }

    @AfterMethod
    public synchronized void tearDown(){
        if(getDriver()!=null){
            try{
                getDriver().quit();
            }catch (Exception e){
                System.out.println("Unable to quite the driver: " + e.getMessage());
            }
        }
        driver.remove();
        actionDriver.remove();
        logger.info("WebDriver instance is quited");
//        actionDriver = null;
        //ExtentManager.endTest();-- This has been implemented in TestListener
    }


    //Getter method for WebDriver
    public static WebDriver getDriver(){
        if(driver.get() == null){
            System.out.println("WebDriver is not initialized");
            throw new IllegalStateException("Driver is not initialized");
        }
        return driver.get();
    }

    //Getter method for ActionDriver
    public static ActionDriver getActionDriver(){
        if(actionDriver.get() == null){
            System.out.println("ActionDriver is not initialized");
            throw new IllegalStateException("ActionDriver is not initialized");
        }
        return actionDriver.get();
    }

//    Driver setter method
    public void setDriver(ThreadLocal<WebDriver> driver){
        this.driver = driver;
    }

    //Prop Getter method
    public static Properties getProp() {
        return prop;
    }

    public SoftAssert getSoftAssert(){
        return softAssert.get();
    }
}
