package me.Nutella;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import javax.security.auth.login.LoginException;

public class Main {
    public static void main(String[] args) throws LoginException, InterruptedException {

        JDA bot = JDABuilder.createDefault("ODM0NTU3MzMyNzk1OTQ5MDk2.GvNziM.A5CeQhx26Z7CV13owg4oWphx5Kng-qqHfm4ab4")
                .setActivity(Activity.playing("Nut Ranked"))
                .addEventListeners(new Commands(),new ModalListeners())
                .build().awaitReady();

        Guild guild = bot.getGuildById("822243238483853313");

        if(guild != null){
            guild.upsertCommand("inputstats","Input Stats").queue();
            guild.upsertCommand("removestats","Remove Stats").queue();
            guild.upsertCommand("namechange", "Change name of a player").queue();
            guild.upsertCommand("removeplayer", "Removes a player from the leaderboard").queue();
            guild.upsertCommand("punishment", "Reduces Elo of a player").queue();
            guild.upsertCommand("give", "Increases Elo for a player").queue();
            guild.upsertCommand("combinestats", "Combines stats of two IGNs for a player.").queue();
            guild.upsertCommand("linkid", "Link discord ID to player's MC ign.").queue();


        }
    }
}
