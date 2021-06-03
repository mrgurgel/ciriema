package dev.legrug.processor.yamlconfig;

import dev.legrug.processor.ExecutionResult;
import dev.legrug.processor.MessageUtils;
import dev.legrug.processor.ProcessBuilderWrapper;
import dev.legrug.processor.cdi.NamedLiteral;
import dev.legrug.processor.postprocessor.CoveragePostProcessor;
import dev.legrug.processor.postprocessor.IPostProcessor;
import io.quarkus.runtime.annotations.RegisterForReflection;

import javax.enterprise.inject.spi.CDI;
import java.util.List;
@RegisterForReflection
public class Step {

    public String name;
    public String runIn;
    public String dockerImage;
    public boolean disabled;
    public List<String> commands;
    public List<PostProcessor> postProcessors;

    public void process(ProcessBuilderWrapper processBuilder) {
        if(!disabled) {
            boolean hasPostProcessors = postProcessors != null && !postProcessors.isEmpty();
            StringBuilder outputOfCommands = new StringBuilder();
            commands.forEach(command ->  {
                ExecutionResult executionResult = executeCommand(processBuilder, command);
                if(hasPostProcessors) {
                    outputOfCommands.append(executionResult.message);
                }
            });

            postProcessors.forEach(postProcessor ->
            {
                    MessageUtils.print(MessageUtils.Emoji.SUB_STARTING, "Starting the post processor: " + postProcessor.name);
                    CDI.current().select(IPostProcessor.class, new NamedLiteral(postProcessor.name)).get()
                            .doThePostProcessing(outputOfCommands.toString(), postProcessor.spec);
                    MessageUtils.print(MessageUtils.Emoji.FINISHED,"");
            });


        }
    }

    private ExecutionResult executeCommand(ProcessBuilderWrapper processBuilder, String command) {
        String message = new StringBuilder().append("STEP: ").append(name).append(", COMMAND: ").append(command).toString();
        MessageUtils.print(MessageUtils.Emoji.STARTING, message);
        ExecutionResult executionResult = processBuilder.executeAndWait(command.split(" "));
        MessageUtils.print(MessageUtils.Emoji.FINISHED, executionResult.prettyPrintDuration());
        return executionResult;


    }

}
