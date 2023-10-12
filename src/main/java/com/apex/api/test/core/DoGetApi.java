package com.apex.api.test.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class DoGetApi {
    static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {

        HttpResponse getResponse = ApexHttpUtil.sendAndReceiveGetMessages("5349492");
        //checking if status code is correct
        if (getResponse.getStatusLine().getStatusCode() == 200) {
            System.out.println("ok");
            HttpEntity entity = getResponse.getEntity();
            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                System.out.println(result);
            }
        } else {
            System.out.println("User not found");
        }

    }
}
