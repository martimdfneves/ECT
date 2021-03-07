from operator import *

# Dominios de pesquisa
class SearchDomain():

    # Constructor
    def __init__(self):
        pass

    # Lista de ações possíveis num estado (linha, coluna)
    def actions(self, state):
        pass

    # Resultado de uma ação num estado, ou seja, o estado seguinte
    def result(self, state, action):
        pass

    # Custo de uma ação num estado
    def cost(self, state, action):
        pass

    # Custo estimado de chegar de um estado a outro
    def heuristic(self, state, goal_state):
        pass

# Problemas concretos a resolver dentro de um determinado dominio
class SearchProblem:
    def __init__(self, domain, initial, goal):
        self.domain = domain
        self.initial = initial
        self.goal = goal

    def goal_test(self, state):
        return state == self.goal

# Nós da árvore de pesquisa do Bomberman
class BombermanNode:
    def __init__(self, state, parent, depth, cost, heuristic):
        self.state = state
        self.parent = parent
        self.depth = depth
        self.cost = cost
        self.heuristic = heuristic

    def in_parent(self, state):
        if self.parent == None:
            return False
        
        return self.parent.state == state or self.parent.in_parent(state)

    def __str__(self):
        return "["+ str(self.state) + " - " + str(self.depth) + " - " + str(self.cost) + "]"
    
    def __repr__(self):
        return str(self)

# Arvores de pesquisa
class BombermanTree:
    # Construtor
    def __init__(self, problem):
        self.problem = problem
        root = BombermanNode(problem.initial, None, 0, 0, self.problem.domain.heuristic(problem.initial, problem.goal))
        self.open_nodes = [root]
        self.length = 0
        self.nodes = 0
        self.leafs = 1
        self.totalCost = 0

    # Obter o caminho (sequencia de estados) da raiz até um nó
    def get_path(self, node):
        if node.parent == None:
            return []
        # Vai até ao inicio da árvore recursivamente
        path = self.get_path(node.parent)
        # Cria uma lista com o path desde a raiz até ao destino
        move = list(map(sub, node.state, node.parent.state))
        if (move == [1, 0]):
            key = 'd'
        elif (move == [-1, 0]):
            key = 'a'
        elif (move == [0, 1]):
            key = 's'
        elif (move == [0, -1]):
            key = 'w'
        else:
            key = ''
        path += [key]
        return path

    # Procurar a solução
    def search(self):
        limit = 0
        # Enquanto houver nós folhas
        while self.open_nodes != []:
            # Retira o nó folha
            node = self.open_nodes.pop(0)
            # Se o nó for igual ao nó de destino
            if self.problem.goal_test(node.state) or limit >= 1000:
                # Retorna o caminho do destino até à origem atraves dos nos pais
                self.length = node.depth
                self.totalCost = node.cost
                return self.get_path(node)
            lnewnodes = []
            limit += 1
            # Expandir um nó, significa um nó terminal a menos            
            self.leafs -= 1
            for a in self.problem.domain.actions(node.state):
                newstate = self.problem.domain.result(node.state, a)
                # Previne a criação de ciclos
                if not node.in_parent(newstate):
                    # Cria um nó com a casa destino e a casa origem como pai
                    lnewnodes += [BombermanNode(newstate,node,node.depth + 1,node.cost+ 
                                 self.problem.domain.cost(node.state, a), 
                                 self.problem.domain.heuristic(newstate,self.problem.goal))]
                # Incrementar o número de nos terminais e não terminais
                self.nodes += 1
                self.leafs += 1
            # Adiciona a lista dos nós folhas à árvore
            self.add_to_open(lnewnodes)
        return None

    def add_to_open(self,lnewnodes):        
        # Greedy strategy
        self.open_nodes = sorted(self.open_nodes+list(lnewnodes), key=lambda n: n.heuristic)