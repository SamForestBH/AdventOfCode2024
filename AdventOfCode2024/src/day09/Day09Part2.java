package day09;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Utilities.InputParser;
import Utilities.StringGridWalker;

public class Day09Part2 {
	
	
	
	public static void main(String[] args) throws IOException 
	{
		//Reads line from input file
		File input = new File("src\\day09\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String diskMap = br.readLine();
		String unchangedDiskMap = diskMap + "";
		long checkSum = 0;
		int diskGapIndex = 1;
		int diskBackIndex = diskMap.length() - 1;
		long fileSystemTotalSpace = 0;
		for (int i = 0; i < diskMap.length(); i++)
		{
			fileSystemTotalSpace += Character.getNumericValue(diskMap.charAt(i));
		}
		//System.out.println(diskBackIndex);
		//Even map length means the last entry is empty space
		if (diskMap.length() % 2 == 0)
		{
			diskBackIndex -= 1;
		}
		long fileSystemBackIndex = fileSystemTotalSpace;
		//Each loop handles the next file and the next chunk of empty space
		//diskFrontIndex increments by 2 each time
		//diskBackIndex increments as needed to fill gaps
		
		while (diskBackIndex >= 0)
		{
			diskGapIndex = 1;
			long fileSystemFrontIndex = Character.getNumericValue(unchangedDiskMap.charAt(0))
					+ Character.getNumericValue(unchangedDiskMap.charAt(1))
					- Character.getNumericValue(diskMap.charAt(1));
			int backFileSize = Character.getNumericValue(diskMap.charAt(diskBackIndex));
			int currentGapSize = Character.getNumericValue(diskMap.charAt(diskGapIndex));
			System.out.println("Attempting to home file of size " + backFileSize + " with ID " + (diskBackIndex/2));
			System.out.print("Checking gaps: " + fileSystemFrontIndex + ", ");
			while (currentGapSize < backFileSize && diskGapIndex < diskBackIndex)
			{
				fileSystemFrontIndex += Character.getNumericValue(diskMap.charAt(diskGapIndex)) 
						+ Character.getNumericValue(unchangedDiskMap.charAt(diskGapIndex + 1))
						+ Character.getNumericValue(unchangedDiskMap.charAt(diskGapIndex + 2))
						- Character.getNumericValue(diskMap.charAt(diskGapIndex + 2));
				System.out.print(fileSystemFrontIndex + ", ");
				diskGapIndex += 2;
				currentGapSize = Character.getNumericValue(diskMap.charAt(diskGapIndex));
			}
			System.out.println();
			long initialID;
			if (diskGapIndex > diskBackIndex)
			{
				initialID = fileSystemBackIndex - backFileSize;
				System.out.println("No home found; start index is unchanged value of " + initialID);
			}
			else
			{
				initialID = fileSystemFrontIndex;
				diskMap = diskMap.substring(0, diskGapIndex) + (currentGapSize - backFileSize) + diskMap.substring(diskGapIndex+1, diskMap.length());
				System.out.println("Home found; new start index is " + initialID);
			}
			for (int i = 0; i < backFileSize; i++)
			{
				checkSum += initialID * (diskBackIndex / 2);
				initialID ++;
			}
			if (diskBackIndex != 0)
			{
				fileSystemBackIndex -= (backFileSize + Character.getNumericValue(unchangedDiskMap.charAt(diskBackIndex - 1)));
			}
			diskBackIndex -= 2;
		}
		System.out.println(checkSum);
		br.close();
	}
}
