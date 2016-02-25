package com.aktheknight.discordbot.obj;

/**
 * Created by Alex on 25/02/2016 at 11:52.
 */
public class Command {
    private String name;
    private boolean admin;
    private int argNum;
    private String reply;
    private boolean argAtEnd;
    private int endArg;
    private boolean replyAfterArg;
    private String reply2;

    public Command(String name, boolean admin, int argNum, String reply, boolean argAtEnd, int endArg, boolean replyAfterArg, String reply2) {
        this.name = name;
        this.admin = admin;
        this.argNum = argNum;
        this.reply = reply;
        this.argAtEnd = argAtEnd;
        this.endArg = endArg;
        this.replyAfterArg = replyAfterArg;
        this.reply2 = reply2;
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

    public String getReply() {
        return reply;
    }

    public boolean getArgAtEnd() {
        return argAtEnd;
    }

    public int getEndArg() {
        return endArg;
    }

    public boolean getReplyAfterArg() {
        return replyAfterArg;
    }

    public String getReply2() {
        return reply2;
    }
}
