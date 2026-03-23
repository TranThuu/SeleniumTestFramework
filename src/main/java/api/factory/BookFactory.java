package api.factory;

import api.config.BookColumnIndex;
import api.config.ConfigManager;
import api.helper.ValueResolver;
import api.payloads.Book;
import org.json.JSONObject;

public class BookFactory {
    private static final String DEFAULT_NAME = ValueResolver.resolveString(ConfigManager.get("name_default"), true);
    private static final String[] DEFAULT_CATEGORIES = ValueResolver.resolveArray(ConfigManager.get("categories_default"));
    private static final Double DEFAULT_PRICE = ValueResolver.resolveDouble(ConfigManager.get("price_default"));
    private static final String DEFAULT_STATUS = ConfigManager.get("status_default");
    private static final String DEFAULT_DESCRIPTION = ConfigManager.get("description_default");
    private static final String[] DEFAULT_PICTURES = ValueResolver.resolveArray(ConfigManager.get("pictures_default"));
    private static final String[] DEFAULT_PROMOTIONS = ValueResolver.resolveArray(ConfigManager.get("promotions_default"));
    private static final String DEFAULT_SLUG = ValueResolver.resolveString(ConfigManager.get("slug_default"), true);
    public static Book createBookFromRowData(String[] rowData){
        String name = rowData[BookColumnIndex.NAME];
        String categories = rowData[BookColumnIndex.CATEGORIES];
        String price = rowData[BookColumnIndex.PRICE];
        String status = rowData[BookColumnIndex.STATUS];
        String description = rowData[BookColumnIndex.DESCRIPTION];
        String pictures = rowData[BookColumnIndex.PICTURES];
        String promotions = rowData[BookColumnIndex.PROMOTIONS];
        String slug = rowData[BookColumnIndex.SLUG];

            return new Book.BookBuilder()
                    .withName(ValueResolver.resolveString(name, true))
                    .withCategories(ValueResolver.resolveArray(categories))
                    .withPrice(ValueResolver.resolveDouble(price))
                    .withStatus(ValueResolver.resolveString(status, false))
                    .withDescription(ValueResolver.resolveString(description, false))
                    .withPictures(ValueResolver.resolveArray(pictures))
                    .withPromotions(ValueResolver.resolveArray(promotions))
                    .withSlug(ValueResolver.resolveString(slug, true))
                    .build();
    }

    public static Book createValidBook(){
        return new Book.BookBuilder()
                .withName(DEFAULT_NAME)
                .withCategories(DEFAULT_CATEGORIES)
                .withPrice(DEFAULT_PRICE)
                .withStatus(DEFAULT_STATUS)
                .withDescription(DEFAULT_DESCRIPTION)
                .withPictures(DEFAULT_PICTURES)
                .withPromotions(DEFAULT_PROMOTIONS)
                .withSlug(DEFAULT_SLUG)
                .build();
    }

    public static JSONObject createValidJSONBook(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", DEFAULT_NAME);
        jsonObject.put("categories", DEFAULT_CATEGORIES);
        jsonObject.put("price", DEFAULT_PRICE);
        jsonObject.put("description", DEFAULT_DESCRIPTION);
        jsonObject.put("status", DEFAULT_STATUS);
        jsonObject.put("pictures", DEFAULT_PICTURES);
        jsonObject.put("promotions", DEFAULT_PROMOTIONS);
        jsonObject.put("slug", DEFAULT_SLUG);
        return  jsonObject;
    }
}
