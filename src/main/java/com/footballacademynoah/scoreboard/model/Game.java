package com.footballacademynoah.scoreboard.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "games")
public class Game {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "team1_id", nullable = false)
    @JsonBackReference
    private Team team1;

    @ManyToOne
    @JoinColumn(name = "team2_id", nullable = false)
    @JsonBackReference
    private Team team2;

    @Column(name = "team1Score")
    private int team1Score;

    @Column(name = "team2Score")
    private int team2Score;

    @Column(name = "stage")
    public String stage;

    @Column(name = "date")
    private String date;

    @Column(name = "field")
    private String field;

    @Column(name = "completed")
    private boolean completed;

    public Game() {

    }

    public Game(Team team1, Team team2, int team1Score, int team2Score, String stage, String date, String field, boolean completed) {
        this.team1 = team1;
        this.team2 = team2;
        this.team1Score = team1Score;
        this.team2Score = team2Score;
        this.stage = stage;
        this.date = date;
        this.field = field;
        this.completed = completed;
    }

    public long getId() {
        return id;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public int getTeam1Score() {
        return team1Score;
    }

    public void setTeam1Score(int team1Score) {
        this.team1Score = team1Score;
    }

    public int getTeam2Score() {
        return team2Score;
    }

    public void setTeam2Score(int team2Score) {
        this.team2Score = team2Score;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}