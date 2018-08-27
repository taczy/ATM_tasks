package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditPage extends AbstractPage
{
	@FindBy(xpath="//div[@data-e2e-title='featured-image']")
	private WebElement featuredImageSidebarMenuItem;

	@FindBy(xpath="//div[@data-e2e-title='featured-image']/div/div/div/button")
	private WebElement featuredImageSetButton;

	@FindBy(xpath = "//button[@data-e2e-button='confirm']")
	private WebElement confirmSetImageButton;

	@FindBy(className = "image-preloader")
	private WebElement imagePreview;

	@FindBy(xpath="//button[@class='button editor-publish-button is-primary']")
	private WebElement updateButton;

	@FindBy(xpath="//span[@class='notice__text']/a")
	private WebElement messagePageUpdated;

	@FindBy(xpath="//button[@class='button editor-delete-post__button is-borderless']")
	private WebElement deletePageButton;

	@FindBy(xpath="//button[@class='button is-primary']/span[@class='dialog__button-label']")
	private WebElement approveDeletePageButton;

	public EditPage() {super();}

	public EditPage addThumbnail(String imageFileName)
	{
		featuredImageSidebarMenuItem.click();
		featuredImageSetButton.click();
		WebElement imageInLibrary = driver.findElement(By.xpath("//figure[@title='"+imageFileName+"']/img"));
		waitForElementVisible(imageInLibrary).click();
		waitForElementClickable(confirmSetImageButton).click();
		return this;
	}

	public boolean isThumbnail()
	{
		return imagePreview.isDisplayed();
	}

	public boolean updatePage() throws InterruptedException
	{
		Thread.sleep(3000);
		waitForElementClickable(updateButton);
		updateButton.click();
		waitForElementClickable(messagePageUpdated);
		return messagePageUpdated.isDisplayed();
	}

	public SinglePage openUpdatedPage()
	{
		messagePageUpdated.click();
		return new SinglePage();
	}

	public AdminPanel deletePage()
	{
		waitForElementClickable(deletePageButton).click();
		waitForElementClickable(approveDeletePageButton).click();
		return new AdminPanel();
	}
}
