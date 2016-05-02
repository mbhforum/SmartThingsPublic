/**
 *  Copyright 2015 SmartThings
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 *
 * This app is intended to be used for Somfy blind installs where you want to synchronize multiple windows using a
 * master/replica setup. This smartapp must be used with a Device Handler that supports sync capabilities
 */
 
definition(
    name: "Blinds Sync",
    namespace: "mbhforum",
    author: "mbhforum",
    description: "Syncs blind status with other blinds",
    category: "My Apps",
    iconUrl: "http://cdn.device-icons.smartthings.com/alarm/beep/beep@2x.png",
    iconX2Url: "http://cdn.device-icons.smartthings.com/alarm/beep/beep@2x.png"
	)

preferences {
	page(name: "mainPage", title: "Blind Sync Setup", install: true, uninstall:true) {
    section("Blind Sync Setup" ) 
    {  
            input("MasterBlind", "capability.windowShade", title: "Pick the master blind", multiple: false)
            input("ReplicaBlind", "capability.windowShade", title: "Pick your replica blind(s)", multiple: true)            
	}
    }

}

def installed() {
    initialize()
}

def updated() {
    unsubscribe()
    initialize()
}

def initialize() {
subscribe(MasterBlind, "windowShade", shadestate)
}

def shadestate(evt) {
  if (evt.value == "open") {
    log.debug "Blinds are open!"
	ReplicaBlind.OpenSync()
 }
 else {
  if (evt.value == "closed") {
    log.debug "Blinds are closed!"
    ReplicaBlind.CloseSync()
  }
 else {
  if (evt.value == "partially open") {
    log.debug "Blinds are titled"
	ReplicaBlind.TiltSync() 
 }   
 
	  }
				}
         }    
         
def OpenSync() {
sendEvent (name:"windowShade", value: "open")
}

def CloseSync() {
sendEvent (name:"windowShade", value: "closed")
}

def TiltSync() {
sendEvent(name:"windowShade", value: "partially open")
}