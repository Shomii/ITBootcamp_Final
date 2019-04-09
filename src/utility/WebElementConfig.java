package utility;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 
 * klasa sa metodama potrebnim za testiranje
 *
 */

public class WebElementConfig {

	private static WebElement element = null;
	public static List<String> allLinks = new ArrayList<String>();

	public static void openPage(WebDriver driver, String urlPage) {

		driver.get(urlPage);
	}

	public static WebElement findWebElement(WebDriver driver, String xpathInput) {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		try {
			element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathInput)));
		} catch (Exception e) {
			throw e;
		}
		return element;
	}

	public static WebElement findElementById(WebDriver driver, String id) {
		try {
			return driver.findElement(By.id(id));
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	public static WebElement findWebElementWithoutWait(WebDriver driver, String xpathInput) {

		try {
			element = driver.findElement(By.xpath(xpathInput));
		} catch (Exception e) {
			throw e;
		}
		return element;
	}

	public static WebElement findWebElementByCssSelector(WebDriver driver, String xpathInput) {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		try {
			element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(xpathInput)));
		} catch (Exception e) {
			throw e;
		}
		return element;
	}

	public static WebElement findWebElementByCssSelectorWithOutWait(WebDriver driver, String xpathInput) {

		try {
			element = driver.findElement(By.cssSelector(xpathInput));
		} catch (Exception e) {
			throw e;
		}
		return element;
	}

	public static void clickWebElement(WebDriver driver, String xpathInput) {

		findWebElement(driver, xpathInput).click();
	}

	public static void fillWebElement(WebDriver driver, String xpathInput, String fillValue) {

		findWebElement(driver, xpathInput).clear();
		findWebElement(driver, xpathInput).sendKeys(fillValue);
	}

	public static void fillWebElementWithList(WebDriver driver, String xpathInput, List<String> fillValue) {

		findWebElement(driver, xpathInput).clear();
		for (int i = 0; i <= fillValue.size() - 1; i++) {
			findWebElement(driver, xpathInput).sendKeys(fillValue.get(i));
		}
		// findWebElement(driver, xpathInput).sendKeys(fillValue);
	}

	public static void scrollDownPage(JavascriptExecutor jsx, WebDriver driver) {

		jsx = (JavascriptExecutor) driver;
		// jsx.executeScript("window.scrollBy(0, 300)", "");
		jsx.executeScript("window.scrollTo(0,document.body.scrollHeight);");
	}

	public static void focusOnWindow(WebDriver driver) {

		((JavascriptExecutor) driver).executeScript("window.focus();");
	}

	public static void waitThread(long duration) throws Exception {
		try {
			Thread.sleep(duration);
		} catch (Exception e) {
			throw e;
		}
	}

	public static void scrollDownToWebElement(WebDriver driver, String xpathInput) throws Exception {

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
				findWebElement(driver, xpathInput));
	}

	public static void slowlyscrollDown(WebDriver driver) throws Exception {

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		for (int second = 0;; second++) {
			if (second >= 60) {
				break;
			}
			jse.executeScript("window.scrollBy(0,10)", "");
			Thread.sleep(500);
		}
	}

	public static void findJavaScriptVoidAndClick(WebDriver driver, String xpathInput) {

		element = findWebElement(driver, xpathInput);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}

	public static void findJavaScriptVoidWithLinkText(WebDriver driver, String xpathInput) {

		WebElement element = driver.findElement(By.linkText(xpathInput));
		element.isEnabled();
		element.click();
	}

	public static void verifyAllLinks(WebDriver driver) {
		List<WebElement> links = driver.findElements(By.tagName("a"));

		for (int i = 0; i < links.size(); i++) {
			element = links.get(i);
			String url = element.getAttribute("href");
			verifyLinkActive(url);
		}
	}

	public static void verifyLinkActive(String linkUrl) {
		try {
			URL url = new URL(linkUrl);

			HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();
			httpURLConnect.setConnectTimeout(3000);
			httpURLConnect.connect();
			if (httpURLConnect.getResponseCode() == 200) {
				String temp = linkUrl + " - " + httpURLConnect.getResponseMessage();
				allLinks.add(temp);
			}
			if (httpURLConnect.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
				String temp = linkUrl + " - " + httpURLConnect.getResponseMessage();
				allLinks.add(temp);
			}
		} catch (Exception e) {

		}
	}

	public static void navigateBack(WebDriver driver) {

		driver.navigate().back();
	}

	public static void zoomInControlAdd(WebDriver driver, double magnifyTimes) throws Exception {

		for (int i = 0; i < magnifyTimes; i++) {
			element = driver.findElement(By.tagName("html"));
			element.sendKeys(Keys.chord(Keys.CONTROL, Keys.ADD));
			waitThread(500);
		}
	}

	public static void zoomInRobot(WebDriver driver, double magnifyTimes) throws Exception {

		Robot robot = new Robot();
		for (int i = 0; i < magnifyTimes; i++) {
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_EQUALS);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_EQUALS);
			waitThread(500);
		}
	}

	public static void zoomOut(WebDriver driver) throws Exception {

		// for (int i = 0; i < magnifyTimes; i++) {
		element = driver.findElement(By.tagName("html"));
		element.sendKeys(Keys.chord(Keys.CONTROL, "0"));
		// waitThread(500);
		// }
	}

	public static void zoomOutRobot(WebDriver driver, double magnifyTimes) throws Exception {
		Robot robot = new Robot();
		for (int i = 0; i < magnifyTimes; i++) {
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_MINUS);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_MINUS);
			waitThread(500);
		}
	}

	public static void backToTop(WebDriver driver) {

		driver.manage().window().setPosition(new Point(0, 0));
	}

	public static void zoomInByWheel() throws Exception {
		Robot robot = new Robot();
		for (int i = 0; i < 3; i++) {
			robot.mouseWheel(+100);
			waitThread(1000);
		}
	}

	public static void selectDropMenu(WebDriver driver, String str, String xpathInput) {
		Select select = new Select(findWebElement(driver, xpathInput));
		select.selectByVisibleText(str);
	}

	public static boolean isDisplayed(WebDriver driver, String xpathInput) {
		element = findWebElement(driver, xpathInput);
		if (element.isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	public static void maximizeWindow(WebDriver driver) {
		driver.manage().window().maximize();
	}

	public static void minimizeWindow(WebDriver driver) {
		driver.manage().window().setPosition(new Point(-2000, 0));
	}

	public static List<WebElement> getListOfWebElementsByXpath(WebDriver driver, String xpathInput) {
		List<WebElement> allElements = driver.findElements(By.xpath(xpathInput));
		return allElements;
	}

	public static List<WebElement> getListOfWebElementsByCssSelector(WebDriver driver, String cssInput) {
		List<WebElement> allElements = driver.findElements(By.cssSelector(cssInput));
		return allElements;
	}

	public static String getStringFromXpath(WebDriver driver, String xpathInput) {
		String string = findWebElement(driver, xpathInput).getText().trim();
		return string;
	}

	public static List<String> getStringListFromId(WebDriver driver, String id) {
		List<String> string = new ArrayList<String>();
		List<WebElement> webElements = new ArrayList<WebElement>();
		webElements = driver.findElements(By.id(id));
		// System.out.println(webElements);
		for (int i = 0; i < webElements.size(); i++) {
			webElements.get(i).getText().trim();
			string.add(webElements.get(i).getText().trim());
		}
		findElementById(driver, id).getText().trim();
		return string;
	}

	public static String randomStringFromList(List<String> list) {
		Random randomizer = new Random();
		String random = list.get(randomizer.nextInt(list.size()));
		return random;
	}

	public static void altTab() throws AWTException {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_TAB);
		robot.delay(100);
		robot.keyRelease(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_ALT);
	}
}
