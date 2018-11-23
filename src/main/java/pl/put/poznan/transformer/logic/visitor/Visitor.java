package pl.put.poznan.transformer.logic.visitor;

import pl.put.poznan.transformer.logic.Scenario;
import pl.put.poznan.transformer.logic.Step;

public interface Visitor {

    void visit(Scenario scenario);
    void visit(Step step);

}
