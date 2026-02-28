import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.lang.reflect.Array;
import java.util.Arrays;


public class Percolation {
    // TODO: Add any necessary instance variables.

    WeightedQuickUnionUF wq;

    int n = 0;

    int count = 0;

    //需要配一个映射矩阵?
    boolean[][] grid;

    //来个四向搜索?

    final int xx[] = {1,0,-1,0};

    final int yy[] = {0,-1,0,1};

    public Percolation(int N) {
        // TODO: Fill in this constructor.

        if (N<=0) {
            throw new IllegalArgumentException();
        }
        //多开两个虚拟站点,一个顶部一个底部。当顶部与底部相连(is connected即可。)
        wq = new WeightedQuickUnionUF(N*N+2);

        grid = new boolean[N][N];

        n = N;

        //初始的时候要把顶部的节点都绑到那个虚拟顶部节点上。底部同理。
        for(int i =1;i<=N;i++)
        {
            wq.union(0,i);
            //不可如此无脑了XD
//            wq.union(N*N+1,N*(N-1)+i);
        }
    }

    public void open(int row, int col) {
        // TODO: Fill in this method.
        if (row< 0 || col < 0|| row> n-1 || col >n-1) {
            throw new IllegalArgumentException();
        }
        if(grid[row][col]!=true)
        {
            count++;

            grid[row][col] = true;

            //四向搜索已开放的节点并加入豪华套餐
            for(int i = 0;i<4;i++)
            {
                int dx = row + xx[i];

                int dy = col + yy[i];

                if(dx>-1 && dx<n && dy>-1 && dy<n && grid[dx][dy])
                {
                    //union特性是两个集合大小若相同则其是后者者连到前者上面。
                    wq.union(toXY(row,col),toXY(dx,dy));
                }
            }
        }
    }

    private int toXY(int x, int y) {
        return x*n+y+1;
    }

    public boolean isOpen(int row, int col) {
        // TODO: Fill in this method.
        if (row< 0 || col < 0|| row> n-1 || col >n-1) {
            throw new IllegalArgumentException();
        }
        return grid[row][col] == true;
    }

    public boolean isFull(int row, int col) {
        // TODO: Fill in this method.
        if (row< 0 || col < 0|| row> n-1 || col >n-1) {
            throw new IllegalArgumentException();
        }
        //为什么不能对那个算法进行剪枝降低树高啊。。这样每次找主节点都要飞到头部看它是否是顶部的叶子节点。。
        //算了其实这个绘图算法就是要对每个格子的情况进行判定的。所以每个格子找个根不过分吧(
        //还有,要判定它是否开通了。。。
        //这个其实还有点问题。当其已经上下导通时,其底部侧向的没连通节点也会判定导通。
        //已加入并查集是这样的。。
        return grid[row][col]==true && wq.find(toXY(row,col)) == 0;
    }

    public int numberOfOpenSites() {
        // TODO: Fill in this method.
        return count;
    }

    public boolean percolates() {
        // TODO: Fill in this method.

        //若代表头部的节点与代表底部的节点处于同一集合,则可以代表渗流导通。
        //那算了直接遍历底部所有节点看是否有东西祖节点是顶部虚拟节点即可。
        //只需要判定是否通了就行是吧。。
        for(int i = 0;i<n;i++)
        {
            if(this.wq.connected(n*(n-1)+i, 0))
            {
                return true;
            }
        }
        return false;
    }

    // TODO: Add any useful helper methods (we highly recommend this!).
    // TODO: Remove all TODO comments before submitting.

    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        p.open(0,0);
        p.isFull(0,0);
    }
}
