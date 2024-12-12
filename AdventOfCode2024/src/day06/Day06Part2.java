package day06;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import Utilities.StringGridWalker;

public class Day06Part2 {
	
	
	public static void main(String[] args) throws IOException 
	{
		//Reads line from input file
		File input = new File("src\\day06\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		StringGridWalker basicMap = new StringGridWalker();
		StringGridWalker map = new StringGridWalker();
		int i = 0;
		int[] startPos = new int[2];
		int startDir = -1;
		while ((line = br.readLine()) != null)
		{
			basicMap.addLine(line);
			map.addLine(line);
			if (line.contains("v"))
			{
				map.setCurPos(i, line.indexOf("v"));
				map.setDirection(StringGridWalker.DOWN);
				startPos[0] = i;
				startPos[1] = line.indexOf("v");
				startDir = StringGridWalker.DOWN;
			}
			if (line.contains("<"))
			{
				map.setCurPos(i, line.indexOf("<"));
				map.setDirection(StringGridWalker.LEFT);
				startPos[0] = i;
				startPos[1] = line.indexOf("<");
				startDir = StringGridWalker.LEFT;
			}
			if (line.contains(">"))
			{
				map.setCurPos(i, line.indexOf(">"));
				map.setDirection(StringGridWalker.RIGHT);
				startPos[0] = i;
				startPos[1] = line.indexOf(">");
				startDir = StringGridWalker.RIGHT;
			}
			if (line.contains("^"))
			{
				map.setCurPos(i, line.indexOf("^"));
				map.setDirection(StringGridWalker.UP);
				startPos[0] = i;
				startPos[1] = line.indexOf("^");
				startDir = StringGridWalker.UP;
			}
			i++;
		}	
		int infiniteCount = 0;
		for (int obstacleRow = 0; obstacleRow < map.getnumRows(); obstacleRow++)
		{
			//System.out.println("Starting Row: " + obstacleRow);
			for (int obstacleCol = 0; obstacleCol < map.getnumCols(); obstacleCol++)
			{
				System.out.println("Obstacle placed at: (" + obstacleRow + ", " + obstacleCol + ")");
				if (map.letterAt(obstacleRow, obstacleCol).equals("."))
				{
					map.setLetterAt(obstacleRow, obstacleCol, "O");
					boolean foundO = false;
					int[] targetPosition = {-1, -1};
					int targetDirection = -1;
					oLoop:
					while (! map.letterInCurDirection().equals("Outside of Grid"))
					{
						//System.out.println(map.curLetter()+ " at (" + map.getCurPos()[0] + ", " + map.getCurPos()[1] + ")");
						//System.out.println(map.toString());
						//System.out.println();
						while (map.letterInCurDirection().equals("#") || map.letterInCurDirection().equals("O"))
						{
							map.turnRight();
							//System.out.println(map.getCurDirection());
						}
						map.moveInCurDirection();
						if (map.facingString().equals(map.curLetter()))
						{
							System.out.println("Infinite found at (" + obstacleRow + ", " + obstacleCol + ")");
							infiniteCount++;
							break oLoop;
						}
						if (map.curLetter().equals("."))
						{
							map.setCurLetter(map.facingString());
						}
					}
					for (int r = 0; r < map.getnumRows(); r++)
					{
						map.setRow(r, basicMap.getRow(r));
						map.setCurPos(startPos);
						map.setDirection(startDir);
					}
				}
			}
		}
		System.out.println("Infinite Count: " + infiniteCount);
		br.close();
	}
}
