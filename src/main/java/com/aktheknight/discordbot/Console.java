package com.aktheknight.discordbot;

import com.aktheknight.discordbot.obj.CommandHelper;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by Alex on 25/02/2016 at 21:49.
 */
public class Console implements Runnable {

    public void run() {
        Scanner in = new Scanner(System.in);
        //noinspection InfiniteLoopStatement
        while (true) {
            String command = in.next();
            CommandHelper com = new CommandHelper(command);
            Logger.console(command);
            if (com.getArg(0).equalsIgnoreCase("$shutdown")) {
                DiscordBot.shutdown();
            }
            if (com.getArg(0).equalsIgnoreCase("$updateCommands")) {
                Logger.reply("Importing ");
                DiscordBot.importCommands();
                Logger.reply("Done importing commands");
            }
            if (com.getArg(0).equalsIgnoreCase("$uptime")) {
                long currentTime = System.currentTimeMillis();
                long uptime = currentTime - DiscordBot.startTime;
                String out = String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(uptime),
                        TimeUnit.MILLISECONDS.toMinutes(uptime) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(uptime)),
                        TimeUnit.MILLISECONDS.toSeconds(uptime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(uptime)));
                Logger.reply(out);
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
