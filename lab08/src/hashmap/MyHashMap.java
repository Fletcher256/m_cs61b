package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    @Override
    public Iterator<K> iterator() {
        return null;
    }

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node> buckets[];
    //这个桶里面每个元素是一个数据结构(链表,二叉树等等),每次添加元素可以从本处观测添加行为的后果。
    // You should probably define some more!

    /** Constructors */
    //初始化自己给构建因子吗???
    public MyHashMap() {
        buckets = new Collection[M];
    }

    public MyHashMap(int initialCapacity) {
        this.M= initialCapacity;
        buckets = new Collection[initialCapacity];
    }

    int M=16;

    double loadFactor = 0.75;

    int N = 0;

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        this.M = initialCapacity;
        this.loadFactor = loadFactor;
        buckets = new Collection[initialCapacity];
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */

    protected Collection<Node> createBucket() {
        // TODO: Fill in this method.
        return new LinkedList<Node>();
    }

    public static void main(String[] args) {
//        Object o = new Object();
//        Integer a = new Integer(43567453);
//       System.out.printf("a = %d\n",o.hashCode());
//       System.out.println(o.hashCode());
//        //被分配的随机对象的内存地址所映射的hash值。
//        for (int i = 0 ;i < 10; i++) {
//            System.out.println(new Object().hashCode());
//        }
        //特定基本类型的哈希码是不变的(不随内存改变而变。)
//        String s = "Hello";
//        Integer i = new Integer(890);
//        Integer j = new Integer(890);
//        System.out.printf("s = %d\n",s.hashCode());
//        System.out.printf("i = %d\n",i.hashCode());
//        System.out.printf("j = %d\n",j.hashCode());
        MyHashMap<Integer,String> h = new MyHashMap<Integer,String>();
        h.put(1, "one");
        h.put(2, "two");
        h.put(3, "three");
        h.put(4, "four");
        h.put(4, "five");
        h.put(4, "five");
        Object o = h.get(2);
        System.out.println(o);
        System.out.println("hi10".hashCode()%16);
        System.out.println("hi1".hashCode()%16);
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    /** Associates the specified value with the specified key in this map.
     *  If the map already contains the specified key, replaces the key's mapping
     *  with the value specified. */
    @Override
    public void put(K key, V value)
    {
        if(buckets != null)
        {
            //元素过多,需要增加桶的数量。
            if(((double)N)/((double)M)>=loadFactor)
            {
                resize(2*M);
            }
            Node n = new Node(key, value);
            int ths = Math.floorMod(n.key.hashCode(),M);
            //这个桶不存在。
            if(buckets[ths] == null)
            {
                //这个就是工厂化方法。多态的实现。
                buckets[ths] = createBucket();
            }
            //桶存在。不存在相同节点再添加。
            //相同键需要进行替换。
            for(Node b : buckets[ths])
            {
                //这个键类型自身的equals方法真的能用啊。。
                //它自己也是内置比较hashcode吧。。
                if(key.equals(b.key))
                {
                    //存在相同节点。直接返回
                    if(b.value.equals(value))
                    {
                        return;
                    }
                    //若两者键值相同但内容不同,那就删掉再添加。
                    buckets[ths].remove(b);
                    N--;
                    break;
                }
            }
            //不存在相同节点则可以新添加节点。
            buckets[ths].add(n);
            N++;
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key. */
    @Override
    //这个方法的返回值不对吧(明明是一个桶,只能返回一个值???????
    //还有就是原有的字符串的hashCode太容易重复了。。这。。。
    public V get(K key)
    {
        int ths = Math.floorMod(key.hashCode(),M);
        //虽然键值会在模之后发生重复,但是没关系,可以去那个桶里头找它。
        if(buckets[ths] == null)
        {
            return null;
        }
        //这里的这个机制看起来是希望你用改写的hashcode和改写的equals去判断两个对象的值
        for(Node b : buckets[ths])
        {
            if(key.equals(b.key))
            {
                return (V) b.value;
            }
        }
        return null;
//        if(buckets[ths] == null)
//        {
//            return null;
//        }
//        else
//        {
//            //这个迭代器的next接口就是从第一个节点开始读取的
//            Node n = buckets[ths].iterator().next();
//            return n.value;
//        }
    }

    /** Returns whether this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key)
    {
        //只好比hash码了,注意用这个取余函数。它可以用模运算将负数模为正数。
        //布号这个类的key的hashcode都是相等的。。。
        //只好用equals一个一个找了。闪避来了,那这样查找效率还是很低。
        if(buckets != null)
        {
            //不如直接取到那个hash的桶进去查找,没必要遍历吧。。
            Collection<Node> b = buckets[Math.floorMod(key.hashCode(),M)];
            if(b!=null)
            {
                for(Node n : b)
                {
                    if(key.equals(n.key))
                    {
                        return true;
                    }
                }
            }

        }

        return false;
    }

    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size()
    {
        return N;
    }

    /** Removes every mapping from this map. */

    @Override
    public void clear()
    {
        buckets = new Collection[M];
        N = 0;
        M = 16;
        return;
    }

    /** Returns a Set view of the keys contained in this map. Not required for this lab.
     * If you don't implement this, throw an UnsupportedOperationException. */
    @Override
    public Set<K> keySet()
    {
        //遍历整张hash表即可。
        Set<K> set = new HashSet<K>();
        for(Collection<Node> b : buckets)
        {
            if(b!=null)
            {
                for(Node n : b)
                {
                    set.add(n.key);
                }
            }
        }
        return set;
    }

    /** Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key)
    {
        //移除整个桶?不对,应该是移除这个键值对应的节点吧。
        //若这个同为空直接置空?还是保留算了
        //还有一个就是是否我们需要实现对空间的缩小?
        //暴力是可以直接过用例的但是用例确实太少了。
        if(buckets != null)
        {
            Collection<Node> b = buckets[Math.floorMod(key.hashCode(),M)];

            if(b!=null)
            {
                for(Node n : b)
                {
                    if(key.equals(n.key))
                    {
                        b.remove(n);
                        N--;
                        //若当前N/M比缩放因子小则可以执行缩容。
                        if(M>31 && ((double)N)/((double)M)<=loadFactor)
                        {
                            resize(M/2);
                        }
                        return (V)n.value;
                    }
                }
            }
        }
        return null;
    }

    private void resize(int newCapacity)
    {
        Collection<Node> newBuckets[] = new Collection[newCapacity];
        //新桶组的创建

        for(Collection<Node> b : buckets)
        {
            if(b!=null)
            {
                //全部转移。
                for(Node n : b)
                {
                    if(newBuckets[Math.floorMod(n.key.hashCode(),newCapacity)] == null)
                    {
                        newBuckets[Math.floorMod(n.key.hashCode(),newCapacity)] = createBucket();
                    }
                    newBuckets[Math.floorMod(n.key.hashCode(),newCapacity)].add(n);
                }
            }
        }
        M=newCapacity;
        buckets = newBuckets;
    }
}
