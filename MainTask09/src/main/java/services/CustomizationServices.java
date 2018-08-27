package services;

import pages.BlogHomePage;
import pages.CustomizingPage;

public class CustomizationServices
{
	public static void runCustomizingMode()
	{
		new BlogHomePage()
				.openHomePageSettingsPanel()
				.clickCustomize();
	}

	public static void changeBlogTitle(String title)
	{
		new CustomizingPage()
				.changeBlogTitle(title)
				.backToMainPage();
	}

	public static void changeBackgroundColor(String color)
	{
		new CustomizingPage()
				.changeBackgroundColor(color)
				.backToMainPage();
	}

	public static void changeBackgroundColorToDefault()
	{
		new CustomizingPage()
				.setDefaultBackgroundColor()
				.backToMainPage();
	}

	public static void changeHeadingFontType(String font)
	{
		new CustomizingPage()
				.setNewHeadingFont(font)
				.backToMainPage();
	}

	public static void changeHeadingFontToDefault()
	{
		new CustomizingPage()
				.setDefaultHeadingFont()
				.backToMainPage();
	}
}
