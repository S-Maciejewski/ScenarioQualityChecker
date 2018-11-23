package pl.put.poznan.transformer.logic.visitor.stepVisitor;

import pl.put.poznan.transformer.logic.Scenario;
import pl.put.poznan.transformer.logic.Step;
import pl.put.poznan.transformer.logic.visitor.Visitor;

/**
 * Sprawdza, czy krok zawiera słowo kluczowe
 */
public class CheckKeywordVisitor implements Visitor {

    private boolean startsWithKeyword = false;

    @Override
    public void visit(Scenario scenario) {}


    /**
     * Odwiedza krok i sprawdza czy zawiera słowo kluczowe. Wynik zapisuje do lokalnej zmiennej startsWithKeyword
     * @param step odwiedzany krok
     */
    @Override
    public void visit(Step step) {
        for (String keyword : Scenario.KEYWORDS)
            if (step.getDescription().startsWith(keyword))
                startsWithKeyword = true;
    }


    /**
     * Zwraca informację o tym, czy krok zawiera słowo kluczowe
     */
    public boolean startsWithKeyword() {
        return startsWithKeyword;
    }
}
