package Utilities;

import java.util.ArrayList;
import java.util.List;

public class InputParser {

	public static List<Integer> splitOnWhitespaceToIntList(String line)
	{
		String[] splits = line.split("\s");
		List<Integer> toReturn = new ArrayList<Integer>();
		for (String s : splits)
		{
			toReturn.add(Integer.valueOf(s));
		}
		return toReturn;
	}
	
	public static List<Integer> splitOnNonDigitToIntList(String line)
	{
		String[] splits = line.split("[\\D]+");
		List<Integer> toReturn = new ArrayList<Integer>();
		for (String s : splits)
		{
			toReturn.add(Integer.valueOf(s));
		}
		return toReturn;
	}
	
	public static List<Long> splitOnWhitespaceToLongList(String line)
	{
		String[] splits = line.split("\s");
		List<Long> toReturn = new ArrayList<Long>();
		for (String s : splits)
		{
			toReturn.add(Long.valueOf(s));
		}
		return toReturn;
	}
	
	public static List<Long> splitOnNonDigitToLongList(String line)
	{
		String[] splits = line.split("[\\D]+");
		List<Long> toReturn = new ArrayList<Long>();
		for (String s : splits)
		{
			toReturn.add(Long.valueOf(s));
		}
		return toReturn;
	}
}
