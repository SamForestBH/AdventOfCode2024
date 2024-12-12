package day02;

import java.io.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@SuppressWarnings("unused")

public class Day02Part1 {

	public static void main(String[] args) throws IOException {
		//Reads line from input file
		File input = new File("src\\day02\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		int numSafeReports = 0;
		//Loop through file, add each line to the two lists
		while((line = br.readLine()) != null)
		{
			//Line to integer list
			String[] splits = line.split("\\s+");
			List<Integer> report = new ArrayList<Integer>();
			for (String s : splits)
			{
				report.add(Integer.valueOf(s));
			}			
			//Fail if the first report values match
			//gapMult is 1 for increasing, -1 for decreasing
			boolean isSafe = true;
			int gapMult = Integer.signum(report.get(1) - report.get(0));
			for (int i = 0; i < report.size() - 1; i++)
			{
				int curr = report.get(i);
				int next = report.get(i+1);
				//Break if two values match, differ by more than 3, or else are going the wrong way. 
				if ((curr == next) || (Math.abs(curr - next) > 3) || (Integer.signum((next - curr) * gapMult) == -1))
				{
					System.out.println(curr + ", " + next);
					isSafe = false;
					break;
				}
			}
			if (isSafe)
			{
				numSafeReports ++;
			}
		}
		System.out.println(numSafeReports);
		br.close();
	}

}
