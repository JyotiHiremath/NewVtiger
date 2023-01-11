package GenericLibraries;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import PomPages.ContactsPage;
import PomPages.CreateNewContactPage;
import PomPages.CreateNewLeadPage;
import PomPages.CreateNewOrganizationPage;
import PomPages.DuplicatingPage;
import PomPages.HomePage;
import PomPages.LeadsPage;
import PomPages.LoginPage;
import PomPages.NewContactInfoPage;
import PomPages.NewLeadInfoPage;
import PomPages.NewOrganizationInfoPage;
import PomPages.OrganizationsPage;

public class BaseClass {
	protected WebDriverUtility web;
	protected PropertyFileUtility property;
	protected ExcelFileUtility excel;
	protected JavaUtility javaUtil;
	protected WebDriver driver;
	protected LoginPage login;
	protected HomePage home;
	protected ContactsPage contacts;
	protected CreateNewContactPage createContact;
	protected NewContactInfoPage newContact;
	protected OrganizationsPage organization;
	protected CreateNewOrganizationPage createOrganization;
	protected NewOrganizationInfoPage newOrganization;
	protected LeadsPage leads;
	protected CreateNewLeadPage createLead;
	protected NewLeadInfoPage newLeadInfo;
	protected DuplicatingPage duplicating;
	public static JavaUtility sjavaUtil;
	public static WebDriver sdriver;

	// @BeforeSuite
	// @BeforeTest

	@BeforeClass
	public void classSetup() {

		web = new WebDriverUtility();
		property = new PropertyFileUtility();
		excel = new ExcelFileUtility();
		javaUtil = new JavaUtility();
		sjavaUtil = javaUtil;

		property.propertyFileInitialization(IConstantpath.PROPERTY_FILE_PATH);
		excel.excelInitialization(IConstantpath.EXCEL_FILE_PATH);

		String url = property.getDataFromProperties("url");
		String browser = property.getDataFromProperties("browser");
		long time = Long.parseLong(property.getDataFromProperties("timeouts"));

		driver = web.openApplication(browser, url, time);
		sdriver = driver;
		login = new LoginPage(driver);
		Assert.assertTrue(login.getPageHeader().contains("vtiger"));

	}

	@BeforeMethod
	public void methodsetup() {

		login = new LoginPage(driver);
		home = new HomePage(driver);
		contacts = new ContactsPage(driver);
		createContact = new CreateNewContactPage(driver);
		newContact = new NewContactInfoPage(driver);
		organization = new OrganizationsPage(driver);
		createOrganization = new CreateNewOrganizationPage(driver);
		newOrganization = new NewOrganizationInfoPage(driver);
		leads = new LeadsPage(driver);
		createLead = new CreateNewLeadPage(driver);
		newLeadInfo = new NewLeadInfoPage(driver);
		duplicating = new DuplicatingPage(driver);
		String username = property.getDataFromProperties("username");
		String password = property.getDataFromProperties("password");
		login.loginToApplication(username, password);
		Assert.assertTrue(home.getPageHeader().contains("Home"));

	}

	@AfterMethod
	public void methodTeardown() {
		home.signOut(web);
	}

	@AfterClass
	public void classTeardown() {
		web.closeBrowser();
		excel.closeExcel();

	}

}
