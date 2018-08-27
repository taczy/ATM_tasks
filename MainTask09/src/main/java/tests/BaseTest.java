package tests;

import bo.Account;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import browser.Browser;
import pages.AbstractPage;
import utils.NamesGenerator;
import java.lang.reflect.Method;

public class BaseTest
{
	private static final String USER_EMAIL = "epam.tatsiana.chyzhova@gmail.com";
	private static final String PASSWORD = "jC5%r8p*Ja%Nrd&fn!4rv@";
	private static final String USER_NAME = "atmhometask";
	private static final String WP_BLOG_URL = "https://atmhometask.wordpress.com/";
	private static final String CATEGORY = "atm";

	protected WebDriver driver;
	protected Account testAccount;
	protected AbstractPage blogUrl;
	protected String categoryName = NamesGenerator.generateCategoryName(CATEGORY);

	@BeforeClass(description = "Open browser in incognito mode and maximize the window. Add implicit waits")
	public void runBrowser()
	{
		System.out.println("Class started: " + getClass());
	}

	@BeforeClass(description = "Creating testAccount fields")
	public void createAccount()
	{
		testAccount = new Account();
		testAccount.setLogin(USER_EMAIL);
		testAccount.setPassword(PASSWORD);
		testAccount.setUserName(USER_NAME);
	}

	@BeforeClass(description = "Creating blog URL")
	public void createBlogUrl()
	{
		blogUrl = new AbstractPage();
		blogUrl.setBlogUrl(WP_BLOG_URL);
	}

	@BeforeMethod(description = "Before method preparation")
	public void beforeMethod(Method method)
	{
		String testName = method.getName();
		System.out.println("Method started: " + testName);
	}

	@AfterMethod(description = "After method preparations")
	public void afterMethod(Method method)
	{
		String testName = method.getName();
		System.out.println("Method finished: " + testName);
	}

	@AfterClass(description = "Close browser")
	public void closeBrowser()
	{
		Browser.kill();
		System.out.println("Class finished: " + getClass());
	}
}
