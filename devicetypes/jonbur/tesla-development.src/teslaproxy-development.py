from flask import Flask
import os
import teslajson
from geopy.distance import vincenty
import json
from config import *


def establish_connection(token=None):
	#c = teslajson.Connection(email=TESLA_EMAIL, password=TESLA_PASSWORD, access_token=token)
	c = teslajson.Connection(email=TESLA_EMAIL, password=TESLA_PASSWORD)
	return c

def get_climate(c, car):
	climate = None
	for v in c.vehicles:
		if v["vin"] == car:
			d = v.data_request("climate_state")
			climate = d
	return climate

def get_lockstatus(c, car):
	lockstatus = None
	for v in c.vehicles:
		if v["vin"] == car:
			d = v.data_request("vehicle_state")
			lockstatus = d
	return lockstatus

def get_isclimateon(c, car):
	return get_climate(c, car)['is_climate_on']

def get_iscarlocked(c,car):
	return get_lockstatus(c, car)['locked']

def get_location(c, car):
	location = None
	for v in c.vehicles:
		if v["vin"] == car:
			d = v.data_request("drive_state")
			location = (d["latitude"], d["longitude"])
	return location

def get_distancefromhome(c, car):
	current_location = get_location(c, car) 
	return vincenty(current_location, HOME_LOCATION).meters

def get_isvehiclehome(c, car):
	if get_distancefromhome(c, car) <= 100:
		return True
	else:
		return False

def start_hvac(c, car):
	for v in c.vehicles:
		if v["vin"] == car:
			result = v.command('auto_conditioning_start')['response']['result']
	return result

def stop_hvac(c, car):
	for v in c.vehicles:
		if v["vin"] == car:
			result = v.command('auto_conditioning_stop')['response']['result']
	return result

def door_lock(c, car):
	for v in c.vehicles:
		if v["vin"] == car:
			result = v.command('door_lock')['response']['result']
	return result

def door_unlock(c, car):
	for v in c.vehicles:
		if v["vin"] == car:
			result = v.command('door_unlock')['response']['result']
	return result

def get_chargestatus(c, car):
	chargestatus = None
	for v in c.vehicles:
		if v["vin"] == car:
			chargestatus = v.data_request("charge_state")
	return chargestatus

def get_iscarcharging(c,car):
	return get_chargestatus(c, car)['charging_state']

def get_batterylevel(c,car):
	return get_chargestatus(c, car)['battery_level']

def get_batteryrange(c,car):
	return get_chargestatus(c, car)['battery_range']

def get_timetocharge(c,car):
	return get_chargestatus(c, car)['time_to_full_charge']

def charge_start(c,car):
	for v in c.vehicles:
		if v["vin"] == car:
			result = v.command('charge_start')['response']['result']
	return result

def charge_stop(c,car):
	for v in c.vehicles:
		if v["vin"] == car:
			result = v.command('charge_stop')['response']['result']
	return result

app = Flask(__name__)

@app.route('/')
def index():
	return 'Hello, World!'

@app.route('/api/distancefromhome')
def distancefromhome():
	c = establish_connection()
	data = {}
	data['distancefromhome'] = str(get_distancefromhome(c, VEHICLE_VIN))
	return json.dumps(data)

@app.route('/api/isvehiclehome')
def isvehiclehome():
	c = establish_connection()
	data = {}
	data['isvehiclehome'] = str(get_isvehiclehome(c, VEHICLE_VIN))
	return json.dumps(data)

@app.route('/api/getclimate')
def getclimate():
	c = establish_connection()
	return str(get_climate(c, VEHICLE_VIN))

@app.route('/api/isclimateon')
def isclimateon():
	c = establish_connection()
	data = {}
	data['isclimateon'] = str(get_isclimateon(c, VEHICLE_VIN))
	return json.dumps(data)

@app.route('/api/starthvac')
def starthvac():
	c = establish_connection()
	data = {}
	data['result'] = str(start_hvac(c, VEHICLE_VIN))
	return json.dumps(data)

@app.route('/api/stophvac')
def stophvac():
	c = establish_connection()
	data = {}
	data['result'] = str(stop_hvac(c, VEHICLE_VIN))
	return json.dumps(data)

@app.route('/api/getlockstatus')
def getlockstatus():
	c = establish_connection()
	return str(get_lockstatus(c, VEHICLE_VIN))

@app.route('/api/iscarlocked')
def iscarlocked():
	c = establish_connection()
	data = {}
	data['iscarlocked'] = str(get_iscarlocked(c, VEHICLE_VIN))
	return json.dumps(data)

@app.route('/api/doorlock')
def doorlock():
	c = establish_connection()
	data = {}
	data['result'] = str(door_lock(c, VEHICLE_VIN))
	return json.dumps(data)

@app.route('/api/doorunlock')
def doorunlock():
	c = establish_connection()
	data = {}
	data['result'] = str(door_unlock(c, VEHICLE_VIN))
	return json.dumps(data)

@app.route('/api/getchargingstatus')
def getchargestatus():
	c = establish_connection()
	return str(get_chargestatus(c, VEHICLE_VIN))

@app.route('/api/iscarcharging')
def iscarcharging():
	c = establish_connection()
	data = {}
	data['iscarcharging'] = str(get_iscarcharging(c, VEHICLE_VIN))
	return json.dumps(data)

@app.route('/api/getbatterylevel')
def getbatterylevel():
	c = establish_connection()
	data = {}
	data['getbatterylevel'] = str(get_batterylevel(c, VEHICLE_VIN))
	return json.dumps(data)

@app.route('/api/getbatteryrange')
def getbatteryrange():
	c = establish_connection()
	data = {}
	data['getbatteryrange'] = str(get_batteryrange(c, VEHICLE_VIN))
	return json.dumps(data)

@app.route('/api/gettimetocharge')
def gettimetocharge():
	c = establish_connection()
	data = {}
	data['gettimetocharge'] = str(get_timetocharge(c, VEHICLE_VIN))
	return json.dumps(data)

@app.route('/api/chargestart')
def chargestart():
	c = establish_connection()
	data = {}
	data['result'] = str(charge_start(c, VEHICLE_VIN))
	return json.dumps(data)

@app.route('/api/chargestop')
def chargestop():
	c = establish_connection()
	data = {}
	data['result'] = str(charge_stop(c, VEHICLE_VIN))
	return json.dumps(data)

@app.route('/api/refresh')
def refresh():
	c = establish_connection()
	
	chargestatus = get_chargestatus(c, VEHICLE_VIN)
	
	data = {}
	data['iscarlocked'] = str(get_iscarlocked(c, VEHICLE_VIN))
	data['isclimateon'] = str(get_isclimateon(c, VEHICLE_VIN))
	data['isvehiclehome'] = str(get_isvehiclehome(c, VEHICLE_VIN))
	data['iscarcharging'] = str(chargestatus['charging_state'])
	data['getbatterylevel'] = str(chargestatus['battery_level'])
	data['getbatteryrange'] = str(chargestatus['battery_range'])
	data['gettimetocharge'] = str(chargestatus['time_to_full_charge'])
	
	return json.dumps(data)

