package pl.put.poznan.transformer.logic.visitor;

import pl.put.poznan.transformer.logic.Scenario;
import pl.put.poznan.transformer.logic.Step;
import java.util.ArrayList;
import java.util.List;

/**
 * Zwraca listę opisów kroków scenariusza, które nie mają poprawnego aktora
 */
public class WrongStepsVisitor implements Visitor {

    private List<String> wrongSteps = new ArrayList<>();
    private List<String> actors;
    private List<String> systemActors;

    /**
     * @param actors lista poprawnych aktorów scenariusza
     * @param systemActors lista poprawnych aktorów systemowych scenariusza
     */
    public WrongStepsVisitor(List<String> actors, List<String> systemActors) {
        this.actors = actors;
        this.systemActors = systemActors;
    }


    /**
     * Odwiedza scenariusz i wywołuje wizutatora kroku dla wszystkich kroków
     * pierwszego poziomu zagnieżdżenia. Następnie do lokalnej listy błędnych
     * kroków dodaje kroki zwrócone przez wizytatora kroku
     * @param scenario odwiedzany scenariusz
     */
    @Override
    public void visit(Scenario scenario) {
        List<Step> steps = scenario.getSteps();
        if (!steps.isEmpty())
            for (Step step: steps) {
                WrongStepsVisitor stepVisitor = new WrongStepsVisitor(actors, systemActors);
                step.accept(stepVisitor);
                wrongSteps.addAll(stepVisitor.getWrongSteps());
            }
    }


    /**
     * Sprawdza czy krok zaczyna się od poprawnego aktora, jeżeli nie, to
     * dodaje opis kroku do lokalnej listy niepoprawnych kroków. Następnie
     * wywołuje rekurencyjnie wizutatora kroku dla wszystkich podkroków
     * danego kroku i dodaje zwrócone przez kolejnych wizytatorów niepoprawne
     * kroki do lokalnej listy niepoprawnych kroków
     * @param step odwiedzany krok
     */
    @Override
    public void visit(Step step) {
        if (!step.helper.hasInvalidActor(step, actors, systemActors))
            wrongSteps.add(step.getDescription());
        if (step.getSubsteps() != null)
            for (Step substep: step.getSubsteps()) {
                WrongStepsVisitor stepVisitor = new WrongStepsVisitor(actors, systemActors);
                substep.accept(stepVisitor);
                wrongSteps.addAll(stepVisitor.getWrongSteps());
            }
    }


    /**
     * Zwraca listę opisów niepoprawnych kroków
     */
    public List<String> getWrongSteps() {
        return wrongSteps;
    }
}
