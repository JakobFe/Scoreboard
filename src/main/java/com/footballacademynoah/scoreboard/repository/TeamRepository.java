package com.footballacademynoah.scoreboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.footballacademynoah.scoreboard.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByNameContaining(String name);
}