package day03;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@SuppressWarnings("unused")

public class Day03Part1 {

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
			System.out.println(fullInput);
		}
		fullInput = fullInput.substring(0, fullInput.length() - 1);
		
		int count = 0; //sum to return
		Pattern multPattern = Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)"); //each mul(#,#) command
		Matcher multMatcher = multPattern.matcher(fullInput);
		while(multMatcher.find())
		{
			String toMultiply = multMatcher.group(); //the command
			int commaIndex = toMultiply.indexOf(','); //split command based on comma
			int firstNum = Integer.valueOf(toMultiply.substring(4,commaIndex)); //firstNum
			int secondNum = Integer.valueOf(toMultiply.substring(commaIndex + 1, toMultiply.length() - 1)); //secondNum
			count += firstNum * secondNum;
		}
		System.out.println(count);
		br.close();
	}

}
