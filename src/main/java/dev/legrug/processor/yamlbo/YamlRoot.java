package dev.legrug.processor.yamlbo;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.List;

@RegisterForReflection
public class YamlRoot {

    public String name;
    public SourceInfo sourceInfo;
    public List<Step> steps;
}
