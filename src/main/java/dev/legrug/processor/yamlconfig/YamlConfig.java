package dev.legrug.processor.yamlconfig;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.List;

@RegisterForReflection
public class YamlConfig {

    public String name;
    public SourceInfo sourceInfo;
    public List<Step> steps;
}
