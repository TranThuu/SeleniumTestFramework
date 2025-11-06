package com.orangehrm.pages;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PimPage {
    private ActionDriver actionDriver;
    private By empNameSearch = By.xpath("//label[text()='Employee Name']/parent::div/following-sibling::div//input");
    private By searchButton = By.xpath("//button[@type='submit']");
    private By empFirstAndMiddleName = By.xpath("//div[@class='oxd-table-body']/div[1]/div/div[3]");
    private By empLastName = By.xpath("//div[@class='oxd-table-body']/div[1]/div/div[4]");


    public PimPage(WebDriver driver){
        this.actionDriver = BaseClass.getActionDriver();
    }

    //Employee Search
    public void empNameSearch(String value){
        actionDriver.enterText(empNameSearch,value);
        actionDriver.click(searchButton);
        actionDriver.scrollToElement(empFirstAndMiddleName);
    }

    //Verify employee first and middle name
    public boolean verifyFirstAndMiddleName(String empFirstAndMiddleNameFromDB){
        return actionDriver.compareText(empFirstAndMiddleName,empFirstAndMiddleNameFromDB);
    }
    //Verify employee last name
    public boolean verifyLastName(String empFLastNameFromDB){
        return actionDriver.compareText(empLastName,empFLastNameFromDB);
    }

}
