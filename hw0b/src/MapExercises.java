import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapExercises {
    /** Returns a map from every lower case letter to the number corresponding to that letter, where 'a' is
     * 1, 'b' is 2, 'c' is 3, ..., 'z' is 26.
     */
    public static Map<Character, Integer> letterToNum() {
        // TODO: Fill in this function.
        /*java是静态数据类型。char和int要用强制进制转换来实现沟通交流。*/
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        char c_temp = 'a';
        /*'z'-'a' == 25????好像是吧。它编号可不就是0-25,一共26个数吗。*/
        for(int i = 0; i <= 'z'-'a'; i++)
        {
            map.put((char)((int)c_temp+i), i+1);
        }
        return map;
    }

    /** Returns a map from the integers in the list to their squares. For example, if the input list
     *  is [1, 3, 6, 7], the returned map goes from 1 to 1, 3 to 9, 6 to 36, and 7 to 49.
     */
    public static Map<Integer, Integer> squares(List<Integer> nums) {
        // TODO: Fill in this function.
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < nums.size(); i++)
        {
            map.put(nums.get(i), nums.get(i)*nums.get(i));
        }
        return map;
    }

    /** Returns a map of the counts of all words that appear in a list of words. */
    public static Map<String, Integer> countWords(List<String> words) {
        // TODO: Fill in this function.
        Map<String, Integer> map = new HashMap<String, Integer>();

        /*这个才是map的一般常见用法:字典。*/

        for(String c : words)
        {
            /*这个put是自带更新功能的。没有这个键那就新建,有这个键那就用新的value更新它的值。*/
//                map.put(c,map.get(c)+1);//这个这。。编译器太nb了。。。直接把递归的那个自增值考虑到了。。。
            /*若本单词不存在那么对这个键用get会导致它访问null爆内存。*/
            if(map.containsKey(c))
            {
                map.put(c,map.get(c)+1);
            }
            else
            {
                map.put(c,1);
            }
        }

        return map;
    }
}
