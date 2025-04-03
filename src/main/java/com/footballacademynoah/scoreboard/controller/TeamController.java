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

import com.footballacademynoah.scoreboard.model.Team;
import com.footballacademynoah.scoreboard.repository.TeamRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class TeamController {

    @Autowired
    TeamRepository teamRepository;

    @GetMapping("/teams")
    public ResponseEntity<List<Team>> getAllTeams(@RequestParam(required = false) String name) {
        try {
            List<Team> teams = new ArrayList<Team>();

            if (name == null)
                teamRepository.findAll().forEach(teams::add);
            else
                teamRepository.findByNameContaining(name).forEach(teams::add);

            if (teams.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(teams, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/teams/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable("id") long id) {
      Optional<Team> teamData = teamRepository.findById(id);
  
      if (teamData.isPresent()) {
        return new ResponseEntity<>(teamData.get(), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }
  
    @PostMapping("/teams")
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
      try {
        Team _team = teamRepository
            .save(new Team(team.getName(), team.getCaptain(), team.isMensTournament(), team.getTournament()));
        return new ResponseEntity<>(_team, HttpStatus.CREATED);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
  
    @PutMapping("/teams/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable("id") long id, @RequestBody Team team) {
      Optional<Team> teamData = teamRepository.findById(id);
  
      if (teamData.isPresent()) {
        Team _team = teamData.get();
        _team.setName(team.getName());
        _team.setCaptain(team.getCaptain());
        _team.setMensTournament(team.isMensTournament());
        return new ResponseEntity<>(teamRepository.save(_team), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }
  
    @DeleteMapping("/teams/{id}")
    public ResponseEntity<HttpStatus> deleteTeam(@PathVariable("id") long id) {
      try {
        teamRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
  
    @DeleteMapping("/teams")
    public ResponseEntity<HttpStatus> deleteAllTeams() {
      try {
        teamRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
  
    }
}