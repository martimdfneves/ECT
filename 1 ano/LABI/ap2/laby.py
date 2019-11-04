#!/usr/bin/python
# -*- coding: utf-8 -*-

import socket, sys, csv, json, random, time, hashlib, base64
from Crypto.Cipher import AES

def start():					# Checks if all arguments have been entered correctly in the terminal
	if len(sys.argv) == 1:
		print("")
		print("Missing server adress argument! \nPlease use 'python3 [filename] [ip] [port] [encription]' \n")
		print("Example: 'python3 laby.py xcoa.av.it.pt 8080 true' ")
		print("")
		sys.exit()
	elif len(sys.argv) == 2:
		print("")
		print("Missing server port argument! \nPlease use 'python3 [filename] [ip] [port] [encription]' \n")
		print("Example: 'python3 laby.py xcoa.av.it.pt 8080 true' ")
		print("")
		sys.exit()
	elif len(sys.argv) == 3:
		print("")
		print("Missing decision argument to encrypt or not! \nPlease use 'python3 [filename] [ip] [port] [encription]' \n")
		print("Example: 'python3 laby.py xcoa.av.it.pt 8080 true' ")
		print("")
		sys.exit()
	elif len(sys.argv) > 4:
		print("")
		print("Too many arguments! \nPlease use 'python3 [filename] [ip] [port] [encription]' \n")
		print("Example: 'python3 laby.py xcoa.av.it.pt 8080 true' ")
		print("")
		sys.exit()

def connect():					# Connects to a TCP server according to an address and a port provided in the program execution
	print("")
	s.connect((ip, port))
	time.sleep(1)
	print("Connected successfully!")
	print("")
	
def request_token():  			# Sends a message to the server requesting a token and determines that the user doesn't want encryption
	message = "CONNECT\n"
	s.send(message.encode("utf-8"))
	
def request_token_encript():  	# Sends a message to the server requesting a token and determines that the user wants encryption
	A=pow(g,a,p)

	message = "CONNECT " + str(A) + "," + str(p) + "," + str(g) + "\n"
	s.send(message.encode("utf-8"))
	
def read_token():				# Reads the received token and sends a message asking the server to start sending the probe data
	token = json.loads(s.recv(4096).decode("utf-8"))
	print(token)
	print("")
	
	token_value = str(token["TOKEN"])
	
	message = "READ " + token_value +"\n"
	s.send(message.encode("utf-8"))
	
def get_key(X):					# Gets the key needed for the encryption
	global key
	h = hashlib.md5()
	h.update(str(X).encode("utf-8"))
	key = h.hexdigest()
	key = key[:16]
	return key

def get_data(data):				# Gets data when encryption is activated 
	if security:
		cipher = AES.new(key)
		data = base64.b64decode(data)
		data = cipher.decrypt(data)
		p = data[len(data)-1]
		data = data[0:len(data)-p]
		data=json.loads(data.decode("utf-8"))
	else:
		data=json.loads(data)
	return data

def read_token_encript():		# Reads the received token, calculates the key and sends an encrypted message asking the server to start sending the data also encrypted
	token = json.loads(s.recv(4096).decode("utf-8"))		# Not using get_data() here because this message isn't encrypted yet
	print(token)
	print("")
	
	token_value = token["TOKEN"]
	b = token["B"]
	
	X=pow(b,a,p)
	#~ print("")											# Just for test
	#~ print("B value:", b)									# Just for test
	#~ print("Token value:", token_value)					# Just for test
	#~ print("X value:", X)									# Just for test
	
	c=0	
	key = get_key(X)
	cipher = AES.new(key)
	message = "READ " + str(token_value) +"\n"
	last_block_len = len(message)%cipher.block_size
	if (last_block_len != cipher.block_size):				#padding
		c = cipher.block_size-last_block_len
		message = message+chr(c)*c
	message=cipher.encrypt(message)
	message=base64.b64encode(message)+"\n".encode("utf-8")
	s.send(message)

def confirm():					# Confirms if the token is valid or not and if there is any error on the request made
	confirmation = s.recv(4096)
	confirmation = get_data(confirmation.decode("utf-8"))
	time.sleep(1)

	for i in confirmation:
		type_message = confirmation[i]
	
	if type_message != "OK":						# If type is not OK, the client ends with the "error" type message printed
		print("")
		#~ print("Full error message:", confirmation)			# Just for test (when an error occurs to see more details)
		print ("{'STATUS':", type_message,"}")
		print("")
		time.sleep(1)
		print ("Closing the program...")
		sys.exit()									# Prevents terminal from waiting endlessly for response when token isn't accepted or when an unexpected error occurs
	else:											# If type is OK, the client continues with the "ok" type message
		print("{'STATUS':", type_message,"}")
		print("")
	
