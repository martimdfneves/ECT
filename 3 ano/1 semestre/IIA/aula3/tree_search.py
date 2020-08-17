# Modulo: tree_search
#
# Fornece um conjunto de classes para suporte a resolucao de
# problemas por pesquisa em arvore:
#    SearchDomain  - dominios de problemas
#    SearchProblem - problemas concretos a resolver
#    SearchNode    - nos da arvore de pesquisa
#    SearchTree    - arvore de pesquisa, com metodos para
#                    a respectiva construcao
#
#  (c) Luis Seabra Lopes
#  Introducao a Inteligencia Artificial, 2012-2018,
#  Inteligência Artificial, 2014-2018

from abc import ABC, abstractmethod

# Dominios de pesquisa
# Permitem calcular
# as accoes possiveis em cada estado, etc
class SearchDomain(ABC):

    # construtor
    @abstractmethod
    def __init__(self):
        pass

    # lista de accoes possiveis num estado
    @abstractmethod
    def actions(self, state):
        pass

    # resultado de uma accao num estado, ou seja, o estado seguinte
    @abstractmethod
    def result(self, state, action):
        pass

    # custo de uma accao num estado
    @abstractmethod
    def cost(self, state, action):
        pass

    # custo estimado de chegar de um estado a outro
    @abstractmethod
    def heuristic(self, state, goal_state):
        pass

# Problemas concretos a resolver
# dentro de um determinado dominio
class SearchProblem:
    def __init__(self, domain, initial, goal):
        self.domain = domain
        self.initial = initial
        self.goal = goal

    def goal_test(self, state):
        return state == self.goal

# Nos de uma arvore de pesquisa
class SearchNode:
    def __init__(self, state, parent, depth, cost, heuristic):
        self.state = state
        self.parent = parent
        self.depth = depth
        self.cost = cost
        self.heuristic = heuristic

    def inParent(self, state):
        if self.parent == None:
            return False
        if self.parent.state == state:
            return True
        return self.parent.inParent(state)

    def __str__(self):
        return "no({}, {}, {})".format(self.state, self.parent, self.depth)

    def __repr__(self):
        return str(self)

# Arvores de pesquisa
class SearchTree:

    # construtor
    def __init__(self,problem, strategy='breadth'):
        self.problem = problem
        root = SearchNode(problem.initial, None, 0, 0, problem.domain.heuristic(problem.initial, problem.goal))
        self.open_nodes = [root]
        self.strategy = strategy

        self.highest_cost_nodes = [root]
        self.average_node_depth = 0

        self.solCost = 0

        self.nodes = 0
        self.leafs = 1

    # obter o caminho (sequencia de estados) da raiz ate um no
    def get_path(self,node):
        if node.parent == None:
            return [node.state]
        path = self.get_path(node.parent)
        path += [node.state]
        return(path)

    # procurar a solucao
    def search(self, limit=None):
        total_node_depth = 0
        while self.open_nodes != []:
            node = self.open_nodes.pop(0)

            # check if highest costing node so far
            if (node.cost > self.highest_cost_nodes[0].cost):
                self.highest_cost_nodes = [node]
            elif (node.cost == self.highest_cost_nodes[0].cost):
                self.highest_cost_nodes.append(node)

            # check if problem solved
            if self.problem.goal_test(node.state):
                self.solCost = node.cost
                self.average_branching = self.leafs/self.nodes
                self.average_node_depth = total_node_depth/self.nodes
                return self.get_path(node), node.depth, self.nodes-self.leafs, self.leafs, node.cost, self.average_node_depth

            # expand node
            lnewnodes = []
            self.leafs -= 1

            for a in self.problem.domain.actions(node.state):
                newstate = self.problem.domain.result(node.state,a)
                lnewnodes += [SearchNode(newstate,node, node.depth+1, \
                              node.cost + self.problem.domain.cost(node.state, a), \
                              self.problem.domain.heuristic(newstate, self.problem.goal))]
                self.leafs += 1
                self.nodes += 1
                total_node_depth += node.depth+1


            # print("[{}], ({})".format(node, lnewnodes))

            # add node's children to nodes to possibly explore
            self.add_to_open(new_n for new_n in lnewnodes if
                             not node.inParent(new_n.state)
                             and (new_n.depth < limit if limit else True))

        # no more nodes to explore, no solution found
        self.solCost = None
        self.average_branching = self.leafs/self.nodes
        self.average_node_depth = total_node_depth/self.nodes
        return None, None, self.nodes-self.leafs, self.leafs, None, self.average_node_depth

    # juntar novos nos a lista de nos abertos de acordo com a estrategia
    def add_to_open(self,lnewnodes):
        if self.strategy == 'breadth':
            self.open_nodes.extend(lnewnodes)
        elif self.strategy == 'depth':
            self.open_nodes[:0] = lnewnodes
        elif self.strategy == 'uniform':
            # sort nodes by cost
            self.open_nodes = sorted(self.open_nodes+list(lnewnodes), key=lambda n: n.cost)
        elif self.strategy == 'greedy':
            # sort nodes by heuristic
            self.open_nodes = sorted(self.open_nodes+list(lnewnodes), key=lambda n: n.heuristic)
        elif self.strategy == 'astar':
            # sort nodes by cost to node + heuristic from node to goal
            self.open_nodes = sorted(self.open_nodes+list(lnewnodes), key=lambda n: n.cost + n.heuristic)