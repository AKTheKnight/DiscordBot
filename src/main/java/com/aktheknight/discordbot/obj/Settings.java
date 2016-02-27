package com.aktheknight.discordbot.obj;

/**
 * Created by Alex on 22/02/2016 at 15:20.
 */
public class Settings {

    private String SettingsForTheBot = "Speak to @AKTheKnight if you need any help";
    private String botEmail;
    private String botPassword;
    private String botName;
    private boolean printAllChat;
    private String adminUserID;

    public Settings(String botEmail, String botPassword, String botName, boolean printAllChat, String adminUserID) {
        this.botEmail = botEmail;
        this.botPassword = botPassword;
        this.botName = botName;
        this.printAllChat = printAllChat;
        this.adminUserID = adminUserID;
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

}
