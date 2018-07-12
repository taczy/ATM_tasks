package selenium05;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

public class BaseTest
{
	protected WebDriver driver;
	protected WebDriverWait wait;

	protected void waitForVisibility(String xpath) // explicit waiter - until element is visible
	{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	}

	protected boolean waitForInvisibility(By webelementLocator, Integer seconds) // explicit waiter - until element is invisible
	{
		Boolean element = wait.until(ExpectedConditions.invisibilityOfElementLocated(webelementLocator));
		return element;
	}

	protected void waitForClickable(WebElement element)
	{
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

//	protected void jsElementClick(WebElement element) //JS executor - check an element is clickable and click
//	{
//		JavascriptExecutor js = (JavascriptExecutor) driver;
//		js.executeScript("arguments[0].click();", element);
//	}

	protected boolean isElementPresent(By by)
	{
		return !driver.findElements(by).isEmpty(); // Custom implementation for checking is ElementPresent
	}

	@BeforeClass(description = "Open browser in incognito mode and maximize the window. Add implicit waits")
	public void runBrowser()
	{
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("incognito");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		options.addArguments("start-maximized");
		driver = new ChromeDriver(options);
		wait = new WebDriverWait(driver, 15);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@BeforeMethod(description = "Add implicit timeout before methods")
	public void timeOut()
	{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	}

	@AfterClass(description = "Close browser")
	public void closeBrowser()
	{
		driver.quit();
	}
}
