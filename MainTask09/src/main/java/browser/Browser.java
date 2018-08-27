package browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Browser
{
	private static WebDriver driver;

	private Browser(){}

	public static WebDriver getWebDriverInstance()
	{
		if (driver != null)
		{
			return driver;
		}
		return driver = webdriverInitializationAndConfiguring();
	}

	private static WebDriver webdriverInitializationAndConfiguring()
	{
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");
		options.addArguments("--start-maximized");
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		WebDriver driver = null;
		try
		{
			driver = new RemoteWebDriver(new URL("http://127.0.0.1:4444/wd/hub"), capabilities);
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return driver;
	}

	public static void kill()
	{
		if (driver != null)
		{
			try
			{
				driver.quit();
			}
			catch (Exception e)
			{
				System.out.println("Cannot kill browser");
			}
			finally
			{
				driver = null;
			}
		}
	}
}
