package Saper.Controllers;

import Saper.Classes.GameLogic;
import Saper.Classes.SuperArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;


public class BoardController {
    private GameLogic logic;
    private Stage thisStage;
    private SuperArrayList<ToggleButton> buttonList;


    public BoardController(Stage thisStage, int size){
        logic = new GameLogic(this);
        this.thisStage=thisStage;
        logic.setSize(size);
    }

    @FXML
    private void initialize(){
        if(logic.getSize()==8){
            logic.setMines(10);
        }
        else if(logic.getSize()==16){
            logic.setMines(40);
        }
        else if(logic.getSize()==24){
            logic.setMines(130);
        }

        fldCount.setText(Integer.toString(logic.getMines()));





        buttonList=new SuperArrayList<>();
        gamePane.setMaxSize(25*logic.getSize(),25*logic.getSize());
        for(int i=0;i<logic.getSize()*logic.getSize();i++){

            stagePane.setPrefWidth(860-(24-logic.getSize())*25);
            stagePane.setPrefHeight(720-(24-logic.getSize())*25);
            thisStage.setMinWidth(stagePane.getPrefWidth());
            thisStage.setMinHeight(stagePane.getPrefHeight()+30);

            buttonList.add(new ToggleButton());
            buttonList.getLast().setMinHeight(25);
            buttonList.getLast().setMinWidth(25);
            buttonList.getLast().setMaxHeight(25);
            buttonList.getLast().setMaxWidth(25);
            gamePane.getChildren().add(buttonList.getLast());
            buttonList.getLast().setLayoutX((i%logic.getSize())*25);
            buttonList.getLast().setLayoutY(i/logic.getSize()*25);
        }

        {
            logic.getXList().add(-1);
            logic.getXList().add(1);
            logic.getXList().add(0);
            logic.getXList().add(0);
            logic.getXList().add(-1);
            logic.getXList().add(1);
            logic.getXList().add(1);
            logic.getXList().add(-1);

            logic.getYList().add(0);
            logic.getYList().add(0);
            logic.getYList().add(1);
            logic.getYList().add(-1);
            logic.getYList().add(-1);
            logic.getYList().add(-1);
            logic.getYList().add(1);
            logic.getYList().add(1);
        }

        addButtonsListeners();
        resetButtons();
        logic.startGame();
    }

    @FXML
    private TextField fldCount;

    @FXML
    private Label lblBombsLeft;

    @FXML
    private Button btnRestart;

    @FXML
    private Button btnMenu;

    @FXML
    private Pane gamePane;

    @FXML
    private BorderPane stagePane;

    @FXML
    private Label lblTime;

    @FXML
    void btnRestartClicked(ActionEvent event) {
        logic.stopTimer();
        logic.startGame();
        resetButtons();
        lblTime.setText("00:00");
        btnRestart.setFont(Font.font(null, FontWeight.NORMAL,13));
    }

    @FXML
    void btnMenuClicked(ActionEvent event){
        logic.stopGame();

        try {
            Stage newStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("Fxml/StartMenu.fxml"));

            StartMenuController smc = new StartMenuController(newStage);
            fxmlLoader.setController(smc);

            Scene scene = new Scene(fxmlLoader.load());
            newStage.setTitle("Saper");
            newStage.setScene(scene);
            newStage.show();
            newStage.setResizable(false);

            thisStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addButtonsListeners() {
        for(int i=0;i<buttonList.size();i++){
            int finalI = i;
            /**
             * Button pressed (left-click)
             */
            buttonList.get(i).setOnAction(event -> {

                leftClickBoardButton(finalI);
                logic.checkIfGameWon();

            });

            /**
             * Button right-clicked
             */
            buttonList.get(i).setOnMouseClicked(mouseEvent -> {
                if(mouseEvent.getButton() == MouseButton.SECONDARY) {
                    rightClickBoardButton(finalI);
                    logic.checkIfGameWon();
                }

            });

        }
    }

    private void resetButtons() {
        for(ToggleButton btn : buttonList){
            btn.setSelected(false);
            setButtonImage(btn,11);
        }
    }

    public void leftClickBoardButton(int finalI){
        if(logic.getIsGameEnded())
            return;

        if (!buttonList.get(finalI).isSelected()) {
            buttonList.get(finalI).setSelected(true);
            logic.openSafeAreas(finalI);

        }
        else {
            setButtonImage(buttonList.get(finalI), logic.getBoard().getNum(finalI));
            if (logic.getBoard().getNum(finalI) == 0   &&  logic.getPlayersBoard().getNum(finalI)==11) {
                logic.getPlayersBoard().setNum(finalI, logic.getBoard().getNum(finalI));

                logic.openZeros(finalI);

            }
            logic.getPlayersBoard().setNum(finalI, logic.getBoard().getNum(finalI));

        }


        if (!logic.getPlayersBoard().verifyMines()&!logic.getIsGameEnded()) {
            logic.gameLoosed();
            btnRestart.setFont(Font.font(null, FontWeight.BOLD,13));
        }

        if (!logic.getIsGameStarted()) {
            logic.startTimer();
        }

    }

    public void rightClickBoardButton(int finalI){
        if(!logic.getIsGameEnded()) {
            if (!logic.getIsGameStarted()) {
                logic.startTimer();
            }

            if (logic.getPlayersBoard().getNum(finalI) == 11) {
                logic.getPlayersBoard().setNum(finalI, 10);
                setButtonImage(buttonList.get(finalI), 10);
                logic.incrementMines();
            }
            else if (logic.getPlayersBoard().getNum(finalI) == 10) {
                logic.getPlayersBoard().setNum(finalI, 11);
                setButtonImage(buttonList.get(finalI), 11);
                logic.decrementMines();
            }

        }
    }

    public void showAllTiles(){
        for(int i=0;i<logic.getSize();i++){
            for(int j=0;j<logic.getSize();j++){
                if(logic.getPlayersBoard().getNum(j,i)==10 && logic.getBoard().getNum(j,i)!=9){
                    ToggleButton tempButton = buttonList.get(i*8 +j);
                    tempButton.setSelected(true);
                    setButtonImage(tempButton,12);
                }
                else if(logic.getPlayersBoard().getNum(j,i)==11){
                    ToggleButton tempButton = buttonList.get(i*8 +j);
                    tempButton.setSelected(true);
                    setButtonImage(tempButton,logic.getBoard().getNum(j,i));
                }
            }
        }
    }

    private void setButtonImage(ToggleButton tb, int imageNumber){
        String urlPart = Integer.toString(imageNumber);
        Image image = new Image("/Images/"+urlPart+".png",25,25,false,true,true);
        tb.setGraphic(new ImageView(image));
    }

    public void setLblBombsLeftText(String str) {
        lblBombsLeft.setText(str);
    }

    public SuperArrayList<ToggleButton> getButtonList() {
        return buttonList;
    }

    public String getFldCountText() {
        return fldCount.getText();
    }

    public void setFldCountText(String str) {
        fldCount.setText(str);
    }

    public void setLblTimeText(String str) {
        lblTime.setText(str);
    }
}

