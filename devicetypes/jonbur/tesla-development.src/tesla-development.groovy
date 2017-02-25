import groovy.json.JsonSlurper
metadata {
    definition (name: "Tesla-Development", namespace: "jonbur", author: "JB-MBH") {
        capability "Polling"
        capability "Refresh"
        capability "Switch"
        capability "presenceSensor"
        capability "Lock"
        capability "battery"
        capability "Temperature Measurement"
        capability "Thermostat Heating Setpoint"
        capability "Thermostat Cooling Setpoint"
        capability "Tone"
        capability "Momentary"
        capability "Thermostat Setpoint"
   
        command "refresh"
        command "chargestart"
        command "chargestop"
        command "SetpointUp"
        command "SetpointDown"
        command "flashlights"


        attribute "network", "string"
        attribute "batteryState", "string"
        attribute "batteryRange", "string"
        attribute "chargestart", "string"
        attribute "chargestop", "string"
        attribute "flashlights", "string"
        attribute "timetocharge", "string"
        attribute "temperature", "number"
    }

    preferences {
        input("ip", "text", title: "IP Address", description: "Local server IP address", required: true, displayDuringSetup: true)
        input("port", "number", title: "Port Number", description: "Port Number (Default:5000)", defaultValue: "5000", required: true, displayDuringSetup: true)
    }
    
    tiles (scale:2) {
        multiAttributeTile(name:"toggle", type: "generic", width: 6, height: 4){
            tileAttribute ("device.lock", key: "PRIMARY_CONTROL") {
            attributeState "locked", label:'locked', action:"lock.unlock", icon:"http://i66.tinypic.com/27ya2bb.png", backgroundColor:"#000000", nextState:"unlocking"
            attributeState "unlocked", label:'unlocked', action:"lock.lock", icon:"http://i63.tinypic.com/214aa0h.png", backgroundColor:"#000000", nextState:"locking"
            attributeState "unknown", label:"unknown", action:"lock.lock", icon:"st.locks.lock.unknown", backgroundColor:"#ffffff", nextState:"locking"
            attributeState "locking", label:'locking', icon:"http://i66.tinypic.com/27ya2bb.png", backgroundColor:"#000000"
            attributeState "unlocking", label:'unlocking', icon:"http://i63.tinypic.com/214aa0h.png", backgroundColor:"#000000"
            }
            tileAttribute("device.batteryState", key: "SECONDARY_CONTROL") {
            attributeState "Charging", label:'Charging Status: Charging'
            attributeState "Disconnected", label: 'Charging Status: Not Charging'
            attributeState "Complete", label: 'Charging Status: Complete'
            attributeState "Stopped", label: 'Charging Status: Stopped'
        	}
        }
        
		standardTile("switch", "device.switch", width: 2, height: 2, canChangeIcon: true) {
            state "on", label:'${name}', action:"switch.off", icon:"https://raw.githubusercontent.com/tonesto7/nest-manager/master/Images/App/heat_cool_icon.png", backgroundColor:"#79b821", nextState:"turningOff"
            state "off", label:'${name}', action:"switch.on", icon:"https://raw.githubusercontent.com/tonesto7/nest-manager/master/Images/App/heat_cool_icon.png", backgroundColor:"#ffffff", nextState:"turningOn"
            state "turningOn", label:'${name}', action:"switch.off", icon:"https://raw.githubusercontent.com/tonesto7/nest-manager/master/Images/App/heat_cool_icon.png", backgroundColor:"#79b821", nextState:"turningOff"
            state "turningOff", label:'${name}', action:"switch.on", icon:"https://raw.githubusercontent.com/tonesto7/nest-manager/master/Images/App/heat_cool_icon.png", backgroundColor:"#ffffff", nextState:"turningOn"
            state "offline", label:'${name}', icon:"https://raw.githubusercontent.com/tonesto7/nest-manager/master/Images/App/heat_cool_icon.png", backgroundColor:"#ff0000"
        }
		
        standardTile("DriverSide", "device.DriverSide", width: 1, height: 1, canChangeIcon: false, decoration: "flat") {
			state "default", label: '', icon:"http://i66.tinypic.com/95pjbd.png"
			state "", label: ''
		}
                
		standardTile("heatingSetpointUp", "device.heatingSetpoint", width: 1, height: 1, canChangeIcon: false, decoration: "flat") {
			state "default", label: '', action:"heatingSetpointUp", icon:"https://raw.githubusercontent.com/tonesto7/nest-manager/master/Images/Devices/heat_arrow_up.png"
			state "", label: ''}
               
        valueTile("drivertemp", "device.drivertemp", width: 1, height: 1, inactiveLabel: false) {
            state "drivertemp", label:'${currentValue}°', unit:units,
            backgroundColors:
            [
                // Celcius Color Range
   				[value: 7, color: "#1e9cbb"],
				[value: 15, color: "#90d2a7"],
				[value: 23, color: "#44b621"],
				[value: 29, color: "#f1d801"],
				[value: 33, color: "#d04e00"],
				[value: 36, color: "#bc2323"],
				// Fahrenheit Color Range
				[value: 40, color: "#153591"],
				[value: 44, color: "#1e9cbb"],
				[value: 59, color: "#90d2a7"],
				[value: 74, color: "#44b621"],
				[value: 84, color: "#f1d801"],
				[value: 92, color: "#d04e00"],
				[value: 96, color: "#bc2323"]
            ]
        }
        
		standardTile("heatingSetpointDown", "device.heatingSetpoint",  width: 1, height: 1, canChangeIcon: false, decoration: "flat") {
			state "default", label:'', action:"heatingSetpointDown", icon:"https://raw.githubusercontent.com/tonesto7/nest-manager/master/Images/Devices/cool_arrow_down.png"
			state "", label: ''
		}
        
        standardTile("PassengerSide", "device.PassengerSide", width: 1, height: 1, canChangeIcon: false, decoration: "flat") {
			state "default", label: '', icon:"http://i67.tinypic.com/2lm04lj.png"
			state "", label: ''
		}
        
 
       	standardTile("coolingSetpointUp", "device.coolingSetpoint", width: 1, height: 1,canChangeIcon: false, decoration: "flat") {
			state "default", label:'', action:"coolingSetpointUp", icon:"https://raw.githubusercontent.com/tonesto7/nest-manager/master/Images/Devices/heat_arrow_up.png"
			state "", label: ''
		}
        
        valueTile("passtemp", "device.passtemp", width: 1, height: 1, inactiveLabel: false) {
            state "passtemp", label:'${currentValue}°', unit:units,
            backgroundColors:
            [
                // Celcius Color Range
   				[value: 7, color: "#1e9cbb"],
				[value: 15, color: "#90d2a7"],
				[value: 23, color: "#44b621"],
				[value: 29, color: "#f1d801"],
				[value: 33, color: "#d04e00"],
				[value: 36, color: "#bc2323"],
				// Fahrenheit Color Range
				[value: 40, color: "#153591"],
				[value: 44, color: "#1e9cbb"],
				[value: 59, color: "#90d2a7"],
				[value: 74, color: "#44b621"],
				[value: 84, color: "#f1d801"],
				[value: 92, color: "#d04e00"],
				[value: 96, color: "#bc2323"]
            ]
        }
		standardTile("coolingSetpointDown", "device.coolingSetpoint", width: 1, height: 1, canChangeIcon: false, decoration: "flat") {
			state "default", label:'', action:"coolingSetpointDown", icon:"https://raw.githubusercontent.com/tonesto7/nest-manager/master/Images/Devices/cool_arrow_down.png"
			state "", label: ''
		}
       
        valueTile("temperature", "device.temperature", width: 2, height: 2, inactiveLabel: false) {
            state "temperature", label:'${currentValue}°', unit:units,
            backgroundColors:
            [
                // Celcius Color Range
   				[value: 7, color: "#1e9cbb"],
				[value: 15, color: "#90d2a7"],
				[value: 23, color: "#44b621"],
				[value: 29, color: "#f1d801"],
				[value: 33, color: "#d04e00"],
				[value: 36, color: "#bc2323"],
				// Fahrenheit Color Range
				[value: 40, color: "#153591"],
				[value: 44, color: "#1e9cbb"],
				[value: 59, color: "#90d2a7"],
				[value: 74, color: "#44b621"],
				[value: 84, color: "#f1d801"],
				[value: 92, color: "#d04e00"],
				[value: 96, color: "#bc2323"]
            ]
        }
       
       valueTile("battery", "device.battery", width: 2, height: 1, inactiveLabel: false, decoration: "flat") {
        	state "battery", label:'Battery: ${currentValue}%', unit:""
		}

        valueTile("batteryRange", "device.batteryRange", width: 2, height: 1, inactiveLabel: false, decoration: "flat") {
        	state "batteryRange", label:'Estimated Mileage Range: ${currentValue}', unit:""
        }    

        standardTile("chargestart", "device.chargestart", width:1, height:1, decoration: "flat") {
        	state "chargestart", action: "chargestart",	icon: "http://i67.tinypic.com/52zyo7.png"}
            

        standardTile("chargestop", "device.chargestop", width:1, height:1, decoration: "flat") {
        	state "chargestop", action: "chargestop",icon: "http://i.imgur.com/LHYiTzK.png"}

        valueTile("timetocharge", "device.timetocharge", width: 2, height: 1, inactiveLabel: false, decoration: "flat") {
        	state "timetocharge", label:'Hours to reach full battery: ${currentValue}', unit:""
        }

        standardTile("presence", "device.presence", width: 3, height: 2, canChangeBackground: true) {
            state("present", labelIcon:"st.presence.tile.mobile-present", backgroundColor:"#53a7c0")
            state("not present", labelIcon:"st.presence.tile.mobile-not-present", backgroundColor:"#ebeef2")
        }
            
        standardTile("carstart", "device.carstart", width:1, height:1, decoration: "flat") {
        	state "push", action:"momentary.push",icon: "http://i.imgur.com/ZMum6n6.png"}
        
        standardTile("honkhorn", "device.honkhorn", width:1, height:1, decoration: "flat") {
        	state "beep", action: "tone.beep",icon: "http://i.imgur.com/XgV3yge.jpg"}
        
        standardTile("flashlights", "device.flashlights", width:1, height:1, decoration: "flat") {
        	state "flashlights", action: "flashlights",icon: "http://i.imgur.com/YsKVv12.jpg"}
        
        standardTile("refresh", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"", action:"refresh.refresh", icon:"st.secondary.refresh"
        }
   }
   }

