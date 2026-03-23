package api.factory;

import api.config.ConfigManager;
import api.endpoints.Routes;
import api.helper.ValueResolver;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class HeaderFactory {
    //Pass "DEFAULT" if want a default Request specification
    public static RequestSpecification createHeaders(String headers){
        if("DEFAULT".equalsIgnoreCase(headers)){
            return new RequestSpecBuilder()
                    .setBaseUri(Routes.BASE_URL)
                    .setContentType(ContentType.JSON)
                    .addHeader("Authorization", "Bearer " + ConfigManager.get("token"))
                    .build();
        }

        return  new RequestSpecBuilder()
                .setBaseUri(Routes.BASE_URL)
                .addHeaders(ValueResolver.resolveHeader(headers)).build();
    }
}
