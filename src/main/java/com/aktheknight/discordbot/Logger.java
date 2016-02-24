package com.aktheknight.discordbot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alex on 24/02/2016.
 */
public class Logger {

    static Date date = new Date() ;
    static SimpleDateFormat fileFormat = new SimpleDateFormat("HH-mm-ss dd-MM-yyyy");
    static SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss") ;
    static BufferedWriter writer;

    public static void init() {
        try {
            File file = new File(DiscordBot.location + "/logs") ;
            file.mkdirs();
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
    }

    //one content
    public static void error(String content1, Exception e) {
        String output = format.format(date) + " [ERROR] " + content1;
        System.out.println(output);
        write(output);
        e.printStackTrace();
        PrintWriter pw = new PrintWriter(writer);
        e.printStackTrace(pw);
        pw.flush();
        pw.close();
    }

    //two content
    public static void error(String content1, String content2, Exception e) {
        String output = format.format(date) + " [ERROR] " + content1;
        System.out.println(output);
        write(output);
        output = format.format(date) + " [ERROR] " + content2;
        System.out.println(output);
        write(output);
        e.printStackTrace();
        PrintWriter pw = new PrintWriter(writer);
        e.printStackTrace(pw);
        pw.flush();
        pw.close();
    }

    public static void info(String content) {
        String output = format.format(date) + " [INFO] " + content;
        System.out.println(output);
        write(output);
    }

    public static void reply(String content) {
        String output = format.format(date) + " [BOT] " + content;
        if (DiscordBot.settings.getPrintAllChat()) {
            System.out.println(output);
        }
        write(output);
    }

    public static void chat(String name, String content) {
        String output = format.format(date) + " [CHAT] " + name + ": " + content;
        if (DiscordBot.settings.getPrintAllChat()) {
            System.out.println(output);
        }
        write(output);
    }

    private static void write(String message) {
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


    public static void close() {
        try {
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
