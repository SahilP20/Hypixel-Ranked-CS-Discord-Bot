package me.Nutella;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
public class importStats {
    private String stats;
    private String[] statsList;

    public importStats(String stats) {
        this.stats = stats;
        this.statsList = stats.split("\n");
    }

    public ArrayList<String> importAllStats() throws IOException {
        ArrayList<String> unlinkedPlayers = new ArrayList<>();
        String[] ScoreLine1 = this.statsList[1].split(" ");
        String wOrL = ScoreLine1[1];
        if (wOrL.equalsIgnoreCase("12")) {
            for (int i = 2; i <= 13; i++) {
                if (i == 7 || i == 8) {
                    continue;
                } else {
                    String results = "l";
                    if (2 <= i && i <= 6) {
                        results = "w";
                    }
                    String[] playerArray = this.statsList[i].split(" ");
                    if (this.isPlayerOnLB(playerArray[0])) {
                        String updatedLine = this.updateLine(results, this.statsList[i]);
                        ChangeLineInFile changeLineInFile = new ChangeLineInFile();
                        changeLineInFile.changeALineInATextFile("Leaderboard.txt", updatedLine, findLineNumber(playerArray[0]));
                    } else {
                        addPlayerToLB(results, this.statsList[i]);
                        unlinkedPlayers.add(playerArray[0]);
                    }
                }
            }
        } else {
            for (int i = 2; i <= 13; i++) {
                if (i == 7 || i == 8) {
                    continue;
                } else {
                    String results = "l";
                    if (9 <= i && i <= 13) {
                        results = "w";
                    }
                    String[] playerArray = this.statsList[i].split(" ");
                    if (this.isPlayerOnLB(playerArray[0])) {
                        String updatedLine = this.updateLine(results, this.statsList[i]);
                        ChangeLineInFile changeLineInFile = new ChangeLineInFile();
                        changeLineInFile.changeALineInATextFile("Leaderboard.txt", updatedLine, findLineNumber(playerArray[0]));
                    } else {
                        addPlayerToLB(results, this.statsList[i]);
                        unlinkedPlayers.add(playerArray[0]);
                    }
                }
            }
            Leaderboard lb = new Leaderboard();
            lb.sortLeaderboard();
        }
        return unlinkedPlayers;
    }

    public void removeStats() throws IOException {
        String[] ScoreLine1 = this.statsList[1].split(" ");
        String wOrL = ScoreLine1[1];
        if (wOrL.equalsIgnoreCase("12")) {
            for (int i = 2; i <= 13; i++) {
                if (i == 7 || i == 8) {
                    continue;
                } else {
                    String results = "l";
                    if (2 <= i && i <= 6) {
                        results = "w";
                    }
                    String[] playerArray = this.statsList[i].split(" ");
                    if (this.isPlayerOnLB(playerArray[0])) {
                        String updatedLine = this.removeLine(results, this.statsList[i]);
                        ChangeLineInFile changeLineInFile = new ChangeLineInFile();
                        changeLineInFile.changeALineInATextFile("Leaderboard.txt", updatedLine, findLineNumber(playerArray[0]));
                    }
                }
            }
        } else {
            for (int i = 2; i <= 13; i++) {
                if (i == 7 || i == 8) {
                    continue;
                } else {
                    String results = "l";
                    if (9 <= i && i <= 13) {
                        results = "w";
                    }
                    String[] playerArray = this.statsList[i].split(" ");
                    if (this.isPlayerOnLB(playerArray[0])) {
                        String updatedLine = this.removeLine(results, this.statsList[i]);
                        ChangeLineInFile changeLineInFile = new ChangeLineInFile();
                        changeLineInFile.changeALineInATextFile("Leaderboard.txt", updatedLine, findLineNumber(playerArray[0]));
                    }
                }
            }
        }
        Leaderboard lb = new Leaderboard();
        lb.sortLeaderboard();
    }

