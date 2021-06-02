package dev.legrug.processor;

import java.io.IOException;

public class MessageUtils {

    public static void print(Emoji emoji, String message) {
        System.out.println(new StringBuilder(emoji.code).append("\t").append(message));
    }

    public enum Emoji {

        STRONG("\uD83D\uDCAA"),
        ERROR("\u274E"),
        STARTING("\uD83D\uDFE1"),
        FINISHED("\u2705"),
        ROCKET("");

        private String code;

        Emoji(String code) {
            this.code = code;
        }

}

}
