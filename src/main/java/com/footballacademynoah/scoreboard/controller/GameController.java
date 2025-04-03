package com.footballacademynoah.scoreboard.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.footballacademynoah.scoreboard.model.Game;
import com.footballacademynoah.scoreboard.model.Tournament;
import com.footballacademynoah.scoreboard.model.Team;
import com.footballacademynoah.scoreboard.projection.GameProjection;
import com.footballacademynoah.scoreboard.repository.GameRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class GameController {

    @Autowired
    GameRepository gameRepository;

    @GetMapping("/games")
    public ResponseEntity<List<GameProjection>> getAllGames(@RequestParam(required = true) Tournament tournament, @RequestParam(required = false) String stage, @RequestParam(required = false) Team team) {
        try {
            // Add null checks for tournament, stage, and team
            if (stage != null){
              Optional<List<GameProjection>> games = gameRepository.findProjectedByTournamentAndStage(tournament, stage);
              
              if (games.isPresent()) {
                return new ResponseEntity<>(games.get(), HttpStatus.OK);
              } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
              }
            } else if (team != null) {
              List<GameProjection> games = new ArrayList<GameProjection>();
              Optional<List<GameProjection>> team1Games = gameRepository.findProjectedByTournamentAndTeam1(tournament, team);
              team1Games.ifPresent(games::addAll);
              Optional<List<GameProjection>> team2Games = gameRepository.findProjectedByTournamentAndTeam2(tournament, team);
              team2Games.ifPresent(games::addAll);

              if (!games.isEmpty()) {
                return new ResponseEntity<>(games, HttpStatus.OK);
              } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
              }
            } else {
              List<GameProjection> games = gameRepository.findAllProjectedByTournament(tournament);
              
              if (!games.isEmpty()) {
                return new ResponseEntity<>(games, HttpStatus.OK);
              } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
              }
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/games/{id}")
    public ResponseEntity<GameProjection> getGameById(@PathVariable("id") long id) {
      Optional<GameProjection> gameData = gameRepository.findProjectedById(id);
  
      if (gameData.isPresent()) {
        return new ResponseEntity<>(gameData.get(), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }
  
    @PostMapping("/games")
    public ResponseEntity<Game> createGame(@RequestBody Game game) {
      try {
        Game _game = gameRepository
            .save(new Game(
                    game.getTeam1(),
                    game.getTeam2(),
                    game.getTeam1Score(),
                    game.getTeam2Score(),
                    game.getStage(),
                    game.getDate(),
                    game.getTime(),
                    game.getField(),
                    game.isCompleted(),
                    game.getTournament()
                ));
        return new ResponseEntity<>(_game, HttpStatus.CREATED);
      } catch (Exception e) {
        System.out.println("An error occurred while creating the game: " + e.getMessage());
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
  
    @PutMapping("/games/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable("id") long id, @RequestBody Game game) {
      Optional<Game> gameData = gameRepository.findById(id);
  
      if (gameData.isPresent()) {
        Game _game = gameData.get();
        _game.setTeam1(game.getTeam1());
        _game.setTeam2(game.getTeam2());
        _game.setTeam1Score(game.getTeam1Score());
        _game.setTeam2Score(game.getTeam2Score());
        _game.setStage(game.getStage());
        _game.setDate(game.getDate());
        _game.setTime(game.getTime());
        _game.setField(game.getField());
        _game.setCompleted(game.isCompleted());
        return new ResponseEntity<>(gameRepository.save(_game), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }
  
    @DeleteMapping("/games/{id}")
    public ResponseEntity<HttpStatus> deleteGame(@PathVariable("id") long id) {
      try {
        gameRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
  
    @DeleteMapping("/games")
    public ResponseEntity<HttpStatus> deleteAllGames() {
      try {
        gameRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
  
    }
}