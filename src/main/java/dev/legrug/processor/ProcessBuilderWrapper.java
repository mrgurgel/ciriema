package dev.legrug.processor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ProcessBuilderWrapper {

    public static final int SUCCESS = 0;
    public static final int FIRST_COMMAND = 0;
    private ProcessBuilder processBuilder;
    private File currentWorkingDirectory;
    private int currentNumberOfExecutions = 1;

    public ProcessBuilderWrapper(File initialWorkingDirectory) {
        currentWorkingDirectory = initialWorkingDirectory;
        MessageUtils.print(MessageUtils.Emoji.INFO, "All files in this CI process will be in: " + initialWorkingDirectory.getPath() + "\n\t" +
                "The log files will be located in: " + currentWorkingDirectory.getPath() + "/logs");

        this.processBuilder = new ProcessBuilder();
        createInitialDirectories(initialWorkingDirectory);
    }

    private void createInitialDirectories(File initialWorkingDirectory)  {
        try {
            processBuilder.command("mkdir", "-p", initialWorkingDirectory.getPath() + "/logs");
            processBuilder.start().waitFor();
            this.processBuilder.directory(initialWorkingDirectory);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ExecutionResult executeAndWait(String command) {
        String commandWithVariables = doVariableSubstitution(command);
        String[] commandArgs = splitCommand(commandWithVariables);
        int status = 0;
        try {
            File logForThisCommand = new File(currentWorkingDirectory, "/logs/" + (currentNumberOfExecutions++) + "_" + commandArgs[FIRST_COMMAND] + ".log");
            logForThisCommand.createNewFile();
            processBuilder.redirectOutput(logForThisCommand);
            processBuilder.redirectError(logForThisCommand);
            processBuilder.command(commandArgs);

            Instant startTime = Instant.now();
            Process process = processBuilder.start();
            status = process.waitFor();

             if(status == SUCCESS) {
                ExecutionResult executionResult = new ExecutionResult();
                executionResult.duration = Duration.between(startTime, Instant.now());
                executionResult.message = extractMessage(logForThisCommand);
                executionResult.statusCode = status;
                return executionResult;
            }
            else {
                handleCommandWithError(process, commandArgs, logForThisCommand);
            }
        } catch (IOException | InterruptedException e) {
            throw new CIChainException(e);
        }
        return null;

    }

    private String[] splitCommand(String commandWithVariables) {
        return commandWithVariables.split("\\s(?=(?:\"[^\"]*\"|[^\"])*$)");
    }

    private String doVariableSubstitution(String command) {

        return command.replaceAll("%CURRENT_WORKING_DIR", currentWorkingDirectory.getPath()).replaceAll("\n", " ");
    }

    private void handleCommandWithError(Process process, String[] args, File logForThisCommand) {
        String errorMessage = extractMessage(logForThisCommand);

        throw new CIChainException(" - There was an error wile processing the comand: " +
                Arrays.stream(args).collect(Collectors.joining(" ")) +
                "\n" + errorMessage);
    }


    private String extractMessage(File logFile) {
        try {
            String fileContents = new BufferedReader(new FileReader(logFile)).lines()
                    .collect(Collectors.joining("\n"));
            return fileContents;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void goToWorkingDirectory(File directory) {
        processBuilder.directory(directory);
    }


    public File currentDirectory() {
        return processBuilder.directory();
    }
}
