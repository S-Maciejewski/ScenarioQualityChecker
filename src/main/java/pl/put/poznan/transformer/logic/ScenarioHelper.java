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
     * Metoda zwraca kroki, które nie zaczynają się od aktora lub aktora systemowego
     * @param steps Lista kroków, w której będą szukane błędne kroki
     * @param actors Lista poprawnych aktorów
     * @param systemActors Lista poprawnych aktorów systemowych
     * @return List<Step> Lista błędnych kroków
     */
    List<Step> stepsWithInvalidActor(List<Step> steps, List<String> actors, List<String> systemActors) {
        List<Step> wrongSteps = new ArrayList<>();
        if (!steps.isEmpty())
            for (Step step : steps) {
                if (!step.helper.hasInvalidActor(step, actors, systemActors))
                    wrongSteps.add(step);
                if (step.getSubsteps() != null) {
                    List<Step> wrongSubsteps = stepsWithInvalidActor(step.getSubsteps(), actors, systemActors);
                    if (wrongSubsteps.size() != 0)
                        wrongSteps.addAll(wrongSubsteps);
                }
            }
        return wrongSteps;
    }


    /**
     * Metoda zwraca rekurencyjnie liczbę kroków
     * scenariusza, określonych przez parametr 'mode'
     * @param steps Lista podkroków scenariusza
     * @param mode Rodzaj zliczanych kroków: "all steps" lub "keyword steps"
     * @return int Liczba określonych kroków w scenariuszu
     */
    int countSteps(List<Step> steps, String mode) {
        int counter = 0;
        if (!steps.isEmpty())
            for (Step step : steps) {
                if (mode.equals("all steps"))
                    counter += 1;
                else if (mode.equals("keyword steps"))
                    if (step.helper.startsWithKeyword(step.getDescription()))
                        counter += 1;
                if (step.getSubsteps() != null)
                    counter += countSteps(step.getSubsteps(), mode);
            }
        return counter;
    }


    /**
     * Metoda zwraca listę ponumerowanych kroków scenariusza
     * do zadanej głębokości, uwzględniając zagnieżdżenia
     * W przypadku maksymalnej głębokości przeszukiwania
     * równej 0, zwracane są wszystkie kroki scenariusza
     * @param steps Lista kroków scenariusza
     * @param maxdepth Maksymalny poziom zagłębienia
     * @param currentNumbers Lista aktualnych numerów zagnieżdżenia
     * @return List<String> Lista ponumerowanych kroków scenariusza
     */
    List<String> getNumberedScenario(List<Step> steps, int maxdepth, List<String> currentNumbers) {
        List<String> numberedScenario = new ArrayList<>();
        if (!steps.isEmpty()) {
            if (currentNumbers.size() < maxdepth || maxdepth == 0) {
                for (int i = 0; i < steps.size(); i++) {
                    currentNumbers.add(currentNumbers.size(), Integer.toString(i + 1));
                    numberedScenario.add(String.join(".", currentNumbers) + ". " + steps.get(i).getDescription());
                    if (steps.get(i).getSubsteps() != null)
                        numberedScenario.addAll(getNumberedScenario(steps.get(i).getSubsteps(), maxdepth, currentNumbers));
                    currentNumbers.remove(currentNumbers.size() - 1);
                }
            }
        }
        return numberedScenario;
    }
}
