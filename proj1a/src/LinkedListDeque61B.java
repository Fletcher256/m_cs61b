import java.util.List;

import java.util.ArrayList;

public class LinkedListDeque61B<T> implements Deque61B<T>{
    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * //@param x item to add
     */

    /*这里内部私有类不需要直接声明泛型类模版(不然它就会自己内部再实现一个模板类把外部覆盖掉。)
    * 这么做可以直接在内部类里同步使用主部类的泛型,这样不会有语法上的冲突。
    * */
    private class Node
    {
        Node last;

        T data;

        Node next;

        public Node(T d, Node last, Node next)
        {
            data = d;
            this.last = last;
            this.next = next;
        }
    }

    private Node sentinel;

    private int size;

    public LinkedListDeque61B()
    {
        Integer d = 0;
        /*java没有C++那么自由XD,这里创建时放进来sentinel的还是null,后续再联吧。。*/
        sentinel = new Node(null,null,null);
        sentinel.next = sentinel;
        sentinel.last = sentinel;
        size  = 0;
    }

    @Override
    public void addFirst(T x)
    {
        sentinel.next.last = new Node(x,sentinel,sentinel.next);
        sentinel.next = sentinel.next.last;
        size++;
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x)
    {
        sentinel.last.next = new Node(x,sentinel.last,sentinel);
        sentinel.last = sentinel.last.next;
        size++;
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList()
    {
        List<T> returnList = new ArrayList<>();
        Node current = sentinel;
        for(int i = 0; i < size; i++)
        {
            returnList.add(current.next.data);
            current = current.next;
        }
        return returnList;
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     *
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     */
    @Override
    public boolean isEmpty()
    {
        return sentinel.next == sentinel;
    }

    /**
     * Returns the size of the deque. Does not alter the deque.
     *
     * @return the number of items in the deque.
     */
    @Override
    public int size()
    {
        return size;
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeFirst()
    {
        T data = sentinel.next.data;
        if(size > 0)
        {
            /*哨兵的下一个的下一个的上一个节点为哨兵。*/
            sentinel.next.next.last = sentinel;

            sentinel.next = sentinel.next.next;

            size--;
        }
        return data;
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast()
    {
        T data = sentinel.last.data;
        if(size > 0)
        {
            sentinel.last.last.next = sentinel;

            sentinel.last = sentinel.last.last;

            size--;
        }
        return data;
    }

    /**
     * The Deque61B abstract data type does not typically have a get method,
     * but we've included this extra operation to provide you with some
     * extra programming practice. Gets the element, iteratively. Returns
     * null if index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T get(int index)
    {
        if(index < 0 || index >= size)
        {
            return null;
        }
        Node current = sentinel;
        for(int i = index; i > -1; i--)
        {
            current = current.next;
        }

        return current.data;
    }

    /**
     * This method technically shouldn't be in the interface, but it's here
     * to make testing nice. Gets an element, recursively. Returns null if
     * index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    /*突兀想起这个东西是不是可以传神秘小方法啊。
    * 封装的接口思想。
    * */
    public T getRecursive(int index)
    {
        /*只有在没有指向该对象的指针时，Java垃圾回收器才会为我们“删除”东西。这一点和C这种自己管理内存的语言简直完全是里两个东西。。*/
        if(index < 0 || index >= size)
        {
            return null;
        }

        return MysterygetRecursive(index,sentinel.next);
    }


    private T MysterygetRecursive(int index, Node current)
    {
        if(index == 0){
            return current.data;
        }
        else{
            return MysterygetRecursive(index-1,current.next);
        }
    }
}
