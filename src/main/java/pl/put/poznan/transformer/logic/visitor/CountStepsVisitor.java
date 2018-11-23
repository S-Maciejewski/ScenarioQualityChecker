package pl.put.poznan.transformer.logic.visitor;

import pl.put.poznan.transformer.logic.Scenario;
import pl.put.poznan.transformer.logic.Step;
import java.util.List;

/**
 * Wizytator klas Scenario i Step
 * <p>
 * Zlicza kroki scenariusza, określone parametrem
 */
public class CountStepsVisitor implements Visitor {

    private int stepsCounter = 0;
    private String mode;

    /**
     * Ustawia tryb zliczania wizytatora
     * @param mode określa rodzaj zliczanych kroków:
     *             '<b>all steps</b>' - wszystkie kroki,
     *             '<b>keyword steps</b>' - kroki zaczynające się od słowa kluczowego
     */
    public CountStepsVisitor(String mode) {
        this.mode = mode;
    }


    /**
     * Odwiedza scenariusz i zwraca liczbę kroków w każdym
     * kroku (razem z podkrokami) na pierwszym poziomie zagnieżdżenia scenariusza.
     * <p>
     * Dla każdego kroku i jego podkroków tworzony jest nowy, rekurencyjny wizytator kroku,
     * zgodny z trybem zliczania kroków, zliczający liczbę kroków w danym kroku i wszystkich
     * jego zagnieżdżonych podkrokach. Po każdej wizycie wizytatora kroku zwracany jest
     * jego licznik i dodawany do lokalnego licznika kroków wizytatora scenariusza.
     * @param scenario odwiedzany scenariusz
     */
    @Override
    public void visit(Scenario scenario) {
        List<Step> steps = scenario.getSteps();
        if (!steps.isEmpty())
            for (Step step : steps) {
                CountStepsVisitor stepsVisitor = new CountStepsVisitor(mode);
                step.accept(stepsVisitor);
                stepsCounter += stepsVisitor.getStepsCounter();
            }
    }


    /**
     * Odwiedza krok i zwraca liczbę kroków w podkrokach
     * <p>
     * Dla wszystkich podkroków tworzony jest nowy, rekurencyjny wizytator,
     * zgodny z trybem zliczania kroków, zliczający liczbę wszystkich zagnieżdżonych podkroków.
     * Po każdej wizycie wizytatora kroku zwracany jest jego licznik i dodawany
     * do lokalnego licznika nadrzędnego wizytatora kroku.
     * @param step odwiedzany krok
     */
    @Override
    public void visit(Step step) {
        if (mode.equals("all steps"))
            stepsCounter += 1;
        else if (mode.equals("keyword steps"))
            if (step.helper.startsWithKeyword(step.getDescription()))
                stepsCounter += 1;
        if (step.getSubsteps() != null)
            for (Step substep : step.getSubsteps()) {
                CountStepsVisitor substepsVisitor = new CountStepsVisitor(mode);
                substep.accept(substepsVisitor);
                stepsCounter += substepsVisitor.getStepsCounter();
            }
    }


    /**
     * Zwraca lokalną liczbę kroków
     */
    public int getStepsCounter() {
        return stepsCounter;
    }
}
