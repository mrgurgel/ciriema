package dev.legrug.processor.yamlbo;

import dev.legrug.processor.ProcessBuilderWrapper;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class SourceInfo {

    public Git git;


    public void process(ProcessBuilderWrapper processBuilder) {
        if(git != null) {
            git.process(processBuilder);
        }
    }
}
