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

public class Day16Part1 {
	
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
		while ((line = br.readLine()) != null)
		{
			maze.addLine(line);
			if (line.contains("S"))
			{
				maze.setCurPos(maze.getNumRows() - 1, line.indexOf('S'));
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
		List<int[]> pathsToWalk = new ArrayList<int[]>(); //Elements of form {row, col, direction, points}
		int[] startInfo = {maze.getCurPos()[0], maze.getCurPos()[1], StringGridWalker.RIGHT, 0};
		pathsToWalk.add(startInfo);
		while (!pathsToWalk.isEmpty())
		{
			int[] path = pathsToWalk.get(0);
			int row = path[0];
			int col = path[1];
			int dir = path[2];
			int points = path[3];
			System.out.println("Testing path from (" + row + ", " + col + ") in direction " + dir + " and points " + points + ". ");
			if (cheapestPath[row][col][dir] > points)
			{
				System.out.println("Cheaper");
				maze.setCurPos(row, col);
				maze.setDirection(dir);
				cheapestPath[row][col][dir] = points;
				updateCheapestPath();
				if (maze.curLetter().equals("E"))
				{
					minimumPoints = Math.min(points, minimumPoints);
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
						pathsToWalk.add(newPath);
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
						pathsToWalk.add(newPath);
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
						pathsToWalk.add(newPath);
					}
				}
			}
			pathsToWalk.remove(0);
		}
		System.out.println(minimumPoints);
	}
}