package pl.put.poznan.transformer.logic;
import java.util.ArrayList;
import java.util.Collections;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Scenario {

    private JSONObject scenario;

    private String title;
    private List<String> actors;
    private List<String> systemActors;

    private List<Step> steps;

    public Scenario(JSONObject sendscenario) {

        this.scenario = sendscenario;
        this.title = sendscenario.getAsString("title");
        this.actors = readJson("actors");
        this.systemActors = readJson("system_actors");
        this.steps = readJsonSteps();
    }

    private List<String> readJson(String field) {
        List<String> list = new ArrayList<>();
        Object obj = this.scenario.get(field);
        if (obj instanceof String) {
            list.add((String)obj);
        } else if (obj instanceof ArrayList) {
            for (Object o : (ArrayList) obj){
                if (o instanceof String)
                    list.add((String) o);
            }
        }
        return list;
    }

    private List<Step> readJsonSteps() {
        Object stepsAsObject = this.scenario.get("steps");
        List<Object> stepsAsObjectsList = new ArrayList<>();
        if (stepsAsObject instanceof ArrayList)
             stepsAsObjectsList.addAll((ArrayList)stepsAsObject);
        return recursiveStepsReader(stepsAsObjectsList);
    }

    private List<Step> recursiveStepsReader(List<Object> steps) {
        List<Step> steplist = new ArrayList<>();
        for (int i=0; i<steps.size(); i++) {
            if (steps.get(i) instanceof String) {
                if (i == steps.size() - 1) {
                    steplist.add(new Step((String) steps.get(i), null));
                } else {
                    if (steps.get(i + 1) instanceof String) {
                        steplist.add(new Step((String) steps.get(i), null));
                    } else if (steps.get(i + 1) instanceof ArrayList) {
                        List<Object> substepsAsObjectList = new ArrayList<>();
                        if (steps.get(i + 1) instanceof ArrayList)
                            substepsAsObjectList.addAll((ArrayList)steps.get(i+1));
                        List<Step> substeps = recursiveStepsReader(substepsAsObjectList);
                        steplist.add(new Step((String) steps.get(i), substeps));
                    }
                }
            }
        }
        return steplist;
    }
    
    public List<Step> missingActorSteps() {
        List<Step> wrongSteps = new ArrayList<>();
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
    
    public int countKeyWordSteps() {
        AtomicInteger stepsCounter = new AtomicInteger(0);
        if (steps != null) {
            for (Step step : steps) {
                step.countKeyWordSteps(stepsCounter);
            }
        }
        return stepsCounter.intValue();
    }

    public JSONArray showNumberedScenario(int depth) {
        JSONArray numberedScenario = new JSONArray();
        List<String> currentNumbers = new ArrayList<>();
        for (int i = 0; i < steps.size(); i++) {
            currentNumbers.add(Integer.toString(i+1));
            steps.get(i).getNumberedScenario(numberedScenario, depth, currentNumbers);
            currentNumbers.clear();
        }
        return numberedScenario;
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

    public JSONObject triggerFunction(String function, int intParam) {
        JSONObject response = new JSONObject();
        switch (function) {
            case "wrongSteps":
                JSONArray wrongStepsJSON = new JSONArray();
                for (Step s: missingActorSteps()){
                    wrongStepsJSON.add(s.getDescription());
                }
                response.put("wrongSteps", wrongStepsJSON);
                break;
            case "countSteps":
                response.put("stepsNumber", countSteps());
                break;
            case "countKeyWordSteps":
                response.put("keywordStepsNumber", countKeyWordSteps());
                break;
            case "showScenario":
                response.put("steps", showNumberedScenario(0));
                break;
            case "showScenarioWithMaxDepth":
                response.put("steps", showNumberedScenario(intParam));
                break;
            default:
                break;
        }
        return response;
    }


}
