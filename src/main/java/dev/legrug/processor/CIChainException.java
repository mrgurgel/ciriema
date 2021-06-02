package dev.legrug.processor;

public class CIChainException extends RuntimeException{

    private String message;

    public CIChainException(Exception e) {
        super(e);
    }

    public CIChainException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return MessageUtils.Emoji.ERROR + message;
    }
}
