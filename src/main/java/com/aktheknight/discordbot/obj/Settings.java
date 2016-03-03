package com.aktheknight.discordbot.obj;

import java.util.ArrayList;

/**
 * Created by Alex on 22/02/2016 at 15:20.
 */
@SuppressWarnings("JavaDoc")
public class Settings {

    private String SettingsForTheBot = "Speak to @AKTheKnight if you need any help";
    private String version;
    private String DoNotChange = "Anything above this line";
    private String botEmail;
    private String botPassword;
    private String botName;
    private boolean printAllChat;
    private String adminUserID;
    private ArrayList<String> otherAdminIDs;

    /**
     * Only used for creating the default template at the moment
     * @param version set by the bot on first installation
     * @param botEmail used for logging in
     * @param botPassword used for logging in
     * @param botName used for commands (Yeah I could just check for the name on discord)
     * @param printAllChat whether to print all chat recieved to console
     * @param adminUserID the admin user id (for admin commands)
     */
    public Settings(String version, String botEmail, String botPassword, String botName, boolean printAllChat, String adminUserID, ArrayList<String> otherAdminIDs) {
        this.version = version;
        this.botEmail = botEmail;
        this.botPassword = botPassword;
        this.botName = botName;
        this.printAllChat = printAllChat;
        this.adminUserID = adminUserID;
        this.otherAdminIDs = otherAdminIDs;
    }

    public String getBotPassword() {
        return botPassword;
    }

    public String getBotEmail() {
        return botEmail;
    }

    public String getBotName() {
        return botName;
    }

    public boolean getPrintAllChat() {
        return printAllChat;
    }

    public void setPrintAllChat(boolean printAllChat) {
        this.printAllChat = printAllChat;
    }

    public String getAdminUserID() {
        return adminUserID;
    }

    public ArrayList<String> getOtherAdminIDs() {
        return otherAdminIDs;
    }

    public void addOtherAdminID(String id) {
        otherAdminIDs.add(id);
    }

}
