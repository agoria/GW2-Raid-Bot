package me.cbitler.raidbot.commands;

import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class HelpCommand implements Command {
    private final String helpMessage = "Riz-GW2-Event-Bot Help:\n" +
            "Commands:\n" +
            "**!createEvent** - Start the event creation process. Usable by people with the event manager role.\n" +
            "**!endEvent [event id] [log link 1] [log link 2] ...** - End an event, removing the message and DM'ing the users in the event with log links. The log links are optional arguments.\n" +
            "**!editEvent [event id]** - Start the event edit process. Usable by people with then event manager role.\n" +
            "**!removeFromEvent [event id] [name]** - Remove a player from an event. Only usable by people with the event manager role.\n" +            
            "**!help** - You are looking at it.\n" +
            "**!info** - Information about the bot and it's authors.\n" +
            "**!setEventManagerRole [role]** - Set the role that serves as an event manager. This is only usable by people with the manage server permission.\n" + 
            "\n\n" +
            "Help information:\n" +
            "To use this bot, set the event manager role, and then anyone with that role can use !createEvent. This will take them through" +
            " an event setup process with the bot prompting them for information. After that, it will create the event in the channel specified" +
            " Once that is there, people can join it by clicking on the reaction for their specialization, and then responding to the bot with the role" +
            " that they want.";
    @Override
    public void handleCommand(String command, String[] args, TextChannel channel, User author) {
        channel.sendMessage(helpMessage).queue();
    }
}