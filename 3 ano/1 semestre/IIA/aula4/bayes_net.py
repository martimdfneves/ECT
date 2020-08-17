

class BayesNet:

    def __init__(self, ldep=None):  # Why not ldep={}? See footnote 1.
        if not ldep:
            ldep = {}
        self.dependencies = ldep

    # Os dados estao num dicionario (var,dependencies)
    # em que as dependencias de cada variavel
    # estao num dicionario (mothers,prob);
    # "mothers" e' um frozenset de pares (mothervar,boolvalue)
    def add(self,var,mothers,prob):
        self.dependencies.setdefault(var,{})[frozenset(mothers)] = prob

    # Probabilidade conjunta de uma dada conjuncao 
    # de valores de todas as variaveis da rede
    def jointProb(self,conjunction):
        prob = 1.0
        for (var,val) in conjunction:
            for (mothers,p) in self.dependencies[var].items():
                if mothers.issubset(conjunction):
                    prob*=(p if val else 1-p)
        return prob

    def ancestors(self, var):
        lvars = [v for (v,x) in list(self.dependencies[var].keys())[0]]
        lvars2 = lvars

        for v in lvars:
            lvars2 += self.ancestors(v)
        return list(set(lvars2))

    def conjunctions(self, listvars):
        if listvars == []:
            return [[]]
        rec = self.conjunctions(listvars[1:])
        v = listvars[0]
        return[c + [(v,True)] for c in rec] + [c + [(v,False)] for c in rec]

    def individualProb(self, var, val):
        prob = 0
        lvars = self.ancestors(var)
        lcomb = [c + [var,val] for c in self.conjunctions(lvars)]

        for conj in lcomb:
            prob += self.jointProb(conj)
        returnprob

# Footnote 1:
# Default arguments are evaluated on function definition,
# not on function evaluation.
# This creates surprising behaviour when the default argument is mutable.
# See:
# http://docs.python-guide.org/en/latest/writing/gotchas/#mutable-default-arguments

