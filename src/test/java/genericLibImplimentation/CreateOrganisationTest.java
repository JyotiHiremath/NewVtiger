package genericLibImplimentation;

import java.time.Duration;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import GenericLibraries.ExcelFileUtility;
import GenericLibraries.IConstantpath;
import GenericLibraries.JavaUtility;
import GenericLibraries.PropertyFileUtility;
import GenericLibraries.WebDriverUtility;
import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateOrganisationTest {

	public static void main(String[] args) {

		System.setProperty("webdriver.chrome.driver", "/Users/namita/jyoti/11/chromedriver");

		WebDriverUtility web = new WebDriverUtility();
		PropertyFileUtility property = new PropertyFileUtility();
		ExcelFileUtility excel = new ExcelFileUtility();
		JavaUtility JavaUtil = new JavaUtility();

		property.propertyFileInitialization(IConstantpath.PROPERTY_FILE_PATH);
		excel.excelInitialization(IConstantpath.EXCEL_FILE_PATH);

		String url = property.getDataFromProperties("url");
		String browser = property.getDataFromProperties("browser");
		long time = Long.parseLong(property.getDataFromProperties("timeouts"));

		WebDriver driver = web.openApplication(browser, url, time);

		// Random random = new Random();
		// int randomNum = random.nextInt(100);

		// WebDriverManager.chromedriver().setup();
		// WebDriver driver=new ChromeDriver();
		// driver.manage().window().maximize();
		// driver.get("http://192.168.9.128:8888/index.php?action=Login&module=Users");
		// driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		// To check Vtiger login page
		String title = driver.findElement(By.xpath("//a[@href='http://www.vtiger.com']")).getText();

		if (title.contains("vtiger"))
			System.out.println("Login Page Displayed");

		else
			System.out.println("Login page not dispalyed");

		// Login credentials
		driver.findElement(By.name("user_name")).sendKeys("admin");
		driver.findElement(By.name("user_password")).sendKeys("admin");
		driver.findElement(By.id("submitButton")).click();

		// To check Home page is appearing or not
		String homePageText = driver.findElement(By.xpath("//a[@class='hdrLink']")).getText();
		if (homePageText.contains("Home"))
			System.out.println("Home page is display");
		else
			System.out.println("Home page is  not displayed");

		// To click on Organization tab
		driver.findElement(By.xpath("//a[.='Organizations']")).click();

		// to verify Text Organizations in organization
		String Organization = driver.findElement(By.xpath("//a[@class='hdrLink']")).getText();
		if (Organization.contains("organization"))
			System.out.println("pass");
		else
			System.out.println("fail");

		// + button click
		driver.findElement(By.xpath("//img[@alt='Create Organization...']")).click();

		// To check create organization
		String createOrg = driver.findElement(By.xpath("//span[@class='lvtHeaderText']")).getText();
		if (createOrg.contains("Creating New organization"))
			System.out.println("pass");
		else
			System.out.println("fail");

		// Give organisation name & Industry

		Map<String, String> map = excel.getDataBasedOnKey("TestData", "Create Organization");
		String organizationName = map.get("Organization name") + JavaUtil.generateRandomNumber(100);
		driver.findElement(By.name("accountname")).sendKeys("organizationName");
		WebElement industryDropdown = driver.findElement(By.name("industry"));
		web.dropdown(map.get("Industry"), industryDropdown);
		// Select industry = new Select(industryDropdown);
		// industry.selectByValue("Banking");

		// to save the data
		driver.findElement(By.xpath("//input[contains(@value,'  Save  ')]")).click();

		// To display created Organisation name
		String neworginfo = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();

		if (neworginfo.contains("organizationName"))
			System.out.println("pass");
		else
			System.out.println("fail");

		driver.findElement(By.xpath("//a[@class='hdrLink']")).click();
		String newOrg = driver.findElement(By.xpath("//table[@class='lvt small']/descendant::tr[last()]/td[3]"))
				.getText();
		if (newOrg.equals(organizationName))
			System.out.println("Pass");
		else
			System.out.println("Fail");

		WebElement administratorIcon = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		// Actions a = new Actions(driver);
		// a.moveToElement(administratorIcon).perform();

		web.mouseHoverToElement(administratorIcon);
		driver.findElement(By.xpath("//a[.='Sign Out']")).click();

		web.closeBrowser();
		excel.closeExcel();

		// driver.findElement(By.xpath("//a[.='Sign Out']")).click();
		// driver.quit();

	}

}
