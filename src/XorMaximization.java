import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

class TreeNode{
	int id;
	int data;
	ArrayList<Integer> children = null;
	public TreeNode(int id , int data){
		this.id=id;
		this.data = data;
	}
	
}
public class XorMaximization {
	static HashMap<Integer,TreeNode> tree = new HashMap<Integer,TreeNode>();
	static HashMap<Integer,TreeNode> dTree = new HashMap<Integer,TreeNode>();
	
	public static void addEdge(int from , int to){
		TreeNode fromNode = tree.get(from);
		TreeNode toNode = tree.get(to);
		if(null == fromNode.children)
			fromNode.children=new ArrayList<Integer>();
		fromNode.children.add(to);
		if(null == toNode.children)
			toNode.children=new ArrayList<Integer>();
		toNode.children.add(from);
		
		fromNode = dTree.get(from);
		toNode = dTree.get(to);
		if(null == fromNode.children)
			fromNode.children=new ArrayList<Integer>();
		fromNode.children.add(to);
	}
	public static void bfs(int root){
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(root);
		while(!q.isEmpty()){
			int t = q.poll();
			q.addAll(dTree.get(t).children);
			System.out.print(" "+dTree.get(t).data);
		}
		
	}
    private static Stack<Integer> path  = new Stack<Integer>();   // the current path
    private static ArrayList<Integer> onPath  = new ArrayList<Integer>();     // the set of vertices on the path

    public static HashSet<Integer> findAllPaths(HashMap<Integer,TreeNode> g, int s, int t) {
    	ArrayList<ArrayList<Integer>> paths = new ArrayList<>();
        dfs(g, s, t , paths);
        HashSet<Integer> nodesInPath = new HashSet<>();
        for(ArrayList<Integer> a : paths){
        	nodesInPath.addAll(a);
        }
//        for(int n : nodesInPath)
//        	System.out.println(tree.get(n).data);       	
        return nodesInPath;
    }

    // use DFS
    private static void dfs(HashMap<Integer,TreeNode>  g, int s, int t, ArrayList<ArrayList<Integer>> paths) {

        // add node v to current path from s
        path.push(s);
        onPath.add(s);

        // found path from s to t - currently prints in reverse order because of stack
        if (s == t) {
        	   ArrayList<Integer> p = new ArrayList<>(path);
        	   paths.add(p);
        }

        // consider all neighbors that would continue path with repeating a node
        else {
            for (int id : g.get(s).children) {
                if (!onPath.contains(id)) 
                	dfs(g, id, t,paths);
            }
        }

        // done exploring from v, so remove from path
        path.pop();
        onPath.remove(onPath.indexOf(s));
    }
	public static void divide(int src , int dest , int num){
		HashSet<Integer> nodesInPath = findAllPaths(tree, src, dest);
		for(int node : nodesInPath){
			TreeNode treeNode = tree.get(node);
			TreeNode dTreeNode = dTree.get(node);
			treeNode.data = (int) Math.ceil((double)treeNode.data / num);
			dTreeNode.data=treeNode.data;
		}
	}
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int q = scan.nextInt();
		for(int i=0;i<n;i++){
			int val=scan.nextInt();
			tree.put(i+1, new TreeNode(i+1,val));
			dTree.put(i+1, new TreeNode(i+1,val));
		}
		for(int i=0;i<n-1;i++){
			int from = scan.nextInt();
			int to = scan.nextInt();
			addEdge(from, to);
		}
		scan.nextLine();
		for(int i=0;i<q;i++){
			String scanQuery = scan.nextLine();
			String[] scanQueryElements = scanQuery.split(" ");
			int a = Integer.parseInt(scanQueryElements[1]);
			int b = Integer.parseInt(scanQueryElements[2]);
			switch(scanQueryElements[0]){
			case "Divide":
						int w = Integer.parseInt(scanQueryElements[3]);
						divide(a,b,w);	
						break;
			case "Multiply":
						multiply(a,b);
						break;
			case "Query":
						System.out.println(query(a,b));
						break;
			}
		}
	}
	private static void multiply(int node, int num) {
		TreeNode treeNode = tree.get(node);
		TreeNode dTreeNode = dTree.get(node);
		treeNode.data = (treeNode.data * num) % (1009);
		dTreeNode.data=treeNode.data;
		
	}
	private static int query(int k, int w) {
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(k);
		int maxVal= Integer.MIN_VALUE;
		//Set<Integer> isDone = new HashSet<>();
		while(!q.isEmpty()){
			int t = q.poll();
			//isDone.add(t);
			if(null != dTree.get(t).children){
				q.addAll(dTree.get(t).children);
			}	
			maxVal = (dTree.get(t).data ^ w) > maxVal ? (dTree.get(t).data) ^ w : maxVal;
		}
		return maxVal;
	}
	

}
