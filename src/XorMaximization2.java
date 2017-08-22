import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

class TreeNode2{
	int id;
	int data;
	ArrayList<Integer> in = null;
	ArrayList<Integer> out = null;
	public TreeNode2(int id , int data){
		this.id=id;
		this.data = data;
	}
}

public class XorMaximization2 {
	static HashMap<Integer,TreeNode2> tree = new HashMap<Integer,TreeNode2>();

	static HashMap<String,HashSet<Integer>> pathsRepo = new HashMap<>();
	
	public static void addEdge(int from , int to){
		TreeNode2 fromNode = tree.get(from);
		TreeNode2 toNode = tree.get(to);
		if(null == fromNode.out)
			fromNode.out=new ArrayList<Integer>();
		fromNode.out.add(to);
		if(null == toNode.in)
			toNode.in=new ArrayList<Integer>();
		toNode.in.add(from);

	}
	public static void bfs(int root){
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(root);
		while(!q.isEmpty()){
			int t = q.poll();
			q.addAll(tree.get(t).out);
			System.out.print(" "+tree.get(t).data);
		}
		
	}
    private static Stack<Integer> path  = new Stack<Integer>();   // the current path
    private static ArrayList<Integer> onPath  = new ArrayList<Integer>();     // the set of vertices on the path

    public static HashSet<Integer> findAllPaths(HashMap<Integer,TreeNode2> g, int s, int t) {
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
    private static void dfs(HashMap<Integer,TreeNode2>  g, int s, int t, ArrayList<ArrayList<Integer>> paths) {

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
        	if(g.get(s).out != null){
	            for (int id : g.get(s).out) {
	                if (!onPath.contains(id)) 
	                	dfs(g, id, t,paths);
	            }
        	}
        	if(g.get(s).in != null){
	            for (int id : g.get(s).in) {
	                if (!onPath.contains(id)) 
	                	dfs(g, id, t,paths);
	            }
        	}
        }

        // done exploring from v, so remove from path
        path.pop();
        onPath.remove(onPath.indexOf(s));
    }
	public static void divide(int src , int dest , int num){
		HashSet<Integer> nodesInPath = (null == pathsRepo.get(src+","+dest)) ? findAllPaths(tree, src, dest) : pathsRepo.get(src+","+dest);
		if((null == pathsRepo.get(src+","+dest)))
			pathsRepo.put(src+","+dest, nodesInPath);
		for(int node : nodesInPath){
			TreeNode2 treeNode = tree.get(node);
			//TreeNode2 dTreeNode = dTree.get(node);
			treeNode.data = (int) Math.ceil((double)treeNode.data / num);
			//dTreeNode.data=treeNode.data;
		}
	}
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int q = scan.nextInt();
		for(int i=0;i<n;i++){
			int val=scan.nextInt();
			tree.put(i+1, new TreeNode2(i+1,val));
			//dTree.put(i+1, new TreeNode2(i+1,val));
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
		TreeNode2 treeNode = tree.get(node);
		//TreeNode2 dTreeNode = dTree.get(node);
		treeNode.data = (treeNode.data * num) % (1009);
		//dTreeNode.data=treeNode.data;
		
	}
	private static int query(int k, int w) {
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(k);
		int maxVal= Integer.MIN_VALUE;
		//Set<Integer> isDone = new HashSet<>();
		while(!q.isEmpty()){
			int t = q.poll();
			//isDone.add(t);
			if(null != tree.get(t).out){
				q.addAll(tree.get(t).out);
			}	
			maxVal = (tree.get(t).data ^ w) > maxVal ? (tree.get(t).data) ^ w : maxVal;
		}
		return maxVal;
	}
	

}
