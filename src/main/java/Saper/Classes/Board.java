package Saper.Classes;

import java.util.Random;

public class Board {
    private int size;
    /**
     * 0 in array means empty place
     * 1-8 in array means number of neighboring mines
     * 9 in array means mine
     */
    private Integer[][] array;

    public Board(int size){
        this.size=size;
        this.array = new Integer[size][size];
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                array[i][j]=0;
            }
        }
    }

    public boolean setMine(int x, int y){
        if(array[x][y]!=9) {
            array[x][y]=9;
            return true;
        }
        return false;
    }

    public boolean setMines(int ammount){
        if(ammount>=size*size){
            return false;
        }
        Random random = new Random();
        for(int i=0;i<ammount;i++) {
            int x = random.nextInt(size);
            int y = random.nextInt(size);
            if(array[x][y]==1){
                i--;
                continue;
            }
            else
                array[x][y]=9;
        }
        refactorMines();
        return true;
    }

    public void printBoard(){
        System.out.println("-----------------------");
        for(int i=0;i<size;i++){
            System.out.print("| ");
            for(int j=0;j<size;j++){
                System.out.print(array[j][i]+" ");
            }
            System.out.print("|\n");
        }
        System.out.println("-----------------------");
    }

    public void refactorMines(){

    }
}
