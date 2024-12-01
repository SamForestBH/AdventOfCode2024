package day01;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day01Part1 {

	public static void main(String[] args) throws IOException {
		//Reads line from input file
		File input = new File("src\\day01\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		int count = 0; //Sum of differences
		List<Integer> l1 = new ArrayList<Integer>();
		List<Integer> l2 = new ArrayList<Integer>();
		//Loop through file, add each line to the two lists
		while((line = br.readLine()) != null)
		{
			String[] splits = line.split("\\s+");
			l1.add(Integer.valueOf(splits[0]));
			l2.add(Integer.valueOf(splits[1]));
		}
		//Sort each list
		Collections.sort(l1);
		Collections.sort(l2);
		//Add differences to count
		for (int i = 0; i < l1.size(); i++)
		{
			count += Math.abs(l1.get(i) - l2.get(i));
		}
		System.out.println(count);
	}

}
