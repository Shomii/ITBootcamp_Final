package pageWebElements.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import pageWebElements.GalleryPage;
import pageWebElements.HomePage;
import utility.WebElementConfig;

public class TestGalleryPage {

	private static WebDriver driver = new FirefoxDriver();

	// complete automated test of gallery page
	public static void testGallery() throws Exception {

		try {
			testingGallery();
			WebElementConfig.waitThread(5000);
		} catch (Exception e) {
			throw e;
		} finally {
			driver.quit();
		}
	}

	// gallery page test
	private static void testingGallery() throws Exception {

		// opening home page
		WebElementConfig.openPage(driver, HomePage.URL_HOMEPAGE);
		WebElementConfig.waitThread(1000);

		// opening gallery page
		WebElementConfig.clickWebElement(driver, GalleryPage.GALLERY_BUTTON_ON_HOMEPAGE);
		
		// maximize browser window
		WebElementConfig.maximizeWindow(driver);
		WebElementConfig.waitThread(2000);
		
		// zoom in
		WebElementConfig.zoomInRobot(driver, 4);

		// scrolling down
		WebElementConfig.slowlyscrollDown(driver);

		WebElementConfig.waitThread(2000);
		
		// zoom out
		WebElementConfig.zoomOutRobot(driver, 4);
		
		// scroll to element
		WebElementConfig.scrollDownToWebElement(driver, GalleryPage.PICTURE_NO_1);
		WebElementConfig.waitThread(1500);
		
		// click on back button
		WebElementConfig.clickWebElement(driver, GalleryPage.BACK_BUTTON);
		WebElementConfig.waitThread(2000);

	}
}
