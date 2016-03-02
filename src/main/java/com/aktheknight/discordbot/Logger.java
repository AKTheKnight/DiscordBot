package com.aktheknight.discordbot;

import com.aktheknight.discordbot.util.PrintColour;
import com.aktheknight.discordbot.util.PrintColourWriter;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alex on 24/02/2016 at 19:49.
 */
public class Logger {

    static Date date;
    static SimpleDateFormat fileFormat = new SimpleDateFormat("HH-mm-ss dd-MM-yyyy");
    static SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss") ;
    static BufferedWriter writer;

    static PrintColourWriter out;

    static void colourInit() {
        try {
            out = new PrintColourWriter(System.out);
        } catch (UnsupportedEncodingException e) {
            error("This is bad", "Report this to AK NOW!!", e);
        }
    }

    /**
     * Writes a blank line to the file (used for errors)
     */
    static void blank() {
        String output = "";
        System.out.println(output);
        write(output);
    }

    /**
     * Sets up the BufferedWriter for the logger
     */
    static void init() {
        try {
            File file = new File(DiscordBot.location + "/logs") ;
            file.mkdirs();
            date = new Date() ;
            file = new File(file.getAbsolutePath() + "/" + fileFormat.format(date) + ".log");
            writer = new BufferedWriter(new FileWriter(file));
        }
        catch (Exception e) {
            System.out.println();
            System.out.println("ERROR. Unable to create logging file");
            System.out.println("Please report this to AK");
            System.out.println();
            e.printStackTrace();
            System.exit(3);
        }
        try {
            out = new PrintColourWriter(System.out);
        } catch (UnsupportedEncodingException e) {
            error("This is bad", "Report this to AK NOW!!", e);
        }
    }

    /**
     * Write out messages from the console into the log
     * @param content the string message from the console
     */
    static void console(String content) {
        date = new Date();
        String output = format.format(date) + " [CONSOLE] " + content;
        write(output);
    }

    /**
     * Prints an error to the log and console with 2 param
     * @param content1 First string form of error
     * @param content2 Second message to admin
     */
    static void error(String content1, String content2) {
        date = new Date();
        String output = format.format(date) + " [ERROR] " + content1;
        out.println(PrintColour.RED, output);
        write(output);
        output = format.format(date) + " [ERROR] " + content2;
        out.println(PrintColour.RED, output);
        write(output);
    }

    /**
     * Prints an error to the log and console with 1 param
     * @param content1 The string form of the error
     * @param e The exception
     */
    static void error(String content1, Exception e) {
        date = new Date();
        String output = format.format(date) + " [ERROR] " + content1;
        out.println(PrintColour.RED, output);
        write(output);
        e.printStackTrace();
        PrintWriter pw = new PrintWriter(writer);
        e.printStackTrace(pw);
        pw.flush();
        pw.close();
    }

    /**
     * Prints an error to the log and console with 2 param
     * @param content1 First string form of error
     * @param content2 Second message to admin
     * @param e The exception
     */
    static void error(String content1, String content2, Exception e) {
        date = new Date();
        String output = format.format(date) + " [ERROR] " + content1;
        out.println(PrintColour.RED, output);
        write(output);
        output = format.format(date) + " [ERROR] " + content2;
        out.println(PrintColour.RED, output);
        write(output);
        e.printStackTrace();
        PrintWriter pw = new PrintWriter(writer);
        e.printStackTrace(pw);
        pw.flush();
        pw.close();
    }

    /**
     * Prints out info to console and log
     * @param content The info message to be logged
     */
    static void info(String content) {
        date = new Date();
        String output = format.format(date) + " [INFO] " + content;
        System.out.println(output);
        write(output);
    }

    /**
     * Prints out the bots reply to a message
     * @param content The bots reply
     */
    static void reply(String content) {
        date = new Date();
        String output = format.format(date) + " [BOT] " + content;
        if (DiscordBot.settings.getPrintAllChat()) {
            System.out.println(output);
        }
        write(output);
    }

    /**
     * Print out discord chat to log and console (if on)
     * @param name Name of the user
     * @param content The message
     */
    static void chat(String name, String content) {
        date = new Date();
        String output = format.format(date) + " [CHAT] " + name + ": " + content;
        if (DiscordBot.settings.getPrintAllChat()) {
            System.out.println(output);
        }
        write(output);
    }

    /**
     * Used for testing
     * @param colour colour for printing
     * @param output string to be printed
     */
    static void print(PrintColour colour, String output) {
        out.println(colour, output);
    }

    /**
     * Writes out the formatted message to the log
     * @param message Message to print
     */
    static void write(String message) {
        try {
            writer.write(message + "\n");
            writer.flush();
        }
        catch (Exception e) {
            System.out.println();
            System.out.println("ERROR. Unable to write to logging file");
            System.out.println("Please report this to AK");
            System.out.println();
            e.printStackTrace();
        }
    }


    /**
     * Closes the writer nicely :)
     */
    static void close() {
        try {
            writer.flush();
            writer.close();
        }
        catch (Exception e) {
            System.out.println();
            System.out.println("ERROR. Unable to close logging file");
            System.out.println("Please report this to AK");
            System.out.println();
            e.printStackTrace();
            System.exit(3);
        }
    }
}
