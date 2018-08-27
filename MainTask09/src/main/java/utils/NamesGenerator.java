package utils;

import java.util.Random;

public class NamesGenerator
{
	public static String generateCategoryName(String name)
	{
		name+= Integer.toString(new Random().nextInt(9999));
		return name;
	}
}
