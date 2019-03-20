package A1;

public class BinomialTheorem {

    private int binomial[][] = new int[8][5];

    public static void main(String[] args){

        //int[] C = {0,5,3,1,6,2,8};
        BinomialTheorem test = new BinomialTheorem();

        System.out.println(test.binomial(4,7));
        System.out.println(test.binomial_iter(4,7));
        System.out.println(test.binomial_mome(4,7));

    }

    /*public void binomialTheorem(int[] C,int k,int n,String temp,int count){

        if(count == k){
            System.out.println(temp);
            return;
        }
        if(n == -1)
            return;
        binomialTheorem(C,k,n-1,temp+C[n],count+1);
        binomialTheorem(C,k,n-1,temp,count);
    }*/

    //递归
    public int binomial(int k,int n){

        if(k==0 || k==n){
            return 1;
        }
        return binomial(k-1,n-1)+binomial(k,n-1);
    }


    //备忘录
    public int binomial_mome(int k,int n){

        int value;

        if(k==0 || k==n){

            value = 1;

        }else if(binomial[n][k] != 0){//判断是否已经运算过。

            value = binomial[n][k];

        }else{

            value = binomial_mome(k-1,n-1)+binomial_mome(k,n-1);
        }

        binomial[n][k] = value;
        return binomial[n][k];
    }


    //迭代
    public int binomial_iter(int k,int n){

        int cache[][] = new int[n+1][k+1];
        for(int i=0;i<=n;i++){

            for(int j=0;j<=min(i,k);j++){

                if(j == 0 || j == i)
                    cache[i][j] = 1;

                else
                    cache[i][j] = cache[i-1][j-1]+cache[i-1][j];
            }
        }
        return cache[n][k];
    }


    private int min(int a,int b){
        if(a>b)
            return b;
        return a;
    }


}
