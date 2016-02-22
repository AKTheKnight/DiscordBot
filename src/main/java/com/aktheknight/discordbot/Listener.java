package com.aktheknight.discordbot;

import sx.blah.discord.handle.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.MessageBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alex on 21/02/2016.
 */
public class Listener {

    Date dNow = new Date();
    SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");


    @EventSubscriber
    public void onMessageReceivedEvent(MessageReceivedEvent event) {
        IMessage m = event.getMessage();
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
                Command c = new Command(m.getContent());
                if (c.getArgNum() < 6) {
                    new MessageBuilder(DiscordBot.client).withChannel(m.getChannel()).appendContent("Not enough args for $hello").build();
                    return;
                }
                String name = c.getArg(6);
                new MessageBuilder(DiscordBot.client).withChannel(m.getChannel()).appendContent("Hello " + name + ". I am AKTheBot. How are you?").build();
            }
            if (m.getAuthor().getName().equals("AKTheKnight") && m.getContent().startsWith("$hello")) {
                Command c = new Command(m.getContent());
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
