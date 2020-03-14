package Saper.Main;
import Saper.Classes.Board;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Saper.Controllers.StartMenuController;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("StartMenu.fxml"));

        StartMenuController startMenuController = new StartMenuController(primaryStage);

        fxmlLoader.setController(startMenuController);

        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Saper");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);







    }


    public static void main(String[] args) {
        launch(args);
    }
}
