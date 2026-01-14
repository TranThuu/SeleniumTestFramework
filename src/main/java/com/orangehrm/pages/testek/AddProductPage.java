package com.orangehrm.pages.testek;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.enums.ProductFieldsEnum;
import com.orangehrm.utilities.enums.RequiredMessageProduct;
import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.By;
public class AddProductPage {
    private ActionDriver actionDriver;
    private By addProductLabel = By.xpath("//div[@class='header']/div");
    private By productCodeInput = By.id("form_item_code");
    private By productNameInput = By.id("form_item_name");
    private By productDescriptionInput = By.id("form_item_description");
    private By productPriceInput = By.id("form_item_price");
    private By productUnitInput = By.id("form_item_unit");
    private By productQuantityInput = By.id("form_item_quantity");
    private By productSupplierInput = By.id("form_item_supplier");
    private String productSupplierItem = "//div[contains(@class,'dropdown-menu-supplier')]//div[@title='%s']";
    private By productCategoryInput = By.id("form_item_category");
    private String productCategoryXpathItem = "//div[contains(@class,'dropdown-menu-category ')]//div[@title='%s']";
    private By addButton = By.xpath("//button[@testek='btn-add']");
    private By viewDetailButton = By.xpath("//button[@testek='btn-view-detail']");
    //Item detail
    private By jsonTextareaValue = By.xpath("//div[@testek='textarea']");
    private String requiredMessage = "//ancestor::div[@testek='%s']//div[@role='alert']";

    public AddProductPage(){
        actionDriver = BaseClass.getActionDriver();
    }

    //Add a new product
    public void enterDataIntoAllFields(String category, String supplier, String productCode, String productName, String productUnit, String description, String price, String productQuantity){
        if(!productCode.isEmpty()){ //Allow to enter spaces
            actionDriver.enterText(productCodeInput, productCode);
        }

        if(!productName.isEmpty()){
            actionDriver.enterText(productNameInput, productName);
        }

        if(!description.isEmpty()){
            actionDriver.enterText(productDescriptionInput, description);
        }

        if(!price.isEmpty()){
            actionDriver.enterText(productPriceInput, price);
        }

        if(!productQuantity.isEmpty()){
            actionDriver.enterText(productQuantityInput, productQuantity);
        }

        if(!productUnit.isEmpty()){
            actionDriver.enterText(productUnitInput, productUnit);
        }

        if(!category.isEmpty()){
            actionDriver.click(productCategoryInput);
            actionDriver.click(By.xpath(String.format(productCategoryXpathItem, category)));
        }

        if(!supplier.isEmpty()){
            actionDriver.click(productSupplierInput);
            actionDriver.click(By.xpath(String.format(productSupplierItem, supplier)));
        }
    }

    public void clickAddButton(){
        actionDriver.click(addButton);
    }

    //Verify that add product page with label "Thêm sản phẩm"
    public boolean isAddProductPage(){
        return actionDriver.compareText(addProductLabel, "Thêm sản phẩm");
    }

    public boolean isDetailProductCorrect(String category, String supplier, String productCode, String productName, String productUnit, String description, String price, String productQuantity) throws JsonProcessingException {
        //Get JSON result
        String jsonResult = actionDriver.getValueOfAttribute(jsonTextareaValue, "value");

        //Transform the JSON String to JsonNode
        JsonNode rootNode = actionDriver.stringToNodeJson(jsonResult);

        //Get JSON data
        String productCodeResult = rootNode.path("data").get("productCode").asText();
        String productNameResult = rootNode.path("data").get("productName").asText();
        String productDescResult = rootNode.path("data").get("productDesc").asText();
        String quantityResult = rootNode.path("data").get("quantity").asText();
        String categoryNameResult = rootNode.path("data").path("category").get("categoryName").asText();
        String supNameResult = rootNode.path("data").get("supplier").get("supName").asText();
        String unitResult = rootNode.path("data").get("unit").asText();
        String priceResult = rootNode.path("data").get("price").asText();

        return actionDriver.compareTwoString(category,categoryNameResult) && actionDriver.compareTwoString(supplier,supNameResult) && actionDriver.compareTwoString(productCodeResult,productCode) &&
                actionDriver.compareTwoString(productName, productNameResult) && actionDriver.compareTwoString(description,productDescResult) &&
                actionDriver.compareTwoString(productQuantity, quantityResult) && actionDriver.compareTwoString(productUnit, unitResult) && actionDriver.compareTwoString(price, priceResult);
    }

    public boolean isDuplicateMessageDisplay() throws JsonProcessingException {
        //Get JSON result
        String jsonResult = actionDriver.getValueOfAttribute(jsonTextareaValue, "value");

        //Transform the JSON String to JsonNode
        JsonNode rootNote = actionDriver.stringToNodeJson(jsonResult);

        //Get JSON data
        String errorMessage = rootNote.get("message").asText();

        return actionDriver.compareTwoString(errorMessage, "Product already exists {0}");
    }

    public boolean isRequiredMessageDisplayedCorrect(ProductFieldsEnum fieldName, RequiredMessageProduct message){
        if(fieldName.equals(ProductFieldsEnum.PRODUCT_CATEGORY)){
            return actionDriver.compareTwoString(actionDriver.getText(productCategoryInput, String.format(requiredMessage, "category")), message.toString());
        }

        if(fieldName.equals(ProductFieldsEnum.PRODUCT_CODE)){
            return actionDriver.compareTwoString(actionDriver.getText(productCategoryInput, String.format(requiredMessage, "code")), message.toString());
        }

        if(fieldName.equals(ProductFieldsEnum.PRODUCT_DESCRIPTION)){
            return actionDriver.compareTwoString(actionDriver.getText(productCategoryInput, String.format(requiredMessage, "description")), message.toString());
        }

        if(fieldName.equals(ProductFieldsEnum.PRODUCT_NAME)){
            return actionDriver.compareTwoString(actionDriver.getText(productCategoryInput, String.format(requiredMessage, "name")), message.toString());
        }

        if(fieldName.equals(ProductFieldsEnum.PRODUCT_PRICE)){
            return actionDriver.compareTwoString(actionDriver.getText(productCategoryInput, String.format(requiredMessage, "price")), message.toString());
        }

        if(fieldName.equals(ProductFieldsEnum.PRODUCT_QUANTITY)){
            return actionDriver.compareTwoString(actionDriver.getText(productCategoryInput, String.format(requiredMessage, "quantity")), message.toString());
        }

        if(fieldName.equals(ProductFieldsEnum.PRODUCT_SUPPLIER)){
            return actionDriver.compareTwoString(actionDriver.getText(productCategoryInput, String.format(requiredMessage, "supplier")), message.toString());
        }

        if(fieldName.equals(ProductFieldsEnum.PRODUCT_UNIT)){
            return actionDriver.compareTwoString(actionDriver.getText(productCategoryInput, String.format(requiredMessage, "unit")), message.toString());
        }
        return false;
    }
}
