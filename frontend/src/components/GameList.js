import React, { useEffect, useState } from 'react';
import Box from '@mui/joy/Box';
import Game from './Game';
import { fetchGames } from '../services/apiService';

import teamLogo from '../static/img/icon_team.png';

function GameList() {
    const [games, setGames] = useState([]);

    useEffect(() => {
        const loadGames = async () => {
            try {
                const fetchedGames = await fetchGames();
                const gamesWithLogos = fetchedGames.map((game) => ({
                    ...game,
                    team1: { ...game.team1, logo: teamLogo },
                    team2: { ...game.team2, logo: teamLogo },
                }));
                setGames(gamesWithLogos);
            } catch (error) {
                console.error('Failed to fetch games:', error);
            }
        };

        loadGames();
    }, []);

    return (
        <Box sx={{ padding: 2 }}>
            {games.map((game) => (
                <Game
                    key={game.id}
                    homeTeam={game.team1}
                    awayTeam={game.team2}
                    homeScore={game.team1Score}
                    awayScore={game.team2Score}
                    field={game.field}
                    time={game.time}
                />
            ))}
        </Box>
    );
}

export default GameList;