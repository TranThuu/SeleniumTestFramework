package api.client;

import api.endpoints.Routes;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.devtools.v139.network.model.Request;

import static io.restassured.RestAssured.*;
import static io.restassured.path.xml.XmlPath.from;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class BookClient {
    public static Response createBook(Object body, RequestSpecification spec){
        return given()
                .spec(spec)
                .body(body)
                .log().all()

                .when()
                .post(Routes.Book.POST_CREATE);
    }

    public static Response getBook(String queryParams, RequestSpecification spec){
        return given()
                .spec(spec)

                .when()
                .get(Routes.Book.GET_All + queryParams);
    }
}
