package me.Nutella;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ModalListeners extends ListenerAdapter {

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        if (event.getModalId().equals("inputStats")) {

            String message = event.getValue("input:").getAsString();
            importStats stats = new importStats(message);
            try {
                ArrayList<String> unlinked = stats.importAllStats();
                if(unlinked.size() > 0){
                    String mesAuthor = event.getMember().getUser().getId();
                    String mes = "<@" + mesAuthor + "> Please link the following player(s) to their discord ID:\n ";
                    for (String player : unlinked){
                        mes+=player + "\n";
                    }
                    TextChannel textChannel = event.getGuild().getTextChannelsByName("staff-commands-log",true).get(0);
                    textChannel.sendMessage(mes).queue();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            event.reply("```" + message + "```").queue();

        } else if (event.getModalId().equals("removeStats")) {
            String message = event.getValue("removestats:").getAsString();
            importStats removeStats = new importStats(message);
            try {
                removeStats.removeStats();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            event.reply("```" + message + "```").queue();

        } else if (event.getModalId().equals("nameChange")) {
            String originalName = event.getValue("originalName:").getAsString();
            String newName = event.getValue("newName:").getAsString();
            importStats nameChange = new importStats(originalName);
            try {
                String status = nameChange.nameChange(originalName, newName);
                event.reply(status).queue();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        } else if (event.getModalId().equals("removePlayer")) {
            String playerToRemove = event.getValue("removeplayer:").getAsString();
            importStats removePlayer = new importStats(playerToRemove);
            try {
                String status = removePlayer.removePlayer(playerToRemove);
                event.reply(status).queue();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else if (event.getModalId().equals("punishment")) {
            String player = event.getValue("name:").getAsString();
            String offense = event.getValue("offense:").getAsString();
            String elo = event.getValue("elo:").getAsString();

            int eloAmount = Integer.valueOf(elo);
            importStats playerToReduce = new importStats(player);
            try {
                String status = playerToReduce.punishment(offense, player, eloAmount);
                event.reply(status).queue();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        } else if (event.getModalId().equals("giveElo")) {
            String player = event.getValue("name:").getAsString();
            String elo = event.getValue("elo:").getAsString();

            int eloAmount = Integer.valueOf(elo);
            importStats playerToIncrease = new importStats(player);
            try {
                String status = playerToIncrease.giveElo(player, eloAmount);
                event.reply(status).queue();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        } else if (event.getModalId().equals("combineStats")) {
            String originalName = event.getValue("originalName:").getAsString();
            String newName = event.getValue("newName:").getAsString();
            importStats nameChange = new importStats(originalName);
            try {
                String status = nameChange.combineStats(originalName,newName);
                event.reply(status).queue();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        } else if (event.getModalId().equals("linkId")) {
            String ign = event.getValue("IGN:").getAsString();
            String id = event.getValue("id:").getAsString();
            importStats discordIDLink = new importStats("");
            try {
                String status = discordIDLink.linkID(id,ign);
                event.reply(status).queue();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
