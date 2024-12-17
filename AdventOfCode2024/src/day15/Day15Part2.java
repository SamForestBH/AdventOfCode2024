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

public class Day15Part2 {
	
	static StringGridWalker map;
	
	public class BoxStruct
	{
		protected int selfRow;
		protected int selfLeftCol;
		protected List<BoxStruct> nextBoxes;
		
		public BoxStruct(int r, int c)
		{
			selfRow = r;
			selfLeftCol = c;
			nextBoxes = new ArrayList<BoxStruct>();
		}
		
		public int getRow()
		{
			return selfRow;
		}
		
		public int getLeftCol()
		{
			return selfLeftCol;
		}
		
		public int getRightCol()
		{
			return selfLeftCol + 1;
		}
		
		/*Checks the two spaces to see what's there. If both are empty, move and return true. If either is blocked, do nothing and return false.
		Otherwise, make new boxes as needed above and move if they do.  */ 
		public boolean attemptMoveInDirection(int direction)
		{
			int offset;
			if (direction == 2)
			{
				offset = -1;
			}
			else
			{
				offset = 1;
			}
			String leftTarget = map.letterAt(this.getRow() + offset, this.getLeftCol());
			String rightTarget = map.letterAt(this.getRow() + offset, this.getRightCol());
			if (leftTarget.equals("#") || rightTarget.equals("#"))
			{
				return false;
			}
			if (leftTarget.equals(".") && rightTarget.equals("."))
			{
				this.moveInDirection(direction);
				return true;
			}
			
		}
		
		//Moves box in given direction. Assumes the space is clear; called only by attemptMoveInDirection(). 
		private void moveInDirection(int direction)
		{
			int offset;
			if (direction == 2)
			{
				offset = -1;
			}
			else
			{
				offset = 1;
			}
			map.setLetterAt(this.getRow() + offset, this.getLeftCol(), "[");
			map.setLetterAt(this.getRow() + offset, this.getRightCol(), "]");
			map.setLetterAt(this.getRow(), this.getLeftCol(), ".");
			map.setLetterAt(this.getRow(), this.getRightCol(), ".");
		}
	}
	
	public static void main(String[] args) throws IOException 
	{
		//Reads line from input file
		File input = new File("src\\day15\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		map = new StringGridWalker();
		String instructions = "";
		while ((line = br.readLine()) != null)
		{
			if (line.equals(""))
			{
				
			}
			else if (line.charAt(0) == '#')
			{
				String wideFactoryLine = "";
				for (int i = 0; i < line.length(); i++)
				{
					String toAdd = "";
					switch (line.charAt(i))
					{
					case '#':
						toAdd = "##";
					case 'O':
						toAdd = "[]";
					case '.':
						toAdd = "..";
					case '@':
						toAdd = "@.";
					}
					wideFactoryLine += toAdd;
				}
				map.addLine(wideFactoryLine);
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
			if (directionToMove == StringGridWalker.RIGHT 
					|| directionToMove == StringGridWalker.LEFT)
			{
				target = map.letterInDirectionAtDistance(directionToMove, lastBoxDistance);
				while(target.equals("[") || target.equals("]"))
				{
					lastBoxDistance ++;
					target = map.letterInDirectionAtDistance(directionToMove, lastBoxDistance);
				}
				if (target.equals("."))
				{
					System.out.println(lastBoxDistance);
					map.setDirection(directionToMove);
					for (int j = 0; j < lastBoxDistance; j++)
					{
						map.setLetterInCurrentDirectionAtDistance(j+1, 
							map.letterInCurrentDirectionAtDistance(j));
					}
					map.setCurLetter(".");
					map.moveInCurDirection();
				}
			}
			else //Now the most fun part yet
			{
				
			}
			System.out.println(map.toString());
		}
		long gpsSum = 0;
		for (int row = 0; row < map.getNumRows(); row++)
		{
			for (int col = 0; col < map.getNumCols(); col++)
			{
				if (map.letterAt(row, col).equals("["))
				{
					gpsSum += 100*row + col;
				}
			}
		}
		System.out.println(gpsSum);
	}
}