package pl.put.poznan.transformer.logic;
import net.minidev.json.JSONObject;
import java.util.ArrayList;

public class Scenario {

    private JSONObject scenario;

    private String title;
    private ArrayList<String> actors;
    private ArrayList<String> systemActors;

    private ArrayList<Step> steps;

    public Scenario(JSONObject sendscenario){

        this.scenario = sendscenario;
        this.title = sendscenario.getAsString("title");
        this.actors = (ArrayList<String>) sendscenario.get("actors");
        this.systemActors = (ArrayList<String>) sendscenario.get("system_actors");

        //TODO parsowanie scenariusza, wyodrębnienie poszczególnych kroków do klasy Step

    }

    public JSONObject getScenario() {
        return scenario;
    }
    public String getTitle() {
        return title;
    }
    public ArrayList<String> getActors() {
        return actors;
    }
    public ArrayList<String> getSystemActors() {
        return systemActors;
    }
    public ArrayList<Step> getSteps() {
        return steps;
    }

    public JSONObject triggerFunction(String function) {

        //TODO implementacja funkcji zwracających odpowiednie rzeczy
        return scenario;
    }


}
