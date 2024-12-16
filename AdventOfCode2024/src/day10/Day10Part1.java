package day10;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Utilities.InputParser;
import Utilities.StringGridWalker;

public class Day10Part1 {
	
	static StringGridWalker topoMap = new StringGridWalker();
	static boolean[][] nineReached;
	
	public static void resetBoolean()
	{
		for (int i = 0; i < topoMap.getNumRows(); i++)
		{
			for (int j = 0; j < topoMap.getNumCols(); j++)
			{
				nineReached[i][j] = false;
			}
		}
	}
	
	//Returns the count of paths going from height to 9 that begin at location. 
	public static int countPaths(int locx, int locy, int height)
	{
		
		if (topoMap.letterAt(locx, locy).equals("Outside of Grid") 
				|| Integer.valueOf(topoMap.letterAt(locx, locy)) != height
				|| (height == 9 && nineReached[locx][locy] == true))
		{
			return 0;
		}
		if (height == 9)
		{
			nineReached[locx][locy] = true;
			return 1;
		}
		//System.out.println("Currently at (" + locx + ", " + locy + ") at height " + height);
		return countPaths(locx, locy + 1, height + 1)
				+ countPaths(locx, locy - 1, height + 1)
				+ countPaths(locx - 1, locy, height + 1)
				+ countPaths(locx + 1, locy, height + 1);
	}
	
	public static void main(String[] args) throws IOException 
	{
		//Reads line from input file
		File input = new File("src\\day10\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		while ((line = br.readLine()) != null)
		{
			topoMap.addLine(line);
		}
		br.close();
		nineReached = new boolean[topoMap.getNumRows()][topoMap.getNumCols()];
		long hikeCount = 0;
		for (int startRow = 0; startRow < topoMap.getNumRows(); startRow++)
		{
			for (int startCol = 0; startCol < topoMap.getNumCols(); startCol ++)
			{
				int toAdd = countPaths(startRow, startCol, 0);
				if (toAdd != 0)
				{
					System.out.println("Path beginning at (" + startRow + ", " + startCol + ") has length " + toAdd);
				}
				hikeCount += toAdd;
				resetBoolean();
			}
		}
		System.out.println(hikeCount);
	}
}
