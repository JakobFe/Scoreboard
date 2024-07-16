import React from 'react';
import Box from '@mui/joy/Box';
import Game from './Game';

import teamLogo from '../static/img/icon_team.png';

function GameList() {
    const homeTeam = {
        name: 'Team A',
        logo: teamLogo,
      };
      const awayTeam = {
        name: 'Team B',
        logo: teamLogo,
      };
    
    return (
        <Box sx={{ padding: 2 }}>
            <Game homeTeam={homeTeam} awayTeam={awayTeam} homeScore="." awayScore="." field="Field A" time="20u30"/>
        </Box>
    );
}

export default GameList;