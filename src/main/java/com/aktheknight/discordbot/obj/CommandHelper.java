package com.aktheknight.discordbot.obj;

/**
 * Created by Alex on 21/02/2016 at 11:58.
 */
public class CommandHelper {

    private String[] command;

    public CommandHelper(String cmd) {
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
