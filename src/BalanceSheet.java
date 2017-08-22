import java.io.*;
import java.util.*;
public class BalanceSheet {

    static String solve(int n, int m, int[] d, int[] c) {

        int minOps=0;
        int creditSum = 0;
        int debitSum=0;
        
        for(int debitAmount : d)
        	debitSum+=debitAmount;
        
        for(int creditAmount : c)
        	creditSum+=creditAmount;
        
    	int b[] = debitSum > creditSum ? d : c;
    	int s[] = debitSum < creditSum ? d : c;
    	
    	
        ArrayList<Integer> bigList = new ArrayList<Integer>();
        int bSum=0;
        for(int ele : b){
        	bigList.add(ele);
        	bSum+=ele;
        }
        
        ArrayList<Integer> smallList = new ArrayList<Integer>();
        int sSum=0;
        for(int ele : s){
        	smallList.add(ele);
        	sSum+=ele;
        }
        
        //int diff = bSum - sSum;
        
        int mid = ( bSum + sSum ) / 2;
        int carry = ( bSum + sSum )%2 ==0 ? 0 :1;
        int extra = bSum-mid;      
        int diff = 2*extra;
        
   		bigList.sort(new Comparator<Integer>() {

				@Override
				public int compare(Integer o1, Integer o2) {
					// TODO Auto-generated method stub
					return o2-o1;
				}
			});
   		
   		
        while(extra>0){
        	if(bigList.contains(extra)){
        		diff=0;
        		minOps++;
        		extra=0;
        		break;
        	}
        	else if (comboExists(extra,bigList,smallList)){
        		diff=0;
        		minOps+=2;
        		extra=0;
        		break;
        	}
        	else{
 
        		int index=0;
        		while(index<bigList.size()){
        			if(bigList.get(index) < extra)
        				break;
        			index++;
        		}
        		if(index<bigList.size()){
	        		extra-=bigList.get(index);
	        		smallList.add(bigList.remove(index));
	        		diff=2*extra;
	        		minOps++;
        		}
        		else
        			break;
        		
        	}
        }      
        diff+=carry;
        return diff+" "+minOps;
    }

    private static boolean comboExists(int extra, ArrayList<Integer> bigList, ArrayList<Integer> smallList) {
		HashSet<Integer> h = new HashSet<Integer>(bigList);
		for(int ele:smallList){
			if(h.contains(Math.abs(extra+ele)) || h.contains(Math.abs(ele-extra))){
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int[] d = new int[n];
        for(int d_i = 0; d_i < n; d_i++){
            d[d_i] = in.nextInt();
        }
        int[] c = new int[n];
        for(int c_i = 0; c_i < m; c_i++){
            c[c_i] = in.nextInt();
        }
        String result = solve(n, m, d, c);
        System.out.println(result);
        in.close();
    }
}
