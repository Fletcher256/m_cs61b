import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnionFind {
    private static final Logger log = LoggerFactory.getLogger(UnionFind.class);
    // TODO: Instance variables
    int[] Array;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        // TODO: YOUR CODE HERE
        Array = new int[N];
        //将数组中的值初始化为-1。
        Arrays.fill(Array, -1);
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        // TODO: YOUR CODE HERE
        if(v>Array.length-1 || v<0)
        {
            return -1;
        }
        return -Array[findroot(v)];
    }

    private int findroot(int v)
    {
        if(v>Array.length-1 || v<0)
        {
            return -1;
        }

        int idx =v;
        while(Array[idx]>-1)
        {
            idx = Array[idx];
        }

        return idx;
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root.
       只是找本节点的父节点而已
       */
    public int parent(int v) {
        // TODO: YOUR CODE HERE

        return v>Array.length-1 || v<0 ? -1: Array[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        // TODO: YOUR CODE HERE

        //两者相等,或者两者的根节点相同,那么可以证明两者属于同一个集合。
        return !(v1 < 0 || v2 < 0||v1 >= Array.length || v2 >= Array.length) && (v1 == v2 || findroot(v1) == findroot(v2));
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException.
       这个东西是找集合的根。
       先不写快速压缩算法。
       find这个函数会把待嫁接的那棵树展平(从初始搜索叶子结点的位置一直爬树爬到根)
       中途对其进行节点重接到根的操作。
       */
    public int find(int v) {
        // TODO: YOUR CODE HERE
        if(v>Array.length-1 || v<0)
        {
            throw new IllegalArgumentException();
        }
        //就不写递归hhh
        //布号,要是实现每次循环的话要两次循环才能跟进根节点的值。不如递归(反正增长慢。)
        //        int idx =v;
        //        while(Array[v]>-1)
        //        {
        //            idx = Array[idx];
        //        }
        //        //记录一下根。
        //        int root = idx;
        //        while(root != v)
        //        {
        //
        //        }


        //若其值为负,则其为根节点
        if(Array[v]<0)
        {
            return v;
        }
        else //否则开始递归搜索。
        {
            int root=find(Array[v]);
            /*路径压缩。*/
            Array[v]=root;
            return root;
        }
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure.
       翻译:连接v1,v2集合。当两者大小相等时,将v1连到v2上。
       大小不等时,将节点数少的那个集合连接到节点数多的那个集合上。
       新的父集合会从它的v点处开始向上爬树执行压缩优化操作。
       */
    public void union(int v1, int v2) {
        // TODO: YOUR CODE HERE
        //非法搜索。
        int root1 = 0;
        int root2 = 0;
        /*自己连自己的来了。。不兑
        *  还要一个判断,就是判断两个节点是否属于同一个集合。。。。。
        * 不然卡死循环。。。
        * */
        if(connected(v1,v2))
        {
            //若两者处在同一个集合上,则对前者使用优化。
            find(v1);
            return;
        }
        else if(sizeOf(v1)>sizeOf(v2))
        {
            //找根的时候直接开优化。对哦。这个好像是初衷吧(
            v1 ^= v2;
            v2 ^= v1;
            v1 ^= v2;
        }

        root2=find(v2);
        root1=find(v1);
        Array[root2]+=Array[root1];
        Array[root1]=root2;
    }
}
