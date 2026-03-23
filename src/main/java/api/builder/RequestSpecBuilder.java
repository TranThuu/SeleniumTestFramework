package api.builder;

import api.config.ConfigManager;
import api.endpoints.Routes;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecBuilder {
    public static RequestSpecification getDefaultSpec(){
        return new io.restassured.builder.RequestSpecBuilder()
                .setBaseUri(Routes.BASE_URL)
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + ConfigManager.get("token"))
                .build();
    }
}
