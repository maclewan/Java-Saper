package Saper.Classes;

import Saper.Controllers.BoardController;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

public class GameLogic {
    private BoardController bc;
    private int size;
    private int mines;
    private int minesPlaced;
    private boolean isGameStarted=false;
    private boolean isGameEnded =false;
    private Board board;
    private Board playersBoard;
    private GameLogic.Timer timer;
    private LocalTime gameTime;

    private final ArrayList<Integer> xList=new ArrayList<>();
    private final ArrayList<Integer> yList=new ArrayList<>();

    public GameLogic(BoardController bc) {
        this.bc = bc;
        timer = new Timer(this);
    }


    public void incrementMines(){
        minesPlaced++;
        bc.setLblBombsLeftText(Integer.toString(mines-minesPlaced));
    }

    public void decrementMines(){
        minesPlaced--;
        bc.setLblBombsLeftText(Integer.toString(mines-minesPlaced));
    }

    public void openZeros(int finalI){
        int temp;
        int constX;
        int constY;
        constX=(finalI%size);
        constY=(finalI/size);


        for(int i=0;i<xList.size();i++){
            int x=constX+xList.get(i);
            int y=constY+yList.get(i);

            try{
                if(board.getNum(x,y)!=9){
                    temp= x + size * y;



                    bc.getButtonList().get(temp).setSelected(true);
                    bc.leftClickBoardButton(temp);
                }
            } catch (ArrayIndexOutOfBoundsException ignored){

            }
        }
    }

    public void openSafeAreas(int finalI){
        int constX;
        int constY;
        constX=(finalI%size);
        constY=(finalI/size);


        if(playersBoard.getNumberOfMinesAround(constX,constY)!=board.getNum(finalI))
            return;

        int temp;
        for(int i=0;i<xList.size();i++){
            int x=constX+xList.get(i);
            int y=constY+yList.get(i);

            try{
                if(playersBoard.getNum(x,y)==11){
                    temp= x + size * y;

                    bc.getButtonList().get(temp).setSelected(true);
                    bc.leftClickBoardButton(temp);
                }
            } catch (ArrayIndexOutOfBoundsException ignored){}
        }

    }

    public void checkIfGameWon(){
        if(isGameEnded)
            return;
        int notOpened = playersBoard.getNotOpened();
        if(notOpened==mines) {
            gameWon();
            return;
        }
        else if(minesPlaced>mines){
            return;
        }
        for (int i = 0; i < size * size; i++) {
            if (board.getNum(i) == 9 && !(playersBoard.getNum(i) == 10)) {
                return;
            }
        }
        gameWon();
    }

    public void startGame(){

        board = new Board(size);
        playersBoard = new Board(size);

        try {
            mines=Integer.parseInt(bc.getFldCountText());
            if(mines>((size*size)-1))
                throw new NumberFormatException();
            board.setMines(mines);
        }
        catch (NumberFormatException e){
            bc.setFldCountText("Wrong number");
            return;
        }
        //todo: usunac:
        board.printBoard();

        bc.setLblTimeText("00:00");
        minesPlaced=0;
        bc.setLblBombsLeftText(Integer.toString(mines-minesPlaced));
        isGameEnded =false;
        isGameStarted=false;

    }

    public void stopGame(){
        stopTimer();
    }

    public void gameWon(){
        stopGame();
        String durString ="";
        try{
            durString=gameTime.format(DateTimeFormatter.ofPattern("mm:ss"));
        }
        catch(NullPointerException e){
            // Null pointer when game ends with first click - because of not yet initialized timer and gameTime
            durString="00:01";
        }

        TextInputDialog alert = new TextInputDialog();
        alert.setGraphic(null);
        alert.setTitle("Koniec gry");
        alert.setHeaderText("Wygrałeś! Twój czas: "+durString);
        alert.setContentText("Podaj imie:");

        Optional<String> result = alert.showAndWait();

        Score score;
        if (result.isPresent() && !result.get().equals("")){
            System.out.println("Your name: " + result.get());
            score = new Score(durString,result.get(),size,mines);
        }
        else{
            System.out.println("Your name: " + "Anon");
            score = new Score(durString,"Anonim",size,mines);
        }
        JSONObject json = Scoreboard.generateJSON(score);
        JSONArray jsonArr = Scoreboard.readJSONFile();
        jsonArr.add(json);
        Scoreboard.writeJSONArray(jsonArr);
    }

    public void gameLoosed(){
        stopGame();
        bc.showAllTiles();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Przegrałeś");
        alert.setHeaderText(null);
        alert.setContentText("Koniec gry :(");

        alert.showAndWait();
    }

    public void startTimer(){
        isGameStarted=true;
        timer=new Timer(this);
        timer.start();


    }

    public void stopTimer(){
        isGameEnded=true;
        timer.interrupt();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size=size;
    }
    
    public int getMines(){
        return mines;
    }

    public void setMines(int mines) {
        this.mines=mines;
    }

    public ArrayList<Integer> getXList() {
        return xList;
    }

    public ArrayList<Integer> getYList() {
        return yList;
    }

    public boolean getIsGameEnded() {
        return isGameEnded;
    }

    public Board getBoard() {
        return board;
    }

    public Board getPlayersBoard() {
        return playersBoard;
    }

    public boolean getIsGameStarted() {
        return isGameStarted;
    }

    private class Timer extends Thread{
        private GameLogic gl;
        private LocalTime startTime;
        private LocalTime inGameTime;
        private long gameDuration;
        private long prevGameDuration;

        private Timer(GameLogic gl){
            this.gl=gl;
        }

        @Override
        public void run() {
            try {
                startTime = LocalTime.now();
                while (isGameStarted && !isGameEnded) {
                    try {
                        Thread.sleep(230);
                    } catch (InterruptedException ignored) {
                    }
                    gameDuration = (int) Duration.between(startTime, LocalTime.now()).getSeconds();
                    if (prevGameDuration == gameDuration) {
                        continue;
                    }
                    prevGameDuration = gameDuration;
                    inGameTime = LocalTime.of(0, (int) gameDuration /60, (int) gameDuration % 60);

                    Platform.runLater(() -> {
                        gl.bc.setLblTimeText(inGameTime.format(DateTimeFormatter.ofPattern("mm:ss")));
                        gl.gameTime = inGameTime;

                    });
                }
            }
            catch(java.time.DateTimeException e){
                System.err.println("Your game lasted too long");
            }



        }
    }
}
