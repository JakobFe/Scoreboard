package com.footballacademynoah.scoreboard.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.footballacademynoah.scoreboard.model.Team;
import com.footballacademynoah.scoreboard.repository.GameRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class GameController {

    GameRepository gameRepository;

    @GetMapping("/games")
    public ResponseEntity<List<Game>> getAllGames(@RequestParam(required = false) String stage, @RequestParam(required = false) Team team) {
        try {
            List<Game> games = new ArrayList<Game>();

            if (stage != null){
              games.add(gameRepository.findByStage(stage));
            } else if (team != null) {
              gameRepository.findByTeam1(team).forEach(games::add);
              gameRepository.findByTeam2(team).forEach(games::add);
            } else {
                gameRepository.findAll().forEach(games::add);
            }

            if (games.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(games, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/games/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable("id") long id) {
      Optional<Game> gameData = gameRepository.findById(id);
  
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
                    game.getField(),
                    game.isCompleted()
                ));
        return new ResponseEntity<>(_game, HttpStatus.CREATED);
      } catch (Exception e) {
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