def listlen(lista):
	if lista==[]:
		return 0
	return 1+listlen(lista[1:])
	
def soma(lista):
	if lista==[]:
		return 0
	return lista[0]+soma(lista[1:])
	
def membro(x,lista):
	if lista==[]:
		return False
	return (lista[0]==x) or membro(x,lista[1:])
	
def concat(lista,lista1):
	if lista==[]:
		return lista1
	aux=concat(lista[1:],lista1)
	aux[:0]=[lista[0]]
	return aux
	
def inverter(lista):
	if lista==[]:
		return []
	aux = inverter(lista[1:])
	aux[len(aux):] = [lista[0]]
	return aux
	
def capicua(lista):
	if lista==[]:
		return False
	aux=inverter(lista)
	return aux==lista
	
def concatlist(lista):
	if lista==[]:
		return []
	if len(lista)==1:
		return lista[0]
	else:
		lista[0]=concat(lista[0], lista[1])
		del lista[1]
		return concatlist(lista)
		
def replace(lista,x,y):
	if lista==[]:
		return []
	if membro(x,lista):
		lista[lista.index(x)]=y
	else:
		return lista
	return replace(lista,x,y)
	
lista=[0,1,2,3,4,5,6,7,8,9]	
lista1=[10,11,12,13,14,15]
lista2=[]
lista3=[]
lista4=[1,2,3,4,5,4,3,2,1]
lista5=[lista,lista1,lista4]
print(listlen(lista))
print(soma(lista))
print(membro(1,lista))
print(concat(lista,lista1))
print(inverter(lista))
print(capicua(lista))
print(concatlist(lista5))
print(replace(lista,2,3))
