package dev.legrug.processor.yamlconfig;

import dev.legrug.processor.MessageUtils;
import dev.legrug.processor.ProcessBuilderWrapper;

import java.io.File;

public class Git {

    public String url;
    public String branch;


    void process(ProcessBuilderWrapper processBuilder) {
        processBuilder.executeAndWait("git", "clone", url);

        String projectName = extractProjectName();
        processBuilder.goToWorkingDirectory(new File(processBuilder.currentDirectory(),
                projectName));

        MessageUtils.print(MessageUtils.Emoji.STARTING, "Checking out the project");
        if(!"master".equals(branch)) {
            processBuilder.executeAndWait("git", "checkout", "-b", branch);
        }
        MessageUtils.print(MessageUtils.Emoji.STRONG, "Git project checked out");

    }

    private String extractProjectName() {
        String[] urlParts = url.split("\\/");
        String projectName = urlParts[urlParts.length - 1].replaceAll("\\.git", "");
        return projectName;
    }

}
