package pl.put.poznan.transformer.logic;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class StepTest {
    
    public StepTest() {
    }

    /**
     * Test of getDescription method, of class Step.
     */
    @Test
    public void testGetDescription() {
        Step instance = new Step("Test step description", null);
        String expResult = "Test step description";
        String result = instance.getDescription();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSubsteps method, of class Step.
     */
    @Test
    public void testGetSubsteps() {
        List<Step> steplist = new ArrayList<>();
        steplist.add(new Step("Test substep 1", null));
        steplist.add(new Step("Test substep 2", null));
        Step instance = new Step("Test step", steplist);
        List<Step> expResult = steplist;
        List<Step> result = instance.getSubsteps();
        assertEquals(expResult, result);
    }
}
