package com.footballacademynoah.scoreboard.model;

public class RankingInfo {
    private String teamName;
    private int totalPoints;
    private int totalGames;
    private int totalGoalsFor;
    private int totalGoalsAgainst;
    private int totalGoals;

    public RankingInfo(String teamName, int totalPoints, int totalGames, int totalGoalsFor, int totalGoalsAgainst, int totalGoals) {
        this.teamName = teamName;
        this.totalPoints = totalPoints;
        this.totalGames = totalGames;
        this.totalGoalsFor = totalGoalsFor;
        this.totalGoalsAgainst = totalGoalsAgainst;
        this.totalGoals = totalGoals;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }

    public int getTotalGoalsFor() {
        return totalGoalsFor;
    }

    public void setTotalGoalsFor(int totalGoalsFor) {
        this.totalGoalsFor = totalGoalsFor;
    }

    public int getTotalGoalsAgainst() {
        return totalGoalsAgainst;
    }

    public void setTotalGoalsAgainst(int totalGoalsAgainst) {
        this.totalGoalsAgainst = totalGoalsAgainst;
    }

    public int getTotalGoals() {
        return totalGoals;
    }

    public void setTotalGoals(int totalGoals) {
        this.totalGoals = totalGoals;
    }
}