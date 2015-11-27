/**
 *  Mail Has Arrive Push with minimum time requirements
 *
 *  Copyright 2014 Tim Slagle
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
 */
// Automatically generated. Make future change here.
definition(
    name: "Mail Has Arrived (Push) with minimum time requirement (contact))",
    namespace: "tslagle13",
    author: "Tim Slagle",
    description: "Mail Has Arrived with Push Notifications and frequency minumum times.",
    category: "My Apps",
	iconUrl: "https://s3.amazonaws.com/smartapp-icons/Meta/mail_contact.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Meta/mail_contact@2x.png"
)

preferences {
	section("When mail arrives...") {
		input "contactSensor", "capability.contactSensor", title: "Where?"
	}
    section("Notifications") {
        input "sendPushMessage", "enum", title: "Send a push notification?", metadata:[values:["Yes","No"]], required:false
  	}

    section("Minimum time between actions (defaults to every event)") {
        input "frequency", "decimal", title: "Minimum time between actions (defaults to every event)", description: "Minutes", required: false
  	}
    section("Settings") {
    	label title: "Assign a name", required: false
  	}
}

def installed() {
	subscribe(contactSensor, "contact.open",contactActiveHandler)
}

def updated() {
	unsubscribe()
	subscribe(contactSensor, "contact.open", contactActiveHandler)
}

def contactActiveHandler(evt) {
	log.trace "$evt.value: $evt, $settings"

	// Don't send a continuous stream of text messages
	def deltaSeconds = 5
	def timeAgo = new Date(now() - (1000 * deltaSeconds))
	def recentEvents = contactSensor.eventsSince(timeAgo)
	log.trace "Found ${recentEvents?.size() ?: 0} events in the last $deltaSeconds seconds"
	def alreadySentNotification = recentEvents.count { it.value && it.value == "active" } > 1

	if (alreadySentNotification) {
		log.debug "Notification already sent within the last $deltaSeconds seconds"
	} else {
		log.debug "$contactSensor has moved, notifying via push"
		send("Mail has arrived!")
	}
}

private send(msg) {
	if (sendPushMessage != "no"){
    	def lastTime = state[frequencyKey(evt)]
		if (lastTime == null || now() - lastTime >= frequency * 60000) {
			log.debug( "sending push message" )
			sendPush( msg )
			log.debug msg
            state[frequencyKey(evt)] = now()
        }    
    }
}

private frequencyKey(evt) {
	"lastActionTimeStamp"
}