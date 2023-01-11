package TestNGImplemenattion;


import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.util.Map;
import java.util.Random;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import GenericLibraries.IConstantpath;
import GenericLibraries.BaseClass;

public class CreateContactWithExistingOrganization extends BaseClass {


		@Test
		public void createContactTest() {
			SoftAssert softAssert = new SoftAssert();
			home.clickContacts();
			AssertJUnit.assertTrue(contacts.getPageHeader().contains("Contacts"));
		
		
		contacts.clickPlusButton();
		
       AssertJUnit.assertTrue(createContact.getpageHeader().contains("Creating New Contact"));
		
		Map<String,String> map = excel.getDataBasedOnKey("TestData", "Create Contact");
			
		createContact.selectSalutation(web, map.get("First Name Salutation"));
		String lastName = map.get("Last Name")+javaUtil.generateRandomNumber(100);
		createContact.setLastName(lastName);
		createContact.selectExistingOrganization(web, map.get("Organization Name"));
		createContact.uploadContactImage(map.get("Contact Image"));		
		createContact.clickSave();
		
		AssertJUnit.assertTrue(newContact.getPageHeader().contains(lastName));
		
		newContact.clickContactsLink();
		AssertJUnit.assertTrue(contacts.getNewContact().equals(lastName));
		if(contacts.getNewContact().equals(lastName)) 
			excel.updateTestStatusInExcel("TestData", "Create Contact", "Pass", IConstantpath.EXCEL_FILE_PATH);
		else 
			excel.updateTestStatusInExcel("TestData", "Create Contact", "Fail", IConstantpath.EXCEL_FILE_PATH);
		
		softAssert.assertAll();
	}

		
		

}