def parse(String description) {
    def map
    def headerString
    def bodyString
    def slurper
    def result
    
    map = stringToMap(description)
    headerString = new String(map.headers.decodeBase64())
    
    if (headerString.contains("200 OK")) {
    	
        bodyString = new String(map.body.decodeBase64())
        slurper = new JsonSlurper()
        result = slurper.parseText(bodyString)
        
   		log.debug result

        switch (result.isclimateon) {
            case "False":
                sendEvent(name: 'switch', value: "off" as String)
            break;
            case "True":
                sendEvent(name: 'switch', value: "on" as String)
            }

        switch (result.iscarlocked) {
            case "False":
                log.debug 'Vehicle is unlocked'
                sendEvent(name: 'lock', value: "unlocked" as String)
                break;
            case "True":
                log.debug 'Vehicle is locked'
                sendEvent(name: 'lock', value: "locked" as String)
            }

        switch (result.isvehiclehome) {
            case "False":
                log.debug 'Vehicle is away'
                away()
            break;
            case "True":
                log.debug 'Vehicle is home'
                present()
            }
			             
            def tempScale = result.getttempunits
            def temp = result.insidetemp.toBigDecimal()
            def curTemp = cToF(temp)
            sendEvent(name:"temperature", value:curTemp as Integer)     
                 
            sendEvent(name:"battery", value:result.getbatterylevel)
            sendEvent(name:"batteryRange", value:result.getbatteryrange)
            sendEvent(name:"timetocharge", value:result.gettimetocharge)
            
            def drivertemp2 = result.drivertemp.toBigDecimal()
        	def curdriverTemp = cToF(drivertemp2)
            sendEvent(name:"drivertemp", value:curdriverTemp as Integer)
           
            def passtemp3 = result.passtemp.toBigDecimal()
        	def curpassTemp = cToF(passtemp3)
            sendEvent(name:"passtemp", value:curpassTemp as Integer)
            
        switch (result.iscarcharging) {
            case "Disconnected":
                log.debug 'Vehicle is not charging'
                sendEvent(name: 'batteryState', value: "Disconnected")
            break;
            case "Charging":
                log.debug 'Vehicle is charging'
                sendEvent(name: 'batteryState', value: "Charging")
            break;
            case "Complete":
                log.debug 'Charging is complete'
                sendEvent(name: 'batteryState', value: "Complete")
            break;
            case "Stopped":
                log.debug 'Charging is stopped'
                sendEvent(name: 'batteryState', value: "Stopped") 
            }
    }
    else {
        sendEvent(name: 'status', value: "error" as String)
        log.debug headerString
    }
}

