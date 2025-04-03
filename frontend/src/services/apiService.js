import axios from 'axios';

const BASE_URL = process.env.REACT_APP_API_BASE_URL;
const TOURNAMENT_ID = process.env.REACT_APP_TOURNAMENT_ID;

const api = axios.create({
  baseURL: BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const fetchGames = async () => {
  const response = await api.get('/games', {
    params: {
      tournament: TOURNAMENT_ID,
    },
  });
  console.log('Fetched games:', response);
  return response.data;
};