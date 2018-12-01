package pl.put.poznan.transformer.logic.visitor;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import pl.put.poznan.transformer.logic.Scenario;
import pl.put.poznan.transformer.logic.Step;

public class WrongStepsVisitorTest {
    
    public WrongStepsVisitorTest() {
    }

    /**
     * Test of visit method, of class WrongStepsVisitor.
     * Testuje poprawność działania funkcji wykrywającej błędne kroki
     * niezaczynające się od poprawnego aktora.
     * Poprawny scenariusz, brak aktora systemowego, brak słów kluczowych
     */
    @Test
    public void test_1_Visit_Scenario() {
        List<String> actorslist = new ArrayList<>();
        actorslist.add("Aktor1");
        List<String> systemactorslist = new ArrayList<>();
        List<Step> steplist = new ArrayList<>();
        steplist.add(new Step("Aktor1 Test substep 1", null));
        steplist.add(new Step("Aktor1 Test substep 2", null));
        List<Step> steps = new ArrayList<>();
        steps.add(new Step("Aktor1 Test step", steplist));
        Scenario scenario = new Scenario("Tytul", actorslist, systemactorslist, steps);
        WrongStepsVisitor instance = new WrongStepsVisitor(actorslist, systemactorslist);
        instance.visit(scenario);
        List<String> expResult = new ArrayList<>();
        List<String> result = instance.getWrongSteps();
        assertEquals(expResult, result);
    }
    
    /**
    * Test of visit method, of class WrongStepsVisitor.
    * Testuje poprawność działania funkcji wykrywającej błędne kroki
    * niezaczynające się od poprawnego aktora.
    * Poprawny scenariusz, tylko aktor systemowy, brak słów kluczowych
    */
    @Test
    public void test_2_Visit_Scenario() {
        List<String> actorslist = new ArrayList<>();
        List<String> systemactorslist = new ArrayList<>();
        systemactorslist.add("AktorSys1");
        List<Step> steplist = new ArrayList<>();
        steplist.add(new Step("AktorSys1 Test substep 1", null));
        steplist.add(new Step("AktorSys1 Test substep 2", null));
        List<Step> steps = new ArrayList<>();
        steps.add(new Step("AktorSys1 Test step", steplist));
        Scenario scenario = new Scenario("Tytul", actorslist, systemactorslist, steps);
        WrongStepsVisitor instance = new WrongStepsVisitor(actorslist, systemactorslist);
        instance.visit(scenario);
        List<String> expResult = new ArrayList<>();
        List<String> result = instance.getWrongSteps();
        assertEquals(expResult, result);
    }
    
    /**
    * Test of visit method, of class WrongStepsVisitor.
    * Testuje poprawność działania funkcji wykrywającej błędne kroki
    * niezaczynające się od poprawnego aktora.
    * Poprawny scenariusz, obaj aktorzy, brak słów kluczowych
    */
    @Test
    public void test_3_Visit_Scenario() {
        List<String> actorslist = new ArrayList<>();
        actorslist.add("Aktor1");
        List<String> systemactorslist = new ArrayList<>();
        systemactorslist.add("AktorSys1");
        List<Step> steplist = new ArrayList<>();
        steplist.add(new Step("AktorSys1 Test substep 1", null));
        steplist.add(new Step("Aktor1 Test substep 2", null));
        List<Step> steps = new ArrayList<>();
        steps.add(new Step("Aktor1 Test step", steplist));
        Scenario scenario = new Scenario("Tytul", actorslist, systemactorslist, steps);
        WrongStepsVisitor instance = new WrongStepsVisitor(actorslist, systemactorslist);
        instance.visit(scenario);
        List<String> expResult = new ArrayList<>();
        List<String> result = instance.getWrongSteps();
        assertEquals(expResult, result);
    }
    
