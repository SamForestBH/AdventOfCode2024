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
	
	public static void main(String[] args) throws IOException 
	{
		//Reads line from input file
		File input = new File("src\\day10\\testFile.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		StringGridWalker topoMap = new StringGridWalker();
		String line;
		while ((line = br.readLine()) != null)
		{
			topoMap.addLine(line);
		}
		br.close();
		for (int startRow = 0; startRow < topoMap.getnumRows(); startRow++)
		{
			for (int startCol = 0; startCol < topoMap.getnumCols(); startCol ++)
			{
				topoMap.setCurPos(startRow, startCol);
				if (Integer.valueOf(topoMap.curLetter()).equals(0))
				{
					System.out.println("Start location at (" + startRow + ", " + startCol + ")");
				}
			}
		}
	}
}
