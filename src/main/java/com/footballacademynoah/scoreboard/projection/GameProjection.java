package com.footballacademynoah.scoreboard.projection;

public interface GameProjection {
    long getId();
    TeamInfo getTeam1();
    TeamInfo getTeam2();
    int getTeam1Score();
    int getTeam2Score();
    String getStage();
    String getDate();
    String getTime();
    String getField();
    boolean getCompleted();
    String getTournamentName();

    interface TeamInfo {
        long getId();
        String getName();
    }        
}
