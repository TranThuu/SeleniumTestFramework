package base;

import api.factory.HeaderFactory;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import utilities.ExtentManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class APIBaseTest {
    // Setup - Configuration - Common hooks

    protected RequestSpecification requestSpec;
    protected static Properties prop;

//    @BeforeSuite
//    public void loadConfig() throws IOException {
//        FileInputStream fs = new FileInputStream("src/main/resources/config.properties");
//        prop.load(fs);
//    }
    @BeforeClass
    public void setUp(){
        requestSpec = HeaderFactory.createHeaders("DEFAULT");
    }

}
