package org.example;

import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONArray;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class RestApiUtil {

    Response response = null;

    PropUtil prop = new PropUtil();


    public Response executeMainApiRequest(String httpRequestType, String headerKeyVal, String endPoint, String inputBody, String params) throws IOException {
        System.out.println("\n\n~~~~~~~~***** inside genericApiRequest() method *****~~~~~~~~");

        RequestSpecification requestSpecification = RestAssured.given();
        //Response response = null;

        /******* SEt Payload  body for POST *****/


        String FullEndPoint = prop.getProperty(endPoint);
        System.out.println("Full EndPoint URL = " + FullEndPoint);

        /***** set Query Params to RequestSpecification ***/
        if (!params.equals("")) {
            System.out.println("Original Qury-params are: " + params);
            String[] paramArray = params.split("#");
            for (String s : paramArray) {
                if (!s.equals("")) {
                    String[] eachParam = s.split("=");
                    System.out.println("Params are: "+eachParam[0]+", "+eachParam[1]);
                    requestSpecification.queryParam(eachParam[0], eachParam[1]);
                }
            }
        }

        System.out.println("APPID = "+prop.getProperty("APPID"));

        requestSpecification.queryParam("appid",prop.getProperty("APPID"));

        /****** set Content-Type & Headers ********/
        if (!headerKeyVal.equals("")) {
            String[] header = headerKeyVal.split("=");
            requestSpecification.header(header[0], header[1]);
        }
        requestSpecification.contentType(ContentType.JSON);


        /****** Trigger the HTTP_Request with Headers ******/
        if (httpRequestType.equalsIgnoreCase("GET")) {
            System.out.println("*** GET request *** = "+requestSpecification.given().log().uri());
            response = requestSpecification.get(FullEndPoint);

        } else if (httpRequestType.equalsIgnoreCase("POST")) {
            System.out.println("*** POST request ***");
            if (inputBody.equalsIgnoreCase("POST_BODY")) {
                System.out.println("File contents = " + prop.loadFile(prop.getProperty("PAYLOAD_DUMMYAPI_POST")));
                requestSpecification.body(updateElementInPayload(prop.loadFile(prop.getProperty("PAYLOAD_DUMMYAPI_POST")),"NewName" + createRandomWord(6)));
            }
            response = requestSpecification.post(FullEndPoint);
        } else if (httpRequestType.equalsIgnoreCase("PUT")) {
            System.out.println("*** PUT request ***");
            requestSpecification.body(inputBody);
            response = requestSpecification.post(FullEndPoint);
        }

        System.out.println("*******  API statusCode = " + response.getStatusCode());
        System.out.println("*******  Full Response message = " + response.getBody().asString());

        return response;

    }

    public Response apirequest(String httpReq, String endpoint, String paramsList) {

        RequestSpecification specification = RestAssured.given();


        response = specification.when().queryParam("country", "United States").get(endpoint);

        response = specification.when().queryParam("country", "United States").post(endpoint);

        System.out.println("Response message: " + response.getBody().asString());

        return response;

    }

    public void verifyResponseCode(int respCode) {

        Assert.assertEquals(response.getStatusCode(), respCode);
        System.out.println("Successfull 200 Code has received");
    }

    //    public void checkForElement(String elementToBeChecked) {
//        System.out.println("Response message: " + response.getBody().asString());
//
//        String output = JsonPath.read(response.getBody().asString(), "$[1].web_pages[0]");
//        System.out.println("we got this page for you"+output);
//
//    }
    public void checkForElement(String elementToBeChecked) throws IOException {
        System.out.println("Response message: " + response.getBody().asString());
//        if (!elementToBeChecked.contains("Successfully")) {
//            JSONArray outputArray = JsonPath.read(response.getBody().asString(), "$..name");
//            System.out.println("Number of Universtiies " + outputArray.size());
//            String matchedName = null;
//            for (int i = 0; i < outputArray.size(); i++) {
//                // System.out.println("Current Element "+i+" : "+outputArray.get(i));
//                //    matchedName = JsonPath.read(outputArray.get(i).toString(), "$.name").toString();
//                if (elementToBeChecked.equalsIgnoreCase(outputArray.get(i).toString())) {
//                    System.out.println("The element '" + elementToBeChecked + "' found at position: " + i);
//
//                }
//
//            }
//        } else {
//            Response response = (Response) context.getContext("POST_RESPONSE");

                String[] respMsgParam = elementToBeChecked.split("=");

            String responseMessage = JsonPath.read(response.getBody().asString(), respMsgParam[0]);
            Assert.assertTrue(responseMessage.equalsIgnoreCase((respMsgParam[1])));
            System.out.println("Response contains: " + respMsgParam[1]+" at location: "+respMsgParam[0]);


    }


    public Object updateElementInPayload(File payload, String elementToBeUpdated) throws IOException {
        Object newJson = JsonPath.parse(payload).set("$.name", elementToBeUpdated).jsonString();
        System.out.println("****** Updated JSON file:  " + newJson);
        return newJson;
    }

    public static String createRandomWord(int len) {
        String name = "";
        for (int i = 0; i < len; i++) {
            int v = 1 + (int) (Math.random() * 26);
            char c = (char) (v + (i == 0 ? 'A' : 'a') - 1);
            name += c;
        }
        return name;
    }
}
