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
     * Konstruuje obiekt klasy "Step"
     * @param description Opis obiektu (etapu)
     * @param substeps Lista "substeps" (podetapów)
     */
    public Step(String description, List<Step> substeps) {
        this.substeps = substeps;
        this.description = description;
    }

    /**
     * Zwraca opis etapu
     * @return opis etapu
     */
    public String getDescription() {
        return description;
    }

    /**
     * Metoda sprawdza, czy opis etapu zaczyna się od aktora lub aktora systemowego;
     * usuwa początkowy "IF:";
     * jeśli wynik nie spełnia warunku, dodaje etap do listy "wrongSteps";
     * Następnie wywołuje metodę {@link #checkSubstepsSWA(List, List, List)}
     * @param wrongSteps Lista niepoprawnych opisów etapów
     * @param actors Lista aktorów
     * @param systemActors Lista aktorów systemowych
     */
    public void startsWithActor(List<Step> wrongSteps, List<String> actors, List<String> systemActors) {
        if(!(description.startsWith("ELSE:") || description.startsWith("FOR EACH:"))) {
            description = (description.replaceAll("IF:", "")).trim();
            Boolean correct = false;
            if(actors != null){
                for (String actor : actors) {
                    if(description.startsWith(actor)) {
                        correct = true;
                        break;
                    }
                }
            }
            if(!correct && systemActors != null){
                for (String systemActor : systemActors) {
                    if(description.startsWith(systemActor)) {
                        correct = true;
                        break;
                    }
                }
            }
            if(!correct){
                wrongSteps.add(this);
            }
        }
        checkSubstepsSWA(wrongSteps, actors, systemActors);
    }

    /**
     * Metoda wywołuje metodę {@link #startsWithActor(List, List, List)} dla każdego element z listy "substeps"
     * @param wrongSteps Lista etapów
     * @param actors Lista aktorów
     * @param systemActors Lista aktorów systemowych
     */
    public void checkSubstepsSWA(List<Step> wrongSteps, List<String> actors, List<String> systemActors) {
        if(substeps != null) {
                for (Step step : substeps) {
                    step.startsWithActor(wrongSteps, actors, systemActors);
            }
        }
    }

    /**
     * Metoda liczy liczbę podetapów
     * @param stepsCounter Licznik etapu
     */
    void countSteps(AtomicInteger stepsCounter) {
        if(substeps != null) {
                for (Step step : substeps) {
                    step.countSteps(stepsCounter);
            }
        }
        stepsCounter.addAndGet(1);
    }

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
