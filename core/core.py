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
    d = json.load(json_data)
    #print(d)

print d['publicfigures'][0]['username']

pfhandler = PFHandler()
fan_count = pfhandler.general_info('belindapop')
print fan_count

