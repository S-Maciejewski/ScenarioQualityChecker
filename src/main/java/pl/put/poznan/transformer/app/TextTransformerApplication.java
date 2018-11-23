package pl.put.poznan.transformer.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"pl.put.poznan.transformer.rest"})


/**
 * <h1>Klasa "TextTransformerApplication"</h1>
 * Klasa, która inicjuje Springa
 */
public class TextTransformerApplication {

    /**
    * Metoda inicjująca Springa
    */
    public static void main(String[] args) {
        SpringApplication.run(TextTransformerApplication.class, args);
    }
}
