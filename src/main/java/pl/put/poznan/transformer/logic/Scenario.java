package pl.put.poznan.transformer.logic;
import java.util.Collections;
import net.minidev.json.JSONObject;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Scenario {

    private JSONObject scenario;

    private String title;
    private List<String> actors;
    private List<String> systemActors;

    private List<Step> steps;

    public Scenario(JSONObject sendscenario){

        this.scenario = sendscenario;
        this.title = sendscenario.getAsString("title");
        this.actors = (List<String>) sendscenario.get("actors");
        this.systemActors = (List<String>) sendscenario.get("system_actors");

        //TODO parsowanie scenariusza, wyodrębnienie poszczególnych kroków do klasy Step

    }
    
    public List<Step> missingActorSteps()
    {
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
