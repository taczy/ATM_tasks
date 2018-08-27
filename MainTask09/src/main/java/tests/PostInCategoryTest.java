package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import services.AccountServices;
import services.BlogEditionServices;
import services.CommonBlogVerificationServices;

public class PostInCategoryTest extends BaseTest
{
	private static final String POST_TITLE_ADD = "Hello world!";
	private static final String POST_BODY_ADD = "This is my new post in this blog.";
	private static final String IMAGE_FILE_NAME = "curiosity.jpg";
	private static final String POST_TITLE_EDIT = "Hello Minsk!";
	private static final String POST_BODY_EDIT = "This is my edited post.";

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

	@Test(description = "Check if 'Categories' widget presents in sidebar", dependsOnMethods = "openWpBlog")
	public void checkCategoriesWidgetDisplayed()
	{
		boolean isCategoryWidget = new BlogHomePage().isCategoryWidgetDisplayed();
		Assert.assertTrue(isCategoryWidget, "No 'Categories' widget on the main page");
	}

	@Test(description = "Add a post, close it, find it in drafts", dependsOnMethods = "checkCategoriesWidgetDisplayed")
	public void addPostAndFindInDrafts()
	{
		BlogEditionServices.createPostDraft(POST_TITLE_ADD, POST_BODY_ADD, IMAGE_FILE_NAME);
		new PostsListPage().openDraftsList();
		Assert.assertTrue(new PostsListPage().isPostInDrafts(POST_TITLE_ADD), "The post is not found in drafts");
	}

	@Test(description = "Add a new category for the post and publish", dependsOnMethods = "addPostAndFindInDrafts")
	public void addCategoryAndPublish() throws InterruptedException
	{
		BlogEditionServices.publishDraftInCategory(POST_TITLE_ADD, categoryName);
		Assert.assertTrue(new PostPreviewPage().isPublishedMessageDisplayed()
				, "The post is not published");
	}

	@Test(description = "Open the new post through its category", dependsOnMethods = "addCategoryAndPublish")
	public void openPostThroughCategory()
	{
		String postTitle = BlogEditionServices.goToCategoryPageAndGetPostTitle(blogUrl, categoryName);
		Assert.assertEquals(postTitle, POST_TITLE_ADD
				, "The post "+POST_TITLE_ADD+" is not found in the category "+ categoryName);
	}

	@Test(description = "Edit post and check updates applied", dependsOnMethods = "openPostThroughCategory")
	public void checkChangesAppliedInPost()
	{
		BlogEditionServices.editAndPublishPost(POST_TITLE_ADD, POST_TITLE_EDIT, POST_BODY_EDIT);
		Assert.assertEquals(new SinglePost().getPostTitle(), POST_TITLE_EDIT
				,"The changes in post title are not applied");
		Assert.assertEquals(new SinglePost().getPostBody(), POST_BODY_EDIT
				, "The changes in post body are not applied");
	}

	@Test (description = "Delete the post", dependsOnMethods = "checkChangesAppliedInPost")
	public void deletePostAndCheckIsDeleted()
	{
		BlogEditionServices.deleteOpenedPost();
		boolean isPostPresent = new BlogPreviewPage().isPostByTitlePresent(POST_TITLE_EDIT);
		Assert.assertFalse(isPostPresent, "The post is not deleted");
	}

	@Test (description = "Delete the category", dependsOnMethods = "deletePostAndCheckIsDeleted")
	public void deleteCategoryAndCheckItDeleted()
	{
		BlogEditionServices.removeCategory(categoryName);
		boolean isCategoryExists = new SettingsPage().isCategoryFound(categoryName);
		Assert.assertFalse(isCategoryExists, "The category is not deleted");
	}
}


