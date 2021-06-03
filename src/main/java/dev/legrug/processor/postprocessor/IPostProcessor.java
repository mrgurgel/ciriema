package dev.legrug.processor.postprocessor;

import java.util.Map;

public interface IPostProcessor {

    void doThePostProcessing(String commandsOutput, Map<String, String> postProcessorParams);

}
