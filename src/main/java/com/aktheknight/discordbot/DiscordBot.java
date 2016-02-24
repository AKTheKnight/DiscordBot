package com.aktheknight.discordbot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.DiscordException;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.EventDispatcher;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by dsupport on 21/02/2016.
 */
public class DiscordBot {

    public static boolean chat;

    public static String VERSION = "1.0.0-ALPHA";
    public static IDiscordClient client;

    public static boolean started = false;

    static File location;
    static File settingsLocation;

    static Settings settings;

    static long startTime;

    public static void main(String[] args) {
        startTime = System.currentTimeMillis();
        getLocation();
        Logger.init();
        Logger.info("Starting DiscordBot-" + VERSION);
        pause(100);

        //TODO add $version command
/*        System.out.println("Checking for latest version");
        VersionChecker versionChecker = new VersionChecker();
        Thread versionCheckThread = new Thread(versionChecker, "Version Check");
        versionCheckThread.start();
        pause(100); */

        Logger.info("Importing settings");
        getSettings();
        pause(100);

        Logger.info("Logging in");
        client = getClient(settings.getBotEmail(), settings.getBotPassword(), true);
        EventDispatcher dispatcher = client.getDispatcher();
        pause(100);

        Logger.info("Starting chat listener");
        dispatcher.registerListener(new Listener());
        pause(100);

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println();
        Logger.info("Done in " + elapsedTime + " ms");
        System.out.println();
    }

    /**
     * Imports the current settings from settings.json
     * If settings.json isn't found, it writes a new instance and stops the bot
     */
    public static void getSettings() {
        try {
            settingsLocation = new File(location.getAbsolutePath() + "/settings.json");
            if (!settingsLocation.exists()) {
                Logger.info("Detected a new installation.");
                Logger.info("Please edit settings.json and restart the bot");
                settings = new Settings("email", "password", "AKTheBot", true, "username");
                writeSettings();
                shutdown();
            }
            else {
                BufferedReader reader = new BufferedReader(new FileReader(settingsLocation));
                Gson gson = new GsonBuilder().create();
                settings = gson.fromJson(reader, Settings.class);
                reader.close();
                writeSettings();
            }
        }
        catch (Exception e) {
            System.out.println();
            Logger.error("Unable to read existing settings.json", "Please report this to AK", e);
            shutdown();
        }
    }

    /**
     * Gets the file location of the program
     */
    public static void getLocation() {
        try {
            location = new File(DiscordBot.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile();
        }
        catch (Exception e) {
            System.out.println();
            System.out.println("ERROR. Unable to get file location");
            System.out.println("Please report this to AK");
            System.out.println();
            e.printStackTrace();
            System.exit(3);
        }

    }


    /**
     * Writes currently held settings into the settings.json file
     */
    public static void writeSettings() {
        try {
            Gson obj = new GsonBuilder().setPrettyPrinting().create();
            FileWriter writer = new FileWriter(settingsLocation);
            writer.write(obj.toJson(settings));
            writer.close();
        }
        catch (IOException e) {
            System.out.println();
            Logger.error("Unable to write settings to file", "Please report this to AK", e);
            shutdown();
        }

    }


    /**
     * Creates the instance of the discord client
     * @param email bot email
     * @param password bot password
     * @param login whether to login or not? (always true)
     * @return instance of discord client or null
     */
    public static IDiscordClient getClient(String email, String password, boolean login) { //Returns an instance of the discord client
        ClientBuilder clientBuilder = new ClientBuilder(); //Creates the ClientBuilder instance
        clientBuilder.withLogin(email, password); //Adds the login info to the builder
        try {
            if (login) {
                return clientBuilder.login(); //Creates the client instance and logs the client in
            } else {
                return clientBuilder.build(); //Creates the client instance but it doesn't log the client in yet, you would have to call client.login() yourself
            }
        }
        catch (DiscordException e) {
            System.out.println("ERROR attemping to login");
            e.printStackTrace();
            System.out.println("Please check your login settings");
            System.out.println("Exiting");
            System.exit(2);
        }
        return null;
    }

    /**
     * Executes Thread#sleep in catch blocks
     * @param time how long to sleep for (ms)
     */
    static void pause(int time) {
        try {
            Thread.sleep(time);
        }
        catch (Exception e) {
            System.out.println("Error while sleeping. Report to AKTheKnight");
        }
    }

    /**
     * Closes the logger and shuts down
     */
    static void shutdown() {
        Logger.info("Shutting down...");
        Logger.close();
        System.exit(3);
    }

}
