package Saper.Classes;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Scoreboard {
    public static JSONObject generateJSON(Score s){
        JSONObject score = new JSONObject();
        score.put("time",s.getTime());
        score.put("name", s.getName());
        score.put("size", Integer.toString(s.getSize()));
        score.put("mines", Integer.toString(s.getMines()));
        return score;
    }
    public static void writeJSONArray(JSONArray array){
        try (FileWriter file = new FileWriter("scoreboard.json")) {
            file.write(prettyJSON(array.toJSONString()));
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static JSONArray readJSONFile(){
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("scoreboard.json"))
        {
            Object obj = jsonParser.parse(reader);
            return (JSONArray) obj;
        } catch (IOException | ParseException e) {
            return new JSONArray();
        }
    }
    public static SuperArrayList<Score> JSONArrToArrList(JSONArray jsonArray){
        SuperArrayList<Score> scores = new SuperArrayList<>();
        for (Object object: jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            String time = (String) jsonObject.get("time");
            String name = (String) jsonObject.get("name");
            String size = (String) jsonObject.get("size");
            String mines = (String) jsonObject.get("mines");
            Score score = new Score(time,name,size,mines);
            scores.add(score);
        }
        return scores;
    }
    public static String prettyJSON(String uglyJSON){
        org.json.JSONArray newObject = new org.json.JSONArray(uglyJSON);
        return newObject.toString(4);
    }




}
