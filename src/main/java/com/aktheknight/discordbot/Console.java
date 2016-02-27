package com.aktheknight.discordbot;

import com.aktheknight.discordbot.obj.CommandHelper;

import java.util.Scanner;

/**
 * Created by Alex on 25/02/2016 at 21:49.
 */
public class Console implements Runnable {

    public void run() {
        Scanner in = new Scanner(System.in);
        while (true) {
            String command = in.next();
            CommandHelper com = new CommandHelper(command);
            Logger.console(command);
            if (com.getArg(0).equalsIgnoreCase("$shutdown")) {
                DiscordBot.shutdown();
            }
            if (com.getArg(0).equalsIgnoreCase("$updateCommands")) {
                DiscordBot.importCommands();
                Logger.reply("Importing ");
            }
            if (com.getArg(0).equalsIgnoreCase("$update")) {
                Logger.reply("Checking for latest version");
                VersionChecker versionChecker = new VersionChecker();
                Thread versionCheckThread = new Thread(versionChecker, "Version Check");
                versionCheckThread.start();
            }
        }
    }
}
