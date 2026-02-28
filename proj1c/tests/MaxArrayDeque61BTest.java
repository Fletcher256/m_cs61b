import org.junit.jupiter.api.*;

import java.util.Comparator;
import java.util.Random;

import deque.MaxArrayDeque61B;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class MaxArrayDeque61BTest {
    private static class StringLengthComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.length() - b.length();
        }
    }

    private static class IntComparator implements Comparator<Integer> {
        public int compare(Integer a, Integer b) {
            return a-b;
        }
    }

    @Test
    public void basicTest() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());
        mad.addFirst("");
        mad.addFirst("2");
        mad.addFirst("fury road");
        assertThat(mad.max()).isEqualTo("fury road");
    }

    @Test
    public void EmptyTest() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());

        assertThat(mad.max()).isEqualTo(null);
    }

    @Test
    public void IntTest() {
        /*这个naturelOrder是自然顺序比较器。。就是以一个升序大小进行比较。
        * 要是用内部自己封装的高阶函数,那么我们要new一个高阶函数对象来实现方法接入
        * */
        MaxArrayDeque61B<Integer> mad = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());

        Random random = new Random();

        random.setSeed(0);

        for(int i = 0;i<10;i++)
        {
            mad.addFirst(i*i);
        }

        mad.addLast(89);

        mad.addFirst(-32342);

        assertThat(mad.max()).isEqualTo(89);

        /*比较自定义方法与java的库方法*/
        assertThat(mad.max()).isEqualTo(mad.max(new IntComparator()));
    }
}
