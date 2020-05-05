package Saper.Controllers;

import Saper.Classes.Score;
import Saper.Classes.Scoreboard;
import Saper.Classes.SuperArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;

import static Saper.Classes.Scoreboard.JSONArrToArrList;
import static Saper.Classes.Scoreboard.readJSONFile;

public class ScoreboardController {
    private Stage lockedStage;
    private Stage thisStage;
    private SuperArrayList<Score> scores8;
    private SuperArrayList<Score> scores16;
    private SuperArrayList<Score> scores24;
    private SuperArrayList<Score> scoresCustom;

    private ObservableList<String> obsScores8;
    private ObservableList<String> obsScores16;
    private ObservableList<String> obsScores24;
    private ObservableList<String> obsScoresCustom;


    public ScoreboardController(Stage lockedStage,Stage thisStage){
        this.lockedStage=lockedStage;
        this.thisStage=thisStage;
    }

    @FXML
    private void initialize(){
        SuperArrayList<Score> scores = JSONArrToArrList(readJSONFile());
        filterScores(scores);
        setObservableLists();

    }



    @FXML
    private ListView<String> listView8;

    @FXML
    private ListView<String> listView16;

    @FXML
    private ListView<String> listView24;

    @FXML
    private ListView<String> listViewCustom;

    private void filterScores(SuperArrayList<Score> scores){
        scores8 = new SuperArrayList<>();
        scores16 = new SuperArrayList<>();
        scores24 = new SuperArrayList<>();
        scoresCustom = new SuperArrayList<>();

        for(Score s : scores){
            if(s.getSize()==8 && s.getMines()==10)
                scores8.add(s);
            else if(s.getSize()==16 && s.getMines()==40)
                scores16.add(s);
            else if(s.getSize()==24 && s.getMines()==130)
                scores24.add(s);
            else
                scoresCustom.add(s);
        }
        Collections.sort(scores8);
        Collections.sort(scores16);
        Collections.sort(scores24);
        Collections.sort(scoresCustom);
    }


    private void setObservableLists(){
        obsScores8 = FXCollections.observableArrayList(scores8.toArrayString());
        obsScores16 = FXCollections.observableArrayList(scores16.toArrayString());
        obsScores24 = FXCollections.observableArrayList(scores24.toArrayString());
        obsScoresCustom = FXCollections.observableArrayList(scoresCustom.toArrayString());

        listView8.setItems(obsScores8);
        listView16.setItems(obsScores16);
        listView24.setItems(obsScores24);
        listViewCustom.setItems(obsScoresCustom);


    }

}
