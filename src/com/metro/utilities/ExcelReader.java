package com.metro.utilities;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

    public static void ReadFromExcel(String filePath, String fileName, String sheetName) throws IOException {
        FileInputStream inputStream = new FileInputStream(filePath + "/" + fileName);
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheet(sheetName);
        int rowCount = sheet.getLastRowNum();
        System.out.println("Row Count : " + rowCount);
        for (int r = 0; r < rowCount + 1; r++) {
            Row row = sheet.getRow(r);
            for (int c = 0; c < row.getLastCellNum(); c++) {
                Cell cell = row.getCell(c);
                System.out.print(cell.getStringCellValue() + "\t");
            }
            System.out.println();
        }


    }

    public static void main(String[] args) throws IOException {
        String filePath = System.getProperty("user.dir") + "/src/com/metro/testdata";
        String fileName = "MetroValidLoginTestData.xlsx";
        String sheetName = "LoginDetails";
        ExcelReader.ReadFromExcel(filePath, fileName, sheetName);
    }

}
