package pageWebElements;

public class HomePage {

	// web elements on home page

	public static final String URL_HOMEPAGE = "http://localhost/izlet/index.php";
	public static final String FIRSTNAME_INPUTFIELD = "//input[@name='firstname']";
	public static final String GALLERY_BUTTON = "//nav[@class='main-nav']//a[@class='a_bold'][contains(text(),'Galerija')]";
	public static final String LASTNAME_INPUTFIELD = "//input[@name='lastname']";
	public static final String USERNAME_INPUTFIELD = "//form[@action='processregister.php']//input[@name='username']";
	public static final String EMAIL_INPUTFIELD = "//input[@name='email']";
	public static final String PASSWORD_INPUTFIELD = "//form[@action='processregister.php']//input[@name='password']";
	public static final String SUBMIT_BUTTON = "//input[@id='blue_btn']";
	public static final String USERNAME_SIGNIN = "//input[@placeholder='username']";
	public static final String PASSWORD_SIGNIN = "//input[@placeholder='password']";
	public static final String BUTTON_SIGNIN = "//input[@value='Log in']";
	public static final String BUTTON_CONTACT_US = "//a[@id='contact_btn']";
	public static final String UNSUCCESSFUL_LOGIN = "http://localhost/izlet/processlogin.php";

}
