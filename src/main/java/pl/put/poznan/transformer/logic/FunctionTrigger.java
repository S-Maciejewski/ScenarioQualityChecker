package pl.put.poznan.transformer.logic;

import net.minidev.json.JSONObject;
import pl.put.poznan.transformer.logic.visitor.CountStepsVisitor;
import pl.put.poznan.transformer.logic.visitor.NumberStepsVisitor;
import pl.put.poznan.transformer.logic.visitor.WrongStepsVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Uruchamia wybraną funkcję przetwarzającą scenariusz
 */
public class FunctionTrigger {

    /**
     * Uruchamia wybraną funkcję przetwarzającą i zwraca właściwość utworzoną wybraną funkcją
     * @param scenario scenariusz, na którym ma zostać wywołana wybrana funkcja
     * @param function nazwa wybranej funkcji
     * @param intParam dodatkowy parametr liczbowy
     * @return Wynik wybranej funkcji jako właściwość w formacie JSON
     */
    public static JSONObject run(Scenario scenario, String function, int intParam) {
        JSONObject response = new JSONObject();
        switch (function) {
            case "wrongSteps":
                WrongStepsVisitor sVisitor = new WrongStepsVisitor(scenario.getActors(), scenario.getSystemActors());
                scenario.accept(sVisitor);
                response.put("wrongSteps", JSONTools.toJSONArray(sVisitor.getWrongSteps()));
                break;
            case "countSteps":
                CountStepsVisitor scenarioVisitor = new CountStepsVisitor("all steps");
                scenario.accept(scenarioVisitor);
                response.put("stepsNumber", scenarioVisitor.getStepsCounter());
                break;
            case "countKeyWordSteps":
                scenarioVisitor = new CountStepsVisitor("keyword steps");
                scenario.accept(scenarioVisitor);
                response.put("keywordStepsNumber", scenarioVisitor.getStepsCounter());
                break;
            case "showScenario":
                NumberStepsVisitor scenario1Visitor = new NumberStepsVisitor(0);
                scenario.accept(scenario1Visitor);
                response.put("steps", JSONTools.toJSONArray(scenario1Visitor.getNumberedSteps()));
                break;
            case "showScenarioWithMaxDepth":
                scenario1Visitor = new NumberStepsVisitor(intParam);
                scenario.accept(scenario1Visitor);
                response.put("steps", JSONTools.toJSONArray(scenario1Visitor.getNumberedSteps()));
                break;
            default:
                break;
        }
        return response;
    }
}
