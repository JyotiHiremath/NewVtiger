package PomImplimentation;

import java.time.Duration;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import GenericLibraries.ExcelFileUtility;
import GenericLibraries.IConstantpath;
import GenericLibraries.JavaUtility;
import GenericLibraries.PropertyFileUtility;
import GenericLibraries.WebDriverUtility;
import PomPages.CreateNewContactPage;
import PomPages.DuplicatingPage;
import PomPages.HomePage;
import PomPages.LeadsPage;
import PomPages.LoginPage;
import PomPages.NewLeadInfoPage;
import PomPages.CreateNewLeadPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateAndDuplicateLeadTest {


		 public static void main(String[] args) {
			 
			 
			 System.setProperty("webdriver.chrome.driver", "/Users/namita/jyoti/11/chromedriver");

			 WebDriverUtility web = new WebDriverUtility();
				JavaUtility javaUtil = new JavaUtility();
				PropertyFileUtility property = new PropertyFileUtility();
				ExcelFileUtility excel = new ExcelFileUtility();
				
				property.propertyFileInitialization(IConstantpath.PROPERTY_FILE_PATH);
				excel.excelInitialization(IConstantpath.EXCEL_FILE_PATH);
				
				String url = property.getDataFromProperties("url");
				String browser = property.getDataFromProperties("browser");
				long time = Long.parseLong(property.getDataFromProperties("timeouts"));
				
				WebDriver driver = web.openApplication(browser, url, time);
				  
				LoginPage login = new LoginPage(driver);
				HomePage home = new HomePage(driver);
				LeadsPage leads = new LeadsPage(driver);
				//CreateNewContactPage createLead = new CreateNewContactPage(driver); //nagaraj
				CreateNewLeadPage createLead = new CreateNewLeadPage(driver);//nagaraj
				
				
				NewLeadInfoPage newLeadInfo = new NewLeadInfoPage(driver); 
				DuplicatingPage duplicating = new DuplicatingPage(driver);
				
				if(login.getPageHeader().contains("vtiger"))
					System.out.println("Login Page Displayed");
				else
					System.out.println("Login Page is not Displayed");
			
				String username = property.getDataFromProperties("username");
				String password = property.getDataFromProperties("password");
				login.loginToApplication(username, password);
				
				if(home.getPageHeader().contains("Home"))
					System.out.println("Home Page is displayed");
				else
					System.out.println("Home Page is not displayed");
				
				home.clickLeads();
				
				if (leads.getPageHeader().contains("Leads"))
					System.out.println("Pass");
				else
					System.out.println("Fail");
				
				leads.clickPlusButton();
				
				if(createLead.getPageHeader().contains("Creating New Lead"))
					System.out.println("Pass");
				else
					System.out.println("Fail");
				
				Map<String,String> map = excel.getDataBasedOnKey("TestData", "Create Lead");

				createLead.selectSalutation(web, map.get("First Name Salutation"));
			
				String leadName = map.get("Last Name")+javaUtil.generateRandomNumber(100);
				createLead.setLastName(leadName);
				createLead.setCompanyName(map.get("Company"));
				createLead.clickSave();
			
				if(newLeadInfo.getPageHeader().contains(leadName))
					System.out.println("Pass");
				else
					System.out.println("Fail");
				
				newLeadInfo.clickDuplicateButton();
				
				if(duplicating.getPageHeader().contains(leadName))
					System.out.println("Pass");
				else
					System.out.println("Fail");
				String duplicateLeadName = map.get("New Last Name")+javaUtil.generateRandomNumber(100);
				duplicating.setLastName(duplicateLeadName);
				duplicating.clickSave();
				
				newLeadInfo.clickLeadsLink();
				
				if(leads.getNewLead().equals(duplicateLeadName)) {
					System.out.println("Pass");
					excel.updateTestStatusInExcel("TestData", "Create Lead", "Pass", IConstantpath.EXCEL_FILE_PATH);
				}
				else {
					System.out.println("Fail");
					excel.updateTestStatusInExcel("TestData", "Create Lead", "Fail", IConstantpath.EXCEL_FILE_PATH);
				}
			
			
				home.signOut(web);
				web.closeBrowser();
				excel.closeExcel();
			
		
	}

}
