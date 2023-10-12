package com.apex.api.test.core;

public interface ApexHTTPConstants {

    public static final String BASE_URL = "https://gorest.co.in/public/v2/users/";
    public static final int HTTP_STATUS_OK = 200;
    public static final int HTTP_STATUS_CREATED = 201;
    public static final int HTTP_STATUS_DELETED = 204;
    public static final int HTTP_STATUS_BADREQUEST= 400;
    public static final int HTTP_STATUS_AUTHENTICATION_FAILED = 401;
    public static final int HTTP_STATUS_USER_NOTALLOWED = 403;
    public static final int HTTP_STATUS_NOT_FOUND = 404;
    public static final int HTTP_STATUS_METHODNOTALLOWED = 405;
    public static final int HTTP_STATUS_UNSUPPORTEDMEDIATYPE = 415;
    public static final int HTTP_STATUS_DATAVALIDATIONERROR = 422;
    public static final int HTTP_STATUS_TOOMANYREQUESTS = 429;
    public static final int HTTP_STATUS_INTERNALSERVERERROR = 500;


    //status messages
    public static final String HTTP_STATUS_OK200_MSG= "Everything worked as expected.";
    public static final String HTTP_STATUS_CREATED201_MSG= "A resource was successfully created in response to a POST request. The Location header contains the URL pointing to the newly created resource.";
    public static final String HTTP_STATUS_DELETED204_MSG = "The request was handled successfully and the response contains no body content (like a DELETE request).";
    public static final String HTTP_STATUS_BADREQUEST400_MSG ="Bad request. This could be caused by various actions by the user, such as providing invalid JSON data in the request body etc.";
    public static final String HTTP_STATUS_AUTHENTICATION_FAILED401_MSG="Authentication failed.";
    public static final String HTTP_STATUS_USER_NOTALLOWED403_MSG="The authenticated user is not allowed to access the specified API endpoint.";
    public static final String HTTP_STATUS_NOT_FOUND_404_MSG ="The requested resource does not exist.";
    public static final String HTTP_STATUS_METHODNOTALLOWED405_MSG ="Method not allowed. Please check the Allow header for the allowed HTTP methods.";

    public static final String HTTP_STATUS_UNSUPPORTEDMEDIATYPE415_MSG ="Unsupported media type. The requested content type or version number is invalid.";
    public static final String HTTP_STATUS_DATAVALIDATIONERROR422_MSG = "Data validation failed (in response to a POST request, for example). Please check the response body for detailed error messages.";
   public static final String HTTP_STATUS_TOOMANYREQUESTS429_MSG = "Too many requests. The request was rejected due to rate limiting.";
   public static final String HTTP_STATUS_INTERNALSERVERERROR_MSG = "Internal server error. This could be caused by internal program errors.";



}
