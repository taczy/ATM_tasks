package selenium05;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WpBlogTest extends BaseTest
{
	private static final String WP_BLOG_URL = "https://atmhometask.wordpress.com/";
	private static final String WP_ADMIN_URL = "https://wordpress.com/wp-login.php";
	private static final String USER_EMAIL = "";
	private static final String PASSWORD = "";
	private static final String BLOG_TITLE = "ATM program";
	private static final String CATEGORY = "atm-hometask";
	private static final String TITLE_ADD = "Hello world!";
	private static final String POST_ADD = "This is my new post in this blog.";
	private static final String TITLE_EDIT = "Hello Minsk!";
	private static final String POST_EDIT = "This is my edited post.";
	private static final By POST_TITLE = By.xpath("//textarea[@tabindex='1']");
	private static final By EDITED_TITLE_LINK = By.xpath("//h1[@class='entry-title'][contains(text(),'"+TITLE_EDIT+"')]");
	private static final By DELETE_POST_BTN = By.xpath("//button[@class='button editor-delete-post__button is-borderless']");

	@Test(description = "Open created WP blog")
	public void openWpBlog()
	{
		driver.get(WP_BLOG_URL);
		String URL = driver.getCurrentUrl();
		Assert.assertEquals(URL, WP_BLOG_URL, "Cannot open ATM program blog");
	}

	@Test(description = "Categories widget presents on the main page", dependsOnMethods = "openWpBlog")
	public void findCategories()
	{
		Assert.assertTrue(driver.findElement(By.className("widget_categories")).isDisplayed()
				, "No 'Categories' widget on the main page");
	}

	@Test(description = "Enter login", dependsOnMethods = "findCategories")
	public void enterLogIn()
	{
		driver.get(WP_ADMIN_URL);
		WebElement adminSearchLogin = driver.findElement(By.id("usernameOrEmail"));
		adminSearchLogin.sendKeys(USER_EMAIL);
		driver.findElement(By.className("login__form-action")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//input[@id='usernameOrEmail'][@value='"+USER_EMAIL+"']")).isDisplayed()
				, "Login wasn't entered");
	}

	@Test(description = "Enter password and submit form", dependsOnMethods = "enterLogIn")
	public void enterPasswordAndSubmitForm()
	{
		WebElement password = driver.findElement(By.id("password"));
		password.sendKeys(PASSWORD);
		driver.findElement(By.className("login__form-action")).click();
		Assert.assertTrue(driver.findElement(By.cssSelector(".gridicons-user-circle")).isDisplayed(), "You are not logged in");
	}

	@Test(description = "Open your blog, find "+BLOG_TITLE+" header", dependsOnMethods = "enterPasswordAndSubmitForm")
	public void openWpBlogLoggedIn()
	{
		driver.findElement(By.xpath("//a[@href='/stats/day/atmhometask.wordpress.com']")).click();
		driver.findElement(By.xpath("//a[@href='https://atmhometask.wordpress.com']")).click();
		driver.switchTo().frame(driver.findElement(By.className("web-preview__frame")));
		Assert.assertTrue(driver.findElement(By.xpath("//h1[@class='site-title']/a[contains(text(),'"+BLOG_TITLE+"')]")).isDisplayed()
				, "The blog is not open");
		driver.switchTo().defaultContent();
	}

	@Test(description = "Add a post, close it, find it in drafts", dependsOnMethods = "openWpBlogLoggedIn")
	public void addPostAndFindInDrafts()
	{
		addPost();
		closePost();
		openDrafts();
		Assert.assertTrue(driver.findElement(By.linkText(TITLE_ADD)).isDisplayed(), "The post is not found in drafts");
	}

	@Test(description = "Add a new category for the post and publish", dependsOnMethods = "addPostAndFindInDrafts")
	public void addCategoryAndPublish() throws InterruptedException
	{
		addCategory();
		publishPostInCategory();
		Assert.assertTrue(driver.findElement(By.xpath("//div/div/span/span[@class='notice__text']/a[contains (text(), '"+BLOG_TITLE+"')]")).isDisplayed()
				, "The post is not published");

	}

	@Test(description = "Open the new post through its category", dependsOnMethods = "addCategoryAndPublish")
	public void openPostThroughCategory()
	{
		driver.findElement(By.xpath("//a[@class='button web-preview__external is-primary']")).click();
		driver.findElement(By.xpath("//a[contains(text(),'"+CATEGORY+"')]")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//a[contains(text(),'"+TITLE_ADD+"')]")).isDisplayed()
				, "The post "+TITLE_ADD+" is not found in the category "+CATEGORY);
	}

	@Test(description = "Edit the post created", dependsOnMethods = "openPostThroughCategory")
	public void editPost()
	{
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//footer/div/span/a[@class='post-edit-link']"))).click();
		driver.findElement(POST_TITLE).clear();
		driver.findElement(POST_TITLE).sendKeys(TITLE_EDIT);
		driver.switchTo().frame("tinymce-1_ifr");
		driver.findElement(By.className("post-type-post")).clear();
		driver.findElement(By.className("post-type-post")).sendKeys(POST_EDIT);
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("//button[@class='button editor-publish-button is-primary']")).click();
		waitForVisibility("//div[@class='notice is-success is-dismissable']");
		Assert.assertTrue(driver.findElement(By.xpath("//span[@class='notice__text']/a")).isDisplayed()
				, "The post edited version is not saved");
	}

	@Test(description = "Open post again and check changes applied in post", dependsOnMethods = "editPost")
	public void checkChangesAppliedInPost()
	{
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='notice__text']/a"))).click();
		Assert.assertTrue(driver.findElement(EDITED_TITLE_LINK).isDisplayed()
				, "The changes in post title are not applied");
		Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'"+POST_EDIT+"')]")).isDisplayed()
				, "The changes in post body are not applied");
	}

	@Test (description = "Delete the post", dependsOnMethods = "checkChangesAppliedInPost")
	public void deletePost()
	{
		driver.findElement(By.xpath("//a[@class='post-edit-link']")).click();
		wait.until(ExpectedConditions.elementToBeClickable(DELETE_POST_BTN)).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='button is-primary']/span[@class='dialog__button-label']"))).click();
		driver.get(WP_BLOG_URL);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h1[@class='entry-title']")));
		Assert.assertFalse(isElementPresent(EDITED_TITLE_LINK), "The post is not deleted");
	}

	@Test (description = "Delete the category", dependsOnMethods = "deletePost")
	public void deleteCategoryAndCheck()
	{
		navigateInSettings();
		deleteCategory();
		Assert.assertFalse(isElementPresent(By.xpath("//span[contains(text(),'"+CATEGORY+"')]"))
				, "The category is not deleted");
	}

	private void addPost()
	{
		driver.findElement(By.xpath("//a[@href='/post/atmhometask.wordpress.com']")).click();
		driver.findElement(POST_TITLE).sendKeys(TITLE_ADD);
		driver.switchTo().frame("tinymce-1_ifr");
		driver.findElement(By.className("post-type-post")).sendKeys(POST_ADD);
		driver.switchTo().defaultContent();
	}

	private void closePost()
	{
		waitForVisibility("//button[@class='button editor-delete-post__button is-borderless']");
		driver.findElement(By.className("editor-ground-control__back")).click();
	}

	private void openDrafts()
	{
		driver.findElement(By.xpath("//a[@href='/posts/atmhometask.wordpress.com']")).click();
		driver.findElement(By.xpath("//a[@href='/posts/drafts/atmhometask.wordpress.com']")).click();
	}
	private void addCategory()
	{
		driver.findElement(By.linkText(TITLE_ADD)).click();
		driver.findElement(By.xpath("//div[@data-e2e-title='categories-tags']")).click();
		driver.findElement(By.xpath("//div[@class='editor-term-selector__add-term is-compact']/button")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//fieldset[@class='form-fieldset']//input[@class='form-text-input']"))).click();
		driver.findElement(By.xpath("//fieldset[@class='form-fieldset']//input[@class='form-text-input']")).sendKeys(CATEGORY);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-e2e-button='add']"))).click();
	}

	private void publishPostInCategory() throws InterruptedException
	{
		Thread.sleep(5000);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@tabindex='5']"))).click();
		waitForVisibility("//div[@class='web-preview__toolbar-actions']/a");
	}

	private void navigateInSettings()
	{
		driver.findElement(By.id("wp-admin-bar-blog")).click();
		driver.findElement(By.xpath("//a[@href='https://wordpress.com/settings/general/atmhometask.wordpress.com']")).click();
		driver.findElement(By.xpath("//a[@href='/settings/writing/atmhometask.wordpress.com']")).click();
		driver.findElement(By.xpath("//a[@href='/settings/taxonomies/category/atmhometask.wordpress.com']")).click();
	}
	private void deleteCategory()
	{
		driver.findElement(By.xpath("//span[@class='ellipsis-menu'][..//span[@class='taxonomy-manager__label']/span[contains(text(),'"+CATEGORY+"')]]")).click();
		driver.findElement(By.xpath("//button[@role='menuitem'][2]")).click();
		driver.findElement(By.xpath("//span[@class='dialog__button-label'][contains(text(),'OK')]")).click();
		waitForInvisibility(By.xpath("//span[contains(text(),'"+CATEGORY+"')]"), 10);
	}

}
