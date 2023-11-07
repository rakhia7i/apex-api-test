package com.apex.api.test.util;

import com.apex.api.test.core.ApexHttpUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class TestDataInitializer {
    public static void initializeDataFromGoRestAPI(String xlsxFileName, String sheetName) {
        ObjectMapper mapper = new ObjectMapper();
        try {

            HttpResponse response = ApexHttpUtil.sendAndReceiveGetMessages("");

            String strResponse = EntityUtils.toString(response.getEntity());
            JsonNode rootNode = mapper.readTree(strResponse);
            if(rootNode.isArray()){ // Get ALL users returns an array of users
                FileOutputStream fileOut = new FileOutputStream("src/main/resources/TestData.xlsx");
                XSSFWorkbook wb = new XSSFWorkbook();
                XSSFSheet sheet = wb.createSheet("Sheet1");
                int rowNum = 0;
                XSSFRow xssfRow;
                for(JsonNode userNode: rootNode) {
                    //System.out.println(userNode.get("id"));
                    // write validIds to XL filE

                    xssfRow = sheet.createRow(rowNum++);

                    xssfRow.createCell(0).setCellValue(userNode.get("id").toString());

                }
                wb.write(fileOut);
                fileOut.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

   public static void main(String[] args) {
       initializeDataFromGoRestAPI("ApexTestData.xlsx","Sheet1");
   }
}
