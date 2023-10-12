package com.apexAssignments.test;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import com.apex.api.test.core.User;
import com.apex.api.test.core.ApexHttpUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;


public class UserApiPostTest {

    @Test
    public void whenPostWithValidUserFields_ThenStatus201() throws Exception {

        HttpClient client = HttpClientBuilder.create().build();
        ObjectMapper mapper = new ObjectMapper();
        User newUser = new User("jamie","jamie@gg.com","female","inactive");
        String newUserString = mapper.writeValueAsString(newUser);

        HttpResponse response = ApexHttpUtil.sendAndReceivePostMessages(newUserString);
        String result = ApexHttpUtil.getResponseString(response);

        Assert.assertEquals(response.getStatusLine().getStatusCode(),201 );
        Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "Created");

        String newUserId = null;
        if (response.getStatusLine().getStatusCode() == 201) {
            newUser = mapper.readValue(result, User.class);
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
}