// handle commands

def installed() {
	log.debug "Installed with settings: ${settings}"
    initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"
    initialize()
}

def initialize() {
	log.info "Tesla ${textVersion()} ${textCopyright()}"
	ipSetup()
    poll()
}

def on() {
	log.debug "Executing 'on'"
	ipSetup()
	api('on')
}

def off() {
	log.debug "Executing 'off'"
	ipSetup()
	api('off')
}

def away() {
	log.debug('not present')
	sendEvent(name: 'presence', value: 'not present')
}

def present() {
	log.debug('present')
	sendEvent(name: 'presence', value: 'present')
}

def unlock() {
	log.debug "Executing Unlock"
	ipSetup()
	api('unlock')
}

def lock() {
	log.debug "Executing Lock"
	ipSetup()
	api('lock')
}

def beep() {
	log.debug "Honking Horn"
	ipSetup()
	api('beep')
}

def flashlights() {
	log.debug "Flash Lights"
	ipSetup()
	api('flashlights')
}

def push() {
	log.debug "Keyless Start Enabled"
    ipSetup()
    api('startcar')
}

def insidetemp() {
	log.debug "Executing Inside Temperature Query"
    ipSetup()
    api('insidetemp')
}

def drivertemp() {
	log.debug "Executing Driver Temperature Query"
    ipSetup()
    api('drivertemp')
}

