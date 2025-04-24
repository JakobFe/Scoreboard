package com.footballacademynoah.scoreboard.projection;

import com.footballacademynoah.scoreboard.model.Stage;

public interface GameProjection {
    long getId();
    TeamInfo getTeam1();
    TeamInfo getTeam2();
    int getTeam1Score();
    int getTeam2Score();
    int getTeam1Penalties();
    int getTeam2Penalties();
    Stage getStage();
    String getSubStage();
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
