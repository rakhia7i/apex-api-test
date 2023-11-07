package com.apexAssignments.test;

import com.apex.api.test.core.User;
import com.apex.api.test.util.JXLExcelUtil;
import com.apex.api.test.util.POIExcelUtil;
import com.apex.api.test.util.TestDataInitializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import com.apex.api.test.core.ApexHTTPConstants;
import com.apex.api.test.core.ApexHttpUtil;
import com.apex.api.test.core.ApexApiBaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;


public class UserApiGetTest extends ApexApiBaseTest {

    //-------------- ValidIds------------//
//    @DataProvider(name = "dp_validIds")
//    public Object[][] getValidUsersUsingJXL() {
//        String[][] ids = JXLExcelUtil.getUsersFromExcel("ApexTestData.xls", "Sheet1", "validIds");
//        return ids;
//    }

//    @BeforeClass
//    private void init(){
//        System.out.println("before class running");
//        TestDataInitializer.initializeDataFromGoRestAPI("TestData.xlsx", "Sheet1");
//    }
    @DataProvider(name = "dp_validIds")
    public Object[][] getValidUsersUsingPOI()  {
        //TestDataInitializer.initializeDataFromGoRestAPI("TestData.xlsx", "Sheet1", "validIds");
        //String[][] ids = POIExcelUtil.getUsersFromInitializer("TestData.xlsx", "Sheet1", "validIds");
        String[][] ids = POIExcelUtil.getValidUserIdsFromExcel("ApexTestData.xlsx", "ValidIdsForGet");
        //System.out.println(Entity.getEntity(ids));
        return ids;
    }

    @Test(dataProvider = "dp_validIds", priority = 1)
    public void whenGetWithValidUserIDs_thenStatus200(String id) throws Exception {
        //User user = new User(id, name, email, gender, status);

        HttpResponse res = ApexHttpUtil.sendAndReceiveGetMessages(id);
        String responseString = ApexHttpUtil.getResponseString(res);

        //create object from responseString
        ObjectMapper mapper = new ObjectMapper();
        JsonNode resUser = mapper.readTree(responseString);


        Assert.assertEquals(res.getStatusLine().getStatusCode(), ApexHTTPConstants.HTTP_STATUS_OK);
        Assert.assertEquals(res.getStatusLine().getReasonPhrase(), ApexHTTPConstants.HTTP_STATUS_OK200_MSG);

        // match responseString id to the id from excel file
        Assert.assertEquals(resUser.get("id").asText(), id);
        //Assert.assertTrue(responseString.contains(user.getName()));

        // Can also be validated using JSonNode (XML reading)
        JsonNode node = mapper.readTree(responseString);
        String userName = node.get("name").asText();
    }


    //-------------- InvalidIds------------//
    @DataProvider(name = "dp_invalidIds")
    public Object[][] getInvalidUsers() {
        String[][] ids = JXLExcelUtil.getUsersFromExcel("ApexTestData.xls", "Sheet1", "invalidIds");
        return ids;
    }

    @Test(dataProvider = "dp_invalidIds", priority = 2)
    public void whenGetWithInvalidUserID_thenStatus404(String id, String name, String email, String gender, String status) throws Exception {
        User user = new User(id, name, email, gender, status);

        HttpResponse res = ApexHttpUtil.sendAndReceiveGetMessages(user.getId());
        Assert.assertEquals(res.getStatusLine().getStatusCode(), ApexHTTPConstants.HTTP_STATUS_NOT_FOUND);
        Assert.assertEquals(res.getStatusLine().getReasonPhrase(), ApexHTTPConstants.HTTP_STATUS_NOT_FOUND_404_MSG);
    }

    //--------------//
    @DataProvider(name = "dp_nonIntegerIds")
    public Object[][] getActiveUsers() {
        String[][] ids = JXLExcelUtil.getUsersFromExcel("ApexTestData.xls", "Sheet1", "invalidIds");
        return ids;
    }

    @Test(priority = 3)
    public void whenGetWithNonIntegerUserId_thenStatus404() throws Exception {
        HttpResponse res = ApexHttpUtil.sendAndReceiveGetMessages("abcd");
        Assert.assertEquals(res.getStatusLine().getStatusCode(), ApexHTTPConstants.HTTP_STATUS_NOT_FOUND);
        Assert.assertEquals(res.getStatusLine().getReasonPhrase(), ApexHTTPConstants.HTTP_STATUS_NOT_FOUND_404_MSG);
    }

    @Test(priority = 4)
    public void whenGetWithBlankUserID_thenStatus200_AllRecords() throws Exception {
        // This should return a list of users (all users) and status OK
        HttpResponse res = ApexHttpUtil.sendAndReceiveGetMessages("");
        Assert.assertEquals(res.getStatusLine().getStatusCode(), ApexHTTPConstants.HTTP_STATUS_OK);
        Assert.assertEquals(res.getStatusLine().getReasonPhrase(), ApexHTTPConstants.HTTP_STATUS_OK200_MSG);
    }

    @Test(priority = 5)
    public void whenGetWithSpecialCharacters_thenStatus404() throws Exception {
        HttpResponse res = ApexHttpUtil.sendAndReceiveGetMessages("$$$");
        Assert.assertEquals(res.getStatusLine().getStatusCode(), ApexHTTPConstants.HTTP_STATUS_NOT_FOUND);
        Assert.assertEquals(res.getStatusLine().getReasonPhrase(), ApexHTTPConstants.HTTP_STATUS_NOT_FOUND_404_MSG);
    }

}


