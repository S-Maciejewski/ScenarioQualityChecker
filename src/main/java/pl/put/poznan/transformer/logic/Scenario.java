package pl.put.poznan.transformer.logic;

import net.minidev.json.JSONObject;
import java.util.List;

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
     * Konstruktor klasy "Scenario"
     * @param sendscenario Scenariusz w formacie JSON wysłany przez użytkownika
     */
    public Scenario(JSONObject sendscenario) {
        this.scenario = sendscenario;
        this.title = sendscenario.getAsString("title");
        this.actors = JSONReader.getProperty(scenario, "actors");
        this.systemActors = JSONReader.getProperty(scenario, "system_actors");
        this.steps = JSONReader.getStepsRecursively(JSONReader.getObjectsList(scenario, "steps"));
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
