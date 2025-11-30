package com.orangehrm.test.book;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.bookPages.BookLoginPage;
import com.orangehrm.pages.bookPages.BookManagementPage;
import com.orangehrm.pages.bookPages.CreateNewBookPage;
import com.orangehrm.pages.bookPages.MenuSection;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CreateBookTest extends BaseClass {
    private BookLoginPage bookLogin;
    private CreateNewBookPage createNewBookPage;
    private MenuSection menuSection;
    private BookManagementPage bookManagement;

    @BeforeClass
    public void setupPage(){
        bookLogin = new BookLoginPage();
        createNewBookPage = new CreateNewBookPage();
        menuSection = new MenuSection();
        bookManagement = new BookManagementPage();
    }

    @Test
    public void createNewBook(){
        bookLogin.login("tranthu131200@gmail.com", "abcde12345-");
        menuSection.clickBookLink();
        bookManagement.clickNewBookButton();
        String description ="Nghìn lẻ một đêm\" là một bộ sưu tập truyện dân gian Ả Rập, Ba Tư và Ấn Độ, nổi tiếng với khung truyện về nàng Scheherazade kể chuyện cho vua Shahriyar, và cấu trúc \"chuyện lồng chuyện\" độc đáo. Bộ truyện này chứa đựng những câu chuyện ly kỳ, kỳ ảo với nhiều chủ đề đa dạng như phiêu lưu, thần thoại, tình yêu, và phản ánh văn hóa, trí tuệ, lòng nhân ái của con người. Các nhân vật và câu chuyện tiêu biểu bao gồm Aladdin, Alibaba và Sinbad. ";
        String image = "C:\\Users\\DELL\\Downloads\\nghinlemotdem.png";
        createNewBookPage.createBook("Nghìn lẻ một đem", "Nghin-le-mot-dem", description, image, "30000", "Tiểu Thuyết");
    }

    @Test
    public void openPromotionPage(){
        menuSection.clickPromotionLink();
    }
}
