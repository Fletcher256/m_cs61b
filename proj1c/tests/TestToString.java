import deque.ArrayDeque61B;
import deque.LinkedListDeque61B;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.*;

import java.util.Comparator;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class TestToString {
    @Test
    public void testArrayToString() {
        ArrayDeque61B<Integer> arrayDeque = new ArrayDeque61B<Integer>();

        ArrayDeque61B<Integer> a1 = new ArrayDeque61B<Integer>();

        for (int i = 0; i < 10; i++) {
            arrayDeque.addFirst(i);
            a1.addFirst(i);
        }

        System.out.println(arrayDeque);

        /*String也是没有正常的比较方法。。又比地址是吧(*/
//        assertThat(arrayDeque.equals(new String("[0 1 2 3 4 5 6 7 8 9]"))).isTrue();

//        a1.addFirst(11);
//
//        assertThat(arrayDeque.equals(a1)).isFalse();
    }

    @Test
    public void testListToString() {
        LinkedListDeque61B listDeque = new LinkedListDeque61B<Integer>();

//        LinkedListDeque61B l1 = new LinkedListDeque61B<Integer>();

        for (int i = 0; i < 10; i++) {
            listDeque.addFirst(i);
//            l1.addFirst(i);
        }

        System.out.println(listDeque);
    }

    @Test
    public void testArrayToStringEmpty() {
        ArrayDeque61B<Integer> arrayDeque = new ArrayDeque61B<Integer>();

        ArrayDeque61B<Integer> a1 = new ArrayDeque61B<Integer>();

        System.out.println(arrayDeque);
    }

    @Test
    public void testListToStringEmpty() {
        LinkedListDeque61B listDeque = new LinkedListDeque61B<Integer>();

        System.out.println(listDeque);
    }
}
