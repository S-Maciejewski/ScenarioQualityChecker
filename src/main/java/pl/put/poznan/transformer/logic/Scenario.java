package pl.put.poznan.transformer.logic;
import java.util.ArrayList;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <h1>Klasa "Scenario"</h1>
 * Klasa reprezentuje scenariusz działań wysłany przez użytkownika
 * i udostępnia zbiór metod w celu jego przetwarzania
 * @author Jan Techner
 * @version 4.0
 * @since 2018-10-31
 *
 */
public class Scenario {

    /**
     * Scenariusz w postaci JSON
     */
    private JSONObject scenario;

    /**
     * Tytuł scenariusza
     */
    private String title;

    /**
     * Lista aktorów scenariusza
     */
    private List<String> actors;

    /**
     * Lista aktorów systemowych scenariusza
     */
    private List<String> systemActors;

    /**
     * Lista kroków scenariusza
     */
    private List<Step> steps;

    /**
     * <h1>Konstruktor klasy "Scenario"</h1>
     * Konstruktor przetwarza wysłany scenariusz i wyodrębnia elementy takie jak
     * tytuł scenariusza, aktorów i listę kroków
     * @param sentScenario Scenario sent by user
     */
    public Scenario(JSONObject sentScenario) {

        this.scenario = sentScenario;
        this.title = sentScenario.getAsString("title");
        this.actors = readJson("actors");
        this.systemActors = readJson("system_actors");
        this.steps = readJsonSteps();
    }

    /**
     * Metoda zwraca listę wartości z wysłanego scenariusza w postaci JSON
     * @param field Nazwa kolekcji, której wartości mają zostać odczytane
     * @return Lista wartości kolekcji
     */
    private List<String> readJson(String field) {
        List<String> list = new ArrayList<>();
        Object obj = this.scenario.get(field);
        if (obj instanceof String) {
            list.add((String)obj);
        } else if (obj instanceof ArrayList) {
            for (Object o : (ArrayList) obj){
                if (o instanceof String)
                    list.add((String) o);
            }
        }
        return list;
    }

    /**
     * Metoda odczytuje obiekt zawierający kroki z pliku JSON, inicjuje rekursywne odczytanie kroków
     * scenariusza i zwraca ich zagnieżdżoną listę
     * @return Lista kroków scenariusza
     */
    private List<Step> readJsonSteps() {
        Object stepsAsObject = this.scenario.get("steps");
        List<Object> stepsAsObjectsList = new ArrayList<>();
        if (stepsAsObject instanceof ArrayList)
             stepsAsObjectsList.addAll((ArrayList)stepsAsObject);
        return recursiveStepsReader(stepsAsObjectsList);
    }

    /**
     * Metoda rekursywnie odczytuje kroki scenariusza
     * @param steps Lista obiektów zawierających kroki scenariusza
     * @return Lista kroków scenariusza
     */
    private List<Step> recursiveStepsReader(List<Object> steps) {
        List<Step> steplist = new ArrayList<>();
        for (int i=0; i<steps.size(); i++) {
            if (steps.get(i) instanceof String) {
                if (i == steps.size() - 1) {
                    steplist.add(new Step((String) steps.get(i), null));
                } else {
                    if (steps.get(i + 1) instanceof String) {
                        steplist.add(new Step((String) steps.get(i), null));
                    } else if (steps.get(i + 1) instanceof ArrayList) {
                        List<Object> substepsAsObjectList = new ArrayList<>();
                        if (steps.get(i + 1) instanceof ArrayList)
                            substepsAsObjectList.addAll((ArrayList)steps.get(i+1));
                        List<Step> substeps = recursiveStepsReader(substepsAsObjectList);
                        steplist.add(new Step((String) steps.get(i), substeps));
                    }
                }
            }
        }
        return steplist;
    }

    /**
     * Metoda zwraca listę kroków, które nie rozpoczynają się od poprawnego aktora scenariusza
     * @return List<Step> Lista kroków zawierających niepoprawnego aktora
     */
    private List<Step> missingActorSteps() {
        List<Step> wrongSteps = new ArrayList<>();
        if(steps != null){
            for (Step step : steps) {
                step.startsWithActor(wrongSteps, actors, systemActors);
            }
        }
        return wrongSteps;
    }

    /**
     * Metoda zlicza sumaryczną liczbę kroków scenariusza
     * @return int Liczba kroków scenariusza
     */
    private int countSteps() {
        AtomicInteger stepsCounter = new AtomicInteger(0);
        if (steps != null) {
            for (Step step : steps) {
                step.countSteps(stepsCounter);
            }
        }
        return stepsCounter.intValue();
    }

    /**
     * Metoda zlicza liczbę słów kluczowych we wszystkich krokach scenariusza
     * @return int Liczba słów kluczowych
     */
    private int countKeyWordSteps() {
        AtomicInteger stepsCounter = new AtomicInteger(0);
        if (steps != null) {
            for (Step step : steps) {
                step.countKeyWordSteps(stepsCounter);
            }
        }
        return stepsCounter.intValue();
    }

    /**
     * Metoda zwraca kolekcję hierarchicznie ponumerowanych scenariuszy do zadanej głębokości
     * @param depth Głębokość
     * @return JSONArray Lista hierarchicznie ponumerowanych kroków scenariusza
     */
    private JSONArray showNumberedScenario(int depth) {
        JSONArray numberedScenario = new JSONArray();
        List<String> currentNumbers = new ArrayList<>();
        for (int i = 0; i < steps.size(); i++) {
            currentNumbers.add(Integer.toString(i+1));
            steps.get(i).getNumberedScenario(numberedScenario, depth, currentNumbers);
            currentNumbers.clear();
        }
        return numberedScenario;
    }

    /**
     * Metoda zwraca scenariusz
     * @return Sceariusz
     */
    public JSONObject getScenario() {
        return scenario;
    }

    /**
     * Metoda zwraca tytuł scenariusza
     * @return Tytuł scenariusza
     */
    public String getTitle() {
        return title;
    }

    /**
     * Metoda zwraca listę aktorów
     * @return Lista aktorów
     */
    public List<String> getActors() {
        return actors;
    }

    /***
     * Metoda zwraca liczbę aktorów systemowych
     * @return Lista aktorów systemowych
     */
    public List<String> getSystemActors() {
        return systemActors;
    }

    /**
     * Metoda zwraca listę etapów
     * @return Lista etapów
     */
    public List<Step> getSteps() {
        return steps;
    }

    /**
     * Metoda wywołuje odpowiednią funkcję klasy Scenario
     * @param function Wybrana funkcja
     * @param intParam Dodatkowy parametr
     * @return Wynik wybranej funkcji
     */
    public JSONObject triggerFunction(String function, int intParam) {
        JSONObject response = new JSONObject();
        switch (function) {
            case "wrongSteps":
                JSONArray wrongStepsJSON = new JSONArray();
                for (Step s: missingActorSteps()){
                    wrongStepsJSON.add(s.getDescription());
                }
                response.put("wrongSteps", wrongStepsJSON);
                break;
            case "countSteps":
                response.put("stepsNumber", countSteps());
                break;
            case "countKeyWordSteps":
                response.put("keywordStepsNumber", countKeyWordSteps());
                break;
            case "showScenario":
                response.put("steps", showNumberedScenario(0));
                break;
            case "showScenarioWithMaxDepth":
                response.put("steps", showNumberedScenario(intParam));
                break;
            default:
                break;
        }
        return response;
    }
}
