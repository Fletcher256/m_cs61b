import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.Assert.fail;

public class UnionFindTest {

    /**
     * Checks that the initial state of the disjoint sets are correct (this will pass with the skeleton
     * code, but ensure it still passes after all parts are implemented).
     */
    @Test
    public void initialStateTest() {
        UnionFind uf = new UnionFind(4);
        assertThat(uf.connected(0, 1)).isFalse();
        assertThat(uf.connected(0, 2)).isFalse();
        assertThat(uf.connected(0, 3)).isFalse();
        assertThat(uf.connected(1, 2)).isFalse();
        assertThat(uf.connected(1, 3)).isFalse();
        assertThat(uf.connected(2, 3)).isFalse();
    }

    /**
     * Checks that invalid inputs are handled correctly.
     */
    @Test
    public void illegalFindTest() {
        UnionFind uf = new UnionFind(4);
        try {
            uf.find(10);
            fail("Cannot find an out of range vertex!");
        } catch (IllegalArgumentException e) {
            return;
        }
        try {
            uf.union(1, 10);
            fail("Cannot union with an out of range vertex!");
        } catch (IllegalArgumentException e) {
            return;
        }
    }

    /**
     * Checks that union is done correctly (including the tie-breaking scheme).
     */
    @Test
    public void basicUnionTest() {
        UnionFind uf = new UnionFind(10);
        uf.union(0, 1);
        assertThat(uf.find(0)).isEqualTo(1);
        uf.union(2, 3);
        assertThat(uf.find(2)).isEqualTo(3);
        uf.union(0, 2);
        assertThat(uf.find(1)).isEqualTo(3);

        uf.union(4, 5);
        uf.union(6, 7);
        uf.union(8, 9);
        uf.union(4, 8);
        uf.union(4, 6);

        assertThat(uf.find(5)).isEqualTo(9);
        assertThat(uf.find(7)).isEqualTo(9);
        assertThat(uf.find(8)).isEqualTo(9);

        uf.union(9, 2);
        assertThat(uf.find(3)).isEqualTo(9);
    }

    /**
     * Unions the same item with itself. Calls on find and checks that the outputs are correct.
     */
    @Test
    public void sameUnionTest() {
        UnionFind uf = new UnionFind(4);
        uf.union(1, 1);
        for (int i = 0; i < 4; i += 1) {
            assertThat(uf.find(i)).isEqualTo(i);
        }
    }

    /**
     * Write your own tests below here to verify for correctness. The given tests are not comprehensive.
     * Specifically, you may want to write a test for path compression and to check for the correctness
     * of all methods in your implementation.
     */

    @Test
    public void SizeofUnionTest() {
        UnionFind uf = new UnionFind(15);

        assertThat(uf.sizeOf(1)).isEqualTo(1);

        uf.union(0, 0);

        assertThat(uf.sizeOf(0)).isEqualTo(1);

        uf.union(1, 0);

        uf.union(0, 1);

//        uf.sizeOf(0);

        assertThat(uf.sizeOf(0)).isEqualTo(2);

        uf.union(0, 2);

        assertThat(uf.sizeOf(0)).isEqualTo(3);
    }

    @Test
    public void QuickUnionTest() {
        UnionFind uf = new UnionFind(15);

        assertThat(uf.sizeOf(1)).isEqualTo(1);

        uf.union(1, 0);

        uf.union(0, 2);

        uf.union(4, 3);

        uf.union(5, 3);

        //先造出两条枝。

        uf.union(4, 0);

        //接上

        assertThat(uf.sizeOf(1)).isEqualTo(6);

        assertThat(uf.parent(3)).isEqualTo(0);

        assertThat(uf.parent(0)).isEqualTo(-6);

        assertThat(uf.parent(4)).isEqualTo(3);

        //优化测试-初步(树压缩)

        uf.find(5);

        assertThat(uf.parent(5)).isEqualTo(0);

        //再来一条树吧。(等大。)算是测试规模扩大了。

        uf.union(8, 7);

        uf.union(6, 7);

        uf.union(10, 9);

        uf.union(11, 9);

        uf.union(10,8);

        assertThat(uf.sizeOf(7)).isEqualTo(6);

        //先造出两条枝。

        uf.union(10, 4);

        assertThat(uf.parent(4)).isEqualTo(0);

        assertThat(uf.parent(10)).isEqualTo(7);

        uf.union(11,5);

        assertThat(uf.parent(9)).isEqualTo(0);

        assertThat(uf.parent(11)).isEqualTo(0);
    }

    @Test
    public void BadUnionTest() {
        UnionFind uf = new UnionFind(16);

//        uf.union(1, 0);
//
//        uf.union(3, 2);
//
//        uf.union(5, 4);
//
//        uf.union(7, 6);
//
//        uf.union(9, 8);
//
//        uf.union(11, 10);
//
//        uf.union(13, 12);
//
//        uf.union(15, 14);

        for(int i = 0;i<16;i+=2)
        {
            uf.union(i+1, i);
        }

        //基元准备完毕。

//        uf.union(3,1);

        for(int i = 1;i<16;i+=4)
        {
            uf.union(i+2, i);
        }

        uf.union(7, 0);

        uf.union(15, 8);

        //每次联合都会执行底层优化操作。
        uf.union(15,0);

        //如此,一个完全二叉树就构建完成了。

        assertThat(uf.sizeOf(15)).isEqualTo(16);

        assertThat(uf.parent(15)).isEqualTo(8);
    }
}


