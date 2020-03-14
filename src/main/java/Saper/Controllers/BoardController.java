package Saper.Controllers;

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
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class BoardController {
    private Stage thisStage = new Stage();
    private int size;
    private int boms;
    private SuperArrayList<ToggleButton> buttonList;


    public BoardController(Stage thisStage, int size){
        this.thisStage=thisStage;
        this.size = size;

    }

    @FXML
    private void initialize(){
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
            buttonList.getLast().setMinWidth(25);
            gamePane.getChildren().add(buttonList.getLast());
            buttonList.getLast().setLayoutX((i%size)*25);
            buttonList.getLast().setLayoutY(i/size*25);

            setButtonImage(buttonList.getLast(),11);










            addButtonsListeners();
            startGame();

        }
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
        resetButtons();
    }

    private void resetButtons() {
        System.out.println(buttonList.getLast().getWidth());
        for(ToggleButton btn : buttonList){
            btn.setSelected(false);
            btn.setDisable(false);
            btn.setText("");
        }
        lblTime.setText("00:00:00");
    }

    private void addButtonsListeners() {
        for(int i=0;i<buttonList.size();i++){
            int finalI = i;
            buttonList.get(i).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(!buttonList.get(finalI).isSelected()){
                        buttonList.get(finalI).setSelected(true);
                    }
                    else{
                        setButtonImage(buttonList.get(finalI),9);

                    }
                   // buttonList.get(finalI).setDisable(true);
                }
            });
        }
    }


    private void startGame(){

    }

    private void setButtonImage(ToggleButton tb, int imageNumber){

        String urlPart = Integer.toString(imageNumber);
        Image image = new Image("/Images/"+urlPart+".png",25,25,false,true,true);

        BackgroundImage bImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(25, 25, true, true, true, false));
        Background backGround = new Background(bImage);
        tb.setBackground(backGround);





    }

}
