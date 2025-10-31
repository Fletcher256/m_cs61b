import java.util.ArrayList;
import java.util.List;

public class ListExercises {

    /** Returns the total sum in a list of integers */
	public static int sum(List<Integer> L) {
        // TODO: Fill in this function.
        /*无脑来了*/
        //return sum(L);
        int sum = 0;
        for(int i : L)
        {
            sum+=i;
        }
        return sum;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        // TODO: Fill in this function.
        List<Integer> even_reflect = new ArrayList<Integer>();
        for(int i : L)
        {
            if(i%2==0)
            {
                even_reflect.add(i);
            }
        }
        return even_reflect;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        // TODO: Fill in this function.

        List common = new ArrayList();

        for(int i : L1)
        {
            for(int j :L2)
            {
                if(i == j)
                {
                    common.add(i);
                }
            }
        }
        return common;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        // TODO: Fill in this function.
        int count = 0;
        for(String str : words)
        {
          for(int i = 0;i<str.length();i++)
          {
              if(c==str.charAt(i))
              {
                  count++;
              }
          }
        }
        return count;
    }
}
