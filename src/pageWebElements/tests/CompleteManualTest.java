package pageWebElements.tests;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import pageWebElements.BlogPage;
import pageWebElements.HomePage;
import utility.ExcelUtils;
import utility.PathsConfig;
import utility.WebElementConfig;

public class CompleteManualTest {

	private static WebDriver driver = new FirefoxDriver();
	private static String typeOfTransport = "";
	private static String typeOfTransportEdit = "";
	private static String email = "";

	// complete manual test
	public static void completeManualTest() throws Exception {
		try {
			// open page in browser
			WebElementConfig.openPage(driver, HomePage.URL_HOMEPAGE);

			// checking if the links posted on the page are correct and writes in excel
			WebElementConfig.verifyAllLinks(driver);
			writeLinks();

			// start manual test
			completeManualTesting();

		} catch (Exception e) {
			throw e;
		} finally {
			driver.quit();
		}
	}

	// manually fill the fields for registering, login, creating, editing and
	// deleting a blog from excel
	private static void completeManualTesting() throws Exception {

		// minimize browser window
		WebElementConfig.minimizeWindow(driver);

		Scanner scan = new Scanner(System.in);

		// ----------------- manual data insert -----------------
		// ---------------- register user ---------------------
		System.out.println("Enter your first name");
		String firstName = scan.next();

		System.out.println("Enter your last name");
		String lastName = scan.next();

		System.out.println("Enter your username");
		String userName = scan.next();

		// check entered email
		getEmail();
		
		System.out.println("Enter password");
		String password = scan.next();

		// maximize browser window
		WebElementConfig.maximizeWindow(driver);
		WebElementConfig.waitThread(1000);

		// get focus on browser window
		WebElementConfig.focusOnWindow(driver);

		// imitation alt + tab
		WebElementConfig.altTab();

		WebElementConfig.waitThread(1500);

		// input values in fields
		WebElementConfig.fillWebElement(driver, HomePage.FIRSTNAME_INPUTFIELD, firstName);
		WebElementConfig.fillWebElement(driver, HomePage.LASTNAME_INPUTFIELD, lastName);
		WebElementConfig.fillWebElement(driver, HomePage.USERNAME_INPUTFIELD, userName);
		WebElementConfig.fillWebElement(driver, HomePage.EMAIL_INPUTFIELD, email);
		WebElementConfig.fillWebElement(driver, HomePage.PASSWORD_INPUTFIELD, password);

		WebElementConfig.waitThread(2000);
		WebElementConfig.clickWebElement(driver, HomePage.SUBMIT_BUTTON);
		// ----------------- end registration ---------------------------------

		// ---------------------- login ---------------------------------------
		WebElementConfig.minimizeWindow(driver);

		System.out.println("Enter username for login");
		String userNameForLogin = scan.next();

		System.out.println("Enter password for login");
		String passwordForLogin = scan.next();

		WebElementConfig.waitThread(2000);

		WebElementConfig.maximizeWindow(driver);
		WebElementConfig.waitThread(1000);

		WebElementConfig.focusOnWindow(driver);
		WebElementConfig.altTab();

		WebElementConfig.fillWebElement(driver, HomePage.USERNAME_SIGNIN, userNameForLogin);
		WebElementConfig.fillWebElement(driver, HomePage.PASSWORD_SIGNIN, passwordForLogin);
		WebElementConfig.waitThread(3000);

		WebElementConfig.clickWebElement(driver, HomePage.BUTTON_SIGNIN);
		WebElementConfig.waitThread(3000);

		// checking if login successful
		while (true) {

			if (driver.getCurrentUrl().equals(HomePage.UNSUCCESSFUL_LOGIN)) {
				System.out.println("Bad credentials for login, try again...");
				WebElementConfig.openPage(driver, HomePage.URL_HOMEPAGE);
				logInWithScanner();
				continue;
			} else {
				break;
			}
		}
		// --------------------- end login ---------------------------------

		// --------------------- create new blog ---------------------------
		WebElementConfig.clickWebElement(driver, BlogPage.NEW_BLOG_BUTTON);
		WebElementConfig.waitThread(2000);

		WebElementConfig.minimizeWindow(driver);

		System.out.println("Enter blog name");
		String blog = scan.next();
		String blogName = scan.nextLine();

		System.out.println("Enter location name");
		String locationName = scan.nextLine();

		// check entered values for transport
		inputTypeOfTransport();

		// choose from four options for picture
		System.out.println("Choose picture: 1-3");

		System.out.println("1. First picture");
		System.out.println("2. Second picture");
		System.out.println("3. Third picture");

		int pictureNum = scan.nextInt();
		String picture1 = "C:\\Users\\Korisnik\\final_project\\Final_Project\\src\\data\\1.jpg";
		String picture2 = "C:\\Users\\Korisnik\\final_project\\Final_Project\\src\\data\\2.jpg";
		String picture3 = "C:\\Users\\Korisnik\\final_project\\Final_Project\\src\\data\\3.jpg";
		String picture;

		if (pictureNum == 1) {
			picture = picture1;
		} else if (pictureNum == 2) {
			picture = picture2;
		} else {
			picture = picture3;
		}

		System.out.println("Enter discrption");
		String desc = scan.next();
		String description = scan.nextLine();

		WebElementConfig.maximizeWindow(driver);
		WebElementConfig.focusOnWindow(driver);
		WebElementConfig.altTab();

		// input values in fields
		WebElementConfig.fillWebElement(driver, BlogPage.NEW_BLOG_NAME, blog + blogName);

		WebElementConfig.fillWebElement(driver, BlogPage.NEW_BLOG_LOCATION, locationName);

		WebElementConfig.selectDropMenu(driver, typeOfTransport, BlogPage.NEW_BLOG_TRANSPORT);
		System.out.println("-");
		driver.findElement(By.id("image")).sendKeys(picture);
		System.out.println("--");
		WebElementConfig.fillWebElement(driver, BlogPage.NEW_BLOG_DESCRIPTION, desc + description);
		System.out.println("---");
		WebElementConfig.waitThread(1000);
		WebElementConfig.clickWebElement(driver, BlogPage.NEW_BLOG_POST_BUTTON);
		WebElementConfig.waitThread(2000);

		System.out.println("username: " + userName);

		// verifies if user have post
		List<String> stringFromWebElements = WebElementConfig.getStringListFromId(driver, BlogPage.POST_BY_USER);
		String user = "";
		for (int k = 0; k < stringFromWebElements.size(); k++) {
			if (stringFromWebElements.get(k).equals(userName)) {
				user = stringFromWebElements.get(k);
			}
		}
		if (user.equals(userName)) {
			System.out.println("post was created");
		} else {
			System.out.println("post was not created");
		}

		WebElementConfig.waitThread(1000);
		WebElementConfig.findJavaScriptVoidAndClick(driver, BlogPage.EXTRA_OPTION);
		WebElementConfig.waitThread(1000);

		// ------------------ edit blog -----------------------------------------
		WebElementConfig.findJavaScriptVoidAndClick(driver, BlogPage.EDIT_BUTTON);
		WebElementConfig.waitThread(1000);

		WebElementConfig.minimizeWindow(driver);

		System.out.println("Enter new blog name");
		String blogEdit = scan.next();
		String blogNameEdit = scan.nextLine();

		System.out.println("Enter new location");
		String blogLocationEdit = scan.nextLine();

		// check entered values for transport
		inputTypeOfTransport();
		
		// choose from four options for picture
		System.out.println("Choose picture: 1-3");

		System.out.println("1. First picture");
		System.out.println("2. Second picture");
		System.out.println("3. Third picture");

		int pictureNumEdit = scan.nextInt();

		if (pictureNumEdit == 1) {
			picture = picture1;
		} else if (pictureNumEdit == 2) {
			picture = picture2;
		} else if (pictureNumEdit == 3) {
			picture = picture3;
		}

		System.out.println("Enter new description of location");
		String descEdit = scan.next();
		String descriptionEdit = scan.nextLine();

		WebElementConfig.maximizeWindow(driver);
		WebElementConfig.focusOnWindow(driver);
		WebElementConfig.altTab();

		// input values in fields
		WebElementConfig.findWebElement(driver, BlogPage.BLOG_NAME_EDIT).clear();
		WebElementConfig.fillWebElement(driver, BlogPage.BLOG_NAME_EDIT, blogEdit + blogNameEdit);

		WebElementConfig.findWebElement(driver, BlogPage.BLOG_LOCATION_EDIT).clear();
		WebElementConfig.fillWebElement(driver, BlogPage.BLOG_LOCATION_EDIT, blogLocationEdit);

		driver.findElement(By.id("editImage")).sendKeys(picture);

		WebElementConfig.selectDropMenu(driver, typeOfTransport, BlogPage.BLOG_TRANSPORT_EDIT);

		WebElementConfig.findWebElement(driver, BlogPage.BLOG_DESCRIPTION_EDIT).clear();
		WebElementConfig.fillWebElement(driver, BlogPage.BLOG_DESCRIPTION_EDIT, descEdit + descriptionEdit);

		WebElementConfig.waitThread(1500);
		WebElementConfig.clickWebElement(driver, BlogPage.BLOG_POST_BUTTON_EDIT);

		// --------------------------- end edit blog ------------------------------
		
		WebElementConfig.waitThread(1000);
		WebElementConfig.findJavaScriptVoidAndClick(driver, BlogPage.EXTRA_OPTION);
		WebElementConfig.waitThread(1000);

		WebElementConfig.findJavaScriptVoidAndClick(driver, BlogPage.DELETE_BUTTON);
		WebElementConfig.waitThread(3000);

		// verifies if delete was successful
		List<WebElement> elems = WebElementConfig.getListOfWebElementsByCssSelector(driver,
				BlogPage.CSS_FOR_EXTRA_OPTION);
		if (elems.size() > 0) {
			System.out.println("Delete was unsuccessful");
		} else {
			System.out.println("Delete was successful");
		}
		WebElementConfig.waitThread(1000);
		WebElementConfig.clickWebElement(driver, BlogPage.LOGOUT_BUTTON);
		WebElementConfig.waitThread(3000);
	}

