package com.footballacademynoah.scoreboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.footballacademynoah.scoreboard.model.Game;
import com.footballacademynoah.scoreboard.model.Team;

public interface GameRepository extends JpaRepository<Game, Long> {
    Game findByStage(String stage);
    List<Game> findByTeam1(Team team1);
    List<Game> findByTeam2(Team team2);
}