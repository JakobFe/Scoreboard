package com.footballacademynoah.scoreboard.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.footballacademynoah.scoreboard.model.Game;
import com.footballacademynoah.scoreboard.model.Tournament;
import com.footballacademynoah.scoreboard.model.Team;
import com.footballacademynoah.scoreboard.projection.GameProjection;

public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findById(long id);
    Optional<GameProjection> findProjectedById(long id);
    Optional<List<GameProjection>> findProjectedByTournamentAndStage(Tournament tournament, String stage);
    List<GameProjection> findAllProjectedByTournament(Tournament tournament);
    Optional<List<GameProjection>> findProjectedByTournamentAndTeam1(Tournament tournament, Team team1);
    Optional<List<GameProjection>> findProjectedByTournamentAndTeam2(Tournament tournament, Team team2);
}