package com.epam.atm.selenium05;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class WpBlogTest
{
	private static final String WP_BLOG_URL = "https://atmhometask.wordpress.com/";
	private static final String WP_ADMIN_URL = "https://wordpress.com/wp-login.php";
	private static final String USER_EMAIL = "epam.tatsiana.chyzhova@gmail.com";
	private static final String PASSWORD = "jC5%r8p*Ja%Nrd&fn!4rv@";
	private static final String BLOG_TITLE = "ATM program";
	private static final String CATEGORY = "atm-hometask";
	private static final String TITLE_ADD = "Hello world!";
	private static final String POST_ADD = "This is my new post in this blog.";
	private static final String TITLE_EDIT = "Hello Minsk!";
	private static final String POST_EDIT = "This is my edited post.";
	private static final String postTitle = "//textarea[@tabindex='1']";

	private WebDriver driver;

	protected void explicitWait(String xpath)
	{
		WebDriverWait wait = new WebDriverWait(driver, 15); // explicit waiter
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	}

	protected void jsElementClick(WebElement element)
	{
		JavascriptExecutor js = (JavascriptExecutor) driver; //check if an element is clickable
		js.executeScript("arguments[0].click();", element);
	}

	private boolean isElementPresent(By by)
	{
		return !driver.findElements(by).isEmpty(); // Custom implementation for is ElementPresent
	}

	@BeforeClass(description = "Open browser in incognito mode and maximize the window")
	public void runBrowser()
	{
		System.setProperty("webdriver.chrome.driver", "d:\\_webdriver\\chromedriver\\chromedriver.exe");
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("incognito");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		options.addArguments("start-maximized");
		driver = new ChromeDriver(options);
	}

	@BeforeMethod(description = "Add implicit timeout before methods")
	public void timeOut()
	{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	}

	@AfterClass(description = "Close browser")
	public void closeBrowser()
	{
		driver.quit();
	}

	@Test(description = "Open created WP blog", priority = 0)
	public void openWpBlog()
	{
		driver.get(WP_BLOG_URL);
		String URL = driver.getCurrentUrl();
		Assert.assertEquals(URL, WP_BLOG_URL, "Cannot open ATM program blog");
	}

	@Test(description = "Categories widget presents on the main page", priority = 1)
	public void findCategories()
	{
		Assert.assertTrue(isElementPresent(By.className("widget_categories")), "No 'Categories' widget on the main page");
	}

	@Test(description = "Login entering", priority = 10)
	public void enterLogIn()
	{
		driver.get(WP_ADMIN_URL);
		WebElement adminSearchLogin = driver.findElement(By.id("usernameOrEmail"));
		adminSearchLogin.sendKeys(USER_EMAIL);
		driver.findElement(By.className("login__form-action")).click();
		Assert.assertEquals(adminSearchLogin.getText(), USER_EMAIL, "Login wasn't entered");
	}

	@Test(description = "User logging into the system", priority = 20)
	public void logInto()
	{
		WebElement password = driver.findElement(By.id("password"));
		password.sendKeys(PASSWORD);
		driver.findElement(By.className("login__form-action")).click();
		Assert.assertTrue(isElementPresent(By.className("gridicons-user-circle")), "You are not logged in");
	}

	@Test(description = "Open your blog, find "+BLOG_TITLE+" header", priority = 30)
	public void openBlog()
	{
		driver.findElement(By.xpath("//a[@href='/stats/day/atmhometask.wordpress.com']")).click();
		driver.findElement(By.xpath("//a[@href='https://atmhometask.wordpress.com']")).click();
		Assert.assertTrue(isElementPresent(By.linkText(BLOG_TITLE)), "The blog is not open");
	}

	@Test(description = "Add a post, close it, find it in drafts", priority = 40)
	public void postIsCreated()
	{
		driver.findElement(By.xpath("//a[@href='/post/atmhometask.wordpress.com']")).click();
		driver.findElement(By.xpath(postTitle)).sendKeys(TITLE_ADD);
		driver.switchTo().frame("tinymce-1_ifr");                        //switch to iframe
		driver.findElement(By.className("post-type-post")).sendKeys(POST_ADD);
		driver.switchTo().defaultContent();                                // switch to default content
		explicitWait("//button[@class='button editor-delete-post__button is-borderless']");
		driver.findElement(By.className("editor-ground-control__back")).click();
		driver.findElement(By.xpath("//a[@href='/posts/atmhometask.wordpress.com']")).click();
		driver.findElement(By.xpath("//a[@href='/posts/drafts/atmhometask.wordpress.com']")).click();
		Assert.assertTrue(isElementPresent(By.linkText(TITLE_ADD)), "The post is not found in drafts");
	}

	@Test(description = "Add a new category for the post and publish", priority = 50)
	public void addCatPublish() throws InterruptedException
	{
		driver.findElement(By.linkText(TITLE_ADD)).click();
		driver.findElement(By.xpath("//div[@data-e2e-title='categories-tags']")).click();
		driver.findElement(By.xpath("//div[@class='editor-term-selector__add-term is-compact']/button")).click();
		explicitWait("//fieldset[@class='form-fieldset']//input[@class='form-text-input']");
		driver.findElement(By.xpath("//fieldset[@class='form-fieldset']//input[@class='form-text-input']")).sendKeys(CATEGORY);
		driver.findElement(By.xpath("//button[@data-e2e-button='add']")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//button[@tabindex='5']")).click();
		explicitWait("//div[@class='web-preview__toolbar-actions']/a");
		Assert.assertTrue(isElementPresent(By.xpath("//div/div/span/span[@class='notice__text']/a[contains (text(), '"+BLOG_TITLE+"')]")), "The post is not published");

	}

	@Test(description = "Open the new post trough its category", priority = 60)
	public void openPostTroughCat()
	{
		driver.findElement(By.xpath("//a[@class='button web-preview__external is-primary']")).click();
		driver.findElement(By.xpath("//a[contains(text(),'"+CATEGORY+"')]")).click();
		Assert.assertTrue(isElementPresent(By.xpath("//a[contains(text(),'"+TITLE_ADD+"')]")), "The post "+TITLE_ADD+" is not found in the category "+CATEGORY);
	}

	@Test(description = "Edit the post created", priority = 70)
	public void editPost()
	{
		jsElementClick(driver.findElement(By.xpath("//footer/div/span/a[@class='post-edit-link']")));
		driver.findElement(By.xpath(postTitle)).clear();
		driver.findElement(By.xpath(postTitle)).sendKeys(TITLE_EDIT);
		driver.switchTo().frame("tinymce-1_ifr");
		driver.findElement(By.className("post-type-post")).clear();
		driver.findElement(By.className("post-type-post")).sendKeys(POST_EDIT);
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("//button[@class='button editor-publish-button is-primary']")).click();
		explicitWait("//div[@class='notice is-success is-dismissable']");
		Assert.assertTrue(isElementPresent(By.xpath("//span[@class='notice__text']/a")));
	}

	@Test(description = "Open post again check that changes were applied", priority = 80)
	public void checkChanges()
	{
		jsElementClick(driver.findElement(By.xpath("//span[@class='notice__text']/a")));
		Assert.assertTrue(isElementPresent(By.xpath("//h1[@class='entry-title'][contains(text(),'"+TITLE_EDIT+"')]")));
		Assert.assertTrue(isElementPresent(By.xpath("//p[contains(text(),'"+POST_EDIT+"')]")));
	}

	@Test (description = "Delete the post", priority = 90)
	public void deletePost()
	{
		driver.findElement(By.xpath("//a[@class='post-edit-link']")).click();
		explicitWait("//button[@class='button editor-delete-post__button is-borderless']");
		driver.findElement(By.xpath("//button[@class='button editor-delete-post__button is-borderless']")).click();
		explicitWait("//button[@class='button is-primary']/span[@class='dialog__button-label']");
		driver.findElement(By.xpath("//button[@class='button is-primary']/span[@class='dialog__button-label']")).click();
		driver.get(WP_BLOG_URL);
		explicitWait("//h1[@class='entry-title']");
		Assert.assertFalse(isElementPresent(By.xpath("//h1[@class='entry-title'][contains(text(),'"+TITLE_EDIT+"')]")));
	}

}