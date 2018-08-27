package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CategoryPage extends AbstractPage
{
	private static final String POST_IN_CATEGORY_LINK = "//a[contains(text(),'%s')]";

	@FindBy(xpath="//h1[@class='entry-title']")
	private WebElement postTitleInCategory;

	public CategoryPage() {super();}

	public String getPostTitleInCategory()
	{
		return postTitleInCategory.getText();
	}

	public SinglePost openPostInCategory(String postTitle)
	{
		driver.findElement(By.xpath(String.format(POST_IN_CATEGORY_LINK, postTitle))).click();
		return new SinglePost();
	}
}
