package com.aktheknight.discordbot.obj;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Alex on 27/02/2016 at 14:55.
 */
@SuppressWarnings("SameParameterValue")
public class Command {
    private String name;
    private boolean admin;
    private int argNum;
    private String aliases;
    private ArrayList<String> reply;

    /**
     * Only used for creating the default template at the moment
     * @param name usage of command (missing $)
     * @param admin whether admin is required for executing command
     * @param argNum how many args expected on execution
     * @param aliases aliases for command
     * @param reply reply on execution of command
     */
    public Command(String name, boolean admin, int argNum, String aliases, ArrayList<String> reply) {
        this.name = name;
        this.admin = admin;
        this.argNum = argNum;
        this.aliases = aliases;
        this.reply = reply;
    }

    /**
     * @return name of command (usage)
     */
    public String getName() {
        return name;
    }

    /**
     * @return whether admin permissions are needed to execute command
     */
    public boolean getAdmin() {
        return admin;
    }

    /**
     * @return number of args expected when using command
     */
    public int getArgNum() {
        return argNum;
    }

    /**
     * @return list of aliases split by " , "
     */
    public ArrayList getAliases() {
        return new ArrayList<>(Arrays.asList(aliases.split("\\s*,\\s*")));
    }

    /**
     * @return list of replies
     */
    public ArrayList getReply() {
        return reply;
    }
}
