package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.ScreenShooter;


public class BlogHomePage extends AbstractPage
{
	private static final String CUSTOM_FONT = "//style[@id='jetpack-custom-fonts-css']" +
			                    "[contains(text(),'font-family:\"%s\"')]";
	private static final String CATEGORY_LOCATOR = "//ul/li/a[contains(text(),'%s')]";
	private static final By ANY_CUSTOM_FONT = By.xpath("//style[@id='jetpack-custom-fonts-css']");
	private static final By CUSTOM_COLOR_LOCATOR = By.xpath("//head/meta[@name='theme-color']");

	@FindBy(xpath="//a[@href='https://wordpress.com/stats/atmhometask.wordpress.com']")
	private WebElement mySiteButton;

	@FindBy(className="widget_categories")
	private WebElement categoryWidget;

	@FindBy(xpath="//aside[@class='widget widget_pages']")
	private WebElement pageWidget;

	@FindBy(xpath = "//h1[@class='site-title']/a")
	private WebElement blogTitleItem;

	@FindBy(xpath = "//head/meta[@name='theme-color']")
	private WebElement customBackgroundColor;

	public BlogHomePage() {super();}

	public BlogHomePage openBlogHomePage(String url)
	{
		driver.get(url);
		return this;
	}

	public String getCurrentUrl()
	{
		return driver.getCurrentUrl();
	}

	public HomePageSettingsPanel openHomePageSettingsPanel()
	{
		mySiteButton.click();
		return new HomePageSettingsPanel();
	}

	public boolean isCategoryWidgetDisplayed()
	{
		return categoryWidget.isDisplayed();
	}

	public CategoryPage goToCategoryPage(String category)
	{
		WebElement categoryElement = driver.findElement(By.xpath(String.format(CATEGORY_LOCATOR, category)));
		waitForElementClickable(categoryElement).click();
		return new CategoryPage();
	}

	public boolean isPageWidgetDisplayed()
	{
		highlightElement(pageWidget);
		ScreenShooter.takeScreenshot();
		unHighlightElement(pageWidget);
		return pageWidget.isDisplayed();
	}

	public String getBlogTitle()
	{
		return waitForElementVisible(blogTitleItem).getText();
	}

	public String getNewBackgroundColor()
	{
		return customBackgroundColor.getAttribute("content");
	}

	public boolean isDefaultBackgroundColor()
	{
		return !isElementPresent(CUSTOM_COLOR_LOCATOR);
	}

	public boolean isNewHeadingFont(String fontName)
	{
		return isElementPresent(By.xpath(String.format(CUSTOM_FONT, fontName)));
	}

	public boolean isDefaultFont()
	{
		return !isElementPresent(ANY_CUSTOM_FONT);
	}
}