    public boolean isPlayerOnLB(String player) throws FileNotFoundException {
        File lb = new File("Leaderboard.txt");
        Scanner scan = new Scanner(lb);
        while (scan.hasNextLine()) {
            String playerStatsString = scan.nextLine();
            if (playerStatsString.equals("removed")) {
                continue;
            } else if (playerStatsString.isEmpty()) {
                break;
            } else {
                String[] playerStatsArray = playerStatsString.split(",");
                if (playerStatsArray[2].equalsIgnoreCase(player)) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isPlayerIDOnLB(String id) throws FileNotFoundException {
        File lb = new File("Leaderboard.txt");
        Scanner scan = new Scanner(lb);
        while (scan.hasNextLine()) {
            String playerStatsString = scan.nextLine();
            if (playerStatsString.equals("removed")) {
                continue;
            } else if (playerStatsString.isEmpty()) {
                break;
            } else {
                String[] playerStatsArray = playerStatsString.split(",");
                if (playerStatsArray[8].equalsIgnoreCase(id)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String updateLine(String winOrLoss, String statline) throws FileNotFoundException {
        String[] statLineArray = statline.split(" ");
        String IGN = statLineArray[0];
        String[] killDeathRatio = statLineArray[1].replace("-", " ").split(" ");
        String[] currentStatLine = findCurrentStatLine(IGN).split(",");

        int currentKills, killsGained, currentDeaths, deathsGained, currentElo, eloGained, currentWins, currentLosses;
        currentKills = Integer.valueOf(currentStatLine[3]);
        currentDeaths = Integer.valueOf(currentStatLine[4]);
        killsGained = Integer.valueOf(killDeathRatio[0]);
        deathsGained = Integer.valueOf(killDeathRatio[1]);
        currentElo = Integer.valueOf(currentStatLine[0]);
        currentWins = Integer.valueOf(currentStatLine[5]);
        currentLosses = Integer.valueOf(currentStatLine[6]);
        double winPercent;


        if (winOrLoss.equalsIgnoreCase("w")) {
            eloGained = 7 + (killsGained - deathsGained);
            currentWins++;
        } else {
            eloGained = -7 + (killsGained - deathsGained);
            currentLosses++;

        }
        if (currentLosses == 0) {
            winPercent = 100.00;
        } else {
            winPercent = 100.0 * currentWins / (currentLosses + currentWins);

        }

        int newKillCount = currentKills + killsGained, newDeathcount = currentDeaths + deathsGained;
        int newEloAmount = currentElo + eloGained;
        double newKillDeathRatio = 0.0;
        if (newDeathcount == 0) {
            newKillDeathRatio = 1.0 * newKillCount;
        } else {
            newKillDeathRatio = 1.0 * newKillCount / newDeathcount;
        }
        String updatedStats = newEloAmount + "," + String.format("%.3f", newKillDeathRatio) + "," + IGN + "," + newKillCount + ","
                + newDeathcount + "," + currentWins + "," + currentLosses + "," + String.format("%.2f", winPercent) + "," + currentStatLine[8];

        return updatedStats;

    }

    public String findCurrentStatLine(String IGN) throws FileNotFoundException {
        File lb = new File("Leaderboard.txt");
        Scanner scan = new Scanner(lb);
        while (scan.hasNextLine()) {
            String playerStatsString = scan.nextLine();
            if (playerStatsString.equals("removed")) {
                continue;
            }
            String[] playerStatsArray = playerStatsString.split(",");
            if (playerStatsArray[2].equalsIgnoreCase(IGN)) {
                scan.close();
                return playerStatsString;
            }
        }
        scan.close();
        return "Not on LB";
    }

    public void addPlayerToLB(String winOrLoss, String statLine) throws IOException {
        File lb = new File("Leaderboard.txt");
        FileWriter fileWriter = new FileWriter(lb, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        PrintWriter printWriter = new PrintWriter((bufferedWriter));

        String[] stats = statLine.split(" ");
        String[] killDeath = stats[1].replace("-", " ").split(" ");
        int deaths = Integer.valueOf(killDeath[1]), kills = Integer.valueOf(killDeath[0]), elo;
        double killDeathRatio = 0.0;
        int wins = 0;
        int losses = 0;
        double winPercent = 0.00;

        if (deaths == 0) {
            killDeathRatio = 1.0 * kills;
        } else {
            killDeathRatio = 1.0 * kills / deaths;
        }

        if (winOrLoss.equalsIgnoreCase("w")) {
            elo = 1007 + (kills - deaths);
            wins++;
            winPercent = 100.00;
        } else {
            elo = 993 + (kills - deaths);
            losses++;
        }


        String newStats = elo + "," + String.format("%.3f", killDeathRatio) + "," + stats[0] + "," + kills + "," + deaths + "," + wins + "," + losses + "," + String.format("%.2f", winPercent) + ",0";

        printWriter.println(newStats);
        bufferedWriter.close();
        printWriter.close();
    }

    public int findLineNumber(String IGN) throws FileNotFoundException {
        File lb = new File("Leaderboard.txt");
        Scanner scan = new Scanner(lb);
        int lineCount = 0;
        while (scan.hasNextLine()) {
            lineCount++;
            String playerStatsString = scan.nextLine();
            if (playerStatsString.equals("removed")) {
                continue;
            }
            String[] playerStatsArray = playerStatsString.split(",");
            if (playerStatsArray[2].equalsIgnoreCase(IGN)) {
                scan.close();
                return lineCount;
            }
        }
        scan.close();
        return lineCount;
    }

    public String findPlayerStats(String IGN) throws IOException {
        Leaderboard leb = new Leaderboard();
        leb.sortLeaderboard();
        File lb = new File("Empty.txt");
        Scanner scan = new Scanner(lb);
        if (isPlayerOnLB(IGN)) {
            int lineCount = 0;
            while (scan.hasNextLine()) {
                String playerStatsString = scan.nextLine();
                if (playerStatsString.equals("removed")) {
                    continue;
                }
                lineCount++;
                String[] playerStatsArray = playerStatsString.split(",");
                if (playerStatsArray[2].equalsIgnoreCase(IGN)) {
                    scan.close();
                    return "**" + playerStatsArray[2] + "**" + "\nRank: " + lineCount + "\nELO: " + playerStatsArray[0] + "\nK/D: " + playerStatsArray[1]
                            + "\nW/L: " + playerStatsArray[5] + "/" + playerStatsArray[6] + " (" + playerStatsArray[7] + "%)";
                }
            }

        }
        scan.close();
        return "Cannot find player: " + IGN;
    }
    public String findPlayerStatsWithID(String ID) throws IOException {
        Leaderboard leb = new Leaderboard();
        leb.sortLeaderboard();
        File lb = new File("Empty.txt");
        Scanner scan = new Scanner(lb);
        if (isPlayerIDOnLB(ID)) {
            int lineCount = 0;
            while (scan.hasNextLine()) {
                String playerStatsString = scan.nextLine();
                if (playerStatsString.equals("removed")) {
                    continue;
                }
                lineCount++;
                String[] playerStatsArray = playerStatsString.split(",");
                if (playerStatsArray[8].equalsIgnoreCase(ID)) {
                    scan.close();
                    return "**" + playerStatsArray[2] + "**" + "\nRank: " + lineCount + "\nELO: " + playerStatsArray[0] + "\nK/D: " + playerStatsArray[1]
                            + "\nW/L: " + playerStatsArray[5] + "/" + playerStatsArray[6] + " (" + playerStatsArray[7] + "%)";
                }
            }

        }
        scan.close();
        return "You have not played a game yet.";
    }

    public String removeLine(String winOrLoss, String statline) throws FileNotFoundException {
        String[] statLineArray = statline.split(" ");
        String IGN = statLineArray[0];
        String[] killDeathRatio = statLineArray[1].replace("-", " ").split(" ");
        String[] currentStatLine = findCurrentStatLine(IGN).split(",");

        int currentKills, killsGained, currentDeaths, deathsGained, currentElo, eloReduction = 0, currentWins, currentLosses;
        currentKills = Integer.valueOf(currentStatLine[3]);
        currentDeaths = Integer.valueOf(currentStatLine[4]);
        killsGained = Integer.valueOf(killDeathRatio[0]);
        deathsGained = Integer.valueOf(killDeathRatio[1]);
        currentElo = Integer.valueOf(currentStatLine[0]);
        currentWins = Integer.valueOf(currentStatLine[5]);
        currentLosses = Integer.valueOf(currentStatLine[6]);
        double winPercent;

        if (winOrLoss.equalsIgnoreCase("w")) {
            eloReduction = -7 + (deathsGained - killsGained);
            currentWins--;
        } else if (winOrLoss.equalsIgnoreCase("l")) {
            eloReduction = 7 + (deathsGained - killsGained);
            currentLosses--;
        }
        if (currentLosses == 0) {
            if (currentWins == 0) {
                winPercent = 0.00;
            } else {
                winPercent = 100.0;
            }
        } else {
            winPercent = 100.0 * currentWins / (currentLosses + currentWins);
        }

        int newKillCount = currentKills - killsGained, newDeathcount = currentDeaths - deathsGained;
        int newEloAmount = currentElo + eloReduction;
        double newKillDeathRatio = 0.0;
        if (newDeathcount == 0) {
            newKillDeathRatio = 1.0 * newKillCount;
        } else {
            newKillDeathRatio = 1.0 * newKillCount / newDeathcount;
        }
        String updatedStats = newEloAmount + "," + String.format("%.3f", newKillDeathRatio) + "," + IGN + "," + newKillCount + ","
                + newDeathcount + "," + currentWins + "," + currentLosses + "," + String.format("%.2f", winPercent) +"," +currentStatLine[8];

        return updatedStats;

    }

    public String nameChange(String originalName, String newName) throws FileNotFoundException {
        File lb = new File("Empty.txt");
        Scanner scan = new Scanner(lb);
        if (isPlayerOnLB(originalName)) {
            if (isPlayerOnLB(newName)) {
                return "```css\nOriginal Name - " + originalName + "\nNew Name - " + newName + "\nStatus - [Name Change Failure]\n```";
            }
            int lineCount = 0;
            while (scan.hasNextLine()) {
                lineCount++;
                String playerStatsString = scan.nextLine();
                if (playerStatsString.equals("removed")) {
                    continue;
                }
                String[] playerStatsArray = playerStatsString.split(",");
                if (playerStatsArray[2].equalsIgnoreCase(originalName)) {
                    scan.close();
                    String[] newNameLine = new String[9];
                    newNameLine[0] = playerStatsArray[0];
                    newNameLine[1] = playerStatsArray[1];
                    newNameLine[2] = newName;
                    newNameLine[3] = playerStatsArray[3];
                    newNameLine[4] = playerStatsArray[4];
                    newNameLine[5] = playerStatsArray[5];
                    newNameLine[6] = playerStatsArray[6];
                    newNameLine[7] = playerStatsArray[7];
                    newNameLine[8] = playerStatsArray[8];

                    String newLine = "";
                    int count = 0;
                    for (String stat : newNameLine) {
                        if (count == playerStatsArray.length - 1) {
                            newLine += stat;
                        } else {
                            newLine += stat + ",";
                        }
                    }
                    ChangeLineInFile changeLineInFile = new ChangeLineInFile();
                    ChangeLineInFile changeLineInFile1 = new ChangeLineInFile();
                    changeLineInFile.changeALineInATextFile("Leaderboard.txt", newLine, findLineNumber(originalName));
                    changeLineInFile1.changeALineInATextFile("Empty.txt", newLine, lineCount);

                    return "```ini\nOriginal Name - " + originalName + "\nNew Name - " + newName + "\nStatus - [Successfully Changed]\n```";
                }
            }
        }
        scan.close();
        return "```css\nOriginal Name - " + originalName + "\nNew Name - " + newName + "\nStatus - [Name Change Failure]\n```";
    }

    public String removePlayer(String ign) throws FileNotFoundException {
        File lb = new File("Leaderboard.txt");
        Scanner scan = new Scanner(lb);
        if (isPlayerOnLB(ign)) {
            int lineCount = 0;
            while (scan.hasNextLine()) {
                lineCount++;
                String playerStatsString = scan.nextLine();
                if (playerStatsString.equals("removed")) {
                    continue;
                }
                String[] playerStatsArray = playerStatsString.split(",");
                if (playerStatsArray[2].equalsIgnoreCase(ign)) {
                    ChangeLineInFile changeLineInFile = new ChangeLineInFile();
                    changeLineInFile.changeALineInATextFile("Leaderboard.txt", "removed", lineCount);
                    return ign + " has be removed from the leaderboard.";

                }

            }
        }
        return "Unknown Player: " + ign;
    }

    public String punishment(String offense, String ign, int eloReduction) throws FileNotFoundException {
        File lb = new File("Leaderboard.txt");
        Scanner scan = new Scanner(lb);
        if (isPlayerOnLB(ign)) {
            int lineCount = 0;
            while (scan.hasNextLine()) {
                lineCount++;
                String playerStatsString = scan.nextLine();
                if (playerStatsString.equals("removed")) {
                    continue;
                }
                String[] playerStatsArray = playerStatsString.split(",");
                if (playerStatsArray[2].equalsIgnoreCase(ign)) {
                    int currentElo = Integer.valueOf(playerStatsArray[0]);
                    currentElo = currentElo - eloReduction;
                    playerStatsArray[0] = String.valueOf(currentElo);
                    String newLine = "";
                    int count = 0;
                    for (String stat : playerStatsArray) {
                        if (count == playerStatsArray.length - 1) {
                            newLine += stat;
                        } else {
                            newLine += stat + ",";
                            count++;
                        }
                    }
                    ChangeLineInFile changeLineInFile = new ChangeLineInFile();
                    changeLineInFile.changeALineInATextFile("Leaderboard.txt", newLine, lineCount);
                    return "```ini\nName - " + ign + "\nPunishment - " + offense + "\nElo Reduction Amount - " + eloReduction + "\nStatus - [Successfully Changed]\n```";

                }

            }
        }
        return "```css\nName - " + ign + "\nPunishment - " + offense + "\nElo Reduction Amount - " + eloReduction + "\nStatus - [Elo Reduction Failed]\n```";
    }

    public String giveElo(String ign, int eloIncrease) throws FileNotFoundException {
        File lb = new File("Leaderboard.txt");
        Scanner scan = new Scanner(lb);
        if (isPlayerOnLB(ign)) {
            int lineCount = 0;
            while (scan.hasNextLine()) {
                lineCount++;
                String playerStatsString = scan.nextLine();
                if (playerStatsString.equals("removed")) {
                    continue;
                }
                String[] playerStatsArray = playerStatsString.split(",");
                if (playerStatsArray[2].equalsIgnoreCase(ign)) {
                    int currentElo = Integer.valueOf(playerStatsArray[0]);
                    currentElo = currentElo + eloIncrease;
                    playerStatsArray[0] = String.valueOf(currentElo);
                    String newLine = "";
                    int count = 0;
                    for (String stat : playerStatsArray) {
                        if (count == playerStatsArray.length - 1) {
                            newLine += stat;
                        } else {
                            newLine += stat + ",";
                            count++;
                        }
                    }
                    ChangeLineInFile changeLineInFile = new ChangeLineInFile();
                    changeLineInFile.changeALineInATextFile("Leaderboard.txt", newLine, lineCount);
                    return "```ini\nName - " + ign + "\nElo Increase Amount - " + eloIncrease + "\nStatus - [Successfully Changed]\n```";

                }

            }
        }
        return "```css\nName - " + ign + "\nElo Reduction Amount - " + eloIncrease + "\nStatus - [Elo Increase Failed]\n```";
    }

    public String combineStats(String IGN, String IGNNew) throws FileNotFoundException {
        if (IGN.equalsIgnoreCase(IGNNew)) {
            return "```css\nOriginal IGN - " + IGN + "\nNew IGN - " + IGNNew + "\nStatus - [Combining Stats Failed]\n```";
        }
        File lb = new File("Leaderboard.txt");
        Scanner scan = new Scanner(lb);
        if (isPlayerOnLB(IGN)) {
            int lineCount = 0;
            firstWhile:
            while (scan.hasNextLine()) {
                lineCount++;
                String playerStatsString = scan.nextLine();
                if (playerStatsString.equals("removed")) {
                    continue;
                }
                String[] playerStatsArray = playerStatsString.split(",");
                if (playerStatsArray[2].equalsIgnoreCase(IGN)) {
                    int oldElo = Integer.valueOf(playerStatsArray[0]);
                    int oldKills = Integer.valueOf(playerStatsArray[3]);
                    int oldDeaths = Integer.valueOf(playerStatsArray[4]);
                    int oldWins = Integer.valueOf(playerStatsArray[5]);
                    int oldLosses = Integer.valueOf(playerStatsArray[6]);
                    File otherLb = new File("Leaderboard.txt");
                    Scanner otherScan = new Scanner(otherLb);
                    if (isPlayerOnLB(IGNNew)) {
                        int newLineCount = 0;
                        while (otherScan.hasNextLine()) {
                            newLineCount++;
                            String newPlayerStatsString = otherScan.nextLine();
                            if (newPlayerStatsString.equals("removed")) {
                                continue;
                            }
                            String[] newPlayerStatsArray = newPlayerStatsString.split(",");
                            if (newPlayerStatsArray[2].equalsIgnoreCase(IGNNew)) {
                                this.removePlayer(IGN);
                                int newElo = 1000 + ((oldElo - 1000) + (Integer.valueOf(newPlayerStatsArray[0]) - 1000));
                                int newKills = oldKills + Integer.valueOf(newPlayerStatsArray[3]);
                                int newDeaths = oldDeaths + Integer.valueOf(newPlayerStatsArray[4]);
                                int newWins = oldWins + Integer.valueOf(newPlayerStatsArray[5]);
                                int newLosses = oldLosses + Integer.valueOf(newPlayerStatsArray[6]);
                                double newKD = 1.0 * newKills / newDeaths;
                                double newWL = 100.0 * newWins / (newLosses + newWins);

                                newPlayerStatsArray[0] = String.valueOf(newElo);
                                newPlayerStatsArray[1] = String.format("%.3f", newKD);
                                newPlayerStatsArray[3] = String.valueOf(newKills);
                                newPlayerStatsArray[4] = String.valueOf(newDeaths);
                                newPlayerStatsArray[5] = String.valueOf(newWins);
                                newPlayerStatsArray[6] = String.valueOf(newLosses);
                                newPlayerStatsArray[7] = String.format("%.2f", newWL);
                                newPlayerStatsArray[8] = String.format(playerStatsArray[8]);
                                String newFormatLine = "";
                                int count = 0;
                                for (String stat : newPlayerStatsArray) {
                                    if (count == newPlayerStatsArray.length - 1) {
                                        newFormatLine += stat;
                                    } else {
                                        newFormatLine += stat + ",";
                                        count++;
                                    }

                                }
                                ChangeLineInFile newChangeLineInFile = new ChangeLineInFile();
                                newChangeLineInFile.changeALineInATextFile("Leaderboard.txt", newFormatLine, newLineCount);
                                return "```ini\nOriginal IGN - " + IGN + "\nNew IGN - " + IGNNew + "\nStatus - [Stats Successfully Combined]\n```";

                            }
                        }
                    } else {
                        break firstWhile;
                    }

                }

            }
        }
        return "```css\nOriginal IGN - " + IGN + "\nNew IGN - " + IGNNew + "\nStatus - [Combining Stats Failed]\n```";
    }

    public String captains(ArrayList<String> idList) throws FileNotFoundException {
        String caps = "";
        File lb = new File("Leaderboard.txt");
        Scanner scan = new Scanner(lb);
        ArrayList<String> playersInOrg = new ArrayList<>();
        while (scan.hasNextLine()) {
            String playerStatsString = scan.nextLine();
            if (playerStatsString.equals("removed")) {
                continue;
            } else {
                String[] playerStatsArray = playerStatsString.split(",");
                if (playerStatsArray.length == 8) {
                    continue;
                } else {
                    for (String id : idList) {
                        if (id.equals(playerStatsArray[8])) {
                            int wins = (Integer.valueOf(playerStatsArray[5]));
                            int losses = (Integer.valueOf(playerStatsArray[6]));
                            if(wins+losses >= 10) {
                                playersInOrg.add(playerStatsString);
                            }
                        }
                    }
                }
            }
        }
        List<String> sorted = playersInOrg.stream().sorted(new AlphanumComparator()).collect(Collectors.toList());
        Collections.reverse(sorted);
        if(sorted.size() <= 1){
            return "Not enough games played to determine caps. Refer to old LB.";
        }else {
            String[] cap1Array = sorted.get(0).split(",");
            String[] cap2Array = sorted.get(1).split(",");
            caps = "Captain 1: " + cap1Array[2] + "\nCaptain 2: " + cap2Array[2];
            scan.close();
            return caps;
        }
    }

    public String linkID(String id, String ign) throws FileNotFoundException {
        File lb = new File("Leaderboard.txt");
        Scanner scan = new Scanner(lb);
        if (isPlayerOnLB(ign)) {
            int lineCount = 0;
            while (scan.hasNextLine()) {
                lineCount++;
                String playerStatsString = scan.nextLine();
                if (playerStatsString.equals("removed")) {
                    continue;
                }
                String[] playerStatsArray = playerStatsString.split(",");
                if (playerStatsArray[2].equalsIgnoreCase(ign)) {
                    String newLine = "";
                    int count = 0;
                    for (String stat : playerStatsArray) {
                        if (count == playerStatsArray.length - 1) {
                            newLine +=  id;
                        } else {
                            newLine += stat + ",";
                            count++;
                        }
                    }
                    ChangeLineInFile changeLineInFile = new ChangeLineInFile();
                    changeLineInFile.changeALineInATextFile("Leaderboard.txt", newLine, lineCount);

                    return "Linked " + ign + " to id: " + id;
                }
            }

        }
        return "Couldn't link " + ign + " to id: " + id;
    }
}
