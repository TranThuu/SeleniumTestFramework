package com.orangehrm.pages.bookPages;

import actiondriver.ActionDriver;
import base.BaseClass;
import org.openqa.selenium.By;

public class BookManagementPage {
    private ActionDriver actionDriver;

    private By newBookButton = By.xpath("//a[contains(., 'New book')]");

    public BookManagementPage(){
        actionDriver = BaseClass.getActionDriver();
    }

    public void clickNewBookButton(){
        actionDriver.click(newBookButton);
    }
}
