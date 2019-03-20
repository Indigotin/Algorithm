package A4;



import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;

public class PointGameOf24 {

    private String[] str = {"+","-","*","/"};
    private String[] ch = {"(", ")"};
    private int count = 0;
    //用数组标识括号可能出现的位置
    private int[][] index1 = {{0, 4}, {2, 6},  {4, 8}, {0, 6}, {2, 8}};
    private int[][][] index2 = {
            {{0, 6}, {1, 5}},
            {{0, 6}, {3, 7}},
            {{2, 8}, {3, 7}},
            {{2, 8}, {5, 9}},
            {{0, 4}, {6, 10}}};
    private List<String> result = new ArrayList<>();

    private int[][] num = new int[24][4];
    private String[][] chars = new String[64][3];


    public List<String> getResult(int[] array) throws ScriptException {

        //初始化
        count = 0;
        result.clear();
        List<List<String>> sentence = new ArrayList<>();

        //得到数字全排列结果
        getNumArrays(array, 0, array.length - 1);

        //得到运算符号的排列结果
        getChars();

        //在表达式中插入括号
        insertCh(sentence);

        //计算表达式结果是否为24
        isCorrect();

        return result;
    }



    //全排列
    public void getNumArrays(int[] array,int begin,int end){

        if(end == begin){         //一到递归的出口就输出数组，此数组为全排列
            //sentence[count] = "";
            for(int i=0;i<=end;i++){
                num[count][i] = array[i];
                //System.out.print(array[i]+" ");
            }
            count++;
            //System.out.println();
            return;
        }
        else{
            for(int j=begin;j<=end;j++){
                swap(array,begin,j);      //for循环将begin~end中的每个数放到begin位置中去
                getNumArrays(array,begin+1,end);  //假设begin位置确定，那么对begin+1~end中的数继续递归
                swap(array,begin,j);      //换过去后再还原
            }
        }
    }

    private  void swap(int[] array,int i1, int i2) {
        int temp = array[i2];
        array[i2] = array[i1];
        array[i1] = temp;
    }

    public void getChars(){

        int count1 = 0;
        for(int i=0;i<str.length;i++){
            for(int j=0;j<str.length;j++){
                for(int k=0;k<str.length;k++){
                    int index = 0;
                    chars[count1][index++] = str[i];
                    chars[count1][index++] = str[j];
                    chars[count1++][index] = str[k];
                }
            }
        }
    }

    public List<String> insertOneElement(List<String> array, int insertIndex, String element) {

        List<String> temp = new ArrayList<>();

        for (int i = 0; i < insertIndex; i++) {
            temp.add(array.get(i));
        }
        temp.add(element);
        for (int i = insertIndex; i < array.size(); i++) {
            temp.add(array.get(i));
        }
        return temp;
    }

    public void insertCh(List<List<String>> sentence){

        for(int i=0;i<num.length;i++){
            for(int j=0;j<chars.length;j++){
                saveSentence(num[i],chars[j],sentence);
            }
        }

        //插入括号
        for(int i=0;i<sentence.size();i++){

            for(int k=0;k<index1.length;k++){
                List<String> array = sentence.get(i);
                array = insertOneElement(array, index1[k][0], ch[0]);
                array = insertOneElement(array, index1[k][1], ch[1]);
                result.add(turnString(array));
            }

            for(int k=0;k<index2.length;k++){
                List<String> array = sentence.get(i);
                for(int l=0;l<index2[k].length;l++){
                    array = insertOneElement(array, index2[k][l][0], ch[0]);
                    array = insertOneElement(array, index2[k][l][1], ch[1]);
                }
                result.add(turnString(array));
            }
        }
    }

    private void saveSentence(int[] num,String[] chars,List<List<String>> sentence){

        List<String> temp = new ArrayList<>();
        for(int i=0;i<num.length;i++){

            if(i==0){
                temp.add(String.valueOf(num[i]));
                continue;
            }
            temp.add(chars[i-1]);
            temp.add(String.valueOf(num[i]));
        }
        sentence.add(temp);
    }

    private String turnString(List<String> list){
        String temp = new String();
        for(int i=0;i<list.size();i++){
            temp += list.get(i);
        }
        return temp;
    }

    private void isCorrect() throws ScriptException {

        for(int i=0;i<result.size();i++){

            ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
            if(!jse.eval(result.get(i)).equals(24)) {
                result.remove(i);
                i--;
            }
            else{
                //System.out.println(result.get(i));
            }
        }
    }

}
