package pl.put.poznan.transformer.logic;

import java.util.List;

public class Step {

    private List<Step> substeps;

    public Step(List<Step> substeps) {
        this.substeps = substeps;
    }
}
