package stepDefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.RestApiUtil;

import java.io.IOException;

public class APItests_OpenWeatherMap_Stepdef {

    RestApiUtil restApiUtil = new RestApiUtil();

    @Given("user executes {string} request api {string} with params {string}")
    public void userExecutesRequestApiWithParams(String httpReqType, String endpint, String paramsList) throws IOException {
        //restApiUtil.apirequest(http, endpint, paramsList);
        restApiUtil.executeMainApiRequest(httpReqType, "", endpint, "",paramsList);
    }

    @When("response code is {int}")
    public void response_code_is(int ResponseCODE) {
        restApiUtil.verifyResponseCode(ResponseCODE);
    }

    @Then("Verifies that {string} is present in the response")
    public void verifies_that_string_is_present_in_the_response(String strToCheck) throws IOException {

        // Write code here that turns the phrase above into concrete actions
        System.out.println("Hurray");
        restApiUtil.checkForElement(strToCheck.trim());

    }

}
