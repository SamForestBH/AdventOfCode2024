package day03;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")

public class Day03Part2 {

	public static void main(String[] args) throws IOException {
		//Reads line from input file
		File input = new File("src\\day03\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String fullInput = "";
		String line;
		
		//Reclaim entire file as a single multiline string
		while ((line = br.readLine()) != null)
		{
			fullInput = fullInput + line + "\n";
		}
		fullInput = fullInput.substring(0, fullInput.length() - 1);
		
		int count = 0; //sum to return
		boolean multiplying = true;
		
		//Pattern matches either mul(#,#) command OR a do or don't command, prioritizing don't. 
		Pattern multPattern = Pattern.compile("(mul\\(\\d{1,3},\\d{1,3}\\))|(don't\\(\\))|(do\\(\\))");
		Matcher multMatcher = multPattern.matcher(fullInput);
		while(multMatcher.find())
		{
			String command = multMatcher.group();
			System.out.println(command);
			switch (command) //Either toggle multiplying on or off, or multiply (if toggled on). 
			{
			case "don't()":
				multiplying = false;
				break;
			case "do()":
				multiplying = true;
				break;
			default:
				if (multiplying)
				{
					String toMultiply = multMatcher.group(); //the command
					int commaIndex = toMultiply.indexOf(','); //split command based on comma
					int firstNum = Integer.valueOf(toMultiply.substring(4,commaIndex)); //firstNum
					int secondNum = Integer.valueOf(toMultiply.substring(commaIndex + 1, toMultiply.length() - 1)); //secondNum
					count += firstNum * secondNum;
				}
				break;
			}
		}
		System.out.println(count);
		br.close();
		
	}

}
