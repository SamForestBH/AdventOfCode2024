package day11;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Utilities.InputParser;
import Utilities.StringGridWalker;

public class Day11Part1 {
	
	public static class Stone implements Comparable<Stone>
	{
		protected long value;
		protected long count;
		
		public long getValue()
		{
			return value;
		}
		
		public long getCount()
		{
			return count;
		}
		
		public void addStone()
		{
			count ++;
		}
		
		public void incrementCount(long inc)
		{
			count += inc;
		}
		
		public Stone(long v, long c)
		{
			value = v;
			count = c;
		}
		
		@Override
		public int compareTo(Stone o) {
			return (int) (value - o.getValue());
		}
	}
	
	public static class StoneList
	{
		protected List<Stone> stones;
		
		public long numUniqueStones()
		{
			return stones.size();
		}
		
		public long numStones()
		{
			long count = 0;
			for (Stone s : stones)
			{
				count += s.getCount();
			}
			return count;
		}
		
		public Stone getStone(int i)
		{
			return stones.get(i);
		}
		
		public Stone setStone(int i, Stone s)
		{
			return stones.set(i, s);
		}
		
		public void addStone(int i, Stone s)
		{
			stones.add(i, s);
		}
		
		public boolean addStone(Stone s)
		{
			return stones.add(s);
		}
		
		public void blink()
		{
			int stoneIndex = 0;
			while (stoneIndex < stones.size())
			{
				Stone currentStone = stones.get(stoneIndex);
				if (currentStone.getValue() == 0)
				{
					stones.set(stoneIndex, new Stone(1, currentStone.getCount()));
				}
				else if (Math.floor(Math.log10(stones.get(stoneIndex).getValue())) % 2 == 0)
				{
					stones.set(stoneIndex, new Stone(currentStone.getValue() * 2024, currentStone.getCount()));
				}
				else
				{
					String stoneString = stones.get(stoneIndex).getValue() + "";
					this.setStone(stoneIndex, new Stone(Long.valueOf(stoneString.substring(0, stoneString.length()/2)), currentStone.getCount()));
					this.addStone(stoneIndex + 1, new Stone(Long.valueOf(stoneString.substring(stoneString.length()/2)), currentStone.getCount()));
					stoneIndex ++;
				}
				stoneIndex ++;
			}
			this.consolidate();
		}
		
		public void consolidate()
		{
			int i = 0;
			while (i < stones.size())
			{
				int j = i + 1;
				while (j < stones.size())
				{
					if (stones.get(i).getValue() == (stones.get(j).getValue()))
					{
						stones.get(i).incrementCount(stones.get(j).getCount());
						stones.remove(j);
					}
					else
					{
						j++;
					}
				}
				i += 1;
			}
		}
		
		public StoneList()
		{
			stones = new ArrayList<Stone>();
		}
	}
	
	public static void main(String[] args) throws IOException 
	{
		//Reads line from input file
		File input = new File("src\\day11\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line = br.readLine();
		br.close();
		List<Long> stoneLongs = InputParser.splitOnWhitespaceToLongList(line);
		StoneList stones = new StoneList();
		for (Long l : stoneLongs)
		{
			stones.addStone(new Stone(l, 1));
		}
		for (int i = 0; i < 75; i++)
		{
			stones.blink();
			System.out.println("Blinks = " + (i + 1) + "; Stone Count: " + stones.numStones());
		}
	}
}

