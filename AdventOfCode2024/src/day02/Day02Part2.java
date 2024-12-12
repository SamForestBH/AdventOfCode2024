package day02;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@SuppressWarnings("unused")

public class Day02Part2 {

	public static void main(String[] args) throws IOException {
		//Reads line from input file
		File input = new File("src\\day02\\testFile.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		int numSafeReports = 0; //Return value
		int reportNum = 1;
		List<Integer> safeRows = new ArrayList<Integer>();
		//Loop through file
		while((line = br.readLine()) != null)
		{
			//System.out.print(reportNum + ": ");
			reportNum ++;
			//Line to integer list
			String[] splits = line.split("\\s+");
			List<Integer> report = new ArrayList<Integer>();
			List<Integer> originalReport = new ArrayList<Integer>();
			for (String s : splits)
			{
				report.add(Integer.valueOf(s));
				originalReport.add(Integer.valueOf(s));
			}
			//System.out.println(report);
			int failedLevels = 0;
			int temp = 0;
			int failIndex = 0;
			int i = 0;
			int gapMult = Integer.signum(report.get(1) - report.get(0)); // 1 if increasing, -1 if decreasing
			outerloop:
			while (i < report.size() - 1)
			{
				int curr = report.get(i);
				int next = report.get(i+1);
				
				//Break if two values match, differ by more than 3, or else are going the wrong way. 
				if ((Math.abs(curr - next) > 3) || (Integer.signum((next - curr) * gapMult) != 1))
				{
					//System.out.println("(" + curr + ", " + next + ")");
					failedLevels ++;
					switch (failedLevels)
					{
					case 1: //Remove the current level, reset and check the new list. 
						temp = report.get(i);
						report.remove(i);
						failIndex = i;
						i = -1;
						gapMult = Integer.signum(report.get(1) - report.get(0)); // 1 if increasing, -1 if decreasing
						//System.out.println(report);
						break;
					case 2: //Maybe it works if you remove next instead of curr? 
						report.set(failIndex, temp);
						i = -1;
						gapMult = Integer.signum(report.get(1) - report.get(0)); // 1 if increasing, -1 if decreasing
						//System.out.println(report);
						break;						
					case 3: //Last chance: does removing the first element fix it? 
						report = originalReport;
						report.remove(0);
						i = -1;
						gapMult = Integer.signum(report.get(1) - report.get(0)); // 1 if increasing, -1 if decreasing
						//System.out.println(report);
						break;	
					case 4: 
						System.out.println(reportNum + ": " + originalReport);// + "->" + report);
						//System.out.println("FAIL!");
						break outerloop;
					}
				}
				i++;
			}
			if (failedLevels < 4) //One of the three passes worked
			{
				if (failedLevels == 3)
				{
					//System.out.println(reportNum + ": " + originalReport + "->" + report);
				}
				//System.out.println("SAFE!");
				safeRows.add(reportNum);
				numSafeReports ++;
			}
		}
		System.out.println(safeRows);
		System.out.println(numSafeReports);
		br.close();
	}

}