    /**
    * Test of visit method, of class WrongStepsVisitor.
    * Testuje poprawność działania funkcji wykrywającej błędne kroki
    * niezaczynające się od poprawnego aktora.
    * Poprawny scenariusz, obaj aktorzy, obecne słowa kluczowe
    */
    @Test
    public void test_4_Visit_Scenario() {
        List<String> actorslist = new ArrayList<>();
        actorslist.add("Aktor1");
        List<String> systemactorslist = new ArrayList<>();
        systemactorslist.add("AktorSys1");
        List<Step> steplist = new ArrayList<>();
        steplist.add(new Step("AktorSys1 Test substep 1", null));
        steplist.add(new Step("IF: Aktor1 Test substep 2", null));
        steplist.add(new Step("ELSE:", null));
        steplist.add(new Step("FOR EACH: Test substep 2", null));
        List<Step> steps = new ArrayList<>();
        steps.add(new Step("Aktor1 Test step", steplist));
        Scenario scenario = new Scenario("Tytul", actorslist, systemactorslist, steps);
        WrongStepsVisitor instance = new WrongStepsVisitor(actorslist, systemactorslist);
        instance.visit(scenario);
        List<String> expResult = new ArrayList<>();
        List<String> result = instance.getWrongSteps();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of visit method, of class WrongStepsVisitor.
     * Testuje poprawność działania funkcji wykrywającej błędne kroki
     * niezaczynające się od poprawnego aktora.
     * Niepoprawny jeden krok, brak aktora systemowego, brak słów kluczowych
     */
    @Test
    public void test_5_Visit_Scenario() {
        List<String> actorslist = new ArrayList<>();
        actorslist.add("Aktor1");
        List<String> systemactorslist = new ArrayList<>();
        List<Step> steplist = new ArrayList<>();
        steplist.add(new Step("Aktor1 Test substep 1", null));
        steplist.add(new Step("No actor Test substep 2", null));
        List<Step> steps = new ArrayList<>();
        steps.add(new Step("Aktor1 Test step", steplist));
        Scenario scenario = new Scenario("Tytul", actorslist, systemactorslist, steps);
        WrongStepsVisitor instance = new WrongStepsVisitor(actorslist, systemactorslist);
        instance.visit(scenario);
        List<String> expResult = new ArrayList<>();
        expResult.add("No actor Test substep 2");
        List<String> result = instance.getWrongSteps();
        assertEquals(expResult, result);
    }
    
    /**
    * Test of visit method, of class WrongStepsVisitor.
    * Testuje poprawność działania funkcji wykrywającej błędne kroki
    * niezaczynające się od poprawnego aktora.
    * Niepoprawny jeden krok, tylko aktor systemowy, brak słów kluczowych
    */
    @Test
    public void test_6_Visit_Scenario() {
        List<String> actorslist = new ArrayList<>();
        List<String> systemactorslist = new ArrayList<>();
        systemactorslist.add("AktorSys1");
        List<Step> steplist = new ArrayList<>();
        steplist.add(new Step("No actor Test substep 1", null));
        steplist.add(new Step("AktorSys1 Test substep 2", null));
        List<Step> steps = new ArrayList<>();
        steps.add(new Step("AktorSys1 Test step", steplist));
        Scenario scenario = new Scenario("Tytul", actorslist, systemactorslist, steps);
        WrongStepsVisitor instance = new WrongStepsVisitor(actorslist, systemactorslist);
        instance.visit(scenario);
        List<String> expResult = new ArrayList<>();
        expResult.add("No actor Test substep 1");
        List<String> result = instance.getWrongSteps();
        assertEquals(expResult, result);
    }
    
    /**
    * Test of visit method, of class WrongStepsVisitor.
    * Testuje poprawność działania funkcji wykrywającej błędne kroki
    * niezaczynające się od poprawnego aktora.
    * Niepoprawny jeden krok, obaj aktorzy, brak słów kluczowych
    */
    @Test
    public void test_7_Visit_Scenario() {
        List<String> actorslist = new ArrayList<>();
        actorslist.add("Aktor1");
        List<String> systemactorslist = new ArrayList<>();
        systemactorslist.add("AktorSys1");
        List<Step> steplist = new ArrayList<>();
        steplist.add(new Step("AktorSys1 Test substep 1", null));
        steplist.add(new Step("No actor Test substep 2", null));
        List<Step> steps = new ArrayList<>();
        steps.add(new Step("Aktor1 Test step", steplist));
        Scenario scenario = new Scenario("Tytul", actorslist, systemactorslist, steps);
        WrongStepsVisitor instance = new WrongStepsVisitor(actorslist, systemactorslist);
        instance.visit(scenario);
        List<String> expResult = new ArrayList<>();
        expResult.add("No actor Test substep 2");
        List<String> result = instance.getWrongSteps();
        assertEquals(expResult, result);
    }
    
    /**
    * Test of visit method, of class WrongStepsVisitor.
    * Testuje poprawność działania funkcji wykrywającej błędne kroki
    * niezaczynające się od poprawnego aktora.
    * Niepoprawny jeden krok, obaj aktorzy, obecne słowa kluczowe
    */
    @Test
    public void test_8_Visit_Scenario() {
        List<String> actorslist = new ArrayList<>();
        actorslist.add("Aktor1");
        List<String> systemactorslist = new ArrayList<>();
        systemactorslist.add("AktorSys1");
        List<Step> steplist = new ArrayList<>();
        steplist.add(new Step("AktorSys1 Test substep 1", null));
        steplist.add(new Step("IF: No Actor Test substep 2", null));
        steplist.add(new Step("ELSE:", null));
        steplist.add(new Step("FOR EACH: Test substep 2", null));
        List<Step> steps = new ArrayList<>();
        steps.add(new Step("Aktor1 Test step", steplist));
        Scenario scenario = new Scenario("Tytul", actorslist, systemactorslist, steps);
        WrongStepsVisitor instance = new WrongStepsVisitor(actorslist, systemactorslist);
        instance.visit(scenario);
        List<String> expResult = new ArrayList<>();
        expResult.add("IF: No Actor Test substep 2");
        List<String> result = instance.getWrongSteps();
        assertEquals(expResult, result);
    }
  
    /**
    * Test of visit method, of class WrongStepsVisitor.
    * Testuje poprawność działania funkcji wykrywającej błędne kroki
    * niezaczynające się od poprawnego aktora.
    * Niepoprawne wiele kroków, obaj aktorzy, nieobecne słowa kluczowe
    */
    @Test
    public void test_9_Visit_Scenario() {
        List<String> actorslist = new ArrayList<>();
        actorslist.add("Aktor1");
        List<String> systemactorslist = new ArrayList<>();
        systemactorslist.add("AktorSys1");
        List<Step> steplist = new ArrayList<>();
        steplist.add(new Step("AktorSys1 Test substep 1", null));
        steplist.add(new Step("Aktor1 Test substep 2", null));
        steplist.add(new Step("No actor Test substep 3", null));
        List<Step> steps = new ArrayList<>();
        steps.add(new Step("Aktor1 Test step 1", steplist));
        steps.add(new Step("No actor Test step 2", null));
        Scenario scenario = new Scenario("Tytul", actorslist, systemactorslist, steps);
        WrongStepsVisitor instance = new WrongStepsVisitor(actorslist, systemactorslist);
        instance.visit(scenario);
        List<String> expResult = new ArrayList<>();
        expResult.add("No actor Test substep 3");
        expResult.add("No actor Test step 2");
        List<String> result = instance.getWrongSteps();
        assertEquals(expResult, result);
    }
    
    /**
    * Test of visit method, of class WrongStepsVisitor.
    * Testuje poprawność działania funkcji wykrywającej błędne kroki
    * niezaczynające się od poprawnego aktora.
    * Niepoprawne wiele kroków, obaj aktorzy, obecne słowa kluczowe
    */
    @Test
    public void test_10_Visit_Scenario() {
        List<String> actorslist = new ArrayList<>();
        actorslist.add("Aktor1");
        List<String> systemactorslist = new ArrayList<>();
        systemactorslist.add("AktorSys1");
        List<Step> steplist = new ArrayList<>();
        steplist.add(new Step("AktorSys1 Test substep 1", null));
        steplist.add(new Step("IF: No Actor Test substep 2", null));
        steplist.add(new Step("ELSE:", null));
        steplist.add(new Step("FOR EACH: Test substep 2", null));
        List<Step> steps = new ArrayList<>();
        steps.add(new Step("Aktor1 Test step", steplist));
        steps.add(new Step("No actor Test step 2", null));
        Scenario scenario = new Scenario("Tytul", actorslist, systemactorslist, steps);
        WrongStepsVisitor instance = new WrongStepsVisitor(actorslist, systemactorslist);
        instance.visit(scenario);
        List<String> expResult = new ArrayList<>();
        expResult.add("IF: No Actor Test substep 2");
        expResult.add("No actor Test step 2");
        List<String> result = instance.getWrongSteps();
        assertEquals(expResult, result);
    }
}
