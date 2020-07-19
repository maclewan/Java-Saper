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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GameLogicTest {

    static Integer[][] zerosArray;
    static Integer[][] elevensArray;
    static BoardController bc;
    static GameLogic logic;
    static Stage stage;


    @Test
    public void testA() throws InterruptedException {

    }


    @BeforeAll
    static void prepare() throws InterruptedException {
        Thread thread = new Thread(() -> {
            new JFXPanel();
            Platform.runLater(() -> {
                try {
                    /**Jakby to wsadzić do beforeall?*/
                    stage = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader(GameLogicTest.class.getClassLoader().getResource("Fxml/Board.fxml"));

                    BoardController boardController = new BoardController(stage,8);
                    fxmlLoader.setController(boardController);
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setScene(scene);
                    stage.hide();

                    bc = boardController;
                    logic = bc.getLogic();

                    /**Jakby to wyjąc?*/

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
        thread.start();
        Thread.sleep(3000);

    }


    @Test
    void openZerosTest() throws InterruptedException {

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
        assertArrayEquals(logic.getPlayersBoard().getArray(),logic.getBoard().getArray());

    }

    @Test
    void openSafeAreas() {
    }

    @Test
    void checkIfGameWon() {
    }
}

