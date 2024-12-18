package day16;

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

public class Day16Part2 {
	
	static StringGridWalker maze;
	static long[][][] cheapestPath;
	
	//Checks the cheapest path from all 4 directions, and updates if it's cheaper from the new direction
	public static void updateCheapestPath()
	{
		int row = maze.getCurPos()[0];
		int col = maze.getCurPos()[1];
		for (int d = 0; d < 8; d+=2)
		{
			cheapestPath[row][col][d] = 
					Math.min(cheapestPath[row][col][d], 
					Math.min(cheapestPath[row][col][StringGridWalker.directionToRight(d)] + 1000, 
					Math.min(cheapestPath[row][col][StringGridWalker.directionToLeft(d)] + 1000, 
							cheapestPath[row][col][StringGridWalker.directionBehind(d)] + 2000)));
		}
	}
	
	public static void main(String[] args) throws IOException 
	{
		//Reads line from input file
		File input = new File("src\\day16\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		maze = new StringGridWalker();
		int[]startPos = new int[2];
		while ((line = br.readLine()) != null)
		{
			maze.addLine(line);
			if (line.contains("S"))
			{
				maze.setCurPos(maze.getNumRows() - 1, line.indexOf('S'));
				startPos[0] = maze.getCurPos()[0];
				startPos[1] = maze.getCurPos()[1];
			}
		}
		cheapestPath = new long[maze.getNumRows()][maze.getNumCols()][8];
		//cheapestPath[row][col][direction] = smallest points to reach spot in given direction
		for (int row = 0; row < cheapestPath.length; row++)
		{
			for (int col = 0; col < cheapestPath[0].length; col++)
			{
				for (int dir = 0; dir < 8; dir += 2)
				{
					cheapestPath[row][col][dir] = Long.MAX_VALUE/2;
				}
			}
		}
		long minimumPoints = Long.MAX_VALUE;
		List<int[][]> pathsToWalk = new ArrayList<int[][]>(); //Elements of form {{row, col, direction, points},steps[]}
		int[][] startInfo = {{maze.getCurPos()[0], maze.getCurPos()[1], StringGridWalker.RIGHT, 0},{}};
		pathsToWalk.add(startInfo);
		List<int[][]> winningPathInfo = new ArrayList<int[][]>();
		while (!pathsToWalk.isEmpty())
		{
			int[][] fullPath = pathsToWalk.get(0);
			int[] path = fullPath[0];
			int row = path[0];
			int col = path[1];
			int dir = path[2];
			int points = path[3];
			System.out.println("Testing path from (" + row + ", " + col + ") in direction " + dir + " and points " + points + ". ");
			if (cheapestPath[row][col][dir] >= points)
			{
				System.out.println("Cheaper");
				maze.setCurPos(row, col);
				maze.setDirection(dir);
				cheapestPath[row][col][dir] = points;
				updateCheapestPath();
				int[] newPathInfo;
				if (! maze.curLetter().equals("S"))
				{
					newPathInfo = new int[fullPath[1].length + 1];
					System.arraycopy(fullPath[1], 0, newPathInfo, 0, fullPath[1].length);
					newPathInfo[newPathInfo.length-1] = dir;
				}
				else
				{
					newPathInfo = new int[] {};
				}
				if (maze.curLetter().equals("E"))
				{
					minimumPoints = Math.min(points, minimumPoints);
					int[][] thisPathInfo = new int[2][];
					thisPathInfo[0] = new int[]{points};
					thisPathInfo[1] = fullPath[1];
					winningPathInfo.add(thisPathInfo);
				}
				else
				{
					if (!maze.letterInCurDirection().equals("#"))
					{
						int[] vector = StringGridWalker.vectorInDirection(maze.getCurDirection());
						int newRow = row + vector[0];
						int newCol = col + vector[1];
						int newDir = dir;
						int newPoints = points + 1;
						int[] newPath = {newRow, newCol, newDir, newPoints};
						int[][]combinedPath = new int[2][];
						combinedPath[0] = newPath;
						combinedPath[1] = newPathInfo;
						pathsToWalk.add(combinedPath);
					}
					maze.turnLeft();
					dir = StringGridWalker.directionToLeft(dir);
					if (!maze.letterInCurDirection().equals("#"))
					{
						int[] vector = StringGridWalker.vectorInDirection(maze.getCurDirection());
						int newRow = row + vector[0];
						int newCol = col + vector[1];
						int newDir = dir;
						int newPoints = points + 1001;
						int[] newPath = {newRow, newCol, newDir, newPoints};
						int[][]combinedPath = new int[2][];
						combinedPath[0] = newPath;
						combinedPath[1] = newPathInfo;
						pathsToWalk.add(combinedPath);
					}
					maze.turnAround();
					dir = StringGridWalker.directionBehind(dir);
					if (!maze.letterInCurDirection().equals("#"))
					{
						int[] vector = StringGridWalker.vectorInDirection(maze.getCurDirection());
						int newRow = row + vector[0];
						int newCol = col + vector[1];
						int newDir = dir;
						int newPoints = points + 1001;
						int[] newPath = {newRow, newCol, newDir, newPoints};
						int[][]combinedPath = new int[2][];
						combinedPath[0] = newPath;
						combinedPath[1] = newPathInfo;
						pathsToWalk.add(combinedPath);
					}
				}
			}
			pathsToWalk.remove(0);
		}
		for (int[][] potentialPath : winningPathInfo)
		{
			if (potentialPath[0][0] == minimumPoints)
			{
				maze.setCurPos(startPos);
				for (int i = 0; i < potentialPath[1].length; i++)
				{
					maze.moveInDirection(potentialPath[1][i]);
					if (! maze.curLetter().equals("E"))
					{
						maze.setCurLetter("O");
					}
				}
			}
		}
		int bestSeats = 2; //start and end are unchanged
		for (int row = 0; row < maze.getNumRows(); row ++)
		{
			for (int col = 0; col < maze.getNumCols(); col++)
			{
				if (maze.letterAt(row, col).equals("O"))
				{
					bestSeats ++;
				}
			}
		}
		System.out.println(maze.toString());
		System.out.println(bestSeats);
	}
}