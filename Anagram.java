
import java.util.ArrayList;
public class Anagram
{
	private static int a;
	
	public Anagram()
	{
		a = 0;
	}
	
	public static void main(String[] args) 
    {
		updatePhrase("a");
    } 
    
    public static void updatePhrase(String phrase)
	{
		for(int i = 0; i < 5; i++)
		{
			System.out.println(phrase);
			if(a > 3)
				return;
			else
			{
				a++;
				updatePhrase(phrase+"a");
			}
		}
		return;
	}
}
