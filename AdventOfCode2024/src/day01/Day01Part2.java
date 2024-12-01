package day01;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day01Part2 {

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
		int i = 0;
		int j = 0;
		//Loop through first list
		while (i < l1.size())
		{
			int current = l1.get(i); //list element for l1
			int l2Count = 0;
			//Skip to current in l2
			while (l2.get(j) < current)
			{
				j++;
			}
			//count how many times that element is found
			while (l2.get(j).equals(current))
			{
				l2Count ++;
				j++;
			}
			count += (current * l2Count);
			//Skip to next element in l1, unless out of bounds
			while (i < l1.size() && l1.get(i).equals(current))
			{
				i++;
			}
		}
		System.out.println(count);
	}

}
