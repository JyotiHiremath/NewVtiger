package PomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * This class contains element references and respective business libraries of Contacts page
 * @author QPS-Basavanagudi
 *
 */

public class ContactsPage {
	
	//Declartion
	@FindBy(xpath="//a[@class='hdrLink']") private WebElement pageHeader;
	@FindBy(xpath="//img[@alt='Create Contact...']") private WebElement plusButton;
	//@FindBy(xpath="//div[@id='ListViewContents']/descendant::table[@cellpadding='5']/descendant::tr/td[1]/a") private WebElement newContact;
	@FindBy(xpath="//table[@class='lvt small']/descendant::tr[last()]/td[4]/a") private WebElement newContact;


//Initialization
	public ContactsPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	//Utilization
		/**
		 * This method is used to get contacts page header
		 * @return
		 */
	
	public String getPageHeader() {
		return pageHeader.getText();
	}
	
	/**
	 * This method is used to click on create contact plus button
	 */
	public void clickPlusButton() {
		System.out.println("Coming........");
		plusButton.click();
	}
	
	/**
	 * This method is used to get the new contact created
	 * @return
	 */
	public String getNewContact() {
		return newContact.getText();
	}
}
	
	