package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CustomizingPage extends AbstractPage
{
	private static final By SITE_IDENTITY_FORM_OPENED = By.xpath("//li[@id='accordion-section-title_tagline']/h3[@tabindex='-1']");
	private static final String FONT_ITEM = "//div[@class='jetpack-fonts__option'][contains(text(),'%s')]";
	private static final By INACTIVE_PUBLISH_BUTTON = By.xpath("//button[@style='display: none;'][..//input[@id='save']]");
	private static final By COLORS_FORM_OPENED = By.xpath("//li[@id='accordion-section-colors_manager_tool']/h3[@tabindex='-1']");

	@FindBy(xpath = "//form[@id='customize-controls']")
	private WebElement customizingPanel;

	@FindBy(className = "customize-controls-close")
	private WebElement closeCustomizePanel;

	@FindBy(xpath = "//input[@id='save']")
	private WebElement publishButton;

	@FindBy(xpath = "//li[@id='accordion-section-title_tagline']")
	private WebElement siteIdentityMenuItem;

	@FindBy(xpath = "//input[@id='_customize-input-blogname']")
	private WebElement titleInputField;

	@FindBy(xpath = "//li[@id='accordion-section-colors_manager_tool']")
	private WebElement colorsMenuItem;

	@FindBy(xpath = "//div[@class='background-rectangle']/div")
	private WebElement palletsButton;

	@FindBy(xpath = "//li[@data-role='bg']/span")
	private WebElement manualButton;

	@FindBy(xpath = "//a[@id='pick-your-nose']")
	private WebElement pickUpColorLink;

	@FindBy(xpath = "//input[@id='iris']")
	private WebElement customColorField;

	@FindBy(xpath = "//a[@class='revert revert-default button']")
	private WebElement defaultColorButton;

	@FindBy(xpath = "//li[@id='accordion-section-jetpack_fonts']")
	private WebElement fontsMenuItem;

	@FindBy(className = "jetpack-fonts__menu-container")
	private WebElement headingsFontsList;

	@FindBy(className = "jetpack-fonts__default-button")
	private WebElement defaultFontButton;

	public CustomizingPage() {super();}

	public boolean isCustomizingMode()
	{
		return waitForElementVisible(customizingPanel).isDisplayed();
	}

	public BlogHomePage backToMainPage()
	{
		waitForElementClickable(closeCustomizePanel).click();
		return new BlogHomePage();
	}

	public CustomizingPage changeBlogTitle(String newTitle)
	{
		changeName(newTitle);
		publishChanges();
		return this;
	}

	public CustomizingPage changeBackgroundColor(String color)
	{
		changeColor(color);
		publishChanges();
		return this;
	}

	public CustomizingPage setDefaultBackgroundColor()
	{
		diveIntoColorMenu();
		waitForElementClickable(defaultColorButton).click();
		publishChanges();
		return this;
	}

	public CustomizingPage setNewHeadingFont(String fontName)
	{
		waitForElementClickable(fontsMenuItem).click();
		waitForElementClickable(headingsFontsList).click();
		waitForElementClickable(driver.findElement(By.xpath(String.format(FONT_ITEM, fontName)))).click();
		publishChanges();
		return this;
	}

	public CustomizingPage setDefaultHeadingFont()
	{
		waitForElementClickable(fontsMenuItem).click();
		waitForElementClickable(defaultFontButton).click();
		publishChanges();
		return this;
	}

	private CustomizingPage publishChanges()
	{
		waitForElementClickable(publishButton).click();
		waitForElementPresence(INACTIVE_PUBLISH_BUTTON);
		return this;
	}

	private CustomizingPage changeName(String newTitle)
	{
		waitForElementClickable(siteIdentityMenuItem).click();
		waitForElementPresence(SITE_IDENTITY_FORM_OPENED);
		waitForElementClickable(titleInputField).clear();
		titleInputField.sendKeys(newTitle);
		return this;
	}

	private CustomizingPage changeColor(String color)
	{
		diveIntoColorMenu();
		waitForElementClickable(palletsButton).click();
		waitForElementClickable(manualButton).click();
		waitForElementClickable(pickUpColorLink).click();
		waitForElementClickable(customColorField).clear();
		customColorField.sendKeys(color);
		return this;
	}

	private CustomizingPage diveIntoColorMenu()
	{
		waitForElementClickable(colorsMenuItem).click();
		waitForElementPresence(COLORS_FORM_OPENED);
		return this;
	}
}
