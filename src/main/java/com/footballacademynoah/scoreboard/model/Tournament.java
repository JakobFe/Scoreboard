package com.footballacademynoah.scoreboard.model;

import jakarta.persistence.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "tournament")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "stage")
    private Stage stage;

    @OneToMany(mappedBy = "tournament")
    @JsonManagedReference(value="tournament-teams")
    private List<Team> teams;

    @OneToMany(mappedBy = "tournament")
    @JsonManagedReference(value="tournament-games")
    private List<Game> games;

    public Tournament() {

    }
    
    public Tournament(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public List<Game> getGames() {
        return games;
    }

    public List<Team> getTeams() {
        return teams;
    }
}
