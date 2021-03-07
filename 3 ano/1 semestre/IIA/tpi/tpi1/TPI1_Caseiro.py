from tree_search import *

class MyTree(SearchTree):

    def __init__(self,problem, strategy='breadth',max_nodes=None): 
        self.problem = problem
        self.root = SearchNode(problem.initial, None, self.problem.domain.heuristic(self.problem.initial, self.problem.goal))
        self.open_nodes = [self.root]
        self.strategy = strategy
        self.total_nodes = 1        #root
        self.solution_cost = 0
        self.solution_length = 0
        self.terminal_nodes = 0
        self.non_terminal_nodes = 1     #root
        self.non_terminal_nodes_list = []
        self.leaves = 0
        self.max_nodes = max_nodes


    def astar_add_to_open(self,lnewnodes):
        #sorts by f(n) = g(n) + h(n)
        self.open_nodes = sorted(lnewnodes + self.open_nodes, key=lambda node: node.cost + node.heuristic)    

    def effective_branching_factor(self):

        n = self.total_nodes
        d = self.solution_length
        B = (n+1)/self.leaves
        #slide 126
        Naprox = pow(n, 1/d)

        #flags
        lower = False
        higher = False

        # Ajusta b consoante o erro
        # Valor encontrado por tentativa e erro*

        while abs(Naprox - n) > 0.04:       # 0.04*
            if Naprox > n:
                higher = True
                lower = False
            else:
                higher = False
                lower = True

            if higher:
                B -= 0.0001                  # 0.0001 *
                Naprox = (pow(B, d+1)-1)/(B-1)
            if lower:
                B += 0.0001                  # 0.0001 *
                Naprox = (pow(B, d+1)-1)/(B-1)

        return B
        

    def update_ancestors(self,node):
        # recursive
        if node == None or node.children == []:
            return

        #avoiding bugs
        node.evalfunc = 10000 

        #takes evalfunc of the child with lowest evalfunc
        for child in node.children:
            if child.evalfunc < node.evalfunc:
                node.evalfunc = child.evalfunc

        return self.update_ancestors(node.parent)

    def discard_worse(self):
        # list of non terminal nodes in which all children are leaves
        nodes = []

        #fills list
        for node in self.non_terminal_nodes_list:
            toAdd = True
            for child in node.children:
                if child.children != []:
                    toAdd = False
            if toAdd:    
                nodes.append(child)

        #gets node with highest evalfunc
        nodeHiEval = sorted(nodes, key=lambda node: node.evalfunc, reverse=True)[0]

        #removes children from q
        for child in nodeHiEval.children:
            self.open_nodes.remove(child)

        #adds parent to q
        self.open_nodes.append(nodeHiEval)

        return  #not working, loop infinito :(

    def search2(self):
        while self.open_nodes != []:
            # Could loop inside discard_worse, but in the enunciado diz lÃ¡ que a function Ã© CHAMADA one or more times
            # nodes in memory = terminal + non terminal


            ########## THE FOLLOWING CODE WAS INTENDED FOR EXERCISE 5 WHICH I COULDN'T FIGURE OUT A TEMPO ##########
            #if self.max_nodes != None:    
                #while (self.terminal_nodes + self.non_terminal_nodes) > self.max_nodes:
                    #self.discard_worse()
            ##########                                                                                ##########

            node = self.open_nodes.pop(0)
            if node.children == []:
                self.leaves += 1

            if self.problem.goal_test(node.state):
                self.solution_cost = node.cost
                self.solution_length = node.depth
                self.total_cost = node.cost
                return self.get_path(node)
            lnewnodes = []

            for a in self.problem.domain.actions(node.state):
                newstate = self.problem.domain.result(node.state,a)
                if newstate not in self.get_path(node):
                    self.total_nodes += 1
                    newnode = SearchNode(newstate, node, self.problem.domain.heuristic(newstate, self.problem.goal))
                    newnode.cost = self.problem.domain.cost(node.state, a) + node.cost
                    
                    # f(n) = g(n) + h(n)
                    newnode.evalfunc = newnode.cost + self.problem.domain.heuristic(newstate, self.problem.goal)
                    node.children.append(newnode)

                    

                    if lnewnodes == []:
                        self.terminal_nodes += 1
                    else:
                        self.non_terminal_nodes += 1
                        self.non_terminal_nodes_list += lnewnodes
                    
                    lnewnodes.append(newnode)

            self.add_to_open(lnewnodes)
            self.update_ancestors(node)
        return None

    # shows the search tree in the form of a listing
    def show(self,heuristic=False,node=None,indent=''):
        if node==None:
            self.show(heuristic,self.root)
            print('\n')
        else:
            line = indent+node.state
            if heuristic:
                line += (' [' + str(node.evalfunc) + ']')
            print(line)
            if node.children==None:
                return
            for n in node.children:
                self.show(heuristic,n,indent+'  ')


class SearchNode(SearchNode):
    def __init__(self,state,parent,heuristic): 
        #added heurisitc to constructor
        #heuristic and cost is the same but will be update in search2()
        self.state = state
        self.parent = parent
        self.cost = 0
        self.evalfunc = heuristic
        self.children = []
        self.heuristic = heuristic

        if (parent == None):
            self.depth = 0
        else:
            self.depth = self.parent.depth+1