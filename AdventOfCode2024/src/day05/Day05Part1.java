package day05;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Day05Part1 {
	
	//The list of rules. Used to check order and sort Page objects. 
	public static PageOrderingRuleset ruleset;
	
	//Literally just an integer, but compares using rules from ruleset
	public static class Page implements Comparable<Page>
	{
		int page;
		
		public Page(int p)
		{
			page = p;
		}
		
		public int getPageValue()
		{
			return page;
		}
		
		//a<b if there is a rule of the form a|b
		@Override
		public int compareTo(Page p)
		{
			for (Rule rule : ruleset.getRules())
			{
				if (rule.getPageValue() == this.getPageValue())
				{
					if (rule.containsInFuture(p.getPageValue()))
					{
						return -1;
					}
					if (rule.containsInPast(p.getPageValue()))
					{
						return 1;
					}
				}
			}
			return 0;
		}
	}
	
	//Contains a page, and a list of rules that must be before and after itself
	public static class Rule implements Comparable<Rule>
	{
		private Page page;
		private List<Rule> futurePages;
		private List<Rule> pastPages;
		
		//Constructors
		public Rule(Page p, List<Rule> fP, List<Rule> pP)
		{
			page = p;
			futurePages = fP;
			pastPages = pP;
		}
		
		public Rule(int p, List<Rule> fP, List<Rule> pP)
		{
			page = new Page(p);
			futurePages = fP;
			pastPages = pP;
		}
		
		public Rule(int p)
		{
			this(p, new ArrayList<Rule>(), new ArrayList<Rule>());
		}
		
		//Getters and setters
		public void setPage(int p)
		{
			page = new Page(p);
		}
		
		public void setPage(Page p) 
		{
			page = p;
		}
		
		public void setFuturePages(List<Rule> fP)
		{
			futurePages = fP;
		}
		
		public void setPastPages(List<Rule> pP)
		{
			pastPages = pP;
		}
		
		public int getPageValue()
		{
			return page.getPageValue();
		}
		
		public Page getPage()
		{
			return page;
		}
		
		public List<Rule> getFuturePages()
		{
			return futurePages;
		}
		
		public List<Rule> getPastPages()
		{
			return pastPages;
		}
		
		//Attempts to add p. Returns true if added, false if already present. 
		public boolean linkFuturePage(Rule p)
		{
			if (!(futurePages.contains(p)))
			{
				futurePages.add(p);
				return true;
			}
			return false;
		}
		
		//Attempts to add p. Returns true if added, false if already present. 
		public boolean linkPastPage(Rule p)
		{
			if (!(pastPages.contains(p)))
			{
				pastPages.add(p);
				return true;
			}
			return false;
		}
		
		//Checks if a page containing f is in the future list
		public boolean containsInFuture(int f)
		{
			for (Rule r : futurePages)
			{
				if (r.getPageValue() == f)
					return true;
			}
			return false;
		}
		
		//Checks if a page containing p is in the past list
		public boolean containsInPast(int p)
		{
			for (Rule r : pastPages)
			{
				if (r.getPageValue() == p)
					return true;
			}
			return false;
		}
		
		//Compares page
		@Override
		public int compareTo(Rule r)
		{
			return (this.getPageValue() - (r.getPageValue()));
		}
		
		//Mostly for debugging
		@Override
		public String toString()
		{
			String toReturn = "{";
			for (Rule p : this.getPastPages())
			{
				toReturn += p.getPageValue() + ", ";
			}
			toReturn = toReturn.substring(0, toReturn.length()) + "} -> " + this.getPageValue() + " -> {";
			for (Rule f : this.getFuturePages())
			{
				toReturn += f.getPageValue() + ", ";
			}
			toReturn = toReturn.substring(0, toReturn.length()) + "}";
			return toReturn;
		}
	}
	
	//Holds all rules in a list, with methods to check the order of, and sort, updates
	public static class PageOrderingRuleset
	{
		private List<Rule> rules;
		
		public PageOrderingRuleset(List<Rule> r)
		{
			rules = r;
		}
		
		public List<Rule> getRules()
		{
			return rules;
		}
		
		public boolean addRule(Rule r)
		{
			rules.add(r);
			return true;
		}
		
		//Points the two rules at each other
		public boolean linkRules(Rule earlyRule, Rule lateRule)
		{
			return (earlyRule.linkFuturePage(lateRule) && lateRule.linkPastPage(earlyRule));
		}
		
		public boolean linkRules(int earlyNum, int lateNum)
		{
			Rule earlyRule;
			Rule lateRule;
			if ((earlyRule = this.getRule(earlyNum)) == null || (lateRule = this.getRule(lateNum)) == null)
			{
				return false;
			}
			return this.linkRules(earlyRule, lateRule);
		}
		
		//Takes the list of strings from file, turns them into rules, links each one properly, and sorts them. 
		public PageOrderingRuleset(List<String> rawRules, int flag)
		{
			rules = new ArrayList<Rule>();
			for (String s : rawRules)
			{
				String[] splits = s.split("\\|");
				int earlyNum = Integer.valueOf(splits[0]);
				int lateNum = Integer.valueOf(splits[1]);
				Rule earlyRule;
				Rule lateRule;
				if ((earlyRule = this.getRule(earlyNum)) == null)
				{
					earlyRule = new Rule(earlyNum);
					this.addRule(earlyRule);
				}
				if((lateRule = this.getRule(lateNum)) == null)
				{
					lateRule = new Rule(lateNum);
					this.addRule(lateRule);
				}
				this.linkRules(earlyRule, lateRule);
			}
			Collections.sort(rules);
		}
		
		//If there is a rule in the set with r, return that rule. Otherwise, return null
		public Rule getRule(int r)
		{
			for (Rule rule : rules)
			{
				if (rule.getPageValue() == r)
				{
					return rule;
				}
			}
			return null;
		}
		
		//Checks if an update is in order (according to ruleset)
		public boolean isInOrder(List<Page> update)
		{
			List<Page> banList = new ArrayList<Page>(); //Things that cannot appear from this point on
			for (Page page : update)
			{
				//Check the banlist to see if anything matches
				for (Page p : banList)
				{
					if (page.getPageValue() == p.getPageValue())
					{
						return false;
					}
				}
				//Otherwise, add all past pages to banlist
				Rule pageRule;
				if ((pageRule = this.getRule(page.getPageValue())) != null)
				{
					for (Rule r : pageRule.getPastPages())
					{
						banList.add(r.getPage());
					}
				}
			}
			return true;
		}
		
		//Mostly for debugging
		@Override
		public String toString()
		{
			String toReturn = "";
			for (Rule r : this.getRules())
			{
				toReturn += r.toString() + "\n";
			}
			return toReturn;
		}
	}
	
	public static void main(String[] args) throws IOException 
	{
		//Reads line from input file
		File input = new File("src\\day05\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		List<String> rawRules = new ArrayList<String>(); //to create ruleset
		List<List<Page>> updates = new ArrayList<List<Page>>(); //List of updates
		while ((line = br.readLine()) != null)
		{
			if (line.contains("|"))
			{
				rawRules.add(line);
			}
			if(line.contains(","))
			{
				String[] splits = line.split(",");
				List<Page> pages = new ArrayList<Page>();
				for (String s : splits)
				{
					pages.add(new Page(Integer.valueOf(s)));
				}
				updates.add(pages);
			}
		}
		ruleset = new PageOrderingRuleset(rawRules, 0); //create ruleset
		System.out.println(ruleset);
		int middlePageCount = 0; //Count to return
		for (List<Page> update : updates)
		{
			//If in order, add to count. 
			if (ruleset.isInOrder(update)) 
			{
				middlePageCount += update.get(update.size()/2).getPageValue();
			}
		}
		System.out.println(middlePageCount);
	}
}
