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

import Utilities.InputParser;
import Utilities.StringGridWalker;

public class Day19Part2 {
	
	
	public static void main(String[] args) throws IOException 
	{
		//Reads line from input file
		File input = new File("src\\day19\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		line = br.readLine();
		String regPattern = "(?:";
		String[] splits = line.split(", ");
		for (String towel : splits)
		{
			regPattern = regPattern + towel + "|";
		}
		regPattern = regPattern.substring(0, regPattern.length() - 1) + ")+";
		Pattern pattern = Pattern.compile(regPattern);
		Matcher matcher;
		int count = 0;
		while ((line = br.readLine()) != null)
		{
			matcher = pattern.matcher(line);
			if (matcher.matches())
			{
				count++;
			}
		}
		System.out.print(count);
	}
}