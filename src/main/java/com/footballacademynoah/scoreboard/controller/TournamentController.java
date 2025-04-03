package com.footballacademynoah.scoreboard.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.footballacademynoah.scoreboard.model.Tournament;
import com.footballacademynoah.scoreboard.projection.GameProjection;
import com.footballacademynoah.scoreboard.model.Team;
import com.footballacademynoah.scoreboard.repository.TournamentRepository;
import com.footballacademynoah.scoreboard.repository.GameRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class TournamentController {

  @Autowired
  TournamentRepository tournamentRepository;

  @Autowired
  GameRepository gameRepository;

  @GetMapping("/tournaments")
  public ResponseEntity<List<Tournament>> getAllTournaments(@RequestParam(required = false) String name) {
      try {
          List<Tournament> tournaments = new ArrayList<Tournament>();

          if (name == null)
              tournamentRepository.findAll().forEach(tournaments::add);
          else
              tournamentRepository.findByNameContaining(name).forEach(tournaments::add);
          
          if (tournaments.isEmpty()) {
              return new ResponseEntity<>(HttpStatus.NO_CONTENT);
          }
          return new ResponseEntity<>(tournaments, HttpStatus.OK);
      } catch (Exception e) {
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }

  @GetMapping("/tournaments/{id}")
  public ResponseEntity<Tournament> getTournamentById(@PathVariable("id") long id) {
    Optional<Tournament> tournamentData = tournamentRepository.findById(id);

    if (tournamentData.isPresent()) {
      return new ResponseEntity<>(tournamentData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/tournaments")
  public ResponseEntity<Tournament> createTournament(@RequestBody Tournament tournament) {
    try {
      Tournament _tournament = tournamentRepository
          .save(new Tournament(tournament.getName()));
      return new ResponseEntity<>(_tournament, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/tournaments/{id}")
  public ResponseEntity<Tournament> updateTournament(@PathVariable("id") long id, @RequestBody Tournament tournament) {
    Optional<Tournament> tournamentData = tournamentRepository.findById(id);

    if (tournamentData.isPresent()) {
      Tournament _tournament = tournamentData.get();
      _tournament.setName(tournament.getName());
      return new ResponseEntity<>(tournamentRepository.save(_tournament), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/tournaments/{id}")
  public ResponseEntity<HttpStatus> deleteTournament(@PathVariable("id") long id) {
    try {
      tournamentRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/tournaments")
  public ResponseEntity<HttpStatus> deleteAllTournaments() {
    try {
      tournamentRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  @GetMapping("/tournaments/{id}/ranking")
  public ResponseEntity<Map<String, Object>> getRankingById(@PathVariable("id") long id) {
    Optional<Tournament> tournament = tournamentRepository.findById(id);

    if (tournament.isPresent()) {
      Tournament _tournament = tournament.get();
      List<Team> teams = _tournament.getTeams();

      Map<String, Object> ranking = new HashMap<String, Object>();

      for(Team team: teams) {
        Optional<List<GameProjection>> home_games = gameRepository.findProjectedByTournamentAndTeam1(_tournament, team);
        Optional<List<GameProjection>> away_games = gameRepository.findProjectedByTournamentAndTeam2(_tournament, team);

        int total_for_home = home_games.orElse(new ArrayList<>()).stream().mapToInt(GameProjection::getTeam1Score).sum();
        int total_against_home = home_games.orElse(new ArrayList<>()).stream().mapToInt(GameProjection::getTeam2Score).sum();
        int total_for_away = away_games.orElse(new ArrayList<>()).stream().mapToInt(GameProjection::getTeam2Score).sum();
        int total_against_away = away_games.orElse(new ArrayList<>()).stream().mapToInt(GameProjection::getTeam1Score).sum();
        int total_goals_for = total_for_home + total_for_away;
        int total_goals_against = total_against_home + total_against_away;
        int total_points = 0;

        for(GameProjection home_game: home_games.orElse(new ArrayList<>())){
          int score1 = home_game.getTeam1Score();
          int score2 = home_game.getTeam2Score();

          if(score1 > score2){
            total_points += 3;
          } else if(score1 == score2){
            total_points += 1;
          }
        }

        for(GameProjection away_game: away_games.orElse(new ArrayList<>())){
          int score1 = away_game.getTeam1Score();
          int score2 = away_game.getTeam2Score();

          if(score2 > score1){
            total_points += 3;
          } else if(score1 == score2){
            total_points += 1;
          }
        }

        Map<String, Object> info = new HashMap<String, Object>();
        info.put("total_points", total_points);
        info.put("total_games", home_games.orElse(new ArrayList<>()).size() + away_games.orElse(new ArrayList<>()).size());
        info.put("total_goals_for", total_goals_for);
        info.put("total_goals_against", total_goals_against);
        info.put("total_goals", total_goals_for - total_goals_against);

        ranking.put(team.getName(), info);
      }

      return new ResponseEntity<>(ranking, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}