package pageWebElements.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import pageWebElements.BlogPage;
import pageWebElements.HomePage;
import utility.ExcelUtils;
import utility.PathsConfig;
import utility.WebElementConfig;

public class TestBlogPage {

	private static WebDriver driver = new FirefoxDriver();
	private static String typeOfTransport = "";
	private static String typeOfTransportEdit = "";

	// manually create, edit and delete a blog from excel
	public static void blogPageTestManual() throws Exception {

		try {

			createOneNewBlogWithScanner();

			WebElementConfig.waitThread(5000);
		} catch (Exception e) {
			throw e;
		} finally {
			driver.quit();
		}
	}

	// automatically create, edit and delete a blog from excel
	public static void blogPageTestAutomate() throws Exception {

		try {

			createNewBlogs();

			WebElementConfig.waitThread(5000);
		} catch (Exception e) {
			throw e;
		} finally {
			driver.quit();
		}
	}

	// automatically create, edit and delete a blog from excel
	private static void createNewBlogs() throws Exception {
		// open page in browser
		WebElementConfig.openPage(driver, HomePage.URL_HOMEPAGE);

		// checking if the links posted on the page are correct and writes in excel
		WebElementConfig.verifyAllLinks(driver);
		writeLinks();

		// maximize browser window
		WebElementConfig.maximizeWindow(driver);

		// setting excel sheet to login user
		ExcelUtils.setExcelFile(PathsConfig.PATH + PathsConfig.FILENAME, PathsConfig.SHEET_NAME_FOR_REGISTRATION);

		// get data from excel
		ExcelUtils.getAllValuesFromExcel();

		int j = 0;
		for (int i = 1; i < ExcelUtils.allValues.size(); i++) {

			WebElementConfig.waitThread(3000);

			// get user name from excel and input values
			String userNameSignIn = ExcelUtils.allValues.get(j + 2);
			System.out.println("username: " + userNameSignIn);
			WebElementConfig.fillWebElement(driver, HomePage.USERNAME_SIGNIN, userNameSignIn);

			// get password from excel and input values
			String passwordSignIn = ExcelUtils.allValues.get(j + 4);
			WebElementConfig.fillWebElement(driver, HomePage.PASSWORD_SIGNIN, passwordSignIn);
			WebElementConfig.clickWebElement(driver, HomePage.BUTTON_SIGNIN);
			WebElementConfig.waitThread(2000);

			// setting excel sheet for creating blog
			ExcelUtils.setExcelFile(PathsConfig.PATH + PathsConfig.FILENAME, PathsConfig.SHEET_NAME_FOR_COMMENT);

			// verifies all the links on the page and writes it in an excel
			WebElementConfig.verifyAllLinks(driver);
			writeLinks();

			// setting excel sheet for creating a blog
			ExcelUtils.setExcelFile(PathsConfig.PATH + PathsConfig.FILENAME, PathsConfig.SHEET_NAME_FOR_COMMENT);

			WebElementConfig.clickWebElement(driver, BlogPage.NEW_BLOG_BUTTON);

			// get data from excel - post name
			String newPost = ExcelUtils.getCellData(i + 1, 1);
			WebElementConfig.fillWebElement(driver, BlogPage.NEW_BLOG_NAME, newPost);

			// get data from excel - location
			String location = ExcelUtils.getCellData(i + 1, 2);
			WebElementConfig.fillWebElement(driver, BlogPage.NEW_BLOG_LOCATION, location);

			// random picture for blog
			List<String> allPicures = new ArrayList<String>();
			allPicures.add("C:\\Users\\Korisnik\\final_project\\Final_Project\\src\\data\\1.jpg");
			allPicures.add("C:\\Users\\Korisnik\\final_project\\Final_Project\\src\\data\\2.jpg");
			allPicures.add("C:\\Users\\Korisnik\\final_project\\Final_Project\\src\\data\\4.jpg");
			String picture = WebElementConfig.randomStringFromList(allPicures);
			driver.findElement(By.id("image")).sendKeys(picture);

			// random type of transport
			List<String> typeOfTransportList = new ArrayList<String>();
			typeOfTransportList.add("Car");
			typeOfTransportList.add("Bus");
			typeOfTransportList.add("Walk");
			typeOfTransportList.add("Bicycle");
			typeOfTransportList.add("Motorbike");
			String typeOfTransport = WebElementConfig.randomStringFromList(typeOfTransportList);
			WebElementConfig.selectDropMenu(driver, typeOfTransport, BlogPage.NEW_BLOG_TRANSPORT);

			// get data from excel - description
			String description = ExcelUtils.getCellData(i + 1, 0);
			WebElementConfig.fillWebElement(driver, BlogPage.NEW_BLOG_DESCRIPTION, description);

			WebElementConfig.clickWebElement(driver, BlogPage.NEW_BLOG_POST_BUTTON);
			// ---------------- end create new blog --------------------

			WebElementConfig.waitThread(3000);

			// verifies if user have post
			List<String> stringFromWebElements = WebElementConfig.getStringListFromId(driver, BlogPage.POST_BY_USER);
			String user = "";
			for (int k = 0; k < stringFromWebElements.size(); k++) {
				if (stringFromWebElements.get(k).equals(userNameSignIn)) {
					user = stringFromWebElements.get(k);
				}
			}
			if (user.equals(userNameSignIn)) {
				System.out.println("post was created");
			} else {
				System.out.println("post was not created");
			}

			WebElementConfig.waitThread(1000);

			// ------------------ edit blog -----------------------------
			WebElementConfig.findJavaScriptVoidAndClick(driver, BlogPage.EXTRA_OPTION);
			WebElementConfig.waitThread(1000);

			WebElementConfig.findJavaScriptVoidAndClick(driver, BlogPage.EDIT_BUTTON);
			WebElementConfig.waitThread(1000);

			ExcelUtils.setExcelFile(PathsConfig.PATH + PathsConfig.FILENAME, PathsConfig.SHEET_NAME_FOR_COMMENT);

			// clear post name, get data from excel and enters new post name
			WebElementConfig.findWebElement(driver, BlogPage.BLOG_NAME_EDIT).clear();
			String newPostEdit = ExcelUtils.getCellData(i + 1, 1);
			WebElementConfig.fillWebElement(driver, BlogPage.BLOG_NAME_EDIT, newPostEdit);

			// clear location, get data from excel and enters new location
			WebElementConfig.findWebElement(driver, BlogPage.BLOG_LOCATION_EDIT).clear();
			String locationEdit = ExcelUtils.getCellData(i + 1, 2);
			WebElementConfig.fillWebElement(driver, BlogPage.BLOG_LOCATION_EDIT, locationEdit);

			// random picture
			String pictureEdit = WebElementConfig.randomStringFromList(allPicures);
			driver.findElement(By.id("editImage")).sendKeys(pictureEdit);

			// random type of transport
			String typeOfTransportEdit = WebElementConfig.randomStringFromList(typeOfTransportList);
			WebElementConfig.selectDropMenu(driver, typeOfTransportEdit, BlogPage.BLOG_TRANSPORT_EDIT);

			// clear description, get data from excel and enters new description
			WebElementConfig.findWebElement(driver, BlogPage.BLOG_DESCRIPTION_EDIT).clear();
			String descriptionEdit = ExcelUtils.getCellData(i + 1, 0);
			WebElementConfig.fillWebElement(driver, BlogPage.BLOG_DESCRIPTION_EDIT, descriptionEdit);

			WebElementConfig.waitThread(1500);
			WebElementConfig.clickWebElement(driver, BlogPage.BLOG_POST_BUTTON_EDIT);
			// ---------------------- end edit blog -------------------------
			WebElementConfig.waitThread(1000);

			// ----------------- delete blog -----------------------------
			WebElementConfig.findJavaScriptVoidAndClick(driver, BlogPage.EXTRA_OPTION);
			WebElementConfig.waitThread(1000);

			WebElementConfig.findJavaScriptVoidAndClick(driver, BlogPage.DELETE_BUTTON);
			WebElementConfig.waitThread(1000);

			// verifies if delete was successful
			List<WebElement> elems = WebElementConfig.getListOfWebElementsByCssSelector(driver,
					BlogPage.CSS_FOR_EXTRA_OPTION);
			if (elems.size() > 0) {
				System.out.println("Delete was unsuccessful");
			} else {
				System.out.println("Delete was successful");
			}

			WebElementConfig.clickWebElement(driver, BlogPage.LOGOUT_BUTTON);
			j += ExcelUtils.numberOfCellsInRow;
			WebElementConfig.waitThread(3000);
		}
	}

