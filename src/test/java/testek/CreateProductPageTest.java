package testek;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.testek.LoginPage;
import com.orangehrm.pages.testek.AddProductPage;
import com.orangehrm.pages.testek.NavbarComponent;
import com.orangehrm.pages.testek.ProductPage;
import com.orangehrm.utilities.ExtentManager;
import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

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

    @Test
    public void verifyValidCreateNewProduct() throws JsonProcessingException {
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
        addProductPage.addAProduct(category, provider, productCode, productName, unit, description, price, quantity);

        //Compare product
        addProductPage.isDetailProductCorrect(category, provider, productCode, productName, unit, description, price, quantity);

    }

}
