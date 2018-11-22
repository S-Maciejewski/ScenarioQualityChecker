package pl.put.poznan.transformer.logic;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 * Klasa "FunctionTrigger" uruchamia wybraną
 * przez użytkownika funkcję przetwarzającą
 */
public class FunctionTrigger {

    /**
     * Metoda uruchamia wybraną funkcję przetwarzającą i
     * zwraca obiekt kolekcji utworzony wybraną funkcją
     * @param scenario Scenariusz, na którym ma być wywołana wybrana funkcja
     * @param function Wybrana funkcja
     * @param intParam Dodatkowy parametr
     * @return Wynik wybranej funkcji jako obiekt w formacie JSON
     */
    public static JSONObject run(Scenario scenario, String function, int intParam) {
        JSONObject response = new JSONObject();

        switch (function) {
            case "wrongSteps":
                JSONArray wrongStepsJSON = new JSONArray();
                for (Step s: scenario.helper.missingActorSteps(scenario.getSteps(), scenario.getActors(), scenario.getSystemActors())){
                    wrongStepsJSON.add(s.getDescription());
                }
                response.put("wrongSteps", wrongStepsJSON);
                break;
            case "countSteps":
                response.put("stepsNumber", scenario.helper.countSteps(scenario.getSteps()));
                break;
            case "countKeyWordSteps":
                response.put("keywordStepsNumber", scenario.helper.countKeyWordSteps(scenario.getSteps()));
                break;
            case "showScenario":
                response.put("steps", scenario.helper.showNumberedScenario(scenario.getSteps(), 0));
                break;
            case "showScenarioWithMaxDepth":
                response.put("steps", scenario.helper.showNumberedScenario(scenario.getSteps(), intParam));
                break;
            default:
                break;
        }
        return response;
    }
}
