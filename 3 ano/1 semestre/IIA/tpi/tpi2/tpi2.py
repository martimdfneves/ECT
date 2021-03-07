# Martim Neves 88904
#TPI discutido com Bruno Caseiro 88804, Tiago Dias 88896, João Simões 88930

from semantic_network import *
from bayes_net import *


class MySN(SemanticNetwork):


    def query_dependents(self,entity):
        depends = [d.relation.entity1 for d in self.declarations if isinstance(d.relation, Depends) and (d.relation.entity2 == entity)]
        subtipos = [d.relation.entity1 for d in self.declarations if isinstance(d.relation, Subtype) and (d.relation.entity2 == entity)]
        lista = depends

        for elem in depends:
            sub = [d.relation.entity1 for d in self.declarations if isinstance(d.relation, Subtype) and (d.relation.entity2 == elem)]
            if len(sub) > 0:   
                lista.remove(elem)  
            lista += sub + self.query_dependents(elem)

        for elem in subtipos:
            depends = [d.relation.entity1 for d in self.declarations if isinstance(d.relation, Depends) and (d.relation.entity2 == entity)]
            lista += depends + self.query_dependents(elem)

        return list(set(lista))

    def query_causes(self, entity):
        depends = [d.relation.entity2 for d in self.declarations if isinstance(d.relation, Depends) and (d.relation.entity1==entity)]
        subtipos = [d.relation.entity2 for d in self.declarations if isinstance(d.relation, Subtype) and (d.relation.entity1 == entity)]
        lista = depends

        if depends == [] and subtipos == []:
            return []

        for elem in depends:
            depend = [d.relation.entity2 for d in self.declarations if isinstance(d.relation, Depends) and (d.relation.entity1 == elem)]
            lista += depend + self.query_causes(elem)

        for elem in subtipos:
            depends = [d.relation.entity2 for d in self.declarations if isinstance(d.relation, Depends) and (d.relation.entity1 == entity)]
            lista += depends + self.query_causes(elem)

        return list(set(lista))

    def query_causes_sorted(self,entity):
        lst = []
        lst1 = self.query_causes(entity)
        for elem in lst1:
            cnt = 0
            avg = 0
            for elem2 in self.declarations:
                if isinstance(elem2.relation,Association) and elem2.relation.name == 'debug_time' and elem2.relation.entity1 == elem:
                    avg += elem2.relation.entity2
                    cnt += 1
            if cnt != 0:
                lst.append((elem, round(avg/cnt)))
        lst.sort(key = lambda tup: tup[1])
        return lst

class MyBN(BayesNet):

    def markov_blanket(self,var):
        lst = []
        ancestors = []
        children = []
        oth_par = []

        #ancestors
        ancestors = [v for (v,x) in list(self.dependencies[var].keys())[0]]

        #children
        for i in self.dependencies:
            children += [i for (y,z) in list(self.dependencies[i].keys())[0] if y == var]

        #other parent(s)
        for j in children:
            oth_par += [m for (m,n) in list(self.dependencies[j].keys())[0]] #acrescentei o +

        oth_par = set(oth_par) #acrescentei esta linha

        lst.extend(ancestors)
        lst.extend(children)
        lst.extend(oth_par)
        if var in lst: #acrescentei esta linha
            lst.remove(var)
        return lst