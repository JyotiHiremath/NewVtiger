package TestNGImplemenattion;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.time.Duration;
import java.util.Map;
import java.util.Random;


import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import GenericLibraries.BaseClass;
import GenericLibraries.ExcelFileUtility;
import GenericLibraries.IConstantpath;


public class CreateAndDuplicateLeadTest extends BaseClass {


	@Test
	public void createDuplicateLeadTest() {
		SoftAssert softAssert = new SoftAssert();
		home.clickLeads();
		AssertJUnit.assertTrue(leads.getPageHeader().contains("Leads"));
		
		leads.clickPlusButton();
		AssertJUnit.assertTrue(createLead.getPageHeader().contains("Creating New Lead"));
		
		Map<String,String> map = excel.getDataBasedOnKey("TestData", "Create Lead");

		createLead.selectSalutation(web, map.get("First Name Salutation"));
	
		String leadName = map.get("Last Name")+javaUtil.generateRandomNumber(100);
		createLead.setLastName(leadName);
		createLead.setCompanyName(map.get("Company"));
		createLead.clickSave();
		
		AssertJUnit.assertTrue(newLeadInfo.getPageHeader().contains(leadName));
		
		newLeadInfo.clickDuplicateButton();
		
		String duplicateLeadName = map.get("New Last Name")+javaUtil.generateRandomNumber(100);
		duplicating.setLastName(duplicateLeadName);
		duplicating.clickSave();
		
		newLeadInfo.clickLeadsLink();
		AssertJUnit.assertTrue(leads.getNewLead().equals(duplicateLeadName));
		if(leads.getNewLead().equals(duplicateLeadName)) 
			excel.updateTestStatusInExcel("TestData", "Create Lead", "Pass", IConstantpath.EXCEL_FILE_PATH);
		else 
			excel.updateTestStatusInExcel("TestData", "Create Lead", "Fail", IConstantpath.EXCEL_FILE_PATH);
		softAssert.assertAll();
	}
		
	}


