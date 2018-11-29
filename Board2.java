package lab7out;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Board2 implements Serializable
{
	private ArrayList<Integer> list2 = new ArrayList<Integer>(
			Arrays.asList(2, 3, 4,22,23,24,25,26,39,43,49,53,55,59,65,75,85));
	public ArrayList<Integer> getList()
	{
		
			return list2;
		
	}
}
