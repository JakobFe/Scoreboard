package com.footballacademynoah.scoreboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.footballacademynoah.scoreboard.repository.GameRepository;

@SpringBootApplication
@RestController	
public class ScoreboardApplication {

	@Autowired
	GameRepository gameRepository;

	public static void main(String[] args) {
		SpringApplication.run(ScoreboardApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	@GetMapping("/admin")
	public ModelAndView admin(Model model) {
		model.addAttribute("game", gameRepository.findByStage("test"));
		return new ModelAndView("admin");
	}

}
