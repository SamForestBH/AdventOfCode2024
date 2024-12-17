package day12;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Utilities.InputParser;
import Utilities.StringGridWalker;

public class Day12Part1 {
	
	public static StringGridWalker garden;
	public static boolean[][] inRegion;
	
	public static class Plot
	{
		protected int[] location;
		protected String plantType;
		protected int perimeter;
		
		public Plot(int r, int c, String pT)
		{
			location = new int[2];
			location[0] = r;
			location[1] = c;
			plantType = pT;
			garden.setCurPos(r, c);
			perimeter = 0;
			for (int i = 0; i < 8; i+= 2)
			{
				if (! garden.letterInDirection(i).equals(plantType))
				{
					perimeter ++; 
				}
			}
				
		}
		
		public Plot(int[] l, String pT)
		{
			this(l[0], l[1], pT);
		}
		
		public int[] getLocation()
		{
			return location;
		}
		
		public int getRow()
		{
			return location[0];
		}
		
		public int getCol()
		{
			return location[1];
		}
		
		public String getPlantType()
		{
			return plantType;
		}
		
		public int getPerimeter()
		{
			return perimeter;
		}
	}
	
	public static class Region
	{
		protected List<Plot> plots;
		protected String plantType;
		protected int perimeter;
		protected int area;
		protected int cost;
		
		public int getCost()
		{
			return cost;
		}
		
		public Region(int row, int col)
		{
			plots = new ArrayList<Plot>();
			plantType = garden.letterAt(row,  col);
			this.addPlotAndSpread(row, col);
			this.updateDimensions();
		}
		
		public Region(String pT)
		{
			plots = new ArrayList<Plot>();
			plantType = pT;
			perimeter = 0;
			area = 0;
			cost = 0;
		}
		
		public void addPlotAndSpread(int row, int col)
		{
			if (garden.letterAt(row, col).equals(plantType) && ! inRegion[row][col])
			{
				plots.add(new Plot(row, col, plantType));
				inRegion[row][col] = true;
				addPlotAndSpread(row - 1, col);
				addPlotAndSpread(row + 1, col);
				addPlotAndSpread(row, col - 1);
				addPlotAndSpread(row, col + 1);
			}
		}
		
		public void updateDimensions()
		{
			area = plots.size();
			perimeter = 0;
			for (Plot p : plots)
			{
				perimeter += p.getPerimeter();
			}
			cost = area * perimeter;
		}
	}
	
	public static void main(String[] args) throws IOException 
	{
		System.out.println("AOK");
		//Reads line from input file
		File input = new File("src\\day12\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		garden = new StringGridWalker();
		while ((line = br.readLine()) != null)
		{
			garden.addLine(line);
		}
		inRegion = new boolean[garden.getNumRows()][garden.getNumCols()];
		br.close();
		List<Region> regions = new ArrayList<Region>();
		int totalCost = 0;
		
		for (int row = 0; row < garden.getNumRows(); row++)
		{
			for (int col = 0; col < garden.getNumCols(); col++)
			{
				if (! inRegion[row][col])
				{
					regions.add(new Region(row, col));
					totalCost += regions.get(regions.size() - 1).getCost();
				}
			}
		}
		System.out.println(totalCost);
	}
}