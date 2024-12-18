package day15;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Utilities.InputParser;
import Utilities.StringGridWalker;

public class Day15Part1 {
	
	
	public static void main(String[] args) throws IOException 
	{
		//Reads line from input file
		File input = new File("src\\day15\\testFile.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		StringGridWalker map = new StringGridWalker();
		String instructions = "";
		while ((line = br.readLine()) != null)
		{
			if (line.equals(""))
			{
				
			}
			else if (line.charAt(0) == '#')
			{
				map.addLine(line);
				if (line.contains("@"))
				{
					map.setCurPos(map.getNumRows() - 1, line.indexOf("@"));
				}
			}
			else
			{
				instructions = (instructions + line).split("\s")[0];
			}
		}
		System.out.println(map.toString());
		for (int i = 0; i < instructions.length(); i++)
		{
			char instructionChar = instructions.charAt(i);
			int directionToMove = StringGridWalker.directionFromFacing(instructionChar);
			int lastBoxDistance = 1;
			String target;
			while((target = map.letterInDirectionAtDistance(directionToMove, lastBoxDistance)).equals("O"))
			{
				lastBoxDistance ++;
			}
			if (target.equals("."))
			{
				System.out.println(lastBoxDistance);
				map.setDirection(directionToMove);
				for (int j = 1; j < lastBoxDistance; j++)
				{
					map.setLetterInCurrentDirectionAtDistance(j+1, "O");
				}
				map.setLetterInCurrentDirectionAtDistance(1, "@");
				map.setCurLetter(".");
				map.moveInCurDirection();
			}
			System.out.println(map.toString());
		}
		long gpsSum = 0;
		for (int row = 0; row < map.getNumRows(); row++)
		{
			for (int col = 0; col < map.getNumCols(); col++)
			{
				if (map.letterAt(row, col).equals("O"))
				{
					gpsSum += 100*row + col;
				}
			}
		}
		System.out.println(gpsSum);
	}
}