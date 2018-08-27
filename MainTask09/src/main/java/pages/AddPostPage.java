package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import utils.ScreenShooter;

public class AddPostPage extends AbstractPage

{
	private static final String POST_BODY_FRAME_ID = "tinymce-1_ifr";

	@FindBy(xpath = "//button[@class='button editor-delete-post__button is-borderless']")
	private WebElement deletePostButton;

	@FindBy(xpath = "//textarea[@tabindex='1']")
	private WebElement postTitleField;

	@FindBy(className= "post-type-post")
	private WebElement postBodyField;

	@FindBy(xpath = "//div[@id='mceu_0']")
	private WebElement addObjectButton;

	@FindBy(xpath = "//span[@data-e2e-insert-type='media']")
	private WebElement addMediaButton;

	@FindBy(xpath = "//input[@type='range']")
	private WebElement sliderRangeButton;

	@FindBy(xpath = "//button[@data-e2e-button='confirm']")
	private WebElement insertImageButton;

	@FindBy(xpath = "//img")
	private WebElement postImage;

	@FindBy(xpath = "//div/span[@data-e2e-status='Saved']")
	private WebElement postDraftSaved;

	@FindBy(className = "editor-ground-control__back")
	private WebElement closePostButton;

	@FindBy(xpath="//div[@data-e2e-title='categories-tags']")
	private WebElement categoriesSidebarMenuItem;

	@FindBy(xpath="//div[@class='editor-term-selector__add-term is-compact']/button")
	private WebElement addNewCategoryButton;

	@FindBy(xpath="//fieldset[@class='form-fieldset']//input[@class='form-text-input']")
	private WebElement addNewCategoryForm;

	@FindBy(xpath="//button[@data-e2e-button='add']")
	private WebElement saveNewCategoryButton;

	@FindBy(xpath="//button[@tabindex='5']")
	private WebElement publishPostButton;

	public AddPostPage() {super();}

	public AddPostPage addNewPost(String title, String body, String imageFileName)
	{
		addPostTitle(title);
		addPostText(body);
		addPostImage(imageFileName);
		return this;
	}

	public AddPostPage autoSavePostDraft()
	{
		waitForElementVisible(postDraftSaved);
		return this;
	}

	public PostsListPage closePost()
	{
		jsElementClick(closePostButton);
		return new PostsListPage();
	}

	public AddPostPage addPostInNewCategory(String category)
	{
		categoriesSidebarMenuItem.click();
		waitForElementClickable(addNewCategoryButton).click();
		waitForElementVisible(addNewCategoryForm).sendKeys(category);
		waitForElementClickable(saveNewCategoryButton).click();
		return this;
	}

	public PostPreviewPage publishPostInCategory() throws InterruptedException
	{
        Thread.sleep(5000);
        waitForElementClickable(publishPostButton).click();
		return new PostPreviewPage();
	}

	private AddPostPage addPostTitle(String title)
	{
		waitForElementClickable(postTitleField).click();
		postTitleField.sendKeys(title);
		return this;
	}

	private AddPostPage addPostText(String body)
	{
		driver.switchTo().frame(POST_BODY_FRAME_ID);
		waitForElementClickable(postBodyField).click();
		postBodyField.sendKeys(body);
		new Actions(driver).sendKeys(Keys.ENTER);
		driver.switchTo().defaultContent();
		return this;
	}

	private AddPostPage addPostImage(String imageFileName)
	{
		waitForElementClickable(addObjectButton).click();
		waitForElementClickable(addMediaButton).click();
		ScreenShooter.takeScreenshot();
		new Actions(driver).clickAndHold(sliderRangeButton)
				.moveByOffset(70, 0)
				.release(sliderRangeButton)
				.build()
				.perform();
		ScreenShooter.takeScreenshot();
		WebElement image = driver.findElement(By.xpath("//figure[@title='"+imageFileName+"']/img"));
		image.click();
		waitForElementClickable(insertImageButton).click();
		return this;
	}
}
