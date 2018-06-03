import java.util.Scanner;

/**
 * Created by Kray on 2017/3/31.
 */
public class Legendary {

    static double q;
    static int n;

    public static void main(String[] args) {

        double p = 0.5;
        q = 0.75;
        n = 2;

        Scanner in = new Scanner(System.in);
        try {
            p = in.nextInt() / 100.0;
            q = in.nextInt() / 100.0;
            n = in.nextInt();
        } catch (Exception e) {
            e.printStackTrace();
        }

        TreeNode treeNode = new TreeNode(0, p, false);

        createTree(treeNode);

        System.out.printf("%.2f", calculate(treeNode, 0));
    }

    public static void createTree(TreeNode treeNode) {
        if (treeNode == null) {
            return;
        } else {

//            System.out.println("Create " + treeNode.val + " with " + treeNode.per + " and " + treeNode.getItem);

            if (treeNode.val == n) {
//                treeNode.left = null;
//                treeNode.right = null;
                return;
            }

            if (!treeNode.getItem) {
                if (treeNode.per < 1) {
                    treeNode.left = new TreeNode(treeNode.val + 1, Math.floor(100 * treeNode.per / Math.pow(2, treeNode.val + 1)) / 100.0, true);
                    treeNode.right = new TreeNode(treeNode.val, 1 - treeNode.per + q > 1 ? 1 : 1 - treeNode.per + q, false);
                } else {
                    treeNode.left = new TreeNode(treeNode.val + 1, Math.floor(100 * treeNode.per / Math.pow(2, treeNode.val + 2)) / 100.0,  true);
                    treeNode.right = null;
                }
            } else {
                treeNode.left = new TreeNode(treeNode.val + 1, Math.floor(100 * treeNode.per / Math.pow(2, treeNode.val + 1)) / 100.0,  true);
                treeNode.right = new TreeNode(treeNode.val, 1 - treeNode.per + q > 1 ? 1 : 1 - treeNode.per + q, false);
            }
            createTree(treeNode.left);
            createTree(treeNode.right);
        }
    }

    public static double calculate(TreeNode treeNode, int sum) {
        if (treeNode == null) {
            return 0;
        }
        if (treeNode.val == n) {
            return sum;
        } else {
            return calculate(treeNode.left, sum + 1) * treeNode.per + calculate(treeNode.right, sum + 1) * (1 - treeNode.per);
        }
    }

    public static class TreeNode {
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
}
