package pl.put.poznan.transformer.logic;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.util.ArrayList;

/**
 * Klasa "FunctionTrigger" uruchamia wybraną
 * przez użytkownika funkcję przetwarzającą
 */
public class FunctionTrigger {

    /**
     * Metoda uruchamia wybraną funkcję przetwarzającą i zwraca właściwość utworzoną wybraną funkcją
     * @param scenario Scenariusz, na którym ma zostać wywołana wybrana funkcja
     * @param function Nazwa wybranej funkcji
     * @param intParam Dodatkowy parametr liczbowy
     * @return JSONObject Wynik wybranej funkcji jako obiekt w formacie JSON
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
