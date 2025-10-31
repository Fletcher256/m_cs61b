import java.util.*;

public class JavaExercises {

    /**
     * Returns an array [1, 2, 3, 4, 5, 6]
     */
    public static int[] makeDice() {
        // TODO: Fill in this function.
        //int[] a = new int[]{1, 2, 3, 4, 5, 6};
        return new int[]{1, 2, 3, 4, 5, 6};
    }

    /**
     * Returns the order depending on the customer.
     * If the customer is Ergun, return ["beyti", "pizza", "hamburger", "tea"].
     * If the customer is Erik, return ["sushi", "pasta", "avocado", "coffee"].
     * In any other case, return an empty String[] of size 3.
     */
    public static String[] takeOrder(String customer) {
        // TODO: Fill in this function.
        if (customer.equals("Ergun")) {
            return new String[]{"beyti", "pizza", "hamburger", "tea"};
        } else if (customer.equals("Erik")) {
            return new String[]{"sushi", "pasta", "avocado", "coffee"};
        } else {
            return new String[3];
        }
    }

    /**
     * Returns the positive difference between the maximum element and minimum element of the given array.
     * Assumes array is nonempty.
     */
    /*这东西就是极大值减极小值*/
    public static int findMinMax(int[] array) {
        // TODO: Fill in this function.
        /*没初始化但已分配内存的数组内部都是0的。*/
        if (array == null) {
            return 0;
        } else {
            int max = 0, min = Integer.MAX_VALUE;
//            for(int i = 0;i<array.length;i++)
//            {
//                if(min > array[i])
//                {
//                    min = array[i];
//                }
//                else if(max < array[i])
//                {
//                    max = array[i];
//                }
//            }

            /*u1s1这种写法是不严谨的。可能出现两个列表出现两个以上相同的情况,这时还要不要考虑查重呢。*/
            for(int i = 0;i<array.length;i++)
            {
                if(min > array[i])
                {
                    min = array[i];
                }
                else if(max < array[i])
                {
                    max = array[i];
                }
            }
            return max - min;
        }

    }

    /**
     * Uses recursion to compute the hailstone sequence as a list of integers starting from an input number n.
     * Hailstone sequence is described as:
     * - Pick a positive integer n as the start
     * - If n is even, divide n by 2
     * - If n is odd, multiply n by 3 and add 1
     * - Continue this process until n is 1
     */
    /*递归封装思想.jpg。hhh这个就是开了一个接口给#神秘递归方法#用hhh*/
    public static List<Integer> hailstone(int n) {
        List l = new ArrayList<>();
        l.add(n);
        return hailstoneHelper(n, l);
    }

    /*java的List(列表)分链表型和数组型。*/
    /*下面这个用递归了,那它必定是链表型了。*/
    /*这个冰雹序列的函数意思是执行特定对N操作并把这个值存到List里头。*/
    private static List<Integer> hailstoneHelper(int x, List<Integer> list) {
        // TODO: Fill in this function.

        /*这玩意不要递归也行说是。*/
//        if(x == 1)
//        {
//             return list;
//        }
//        else
//        {
//            /*even*/
//            if(x%2 == 0)
//            {
//                x/=2;
//            }
//            /*odd*/
//            else
//            {
//                x = x*3+1;
//            }
//            list.add(x);
//            return hailstoneHelper(x, list);
//        }

        while(x!=1)
        {
            /*even*/
            if(x%2 == 0)
            {
                x/=2;
            }
            /*odd*/
            else
            {
                x = x*3+1;
            }
            list.add(x);
        }
        return list;
    }

