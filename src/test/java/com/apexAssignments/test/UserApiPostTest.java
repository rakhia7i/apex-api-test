package com.apexAssignments.test;

import com.apex.api.test.util.UserUtil;
import org.apache.http.HttpResponse;
import com.apex.api.test.core.User;
import com.apex.api.test.core.ApexHttpUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;


public class UserApiPostTest {

    ObjectMapper mapper = new ObjectMapper();


    @BeforeClass
    public void setup() throws Exception {
        // Create a user with random values and save it to the user.json file
        UserUtil.createRandomUser();
    }

    @Test(priority = 1)
    public void whenPostWithValidUserFields_ThenStatus201() throws Exception {
        User user = UserUtil.getUserFromFile(); // Getting the user from file

        // NOTE: First time this is run, the user will not have an id.
        // The id will be set after the user is created (after POST call)
        String newUserString = mapper.writeValueAsString(user);

        HttpResponse response = ApexHttpUtil.sendAndReceivePostMessages(newUserString);

        // Check if create operation is successful
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 201);
        Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "Created");

        String newUserId = null;
        if (response.getStatusLine().getStatusCode() == 201) {
            // Read the response result and get the id of the new user
            String result = ApexHttpUtil.getResponseString(response);
            User newUser = mapper.readValue(result, User.class);
            newUserId = newUser.getId();

            System.out.println("New User created with id " + newUserId);
            user.setId(newUserId);

            // Updating the user with the new id and saving it to the user.json file
            UserUtil.writeUserToFile(user);

            System.out.println(newUser);
        }
    }

    @Test(priority = 2)
    public void whenPostWithExistingUserEmail_ThenStatus422() throws Exception {
        // Getting the user from file
        User user = UserUtil.getUserFromFile();
        String newUserString = mapper.writeValueAsString(user);

        HttpResponse response = ApexHttpUtil.sendAndReceivePostMessages(newUserString);
        String result = ApexHttpUtil.getResponseString(response);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 422);
        Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "Unprocessable Entity");

        if (response.getStatusLine().getStatusCode() == 422) { // Unprocessable entity returned from server
            System.out.println("User could not be created!");
            System.err.println(result);
        }
    }

    @Test(priority = 3)
    public void whenPostWithNoEmail_ThenStatus422() throws Exception {
        User user = UserUtil.getUserFromFile();

        user.setEmail(null); // Manually removing the email from the user
        String newUserString = mapper.writeValueAsString(user);

        HttpResponse response = ApexHttpUtil.sendAndReceivePostMessages(newUserString);
        String result = ApexHttpUtil.getResponseString(response);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 422);
        Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "Unprocessable Entity");

        if (response.getStatusLine().getStatusCode() == 422) { // Unprocessable entity returned from server
            System.out.println("User could not be created!");
            System.err.println(result);
        }
    }
}


