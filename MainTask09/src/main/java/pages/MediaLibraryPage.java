package pages;

import org.openqa.selenium.By;

public class MediaLibraryPage extends AbstractPage
{
	private static final String IMAGE_LOCATOR = "//figure[@title='%s']";

	public MediaLibraryPage() {super();}

	public boolean isImageUploaded(String imageFileName)
	{
		return driver.findElement(By.xpath(String.format(IMAGE_LOCATOR, imageFileName))).isDisplayed();
	}
}
