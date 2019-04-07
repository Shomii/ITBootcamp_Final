package pageWebElements.tests;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import pageWebElements.BlogPage;
import pageWebElements.HomePage;
import utility.ExcelUtils;
import utility.PathsConfig;
import utility.WebElementConfig;

public class TestHomePage {

	private static WebDriver driver = new FirefoxDriver();
	private static String email = "";

	// manually fill the fields for registering and login in eclipse console
	public static void homePageTestManual() throws Exception {

		try {
			// open page in browser
			WebElementConfig.openPage(driver, HomePage.URL_HOMEPAGE);
			WebElementConfig.waitThread(2500);

			// checking if the links posted on the page are correct and writes in excel
			WebElementConfig.verifyAllLinks(driver);
			writeLinks();

			// checking if the links posted on the page are correct and writes in excel
			checkIfElementsIsPresentOnPage();

			// start manual register test
			registerWithScanner();

			// start manual login test
			logInWithScanner();

		} catch (Exception e) {
			throw e;
		} finally {
			driver.quit();
		}
	}

	// automatically fill the fields for registering and login from excel
	public static void homePageTestAutomate() throws Exception {

		try {
			// open page in browser
			WebElementConfig.openPage(driver, HomePage.URL_HOMEPAGE);
			WebElementConfig.waitThread(2500);

			// checking if the links posted on the page are correct and writes in excel
			WebElementConfig.verifyAllLinks(driver);
			writeLinks();

			// maximize browser window
			WebElementConfig.maximizeWindow(driver);

			// checking whether the elements are present on the page and writes to excel
			checkIfElementsIsPresentOnPage();

			// setting excel sheet for register user
			ExcelUtils.setExcelFile(PathsConfig.PATH + PathsConfig.FILENAME, PathsConfig.SHEET_NAME_FOR_REGISTRATION);

			// get data from excel
			ExcelUtils.getAllValuesFromExcel();

			// start automated test
			registeAndLoginAllUsers();

		} catch (Exception e) {
			throw e;
		} finally {
			driver.quit();
		}
	}

	// automatically fill the fields for registering and login from excel
	private static void registeAndLoginAllUsers() throws Exception {

		int j = 0;
		for (int i = 0; i < ExcelUtils.allValues.size(); i++) {

			// ---------- register new user ----------------------
			// first name in register form
			String fName = ExcelUtils.allValues.get(j);
			WebElementConfig.fillWebElement(driver, HomePage.FIRSTNAME_INPUTFIELD, fName);

			// last name
			String lName = ExcelUtils.allValues.get(j + 1);
			WebElementConfig.fillWebElement(driver, HomePage.LASTNAME_INPUTFIELD, lName);

			// user name
			String userName = ExcelUtils.allValues.get(j + 2);
			WebElementConfig.fillWebElement(driver, HomePage.USERNAME_INPUTFIELD, userName);

			// email
			String email = ExcelUtils.allValues.get(j + 3);
			WebElementConfig.fillWebElement(driver, HomePage.EMAIL_INPUTFIELD, email);

			// password
			String password = ExcelUtils.allValues.get(j + 4);
			WebElementConfig.fillWebElement(driver, HomePage.PASSWORD_INPUTFIELD, password);

			WebElementConfig.clickWebElement(driver, HomePage.SUBMIT_BUTTON);
			WebElementConfig.waitThread(1000);
			// ------------- end registration ------------------

			// -------------- login -------------------------
			// checking if register is successful
			// user name
			String userNameSignIn = ExcelUtils.allValues.get(j + 2);
			WebElementConfig.fillWebElement(driver, HomePage.USERNAME_SIGNIN, userNameSignIn);

			// password
			String passwordSignIn = ExcelUtils.allValues.get(j + 4);
			WebElementConfig.fillWebElement(driver, HomePage.PASSWORD_SIGNIN, passwordSignIn);

			WebElementConfig.clickWebElement(driver, HomePage.BUTTON_SIGNIN);
			// ------------------- end login ---------------------

			WebElementConfig.waitThread(3000);

			// writing in excel sheet if register and login is successful
			String result;
			if (driver.getCurrentUrl().equals(BlogPage.URL_BLOG)) {
				result = "register and login with username: " + userNameSignIn + " -  successful";
				System.out.println(result);
				ExcelUtils.setCellData(result, (i + 1), 7);
			} else {
				result = "register and login with username: " + userNameSignIn + " -  unsuccessful";
				System.out.println(result);
				ExcelUtils.setCellData(result, (i + 1), 7);
			}

			WebElementConfig.clickWebElement(driver, BlogPage.LOGOUT_BUTTON);
			j += ExcelUtils.numberOfCellsInRow;
			WebElementConfig.waitThread(2000);
		}
	}

