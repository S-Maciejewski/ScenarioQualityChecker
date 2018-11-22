package pl.put.poznan.transformer.logic;
import java.util.ArrayList;
import java.util.Collections;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Klasa "Scenario"
 */
public class Scenario {

    private JSONObject scenario;

    private String title;
    private List<String> actors;
    private List<String> systemActors;

    private List<Step> steps;

    ScenarioHelper helper = new ScenarioHelper();

    /**
     * Konstruktor obiektów klasy "Scenario"
     * @param sendscenario Scenariusz do przetworzenia
     */
    public Scenario(JSONObject sendscenario) {

        this.scenario = sendscenario;
        this.title = sendscenario.getAsString("title");
        this.actors = readJson("actors");
        this.systemActors = readJson("system_actors");
        this.steps = readJsonSteps();
    }

    /**
     * Metoda zwraca listę wartości z danej kolekcji
     * @param field Kolekcja, dla której wartości mają zostać odczytane
     * @return Lista wartości kolekcji
     */
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

    /**
     * Metoda zwracająca listę "steps"
     * @return Lista "steps"
     */
    private List<Step> readJsonSteps() {
        Object stepsAsObject = this.scenario.get("steps");
        List<Object> stepsAsObjectsList = new ArrayList<>();
        if (stepsAsObject instanceof ArrayList)
             stepsAsObjectsList.addAll((ArrayList)stepsAsObject);
        return recursiveStepsReader(stepsAsObjectsList);
    }

    /**
     * Rekurencyjne odczytywanie (przetworzenie) "steps"
     * @param steps Lista obiektów z kolekcji "steps"
     * @return Przetworzona lista "steps"
     */
    private List<Step> recursiveStepsReader(List<Object> steps) {
        List<Step> steplist = new ArrayList<>();
        for (int i=0; i<steps.size(); i++)
            if (steps.get(i) instanceof String) {
                if (i == steps.size() - 1) {
                    steplist.add(new Step((String) steps.get(i), null));
                } else {
                    if (steps.get(i + 1) instanceof String) {
                        steplist.add(new Step((String) steps.get(i), null));
                    } else if (steps.get(i + 1) instanceof ArrayList) {
                        List<Object> substepsAsObjectList = new ArrayList<>();
                        if (steps.get(i + 1) instanceof ArrayList)
                            substepsAsObjectList.addAll((ArrayList) steps.get(i + 1));
                        List<Step> substeps = recursiveStepsReader(substepsAsObjectList);
                        steplist.add(new Step((String) steps.get(i), substeps));
                    }
                }
            }
        return steplist;
    }


    /**
     * Metoda zwraca scenariusz
     * @return Sceariusz
     */
    public JSONObject getScenario() {
        return scenario;
    }

    /**
     * Metoda zwraca tytuł scenariusza
     * @return Tytuł scenariusza
     */
    public String getTitle() {
        return title;
    }

    /**
     * Metoda zwraca listę aktorów
     * @return Lista aktorów
     */
    public List<String> getActors() {
        return actors;
    }

    /***
     * Metoda zwraca liczbę aktorów systemowych
     * @return Lista aktorów systemowych
     */
    public List<String> getSystemActors() {
        return systemActors;
    }

    /**
     * Metoda zwraca listę etapów
     * @return Lista etapów
     */
    public List<Step> getSteps() {
        return steps;
    }
}
