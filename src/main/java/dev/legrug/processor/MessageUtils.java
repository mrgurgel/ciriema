package dev.legrug.processor;

import java.io.IOException;

public class MessageUtils {

    public static void print(Emoji emoji, String message) {
        System.out.print(new StringBuilder(emoji.code).append("\t").append(message).append(emoji.sufix));
    }

    public enum Emoji {

        STRONG("\uD83D\uDCAA", "\n"),
        ERROR("\u274E"),
        STARTING("\uD83D\uDFE1", "\n"),
        SUB_STARTING("\t\uD83D\uDD38", "... "),
        FINISHED("\u2705", "\n"),
        ENTIRE_PROCESS_FINISHED("\uD83D\uDE80"),
        INFO("\u2139", "\n"),
        BIRD("\uD83E\uDDA4");

        private String code;
        private String sufix = "\n";

        Emoji(String code) {
            this.code = code;
        }

        Emoji(String code, String sufix) {
            this.code = code;
            this.sufix = sufix;
        }
}

}
