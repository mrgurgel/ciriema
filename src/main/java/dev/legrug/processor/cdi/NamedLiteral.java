package dev.legrug.processor.cdi;

import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Named;

public class NamedLiteral  extends AnnotationLiteral<Named> implements Named {

    String chaveParaDestino;

    public NamedLiteral(String chaveParaDestino) {
        this.chaveParaDestino = chaveParaDestino;
    }

    @Override public String value() {
        return this.chaveParaDestino;
    }
}