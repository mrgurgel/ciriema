package dev.legrug.processor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import dev.legrug.processor.yamlbo.YamlRoot;
import io.quarkus.runtime.QuarkusApplication;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class CiriemaProcessor implements QuarkusApplication {

    public int run(String... args) {
        YamlRoot yamlRoot = loadYaml();
        String workingDir = createWorkinDir();

        ProcessBuilderWrapper processBuilder = new ProcessBuilderWrapper(new File(workingDir));

        yamlRoot.sourceInfo.process(processBuilder);
        yamlRoot.steps.forEach(step -> step.process(processBuilder));

        MessageUtils.print(MessageUtils.Emoji.ENTIRE_PROCESS_FINISHED, "CI Pipeline finished with success");
        return 0;
    }

    private String createWorkinDir() {
        return new StringBuilder().append("/tmp/ciriema/").append(UUID.randomUUID()).toString();
    }

    private YamlRoot loadYaml()  {
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
            return mapper.readValue(new File(System.getProperty("user.home") + "/default-ci.yaml"), YamlRoot.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
