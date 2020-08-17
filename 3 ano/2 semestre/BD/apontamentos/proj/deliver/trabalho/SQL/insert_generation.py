# -*- coding: utf-8 -*-
import json
import datetime
from random import randint

data = json.load(open('data.json'))

insert = open('insert.sql', "w")
insert.write("use MOTOSHOP\n")

user = []
staff = []
stand = []
workshop = []
motostation = []
motorcycle = []
rentable = []
owned = []
stock =[]
salesman = []
mechanic = []

def rand(arr):
	r = arr[randint(0, len(arr)-1)]
	if(isinstance(r, basestring)):
		return r.encode('utf-8')
	return r

def rand_addr():
	return rand(data["addresses"]) + ', Nr ' + str(randint(1, 60)) + ', ' + str(randint(1, 6)) + rand(data["aux_addrss"]).encode('utf-8')

def rand_part():
	return rand(data["parts"])

def rand_date():
	return str(2015 + randint(0, 3)) + "{:02d}".format(randint(1, 12)) + "{:02d}".format(randint(1, 28))

def generate_clients(nr):
	global user
	insert.write("\n")
	for i in range(nr):
		name = rand(data["first_name"])
		name += ' ' + rand(data["last_name"])
		nif = randint(100000000, 999999999)
		addr = rand_addr()
		user += [nif]
		insert.write("INSERT INTO CLIENT (NIF, C_NAME, C_ADDRESS) VALUES ({}, \'{}\', \'{}\');\n".format(nif, name, addr))

def generate_staff(nr):
	global staff
	insert.write("\n")
	for i in range(nr):
		number = randint(100, 999)
		name = rand(data["first_name"]) + ' ' + rand(data["last_name"])
		addr = rand_addr()
		staff += [100 + i]
		insert.write("INSERT INTO STAFF_MEMBER (S_NAME, S_ADDRESS) VALUES (\'{}\', \'{}\');\n".format(name, addr))

def generate_stand(nr, current_id):
	global stand
	insert.write("\n")
	for i in range(nr):
		local = rand(data["counties"])
		insert.write("INSERT INTO STAND (LOCALIZATION) VALUES (\'{}\');\n".format(local))
		stand += [i + 1]

def generate_workshop(nr, current_id):
	global workshop
	insert.write("\n")
	for i in range( nr):
		local = rand(data["counties"])
		insert.write("INSERT INTO WORKSHOP (LOCALIZATION) VALUES (\'{}\');\n".format(local))
		workshop += [i + 1]

def genererate_motostation(nr, current_id):
	global motostation
	insert.write("\n")
	for i in range(nr):
		local = rand(data["counties"])
		capacity = randint(5, 10)
		insert.write("INSERT INTO MOTO_STATION (LOCALIZATION, CAPACITY) VALUES (\'{}\', {});\n".format(local, capacity))
		motostation += [i + 1]

def generate_motorcycle(nr):
	global motorcycle
	insert.write("\n")
	for i in range(nr):
		frame = randint(100000000, 999999999)
		model = rand(data["moto_model"])
		brand = rand(data["moto_brand"])
		year = randint(1995, 2019)
		cc = rand(data["cc"])
		hp = 5 + int(cc/10) + randint(1, 5)
		insert.write("INSERT INTO MOTORCYCLE (FRAME_NO, MODEL, BRAND, YEAR, CC, HP) VALUES ({}, \'{}\', \'{}\', {}, {}, {});\n".format(frame, model, brand, year, cc, hp))
		motorcycle += [frame]

def generate_sale_mech():
	global staff, stand, workshop, salesman, mechanic
	insert.write("\n")
	for i in range(len(staff)):
		if i % 2 :
			insert.write("INSERT INTO SALESMAN (NUMBER, WORK_STAND) VALUES ({}, {});\n".format(staff[i], rand(stand)))
			salesman += [staff[i]]	
		else :
			insert.write("INSERT INTO MECHANIC (NUMBER, WORK_WORKSHOP) VALUES ({}, {});\n".format(staff[i], rand(workshop)))
			mechanic += [staff[i]]

def generate_moto_types():
	global motorcycle, rentable, owned, stock, motostation, user, stand
	insert.write("\n")
	for i in range(len(motorcycle)):
		if i % 3 == 0:
			price_km = randint(1, 10)
			total_km = randint(100, 50000)
			insert.write("INSERT INTO RENTABLE_BIKE (FRAME_NO, PRICE_KM, TOTAL_KM, MOTO_STATION) VALUES({}, {}, {}, {});\n".format(motorcycle[i], price_km, total_km, rand(motostation)))
			rentable += [motorcycle[i]]
		elif i % 3 > 0:
			price = randint(2500, 20000)
			insert.write("INSERT INTO STOCK_BIKE (FRAME_NO, PRICE, STAND) VALUES({}, {}, {});\n".format(motorcycle[i], price, rand(stand)))
			stock += [motorcycle[i]]

part_nrs = []
def generate_parts(nr):
	global part_nrs, part_txt
	insert.write("\n")
	for i in range(nr):
		part_nr = randint(100, 999)
		while(part_nr in part_nrs):
			part_nr = randint(100, 999)
		part_nrs += [part_nr]
		insert.write("INSERT INTO PART (NUMBER, PART_TEXT) VALUES ({}, \'{}\');\n".format(part_nr, rand_part()))


def generate_revision(): # And changed parts
	global motorcycle, workshop, mechanic
	insert.write("\n")
	for i in range (len(motorcycle)):
		# YYYYMMDD
		date = rand_date()
		price = randint(20, 500)
		insert.write("INSERT INTO REVISION (FRAME_NO, R_DATE, PRICE, MECHANIC) VALUES ({}, \'{}\', {}, {});\n".format(motorcycle[i], date, price, rand(mechanic)))
		used_parts = []
		for j in range(randint(1, 3)):
			part = rand(part_nrs)
			while(part in used_parts):
				part = rand(part_nrs)
			used_parts += [part]
			insert.write("INSERT INTO CHANGED_PARTS (REVISION_NO, PART) VALUES ({}, {});\n".format(i + 1000, part))

def generate_rent(nr):
	global rentable, user
	insert.write("\n")
	for i in range(nr):
		date = rand_date()
		insert.write("INSERT INTO RENT (FRAME_NO, R_DATE, CLIENT) VALUES ({}, \'{}\', {});\n".format(rand(rentable), date, rand(user)))

def generate_sale(nr):
	global stock, user, salesman
	insert.write("\n")
	for i in range(nr):
		date = rand_date()
		price = randint(2500, 20000)
		insert.write("INSERT INTO SALE (CLIENT, MOTORCYCLE, SELLER, PRICE, S_DATE) VALUES ({}, {}, {}, {}, \'{}\');\n".format(rand(user), stock.pop(0), rand(salesman), price, date))

id = 0

generate_clients(10)
generate_staff(20)
generate_stand(3, id)
generate_workshop(3, id)
genererate_motostation(7, id)
generate_motorcycle(20)
generate_sale_mech()
generate_moto_types()
generate_parts(30)
generate_revision()
generate_rent(10)
generate_sale(len(stock)/2)

