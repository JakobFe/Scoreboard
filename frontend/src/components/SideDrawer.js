import React from 'react';
import { Link } from 'react-router-dom';
import Box from '@mui/joy/Box';
import List from '@mui/joy/List';
import ListItemButton from '@mui/joy/ListItemButton';
import Typography from '@mui/joy/Typography';
import ModalClose from '@mui/joy/ModalClose';

function SideDrawer() {
    return (
        <Box>
            <Box
                sx={{
                    display: 'flex',
                    alignItems: 'center',
                    gap: 0.5,
                    ml: 'auto',
                    mt: 1,
                    mr: 2,
                }}
            >
                <Typography
                    component="label"
                    htmlFor="close-icon"
                    fontSize="sm"
                    fontWeight="lg"
                    sx={{ cursor: 'pointer' }}
                >
                </Typography>
                <ModalClose id="close-icon" sx={{ position: 'initial' }} />
            </Box>
            <List
                size="lg"
                component="nav"
                sx={{
                    flex: 'none',
                    fontSize: 'xl',
                    '& > div': { justifyContent: 'center' },
                }}
            >
                <ListItemButton>
                    <Link 
                        style={{ textDecoration: 'none', color: 'black' }} 
                        to="/games"
                    >
                        Games
                    </Link>
                </ListItemButton>
                <ListItemButton>
                    <Link 
                        style={{ textDecoration: 'none', color: 'black' }} 
                        to="/ranking"
                    >
                        Ranking
                    </Link>
                </ListItemButton>
            </List>
        </Box>
    );
}

export default SideDrawer;