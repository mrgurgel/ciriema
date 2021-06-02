package dev.legrug.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import dev.legrug.processor.yamlconfig.YamlConfig;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@QuarkusMain
public class CiChainProcessor implements QuarkusApplication {

    public int run(String... args) {
        YamlConfig yamlConfig = loadYaml();

        String workingDir = createWorkinDir();

        ProcessBuilderWrapper processBuilder = new ProcessBuilderWrapper(new File(workingDir));

        yamlConfig.sourceInfo.process(processBuilder);

        yamlConfig.steps.forEach(step ->
            step.commands.forEach(command ->
            {
                String message = new StringBuilder().append("STEP: ").append(step.name).append(" COMMAND: ").append(command).toString();
                MessageUtils.print(MessageUtils.Emoji.STARTING, message);
                processBuilder.executeAndWait(command.split(" "));
                MessageUtils.print(MessageUtils.Emoji.FINISHED, message);
            })
        );


        processBuilder.executeAndWait("rm", "-rf", workingDir);
        return 0;
    }

    private String createWorkinDir() {
        return new StringBuilder().append("/tmp/ci-chain/").append(UUID.randomUUID()).toString();
    }

    private YamlConfig loadYaml()  {

        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

            return mapper.readValue(new File("/Users/mrgurgel/git/ci-chain/src/main/resources/default-ci.yaml"), YamlConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
