package pl.put.poznan.transformer.logic;

import net.minidev.json.JSONArray;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Klasa "Step"
 */

public class Step {

    private List<Step> substeps;
    private String description;

    /**
     * Konstruktor klasy "Step"
     * @param description Opis kroku scenariusza
     * @param substeps Lista podkroków kroku scenariusza
     */
    public Step(String description, List<Step> substeps) {
        this.substeps = substeps;
        this.description = description;
    }


    /**
     * Metoda zwraca opis kroku scenariusza
     * @return Opis kroku scenariusza
     */
    public String getDescription() {
        return description;
    }


    /**
     * Metoda zwraca listę podkroków scenariusza
     * @return Lista podkroków scenariusza
     */
    public List<Step> getSubsteps() { return substeps; }

    /**
     * Metoda liczy liczbę słów kluczowych "ELSE:", "FOR EACH", "IF:"
     * @param stepsCounter Licznik słów kluczowych
     */
    void countKeyWordSteps(AtomicInteger stepsCounter) {
        if(substeps != null) {
            for (Step step : substeps) {
                step.countKeyWordSteps(stepsCounter);
            }
        }
        if(description.startsWith("ELSE:") || description.startsWith("FOR EACH") || description.startsWith("IF:")) {
            stepsCounter.addAndGet(1);
        }
    }

    /**
     * Tworzy listę ponumerowanych scenariuszy do zadanej głębokości
     * @param numberedScenario Kolekcja numerowanych scenariuszy
     * @param maxdepth Maksymalna głębokość
     * @param currentNumbers Aktualna głębokość
     */
    public void getNumberedScenario(JSONArray numberedScenario, int maxdepth, List<String> currentNumbers) {
        if (maxdepth >= currentNumbers.size() || maxdepth == 0) {
            numberedScenario.add(String.join(".", currentNumbers) + ". " + this.description);
            if (this.substeps != null) {
                for (int i = 0; i < this.substeps.size(); i++) {
                    currentNumbers.add(currentNumbers.size(), Integer.toString(i+1) );
                    this.substeps.get(i).getNumberedScenario(numberedScenario, maxdepth, currentNumbers);
                    currentNumbers.remove(currentNumbers.size() - 1);
                }
            }
        }
    }

    /**
     * Hierarchinczny podgląd wszystkich kroków
     * @param prefix Prefix
     */
    //prefix pozwala na hierarchiczny podgląd wszystkich kroków - domyślnie ""
    public void showRecursively(String prefix) {
        prefix += "  ";
        System.out.println(prefix + this.description);
        if (this.substeps != null) {
            for (Step s: this.substeps) {
                s.showRecursively(prefix);
            }
        }
    }
}
