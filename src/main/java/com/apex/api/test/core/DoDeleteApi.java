package com.apex.api.test.core;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

import static com.apex.api.test.core.ApexHttpUtil.createRequestUrl;

public class DoDeleteApi {

    public static void main(String[] args) throws IOException, ClientProtocolException {

        String url = createRequestUrl("5227865");
        HttpDelete httpDelete = new HttpDelete(url);
        httpDelete.addHeader("Authorization", "Bearer 0b3ea60f22dd50ba1f175e34d416c2638221be108d8097c4eaac9f8858d72527");
        httpDelete.addHeader("content-type", "application/json");

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(httpDelete);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 200 && statusCode <=300) {
                System.out.println("Successfully Deleted");
            }
            else if(statusCode == 404){
                System.out.println("Record does not exist");
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
