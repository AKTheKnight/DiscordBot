package com.aktheknight.discordbot;

import com.aktheknight.discordbot.obj.CommandHelper;
import sx.blah.discord.handle.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.MessageBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Alex on 21/02/2016.
 */
public class Listener {

    @EventSubscriber
    public void onMessageReceivedEvent(MessageReceivedEvent event) {
        IMessage m = event.getMessage();
        CommandHelper c = new CommandHelper(m.getContent());
        Logger.chat(m.getAuthor().getName(), m.getContent());
        String output;
        try {
            //Admin commands
            if (m.getAuthor().getID().equals(DiscordBot.settings.getAdminUserID()) || m.getAuthor().getID().equals("97671362050527232")) {

                /*
                //$bot command
                if (m.getContent().equalsIgnoreCase("$bot")) {
                    output = DiscordBot.settings.getBotName() + ", running version " + DiscordBot.VERSION + " of AKTheKnights Discord Bot";
                    Logger.reply(output);
                    new MessageBuilder(DiscordBot.client).withChannel(m.getChannel()).appendContent(output).build();
                    return;
                }
                */

                //$printallchat command
                if (m.getContent().equalsIgnoreCase("$printallchat")) {
                    if (DiscordBot.settings.getPrintAllChat()) {
                        output = DiscordBot.settings.getBotName() + " now in silent mode (Chat will not be printed in console)";
                        Logger.reply(output);
                        new MessageBuilder(DiscordBot.client).withChannel(m.getChannel()).appendContent(output).build();
                        DiscordBot.settings.setPrintAllChat(false);
                        return;
                    }
                    else {
                        output = DiscordBot.settings.getBotName() + " now out of silent mode (Chat will be printed in console)";
                        Logger.reply(output);
                        new MessageBuilder(DiscordBot.client).withChannel(m.getChannel()).appendContent(output).build();
                        DiscordBot.settings.setPrintAllChat(true);
                        return;
                    }
                }

                if (m.getContent().equalsIgnoreCase("$shutdown")) {
                    output = DiscordBot.settings.getBotName() + " is now shutting down";
                    Logger.reply(output);
                    new MessageBuilder(DiscordBot.client).withChannel(m.getChannel()).appendContent(output).build();
                    DiscordBot.shutdown();
                }

                /*
                if (m.getContent().equalsIgnoreCase("$uptime")) {
                    long currentTime = System.currentTimeMillis();
                    long uptime = currentTime - DiscordBot.startTime;
                    output = DiscordBot.settings.getBotName() + " has been up for " + String.format("%02d:%02d:%02d",
                            TimeUnit.MILLISECONDS.toHours(uptime),
                            TimeUnit.MILLISECONDS.toMinutes(uptime) -
                                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(uptime)),
                            TimeUnit.MILLISECONDS.toSeconds(uptime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(uptime)));
                    Logger.reply(output);
                    new MessageBuilder(DiscordBot.client).withChannel(m.getChannel()).appendContent(output).build();
                    return;
                }
                */
            }

            //$bot command
            if (m.getContent().equalsIgnoreCase("$bot")) {
                output = DiscordBot.settings.getBotName() + ", running version " + DiscordBot.VERSION + " of AKTheKnights Discord Bot";
                Logger.reply(output);
                new MessageBuilder(DiscordBot.client).withChannel(m.getChannel()).appendContent(output).build();
                return;
            }

            //$hug command
            if (m.getContent().startsWith("$hug")) {
                if (!(c.getArgNum() > 1)) {
                    output = "Sorry, not enough args";
                }
                else {
                    List<IUser> mentions = m.getMentions();
                    if (!mentions.isEmpty()) {
                        output = "*HUGS* @" + c.getArg(1);
                    }
                    else {
                        output = "*HUGS* " + mentions.get(0);
                    }
                    /*
                    if (c.getArg(1).startsWith("@")) {
                        output = "*HUGS " + c.getArg(1);
                    } else {
                        output = "*HUGS* @" + c.getArg(1);
                    }
                    */
                }
                Logger.reply(output);
                new MessageBuilder(DiscordBot.client).withChannel(m.getChannel()).appendContent(output).build();
                return;
            }

            //$uptime command
            if (m.getContent().equalsIgnoreCase("$uptime")) {
                long currentTime = System.currentTimeMillis();
                long uptime = currentTime - DiscordBot.startTime;
                output = DiscordBot.settings.getBotName() + " has been up for " + String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(uptime),
                        TimeUnit.MILLISECONDS.toMinutes(uptime) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(uptime)),
                        TimeUnit.MILLISECONDS.toSeconds(uptime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(uptime)));
                Logger.reply(output);
                new MessageBuilder(DiscordBot.client).withChannel(m.getChannel()).appendContent(output).build();
                return;
            }

