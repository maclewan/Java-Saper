package Saper.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

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
    private Button btnLeaderboard;

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
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("Board.fxml"));

            BoardController boardController = new BoardController(stage,size);
            fxmlLoader.setController(boardController);

            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Saper");
            stage.setScene(scene);
            stage.show();
            stage.setResizable(true);

            thisStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnLeaderboardClicked(ActionEvent event) {

    }

}
