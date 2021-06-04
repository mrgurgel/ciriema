package dev.legrug.processor.postprocessor;

import dev.legrug.processor.CiriemaException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class CoveragePostProcessorTest {

    private static String EXAMPLE_OUTPUT = "/Library/Java/JavaVirtualMachines/jdk-11.0.7.jdk/Contents/Home/bin/java -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:50606,suspend=y,server=n -javaagent:/Users/mrgurgel/Library/Caches/JetBrains/IntelliJIdea2021.1/captureAgent/debugger-agent.jar -Dfile.encoding=UTF-8 -classpath /Users/mrgurgel/git/ciriema/target/classes:/Users/mrgurgel/.m2/repository/io/quarkus/quarkus-arc/1.13.6.Final/quarkus-arc-1.13.6.Final.jar:/Users/mrgurgel/.m2/repository/io/quarkus/arc/arc/1.13.6.Final/arc-1.13.6.Final.jar:/Users/mrgurgel/.m2/repository/jakarta/enterprise/jakarta.enterprise.cdi-api/2.0.2/jakarta.enterprise.cdi-api-2.0.2.jar:/Users/mrgurgel/.m2/repository/jakarta/el/jakarta.el-api/3.0.3/jakarta.el-api-3.0.3.jar:/Users/mrgurgel/.m2/repository/jakarta/interceptor/jakarta.interceptor-api/1.2.5/jakarta.interceptor-api-1.2.5.jar:/Users/mrgurgel/.m2/repository/jakarta/annotation/jakarta.annotation-api/1.3.5/jakarta.annotation-api-1.3.5.jar:/Users/mrgurgel/.m2/repository/jakarta/transaction/jakarta.transaction-api/1.3.3/jakarta.transaction-api-1.3.3.jar:/Users/mrgurgel/.m2/repository/org/jboss/logging/jboss-logging/3.4.1.Final/jboss-logging-3.4.1.Final.jar:/Users/mrgurgel/.m2/repository/io/quarkus/quarkus-core/1.13.6.Final/quarkus-core-1.13.6.Final.jar:/Users/mrgurgel/.m2/repository/jakarta/inject/jakarta.inject-api/1.0/jakarta.inject-api-1.0.jar:/Users/mrgurgel/.m2/repository/io/quarkus/quarkus-ide-launcher/1.13.6.Final/quarkus-ide-launcher-1.13.6.Final.jar:/Users/mrgurgel/.m2/repository/io/quarkus/quarkus-development-mode-spi/1.13.6.Final/quarkus-development-mode-spi-1.13.6.Final.jar:/Users/mrgurgel/.m2/repository/io/smallrye/config/smallrye-config/1.13.1/smallrye-config-1.13.1.jar:/Users/mrgurgel/.m2/repository/org/eclipse/microprofile/config/microprofile-config-api/1.4/microprofile-config-api-1.4.jar:/Users/mrgurgel/.m2/repository/io/smallrye/common/smallrye-common-annotation/1.5.0/smallrye-common-annotation-1.5.0.jar:/Users/mrgurgel/.m2/repository/io/smallrye/common/smallrye-common-expression/1.5.0/smallrye-common-expression-1.5.0.jar:/Users/mrgurgel/.m2/repository/io/smallrye/common/smallrye-common-function/1.5.0/smallrye-common-function-1.5.0.jar:/Users/mrgurgel/.m2/repository/io/smallrye/common/smallrye-common-constraint/1.5.0/smallrye-common-constraint-1.5.0.jar:/Users/mrgurgel/.m2/repository/io/smallrye/common/smallrye-common-classloader/1.5.0/smallrye-common-classloader-1.5.0.jar:/Users/mrgurgel/.m2/repository/io/smallrye/config/smallrye-config-common/1.13.1/smallrye-config-common-1.13.1.jar:/Users/mrgurgel/.m2/repository/org/jboss/logmanager/jboss-logmanager-embedded/1.0.9/jboss-logmanager-embedded-1.0.9.jar:/Users/mrgurgel/.m2/repository/org/jboss/logging/jboss-logging-annotations/2.2.0.Final/jboss-logging-annotations-2.2.0.Final.jar:/Users/mrgurgel/.m2/repository/org/jboss/threads/jboss-threads/3.2.0.Final/jboss-threads-3.2.0.Final.jar:/Users/mrgurgel/.m2/repository/org/slf4j/slf4j-api/1.7.30/slf4j-api-1.7.30.jar:/Users/mrgurgel/.m2/repository/org/jboss/slf4j/slf4j-jboss-logmanager/1.1.0.Final/slf4j-jboss-logmanager-1.1.0.Final.jar:/Users/mrgurgel/.m2/repository/org/graalvm/sdk/graal-sdk/21.0.0/graal-sdk-21.0.0.jar:/Users/mrgurgel/.m2/repository/org/wildfly/common/wildfly-common/1.5.4.Final-format-001/wildfly-common-1.5.4.Final-format-001.jar:/Users/mrgurgel/.m2/repository/io/quarkus/quarkus-bootstrap-runner/1.13.6.Final/quarkus-bootstrap-runner-1.13.6.Final.jar:/Users/mrgurgel/.m2/repository/org/eclipse/microprofile/context-propagation/microprofile-context-propagation-api/1.0.1/microprofile-context-propagation-api-1.0.1.jar:/Users/mrgurgel/.m2/repository/io/quarkus/quarkus-jackson/1.13.6.Final/quarkus-jackson-1.13.6.Final.jar:/Users/mrgurgel/.m2/repository/com/fasterxml/jackson/core/jackson-databind/2.12.3/jackson-databind-2.12.3.jar:/Users/mrgurgel/.m2/repository/com/fasterxml/jackson/core/jackson-annotations/2.12.3/jackson-annotations-2.12.3.jar:/Users/mrgurgel/.m2/repository/com/fasterxml/jackson/datatype/jackson-datatype-jsr310/2.12.3/jackson-datatype-jsr310-2.12.3.jar:/Users/mrgurgel/.m2/repository/com/fasterxml/jackson/datatype/jackson-datatype-jdk8/2.12.3/jackson-datatype-jdk8-2.12.3.jar:/Users/mrgurgel/.m2/repository/com/fasterxml/jackson/module/jackson-module-parameter-names/2.12.3/jackson-module-parameter-names-2.12.3.jar:/Users/mrgurgel/.m2/repository/com/fasterxml/jackson/dataformat/jackson-dataformat-yaml/2.12.3/jackson-dataformat-yaml-2.12.3.jar:/Users/mrgurgel/.m2/repository/org/yaml/snakeyaml/1.28/snakeyaml-1.28.jar:/Users/mrgurgel/.m2/repository/com/fasterxml/jackson/core/jackson-core/2.12.3/jackson-core-2.12.3.jar:/Users/mrgurgel/.m2/repository/io/smallrye/common/smallrye-common-io/1.5.0/smallrye-common-io-1.5.0.jar:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar dev.legrug.CiChain\n" +
            "Connected to the target VM, address: '127.0.0.1:50606', transport: 'socket'\n" +
            "ℹ\tAll files in this CI process will be in: /tmp/ciriema/a4e2b12b-a679-457f-bc4b-65436ad97515\n" +
            "\tThe log file will be located in: /tmp/ciriema/a4e2b12b-a679-457f-bc4b-65436ad97515/ciriema.log\n" +
            "The current coverage is: 80%\n";

    private static String EXAMPLE_OUTPUT_DECIMAL = "/Library/Java/JavaVirtualMachines/jdk-11.0.7.jdk/Contents/Home/bin/java -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:50606,suspend=y,server=n -javaagent:/Users/mrgurgel/Library/Caches/JetBrains/IntelliJIdea2021.1/captureAgent/debugger-agent.jar -Dfile.encoding=UTF-8 -classpath /Users/mrgurgel/git/ciriema/target/classes:/Users/mrgurgel/.m2/repository/io/quarkus/quarkus-arc/1.13.6.Final/quarkus-arc-1.13.6.Final.jar:/Users/mrgurgel/.m2/repository/io/quarkus/arc/arc/1.13.6.Final/arc-1.13.6.Final.jar:/Users/mrgurgel/.m2/repository/jakarta/enterprise/jakarta.enterprise.cdi-api/2.0.2/jakarta.enterprise.cdi-api-2.0.2.jar:/Users/mrgurgel/.m2/repository/jakarta/el/jakarta.el-api/3.0.3/jakarta.el-api-3.0.3.jar:/Users/mrgurgel/.m2/repository/jakarta/interceptor/jakarta.interceptor-api/1.2.5/jakarta.interceptor-api-1.2.5.jar:/Users/mrgurgel/.m2/repository/jakarta/annotation/jakarta.annotation-api/1.3.5/jakarta.annotation-api-1.3.5.jar:/Users/mrgurgel/.m2/repository/jakarta/transaction/jakarta.transaction-api/1.3.3/jakarta.transaction-api-1.3.3.jar:/Users/mrgurgel/.m2/repository/org/jboss/logging/jboss-logging/3.4.1.Final/jboss-logging-3.4.1.Final.jar:/Users/mrgurgel/.m2/repository/io/quarkus/quarkus-core/1.13.6.Final/quarkus-core-1.13.6.Final.jar:/Users/mrgurgel/.m2/repository/jakarta/inject/jakarta.inject-api/1.0/jakarta.inject-api-1.0.jar:/Users/mrgurgel/.m2/repository/io/quarkus/quarkus-ide-launcher/1.13.6.Final/quarkus-ide-launcher-1.13.6.Final.jar:/Users/mrgurgel/.m2/repository/io/quarkus/quarkus-development-mode-spi/1.13.6.Final/quarkus-development-mode-spi-1.13.6.Final.jar:/Users/mrgurgel/.m2/repository/io/smallrye/config/smallrye-config/1.13.1/smallrye-config-1.13.1.jar:/Users/mrgurgel/.m2/repository/org/eclipse/microprofile/config/microprofile-config-api/1.4/microprofile-config-api-1.4.jar:/Users/mrgurgel/.m2/repository/io/smallrye/common/smallrye-common-annotation/1.5.0/smallrye-common-annotation-1.5.0.jar:/Users/mrgurgel/.m2/repository/io/smallrye/common/smallrye-common-expression/1.5.0/smallrye-common-expression-1.5.0.jar:/Users/mrgurgel/.m2/repository/io/smallrye/common/smallrye-common-function/1.5.0/smallrye-common-function-1.5.0.jar:/Users/mrgurgel/.m2/repository/io/smallrye/common/smallrye-common-constraint/1.5.0/smallrye-common-constraint-1.5.0.jar:/Users/mrgurgel/.m2/repository/io/smallrye/common/smallrye-common-classloader/1.5.0/smallrye-common-classloader-1.5.0.jar:/Users/mrgurgel/.m2/repository/io/smallrye/config/smallrye-config-common/1.13.1/smallrye-config-common-1.13.1.jar:/Users/mrgurgel/.m2/repository/org/jboss/logmanager/jboss-logmanager-embedded/1.0.9/jboss-logmanager-embedded-1.0.9.jar:/Users/mrgurgel/.m2/repository/org/jboss/logging/jboss-logging-annotations/2.2.0.Final/jboss-logging-annotations-2.2.0.Final.jar:/Users/mrgurgel/.m2/repository/org/jboss/threads/jboss-threads/3.2.0.Final/jboss-threads-3.2.0.Final.jar:/Users/mrgurgel/.m2/repository/org/slf4j/slf4j-api/1.7.30/slf4j-api-1.7.30.jar:/Users/mrgurgel/.m2/repository/org/jboss/slf4j/slf4j-jboss-logmanager/1.1.0.Final/slf4j-jboss-logmanager-1.1.0.Final.jar:/Users/mrgurgel/.m2/repository/org/graalvm/sdk/graal-sdk/21.0.0/graal-sdk-21.0.0.jar:/Users/mrgurgel/.m2/repository/org/wildfly/common/wildfly-common/1.5.4.Final-format-001/wildfly-common-1.5.4.Final-format-001.jar:/Users/mrgurgel/.m2/repository/io/quarkus/quarkus-bootstrap-runner/1.13.6.Final/quarkus-bootstrap-runner-1.13.6.Final.jar:/Users/mrgurgel/.m2/repository/org/eclipse/microprofile/context-propagation/microprofile-context-propagation-api/1.0.1/microprofile-context-propagation-api-1.0.1.jar:/Users/mrgurgel/.m2/repository/io/quarkus/quarkus-jackson/1.13.6.Final/quarkus-jackson-1.13.6.Final.jar:/Users/mrgurgel/.m2/repository/com/fasterxml/jackson/core/jackson-databind/2.12.3/jackson-databind-2.12.3.jar:/Users/mrgurgel/.m2/repository/com/fasterxml/jackson/core/jackson-annotations/2.12.3/jackson-annotations-2.12.3.jar:/Users/mrgurgel/.m2/repository/com/fasterxml/jackson/datatype/jackson-datatype-jsr310/2.12.3/jackson-datatype-jsr310-2.12.3.jar:/Users/mrgurgel/.m2/repository/com/fasterxml/jackson/datatype/jackson-datatype-jdk8/2.12.3/jackson-datatype-jdk8-2.12.3.jar:/Users/mrgurgel/.m2/repository/com/fasterxml/jackson/module/jackson-module-parameter-names/2.12.3/jackson-module-parameter-names-2.12.3.jar:/Users/mrgurgel/.m2/repository/com/fasterxml/jackson/dataformat/jackson-dataformat-yaml/2.12.3/jackson-dataformat-yaml-2.12.3.jar:/Users/mrgurgel/.m2/repository/org/yaml/snakeyaml/1.28/snakeyaml-1.28.jar:/Users/mrgurgel/.m2/repository/com/fasterxml/jackson/core/jackson-core/2.12.3/jackson-core-2.12.3.jar:/Users/mrgurgel/.m2/repository/io/smallrye/common/smallrye-common-io/1.5.0/smallrye-common-io-1.5.0.jar:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar dev.legrug.CiChain\n" +
            "Connected to the target VM, address: '127.0.0.1:50606', transport: 'socket'\n" +
            "ℹ\tAll files in this CI process will be in: /tmp/ciriema/a4e2b12b-a679-457f-bc4b-65436ad97515\n" +
            "\tThe log file will be located in: /tmp/ciriema/a4e2b12b-a679-457f-bc4b-65436ad97515/ciriema.log\n" +
            "The current coverage is: 80.7%\n";

    @Test
    void testRegex() {

        Map<String, String> regexValues = new HashMap<>();
        regexValues.put("regexToExtractCurrentCoverageFromConsole", "The current coverage is: (.*)%");
        regexValues.put("minimumPercentageAccepted", "80");

        new CoveragePostProcessor().doThePostProcessing(EXAMPLE_OUTPUT, regexValues);
    }

    @Test
    void testCoverageNotAcceptable() {

        Assertions.assertThrows(CiriemaException.class, () -> {
            Map<String, String> regexValues = new HashMap<>();
            regexValues.put("regexToExtractCurrentCoverageFromConsole", "The current coverage is: (.*)%");
            regexValues.put("minimumPercentageAccepted", "90");

            new CoveragePostProcessor().doThePostProcessing(EXAMPLE_OUTPUT, regexValues);
        });
    }

    @Test
    void testCoverageNotAcceptableWithDecimal() {

        Assertions.assertThrows(CiriemaException.class, () -> {
            Map<String, String> regexValues = new HashMap<>();
            regexValues.put("regexToExtractCurrentCoverageFromConsole", "The current coverage is: (.*)%");
            regexValues.put("minimumPercentageAccepted", "90.0");

            new CoveragePostProcessor().doThePostProcessing(EXAMPLE_OUTPUT_DECIMAL, regexValues);
        });
    }

    @Test
    void testRegexWithDecimal() {

        Map<String, String> regexValues = new HashMap<>();
        regexValues.put("regexToExtractCurrentCoverageFromConsole", "The current coverage is: (.*)%");
        regexValues.put("minimumPercentageAccepted", "79.9");

        new CoveragePostProcessor().doThePostProcessing(EXAMPLE_OUTPUT_DECIMAL, regexValues);
    }

}