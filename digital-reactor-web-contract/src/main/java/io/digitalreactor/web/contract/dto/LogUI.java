package io.digitalreactor.web.contract.dto;

/**
 * Created by MStepachev on 07.10.2016.
 */
public class LogUI {
    private String level;
    private String timeStamp;
    private String loggerName;
    private String message;
    private String threadName;

    public LogUI(String level, String timeStamp, String loggerName, String message, String threadName) {
        this.level = level;
        this.timeStamp = timeStamp;
        this.loggerName = loggerName;
        this.message = message;
        this.threadName = threadName;
    }

    public String getLevel() {
        return level;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public String getMessage() {
        return message;
    }

    public String getThreadName() {
        return threadName;
    }
}
