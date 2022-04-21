import java.util.HashMap;
import java.util.Stack;

public class 用前序遍历构造二叉搜索树 {
    /*
    * 力扣链接:https://leetcode-cn.com/problems/construct-binary-search-tree-from-preorder-traversal/
    * 给你一颗二叉搜索树的前序遍历数组preorder,请你利用这个数组构建出一颗搜索二叉树并
    * 返回其根节点
    * */
    public static class TreeNode{
        public int val;
        public TreeNode left;
        public TreeNode right;
        public TreeNode(int val) {
            this.val = val;
        }
    }
    public static TreeNode bstFromPreorder2(int[] preorder){
        if(preorder==null|| preorder.length==0){
            return null;
        }
        return process2(preorder,0,preorder.length-1);
    }
    //用递归来构建这颗树
    /* 递归含义:利用数组preorder[L....R]返回构建出二叉树并返回头节点  */
    public static TreeNode process2(int[] preorder,int L,int R){
        //一个节点都没有
        if(L>R){
            return null;
        }
        //只有一个节点
        if(L==R){
            return new TreeNode(preorder[L]);
        }
        //不止有一个节点
        TreeNode head=new TreeNode(preorder[L]);
        //找到第一个比head大的数
        int firstBig=L+1;
        for(;firstBig<=R;firstBig++){
            if(preorder[firstBig]>preorder[L]){
                break;
            }
        }
        head.left=process2(preorder,L+1,firstBig-1);
        head.right=process2(preorder,firstBig,R);
        return head;
    }
    /* 对于上面在每一个位置找后面第一个比自己大的数，最差情况时间复杂度可以达到O(N^2)
    * 对于这种在某一个位置，左右两侧，第一个比自己大和第一个比自己小的问题，均可以用单调栈 */
    //最优解，用单调栈生成预处理数组
    public static TreeNode bstFromPreorder1(int[] preorder){
        if(preorder==null|| preorder.length==0){
            return null;
        }
        HashMap<Integer, Integer> valueIndexMap=new HashMap<>();
        getFirstBig1(preorder,valueIndexMap);
        return process1(preorder,0,preorder.length-1,valueIndexMap);
    }
    //用递归来构建这颗树
    /* 递归含义:利用数组preorder[L....R]返回构建出二叉树并返回头节点  */
    public static TreeNode process1(int[] preorder,int L,int R,HashMap<Integer, Integer> valueIndexMap){
        //一个节点都没有
        if(L>R){
            return null;
        }
        //只有一个节点
        if(L==R){
            return new TreeNode(preorder[L]);
        }
        //不止有一个节点
        TreeNode head=new TreeNode(preorder[L]);
        //找到第一个比head大的数,并记录在表中
        int firstBig=valueIndexMap.get(preorder[L])>R?R+1:valueIndexMap.get(preorder[L]);
        head.left=process1(preorder,L+1,firstBig-1,valueIndexMap);
        head.right=process1(preorder,firstBig,R,valueIndexMap);
        return head;
    }
    public static void getFirstBig1(int[] preorder,HashMap<Integer, Integer> valueIndexMap){
        Stack<Integer> stack=new Stack<>();
        for(int i=0;i<preorder.length;i++){
            //1:将栈中所有小于arr[i]的值全弹出，并记录信息
            while(!stack.isEmpty()&&preorder[stack.peek()]<preorder[i]){
                int popIndex = stack.pop();
                valueIndexMap.put(preorder[popIndex],i);
            }
            //2:将i压入栈中
            stack.add(i);
        }
        //3:将栈中剩余的元素弹出
        while(!stack.isEmpty()){
            int popIndex=stack.pop();
            valueIndexMap.put(preorder[popIndex],preorder.length);
        }
    }
    //上面用单调栈的解法已经是时间复杂度最优的解法了
    //但我们还可以用数组来实现单调栈，优化常数时间
    public static void getFirstBig2(int[] preorder,HashMap<Integer, Integer> valueIndexMap){
        int[] stack=new int[preorder.length];
        int index=0;
        for(int i=0;i<preorder.length;i++){
            //1:将栈中所有小于arr[i]的值全弹出，并记录信息
            while(index!=0&&preorder[stack[index-1]]<preorder[i]){
                int popIndex = stack[--index];
                valueIndexMap.put(preorder[popIndex],i);
            }
            //2:将i压入栈中
            stack[index++]=i;
        }
        //3:将栈中剩余的元素弹出
        while(index!=0){
            int popIndex=stack[--index];
            valueIndexMap.put(preorder[popIndex],preorder.length);
        }
    }
}
