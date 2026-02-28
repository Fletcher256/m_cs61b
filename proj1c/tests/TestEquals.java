import deque.ArrayDeque61B;
import deque.LinkedListDeque61B;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.*;

import java.util.Comparator;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class TestEquals {
    @Test
    public void testArrayEquals() {
        ArrayDeque61B<Integer> arrayDeque = new ArrayDeque61B<Integer>();

        ArrayDeque61B<Integer> a1 = new ArrayDeque61B<Integer>();

        for (int i = 0; i < 10; i++) {
            arrayDeque.addFirst(i);
            a1.addFirst(i);
        }

        assertThat(arrayDeque.equals(a1)).isTrue();

        a1.addFirst(11);

        assertThat(arrayDeque.equals(a1)).isFalse();
    }

    @Test
    public void testListEquals() {
        LinkedListDeque61B listDeque = new LinkedListDeque61B<Integer>();

        LinkedListDeque61B l1 = new LinkedListDeque61B<Integer>();

        for (int i = 0; i < 10; i++) {
            listDeque.addFirst(i);
            l1.addFirst(i);
        }

//        for(Object i:listDeque)
//        {
//            System.out.println(i);
//        }

        /*之前为什么要toList呢。
         * 因为我们的ArrayDeque之前没有迭代器接口所以containsExactly调用不了。
         * java标准的List是有的因此可以比较。
         * 实现了之后就不用担心啦。
         * */
        assertThat(listDeque.equals(l1)).isTrue();

        l1.addFirst(11);

        assertThat(l1.equals(listDeque)).isFalse();
    }

    @Test
    public void testArrayEqualsEmpty() {
        ArrayDeque61B<Integer> arrayDeque = new ArrayDeque61B<Integer>();

        ArrayDeque61B<Integer> a1 = new ArrayDeque61B<Integer>();

        assertThat(arrayDeque.equals(a1)).isTrue();

        a1.addFirst(1);

        assertThat(arrayDeque.equals(a1)).isFalse();
    }

    @Test
    public void testListEqualsrEmpty() {
        LinkedListDeque61B listDeque = new LinkedListDeque61B<Integer>();

        LinkedListDeque61B l1 = new LinkedListDeque61B<Integer>();

        assertThat(listDeque.equals(listDeque)).isTrue();

        l1.addFirst(11);

        assertThat(l1.equals(listDeque)).isFalse();
      /*上下这两个东西等价。Truth库的isEquals()调用的就是LinkedListDeque61B.equals()。*/
//      assertThat(l1).isEqualTo(listDeque);
    }
}
