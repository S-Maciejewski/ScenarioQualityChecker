package pl.put.poznan.transformer.logic;

import net.minidev.json.JSONObject;
import pl.put.poznan.transformer.logic.visitor.Visitable;
import pl.put.poznan.transformer.logic.visitor.Visitor;
import java.util.Arrays;
import java.util.List;

/**
 * Reprezentuje scenariusz działań wysłany przez użytkownika.
 * <p>
 * Implementuje interfejs Visitable umożliwiając wykonywanie logiki przez inne klasy
 * @author Jan Techner
 * @version 4.0
 * @since 2018-10-31
 */
public class Scenario implements Visitable {

    /**
     * Lista słów kluczowych używanych w scenariuszu
     */
    public static List<String> KEYWORDS = Arrays.asList("ELSE:", "FOR EACH", "IF:");

    private String title;
    private List<String> actors;
    private List<String> systemActors;
    private List<Step> steps;

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

    public Scenario(String title, List<String> actors, List<String> systemActors, List<Step> steps) {
        this.title = title;
        this.actors = actors;
        this.systemActors = systemActors;
        this.steps = steps;
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
    public List<Step> getSteps() {
        return steps;
    }


    /**
     * Umożliwia wizytatorom operowanie na instancji klasy
     * @param visitor Wizytator przetwarzający instancję klasy
     */
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
