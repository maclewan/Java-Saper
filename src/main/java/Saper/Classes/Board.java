package Saper.Classes;

import java.util.Random;

public class Board {
    private int size;

    /**
     * Array that represents board.
     *
     * 0 in array means 0 neighbours
     * 1-8 in array means number of neighboring mines
     * 9 in array means mine
     * 10 in array means flag
     * 11 in array means empty, not defined/opened
     */
    private Integer[][] array;


    /**
     * Constructor to create new board, filled with 11ths
     * @param size Board size
     */
    public Board(int size){
        this.size=size;
        this.array = new Integer[size][size];
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                array[i][j]=11;
            }
        }
    }

    /**
     * Constructor for testing purpose.
     * @param array 8x8 array filled with numbers.
     */
    public Board(Integer[][] array){
        this.array = array;
        this.size = 8;
    }

    /**
     * Method setting mine at given place on board
     * @param x x coordinate on board
     * @param y y coordinate on board
     * @return False if there was already mine, else True
     */
    public boolean setMine(int x, int y){
        if(array[x][y]!=9) {
            array[x][y]=9;
            return true;
        }
        return false;
    }

    /**
     * Method for setting number on (x,y) place in board.
     * @param x x coordinate on board
     * @param y y coordinate on board
     * @param num Number (from 0 to 11) to set
     */
    public void setNum(int x, int y, int num){
        array[x][y]=num;
    }

    /**
     * Method for setting number on board, where x = btnNum%size and y = int(btnNum/size)
     * @param btnNum Coordinates on board to set the number.
     * @param num Number (from 0 to 11) to set
     */
    public void setNum(int btnNum, int num){

        array[btnNum%size][btnNum/size]=num;
    }

    /**
     * Method for placing mines on empty board
     * @param ammount Number of mines to place
     * @return False if there is too much mines to place, else True
     */
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

    /**
     * Method for printing board in terminal
     */
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


    /**
     * This method fills firstly filled with mines board with numbers from 0-8
     * depending on amount of mines surrounding each
     */
    public void refactorMines(){
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                array[i][j]=getNumberOfMinesAround(i,j);
            }
        }
    }


    /**
     * Method returns number of mines which surrounds (i,j) place. If this place is mine, returns 9 (what is mine)
     * @param i x coordinate on board
     * @param j y coordinate on board
     * @return Count of surrounding mines
     */
    public int getNumberOfMinesAround(int i, int j){
        if(array[i][j]==9 || array[i][j]==10){
            return 9;
        }
        int counter=0;

        //todo: Wrap into one for using sinus function
        try{
            if(array[i-1][j]==9 || array[i-1][j]==10)
                counter++;
        } catch (ArrayIndexOutOfBoundsException ignored){}
        try{
            if(array[i+1][j]==9 || array[i+1][j]==10)
                counter++;
        } catch (ArrayIndexOutOfBoundsException ignored){}
        try{
            if(array[i][j+1]==9 || array[i][j+1]==10)
                counter++;
        } catch (ArrayIndexOutOfBoundsException ignored){}
        try{
            if(array[i][j-1]==9 || array[i][j-1]==10)
                counter++;
        } catch (ArrayIndexOutOfBoundsException ignored){}

        try{
            if(array[i-1][j-1]==9 || array[i-1][j-1]==10)
                counter++;
        } catch (ArrayIndexOutOfBoundsException ignored){}
        try{
            if(array[i+1][j+1]==9 || array[i+1][j+1]==10)
                counter++;
        } catch (ArrayIndexOutOfBoundsException ignored){}
        try{
            if(array[i-1][j+1]==9 || array[i-1][j+1]==10)
                counter++;
        } catch (ArrayIndexOutOfBoundsException ignored){}
        try{
            if(array[i+1][j-1]==9 || array[i+1][j-1]==10)
                counter++;
        } catch (ArrayIndexOutOfBoundsException ignored){}

        return counter;
    }


    /**
     * Method for getting number on board on (x,y) place
     * @param x x coordinate
     * @param y y coordinate
     * @return Number on this place on board.
     */
    public int getNum(int x, int y){
        return array[x][y];
    }

    /**
     * Method for getting number on board, where x = btnNum%size and y = int(btnNum/size)
     * @param btnNum Coordinates on board to get the number
     * @return Number on this place on board.
     */
    public int getNum(int btnNum){
        return array[btnNum%size][btnNum/size];
    }

    /**
     * Method checking if there are any mines on board
     * @return False if there are any mine, else True
     */
    public boolean verifyMines(){
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if(array[i][j]==9)
                    return false;
            }
        }
        return true;
    }

    /**
     * Method counts not opened places on the board
     * @return Count of not opened places on the board
     */
    public int getNotOpenedCount(){
        int counter = 0;
        for(Integer i[] : array){
            for(Integer j : i){
                if(j>9)
                    counter++;
            }
        }
        return counter;
    }

    public Integer[][] getArray() {
        return array;
    }
}
