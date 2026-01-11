package com.orangehrm.pages.testek;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;
import org.openqa.selenium.By;

public class NavbarComponent {
    private ActionDriver actionDriver;
    private By productTabButton = By.xpath("//div[@testek='the-navbar']//div[text()='Sản phẩm']");

    public NavbarComponent(){
        actionDriver = BaseClass.getActionDriver();
    }
    //Open Product Tab
    public void openProductTab(){
        actionDriver.click(productTabButton);
    }
}
