package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

import java.util.ArrayList;

public class StartMenuController {
    private ArrayList<ToggleButton> toggleButtons = new ArrayList<>();

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

    }

}
