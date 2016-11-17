"""
A python module that provides methods 
to collect public figure information. 
"""

import os
import time
import warnings
import json
from PFHandler import PFHandler

start = time.strftime("%Y_%m_%d_%H:%M:%S")
data = os.environ['GURU_ROOT'] + "scripts/DATA1000.json"
publicfigures = os.environ['GURU_ROOT'] + "data/publicfigures_" + start

with open(data) as json_data:
    data_json = json.load(json_data)
   
print len(data_json['public figures'])

pfhandler = PFHandler()

pcount = 1

"""
with open(publicfigures,"w", encodin='utf-8') as outfile:
	json.dump([], outfile, indent=4)
"""
while True:
	profile = pfhandler.general_info(data_json['public figures'][pcount]['username'])
	with open(publicfigures, "w",) as outfile:
		json.dump(profile, outfile, indent=4)
	
	print (profile['name'] + " has " )
	print profile['fan_count']

	pcount += 1

