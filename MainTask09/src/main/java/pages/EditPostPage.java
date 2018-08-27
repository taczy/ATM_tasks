package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditPostPage extends AbstractPage
{
	private static final String POST_BODY_FRAME_ID = "tinymce-1_ifr";

	@FindBy(xpath="//textarea[@tabindex='1']")
	private WebElement postTitleField;

	@FindBy(className="post-type-post")
	private WebElement postBodyTextBox;

	@FindBy(xpath="//button[@class='button editor-publish-button is-primary']")
	private WebElement updateButton;

	@FindBy(xpath="//span[@class='notice__text']/a")
	private WebElement messagePostUpdated;

	@FindBy(xpath="//button[@class='button editor-delete-post__button is-borderless']")
	private WebElement deletePostButton;

	@FindBy(xpath="//button[@class='button is-primary']/span[@class='dialog__button-label']")
	private WebElement approveDeletePostButton;

	public EditPostPage() {super();}

	public EditPostPage editPost(String newTitle, String newBody)
	{
		editPostTitle(newTitle);
		editPostBody(newBody);
		waitForElementClickable(updateButton).click();
		waitForElementClickable(messagePostUpdated);
		return this;
	}

	public SinglePost openPostEdited()
	{
		messagePostUpdated.click();
		return new SinglePost();
	}

	public AdminPanel deletePost()
	{
		waitForElementClickable(deletePostButton).click();
		waitForElementClickable(approveDeletePostButton).click();
		return new AdminPanel();
	}

	private EditPostPage editPostTitle(String newTitle)
	{
		postTitleField.clear();
		postTitleField.sendKeys(newTitle);
		return this;
	}

	private EditPostPage editPostBody(String newBody)
	{
		driver.switchTo().frame(POST_BODY_FRAME_ID);
		postBodyTextBox.clear();
		postBodyTextBox.sendKeys(newBody);
		driver.switchTo().defaultContent();
		return this;
	}
}
