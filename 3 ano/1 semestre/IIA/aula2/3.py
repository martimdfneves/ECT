import functools

def homo (lista1, lista2):
    if lista1==[] or lista2==[]:
        return []
    if len(lista1) != len(lista2):
        return None
    return [(lista1[0],lista2[0])] + homo(lista1[1:],lista2[1:])

def menor(l1):
    if l1==[]:
        return None
    return functools.reduce(lambda h, m: h if h<m else m, l1)

def par(l1):
    if l1==[]:
        return None
    i=l1.index(functools.reduce(lambda h, m: h if h<m else m, l1))
    return (l1[i],l1[0:i]+l1[i+1:])

l1=[1,2,3,0,4,5]
l2=[6,7,8,9,0]
print(homo(l1,l2))
print(menor(l1))
print(par(l1))