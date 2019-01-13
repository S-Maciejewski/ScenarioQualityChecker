package pl.put.poznan.transformer.logic.visitor.stepVisitor;

import org.junit.Test;
import pl.put.poznan.transformer.logic.Step;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CheckActorVisitorTest {

    @Test
    public void visit1_mock1() {
        Step mockObj = mock(Step.class);
        when(mockObj.getDescription()).thenReturn("Uzytkownik zamyka okno");
        CheckActorVisitor testObj = new CheckActorVisitor(new ArrayList<String>(
                Arrays.asList("Uzytkownik", "Admin")), new ArrayList<String>(
                Arrays.asList("System")));
        testObj.visit(mockObj);
        assertEquals(true, testObj.hasValidActor());
    }

    @Test
    public void visit_mock2() {
        Step mockObj = mock(Step.class);
        when(mockObj.getDescription()).thenReturn("System wyswietla formularz");
        CheckActorVisitor testObj = new CheckActorVisitor(new ArrayList<String>(
                Arrays.asList("Uzytkownik", "Admin")), new ArrayList<String>(
                Arrays.asList("System")));
        testObj.visit(mockObj);
        assertEquals(true, testObj.hasValidActor());
    }

    @Test
    public void visit_mock3() {
        Step mockObj = mock(Step.class);
        when(mockObj.getDescription()).thenReturn("Pojawia sie alert o bledzie");
        CheckActorVisitor testObj = new CheckActorVisitor(new ArrayList<String>(
                Arrays.asList("Uzytkownik", "Admin")), new ArrayList<String>(
                Arrays.asList("System")));
        testObj.visit(mockObj);
        assertEquals(false, testObj.hasValidActor());
    }

    @Test
    public void visit_mock4() {
        Step mockObj = mock(Step.class);
        when(mockObj.getDescription()).thenReturn("FOR EACH: dokument");
        CheckActorVisitor testObj = new CheckActorVisitor(new ArrayList<String>(
                Arrays.asList("Uzytkownik", "Admin")), new ArrayList<String>(
                Arrays.asList("System")));
        testObj.visit(mockObj);
        assertEquals(true, testObj.hasValidActor());
    }

}