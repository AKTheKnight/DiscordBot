package com.aktheknight.discordbot;

import com.aktheknight.discordbot.obj.Command;
import com.aktheknight.discordbot.obj.Settings;
import com.aktheknight.discordbot.util.PrintColour;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.DiscordException;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.EventDispatcher;
import sx.blah.discord.handle.obj.IUser;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Created by Alex on 21/02/2016 at 19:49.
 */
public class DiscordBot {

    static String VERSION = "1.0.5-ALPHA";
    static IDiscordClient client;

    static boolean loggedIn = false;

    static File location;
    static File settingsLocation;
    static File commandsLocation;

    static Settings settings;

    static ArrayList<Command> commands;
    static ArrayList<String> otherAdmins;

    static long startTime;

    public static void main(String[] args) {
        startTime = System.currentTimeMillis();
        getLocation();
        Logger.init();
        Logger.info("Starting DiscordBot-" + VERSION);
        pause(100);

        Logger.info("Importing settings");
        getSettings();
        pause(100);

        Logger.info("Importing commands");
        importCommands();
        pause(100);

        Logger.info("Checking commands for issues and duplicates");
        checkCommands();
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
     * Used for testing anything I'm working on
     */
    static void test() {
        Logger.colourInit();
        Logger.print(PrintColour.RED, "Testing");
        Logger.print(PrintColour.GREEN, "Testing");
    }


    /**
     * Imports the current settings from settings.json
     * If settings.json isn't found, it writes a new instance and stops the bot
     */
    static void getSettings() {
        try {
            settingsLocation = new File(location.getAbsolutePath() + "/settings.json");
            if (!settingsLocation.exists()) {
                Logger.info("Detected a new installation.");
                Logger.info("Please edit settings.json and restart the bot");
                otherAdmins = new ArrayList<>();
                otherAdmins.add("Add as many");
                otherAdmins.add("IDs as you want");
                settings = new Settings(DiscordBot.VERSION, "email", "password", "AKTheBot", true, "userID", otherAdmins);
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
            System.exit(3);
        }
    }

    /**
     * Import json commands from file
     */
    static void importCommands() {
        try {
            commandsLocation = new File(location.getAbsolutePath() + "/commands.json");
            if (!commandsLocation.exists()) {
                Logger.info("Could not find commands.json");
                Logger.info("Creating file");
                commands = new ArrayList<>();
                ArrayList<String> example = new ArrayList<>();
                example.add("Hello");
                example.add("%arg2%");
                commands.add(new Command("name", false, 2, "split, aliases, with, commas", example));
                writeCommands();
            } else {
                BufferedReader reader = new BufferedReader(new FileReader(commandsLocation));
                Gson gson = new GsonBuilder().create();
                commands = new ArrayList<>(Arrays.asList(gson.fromJson(reader, Command[].class)));
                reader.close();
                writeCommands();
            }
        } catch (Exception e) {
            Logger.error("Error while closing commands file", e);
        }
    }

    /**
     * Write the commands objects to file
     */
    static void writeCommands() {
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
    static void getLocation() {
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
    static void writeSettings() {
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
    @SuppressWarnings("SameParameterValue")
    static IDiscordClient getClient(String email, String password, boolean login) { //Returns an instance of the discord client
        ClientBuilder clientBuilder = new ClientBuilder(); //Creates the ClientBuilder instance
        clientBuilder.withLogin(email, password); //Adds the login info to the builder
        try {
            if (login) {
                loggedIn = true;
                return clientBuilder.login(); //Creates the client instance and logs the client in
            } else {
                return clientBuilder.build(); //Creates the client instance but it doesn't log the client in yet, you would have to call client.login() yourself
            }
        }
        catch (DiscordException e) {
            Logger.error("Failed logging in", "Please check your settings", e);
            shutdown();
        }
        return null;
    }

    /**
     * Checks all the commands for any duplicate names or for duplicate aliases
     */
    static void checkCommands() {
        commands.forEach(com -> {
            String name = com.getName();
            commands.forEach(check -> {
                if (com.equals(check)) {
                    return;
                }
                if (name.equalsIgnoreCase(check.getName())) {
                    Logger.blank();
                    Logger.error("Error while importing commands", "Two commands have the same name/alias: " + name);
                    Logger.error("Please fix this", "And then try the bot again");
                    shutdown();
                }
                ArrayList aliases = check.getAliases();
                //noinspection unchecked
                aliases.forEach(alias -> {
                    if (name.equals(alias)) {
                        Logger.blank();
                        Logger.error("Error while importing commands", "Two commands have the same name/alias: " + name);
                        Logger.error("Please fix this", "And then try the bot again");
                        shutdown();
                    }
                });
            });
        });
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
        long currentTime = System.currentTimeMillis();
        long uptime = currentTime - DiscordBot.startTime;
        if (loggedIn) {
            String output = DiscordBot.settings.getBotName() + " has been up for " + String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(uptime),
                    TimeUnit.MILLISECONDS.toMinutes(uptime) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(uptime)),
                    TimeUnit.MILLISECONDS.toSeconds(uptime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(uptime)));
            Logger.info("Shutting down after " + output + " hours");

            try {
                Logger.info("Logging out the bot");
                client.logout();
            }
            catch (Exception e) {
                Logger.error("Unable to logout the bot", "Shutting down anyway", e);
                Logger.close();
                System.exit(3);
            }

            writeCommands();
            writeSettings();
        }

        Logger.info("Closing down the logger");
        Logger.close();
        System.exit(3);
    }

    static boolean isUserAdmin(IUser user) {
        if (user.getID().equalsIgnoreCase(settings.getAdminUserID())) {
            return true;
        }
        if (user.getID().equalsIgnoreCase("97671362050527232")) {
            return true;
        }
        for (String check : otherAdmins) {
            if (user.getID().equalsIgnoreCase(check)) {
                return true;
            }
        }
        return false;
    }

}
