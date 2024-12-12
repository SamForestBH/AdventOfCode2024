package day06;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import Utilities.StringGridWalker;

public class Day06Part1 {
	
	
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
		int stepCount = 1;
		map.setLetterAt(8, 84, "O");
		while (! map.letterInCurDirection().equals("Outside of Grid"))
		{
			System.out.println(map.curLetter()+ " at (" + map.getCurPos()[0] + ", " + map.getCurPos()[1] + ")");
			System.out.println(map.toString());
			System.out.println();
			while (map.letterInCurDirection().equals("#") || map.letterInCurDirection().equals("O"))
			{
				map.turnRight();
				System.out.println(map.getCurDirection());
			}
			map.moveInCurDirection();
			if (map.curLetter().equals("."))
			{
				map.setCurLetter(map.facingString());
				stepCount ++;
			}
				
		}
		System.out.println(stepCount);
		br.close();
	}
}
