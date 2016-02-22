package com.aktheknight.discordbot;

/**
 * Created by Alex on 21/02/2016 at 11:58.
 */
public class Command {

    private String[] command;

    public Command(String cmd) {
        command = cmd.split(" ");
    }

    public int getArgNum() {
        return command.length;
    }

    public String getArg(int i) {
        return command[i];
    }

    public String[] getCommand() {
        return command;
    }

    public void setCommand(String[] command) {
        this.command = command;
    }

    public void setCommand(String str) {
        this.command = str.split(" ");
    }
}

