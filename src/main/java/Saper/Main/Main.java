package Saper.Main;
import Saper.Classes.Board;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Saper.Controllers.StartMenuController;


public class Main extends Application {
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("Fxml/StartMenu.fxml"));

        StartMenuController startMenuController = new StartMenuController(primaryStage);

        fxmlLoader.setController(startMenuController);

        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Saper");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        stage=primaryStage;

    }


    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getStage(){
        return stage;
    }
}
