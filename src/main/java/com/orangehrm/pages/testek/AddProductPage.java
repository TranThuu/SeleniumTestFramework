package com.orangehrm.pages.testek;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;
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

    public AddProductPage(){
        actionDriver = BaseClass.getActionDriver();
    }

    //Add a new product
    public void addAProduct(String category, String supplier, String productCode, String productName, String productUnit, String description, String price, String productQuantity){
        actionDriver.enterText(productCodeInput, productCode);
        actionDriver.enterText(productNameInput, productName);
        actionDriver.enterText(productDescriptionInput, description);
        actionDriver.enterText(productPriceInput, price);
        actionDriver.enterText(productQuantityInput, productQuantity);
        actionDriver.enterText(productUnitInput, productUnit);
        actionDriver.click(productCategoryInput);
        actionDriver.click(By.xpath(String.format(productCategoryXpathItem, category)));
        actionDriver.click(productSupplierInput);
        actionDriver.click(By.xpath(String.format(productSupplierItem, supplier)));
        actionDriver.click(addButton);
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
        System.out.println(jsonResult);
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
}
