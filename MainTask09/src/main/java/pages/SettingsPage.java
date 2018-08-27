package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SettingsPage extends AbstractPage
{
	private static final String CATEGORY_CONTEXT_MENU = "//span[@class='ellipsis-menu']" +
			             "[..//span[@class='taxonomy-manager__label']/span[contains(text(),'%s')]]";
	private static final String CATEGORY_NAME_ITEM = "//span[contains(text(),'%s')]";

	@FindBy(xpath="//a[@href='/settings/writing/atmhometask.wordpress.com']")
	private WebElement writingTab;

	@FindBy(xpath="//a[@href='/settings/taxonomies/category/atmhometask.wordpress.com']")
	private WebElement categoriesLink;

	@FindBy(xpath="//button[@role='menuitem'][2]")
	private WebElement deleteCategoryButton;

	@FindBy(xpath="//span[@class='dialog__button-label'][contains(text(),'OK')]")
	private WebElement approveCategoryDeletionButton;

	public SettingsPage() {super();}

	public SettingsPage openWritingTab()
	{
		waitForElementClickable(writingTab).click();
		return this;
	}

	public SettingsPage openCategoriesSettings()
	{
		waitForElementClickable(categoriesLink).click();
		return this;
	}

	public SettingsPage deleteCategory(String category)
	{
		driver.findElement(By.xpath(String.format(CATEGORY_CONTEXT_MENU, category))).click();
		waitForElementClickable(deleteCategoryButton).click();
		waitForElementClickable(approveCategoryDeletionButton).click();
		WebElement categoryName = driver.findElement(By.xpath(String.format(CATEGORY_NAME_ITEM, category)));
		waitForInvisibility(categoryName);
		return this;
	}

	public boolean isCategoryFound(String category)
	{
		return isElementPresent(By.linkText(category));
	}
}