def passtemp() {
	log.debug "Executing Passenger Temperature Query"
    ipSetup()
    api('passtemp')
}

def getbattery() {
	log.debug "Executing Battery Level Query"
	ipSetup()
	api('getbattery')
}

def getbatteryrange() {
	log.debug "Executing Battery Range Query"
	ipSetup()
	api('getbatteryrange')
}

def getcharging() {
	log.debug "Executing Charging Query"
	ipSetup()
	api('getcharging')
}

def chargestart() {
	log.debug "Sending Charge Start Command"
	ipSetup()
	api('chargestart')
}

def chargestop() {
	log.debug "Sending Charge Stop Command"
	ipSetup()
	api('chargestop')
}

def gettimetocharge() {
	log.debug "Executing Time to Charge Query"
	ipSetup()
	api('gettimetocharge')
}

def gettempscale() {
	log.debug "Executing Temperature Scale Query"
    api ('gettempscale')
}

def poll() {
	log.debug "Executing 'poll'"
    if (device.deviceNetworkId != null) {
		refresh()
	}
	else {
        sendEvent(name: 'status', value: "error" as String)
		sendEvent(name: 'network', value: "Not Connected" as String)
        log.debug "DNI: Not set"
	}
}

def refresh() {
	log.debug "Executing 'refresh'"
	ipSetup()
    api('refresh')
}

