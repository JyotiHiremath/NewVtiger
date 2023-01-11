package PomImplimentation;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import GenericLibraries.IConstantpath;
import GenericLibraries.ExcelFileUtility;
import GenericLibraries.JavaUtility;
import GenericLibraries.PropertyFileUtility;
import GenericLibraries.WebDriverUtility;
import PomPages.ContactsPage;
import PomPages.CreateNewContactPage;
import PomPages.HomePage;
import PomPages.LoginPage;
import PomPages.NewContactInfoPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateContactWithExistingOrganization {

	public static void main(String[] args) {

		System.setProperty("webdriver.chrome.driver", "/Users/namita/jyoti/11/chromedriver");

		WebDriverUtility web = new WebDriverUtility();
		PropertyFileUtility property = new PropertyFileUtility();
		ExcelFileUtility excel = new ExcelFileUtility();
		JavaUtility javaUtil = new JavaUtility();

		property.propertyFileInitialization(IConstantpath.PROPERTY_FILE_PATH);
		excel.excelInitialization(IConstantpath.EXCEL_FILE_PATH);

		String url = property.getDataFromProperties("url");
		String browser = property.getDataFromProperties("browser");
		long time = Long.parseLong(property.getDataFromProperties("timeouts"));

		WebDriver driver = web.openApplication(browser, url, time);

		LoginPage login = new LoginPage(driver);
		HomePage home = new HomePage(driver);
		ContactsPage contacts = new ContactsPage(driver);
		CreateNewContactPage createContact = new CreateNewContactPage(driver);
		NewContactInfoPage newContact = new NewContactInfoPage(driver);

		if (login.getPageHeader().contains("vtiger"))
			System.out.println("Login Page Displayed");
		else
			System.out.println("Login Page is not Displayed");

		String username = property.getDataFromProperties("username");
		String password = property.getDataFromProperties("password");
		login.loginToApplication(username, password);

		if (home.getPageHeader().contains("Home"))
			System.out.println("Home Page is displayed");
		else
			System.out.println("Home Page is not displayed");
		
		System.out.println("Before home.clickContacts();");

		home.clickContacts();
		
		System.out.println("After home.clickContacts();");

		if (contacts.getPageHeader().contains("Contacts"))
			System.out.println("Pass");
		else
			System.out.println("Fail");

		contacts.clickPlusButton();

		if (createContact.getpageHeader().contains("Creating New Contact"))
			System.out.println("Pass");
		else
			System.out.println("Fail");

		
		System.out.println("After new contact....");
		
		Map<String, String> map = excel.getDataBasedOnKey("TestData", "Create Contact");

		createContact.selectSalutation(web, map.get("First Name Salutation"));
		String lastName = map.get("Last Name") + javaUtil.generateRandomNumber(100);
		createContact.setLastName(lastName);
		System.out.println(".....Last Name:  "+lastName);
		createContact.selectExistingOrganization(web, map.get("Organization Name"));
		createContact.uploadContactImage(map.get("Contact Image"));
		createContact.clickSave();
		
		System.out.println("after save....");
		
		
	

		if (newContact.getPageHeader().contains(lastName))
			System.out.println("Pass");
		else
			System.out.println("Fail");

		newContact.clickContactsLink();

		if (contacts.getNewContact().equals(lastName)) {
			System.out.println("Pass");
			excel.updateTestStatusInExcel("TestData", "Create Contact", "Pass", IConstantpath.EXCEL_FILE_PATH);
		} else {
			System.out.println("Fail");
			excel.updateTestStatusInExcel("TestData", "Create Contact", "Fail", IConstantpath.EXCEL_FILE_PATH);
		}

		home.signOut(web);

		web.closeBrowser();
		excel.closeExcel();

	}

}
