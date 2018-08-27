package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddPage extends AbstractPage
{
	private static final String PAGE_BODY_FRAME_ID = "tinymce-1_ifr";

	@FindBy(xpath = "//textarea[@tabindex='1']")
	private WebElement pageTitleField;

	@FindBy(className= "post-type-post")
	private WebElement pageBodyField;

	@FindBy(xpath="//button[@class='button editor-publish-button is-primary']")
	private WebElement publishPagetButton;

	public AddPage() {super();}

	public AddPage addNewPage(String title, String body)
	{
		addPageTitle(title);
		addPageBody(body);
		return this;
	}

	public PagePreview publishNewPage()
	{
		waitForElementClickable(publishPagetButton);
		publishPagetButton.click();
		return new PagePreview();
	}

	private AddPage addPageTitle(String title)
	{
		waitForElementClickable(pageTitleField).click();
		pageTitleField.sendKeys(title);
		return this;
	}

	private AddPage addPageBody(String body)
	{
		driver.switchTo().frame(PAGE_BODY_FRAME_ID);
		pageBodyField.click();
		pageBodyField.sendKeys(body);
		driver.switchTo().defaultContent();
		return this;
	}
}
