package Saper.Classes;

import Saper.Controllers.BoardController;
import Saper.Controllers.StartMenuController;
import Saper.Main.Main;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GameLogicTest {

    static Integer[][] zerosArray;
    static Integer[][] elevensArray;
    static BoardController bc;
    static GameLogic logic;
    static Stage stage;


    @BeforeAll
    static void prepare() throws InterruptedException {
        Thread thread = new Thread(() -> {
            new JFXPanel();
            Platform.runLater(() -> {
                try {
                    stage = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader(GameLogicTest.class.getClassLoader().getResource("Fxml/Board.fxml"));

                    BoardController boardController = new BoardController(stage,8);
                    fxmlLoader.setController(boardController);
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setScene(scene);
                    stage.hide();

                    bc = boardController;
                    logic = bc.getLogic();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
        thread.start();
        Thread.sleep(3000);

    }

    @BeforeEach
    void prepareArrays(){
        zerosArray = new Integer[8][8];
        elevensArray = new Integer[8][8];
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                zerosArray[i][j]=0;
                elevensArray[i][j]=11;
            }
        }
    }

    @Test
    void openZeros() throws InterruptedException {

        zerosArray = new Integer[8][8];
        elevensArray = new Integer[8][8];
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                zerosArray[i][j]=0;
                elevensArray[i][j]=11;
            }
        }
        Board board = new Board(zerosArray);
        Board playersBoard = new Board(elevensArray);
        logic.setBoard(board);
        logic.setPlayersBoard(playersBoard);


        bc.leftClickBoardButton(0);
        assertArrayEquals(logic.getPlayersBoard().getArray(),logic.getBoard().getArray(),"Arrays should be equal");
    }

    @Test
    void checkScore() {
        Board board = new Board(zerosArray);
        Board playersBoard = new Board(elevensArray);

        logic.setGameEnded(false);
        logic.setPlayersBoard(playersBoard);
        logic.setBoard(board);

        logic.setMines(64);
        assertTrue(logic.checkScore());

        logic.setMines(63);
        assertFalse(logic.checkScore());

        logic.setMines(1);
        logic.setMinesPlaced(2);
        assertFalse(logic.checkScore());


        logic.setMines(2);
        logic.setMinesPlaced(2);
        board.setNum(0,9);
        board.setNum(1,9);
        playersBoard.setNum(0,10);
        playersBoard.setNum(1,10);
        for(int i=2; i<64;i++){
            playersBoard.setNum(i,0);
        }
        logic.setPlayersBoard(playersBoard);
        logic.setBoard(board);
        assertTrue(logic.checkScore());

        playersBoard.setNum(0,10);
        playersBoard.setNum(1,11);
        for(int i=2; i<64;i++){
            playersBoard.setNum(i,0);
        }
        playersBoard.setNum(2,10);
        logic.setPlayersBoard(playersBoard);
        logic.setBoard(board);
        assertFalse(logic.checkScore());

        playersBoard.setNum(0,10);
        playersBoard.setNum(1,11);
        for(int i=2; i<64;i++){
            playersBoard.setNum(i,0);
        }
        logic.setPlayersBoard(playersBoard);
        logic.setBoard(board);
        assertTrue(logic.checkScore());
    }
}

