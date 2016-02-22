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

    public static String version = "1.0.0";
    public static IDiscordClient client;
//    public static ArrayList<String> channelList;
    public static boolean chat;

    static File location;
    static File settingsLocation;

    static Settings settings;


    public static void main(String[] args) {
        System.out.println("Starting DiscordBot-" + version);
        getSettings();




        System.out.println("Starting DiscordBot-" + version);
        chat = true;
        System.out.println("Logging in");
        client = getClient("alex@aktheknight.co.uk", "Topper123", true);
        EventDispatcher dispatcher = client.getDispatcher();
        dispatcher.registerListener(new Listener());
    }

    public static void getSettings() {
        try {
            location = new File(DiscordBot.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile();
            System.out.println(location.getAbsolutePath());
            settingsLocation = new File(location.getAbsolutePath() + "/settings.json");
            if (!settingsLocation.exists()) {
                System.out.println("Detected a new installation.");
                System.out.println("Please edit settings.json and restart the bot");
                settings = new Settings("username", "password", true);
                writeSettings();
                System.exit(2);
            }
            else {
                BufferedReader reader = new BufferedReader(new FileReader(settingsLocation));
                Gson gson = new GsonBuilder().create();
                settings = gson.fromJson(reader, Settings.class);
                reader.close();
            }
        }
        catch (URISyntaxException e) {
            System.out.println();
            System.out.println("ERROR. Unable to get file location");
            System.out.println("Please report this to AK");
            System.out.println();
            e.printStackTrace();
            System.exit(3);
        }
        catch (Exception e) {
            System.out.println();
            System.out.println("ERROR. Unable to read existing settings.json");
            System.out.println("Please report this to AK");
            System.out.println();
            e.printStackTrace();
            System.exit(3);
        }
    }

    public static void writeSettings() {
        try {
            Gson obj = new GsonBuilder().setPrettyPrinting().create();
            FileWriter writer = new FileWriter(settingsLocation);
            writer.write(obj.toJson(settings));
            writer.close();
        }
        catch (IOException e) {
            System.out.println();
            System.out.println("ERROR. Unable to write settings to file");
            System.out.println("Please report this to AK");
            System.out.println();
            e.printStackTrace();
            System.exit(3);
        }

    }


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
            System.out.println("ERROR");
            e.printStackTrace();
            System.out.println("Exiting");
            System.exit(2);
        }
        return null;
    }

}
