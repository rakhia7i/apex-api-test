package com.apexAssignments.test;

import com.apex.api.test.core.ApexHTTPConstants;
import com.apex.api.test.core.ApexHttpUtil;
import com.apex.api.test.core.User;
import com.apex.api.test.util.UserUtil;
import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserApiDeleteTest {

    @Test(priority = 1)
    public void whenDeleteWithValidUserId_ThenStatus200() throws Exception {
        User user = UserUtil.getUserFromFile(); // Getting the user from file
        String userId = user.getId();

        // Delete the user
        HttpResponse response = ApexHttpUtil.sendAndReceiveDeleteMessages(userId);

        // Check if delete operation is successful
        Assert.assertEquals(response.getStatusLine().getStatusCode(), ApexHTTPConstants.HTTP_STATUS_DELETED);

    }

    @Test(priority = 2)
    public void whenGetWithDeletedUserId_thenStatus404() throws Exception {
        User user = UserUtil.getUserFromFile(); // Getting the user from file
        String userId = user.getId();

        // Try to GET the deleted user
        HttpResponse response = ApexHttpUtil.sendAndReceiveGetMessages(userId);

        // Expected result is 404 NOT FOUND as the user is already deleted
        Assert.assertEquals(response.getStatusLine().getStatusCode(), ApexHTTPConstants.HTTP_STATUS_NOT_FOUND);
        Assert.assertEquals(response.getStatusLine().getReasonPhrase(), ApexHTTPConstants.HTTP_STATUS_NOT_FOUND_404_MSG);

    }

    @Test(priority = 3)
    public void whenGetWithInvalidUserId_thenStatus404() throws Exception {

        // Try to delete a non existent user
        HttpResponse response = ApexHttpUtil.sendAndReceiveGetMessages("-1099");

        // Expected result is 404 NOT FOUND as the user does not exist
        Assert.assertEquals(response.getStatusLine().getStatusCode(), ApexHTTPConstants.HTTP_STATUS_NOT_FOUND);
        Assert.assertEquals(response.getStatusLine().getReasonPhrase(), ApexHTTPConstants.HTTP_STATUS_NOT_FOUND_404_MSG);

    }
}
