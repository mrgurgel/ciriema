package dev.legrug.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ProcessBuilderWrapper {

    public static final int SUCCESS = 0;
    private ProcessBuilder processBuilder;
    private File logFile;

    public ProcessBuilderWrapper(File initialWorkingDirectory) {
        this.processBuilder = new ProcessBuilder();
        executeAndWait("mkdir", "-p", initialWorkingDirectory.getPath());
        this.processBuilder.directory(initialWorkingDirectory);
        logFile = new File(initialWorkingDirectory, "ci-chain.log");
        executeAndWait("touch", logFile.getPath());
        this.processBuilder.redirectOutput(logFile);
    }

    public int executeAndWait(String... args) {

        int status = 0;
        try {
            processBuilder.command(args);
            Process process = processBuilder.start();
            status = process.waitFor();

            if(status != SUCCESS) {
                String errorMessage = new BufferedReader(
                        new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8)).lines()
                        .collect(Collectors.joining("\n"));

                throw new CIChainException(" - There was an error wile processing the comand: " +
                        Arrays.stream(args).collect(Collectors.joining(" ")) +
                        "\n" + errorMessage);
            }
        } catch (IOException | InterruptedException e) {
            throw new CIChainException(e);
        }
        return status;

    }

    public void goToWorkingDirectory(File directory) {
        processBuilder.directory(directory);
    }


    public File currentDirectory() {
        return processBuilder.directory();
    }
}
