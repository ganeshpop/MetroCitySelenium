package com.metro.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

    public static Object[][] readCredentials(String filePath, String fileName, String sheetName) {
        File excellFile = new File(filePath + "/" + fileName);
        Object[][] loginCredentials = new Object[0][0];
        try {
            FileInputStream excellInputStream = new FileInputStream(excellFile);
            Workbook workbook = new XSSFWorkbook(excellInputStream);
            Sheet dataSheet = workbook.getSheet(sheetName);
            int end = dataSheet.getLastRowNum();
            loginCredentials = new Object[end][2];
            for (int i = 0; i < end ; i++) {
                loginCredentials[i][0] = dataSheet.getRow(i + 1).getCell(0).getStringCellValue();
                loginCredentials[i][1] = dataSheet.getRow(i + 1).getCell(1).getStringCellValue();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return loginCredentials;
    }

    public static Object[][] readRechargeDetails(String filePath, String fileName, String sheetName) {
        File excellFile = new File(filePath + "/" + fileName);
        Object[][] loginCredentials = new Object[0][0];
        try {
            FileInputStream excellInputStream = new FileInputStream(excellFile);
            Workbook workbook = new XSSFWorkbook(excellInputStream);
            Sheet dataSheet = workbook.getSheet(sheetName);
            int end = dataSheet.getLastRowNum();
            loginCredentials = new Object[end][3];
            for (int i = 0; i < end ; i++) {
                loginCredentials[i][0] = dataSheet.getRow(i + 1).getCell(0).getStringCellValue();
                loginCredentials[i][1] = dataSheet.getRow(i + 1).getCell(1).getStringCellValue();
                loginCredentials[i][2] = dataSheet.getRow(i + 1).getCell(2).getStringCellValue();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return loginCredentials;
    }

}
