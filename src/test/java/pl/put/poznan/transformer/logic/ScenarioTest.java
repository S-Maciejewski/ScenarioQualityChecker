package pl.put.poznan.transformer.logic;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class ScenarioTest {

    public ScenarioTest() {
    }

    /**
     * Test of getActors method, of class Scenario
     */
    @Test
    public void testGetActors() {
        List<String> actorslist = new ArrayList<>();
        actorslist.add("Aktor1");
        actorslist.add("Aktor2");
        Scenario instance = new Scenario("Tytul", actorslist, null, null);
        List<String> expResult = actorslist;
        List<String> result = instance.getActors();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSystemActors method, of class Scenario
     */
    @Test
    public void testGetSystemActors() {
        List<String> systemactorslist = new ArrayList<>();
        systemactorslist.add("AktorSys1");
        systemactorslist.add("AktorSys2");
        Scenario instance = new Scenario("Tytul", null, systemactorslist, null);
        List<String> expResult = systemactorslist;
        List<String> result = instance.getSystemActors();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSteps method, of class Scenario
     */
    @Test
    public void testGetSteps() {
        List<Step> steplist = new ArrayList<>();
        steplist.add(new Step("AktorSys1 Test substep 1", null));
        steplist.add(new Step("Aktor1 Test substep 2", null));
        Scenario instance = new Scenario("Tytul", null, null, steplist);
        List<Step> expResult = steplist;
        List<Step> result = instance.getSteps();
        assertEquals(expResult, result);
    }
}