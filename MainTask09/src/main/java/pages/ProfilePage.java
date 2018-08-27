package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProfilePage extends AbstractPage
{
	private static final String PROFILE_PAGE_URL = "https://wordpress.com/me";
	private static final String USER_DISPLAY_NAME_FIELD = "//input[@value='%s']";

	@FindBy(xpath="//button[@title='Log out of WordPress.com']")
	private WebElement logOutButton;

	@FindBy(xpath = "//a[contains(text(), 'Sign In')]")
	private WebElement signInLink;

	public ProfilePage() {super();}

	public ProfilePage openProfilePage()
	{
		driver.get(PROFILE_PAGE_URL);
		return this;
	}

	public boolean isUsername(String userName)
	{
		WebElement userDisplayName = driver.findElement(By.xpath(String.format(USER_DISPLAY_NAME_FIELD, userName)));
		waitForElementVisible(userDisplayName);
		return userDisplayName.isDisplayed();
	}

	public ProfilePage logOut()
	{
		waitForElementClickable(logOutButton);
		logOutButton.click();
		return this;
	}

	public boolean isLogOut()
	{
		return signInLink.isDisplayed();
	}
}
