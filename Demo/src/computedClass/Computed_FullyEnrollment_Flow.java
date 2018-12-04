package computedClass;
import java.awt.AWTException;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;

import Utility.Data;
import Utility.Readexcel_RowName;
import generatedClass.POM_Generated_AccountLookupPage;
import generatedClass.POM_Generated_AccountSecurityPage;
import generatedClass.POM_Generated_CongratulationsPage;
import generatedClass.POM_Generated_ContactInfoPage;
import generatedClass.POM_Generated_Homepage;
import generatedClass.POM_Generated_VerificationPage;
public class Computed_FullyEnrollment_Flow 
{
	//signup page
	public WebDriver fullsignup(WebDriver driver) throws FileNotFoundException, IOException, InterruptedException, AWTException, EncryptedDocumentException, InvalidFormatException 
	{
		POM_Generated_Homepage homepage = new POM_Generated_Homepage(driver);
		POM_Generated_AccountLookupPage accountlookuppage = new POM_Generated_AccountLookupPage(driver);
		Data obj=new Data();
		obj.waitForElementClickable(driver, homepage.click_Login_Or_Signup_Button);
		homepage.click_click_Login_Or_Signup_Button();
		obj.waitForElementClickable(driver, homepage.click_Register_Button);
		homepage.click_click_Register_Button();	
		obj.waitForElement(driver, accountlookuppage.txt_Title_Text);
		
		Reporter.log("Register button clicked successfully");
		
		return driver;
	}

	//Account lookup page 
	public WebDriver fullcardnumber(WebDriver driver, String TCName) throws FileNotFoundException, IOException, InterruptedException, AWTException, EncryptedDocumentException, InvalidFormatException 
	{
		POM_Generated_AccountLookupPage accountlookuppage = new POM_Generated_AccountLookupPage(driver);
		POM_Generated_VerificationPage verificationpage = new POM_Generated_VerificationPage(driver);
		Data obj= new Data();
	   
		try
		{
			new Readexcel_RowName().excelRead("Global_TestData_Sheet","FullyEnrolled",TCName); 	
			
			obj.waitForElementClickable(driver, accountlookuppage.txt_Card_Number_Field);
			if(Readexcel_RowName.getValue("Card_Type(Card/Phone/CRC)").equalsIgnoreCase("Card")||Readexcel_RowName.getValue("Card_Type(Card/Phone/CRC)").equalsIgnoreCase("CRC"))
			{
				accountlookuppage.type_txt_Card_Number_Field(Readexcel_RowName.getValue("Phone/Card_Number"));
			}
			else if(Readexcel_RowName.getValue("Card_Type(Card/Phone/CRC)").equalsIgnoreCase("Phone"))
			{
				accountlookuppage.type_txt_Phone_Number_Field(Readexcel_RowName.getValue("Phone/Card_Number"));
			}
			obj.scrollingToElementofAPage(driver, accountlookuppage.click_Get_Started_With_SEG_Button);
			accountlookuppage.click_click_Get_Started_With_SEG_Button();	
			obj.waitForElement(driver, verificationpage.txt_VerificationPage_HeaderName_Text);
		
		}
		catch (Exception e) 
		{
			Assert.fail("Card Number is already registered or Invalid card number");
	    }
		return driver;
	}	
	
