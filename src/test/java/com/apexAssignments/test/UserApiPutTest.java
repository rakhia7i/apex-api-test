package com.apexAssignments.test;

import com.apex.api.test.core.ApexHTTPConstants;
import com.apex.api.test.core.ApexHttpUtil;
import com.apex.api.test.core.User;
import com.apex.api.test.util.POIExcelUtil;
import com.apex.api.test.util.UserUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class UserApiPutTest {
    ObjectMapper mapper = new ObjectMapper();

    @DataProvider(name = "dp_putValidIds")
    public Object[][] postValidUsersUsingPOI() throws IOException {
        String[][] users = POIExcelUtil.getUsersForPutFromExcel("ApexTestData.xlsx", "UsersForPut");
        return users;
    }
    @Test(priority = 1, dataProvider ="dp_putValidIds" )
    public void whenUpdateName_ThenStatus200(String id,String name, String email, String gender, String status) throws Exception {
        //User user = UserUtil.getUserFromFile(); // Getting the user from file
        User user = new User(id,name,email,gender, status);
        //System.out.println("Current name is " + user.getName());

        // Get a new name to set
        String newName = UserUtil.generateNewName();
        user.setName(newName);

        System.out.println("New name is " + user.getName());

        HttpResponse response = ApexHttpUtil.sendAndReceivePutMessages(user.getId(), mapper.writeValueAsString(user));
        String responseString = ApexHttpUtil.getResponseString(response);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), ApexHTTPConstants.HTTP_STATUS_OK );
        Assert.assertEquals(response.getStatusLine().getReasonPhrase(),  ApexHTTPConstants.HTTP_STATUS_OK200_MSG);

        // Check if the user name has been updated by making a GET request and checking the name
        HttpResponse getResponse = ApexHttpUtil.sendAndReceiveGetMessages(user.getId());
        String getResponseString = ApexHttpUtil.getResponseString(getResponse);
        System.out.println("After Update: Get response string is " + getResponseString);
        Assert.assertTrue(getResponseString.contains(newName));


       if (response.getStatusLine().getStatusCode() == 422) { // Unprocessable entity returned from server
            System.out.println("User could not be updated!");
            System.err.println(responseString);
        }
    }


    @Test(priority = 2)
    public void whenUpdateWithInvalidID_thenStatus404() throws Exception {
        User user = UserUtil.getUserFromFile(); // Getting the user from file

        System.out.println("Current name is " + user.getName());

        // Get a new name to set
        String newName = UserUtil.generateNewName();
        user.setName(newName);

        System.out.println("New name is " + user.getName());

        HttpResponse response = ApexHttpUtil.sendAndReceivePutMessages("-123456789", mapper.writeValueAsString(user));
        String responseString = ApexHttpUtil.getResponseString(response);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), ApexHTTPConstants.HTTP_STATUS_NOT_FOUND );
        Assert.assertEquals(response.getStatusLine().getReasonPhrase(),  ApexHTTPConstants.HTTP_STATUS_NOT_FOUND_404_MSG);

    }
}

