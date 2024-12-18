package day15;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Utilities.InputParser;
import Utilities.StringGridWalker;

public class Day15Part2 {
	
	static StringGridWalker map;
	
	public static class BoxStruct
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
		
		//Only called on the front box in the stack. Checks if the whole stack can move. If it can, moves the stack. 
		public boolean attemptToMoveInDirection(int direction)
		{
			if (this.isFreeToMoveInDirection(direction))
			{
				this.shove(direction);
				return true;
			}
			return false;
		}
		
		public void shove(int direction)
		{
			for (BoxStruct box : nextBoxes)
			{
				box.shove(direction);
			}
			for (BoxStruct box : nextBoxes)
			{
				box.moveInDirection(direction);
			}
			this.moveInDirection(direction);
		}
		
		/*Checks the two spaces to see what's there. If both are empty, move and return true. If either is blocked, do nothing and return false.
		Otherwise, make new boxes as needed above and move if they do.  */ 
		public boolean isFreeToMoveInDirection(int direction)
		{
			int offset = BoxStruct.getOffset(direction);
			String leftTarget = map.letterAt(this.getRow() + offset, this.getLeftCol());
			String rightTarget = map.letterAt(this.getRow() + offset, this.getRightCol());
			if (leftTarget.equals("#") || rightTarget.equals("#"))
			{
				return false;
			}
			if (leftTarget.equals("]"))
			{
				nextBoxes.add(new BoxStruct(this.getRow() + offset, this.getLeftCol() - 1));
			}
			if (rightTarget.equals("["))
			{
				nextBoxes.add(new BoxStruct(this.getRow() + offset, this.getRightCol()));
			}
			if (rightTarget.equals("]"))
			{
				nextBoxes.add(new BoxStruct(this.getRow() + offset, this.getLeftCol()));
			}
			boolean emptyAhead = true;
			for (BoxStruct bs : nextBoxes)
			{
				emptyAhead = emptyAhead && bs.isFreeToMoveInDirection(direction);
			}
			return emptyAhead;
		}
		
		//Moves box in given direction, including moving both children. Assumes the entire stack is clear to move.  
		private void moveInDirection(int direction)
		{
			int offset = BoxStruct.getOffset(direction);
			map.setLetterAt(this.getRow() + offset, this.getLeftCol(), "[");
			map.setLetterAt(this.getRow() + offset, this.getRightCol(), "]");
			map.setLetterAt(this.getRow(), this.getLeftCol(), ".");
			map.setLetterAt(this.getRow(), this.getRightCol(), ".");
		}
		
		public static int getOffset(int direction)
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
			return offset;
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
		int boxCount = 0;
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
						break;
					case 'O':
						boxCount ++;
						toAdd = "[]";
						break;
					case '.':
						toAdd = "..";
						break;
					case '@':
						toAdd = "@.";
						break;
					}
					wideFactoryLine += toAdd;
				}
				map.addLine(wideFactoryLine);
				if (wideFactoryLine.contains("@"))
				{
					map.setCurPos(map.getNumRows() - 1, wideFactoryLine.indexOf("@"));
				}
			}
			else
			{
				instructions = (instructions + line).split("\s")[0];
			}
		}
		System.out.println(map.toString());
		FileWriter fw = new FileWriter("src\\day15\\log.txt");
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
					map.setDirection(directionToMove);
					for (int j = lastBoxDistance; j > 0; j--)
					{
						map.setLetterInCurrentDirectionAtDistance(j, 
							map.letterInCurrentDirectionAtDistance(j-1));
					}
					map.setCurLetter(".");
					map.moveInCurDirection();
				}
			}
			else //Now the most fun part yet
			{
				target = map.letterInDirectionAtDistance(directionToMove, 1);
				if (! target.equals("#"))
				{
					if (target.equals("."))
					{
						map.setLetterInDirectionAtDistance(directionToMove, 1, "@");
						map.setCurLetter(".");
						map.moveInDirection(directionToMove);
					}
					else //moving a box
					{
						BoxStruct boxToMove;
						if (target.equals("]"))
						{
							boxToMove = new BoxStruct(map.getCurPos()[0] + BoxStruct.getOffset(directionToMove), map.getCurPos()[1] - 1);
						}
						else //Should be [ at this stage
						{
							boxToMove = new BoxStruct(map.getCurPos()[0] + BoxStruct.getOffset(directionToMove), map.getCurPos()[1]);
						}
						if (boxToMove.attemptToMoveInDirection(directionToMove) == true)
						{
							map.setLetterInDirectionAtDistance(directionToMove, 1, "@");
							map.setCurLetter(".");
							map.moveInDirection(directionToMove);
						}
					}
				}
			}
			fw.write(map.toString());
			fw.write("\n");
			int newBoxCount = 0;
			for (int row = 0; row < map.getNumRows(); row++)
			{
				for (int col = 0; col < map.getNumCols(); col++)
				{
					if (map.letterAt(row, col).equals("["))
					{
						newBoxCount ++;
					}
				}
			}
			if (boxCount != newBoxCount)
			{
				System.out.println("BOX COUNT ALTERED INSTRUCTION " + i + "; NEW BOX COUNT IS " + newBoxCount);
			}
			boxCount = newBoxCount;
		}
		fw.close();
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
		System.out.println(boxCount + ", " + gpsSum);
	}
}