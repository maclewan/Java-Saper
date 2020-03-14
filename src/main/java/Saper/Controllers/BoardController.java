package Saper.Controllers;

import Saper.Classes.Board;
import Saper.Classes.SuperArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class BoardController {
    private Stage thisStage = new Stage();
    private int size;
    private int mines;
    private int minesPlaced;
    private SuperArrayList<ToggleButton> buttonList;
    private boolean isGameStarted=false;
    private Board board;
    private Board playersBoard;


    public BoardController(Stage thisStage, int size){
        this.thisStage=thisStage;
        this.size = size;

    }

    @FXML
    private void initialize(){
        if(size==8){
            mines=10;
        }
        else if(size==16){
            mines=40;
        }
        else if(size==24){
            mines=130;
        }

        fldCount.setText(Integer.toString(mines));





        buttonList=new SuperArrayList<>();
        gamePane.setMaxSize(25*size,25*size);
        for(int i=0;i<size*size;i++){

            stagePane.setPrefWidth(860-(24-size)*25);
            stagePane.setPrefHeight(720-(24-size)*25);
            thisStage.setMinWidth(stagePane.getPrefWidth());
            thisStage.setMinHeight(stagePane.getPrefHeight()+30);

            buttonList.add(new ToggleButton());
            buttonList.getLast().setMinHeight(25);
            buttonList.getLast().setMinWidth(25);
            buttonList.getLast().setMaxHeight(25);
            buttonList.getLast().setMaxWidth(25);
            gamePane.getChildren().add(buttonList.getLast());
            buttonList.getLast().setLayoutX((i%size)*25);
            buttonList.getLast().setLayoutY(i/size*25);


        }





        addButtonsListeners();
        resetButtons();
        startGame();
    }


    @FXML
    private TextField fldCount;

    @FXML
    private Label lblBombsLeft;

    @FXML
    private Button btnRestart;

    @FXML
    private Pane gamePane;

    @FXML
    private BorderPane stagePane;

    @FXML
    private Label lblTime;

    @FXML
    void btnRestartClicked(ActionEvent event) {
        isGameStarted=false;
        resetButtons();
        startGame();
    }

    private void resetButtons() {
        for(ToggleButton btn : buttonList){
            btn.setSelected(false);
            setButtonImage(btn,11);
        }
    }

    private void addButtonsListeners() {
        for(int i=0;i<buttonList.size();i++){
            int finalI = i;
            /**
             * Button pressed
             */
            buttonList.get(i).setOnAction(event -> {
                if(!isGameStarted){
                    isGameStarted=true;
                    startTimer();
                }
                if(!buttonList.get(finalI).isSelected()){
                    buttonList.get(finalI).setSelected(true);
                }
                else{
                    //todo: clicking board
                    setButtonImage(buttonList.get(finalI),9);

                    if(playersBoard.getNum(finalI)==11)
                        incrementMines();
                    playersBoard.setNum(finalI,9);
                    //todo: if bomb placed incrementMines()

                }

            });

            /**
             * Button right-clicked
             */
            buttonList.get(i).setOnMouseClicked(mouseEvent -> {
                if(!isGameStarted){
                    isGameStarted=true;
                    startTimer();
                }
                if(mouseEvent.getButton() == MouseButton.SECONDARY ){
                    if(playersBoard.getNum(finalI)== 11) {
                        playersBoard.setNum(finalI,10);
                        setButtonImage(buttonList.get(finalI), 10);
                        incrementMines();
                    }
                    else if(playersBoard.getNum(finalI)== 10){
                        playersBoard.setNum(finalI,11);
                        setButtonImage(buttonList.get(finalI),11);
                        decrementMines();
                    }
                }
            });
        }
    }


    private void startGame(){


        board = new Board(size);
        playersBoard = new Board(size);

        try {
            mines=Integer.parseInt(fldCount.getText());
            if(mines>((size*size)-1))
                throw new NumberFormatException();
            board.setMines(mines);

        }
        catch (NumberFormatException e){
            fldCount.setText("Wrong number");
            return;
        }
        board.printBoard();

        lblTime.setText("00:00:00");
        minesPlaced=0;
        lblBombsLeft.setText(Integer.toString(mines-minesPlaced));

    }

    private void setButtonImage(ToggleButton tb, int imageNumber){

        String urlPart = Integer.toString(imageNumber);
        Image image = new Image("/Images/"+urlPart+".png",25,25,false,true,true);
        tb.setGraphic(new ImageView(image));




    }

    private void startTimer(){
        System.out.println("Starting timer");
    }

    private void incrementMines(){
        minesPlaced++;
        lblBombsLeft.setText(Integer.toString(mines-minesPlaced));
    }

    private void decrementMines(){
        minesPlaced--;
        lblBombsLeft.setText(Integer.toString(mines-minesPlaced));
    }

}
