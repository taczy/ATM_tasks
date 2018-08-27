package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PostsListPage extends AbstractPage
{
	@FindBy(xpath="//a[@href='/posts/drafts/atmhometask.wordpress.com']")
	private WebElement draftsTab;

	public PostsListPage() {super();}

	public PostsListPage openDraftsList()
	{
		draftsTab.click();
		return this;
	}

	public boolean isPostInDrafts(String title)
	{
		return driver.findElement(By.linkText(title)).isDisplayed();
	}

	public AddPostPage openDraft(String title)
	{
		driver.findElement(By.linkText(title)).click();
		return new AddPostPage();
	}
}
