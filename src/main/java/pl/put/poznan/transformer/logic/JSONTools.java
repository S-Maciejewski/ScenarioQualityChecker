package pl.put.poznan.transformer.logic;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa "JSONTools" zawierająca metody przetwarzające i
 * odczytujące obiekty w formacie JSON
 */
class JSONTools {

    /**
     * Metoda zwraca listę wartości z wybranej właściwości pliku w formacie JSON
     * @param obj Plik w formacie JSON, którego właściwość ma zostać odczytana
     * @param field Nazwa właściwości, której wartości mają zostać odczytane
     * @return Lista wartości kolekcji
     */
    static List<String> getProperty(JSONObject obj, String field) {
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
     * Metoda zwraca kroki jako listę obiektów
     * @param scenario Scenariusz, z którego odczytywane są kroki
     * @return Lista obiektów, zawierających kroki lub listy kroków
     */
    static List<Object> getObjectsList(JSONObject scenario, String field) {
        return new ArrayList<Object>((ArrayList) scenario.get(field));
    }


    /**
     * Metoda zwraca rekursyjnie kroki scenariusza
     * @param steps Lista kroków i list kroków do przetworzenia
     * @return Zagnieżdżona lista kroków
     */
    static List<Step> getStepsRecursively(List<Object> steps) {
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
     * Metoda konwertująca listę obiektów na listę formacie JSON
     * @param list Lista obiektów do skonwertowania
     * @return JSONArray Lista w formacie JSON
     */
    static JSONArray toJSONArray(List<?> list) {
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
