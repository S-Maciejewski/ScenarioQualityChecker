package pl.put.poznan.transformer.logic;

import net.minidev.json.JSONObject;
import java.util.ArrayList;

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
                response.put("wrongSteps", JSONTools.toJSONArray(scenario.helper.stepsWithInvalidActor(
                        scenario.getSteps(), scenario.getActors(), scenario.getSystemActors())));
                break;
            case "countSteps":
                response.put("stepsNumber", scenario.helper.countSteps(scenario.getSteps(), "all steps"));
                break;
            case "countKeyWordSteps":
                response.put("keywordStepsNumber", scenario.helper.countSteps(scenario.getSteps(), "keyword steps"));
                break;
            case "showScenario":
                response.put("steps", JSONTools.toJSONArray(scenario.helper.getNumberedScenario(
                        scenario.getSteps(), 0, new ArrayList<>())));
                break;
            case "showScenarioWithMaxDepth":
                response.put("steps", JSONTools.toJSONArray(scenario.helper.getNumberedScenario(
                        scenario.getSteps(), intParam, new ArrayList<>())));
                break;
            default:
                break;
        }
        return response;
    }
}
