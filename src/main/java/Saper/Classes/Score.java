package Saper.Classes;


public class Score implements Comparable<Score>{
    private String time;
    private String name;
    private int size;
    private int mines;

    public Score(String time, String name, int size, int mines) {
        this.time = time;
        this.name = name;
        this.size = size;
        this.mines = mines;
    }

    public Score(String time, String name, String size, String mines) {
        this.time = time;
        this.name = name;
        try {
            this.size = Integer.parseInt(size);
            this.mines = Integer.parseInt(mines);
        }catch (NumberFormatException e){
            this.size = -1;
            this.mines = -1;
        }
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getMines() {
        return mines;
    }

    public void setMines(int mines) {
        this.mines = mines;
    }

    @Override
    public int compareTo(Score scoreToComp){
        int thisTime = Integer.parseInt(this.getTime().substring(0,2))*60 +
                Integer.parseInt(this.getTime().substring(3,5));
        int secondTime = Integer.parseInt(scoreToComp.getTime().substring(0,2))*60 +
                Integer.parseInt(scoreToComp.getTime().substring(3,5));
        System.out.println(thisTime-secondTime);
        return thisTime-secondTime;
    }

    @Override
    public String toString() {
        return time+"\t\t "+name+"\t\t\tplansza="+size+"x"+size+"\t\tilość min="+mines;
    }
}
