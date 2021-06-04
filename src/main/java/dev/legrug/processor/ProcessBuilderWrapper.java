package dev.legrug.processor;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ProcessBuilderWrapper {

    public static final int SUCCESS = 0;
    public static final int FIRST_COMMAND = 0;
    public static final String REGEX_TO_SPLIT_SPACES_OUTSIDE_QUOTES = "\\s(?=(?:\"[^\"]*\"|[^\"])*$)";
    private ProcessBuilder processBuilder;
    private File currentWorkingDirectory;
    private int currentNumberOfExecutions = 1;

    public ProcessBuilderWrapper(File initialWorkingDirectory) {
        currentWorkingDirectory = initialWorkingDirectory;
        MessageUtils.print(MessageUtils.Emoji.BIRD, "Welcome to CIriema!");
        MessageUtils.print(MessageUtils.Emoji.INFO, "All files in this CI process will be in: " + initialWorkingDirectory.getPath() + "\n\t" +
                "The log files will be located in: " + currentWorkingDirectory.getPath() + "/logs");

        this.processBuilder = new ProcessBuilder();
        createInitialDirectories(initialWorkingDirectory);
    }

    private void createInitialDirectories(File initialWorkingDirectory) {
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
            File logForThisCommand = createALogForThisCommand(commandArgs);
            redirectTheOutput(logForThisCommand);
            processBuilder.command(commandArgs);

            Instant startTime = Instant.now();
            Process process = processBuilder.start();
            status = process.waitFor();

            if (status == SUCCESS) {
                return handleCommandWithSuccess(status, logForThisCommand, startTime);
            } else {
                handleCommandWithError(process, commandArgs, logForThisCommand);
            }
        } catch (IOException | InterruptedException e) {
            throw new CiriemaException(e);
        }
        return null;

    }

    private void redirectTheOutput(File logForThisCommand) {
        processBuilder.redirectOutput(logForThisCommand);
        processBuilder.redirectError(logForThisCommand);
    }

    private File createALogForThisCommand(String[] commandArgs) throws IOException {
        File logForThisCommand = new File(currentWorkingDirectory, "/logs/" + (currentNumberOfExecutions++) + "_" + commandArgs[FIRST_COMMAND] + ".log");
        logForThisCommand.createNewFile();
        return logForThisCommand;
    }

    private ExecutionResult handleCommandWithSuccess(int status, File logForThisCommand, Instant startTime) {
        ExecutionResult executionResult = new ExecutionResult();
        executionResult.duration = Duration.between(startTime, Instant.now());
        executionResult.message = extractMessage(logForThisCommand);
        executionResult.statusCode = status;
        return executionResult;
    }

    private String[] splitCommand(String commandWithVariables) {
        return commandWithVariables.split(REGEX_TO_SPLIT_SPACES_OUTSIDE_QUOTES);
    }

    private String doVariableSubstitution(String command) {
        return command.replaceAll("%CURRENT_WORKING_DIR", currentWorkingDirectory.getPath()).replaceAll("\n", " ");
    }

    private void handleCommandWithError(Process process, String[] args, File logForThisCommand) {
        String errorMessage = extractMessage(logForThisCommand);

        throw new CiriemaException(" - There was an error wile processing the comand: " +
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
