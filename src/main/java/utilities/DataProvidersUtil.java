package utilities;

import api.config.BookColumnIndex;
import api.factory.BookFactory;
import api.factory.HeaderFactory;
import api.testdatamodels.BookTestData;
import org.testng.annotations.DataProvider;

import java.util.List;

public class DataProvidersUtil {
    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/test/resources/testdata/TestData.xlsx";

    private static final String BOOK_FILE_PATH = System.getProperty("user.dir") + "/src/test/resources/testdata/API_Test cases.xlsx";
    @DataProvider(name = "validLoginData")
    public static Object[][] validLoginData(){
        return getSheetData("validLoginData");
    }

    @DataProvider(name = "invalidLoginData")
    public static Object[][] invalidLoginData(){
        return getSheetData("invalidLoginData");
    }

    @DataProvider(name = "emplVerification")
    public static Object[][] emplVerification(){
        return getSheetData("emplVerification");
    }

    @DataProvider(name = "bookLoginData")
    public static Object[][] bookLoginData(){
        return getSheetData("bookLoginData");
    }

    @DataProvider(name = "invalidProductData")
    public static Object[][] invalidProductData(){
        return getSheetData("invalidProductData");
    }

    @DataProvider(name = "bookCreationData")
    public static Object[][] bookCreationData(){

        List<String[]> sheetData = ExcelReaderUtility.getSheetData(BOOK_FILE_PATH, "TestData");

        Object[][] data = new Object[sheetData.size()][1];

        for(int i = 0; i < sheetData.size(); i++){
            String[] currentRow = sheetData.get(i);
            BookTestData bookTestData = new BookTestData(BookFactory.createBookFromRowData(currentRow), currentRow[BookColumnIndex.TC_ID], currentRow[BookColumnIndex.SCENARIO], currentRow[BookColumnIndex.JSON_SCHEMA], Integer.parseInt(currentRow[BookColumnIndex.EXPECTED_STATUS]), HeaderFactory.createHeaders(currentRow[BookColumnIndex.HEADERS]));

            data[i] = new Object[]{bookTestData};
        }
        return data;
    }

    @DataProvider(name = "invalidDataTypeBook")
    public Object[][] invalidDataTypeBook(){
        return new Object[][]{
                {"price"},
                {"categories"},
                {"pictures"},
                {"promotions"}
        };
    }

    private static Object[][] getSheetData(String sheetName){
        List<String[]> sheetData = ExcelReaderUtility.getSheetData(FILE_PATH,sheetName);
        Object[][] data = new Object[sheetData.size()][sheetData.get(0).length];
        for(int i = 0; i< sheetData.size(); i++){
            data[i] = sheetData.get(i);
        }
        return data;
    }

    @DataProvider(name = "validSortField")
    public Object[][] validSortFields(){
        return new Object[][]{
                {"name", "desc"},
                {"price", "asc"},
                {"viewCount", "asc"}
        };
    }

    @DataProvider(name = "invalidLimit")
    public Object[][] invalidLimit(){
        return new Object[][]{
                {"abc", "[limit:[Property 'limit' should be one of: 'numeric', 'number']]"},
                {-1, "[:[Expected number to be greater or equal to 1]]"},
                {0, "[:[Expected number to be greater or equal to 1]]"},
                {10001, "[:[Expected number to be less or equal to 10000]]"},
                {"", "[limit:[Property 'limit' should be one of: 'numeric', 'number']]"}
        };
    }

    @DataProvider(name = "invalidPage")
    public Object[][] invalidPage(){
        return new Object[][]{
                {"abc", "[page:[Property 'page' should be one of: 'numeric', 'number']]"},
                {-1, "[:[Expected number to be greater or equal to 1]]"},
                {0, "[:[Expected number to be greater or equal to 1]]"},
                {"", "[page:[Property 'page' should be one of: 'numeric', 'number']]"}
        };
    }

    @DataProvider(name = "injection")
    public static Object[][] injection() {
        return new Object[][]{
                {"' OR 1=1 --"},
                {"<script>alert(1)</script>"},
                {"DROP TABLE books"}
        };
    }
}
