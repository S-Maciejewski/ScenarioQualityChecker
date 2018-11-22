package pl.put.poznan.transformer.logic;

import net.minidev.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Klasa "ScenarioHelper" zawiera metody używane do przetwarzania scenariusza
 */
class ScenarioHelper {

    /**
     * Metoda zwraca kroki, które nie zaczynają się od aktora/aktora systemowego
     * @param steps Lista kroków, w której będą szukane błędne kroki
     * @param actors Lista poprawnych aktorów
     * @param systemActors Lista poprawnych aktorów systemowych
     * @return Lista błędnych kroków
     */
    public List<Step> missingActorSteps(List<Step> steps, List<String> actors,List<String> systemActors) {
        List<Step> wrongSteps = new ArrayList<>();
        if(steps != null){
            for (Step step : steps) {
                if (StepHelper.hasInvalidActor(step, actors, systemActors))
                    wrongSteps.add(step);
                if (step.getSubsteps() != null) {
                    List<Step> wrongSubsteps = StepHelper.stepsWithInvalidActors(step.getSubsteps(), actors, systemActors);
                    if (wrongSteps.size() != 0)
                        wrongSteps.addAll(wrongSubsteps);
                }
            }
        }
        return wrongSteps;
    }


    /**
     * Metoda zlicza liczbę wszystkich kroków scenariusza
     * @param steps Lista kroków, w której zliczane będą kroki
     * @return Liczba kroków scenariusza
     */
    public int countSteps(List<Step> steps) {
        int stepsCounter = 0;
        if (steps != null)
            for (Step step : steps) {
                stepsCounter += 1;
                if (step.getSubsteps() != null)
                    stepsCounter += StepHelper.countSteps(step.getSubsteps());
            }
        return stepsCounter;
    }


    /**
     * Meotda liczy liczbę słów kluczowych w poszczególnych krokach
     * @param steps Lista kroków, w której zliczane będą słowa kluczowe
     * @return Liczba słów kluczowych
     */
    public int countKeyWordSteps(List<Step> steps) {
        AtomicInteger stepsCounter = new AtomicInteger(0);
        if (steps != null) {
            for (Step step : steps) {
                step.countKeyWordSteps(stepsCounter);
            }
        }
        return stepsCounter.intValue();
    }


    /**
     * Tworzy kolekcję hierarchicznie ponumerowanych scenariuszy do zadanej głębokości
     * @param steps Przetwarzana lista kroków
     * @param depth Głębokość
     * @return Hierarchicznie ponumerowane scenariusze
     */
    public JSONArray showNumberedScenario(List<Step> steps, int depth) {
        JSONArray numberedScenario = new JSONArray();
        List<String> currentNumbers = new ArrayList<>();
        for (int i = 0; i < steps.size(); i++) {
            currentNumbers.add(Integer.toString(i+1));
            steps.get(i).getNumberedScenario(numberedScenario, depth, currentNumbers);
            currentNumbers.clear();
        }
        return numberedScenario;
    }
}