	public WebDriver Verificationpage(WebDriver driver, String TCName) throws FileNotFoundException, IOException, InterruptedException, AWTException, EncryptedDocumentException, InvalidFormatException 
	{
		POM_Generated_VerificationPage verificationpage = new POM_Generated_VerificationPage(driver);
		
		Data obj= new Data();
	   
	   
		try
		{
			new Readexcel_RowName().excelRead("Global_TestData_Sheet","FullyEnrolled",TCName); 	
	
			
			if(!verificationpage.getText_txt_VerificationPage_HeaderName_Text().equals("Hi "+Readexcel_RowName.getValue("FirstName")+"! Thanks for helping us find your account"))
			{
					Assert.fail("Verification page header is displaying wrongly");
			}
			if(Readexcel_RowName.getValue("Verify_By(SMS/Email/PII)").equalsIgnoreCase("PII")) 
			{
				verificationpage.click_click_PII_Verification_Button();
				obj.waitForElementClickable(driver, verificationpage.txt_PII_Verification_Month_Field);
				
				Reporter.log("PII Verification page displayed successfully");
				
			}	
			else if(Readexcel_RowName.getValue("Verify_By(SMS/Email/PII)").equalsIgnoreCase("SMS"))
			{
				verificationpage.click_click_SMS_Verification_Button();
			}
			else if(Readexcel_RowName.getValue("Verify_By(SMS/Email/PII)").equalsIgnoreCase("Email"))
			{
				verificationpage.click_click_Email_Verification_Button();
			}			
		}
		catch(Exception e)
		{
			Assert.fail("Error in verification page");
		}
		return driver;
	}
	
	
	
	//Verify by PII page
	public WebDriver fullverifybypii(WebDriver driver, String TCName) throws FileNotFoundException, IOException, InterruptedException, AWTException, EncryptedDocumentException, InvalidFormatException 
	{
		POM_Generated_VerificationPage verificationpage = new POM_Generated_VerificationPage(driver);
		POM_Generated_ContactInfoPage contactinfopage = new POM_Generated_ContactInfoPage(driver);
		Data obj=new Data();
		try
		{
			new Readexcel_RowName().excelRead("Global_TestData_Sheet","FullyEnrolled", TCName);	
			verificationpage.type_txt_PII_Verification_Month_Field(Readexcel_RowName.getValue("Month"));
			verificationpage.type_txt_PII_Verification_Date_Field(Readexcel_RowName.getValue("Date"));
			verificationpage.type_txt_PII_Verification_Year_Field(Readexcel_RowName.getValue("Year"));
			verificationpage.type_txt_PII_Verification_Zipcode_Field(Readexcel_RowName.getValue("Zip"));
			if(Readexcel_RowName.getValue("Card_Type(Card/Phone/CRC)").equalsIgnoreCase("Card"))
			{
				obj.scrollingToElementofAPage(driver, verificationpage.txt_PII_Verification_Phone_Number_Field);
				verificationpage.type_txt_PII_Verification_Phone_Number_Field(Readexcel_RowName.getValue("Primary_Phone"));
			}
			else if(Readexcel_RowName.getValue("Card_Type(Card/Phone/CRC)").equalsIgnoreCase("Phone"))
			{
				obj.scrollingToElementofAPage(driver, verificationpage.txt_PII_Verification_Last_Name_Field);
				verificationpage.type_txt_PII_Verification_Last_Name_Field(Readexcel_RowName.getValue("LastName"));
			}
			verificationpage.click_click_PII_Verification_Next_Button();
			obj.waitForElement(driver, contactinfopage.txt_HeaderName_Text);
			
			Reporter.log("PII Verification done successfully");
			
		}
		catch(Exception e)
		{
			Assert.fail("Invalid PII verification details");
		}
		return driver;
	}
	
