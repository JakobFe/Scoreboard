package com.footballacademynoah.scoreboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.footballacademynoah.scoreboard.model.Tournament;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    List<Tournament> findByNameContaining(String name);    
}