package pl.put.poznan.transformer.logic.visitor;

import pl.put.poznan.transformer.logic.Scenario;
import pl.put.poznan.transformer.logic.Step;
import java.util.ArrayList;
import java.util.List;

/**
 * Zwraca listę ponumerowanych kroków scenariusza, zgodnie z poziomem zagnieżdżenia
 */
public class NumberStepsVisitor implements Visitor {

    private List<String> numberedSteps = new ArrayList<>();
    private int maxDepth;
    private List<String> currentNumbers;

    /**
     * @param maxDepth maksymalny poziom przeszukiwania
     * @param currentNumbers lista aktualnych numerów kroku
     */
    private NumberStepsVisitor(int maxDepth, List<String> currentNumbers) {
        this.maxDepth = maxDepth;
        this.currentNumbers = currentNumbers;
    }


    /**
     * @param maxDepth maksymalny poziom przeszukiwania
     */
    public NumberStepsVisitor(int maxDepth) {
        this.maxDepth = maxDepth;
        this.currentNumbers = new ArrayList<>();
    }


    /**
     * Odwiedza scenariusz i przetwarza listę kroków na pierwszym poziomie zagnieżdżenia
     * @param scenario odwiedzany scenariusz
     */
    @Override
    public void visit(Scenario scenario) {
        List<Step> steps = scenario.getSteps();
        if (!steps.isEmpty())
            processSubsteps(steps);
    }


    /**
     * Odwiedza krok i dodaje ponumerowany opis kroku do lokalnej listy ponumerowanych
     * kroków, a następnie przetwarza listę podkroków danego kroku
     * @param step odwiedzany krok
     */
    @Override
    public void visit(Step step) {
        numberedSteps.add(String.join(".", currentNumbers) + ". " + step.getDescription());
        if (step.getSubsteps() != null)
            if (currentNumbers.size() < maxDepth || maxDepth == 0)
                processSubsteps(step.getSubsteps());
    }


    /**
     * Przetwarza listę kroków scenariusza
     * <p>
     * Aktualizuje listę aktualnych numerów kroku, tworzy nowego wizytatora,
     * wywołuje go rekurencyjnie na kolejnych krokach i dodaje ponumerowane kroki zwrócone
     * przez wizytatora do lokalnej listy ponumerowanych kroków
     * @param substeps lista przetwarzanych kroków
     */
    private void processSubsteps (List<Step> substeps) {
        for (int i = 0; i < substeps.size(); i++) {
            currentNumbers.add(currentNumbers.size(), Integer.toString(i + 1));
            NumberStepsVisitor visitor = new NumberStepsVisitor(maxDepth, currentNumbers);
            substeps.get(i).accept(visitor);
            numberedSteps.addAll(visitor.getNumberedSteps());
            currentNumbers.remove(currentNumbers.size() - 1);
        }
    }


    /**
     * Zwraca listę ponumerowanych kroków scenariusza
     */
    public List<String> getNumberedSteps() {
        return numberedSteps;
    }
}
