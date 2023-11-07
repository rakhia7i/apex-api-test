package com.apex.api.test.util;

import com.apex.api.test.core.ApexHttpUtil;
import com.apex.api.test.core.User;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class POIExcelUtil {

    /**
     * This method reads the excel file and returns the list of users.
     * NOTE: This will only read .XLSX files and not .XLS files as we are using XSSF classes
     * We will need to use HSSF classes if we want to read '.xls' files
     *
     * @param xlsxFileName
     * @param sheetName
     * @param tableName
     * @return
     */
    public static String[][] getUsersFromExcel(String xlsxFileName, String sheetName, String tableName) {
        String[][] tabArray = null;
        try {
            // Note that we are reading the XLSX file that is why we are using XSSF classes (JXL Cannot read XLSX files)
            // To read XLS file, we need to use HSSF classes
            XSSFWorkbook workbook = new XSSFWorkbook(POIExcelUtil.class.getClassLoader().getResourceAsStream(xlsxFileName));
            XSSFSheet sheet = workbook.getSheet(sheetName);

            // These are hardcode values for the start and end column index as we know the structure of the excel sheet
            // TODO: Find a way to get the start and end column index dynamically
            int start_col_index = 0;
            int end_col_index = 6;

            int startRow, startCol, endRow, endCol, ci, cj;
            // Get the start row
            startRow = getStartRow(sheet, tableName, start_col_index);
            // Get the end row
            endRow = getEndRow(sheet, tableName, end_col_index);
            startCol = 0;
            endCol = 6;

            tabArray = new String[endRow - startRow - 1][endCol - startCol - 1];
            ci = 0;

            for (int i = startRow + 1; i < endRow; i++, ci++) {
                cj = 0;
                for (int j = startCol + 1; j < endCol; j++, cj++) {
                    // Checking cell type as First Column is numeric and the rest are String
                    if (sheet.getRow(i).getCell(j).getCellType() == CellType.NUMERIC) {
                        // Casting the value to int and then converting it to String to avoid decimal values
                        tabArray[ci][cj] = String.valueOf((int) sheet.getRow(i).getCell(j).getNumericCellValue());
                    } else {
                        tabArray[ci][cj] = sheet.getRow(i).getCell(j).getStringCellValue();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("error in getTableArray ()");
            e.printStackTrace();
        }
        return (tabArray);
    }
    public static String[][] getUsersFromInitializer(String xlsxFileName, String sheetName, String tableName){
        String[][] tabArray = null;
        try {
            // Note that we are reading the XLSX file that is why we are using XSSF classes (JXL Cannot read XLSX files)
            // To read XLS file, we need to use HSSF classes
            XSSFWorkbook workbook = new XSSFWorkbook(POIExcelUtil.class.getClassLoader().getResourceAsStream(xlsxFileName));
            XSSFSheet sheet = workbook.getSheet(sheetName);

            tabArray = new String[sheet.getLastRowNum()][1];
            for(int rowNo = 0; rowNo< sheet.getLastRowNum();   rowNo++) {
                tabArray[rowNo][0] = sheet.getRow(rowNo).getCell(0).getStringCellValue();
                System.out.println("reaading : "+ tabArray[rowNo][0]);
            }
        } catch (Exception e) {
            System.out.println("error in getTableArray ()");
            e.printStackTrace();
        }
        return (tabArray);
    }

    /**
     * This method returns the start row index of the table in the excel sheet based on the table name.
     * It returns -1 if no match found
     *
     * @param xssfSheet
     * @param tableName
     * @param startColIndex
     * @return
     */
    public static int getStartRow(XSSFSheet xssfSheet, String tableName, int startColIndex) {
        // Get the row iterator
        Iterator<Row> rowIterator = xssfSheet.rowIterator();

        // Iterate over rows and check if the first cell value matches the table name
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getCell(startColIndex).getStringCellValue().equalsIgnoreCase(tableName)) {
                return row.getRowNum();
            }
        }

        // If no match found, return -1
        return -1;
    }

    /**
     * This method returns the end row index of the table in the excel sheet based on the table name.
     * It returns -1 if no match found
     *
     * @param xssfSheet
     * @param tableName
     * @param endColIndex
     * @return
     */
    public static int getEndRow(XSSFSheet xssfSheet, String tableName, int endColIndex) {
        Iterator<Row> rowIterator = xssfSheet.rowIterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getCell(endColIndex) != null && row.getCell(endColIndex).getStringCellValue().equalsIgnoreCase(tableName)) {
                return row.getRowNum();
            }
        }

        return -1;
    }

    public static void updateUserIdInXLFile(User user) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(POIExcelUtil.class.getClassLoader().getResourceAsStream("ApexTestData.xlsx"));
        XSSFSheet sheet = workbook.getSheet("Sheet2");
        String tableName = "postValidIds";

       // byte[] strToBytes = mapper.writeValueAsString(user).getBytes();
        int startRow, startCol, endRow, endCol, ci, cj;
        int end_col_index = 0;

        // Get the start row
        startRow = getStartRow(sheet, tableName, 1);

        Row dataRow = sheet.getRow(startRow);
        Cell cell = dataRow.createCell(1);
        System.out.println("New user id : " + user.getId());
        //cell.setCellValue(user.getId());


