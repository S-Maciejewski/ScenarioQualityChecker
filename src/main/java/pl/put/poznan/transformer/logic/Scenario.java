package pl.put.poznan.transformer.logic;
import java.util.ArrayList;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import java.util.List;

/**
 * Reprezentuje scenariusz działań wysłany przez użytkownika
 * i udostępnia zbiór metod w celu jego przetwarzania
 * @author Jan Techner
 * @version 4.0
 * @since 2018-10-31
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
     * Przetwarza wysłany scenariusz i wyodrębnia elementy takie jak
     * tytuł scenariusza, aktorów i listę kroków
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