def api(String APICommand, success = {}) {
def APIPath
def hubAction

switch (APICommand) {
	case "on":
		APIPath = "/api/starthvac"
		log.debug "The start command was sent"
		break;
	case "off":
		APIPath = "/api/stophvac"
		log.debug "The stop command was sent"
		break;
	case "isclimateon":
		APIPath = "/api/isclimateon"
		log.debug "Request if climate is on sent"
		break;	
	case "ishome":
		APIPath = "/api/isvehiclehome"
		log.debug "Request if vehicle is home sent"
		break;
	case "unlock":
		APIPath = "/api/doorunlock"
		log.debug "Unlocking Door"
		break;
	case "lock":
		APIPath = "/api/doorlock"
		log.debug "Locking door"
		break;
	case "beep":
		APIPath = "/api/honkhorn"
		log.debug "Honking Horn"
	break;
    case "flashlights":
		APIPath = "/api/flashlights"
		log.debug "Flash Lights"
	break;
    case "startcar":
		APIPath = "/api/startcar"
		log.debug "Keyless Start enabled"
	break;
    case "islocked":
		APIPath = "/api/iscarlocked"
		log.debug "Request if car is locked sent"
		break;
	case "insidetemp":
    	APIPath =  "/api/insidetemp"
        log.debug "Request inside temperature"
        break;
    case "drivertemp":
    	APIPath = "/api/drivertemp"
        log.debug "Request driver temperature"
        break;
    case "passtemp":
    	APIPath = "/api/passtemp"
        log.debug "Request passenger temperature"
    	break;  
    case "getbattery":
		APIPath = "/api/getbatterylevel"
		log.debug "Request battery level"
		break;
	case "getcharging":
		APIPath = "/api/iscarcharging"
		log.debug "Request Charging Info"
		break;
	case "getbatteryrange":
		APIPath = "/api/getbatteryrange"
		log.debug "Request battery range"
		break;
	case "chargestart":
		APIPath = "/api/chargestart"
		log.debug "Sending Charge Start Command to Vehicle"
		break;
	case "chargestop":
		APIPath = "/api/chargestop"
		log.debug "Sending Charge Stop Command to Vehicle"
		break;
	case "gettimetocharge":
		APIPath = "/api/gettimetocharge"
		log.debug "Getting Time to Charge"
	break;
	case "gettempscale":
    	APIPath = "/api/api/gettempunit"
        log.debug "Getting Temperature Unit"
    break;
    case "refresh":
		APIPath = "/api/refresh"
        log.debug "request Refresh"
    break;
}

switch (APICommand) {
	case ["isclimateon", "ishome", "islocked", "insidetemp", "getbattery", "getbatteryrange", "getcharging", "gettimetocharge", "drivertemp", "passtemp", "gettempscale", "refresh"]:
		log.debug APICommand
		try {
			hubAction = new physicalgraph.device.HubAction(
			method: "GET",
			path: APIPath,
			headers: [HOST: "${settings.ip}:${settings.port}", Accept: "application/json"])
		}
		catch (Exception e) {
			log.debug "Hit Exception $e on $hubAction"
		}
		break;
	default:
		try {
			hubAction = [new physicalgraph.device.HubAction(
			method: "GET",
			path: APIPath,
			headers: [HOST: "${settings.ip}:${settings.port}", Accept: "application/json"]
		), delayAction(1000), refresh()]
		}
		catch (Exception e) {
			log.debug "Hit Exception $e on $hubAction"
		}
		break;
	}
return hubAction
}

def ipSetup() {
	def hosthex
	def porthex
	if (settings.ip) {
		hosthex = convertIPtoHex(settings.ip)
	}
	if (settings.port) {
		porthex = convertPortToHex(settings.port)
	}
	if (settings.ip && settings.port) {
		device.deviceNetworkId = "$hosthex:$porthex"
	}
}

def units = ""   
def cToF(temp){
      if (tempScale == "C"){
      return temp
      }
      else{
        return temp * 1.8 + 32
    }
} 

private String convertIPtoHex(ip) { 
	String hexip = ip.tokenize( '.' ).collect { String.format( '%02x', it.toInteger() ) }.join()
	return hexip
}
private String convertPortToHex(port) {
	String hexport = port.toString().format( '%04x', port.toInteger() )
	return hexport
}
private delayAction(long time) {
	new physicalgraph.device.HubAction("delay $time")
}

private def textVersion() {
	def text = "Version 0.2"
}

private def textCopyright() {
	def text = "Copyright © 2017 JB-MBH"
}