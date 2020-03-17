package Saper.Controllers;

import Saper.Classes.Board;
import Saper.Classes.SuperArrayList;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    private boolean isGameEnded =false;
    private Timer timer;


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
        timer = new Timer(this);




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
        stopTimer();
        resetButtons();
        startGame();
        lblTime.setText("00:00");
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
             * Button pressed (left-click)
             */
            buttonList.get(i).setOnAction(event -> {

                clickBoardButton(finalI);


            });

            /**
             * Button right-clicked
             */


            buttonList.get(i).setOnMouseClicked(mouseEvent -> {
                if(!isGameEnded) {
                    if (!isGameStarted) {
                        startTimer();
                    }
                    if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                        if (playersBoard.getNum(finalI) == 11) {
                            playersBoard.setNum(finalI, 10);
                            setButtonImage(buttonList.get(finalI), 10);
                            incrementMines();
                        }
                        else if (playersBoard.getNum(finalI) == 10) {
                            playersBoard.setNum(finalI, 11);
                            setButtonImage(buttonList.get(finalI), 11);
                            decrementMines();
                        }
                    }
                }
            });

        }
    }

    private void clickBoardButton(int finalI){
        if(!isGameEnded) {
            if (!isGameStarted) {
                startTimer();
            }


            if (!buttonList.get(finalI).isSelected()) {
                buttonList.get(finalI).setSelected(true);
                openSafeAreas(finalI);

            }
            else {
                setButtonImage(buttonList.get(finalI), board.getNum(finalI));
                if (board.getNum(finalI) == 0   &&  playersBoard.getNum(finalI)==11) {
                    playersBoard.setNum(finalI, board.getNum(finalI));

                    openZeros(finalI);

                }
                playersBoard.setNum(finalI, board.getNum(finalI));

            }


            if (!playersBoard.verifyMines()) {
                System.err.println("Przegrałeś!");
                stopGame();
            }
        }
    }

    private void openZeros(int finalI){
        int temp;
        int constX;
        int constY;
        constX=(finalI%size);
        constY=(finalI/size);
        ArrayList<Integer> xList=new ArrayList<>();
        ArrayList<Integer> yList=new ArrayList<>();

        xList.add(-1);
        xList.add(1);
        xList.add(0);
        xList.add(0);
        xList.add(-1);
        xList.add(1);
        xList.add(1);
        xList.add(-1);

        yList.add(0);
        yList.add(0);
        yList.add(1);
        yList.add(-1);
        yList.add(-1);
        yList.add(-1);
        yList.add(1);
        yList.add(1);


        for(int i=0;i<xList.size();i++){
            int x=constX+xList.get(i);
            int y=constY+yList.get(i);

            try{
                if(board.getNum(x,y)!=9 && playersBoard.getNum(x,y)==11); {
                    temp= x + size * y;

                    //xD dziwnym trafem to przepuszczało 9-tki
                    if(board.getNum(temp)==9)
                        continue;


                    buttonList.get(temp).setSelected(true);
                    clickBoardButton(temp);
                }
            } catch (ArrayIndexOutOfBoundsException e){}
        }
    }

    private void openSafeAreas(int finalI){
        int x=finalI%size;
        int y=finalI/size;


        if(playersBoard.getNumberOfMinesAround(x,y)==board.getNum(finalI)){
            openZeros(finalI);
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
        //todo: usunac:
        board.printBoard();

        lblTime.setText("00:00");
        minesPlaced=0;
        lblBombsLeft.setText(Integer.toString(mines-minesPlaced));
        isGameEnded =false;
        isGameStarted=false;

    }

    private void stopGame(){
        isGameEnded=true;
    }

    private void setButtonImage(ToggleButton tb, int imageNumber){
        String urlPart = Integer.toString(imageNumber);
        Image image = new Image("/Images/"+urlPart+".png",25,25,false,true,true);
        tb.setGraphic(new ImageView(image));




    }

    private void startTimer(){
        isGameStarted=true;
        timer=new Timer(this);
        timer.start();
    }

    private void stopTimer(){
        timer.interrupt();
    }

    private void incrementMines(){
        minesPlaced++;
        lblBombsLeft.setText(Integer.toString(mines-minesPlaced));
    }

    private void decrementMines(){
        minesPlaced--;
        lblBombsLeft.setText(Integer.toString(mines-minesPlaced));
    }

    private class Timer extends Thread{
        private BoardController bc;
        private LocalTime startTime;
        private LocalTime inGameTime;
        private long gameDuration;
        private long prevGameDuration;

        private Timer(BoardController bc){
            this.bc=bc;
        }

        @Override
        public synchronized void run() {
            startTime=LocalTime.now();
            while(isGameStarted&&!isGameEnded){
                try {
                    Thread.sleep(230);
                } catch (InterruptedException e) {
                }
                gameDuration = Duration.between(startTime,LocalTime.now()).getSeconds();
                if(prevGameDuration==gameDuration){
                    continue;
                }
                prevGameDuration=gameDuration;
                inGameTime= LocalTime.of(0,(int)gameDuration/60,(int)gameDuration%60);

                Platform.runLater(() ->  {
                    lblTime.setText(inGameTime.format(DateTimeFormatter.ofPattern("mm:ss")));
                });

            }


        }
    }

}

