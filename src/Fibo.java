import java.util.*;

public class Fibo {
    static ArrayList<Long> ff = new ArrayList<Long>();
    static ArrayList<Integer> fibos = new ArrayList<Integer>(Arrays.asList(0,1,2,3,5,8,13,21,34));
    static long solve(long n){
       if(n>0 && n<=1110)
    	   return n;
       long index=n-1110;    
       long chkNumber = 1+ff.get(ff.size()-1);
       while(index>ff.size()-1){
    	   if(isFiboFriendly(chkNumber)){
    		   ff.add(chkNumber);
    	   }
    	   chkNumber++;    	   
       }
       return ff.get((int) index);
    }

    private static boolean isFiboFriendly(long chkNumber) {
		int[] freqTable = new int[10];
		while(chkNumber>0){
			int dgt = (int) (chkNumber%10);
			freqTable[dgt]++;
			chkNumber/=10;
		}
		for(int frequency : freqTable){
			if(!fibos.contains(frequency))
				return false;
		}
		return true;
	}

	public static void main(String[] args) {
		printNonFF();
//    	Scanner in = new Scanner(System.in);
//        int q = in.nextInt();
//        ArrayList<Long> h = new ArrayList<Long>(Arrays.asList(1111L,2222L,3333L,4444L,5555L,6666L,7777L,8888L,9999L));
//        for(long i=1110;i< 10000;i++){
//        	if(!h.contains(i))
//        		ff.add(i);
//        }
//        for(int a0 = 0; a0 < q; a0++){
//            long n = in.nextLong();
//            long result = solve(n);
//            System.out.println(result);
//        }
    }
	public static void printNonFF(){
		for(long i=1111;i<= 111111;i++){
			if(!isFiboFriendly(i)){
				System.out.println(i);
			}
		}
	}
}
