package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import services.CommonBlogVerificationServices;
import services.AccountServices;
import services.BlogEditionServices;

public class AddPageWithPictureTest extends BaseTest
{
	private static final String IMAGE_FILE_NAME = "curiosity.jpg";
	private static final String PAGE_TITLE = "New Test Page";
	private static final String PAGE_BODY = "Some text on the page";

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

	@Test(description = "Check if 'Pages' widget presents in sidebar", dependsOnMethods = "openWpBlog")
	public void checkPageWidgetDisplayed()
	{
		Assert.assertTrue(CommonBlogVerificationServices.isPagesWidget(), "No 'Pages' widget on the main page");
	}

	@Test(description = "Check the image is uploaded", dependsOnMethods = "checkPageWidgetDisplayed")
	public void checkImageUploaded()
	{
		BlogEditionServices.openMediaLibrary();
		Assert.assertTrue(new MediaLibraryPage().isImageUploaded(IMAGE_FILE_NAME), "The image is not found");
	}

	@Test(description = "Add new page and publish it", dependsOnMethods = "checkImageUploaded")
	public void publishNewPage()
	{
		BlogEditionServices.publishPage(PAGE_TITLE, PAGE_BODY);
		Assert.assertTrue(new PagePreview().isPagePublished(), "The page is not published");
	}

	@Test(description = "Check new page title and body content", dependsOnMethods = "publishNewPage")
	public void checkPagePublished()
	{
		SinglePage singlePage = new PagePreview().clickPagePreviewButton();
		Assert.assertEquals(singlePage.getPageTitle(), PAGE_TITLE, "The changes in post title are not applied");
		Assert.assertEquals(singlePage.getPageBody(), PAGE_BODY, "The changes in post body are not applied");
	}

	@Test(description = "Set thumbnail for the page", dependsOnMethods = "checkPagePublished")
	public void addFeaturedImage()
	{
		BlogEditionServices.setPageThumbnail(IMAGE_FILE_NAME);
		Assert.assertTrue(new EditPage().isThumbnail(), "Thumbnail is not set, try again");
	}

	@Test(description = "Update edited page", dependsOnMethods = "addFeaturedImage")
	public void updatePage() throws InterruptedException
	{
		Assert.assertTrue(new EditPage().updatePage(), "Updates are not saved");
	}

	@Test(description = "Open the page and check featured image", dependsOnMethods = "updatePage")
	public void checkIfThumbnailAdded()
	{
		boolean isThumbnailAdded = new EditPage()
				                   .openUpdatedPage()
				                   .isFeaturedImage(IMAGE_FILE_NAME);
		Assert.assertTrue(isThumbnailAdded, "Featured image is not found on the page");
	}

	@Test(description = "Delete the page", dependsOnMethods = "checkIfThumbnailAdded")
	public void deletePage()
	{
		BlogEditionServices.deleteOpenedPage();
		new AdminPanel().openBlogPreview();
		Assert.assertFalse(new BlogPreviewPage().isPageExists(PAGE_TITLE), "The page is not deleted");
	}

	@Test(description = "Log out", dependsOnMethods = "deletePage")
	public void logOut()
	{
		AccountServices.userLogOut();
		Assert.assertTrue(new ProfilePage().isLogOut(), "You are not logged out");
	}

}
