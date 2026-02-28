public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /**
         * Creates a RBTreeNode with item ITEM and color depending on ISBLACK
         * value.
         * @param isBlack
         * @param item
         */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /**
         * Creates a RBTreeNode with item ITEM, color depending on ISBLACK
         * value, left child LEFT, and right child RIGHT.
         * @param isBlack
         * @param item
         * @param left
         * @param right
         */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left, RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Creates an empty RedBlackTree.
     */
    public RedBlackTree() {
        root = null;
    }

    /**
     * Flips the color of node and its children. Assume that NODE has both left
     * and right children
     * @param node
     */
    void flipColors(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE

        if(node != null)
        {
            node.isBlack = !node.isBlack;
        }
        if(node.left != null)
        {
            //这个算是取反操作?
            node.left.isBlack = !node.left.isBlack;
        }

        if(node.right != null)
        {
            node.right.isBlack = !node.right.isBlack;
        }
    }

    /**
     * Rotates the given node to the right. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
        if(node == null || node.left == null)
        {
            return node;
        }
        else
        {
            RBTreeNode<T> temp = node.left;

            //交换两者颜色即可。
            boolean BLeft = temp.isBlack;

            temp.isBlack = node.isBlack;

            node.isBlack = BLeft;

            //左子节点的右子树赋给本节点左子树。
            node.left = temp.right;

            //本节点成为左子结点的右子树
            temp.right = node;

            return temp;
        }
    }

    /**
     * Rotates the given node to the left. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    //左右旋是只有两者颜色不同时才可以启动。(修正。只要满足左为双红或右为一红即可启动旋转。)
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
        if(node == null || node.right == null)
        {
            return node;
        }
        else
        {
            RBTreeNode<T> temp = node.right;

            //交换两者颜色即可。
            boolean BLeft = temp.isBlack;

            temp.isBlack = node.isBlack;

            node.isBlack = BLeft;

            //右子节点的左子树赋给本节点右子树。
            node.right = temp.left;

            //本节点成为右子结点的左子树
            temp.left = node;

            return temp;
        }
    }

    /**
     * Helper method that returns whether the given node is red. Null nodes (children or leaf
     * nodes) are automatically considered black.
     * @param node
     * @return
     */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    /**
     * Inserts the item into the Red Black Tree. Colors the root of the tree black.
     * @param item
     */
    public void insert(T item) {
        root = insert(root, item);
        root.isBlack = true;

    }

    /**
     * Inserts the given node into this Red Black Tree. Comments have been provided to help break
     * down the problem. For each case, consider the scenario needed to perform those operations.
     * Make sure to also review the other methods in this class!
     * @param node
     * @param item
     * @return
     */
    private RBTreeNode<T> insert(RBTreeNode<T> node, T item) {
        // TODO: Insert (return) new red leaf node.

        if(root == null)
        {
            return new RBTreeNode<>(true, item, null, null);
        }

        if(node == null)
        {
            return new RBTreeNode<>(false, item, null, null);
        }

        // TODO: Handle normal binary search tree insertion.

        //本处结点值比目标值大,左查找
        if(node.item.compareTo(item) > 0)
        {
            node.left = insert(node.left, item);
        }
        //or右查找
        else if(node.item.compareTo(item) < 0)
        {
            node.right = insert(node.right, item);
        }
        else
        {
            //已经存在,不需要再对它赋值。(final的不变量)
            return node;
        }

        //能下来说明它成功添加了节点。
        // TODO: Rotate left operation
        //红色节点只存在于左节点,这个旋转是不需要考虑任意节点颜色的。
        if(isRed(node.right))
        {
            node = rotateLeft(node);
        }
        // TODO: Rotate right operation
        RBTreeNode<T> temp = node.left;
        //自己是左子节点,自己的左子节点也是红的
        if(isRed(temp) && isRed(temp.left))
        {
            node = rotateRight(node);
        }
        // TODO: Color flip
        //这个说明一个节点已经塞爆了
        if(isRed(node.left) && isRed(node.right))
        {
            flipColors(node);
        }
        return node; //fix this return statement
    }

}
