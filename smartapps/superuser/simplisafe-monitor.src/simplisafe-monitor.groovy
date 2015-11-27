/**
 *  SimpliSafe Monitor
 *
 *  Author: toby@cth3.com
 *  Date: 4/15/15
 *
 *  Monitors the state of the SimpliSafe alarm system and turns on/off a virtual tile.
 *  Works in conjunction with SimpliSafe integration for SmartThings by Felix Gorodishter.
 */


// Automatically generated. Make future change here.
definition(
    name: "SimpliSafe Monitor",
    namespace: "",
    author: "toby@cth3.com",
    description: "Monitors the state of the SimpliSafe alarm system and turns on/off a virtual tile. Works in conjunction with SimpliSafe integration for SmartThings by Felix Gorodishter.",
    category: "Safety & Security",
    iconUrl: "http://cdn.device-icons.smartthings.com/security/alarm/alarm.png",
    iconX2Url: "http://cdn.device-icons.smartthings.com/security/alarm/alarm@2x.png")

preferences {
  section("Monitor this SimpliSafe alarm system") {
    input "alarmsystem", "capability.alarm", title: "Select alarm system"
  }
  
  section("Control this virtual tile") {
	input "alarmtile", "capability.switch", title: "Select virtual tile"
  } 
  
  section("Set virtual tile to on when alarm mode matches") {
    input "alarmon", "enum", title: "Select on state", multiple: true, metadata:[values:["off", "away", "home"]]
  }
  
  section("Set virtual tile to off when alarm mode matches") {
    input "alarmoff", "enum", title: "Select off state", multiple: true, metadata:[values:["off", "away", "home"]]
  }
   
  section("Notifications") {
    input "sendPushMessage", "enum", title: "Send a push notification?", metadata:[values:["Yes","No"]], required:false
   }
  }

def installed() {
  init()
  }

def updated() {
  unsubscribe()
  unschedule()
  init()
  }
  
def init() {
  subscribe(alarmsystem, "alarm", alarmmode)
  }

def alarmmode(evt) {
state.alarmstate = evt.value
  if (evt.value in alarmon) {
    log.debug("Alarm state: $state.alarmstate")
     alarmstateon()
  }
 else {
 if (evt.value in alarmoff) {
    log.debug("Alarm state: $state.alarmstate")
     alarmstateoff()
  }
  else {
    log.debug("No actions set for alarm state '${state.alarmstate}' - aborting")
    }
   }  
  }

def alarmstateon() {
      def message = "${app.label} changed to on because alarm changed to '${state.alarmstate}'"
      log.info(message)
      send(message)
      settings.alarmtile.on()
  }
  
def alarmstateoff() {
      def message = "${app.label} changed to off because alarm changed to '${state.alarmstate}'"
      log.info(message)
      send(message)
      settings.alarmtile.off()

  } 
  
private send(msg) {
  if(sendPushMessage != "No") {
    log.debug("Sending push message")
    sendPush(msg)
   }
  log.debug(msg)
  }