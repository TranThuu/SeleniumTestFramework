package tests.testek;

import base.BaseClass;
import com.orangehrm.pages.testek.LoginPage;
import com.orangehrm.pages.testek.AddProductPage;
import com.orangehrm.pages.testek.NavbarComponent;
import com.orangehrm.pages.testek.ProductPage;
import utilities.DataProvidersUtil;
import utilities.ExtentManager;
import utilities.enums.ProductFieldsEnum;
import utilities.enums.RequiredMessageProduct;
import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Random;

public class CreateProductPageTest extends BaseClass {
    private LoginPage loginPage;
    private AddProductPage addProductPage;
    private NavbarComponent navbarComponent;
    private ProductPage productPage;

    @BeforeClass
    public void setupPage(){
        loginPage = new LoginPage();
        addProductPage = new AddProductPage();
        navbarComponent = new NavbarComponent();
        productPage = new ProductPage();
    }

    //Create a new product successfully
    @Test(priority = 0)
    public void VerifyCreateNewProductSuccessWhenDataIsValid() throws JsonProcessingException {
        //Data
        String category = "Item 1";
        String provider = "KIM CHI";
        String productCode = "TH005" + new Random().nextInt();
        String productName = "Sản phẩm 2";
        String unit = "Chiếc";
        String description = "This is our best seller product";
        String price = "30000";
        String quantity = "20";

        //Login to the system
        ExtentManager.logStep("Navigating to Login Page entering username and password");
        loginPage.login("admin_com_role", "aA12345678@");

        //Click to "San pham" tab
        ExtentManager.logStep("Open product tab");
        navbarComponent.openProductTab();

        //Click to "Them san pham" button
        ExtentManager.logStep("Click Add product button");
        productPage.clickAddProductButton();

        //Verify that this is the Add Product Page
        ExtentManager.logStep("Verify that is this the Add Product Page");
        addProductPage.isAddProductPage();

        //Input data & click Add product
        ExtentManager.logStep("Input data into all fields then click <Thêm>");
        addProductPage.enterDataIntoAllFields(category, provider, productCode, productName, unit, description, price, quantity);
        addProductPage.clickAddButton();

        //Compare product
        Assert.assertTrue(addProductPage.isDetailProductCorrect(category, provider, productCode, productName, unit, description, price, quantity));
    }

    //Verify that the product is created unsuccessfully with a duplicate code
    @Test(priority = 1)
    public void VerifyCreateNewProductFailsWhenCodeIsDuplicate() throws JsonProcessingException {
        //Data
        String category = "Item 1";
        String provider = "KIM CHI";
        String productCode = "TH001";
        String productName = "Sản phẩm 2";
        String unit = "Chiếc";
        String description = "This is our best seller product";
        String price = "30000";
        String quantity = "20";

        //Click to "San pham" tab
        ExtentManager.logStep("Open product tab");
        navbarComponent.openProductTab();

        //Click to "Them san pham" button
        ExtentManager.logStep("Click Add product button");
        productPage.clickAddProductButton();

        //Verify that this is the Add Product Page
        ExtentManager.logStep("Verify that is this the Add Product Page");
        addProductPage.isAddProductPage();

        //Input data & click Add product
        ExtentManager.logStep("Input data into all fields then click <Thêm>");
        addProductPage.enterDataIntoAllFields(category, provider, productCode, productName, unit, description, price, quantity);
        addProductPage.clickAddButton();

        //Compare product
        Assert.assertTrue(addProductPage.isDuplicateMessageDisplay());
    }

    //Validation

    @Test(dataProvider = "invalidProductData", dataProviderClass = DataProvidersUtil.class)
    public void VerifyRequiredMessageDisplayed_forAllRequiredFields_whenEmpty(String testCaseName, String category, String provider, String productCode, String productName, String unit, String description, String price, String quantity){
        SoftAssert softAssert = new SoftAssert();
        //Data
        if(!productCode.isBlank()){
            productCode += new Random().nextInt();
        }

        ExtentManager.logStep("Test Case: " + testCaseName);
        //Click to "San pham" tab
        ExtentManager.logStep("Open product tab");
        navbarComponent.openProductTab();

        //Click to "Them san pham" button
        ExtentManager.logStep("Click Add product button");
        productPage.clickAddProductButton();

        //Verify that this is the Add Product Page
        ExtentManager.logStep("Verify that is this the Add Product Page");
        addProductPage.isAddProductPage();

        //Input data & click Add product
        ExtentManager.logStep("Input data into all fields then click <Thêm>");
        addProductPage.enterDataIntoAllFields(category, provider, productCode, productName, unit, description, price, quantity);
        addProductPage.clickAddButton();

        //Verify required message
        if(category.isBlank()){
            softAssert.assertTrue(addProductPage.isRequiredMessageDisplayedCorrect(ProductFieldsEnum.PRODUCT_CATEGORY, RequiredMessageProduct.PRODUCT_CATEGORY));
        }
        if(provider.isBlank()){
            softAssert.assertTrue(addProductPage.isRequiredMessageDisplayedCorrect(ProductFieldsEnum.PRODUCT_SUPPLIER, RequiredMessageProduct.PRODUCT_SUPPLIER));
        }
        if(productCode.isBlank()){
            softAssert.assertTrue(addProductPage.isRequiredMessageDisplayedCorrect(ProductFieldsEnum.PRODUCT_CODE, RequiredMessageProduct.PRODUCT_CODE));
        }
        if(productName.isBlank()){
            softAssert.assertTrue(addProductPage.isRequiredMessageDisplayedCorrect(ProductFieldsEnum.PRODUCT_NAME, RequiredMessageProduct.PRODUCT_NAME));
        }
        if(unit.isBlank()){
            softAssert.assertTrue(addProductPage.isRequiredMessageDisplayedCorrect(ProductFieldsEnum.PRODUCT_UNIT, RequiredMessageProduct.PRODUCT_UNIT));
        }
        if(description.isBlank()){
            softAssert.assertTrue(addProductPage.isRequiredMessageDisplayedCorrect(ProductFieldsEnum.PRODUCT_DESCRIPTION, RequiredMessageProduct.PRODUCT_DESCRIPTION));
        }
        if(price.isBlank()){
            softAssert.assertTrue(addProductPage.isRequiredMessageDisplayedCorrect(ProductFieldsEnum.PRODUCT_PRICE, RequiredMessageProduct.PRODUCT_PRICE));
        }
        if(quantity.isBlank()){
            softAssert.assertTrue(addProductPage.isRequiredMessageDisplayedCorrect(ProductFieldsEnum.PRODUCT_QUANTITY, RequiredMessageProduct.PRODUCT_QUANTITY));
        }

        softAssert.assertAll();
    }

}
