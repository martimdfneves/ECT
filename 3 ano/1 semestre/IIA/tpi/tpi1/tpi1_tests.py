

import math

from tpi1 import *

class Cidades(SearchDomain):
    def __init__(self,connections, coordinates):
        self.connections = connections
        self.coordinates = coordinates
    def actions(self,city):
        actlist = []
        for (C1,C2,D) in self.connections:
            if (C1==city):
                actlist += [(C1,C2)]
            elif (C2==city):
               actlist += [(C2,C1)]
        return actlist 
    def result(self,city,action):
        (C1,C2) = action
        if C1==city:
            return C2
    def cost(self, city, action):
        pass
        if action[0]!=city:
            return None
        for (c1,c2,d) in self.connections:
            if (c1==action[0] and c2==action[1]) \
                    or (c2==action[0] and c1==action[1]):
                return d
        return None
    def heuristic(self, city, goal_city):
        pass
        (x1,y1) = self.coordinates[city]
        (x2,y2) = self.coordinates[goal_city]
        return math.hypot(x1-x2,y1-y2)
    def satisfies(self, city, goal_city):
        return goal_city==city


cidades_portugal = Cidades( 
                    # Ligacoes por estrada
                    [
                      ('Coimbra', 'Leiria', 73),
                      ('Aveiro', 'Agueda', 35),
                      ('Porto', 'Agueda', 79),
                      ('Agueda', 'Coimbra', 45),
                      ('Viseu', 'Agueda', 78),
                      ('Aveiro', 'Porto', 78),
                      ('Aveiro', 'Coimbra', 65),
                      ('Figueira', 'Aveiro', 77),
                      ('Braga', 'Porto', 57),
                      ('Viseu', 'Guarda', 75),
                      ('Viseu', 'Coimbra', 91),
                      ('Figueira', 'Coimbra', 52),
                      ('Leiria', 'Castelo Branco', 169),
                      ('Figueira', 'Leiria', 62),
                      ('Leiria', 'Santarem', 78),
                      ('Santarem', 'Lisboa', 82),
                      ('Santarem', 'Castelo Branco', 160),
                      ('Castelo Branco', 'Viseu', 174),
                      ('Santarem', 'Evora', 122),
                      ('Lisboa', 'Evora', 132),
                      ('Evora', 'Beja', 105),
                      ('Lisboa', 'Beja', 178),
                      ('Faro', 'Beja', 147),
                      ('Braga', 'Guimaraes', 25),
                      ('Porto', 'Guimaraes', 44),
                      ('Guarda', 'Covilha', 46),
                      ('Viseu', 'Covilha', 57),
                      ('Castelo Branco', 'Covilha', 62),
                      ('Guarda', 'Castelo Branco', 96),
                      ('Lamego','Guimaraes', 88),
                      ('Lamego','Viseu', 47),
                      ('Lamego','Guarda', 64),
                      ('Portalegre','Castelo Branco', 64),
                      ('Portalegre','Santarem', 157),
                      ('Portalegre','Evora', 194) ],

                    # City coordinates
                     { 'Aveiro': (41,215),
                       'Figueira': ( 24, 161),
                       'Coimbra': ( 60, 167),
                       'Agueda': ( 58, 208),
                       'Viseu': ( 104, 217),
                       'Braga': ( 61, 317),
                       'Porto': ( 45, 272),
                       'Lisboa': ( 0, 0),
                       'Santarem': ( 38, 59),
                       'Leiria': ( 28, 115),
                       'Castelo Branco': ( 140, 124),
                       'Guarda': ( 159, 204),
                       'Evora': (120, -10),
                       'Beja': (125, -110),
                       'Faro': (120, -250),
                       'Guimaraes': ( 71, 300),
                       'Covilha': ( 130, 175),
                       'Lamego' : (125,250),
                       'Portalegre': (130,170) }
                     )


p1 = SearchProblem(cidades_portugal,'Braga','Agueda')
t1 = MyTree(p1,'breadth')
print(t1.search2())
t1.show()
print('length=',t1.solution_length,', nodes=',t1.total_nodes,', cost=',t1.solution_cost,'\n')

p2 = SearchProblem(cidades_portugal,'Aveiro','Beja')
t2 = MyTree(p2,'breadth')
print(t2.search2())
print('length=',t2.solution_length,', nodes=',t2.total_nodes,', cost=',t2.solution_cost,'\n')

p3 = SearchProblem(cidades_portugal,'Braga','Evora')
t3 = MyTree(p3,'astar')
print(t3.search2())
print('length=',t3.solution_length,', nodes=',t3.total_nodes,', cost=',t3.solution_cost,'\n')

print('Effective branching factors:')
print(t1.effective_branching_factor())
print(t2.effective_branching_factor())
print(t3.effective_branching_factor())
print('\n')

t1.show(True)

p45 = SearchProblem(cidades_portugal,'Braga','Faro')

t4 = MyTree(p45,'astar')
print(t4.search2())
print('total=',t4.total_nodes,', non terminal=',t4.non_terminal_nodes,', terminal=',t4.terminal_nodes)

t5 = MyTree(p45,'astar',100)
print(t5.search2())
print('total=',t5.total_nodes,', non terminal=',t5.non_terminal_nodes,', terminal=',t5.terminal_nodes)


