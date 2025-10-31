import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BPreconditionTest {

    @Test
    @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
    void noNonTrivialFields() {
        List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                .toList();

        assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
    }

    @Test
    @DisplayName("TestAddfirstLastBasic")
    void TestAddfirstLastBasic() {
        ArrayDeque61B<Integer> a1 = new ArrayDeque61B<Integer>();

        a1.addFirst(9);
        a1.addLast(8);
        a1.addFirst(10);
        a1.addLast(7);
        a1.addFirst(11);

        assertThat(a1.toList()).containsExactly( 11, 10, 9, 8, 7).inOrder();

        a1.addLast(6);

        a1.addFirst(12);

        assertThat(a1.toList()).containsExactly(12, 11, 10, 9, 8, 7, 6).inOrder();

        //a1.addFirst(5);

        //assertThat(a1.toList()).containsExactly(12, 11, 10, 9, 8, 7, 6, 5).inOrder();
    }

    @Test
    void TestBasicRemoveFirstLast1() {
        ArrayDeque61B<Integer> a1 = new ArrayDeque61B<Integer>();

        a1.addFirst(9);
        a1.addLast(8);
        a1.addFirst(10);
        a1.addLast(7);
        a1.addFirst(11);
        a1.addLast(6);
        a1.addFirst(12);
        //a1.addLast(5);

        assertThat(a1.toList()).containsExactly(12, 11, 10, 9, 8, 7, 6).inOrder();

        a1.removeLast();

        a1.removeFirst();

        assertThat(a1.toList()).containsExactly(11, 10, 9, 8, 7).inOrder();
    }

    @Test
    void TestBasicRemoveFirstLast2() {
        ArrayDeque61B<Integer> a1 = new ArrayDeque61B<Integer>();

        a1.addFirst(9);
        a1.addLast(8);
        a1.addLast(7);
        a1.addLast(6);
        //a1.addLast(5);

        assertThat(a1.toList()).containsExactly(9,8,7,6).inOrder();

        a1.removeLast();

        a1.removeFirst();

        assertThat(a1.toList()).containsExactly(8,7).inOrder();

        a1.removeLast();

        a1.removeFirst();

        assertThat(a1.toList()).isEmpty();

        a1.removeFirst();

        a1.removeLast();

        assertWithMessage("That's wrong.").that(a1.isEmpty() && a1.size() == 0).isTrue();
    }

    @Test
    void TestGetBasic() {
        ArrayDeque61B<Integer> a1 = new ArrayDeque61B<Integer>();

        a1.addFirst(9);
        a1.addLast(8);
        a1.addLast(7);
        a1.addLast(6);
        //a1.addLast(5);

        assertThat(a1.toList()).containsExactly(9,8,7,6).inOrder();

        assertThat(a1.get(5)).isEqualTo(null);

        assertThat(a1.get(3)).isEqualTo(6);

        assertThat(a1.get(0)).isEqualTo(9);

        assertThat(a1.get(-657)).isEqualTo(null);

        a1.removeFirst();

        assertThat(a1.get(0)).isEqualTo(8);
    }

    @Test
    /*一种恰好卡边边的0,7情况。*/
    void TestExtendBasicLast1()
    {
        ArrayDeque61B<Integer> a1 = new ArrayDeque61B<Integer>();

        a1.addFirst(9);
        a1.addLast(8);
        a1.addFirst(10);
        a1.addLast(7);
        a1.addFirst(11);
        a1.addLast(6);
        a1.addFirst(12);
        a1.addLast(5);

        /*满的一个基础数组。
        * 这里的情况比较极端了。
        * */
        assertThat(a1.toList()).containsExactly(12, 11, 10, 9, 8, 7, 6, 5).inOrder();

//        a1.removeLast();
//
//        assertThat(a1.toList()).containsExactly(12, 11, 10, 9, 8, 7, 6).inOrder();

//        a1.removeFirst();
//
//        assertThat(a1.toList()).containsExactly(11, 10, 9, 8, 7, 6,5).inOrder();

        //a1.addFirst(13);

        a1.addLast(4);
    }

    @Test
    /*这个是正常的夹在中间的情况。*/
    void TestExtendBasicLast2()
    {
        ArrayDeque61B<Integer> a1 = new ArrayDeque61B<Integer>();

        a1.addFirst(9);
;
        a1.addFirst(10);

        a1.addFirst(11);

        a1.addFirst(12);

        a1.addFirst(13);
        ;
        a1.addFirst(14);

        a1.addFirst(15);

        a1.addLast(8);

        /*满的一个基础数组
        * 使用那个二分切片法可以应对这种普通情况但是。。
        * */
        assertThat(a1.toList()).containsExactly(15,14,13,12, 11, 10, 9,8).inOrder();
        a1.addLast(7);

        a1.addFirst(16);

        assertThat(a1.toList()).containsExactly(16,15,14,13,12, 11, 10, 9,8,7).inOrder();
    }

    @Test
        /*一种恰好卡边边的0,7情况。*/
    void TestShrinkBasicFirst1()
    {
        ArrayDeque61B<Integer> a1 = new ArrayDeque61B<Integer>();

        a1.addFirst(9);
        a1.addLast(8);
        a1.addFirst(10);
        a1.addLast(7);
        a1.addFirst(11);
        a1.addLast(6);
        a1.addFirst(12);
        a1.addFirst(13);

        /*满的一个基础数组。
         * 这里的情况比较极端了。
         * */
        assertThat(a1.toList()).containsExactly(13,12, 11, 10, 9, 8, 7, 6).inOrder();

        a1.addLast(5);

        a1.removeFirst();

        a1.removeFirst();

        assertThat(a1.toList()).containsExactly( 11, 10, 9, 8, 7, 6,5).inOrder();
    }

    @Test
    /*这个是正常的夹在中间的情况。*/
    void TestShrinkBasicFirst2()
    {
        ArrayDeque61B<Integer> a1 = new ArrayDeque61B<Integer>();

        a1.addFirst(9);
        ;
        a1.addFirst(10);

        a1.addFirst(11);

        a1.addFirst(12);

        a1.addFirst(13);
        ;
        a1.addFirst(14);

        a1.addFirst(15);

        a1.addFirst(16);

        /*满的一个基础数组
         * 使用那个二分切片法可以应对这种普通情况但是。。
         * */
        assertThat(a1.toList()).containsExactly(16,15,14,13,12, 11, 10, 9).inOrder();

        a1.addLast(8);

        a1.addFirst(17);

        assertThat(a1.toList()).containsExactly(17,16,15,14,13,12, 11, 10, 9,8).inOrder();

        a1.addLast(7);

        a1.removeFirst();

        a1.removeFirst();

        a1.removeFirst();

        a1.removeFirst();

        assertThat(a1.toList()).containsExactly( 13,12, 11, 10, 9,8,7).inOrder();
    }

    @Test
        /*一种恰好卡边边的0,7情况。*/
    void TestShrinkBasicLast1()
    {
        ArrayDeque61B<Integer> a1 = new ArrayDeque61B<Integer>();

        a1.addFirst(9);
        a1.addLast(8);
        a1.addFirst(10);
        a1.addLast(7);
        a1.addFirst(11);
        a1.addLast(6);
        a1.addFirst(12);
        a1.addFirst(13);

        /*满的一个基础数组。
         * 这里的情况比较极端了。
         * */
        assertThat(a1.toList()).containsExactly(13,12, 11, 10, 9, 8, 7, 6).inOrder();

        a1.addFirst(14);

        a1.removeLast();

        a1.removeLast();

        assertThat(a1.toList()).containsExactly( 14,13,12, 11, 10, 9, 8).inOrder();
    }

    @Test
        /*这个是正常的夹在中间的情况。*/
    void TestShrinkBasicLast2()
    {
        ArrayDeque61B<Integer> a1 = new ArrayDeque61B<Integer>();

        a1.addFirst(9);
        ;
        a1.addFirst(10);

        a1.addFirst(11);

        a1.addFirst(12);

        a1.addFirst(13);
        ;
        a1.addFirst(14);

        a1.addFirst(15);

        a1.addFirst(16);

        /*满的一个基础数组
         * 使用那个二分切片法可以应对这种普通情况但是。。
         * */
        assertThat(a1.toList()).containsExactly(16,15,14,13,12, 11, 10, 9).inOrder();

        a1.addLast(8);

        a1.addFirst(17);

        assertThat(a1.toList()).containsExactly(17,16,15,14,13,12, 11, 10, 9,8).inOrder();

        a1.addFirst(18);

        a1.removeLast();

        a1.removeLast();

        a1.removeLast();

        a1.removeLast();

        assertThat(a1.toList()).containsExactly( 18,17,16,15,14,13,12).inOrder();
    }

    @Test
        /*这个是正常的夹在中间的情况。*/
    void TestRemoveAddMix()
    {
        ArrayDeque61B<Integer> a1 = new ArrayDeque61B<Integer>();

        a1.addFirst(9);
        ;
        a1.addFirst(10);

        a1.addFirst(11);

        a1.addFirst(12);

        a1.addFirst(13);
        ;
        a1.addFirst(14);

        a1.addFirst(15);

        a1.addFirst(16);

        /*边界扩容反复横跳测试。*/
        assertThat(a1.toList()).containsExactly(16,15,14,13,12, 11, 10, 9).inOrder();

        a1.removeLast();

        assertThat(a1.toList()).containsExactly( 16,15,14,13,12,11,10).inOrder();

        a1.addFirst(17);

        a1.removeFirst();

        assertThat(a1.toList()).containsExactly( 16,15,14,13,12,11,10).inOrder();

        a1.addLast(9);

        assertThat(a1.toList()).containsExactly( 16,15,14,13,12,11,10,9).inOrder();
    }

    @Test
    void TestRemoveFirst_doubleExtend()
    {
        ArrayDeque61B<Integer> a1 = new ArrayDeque61B<Integer>();

        final int TEST_TIME = 32;

        for(int i = 0;i<TEST_TIME;i++)
        {
            a1.addFirst(i);
        }

        for(int i = 0;i<TEST_TIME/2+1;i++)
        {
            a1.removeFirst();
        }

        assertThat(a1.size()).isEqualTo(15);

        assertThat(a1.toList()).containsExactly( 14,13,12,11,10,9,8,7,6,5,4,3,2,1,0).inOrder();

        for(int i = 0;i<(TEST_TIME/4+1);i++)
        {
            a1.removeFirst();
        }

        assertThat(a1.size()).isEqualTo(6);

        assertThat(a1.toList()).containsExactly( 5,4,3,2,1,0).inOrder();

        int size_temp = a1.size();

        for(int i = 0;i<size_temp-1;i++)
        {
            a1.removeFirst();
        }

        /*回到了本源的起点。*/
        assertThat(a1.toList()).containsExactly( 0).inOrder();
    }

    @Test
    void TestRemoveLast_doubleExtend()
    {
        ArrayDeque61B<Integer> a1 = new ArrayDeque61B<Integer>();

        final int TEST_TIME = 32;

        for(int i = 0;i<TEST_TIME;i++)
        {
            a1.addFirst(i);
        }

        for(int i = 0;i<TEST_TIME/2+1;i++)
        {
            a1.removeLast();
        }

        assertThat(a1.size()).isEqualTo(15);

        assertThat(a1.toList()).containsExactly( 31,30,29,28,27,26,25,24,23,22,21,20,19,18,17).inOrder();

        for(int i = 0;i<(TEST_TIME/4+1);i++)
        {
            a1.removeLast();
        }

        assertThat(a1.size()).isEqualTo(6);

        assertThat(a1.toList()).containsExactly( 31,30,29,28,27,26).inOrder();

        int size_temp = a1.size();

        for(int i = 0;i<size_temp-1;i++)
        {
            a1.removeLast();
        }

        /*回到了本源的起点。*/
        assertThat(a1.toList()).containsExactly( 31).inOrder();
    }

    void TestRemoveAddMix_forthExtend()
    {
        ArrayDeque61B<Integer> a1 = new ArrayDeque61B<Integer>();

        final int TEST_TIME = 32;

        for(int i = 0;i<TEST_TIME*2;i++)
        {
            a1.addFirst(i);
        }

        for(int i = 0;i<TEST_TIME+1;i++)
        {
            a1.removeFirst();
        }

        for(int i = 0;i<TEST_TIME;i++)
        {
            a1.addFirst(i);
        }

        for(int i = 0;i<TEST_TIME/2+1;i++)
        {
            a1.removeFirst();
        }

        assertThat(a1.size()).isEqualTo(15);

        assertThat(a1.toList()).containsExactly( 14,13,12,11,10,9,8,7,6,5,4,3,2,1,0).inOrder();

        for(int i = 0;i<(TEST_TIME/4+1);i++)
        {
            a1.removeFirst();
        }

        assertThat(a1.size()).isEqualTo(6);

        assertThat(a1.toList()).containsExactly( 5,4,3,2,1,0).inOrder();

        int size_temp = a1.size();

        for(int i = 0;i<size_temp-1;i++)
        {
            a1.removeFirst();
        }

        /*回到了本源的起点。*/
        assertThat(a1.toList()).containsExactly( 0).inOrder();
    }

    @Test
    void TestIntegration()
    {
        ArrayDeque61B<Integer> a1 = new ArrayDeque61B<Integer>();

        final int TEST_TIME = 32;

        for(int i = 0;i<TEST_TIME/2;i++)
        {
            a1.addFirst(i);
        }

        a1.removeFirst();

        a1.removeLast();

        assertThat(a1.toList()).containsExactly( 14,13,12,11,10,9,8,7,6,5,4,3,2,1).inOrder();

        a1.removeLast();

        a1.removeFirst();

        a1.removeLast();

        a1.removeLast();

        a1.removeFirst();

        a1.removeFirst();

        assertThat(a1.toList()).containsExactly( 11,10,9,8,7,6,5,4).inOrder();

        a1.removeLast();

        assertThat(a1.toList()).containsExactly( 11,10,9,8,7,6,5).inOrder();

        a1.addFirst(12);

        assertThat(a1.toList()).containsExactly( 12,11,10,9,8,7,6,5).inOrder();

        a1.removeLast();

        a1.removeFirst();

        a1.removeLast();

        a1.removeLast();

        a1.removeLast();

        a1.removeLast();

        assertThat(a1.toList()).containsExactly( 11,10).inOrder();

        a1.removeFirst();

        assertThat(a1.toList()).containsExactly( 10).inOrder();

        for(int i = 0;i<6;i++)
        {
            a1.addFirst(i+11);
        }

        assertThat(a1.toList()).containsExactly( 16,15,14,13,12,11,10).inOrder();

        a1.addLast(9);

        assertThat(a1.toList()).containsExactly( 16,15,14,13,12,11,10,9).inOrder();

        for(int i = 0;i<(TEST_TIME*2);i++)
        {
            a1.addFirst(i+17);
        }

        assertThat(a1.size()).isEqualTo(72);

        for(int i = 0;i<54;i++)
        {
            a1.removeFirst();
        }

        assertThat(a1.size()).isEqualTo(18);

        a1.removeFirst();

        a1.removeLast();

        a1.removeLast();

        assertThat(a1.toList()).containsExactly( 25,24,23,22,21,20,19,18,17,16,15,14,13,12,11).inOrder();
    }
}
