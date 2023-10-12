package com.apex.api.test.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.apex.api.test.core.DoGetApi.mapper;

public class DoPostApi {

    //**** HTTP post
    public static void main(String[] args) throws IOException {

        HttpPost httpPost = new HttpPost("https://gorest.co.in/public/v2/users");
        httpPost.addHeader("Authorization", "Bearer 0b3ea60f22dd50ba1f175e34d416c2638221be108d8097c4eaac9f8858d72527");

        HttpEntity httpEntity = new StringEntity(getDataForPost("Sandy", "female", "sandy1124@yymail.com", "inactive"), ContentType.APPLICATION_JSON);
        httpPost.setEntity(httpEntity);
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(httpPost);

        String respString = EntityUtils.toString(response.getEntity());
        String newUserId = null;
        User newUser = null;
        if (response.getStatusLine().getStatusCode() == 201) {
            newUser = mapper.readValue(respString, User.class);
            newUserId = newUser.getId();

            System.out.println("New User created with id " + newUserId);
            System.out.println(newUser);

        } else if (response.getStatusLine().getStatusCode() == 422) { // Unprocessable entity returned from server
            System.out.println("User could not be created!");
            System.err.println(respString);
        }
    }

    static String getDataForPost(String name, String gender, String email, String status) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", name);
        map.put("gender", gender);
        map.put("email", email);
        map.put("status", status);
        try {
            return mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
