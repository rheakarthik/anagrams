import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;		// for testing purposes

/**
 *	SortMethods - Sorts an ArrayList of Strings in ascending order.
 *
 *	Requires FileUtils class to compile.
 *	Requires file randomWords.txt to execute a test run.
 *
 *	@author	Rhea Karthik
 *	@since	January 5th, 2022
 */
public class SortMethods {
	
	String[] temp;
	
	
	
	/**
	 *	Merge Sort algorithm - in ascending order
	 *	@param arr		List of String objects to sort
	 */
	public List<String> mergeSort(List<String> arr) {
		
		int n = arr.size();
		temp = new String[n];
		mergeSortRecurse(arr, 0, arr.size() - 1);
		
		return arr;
	}
	
	/**
	 *	Recursive mergeSort method.
	 *	@param arr		List of String objects to sort
	 *	@param first	first index of arr to sort
	 *	@param last		last index of arr to sort
	 */
	public void mergeSortRecurse(List<String> arr, int from, int to)
	{
		if(to - from < 2)
		{
			if(to > from && arr.get(to).compareTo(arr.get(from)) < 0)
			{
				String temps = arr.get(to);
				arr.set(to, arr.get(from));
				arr.set(from, temps);
			}
		}
		else
		{
			int middle = (from+to)/2;
			mergeSortRecurse(arr, from, middle);
			mergeSortRecurse(arr, middle+1, to);
			merge(arr, from, middle, to);
		}
	}
	
	/**
	 *	Merge two lists that are consecutive elements in array.
	 *	@param arr		List of String objects to merge
	 *	@param first	first index of first list
	 *	@param mid		the last index of the first list;
	 *					mid + 1 is first index of second list
	 *	@param last		last index of second list
	 */
	public void merge(List<String> arr, int from, int middle, int to)
	{
		int i = from;
		int j = middle+1;
		int k = from;
		
		while(i <= middle && j <= to)
		{
			if(arr.get(i).compareTo(arr.get(j)) < 0)
			{
				temp[k] = arr.get(i);
				i++;
			}
			else
			{
				temp[k] = arr.get(j);
				j++;
			}
			k++;
		}
		
		while(i <= middle)
		{
			temp[k] = arr.get(i);
			i++;
			k++;
		}
		
		while(j <= to)
		{
			temp[k] = arr.get(j);
			j++;
			k++;
		}
		
		for(k = from; k <= to; k++)
			arr.set(k, temp[k]);
		
		
	}

	
	/**
	 *	Print an List of Strings to the screen
	 *	@param arr		the List of Strings
	 */
	public void printArray(List<String> arr) {
		if (arr.size() == 0) System.out.print("(");
		else System.out.printf("( %-15s", arr.get(0));
		for (int a = 1; a < arr.size(); a++) {
			if (a % 5 == 0) System.out.printf(",\n  %-15s", arr.get(a));
			else System.out.printf(", %-15s", arr.get(a));
		}
		System.out.println(" )");
	}
	
	/*************************************************************/
	/********************** Test program *************************/
	/*************************************************************/
	private final String FILE_NAME = "randomWords.txt";
	
	public static void main(String[] args) {
		SortMethods se = new SortMethods();
		se.run();
	}
	
	public void run() {
		List<String> arr = new ArrayList<String>();
		// Fill List with random words from file		
		fillArray(arr);
		
		System.out.println("\nMerge Sort");
		System.out.println("Array before sort:");
		printArray(arr);
		System.out.println();
		mergeSort(arr);
		System.out.println("Array after sort:");
		printArray(arr);
		System.out.println();
	}
	
	// Fill String array with words
	public void fillArray(List<String> arr) {
		Scanner inFile = FileUtils.openToRead(FILE_NAME);
		while (inFile.hasNext())
			arr.add(inFile.next());
		inFile.close();
	}
}
