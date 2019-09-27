package tests;

import static io.restassured.RestAssured.*;
import org.testng.annotations.Test;
import org.hamcrest.Matchers;
import static org.hamcrest.CoreMatchers.equalTo;
import pageobjectmodel.PageObjects;

/* 
1.	Assumption: The testing api is reset before each of test case run.
2.	Defined api url (www.noapi.com) is an imaginery url, so all cases will be failed when you run tests.
*/

public class ApiTest {
	
	//default variables to send to PUT request
	int id = 1;
	String owner = "Ali Ertugrul";
	String title = "Lorem Ipsum";
	
	//warning message that will change according to scenarios
	String warningMsg;
	
	//define baseURI and basePath in a constructor to get API_ROOT and API_PATH before test methods run.
	public ApiTest(){
		baseURI= PageObjects.API_ROOT;
		basePath = PageObjects.API_PATH;
	}
	
	//Verify that the api starts with an empty store.
	@Test
	public void verifyEmptyStore() {
		given().
		when().
			get().
		then().
			body("", Matchers.empty());
	}
	
	//Verify that title and owner are required fields.
	@Test
	public void verifyRequiredFields() {
		warningMsg = "Field 'title' and 'owner' are required";
		
		given().
		when().
			put().
		then().
			statusCode(400).
			body("error", equalTo(warningMsg));
	}
	
	//Verify that title and owner cannot be empty.
	@Test
	public void verifyNonEmptyFields() {
		warningMsg = "Field 'title' and 'owner' cannot be empty";
		
		given().
			pathParams("title", null, "owner", null).
		when().
			put().
		then().
			statusCode(400).
			body("error", equalTo(warningMsg));
	}
	
	//Verify that the id field is read only.
	@Test
	public void verifyIdReadOnly() {
		given().
			pathParam("id", id).
		when().
			put("/{id}/").
		then().
			statusCode(400);
	}
	
	//Verify that you can create a new stamp via PUT.
	@Test
	public void verifyNewStampIsCreatable() {
		//create stamp and check if created stamp will be returned in the response.
		//get id of new stamp for get request
		int id =
		given().
			pathParams("owner", owner, "title", title).
		when().
			put("/{owner}/{title}/").
		then().
			statusCode(200).
			body("id", equalTo(1)).
			body("owner", equalTo(owner)).
			body("title", equalTo(title)).
			extract().response().path("id");
		
		//get request using id of created stamp should return the same stamp.
		given().
			pathParam("id", id).
		when().
			get("/{id}/").
		then().
			statusCode(200).
			body("id", equalTo(id)).
			body("owner", equalTo(owner)).
			body("title", equalTo(title));
	}
	
	
	//Verify that you cannot create a duplicate stamp.
	@Test
	public void verifyDuplicateStampIsNotPossible() {
		warningMsg = "Another stamp with similar title and owner already exists.";
		
		//create first stamp
		String owner = "Ali Ertugrul";
		String title = "Lorem Ipsum";
		
		given().
			pathParams("owner", owner, "title", title).
		when().
			put("/{owner}/{title}/").
		then().
			statusCode(200);
		
		//Try to create second stamp with same owner and title data, check if you get error message
		given().
			pathParams("owner", owner, "title", title).
		when().
			put("/{owner}/{title}/").
		then().
			statusCode(400).
			body("error", equalTo(warningMsg));
	}	
}
