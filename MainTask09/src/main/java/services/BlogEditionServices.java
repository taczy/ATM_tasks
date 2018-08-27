package services;

import pages.*;

public class BlogEditionServices
{
	public static void publishPage(String title, String body)
	{
		new AdminPanel().clickAddNewPage()
				.addNewPage(title, body)
				.publishNewPage();
	}

	public static void openMediaLibrary()
	{
		new BlogHomePage()
				.openHomePageSettingsPanel()
				.clickMedia();
	}

	public static void setPageThumbnail(String imageName)
	{
		new SinglePage()
				.openPageToEdit()
				.addThumbnail(imageName);
	}

	public static void deleteOpenedPage()
	{
		new SinglePage()
				.openPageToEdit()
				.deletePage();
	}

	public static void createPostDraft(String title, String body, String image)
	{
		new BlogHomePage()
				.openHomePageSettingsPanel()
				.clickAddPost()
				.addNewPost(title, body, image)
				.autoSavePostDraft()
				.closePost();
	}

	public static void publishDraftInCategory(String title, String category)
	{
		try
		{
			new PostsListPage()
					.openDraft(title)
					.addPostInNewCategory(category)
					.publishPostInCategory();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public static String goToCategoryPageAndGetPostTitle(AbstractPage blogUrl, String category)
	{
		String postTitle =  new BlogHomePage()
				       .openBlogHomePage(blogUrl.getBlogUrl())
				       .goToCategoryPage(category)
				       .getPostTitleInCategory();
		return postTitle;
	}

	public static void editAndPublishPost(String titleOld, String titleNew, String bodyNew)
	{
		new CategoryPage()
				.openPostInCategory(titleOld)
				.openPostToEdit()
				.editPost(titleNew, bodyNew)
				.openPostEdited();
	}

	public static void deleteOpenedPost()
	{
		new SinglePost()
				.openPostToEdit()
				.deletePost()
				.openBlogPreview();
	}

	public static void removeCategory(String category)
	{
		new AdminPanel()
				.openSettings()
				.openWritingTab()
				.openCategoriesSettings()
				.deleteCategory(category);
	}
}
