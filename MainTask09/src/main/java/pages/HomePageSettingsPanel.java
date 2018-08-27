package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePageSettingsPanel extends AbstractPage
{
	@FindBy(id="wp-admin-bar-ab-new-post")
	private WebElement addPostButton;

	@FindBy(id="wp-admin-bar-edit-attachment")
	private WebElement mediaButton;

	@FindBy(xpath = "//li[@id='wp-admin-bar-themes']")
	private WebElement customizeButton;

	public HomePageSettingsPanel() {super();}

	public AddPostPage clickAddPost()
	{
		waitForElementClickable(addPostButton).click();
		return new AddPostPage();
	}

	public MediaLibraryPage clickMedia()
	{
		waitForElementClickable(mediaButton).click();
		return new MediaLibraryPage();
	}

	public CustomizingPage clickCustomize()
	{
		waitForElementClickable(customizeButton).click();
		return new CustomizingPage();
	}
}
