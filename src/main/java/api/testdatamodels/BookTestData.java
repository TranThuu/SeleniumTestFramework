package api.testdatamodels;

import api.payloads.Book;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class BookTestData {
    public Book book;
    public String tcId;
    public String scenario;
    public String jsonSchema;
    public int expectedResult;

    public RequestSpecification headers;

    public BookTestData(Book book, String tcId, String scenario, String jsonSchema, int expectedResult, RequestSpecification headers){
        this.book = book;
        this.tcId = tcId;
        this.jsonSchema = jsonSchema;
        this.expectedResult = expectedResult;
        this.headers = headers;
        this.scenario = scenario;
    }
}
