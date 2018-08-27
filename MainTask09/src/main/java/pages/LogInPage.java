package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LogInPage extends AbstractPage
{
	private static final String WP_ADMIN_URL = "https://wordpress.com/wp-login.php";

	@FindBy(id="usernameOrEmail")
	private WebElement loginField;

	@FindBy(className="login__form-action")
	private WebElement continueButton;

	@FindBy(id="password")
	private WebElement passwordField;

	@FindBy(className="login__form-action")
	private WebElement submitCredentialsButton;

	@FindBy(xpath="//a[@href='/me']")
	private WebElement userProfileIcon;

	public LogInPage() {super();}

	public LogInPage logIn(String email, String password)
	{
		enterLogin(email);
		enterPassword(password);
		waitForElementVisible(userProfileIcon);
		return this;
	}

	public ProfilePage openProfile()
	{
		userProfileIcon.click();
		return new ProfilePage();
	}

	public LogInPage enterLogin(String email)
	{
		driver.get(WP_ADMIN_URL);
		waitForElementVisible(loginField).clear();
		loginField.sendKeys(email);
		continueButton.click();
		return this;
	}

	public LogInPage enterPassword(String password)
	{
		waitForElementVisible(passwordField).sendKeys(password);
		waitForElementClickable(submitCredentialsButton).click();
		return this;
	}
}
