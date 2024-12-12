package day08;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Utilities.InputParser;
import Utilities.StringGridWalker;

public class Day08Part1 {
	
	public static class AntennaList
	{
		protected String frequency;
		protected List<int[]> locations;
		
		public AntennaList(String f)
		{
			frequency = f;
			locations = new ArrayList<int[]>();
		}
		
		public String getFrequency()
		{
			return frequency;
		}
		
		public boolean addLocation(int[] loc)
		{
			if (loc.length == 2)
			{
				locations.add(loc);
				return true;
			}
			return false;
		}
		
		public int numLocations()
		{
			return locations.size();
		}
		
		public int[] getLocation(int i)
		{
			return locations.get(i);
		}
	}
	
	public static void main(String[] args) throws IOException 
	{
		//Reads line from input file
		File input = new File("src\\day08\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		List<AntennaList> masterList = new ArrayList<AntennaList>();
		int row = 0;
		int col = 0;
		while ((line = br.readLine()) != null)
		{
			for (col = 0; col < line.length(); col++)
			{
				String aFreq = line.substring(col, col+1);
				if (! aFreq.equals("."))
				{
					int[] aLoc = {row, col};
					boolean freqListMade = false;
					for (AntennaList aList : masterList)
					{
						if (aList.getFrequency().equals(aFreq))
						{
							freqListMade = true;
							aList.addLocation(aLoc);
						}
					}
					if (!freqListMade)
					{
						AntennaList newAList = new AntennaList(aFreq);
						newAList.addLocation(aLoc);
						masterList.add(newAList);
					}
				}
			}
			row ++;
		}
		boolean[][] isAntenna = new boolean[row][col];
		for (boolean[] boolList : isAntenna)
		{
			for (boolean b : boolList)
			{
				b = false;
			}
		}
		int antennaCount = 0;
		for (AntennaList aList : masterList)
		{
			for (int i = 0; i < aList.numLocations(); i++)
			{
				int[] locOne = aList.getLocation(i);
				for (int j = 0; j < i; j++)
				{
					int[] locTwo = aList.getLocation(j);
					int[] nodeOne = {2*locOne[0]-locTwo[0], 2*locOne[1]-locTwo[1]};
					int[] nodeTwo = {2*locTwo[0]-locOne[0], 2*locTwo[1]-locOne[1]};
					if (nodeOne[0] >= 0 && nodeOne[0] < row && nodeOne[1] >= 0 && nodeOne[1] < col)
					{
						if (!isAntenna[nodeOne[0]][nodeOne[1]])
						{
							antennaCount ++;
						}
						isAntenna[nodeOne[0]][nodeOne[1]] = true;
					}
					if (nodeTwo[0] >= 0 && nodeTwo[0] < row && nodeTwo[1] >= 0 && nodeTwo[1] < col)
					{
						if (!isAntenna[nodeTwo[0]][nodeTwo[1]])
						{
							antennaCount ++;
						}
						isAntenna[nodeTwo[0]][nodeTwo[1]] = true;
					}
				}
			}
		}
		System.out.println(antennaCount);
	}
}
