package pageobjectmodel;

public class PageObjects {
	//api root and path defined as constants because same path is used in all cases.
	public static final String API_ROOT = "http://www.noapi.com";
	public static final String API_PATH = "/api/stamps";
	
	//Warning messages
	public static String warningMsg1 = "Field 'title' and 'owner' are required";
	public static String warningMsg2 = "Field 'title' is required";
	public static String warningMsg3 = "Field 'owner' is required";
	public static String warningMsg4 = "Field 'title' and 'owner' cannot be empty";
	public static String warningMsg5 = "Field 'title' cannot be empty";
	public static String warningMsg6 = "Field 'owner' cannot be empty";
	public static String warningMsg7 = "Another stamp with similar title and owner already exists.";

}