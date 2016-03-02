package com.aktheknight.discordbot.util;

import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by Alex on 01/03/2016 at 18:31.
 */
@SuppressWarnings("JavaDoc")
public class PrintColourWriter extends PrintWriter {

    private static final String ANSI_RESET = "\u001B[0m";

    public PrintColourWriter(PrintStream out) throws UnsupportedEncodingException {
        super(new OutputStreamWriter(out, "UTF-8"), true);
    }

    public void println(PrintColour colour, String string) {
        print(colour.getAnsiColor());
        print(string);
        println(ANSI_RESET);
        flush();
    }

    public void green(String string) {
        println(PrintColour.GREEN, string);
    }

    public void red(String string) {
        println(PrintColour.RED, string);
    }
}
