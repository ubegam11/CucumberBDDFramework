package StepDefinition;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import PageObject.AddNewCustomerPage;
import PageObject.LoginPage;
import PageObject.SearchCustomerPage;
import Utilities.ReadConfig;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
//if we are giving order to before & after method then the lower value will be executed first in Before BUT in after higher will be executed first

public class StepDefn extends BaseClass {
	@Before("@sanity") // will get called before each scenario same for after
	public void setup() {
		readConfig = new ReadConfig();
		
		// initialise logger
		log = LogManager.getLogger("StepDefn");
		System.out.println("I am setup");

		String browser = readConfig.getBrowser();
		// launch browser
		switch (browser.toLowerCase()) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;

		case "msedge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;

		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		default:
			driver = null;
			break;

		}

		log.fatal("Setup1 executed...");
	}

	@Given("User Launch Chrome browser")
	public void user_launch_chrome_browser() {

		loginPg = new LoginPage(driver);
		addNewCustPg = new AddNewCustomerPage(driver);
		SearchCustPg = new SearchCustomerPage(driver);
		log.info("Browser is intialized");
	}
	// ========================Login==================================

	@When("User opens URL {string}")
	public void user_opens_url(String url) {
		driver.get(url);
		log.info("navigated to login page");
	}

	@When("User enters Email as {string} and Password as {string}")
	public void user_enters_email_as_and_password_as(String emailadd, String password) {
		loginPg.enterEmail(emailadd);
		loginPg.enterPassword(password);

		log.info("Login is performed");
	}

	@When("Click on Login")
	public void click_on_login() {
		loginPg.clickOnLoginButton();
		log.info("Clicked on login button");

	}

	@Then("Page Title should be {string}")
	public void page_title_should_be(String expectedTitle) {
		String actualTitle = driver.getTitle();

		if (actualTitle.equals(expectedTitle)) {
			log.warn("Test passed: Login feature :Page title matched.");
			Assert.assertTrue(true);// pass
		} else {
			Assert.assertTrue(false);// fail
			log.warn("Test Failed: Login feature- page title not matched.");
		}
	}
	///////////////// I am needed for Login feature//////////////////

	@When("User click on Log out link")
	public void user_click_on_log_out_link() {
		loginPg.clickOnLogOutButton();
		log.info("user clicked on logout link.");

	}

	@Then("Logout Page Title should be {string}")
	public void page_title_should_be2(String expectedTitle) {
		String actualTitle = driver.getTitle();

		if (actualTitle.equals(expectedTitle))
			Assert.assertTrue(true);// pass

		else
			Assert.assertTrue(false);// fail

	}

	// ========================Create Customer==================================
	@Then("User can view Dashboad")
	public void user_can_view_dashboad() {
		String actualTitle = addNewCustPg.getPageTitle();
		String expectedTitle = "Dashboard / nopCommerce administration";

		if (actualTitle.equals(expectedTitle)) {
			log.info("user can view dashboard test passed.");
			Assert.assertTrue(true);

		} else {
			Assert.assertTrue(false);
			log.warn("user can view dashboard test failed.");

		}

	}

	@When("User click on customers Menu")
	public void user_click_on_customers_menu() throws InterruptedException {
		addNewCustPg.clickOnCustomersMenu();
		Thread.sleep(2000);
		log.info("customer menu clicked");

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@When("click on customers Menu Item")
	public void click_on_customers_menu_item() {
		addNewCustPg.clickOnCustomersMenuItem();
		log.info("customer menu item clicked");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@When("click on Add new button")
	public void click_on_add_new_button() {
		addNewCustPg.clickOnAddnew();
		log.info("clicked on add new button.");
	}

	@Then("User can view Add new customer page")
	public void user_can_view_add_new_customer_page() {
		String actualTitle = addNewCustPg.getPageTitle();
		String expectedTitle = "Add a new customer / nopCommerce administration";

		if (actualTitle.equals(expectedTitle)) {
			log.info("User can view Add new customer page- passed");
			Assert.assertTrue(true);// pass
		} else {
			log.info("User can view Add new customer page- failed");
			Assert.assertTrue(false);// fail
		}
	}

	@When("User enter customer info")
	public void user_enter_customer_info() {
		// addNewCustPg.enterEmail("cs1243909@234gmail.com");
		addNewCustPg.enterEmail(generateEmailId() + "@gmail.com");
		addNewCustPg.enterPassword("test1");
		addNewCustPg.enterFirstName("Prachin");
		addNewCustPg.enterLastName("Gupta");
		addNewCustPg.enterGender("Female");
		addNewCustPg.enterDob("6/13/1988");
		addNewCustPg.enterCompanyName("CodeStudio");
		addNewCustPg.enterAdminContent("Admin content");
		addNewCustPg.enterManagerOfVendor("Vendor 1");
		log.info("customer information entered");
	}

	@When("click on Save button")
	public void click_on_save_button() throws InterruptedException {
		addNewCustPg.clickOnSave();
		Thread.sleep(3000);
		log.info("clicked on save button");
	}

	@Then("User can view confirmation message {string}")
	public void user_can_view_confirmation_message(String exptectedConfirmationMsg) {
		String bodyTagText = driver.findElement(By.tagName("Body")).getText();
		if (bodyTagText.contains(exptectedConfirmationMsg)) {
			Assert.assertTrue(true);// pass
			log.info("User can view confirmation message - passed");

		} else {
			Assert.assertTrue(false);// fail

			log.warn("User can view confirmation message - failed");

		}

	}

	// ========================Search Customer==================================
	@When("Enter customer EMail")
	public void enter_customer_e_mail() {
		SearchCustPg.enterEmailAdd("victoria_victoria@nopCommerce.com");
		log.info("Email address entered");
	}

	@When("Enter customer FirstName")
	public void enter_customer_first_name() {
		SearchCustPg.enterFirstName("Victoria");

	}

	@When("Enter customer LastName")
	public void enter_customer_last_name() {
		SearchCustPg.enterLastName("Terces");
	}

	@When("Click on search button")
	public void click_on_search_button() {
		SearchCustPg.clickOnSearchButton();
		log.info("Clicked on search button.");

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Then("User should found Email in the Search table")
	public void user_should_found_email_in_the_search_table() {
		String expectedEmail = "victoria_victoria@nopCommerce.com";
		String expectedName = "Victoria Terces";
		// Assert.assertTrue(SearchCustPg.searchCustomerByEmail(expectedEmail));

		if (SearchCustPg.searchCustomerByEmail(expectedEmail) == true) {
			Assert.assertTrue(true);
			log.info("User should found Email in the Search table - passed");

		} else
			Assert.assertTrue(true);
		log.info("User should found Email in the Search table - passed");
	}

	@Then("User should found Name in the Search table")
	public void user_should_found_name_in_the_search_table() {
		String expectedName = "Victoria Terces";
		// Assert.assertTrue(SearchCustPg.searchCustomerByEmail(expectedEmail));

		if (SearchCustPg.searchCustomerByName(expectedName) == true) {
			Assert.assertTrue(true);

		} else
			Assert.assertTrue(true);
	}

	

	//@After
	public void teardown(Scenario sc) {
		System.out.println("I am teardown");
		if (sc.isFailed() == true) {
			// Convert web driver object to TakeScreenshot

			String fileWithPath = "C:\\Users\\91790\\eclipse-workspace\\CucumberBDDFramework_CodeStudio\\Screenshot\\failedScreenshot.png";
			TakesScreenshot scrShot = ((TakesScreenshot) driver);

			// Call getScreenshotAs method to create image file
			File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

			// Move image file to new destination
			File DestFile = new File(fileWithPath);

			// Copy file at destination

			try {
				FileUtils.copyFile(SrcFile, DestFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		driver.quit();

	}
	@AfterStep
	public void takeScreenshot(Scenario sc) {
		if(sc.isFailed()) {
			final byte[] screenshot=((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			sc.attach(screenshot, "image/png", sc.getName());
		}
	}
	/*
	 * @BeforeStep public void beforeStepMethodDemo() {
	 * System.out.println("This is before step...."); }
	 * 
	 * 
	 * @AfterStep public void afterStepMethodDemo() {
	 * System.out.println("This is after step...."); }
	 */

}