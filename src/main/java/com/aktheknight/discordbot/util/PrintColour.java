package com.aktheknight.discordbot.util;

/**
 * Created by Alex on 01/03/2016 at 18:30.
 */
@SuppressWarnings("JavaDoc")
public enum PrintColour {

    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m");

    private String ansiColor;

    PrintColour(String ansiColor) {
        this.ansiColor = ansiColor;
    }

    public String getAnsiColor() {
        return ansiColor;
    }
}
