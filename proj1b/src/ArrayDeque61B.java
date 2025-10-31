import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.Math;

public class ArrayDeque61B<T> implements Deque61B<T> {

    private int size;

    private T[] Array;

    private int nextLast;

    private int nextFirst;

    private final int ORG_SIZE = 8;

    private final double USEAGE_FACTOR = 0.25;

    public ArrayDeque61B()
    {
        size = 0;

        Array = (T[]) new Object[ORG_SIZE];

        nextFirst = Array.length/2-1;

        nextLast = Array.length/2;
    }

    /*需要分段拷贝。*/
    private T[] resize(int left,int right,int newSize,int orgSize)
    {
        /*它们答案用的for循环拷贝。。o(n)如何呢。。。*/
        T[] newArray = (T[]) new Object[newSize];

        System.arraycopy(Array, 0, newArray, 0, left%newSize+1);

        if(right+newSize >= orgSize)
        {
            System.arraycopy(Array, right, newArray, right+newSize-orgSize, orgSize-right);
        }

        return newArray;
    }

    @Override
    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    public void addFirst(T x)
    {
        /*不是这个需要用双端队列。。。
        * 看描述这个双端队列和以前那个的实现不太一样。它是中部为头,向两侧扩散的。
        * 是头尾指针索引的叠加引用吧。
        * 以前那个是从栈底开始计数的。
        * */

        /*我们还是在两者交替时进行内存拷贝。*/
        if(nextFirst == nextLast)
        {
            /*先来个指数级空间拓展方案。*/
            Array = resize(nextFirst,nextLast,Array.length*2,Array.length);

            nextFirst += size+1;
        }

        size++;

        Array[nextFirst] = x;

        nextFirst = Math.floorMod(nextFirst-1,Array.length);

    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x)
    {
        /*不行。必须在空间拓展时保证两者是重叠的才行(这样指针分发比较方便。)*/

        if(nextFirst == nextLast)
        {
            /*先来个指数级空间拓展方案。*/
            Array = resize(nextFirst,nextLast,Array.length*2,Array.length);

            nextFirst += size+1;
        }

        size++;

        Array[nextLast] = x;

        nextLast = Math.floorMod(nextLast+1,Array.length);
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList()
    {
        /*这玩意会把已删掉的值直接拷贝出来。ccc
        * */
        List<T> list = new ArrayList<>();
        for(int i = 0; i < size; i++)
        {
            list.add(Array[Math.floorMod(nextFirst+i+1,Array.length)]);
        }
        return list;
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     *
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     */
    @Override
    public boolean isEmpty()
    {
        return size == 0;
    }

    /**
     * Returns the size of the deque. Does not alter the deque.
     *
     * @return the number of items in the deque.
     */
    @Override
    public int size()
    {
        return size;
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeFirst()
    {
        T Temp = null;

        if(size>0)
        {
            /*为了防止一直在扩大内存,在使用删除时也需要使用一个内存缩小的功能。
            * 它需要有效元素个数为内存大小的1/4(至少。)
            * */

            /*内存大小更低那么使用因子可以更低哦。(更宽容了,没毛病。)
            * 但使用因子缩小后内存分配可能出破限值。。
            * */
            double USAGE_FACTOR_TEMP = USEAGE_FACTOR*( Array.length<=ORG_SIZE*2 ? (Array.length>ORG_SIZE ? 0.5 : 4): 1);

            if(size<=USAGE_FACTOR_TEMP*Array.length && USAGE_FACTOR_TEMP != 1)
            {
                /*注意。删除之后两者的next指向是相同的。
                *也就是说,我们需要在将头部删除后,将两个指针指向同一个位置(空的。)。
                */
                Array = resize(nextLast-1,nextFirst+1,Array.length/2,Array.length);

                nextFirst -= (int)(size/(USAGE_FACTOR_TEMP*2));

                /*这俩不该重合的吧。。*/
                nextLast = Math.floorMod(nextLast,Array.length);
            }

            size--;

            nextFirst = Math.floorMod(nextFirst+1,Array.length);

            Temp = Array[nextFirst];

        }

        return Temp;
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast()
    {
        T Temp = null;

        if(size>0)
        {
            /*如removeFirst法炮制即可。
            * 但注意这下的缩放因子有可变性。(16内存以下放缩因子为0.5)
            * */

            double USAGE_FACTOR_TEMP = USEAGE_FACTOR*( Array.length<=ORG_SIZE*2 ? (Array.length>ORG_SIZE ? 0.5 : 4): 1);

            if(size<=USAGE_FACTOR_TEMP*Array.length && USAGE_FACTOR_TEMP != 1)
            {
                /*注意。删除之后两者的next指向是相同的。
                 *也就是说,我们需要在将头部删除后,将两个指针指向同一个位置(空的。)。
                 */
                Array = resize(nextLast-1,nextFirst+1,Array.length/2,Array.length);

                /*这里存疑。。要不要对nextLast进行size的衰减呢。
                * 两者都可能破限。还是先衰减的好
                * */

//                nextLast = Math.floorMod(nextLast,Array.length);

                nextFirst = Math.floorMod(nextFirst,size<ORG_SIZE ?ORG_SIZE : Array.length);
            }
            /*内存使用率小于0.25,缩小。*/

            size--;

            nextLast = Math.floorMod(nextLast-1,Array.length);

            Temp = Array[nextLast];
        }

        return Temp;
    }

    /**
     * The Deque61B abstract data type does not typically have a get method,
     * but we've included this extra operation to provide you with some
     * extra programming practice. Gets the element, iteratively. Returns
     * null if index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T get(int index)
    {
        T Temp = null;

        if(index < size && index > -1)
        {
            Temp = Array[Math.floorMod(nextFirst+index+1,Array.length)];
        }

        return Temp;
    }

    /**
     * This method technically shouldn't be in the interface, but it's here
     * to make testing nice. Gets an element, recursively. Returns null if
     * index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T getRecursive(int index)
    {
        /*666直接抛出异常开始偷懒hhhh不过本来就不需要使用这个递归就是了。*/
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    public static void main (String[] args)
    {
        /*
        * 这个floorMod和%的区别是它是向负无穷取整的,而%是向0取整的。
        * */

        int a= -1;
        int b= 8;

        System.out.printf("floorMod %d Mod %d = %d\n",a,b,Math.floorMod(a,b));

        System.out.printf("Normal %d Mod %d = %d\n",a,b,a%b);

        /*这里就可以看出它是向负无穷取整的。-1-(8)(-1/8) == -1-8*(-1) == 7
        * 一般取模是-1-8*(0) == -1,就是这个取模会多蹭一位。。
        * */
//        floorMod -1 Mod 8 = 7
//        Normal -1 Mod 8 = -1
    }
}
