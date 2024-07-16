import React from 'react';
import { Card, Box, Typography } from '@mui/joy';

function Game({ homeTeam, awayTeam, homeScore, awayScore, field, time }) {
  return (
    <Card
      sx={{
        display: 'flex',
        flexDirection: 'row',
        alignItems: 'center',
      }}
    >
      <Box sx={{ textAlign: 'center', flex: 1 }}>
        <img src={homeTeam.logo} alt={`${homeTeam.name} logo`} style={{ height: '80px' }} />
        <Typography level="body1" component="div" sx={{ fontSize: '1.2rem', fontWeight: 'bold', marginTop: 1 }}>
          {homeTeam.name}
        </Typography>
      </Box>
      <Box sx={{ textAlign: 'center', flex: 1 }}>
        <Typography level="body1" component="div" sx={{ fontSize: '1rem', color: '#666' }}>
          {time}
        </Typography>
        <Box sx={{ display: 'flex', alignItems: 'center', justifyContent: 'center', gap: 2 }}>
          <Typography level="h1" component="div" sx={{ fontSize: '3rem', fontWeight: 'bold' }}>
            {homeScore}
          </Typography>
          <Typography level="h2" component="div" sx={{ fontSize: '2rem', color: '#666' }}>
            -
          </Typography>
          <Typography level="h1" component="div" sx={{ fontSize: '3rem', fontWeight: 'bold' }}>
            {awayScore}
          </Typography>
        </Box>
        <Typography level="body1" component="div" sx={{ fontSize: '1rem', color: '#666' }}>
          {field}
        </Typography>
      </Box>
      <Box sx={{ textAlign: 'center', flex: 1 }}>
        <img src={awayTeam.logo} alt={`${awayTeam.name} logo`} style={{ height: '80px' }} />
        <Typography level="body1" component="div" sx={{ fontSize: '1.2rem', fontWeight: 'bold', marginTop: 1 }}>
          {awayTeam.name}
        </Typography>
      </Box>    
    </Card>
  );
}

export default Game;