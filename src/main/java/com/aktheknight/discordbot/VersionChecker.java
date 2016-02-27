package com.aktheknight.discordbot;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Alex on 23/02/2016 at 21:53.
 */
public class VersionChecker implements Runnable {
    private static boolean isLatestVersion = false;
    private static String latestVersion = "";

    @Override
    public void run() {
        InputStream in = null;
        try {
            in = new URL("https://raw.githubusercontent.com/AKTheKnight/DiscordBot/master/version.txt").openStream();
            latestVersion = IOUtils.readLines(in).get(0);
        } catch (Exception e) {
            Logger.error("Error while checking for latest version", "Report to AK if this keeps happening", e);
        } finally {
            IOUtils.closeQuietly(in);
        }
        Logger.reply("Latest mod version = " + latestVersion);
        isLatestVersion = DiscordBot.VERSION.equals(latestVersion);
        Logger.reply("Are you running latest version = " + isLatestVersion);
    }

    public boolean isLatestVersion() {
        return isLatestVersion;
    }

    public String getLatestVersion() {
        return latestVersion;
    }
}