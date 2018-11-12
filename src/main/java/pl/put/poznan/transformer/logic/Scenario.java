package pl.put.poznan.transformer.logic;
import java.util.ArrayList;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Klasa "Scenario"
 */
public class Scenario {

    private JSONObject scenario;

    private String title;
    private List<String> actors;
    private List<String> systemActors;

    private List<Step> steps;

    /**
     * Konstruktor obiektów klasy "Scenario"
     * @param sendscenario Scenariusz do przetworzenia
     */
    public Scenario(JSONObject sendscenario) {

        this.scenario = sendscenario;
        this.title = sendscenario.getAsString("title");
        this.actors = readJson("actors");
        this.systemActors = readJson("system_actors");
        this.steps = readJsonSteps();
    }

    /**
     * Metoda zwraca listę wartości z danej kolekcji
     * @param field Kolekcja, dla której wartości mają zostać odczytane
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
     * Metoda zwracająca listę "steps"
     * @return Lista "steps"
     */
    private List<Step> readJsonSteps() {
        Object stepsAsObject = this.scenario.get("steps");
        List<Object> stepsAsObjectsList = new ArrayList<>();
        if (stepsAsObject instanceof ArrayList)
             stepsAsObjectsList.addAll((ArrayList)stepsAsObject);
        return recursiveStepsReader(stepsAsObjectsList);
    }

    /**
     * Rekurencyjne odczytywanie (przetworzenie) "steps"
     * @param steps Lista obiektów z kolekcji "steps"
     * @return Przetworzona lista "steps"
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
     * Metoda zwraca "wrongSteps"
     * @return "wrongSteps"
     */
    public List<Step> missingActorSteps() {
        List<Step> wrongSteps = new ArrayList<>();
        if(steps != null){
            for (Step step : steps) {
                step.startsWithActor(wrongSteps, actors, systemActors);
            }
        }
        return wrongSteps;
    }
<<<<<<< HEAD
    
    private int countSteps() {
=======

    /**
     * Metoda liczy liczbę etapów
     * @return Liczba etapów
     */
    public int countSteps() {
>>>>>>> b1af08a671051a37bcc0464de60de51d3119df4d
        AtomicInteger stepsCounter = new AtomicInteger(0);
        if (steps != null) {
            for (Step step : steps) {
                step.countSteps(stepsCounter);
            }
        }
        return stepsCounter.intValue();
    }
<<<<<<< HEAD
    
    private int countKeyWordSteps() {
=======

    /**
     * Meotda liczy liczbę słów kluczowych w poszczególnych etapach
     * @return Liczba słów kluczowych
     */
    public int countKeyWordSteps() {
>>>>>>> b1af08a671051a37bcc0464de60de51d3119df4d
        AtomicInteger stepsCounter = new AtomicInteger(0);
        if (steps != null) {
            for (Step step : steps) {
                step.countKeyWordSteps(stepsCounter);
            }
        }
        return stepsCounter.intValue();
    }

<<<<<<< HEAD
    private JSONArray showNumberedScenario(int depth) {
=======
    /**
     * Tworzy kolekcję hierarchicznie ponumerowanych scenariuszy do zadanej głębokości
     * @param depth Głębokość
     * @return Hierarchicznie ponumerowane scenariusze
     */
    public JSONArray showNumberedScenario(int depth) {
>>>>>>> b1af08a671051a37bcc0464de60de51d3119df4d
        JSONArray numberedScenario = new JSONArray();
        List<String> currentNumbers = new ArrayList<>();
        for (int i = 0; i < steps.size(); i++) {
            currentNumbers.add(Integer.toString(i+1));
            steps.get(i).getNumberedScenario(numberedScenario, depth, currentNumbers);
            currentNumbers.clear();
        }
        return numberedScenario;
    }

<<<<<<< HEAD
=======
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
     * Metoda zwraca obiekt kolekcji utworzony wybraną funkcją
     * @param function Wybrana funkcja
     * @param intParam Dodatkowy parametr
     * @return Wynik wybranej funkcji
     */
>>>>>>> b1af08a671051a37bcc0464de60de51d3119df4d
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
