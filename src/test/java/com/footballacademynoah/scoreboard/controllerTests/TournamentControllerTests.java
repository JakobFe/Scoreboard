// package com.footballacademynoah.scoreboard.controllerTests;

// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import org.mockito.InjectMocks;
// import org.mockito.Mock;

// import com.footballacademynoah.scoreboard.controller.TournamentController;
// import com.footballacademynoah.scoreboard.model.Tournament;
// import com.footballacademynoah.scoreboard.repository.TournamentRepository;


// @DataJpaTest
// public class TournamentControllerTests {

//     @Mock
//     private TournamentRepository tournamentRepository;

//     @InjectMocks
//     private TournamentController tournamentController;

//     private Tournament tournament;

//     @BeforeEach
//     public void setup() {
//         tournament = Tournament.Builder()
//                 .name("Tournament 1")
//                 .location("Location 1")
//                 .startDate("2025-10-01")
//                 .endDate("2025-10-31")
//                 .build();
//     }

//     @Test
//     void testCreateTournament() {





//         // Tournament tournament = new Tournament("Tournament 1");
//         // when(tournamentRepository.save(any(Tournament.class))).thenReturn(tournament);

//         // ResponseEntity<Tournament> response = tournamentController.createTournament(tournament);

//         // assertEquals(HttpStatus.CREATED, response.getStatusCode());
//         // assertNotNull(response.getBody());
//         // assertEquals("Tournament 1", response.getBody().getName());
//     }
// }
