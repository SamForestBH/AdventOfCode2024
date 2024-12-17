package Utilities;

import java.util.ArrayList;
import java.util.List;

public class StringGridWalker
{
	//Each string is a row of characters
	protected List<String> letters;
	
	//(row, col) counting down and left
	protected int[] curPos;
	
	protected int curDirection;
	
	final public static int RIGHT = 0;
	final public static int UPRIGHT = 1;
	final public static int UP = 2;
	final public static int UPLEFT = 3;
	final public static int LEFT = 4;
	final public static int DOWNLEFT = 5;
	final public static int DOWN = 6;
	final public static int DOWNRIGHT = 7;
	
	public static int[] vectorInDirection(int direction)
	{
		switch (direction)
		{
		case RIGHT:
			return new int[] {0, 1};
		case UPRIGHT:
			return new int[] {-1, 1};
		case UP:
			return new int[] {-1, 0};
		case UPLEFT:
			return new int[] {-1, -1};
		case LEFT:
			return new int[] {0, -1};
		case DOWNLEFT:
			return new int[] {1, -1};
		case DOWN: 
			return new int[] {1, 0};
		case DOWNRIGHT: 
			return new int[] {1, 1};
		default:
			return null;
		}
	}
	
	public int[] vectorInCurrentDirection()
	{
		return StringGridWalker.vectorInDirection(curDirection);
	}
	
	public String facingString()
	{
		switch (curDirection)
		{
		case RIGHT:
			return ">";
		case UPRIGHT:
			return"7";
		case UP:
			return "^";
		case UPLEFT:
			return "F";
		case LEFT:
			return "<";
		case DOWNLEFT:
			return "L";
		case DOWN:
			return "v";
		case DOWNRIGHT:
			return "J";
		}
		return "X";
	}
	
	public static int directionFromFacing(String s)
	{
		if (s.length() != 1)
		{
			return -1;
		}
		return directionFromFacing(s.charAt(0));
	}
	
	public static int directionFromFacing(char c)
	{
		switch (c)
		{
		case '>':
			return RIGHT;
		case '<':
			return LEFT;
		case '^':
			return UP;
		case 'v':
			return DOWN;
		case 'F':
			return UPLEFT;
		case '7':
			return UPRIGHT;
		case 'L':
			return DOWNLEFT;
		case 'J':
			return DOWNRIGHT;
		default:
			return -1;
		}
	}
	
	public String letterAt(int i, int j)
	{
		if (i >= 0 && i < letters.size() && j >= 0 && j < letters.get(0).length())
		{
			return letters.get(i).substring(j, j+1);
		}
		return "Outside of Grid";
	}
	
	public String letterAt(int[] pos)
	{
		int i = pos[0];
		int j = pos[1];
		return this.letterAt(i, j);
	}
	
	public String curLetter()
	{
		return this.letterAt(curPos);
	}
	
	public int getCurDirection()
	{
		return this.curDirection;
	}
	
	public void setCurLetter(String c)
	{
		String oldString = letters.get(curPos[0]);
		letters.set(curPos[0], oldString.substring(0, curPos[1]) + c + oldString.substring(curPos[1]+1, oldString.length()));
	}
	
	public boolean setLetterAt(int row, int col, String ch)
	{
		if (this.letterAt(row, col).equals("Outside of Grid"))
		{
			return false;
		}
		String oldString = letters.get(row);
		letters.set(row, oldString.substring(0, col) + ch + oldString.substring(col+1, oldString.length()));
		return true;
	}
	
	public int[] getCurPos()
	{
		return curPos;
	}
	
	public void setCurPos(int[] cp)
	{
		this.setCurPos(cp[0], cp[1]);
	}
	
	public void setCurPos(int row, int col)
	{
		curPos[0] = row;
		curPos[1] = col;
	}
	
	public boolean setDirection(int d)
	{
		curDirection = d;
		return true;
	}
	
	public boolean setDirection(String d)
	{
		switch (d)
		{
		case "UP":
			curDirection = UP;
			break;
		case "DOWN":
			curDirection = DOWN;
			break;
		case "RIGHT":
			curDirection = RIGHT;
			break;
		case "LEFT":
			curDirection = LEFT;
			break;
		case "UPLEFT":
			curDirection = UPLEFT;
			break;
		case "UPRIGHT":
			curDirection = UPRIGHT;
			break;
		case "DOWNLEFT":
			curDirection = DOWNLEFT;
			break;
		case "DOWNRIGHT":
			curDirection = DOWNRIGHT;
			break;
		default:
			return false;
		}
		return true;
	}
	
	public String letterInCurDirection()
	{
		switch (curDirection)
		{
		case UP:
			return this.upLetter();
		case DOWN:
			return this.downLetter();
		case LEFT:
			return this.leftLetter();
		case RIGHT:
			return this.rightLetter();
		case UPLEFT:
			return this.upLeftLetter();
		case UPRIGHT:
			return this.upRightLetter();
		case DOWNLEFT:
			return this.downLeftLetter();
		case DOWNRIGHT:
			return this.downRightLetter();
		}
		return "ERROR";
	}
	
	public String letterInDirection(String d)
	{
		int oldDir = curDirection;
		this.setDirection(d);
		String toReturn = this.letterInCurDirection();
		curDirection = oldDir;
		return toReturn;
	}
	
	public String leftLetter()
	{
		if (curPos[1] == 0)
		{
			return "Outside of Grid";
		}
		return this.letterAt(curPos[0], curPos[1] - 1);
	}
	
	public String rightLetter()
	{
		if (curPos[1] == letters.get(1).length() - 1)
		{
			return "Outside of Grid";
		}
		return this.letterAt(curPos[0], curPos[1] + 1);
	}
	
