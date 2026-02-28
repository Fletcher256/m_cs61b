import java.lang.reflect.Array;
import java.util.*;

//写2-3-4树吧。
public class BSTMap<K extends Comparable<K>, V>  implements Map61B<K, V> {

    int size= 0;

    BSTNode<K, V> root;

    public BSTMap() {
        //java里的Set无法实例化,需要选择内核。
        list = new TreeSet<K>();
    }

    BSTNode<K, V> obj;

    /** Associates the specified value with the specified key in this map.
     *  If the map already contains the specified key, replaces the key's mapping
     *  with the value specified. */

    /** Represents one node in the linked list that stores the key-value pairs
     *  in the dictionary. */
    static class BSTNode<K extends Comparable<K>, V>  {
        K key;
        V val;
        BSTNode left, right;

        BSTNode(K key, V val,BSTNode left, BSTNode right) {
            this.key = key;
            this.val = val;
            this.left = left;
            this.right = right;
        }

        public int compareRoots(BSTNode other) {
            /* We are able to safely invoke `compareTo` on `n1.item` because we
             * know that `K` extends `Comparable<K>`, so `K` is a `Comparable`, and
             *`Comparable`s must implement `compareTo`. */
            return this.key.compareTo((K)(other.key));
        }
    }

//    public int cmp(BSTNode other)
//    {
//        return root.key.compareTo((K)(other.key));
//    }

    public void put(K key, V value)
    {
        if (root == null)
        {
            root = new BSTNode<K,V>(key,value,null,null);

            size++;

            list.add(key);

            return;
        }
        m_put(key,value,root);
    }

    private BSTNode<K, V> m_put(K key, V value,BSTNode<K, V> cur)
    {
        //无则新建。
        BSTNode<K, V> temp = cur;

        if(cur == null)
        {
            size++;

            return new BSTNode<K,V>(key,value,null,null);
        }

        //cur的值比待加入节点大。
        if(cur.key.compareTo(key)>0)
        {
            //左转
            temp.left =m_put(key,value,cur.left);
        }
        //比其小
        else if(cur.key.compareTo(key)<0)
        {
            //右转。
            temp.right =m_put(key,value,cur.right);
        }
        else {
            //相等则覆盖

            temp.val = value;
        }

        return temp;
    }

    /** Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key. */
    public V get(K key)
    {
        BSTNode<K, V> cur = m_get(key,root);
        return  cur == null ? null : cur.val;
    }

    private BSTNode<K, V> m_get(K key, BSTNode<K, V> cur)
    {
        //返回值最好了。
        if(cur == null)
        {
            return null;
        }
        //cur的值比待加入节点大。
        if(cur.key.compareTo(key)>0)
        {
            //左转
            return m_get(key,cur.left);
        }
        //比其小
        else if(cur.key.compareTo(key)<0)
        {
            //右转。
            return m_get(key,cur.right);
        }
        else{
            return cur;
        }
    }

    /** Returns whether this map contains a mapping for the specified key. */
    public boolean containsKey(K key)
    {
        return c_get(key,root);
    }

    private boolean c_get(K key, BSTNode<K, V> cur)
    {
        //返回值最好了。
        if(cur == null)
        {
            return false;
        }
        //cur的值比待加入节点大。
        if(cur.key.compareTo(key)>0)
        {
            //左转
            return c_get(key,cur.left);
        }
        //比其小
        else if(cur.key.compareTo(key)<0)
        {
            //右转。
            return c_get(key,cur.right);
        }
        else{
            return true;
        }
    }

    /** Returns the number of key-value mappings in this map. */
    public int size()
    {
        return size;
    }

    /** Removes every mapping from this map. */
    public void clear()
    {
        size = 0;

        root = null;
    }

    @Override
    //删除目标,并将目标两个最近的节点提升为根节点。
    //向左搜索
    public V remove(K key) {
//        throw new UnsupportedOperationException();
        root = m_remove(key,root);

        return obj == null ? null : obj.val;
    }

    private BSTNode<K,V> m_remove(K key ,BSTNode<K, V> r)
    {
        if(r == null)
        {
            return obj = null;
        }
        //搜到小值
        else if(r.key.compareTo(key)>0)
        {
            r.left = m_remove(key,r.left);
        }
        //搜到大值
        else if(r.key.compareTo(key)<0)
        {
            r.right = m_remove(key,r.right);
        }
        //搜到了。
        else
        {
            obj= r;
            //叶子,返回上级节点为null
            size--;

            list.remove(key);

            if(r.left == null && r.right == null)
            {
                return null;
            }
            //左为空。
            else if(r.left == null)
            {
                return r.right;
            }
            //右为空
            else if (r.right==null)
            {
                return r.left;
            }
            //搜到双子树。
            else
            {
                BSTNode<K,V> temp = r.left;
                //搜左子树的最大值。
                while(temp.right!=null)
                {
                    temp = temp.right;
                }

                //本节点修改值为左子树最大值。

                r.val = temp.val;

                //将左子树最大值节点的左子树衔接上去。或者说,直接删除这个节点。

                m_remove(temp.key,r);

                r.key = temp.key;
            }
        }
        return r;
    }

    //这个函数需要自己可以判断叶子节点。
//   private BSTNode<K,V> mystery_get(boolean sec, BSTNode<K, V> cur,K key){
//        //约定:为真搜索大值,为假搜索小值。
//
//        //搜大值。
//        if(sec)
//        {
//            if(cur.right==null)
//            {
//                return cur;
//            }
//            return mystery_get(sec,cur.right,key);
//        }
//        //搜小值。
//        else
//        {
//            if(cur.left==null)
//            {
//                return cur;
//            }
//            return mystery_get(sec,cur.left,key);
//        }
//    }

    @Override
    //这个不就是遍历整棵树然后把值强行录入么。。。。
    public Set<K> keySet() {
//        throw new UnsupportedOperationException();

        //不让逃课。每次调用这个节点时必须使用
        return list;
    }

    TreeSet<K> list;

    @Override
    public Iterator<K> iterator() {
        return new BSTMap.BSTMapIter();
    }

    /** Keys and values are stored in a linked list of Entry objects.
     *  This variable stores the first pair in this linked list. */

    /** An iterator that iterates over the keys of the dictionary. */
    //这玩意内核是集合。但它用的搜索二叉树来实现搜索。
        //似乎还不让用java内置的hash集合实现。。。
    private class BSTMapIter implements Iterator<K> {

        /** Create a new ULLMapIter by setting cur to the first node in the
         *  linked list that stores the key-value pairs. */
        public BSTMapIter() {
            olist = (K[])list.toArray();
        }

        @Override
        public boolean hasNext() {
            return olist[cur] != null;
        }

        @Override
        public K next() {
            return olist[cur++];
        }

        /** Stores the current key-value pair. */
        K[] olist;

        int cur = 0;
    }

    public static void main(String[] args) {
//        BSTNode<Integer,String> b1 = new BSTNode<>(1,"999",null,null);
//
//        BSTNode<Integer,String> b2 = b1;
//
//        //对象赋值,本质类内存地址复制。
//        System.out.printf("%s\n",b1);
//
//        System.out.printf("%s\n",b2);
    }
}