//        Path path = Paths.get(FILE_PATH_USER_DOT_JSON);
//        byte[] strToBytes = mapper.writeValueAsString(user).getBytes();
//
//        Files.write(path, strToBytes);
//        System.out.println("User written to file: " + FILE_PATH_USER_DOT_JSON + " successfully");
    }


    /**
     * This method reads the excel file and returns the list of users (just for testing the logic)
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        //readExcel("/Users/A407668/Personal/code/apex-api-test/src/main/resources/TestData.xls");
       // String[][] users = getUsersFromExcel("ApexTestData.xlsx", "Sheet1", "invalidIds");
        //String[][] users = getUsersFromInitializer("TestData.xlsx", "Sheet1", "invalidIds");
        String[][] users = getUsersForPutFromExcel("ApexTestData.xlsx", "UsersForPut");
    }

    public static String[][] getUsersForPostFromExcel(String xlsxFile, String sheetName) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(POIExcelUtil.class.getClassLoader().getResourceAsStream(xlsxFile));
        XSSFSheet sheet = workbook.getSheet(sheetName);
        List<String[]> newUsers = new ArrayList<String[]>();
        User user = new User();
        Iterator<Row> itr = sheet.iterator();
        int i = 1;
        while(itr.hasNext()){
            if(i==1){  // skipping the header row
                i++;
                itr.next();
                continue;
            }
            Row row = itr.next();
            user = POIExcelUtil.createNewUserFromRow(row);
            newUsers.add(user.getFieldsAsArray());
        }
        return (String[][]) newUsers.toArray(new String[0][]);
    }

    public static User createNewUserFromRow(Row row) {
        if (row.getCell(0) == null)
            return new User(row.getCell(1).toString(), row.getCell(2).toString(), row.getCell(3).toString(), row.getCell(4).toString());
        else {
            DataFormatter formatter = new DataFormatter();
            String userId = formatter.formatCellValue(row.getCell(0));

            return new User(userId, row.getCell(1).toString(), row.getCell(2).toString(), row.getCell(3).toString(), row.getCell(4).toString());
        }
    }

    public static String[][] getUsersForPutFromExcel(String xlsxFile, String sheetName) throws IOException{
        XSSFWorkbook workbook = new XSSFWorkbook(POIExcelUtil.class.getClassLoader().getResourceAsStream(xlsxFile));
        XSSFSheet sheet = workbook.getSheet(sheetName);

        List<String[]> newUsers = new ArrayList<String[]>();
        User user = new User();
        Iterator<Row> itr = sheet.iterator();
        int i = 1;
        while(itr.hasNext()){
            if(i==1){  // skipping the header row
                i++;
                itr.next();
                continue;
            }
            Row row = itr.next();
            user = POIExcelUtil.createNewUserFromRow(row);
            newUsers.add(user.getFieldsAsArray());
        }
        System.out.println(user.getId() +" " +user.getName() + " " + user.getEmail() );
        return (String[][]) newUsers.toArray(new String[0][]);
    }

    public static String[][] getValidUserIdsFromExcel(String xlsxFile, String sheetName){
        String[][] tabArray = null;
        try {
            // Note that we are reading the XLSX file that is why we are using XSSF classes (JXL Cannot read XLSX files)
            // To read XLS file, we need to use HSSF classes
            XSSFWorkbook workbook = new XSSFWorkbook(POIExcelUtil.class.getClassLoader().getResourceAsStream(xlsxFile));
            XSSFSheet sheet = workbook.getSheet(sheetName);

            int totalNoOfRows = sheet.getPhysicalNumberOfRows();           // gets total no of rows
            int totalNoOfCols = sheet.getRow(0).getLastCellNum(); // gets total no of cols
            tabArray = new String[totalNoOfRows-1][totalNoOfCols];

            for(int rowNo = 0; rowNo < totalNoOfRows-1;   rowNo++) {
                DataFormatter formatter = new DataFormatter();
                String userId = formatter.formatCellValue(sheet.getRow(rowNo+1).getCell(0));

                tabArray[rowNo][0] = userId ;
            }
        } catch (Exception e) {
            System.out.println("error in getTableArray ()");
            e.printStackTrace();
        }
        return (tabArray);
    }


//    public loadingFileUsingClassLoader() {
//        ClassLoader classLoader = UserAPIGetTest.class.getClassLoader();
//
//        // Use getResourceAsStream to get an InputStream to the resource
//        InputStream inputStream = classLoader.getResourceAsStream(xlFilePath);
//    }
}