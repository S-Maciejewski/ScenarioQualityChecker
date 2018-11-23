package pl.put.poznan.transformer.logic;

import net.minidev.json.JSONObject;
import java.util.List;

/**
 * Przechowuje informacje o scenariuszu
 */
public class Scenario {

    private String title;
    private List<String> actors;
    private List<String> systemActors;
    private List<Step> steps;

    /**
     * Pozwala na wykorzystanie metod przetwarzania scenariusza
     */
    public ScenarioHelper helper = new ScenarioHelper();


    /**
     * Konstruktor klasy Scenario
     * @param sendscenario Scenariusz w formacie JSON
     */
    public Scenario(JSONObject sendscenario) {
        this.title = sendscenario.getAsString("title");
        this.actors = JSONTools.getProperty(sendscenario, "actors");
        this.systemActors = JSONTools.getProperty(sendscenario, "system_actors");
        this.steps = JSONTools.getStepsRecursively(JSONTools.getObjectsList(sendscenario));
    }


    /**
     * Metoda zwraca listę aktorów scenariusza
     */
    List<String> getActors() {
        return actors;
    }

    /***
     * Metoda zwraca liczbę aktorów systemowych scenariusza
     */
    List<String> getSystemActors() {
        return systemActors;
    }

    /**
     * Metoda zwraca listę kroków scenariusza
     */
    List<Step> getSteps() {
        return steps;
    }
}
