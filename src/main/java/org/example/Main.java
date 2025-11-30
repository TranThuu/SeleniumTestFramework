package org.example;


import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        WebDriver driver = new ChromeDriver();
        Alert alert = driver.switchTo().alert();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(3));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("")));

        Wait wai2 = new FluentWait(driver).withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofSeconds(600))
                .ignoring(Exception.class);

        Select select = new Select(driver.findElement(By.xpath("")));

        Actions actions = new Actions(driver);


    }

    public static void randomText(){
        String result = UUID.randomUUID().toString();
        System.out.println(result.replace("-","").substring(0,6));
        // Cach 2

        int reqLength = 6;
        String alphabet = "1234567890QWERTYUIOPASDFGHJKLZXCVBNM";
        String reqString = "";
        Random random = new Random();
        for(int i =0; i <6; i++){
            reqString += alphabet.charAt(random.nextInt(alphabet.length()));
        }
        System.out.println(reqString);
    }

    public static void openNewTap() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.navigate().to("https://google.com");
        Thread.sleep(2000);
        Actions actions = new Actions(driver);
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("window.open('https://demoqa.com/broken', '_blank')");
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("https://demoqa.com/broken");
        Thread.sleep(2000);
    }

    public static void readPropertiesFile(){
        Properties prop = new Properties();
        try(FileInputStream fis = new FileInputStream("src/main/resources/config.properties")){
            prop.load(fis);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Browser: " + prop.getProperty("browser"));
    }

    public static void checkBrokenLinks() throws IOException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demoqa.com/broken");


        List<WebElement> elements = driver.findElements(By.xpath("//a[@href] | //img[@src]"));
        System.out.println("Link size: " + elements.size());
        for(WebElement element:elements){
            String url = element.getAttribute("href");
            if(url == null){
                url = element.getAttribute("src");
            }

            URL url2 = new URL(url);
            HttpURLConnection httpConn = (HttpURLConnection) url2.openConnection();
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            int statusCode = httpConn.getResponseCode();
            if(statusCode >= 400){
                System.out.println("Broken link: " + url + ", Status code: " + statusCode);
            }
            httpConn.disconnect();
        }
    }
}

