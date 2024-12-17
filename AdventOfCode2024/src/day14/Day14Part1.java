package day14;

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

public class Day14Part1 {
	
	
	public static void main(String[] args) throws IOException 
	{
		//Reads line from input file
		File input = new File("src\\day14\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		final int numRows = 103;
		final int numCols = 101;
		List<int[]> robotPositions = new ArrayList<int[]>();
		List<int[]> robotVelocities = new ArrayList<int[]>();
		Pattern digitPattern = Pattern.compile("-?\\d+"); 
		while ((line = br.readLine()) != null)
		{
			Matcher digitMatcher = digitPattern.matcher(line);
			int[] botInfo = new int[4];
			for (int i = 0; i < 4; i++)
			{
				digitMatcher.find();
				botInfo[i] = Integer.valueOf(digitMatcher.group());
			}
			int[] currentPosition = {botInfo[0], botInfo[1]};
			robotPositions.add(currentPosition);
			int[] currentVelocity = {botInfo[2], botInfo[3]};
			robotVelocities.add(currentVelocity);
		}
		
		long safetyFactor = (long) Math.pow(((robotPositions.size())/4), 4);
		int numMoves = 0;
		String basicLine = "";
		for (int i = 0; i < numCols; i++)
		{
			basicLine = basicLine + ".";
		}
		int contiguousBots = 0;
		while(numMoves < 10000)
		{
			int[][] quadrantCount = new int[2][2];
			for (int[] robotPosition : robotPositions)
			{
				//System.out.println("Final position is (" + robotPosition[0] + ", " + robotPosition[1] + ")");
				//System.out.println("Thus final quadrant is [" + ((robotPosition[0]) / ((numCols + 1)/2)) + "][" + (robotPosition[1] / ((numRows + 1)/2)) + "]");
				if (robotPosition[0] != (numCols - 1)/2 && 
						robotPosition[1] != (numRows - 1)/2)
				{
					quadrantCount
						[(robotPosition[0]) / ((numCols + 1)/2)]
						[robotPosition[1] / ((numRows + 1)/2)] ++;
				}
			}
			long newSafetyFactor = quadrantCount[0][0] * quadrantCount[0][1]
					*quadrantCount[1][0]*quadrantCount[1][1];
			StringGridWalker botImage = new StringGridWalker();
			boolean newLongestLine = false;
			for (int i = 0; i < numRows; i++)
			{
				botImage.addLine(basicLine);
			}
			for(int[] robotPosition : robotPositions)
			{
				botImage.setLetterAt(robotPosition[1], robotPosition[0], "#");
			}
			/*if (numMoves > 0 && numMoves < 10)
			{
				System.out.println("\n\n\n\nNow Viewing time t= " + numMoves + "\n\n\n\n");
				System.out.println(botImage.toString());
			}*/
			
			for (int row = 0; row < numRows; row++)
			{
				String newPattern = "#{" + (contiguousBots + 1) + ",100}";
				Pattern botPattern = Pattern.compile(newPattern);
				Matcher botMatcher = botPattern.matcher(botImage.getRow(row));
				if (botMatcher.find())
				{
					contiguousBots = botMatcher.group().length();
					System.out.println("\n\n\n\nNew longest line of bots is " + contiguousBots + " at time t= " + numMoves + "\n\n\n\n");
					System.out.println(botImage.toString());
					break;
				}
			}
			
			for (int i = 0; i < robotPositions.size(); i++)
			{
				robotPositions.get(i)[0] = ((robotPositions.get(i)[0] + robotVelocities.get(i)[0]) % numCols + numCols) % numCols;
				robotPositions.get(i)[1] = ((robotPositions.get(i)[1] + robotVelocities.get(i)[1]) % numRows + numRows) % numRows;
			}
			numMoves ++;
		}
		
		
	}
}