package com.apexAssignments.test;

import org.apache.http.HttpResponse;
import com.apex.api.test.core.ApexHTTPConstants;
import com.apex.api.test.core.ApexHttpUtil;
import com.apex.api.test.core.ApexApiBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.apex.api.test.core.ApexHTTPConstants.HTTP_STATUS_NOT_FOUND;
import static com.apex.api.test.core.ApexHTTPConstants.HTTP_STATUS_NOT_FOUND_404_MSG;

public class UserApiGetTest extends ApexApiBaseTest {
    @Test
    public void testGetWithValidUserId() throws Exception {

        HttpResponse res = ApexHttpUtil.sendAndReceiveGetMessages("5297520");
        Assert.assertEquals(res.getStatusLine().getStatusCode(), ApexHTTPConstants.HTTP_STATUS_OK);
        Assert.assertEquals(res.getStatusLine().getReasonPhrase(), ApexHTTPConstants.HTTP_STATUS_OK200_MSG);
        Assert.assertTrue(ApexHttpUtil.getResponseString(res).contains("5297520"));
    }

    @Test
    public void testGetWithNonExistingUserId() throws Exception {
        HttpResponse res = ApexHttpUtil.sendAndReceiveGetMessages("5297524");
        Assert.assertEquals(res.getStatusLine().getStatusCode(), HTTP_STATUS_NOT_FOUND);
        Assert.assertEquals(res.getStatusLine().getReasonPhrase(),HTTP_STATUS_NOT_FOUND_404_MSG);
    }

    @Test
    public void testGetWithNonIntegerUserId() throws Exception{
        HttpResponse res = ApexHttpUtil.sendAndReceiveGetMessages("abcd");
        Assert.assertEquals(res.getStatusLine().getStatusCode(), HTTP_STATUS_NOT_FOUND);
        Assert.assertEquals(res.getStatusLine().getReasonPhrase(),HTTP_STATUS_NOT_FOUND_404_MSG);}

    @Test
    public void testGetWithBlankUserId() throws Exception{
        HttpResponse res = ApexHttpUtil.sendAndReceiveGetMessages("");
        Assert.assertEquals(res.getStatusLine().getStatusCode(), ApexHTTPConstants.HTTP_STATUS_OK);
        Assert.assertEquals(res.getStatusLine().getReasonPhrase(), ApexHTTPConstants.HTTP_STATUS_OK200_MSG);
    }

   @Test
   public void testGetWithSpecialCharacters() throws Exception{
        HttpResponse res = ApexHttpUtil.sendAndReceiveGetMessages("$$$");
        Assert.assertEquals(res.getStatusLine().getStatusCode(), HTTP_STATUS_NOT_FOUND);
        Assert.assertEquals(res.getStatusLine().getReasonPhrase(), HTTP_STATUS_NOT_FOUND_404_MSG);
     }

}


