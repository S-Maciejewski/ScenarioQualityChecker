package pl.put.poznan.transformer.logic.visitor;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import pl.put.poznan.transformer.logic.Scenario;
import pl.put.poznan.transformer.logic.Step;

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
}