	// checking if the links posted on the page are correct and writes in excel
	private static void writeLinks() throws Exception {
		ExcelUtils.setExcelFile(PathsConfig.PATH + PathsConfig.FILENAME, "Links_HomePage");
		for (int i = 0; i < WebElementConfig.allLinks.size(); i++) {
			ExcelUtils.writeToWorkSheet(WebElementConfig.allLinks.get(i), i, 0);
		}
	}

	// checks input string for type of transport
	private static void getTypeOfTranspor(String string) {

		if (string.equals("Car")) {
			typeOfTransport = string;
		} else if (string.equals("Bus")) {
			typeOfTransport = string;
		} else if (string.equals("Walk")) {
			typeOfTransport = string;
		} else if (string.equals("Bicycle")) {
			typeOfTransport = string;
		} else if (string.equals("Motorbike")) {
			typeOfTransport = string;
		} else {
			inputTypeOfTransport();
		}
	}

	// get type of transport
	private static void inputTypeOfTransport() {
		Scanner scan = new Scanner(System.in);

		System.out.println("Enter type of transport: Car, Bus, Walk, Bicycle or Motorbike");
		String typeOfTransport2 = scan.nextLine();
		// convert all letters of string to lower case
		typeOfTransport2.toLowerCase();
		// convert first letter to upper case
		typeOfTransport = typeOfTransport2.substring(0, 1).toUpperCase() + typeOfTransport2.substring(1);

		getTypeOfTranspor(typeOfTransport);
	}