	//Contact info page
	public WebDriver fullcontactinfo(WebDriver driver,String TCName) throws FileNotFoundException, IOException, InterruptedException, AWTException, EncryptedDocumentException, InvalidFormatException 
	{
		POM_Generated_ContactInfoPage contactinfopage = new POM_Generated_ContactInfoPage(driver);
		POM_Generated_AccountSecurityPage accountsecuritypage = new POM_Generated_AccountSecurityPage(driver);
		Data obj=new Data();
	    try
	    {
	    	new Readexcel_RowName().excelRead("Global_TestData_Sheet","FullyEnrolled",TCName);
		    if(!contactinfopage.getText_txt_HeaderName_Text().equals("Hello "+Readexcel_RowName.getValue("FirstName")))
		    {		
		    	Assert.fail("Verification page header is displaying wrongly");
		    }
		    if (!contactinfopage.getValue_ddl_Salutation_Field().equals(Readexcel_RowName.getValue("Salutation")))
		    {
		    	Assert.fail("Salutation is not pre populating");
			}
		    if (!contactinfopage.getValue_txt_Fname_Field().equals(Readexcel_RowName.getValue("FirstName")))
		    {
		    	Assert.fail("First Name is not pre populating");
			}	
		    if (!contactinfopage.getValue_txt_Address_Field().equals(Readexcel_RowName.getValue("Address1")))
		    {
		    	Assert.fail("Address is not pre populating");
			}
		    if (!contactinfopage.getValue_txt_Lname_Field().equals(Readexcel_RowName.getValue("LastName")))
		    {
		    	Assert.fail("Last Name is not pre populating");
			}	
		    if (!(contactinfopage.getValue_txt_Additional_Address_Field()).equals(String.valueOf(Readexcel_RowName.getValue("Address2")).replace( "null","")))
		    {
		    	Assert.fail("Additional address is not pre populating");
			}
		    obj.scrollingToElementofAPage(driver, contactinfopage.txt_Month_Field);
		    if (!contactinfopage.getValue_txt_Month_Field().equals(Readexcel_RowName.getValue("Month")))
		    {
		    	Assert.fail("Month is not pre populating");
			}
		    if (!contactinfopage.getValue_txt_Date_Field().equals(Readexcel_RowName.getValue("Date")))
		    {
		    	Assert.fail("Date is not pre populating");
			}
		    if (!contactinfopage.getValue_txt_Year_Field().equals(Readexcel_RowName.getValue("Year")))
		    {
		    	Assert.fail("Year is not pre populating");
			}		
		    obj.scrollingToElementofAPage(driver, contactinfopage.txt_City_Field);
		    if (!contactinfopage.getValue_txt_City_Field().equals(Readexcel_RowName.getValue("City")))
		    {
		    	Assert.fail("City is not pre populating");
			}
		    obj.scrollingToElementofAPage(driver, contactinfopage.ddl_State_Field);
		    if (!contactinfopage.getValue_ddl_State_Field().equals(Readexcel_RowName.getValue("State_Code")))
		    {
		    	Assert.fail("State is not pre populating");
			}
		    obj.scrollingToElementofAPage(driver, contactinfopage.txt_Primary_Phone_Number_Field);
			if (!contactinfopage.getValue_txt_Primary_Phone_Number_Field().equals(Readexcel_RowName.getValue("Primary_Phone")))
			{
				Assert.fail("Primary Phone number is not pre populating");
			}
			obj.scrollingToElementofAPage(driver, contactinfopage.txt_Zipcode_Field);
			if (!contactinfopage.getValue_txt_Zipcode_Field().equals(Readexcel_RowName.getValue("Zip")))
			{
				Assert.fail("ZipCode is not pre populating");
			}
			String pat=System.getProperty("user.dir")+"\\Screenshots\\FullyEnrolled\\"+TCName+"_"+"ContactInfo";
			obj.Ashot_Screenshot(driver, pat);
		
			contactinfopage.click_click_Submit_Form_Button();			
	    	obj.waitForElementClickable(driver, accountsecuritypage.txt_Email_Address_Field);
			
	    	Reporter.log("Contact info page completed successfully");
	    }
	    catch(Exception e)
	    {
	    	Assert.fail("Error in contactinfo page");
	    }
		return driver;
	}
	