	public String upLetter()
	{
		if (curPos[0] == 0)
		{
			return "Outside of Grid";
		}
		return this.letterAt(curPos[0] - 1, curPos[1]);
	}
	
	public String downLetter()
	{
		if (curPos[0] == (letters.size() - 1))
		{
			return "Outside of Grid";
		}
		return this.letterAt(curPos[0] + 1, curPos[1]);
	}
	
	public String upLeftLetter()
	{
		if (this.upLetter().equals("Outside of Grid") || this.leftLetter().equals("Outside of Grid"))
		{
			return "Outside of Grid";
		}
		return this.letterAt(curPos[0] - 1, curPos[1] - 1);
	}
	
	public String upRightLetter()
	{
		if (this.upLetter().equals("Outside of Grid") || this.rightLetter().equals("Outside of Grid"))
		{
			return "Outside of Grid";
		}
		return this.letterAt(curPos[0] - 1, curPos[1] + 1);
	}
	
	public String downLeftLetter()
	{
		if (this.downLetter().equals("Outside of Grid") || this.leftLetter().equals("Outside of Grid"))
		{
			return "Outside of Grid";
		}
		return this.letterAt(curPos[0] + 1, curPos[1] - 1);
	}
	
	public String downRightLetter()
	{
		if (this.downLetter().equals("Outside of Grid") || this.rightLetter().equals("Outside of Grid"))
		{
			return "Outside of Grid";
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
			return "Outside of Grid";
		}
	}
	
	//Gives the letter from curPos 
	public String letterInDirection(int[] scoot)
	{
		String toReturn;
		if ((toReturn = this.letterAt(curPos[0] + scoot[0], curPos[1] + scoot[1])) != null)
		{
			return toReturn;
		}
		return "Outside of Grid";
	}
	
	public String letterInDirectionAtDistance(int direction, int distance)
	{
		int[] offsetVector = StringGridWalker.vectorInDirection(direction);
		return this.letterAt(curPos[0] + offsetVector[0] * distance, curPos[1] + offsetVector[1] * distance);
	}
	
	public String letterInCurrentDirectionAtDistance(int distance)
	{
		return this.letterInDirectionAtDistance(this.curDirection, distance);
	}
	
	public boolean setLetterInDirectionAtDistance(int direction, int distance, String s)
	{
		return this.setLetterAt(curPos[0] + distance * StringGridWalker.vectorInDirection(direction)[0], 
				curPos[1] + distance * StringGridWalker.vectorInDirection(direction)[1], s);
	}
	
	public boolean setLetterInCurrentDirectionAtDistance(int distance, String s)
	{
		return this.setLetterInDirectionAtDistance(curDirection, distance, s);
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
		if (this.upLetter().equals("Outside of Grid") || this.leftLetter().equals("Outside of Grid"))
		{
			return false;
		}
		this.setCurPos(curPos[0] - 1, curPos[1] - 1);
		return true;
	}
	
	public boolean moveUpRight()
	{
		if (this.upLetter().equals("Outside of Grid") || this.rightLetter().equals("Outside of Grid"))
		{
			return false;
		}
		this.setCurPos(curPos[0] - 1, curPos[1] + 1);
		return true;
	}
	
	public boolean moveDownLeft()
	{
		if (this.downLetter().equals("Outside of Grid") || this.leftLetter().equals("Outside of Grid"))
		{
			return false;
		}
		this.setCurPos(curPos[0] + 1, curPos[1] - 1);
		return true;
	}
	
	public boolean moveDownRight()
	{
		if (this.downLetter().equals("Outside of Grid") || this.rightLetter().equals("Outside of Grid"))
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
		case RIGHT:
			return this.moveRight();
		case UPRIGHT: 
			return this.moveUpRight();
		case UP:
			return this.moveUp();
		case UPLEFT:
			return this.moveUpLeft();
		case LEFT:
			return this.moveLeft();
		case DOWNLEFT:
			return this.moveDownLeft();
		case DOWN:
			return this.moveDown();
		case DOWNRIGHT:
			return this.moveDownRight();
		default:
			return false;
		}
	}
	
	public boolean moveInCurDirection()
	{
		return this.moveInDirection(this.curDirection);
	}
	
	public void turnRight()
	{
		curDirection = (curDirection + 6) % 8;
	}
	
	public void turnLeft()
	{
		curDirection = (curDirection + 2) % 8;
	}
	
	public void turnAround()
	{
		curDirection = (curDirection + 4) % 8;
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
	
	public String getRow(int row)
	{
		if (row < 0 || row >= letters.size())
		{
			//System.out.println(row);
			return null;
		}
		return letters.get(row);
	}
	
	public boolean setRow(int row, String line)
	{
		if (row < 0 || row  >= letters.size() || line.length() != letters.get(1).length())
		{
			return false;
		}
		letters.set(row, line);
		return true;
	}
	
	public StringGridWalker()
	{
		letters = new ArrayList<String>();
		curPos = new int[2];
		curPos[0] = 0;
		curPos[1] = 0;
		curDirection = RIGHT; 
	}
	
	public int getNumRows()
	{
		return letters.size();
	}
	
	public int getNumCols()
	{
		return letters.get(0).length();
	}
	
	public boolean isInsideGrid(int row, int col)
	{
		if (this.letterAt(row, col).equals("Outside of Grid"))
		{
			return false;
		}
		return true;
	}
	
	@Override
	public String toString()
	{
		String toReturn = "";
		for (String s : this.letters)
		{
			toReturn += s + "\n";
		}
		return toReturn.substring(0, toReturn.length() - 1);
	}
	
}
