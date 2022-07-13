package me.Nutella;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import org.jetbrains.annotations.NotNull;
import javax.annotation.Nonnull;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;


public class Commands extends ListenerAdapter {

    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Message mes = event.getMessage();
        String messageSent = event.getMessage().getContentRaw();
        String[] message = messageSent.split(" ");

        if (message[0].equalsIgnoreCase("!lb")) {
            try {
                Leaderboard printLb = new Leaderboard();
                int pageNumber = 0;

                if (message.length == 1) {
                    pageNumber = 0;
                } else {
                    pageNumber = Integer.valueOf(message[1]);
                }
                String lb = printLb.printLeaderboard(pageNumber);
                if(lb.startsWith("Page") || lb.startsWith("Leaderboard is empty.")){
                    event.getChannel().sendMessage(lb).queue();
                }else {
                    event.getChannel().sendMessage("```\n" + lb + "```").queue();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else if (message[0].equalsIgnoreCase("!help")) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Nut Ranked Bot", null);
            eb.setColor(new Color(137, 207, 240));
            eb.setFooter("Nutting is Fun");
            eb.setThumbnail("https://i.ytimg.com/vi/X_t_VJR0ZTI/maxresdefault.jpg");
            eb.addField("Commands:", "!r (player) - Displays the rank, k/d, & win percentage of player.\n" +
                    "!lb (page) - Displays the specified page of the Elo LB.\n" +
                    "!plb (page) - Displays the specified page of the previous Elo LB.\n"+
                    "!caps - Lists the two captains of a game.\n" +
                    "!szn (szn number) - Link to spreadsheet for the mentioned season.\n" +
                    "!cf - Flips a coin.\n" +
                    "!jbk Rules - Learn to play JBK against the NR Bot.", false);
            event.getChannel().sendMessageEmbeds(eb.build()).queue();

        } else if (message[0].equalsIgnoreCase("!r")) {
            if(message.length == 2) {
                importStats findPlayer = new importStats(message[1]);
                try {
                    event.getChannel().sendMessage(findPlayer.findPlayerStats(message[1])).queue();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else{
                importStats findPlayerByID = new importStats("ID");
                String id = mes.getAuthor().getId();

                try {
                    event.getChannel().sendMessage(findPlayerByID.findPlayerStatsWithID(id)).queue();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        } else if (message[0].equalsIgnoreCase("!test")) {
            event.getChannel().sendMessage("test").queue();

        } else if (message[0].equalsIgnoreCase("!szn")) {
            int sznNumber;
            if (message.length == 1) {
                sznNumber = 1;
            } else {
                sznNumber = Integer.valueOf(message[1]);
            }
            if (sznNumber == 1) {
                event.getChannel().sendMessage("https://docs.google.com/spreadsheets/d/13_ho-GoV-61zbJ5Ra5xZ8kXJnTkJM8xdIe2_KQrEL28/edit?usp=sharing").queue();
            } else if (sznNumber == 2) {
                event.getChannel().sendMessage("https://docs.google.com/spreadsheets/d/1EqGPkcimlyJ2_RTYQ121BvPx3kiePNofVZgEltET__4/edit?usp=sharing").queue();
            } else if (sznNumber == 3) {
                event.getChannel().sendMessage("https://docs.google.com/spreadsheets/d/1PjGpXgcLQlMFmiOP0NyBOXxDDvEU76f4jT2z1Lj3Ox0/edit?usp=sharing").queue();
            } else if (sznNumber == 4) {
                event.getChannel().sendMessage("https://docs.google.com/spreadsheets/d/1Sw2WJVuLIoh932NGFIY16O3tUxK4ZwkA60qb1gUSaWw/edit?usp=sharing").queue();
            } else if (sznNumber == 5) {
                event.getChannel().sendMessage("https://docs.google.com/spreadsheets/d/1yKKvN__mdbuHS9sHr2lpKI7D4HBP_gYy01aE1Xyy2vw/edit?usp=sharing").queue();
            } else {
                event.getChannel().sendMessage("Stats for season specified are unavailable.").queue();
            }

        } else if (message[0].equalsIgnoreCase("!cf")) {
            int num = (int) (Math.random() * 2) + 1;
            if (num == 1) {
                event.getChannel().sendMessage("Heads!").queue();
            } else if (num == 2) {
                event.getChannel().sendMessage("Tails!").queue();
            }

        } else if (message[0].equalsIgnoreCase("!clearLb")) {
            Leaderboard lb = new Leaderboard();
            if (message.length == 1) {
            } else if (message[1].equals("NuttySis0318")) {
                try {
                    lb.clearLeaderboard();
                    event.getChannel().sendMessage("Leaderboard cleared!").queue();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                event.getChannel().sendMessage("Incorrect Password!").queue();
            }

        } else if (message[0].equalsIgnoreCase("!miyagi")) {
            event.getChannel().sendMessage("Underage Nut Ranked Worker.").queue();

        }else if(message[0].equalsIgnoreCase("!brenna")) {
            event.getChannel().sendMessage("eat ass smoke grass sled fast").queue();

        }else if(message[0].equalsIgnoreCase("!blue")){
            event.getChannel().sendMessage("YOOOOO WASSS POPPIN!!!").queue();

        }else if(message[0].equalsIgnoreCase("!staffList")||message[0].equalsIgnoreCase("!sl")){
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Nut Ranked Staff List", null);
            eb.setColor(new Color(137, 207, 240));
            eb.setFooter("Nutting is Fun");
            eb.addField("","**Admins**\n" + "Baller\n" + "Brenna\n" + "Nutella\n\n**Intern**\nMiyagi", true);
            eb.addField("","**Staff**\nBlue\nNeshe\nOwen\nSky\nSooki\nToggled",true);
            eb.addField("","**Advisors**\nKie\nVmin\nWosy",true);
            eb.setImage("https://i.ytimg.com/vi/X_t_VJR0ZTI/maxresdefault.jpg");
            event.getChannel().sendMessageEmbeds(eb.build()).queue();


        } else if (message[0].equalsIgnoreCase("!jbk")) {
            if(message[1].equalsIgnoreCase("rules")){
                event.getChannel().sendMessage("\n**Rules of JBK:**\nJBK(Jar, Bread, Knife) is another version of Rock, Paper, Scissors.\nJar beats Knife." +
                        "\nBread beats Jar.\nKnife beats Bread.\nDo !jbk (Your Move) to verse the Nut Ranked Bot.").queue();
            }

            int num = (int) (Math.random() * 3) + 1;
            String cpuChoice = "";
            if (num == 1) {
                cpuChoice = "jar";
            } else if (num == 2) {
                cpuChoice = "bread";
            } else if(num == 3){
                cpuChoice = "knife";
            }

            if (cpuChoice.equalsIgnoreCase(message[1])) {
                event.getChannel().sendMessage("It's a tie.\nYou both chose: " + cpuChoice).queue();
            }else if (message[1].equalsIgnoreCase("jar") && cpuChoice.equalsIgnoreCase("knife")) {
                event.getChannel().sendMessage("You win!\nYou chose: Jar\nNR Bot chose: Knife").queue();
            } else if (message[1].equalsIgnoreCase("knife") && cpuChoice.equalsIgnoreCase("jar")) {
                event.getChannel().sendMessage("You lose!\nYou chose: Knife\nNR Bot chose: Jar").queue();
            }else if (message[1].equalsIgnoreCase("bread") && cpuChoice.equalsIgnoreCase("jar")) {
                event.getChannel().sendMessage("You win!\nYou chose: Bread\nNR Bot chose: Jar").queue();
            }else if (message[1].equalsIgnoreCase("jar") && cpuChoice.equalsIgnoreCase("bread")) {
                event.getChannel().sendMessage("You lose!\nYou chose: Jar\nNR Bot chose: Bread").queue();
            }else if (message[1].equalsIgnoreCase("knife") && cpuChoice.equalsIgnoreCase("bread")) {
                event.getChannel().sendMessage("You win!\nYou chose: Knife\nNR Bot chose: Bread" ).queue();
            }else if (message[1].equalsIgnoreCase("bread") && cpuChoice.equalsIgnoreCase("knife")) {
                event.getChannel().sendMessage("You lose!\nYou chose: Bread\nNR Bot chose: Knife").queue();
            }
        }else if(message[0].equalsIgnoreCase("!ping") && message.length == 1){
            event.getChannel().sendMessage(event.getJDA().getGatewayPing() + "ms " + "<:NUT:842458245822283797>").queue();

        }else if(message[0].equalsIgnoreCase("!plb")){
            if(message.length==1){
                event.getChannel().sendMessage("```1. Simp4Wris - 2043\n" +  "2. ghostpinged - 1548\n" + "3. ToggledAim - 1472\n" + "4. Calebistheman - 1342\n" +
                        "5. Complextual - 1335\n" + "6. gexp - 1285\n" + "7. Tikru5 - 1282\n" + "8. Matlog - 1249\n" + "9. 1bonka - 1236\n" + "10. _Blacklizard - 1235\n" +
                        "11. bluepandaone - 1218\n" + "12. Mrster3 - 1198\n" + "13. hitches - 1161\n" + "14. TisforTiger - 1160\n" + "15. mp5_entity - 1157\n" +
                        "16. BigSlap - 1110\n" + "17. Coyu - 1110\n" + "18. Trigae - 1049\n" + "19. Nutellabro - 1045\n" + "20. _brxnna - 1043\n" + "21. PlusOneBen - 1036\n" +
                        "22. screamachine - 1035\n" + "23. Firechief89 - 1035\n" + "24. SmoothMusic - 1031\n" + "25. ImagineFarts - 1029\n" + "26. UnluckyBlaze - 1017\n" + "27. Chirs - 1016\n" +
                        "28. Asurey - 1015\n" + "29. Brasskicker - 1011\n" + "30. _Blane - 1011\n" + "31. Toolchests - 1009\n" + "32. Skygae - 1009\n" + "33. TheOriginalHam - 1006\n" +
                        "34. Demonified - 1005\n" + "35. Gingr - 1005```").queue();
            }else if(message[1].equalsIgnoreCase("1")){
                event.getChannel().sendMessage("```1. Simp4Wris - 2043\n" +  "2. ghostpinged - 1548\n" + "3. ToggledAim - 1472\n" + "4. Calebistheman - 1342\n" +
                        "5. Complextual - 1335\n" + "6. gexp - 1285\n" + "7. Tikru5 - 1282\n" + "8. Matlog - 1249\n" + "9. 1bonka - 1236\n" + "10. _Blacklizard - 1235\n" +
                        "11. bluepandaone - 1218\n" + "12. Mrster3 - 1198\n" + "13. hitches - 1161\n" + "14. TisforTiger - 1160\n" + "15. mp5_entity - 1157\n" +
                        "16. BigSlap - 1110\n" + "17. Coyu - 1110\n" + "18. Trigae - 1049\n" + "19. Nutellabro - 1045\n" + "20. _brxnna - 1043\n" + "21. PlusOneBen - 1036\n" +
                        "22. screamachine - 1035\n" + "23. Firechief89 - 1035\n" + "24. SmoothMusic - 1031\n" + "25. ImagineFarts - 1029\n" + "26. UnluckyBlaze - 1017\n" + "27. Chirs - 1016\n" +
                        "28. Asurey - 1015\n" + "29. Brasskicker - 1011\n" + "30. _Blane - 1011\n" + "31. Toolchests - 1009\n" + "32. Skygae - 1009\n" + "33. TheOriginalHam - 1006\n" +
                        "34. Demonified - 1005\n" + "35. Gingr - 1005```").queue();
            }else if(message[1].equalsIgnoreCase("2")){
                event.getChannel().sendMessage("```36. WallahiBrother - 1004\n" +
                        "37. northzee - 1002\n" +
                        "38. Guppy80 - 1002\n" +
                        "39. moffey335 - 1002\n" +
                        "40. edendoesminecraf - 1001\n" +
                        "41. theblockmazter - 1000\n" +
                        "42. Slooooooooooooth - 999\n" +
                        "43. ThunderHashira - 998\n" +
                        "44. ummobear - 997\n" +
                        "45. xM1Y - 997\n" +
                        "46. Pezaint - 997\n" +
                        "47. Ticdon - 997\n" +
                        "48. kwuromi - 995\n" +
                        "49. aquidneck - 994\n" +
                        "50. USLEE - 987\n" +
                        "51. PureInventor - 986\n" +
                        "52. civilized_person - 985\n" +
                        "53. BunnyGoSkrrt - 984\n" +
                        "54. Bulann - 984\n" +
                        "55. TopTitanic - 983\n" +
                        "56. BunnyLils - 982\n" +
                        "57. Cupcakecurious - 982\n" +
                        "58. Flanie - 981\n" +
                        "59. DandyMonkey - 974\n" +
                        "60. hbland - 969\n" +
                        "61. FireLord014 - 967\n" +
                        "62. CUDIXX - 963\n" +
                        "63. Kiegae - 962\n" +
                        "64. kvdg - 961\n" +
                        "65. masoof1 - 960\n" +
                        "66. Liezor - 959\n" +
                        "67. BackProblems - 957\n" +
                        "68. hfeynfenhyenfhef - 952\n" +
                        "69. pqndi - 946\n" +
                        "70. InConclusion - 944```").queue();
            }else if(message[1].equalsIgnoreCase("3")){
                event.getChannel().sendMessage("```71. Neshe - 936\n" +
                        "72. ATteam5 - 923\n" +
                        "73. NateusVincere - 919\n" +
                        "74. roybippen - 908\n" +
                        "75. MS_LIGHT - 898\n" +
                        "76. FearFierce - 891\n" +
                        "77. ItzBiscuit - 885\n" +
                        "78. Particularity - 877\n" +
                        "79. Zhal - 875\n" +
                        "80. CanadianSooki - 867\n" +
                        "81. Bevn - 851\n" +
                        "82. LordBaller - 844\n" +
                        "83. prqo1tapper - 830\n" +
                        "84. percci - 770\n" +
                        "85. oHuskii - 738\n" +
                        "86. Vilten - 735\n" +
                        "87. Classkicker - 727\n" +
                        "88. wrisx - 695\n" +
                        "89. W0SY - 666\n" +
                        "90. vmin - 193\n" +
                        "91. Totalio - -85\n" +
                        "92. WHATDADDYWANTS - -936```").queue();
            }else{
                event.getChannel().sendMessage("Previous LB page doesn't exist.").queue();
            }
        }else if (message[0].equalsIgnoreCase("!caps")){
            List<VoiceChannel> channelList = event.getGuild().getVoiceChannelsByName("Org #1" , true);
            VoiceChannel channel = channelList.get(0);

            ArrayList<String> idList = new ArrayList<>();
            if(channel.getMembers().size() == 0||channel.getMembers().size() == 1){
                event.getChannel().sendMessage("Org is empty or insufficient players. ").queue();
            }else if(channel.getMembers().size() > 10) {
                event.getChannel().sendMessage("Too many players. Spectators should leave the call momentarily.").queue();
            }else {
                for (Member m : channel.getMembers()) {
                    if (!m.getUser().isBot())
                        idList.add(m.getId());
                }

                importStats findCaptains = new importStats("");
                try {
                    event.getChannel().sendMessage(findCaptains.captains(idList)).queue();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        if (event.getName().equals("inputstats")) {
            TextInput body = TextInput.create("input:", "Input", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Input stats here")
                    .setMinLength(30)
                    .setMaxLength(1000)
                    .setRequired(true)
                    .build();

            Modal modal = Modal.create("inputStats", "Input-Stats")
                    .addActionRows(ActionRow.of(body))
                    .build();

            event.replyModal(modal).queue();
        } else if (event.getName().equals("removestats")) {
            TextInput body = TextInput.create("removestats:", "Input", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Input stats here")
                    .setMinLength(30)
                    .setMaxLength(1000)
                    .setRequired(true)
                    .build();

            Modal modal = Modal.create("removeStats", "Remove-Stats")
                    .addActionRows(ActionRow.of(body))
                    .build();

            event.replyModal(modal).queue();
        } else if (event.getName().equals("namechange")) {
            TextInput originalName = TextInput.create("originalName:", "Original Name:", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Input original name here:")
                    .setMinLength(1)
                    .setMaxLength(50)
                    .setRequired(true)
                    .build();

            TextInput newName = TextInput.create("newName:", "New name:", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Input new name here:")
                    .setMinLength(1)
                    .setMaxLength(50)
                    .setRequired(true)
                    .build();

            Modal modal = Modal.create("nameChange", "Name-Change")
                    .addActionRows(ActionRow.of(originalName), ActionRow.of(newName))
                    .build();
            event.replyModal(modal).queue();

        } else if (event.getName().equals("removeplayer")) {
            TextInput originalName = TextInput.create("removeplayer:", "Remove Player:", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Input name of player to remove:")
                    .setMinLength(1)
                    .setMaxLength(50)
                    .setRequired(true)
                    .build();


            Modal modal = Modal.create("removePlayer", "Remove-Player")
                    .addActionRows(ActionRow.of(originalName))
                    .build();
            event.replyModal(modal).queue();

        } else if (event.getName().equals("punishment")) {
            TextInput originalName = TextInput.create("name:", "Name:", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Input name here:")
                    .setMinLength(1)
                    .setMaxLength(50)
                    .setRequired(true)
                    .build();

            TextInput offense = TextInput.create("offense:", "Offense:", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Input description of offense here:")
                    .setMinLength(1)
                    .setMaxLength(200)
                    .setRequired(true)
                    .build();

            TextInput reductionAmount = TextInput.create("elo:", "Reduction Amount:", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Input elo amount here:")
                    .setMinLength(1)
                    .setMaxLength(2)
                    .setRequired(true)
                    .build();

            Modal modal = Modal.create("punishment", "Punishment:")
                    .addActionRows(ActionRow.of(originalName), ActionRow.of(offense), ActionRow.of(reductionAmount))
                    .build();
            event.replyModal(modal).queue();

        } else if (event.getName().equals("give")) {
            TextInput originalName = TextInput.create("name:", "Name:", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Input name here:")
                    .setMinLength(1)
                    .setMaxLength(50)
                    .setRequired(true)
                    .build();

            TextInput increaseAmount = TextInput.create("elo:", "Increase Amount:", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Input elo amount here:")
                    .setMinLength(1)
                    .setMaxLength(2)
                    .setRequired(true)
                    .build();

            Modal modal = Modal.create("giveElo", "Give Elo:")
                    .addActionRows(ActionRow.of(originalName), ActionRow.of(increaseAmount))
                    .build();
            event.replyModal(modal).queue();

        } else if (event.getName().equals("combinestats")) {
            TextInput originalName = TextInput.create("originalName:", "Original Name:", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Input original IGN here:")
                    .setMinLength(1)
                    .setMaxLength(50)
                    .setRequired(true)
                    .build();

            TextInput newName = TextInput.create("newName:", "Input new IGN here:", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Enter player's new IGN to combine stats under:")
                    .setMinLength(1)
                    .setMaxLength(50)
                    .setRequired(true)
                    .build();

            Modal modal = Modal.create("combineStats", "Combine Stats")
                    .addActionRows(ActionRow.of(originalName), ActionRow.of(newName))
                    .build();
            event.replyModal(modal).queue();
        } else if (event.getName().equals("linkid")) {
            TextInput ign = TextInput.create("IGN:", "IGN:", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Input Minecraft IGN here:")
                    .setMinLength(1)
                    .setMaxLength(50)
                    .setRequired(true)
                    .build();

            TextInput discordId = TextInput.create("id:", "Discord Id:", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Input user's discord ID here: ")
                    .setMinLength(1)
                    .setMaxLength(100)
                    .setRequired(true)
                    .build();

            Modal modal = Modal.create("linkId", "Link ID")
                    .addActionRows(ActionRow.of(ign), ActionRow.of(discordId))
                    .build();
            event.replyModal(modal).queue();
        }
    }

}