	//Account info page
	public WebDriver fullaccountinfo(WebDriver driver,String TCName) throws FileNotFoundException, IOException, InterruptedException, AWTException, EncryptedDocumentException, InvalidFormatException 
	{

		POM_Generated_AccountSecurityPage accountsecuritypage = new POM_Generated_AccountSecurityPage(driver);
		POM_Generated_CongratulationsPage congratulationspage = new POM_Generated_CongratulationsPage(driver);
		Data obj=new Data();
	
		String congrats="Thank you for signing up!";
	   
	    new Readexcel_RowName().excelRead("Global_TestData_Sheet","FullyEnrolled",TCName);
		try
		{
			boolean presentt;
			try 
			{
				if(accountsecuritypage.isDisplayed_txt_Email_Address_Field())
				{
					presentt=true;
				}
				else
				{
					presentt = false;
				}
			}
			catch (NoSuchElementException e) 
			{
				presentt = false;
			}
			if(presentt==true)
			{
				if (accountsecuritypage.getValue_txt_Email_Address_Field().equals(Readexcel_RowName.getValue("EmailAddress")))
				{
					if(!accountsecuritypage.isDisplayed_click_Email_Address_Message_Verify())
					{	
						Assert.fail("Email message is not displayed");
					}
				}
				else
				{
					Assert.fail("Email address is not pre populating or not matched");
				}
			}
			boolean present;
			try 
			{
				if(accountsecuritypage.isDisplayed_txt_Set_Password1_Field())
				{
					present = true;
				}
				else
				{
					present = false;
				}
			} 
			catch (NoSuchElementException e) 
			{
				present = false;
			}
			if(present==true)
			{
				if (accountsecuritypage.getValue_txt_Set_Password1_Field().equals(""))
				{
					accountsecuritypage.type_txt_Set_Password1_Field(Readexcel_RowName.getValue("Password"));
				}
				else
				{
					Assert.fail("Password is pre populating");
				}				
				if (accountsecuritypage.getValue_txt_Set_Password2_Field().equals(""))
				{
					accountsecuritypage.type_txt_Set_Password2_Field(Readexcel_RowName.getValue("Confirm_Password"));	
				}
				else
				{
					Assert.fail("Confirm Password is pre populating");
				}
			}
			boolean present1;
			try 
			{
				if(accountsecuritypage.isDisplayed_txt_Set_Pin1_Field())
				{
					present1 = true;
				}
				else
				{
					present1 = false;
				}
			} 
			catch (NoSuchElementException e) 
			{
			   present1 = false;
			}
			if(present1==true)
			{
				if (accountsecuritypage.getValue_txt_Set_Pin1_Field().equals(""))
				{
					accountsecuritypage.type_txt_Set_Pin1_Field(Readexcel_RowName.getValue("Pin"));
				}
				else
				{
					Assert.fail("Pin is pre populating");
				} 					
				if (accountsecuritypage.getValue_txt_Set_Pin2_Field().equals(""))
				{
					accountsecuritypage.type_txt_Set_Pin2_Field(Readexcel_RowName.getValue("Confirm_Pin"));
				}
				else
				{
					Assert.fail("Confirm Pin is pre populating");
				}
			}
			obj.scrollingToElementofAPage(driver, accountsecuritypage.click_Terms_And_Condition_Checkbox);
			accountsecuritypage.click_click_Terms_And_Condition_Checkbox();
			boolean present2;
			try 
			{
				if(accountsecuritypage.isDisplayed_txt_NeverMissADeal_Yes_Button())
				{
					present2 = true;
				}
				else
				{
					present2 = false;
				}
			} 
			catch (NoSuchElementException e) 
			{
			   present2 = false;
			}
			if(present2==true)
			{
				if(Readexcel_RowName.getValue("Save_With_Digital_Coupons(Y/N)").equalsIgnoreCase("Y"))
				{
					accountsecuritypage.click_txt_NeverMissADeal_Yes_Button();
					
				}
				else if(Readexcel_RowName.getValue("Save_With_Digital_Coupons(Y/N)").equalsIgnoreCase("N"))
				{
					accountsecuritypage.click_txt_NeverMissADeal_No_Button();
				}
			}
			obj.waitForElementClickable(driver, accountsecuritypage.click_Security_Next_Button);
			accountsecuritypage.click_click_Security_Next_Button();
			obj.waitForElement(driver, congratulationspage.txt_ThankYou_Message_Text);
			String s3=congratulationspage.getText_txt_ThankYou_Message_Text();
			if(s3.equalsIgnoreCase(congrats))
			{
				
			}
			else
			{
				Assert.fail("Error in Congratulations page");
			}
			
		}
		catch (Exception e) 
		{
			Assert.fail("Error in Accountinfo Page");
	    }
		return driver;
	}
}
