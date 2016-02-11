import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JLabel;

public class Tryb1 extends Thread{
    int[][] tablicaInt;
    int k = 1;
    ArrayList<ArrayList<int[]>> tab = new ArrayList<ArrayList<int[]>>();
    ArrayList<int[]> przenum = new ArrayList<>();
    ArrayList<Integer> wierszfirst = new ArrayList<>();
    ArrayList<Integer> wierszlast = new ArrayList<>();
    JLabel[][] tablicaJLabel;

    public Tryb1( JLabel[][] tablicaJLabel){
    	this.tablicaJLabel = tablicaJLabel;
    }
    public void calculate(Boolean[][] tablicaKomorek){
            tablicaInt = new int[tablicaKomorek.length][tablicaKomorek.length];

            for(int i = tablicaKomorek.length-1;i>=0;i--){
                    for(int j = 0;j<tablicaKomorek[i].length;j++){
                            int[] ij = {i,j};
                            Boolean komorkaBl = tablicaKomorek[i][j];
                            Boolean lewyBl = null,dolnyBl = null;
                            int dolnyInt = 0, lewyInt = 0;
                            if(j-1 >= 0){
                                    lewyBl = tablicaKomorek[i][j-1];
                                    lewyInt = tablicaInt[i][j-1];
                            }
                            if(i < tablicaKomorek.length-1) {
                                    dolnyBl = tablicaKomorek[i+1][j];
                                    dolnyInt = tablicaInt[i+1][j];
                            }			

                            if(komorkaBl == true){
                                    if(dolnyBl == null){
                                            if(lewyBl == null || lewyBl == false){//1
                                                    tablicaInt[i][j] = k;
                                                    tab.add(new ArrayList<int[]>());
                                                    tab.get(k-1).add(ij);
                                                    k++;
                                            }
                                            else{//2
                                                    tablicaInt[i][j] = lewyInt;
                                                    tab.get(lewyInt-1).add(ij);
                                            }
                                    }
                                    else{
                                            if(lewyBl == null || lewyBl == false){
                                                    if(dolnyBl == false){//1
                                                            tablicaInt[i][j] = k;
                                                            tab.add(new ArrayList<int[]>());
                                                            tab.get(k-1).add(ij);
                                                            k++;
                                                    }
                                                    else if(dolnyBl ==true){//2
                                                            tablicaInt[i][j] = dolnyInt;
                                                            tab.get(dolnyInt-1).add(ij);
                                                    }
                                            }
                                            else if(lewyBl == true && dolnyBl == true){//3 and 2
                                                    if(lewyInt == dolnyInt){//if both have similar number
                                                            tablicaInt[i][j] = lewyInt;
                                                            tab.get(lewyInt-1).add(ij);
                                                    }
                                                    else if(lewyInt <= dolnyInt){//if both have different numbers
                                                            tablicaInt[i][j] = lewyInt;
                                                            tab.get(lewyInt-1).add(ij);
                                                            int[] przen = {dolnyInt, lewyInt};
                                                            przenum.add(przen);
                                                            }
                                                    else if(lewyInt >= dolnyInt){
                                                            tablicaInt[i][j] = dolnyInt;
                                                            tab.get(dolnyInt-1).add(ij);
                                                            int[] przen = {lewyInt, dolnyInt};
                                                            przenum.add(przen);
                                                            }
                                            }

                                            else if(lewyBl == true && dolnyBl == false){//2
                                                    tablicaInt[i][j] = lewyInt;
                                                    tab.get(lewyInt-1).add(ij);
                                            }
                                    }
                                    if(i == 0) wierszfirst.add(tablicaInt[i][j]);
                                    if(i == tablicaKomorek.length-1) wierszlast.add(tablicaInt[i][j]);
                            }
                            else{//komorka jest zdrowa
                                    tablicaInt[i][j] = 0;
                            }


                    }
            }
	
            for(int k = 0;k<2;k++){
                    Set<Integer> hs = new HashSet<>();
                    hs.addAll(wierszlast);
                    wierszlast.clear();
                    wierszlast.addAll(hs);
            }

            for(int k = 0;k<2;k++){
                    Set<Integer> hs = new HashSet<>();
                    hs.addAll(wierszfirst);
                    wierszfirst.clear();
                    wierszfirst.addAll(hs);
            }

            for(int k = 0;k<2;k++){
                    for(int i = 0;i<przenum.size();i++){
                            for(int j = 1;j<przenum.size();j++){
                                            if(Arrays.equals(przenum.get(j), przenum.get(j-1))){
                                                    przenum.remove(j);
                                            }						
                            }
                    }
            }

 
            for(int m = przenum.size()-1;m>=0;m--){
                    int i = przenum.get(m)[0];
                    int j = przenum.get(m)[1];
                    for(int r = 0;r<przenum.size();r++){
                            if(i == przenum.get(r)[0]){
                                    przenum.get(r)[0] = j;

                            }
                            if(i == przenum.get(r)[1]){
                                    przenum.get(r)[1] = j;
                            }
                    }
                    for(int r = 0;r<wierszfirst.size();r++){
                            if(wierszfirst.get(r)==i){
                                    wierszfirst.remove(r);
                                    wierszfirst.add(j);
                                    break;
                            }
                    }
                    for(int r = 0;r<wierszlast.size();r++){
                            if(wierszlast.get(r)==i){
                                    wierszlast.remove(r);
                                    wierszlast.add(j);
                                    break;
                            }
                    }
                            if(i!=j){
                                    ArrayList<int[]> temp = tab.get(i-1);
                            tab.get(j-1).addAll(temp);
                            tab.get(i-1).clear();
                            }

                    przenum.remove(m);

            }
            for(int i = 0;i<tab.size();i++){
                for(int j = 0;j<tab.get(i).size();j++){
                    int x = tab.get(i).get(j)[0];
                    int y = tab.get(i).get(j)[1];
                    tablicaInt[x][y] = i+1;
                }
            }
            for(int k = 0;k<2;k++){
                Set<Integer> hs = new HashSet<>();
                hs.addAll(wierszlast);
                wierszlast.clear();
                wierszlast.addAll(hs);
            }

            for(int k = 0;k<2;k++){
                Set<Integer> hs = new HashSet<>();
                hs.addAll(wierszfirst);
                wierszfirst.clear();
                wierszfirst.addAll(hs);
            }
            


    }
    public boolean findPercolation(){
    	int temp = 0;
    	for(int i = 0;i<wierszfirst.size();i++)
            for(int j = 0;j<wierszlast.size();j++)
                if(wierszfirst.get(i) == wierszlast.get(j)){
                    makePercolationVisible(tab.get(wierszfirst.get(i)-1));
                    temp++;
                }
    	if (temp >= 0)
    		return true;   	
    	return false;
    	
            
    }
    
    public void makePercolationVisible(ArrayList<int[]> list){
        for(int i = 0;i<list.size();i++){
            int x = list.get(i)[0];
            int y = list.get(i)[1];
            tablicaJLabel[x][y].setBackground(Color.CYAN);
        }
    }

}
