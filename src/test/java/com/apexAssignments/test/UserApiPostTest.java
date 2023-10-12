package com.apexAssignments.test;

import com.apex.api.test.util.FileUtil;
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

    User user = null;

    @BeforeClass
    public void setup() throws Exception {
        user = UserUtil.getFakeUser();
        FileUtil.writeUserTOFile(user);
    }

    @Test(priority = 1)
    public void whenPostWithValidUserFields_ThenStatus201() throws Exception {

        user = FileUtil.readUserFromFile();

        //User newUser = new User("jamie","jamie"+  System.currentTimeMillis() +"@gg.com","female","inactive");
        String newUserString = mapper.writeValueAsString(user);

        HttpResponse response = ApexHttpUtil.sendAndReceivePostMessages(newUserString);
        String result = ApexHttpUtil.getResponseString(response);

        Assert.assertEquals(response.getStatusLine().getStatusCode(),201 );
        Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "Created");

        String newUserId = null;
        if (response.getStatusLine().getStatusCode() == 201) {
            User newUser = mapper.readValue(result, User.class);
            newUserId = newUser.getId();

            System.out.println("New User created with id " + newUserId);
            System.out.println(newUser);

        }
        else if (response.getStatusLine().getStatusCode() == 422) { // Unprocessable entity returned from server
            System.out.println("User could not be created!");
            System.err.println(result);
        }
       // Assert.assertEquals(response.getEntity().getContent());
    }

    @Test(priority = 2)
    public void whenPostWithExistingUserEmail_ThenStatus422() throws Exception {
        String newUserString = mapper.writeValueAsString(user);

        HttpResponse response = ApexHttpUtil.sendAndReceivePostMessages(newUserString);
        String result = ApexHttpUtil.getResponseString(response);

        Assert.assertEquals(response.getStatusLine().getStatusCode(),422 );
        Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "Unprocessable Entity");

        String newUserId = null;
        if (response.getStatusLine().getStatusCode() == 201) {
            User newUser = mapper.readValue(result, User.class);
            newUserId = newUser.getId();

            System.out.println("New User created with id " + newUserId);
            System.out.println(newUser);

        }
        else if (response.getStatusLine().getStatusCode() == 422) { // Unprocessable entity returned from server
            System.out.println("User could not be created!");
            System.err.println(result);
        }
    }

    @Test(priority = 2)
    public void whenPostWithNoEmail_ThenStatus422() throws Exception {
        user.setEmail(null); // Manually removing the email from the user
        String newUserString = mapper.writeValueAsString(user);

        HttpResponse response = ApexHttpUtil.sendAndReceivePostMessages(newUserString);
        String result = ApexHttpUtil.getResponseString(response);

        Assert.assertEquals(response.getStatusLine().getStatusCode(),422 );
        Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "Unprocessable Entity");

        String newUserId = null;
        if (response.getStatusLine().getStatusCode() == 201) {
            User newUser = mapper.readValue(result, User.class);
            newUserId = newUser.getId();

            System.out.println("New User created with id " + newUserId);
            System.out.println(newUser);

        }
        else if (response.getStatusLine().getStatusCode() == 422) { // Unprocessable entity returned from server
            System.out.println("User could not be created!");
            System.err.println(result);
        }
    }
}