	// manually fill the fields in eclipse console for creatin a blog
	private static void createOneNewBlogWithScanner() throws Exception {
		WebElementConfig.openPage(driver, HomePage.URL_HOMEPAGE);

		// minimize browser window
		WebElementConfig.minimizeWindow(driver);

		Scanner scan = new Scanner(System.in);

		// ----------------- manual data insert -----------------
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
		// --------------------- end login ---------------------------------

		// --------------------- create new blog ---------------------------
		checkIfElementsIsPresentOnPage();
		WebElementConfig.clickWebElement(driver, BlogPage.NEW_BLOG_BUTTON);
		WebElementConfig.waitThread(2000);

		WebElementConfig.minimizeWindow(driver);

		System.out.println("Enter blog name");
		String blog = scan.next();
		String blogName = scan.nextLine();

		System.out.println("Enter location name");
		String locationName = scan.nextLine();

		inputTypeOfTransport();

		// choose from four options for picture
		System.out.println("Choose picture: 1-3");

		System.out.println("1. First picture");
		System.out.println("2. Second picture");
		System.out.println("3. Third picture");

		int pictureNum = scan.nextInt();

		String picture1 = "C:\\Users\\Korisnik\\final_project\\Final_Project\\src\\data\\1.jpg";
		String picture2 = "C:\\Users\\Korisnik\\final_project\\Final_Project\\src\\data\\2.jpg";
		String picture3 = "C:\\Users\\Korisnik\\final_project\\Final_Project\\src\\data\\4.jpg";
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

		driver.findElement(By.id("image")).sendKeys(picture);

		WebElementConfig.fillWebElement(driver, BlogPage.NEW_BLOG_DESCRIPTION, desc + description);

		WebElementConfig.waitThread(1000);
		WebElementConfig.clickWebElement(driver, BlogPage.NEW_BLOG_POST_BUTTON);
		WebElementConfig.waitThread(2000);

		System.out.println("username: " + userNameForLogin);

		// verifies if user have post
		List<String> stringFromWebElements = WebElementConfig.getStringListFromId(driver, BlogPage.POST_BY_USER);
		String user = "";
		for (int k = 0; k < stringFromWebElements.size(); k++) {
			if (stringFromWebElements.get(k).equals(userNameForLogin)) {
				user = stringFromWebElements.get(k);
			}
		}
		if (user.equals(userNameForLogin)) {
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
		} else {
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
		ExcelUtils.setExcelFile(PathsConfig.PATH + PathsConfig.FILENAME, "Links_BlogPage");

		for (int i = 0; i < WebElementConfig.allLinks.size(); i++) {
			ExcelUtils.writeToWorkSheet(WebElementConfig.allLinks.get(i), i, 0);
		}
	}

	// checking whether the elements are present on the page and writes to excel
	private static void checkIfElementsIsPresentOnPage() throws Exception {

		// check if elements are present on page
		boolean logo = WebElementConfig.isDisplayed(driver, BlogPage.LOGO);
		boolean profile = WebElementConfig.isDisplayed(driver, BlogPage.PROFILE_BUTTON);
		boolean dashboard = WebElementConfig.isDisplayed(driver, BlogPage.DASHBOARD_BUTTON);
		boolean postButton = WebElementConfig.isDisplayed(driver, BlogPage.NEW_POST_BUTTON);
		boolean logout = WebElementConfig.isDisplayed(driver, BlogPage.LOGOUT_BUTTON);

		boolean[] allElements = new boolean[] { logo, profile, dashboard, postButton, logout };

		String[] allNamesOfElements = new String[] { BlogPage.LOGO, BlogPage.PROFILE_BUTTON, BlogPage.DASHBOARD_BUTTON,
				BlogPage.NEW_POST_BUTTON, BlogPage.LOGOUT_BUTTON };

		ExcelUtils.setExcelFile(PathsConfig.PATH + PathsConfig.FILENAME, "ElementsOnBlogPage");
	
		// write in excel if elements are present on page
		for (int i = 0; i < allElements.length; i++) {
			if (allElements[i] == true) {
				ExcelUtils.writeToWorkSheet("Element -   " + allNamesOfElements[i] + "   -  is present on Home Page", i, 1);
			} else {
				ExcelUtils.writeToWorkSheet("Element -   " + allNamesOfElements[i] + "   -  is not present on Home Page", i, 1);
			}
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
}
