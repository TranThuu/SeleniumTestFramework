package tests.API.BookManagement;

import api.client.BookClient;
import api.endpoints.Routes;
import base.APIBaseTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;
import utilities.DataProvidersUtil;
import validations.ResponseValidator;

import java.util.Arrays;


public class GetBookTest extends APIBaseTest {
    //TC_40: Get book without query params
    @Test
    public void testGetBook_withoutQueryParam(){
        Response response = BookClient.getBook("", requestSpec);

        //Validate
        ResponseValidator.validateStatus200(response);
        ResponseValidator.validateSchema(response, "jsonSchema/GetBook_BookList.json");
        //Validate default limit, page
        ResponseValidator.validateJsonValue(response, "pagination.currentPage", "1");
        ResponseValidator.validateJsonValue(response, "pagination.lengthData", "10");
    }

    //TC_41: Get book with valid limit & page params
    @Test
    public void testGetBook_withLimitAndPage(){
        Response response = BookClient.getBook("?limit=11&page=3", requestSpec);

        //Validate
        ResponseValidator.validateStatus200(response);
        ResponseValidator.validateSchema(response, "jsonSchema/GetBook_BookList.json");
        ResponseValidator.validateJsonValue(response, "pagination.currentPage", "3");
        ResponseValidator.validateJsonValue(response, "pagination.lengthData", "11");
        ResponseValidator.validatePagination(response, 11);
    }

    //TC_42: Get book with valid search param
    @Test
    public void testGetBook_withValidSearch(){
        Response response = BookClient.getBook("?search=Dung bao gio di an mot minh", requestSpec);

        //Validate
        ResponseValidator.validateStatus200(response);
        ResponseValidator.validateSchema(response, "jsonSchema/GetBook_BookList.json");
        ResponseValidator.validateSearchResult(response, "Dung bao gio di an mot minh", "list", Arrays.asList("name", "description"));
    }

    //TC_43: Get book with full params
    @Test
    public void testGetBook_withFullParams(){
        Response response = BookClient.getBook("?limit=5&page=2&search=Dung bao gio di an mot minh&sort=name&sortBy=desc", requestSpec);

        //Validate
        ResponseValidator.validateStatus200(response);
        ResponseValidator.validateSchema(response, "jsonSchema/GetBook_BookList.json");
        ResponseValidator.validateSearchResult(response, "Dung bao gio di an mot minh", "list", Arrays.asList("name", "description"));
        ResponseValidator.validateSort(response, "name", "desc");
        ResponseValidator.validateJsonValue(response, "pagination.currentPage", "2");
        ResponseValidator.validateJsonValue(response, "pagination.lengthData", "5");
        ResponseValidator.validatePagination(response, 5);
    }

    //TC_44, 45: Get book with valid sort field & sort by
    @Test(dataProvider = "validSortField", dataProviderClass = DataProvidersUtil.class)
    public void testGetBook_withValidSortAndSortBy(String field, String sortBy){
        Response response = BookClient.getBook("?sort="+field+"&sortBy=" + sortBy, requestSpec);

        //Validate
        ResponseValidator.validateStatus200(response);
        ResponseValidator.validateSchema(response, "jsonSchema/GetBook_BookList.json");
        ResponseValidator.validateSort(response, field, sortBy);
        ResponseValidator.validateJsonValue(response, "pagination.currentPage", "1");
        ResponseValidator.validateJsonValue(response, "pagination.lengthData", "10");
        ResponseValidator.validatePagination(response, 10);
    }

    //TC_46: Get book with invalid limit value
    @Test(dataProvider = "invalidLimit", dataProviderClass = DataProvidersUtil.class)
    public void testGetBook_withInvalidLimit(Object limit, String msg){
        Response response = BookClient.getBook("?limit="+limit, requestSpec);

        //Validate
        ResponseValidator.validateStatusCode(response, 422);
        ResponseValidator.validateJsonValue(response, "msg", "Invalid data.");
        ResponseValidator.validateJsonValue(response,  "fields.", msg);
    }

    //TC_47: Get book with invalid limit value
    @Test(dataProvider = "invalidPage", dataProviderClass = DataProvidersUtil.class)
    public void testGetBook_withInvalidPage(Object page, String msg){
        Response response = BookClient.getBook("?page="+page, requestSpec);

        //Validate
        ResponseValidator.validateStatusCode(response, 422);
        ResponseValidator.validateJsonValue(response, "msg", "Invalid data.");
        ResponseValidator.validateJsonValue(response,  "fields.", msg);
    }

    //TC_48: Get book with invalid sort
    @Test
    public void testGetBook_withInvalidSort(){
        Response response = BookClient.getBook("?sort=invalidSort", requestSpec);

        //Validate
        ResponseValidator.validateStatusCode(response, 422);
        ResponseValidator.validateJsonValue(response, "msg", "Invalid data.");
        ResponseValidator.validateJsonValue(response,  "fields.sort[0]", "Expected kind 'UnionEnum'" );
    }

    //TC_49: Get book with invalid sort
    @Test
    public void testGetBook_withInvalidSortBy(){
        Response response = BookClient.getBook("?sortBy=invalidSort", requestSpec);

        //Validate
        ResponseValidator.validateStatusCode(response, 422);
        ResponseValidator.validateJsonValue(response, "msg", "Invalid data.");
        ResponseValidator.validateJsonValue(response,  "fields.sortBy[0]", "Expected kind 'UnionEnum'" );
    }

    //TC_50: Get book with empty search
    @Test
    public void testGetBook_withEmptySearch(){
        Response response = BookClient.getBook("?search=", requestSpec);

        //Validate
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateSchema(response, "jsonSchema/GetBook_BookList.json");
    }

    @Test
    public void testGetBook_SearchNotFound(){
        Response response = BookClient.getBook("?search=not exist 977242", requestSpec);

        //Validate
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateEmptyList(response, "list");
    }

    //TC_53: Get book without Authentication header
    @Test
    public void testGetBooks_NoAuth() {
        RequestSpecification noAuth = new RequestSpecBuilder().setBaseUri(Routes.BASE_URL).build();

        Response response = BookClient.getBook("", noAuth);

        response.then().statusCode(200);
    }

    // TC_56: injection
    @Test(dataProvider = "injection", dataProviderClass = DataProvidersUtil.class)
    public void testGetBooks_Injection(String payload) {
        Response response = BookClient.getBook("?search=" + payload, requestSpec);

        ResponseValidator.validateStatus200(response);
    }
}
