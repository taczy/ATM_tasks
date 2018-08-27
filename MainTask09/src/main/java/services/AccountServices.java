package services;

import bo.Account;
import pages.LogInPage;
import pages.ProfilePage;

public class AccountServices
{
	public static void userLogIn(Account account)
	{
		new LogInPage()
				.logIn(account.getLogin(), account.getPassword());
	}

	public static void userLogOut()
	{
		new ProfilePage()
				.openProfilePage()
				.logOut();
	}

}
