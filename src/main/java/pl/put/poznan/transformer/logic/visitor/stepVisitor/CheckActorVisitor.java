package pl.put.poznan.transformer.logic.visitor.stepVisitor;

import pl.put.poznan.transformer.logic.Scenario;
import pl.put.poznan.transformer.logic.Step;
import pl.put.poznan.transformer.logic.visitor.Visitor;
import java.util.List;

/**
 * Sprawdza, czy krok zaczyna się od poprawnego aktora
 */
public class CheckActorVisitor implements Visitor {

    private List<String> actors;
    private List<String> systemActors;
    private boolean hasValidActor;

    /**
     * @param actors lista poprawnych aktorów scenariusza
     * @param systemActors lista poprawnych aktorów systemowych scenariusza
     */
    public CheckActorVisitor(List<String> actors, List<String> systemActors) {
        this.actors = actors;
        this.systemActors = systemActors;
    }

    @Override
    public void visit(Scenario scenario) {}


    /**
     * Odwiedza krok i sprawdza, czy zaczyna się od poprawnego aktora
     * <p>
     * Pomija kroki zaczynające się od słów kluczowych i sprawdza czy
     * krok zaczyna się od któregoś z elementów z list actors i systemActors.
     * Informację o poprawności aktora zapisuje do lokalnej zmiennej hasValidActor
     * @param step odwiedzany krok
     */
    @Override
    public void visit(Step step) {
        String description = step.getDescription();
        if (description.startsWith("ELSE:") || description.startsWith("FOR EACH:"))
            hasValidActor = true;
        else {
            description = (description.replaceAll("IF:", "")).trim();
            if (actors != null)
                if(checkDescription(description, actors)) return;
            if (systemActors != null)
                if(checkDescription(description, systemActors)) return;
            hasValidActor = false;
        }
    }


    /**
     * Sprawdza, czy opis kroku zaczyna się od któregoś z elementów listy
     * @param description opis kroku
     * @param actors lista z elementami do sprawdzenia
     * @return Informacja o tym, czy znaleziono dopsowanie
     */
    private boolean checkDescription(String description, List<String> actors) {
        for (String actor : actors)
            if (description.startsWith(actor)) {
                hasValidActor = true;
                return true;
            }
        return false;
    }


    /**
     * Zwraca informację o poprawności aktora
     */
    public boolean hasValidActor() {
        return hasValidActor;
    }
}
