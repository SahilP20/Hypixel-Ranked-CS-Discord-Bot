package me.Nutella;

import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

public class Leaderboard {

    public Leaderboard(){
    }

    public void clearLeaderboard() throws IOException {
        new PrintWriter("Leaderboard.txt").close();
    }

    public void sortLeaderboard() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("Empty.txt"));
        BufferedReader bufferedReader = new BufferedReader(new FileReader("Leaderboard.txt"));

        String currentPLayerLine = bufferedReader.readLine();
        ArrayList<String> unsortedPlayers = new ArrayList<>();

        while (currentPLayerLine != null) {
                if(currentPLayerLine.equals("removed")){
                    currentPLayerLine = bufferedReader.readLine();
                }else {
                    unsortedPlayers.add(currentPLayerLine);
                    currentPLayerLine = bufferedReader.readLine();
                }
            }

        List<String> sorted = unsortedPlayers.stream().sorted(new AlphanumComparator()).collect(Collectors.toList());
        Collections.reverse((sorted));

        for(String player : sorted){
            bufferedWriter.write(player);
            bufferedWriter.newLine();
        }
        bufferedReader.close();
        bufferedWriter.close();
    }

    public String printLeaderboard(int page) throws IOException{
        this.sortLeaderboard();
        File file = new File("Empty.txt");
        Scanner scan = new Scanner(file);
        ArrayList<String> playerLBList = new ArrayList<>();
        int count = 0;
        while(scan.hasNextLine()){
            String stats = scan.nextLine();
            if(stats.equals("removed")){
                continue;
            }
            count++;
            String[] playerStats = stats.split(",");
            playerLBList.add(count + ". " + playerStats[2] + " - " + playerStats[0] + "\n");
        }
        scan.close();
        double numOfPages = 1.0*count/35;
        String printedLB = "";

        if(numOfPages<=1){
            if(page == 0 || page == 1){
                for (String player : playerLBList) {
                    printedLB += player;
                }
            }else{
                printedLB = "Page " + page + " doesn't exist.";
            }
        }else if(numOfPages>1 && numOfPages <=2){
            if(page == 2){
                for(int i = 35;i<playerLBList.size(); i++){
                    printedLB += playerLBList.get(i);
                }
            }else if(page == 0 || page == 1){
                for(int i = 0;i<35; i++){
                    printedLB += playerLBList.get(i);
                }
            } else{
            printedLB = "Page " + page + " doesn't exist.";
            }
        }else if(numOfPages>2 && numOfPages <=3) {
            if (page == 2) {
                for (int i = 35; i < 70; i++) {
                    printedLB += playerLBList.get(i);
                }
            } else if (page == 0 || page == 1) {
                for (int i = 0; i < 35; i++) {
                    printedLB += playerLBList.get(i);
                }

            } else if (page ==3) {
                for (int i = 70; i < playerLBList.size(); i++) {
                    printedLB += playerLBList.get(i);
                }
            } else {
                printedLB = "Page " + page + " doesn't exist.";
            }
        }else if(numOfPages>3 && numOfPages <=4) {
            if (page == 2) {
                for (int i = 35; i < 70; i++) {
                    printedLB += playerLBList.get(i);
                }
            } else if (page == 0 || page == 1) {
                for (int i = 0; i < 35; i++) {
                    printedLB += playerLBList.get(i);
                }
            } else if (page == 3) {
                for (int i = 70; i < 105; i++) {
                    printedLB += playerLBList.get(i);
                }
            }else if (page == 4){
                for(int i = 105; i< playerLBList.size();i++){
                    printedLB += playerLBList.get(i);
                }
            }else {
                printedLB = "Page " + page + " doesn't exist.";
            }
        }
        if(printedLB.length() == 0){
            return "Leaderboard is empty.";
        }else{
            return printedLB;
        }
    }
}
