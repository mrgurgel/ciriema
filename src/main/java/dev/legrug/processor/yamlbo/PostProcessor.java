package dev.legrug.processor.yamlbo;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.Map;
@RegisterForReflection
public class PostProcessor {

    public String name;
    public Map<String, String> spec;
}
