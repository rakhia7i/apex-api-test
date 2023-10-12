package com.apex.api.test.core;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class DoPutApi {

    public static void main(String[] args) throws IOException {


        String url = ApexHttpUtil.createRequestUrl("5228187");
        HttpPut httpPut = new HttpPut(url);
        httpPut.addHeader("Authorization", "Bearer 0b3ea60f22dd50ba1f175e34d416c2638221be108d8097c4eaac9f8858d72527");
        httpPut.addHeader("content-type", "application/json");

        //  httpPut.setHeader("Accept","application/json");

        StringBuilder strEntity = new StringBuilder();
        strEntity.append("{");
        strEntity.append("\"status\": \"inactive\"");
        strEntity.append("}");

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(httpPut);
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode == 200 || statusCode == 201){
                System.out.println("Successfully Modified");
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // return it as a String
                    String result = EntityUtils.toString(entity);
                    System.out.println(result);
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
