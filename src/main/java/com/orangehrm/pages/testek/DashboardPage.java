package com.orangehrm.pages.testek;

import actiondriver.ActionDriver;
import base.BaseClass;
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
