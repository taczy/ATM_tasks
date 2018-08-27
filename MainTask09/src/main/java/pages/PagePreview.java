package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PagePreview extends AbstractPage
{
	@FindBy(className = "notice__content")
	private WebElement messagePagePublished;

	@FindBy(xpath="//div[@class='web-preview__toolbar-actions']/a")
	private WebElement pagePreviewButton;

	public PagePreview() {super();}

	public boolean isPagePublished()
	{
		waitForElementVisible(messagePagePublished);
		return messagePagePublished.isDisplayed();
	}

	public SinglePage clickPagePreviewButton()
	{
		pagePreviewButton.click();
		return new SinglePage();
	}
}
