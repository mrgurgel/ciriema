package dev.legrug;

import dev.legrug.processor.CiChainProcessor;
import io.quarkus.runtime.Quarkus;

public class CiChain {


    public static void main(String... args) {
        Quarkus.run(CiChainProcessor.class, args);
    }

}