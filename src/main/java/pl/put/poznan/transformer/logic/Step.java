package pl.put.poznan.transformer.logic;

import net.minidev.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Przechowuje informacje o kroku scenariusza i jego podkrokach
 */
public class Step {

    /**
     * Lista słów kluczowych używanych w krokach scenariusza
     */
    public static List<String> KEYWORDS = Arrays.asList("ELSE:", "FOR EACH", "IF:");
    private List<Step> substeps;
    private String description;

    /**
     * Pozwala na wykorzystanie metod przetwarzania kroku scenariusza
     */
    public StepHelper helper = new StepHelper();

    /**
     * Konstruktor klasy Step
     * @param description opis kroku scenariusza
     * @param substeps lista podkroków kroku scenariusza
     */
    public Step(String description, List<Step> substeps) {
        this.substeps = substeps;
        this.description = description;
    }


    /**
     * Zwraca opis kroku scenariusza
     */
    String getDescription() {
        return description;
    }

    /**
     * Zwraca listę podkroków scenariusza
     */
    List<Step> getSubsteps() { return substeps; }

}