    public static void main(String[] args) {
//        int[] dice = new int[3];
//        /*这里说明java里头的数组和C一样,创建未初始化时都是内部赋0的*/
//        for (int i = 0; i < 3; i++) {
//            System.out.println(dice[i]);
//        }

        /*来看看java里的Map吧。*/
        /*无论实现方式是哈希还是红黑树它的外部接口效果都是一样的。*/
         Map<Character,Integer> map = new HashMap<>();
         /*就是键值对嘛。一个k字符对应整型,如下在map中一直用i修改k的对应的值即可。*/
        map.put('b',1);//追加一个键值对。

        //哈希作为字典查东西还是快的。

        for(int i = 0;i<8;i++)
        {
            map.put('k',i);
        }
        System.out.printf("The 'k' is equal %d.\n",map.get('k'));//取特定键对应的值。

        System.out.printf("The map's size is equal %d.\n",map.size());//取map的大小。

        /*下面来点map特有的东西*/

        /*这个东西拿出键值对里的[键],返回值是一个Set(集合)类型。*/
        //System.out.printf("The 'k' is equal %c.",(map.keySet()));//这个。。返回一个集合。这个集合里头保存着Map里的所有类。

        /*对Set的访问只有迭代器与转为数组两种访问方法。*/
        /*Set不发get接口啊啊啊啊啊c*/

        /*这个是通过迭代map中的[键]来访问[值]*/
        for(Character c : map.keySet())//这个本质上是在迭代map映射的那个集合吧。
        {
            /*注意map的get接口只能拿键来查值啊。*/
            System.out.printf("The '%c' is equal %d.\n",c,map.get(c));
        }

        /*对集合有认识之后,我们可以来看其它对map的迭代方式。*/

        /*这个是迭代值直接来访问值*/
        /*没有用值来逆向访问键吗????*/
        for(Integer i : map.values())
        {
            System.out.printf("The value %d.\n",i);
        }

        /*这个比较nb一点,用单个键值对来访问每对值。*/
        /*这个Map.Entry<Key,Value>是map里头的键值对对象。它嘛。。就是。。额保存一对键的值啦hh*/
        /*而entrySet当然就是在迭代map里的存放每个键值对的集合(这个才是map的本体吧说是。)*/
        for(Map.Entry<Character,Integer> i : map.entrySet())
        {
            /*每个键值对有键接口与值接口,如此就可以理解这里它的迭代实现方式了。*/
            System.out.printf("The '%s' is equal %d.\n",i.getKey(),i.getValue());
        }

        /*泛型都要用这个java打包的对象类。*/
        Set<Integer> set = new HashSet<>();

        set.add(4);
        set.add(2);
        set.add(1);
        set.add(3);
        set.add(4);
        /*移除集合中元素*/
        set.remove(3);
        set.add(3);
        /*转出来的数组不能接收吗*/
//        Integer[] setarray =  set.toArray();
        /*也不能直接用。。*/
//        (set.toArray())[2];
        /*只有用这种奇怪的对象数组才能接收这种类型的泛型对象数组,如此方可实现对集合的范访问*/
        Object[] array = set.toArray();
        for(int i = 0;i<array.length;i++)
        {
            System.out.print(array[i]);
        }
        /*从添加值的行为来看,它这个似乎是会自动排序的,会按数组索引升序排序。*/
        /*特点是已添加的元素不会再次添加进集合(符合集合数学上的定义,即没有重复元素出现在同一个集合中。)*/

        if(set.contains(5))//这个是看集合是否包含某个元素。有则返回true。
        {
            System.out.printf("The 'k' is equal %d.\n",map.get('k'));
        }

        for(int i :set)//集合迭代器。
        {
            System.out.printf("A element od set is %d.\n",i);
        }

        System.out.println(findMinMax(new int[]{1, 2, 3, 90, 5, 6}));

        /*测试一下java的List吧*/

        /*这种类似vector这种线性数连续组。*/
        List<Integer> l1 = new ArrayList<>();
        for(int i = 0;i<8;i++)
        {
            l1.add(i);//添加元素
            System.out.printf("The l1[%d] = %d.\n",i,l1.get(i));//接口
        }

        l1.set(7,90);//数组索引一类的东西

        System.out.printf("The size of l1 = %d\n",l1.size());//取大小

        List<Integer> l2 = new LinkedList<Integer>();

        /*等等这个语法糖的意思是i的值在每次循环时都会更新为l1的当前元素的值,不是1~n啊。。*/
        /*java的所谓增强型数组遍历就是c++那个封装过头的迭代器吧这。。*/
        for(int i:l1)
        {
            l2.add(i);//添加元素
            System.out.printf("The l2[%d] = %d.\n",i,l2.get(i%l1.size()));//接口
        }

        /*甚至可以索引取值,赋值。。这链表没内味啊。*/
        /*哦所以它其实是底层实现不同而已是吧*/
        l2.set(2,90);

        System.out.printf("The size of l2 = %d\n",l2.size());//取大小
    }
}




