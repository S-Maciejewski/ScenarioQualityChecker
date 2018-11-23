package pl.put.poznan.transformer.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Zawiera metody służące do przetwarzania kroku scenariusza
 */
public class StepHelper {

    /**
     * Sprawdza, czy krok scenariusza zaczyna się od niepoprawnego aktora
     * @param step sprawdzany krok
     * @param actors lista poprawnych aktorów scenariusza
     * @param systemActors lista poprawnych aktorów systemowych scenariusza
     * @return Prawda, w przypadku, gdy krok nie zaczyna się od poprawnego aktora,
     *         fałsz w przypadku przeciwnym
     */
    public boolean hasInvalidActor(Step step, List<String> actors,List<String> systemActors) {
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
     * Sprawdza, czy opis kroku zaczyna się od słowa kluczowego
     * @param description opis kroku scenariusza
     * @return Informacja o tym, czy opis kroku zaczyna się od słowa kluczowego
     */
    public boolean startsWithKeyword(String description) {
        for (String keyword : Step.KEYWORDS)
            if (description.startsWith(keyword))
                return true;
        return false;
    }
}
