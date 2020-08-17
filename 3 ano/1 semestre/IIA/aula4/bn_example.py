
from bayes_net import *

# Exemplo dos acetatos:

bn = BayesNet()

bn.add('r',[],0.001)
bn.add('t',[],0.002)

bn.add('a',[('r',True ),('t',True )],0.950)
bn.add('a',[('r',True ),('t',False)],0.940)
bn.add('a',[('r',False),('t',True )],0.290)
bn.add('a',[('r',False),('t',False)],0.001)

bn.add('j',[('a',True )],0.900)
bn.add('j',[('a',False)],0.050)

bn.add('m',[('a',True )],0.700)
bn.add('m',[('a',False)],0.100)

conjunction = [('j',True),('m',True),('a',True),('r',False),('t',False)]

print(bn.jointProb(conjunction))

print("-----------------------------------------------------------------")

sof = BayesNet()

sof.add('st',[],0.6)

sof.add('upal',[],0.05)

sof.add('cea',[('st',True)],0.001)
sof.add('cea',[('st',False)],0.9)

sof.add('pa',[('upal',True)],0.25)
sof.add('pa',[('upal',False)],0.004)

sof.add('cp',[('st',True),('pa',True)],0.02)
sof.add('cp',[('st',True),('pa',False)],0.01)
sof.add('cp',[('st',False),('pa',True)],0.011)
sof.add('cp',[('st',False),('pa',False)],0.001)

sof.add('fer',[('upal',True)],0.9)
sof.add('fer',[('pa',True),('upal',False)],0.1)
sof.add('fer',[('pa',False),('upal',False)],0.01)

alltrue = [('st',True),('upal',True),('cea',True),('pa',True),('cp',True),('fer',True)]
print(sof.jointProb(alltrue))

allfalse = [('st',False),('upal',False),('cea',False),('pa',False),('cp',False),('fer',False)]
print(sof.jointProb(allfalse))