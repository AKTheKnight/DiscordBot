package com.aktheknight.discordbot;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

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
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            latestVersion = IOUtils.readLines(in).get(0);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
        }
        System.out.println("Latest mod version = " + latestVersion);
        isLatestVersion = DiscordBot.VERSION.equals(latestVersion);
        System.out.println("Are you running latest version = " + isLatestVersion);
        DiscordBot.started = true;
    }

    public boolean isLatestVersion() {
        return isLatestVersion;
    }

    public String getLatestVersion() {
        return latestVersion;
    }
}