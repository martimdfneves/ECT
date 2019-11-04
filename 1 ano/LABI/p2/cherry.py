import os
import os.path
import cherrypy
import sqlite3
import sys
import json
import wave,struct
import pyaudio
import datetime
import hashlib

baseDir = os.path.dirname(os.path.abspath(__file__))
cherrypy.config.update({'server.socket_port': 10003})

config = {
	"/": { "tools.staticdir.root": baseDir },
	"/js": { "tools.staticdir.on": True, "tools.staticdir.dir": "js" },
	"/css": { "tools.staticdir.on": True, "tools.staticdir.dir": "css" },
	"/html": { "tools.staticdir.on": True, "tools.staticdir.dir": "html" },
	"/img": { "tools.staticdir.on": True, "tools.staticdir.dir": "img" },
	"/audio": { "tools.staticdir.on": True, "tools.staticdir.dir": "audio" },
	"/webfonts": { "tools.staticdir.on": True, "tools.staticdir.dir": "webfonts" },
	}

class Root:
	user=""
	indexHTML = """
		<html lang="en">
			<head>
				<!-- Titulo -->
				<title>Home</title>
				
				<!-- Meta's -->
				<meta charset="UTF-8">
				<meta name="viewport" content="width=device-width, initial-scale=1.0">
				<meta http-equiv="X-UA-Compatible" content="ie=edge">
				
				<!-- CSS's -->
				<link rel="shortcut icon" href="/labi2018-p2-g3/img/music-fontawesome.png" type="image/png">
				<link rel="stylesheet" type="text/css" href="/labi2018-p2-g3/css/bootstrap.css"></link>
				<link rel="stylesheet" type="text/css" href="/labi2018-p2-g3/css/style.css"></link>
				<link rel="stylesheet" type="text/css" href="/labi2018-p2-g3/css/fontawesome-all.css"></link>
				
				<!-- Script's -->
				<script src="/labi2018-p2-g3/js/jquery.min.js"></script>
				<script type="application/javascript" src="/labi2018-p2-g3/js/code.js"></script>
				<script type="application/javascript" src="/labi2018-p2-g3/js/bootstrap.js"></script>
			</head>
			<body>
				<h1>Welcome <i>{whoareyou}</i> !</h1>

				<br>
				
				<p>Go to the <a href="/labi2018-p2-g3/html/page1.html">List of Songs</a>.</p>
				<p>Go to the <a href="/labi2018-p2-g3/html/page2.html">List of Samples</a>.</p>
				<p>Go to the <a href="/labi2018-p2-g3/html/page3.html">Creator of Songs</a>.</p>

				<br>
				
				<button class="btn btn-sm" type="button" onclick="location.href='/labi2018-p2-g3/list?type=songs';">List of Songs</button>
				<button class="btn btn-sm" type="button" onclick="location.href='/labi2018-p2-g3/list?type=samples';">List of Samples</button>
			</body>
		</html>
	"""

	@cherrypy.expose
	def index(self):
		Root.user = cherrypy.request.headers.get('X-Remote-User')	
		return Root.indexHTML.format(whoareyou=Root.user)
	
	@cherrypy.expose
	def list(self, type):
		#print(type)
		db = sqlite3.connect("SongsDataBase.db",check_same_thread=False)
		data =[] # Cria o dicionário
		
		if (type == 'songs'):
			result = db.execute('SELECT * From Songs WHERE type = "Song"')
			#print("Songs:")
		elif (type == 'samples'):
			result = db.execute('SELECT * From Songs WHERE type = "Sample"')	
			#print("Samples:")	
		else:
			#print("All:")
			result = db.execute("SELECT * From Songs")

		rows = result.fetchall()
		for row in rows:
			linha = {}
			# Linhas para o ficheiro JSON
			linha['name'] = row[1]
			linha['id'] = row[0]
			linha['length'] = row[2]
			linha['date'] = row[3]
			linha['uses'] = row[4]
			linha['votes'] = row[5]
			linha['author'] = row[6]
			linha['type'] = row[7]
			linha['songid'] = row[8]
			data.append(linha) # Adiciona a linha no dicionário
		
		data = json.dumps(data)
		#print(data)
		return data

	@cherrypy.expose
	def loguser(self):
		Root.user = cherrypy.request.headers.get('X-Remote-User')	
		return Root.user
	
	@cherrypy.expose
	def SongVotes(self):
		db = sqlite3.connect("SongsDataBase.db",check_same_thread=False)
		data =[] # Cria o dicionário

		result = db.execute("SELECT * From Votes")

		rows = result.fetchall()

		for row in rows:
			linha = {}
			linha['Id']= row[0]
			linha['SongId']=row[1]
			linha['User']=row[2]
			linha['Vote']=row[3]
			data.append(linha)
		#print("Votos:")
		#print(data)
		data=json.dumps(data)
		return data

	@cherrypy.expose
	def put(self, sheet, name):
		db = sqlite3.connect("SongsDataBase.db",check_same_thread=False)
		data=json.loads(sheet)
		print(name)
		bpm=str(data['bpm'])	
		samples=data['samples']	
		effects= data['effects']
		music = data['music']

		output = wave.open("audio/" + name + ".wav", 'wb')
		MD5 = hashlib.md5() #MD5 para criação da SongId
		Sid=""
		song = []
		z=0
		#abre a pauta para escrever a música 
		for pos in music:
			z+=1
			temp=[]
			for p in pos:			
				if p!=",":
					if p!=" " and p!="":
						temp.append(p)
			files=[]
			for n in samples:
				f = wave.open("audio/"+n+".wav","rb")
				files.append(f)
								
			trim=b''
			i=0
			if (len(temp)>0):
				while True:
					if (len(temp)==1): #se for um único sample lê os frames mais rádio
						trim2=files[int(temp[i])].readframes(2048)
					else: # lê frame a frame 
						trim2=files[int(temp[i])].readframes(1)
					if not trim2:
						files[int(temp[i])].close()
						break
					trim=trim+trim2
					output.setframerate((60/int(bpm))*len(temp))
					output.setparams(files[0].getparams())
					if i==len(temp)-1:
						i=0
					else:
						i +=1
			MD5.update(trim)
			Sid = Sid+MD5.hexdigest()
			song.append(trim)	#adiciona o trim a musica							
					
		Sid = Sid[0:17] # limita o ID a 17 algarismos

		for v in song:
			output.writeframes(v) # Escreve os frames 

		frames=output.getnframes()
		rate=output.getframerate()
		duration=frames/float(rate)
		duration=float("{0:.2f}".format(duration))
		now = datetime.datetime.now()
		db.execute("INSERT INTO Songs (name,length,date,uses,votes,author,type,songid) VALUES (?,?,?,?,?,?,?,?)",(name,str(duration),str(now.day)+"-"+str(now.month)+"-"+str(now.year),0,0,str(Root.user),"Song",Sid))
		db.commit()
		db.close()
		
		output.close()


	
	@cherrypy.expose
	def get(self, id):
		return ("Vai obter a musica ou o sample com o id {0}".format(id))

	@cherrypy.expose
	def vote(self, id, user, points):
		db = sqlite3.connect("SongsDataBase.db",check_same_thread=False)
		if points == "-1" : # retira um voto
						
			result = db.execute("SELECT votes From Songs WHERE id = ?",(id,))
			rows = result.fetchall()			
			points = rows[0]
			points = int(points[0]) - 1
						
			result = db.execute("UPDATE Songs SET votes = {0} WHERE id = {1}".format(int(points),id))
			db.commit()

			result = db.execute("SELECT * From Votes WHERE SongId = ? and Vote = ?",(id,"1"))
			rows=result.fetchall()

			if(len(rows) != 0):
				result = db.execute("UPDATE Votes SET Vote = {0} WHERE SongId = {1}".format("-1",id))
				db.commit()
			else:
				result = db.execute("INSERT INTO Votes (SongId,User,Vote) VALUES ({0},\"{1}\",{2})".format(id,Root.user,"-1"))
				db.commit()	

		elif points == "1" : #adiciona um voto
			result = db.execute("SELECT votes From Songs WHERE id = ?",(id,))
			rows = result.fetchall()			
			points = rows[0]
			points = int(points[0]) + 1
						
			result = db.execute("UPDATE Songs SET votes = {0} WHERE id = {1}".format(int(points),id))
			db.commit()

			result = db.execute("SELECT * From Votes WHERE SongId = ? and Vote = ?",(id,"-1"))
			rows=result.fetchall()

			if(len(rows) != 0):
				result = db.execute("UPDATE Votes SET Vote = {0} WHERE SongId = {1}".format("1",id))
				db.commit()
			else:
				result = db.execute("INSERT INTO Votes (SongId,User,Vote) VALUES ({0},\"{1}\",{2})".format(id,Root.user,"1"))
				db.commit()
			

		result = db.execute("SELECT type From Songs WHERE Id = ? ",(id,))
		rows=result.fetchall()
		tipo=rows[0]
		tipo=tipo[0]
		db.close()
		if(tipo=="Song"):
			raise cherrypy.HTTPRedirect("/html/page1.html", 301)
		else:
			raise cherrypy.HTTPRedirect("/html/page2.html", 301)	
	
	@cherrypy.expose
	def upload(self, myFile):
		db = sqlite3.connect("SongsDataBase.db",check_same_thread=False)
		fo = open(os.getcwd()+ "/audio/" + myFile.filename, "wb")
		while True:
			data = myFile.file.read(8192)
			if not data:
				break
			fo.write(data)		
		fo.close()

		MD5 = hashlib.md5()
		Sid=""

		fo=wave.open("audio/"+myFile.filename,"rb")
		#abre o ficheiro para ler os frames e criar a chave
		while True:			
			trim=fo.readframes(2048)
			if not trim:
				break
			MD5.update(trim)
			Sid = Sid+MD5.hexdigest()
		Sid = Sid[0:17]

		frames=fo.getnframes()
		rate=fo.getframerate()
		name=str(myFile.filename).split(".")
		duration=frames/float(rate)
		duration=float("{0:.2f}".format(duration))
		now = datetime.datetime.now()
		db.execute("INSERT INTO Songs (name,length,date,uses,votes,author,type,songid) VALUES (?,?,?,?,?,?,?,?)",(name[0],str(duration),str(now.day)+"-"+str(now.month)+"-"+str(now.year),0,0,str(Root.user),"Sample",Sid))
		db.commit()
		db.close()
		
		raise cherrypy.HTTPRedirect("/html/page2.html", 301)

cherrypy.quickstart(Root(), "/", config)
