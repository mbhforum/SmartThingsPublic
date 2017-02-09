from flask import Flask
import os
import teslajson
from geopy.distance import vincenty
import json
from config import *

TeslaConnection = ""

def establish_connection():
	global TeslaConnection
	if isinstance(TeslaConnection, teslajson.Connection):
		print "Existing connection"
		return TeslaConnection
	else:
		c = teslajson.Connection(email=TESLA_EMAIL, password=TESLA_PASSWORD)
		TeslaConnection = c
		print "Connected again"
		return c

def get_climate(c, car):
	climate = None
	for v in c.vehicles:
		if v["vin"] == car:
			climate = v.data_request("climate_state")
	return climate

def get_lockstatus(c, car):
	lockstatus = None
	for v in c.vehicles:
		if v["vin"] == car:
			lockstatus = v.data_request("vehicle_state")
	return lockstatus

def get_guipreferences(c, car):
	guipreferences = None
	for v in c.vehicles:
		if v["vin"] == car:
			guipreferences = v.data_request("gui_settings")
	return guipreferences
	
def get_guirangedisplay(c, car):
	return get_guipreferences(c, car)['gui_range_display']

def get_guitemperature_units(c, car):
	return get_guipreferences(c, car)['gui_temperature_units']

def get_isclimateon(c, car):
	return get_climate(c, car)['is_climate_on']

def get_insidetemp(c, car):
	return get_climate(c, car)['inside_temp']

def get_drivertemp(c, car):
	return get_climate(c, car)['driver_temp_setting']
	
def get_passtemp(c, car):
	return get_climate(c, car)['passenger_temp_setting']

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

def honk_horn(c, car):
	for v in c.vehicles:
		if v["vin"] == car:
			result = v.command('honk_horn')['response']['result']
	return result

def flash_lights(c, car):
	for v in c.vehicles:
		if v["vin"] == car:
			result = v.command('flash_lights')['response']['result']
	return result

def start_car(c, car):
	for v in c.vehicles:
		if v["vin"] == car:
			result = v.command('remote_start_drive?password=' + TESLA_PASSWORD)['response']['result']
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
	if (get_guirangedisplay(c, car) == "Ideal"):
		return get_chargestatus(c, car)['ideal_battery_range']
	else:
		return get_chargestatus(c, car)['est_battery_range']

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
	
@app.route('/api/getrangedisplay')
def getrangedisplay():
	c = establish_connection()
	return str(get_guirangedisplay(c, VEHICLE_VIN))

@app.route('/api/isclimateon')
def isclimateon():
	c = establish_connection()
	data = {}
	data['isclimateon'] = str(get_isclimateon(c, VEHICLE_VIN))
	return json.dumps(data)

@app.route('/api/insidetemp')
def insidetemp():
	c = establish_connection()
	data = {}
	data['insidetemp'] = str(get_insidetemp(c, VEHICLE_VIN))
	return json.dumps(data)

@app.route('/api/drivertemp')
def drivertemp():
	c = establish_connection()
	data = {}
	data['drivertemp'] = str(get_drivertemp(c, VEHICLE_VIN))
	return json.dumps(data)

@app.route('/api/passtemp')
def passtemp():
	c = establish_connection()
	data = {}
	data['passtemp'] = str(get_passtemp(c, VEHICLE_VIN))
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

@app.route('/api/honkhorn')
def honkhorn():
	c = establish_connection()
	data = {}
	data['result'] = str(honk_horn(c, VEHICLE_VIN))
	return json.dumps(data)

@app.route('/api/flashlights')
def flashlights():
	c = establish_connection()
	data = {}
	data['result'] = str(flash_lights(c, VEHICLE_VIN))
	return json.dumps(data)

@app.route('/api/startcar')
def startcar():
	c = establish_connection()
	data = {}
	data['result'] = str(start_car(c, VEHICLE_VIN))
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

@app.route('/api/gettempunit')
def gettempunit():
	c = establish_connection()
	data = {}
	data['gettempunit'] = str(get_guitemperature_units(c, VEHICLE_VIN))
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
	climatestatus = get_climate(c, VEHICLE_VIN)
	guipreferences = get_guipreferences(c, VEHICLE_VIN)
	
	data = {}
	data['iscarlocked'] = str(get_iscarlocked(c, VEHICLE_VIN))
	data['isvehiclehome'] = str(get_isvehiclehome(c, VEHICLE_VIN))
	data['isclimateon'] = str(climatestatus['is_climate_on'])
	data['insidetemp'] = str(climatestatus['inside_temp'])
	data['drivertemp'] = str(climatestatus['driver_temp_setting'])
	data['passtemp'] = str(climatestatus['passenger_temp_setting'])
	data['iscarcharging'] = str(chargestatus['charging_state'])
	data['getbatterylevel'] = str(chargestatus['battery_level'])
	data['getbatteryrange'] = str(chargestatus['ideal_battery_range'])
	data['gettimetocharge'] = str(chargestatus['time_to_full_charge'])
	
	if (get_guirangedisplay(c, VEHICLE_VIN) == "Ideal"):
		data['getbatteryrange'] = str(chargestatus['ideal_battery_range'])
	else:
		data['getbatteryrange'] = str(chargestatus['est_battery_range'])
	
	data['getttempunits'] = str(guipreferences['gui_temperature_units'])
	
	return json.dumps(data)
	
app.run(host="0.0.0.0", threaded=True)