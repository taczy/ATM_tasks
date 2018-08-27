package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminPanel extends AbstractPage
{
	@FindBy(xpath="//li[@data-tip-target='sitePreview']")
	private WebElement  blogPreviewButton;

	@FindBy(xpath="//a[@href='/settings/general/atmhometask.wordpress.com']")
	private WebElement  settingsButton;

	@FindBy(xpath = "//a[@href='/page/atmhometask.wordpress.com']")
	private WebElement  addNewPageButton;

	public AdminPanel() {super();}

	public BlogPreviewPage openBlogPreview()
	{
		waitForElementClickable(blogPreviewButton).click();
		return new BlogPreviewPage();
	}

	public SettingsPage openSettings()
	{
		settingsButton.click();
		return new SettingsPage();
	}

	public AddPage clickAddNewPage()
	{
		addNewPageButton.click();
		return new AddPage();
	}
}
