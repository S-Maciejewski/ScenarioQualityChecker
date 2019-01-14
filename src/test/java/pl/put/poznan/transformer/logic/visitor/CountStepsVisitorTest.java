package pl.put.poznan.transformer.logic.visitor;

import org.junit.Test;
import pl.put.poznan.transformer.logic.Scenario;
import pl.put.poznan.transformer.logic.Step;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CountStepsVisitorTest {

    public CountStepsVisitorTest() {
    }

    /**
     * Test of visit method, of class CountStepsVisitor.
     * Testuje poprawność działania funkcji zwracającej liczbę kroków.
     * Pusty scenariusz
     */
    @Test
    public void test_1() {
        List<Step> steplist = new ArrayList<>();
        Scenario scenario = new Scenario("Tytul", null, null, steplist);
        CountStepsVisitor instance = new CountStepsVisitor("all steps");
        instance.visit(scenario);
        int expResult = 0;
        int result = instance.getStepsCounter();
        assertEquals(expResult, result);
    }

    /**
     * Test of visit method, of class CountStepsVisitor.
     * Testuje poprawność działania funkcji zwracającej liczbę kroków.
     * Scenariusz z jednym krokiem
     */
    @Test
    public void test_2() {
        List<Step> steplist = new ArrayList<>();
        steplist.add(new Step("Aktor1 Test substep 1", null));
        Scenario scenario = new Scenario("Tytul", null, null, steplist);
        CountStepsVisitor instance = new CountStepsVisitor("all steps");
        instance.visit(scenario);
        int expResult = 1;
        int result = instance.getStepsCounter();
        assertEquals(expResult, result);
    }

    /**
     * Test of visit method, of class CountStepsVisitor.
     * Testuje poprawność działania funkcji zwracającej liczbę kroków.
     * Scenariusz z dziesięcioma krokami
     */
    @Test
    public void test_3() {
        List<Step> steplist = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            String krok = "Aktor1 Test" + i + " substep";
            steplist.add(new Step(krok, null));
        }
        Scenario scenario = new Scenario("Tytul", null, null, steplist);
        CountStepsVisitor instance = new CountStepsVisitor("all steps");
        instance.visit(scenario);
        int expResult = 10;
        int result = instance.getStepsCounter();
        assertEquals(expResult, result);
    }

    /**
     * Test of visit method, of class CountStepsVisitor.
     * Testuje poprawność działania funkcji zwracającej liczbę kroków.
     * Scenariusz z kilkoma krokami, słowa kluczowe
     */
    @Test
    public void test_4() {
        List<Step> steplist = new ArrayList<>();
        steplist.add(new Step("AktorSys1 Test substep 1", null));
        steplist.add(new Step("R_IF: Aktor1 Test substep 2", null));
        steplist.add(new Step("R_ELSE:", null));
        steplist.add(new Step("R_FOR EACH: Test substep 2", null));
        steplist.add(new Step("R_IF: Aktor1 Test substep 3", null));
        steplist.add(new Step("R_ELSE:", null));
        steplist.add(new Step("R_FOR EACH: Test substep 3", null));
        steplist.add(new Step("AktorSys1 Test substep 4", null));
        List<Step> steps = new ArrayList<>();
        steps.add(new Step("Aktor1 Test step", steplist));
        Scenario scenario = new Scenario("Tytul", null, null, steps);
        CountStepsVisitor instance = new CountStepsVisitor("keyword steps");
        instance.visit(scenario);
        int expResult = 0;
        int result = instance.getStepsCounter();
        assertEquals(expResult, result);
    }

    /**
     * Test of visit method, of class CountStepsVisitor.
     * Testuje poprawność działania funkcji zwracającej liczbę kroków.
     * Scenariusz z kilkoma krokami, słowa kluczowe
     */
    @Test
    public void test_5() {
        List<Step> steplist = new ArrayList<>();
        steplist.add(new Step("AktorSys1 Test substep 1", null));
        steplist.add(new Step("IF: Aktor1 Test substep 2", null));
        steplist.add(new Step("ELSE:", null));
        steplist.add(new Step("FOR EACH: Test substep 2", null));
        steplist.add(new Step("IF: Aktor1 Test substep 3", null));
        steplist.add(new Step("ELSE:", null));
        steplist.add(new Step("FOR EACH: Test substep 3", null));
        steplist.add(new Step("AktorSys1 Test substep 4", null));
        List<Step> steps = new ArrayList<>();
        steps.add(new Step("Aktor1 Test step", steplist));
        Scenario scenario = new Scenario("Tytul", null, null, steps);
        CountStepsVisitor instance = new CountStepsVisitor("keyword steps");
        instance.visit(scenario);
        int expResult = 6;
        int result = instance.getStepsCounter();
        assertEquals(expResult, result);
    }

    @Test
    public void test_mock1() {
        Step mockObj = mock(Step.class);
        List<Step> steps = null;
        when(mockObj.getSubsteps()).thenReturn(steps);
        when(mockObj.getDescription()).thenReturn("description");
        CountStepsVisitor testObj = new CountStepsVisitor("all steps");
        testObj.visit(mockObj);
        assertEquals(1, testObj.getStepsCounter());
    }

    @Test
    public void test_mock2() {
        Step mockObj = mock(Step.class);
        List<Step> steps = new ArrayList<>(
                Arrays.asList(new Step("description1", null), new Step("description2", new ArrayList<>(
                        Arrays.asList(new Step("description3", null))))));
        when(mockObj.getSubsteps()).thenReturn(steps);
        when(mockObj.getDescription()).thenReturn("description");
        CountStepsVisitor testObj = new CountStepsVisitor("all steps");
        testObj.visit(mockObj);
        assertEquals(4, testObj.getStepsCounter());
    }

    @Test
    public void test_mock3() {
        Step mockObj = mock(Step.class);
        when(mockObj.getDescription()).thenReturn("description");
        when(mockObj.getSubsteps()).thenReturn(null);
        CountStepsVisitor testObj = new CountStepsVisitor("keyword steps");
        testObj.visit(mockObj);
        assertEquals(0, testObj.getStepsCounter());
    }


    @Test
    public void test_mock4() {
        Step mockObj = mock(Step.class);
        when(mockObj.getDescription()).thenReturn("description");
        List<Step> steps = new ArrayList<>(
                Arrays.asList(new Step("FOR EACH: description1", null), new Step("ELSE: description2", new ArrayList<>(
                        Arrays.asList(new Step("IF: description3", null))))));
        when(mockObj.getSubsteps()).thenReturn(steps);
        CountStepsVisitor testObj = new CountStepsVisitor("keyword steps");
        testObj.visit(mockObj);
        assertEquals(3, testObj.getStepsCounter());
    }

    @Test
    public void test_mock5() {
        Step mockObj = mock(Step.class);
        when(mockObj.getDescription()).thenReturn("description");
        List<Step> steps = new ArrayList<>(
                Arrays.asList(new Step("IF: description1", null), new Step("ELSE:", new ArrayList<>(
                        Arrays.asList(new Step("FOREACH: no space", null))))));
        when(mockObj.getSubsteps()).thenReturn(steps);
        CountStepsVisitor testObj = new CountStepsVisitor("keyword steps");
        testObj.visit(mockObj);
        assertEquals(2, testObj.getStepsCounter());
    }

    @Test
    public void test_mock6() {
        Step mockObj = mock(Step.class);
        when(mockObj.getDescription()).thenReturn("description");
        List<Step> steps = new ArrayList<>(
                Arrays.asList(new Step("FOR EACH: description1", null), new Step("ELSE:", new ArrayList<>(
                        Arrays.asList(new Step("IF: description3", null), new Step("ELSE with no ':'", null))))));
        when(mockObj.getSubsteps()).thenReturn(steps);
        CountStepsVisitor testObj = new CountStepsVisitor("keyword steps");
        testObj.visit(mockObj);
        assertEquals(3, testObj.getStepsCounter());
    }
}