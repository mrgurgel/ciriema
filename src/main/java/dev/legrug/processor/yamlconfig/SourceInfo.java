package dev.legrug.processor.yamlconfig;

import dev.legrug.processor.ProcessBuilderWrapper;

public class SourceInfo {

    public Git git;


    public void process(ProcessBuilderWrapper processBuilder) {
        if(git != null) {
            git.process(processBuilder);
        }
    }
}
