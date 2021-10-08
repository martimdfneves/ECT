from kafka import KafkaProducer
from kafka.errors import KafkaError
import json 
import csv
import pprint
import time
from datetime import datetime

from os import listdir
from os.path import isfile, join

onlyfiles = [f for f in listdir('./dataset/') if isfile(join('./dataset/', f))]

firefighters = {'vr12': {'gps': [], 'env':[], 'hr': []}, 'a1': {'gps': [], 'env':[], 'hr': []}, 'a2': {'gps': [], 'env':[], 'hr': []}}

def convert_to_timestamp(datestring):
    date = datestring.split('/')
    a, b = date.index(datestring.split('/')[0]), date.index(datestring.split('/')[1])
    date[b], date[a] = date[a], date[b]
    date = '/'.join(date)
    return datetime.timestamp(datetime.strptime(date,'%m/%d/%Y %H:%M'))

for f in onlyfiles:
    type_ = f.split('_')[1].split('.')[0]
    if type_ == 'gps':
        with open('./dataset/' + f, encoding='utf-8-sig', newline='') as csvfile:
            spamreader = csv.reader(csvfile, delimiter=',', quotechar='|')
            
            for row in spamreader:
                firefighters[f.split('_')[0]][type_].append({
                    'name': f.split('_')[0],
                    'type' : type_,
                    'date': convert_to_timestamp(row[0]),
                    'gps_tag_lat': row[1],
                    'gps_tag_long': row[2],
                    'gps_time_tag': row[3],
                    'gps_alt_tag': row[4]
                })
    elif type_ == 'env':
        with open('./dataset/' + f, encoding='utf-8-sig',newline='') as csvfile:
            spamreader = csv.reader(csvfile, delimiter=',', quotechar='|')
            for row in spamreader:
                firefighters[f.split('_')[0]][type_].append({
                    'name': f.split('_')[0],
                    'type' : type_,
                    'date': convert_to_timestamp(row[0]),
                    'co': row[1],
                    'temp': row[2],
                    'hgt': row[3],
                    'no2': row[4],
                    'hum': row[5],
                    'lum': row[6],
                    'battery': row[7]
                })
    elif type_ == 'hr':
        with open('./dataset/' + f, encoding='utf-8-sig',newline='') as csvfile:
            spamreader = csv.reader(csvfile, delimiter=',', quotechar='|')
            for row in spamreader:
                firefighters[f.split('_')[0]][type_].append({
                    'name': f.split('_')[0],
                    'type' : type_,                    
                    'date': convert_to_timestamp(row[0]),
                    'hr': row[1],
                })

gps = firefighters['a1']['gps'] + firefighters['a1']['env'] + firefighters['a1']['hr'] + \
      firefighters['a2']['gps'] + firefighters['a2']['env'] + firefighters['a2']['hr'] + \
      firefighters['vr12']['gps'] + firefighters['vr12']['env'] + firefighters['vr12']['hr']

new = sorted(gps, key = lambda i: i['date']) 
pprint.pprint(new)

# 192.168.160.18:19092
# localhost:9092
producer = KafkaProducer(bootstrap_servers=['192.168.160.18:19092'],value_serializer=lambda m: json.dumps(m).encode('ascii'))
for item in new:
   if item['type'] == 'gps':
       producer.send('esp11_gps', item)
   elif item['type'] == 'env':
       producer.send('esp11_env', item)
   elif item['type'] == 'hr':
       producer.send('esp11_hr', item)
   pprint.pprint(item)
   time.sleep(0.04)



def on_send_success(record_metadata):
    print(record_metadata.topic)
    print(record_metadata.partition)
    print(record_metadata.offset)

def on_send_error(excp):
    log.error('I am an errback', exc_info=excp)
    # handle exception



# block until all async messages are sent
producer.flush()

# configure multiple retries
producer = KafkaProducer(retries=5)
