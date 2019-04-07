package pageWebElements.tests;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import pageWebElements.BlogPage;
import pageWebElements.HomePage;
import utility.ExcelUtils;
import utility.PathsConfig;
import utility.WebElementConfig;

public class CompleteAautomatedTest {

	private static WebDriver driver = new FirefoxDriver();

	// complete automated test
	public static void completeAutomatedTest() throws Exception {
		try {
			// open page in browser
			WebElementConfig.openPage(driver, HomePage.URL_HOMEPAGE);

			// checking if the links posted on the page are correct and writes in excel
			WebElementConfig.verifyAllLinks(driver);
			writeLinks();

			// maximize browser window
			WebElementConfig.maximizeWindow(driver);

			// setting excel sheet for register user
			ExcelUtils.setExcelFile(PathsConfig.PATH + PathsConfig.FILENAME, PathsConfig.SHEET_NAME_FOR_REGISTRATION);

			// get data from excel
			ExcelUtils.getAllValuesFromExcel();

			// start automated test
			completeAutomatedTestWork();

		} catch (Exception e) {
			throw e;
		} finally {
			driver.quit();
		}
	}

	// automatically fill the fields for registering, login, creating, editing and
	// deleting a blog from excel
	private static void completeAutomatedTestWork() throws Exception {

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
			ExcelUtils.setExcelFile(PathsConfig.PATH + PathsConfig.FILENAME, PathsConfig.SHEET_NAME_FOR_REGISTRATION);
			
			if (driver.getCurrentUrl().equals(BlogPage.URL_BLOG)) {
				result = "register and login with username: " + userNameSignIn + " -  successful";
				System.out.println(result);
				ExcelUtils.setCellData(result, (i + 1), 7);
			} else {
				result = "register and login with username: " + userNameSignIn + " -  unsuccessful";
				System.out.println(result);
				ExcelUtils.setCellData(result, (i + 1), 7);
			}

			WebElementConfig.waitThread(2000);

			// ---------------- create new blog --------------------

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
			// WebElementConfig.waitThread(1500);
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
			WebElementConfig.waitThread(2000);
		}

	}

	// checking if the links posted on the page are correct and writes in excel
	private static void writeLinks() throws Exception {
		ExcelUtils.setExcelFile(PathsConfig.PATH + PathsConfig.FILENAME, "Links_HomePage");
		for (int i = 0; i < WebElementConfig.allLinks.size(); i++) {
			ExcelUtils.writeToWorkSheet(WebElementConfig.allLinks.get(i), i, 0);
		}
	}
}
