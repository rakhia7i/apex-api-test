package com.apex.api.test.core;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;

public class ApexHttpUtil {

//    public static HttpResponse sendAndReceiveGetMessages(String userId) throws IOException {
//        HttpGet request = new HttpGet(ApexHTTPConstants.BASE_URL + userId);
//        addHeaders(request);
//
//        HttpClient client = HttpClientBuilder.create().build();
//        HttpResponse response = client.execute(request);
//        return response;
//    }
    public static HttpResponse sendAndReceiveGetMessages(String userId) throws IOException {
        HttpGet request = new HttpGet(ApexHTTPConstants.BASE_URL + userId);
        addHeaders(request);

        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(request);
        return response;
    }

    public static HttpResponse  sendAndReceivePostMessages(String message) throws IOException {
        HttpPost postRequest = new HttpPost(ApexHTTPConstants.BASE_URL);
        addHeaders(postRequest);

        HttpClient client = HttpClientBuilder.create().build();
        StringEntity entity = new StringEntity(message, ContentType.APPLICATION_JSON);
        postRequest.setEntity(entity);

        HttpResponse response = client.execute(postRequest);
        return response;
    }

    public static HttpResponse sendAndReceivePutMessages(String userId, String message) throws IOException {
        HttpPut putRequest = new HttpPut(ApexHTTPConstants.BASE_URL + userId);
        addHeaders(putRequest);

        HttpClient client = HttpClientBuilder.create().build();
        StringEntity entity = new StringEntity(message, ContentType.APPLICATION_JSON);
        putRequest.setEntity(entity);

        HttpResponse response = client.execute(putRequest);
        return response;
    }

    public static HttpResponse sendAndReceiveDeleteMessages(String userId) throws IOException {
        HttpDelete delRequest = new HttpDelete(ApexHTTPConstants.BASE_URL + userId);
        addHeaders(delRequest);

        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(delRequest);
        return response;
    }

    public static String createRequestUrl(String userId) {
        return ApexHTTPConstants.BASE_URL + userId;

    }

    public static void addHeaders(HttpRequest request) {
        request.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer 0b3ea60f22dd50ba1f175e34d416c2638221be108d8097c4eaac9f8858d72527");

    }

    public static String getResponseString(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        String result = "";
        if (entity != null) {
            //return as a string
            result = EntityUtils.toString(entity);
        }
        return result;
    }


}

