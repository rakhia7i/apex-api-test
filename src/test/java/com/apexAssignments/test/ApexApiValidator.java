package com.apexAssignments.test;

import com.apex.api.test.core.ApexHTTPConstants;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.http.HttpResponse;
import org.testng.Assert;

public class ApexApiValidator {

    public static void performBasicAssertValidations(HttpResponse resUser, String resString) {
        switch(resString) {
            case "OK":
                Assert.assertEquals(resUser.getStatusLine(), ApexHTTPConstants.HTTP_STATUS_OK);
                Assert.assertEquals(resUser.getStatusLine().getReasonPhrase(), ApexHTTPConstants.HTTP_STATUS_OK200_MSG);
            case "NOT_FOUND":
                Assert.assertEquals(resUser.getStatusLine().getStatusCode(), ApexHTTPConstants.HTTP_STATUS_NOT_FOUND);
               Assert.assertEquals(resUser.getStatusLine().getReasonPhrase(), ApexHTTPConstants.HTTP_STATUS_NOT_FOUND_404_MSG);
            case "CREATED":
                Assert.assertEquals(resUser.getStatusLine().getStatusCode(), ApexHTTPConstants.HTTP_STATUS_CREATED);
                Assert.assertEquals(resUser.getStatusLine().getReasonPhrase(), ApexHTTPConstants.HTTP_STATUS_CREATED201_MSG);
            case "UNPROCESSABLE":
                Assert.assertEquals(resUser.getStatusLine().getStatusCode(), ApexHTTPConstants.HTTP_STATUS_DATAVALIDATIONERROR);
                Assert.assertEquals(resUser.getStatusLine().getReasonPhrase(), ApexHTTPConstants.HTTP_STATUS_DATAVALIDATIONERROR422_MSG);
            default:
                break;

        }

    }
}
