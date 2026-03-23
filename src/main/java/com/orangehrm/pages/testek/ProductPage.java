package com.orangehrm.pages.testek;

import actiondriver.ActionDriver;
import base.BaseClass;
import org.openqa.selenium.By;

public class ProductPage {
    private ActionDriver actionDriver;

    private By addProductButton = By.xpath("//button[@testek='btn-add']");


    public ProductPage(){
        actionDriver = BaseClass.getActionDriver();
    }

    public boolean isProductPage(){
        return actionDriver.isDisplayed(addProductButton);
    }

    public void clickAddProductButton(){
        actionDriver.click(addProductButton);
    }
}
