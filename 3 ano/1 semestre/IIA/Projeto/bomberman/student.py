import sys
import json
import asyncio
import websockets
import getpass
import os
import math

from mapa import Map
from search import *

async def agent_loop(server_address="localhost:8000", agent_name="88930"):
    async with websockets.connect(f"ws://{server_address}/player") as websocket:

        # Receive information about static game properties
        await websocket.send(json.dumps({"cmd": "join", "name": agent_name}))
        msg = await websocket.recv()
        game_properties = json.loads(msg)

        # You can create your own map representation or use the game representation
        mapa = Map(size=game_properties["size"], mapa=game_properties["map"])

        # Variables assignment
        powerupsCount = 0
        bombCount = 0
        prev_state = []
        forceWall = 1

        while True:
            try:
                state = json.loads(
                    await websocket.recv()
                )  # Receive game state, this must be called timely or your game will get out of sync with the server
                
                mapa.walls = state['walls']
                
                # -------------------- CALL FUNCTIONS HERE --------------------

                # Starting problem
                domain = BombermanDomain(mapa, state)
                bomberman = domain.bomberman

                def avoidBomb(state):
                    x_state, y_state = state
                    bomb_pos = tuple(domain.bombs[0][0])
                    bomb_radius = domain.bombs[0][2]
                    enemyPos = []
                    for enemy in domain.enemies:
                        enemyPos += [tuple(enemy['pos'])]
                    actList = []
                    i = 1
                    while (i <= bomb_radius):
                        if not mapa.is_blocked((x_state - i, y_state)) and (x_state - i, y_state) not in enemyPos:
                            actList += ['a']
                        elif not mapa.is_blocked((x_state, y_state + i)) and (x_state, y_state + i) not in enemyPos:
                            actList += ['s']
                        elif not mapa.is_blocked((x_state + i, y_state)) and (x_state + i, y_state) not in enemyPos:
                            actList += ['d']
                        elif not mapa.is_blocked((x_state, y_state - i)) and (x_state, y_state - i) not in enemyPos:
                            actList += ['w']
                        else:
                            actList += ['']
                        i += 1

                    return actList

                # Prevent infinite loops while trying to kill enemies
                if (not len(prev_state) == len(state['enemies'])):  # If the bomb kill one or more enemies,
                    bombCount = 0                                   # Reset bomb count
                
                if (bombCount >= 10):                               # If 10 bombs are planted without killing any enemies,
                    forceWall = 4                                   # Break 4 walls to free space and then go search for enemies again and
                    bombCount = 0                                   # Reset bomb count

                prev_state = state['enemies']

                # Looking for powerups
                if (not domain.powerups == []):
                    # Search path to the powerup
                    p = SearchProblem(domain,tuple(bomberman),tuple(domain.powerups[0][0]))
                    path = BombermanTree(p).search()
                    
                    if (domain.heuristic(tuple(bomberman), tuple(domain.powerups[0][0])) <= 1):
                        powerupsCount += 1
                        # The powerup has been caught

                # Looking for enemies
                elif (not domain.enemies == [] and domain.bombs == [] and forceWall <= 1):
                    # Find nearest enemy
                    closeEnemy = None
                    minDist = 9999999
                    for enemy in state['enemies']:
                        dist = domain.heuristic(bomberman, enemy['pos'])
                        if(dist < minDist):
                            minDist = dist
                            closeEnemy = enemy
                    # Search path to the nearest enemy
                    p = SearchProblem(domain,tuple(bomberman),tuple(closeEnemy['pos']))
                    path = BombermanTree(p).search()
                    
                    # Blow up enemy
                    if (domain.heuristic(tuple(bomberman), tuple(closeEnemy['pos'])) < 3):
                        path = ['B']
                        # The bomb has been planted
                        bombCount += 1

                # Running away from the bomb
                elif (not domain.bombs == []):
                    path = avoidBomb(bomberman)

                # Looking for the exit
                elif (not domain.portal == [] and domain.enemies == [] and powerupsCount == domain.level):
                    # Search path to the exit portal
                    p = SearchProblem(domain,tuple(bomberman),tuple(domain.portal))
                    path = BombermanTree(p).search()

                # Looking for walls
                else:
                    # Find nearest wall
                    closeWall = None
                    minDist = 9999999
                    for wall in state['walls']:
                        dist = domain.heuristic(bomberman, wall)
                        if(dist < minDist):
                            minDist = dist
                            closeWall = wall
                    # Search path to the nearest wall
                    x_state, y_state = closeWall
                    if (not mapa.is_blocked((x_state, y_state-1))):
                        closeNextWall = (x_state,y_state-1)      
                    elif (not mapa.is_blocked((x_state, y_state+1))):
                        closeNextWall = (x_state, y_state+1)
                    elif (not mapa.is_blocked((x_state-1, y_state))):
                        closeNextWall = (x_state-1, y_state)
                    else:
                        closeNextWall = (x_state+1, y_state)

                    p = SearchProblem(domain,tuple(bomberman),closeNextWall)
                    path = BombermanTree(p).search()
                    
                    # Blow up wall
                    if (domain.heuristic(tuple(bomberman), closeNextWall) < 0.5):
                        path = ['B']
                        # The bomb has been planted
                        forceWall -= 1

                # -------------------- END CALL FUNCTIONS --------------------
                
                if path == []:
                    continue
                else:
                    await websocket.send(
                        json.dumps({"cmd": "key", "key": path.pop(0)})
                    )  # Send key command to server
                
            except websockets.exceptions.ConnectionClosedOK:
                print("Server has cleanly disconnected us")
                return

class BombermanDomain(SearchDomain):
    def __init__(self, mapa, state):
        self.mapa = mapa
        self.state = state
        self.bomberman = state['bomberman']
        self.walls = state['walls']
        self.bombs = state['bombs']
        self.enemies = state['enemies']
        self.powerups = state['powerups']
        self.portal = state['exit']
        self.level = state['level']

    # Given a position (state), must return the keys available to go to a free position
    def actions(self, state):
        x_state, y_state = state
        actlist = []
        if (not self.mapa.is_blocked((x_state, y_state - 1))):
            actlist += ["w"]
        if (not self.mapa.is_blocked((x_state - 1, y_state))):
           actlist += ["a"]
        if (not self.mapa.is_blocked((x_state, y_state + 1))):
           actlist += ["s"]
        if (not self.mapa.is_blocked((x_state + 1, y_state))):
           actlist += ["d"]
        return actlist

    # Given a position (state) and key (action), should return to the new position
    def result(self, state, action):
        x_state, y_state = state
        if action == "w":
            return (x_state, y_state - 1)
        elif action == "a":
            return (x_state - 1, y_state)
        elif action == "s":
            return (x_state, y_state + 1)
        elif action == "d":
            return (x_state + 1, y_state)
        else:
            return (x_state, y_state)

    def cost(self, state, action):
        return 1

    def heuristic(self, state, goal):
        x_orig, y_orig = state
        x_dest, y_dest = goal
        return math.hypot(x_orig - x_dest, y_orig - y_dest)

# DO NOT CHANGE THE LINES BELLOW
loop = asyncio.get_event_loop()
SERVER = os.environ.get("SERVER", "localhost")
PORT = os.environ.get("PORT", "8000")
NAME = os.environ.get("NAME", getpass.getuser())
loop.run_until_complete(agent_loop(f"{SERVER}:{PORT}", NAME))