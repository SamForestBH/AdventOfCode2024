package day12;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Utilities.InputParser;
import Utilities.StringGridWalker;

public class Day12Part2 {
	
	public static StringGridWalker garden;
	public static Region[][] inRegion;
	
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
		protected int numSides;
		protected int cost;
		
		public int getCost()
		{
			return cost;
		}
		
		@Override
		public String toString()
		{
			return "A region of " + plantType + " plants with price " + area + " * " + numSides + " = " + cost + ". ";
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
			numSides = 0;
			cost = 0;
		}
		
		public void addSide()
		{
			numSides ++;
		}
		
		public void addPlotAndSpread(int row, int col)
		{
			if (garden.letterAt(row, col).equals(plantType) && inRegion[row][col] == null)
			{
				plots.add(new Plot(row, col, plantType));
				inRegion[row][col] = this;
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
			cost = area * numSides;
		}
	}
	
	public static Region getRegion(int row, int col)
	{
		if (! garden.isInsideGrid(row, col))
		{
			return null;
		}
		return inRegion[row][col];
	}
	
	public static void main(String[] args) throws IOException 
	{
		//Reads line from input file
		File input = new File("src\\day12\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		garden = new StringGridWalker();
		while ((line = br.readLine()) != null)
		{
			garden.addLine(line);
		}
		inRegion = new Region[garden.getNumRows()][garden.getNumCols()];
		br.close();
		List<Region> regions = new ArrayList<Region>();
		int totalCost = 0;
		
		for (int row = 0; row < garden.getNumRows(); row++)
		{
			for (int col = 0; col < garden.getNumCols(); col++)
			{
				if (inRegion[row][col] == null)
				{
					regions.add(new Region(row, col));
				}
			}
		}
		//Horizontal borders
		Region topRegion = null;
		Region bottomRegion = null;
		for (int row = -1; row < garden.getNumRows(); row++)
		{
			boolean inBorder = false;
			for (int col = 0; col < garden.getNumCols(); col++)
			{
				Region newTopRegion = getRegion(row, col);
				Region newBottomRegion = getRegion(row + 1, col); 
				if (newTopRegion != newBottomRegion) //There is a border
				{
					if ((newTopRegion != topRegion || ! inBorder) && newTopRegion != null) //New top border
					{
						System.out.println("Horizontal Upper Border swap at (" + (row + 1) + ", " + col + ")");
						newTopRegion.addSide();
					}
					if ((newBottomRegion != bottomRegion || !inBorder) && newBottomRegion != null) //New bottom border
					{
						System.out.println("Horizontal Lower Border swap at (" + (row + 1) + ", " + col + ")");
						newBottomRegion.addSide();
					}
					inBorder = true;
				}
				else
				{
					inBorder = false;
				}
				topRegion = newTopRegion;
				bottomRegion = newBottomRegion;
			}
		}
		//Vertical Borders
		Region leftRegion = null;
		Region rightRegion = null;
		for (int col = -1; col < garden.getNumCols(); col++)
		{
			boolean inBorder = false;
			for (int row = 0; row < garden.getNumRows(); row++)
			{
				Region newLeftRegion = getRegion(row, col);
				Region newRightRegion = getRegion(row, col + 1); 
				if (newLeftRegion != newRightRegion) //There is a border
				{
					if ((newLeftRegion != leftRegion || ! inBorder) && newLeftRegion != null) //New left border
					{
						newLeftRegion.addSide();
					}
					if ((newRightRegion != rightRegion || !inBorder) && newRightRegion != null) //New right border
					{
						newRightRegion.addSide();
					}
					inBorder = true;
				}
				else
				{
					inBorder = false;
				}
				leftRegion = newLeftRegion;
				rightRegion = newRightRegion;
			}
		}
		for (Region r : regions)
		{
			r.updateDimensions();
			System.out.println(r.toString());
			totalCost += r.getCost();
		}
		System.out.println(totalCost);
	}
}