import requests
from datetime import datetime, timedelta

BASE_URL = "http://localhost:8080/api"

def create_tournament(name):
    response = requests.post(f"{BASE_URL}/tournaments", json={"name": name})
    return response.json()

def create_team(name, captain, mens_tournament, tournament_id):
    response = requests.post(f"{BASE_URL}/teams", json={
        "name": name,
        "captain": captain,
        "mensTournament": mens_tournament,
        "tournament": {"id": tournament_id}
    })
    return response.json()

def create_game(team1_id, team2_id, tournament_id, date, time, field):
    response = requests.post(f"{BASE_URL}/games", json={
        "team1": {"id": team1_id},
        "team2": {"id": team2_id},
        "team1Score": 0,
        "team2Score": 0,
        "stage": "Group Stage",
        "date": date,
        "time": time,
        "field": field,
        "completed": False,
        "tournament": {"id": tournament_id}
    })
    return response.json()

def main():
    tournament = create_tournament("Champions League")
    tournament_id = tournament["id"]

    teams = []
    for i in range(1, 5):
        team = create_team(f"Team {i}", f"Captain {i}", True, tournament_id)
        teams.append(team)

    start_date = datetime.strptime("2023-10-01", "%Y-%m-%d")
    time = "14:00"
    fields = ["Field 1", "Field 2", "Field 3", "Field 4"]
    
    game_index = 0
    for i in range(len(teams)):
        for j in range(i + 1, len(teams)):
            date = (start_date + timedelta(days=game_index)).strftime("%Y-%m-%d")
            field = fields[game_index % len(fields)]
            create_game(teams[i]["id"], teams[j]["id"], tournament_id, date, time, field)
            game_index += 1

if __name__ == "__main__":
    main()