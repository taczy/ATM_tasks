package pages;

import org.openqa.selenium.By;

public class BlogPreviewPage extends AbstractPage
{
	private static final String PAGE_TITLE_LOCATOR = "//a[contains(text(),'%s')]";

	public BlogPreviewPage() {super();}

	public boolean isPostByTitlePresent(String title)
	{
		return isElementPresent(By.linkText(title));
	}

	public boolean isPageExists (String pageTitle)
	{
		return isElementPresent(By.xpath(String.format(PAGE_TITLE_LOCATOR, pageTitle)));
	}

}
