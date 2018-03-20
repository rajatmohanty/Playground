/**
 * Created by Kray on 2016/12/26.
 */
public class TreeNode {
    public int val;
    public double per;
    public TreeNode left;
    public TreeNode right;
    public boolean getItem;

    public TreeNode() {
    }

    public TreeNode(int val, double per, boolean getItem) {
        this.val = val;
        this.per = per;
        this.getItem = getItem;
    }

    public TreeNode(int x) {
        val = x;
    }
}