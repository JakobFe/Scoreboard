import React from 'react';
import IconButton from '@mui/joy/IconButton';
import Menu from '@mui/icons-material/Menu';
import Box from '@mui/joy/Box';
import Typography from '@mui/joy/Typography';
import Drawer from '@mui/joy/Drawer';
import {
  BrowserRouter as Router,
  Routes,
  Route,
} from "react-router-dom";

import './App.css';
import SideDrawer from './components/SideDrawer';
import GameList from './components/GameList';
import Ranking from './components/Ranking';

function App() {

  const [open, setOpen] = React.useState(false);

  return (
    <Router>
      <React.Fragment>
        <Box sx={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', padding: 2 }}>
          <IconButton variant="outlined" color="neutral" onClick={() => setOpen(true)}>
            <Menu />
          </IconButton>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1, textAlign: 'center' }}>
            FAN 5-a-side
          </Typography>
        </Box>
        <Drawer open={open} onClose={() => setOpen(false)}>
          <SideDrawer />
        </Drawer>
      </React.Fragment>
      <Routes>
        <Route path="/" element={<GameList />} />
        <Route path="/games" element={<GameList />} />
        <Route path="/ranking" element={<Ranking />} />
      </Routes>
    </Router>
  );
}

export default App;
