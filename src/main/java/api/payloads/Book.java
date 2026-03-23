package api.payloads;

import com.fasterxml.jackson.annotation.JsonInclude;

// If properties is null, exclude from JSON
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book {
    private final String name;
    private final String[] categories;
    private final Double price;
    private final String status;
    private final String description;
    private final String[] pictures;
    private final String[] promotions;
    private final String slug;

    private  Book(String name, String[] categories, Double price, String status, String description, String[] pictures, String[] promotions, String slug){
        this.name = name;
        this.categories = categories;
        this.price = price;
        this.status = status;
        this.description = description;
        this.pictures = pictures;
        this.promotions = promotions;
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public String[] getCategories() {
        return categories;
    }

    public Double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public String[] getPictures() {
        return pictures;
    }

    public String[] getPromotions() {
        return promotions;
    }

    public String getSlug() {
        return slug;
    }

    public BookBuilder toBuilder(){
        return new BookBuilder()
                .withName(name)
                .withDescription(description)
                .withCategories(categories)
                .withPictures(pictures)
                .withPrice(price)
                .withPromotions(promotions)
                .withSlug(slug)
                .withStatus(status);
    }
    public static class BookBuilder{
        private String name;
        private String[] categories;
        private Double price;
        private String status;
        private String description;
        private String[] pictures;
        private String[] promotions;
        private String slug;

        public BookBuilder(){

        }
        public BookBuilder withName(String name){
            this.name = name;
            return this;
        }
        public BookBuilder withCategories(String[] categories){
            this.categories = categories;
            return this;
        }
        public BookBuilder withPrice(Double price){
            this.price = price;
            return this;
        }
        public BookBuilder withStatus(String status){
            this.status = status;
            return this;
        }
        public BookBuilder withDescription(String description){
            this.description = description;
            return this;
        }
        public BookBuilder withPictures(String[] pictures){
            this.pictures = pictures;
            return this;
        }
        public BookBuilder withPromotions(String[] promotions){
            this.promotions = promotions;
            return this;
        }
        public BookBuilder withSlug(String slug){
            this.slug = slug;
            return this;
        }
        public Book build(){
            return new Book(this.name, this.categories, this.price, this.status, this.description, this.pictures, this.promotions, this.slug);
        }
    }
}
