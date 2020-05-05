package Saper.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;

public class StartMenuController {
    private ArrayList<ToggleButton> toggleButtons = new ArrayList<>();
    private Stage thisStage;

    public StartMenuController(Stage thisStage){
        this.thisStage=thisStage;
    }

    @FXML
    private ToggleButton btn8;

    @FXML
    private ToggleButton btn16;

    @FXML
    private ToggleButton btn24;

    @FXML
    private Button btnStart;

    @FXML
    private Button btnScoreboard;

    @FXML
    private void initialize(){
        toggleButtons.add(btn8);
        toggleButtons.add(btn16);
        toggleButtons.add(btn24);
        btn8.setSelected(true);
        for(int i=0; i<3; i++){
            int finalI = i;
            toggleButtons.get(i).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(toggleButtons.get(finalI).isSelected()){
                        for(int j=0; j<3; j++){
                            if(finalI!=j){
                                toggleButtons.get(j).setSelected(false);
                            }
                        }
                    }
                }
            });
        }
    }

    @FXML
    void btnStartClicked(ActionEvent event) {
        int size;
        if(btn8.isSelected())
            size=8;
        else if(btn16.isSelected())
            size=16;
        else
            size=24;

        try {
            Stage newStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("Fxml/Board.fxml"));

            BoardController boardController = new BoardController(newStage,size);
            fxmlLoader.setController(boardController);

            Scene scene = new Scene(fxmlLoader.load());
            newStage.setTitle("Saper");
            newStage.setScene(scene);
            newStage.show();
            newStage.setResizable(true);

            thisStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnScoreboardClicked(ActionEvent event) {
        try {
            Stage newStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("Fxml/Scoreboard.fxml"));

            ScoreboardController sbc= new ScoreboardController(thisStage,newStage);
            fxmlLoader.setController(sbc);

            Scene scene = new Scene(fxmlLoader.load());
            newStage.setTitle("Wyniki");
            newStage.setScene(scene);
            newStage.setResizable(false);

            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
