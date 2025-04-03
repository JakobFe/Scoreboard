import axios from 'axios';
import { fetchGames } from './apiService';

jest.mock('axios');

describe('fetchGames', () => {
  it('should fetch games data successfully', async () => {
    const mockData = [{ id: 1, name: 'Game 1' }, { id: 2, name: 'Game 2' }];
    axios.get.mockResolvedValueOnce({ data: mockData });

    const result = await fetchGames();

    expect(axios.get).toHaveBeenCalledWith('/games');
    expect(result).toEqual(mockData);
  });

  it('should handle errors', async () => {
    axios.get.mockRejectedValueOnce(new Error('Network Error'));

    await expect(fetchGames()).rejects.toThrow('Network Error');
  });
});
