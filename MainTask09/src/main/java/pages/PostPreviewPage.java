package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PostPreviewPage extends AbstractPage
{
	@FindBy(xpath="//span[@class='notice__text']/a")
	private WebElement messagePostPublished;

	public PostPreviewPage() {super();}

	public boolean isPublishedMessageDisplayed()
	{
		return waitForElementVisible(messagePostPublished).isDisplayed();
	}
}
