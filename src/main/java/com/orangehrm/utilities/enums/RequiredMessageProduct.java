package com.orangehrm.utilities.enums;

public enum RequiredMessageProduct {
    PRODUCT_CATEGORY("Vui lòng chọn danh mục"),
    PRODUCT_SUPPLIER("Vui lòng chọn nhà cung cấp"),
    PRODUCT_CODE("Vui lòng nhập mã"),
    PRODUCT_NAME("Vui lòng nhập tên"),
    PRODUCT_UNIT("Vui lòng nhập đơn vị tính"),
    PRODUCT_DESCRIPTION("Vui lòng nhập mô tả"),
    PRODUCT_PRICE("Vui lòng nhập giá"),
    PRODUCT_QUANTITY("Vui lòng nhập số lượng");

    private final String messageContent;

    RequiredMessageProduct(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getProductCodeRequiredMessage(){
        return messageContent;
    }

    @Override
    public String toString(){
        return messageContent;
    }
}
