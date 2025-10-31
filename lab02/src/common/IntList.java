package common;

public class IntList {
    public int first;
    public IntList rest;

    public IntList(int f, IntList r) {
        first = f;
        rest = r;
    }

    /** Returns the ith item of this IntList. */
    public int get(int i) {
        if (i == 0) {
            return first;
        }
        return rest.get(i - 1);
    }

    /**
     * Method to create an IntList from an argument list.
     * You don't have to understand this code. We have it here
     * because it's convenient with testing. It's used like this:
     * <p>
     * IntList myList = IntList.of(1, 2, 3, 4, 5);
     * will create an IntList 1 -> 2 -> 3 -> 4 -> 5 -> null.
     * <p>
     * You can pass in any number of arguments to IntList.of and it will work:
     * IntList mySmallerList = IntList.of(1, 4, 9);
     */
    /*这个of用法就在这里了。和库里头的list一样。它们都是用的of来一次性用像初始化数组一样地连续初始化一堆节点*/
    /*可变参数还能这么干去初始化链表???C里头去试试。。*/
    /*它这个可变参数可以塞数组的。。*/
    public static IntList of(int... argList) {
        if (argList.length == 0)
            return null;
        /*这么写真占内存吧。每递归一层都会多开上个数组长度-1的内存。。
        * C里头这么也写内存早就爆炸了吧。。
        * JVM发力了。。
        * 临时内存随便开有系统清是吧(
        * */
        int[] restList = new int[argList.length - 1];
        System.arraycopy(argList, 1, restList, 0, argList.length - 1);
        /*拔第一个头用来*/
        return new IntList(argList[0], IntList.of(restList));
    }

    /*比较两个链表是否相同。。*/
    public boolean equals(Object other) {
        if (other instanceof IntList oL) {
            if (first != oL.first) {
                return false;
            } else if (rest == null && oL.rest == null) {
                return true;
            } else if (rest != null && oL.rest != null) {
                return rest.equals(oL.rest);
            } else {
                return false;
            }
        }
        return false;
    }

    /*看着像是对迭代器的接口?*/
    public String print() {
        if (rest == null) {
            // Converts an Integer to a String!
            return String.valueOf(first);
        } else {
            return first + " -> " + rest.print();
        }
    }
}
