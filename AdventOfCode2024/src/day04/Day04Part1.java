package day04;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day04Part1 {

	public static class Crossword
	{
		private List<String> letters;
		
		//(row, col) counting down and left
		private int[] curPos;
		
		public String letterAt(int i, int j)
		{
			return letters.get(i).substring(j, j+1);
		}
		
		public String letterAt(int[] pos)
		{
			return letters.get(pos[0]).substring(pos[1], pos[1]+1);
		}
		
		public String curLetter()
		{
			return this.letterAt(curPos);
		}
		
		public int[] getCurPos()
		{
			return curPos;
		}
		
		public void setCurPos(int row, int col)
		{
			curPos[0] = row;
			curPos[1] = col;
		}
		
		public String leftLetter()
		{
			if (curPos[1] == 0)
			{
				return "Outside of Crossword";
			}
			return this.letterAt(curPos[0], curPos[1] - 1);
		}
		
		public String rightLetter()
		{
			if (curPos[1] == letters.get(1).length() - 1)
			{
				return "Outside of Crossword";
			}
			return this.letterAt(curPos[0], curPos[1] + 1);
		}
		
		public String upLetter()
		{
			if (curPos[0] == 0)
			{
				return "Outside of Crossword";
			}
			return this.letterAt(curPos[0] - 1, curPos[1]);
		}
		
		public String downLetter()
		{
			if (curPos[0] == (letters.size() - 1))
			{
				return "Outside of Crossword";
			}
			return this.letterAt(curPos[0] + 1, curPos[1]);
		}
		
		public String upLeftLetter()
		{
			if (this.upLetter().equals("Outside of Crossword") || this.leftLetter().equals("Outside of Crossword"))
			{
				return "Outside of Crossword";
			}
			return this.letterAt(curPos[0] - 1, curPos[1] - 1);
		}
		
		public String upRightLetter()
		{
			if (this.upLetter().equals("Outside of Crossword") || this.rightLetter().equals("Outside of Crossword"))
			{
				return "Outside of Crossword";
			}
			return this.letterAt(curPos[0] - 1, curPos[1] + 1);
		}
		
		public String downLeftLetter()
		{
			if (this.downLetter().equals("Outside of Crossword") || this.leftLetter().equals("Outside of Crossword"))
			{
				return "Outside of Crossword";
			}
			return this.letterAt(curPos[0] + 1, curPos[1] - 1);
		}
		
		public String downRightLetter()
		{
			if (this.downLetter().equals("Outside of Crossword") || this.rightLetter().equals("Outside of Crossword"))
			{
				return "Outside of Crossword";
			}
			return this.letterAt(curPos[0] + 1, curPos[1] + 1);
		}
		
		//Returns letter in direction d, where 0 is right and incrementing d increments angle 45 ccw
		public String letterInDirection(int d)
		{
			switch (d)
			{
			case 0:
				return this.rightLetter();
			case 1: 
				return this.upRightLetter();
			case 2:
				return this.upLetter();
			case 3:
				return this.upLeftLetter();
			case 4:
				return this.leftLetter();
			case 5:
				return this.downLeftLetter();
			case 6:
				return this.downLetter();
			case 7:
				return this.downRightLetter();
			default:
				return "Error";
			}
		}
		
		public boolean moveLeft()
		{
			if (curPos[1] == 0)
			{
				return false;
			}
			this.setCurPos(curPos[0], curPos[1] - 1);
			return true;
		}
		
		public boolean moveRight()
		{
			if (curPos[1] == letters.get(1).length() - 1)
			{
				return false;
			}
			this.setCurPos(curPos[0], curPos[1] + 1);
			return true;
		}
		
		public boolean moveUp()
		{
			if (curPos[0] == 0)
			{
				return false;
			}
			this.setCurPos(curPos[0] - 1, curPos[1]);
			return true;
		}
		
		public boolean moveDown()
		{
			if (curPos[0] == (letters.size() - 1))
			{
				return false;
			}
			this.setCurPos(curPos[0] + 1, curPos[1]);
			return true;
		}
		
		public boolean moveUpLeft()
		{
			if (this.upLetter().equals("Outside of Crossword") || this.leftLetter().equals("Outside of Crossword"))
			{
				return false;
			}
			this.setCurPos(curPos[0] - 1, curPos[1] - 1);
			return true;
		}
		
		public boolean moveUpRight()
		{
			if (this.upLetter().equals("Outside of Crossword") || this.rightLetter().equals("Outside of Crossword"))
			{
				return false;
			}
			this.setCurPos(curPos[0] - 1, curPos[1] + 1);
			return true;
		}
		
		public boolean moveDownLeft()
		{
			if (this.downLetter().equals("Outside of Crossword") || this.leftLetter().equals("Outside of Crossword"))
			{
				return false;
			}
			this.setCurPos(curPos[0] + 1, curPos[1] - 1);
			return true;
		}
		
		public boolean moveDownRight()
		{
			if (this.downLetter().equals("Outside of Crossword") || this.rightLetter().equals("Outside of Crossword"))
			{
				return false;
			}
			this.setCurPos(curPos[0] + 1, curPos[1] + 1);
			return true;
		}
		
		//Moves curPos in direction d, where 0 is right and incrementing d increments angle 45 ccw
		public boolean moveInDirection(int d)
		{
			switch (d)
			{
			case 0:
				return this.moveRight();
			case 1: 
				return this.moveUpRight();
			case 2:
				return this.moveUp();
			case 3:
				return this.moveUpLeft();
			case 4:
				return this.moveLeft();
			case 5:
				return this.moveDownLeft();
			case 6:
				return this.moveDown();
			case 7:
				return this.moveDownRight();
			default:
				return false;
			}
		}
		
		public boolean addLine(String line)
		{
			if (letters.isEmpty() || letters.get(0).length() == line.length())
			{
				letters.add(line);
				return true;
			}
			return false;
		}
		
		public Crossword()
		{
			letters = new ArrayList<String>();
			curPos = new int[2];
			curPos[0] = 0;
			curPos[1] = 0;
		}
		
		public int getRows()
		{
			return letters.size();
		}
		
		public int getCols()
		{
			return letters.get(0).length();
		}
		
	}
	
	public static void main(String[] args) throws IOException 
	{
		//Reads line from input file
		File input = new File("src\\day04\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		Crossword crossword = new Crossword();
		while ((line = br.readLine()) != null)
		{
			crossword.addLine(line);
		}
		int xmasCount = 0;
		for (int row = 0; row < crossword.getRows(); row++)
		{
			for (int col = 0; col < crossword.getCols(); col++)
			{
				crossword.setCurPos(row,  col);
				if (crossword.curLetter().equals("X"))
				{
					for (int d = 0; d < 8; d++)
					{
						crossword.setCurPos(row,  col);
						if (crossword.letterInDirection(d).equals("M"))
						{
							crossword.moveInDirection(d);
							if (crossword.letterInDirection(d).equals("A"))
							{
								crossword.moveInDirection(d);
								if (crossword.letterInDirection(d).equals("S"))
								{
									System.out.println("(" + row + ", " + col + ") -> (" + 
											crossword.getCurPos()[0] + ", " + crossword.getCurPos()[1] + "); " + d);
									xmasCount ++;
								}
							}
						}
					}
				}
			}
		}
		System.out.println(xmasCount);
	}
}
