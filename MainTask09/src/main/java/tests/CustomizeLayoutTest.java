package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.BlogHomePage;
import pages.CustomizingPage;
import pages.ProfilePage;
import services.AccountServices;
import services.CustomizationServices;
import services.CommonBlogVerificationServices;

public class CustomizeLayoutTest extends BaseTest
{
	private static final String BLOG_NAME = "ATM program";
	private static final String NEW_BLOG_NAME = "Task Eight";
	private static final String NEW_BACKGROUND_COLOR_HEX = "#fdf4d7";
	private static final String FONT_NAME = "Cinzel";

	@Test(description = "User is logged in")
	public void checkIsUserLoggedIn()
	{
		AccountServices.userLogIn(testAccount);
		boolean isUserName = new ProfilePage()
				                     .openProfilePage()
				                     .isUsername(testAccount.getUserName());
		Assert.assertTrue (isUserName, "You are not logged in in your blog");
	}

	@Test(description = "Open created WP blog", dependsOnMethods = "checkIsUserLoggedIn")
	public void openWpBlog()
	{
		Assert.assertEquals(CommonBlogVerificationServices.checkBlogUrl(blogUrl), blogUrl.getBlogUrl()
				, "Cannot openBlogHomePage ATM program blog");
	}

	@Test(description = "Run customizing mode", dependsOnMethods = "openWpBlog")
	public void runCustomizing()
	{
		CustomizationServices.runCustomizingMode();
		Assert.assertTrue(new CustomizingPage().isCustomizingMode(), "You are not in customizing mode");
	}

	@Test(description = "Rename blog", dependsOnMethods = "runCustomizing")
	public void changeBlogTitle()
	{
		CustomizationServices.changeBlogTitle(NEW_BLOG_NAME);
		Assert.assertEquals(new BlogHomePage().getBlogTitle(), NEW_BLOG_NAME, "Blog renamed incorrectly");
	}

	@Test(description = "Return old blog name", dependsOnMethods = "changeBlogTitle")
	public void getOldBlogName()
	{
		CustomizationServices.runCustomizingMode();
		CustomizationServices.changeBlogTitle(BLOG_NAME);
		Assert.assertEquals(new BlogHomePage().getBlogTitle(), BLOG_NAME, "Blog has not its original name");
	}

	@Test(description = "Change background color", dependsOnMethods = "getOldBlogName")
	public void changeBackgroundColor()
	{
		CustomizationServices.runCustomizingMode();
		CustomizationServices.changeBackgroundColor(NEW_BACKGROUND_COLOR_HEX);
		Assert.assertEquals(new BlogHomePage().getNewBackgroundColor(), NEW_BACKGROUND_COLOR_HEX
				, "Background color is not changed correctly");
	}

	@Test(description = "Return default background color", dependsOnMethods = "changeBackgroundColor")
	public void returnDefaultBackgroundColor()
	{
		CustomizationServices.runCustomizingMode();
		CustomizationServices.changeBackgroundColorToDefault();
		boolean isDefaultColor = new BlogHomePage().isDefaultBackgroundColor();
		Assert.assertTrue(isDefaultColor, "Background color is not default");
	}

	@Test(description = "Change headings font", dependsOnMethods = "returnDefaultBackgroundColor")
	public void changeHeadingsFont()
	{
		CustomizationServices.runCustomizingMode();
		CustomizationServices.changeHeadingFontType(FONT_NAME);
		Assert.assertTrue(new BlogHomePage().isNewHeadingFont(FONT_NAME));
	}

	@Test(description = "Return default heading font", dependsOnMethods = "changeHeadingsFont")
	public void returnDefaultHeadingFont()
	{
		CustomizationServices.runCustomizingMode();
		CustomizationServices.changeHeadingFontToDefault();
		Assert.assertTrue(new BlogHomePage().isDefaultFont(), "There is some custom font set");
	}
}
