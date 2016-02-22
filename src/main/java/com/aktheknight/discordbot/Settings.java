package com.aktheknight.discordbot;

/**
 * Created by Alex on 22/02/2016 at 15:20.
 */
public class Settings {

    private String botUsername;
    private String botPassword;
    private boolean printAllChat;

    public Settings(String botUsername, String botPassword, boolean printAllChat) {
        this.botUsername = botUsername;
        this.botPassword = botPassword;
        this.printAllChat = printAllChat;
    }

    public String getBotPassword() {
        return botPassword;
    }

    public String getBotUsername() {
        return botUsername;
    }

    public boolean getPrintAllChat() {
        return printAllChat;
    }

    public void setPrintAllChat(boolean printAllChat) {
        this.printAllChat = printAllChat;
    }


}
