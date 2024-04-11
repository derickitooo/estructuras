public class TreeNode<P, V> {

    private P priority;
    private V value;
    private TreeNode<P, V> parent;
    private TreeNode<P, V> left;
    private TreeNode<P, V> right;

    public TreeNode(P priority, V value) {
        this.priority = priority;
        this.value = value;
    }

    public P getPriority() {
        return priority;
    }

    public void setPriority(P priority) {
        this.priority = priority;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public TreeNode<P, V> getParent() {
        return parent;
    }

    public void setParent(TreeNode<P, V> parent) {
        this.parent = parent;
    }

    public TreeNode<P, V> getLeft() {
        return left;
    }

    public void setLeft(TreeNode<P, V> left) {
        this.left = left;
    }

    public TreeNode<P, V> getRight() {
        return right;
    }

    public void setRight(TreeNode<P, V> right) {
        this.right = right;
    }
}
