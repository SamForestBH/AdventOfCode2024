package day17;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Utilities.InputParser;
import Utilities.StringGridWalker;

public class Day17Part2 {
	
	static int pnt;
	static long finalA;
	static long A;
	static long B;
	static long C;
	static long out;
	
	public static long combo(int op)
	{
		switch (op)
		{
		case 0:
			return 0;
		case 1:
			return 1;
		case 2:
			return 2;
		case 3:
			return 3;
		case 4:
			return A;
		case 5:
			return B;
		case 6:
			return C;
		default:
			return -1;
		}
	}
	
	public static void adv(int op)
	{
		A = A >> combo(op);
	}
	
	public static void bxl(int op)
	{
		B = B ^ op;
	}
	
	public static void bst(int op)
	{
		B = combo(op) & 7;
	}
	
	public static void jnz(int op)
	{
		if (A != 0)
		{
			pnt = op - 2;
		}
	}
	
	public static void bxc(int op)
	{
		B = B ^ C;
	}
	
	public static void out(int op)
	{
		//out = out + (combo(op) & 7) + ",";
	}
	
	public static void bdv(int op)
	{
		B = A >> combo(op);
	}
	
	public static void cdv(int op)
	{
		C = A >> combo(op);
	}
	
	public static void fun(int inst, int op)
	{
		switch (inst)
		{
		case 0:
			adv(op);
			return;
		case 1:
			bxl(op);
			return;
		case 2:
			bst(op);
			return;
		case 3:
			jnz(op);
			return;
		case 4:
			bxc(op);
			return;
		case 5:
			out(op);
			return;
		case 6:
			bdv(op);
			return;
		case 7:
			cdv(op);
			return;
		}
	}
	
	public static void main(String[] args) throws IOException 
	{
		//Reads line from input file
		File input = new File("src\\day17\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		line = br.readLine();
		line = br.readLine();
		line = br.readLine();
		line = br.readLine();
		line = br.readLine();
		List<Integer> program = InputParser.splitOnNonDigitToIntList(line.substring(9));
		pnt = program.size() - 1;
		
		while (pnt >= 0)
		{
			out = program.get(pnt);
			long minB = 8;
			for (int i = 0; i < 8; i++)
			{
				A = 
			}
			A = (A << 3) + minB;
			pnt --;
		}
		System.out.println(A);
	}
}