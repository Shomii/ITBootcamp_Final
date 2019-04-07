package testSite;

import java.util.Scanner;

import pageWebElements.tests.CompleteAautomatedTest;
import pageWebElements.tests.CompleteManualTest;
import pageWebElements.tests.TestBlogPage;
import pageWebElements.tests.TestGalleryPage;
import pageWebElements.tests.TestHomePage;
import utility.DBAccess;

public class PreTestPrep {

	private static String missTypeString;
	private static int chooseOption;
	private static Scanner sc = new Scanner(System.in);

	public static void checkTyping() {

		try {
			System.out.println("You can choose manual input of data or start automated test.");
			System.out.println("Choose from the options below and enter number in front of your choice\n");
			System.out.println("1. Manual test of registration and login");
			System.out.println("2. Automated test of registration and login");
			System.out.println("3. Manual test of creating, editing and deleting blog");
			System.out.println("4. Automated test of creating, editing and deleting blog");
			System.out.println("5. Complete automated test");
			System.out.println("6. Complete manual test");
			System.out.println("7. Test gallery page");
			System.out.println("8. Display data from database in console\n");
			System.out.println("0. Exit");

			missTypeString = sc.nextLine();
			chooseOption = Integer.parseInt(missTypeString);
			execute();
		} catch (Exception e) {
			System.out.println("Invalid input !!\nTry again\n");
			enterSelection();
		}
	}

	private static void enterSelection() {

		checkTyping();
		execute();

	}

	private static void execute() {

		if (chooseOption == 1) {
			try {
				System.out.println(
						"Starting manual test input of data with eclipse console for registration and login ...");
				TestHomePage.homePageTestManual();

			} catch (Exception e) {
				e.toString();
			}
		} else if (chooseOption == 2) {
			try {
				System.out.println("Starting automated test for registration and login ...");
				TestHomePage.homePageTestAutomate();
			} catch (Exception e) {
				e.toString();
			}

		} else if (chooseOption == 3) {
			try {
				System.out.println(
						"Starting manual test input of data with eclipse console to create,edit and delete blog ...");
				TestBlogPage.blogPageTestManual();
			} catch (Exception e) {
				e.toString();
			}
		} else if (chooseOption == 4) {
			try {
				System.out.println("Starting automated test to create,edit and delete blog ...");
				TestBlogPage.blogPageTestAutomate();
			} catch (Exception e) {
				e.toString();
			}
		} else if (chooseOption == 5) {
			try {
				System.out.println("Starting complete automated test ...");
				CompleteAautomatedTest.completeAutomatedTest();
			} catch (Exception e) {
				e.toString();
			}

		} else if (chooseOption == 6) {
			try {
				System.out.println("Starting complete manual test ...");
				CompleteManualTest.completeManualTest();
			} catch (Exception e) {
				e.toString();
			}
		} else if (chooseOption == 7) {
			try {
				System.out.println("Testing gallery page ...");
				TestGalleryPage.testGallery();
			} catch (Exception e) {
				e.toString();
			}
		} else if (chooseOption == 8) {
			try {

				DBAccess.getDataFromDataBase();
			} catch (Exception e) {
				e.toString();
			}
		} else if (chooseOption == 0) {
			try {
				System.out.println("Exiting program ...");
				System.exit(0);
			} catch (Exception e) {
				e.toString();
			}
		} else {
			System.out.println("Typed to many numbers? Don't worry, try again...\n");
			checkTyping();
		}
	}

	public static void checkTypingWithSwitch() throws Exception {

		System.out.println("You can choose manual input of data or start automated test.");
		System.out.println("Choose from the options below and enter number in front of your choice\n");
		System.out.println("1. Manual test of registration and login");
		System.out.println("2. Automated test of registration and login");
		System.out.println("3. Manual test of creating, editing and deleting blog");
		System.out.println("4. Automated test of creating, editing and deleting blog");
		System.out.println("5. Complete automated test");
		System.out.println("6. Complete manual test");
		System.out.println("7. Test gallery page");
		System.out.println("8. Display data from database in console\n");
		System.out.println("0. Exit");

		try {
			missTypeString = sc.nextLine();
			chooseOption = Integer.parseInt(missTypeString);
		} catch (Exception e) {
			System.out.println("Letters is not alowed here... Try again");
			checkTypingWithSwitch();
		}
		switch (chooseOption) {
		case 1:
			System.out
					.println("Starting manual test input of data with eclipse console for registration and login ...");
			TestHomePage.homePageTestManual();
			break;
		case 2:
			System.out.println("Starting automated test for registration and login ...");
			TestHomePage.homePageTestAutomate();
			break;
		case 3:
			System.out.println(
					"Starting manual test input of data with eclipse console to create,edit and delete blog ...");
			TestBlogPage.blogPageTestManual();
			break;
		case 4:
			System.out.println("Starting automated test to create,edit and delete blog ...");
			TestBlogPage.blogPageTestAutomate();
			break;
		case 5:
			System.out.println("Starting complete automated test ...");
			CompleteAautomatedTest.completeAutomatedTest();
			break;
		case 6:
			System.out.println("Starting complete manual test ...");
			CompleteManualTest.completeManualTest();
			break;
		case 7:
			System.out.println("Testing gallery page ...");
			TestGalleryPage.testGallery();
			break;
		case 8:
			DBAccess.getDataFromDataBase();
			break;
		case 0:
			System.out.println("Exiting program ...");
			System.exit(0);
			break;
		default:
			System.out.println("Something went wrong... Try again\n");
			checkTypingWithSwitch();
			break;
		}
	}
}
