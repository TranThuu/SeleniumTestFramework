package api.endpoints;

import api.config.ConfigManager;

public class Routes {
    public static final String BASE_URL = ConfigManager.get("base.url");

    //Book Management
    public static class Book{
        public static final String POST_CREATE = "/api/book";
        public static final String GET_BYID = "/api/book/{id}";
        public static final String GET_All = "/api/book";
        public static final String PATCH_BYID = "/api/book/{id}";
        public static final String DELETE_BYID = "/api/book/{id}";
    }
}
