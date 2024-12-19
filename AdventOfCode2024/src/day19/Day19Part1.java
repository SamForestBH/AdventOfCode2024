package day19;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;

import Utilities.InputParser;
import Utilities.StringGridWalker;

public class Day19Part1 {
	
	static HashMap<String, Long> towelMap = new HashMap<>();
	static String[] towels;
	
	public static long getPatternCount(String line)
	{
		if (line.equals(""))
		{
			return 1;
		}
		if (towelMap.containsKey(line))
		{
			return towelMap.get(line);
		}
		long toReturn = 0;
		for (String towel : towels)
		{
			if (line.length() >= towel.length() && line.substring(0, towel.length()).equals(towel))
			{
				toReturn += getPatternCount(line.substring(towel.length()));
			}
		}
		towelMap.put(line, toReturn);
		return toReturn;
	}
	
	public static void main(String[] args) throws IOException 
	{
		//Reads line from input file
		File input = new File("src\\day19\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		line = br.readLine();
		towels = line.split(", ");
		line = br.readLine();
		long count = 0;
		while ((line = br.readLine()) != null)
		{
			long towelCount = getPatternCount(line);
			System.out.print(towelCount + " + ");
			count += towelCount;
		}
		System.out.println();
		System.out.print(" = " + count);
	}
}