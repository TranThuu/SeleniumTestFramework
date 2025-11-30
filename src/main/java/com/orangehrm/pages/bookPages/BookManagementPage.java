package com.orangehrm.pages.bookPages;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;
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
