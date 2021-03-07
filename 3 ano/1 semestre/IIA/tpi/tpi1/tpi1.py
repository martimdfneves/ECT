#Martim Neves 88904
#TPI discutido com Bruno Caseiro 88804, Tiago Dias 88896, João Tomás Simões 88930

from tree_search import *

class SearchNode2(SearchNode):
    def __init__(self,state,parent,heuristic): 
        super().__init__(state,parent)
        self.depth=0
        self.cost=0
        self.children=[]
        self.heuristic=heuristic
        self.evalfunc=self.heuristic+self.cost

        if (parent==None):
            self.depth=0
        else:
            self.depth=self.parent.depth+1

class MyTree(SearchTree):

    def __init__(self,problem, strategy='breadth',max_nodes=None): 
        self.problem=problem
        self.root = SearchNode2(problem.initial,None,self.problem.domain.heuristic(problem.initial, problem.goal))
        self.open_nodes = [self.root]
        self.strategy=strategy
        self.terminal_nodes=0
        self.non_terminal_nodes=1
        self.max_nodes = max_nodes

    def astar_add_to_open(self,lnewnodes):
        self.open_nodes = sorted(self.open_nodes+list(lnewnodes), key=lambda node: node.cost + node.heuristic)

    def effective_branching_factor(self):
        # num=(len(self.node.children)**(self.node.depth+1))-1
        # den=len(self.node.children)-1
        # return num/den
        pass

    def update_ancestors(self,node):
        if node not in node.children:
            min=99999
            for i in range(len(node.children)):
                if node.children[i].evalfunc<min:
                    min=node.children[i].evalfunc
        node.evalfunc=min
        return node.evalfunc

    def discard_worse(self):
        max=-99999
        ind=0
        for n in range(len(self.node.children)):
            if n not in self.node.children:
                if self.node.children[n].evalfunc>max:
                    max=self.node.children[n].evalfunc
                    ind=n
        del open_nodes[ind]
        self.terminal_nodes=self.node.children
        self.non_terminal_nodes=self.total_nodes-terminal_nodes
        return 

    def search2(self):
        self.solution_cost=0
        self.solution_length=0
        self.total_nodes=1
        while self.open_nodes != []:
            node = self.open_nodes.pop(0)
            if self.problem.goal_test(node.state):
                self.solution_cost = node.cost
                self.solution_length=node.depth
                return self.get_path(node)
            lnewnodes = []
            for a in self.problem.domain.actions(node.state):
                newstate = self.problem.domain.result(node.state,a)
                if newstate not in self.get_path(node):
                    self.total_nodes+=1
                    newnode = SearchNode2(newstate,node,self.problem.domain.heuristic(newstate, self.problem.goal))
                    newnode.cost=self.problem.domain.cost(node.state,a)+node.cost
                    newnode.evalfunc=newnode.cost + self.problem.domain.heuristic(newstate, self.problem.goal)
                    if lnewnodes==[]:
                        self.non_terminal_nodes+=1
                    else:
                        self.terminal_nodes+=1
                    lnewnodes.append(newnode)
                    newnode.depth = node.depth + 1
                    node.children.append(newnode)
            self.add_to_open(lnewnodes)
            self.update_ancestors(node)
            if self.max_nodes != None:
                while self.total_nodes>self.max_nodes:
                    self.discard_worse()
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