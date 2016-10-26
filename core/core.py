"""
A python module that provides methods 
to collect public figure information. 
"""

import os
import warnings
import json
from PFHandler import PFHandler


data = os.environ['GURU_ROOT'] + "scripts/data.json"

with open(data) as json_data:
    data_json = json.load(json_data)
    #print(d)

print data_json['public figures'][0]['username']

pfhandler = PFHandler()

pcount = 1
while True:
	profile = pfhandler.general_info(data_json['public figures'][pcount]['username'])
	print (profile['name'] + " has ")
	print profile['fan_count']
	pcount += 1

