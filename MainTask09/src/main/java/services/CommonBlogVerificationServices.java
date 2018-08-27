package services;

import pages.AbstractPage;
import pages.BlogHomePage;

public class CommonBlogVerificationServices
{
	public static String checkBlogUrl(AbstractPage blogUrl)
	{
		return  new BlogHomePage()
				.openBlogHomePage(blogUrl.getBlogUrl())
				.getCurrentUrl();
	}

	public static boolean isPagesWidget()
	{
		return new BlogHomePage().isPageWidgetDisplayed();
	}
}
