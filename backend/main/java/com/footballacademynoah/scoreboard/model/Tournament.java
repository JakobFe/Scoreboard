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

    @OneToMany(mappedBy = "tournament")
    @JsonManagedReference
    private List<Team> teams;

    @OneToMany(mappedBy = "tournament")
    @JsonManagedReference
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

    public List<Game> getGames() {
        return games;
    }

    public List<Team> getTeams() {
        return teams;
    }
}
