package com.footballacademynoah.scoreboard.model;

public class GameCompletion {

    private int team1Score;

    private int team2Score;

    public GameCompletion(int team1Score, int team2Score) {
        this.team1Score = team1Score;
        this.team2Score = team2Score;
    }

    public int getTeam1Score() {
        return this.team1Score;
    }

    public void setTeam1Score(int team1Score) {
        this.team1Score = team1Score;
    }

    public int getTeam2Score() {
        return this.team2Score;
    }

    public void setTeam2Score(int team2Score) {
        this.team2Score = team2Score;
    }

}
