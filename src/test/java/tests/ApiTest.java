package tests;

import static io.restassured.RestAssured.*;
import org.testng.annotations.Test;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.CoreMatchers.equalTo;
import pageobjectmodel.StaticVariables;

/* 
	The testing api is reset before each of test case run.
	Defined api url (www.noapi.com) is an imaginery url, so all cases will be failed when tests are run.
*/

public class ApiTest {
	
	//define baseURI and basePath in a constructor to get API_ROOT and API_PATH before test methods run.
	public ApiTest(){
		baseURI= StaticVariables.API_ROOT;
		basePath = StaticVariables.API_PATH;
	}
	
	//Verify that the api starts with an empty store.
	@Test
	public void verifyEmptyStore() {
		given().
		when().
			get().
		then().
			//check if response body is empty
			body("", Matchers.empty());
	}
	
	//Verify that title and owner are required fields.
	@Test
	public void verifyRequiredFields() {		
		//send put request without title and owner, don't send request body
		given().
		when().
			put().
		then().
			statusCode(400).
			body("error", equalTo(StaticVariables.warningMsg1));
		
		//send put request without title field in body
		given().
		when().
			body(StaticVariables.getRequestBodyWithoutFields(StaticVariables.title)).
			put().
		then().
			statusCode(400).
			body("error", equalTo(StaticVariables.warningMsg2));
		
		//send put request without owner field in body
		given().
		when().
			body(StaticVariables.getRequestBodyWithoutFields(StaticVariables.owner)).
			put().
		then().
			statusCode(400).
			body("error", equalTo(StaticVariables.warningMsg3));
	}
	
	//Verify that title and owner cannot be empty.
	@Test
	public void verifyNonEmptyFields() {
		//send title and owner null
		given().
		when().
			body(StaticVariables.getRequestBody(null, null)).
			put().
		then().
			statusCode(400).
			body("error", equalTo(StaticVariables.warningMsg4));
		
		//send title null
		given().
		when().
			body(StaticVariables.getRequestBody(StaticVariables.owner, null)).
			put().
		then().
			statusCode(400).
			body("error", equalTo(StaticVariables.warningMsg5));
		
		//send owner null
		given().
		when().
			body(StaticVariables.getRequestBody(null, StaticVariables.title)).
			put().
		then().
			statusCode(400).
			body("error", equalTo(StaticVariables.warningMsg6));
	}
	
	//Verify that the id field is read only.
	@Test
	public void verifyIdReadOnly() {
		given().
		when().
			body(StaticVariables.requestBodyWithID).
			put().
		then().
			statusCode(400);
	}
	
	//Verify that you can create a new stamp using PUT request.
	@Test
	public void verifyNewStampIsCreatable() {
		//create stamp and check if created stamp will be returned in the response.
		int id =
		given().
		when().
			body(StaticVariables.getRequestBody(StaticVariables.owner, StaticVariables.title)).
			put().
		then().
			statusCode(200).
			body("id.Size()", greaterThan(0)).
			body("owner", equalTo(StaticVariables.owner)).
			body("title", equalTo(StaticVariables.title)).
			//take id of new stamp from response to use it on get.
			extract().response().path("id");
		
		//get request using id of created stamp. it should return the same stamp.
		given().
			pathParam("id", id).
		when().
			get("{id}/").
		then().
			statusCode(200).
			body("id", equalTo(id)).
			body("owner", equalTo(StaticVariables.owner)).
			body("title", equalTo(StaticVariables.title));
	}
	
	//Verify that you cannot create a duplicate stamp.
	@Test
	public void verifyDuplicateStampIsNotPossible() {
		//create first stamp
		given().
		when().
			body(StaticVariables.getRequestBody(StaticVariables.owner, StaticVariables.title)).
			put().
		then().
			statusCode(200);
		
		//Try to create second stamp with same owner and title fields, then check error message
		given().
		when().
			body(StaticVariables.getRequestBody(StaticVariables.owner, StaticVariables.title)).
			put().
		then().
			statusCode(400).
			body("error", equalTo(StaticVariables.warningMsg7));
	}	
}
