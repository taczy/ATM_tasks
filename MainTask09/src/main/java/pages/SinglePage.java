package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SinglePage extends AbstractPage
{
	private static final String FEATURED_IMAGE_LOCATOR = "//img[contains(@src,'%s')]";

	@FindBy(xpath="//h1[@class='entry-title']")
	private WebElement pageTitleItem;

	@FindBy(xpath="//div[@class='entry-content']/p")
	private WebElement pageBodyItem;

	@FindBy(xpath="//footer/div/span/a[@class='post-edit-link']")
	private WebElement editLink;

	public SinglePage() {super();}

	public String getPageTitle()
	{
		return pageTitleItem.getText();
	}

	public String getPageBody()
	{
		return pageBodyItem.getText();
	}

	public EditPage openPageToEdit()
	{
		waitForElementClickable(editLink);
		editLink.click();
		return new EditPage();
	}

	public boolean isFeaturedImage(String imageFileName)
	{
		WebElement isFeaturedImageDisplayed = driver.findElement(By.xpath(String.format(FEATURED_IMAGE_LOCATOR, imageFileName)));
		return isFeaturedImageDisplayed.isDisplayed();
	}
}
