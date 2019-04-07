package pageWebElements;

public class BlogPage {

	// web elements on blog page

	public static final String URL_BLOG = "http://localhost/izlet/dashboard.php";
	public static final String NEW_BLOG_BUTTON = "//a[contains(text(),'Make a post')]";
	public static final String NEW_BLOG_NAME = "//input[@placeholder='Naziv']";
	public static final String NEW_BLOG_LOCATION = "//input[@placeholder='Lokacija']";
	public static final String NEW_BLOG_TRANSPORT = "//select[@name='transport']";
	public static final String NEW_BLOG_DESCRIPTION = "//textarea[@placeholder='Opis']";
	public static final String NEW_BLOG_POST_BUTTON = "//input[@value='Post']";
	public static final String LOGOUT_BUTTON = "//a[@id='logoutBtn']";
	public static final String BROWSE_BUTTON = "//label[@class='custom-file-upload']";
	public static final String POST_BY_USER = "userBtn";
	public static final String EXTRA_OPTION = "//i[@class='fas fa-ellipsis-v']";
	public static final String DELETE_BUTTON = "//i[@class='fas fa-trash-alt']";
	public static final String EDIT_BUTTON = "//i[@class='fas fa-edit']";
	public static final String CSS_FOR_EXTRA_OPTION = "fas fa-ellipsis-v";
	public static final String BLOG_NAME_EDIT = "//input[@id='title']";
	public static final String BLOG_LOCATION_EDIT = "//input[@id='location']";
	public static final String BLOG_TRANSPORT_EDIT = "//select[@id='transport']";
	public static final String BLOG_DESCRIPTION_EDIT = "//textarea[@id='description']";
	public static final String BLOG_POST_BUTTON_EDIT = "//div[@class='popupEdit']//input[@value='Post']";
	public static final String LOGO = "//img[@src='images/izlet_logo.svg']";
	public static final String PROFILE_BUTTON = "//div[@id='profileBtn']";
	public static final String DASHBOARD_BUTTON = "//div[@id='dashboardBtn']";
	public static final String NEW_POST_BUTTON = "//div[@id='newPostBtn']";

}