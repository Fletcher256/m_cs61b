package deque;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;

public class MaxArrayDeque61B<T> extends ArrayDeque61B<T> {

    Comparator<T> comparator;

    /*注意这个比大小的类用的外部Comparator方法。*/

    public MaxArrayDeque61B(Comparator<T> c) {
        comparator = c;
    }

    /*用自己内部默认的比较方法*/
    public T max()
    {
        T max = get(0);

        for(Object val:this)
        {
            if(comparator.compare(max,(T)val) < 0)
            {
                max = (T)val;
            }
        }
        return max;
    }

    /*用的外部比较方法。*/
    public T max(Comparator<T> c)
    {
        T max = get(0);

        for(Object val:this)
        {
            if(c.compare(max,(T)val) < 0)
            {
                max = (T)val;
            }
        }
        return max;
    }
}
