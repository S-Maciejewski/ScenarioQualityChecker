package pl.put.poznan.transformer.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Zawiera metody służące do przetwarzania scenariusza
 */
public class ScenarioHelper {

    /**
     * Zwraca listę kroków scenariusza, które nie zaczynają się od aktora lub aktora systemowego
     * @param steps lista kroków scenariusza, w której będą szukane błędne kroki
     * @param actors lista poprawnych aktorów
     * @param systemActors lista poprawnych aktorów systemowych
     * @return Lista błędnych kroków
     */
    public List<Step> stepsWithInvalidActor(List<Step> steps, List<String> actors, List<String> systemActors) {
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
     * Metoda zwraca listę ponumerowanych kroków scenariusza
     * do zadanej głębokości, uwzględniając zagnieżdżenia. <p>
     * W przypadku maksymalnej głębokości przeszukiwania
     * równej 0, zwracane są wszystkie kroki scenariusza.
     * @param steps lista kroków scenariusza
     * @param maxdepth maksymalny poziom zagnieżdżenia
     * @param currentNumbers lista aktualnych numerów kroku i nadkroków,
     *                       początkowo powinna być pusta
     * @return Lista ponumerowanych kroków scenariusza
     */
    public List<String> getNumberedScenario(List<Step> steps, int maxdepth, List<String> currentNumbers) {
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
