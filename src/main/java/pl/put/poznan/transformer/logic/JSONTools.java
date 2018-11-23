package pl.put.poznan.transformer.logic;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Zawierająca metody przetwarzające i odczytujące obiekty w formacie JSON
 */
public class JSONTools {

    /**
     * Zwraca listę wartości z wybranej właściwości pliku w formacie JSON
     * @param obj plik w formacie JSON, którego właściwość ma zostać odczytana
     * @param field nazwa właściwości
     * @return Lista wartości kolekcji
     */
    public static List<String> getProperty(JSONObject obj, String field) {
        List<String> list = new ArrayList<>();
        Object propertyValue = obj.get(field);
        if (propertyValue instanceof String)
            list.add((String) propertyValue);
        else if (propertyValue instanceof ArrayList)
            for (Object item : (ArrayList) propertyValue)
                if (item instanceof String)
                    list.add((String) item);
        return list;
    }


    /**
     * Zwraca kroki jako listę obiektów
     * @param scenario scenariusz, z którego odczytywane są kroki
     * @return Lista obiektów, zawierających kroki i/lub listy kroków
     */
    public static List<Object> getObjectsList(JSONObject scenario) {
        return new ArrayList<Object>((ArrayList) scenario.get("steps"));
    }


    /**
     * Odczytuje rekursywnie kroki scenariusza
     * @param steps lista kroków do przetworzenia
     * @return Zagnieżdżona lista kroków
     */
    public static List<Step> getStepsRecursively(List<Object> steps) {
        List<Step> steplist = new ArrayList<>();
        for (int i=0; i<steps.size(); i++)
            if (steps.get(i) instanceof String) {
                if (i == steps.size() - 1) {
                    steplist.add(new Step((String) steps.get(i), null));
                } else {
                    if (steps.get(i + 1) instanceof String) {
                        steplist.add(new Step((String) steps.get(i), null));
                    } else if (steps.get(i + 1) instanceof ArrayList) {
                        List<Object> substepsAsObjectList = new ArrayList<>();
                        if (steps.get(i + 1) instanceof ArrayList)
                            substepsAsObjectList.addAll((ArrayList) steps.get(i + 1));
                        List<Step> substeps = getStepsRecursively(substepsAsObjectList);
                        steplist.add(new Step((String) steps.get(i), substeps));
                    }
                }
            }
        return steplist;
    }


    /**
     * Konwertuje listę obiektów na listę w formacie JSON
     * @param list lista obiektów do skonwertowania
     * @return Lista w formacie JSON
     */
    public static JSONArray toJSONArray(List<?> list) {
        JSONArray jsonArray = new JSONArray();
        for (Object obj: list) {
            if (obj instanceof Step)
                jsonArray.add(((Step) obj).getDescription());
            if (obj instanceof String)
                jsonArray.add(obj);
        }
        return jsonArray;
    }
}
