package com.aktheknight.discordbot;

import java.util.Scanner;

/**
 * Created by Alex on 25/02/2016 at 21:49.
 */
public class Console implements Runnable {

    public void run() {
        Scanner in = new Scanner(System.in);
        while (true) {
            String message = in.nextLine();
            if (message.startsWith("$shutdown")) {
                DiscordBot.shutdown();
            }
            if (message.startsWith("$updateCommands")) {
                DiscordBot.importCommands();
                Logger.info("Importing ");
            }
        }
    }
}
