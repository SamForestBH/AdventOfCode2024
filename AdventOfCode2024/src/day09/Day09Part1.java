package day09;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Utilities.InputParser;
import Utilities.StringGridWalker;

public class Day09Part1 {
	
	
	
	public static void main(String[] args) throws IOException 
	{
		//Reads line from input file
		File input = new File("src\\day09\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String diskMap = br.readLine();
		long checkSum = 0;
		int diskFrontIndex = 0;
		int diskBackIndex = diskMap.length() - 1;
		System.out.println(diskBackIndex);
		//Even map length means the last entry is empty space
		if (diskMap.length() % 2 == 0)
		{
			diskBackIndex -= 1;
		}
		int fileSystemIndex = 0;
		int backFileRemaining = Character.getNumericValue(diskMap.charAt(diskBackIndex));
		System.out.println(backFileRemaining);
		//Each loop handles the next file and the next chunk of empty space
		//diskFrontIndex increments by 2 each time
		//diskBackIndex increments as needed to fill gaps
		while (diskFrontIndex < diskBackIndex)
		{
			int currentFileLength = Character.getNumericValue(diskMap.charAt(diskFrontIndex));
			int currentGapLength = Character.getNumericValue(diskMap.charAt(diskFrontIndex + 1));
			System.out.println("New front file; Disk Index:" + diskFrontIndex + "; fileSystemIndex: " + fileSystemIndex);
			for (int i = 0; i < currentFileLength; i++)
			{
				checkSum += fileSystemIndex * (diskFrontIndex/2);
				System.out.println("Adding " + fileSystemIndex + " * " +(diskFrontIndex/2) + " = " + fileSystemIndex * (diskFrontIndex/2) + " to checkSum");
				fileSystemIndex ++;
			}
			diskFrontIndex += 2;
			for (int j = 0; j < currentGapLength; j++)
			{
				checkSum += fileSystemIndex * (diskBackIndex / 2);
				System.out.println("Adding " + fileSystemIndex + " * " +(diskBackIndex/2) + " = " + fileSystemIndex * (diskBackIndex/2) + " to checkSum");
				fileSystemIndex ++;
				backFileRemaining --;
				if (backFileRemaining == 0)
				{
					diskBackIndex -= 2;
					backFileRemaining = Character.getNumericValue(diskMap.charAt(diskBackIndex));
					System.out.println("New back file; Disk Index:" + diskBackIndex + "; fileSystemIndex: " + fileSystemIndex);
				}
			}
		}
		System.out.println("Draining back file");
		while (backFileRemaining > 0)
		{
			checkSum += fileSystemIndex * (diskBackIndex/2);
			fileSystemIndex ++;
			System.out.println("Adding " + fileSystemIndex + " * " +(diskBackIndex/2) + " = " + fileSystemIndex * (diskBackIndex/2) + " to checkSum");
			backFileRemaining --;
		}
		System.out.println(checkSum);
		br.close();
	}
}
