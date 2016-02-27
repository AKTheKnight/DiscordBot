package com.aktheknight.discordbot.obj;

import java.util.ArrayList;

/**
 * Created by Alex on 27/02/2016 at 14:55.
 */
public class Command {
    private String name;
    private boolean admin;
    private int argNum;
    private ArrayList<String> reply;

    public Command(String name, boolean admin, int argNum, ArrayList<String> reply) {
        this.name = name;
        this.admin = admin;
        this.argNum = argNum;
        this.reply = reply;
    }

    public String getName() {
        return name;
    }

    public boolean getAdmin() {
        return admin;
    }

    public int getArgNum() {
        return argNum;
    }

    public ArrayList getReply() {
        return reply;
    }

    public int getReplyLength() {
        return reply.size();
    }

    public String getReplyNum(int n) {
        return reply.get(n);
    }
}
