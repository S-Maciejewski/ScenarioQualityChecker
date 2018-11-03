package pl.put.poznan.transformer.logic;

import net.minidev.json.JSONArray;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Step {

    private List<Step> substeps;
    private String description;

    public Step(String description, List<Step> substeps) {
        this.substeps = substeps;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void startsWithActor(List<Step> wrongSteps, List<String> actors, List<String> systemActors) {
        if(!(description.startsWith("ELSE:") || description.startsWith("FOR EACH:"))) {
            description = (description.replaceAll("IF:", "")).trim();
            Boolean correct = false;
            if(actors != null){
                for (String actor : actors) {
                    if(description.startsWith(actor)) {
                        correct = true;
                        break;
                    }
                }
            }
            if(!correct && systemActors != null){
                for (String systemActor : systemActors) {
                    if(description.startsWith(systemActor)) {
                        correct = true;
                        break;
                    }
                }
            }
            if(!correct){
                wrongSteps.add(this);
            }
        }
        checkSubstepsSWA(wrongSteps, actors, systemActors);
    }
    
    public void checkSubstepsSWA(List<Step> wrongSteps, List<String> actors, List<String> systemActors) {
        if(substeps != null) {
                for (Step step : substeps) {
                    step.startsWithActor(wrongSteps, actors, systemActors);
            }
        }
    }

    void countSteps(AtomicInteger stepsCounter) {
        if(substeps != null) {
                for (Step step : substeps) {
                    step.countSteps(stepsCounter);
            }
        }
        stepsCounter.addAndGet(1);
    }
    
    void countKeyWordSteps(AtomicInteger stepsCounter) {
        if(substeps != null) {
            for (Step step : substeps) {
                step.countKeyWordSteps(stepsCounter);
            }
        }
        if(description.startsWith("ELSE:") || description.startsWith("FOR EACH") || description.startsWith("IF:")) {
            stepsCounter.addAndGet(1);
        }
    }

    public void getNumberedScenario(JSONArray numberedScenario, int maxdepth, List<String> currentNumbers) {
        if (maxdepth >= currentNumbers.size() || maxdepth == 0) {
            numberedScenario.add(String.join(".", currentNumbers) + ". " + this.description);
            if (this.substeps != null) {
                for (int i = 0; i < this.substeps.size(); i++) {
                    currentNumbers.add(currentNumbers.size(), Integer.toString(i+1) );
                    this.substeps.get(i).getNumberedScenario(numberedScenario, maxdepth, currentNumbers);
                    currentNumbers.remove(currentNumbers.size() - 1);
                }
            }
        }
    }

    //prefix pozwala na hierarchiczny podgląd wszystkich kroków - domyślnie ""
    public void showRecursively(String prefix) {
        prefix += "  ";
        System.out.println(prefix + this.description);
        if (this.substeps != null) {
            for (Step s: this.substeps) {
                s.showRecursively(prefix);
            }
        }
    }

    
}
