package day06;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Utilities.StringGridWalker;

public class Day06Part2 {
	
	public static class GuardWalker extends StringGridWalker
	{
		List<List<Boolean[]>> validDirections;
		
		public GuardWalker()
		{
			super();
			validDirections = new ArrayList<List<Boolean[]>>();
		}
		
		@Override
		public boolean addLine(String line)
		{
			List<Boolean[]> nextRow = (new ArrayList<Boolean[]>());
			for (int i = 0; i < line.length(); i ++)
			{
				Boolean[] toAdd = new Boolean[4];
				nextRow.add(new Boolean({false, false, false, false}));
			}
			return super.addLine(line);
		}
	}
	
	public static void main(String[] args) throws IOException 
	{
		//Reads line from input file
		File input = new File("src\\day06\\testFile.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		StringGridWalker map = new StringGridWalker();
		int i = 0;
		while ((line = br.readLine()) != null)
		{
			map.addLine(line);
			if (line.contains("v"))
			{
				map.setCurPos(i, line.indexOf("v"));
				map.setDirection(StringGridWalker.DOWN);
			}
			if (line.contains("<"))
			{
				map.setCurPos(i, line.indexOf("<"));
				map.setDirection(StringGridWalker.LEFT);
			}
			if (line.contains(">"))
			{
				map.setCurPos(i, line.indexOf(">"));
				map.setDirection(StringGridWalker.RIGHT);
			}
			if (line.contains("^"))
			{
				map.setCurPos(i, line.indexOf("^"));
				map.setDirection(StringGridWalker.UP);
			}
			i++;
		}		
		int obstacleCount = 0;
		while (! map.letterInCurDirection().equals("Outside of Grid"))
		{
			System.out.println(map.curLetter()+ " at (" + map.getCurPos()[0] + ", " + map.getCurPos()[1] + ")");
			System.out.println(map.toString());
			System.out.println();
			while (map.letterInCurDirection().equals("#"))
			{
				map.turnRight();
			}
			map.moveInCurDirection();
			if (map.curLetter().equals("."))
			{
				map.setCurLetter(map.facingString());
			}
			else
			{
				System.out.println("MAP HERE");
				obstacleCount ++;
			}
				
		}
		System.out.println(obstacleCount);
		br.close();
	}
}
