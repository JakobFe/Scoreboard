package com.footballacademynoah.scoreboard.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
import com.footballacademynoah.scoreboard.model.Game;
import com.footballacademynoah.scoreboard.model.RankingInfo;
import com.footballacademynoah.scoreboard.model.Stage;
import com.footballacademynoah.scoreboard.repository.TournamentRepository;
import com.footballacademynoah.scoreboard.repository.GameRepository;
import com.footballacademynoah.scoreboard.repository.TeamRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class TournamentController {

  @Autowired
  TournamentRepository tournamentRepository;

  @Autowired
  GameRepository gameRepository;

  @Autowired
  GameController gameController;

  @Autowired
  TeamRepository teamRepository;

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

  /*
   * Only one flow possible for now: POULE >>> QUARTER >>> HALF >>> FINAL
   */
  @PostMapping("/tournaments/{id}/progress")
  public ResponseEntity<List<Game>> progressToPhase(@PathVariable("id") long id, Stage stage) {
    Optional<Tournament> tournament = tournamentRepository.findById(id);

    if(tournament.isPresent()) {
      Tournament _tournament = tournament.get();

      List<Game> games = new ArrayList<>();

        if(stage == Stage.QUARTER) {
            Map<Long, RankingInfo> ranking = getRankingById(id).getBody();

            if (ranking == null || ranking.size() < 8) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Not enough teams for quarter-finals
            }

            List<Long> rankedTeams = new ArrayList<>(ranking.keySet());

            games.add(gameRepository.save(this.createKnockoutGame(_tournament, rankedTeams.get(0), rankedTeams.get(7), Stage.QUARTER)));
            games.add(gameRepository.save(this.createKnockoutGame(_tournament, rankedTeams.get(1), rankedTeams.get(6), Stage.QUARTER)));
            games.add(gameRepository.save(this.createKnockoutGame(_tournament, rankedTeams.get(2), rankedTeams.get(5), Stage.QUARTER)));
            games.add(gameRepository.save(this.createKnockoutGame(_tournament, rankedTeams.get(3), rankedTeams.get(4), Stage.QUARTER)));
        } else if (stage == Stage.SEMI) {
          Optional<List<GameProjection>> quarterFinals = gameRepository.findProjectedByTournamentAndStage(_tournament, Stage.QUARTER);

          if (!quarterFinals.isPresent() || quarterFinals.get().size() < 4) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // No or not enough quarter-finals found
          }

          List<GameProjection> _quarterFinals = quarterFinals.get();
          games = createSemiFinalGames(_tournament, _quarterFinals);
        } else if (stage == Stage.FINAL) {
          Optional<List<GameProjection>> completedSemiFinals = gameRepository.findProjectedByTournamentAndStage(_tournament, Stage.SEMI);

          if (!completedSemiFinals.isPresent() || completedSemiFinals.get().size() < 2) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // No or not enough quarter-finals found
          }

          games.add(createFinalGame(_tournament, completedSemiFinals.get()));          
        }

      _tournament.setStage(stage);
      tournamentRepository.save(_tournament);
      return new ResponseEntity<>(games, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/tournaments/{id}/ranking")
  public ResponseEntity<Map<Long, RankingInfo>> getRankingById(@PathVariable("id") long id) {
    Optional<Tournament> tournament = tournamentRepository.findById(id);

    if (tournament.isPresent()) {
      Tournament _tournament = tournament.get();
      List<Team> teams = _tournament.getTeams();

      Map<Long, RankingInfo> ranking = new HashMap<Long, RankingInfo>();

      for(Team team: teams) {
        Optional<List<GameProjection>> home_games = gameRepository.findProjectedByTournamentAndTeam1AndCompleted(_tournament, team, true);
        Optional<List<GameProjection>> away_games = gameRepository.findProjectedByTournamentAndTeam2AndCompleted(_tournament, team, true);

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

        RankingInfo info = new RankingInfo(
          team.getName(),
          total_points,
          home_games.orElse(new ArrayList<>()).size() + away_games.orElse(new ArrayList<>()).size(),
          total_goals_for,
          total_goals_against,
          total_goals_for - total_goals_against
        );

        ranking.put(team.getId(), info);
      }

      Map<Long, RankingInfo> sortedRanking = ranking.entrySet()
        .stream()
        .sorted((e1, e2) -> {
          RankingInfo v1 = e1.getValue();
          RankingInfo v2 = e2.getValue();

          int pointsCompare = Integer.compare((int) v2.getTotalPoints(), (int) v1.getTotalPoints());
          if (pointsCompare != 0) return pointsCompare;

          int goalsCompare = Integer.compare((int) v2.getTotalGoals(), (int) v1.getTotalGoals());
          if (goalsCompare != 0) return goalsCompare;

          int goalsForCompare = Integer.compare((int) v2.getTotalGoalsFor(), (int) v1.getTotalGoalsFor());
          if (goalsForCompare != 0) return goalsForCompare;

          return Integer.compare((int) v1.getTotalGoalsAgainst(), (int) v2.getTotalGoalsAgainst());
        })
        .collect(Collectors.toMap(
          Map.Entry::getKey,
          Map.Entry::getValue,
          (e1, e2) -> e1,
          LinkedHashMap::new
        ));

      return new ResponseEntity<>(sortedRanking, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  private Game createKnockoutGame(Tournament tournament, long team1, long team2, Stage stage) {
    Optional<Team> _team1 = teamRepository.findById(team1);
    Optional<Team> _team2 = teamRepository.findById(team2);

    if (!_team1.isPresent() || !_team2.isPresent()) {
      throw new IllegalArgumentException("One or more teams are not found");
    }

    return new Game(
      _team1.get(),
      _team2.get(),
      0,
      0,
      0,
      0,
      stage,
      null,
      null,
      null,
      false,
      tournament
    );
  }

  private List<Game> createSemiFinalGames(Tournament tournament, List<GameProjection> quarterFinals) {
    List<Game> games = new ArrayList<>();
    List<Long> winners = new ArrayList<>(Arrays.asList(0L, 0L, 0L, 0L));

    for(GameProjection quarter: quarterFinals){
      switch (quarter.getSubStage()) {
        case "K1":
          winners.set(0, this.getWinnerFromGame(quarter));
          break;
        case "K2":
          winners.set(1, this.getWinnerFromGame(quarter));
          break;
        case "K3":
          winners.set(2, this.getWinnerFromGame(quarter));
          break;
        case "K4":
          winners.set(3, this.getWinnerFromGame(quarter));
          break;      
        default:
          break;
      }
    }

    games.add(createKnockoutGame(tournament, winners.get(0), winners.get(3), Stage.SEMI));
    games.add(createKnockoutGame(tournament, winners.get(1), winners.get(2), Stage.SEMI));

    return games;    
  }

  private Game createFinalGame(Tournament tournament, List<GameProjection> semiFinals) {
    if(semiFinals.get(0).getSubStage() == "S2") {
      return createKnockoutGame(tournament, this.getWinnerFromGame(semiFinals.get(1)), this.getWinnerFromGame(semiFinals.get(0)), Stage.FINAL);
    } else {
      return createKnockoutGame(tournament, this.getWinnerFromGame(semiFinals.get(0)), this.getWinnerFromGame(semiFinals.get(1)), Stage.FINAL);
    }
  }

  private Long getWinnerFromGame(GameProjection game) {
    if(game.getTeam1Score() > game.getTeam2Score()) {
      return game.getTeam1().getId();
    } else if (game.getTeam2Score() > game.getTeam1Score()) {
      return game.getTeam2().getId();
    } else if (game.getTeam1Penalties() > game.getTeam2Penalties()) {
      return game.getTeam1().getId();
    } else if (game.getTeam2Penalties() > game.getTeam1Penalties()) {
      return game.getTeam2().getId();
    } else {
      throw new IllegalStateException("No winner could be determined, however in a knockout game there should be a winner");
    }
  }

}