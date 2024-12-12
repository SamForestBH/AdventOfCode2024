package day07;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Utilities.InputParser;
import Utilities.StringGridWalker;

public class Day07Part1 {
	
	
	public static void main(String[] args) throws IOException 
	{
		//Reads line from input file
		File input = new File("src\\day07\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		List<List<Long>> equations = new ArrayList<List<Long>>();
		while ((line = br.readLine()) != null)
		{
			equations.add(InputParser.splitOnNonDigitToLongList(line));
		}
		int validEquationCount = 0;
		long calibrationSum = 0;
		for (List<Long> equation : equations)
		{
			long target = equation.get(0);
			System.out.println("Trying to reach " + target);
			equation.remove(0);
			List<List<Long>> subArguments = new ArrayList<List<Long>>();
			subArguments.add(equation);
			while (! subArguments.isEmpty())
			{
				List<Long> arguments = subArguments.get(0);
				if (arguments.size() == 1)
				{
					if (arguments.get(0) == target)
					{
						System.out.println("Reached " + target + "!");
						validEquationCount ++;
						calibrationSum += target;
						break;
					}
				}
				else
				{
					boolean considerSum = true;
					boolean considerMult = true;
					boolean considerConcat = true;
					long sum = arguments.get(0) + arguments.get(1);
					if (sum > target)
					{
						considerSum = false;
					}
					long mult = arguments.get(0) * arguments.get(1);
					if (mult > target)
					{
						considerMult = false;
					}
					long concat = Long.valueOf(arguments.get(0) + "" + arguments.get(1));
					if (concat > target)
					{
						considerConcat = false;
					}
					arguments.remove(0);
					arguments.remove(0);
					List<Long> newSumArgs = new ArrayList<Long>();
					List<Long> newMultArgs = new ArrayList<Long>();
					List<Long> newConcatArgs = new ArrayList<Long>();
					if (considerSum || considerMult || considerConcat)
					{
						newSumArgs.add(sum);
						newMultArgs.add(mult);
						newConcatArgs.add(concat);
						for (Long i : arguments)
						{
							newSumArgs.add(i);
							newMultArgs.add(i);
							newConcatArgs.add(i);
						}
					}
					if (considerSum)
					{
						subArguments.add(newSumArgs);
						System.out.print("New equation from sum: ");
						for (long l : newSumArgs)
						{
							System.out.print(l + ", ");
						}
						System.out.println();
					}
					if (considerMult)
					{
						subArguments.add(newMultArgs);
						System.out.print("New equation from mult: ");
						for (long l : newMultArgs)
						{
							System.out.print(l + ", ");
						}
						System.out.println();
					}
					if (considerConcat)
					{
						subArguments.add(newConcatArgs);
						System.out.print("New equation from concat: ");
						for (long l : newConcatArgs)
						{
							System.out.print(l + ", ");
						}
						System.out.println();
					}
				}
				subArguments.remove(0);
			}
		}
		System.out.println(calibrationSum);
	}
}
