import React from "react";
import Table from '@mui/joy/Table';
import Box from "@mui/joy/Box";

function createData(team, total_games, total_points, total_for, total_against) {
    return { team, total_games, total_points, total_for, total_against };
}

const rows = [
    createData('Team A', 5, 15, 12, 12),
    createData('Team B', 5, 12, 21, 15),
    createData('Team C', 5, 10, 8, 1),
    createData('Team D', 5, 3, 5, 10),
    createData('Team E', 5, 1, 11, 30),
];

function Ranking() {
    return (
        <Box sx={{ padding: 2 }}>
            <Table sx={{ '& tr > *:not(:first-child)': { textAlign: 'right' } }}>
                <thead>
                    <tr>
                        <th style={{ width: '40%' }}>Team</th>
                        <th>G</th>
                        <th>P</th>
                        <th>F</th>
                        <th>A</th>
                    </tr>
                </thead>
                <tbody>
                    {rows.map((row) => (
                        <tr key={row.team}>
                            <td>{row.team}</td>
                            <td>{row.total_games}</td>
                            <td>{row.total_points}</td>
                            <td>{row.total_for}</td>
                            <td>{row.total_against}</td>
                        </tr>
                    ))}
                </tbody>
            </Table>
        </Box>
    );
}

export default Ranking;