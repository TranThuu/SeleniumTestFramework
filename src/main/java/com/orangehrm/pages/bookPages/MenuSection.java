package com.orangehrm.pages.bookPages;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;
import org.openqa.selenium.By;

public class MenuSection {
    ActionDriver actionDriver;
    private By dashboardLink = By.xpath("//a/span[contains(.,'Dashboard')]");
    private By userLink = By.xpath("//a/span[contains(.,'User')]");
    private By bookLink = By.xpath("//a/span[contains(.,'Book')]");
    private By promotionLink = By.xpath("//a/span[contains(.,'Promotion')]");
    private By fileLink = By.xpath("//a/span[contains(.,'fileLink')]");

    public MenuSection(){
        actionDriver = BaseClass.getActionDriver();
    }

    public void clickBookLink(){
        actionDriver.click(bookLink);
    }

    public void clickDashboardLink(){
        actionDriver.click(dashboardLink);
    }

    public void clickUserLink(){
        actionDriver.click(userLink);
    }

    public void clickPromotionLink(){
        actionDriver.click(promotionLink);
    }

    public void clickFileLink(){
        actionDriver.click(fileLink);
    }
}
