package com.apexAssignments.test;

import com.apex.api.test.util.JXLExcelUtil;
import com.apex.api.test.util.POIExcelUtil;
import com.apex.api.test.util.UserUtil;
import org.apache.http.HttpResponse;
import com.apex.api.test.core.User;
import com.apex.api.test.core.ApexHttpUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;


public class UserApiPostTest {

    ObjectMapper mapper = new ObjectMapper();


    @BeforeClass
    public void setup() throws Exception {
        // Create a user with random values and save it to the user.json file
        UserUtil.createRandomUser();
    }

    @DataProvider(name = "dp_postValidIds")
    public Object[][] postValidUsersUsingPOI() throws IOException {
        String[][] users = POIExcelUtil.getUsersForPostFromExcel("ApexTestData.xlsx", "UsersForPost");
        return users;
    }

    @Test(dataProvider = "dp_postValidIds", priority = 1)
    public void whenPostWithValidUserFields_ThenStatus201(String id,String name, String email, String gender, String status) throws Exception {
        //User user = UserUtil.getUserFromFile(); // Getting the user from file

        // NOTE: First time this is run, the user will not have an id.
        // The id will be set after the user is created (after POST call)
        User user = new User(name,email,gender,status);
        String newUserString = mapper.writeValueAsString(user);

        HttpResponse response = ApexHttpUtil.sendAndReceivePostMessages(newUserString);

        // Check if create operation is successful
        ApexApiValidator.performBasicAssertValidations(response, "CREATED");

        String newUserId = null;
        if (response.getStatusLine().getStatusCode() == 201) {
            // Read the response result and get the id of the new user
            String result = ApexHttpUtil.getResponseString(response);
            User newUser = mapper.readValue(result, User.class);
            newUserId = newUser.getId();

            System.out.println("New User created with id: " + newUserId);
            user.setId(newUserId);

            // Updating the user with the new id and saving it to the user.json file
          // POIExcelUtil.updateUserIdInXLFile(user);

            System.out.println(newUser);

        }
    }


    //-------
    @DataProvider(name = "dp_postInvalidIds")
    public Object[][] postInvalidUsersUsingPOI() throws IOException {
        String[][] users = POIExcelUtil.getUsersForPostFromExcel("ApexTestData.xlsx", "UsersForPost");
        return users;
    }
    @Test(priority = 2, dataProvider = "dp_postInvalidIds")
    public void whenPostWithExistingUserEmail_ThenStatus422(String id,String name, String email, String gender, String status) throws Exception {
        // Getting the user from file
       // User user = UserUtil.getUserFromFile();
       // String newUserString = mapper.writeValueAsString(user);

        User user = new User(name,email,gender,status);
        String newUserString = mapper.writeValueAsString(user);

        HttpResponse response = ApexHttpUtil.sendAndReceivePostMessages(newUserString);
        String result = ApexHttpUtil.getResponseString(response);

        ApexApiValidator.performBasicAssertValidations(response, "UNPROCESSABLE");
        }


    //--------
    @DataProvider(name = "dp_postNoEmailIds")
    public Object[][] postNoEmailUsersUsingPOI() throws IOException {
        String[][] users = POIExcelUtil.getUsersForPostFromExcel("ApexTestData.xlsx", "NoEmailUsersForPost");
        return users;
    }

    @Test(priority = 3, dataProvider = "dp_postNoEmailIds")
    public void whenPostWithNoEmail_ThenStatus422(String id,String name, String email, String gender, String status) throws Exception {
       // User user = UserUtil.getUserFromFile();

       // user.setEmail(null); // Manually removing the email from the user
        //String newUserString = mapper.writeValueAsString(user);

        User user = new User(name,email,gender,status);
        String newUserString = mapper.writeValueAsString(user);

        HttpResponse response = ApexHttpUtil.sendAndReceivePostMessages(newUserString);
        String result = ApexHttpUtil.getResponseString(response);

        ApexApiValidator.performBasicAssertValidations(response, "UNPROCESSABLE");

        if (response.getStatusLine().getStatusCode() == 422) { // Unprocessable entity returned from server
            System.out.println("User could not be created!");
            System.err.println(result);
        }
    }
}