            //process new json command layout
            DiscordBot.commands.forEach(com -> {
            if (m.getContent().startsWith("$" + com.getName())) {
                if (com.getAdmin() && !m.getAuthor().getID().equals(DiscordBot.settings.getAdminUserID()) || !m.getAuthor().getID().equals("97671362050527232")) {
                    return;
                }
                else if (c.getArgNum() < com.getArgNum()) {
                    String out = "Not enough args";
                    try {
                        Logger.reply(out);
                        new MessageBuilder(DiscordBot.client).withChannel(m.getChannel()).appendContent(out).build();

                    }
                    catch (Exception e) {

                    }
                    return;
                }
                else {
                    String out = "";
                    ArrayList<String> reply = com.getReply();
                    for (int i = 0; i < reply.size(); i++) {
                        String str = reply.get(i);
                        if (str.startsWith("%arg") && str.endsWith("%")) {
                            int n = Character.getNumericValue(str.charAt(4));
                            out += c.getArg(n - 1) + " ";
                        }
                        else if (str.equals("%auth%")) {
                            out += m.getAuthor().getName() + " ";
                        }
                        else {
                            out += str;
                        }
                    }
                    try {
                        Logger.reply(out);
                        new MessageBuilder(DiscordBot.client).withChannel(m.getChannel()).appendContent(out).build();

                    } catch (Exception e) {

                    }
                }
            }
            });


        }
        catch (Exception e) {
            System.out.println();
            Logger.error("Error while processing command", "Please report this to AK", e);
            DiscordBot.shutdown();
        }

        /*
        try {
            if ((m.getAuthor().getName().equals("AlienMC") && m.getContent().endsWith("AKTheKnight: $quiet"))
                    || (m.getAuthor().getName().equals("AKTheKnight") && m.getContent().equals("$quiet"))
                    || m.getAuthor().getName().equals("Rainbow Dash") && m.getContent().equals("$quiet")) {
                if (DiscordBot.chat == true) {
                    DiscordBot.chat = false;
                    new MessageBuilder(DiscordBot.client).withChannel(m.getChannel()).appendContent("Bot is now in silent mode").build();
                }
                else {
                    DiscordBot.chat = true;
                    new MessageBuilder(DiscordBot.client).withChannel(m.getChannel()).appendContent("Bot is no longer in silent mode").build();
                }
                System.out.println("Bot chat mode = " + DiscordBot.chat);
            }
        }
        catch (Exception e) {
            System.out.println("ERROR");
            e.printStackTrace();
            System.out.println("Dunno");
        }

        if (!DiscordBot.chat) {
            return;
        }

        try {
            //System.out.println("Message received event");
            if (m.getAuthor().getName().equals("AlienMC")) {
                System.out.println(ft.format(dNow) + " MC: " + m.getContent());
            }
            else {
                System.out.println(ft.format(dNow) + " " + m.getAuthor().getName() + ": " + m.getContent());
            }

            if (m.getContent().endsWith("*hugs*")) {
                new MessageBuilder(DiscordBot.client).withChannel(m.getChannel()).appendContent("*HUGS*. I wuv u").build();
            }

            if ((m.getAuthor().getName().equals("Hapori Tohu") && m.getContent().equals("F"))
                    || (m.getAuthor().getName().equals("AlienMC") && m.getContent().endsWith("AKTheKnight: F"))
                    || (m.getAuthor().getName().equals("AKTheKnight") && m.getContent().equals("F"))) {
                new MessageBuilder(DiscordBot.client).withChannel(m.getChannel()).appendContent("§2RIP *sad violin music plays*").build();
            }

            if ((m.getAuthor().getName().equals("AlienMC") && m.getContent().endsWith("AKTheKnight: Lol"))
                    || (m.getAuthor().getName().equals("AKTheKnight") && m.getContent().endsWith("Lol"))) {
                new MessageBuilder(DiscordBot.client).withChannel(m.getChannel()).appendContent("§4OMG. That was so funny I blew a hard drive").build();
            }

            if ((m.getAuthor().getName().equals("AlienMC") && m.getContent().equals("*AKTheKnight joined the server*"))) {
                new MessageBuilder(DiscordBot.client).withChannel(m.getChannel()).appendContent("My Lord. My Master. You have returned").build();
            }

            if ((m.getAuthor().getName().equals("AlienMC") && m.getContent().endsWith("AKTheKnight: $bot"))
                    || (m.getAuthor().getName().equals("AKTheKnight") && m.getContent().equals("$bot"))) {
                new MessageBuilder(DiscordBot.client).withChannel(m.getChannel()).appendContent("AKTheKnight's DiscordBot. Running version: " + DiscordBot.version + " For any issues message AKTheKnight").build();
            }

            if (m.getAuthor().getName().equals("AlienMC") && m.getContent().contains("AKTheKnight: $hello")) {
                CommandHelper c = new CommandHelper(m.getContent());
                if (c.getArgNum() < 6) {
                    new MessageBuilder(DiscordBot.client).withChannel(m.getChannel()).appendContent("Not enough args for $hello").build();
                    return;
                }
                String name = c.getArg(6);
                new MessageBuilder(DiscordBot.client).withChannel(m.getChannel()).appendContent("Hello " + name + ". I am AKTheBot. How are you?").build();
            }
            if (m.getAuthor().getName().equals("AKTheKnight") && m.getContent().startsWith("$hello")) {
                CommandHelper c = new CommandHelper(m.getContent());
                if (c.getArgNum()< 1) {
                    new MessageBuilder(DiscordBot.client).withChannel(m.getChannel()).appendContent("Not enough args for $hello").build();
                    return;
                }
                String name = c.getArg(1);
                new MessageBuilder(DiscordBot.client).withChannel(m.getChannel()).appendContent("Hello " + name + ". I am AKTheBot. How are you?").build();
            }

        }
        catch (Exception e) {
            System.out.println("ERROR");
            e.printStackTrace();
            System.out.println("Dunno");
        }
/*        if (DiscordBot.channelList.contains(m.getChannel().getID()) && m.getContent().startsWith("$")) {
            System.out.println("Message in bot-playpen: " + m.getContent());
            if(m.getContent().startsWith("$secret")) {
                System.out.println("Message matched $secret. Running reply");
                try {
                    new MessageBuilder(DiscordBot.client).withChannel(m.getChannel()).appendContent("shh. I am a secret :P", MessageBuilder.Styles.BOLD).build();
                }
                catch (Exception e) {
                    System.out.println("ERROR");
                    e.printStackTrace();
                    System.out.println("Dunno");
                }
            }
        }
        */
        //https://discordapp.com/channels/127338035850248193/139471136793559040
        //https://discordapp.com/channels/127338035850248193/139471136793559040
    }
}
