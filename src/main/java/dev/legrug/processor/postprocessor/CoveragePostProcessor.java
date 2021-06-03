package dev.legrug.processor.postprocessor;

import dev.legrug.processor.CIChainException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ApplicationScoped
@Named("coverage")
public class CoveragePostProcessor implements IPostProcessor {

    public static final int EXPECTED_POSITION = 1;
    private static String PARAM_REGEX_TO_GET_THE_COVERAGE = "regexToExtractCurrentCoverageFromConsole";
    private static String PARAM_MINIMUM_PERCENTAGE_ACCEPTED = "minimumPercentageAccepted";


    @Override
    public void doThePostProcessing(String commandsOutput, Map<String, String> postProcessorParams) {
        Double currentCoverage = findCurrentCoverage(commandsOutput, postProcessorParams);
        Double minimumAcceptedCoverage = Double.valueOf(postProcessorParams.get(PARAM_MINIMUM_PERCENTAGE_ACCEPTED));

        if(currentCoverage < minimumAcceptedCoverage) {
            StringBuilder errorMessage = new StringBuilder().append("The current coverage for the project is: ").append(currentCoverage)
                    .append("\nThe minimum coverage is: ").append(minimumAcceptedCoverage);
            throw new CIChainException(errorMessage.toString());
        }
    }

    private Double findCurrentCoverage(String commandsOutput, Map<String, String> postProcessorParams) {
        Pattern pattern = Pattern.compile(postProcessorParams.get(PARAM_REGEX_TO_GET_THE_COVERAGE));
        Matcher matcher = pattern.matcher(commandsOutput);
        int matches = 0;
        while (matcher.find()) {
            return Double.valueOf(matcher.group(EXPECTED_POSITION));
        }
        throw new CIChainException("Couldnt extract the current coverage from console");
    }
}
