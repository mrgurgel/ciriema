package dev.legrug;

import dev.legrug.processor.CiriemaProcessor;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class Ciriema {

    public static void main(String... args) {
        Quarkus.run(CiriemaProcessor.class, args);
    }

}