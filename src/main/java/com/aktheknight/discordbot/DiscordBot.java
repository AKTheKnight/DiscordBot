package com.aktheknight.discordbot;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.DiscordException;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.EventDispatcher;

import java.util.ArrayList;

/**
 * Created by dsupport on 21/02/2016.
 */
public class DiscordBot {

    public static String version = "1.0.0";
    public static IDiscordClient client;
//    public static ArrayList<String> channelList;
    public static boolean chat;

    public static void main(String[] args) {
        System.out.println("Starting DiscordBot-" + version);
        chat = true;
//        System.out.println("Setting up channels");
//        channelList = new ArrayList<>();
//        System.out.println("Adding bot-playpen");
//        channelList.add("139471136793559040");
        System.out.println("Logging in");
        client = getClient("alex@aktheknight.co.uk", "Topper123", true);
        EventDispatcher dispatcher = client.getDispatcher();
        dispatcher.registerListener(new Listener());
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
