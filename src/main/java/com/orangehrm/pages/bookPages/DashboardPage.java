package com.orangehrm.pages.bookPages;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;
import org.openqa.selenium.By;

public class DashboardPage {
    private ActionDriver actionDriver;
    private By welcomeLabel = By.xpath("(//h6)[2]");
    public DashboardPage(){
        actionDriver = BaseClass.getActionDriver();
    }

    public boolean isDashboardPage(){
        return actionDriver.compareText(welcomeLabel, "Welcome Thu");
    }
}
