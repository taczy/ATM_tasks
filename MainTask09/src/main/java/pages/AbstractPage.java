package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import browser.Browser;

public class AbstractPage
{
	private static final int WAIT_FOR_ELEMENT_TIMEOUT_SECONDS = 15;

	protected WebDriver driver;
	protected WebDriverWait wait;
	private String blogUrl;

	public AbstractPage()
	{
		this.driver = Browser.getWebDriverInstance();
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, WAIT_FOR_ELEMENT_TIMEOUT_SECONDS);
	}

	public void setBlogUrl(String blogUrl)
	{
		this.blogUrl = blogUrl;
	}

	public String getBlogUrl()
	{
		return blogUrl;
	}

	public boolean isElementPresent(By locator)
	{
		return !driver.findElements(locator).isEmpty();
	}

	protected WebElement waitForElementVisible(WebElement element)
	{
		return wait.until(ExpectedConditions.visibilityOf(element));
	}

	protected WebElement waitForElementPresence(By locator)
	{
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	protected WebElement waitForElementClickable(WebElement element)
	{
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	protected void waitForInvisibility(WebElement element)
	{
		wait.until(ExpectedConditions.invisibilityOf(element));
	}

	protected void highlightElement(WebElement element)
	{
		((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid green'", element);
	}

	protected void unHighlightElement(WebElement element)
	{
		((JavascriptExecutor) driver).executeScript("arguments[0].style.border='0px'", element);
	}

	protected void jsElementClick(WebElement element)
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}
}
