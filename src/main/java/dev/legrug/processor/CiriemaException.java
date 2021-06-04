package dev.legrug.processor;

public class CiriemaException extends RuntimeException{

    private String message;

    public CiriemaException(Exception e) {
        super(e);
    }

    public CiriemaException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return MessageUtils.Emoji.ERROR + message;
    }
}
