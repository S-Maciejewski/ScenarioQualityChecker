package pl.put.poznan.transformer.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Klasa "StepHelper" zawiera metody służące
 * do przetwarzania kroków scenariusza
 */
public class StepHelper {

    /**
     * Metoda sprawdza, czy krok scenariusza zaczyna się od niepoprawnego aktora
     * @param step Krok, którego popraność jest sprawdzana
     * @param actors Lista poprawnych aktorów scenariusza
     * @param systemActors Lista poprawnych aktorów systemowych scenariusza
     * @return Prawda, w przypadku, gdy krok nie zaczyna się od poprawnego aktora, fałsz w przeciwnym
     */
    static boolean hasInvalidActor(Step step, List<String> actors,List<String> systemActors) {
        String description = step.getDescription();
        if (description.startsWith("ELSE:") || description.startsWith("FOR EACH:"))
            return true;
        else {
            description = (description.replaceAll("IF:", "")).trim();
            if (actors != null)
                for (String actor : actors)
                    if (description.startsWith(actor))
                        return true;
            if (systemActors != null)
                for (String systemActor : systemActors)
                    if (description.startsWith(systemActor))
                        return true;
            return false;
        }
    }


    /**
     * Metoda zwraca rekurencyjnie listę kroków scenariusza
     * zaczynających się od niepoprawnego aktora
     * @param steps Lista kroków scenariusza
     * @param actors Lista poprawnych aktorów scenariusza
     * @param systemActors Lista poprawnych sktorów systemowych scenariusza
     * @return Lista kroków zaczynających się od niepoprawnego aktora
     */
    static List<Step> stepsWithInvalidActors(List<Step> steps, List<String> actors,List<String> systemActors) {
        List<Step> wrongSteps = new ArrayList<>();
        for (Step step : steps) {
            if (!StepHelper.hasInvalidActor(step, actors, systemActors))
                wrongSteps.add(step);
            if (step.getSubsteps() != null) {
                List<Step> wrongSubsteps = stepsWithInvalidActors(step.getSubsteps(), actors, systemActors);
                if (wrongSubsteps.size() != 0)
                    wrongSteps.addAll(wrongSubsteps);
            }
        }
        return wrongSteps;
    }


    /**
     * Metoda zwraca rekurencyjnie liczbę kroków w zbiorze podkroków scenariusza
     * @param substeps Lista podkroków scenariusza
     * @return Liczba kroków w zbiorze podkroków scenariusza
     */
    static int countSteps(List<Step> substeps) {
        int counter = 0;
        for (Step step : substeps) {
            counter += 1;
            if (step.getSubsteps() != null)
                counter += StepHelper.countSteps(step.getSubsteps());
        }
        return counter;
    }
}
