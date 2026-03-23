package validations;

import io.restassured.response.Response;
import org.testng.Assert;

import java.text.Collator;
import java.util.*;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ResponseValidator {
    public static void validateStatusCode(Response response, int expectedStatusCode){
        response.then().statusCode(expectedStatusCode);
    }

    public static void validateStatus200(Response response){
        response.then().statusCode(200);
    }

    public static void validateSchema(Response response, String schema){
        if(schema != null && !schema.trim().isEmpty())
            response.then().assertThat().body(matchesJsonSchemaInClasspath(schema));
    }

    public static void validateJsonValue(Response response, String jsonPathKey, String expectedValue){
        Assert.assertEquals(response.jsonPath().getString(jsonPathKey), expectedValue);
        response.jsonPath().getString(jsonPathKey).equals(expectedValue);
    }

    public static void validatePagination(Response response, int limit){
        List<?> list = response.jsonPath().getList("list");
        if(list == null)
            Assert.assertTrue(false);
        Assert.assertEquals(list.size(), limit);
    }

    public static void validateSearchResult(Response response, String searchKey, String searchPathKeys, List<String> jsonSearchKeys){
        List<HashMap<String, String>> list = response.jsonPath().getList(searchPathKeys);
        if(list == null)
            Assert.assertTrue(false);

        for(HashMap<String, String> item : list){
            int differenceNumber = 0;
            for(int i =0; i < jsonSearchKeys.size(); i++){
                if(!item.get(jsonSearchKeys.get(i)).toLowerCase().contains(searchKey.toLowerCase())){
                    differenceNumber++;
                }
            }
            if(differenceNumber == jsonSearchKeys.size())
                Assert.assertTrue(false);
        }
        Assert.assertTrue(true);
    }

    public static void validateSort(Response response, String sortField, String sortBy){
        List<?> values = response.jsonPath().getList("list." + sortField);
        List<Object> sorted = new ArrayList<>(values);
        System.out.println(values);
        if(values.get(0) instanceof String){
            Collator collator = Collator.getInstance();
            sorted.sort((o1, o2) ->{
                int result = collator.compare((String) o1, (String) o2);
                return sortBy.equalsIgnoreCase("asc") ? result : -result;
            });
        }else if (values.get(0) instanceof Number){
            sorted.sort((o1, o2) -> {
                Double d1 = ((Number) o1).doubleValue();
                Double d2 = ((Number) o2).doubleValue();
                int result = d1.compareTo(d2);
                return sortBy.equalsIgnoreCase("asc") ? result : -result;
            });
        } else {
            throw new UnsupportedOperationException(
                    "Unsupported data type for sorting: " + values.get(0).getClass()
            );
        }
        System.out.println(sorted);
        Assert.assertEquals(values, sorted, "sorting is incorrect for field: " + sortField);
    }

    public static void validateEmptyList(Response response, String path){
        List<?> list = response.jsonPath().getList(path);
        Assert.assertTrue(list.isEmpty(), "List is not empty");
    }
}
