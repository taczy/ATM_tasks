package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SinglePost extends AbstractPage
{
	@FindBy(xpath="//footer/div/span/a[@class='post-edit-link']")
	private WebElement editLink;

	@FindBy(xpath="//h1[@class='entry-title']")
	private WebElement postTitleItem;

	@FindBy(xpath="//div[@class='entry-content']/p")
	private WebElement postBodyItem;

	@FindBy(xpath="//div[@class='web-preview__toolbar-actions']/a")
	private WebElement postPreviewButton;

	public SinglePost() {super();}

	public EditPostPage openPostToEdit()
	{
		waitForElementVisible(editLink);
		jsElementClick(editLink);
		return new EditPostPage();
	}

	public String getPostTitle()
	{
		return postTitleItem.getText();
	}

	public String getPostBody()
	{
		return postBodyItem.getText();
	}
}
