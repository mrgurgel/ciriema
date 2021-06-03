package dev.legrug.processor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import dev.legrug.processor.yamlconfig.YamlConfig;
import io.quarkus.runtime.QuarkusApplication;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class CiChainProcessor implements QuarkusApplication {

    public int run(String... args) {
        YamlConfig yamlConfig = loadYaml();
        String workingDir = createWorkinDir();

        ProcessBuilderWrapper processBuilder = new ProcessBuilderWrapper(new File(workingDir));

        yamlConfig.sourceInfo.process(processBuilder);
        yamlConfig.steps.forEach(step -> step.process(processBuilder));

        MessageUtils.print(MessageUtils.Emoji.ENTIRE_PROCESS_FINISHED, "CI Pipeline finished with success");
        return 0;
    }

    private String createWorkinDir() {
        return new StringBuilder().append("/tmp/ci-chain/").append(UUID.randomUUID()).toString();
    }

    private YamlConfig loadYaml()  {
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
            return mapper.readValue(new File(System.getProperty("user.home") + "/default-ci.yaml"), YamlConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
