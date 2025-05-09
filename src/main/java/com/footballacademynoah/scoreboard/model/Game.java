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
    @JsonBackReference(value="tournament-games")
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "team1_id", nullable = false)
    @JsonBackReference(value="team-homeGames")
    private Team team1;

    @ManyToOne
    @JoinColumn(name = "team2_id", nullable = false)
    @JsonBackReference(value="team-awayGames")
    private Team team2;

    @Column(name = "team1Score")
    private int team1Score;

    @Column(name = "team2Score")
    private int team2Score;

    @Column(name = "team1Penalties")
    private int team1Penalties;

    @Column(name = "team2Penalties")
    private int team2Penalties;

    @Enumerated(EnumType.STRING)
    @Column(name = "stage")
    public Stage stage;

    @Column(name = "substage")
    public String substage;

    @Column(name = "date")
    private String date;

    @Column(name = "time")
    private String time;

    @Column(name = "field")
    private String field;

    @Column(name = "completed")
    private boolean completed;

    public Game() {

    }

    public Game(
        Team team1, 
        Team team2, 
        int team1Score, 
        int team2Score,
        int team1Penalties,
        int team2Penalties,
        Stage stage, 
        String date,
        String time, 
        String field, 
        boolean completed,
        Tournament tournament
        ) {
        this.team1 = team1;
        this.team2 = team2;
        this.team1Score = team1Score;
        this.team2Score = team2Score;
        this.team1Penalties = team1Penalties;
        this.team2Penalties = team2Penalties;
        this.stage = stage;
        this.date = date;
        this.time = time;
        this.field = field;
        this.completed = completed;
        this.tournament = tournament;
    }

    public long getId() {
        return id;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
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

    public int getTeam1Penalties() {
        return team1Penalties;
    }

    public void setTeam1Penalties(int team1Penalties) {
        this.team1Penalties = team1Penalties;
    }

    public int getTeam2Penalties() {
        return team2Penalties;
    }

    public void setTeam2Penalties(int team2Penalties) {
        this.team2Penalties = team2Penalties;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public String getSubStage() {
        return substage;
    }

    public void setSubStage(String substage) {
        this.substage = substage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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