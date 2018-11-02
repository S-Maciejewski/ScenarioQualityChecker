package pl.put.poznan.transformer.logic;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Step {

    private List<Step> substeps;
    private String description;

    public Step(String description, List<Step> substeps) {
        this.substeps = substeps;
        this.description = description;
    }
    
    public void startsWithActor(List<Step> wrongSteps, List<String> actors, List<String> systemActors) {
        Boolean correct = false;
        String[] keyWords = {"FOR EACH:", "IF:", "ELSE:"};
        for (String keyWord : keyWords) {
            description = description.replaceAll(keyWord, "");
        }
        description = description.trim();
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
