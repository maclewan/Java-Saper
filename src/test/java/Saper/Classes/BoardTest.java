package Saper.Classes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    static Integer[][] array;
    static Board board;


    @BeforeEach
    void prepareArrays(){
        array = new Integer[8][8];
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                array[i][j]=11;
            }
        }
        board = new Board(array);
    }



    @Test
    void setMine() {
        board.setMine(3,3);
        assertEquals(array[3][3],9);
        assertFalse(board.setMine(3,3));
    }

    @Test
    void setNum() {
        board.setNum(2,2,2);
        board.setNum(4,4);
        assertEquals(array[2][2],2);
        assertEquals(array[4][0],4);
    }


    @Test
    void setMines() {
        assertFalse(board.setMines(65));
        assertTrue(board.setMines(63));

        int counter=0;
        for(var i=0;i<8;i++){
            for(var j=0;j<8;j++){
                if(array[i][j]==9)
                    counter++;
            }
        }
        assertEquals(63,counter);
    }

    @Test
    void getNumberOfMinesAround() {
    }

    @Test
    void getNum() {
    }

    @Test
    void testGetNum() {
    }

    @Test
    void verifyMines() {
    }

    @Test
    void getNotOpenedCount() {
    }
}