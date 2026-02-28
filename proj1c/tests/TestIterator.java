import deque.ArrayDeque61B;
import deque.LinkedListDeque61B;
import org.junit.jupiter.api.*;

import java.util.Comparator;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class TestIterator {
    @Test
    public void testArrayIterator() {
        ArrayDeque61B<Integer> arrayDeque = new ArrayDeque61B<Integer>();

        for (int i = 0; i < 10; i++) {
            arrayDeque.addFirst(i);
        }

//        for(Object i:arrayDeque)
//        {
//            System.out.println(i);
//        }

        /*这里只能用Object自带的toString。这个东西只能打印类内的地址值。。
        * 就是那个hashcode,它返回的是个[身份哈希值]。比地址那一串64位十六进制值好点。。吧应该。。
        * */
        System.out.println(arrayDeque);

        /*之前为什么要toList呢。
        * 因为我们的ArrayDeque之前没有迭代器接口所以containsExactly调用不了。
        * java标准的List是有的因此可以比较。
        * 实现了之后就不用担心啦。
        * */
        assertThat(arrayDeque).containsExactly(9,8,7,6,5,4,3,2,1,0);
    }

    @Test
    public void testListIterator() {
        LinkedListDeque61B listDeque = new LinkedListDeque61B<Integer>();

        for (int i = 0; i < 10; i++) {
            listDeque.addFirst(i);
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
        assertThat(listDeque).containsExactly(9,8,7,6,5,4,3,2,1,0);
    }

    @Test
    public void testArrayIteratorEmpty() {
        ArrayDeque61B<Integer> arrayDeque = new ArrayDeque61B<Integer>();

        assertThat(arrayDeque).containsExactly();
    }

    @Test
    public void testListIteratorEmpty() {
        LinkedListDeque61B listDeque = new LinkedListDeque61B<Integer>();

        assertThat(listDeque).containsExactly();
    }
}
