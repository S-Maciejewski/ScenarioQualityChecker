package pl.put.poznan.transformer.logic;

import net.minidev.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Klasa "Step"
 */

public class Step {

    public static List<String> KEYWORDS = Arrays.asList("ELSE:", "FOR EACH", "IF:");
    private List<Step> substeps;
    private String description;
    public StepHelper helper;

    /**
     * Konstruktor klasy "Step"
     * @param description Opis kroku scenariusza
     * @param substeps Lista podkroków kroku scenariusza
     */
    Step(String description, List<Step> substeps) {
        this.substeps = substeps;
        this.description = description;
    }


    /**
     * Metoda zwraca opis kroku scenariusza
     * @return Opis kroku scenariusza
     */
    String getDescription() {
        return description;
    }


    /**
     * Metoda zwraca listę podkroków scenariusza
     * @return Lista podkroków scenariusza
     */
    List<Step> getSubsteps() { return substeps; }


    /**
     * Tworzy listę ponumerowanych scenariuszy do zadanej głębokości
     * @param numberedScenario Kolekcja numerowanych scenariuszy
     * @param maxdepth Maksymalna głębokość
     * @param currentNumbers Aktualna głębokość
     */
    public void getNumberedScenario(JSONArray numberedScenario, int maxdepth, List<String> currentNumbers) {
        if (maxdepth >= currentNumbers.size() || maxdepth == 0) {
            numberedScenario.add(String.join(".", currentNumbers) + ". " + this.description);
            if (this.substeps != null)
                for (int i = 0; i < this.substeps.size(); i++) {
                    currentNumbers.add(currentNumbers.size(), Integer.toString(i+1));
                    this.substeps.get(i).getNumberedScenario(numberedScenario, maxdepth, currentNumbers);
                    currentNumbers.remove(currentNumbers.size() - 1);
                }
        }
    }
}
