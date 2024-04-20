package com.footballacademynoah.scoreboard.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "teams")
public class Team {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "captain")
    private String captain;

    @Column(name = "mensTournament")
    private boolean mensTournament;

    @ManyToOne
    @JoinColumn(name = "tournament", nullable = false)
    private Tournament tournament;

    @OneToMany(mappedBy = "team1")
    private List<Game> homeGames;

    @OneToMany(mappedBy = "team2")
    private List<Game> awayGames;

    public Team() {

    }

    public Team(String name, String captain, boolean mensTournament) {
        this.name = name;
        this.captain = captain;
        this.mensTournament = mensTournament;
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

    public String getCaptain() {
        return captain;
    }

    public void setCaptain(String captain) {
        this.captain = captain;
    }

    public boolean isMensTournament() {
        return mensTournament;
    }

    public void setMensTournament(boolean mensTournament) {
        this.mensTournament = mensTournament;
    }

    public List<Game> getHomeGames() {
        return homeGames;
    }

    public void setHomeGames(List<Game> homeGames) {
        this.homeGames = homeGames;
    }

    public List<Game> getAwayGames() {
        return awayGames;
    }

    public void setAwayGames(List<Game> awayGames) {
        this.awayGames = awayGames;
    }

}
