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

}
