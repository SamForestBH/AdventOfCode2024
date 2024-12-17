package day13;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Utilities.InputParser;
import Utilities.StringGridWalker;

public class Day13Part1 {
	
	
	public static void main(String[] args) throws IOException 
	{
		//Reads line from input file
		File input = new File("src\\day13\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		long winCount = 0;
		while ((line = br.readLine()) != null)
		{
			double AX = Double.valueOf(line.substring(line.indexOf("X") + 2, line.indexOf(",")));
			double AY = Double.valueOf(line.substring(line.indexOf("Y") + 2));
			line = br.readLine();
			double BX = Double.valueOf(line.substring(line.indexOf("X") + 2, line.indexOf(",")));
			double BY = Double.valueOf(line.substring(line.indexOf("Y") + 2));
			line = br.readLine();
			double PX = Double.valueOf(line.substring(line.indexOf("X") + 2, line.indexOf(","))) + Math.pow(10, 13);
			double PY = Double.valueOf(line.substring(line.indexOf("Y") + 2)) + Math.pow(10, 13);
			line = br.readLine();
			double det = AX*BY-AY*BX;
			double a = -1;
			double b = -1;
			if (det == 0)
			{
				if (PY/PX == BY/BX)
				{
					if (AX > BX)
					{
						a = PY/AY;
						b = 0;
					}
					else
					{
						a = 0;
						b = PY/BY;
					}
				}
			}
			else 
			{
				a = (BY*PX-BX*PY)/det;
				b = (-1*PX*AY+AX*PY)/det;
			}
			if (a >= 0 && b >= 0 && (a % 1) == 0 && (b % 1) == 0)
			{
				winCount += (a * 3+ b);
			}
			System.out.println(a + ", " + b);
		}
		System.out.println(winCount);

	}
}