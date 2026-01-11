package com.orangehrm.pages.testek;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;
import org.openqa.selenium.By;
public class DashboardPage {
    private ActionDriver actionDriver;

    private By welcomeLabel = By.xpath("//h2/b");

    public DashboardPage(){
        this.actionDriver = BaseClass.getActionDriver();
    }

    public boolean isDashboardPage(){
        return  actionDriver.compareText(welcomeLabel, "TESTEK - KIỂM THỬ THỰC CHIẾN");
    }


}
