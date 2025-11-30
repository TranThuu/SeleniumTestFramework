package com.orangehrm.pages.bookPages;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;
import org.openqa.selenium.By;

public class CreateNewBookPage {
    private ActionDriver actionDriver;
    private By bookNameField = By.name("name");
    private By slugNameBookField= By.name("slug");
    private By slugNameBookChangeButton = By.xpath("//button[contains(., 'Change')]");
    private By descriptionField= By.xpath("//textarea[@name='description']");
    private By pictureField = By.name("picture");
    private By regularPriceField= By.name("price");
    private By categoriesInput = By.xpath("//input[contains(@role, 'combobox')]");
    private By categoryValueList = By.xpath("//ul[@role='listbox']/li");
    private By createBookButton = By.xpath("//button[contains(., 'Create')]");
    private By resetButton = By.xpath("//button[contains(., 'Reset')]");
    private By pageTitleLabel = By.tagName("h4");
    private By fieldErrorMessage = By.xpath("/parent::div/following-sibling::p");

    public CreateNewBookPage(){
        actionDriver = BaseClass.getActionDriver();
    }

    public boolean isCreateNewBookPageDisPlayed(String pageTitle){
        return actionDriver.compareText(pageTitleLabel,pageTitle);
    }

    public void setBookNameField(String bookName) {
        actionDriver.enterText(bookNameField, bookName);
    }

    public void setSlugNameBookField(String slugNameBook) {
        actionDriver.enterText(slugNameBookField, slugNameBook);
    }

    public void setDescriptionField(String description) {
        actionDriver.enterText(descriptionField, description);
    }

    public void setPictureField(String picture) {
        actionDriver.enterText(pictureField, picture);
    }

    public void setCategoriesField(String categoryName) {
        actionDriver.enterText(categoriesInput,categoryName);
        actionDriver.click(categoryValueList);
    }

    public void setRegularPriceField(String price){
        actionDriver.enterText(regularPriceField, price);
    }

    public void clickCreateBookButton(){
        actionDriver.click(createBookButton);
    }

    public void clickResetButton(){
        actionDriver.click(resetButton);
    }

    public void clickChangeButton(){
        actionDriver.click(slugNameBookChangeButton);
    }

    public void createBook(String bookName, String slugBook, String description, String picture, String price, String categoryName){
        setBookNameField(bookName);
        clickChangeButton();
        setSlugNameBookField(slugBook);
        setDescriptionField(description);
        setPictureField(picture);
        setRegularPriceField(price);
        setCategoriesField(categoryName);
        clickCreateBookButton();
    }
}