	// checks if entered email is valid
	private static boolean checkEmail(String email) {

		String regex = "[a-zA-Z0-9]+[._a-zA-Z0-9!#$%&'*+-/=?^_`{|}~]*[a-zA-Z]*@[a-zA-Z0-9]{2,8}.[a-zA-Z.]{2,6}";

		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(email);
		boolean goodEmail = matcher.matches();

		if (goodEmail) {
			return true;
		} else {
			return false;
		}
	}

	// input email
	private static void getEmail() {

		Scanner scan = new Scanner(System.in);

		System.out.println("Enter email");
		String emailInput = scan.nextLine();

		if (checkEmail(emailInput)) {
			email = emailInput;
		} else {
			getEmail();
		}

	}

	// manually fill the fields for login in eclipse console
	private static void logInWithScanner() throws Exception {

		Scanner scan = new Scanner(System.in);

		// minimize browser window
		WebElementConfig.minimizeWindow(driver);

		System.out.println("Enter username for login");
		String userNameForLogin = scan.next();

		System.out.println("Enter password for login");
		String passwordForLogin = scan.next();

		WebElementConfig.waitThread(2000);

		WebElementConfig.maximizeWindow(driver);
		WebElementConfig.waitThread(1000);

		WebElementConfig.focusOnWindow(driver);
		WebElementConfig.altTab();

		WebElementConfig.fillWebElement(driver, HomePage.USERNAME_SIGNIN, userNameForLogin);
		WebElementConfig.fillWebElement(driver, HomePage.PASSWORD_SIGNIN, passwordForLogin);
		WebElementConfig.waitThread(3000);

		WebElementConfig.clickWebElement(driver, HomePage.BUTTON_SIGNIN);
		WebElementConfig.waitThread(1500);

		// checking if login successful
		while (true) {

			if (driver.getCurrentUrl().equals(HomePage.UNSUCCESSFUL_LOGIN)) {

				WebElementConfig.openPage(driver, HomePage.URL_HOMEPAGE);
				logInWithScanner();
				continue;
			} else {
				break;
			}
		}
		WebElementConfig.waitThread(3000);
	}
}
