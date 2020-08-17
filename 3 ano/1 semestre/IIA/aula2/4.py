import math

parouimpar=lambda num: True if num%2==0 else False
modulo=lambda x,y: True if abs(x)<abs(y) else False
polares=lambda x,y: (math.sqrt(x**2 + y**2), math.atan2(y,x))

print(parouimpar(11))
print(modulo(-15,-14))
print(polares(10,20))