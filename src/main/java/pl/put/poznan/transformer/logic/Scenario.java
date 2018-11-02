package pl.put.poznan.transformer.logic;
import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;

import com.fasterxml.jackson.annotation.JsonValue;
import net.minidev.json.JSONObject;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Scenario {

    private JSONObject scenario;

    private String title;
    private List<String> actors = new ArrayList<>();
    private List<String> systemActors = new ArrayList<>();

    private List<Step> steps;

    public Scenario(JSONObject sendscenario){

        this.scenario = sendscenario;
        this.title = sendscenario.getAsString("title");
        this.actors = readJson("actors");
        this.systemActors = readJson("system_actors");
        this.steps = readJsonSteps();

        //TODO parsowanie scenariusza, wyodrębnienie poszczególnych kroków do klasy Step

    }

    private List<String> readJson(String field) {

        List<String> list = new ArrayList<>();
        Object obj = this.scenario.get(field);

        if (obj instanceof String) {
            list.add((String)obj);
        } else if (obj instanceof ArrayList<?>) {
            for (Object o : (ArrayList) obj){
                if (o instanceof String)
                    list.add((String) o);
            }
        }
        return list;
    }
    
    public List<Step> missingActorSteps() {
        List<Step> wrongSteps = Collections.<Step>emptyList();
        if(steps != null){
            for (Step step : steps) {
                step.startsWithActor(wrongSteps, actors, systemActors);
            }
        }
        return wrongSteps;
    }
    
    public int countSteps() {
        AtomicInteger stepsCounter = new AtomicInteger(0);
        if (steps != null) {
            for (Step step : steps) {
                step.countSteps(stepsCounter);
            }
        }
        return stepsCounter.intValue();
    }

    public JSONObject getScenario() {
        return scenario;
    }
    public String getTitle() {
        return title;
    }
    public List<String> getActors() {
        return actors;
    }
    public List<String> getSystemActors() {
        return systemActors;
    }
    public List<Step> getSteps() {
        return steps;
    }

    public JSONObject triggerFunction(String function) {

        //TODO implementacja funkcji zwracających odpowiednie rzeczy
        return scenario;
    }


}
