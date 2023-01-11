package TestNGImplemenattion;

import org.testng.annotations.Test;

import java.util.Map;

import org.testng.asserts.SoftAssert;

import GenericLibraries.BaseClass;

import GenericLibraries.IConstantpath;

public class CreateOrganisationTest extends BaseClass {

	@Test
	public void createorganisationtest() {

		System.setProperty("webdriver.chrome.driver", "/Users/namita/jyoti/11/chromedriver");

		SoftAssert softAssert = new SoftAssert();
		home.clickOrganizations();
		softAssert.assertTrue(organization.getPageHeader().contains("Organization"));

		organization.clickPlusButton();
		softAssert.assertTrue(createOrganization.getPageHeader().contains("Creating1 New Organization"));

		Map<String, String> map = excel.getDataBasedOnKey("TestData", "Create Organization");

		String organizationName = map.get("Organization Name") + javaUtil.generateRandomNumber(100);
		createOrganization.setOrganizationName(organizationName);

		createOrganization.selectIndustry(web, map.get("Industry"));
		createOrganization.clickSaveButton();
		softAssert.assertTrue(newOrganization.getPageHeader().contains(organizationName));

		newOrganization.clickOrganizationsLink();
		softAssert.assertTrue(organization.getNewOrganization().equals(organizationName));
		if (organization.getNewOrganization().equals(organizationName))
			excel.updateTestStatusInExcel("TestData", "Create Organization", "Pass", IConstantpath.EXCEL_FILE_PATH);
		else
			excel.updateTestStatusInExcel("TestData", "Create Organization", "Fail", IConstantpath.EXCEL_FILE_PATH);

		softAssert.assertAll();

	}
}
