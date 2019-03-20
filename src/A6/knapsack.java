package A6;

import java.util.ArrayList;
import java.util.List;

public class knapsack {

	public static void main(String[] args){
		int[] W = {500,3000,700,900,300,500,1000};
		int[] V = {20,5,3,8,50,78,98};
		int weight = 5000;

        int[][] cache = new int[W.length][weight];
        List result = new ArrayList();
		knapsack test = new knapsack();
        //W物品的重量  V物品的价值  W。length从前i个物品中选择  j剩余背包承重
        System.out.println(test.knapSack(W, V, W.length,weight,result));
        System.out.print(result);
	}

	private int max(int a,int b){
		if(a >= b)
			return a;
		return b;
	}

	public int knapSack(int[] W,int[] V,int i,int j,List result){

	    int[][] cache = new int[i+1][j+1];
	    for(int k=0;k<cache.length;k++){
	        for(int l=0;l<cache[k].length;l++){
	            if(k==0 || l==0){
                    cache[k][l] = 0;
                    continue;
                }
	            if(l >= W[k-1])
                    cache[k][l] = max(cache[k-1][l],cache[k-1][l-W[k-1]]+V[k-1]);
	            else
 	                cache[k][l] = cache[k-1][l];
            }
        }
        //traceback
        int k = i;
        int l = j;
        while (k>0 && l>0){
            if(cache[k][l] > cache[k-1][l]){
	            result.add(k-1);
	            l -= W[k-1];
	            k--;
            }
            else if(cache[k][l] == cache[k-1][l]){
                k--;
            }
        }
	    return cache[i][j];
    }

}