	// checking whether the elements are present on the page and writes to excel
	private static void checkIfElementsIsPresentOnPage() throws Exception {
		
		// check if elements are present on page
		boolean password = WebElementConfig.isDisplayed(driver, HomePage.PASSWORD_SIGNIN);
		boolean username = WebElementConfig.isDisplayed(driver, HomePage.USERNAME_SIGNIN);
		boolean gallery = WebElementConfig.isDisplayed(driver, HomePage.GALLERY_BUTTON);
		boolean signin = WebElementConfig.isDisplayed(driver, HomePage.BUTTON_SIGNIN);
		boolean fname = WebElementConfig.isDisplayed(driver, HomePage.FIRSTNAME_INPUTFIELD);
		boolean lname = WebElementConfig.isDisplayed(driver, HomePage.LASTNAME_INPUTFIELD);
		boolean uname = WebElementConfig.isDisplayed(driver, HomePage.USERNAME_INPUTFIELD);
		boolean email = WebElementConfig.isDisplayed(driver, HomePage.EMAIL_INPUTFIELD);
		boolean pass = WebElementConfig.isDisplayed(driver, HomePage.PASSWORD_INPUTFIELD);
		boolean submit = WebElementConfig.isDisplayed(driver, HomePage.SUBMIT_BUTTON);
		boolean contactUs = WebElementConfig.isDisplayed(driver, HomePage.BUTTON_CONTACT_US);

		boolean[] allElements = new boolean[] { password, username, gallery, signin, fname, lname, uname, email, pass,
				submit, contactUs };

		String[] allNamesOfElements = new String[] { HomePage.PASSWORD_SIGNIN, HomePage.USERNAME_SIGNIN,
				HomePage.GALLERY_BUTTON, HomePage.BUTTON_SIGNIN, HomePage.FIRSTNAME_INPUTFIELD,
				HomePage.LASTNAME_INPUTFIELD, HomePage.USERNAME_INPUTFIELD, HomePage.EMAIL_INPUTFIELD,
				HomePage.PASSWORD_INPUTFIELD, HomePage.SUBMIT_BUTTON, HomePage.BUTTON_CONTACT_US };

		ExcelUtils.setExcelFile(PathsConfig.PATH + PathsConfig.FILENAME, "ElementsOnHomePage");
		
		// write in excel if elements are present on page
		for (int i = 0; i < allElements.length; i++) {
			if (allElements[i] == true) {
				ExcelUtils.writeToWorkSheet("Element -   " + allNamesOfElements[i] + "   - is present on Home Page", i, 1);
			} else {
				ExcelUtils.writeToWorkSheet("Element -   " + allNamesOfElements[i] + "   -  is not present on Home Page", i, 1);
			}
		}
	}

	// manually fill the fields for registering in eclipse console
	private static void registerWithScanner() throws Exception {

		// scroll down to first name input field
		WebElementConfig.scrollDownToWebElement(driver, HomePage.FIRSTNAME_INPUTFIELD);

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
				System.out.println("Bad credentials for login, try again...");
				WebElementConfig.openPage(driver, HomePage.URL_HOMEPAGE);
				logInWithScanner();
				continue;
			} else {
				break;
			}
		}
		WebElementConfig.waitThread(3000);
	}

	// checking if the links posted on the page are correct and writes in excel
	private static void writeLinks() throws Exception {

		ExcelUtils.setExcelFile(PathsConfig.PATH + PathsConfig.FILENAME, "Links_HomePage");

		for (int i = 0; i < WebElementConfig.allLinks.size(); i++) {
			ExcelUtils.writeToWorkSheet(WebElementConfig.allLinks.get(i), i, 0);
		}
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
}