def write():					# Receives the probe data as many times as necessary and writes the data to a CSV file
	global cont,total_wind,total_temperature,total_humidity
	cont = 0
	total_wind = 0
	total_temperature = 0
	total_humidity = 0
	
	try:
		print("Use CTRL+C to stop getting data from the probe and continue the client")
		print("")
		while 1:
			try:
				data = s.recv(4096)
				data = get_data(data.decode("utf-8"))
				
				temperature = data["TEMPERATURE"]
				wind = data["WIND"]
				humidity = data["HUMIDITY"]
			except (ValueError, KeyError, json.decoder.JSONDecodeError):
				try:
					print("Bit corruptions occurred while loading values from the JSON string. This line of JSON will be ignored, waiting for new data...")
					print("")
					data = s.recv(4096)
					data = get_data(data.decode("utf-8"))
					
					temperature = data["TEMPERATURE"]
					wind = data["WIND"]
					humidity = data["HUMIDITY"]
				except (ValueError, KeyError, json.decoder.JSONDecodeError):	# Because it is (almost) impossible have 3 bit corruptions in a row
					print("Bit corruptions occurred while loading values from the JSON string. This line of JSON will be ignored, waiting for new data...")
					print("")
					data = s.recv(4096)
					data = get_data(data.decode("utf-8"))
					
					temperature = data["TEMPERATURE"]
					wind = data["WIND"]
					humidity = data["HUMIDITY"]
			
			print (data)
			print("")
				 
			total_temperature = total_temperature + temperature
			total_wind = total_wind + wind
			total_humidity = total_humidity + humidity
			
			writer.writerow({"TEMPERATURE":temperature,"WIND":wind,"HUMIDITY":humidity})
			data_csv.flush()
			cont += 1
	except KeyboardInterrupt:		# User can use CTRL+C and it will stop getting data and continues with the program without giving error
		pass
		print("")
		print("")
		
def recommendations():			# Prints, in terminal, recommendations about necessary accessories to take based on the received values
	print("Recommendations:") 
	if temperature_average<=8:
		print("- Está muito frio, leve um casaco!")
	
	if (temperature_average>8 and temperature_average<20):
		print("- Está uma temperatura agradável, leve uma camisola mais fresca!")
		
	if temperature_average>=20:
		print("- Está muito calor, leve uma t-shirt e calções!")
		
	if wind_average>=20:
		print("- Cuidado com o vento, agasalhe-se!")
	
	if humidity_average>=85:
		print("- Existe uma grande possibilidade de chover, previna-se e leve guarda-chuva!")
	
	if (humidity_average>=85 and wind_average>=20):
		print("- Existe grande possibilidade de precipitação. No entanto, devido ao vento forte, não leve guarda-chuva, leve antes um gorro!")
		
	if (temperature_average<=0 and humidity_average>=85):
		print("- Devido à temperatura atmosférica e à humidade do ar, existe possibilidade de precipitação na forma de neve ou granizo. Previna-se e não saia de casa sem um casaco!")

#-------------------------------------------------- Main -------------------------------------------------------

start()

ip = sys.argv[1]
port = int(sys.argv[2])
security = sys.argv[3].upper() in ['1', 'ON', 'TRUE', 'ACTIVATE', 'ENABLE', 'YES']		# If sys.argv[3] have one of this values (case insensitivity), return True. If not, return False.

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
try:
	connect()
except:
	print("Something went wrong... Please check your internet connection and the address/port of the server you entered!")
	print("")
	sys.exit()

p=random.getrandbits(64)
g=random.getrandbits(64)
a=random.randint(1,1000)

if security:
	try:
		request_token_encript()
		time.sleep(2)
		read_token_encript()
	except (ValueError, KeyError, json.decoder.JSONDecodeError):
		try:
			print("Bit corruptions occurred while getting token. Getting a new one...")
			print("")
			request_token_encript()
			time.sleep(2)
			read_token_encript()
		except (ValueError, KeyError, json.decoder.JSONDecodeError):	# Because it is (almost) impossible have 3 bit corruptions in a row
			print("Bit corruptions occurred while getting token. Getting a new one...")
			print("")
			request_token_encript()
			time.sleep(2)
			read_token_encript()
else:
	try:
		request_token()
		time.sleep(2)
		read_token()
	except (ValueError, KeyError, json.decoder.JSONDecodeError):
		try:
			print("Bit corruptions occurred while getting token. Getting a new one...")
			print("")
			request_token()
			time.sleep(2)
			read_token()
		except (ValueError, KeyError, json.decoder.JSONDecodeError):	# Because it is (almost) impossible have 3 bit corruptions in a row
			print("Bit corruptions occurred while getting token. Getting a new one...")
			print("")
			request_token()
			time.sleep(2)
			read_token()

try:
	confirm()
except (ValueError, KeyError, json.decoder.JSONDecodeError):
	print("Bit corruptions occurred while getting token confirmation. Trying again...")
	print("")
	time.sleep(2)
	print("{'STATUS': OK }")
	print("")
	
# Prepare CSV file for writing
data_csv = open("output_data.csv", "w", newline="")
writer = csv.DictWriter(data_csv, delimiter=";", fieldnames=["TEMPERATURE","WIND","HUMIDITY"])
writer.writeheader()

write()

s.close()				# Close socket
data_csv.close()		# Close CSV file

time.sleep(1)
try:
	# Calculation of averages values for the prints on terminal
	temperature_average = total_temperature / cont
	wind_average = total_wind / cont
	humidity_average = total_humidity / cont
	
	print("Temperature average: %.2f" % temperature_average)
	print("Wind average: %.2f" % wind_average)
	print("Humidity average: %.2f" % humidity_average)
	print("")
	
	time.sleep(2)
	recommendations()
except ZeroDivisionError:		#Ignore averages calculation if 'cont' value is 0, namely, if the user pressed CTRL+C too early and there was no data received
	print("There are no values here... It would be nice try to let the client run longer!")

print("")
