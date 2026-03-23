package tests.API.BookManagement;

import api.client.BookClient;
import api.factory.BookFactory;
import api.helper.ValueResolver;
import api.payloads.Book;
import api.testdatamodels.BookTestData;
import base.APIBaseTest;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;
import utilities.DataProvidersUtil;
import utilities.ExtentManager;
import validations.ResponseValidator;

import static io.restassured.path.xml.XmlPath.from;

public class PostBookTest extends APIBaseTest {
    @Test(dataProvider = "bookCreationData", dataProviderClass = DataProvidersUtil.class)
    public void bookCreationTest(BookTestData bookTestData){

        ExtentManager.logStep(bookTestData.tcId+ "_" + bookTestData.scenario + "_" + "bookCreationTest");

        ExtentManager.logStep("Send post request");
        Response res = BookClient.createBook(bookTestData.book, bookTestData.headers);
        // get log
        res.then().log().all();

        ExtentManager.logStep("Validation");
        //Validate status
        ResponseValidator.validateStatusCode(res,bookTestData.expectedResult);
        //Validate Schema
        ResponseValidator.validateSchema(res, bookTestData.jsonSchema);
    }

    //TC_ID22
    @Test
    public void bookCreationTest_duplicateName(){
        Book originalBook = BookFactory.createValidBook();
        Book duplicateNameBook = originalBook.toBuilder()
                .withSlug(ValueResolver.resolveString("new slug", true))
                .build();

        // create the first book
        Response createResponse = BookClient.createBook(originalBook, requestSpec);
        ResponseValidator.validateStatusCode(createResponse, 200);

        //Send the request with duplicate name
        Response duplicateNameResponse = BookClient.createBook(duplicateNameBook, requestSpec);

        ResponseValidator.validateStatusCode(duplicateNameResponse, 400);
        ResponseValidator.validateSchema(duplicateNameResponse, "jsonSchema/CreateBook_msg.json");
        ResponseValidator.validateJsonValue(duplicateNameResponse, "msg", "Book name already exists.");
    }

    //TC_ID32
    @Test
    public void testCreateBook_ShouldFail_WhenDuplicateSlug(){
        Book originalBook = BookFactory.createValidBook();
        Response createResponse = BookClient.createBook(originalBook, requestSpec);
        ResponseValidator.validateStatusCode(createResponse, 200);

        Book duplicateSlugBook = originalBook.toBuilder()
                .withName(ValueResolver.resolveString(originalBook.getName(), true))
                .build();

        //Send the request with duplicate name
        Response duplicateSlugResponse = BookClient.createBook(duplicateSlugBook, requestSpec);
        //Verify response of duplicateName request
        ResponseValidator.validateStatusCode(duplicateSlugResponse, 400);
        ResponseValidator.validateSchema(duplicateSlugResponse, "jsonSchema/CreateBook_msg.json");
        ResponseValidator.validateJsonValue(duplicateSlugResponse, "msg", "Book name already exists.");
    }

    //TC_33: get default price
    @Test
    public void testCreateBook_ShouldFail_WhenSendPriceAsString(){
        JSONObject data = BookFactory.createValidJSONBook();
        data.put("price", "price as string");

        Response response = BookClient.createBook(data.toString(), requestSpec);

        response.then().log().all();
        ResponseValidator.validateStatusCode(response, 422);
//        ResponseValidator.validateSchema(response, "jsonSchema/CreateBook_msg.json");
        ResponseValidator.validateJsonValue(response, "msg", "Invalid data.");
    }

    //InvalidDataType TC_34, 35, 36, 37:
    @Test(dataProvider = "invalidDataTypeBook", dataProviderClass = DataProvidersUtil.class)
    public void testCreateBook_ShouldFail_WhenSendCategoriesAsString(String keyName){
        JSONObject data = BookFactory.createValidJSONBook();
        data.put(keyName, "value as string");

        Response response = BookClient.createBook(data.toString(), requestSpec);

        response.then().log().all();
        ResponseValidator.validateStatusCode(response, 422);
        ResponseValidator.validateSchema(response, "jsonSchema/CreateBook_invalidData.json");
        ResponseValidator.validateJsonValue(response, "msg", "Invalid data.");
        ResponseValidator.validateJsonValue(response, "fields." +keyName + "[0]", keyName.equalsIgnoreCase("price") ? "Expected number": "Expected array");
    }

}
