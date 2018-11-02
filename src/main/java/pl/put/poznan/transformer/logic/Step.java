package pl.put.poznan.transformer.logic;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Step {

    private List<Step> substeps;
    private String stepDescription;

    public Step(List<Step> substeps, String stepDescription) {
        this.substeps = substeps;
        this.stepDescription = stepDescription;
    }
    
    public void startsWithActor(List<Step> wrongSteps, List<String> actors, List<String> systemActors){
        Boolean correct = false;
        String[] keyWords = {"FOR EACH:", "IF:", "ELSE:"};
        for (String keyWord : keyWords) {
            stepDescription = stepDescription.replaceAll(keyWord, "");
        }
        stepDescription = stepDescription.trim();
        if(actors != null){
            for (String actor : actors) {
                if(stepDescription.startsWith(actor)) {
                    correct = true;
                    break;
                }
            }
        }
        if(!correct && systemActors != null){
            for (String systemActor : systemActors) {
                if(stepDescription.startsWith(systemActor)) {
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
    
    public void checkSubstepsSWA(List<Step> wrongSteps, List<String> actors, List<String> systemActors){
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
    
    
}
