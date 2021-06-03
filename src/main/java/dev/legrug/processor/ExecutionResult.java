package dev.legrug.processor;

import java.time.Duration;

public class ExecutionResult {
    public int statusCode;
    public String message;
    public Duration duration;

    public String prettyPrintDuration() {
        return new StringBuilder("[").append(duration.toMillis()).append("ms]").toString();
    }
}
