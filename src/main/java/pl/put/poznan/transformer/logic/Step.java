package pl.put.poznan.transformer.logic;

import pl.put.poznan.transformer.logic.visitor.Visitable;
import pl.put.poznan.transformer.logic.visitor.Visitor;

import java.util.Arrays;
import java.util.List;

/**
 * Przechowuje informacje o kroku scenariusza i jego podkrokach
 * <p>
 * Implementuje interfejs Visitable umożliwiając wykonywanie logiki przez inne klasy
 */
public class Step implements Visitable {

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
    public String getDescription() {
        return description;
    }

    /**
     * Zwraca listę podkroków scenariusza
     */
    public List<Step> getSubsteps() { return substeps; }

    /**
     * Umożliwia wizytatorom operowanie na instancji klasy
     * @param visitor Wizytator przetwarzający instancję klasy
     */
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
