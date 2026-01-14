package com.orangehrm.utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelReaderUtility {
    public static List<String[]> getSheetData(String filePath, String sheetName){
        //Data variable is defined as a list of arrays of string
        List<String[]> data = new ArrayList<>();

        //
        try(FileInputStream fis = new FileInputStream(filePath);
            Workbook wb = new XSSFWorkbook(fis);){
            Sheet sheet = wb.getSheet(sheetName);
            if(sheet == null){
                throw new IllegalStateException("Sheet " + sheetName + " doesn't exists");
            }
            //Get total colum, row
            int totalCol = sheet.getRow(0).getLastCellNum(); //Return index + 1 (0-based) = list/array
            int totalRow = sheet.getLastRowNum(); //Return index (0-based)
            //Iterate through rows
            for(int r = 1; r <= totalRow; r++){//Skip the first row

                Row row = sheet.getRow(r); //If all the row blank -> it returns row = null
//                if(row.getRowNum() == 0){
//                    continue;
//                }
                //Read all cells in the row
                List<String> rowData = new ArrayList<>();
                for(int c = 0;  c < totalCol; c++){
//                for(Cell cell:row){ //Only loop through the cell that has data
                    if(row == null){
                        rowData.add("");
                        continue;
                    }
                    rowData.add(getCellValue(row.getCell(c)));
                }
                //convert rowData to Array
                data.add(rowData.toArray(new String[0]));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }
    private static String getCellValue(Cell cell){
        if(cell == null){
            return "";
        }
        switch (cell.getCellType()){
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if(DateUtil.isCellDateFormatted(cell)){
                    return cell.getDateCellValue().toString();
                }
                return String.valueOf((int)cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }
}
