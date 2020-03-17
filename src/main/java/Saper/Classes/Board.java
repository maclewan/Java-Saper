package Saper.Classes;

import java.util.Random;

public class Board {
    private int size;
    /**
     * 0 in array means 0 neighbours
     * 1-8 in array means number of neighboring mines
     * 9 in array means mine
     * 10 in array means flag
     * 11 in array means empty, not defined
     */
    private Integer[][] array;

    public Board(int size){
        this.size=size;
        this.array = new Integer[size][size];
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                array[i][j]=11;
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

    public void setNum(int x, int y, int num){
        array[x][y]=num;
    }

    public void setNum(int btnNum, int num){
        array[btnNum%size][btnNum/size]=num;
    }

    public boolean setMines(int ammount){
        if(ammount>=size*size){
            return false;
        }
        Random random = new Random();
        for(int i=0;i<ammount;i++) {
            int x = random.nextInt(size);
            int y = random.nextInt(size);
            if(array[x][y]!=11){
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
        for(int i=0;i<size*2+3;i++){
            System.out.print("-");
        }
        System.out.println();
        for(int i=0;i<size;i++){
            System.out.print("| ");
            for(int j=0;j<size;j++){
                if(array[j][i]==9)
                    System.out.print("* ");
                else if(array[j][i]==0)
                    System.out.print(". ");
                else
                    System.out.print(array[j][i]+" ");
            }
            System.out.print("|\n");
        }
        for(int i=0;i<size*2+3;i++){
            System.out.print("-");
        }
        System.out.println();

    }

    public void refactorMines(){
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if(array[i][j]==9){
                    continue;
                }
                int counter=0;

                try{
                    if(array[i-1][j]==9)
                        counter++;
                } catch (ArrayIndexOutOfBoundsException e){}
                try{
                    if(array[i+1][j]==9)
                        counter++;
                } catch (ArrayIndexOutOfBoundsException e){}
                try{
                    if(array[i][j+1]==9)
                        counter++;
                } catch (ArrayIndexOutOfBoundsException e){}
                try{
                    if(array[i][j-1]==9)
                        counter++;
                } catch (ArrayIndexOutOfBoundsException e){}

                try{
                    if(array[i-1][j-1]==9)
                        counter++;
                } catch (ArrayIndexOutOfBoundsException e){}
                try{
                    if(array[i+1][j+1]==9)
                        counter++;
                } catch (ArrayIndexOutOfBoundsException e){}
                try{
                    if(array[i-1][j+1]==9)
                        counter++;
                } catch (ArrayIndexOutOfBoundsException e){}
                try{
                    if(array[i+1][j-1]==9)
                        counter++;
                } catch (ArrayIndexOutOfBoundsException e){}

                array[i][j]=counter;

            }

        }
    }

    public int getNum(int x, int y){
        return array[x][y];
    }

    public int getNum(int btnNum){
        return array[btnNum%size][btnNum/size];
    }

    public boolean verifyMines(){
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if(array[i][j]==9)
                    return false;
            }
        }
        return true;
    }
}
