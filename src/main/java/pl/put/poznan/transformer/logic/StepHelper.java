package pl.put.poznan.transformer.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Zawiera metody służące do przetwarzania kroku scenariusza
 */
public class StepHelper {

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
