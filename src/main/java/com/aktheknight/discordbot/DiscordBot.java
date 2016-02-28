package com.aktheknight.discordbot;

import com.aktheknight.discordbot.obj.Command;
import com.aktheknight.discordbot.obj.Settings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.DiscordException;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.EventDispatcher;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by dsupport on 21/02/2016.
 */
public class DiscordBot {

    static String VERSION = "1.0.0-ALPHA";
    static IDiscordClient client;

    static boolean started = false;

    static File location;
    static File settingsLocation;
    static File commandsLocation;

    static Settings settings;

    static ArrayList<Command> commands;

    static long startTime;

    public static void main(String[] args) {
        startTime = System.currentTimeMillis();
        getLocation();
        Logger.init();
        Logger.info("Starting DiscordBot-" + VERSION);
        pause(100);

        Logger.info("Importing commands");
        importCommands();
        pause(100);

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

        Thread console = new Thread(new Console());
        console.start();
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
                settings = new Settings(DiscordBot.VERSION, "email", "password", "AKTheBot", true, "userID");
                writeSettings();
                Logger.info("Shutting down....");
                System.exit(2);
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
     * Import json commands from file
     */
    public static void importCommands() {
        try {
            commandsLocation = new File(location.getAbsolutePath() + "/commands.json");
            if (!commandsLocation.exists()) {
                Logger.info("Could not find commands.json");
                Logger.info("Creating file");
                commands = new ArrayList<>();
                ArrayList<String> example = new ArrayList<>();
                example.add("Hello");
                example.add("%arg2%");
                commands.add(new Command("name", false, 2, example));
                writeCommands();
            }
            else {
                BufferedReader reader = new BufferedReader(new FileReader(commandsLocation));
                Gson gson = new GsonBuilder().create();
                commands = new ArrayList<>(Arrays.asList(gson.fromJson(reader, Command[].class)));
                reader.close();
                writeCommands();
            }
        }
        catch (Exception e) {
            Logger.error("Error while closing commands file", e);
        }
    }

    /**
     * Write the commands objects to file
     */
    public static void writeCommands() {
        try {
            Gson obj = new GsonBuilder().setPrettyPrinting().create();
            FileWriter writer = new FileWriter(commandsLocation);
            writer.write(obj.toJson(commands));
            writer.close();
        }
        catch (IOException e) {
            System.out.println();
            Logger.error("Unable to write commands to file", "Please report this to AK", e);
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
        try {
            Logger.info("Logging out the bot");
            client.logout();
        }
        catch (Exception e) {
            Logger.error("Unable to logout the bot", "Shutting down anyway", e);
            Logger.close();
            System.exit(3);
        }
        Logger.info("Closing down the logger");
        Logger.close();
        System.exit(3);
    }

}
