package dev.legrug.processor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ProcessBuilderWrapper {

    public static final int SUCCESS = 0;
    private ProcessBuilder processBuilder;
    private File logFile;

    public ProcessBuilderWrapper(File initialWorkingDirectory) {
        logFile = new File(initialWorkingDirectory, "ci-chain.log");
        MessageUtils.print(MessageUtils.Emoji.INFO, "All files in this CI process will be in: " + initialWorkingDirectory.getPath() + "\n\t" +
                "The log file will be located in: " + logFile.getPath());

        this.processBuilder = new ProcessBuilder();
        executeAndWait("mkdir", "-p", initialWorkingDirectory.getPath());
        this.processBuilder.directory(initialWorkingDirectory);
        executeAndWait("touch", logFile.getPath());
    }

    public ExecutionResult executeAndWait(String... args) {

        int status = 0;
        try {
            processBuilder.command(args);

            Instant startTime = Instant.now();
            Process process = processBuilder.start();
            status = process.waitFor();

             if(status == SUCCESS) {
                ExecutionResult executionResult = new ExecutionResult();
                executionResult.duration = Duration.between(startTime, Instant.now());
                executionResult.message = extractCommandMessageAndWriteItToLog(process.getInputStream());
                executionResult.statusCode = status;
                return executionResult;
            }
            else {
                handleCommandWithError(process, args);
            }
        } catch (IOException | InterruptedException e) {
            throw new CIChainException(e);
        }
        return null;

    }

    private void handleCommandWithError(Process process, String[] args) {
        String errorMessage = extractCommandMessageAndWriteItToLog(process.getErrorStream());

        throw new CIChainException(" - There was an error wile processing the comand: " +
                Arrays.stream(args).collect(Collectors.joining(" ")) +
                "\n" + errorMessage);
    }

    private String extractCommandMessageAndWriteItToLog(InputStream stream) {
        String message = extractMessage(stream);
        writeToLogFile(message);
        return message;
    }

    private void writeToLogFile(String message) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(logFile.getPath(), true));
            writer.write(message);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            throw new CIChainException("There was an error while writing into the log file");
        }
    }

    private String extractMessage(InputStream stream) {
        String errorMessage = new BufferedReader(
                new InputStreamReader(stream, StandardCharsets.UTF_8)).lines()
                .collect(Collectors.joining("\n"));
        return errorMessage;
    }


    public void goToWorkingDirectory(File directory) {
        processBuilder.directory(directory);
    }


    public File currentDirectory() {
        return processBuilder.directory();
    }
}
