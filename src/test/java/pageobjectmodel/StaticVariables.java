package pageobjectmodel;

public class StaticVariables {
	//api root and path defined as constants because same path is used in all cases.
	public static final String API_ROOT = "http://www.noapi.com";
	public static final String API_PATH = "/api/stamps/";
	
	//Warning messages
	public static String warningMsg1 = "Field 'title' and 'owner' are required";
	public static String warningMsg2 = "Field 'title' is required";
	public static String warningMsg3 = "Field 'owner' is required";
	public static String warningMsg4 = "Field 'title' and 'owner' cannot be empty";
	public static String warningMsg5 = "Field 'title' cannot be empty";
	public static String warningMsg6 = "Field 'owner' cannot be empty";
	public static String warningMsg7 = "Another stamp with similar title and owner already exists.";
	
	//default variables to send to PUT request
	public static int id = 1;
	public static String owner = "Ali Ertugrul";
	public static String title = "Lorem Ipsum";
	
	//function that will return request body for put requests.
	public static String getRequestBody(String owner, String title) {
		String requestBody = "{\"owner\": \"" + owner + "\", \"title\": \"" + title + "\"}";
		return requestBody;
	}
	
	//function that will return request body without some fields to test required fields on put requests.
	public static String getRequestBodyWithoutFields(String unnecassaryField) {
		String requestBody;
		if(unnecassaryField=="owner") {
			requestBody = "\"title\": \"" + title + "\"}";
			return requestBody;
		}
		
		else {
			requestBody = "\"owner\": \"" + owner + "\"}";
			return requestBody;
		}			
	}
	
	public static String requestBodyWithID = "{\"id\":" + StaticVariables.id + ",\"owner\": \"" + owner + "\", \"title\": \"" + title + "\"}";
}