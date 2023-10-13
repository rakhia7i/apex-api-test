package com.apexAssignments.test;

import com.apex.api.test.core.User;
import com.apex.api.test.util.UserUtil;
import org.apache.http.HttpResponse;
import com.apex.api.test.core.ApexHTTPConstants;
import com.apex.api.test.core.ApexHttpUtil;
import com.apex.api.test.core.ApexApiBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;



public class UserApiGetTest extends ApexApiBaseTest {

    @Test(priority = 1)
    public void whenGetWithValidUserID_thenStatus200() throws Exception {
        User user = UserUtil.getUserFromFile();
        HttpResponse res = ApexHttpUtil.sendAndReceiveGetMessages(user.getId());

        Assert.assertEquals(res.getStatusLine().getStatusCode(), ApexHTTPConstants.HTTP_STATUS_OK);
        Assert.assertEquals(res.getStatusLine().getReasonPhrase(), ApexHTTPConstants.HTTP_STATUS_OK200_MSG);
        Assert.assertTrue(ApexHttpUtil.getResponseString(res).contains(user.getId()));
    }

    @Test(priority = 2)
    public void whenGetWithInvalidUserID_thenStatus404() throws Exception {
        HttpResponse res = ApexHttpUtil.sendAndReceiveGetMessages("-5297524");
        Assert.assertEquals(res.getStatusLine().getStatusCode(), ApexHTTPConstants.HTTP_STATUS_NOT_FOUND);
        Assert.assertEquals(res.getStatusLine().getReasonPhrase(),ApexHTTPConstants.HTTP_STATUS_NOT_FOUND_404_MSG);
    }

    @Test(priority = 3)
    public void whenGEtWithNonIntegerUserId_thenStatus404() throws Exception{
        HttpResponse res = ApexHttpUtil.sendAndReceiveGetMessages("abcd");
        Assert.assertEquals(res.getStatusLine().getStatusCode(), ApexHTTPConstants.HTTP_STATUS_NOT_FOUND);
        Assert.assertEquals(res.getStatusLine().getReasonPhrase(),ApexHTTPConstants.HTTP_STATUS_NOT_FOUND_404_MSG);}

    @Test(priority = 4)
    public void whenGetWithBlankUserID_thenStatus200_AllRecords() throws Exception{
        // This should return a list of users (all users) and status OK
        HttpResponse res = ApexHttpUtil.sendAndReceiveGetMessages("");
        Assert.assertEquals(res.getStatusLine().getStatusCode(), ApexHTTPConstants.HTTP_STATUS_OK);
        Assert.assertEquals(res.getStatusLine().getReasonPhrase(), ApexHTTPConstants.HTTP_STATUS_OK200_MSG);
    }

   @Test(priority = 5)
   public void whenGetWithSpecialCharacters_thenStatus404() throws Exception{
        HttpResponse res = ApexHttpUtil.sendAndReceiveGetMessages("$$$");
        Assert.assertEquals(res.getStatusLine().getStatusCode(), ApexHTTPConstants.HTTP_STATUS_NOT_FOUND);
        Assert.assertEquals(res.getStatusLine().getReasonPhrase(), ApexHTTPConstants.HTTP_STATUS_NOT_FOUND_404_MSG);
     }

}


