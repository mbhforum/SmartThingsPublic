/**
 *  My Automatic Device
 *
 *  Copyright 2015 Yves Racine
 *  Version 2.2.6
 *  LinkedIn profile: ca.linkedin.com/pub/yves-racine-m-sc-a/0/406/4b/
 *  Refer to readme file for installation instructions.
 *     https://github.com/yracine/device-type.myautomatic/blob/master/README.md
 *
 *  Code: https://github.com/yracine
 *
 *  Developer retains all right, title, copyright, and interest, including all copyright, patent rights,
 *  trade secret in the Background technology. May be subject to consulting fees under an Agreement 
 *  between the Developer and the Customer. Developer grants a non exclusive perpetual license to use
 *  the Background technology in the Software developed for and delivered to Customer under this
 *  Agreement. However, the Customer shall make no commercial use of the Background technology without
 *  Developer's written consent.
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * 
 * Software Distribution is restricted and shall be done only with Developer's written approval.
*/

import java.text.SimpleDateFormat


// for the UI
preferences {
	input("vehicleId", "text", title: "vehicle [default=first Automatic vehicle found", description:
		"optional, the id of your Automatic connected vehicle (no spaces)")
	input("homeAddress", "text", title: "minimum Home Address info for presence settings", 
		description: "optional, ex. postal/zip code or street name")
	input("localFuelCostPerVolUnit", "decimal", title: "fuel cost per gallon/liter in local currency", 
		description: "optional, default is Automatic fuel cost in USD")
	input("trace", "bool", title: "trace", description:
		"Set it to true to enable tracing (no spaces) or leave it empty (no tracing)")
    
}


metadata {
	definition (name: "My Automatic Device", namespace: "yracine", author: "Yves Racine") {
		capability "Refresh"
		capability "Polling"
		capability "Presence Sensor"
		capability "Switch"        

		command "getCurrentUserInfo"
		command "getTrips"
		command "getVehicles"
		command "generateVehicleEvents"
		command "generateVehicleRTEvents"        
		command "generateYesterdayTripStats"
		command "generateWeeklyTripStats"
		command "generateMonthlyTripStats"
        
		attribute "verboseTrace","string"
		
		attribute "userid","string"
		attribute "username", "string"
		attribute "email","string"
		attribute "firstName", "string"
		attribute "lastName", "string"
		attribute "vehicleId", "string"
		attribute "make","string"
		attribute "model", "string"
		attribute "submodel", "string"
		attribute "color","string"
		attribute "displayName", "string"
		attribute "year","string"

		attribute "locationId","string"
		attribute "timezone","string"

		attribute "tripsList","string"
		attribute "tripsData","string"


		attribute "tripsAvgAverageKmpl","string"
		attribute "tripsAvgDistanceM","string"
		attribute "tripsAvgDurationS","string"
		attribute "totalDistanceM","string"
		attribute "tripsAvgCostUsd", "string"
		attribute "tripsAvgFuelVolumeL","string"
		attribute "totalFuelVolumeL","string"
		attribute "totalCostUsd", "string"
		attribute "totalDurationS", "string"
		attribute "totalNbTrips", "string"
		attribute "totalHardAccels", "string"
		attribute "totalHardBrakes", "string"
		attribute "tripsAvgScoreSpeeding", "string"
		attribute "tripsAvgScoreEvents", "string"


		attribute "tripsAvgAverageKmplInPeriod","string"
		attribute "tripsAvgDistanceMInPeriod","string"
		attribute "tripsAvgDurationSInPeriod","string"
		attribute "totalDistanceMInPeriod","string"
		attribute "tripsAvgCostUsdInPeriod", "string"
		attribute "tripsAvgFuelVolumeLInPeriod","string"
		attribute "totalFuelVolumeLInPeriod","string"
		attribute "totalCostUsdInPeriod", "string"
		attribute "totalDurationSInPeriod", "string"
		attribute "totalAverageKmplInPeriod", "string"
		attribute "totalNbTripsInPeriod", "string"
		attribute "totalHardAccelsInPeriod", "string"
		attribute "totalHardBrakesInPeriod", "string"
		attribute "totalScoreSpeedingInPeriod", "string"
		attribute "totalScoreEventsInPeriod", "string"
		attribute "tripsAvgScoreSpeedingInPeriod", "string"
		attribute "tripsAvgScoreEventsInPeriod", "string"
		

		/* Stats for yesterday */
  
		attribute "yesterdayTripsAvgAverageKmpl","string"
		attribute "yesterdayTripsAvgDistanceM","string"
		attribute "yesterdayTripsAvgDurationS","string"
		attribute "yesterdayTotalDistanceM","string"
		attribute "yesterdayTripsAvgCostUsd", "string"
		attribute "yesterdayTripsAvgFuelVolumeL","string"
		attribute "yesterdayTotalFuelVolumeL","string"
		attribute "yesterdayTotalCostUsd", "string"
		attribute "yesterdayTotalDurationS", "string"
		attribute "yesterdayTotalNbTrips", "string"
		attribute "yesterdayTotalHardAccels", "string"
		attribute "yesterdayTotalHardBrakes", "string"
		attribute "yesterdayTripsAvgScoreSpeeding", "string"
		attribute "yesterdayTripsAvgScoreEvents", "string"

		/* Stats for the last 7 days (week) */
        
		attribute "weeklyTripsAvgAverageKmpl","string"
		attribute "weeklyTripsAvgDistanceM","string"
		attribute "weeklyTripsAvgDurationS","string"
		attribute "weeklyTotalDistanceM","string"
		attribute "weeklyTripsAvgCostUsd", "string"
		attribute "weeklyTripsAvgFuelVolumeL","string"
		attribute "weeklyTotalFuelVolumeL","string"
		attribute "weeklyTotalCostUsd", "string"
		attribute "weeklyTotatDurationS", "string"
		attribute "weeklyTotalNbTrips", "string"
		attribute "weeklyTotalHardAccels", "string"
		attribute "weeklyTotalHardBrakes", "string"
		attribute "weeklyTripsAvgScoreSpeeding", "string"
		attribute "weeklyTripsAvgScoreEvents", "string"


		/* Stats for the last 30 days (month) */
        
		attribute "monthlyTripsAvgAverageKmpl","string"
		attribute "monthlyTripsAvgDistanceM","string"
		attribute "monthlyTripsAvgDurationS","string"
		attribute "monthlyTotalDistanceM","string"
		attribute "monthlyTripsAvgCostUsd", "string"
		attribute "monthlyTripsAvgFuelVolumeL","string"
		attribute "monthlyTotalFuelVolumeL","string"
		attribute "monthlyTotalCostUsd", "string"
		attribute "monthlyTotalDurationS", "string"
		attribute "monthlyTotalNbTrips", "string"
		attribute "monthlyTotalHardAccels", "string"
		attribute "monthlyTotalHardBrakes", "string"
		attribute "monthlyTripsAvgScoreSpeeding", "string"
		attribute "monthlyTripsAvgScoreEvents", "string"

		attribute "eventVehicleId", "string"    
		attribute "eventType", "string"    
		attribute "eventTripId", "string"    
		attribute "eventTripDistanceM", "string"
		attribute "eventTripAvgFuelMPG", "string"
		attribute "eventTripLocationAccuracyM", "string"
		attribute "eventTripStartLocation", "string"
		attribute "eventTripEndLocation","string"
		attribute "eventTripStartAddress", "string"
		attribute "eventTripEndAddress","string"
		attribute "eventTripSpeedKPH","string"
		attribute "eventTripGForce","string"
		attribute "eventTripRegionStatus", "string"
		attribute "eventTripRegionName", "string"
		attribute "eventTripRegionTag","string"
		attribute "eventTripCode","string"
		attribute "eventTripDescription","string"
		attribute "eventTripLocationLat", "string"
		attribute "eventTripLocationLon", "string"
		attribute "eventTripStartedAt", "string"
		attribute "eventTripEndedAt", "string"
		attribute "eventTripCreatedAt", "string"
		attribute "eventStartTimezone", "string"
		attribute "eventEndTimezone", "string"

}

	simulator {
		// TODO: define status and reply messages here
	}

	// UI tile definitions
	tiles(scale: 2) {
		multiAttributeTile(name:"main_multi", type: "generic", width: 6, height: 4, canChangeIcon: true) {
			tileAttribute ("device.totalDistanceM", key: "PRIMARY_CONTROL") {            		
				attributeState(	"totalDistanceM", label:'dist. ${currentValue}',
					backgroundColor: "#53a7c0", icon: "st.Transportation.transportation2"
				)
			}
			tileAttribute(	"device.presence",  key: "SECONDARY_CONTROL") {
				attributeState("default", label:'vehicle ${currentValue} at home')
			}
            
		}
		standardTile("present", "device.presence", inactiveLabel: false, height:2, width:2, canChangeIcon: false) {
			state "not present", label:'${name}', backgroundColor: "#ffffff", icon:"st.presence.tile.presence-default" 
			state "present", label:'${name}', backgroundColor: "#53a7c0", icon:"st.presence.tile.presence-default" 
		}	
		standardTile("switch", "device.switch", width: 2, height: 2, canChangeIcon: true) {
			state "off", label: '${name}', action: "switch.on", icon: "st.switches.switch.off", backgroundColor: "#ffffff"
			state "on", label: '${name}', action: "switch.off", icon: "st.switches.switch.on", backgroundColor: "#79b821"
		}
          
		valueTile(	"make", "device.make",width: 2, height: 2,canChangeIcon: false,
  					decoration: "flat"
				)
        	{
			state("default",
					label:'Make\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"model", "device.model",width: 2, height: 2,canChangeIcon: false,
  					decoration: "flat"
				)
        	{
			state("default",
					label:'Model\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"submodel", "device.submodel",width: 2, height: 2,canChangeIcon: false,
  					decoration: "flat"
				)
        	{
			state("default",
					label:'Submodel\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"year", "device.year",width: 2, height: 2,canChangeIcon: false,
  					decoration: "flat"
				)
        	{
			state("default",
					label:'Year\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
/*        
		valueTile(	"color", "device.color",width: 1, height: 1,canChangeIcon: false,
  					decoration: "flat"
				)
        	{
			state("default",
					label:'Color\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"tripsAvgAverageKmpl", "device.tripsAvgAverageKmpl",width: 1, height: 1,canChangeIcon: false,
 					decoration: "flat"
				) 
        	{
			state("default",
					label:'Avg\nConsmption\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
*/        
		valueTile(	"totalNbTrips", "device.totalNbTrips",width: 2, height: 2,canChangeIcon: false,
 					decoration: "flat"
				) 
        	{
			state("default",
					label:'Today Trip Count\n${currentValue}',
					backgroundColor: "#ffffff"
			)
		}
		valueTile(	"tripsAvgAverageKmpl", "device.tripsAvgAverageKmpl",width: 2, height:2,canChangeIcon: false,
 					decoration: "flat"
				) 
        	{
			state("default",
					label:'Avg\nConsmption\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"tripsAvgDistanceM", "device.tripsAvgDistanceM",width: 2, height: 2,canChangeIcon: false,
 					decoration: "flat"
				) 
        	{
			state("default",
					label:'Avg dist/trip\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"tripsAvgDurationS", "device.tripsAvgDurationS", unit:'min.',width: 2, height: 2,canChangeIcon: false,
  					decoration: "flat"
				)
        	{
			state("default",
					label:'Avg time/trip\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"totalDistanceM", "device.totalDistanceM",width: 2, height: 2,canChangeIcon: false,
  					decoration: "flat",
				)
        	{
			state("default",
					label:'Total Distance\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"totalFuelVolumeL", "device.totalFuelVolumeL",width: 2, height: 2,canChangeIcon: false,
  					decoration: "flat",
				)
        	{
			state("default",
					label:'Fuel Volume\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"totalCostFuel", "device.totalCostUsd",width: 2, height: 2,canChangeIcon: false,
  					decoration: "flat",
				)
        	{
			state("default",
					label:'Fuel Cost\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"tripsAvgScoreSpeeding", "device.tripsAvgScoreSpeeding",width: 2, height: 2,canChangeIcon: false,
  					decoration: "flat",
				)
        	{
			state("default",
					label:'Avg Score Speed\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"tripsAvgScoreEvents", "device.tripsAvgScoreEvents",width: 2, height: 2,canChangeIcon: false,
  					decoration: "flat",
				)
        	{
			state("default",
					label:'Avg Score Events\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
        
		// yesterday tiles

		valueTile(	"yestripsAvgAverageKmpl", "device.yesterdayTripsAvgAverageKmpl",width: 2, height: 2,canChangeIcon: false,
 					decoration: "flat"
				) 
        	{
			state("default",
					label:'Yesterday Avg Cons\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"yestripsAvgDistanceM", "device.yesterdayTripsAvgDistanceM",width: 2, height: 2,canChangeIcon: false,
 					decoration: "flat"
				) 
        	{
			state("default",
					label:'Yesterday Avg dist/trip\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"yestripsAvgDurationS", "device.yesterdayTripsAvgDurationS", unit:'min.',width: 2, height: 2,canChangeIcon: false,
  					decoration: "flat"
				)
        	{
			state("default",
					label:'Yesterday Avg time/trip\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"yestotalDistanceM", "device.yesterdayTotalDistanceM",width: 2, height: 2,canChangeIcon: false,
  					decoration: "flat",
				)
        	{
			state("default",
					label:'Yesterday Total Dist.\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}

		valueTile(	"yestotalFuelVolumeL", "device.yesterdayTotalFuelVolumeL",width: 2, height: 2,canChangeIcon: false,
  					decoration: "flat",
				)
        	{
			state("default",
					label:'Yesterday Fuel Vol.\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"yestotalCostFuel", "device.yesterdayTotalCostUsd",width: 2, height: 2,canChangeIcon: false,
  					decoration: "flat",
				)
        	{
			state("default",
					label:'Yesterday Fuel Cost\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"yestotalNbTrips", "device.yesterdayTotalNbTrips",width: 2, height: 2,canChangeIcon: false,
  					decoration: "flat",
				)
        	{
			state("default",
					label:'Yesterday Trip Count\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"yestripsAvgScoreSpeeding", "device.yesterdayTripsAvgScoreSpeeding",width: 2, height: 2,canChangeIcon: false,
  					decoration: "flat",
				)
        	{
			state("default",
					label:'Yesterday Avg Score Speed\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"yestripsAvgScoreEvents", "device.yesterdayTripsAvgScoreEvents",width: 2, height: 2,canChangeIcon: false,
  					decoration: "flat",
				)
        	{
			state("default",
					label:'Yesterday Avg Score Events\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
		// weekly tiles

		valueTile(	"weektripsAvgAverageKmpl", "device.weeklyTripsAvgAverageKmpl",width: 2, height: 2,canChangeIcon: false,
 					decoration: "flat"
				) 
        	{
			state("default",
					label:'Week Avg Consmption\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"weektripsAvgDistanceM", "device.weeklyTripsAvgDistanceM",width: 2, height: 2,canChangeIcon: false,
 					decoration: "flat"
				) 
        	{
			state("default",
					label:'Week Avg dist/trip\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"weektripsAvgDurationS", "device.weeklyTripsAvgDurationS", unit:'min.',width: 2, height: 2,canChangeIcon: false,
  					decoration: "flat"
				)
        	{
			state("default",
					label:'Week Avg time/trip\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"weektotalDistanceM", "device.weeklyTotalDistanceM",width: 2, height: 2,canChangeIcon: false,
  					decoration: "flat",
				)
        	{
			state("default",
					label:'Week Total Dist.\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}

		valueTile(	"weektotalFuelVolumeL", "device.weeklyTotalFuelVolumeL",width: 2, height: 2,canChangeIcon: false,
  					decoration: "flat",
				)
        	{
			state("default",
					label:'Week Fuel Vol.\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"weektotalCostFuel", "device.weeklyTotalCostUsd",width: 2, height: 2,canChangeIcon: false,
  					decoration: "flat",
				)
        	{
			state("default",
					label:'Week Fuel Cost\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"weektotalNbTrips", "device.weeklyTotalNbTrips",width: 2, height: 2,canChangeIcon: false,
  					decoration: "flat",
				)
        	{
			state("default",
					label:'Week Trip Count\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"weektripsAvgScoreSpeeding", "device.weeklyTripsAvgScoreSpeeding",width: 2, height: 2,canChangeIcon: false,
  					decoration: "flat",
				)
        	{
			state("default",
					label:'Week Avg Score Speed\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"weektripsAvgScoreEvents", "device.weeklyTripsAvgScoreEvents",width: 2, height: 2,canChangeIcon: false,
  					decoration: "flat",
				)
        	{
			state("default",
					label:'Week Avg Score Events\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}

/*	Removed monthly stats due to rate limiting (need to be implemented by stats API on Automatic side
		// monthly tiles

		valueTile(	"monthtripsAvgAverageKmpl", "device.monthlyTripsAvgAverageKmpl",width: 1, height: 1,canChangeIcon: false,
 					decoration: "flat"
				) 
        	{
			state("default",
					label:'Month Avg Consmption\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"monthtripsAvgDistanceM", "device.monthlyTripsAvgDistanceM",width: 1, height: 1,canChangeIcon: false,
 					decoration: "flat"
				) 
        	{
			state("default",
					label:'Month Avg dist/trip\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"monthtripsAvgDurationS", "device.monthlyTripsAvgDurationS", unit:'min.',width: 1, height: 1,canChangeIcon: false,
  					decoration: "flat"
				)
        	{
			state("default",
					label:'Month Avg time/trip\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"monthtotalDistanceM", "device.monthlyTotalDistanceM",width: 1, height: 1,canChangeIcon: false,
  					decoration: "flat",
				)
        	{
			state("default",
					label:'Month Total Dist.\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}	

		valueTile(	"monthtotalFuelVolumeL", "device.monthlyTotalFuelVolumeL",width: 1, height: 1,canChangeIcon: false,
  					decoration: "flat",
				)
        	{
			state("default",
					label:'Month Fuel Vol.\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"monthtotalNbTrips", "device.monthlyTotalNbTrips",width: 1, height: 1,canChangeIcon: false,
  					decoration: "flat",
				)
        	{
			state("default",
					label:'Month Total Trips\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"monthtripsAvgScoreSpeeding", "device.monthlyTripsAvgScoreSpeeding",width: 1, height: 1,canChangeIcon: false,
  					decoration: "flat",
				)
        	{
			state("default",
					label:'Month Avg Score Speed\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"monthtripsAvgScoreEvents", "device.monthlyTripsAvgScoreEvents",width: 1, height: 1,canChangeIcon: false,
  					decoration: "flat",
				)
        	{
			state("default",
					label:'Month Avg Score Events\n${currentValue}',
					backgroundColor: "#ffffff"
				)
		}
*/
		standardTile("refresh", "device.make", width: 2, height: 2,inactiveLabel: false, decoration: "flat") {
			state "default", action:"polling.poll", icon:"st.secondary.refresh"
		}

		main(["main_multi","present"])
		details(["main_multi", "make", "model",  "refresh",  "submodel", "year", "present", "switch", "totalNbTrips", 
			"tripsAvgAverageKmpl", "tripsAvgDistanceM", "tripsAvgDurationS","totalDistanceM",
			"totalFuelVolumeL", "totalCostFuel", "tripsAvgScoreSpeeding", "tripsAvgScoreEvents",
			"yestripsAvgAverageKmpl", "yestripsAvgDistanceM", "yestripsAvgDurationS","yestotalDistanceM",
			"yestotalFuelVolumeL","yestotalCostFuel", "yestotalNbTrips", "yestripsAvgScoreSpeeding", "yestripsAvgScoreEvents",
			"weektripsAvgAverageKmpl", "weektripsAvgDistanceM", "weektripsAvgDurationS","weektotalDistanceM",
			"weektotalFuelVolumeL","weektotalCostFuel", "weektotalNbTrips","weektripsAvgScoreSpeeding", "weektripsAvgScoreEvents",
/*			Removed monthly stats due to rate limiting (need to be implemented by stats API on Automatic side
			"monthtripsAvgAverageKmpl", "monthtripsAvgDistanceM", "monthtripsAvgDurationS", "monthtotalDistanceM",
			"monthtotalFuelVolumeL","monthtotalNbTrips","monthtripsAvgScoreSpeeding", "monthtripsAvgScoreEvents",
*/            
		])        


	}
}

// handle commands

// Commands to device
def on() {
	sendEvent(name: "switch", value: "on")
	sendEvent(name: "presence", value: "present")
}

def off() {
	sendEvent(name: "switch", value: "off")
	sendEvent(name: "presence", value: "not present")
} 

void poll() {
	def vehicleMake
	def vehicleModel
	def vehicleSubmodel
	def vehicleDisplayName
	def vehicleYear
    
	
	def vehicleId= determine_vehicle_id("") 	    
	// Update the vehicle information
        
	getVehicles(vehicleId)
        
	String dateInLocalTime = new Date().format("yyyy-MM-dd", location.timeZone)

	if ((state?.lastGeneratedStatsDate != dateInLocalTime) || (state?.localFuelCostPerVolUnit != settings.localFuelCostPerVolUnit)) {
    
		// call getCurrentUserInfo() to update basic info on the user
        
		getCurrentUserInfo() 


		if (settings.trace) {
			log.debug "poll>about to call generateTripStats,dateInLocalTime=${dateInLocalTime},state.lastGeneratedDate= $state.lastGeneratedDate"
		}
		// generate all stats only once every day
		generateYesterdayTripStats(vehicleId)
		generateWeeklyTripStats(vehicleId)     
//		generateMonthlyTripStats(vehicleId) // not called due to rate limiting in ST
		state.lastGeneratedStatsDate= dateInLocalTime       
		state?.localFuelCostPerVolUnit =settings.localFuelCostPerVolUnit // save it for later processing
	}
    
	data?.vehicles.results.each {
		if (it.id == vehicleId) {
			if (settings.trace) {
				log.debug "poll>found vehicleId=${it.id}"
			}
			vehicleMake=it.make
			vehicleModel=it.model
			vehicleSubmodel=it.submodel
			vehicleDisplayName=it.display_name
			vehicleYear=it.year.toString()
		}        
	}    

	def dataEvents = [
		userid:data?.user.id,
		username:data?.user.username,
		email:data?.user.email,
		firstName:data?.user.first_name,
		lastName:data?.user.last_name,
		vehicleId:vehicleId,
		make: vehicleMake,
		model:vehicleModel,
		submodel:vehicleSubmodel,
		displayName:vehicleDisplayName,
		year:vehicleYear
	]



	// Generate trip stats for today
	String timezone = new Date().format("zzz", location.timeZone)
	String dateAtMidnight = dateInLocalTime + " 00:00 " + timezone    
	if (settings.trace) {
		log.debug("poll>date at Midnight in local time= ${dateAtMidnight}")
	}
	Date startDate = formatDate(dateAtMidnight)
	Date endDate= new Date()
//	Generate Events since last poll() call only

	getTrips("","", startDate,endDate, state?.lastPollTimestamp)
	state.lastPollTimestamp = now()
    
	String exceptionCheck = device.currentValue("verboseTrace").toString()
	if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {  
	// check if there is any exception or an error reported in the verboseTrace associated to the device 
		log.error "poll>vehicleId=$vehicleId,$exceptionCheck" 
		return    
	}

	def tripsAvgAverageKmplInPeriod =  device.currentValue("tripsAvgAverageKmplInPeriod")
	def tripsAvgDistanceMInPeriod =  device.currentValue("tripsAvgDistanceMInPeriod")
	def tripsAvgDurationSInPeriod =  device.currentValue("tripsAvgDurationSInPeriod")
	def totalDistanceMInPeriod =  device.currentValue("totalDistanceMInPeriod")
	def tripsAvgFuelVolumeLInPeriod = device.currentValue("tripsAvgFuelVolumeLInPeriod")
	def tripsAvgCostUsdInPeriod =  device.currentValue("tripsAvgCostUsdInPeriod")
	def totalFuelVolumeLInPeriod =  device.currentValue("totalFuelVolumeLInPeriod")
	def totalCostUsdInPeriod =  device.currentValue("totalCostUsdInPeriod")
	def totalDurationSInPeriod =  device.currentValue("totalDurationSInPeriod")
	def totalNbTripsInPeriod =  device.currentValue("totalNbTripsInPeriod")
	def totalHardAccelsInPeriod= device.currentValue("totalHardAccelsInPeriod")
	def totalHardBrakesInPeriod = device.currentValue("totalHardBrakesInPeriod")
	def tripsAvgScoreSpeedingInPeriod = device.currentValue("tripsAvgScoreSpeedingInPeriod")
	def tripsAvgScoreEventsInPeriod = device.currentValue("tripsAvgScoreEventsInPeriod")
    
	float tripsAvgAverageKmpl =  (tripsAvgAverageKmplInPeriod)? tripsAvgAverageKmplInPeriod.toFloat():0
	float tripsAvgDistanceM =  (tripsAvgDistanceMInPeriod)?tripsAvgDistanceMInPeriod.toFloat():0
	float tripsAvgDurationS =  (tripsAvgDurationSInPeriod)?tripsAvgDurationSInPeriod.toFloat():0
	float totalDistanceM =  (totalDistanceMInPeriod)?totalDistanceMInPeriod.toFloat():0
	float tripsAvgFuelVolumeL =  (tripsAvgFuelVolumeLInPeriod)? tripsAvgFuelVolumeLInPeriod.toFloat():0
	float tripsAvgCostUsd = (tripsAvgCostUsdInPeriod)?tripsAvgCostUsdInPeriod.toFloat():0 
	float totalFuelVolumeL =  (totalFuelVolumeLInPeriod)? totalFuelVolumeLInPeriod.toFloat():0
	float totalCostUsd = (totalCostUsdInPeriod)?totalCostUsdInPeriod.toFloat():0 
	float totalDurationS = (totalDurationSInPeriod)?totalDurationSInPeriod.toFloat():0 
	int totalNbTrips = (totalNbTripsInPeriod)?totalNbTripsInPeriod.toInteger():0 
	long totalHardAccels= (totalHardAccelsInPeriod)?totalHardAccelsInPeriod.toLong():0
	long totalHardBrakes = (totalHardBrakesInPeriod)?totalHardBrakesInPeriod.toLong():0
	float tripsAvgScoreSpeeding= (tripsAvgScoreSpeedingInPeriod)? tripsAvgScoreSpeedingInPeriod.toFloat():0
	float tripsAvgScoreEvents = (tripsAvgScoreEventsInPeriod)?tripsAvgScoreEventsInPeriod.toFloat():0
    
	if (settings.localFuelCostPerVolUnit) {     
		tripsAvgCostUsd = tripsAvgFuelVolumeL * settings.localFuelCostPerVolUnit
		totalCostUsd = totalFuelVolumeL * settings.localFuelCostPerVolUnit
	}
    
	dataEvents = dataEvents + [
		'tripsAvgAverageKmpl':tripsAvgAverageKmpl,
		'tripsAvgDistanceM':tripsAvgDistanceM,
		'tripsAvgDurationS':tripsAvgDurationS,
		'totalDistanceM': totalDistanceM,
		'tripsAvgFuelVolumeL':tripsAvgFuelVolumeL,
		'tripsAvgCostUsd':tripsAvgCostUsd,
		'totalFuelVolumeL':totalFuelVolumeL,
		'totalCostUsd':totalCostUsd,
		'totalDurationS':totalDurationS, 
		'totalNbTrips':totalNbTrips.toString(), 
		'totalHardAccels':totalHardAccels.toString(),
		'totalHardBrakes': totalHardBrakes.toString(),
		'tripsAvgScoreSpeeding':tripsAvgScoreSpeeding,
		'tripsAvgScoreEvents': tripsAvgScoreEvents
	]
	generateEvent(dataEvents)
	sendEvent name: "verboseTrace", displayed: ((settings.trace)?: false), value:
			"poll>done for vehicleId = ${vehicleId}"

}


private void generateEvent(Map results) {
	if (settings.trace) {
		log.debug "generateEvent>parsing data $results"
	}
    
	if(results) {
		results.each { name, value ->
			def isDisplayed = true
			if (name.toUpperCase().contains("COST")) { 

// 			Cost variable names contain 'cost' 
 				float costValue = value.toFloat().round(2)
				isDisplayed = (costValue)? isStateChange(device, name, costValue.toString()):false
				sendEvent(name: name, value: costValue.toString(), unit: "USD",  displayed: isDisplayed)                                     									 
			} else if (name.toUpperCase().contains("KMPL")) { 

// 			Consumption in kmpl variable names contain 'kmpl' 
 				float kmplValue = getConsumptionInKmpl(value).toFloat().round(1)
				isDisplayed = (kmplValue)? isStateChange(device, name, kmplValue.toString()):false
				sendEvent(name: name, value: kmplValue.toString(), unit: getConsumptionScale(), displayed: isDisplayed)                                     									 
			} else if (name.toUpperCase().contains("MPG")) { 

// 			Consumption in mpg variable names contain 'mpg' 
 				float mpgValue = getConsumptionInMpg(value).toFloat().round(1)
				isDisplayed = (mpgValue)? isStateChange(device, name, mpgValue.toString()):false
				sendEvent(name: name, value: mpgValue.toString(), unit: getConsumptionScale(), displayed: isDisplayed)                                     									 
			} else if (name.toUpperCase().contains("SCORE")) { 
// 			Score variable names contain 'score' 
 				float scoreValue = value.toFloat().round(1)
				isDisplayed = (scoreValue)? isStateChange(device, name, scoreValue.toString()):false
				sendEvent(name: name, value: scoreValue.toString(), displayed: isDisplayed)                                     									 
			} else if (name.toUpperCase().contains("VOLUME")) { 
// 			Volume variable names contain 'volume' 
 				float volumeValue = getVolume(value).toFloat().round(1)
				isDisplayed = (volumeValue)?isStateChange(device, name, volumeValue.toString()):false
				sendEvent(name: name, value: volumeValue.toString(), unit: getVolumeScale(), displayed: isDisplayed)                                     									 
			} else if (name.toUpperCase().contains("DURATION")) { 

// 			Duration variable names contain 'duration' (in seconds)
 				float durationValue = getDuration(value).toFloat().round(1)
				isDisplayed = (durationValue)? isStateChange(device, name, durationValue.toString()):false
				sendEvent(name: name, value: durationValue.toString(), unit: "min", displayed: isDisplayed)                                     									 
			} else if (name.toUpperCase().contains("DISTANCE")) { 
// 			Distance variable names contain 'distance'
 				float distanceValue = getDistance(value).toFloat().round(1)
				isDisplayed = (distanceValue)? isStateChange(device, name, distanceValue.toString()):false
				sendEvent(name: name, value: distanceValue.toString(), unit: getDistanceScale(), displayed: isDisplayed)                                     									 
			} else if (name.toUpperCase().contains("SPEED")) { 
// 			Speed variable names contain 'speed' (in kph)

 				float speedValue = getSpeed(value).toFloat().round(1)
			 	isDisplayed = (speedValue)? isStateChange(device, name, speedValue.toString()):false
				sendEvent(name: name, value: speedValue.toString(), unit: getSpeedScale(), displayed: isDisplayed)                                     									 
			} else if (name.toUpperCase().contains("DATA")) { 
// 			Speed variable names contain 'data'
				isDisplayed = false
				sendEvent(name: name, value: value, displayed: isDisplayed)       
 			} else {
				isDisplayed = (value)?isStateChange(device, name, value):false
				sendEvent(name: name, value: value, displayed: isDisplayed)       
			}
		}
	}
}

private def getDuration(value) {
	if (!value) {
		return 0    
	}    
	return (value/60)
}

private def getSpeed(value) {
	if (!value) {
		return 0    
	}    
	if(getTemperatureScale() == "C"){
		return value
	} else {
		return milesToKm(value)
	}
}

private def getDistance(value) {
	if (!value) {
		return 0    
	}    
	def km = value/1000
	if (getTemperatureScale() == "C"){
		return km
	} else {
		return kmToMiles(km)
	}
}


private def kmplToMpg(value) {
	if (!value) {
		return 0
	}        
	return (value * 2.352145833)  
}

private def mpgToKmpl(value) {
	if (!value) {
		return 0
	}        
	return (value * 0.4251)  
}

private def getConsumptionInMpg(value) {
	if (getTemperatureScale() == "F"){
		return value    
    }
	return (kmplToMpg(value))
}

private def getConsumptionInKmpl(value) {
	if (getTemperatureScale() == "C"){
		return value    
    }
	return (kmplToMpg(value))
}

private def getVolumeScale() {
	def scale= getTemperatureScale()
	if (scale == 'C') {
		return "liters"
	}
	return "gallons"
}

private def getVolume(value) {
	if (getTemperatureScale() == "C"){
		return value    
    }
	if (!value) {
		return 0
	}        
	return (value * 0.26417)
}

private def getConsumptionScale() {
	def scale= getTemperatureScale()
	if (scale == 'C') {
		return "kmpl"
	}
	return "mpg"
}

private def getDistanceScale() {
	def scale= getTemperatureScale()
	if (scale == 'C') {
		return "km"
	}
	return "mi"
}

private def getSpeedScale() {
	def scale= getTemperatureScale()
	if (scale == 'C') {
		return "kmh"
	}
	return "mph"
}

private def milesToKm(distance) {
	if (!distance) {
		return 0    
	}    
	return (distance * 1.609344) 
}

private def kmToMiles(distance) {
	if (!distance) {
		return 0    
	}    
	return (distance * 0.62137) 
}

void refresh() {
	poll()
}
private def api(method, args, success={}) {

	String URI_ROOT = "${get_URI_ROOT()}"
	if (!isLoggedIn()) {
		login()
	}
/*  Y.R. No need to check the tokens as they are valid for a year and no refresh method is available at Automatic.    
	if (isTokenExpired()) {
		if (settings.trace) {
			log.debug "api> need to refresh tokens"
		}
		refresh_tokens()
	}
*/    
	def methods = [
		'currentUserInfo': 
			[uri:"${URI_ROOT}/user/me/", type: 'get'],
		'trips': 
			[uri:"${URI_ROOT}/trip/${args}", 
          		type: 'get'],
		'vehicles': 
			[uri:"${URI_ROOT}/vehicle/", 
          		type: 'get']
	]
	def request = methods.getAt(method)
	if (settings.trace) {
		sendEvent name: "verboseTrace", value:
			"api> about to call doRequest with (unencoded) args = ${args}"
	}
	doRequest(request.uri, args, request.type, success)
}

// Need to be authenticated in before this is called. So don't call this. Call api.
private def doRequest(uri, args, type, success) {
        
	def params = [
		uri: uri,
		headers: [
			'Authorization': "${data.auth.token_type} ${data.auth.access_token}",
			'Content-Type': "application/json",
			'charset': "UTF-8",
			'Accept': "application/json"
		],
		body: args
	]
	try {
		if (settings.trace) {
			sendEvent name: "verboseTrace", value: "doRequest>token= ${data.auth.access_token}"
			sendEvent name: "verboseTrace", value:
				"doRequest>about to ${type} with uri ${params.uri}, args= ${args}"
				log.debug "doRequest> ${type}> uri ${params.uri}, args= ${args}"
		}
		if (type == 'post') {
			httpPostJson(params, success) 
		} else if (type == 'get') {
			params.body = null // parameters already in the URL request
			httpGet(params, success) 
		}
	} catch (java.net.UnknownHostException e) {
		log.error "doRequest> Unknown host - check the URL " + params.uri
		sendEvent name: "verboseTrace", value: "doRequest>exception $e, Unknown host ${params.uri}" 
		throw e        
	} catch (java.net.NoRouteToHostException e) {
		log.error "doRequest> No route to host - check the URL " + params.uri
		sendEvent name: "verboseTrace", value: "doRequest>exception $e, No route to host ${params.uri}"
		throw e        
	} catch (e) {
		log.error "doRequest> exception $e " + params.uri
		sendEvent name: "verboseTrace", value: "doRequest>exception $e at ${params.uri}" 
		throw e        
	}
}

void generateYesterdayTripStats(vehicleId) {

//	Generate stats for yesterday

	String nowInLocalTime = new Date().format("yyyy-MM-dd HH:mm", location.timeZone)
	String dateInLocalTime = new Date().format("yyyy-MM-dd", location.timeZone) 
	String timezone = new Date().format("zzz", location.timeZone)
	String dateAtMidnight = dateInLocalTime + " 00:00 " + timezone    
	
    
	if (settings.trace) {
		log.debug("generateTripStats>date at Midnight= ${dateAtMidnight}")
	}
	Date endDate = formatDate(dateAtMidnight) 
	Date startDate = (endDate -1)

	if (settings.trace) {
		log.debug("generateYesterdayTripStats>startDate in UTC =${startDate.format("yyyy-MM-dd HH:mm:ss", TimeZone.getTimeZone("UTC"))}" +
			",endDate in UTC =${endDate.format("yyyy-MM-dd HH:mm:ss", TimeZone.getTimeZone("UTC"))}")        	
	}
	getTrips(vehicleId,"", startDate,endDate, null)

	def tripsAvgAverageKmplInPeriod =  device.currentValue("tripsAvgAverageKmplInPeriod")
	def tripsAvgDistanceMInPeriod =  device.currentValue("tripsAvgDistanceMInPeriod")
	def tripsAvgDurationSInPeriod =  device.currentValue("tripsAvgDurationSInPeriod")
	def totalDistanceMInPeriod =  device.currentValue("totalDistanceMInPeriod")
	def tripsAvgFuelVolumeLInPeriod = device.currentValue("tripsAvgFuelVolumeLInPeriod")
	def tripsAvgCostUsdInPeriod =  device.currentValue("tripsAvgCostUsdInPeriod")
	def totalFuelVolumeLInPeriod =  device.currentValue("totalFuelVolumeLInPeriod")
	def totalCostUsdInPeriod =  device.currentValue("totalCostUsdInPeriod")
	def totalDurationSInPeriod =  device.currentValue("totalDurationSInPeriod")
	def totalNbTripsInPeriod=  device.currentValue("totalNbTripsInPeriod")
	def totalHardAccelsInPeriod= device.currentValue("totalHardAccelsInPeriod")
	def totalHardBrakesInPeriod = device.currentValue("totalHardBrakesInPeriod")
	def tripsAvgScoreSpeedingInPeriod = device.currentValue("tripsAvgScoreSpeedingInPeriod")
	def tripsAvgScoreEventsInPeriod = device.currentValue("tripsAvgScoreEventsInPeriod")
    
	float tripsAvgAverageKmpl =  (tripsAvgAverageKmplInPeriod)? tripsAvgAverageKmplInPeriod.toFloat():0
	float tripsAvgDistanceM =  (tripsAvgDistanceMInPeriod)?tripsAvgDistanceMInPeriod.toFloat():0
	float tripsAvgDurationS =  (tripsAvgDurationSInPeriod)?tripsAvgDurationSInPeriod.toFloat():0
	float totalDistanceM =  (totalDistanceMInPeriod)?totalDistanceMInPeriod.toFloat():0
	float tripsAvgFuelVolumeL =  (tripsAvgFuelVolumeLInPeriod)? tripsAvgFuelVolumeLInPeriod.toFloat():0
	float tripsAvgCostUsd = (tripsAvgCostUsdInPeriod)?tripsAvgCostUsdInPeriod.toFloat():0 
	float totalFuelVolumeL =  (totalFuelVolumeLInPeriod)? totalFuelVolumeLInPeriod.toFloat():0
	float totalCostUsd = (totalCostUsdInPeriod)?totalCostUsdInPeriod.toFloat():0 
	float totalDurationS = (totalDurationSInPeriod)?totalDurationSInPeriod.toFloat():0 
	int totalNbTrips = (totalNbTripsInPeriod)?totalNbTripsInPeriod.toInteger():0 
	long totalHardAccels= (totalHardAccelsInPeriod)?totalHardAccelsInPeriod.toLong():0
	long totalHardBrakes = (totalHardBrakesInPeriod)?totalHardBrakesInPeriod.toLong():0
	float tripsAvgScoreSpeeding= (tripsAvgScoreSpeedingInPeriod)? tripsAvgScoreSpeedingInPeriod.toFloat():0
	float tripsAvgScoreEvents = (tripsAvgScoreEventsInPeriod)?tripsAvgScoreEventsInPeriod.toFloat():0

	if (settings.localFuelCostPerVolUnit) {     
		tripsAvgCostUsd = tripsAvgFuelVolumeL * settings.localFuelCostPerVolUnit
		totalCostUsd = totalFuelVolumeL * settings.localFuelCostPerVolUnit
	}
    
	def dataStats = ['yesterdayTripsAvgAverageKmpl':tripsAvgAverageKmpl,
		'yesterdayTripsAvgDistanceM':tripsAvgDistanceM,
		'yesterdayTripsAvgDurationS':tripsAvgDurationS,
		'yesterdayTotalDistanceM': totalDistanceM,
		'yesterdayTripsAvgFuelVolumeL':tripsAvgFuelVolumeL,
		'yesterdayTripsAvgCostUsd':tripsAvgCostUsd,
		'yesterdayTotalFuelVolumeL':totalFuelVolumeL,
		'yesterdayTotalCostUsd':totalCostUsd,
		'yesterdayTotalDurationS':totalDurationS,
		'yesterdayTotalNbTrips':totalNbTrips.toString(),
		'yesterdayTotalHardAccels':totalHardAccels.toString(),
		'yesterdayTotalHardBrakes': totalHardBrakes.toString(),
		'yesterdayTripsAvgScoreSpeeding':tripsAvgScoreSpeeding,
		'yesterdayTripsAvgScoreEvents': tripsAvgScoreEvents
	]    

	generateEvent(dataStats)
}

void generateWeeklyTripStats(vehicleId) {

	String nowInLocalTime = new Date().format("yyyy-MM-dd HH:mm", location.timeZone)
	String dateInLocalTime = new Date().format("yyyy-MM-dd", location.timeZone) 
	String timezone = new Date().format("zzz", location.timeZone)
	String dateAtMidnight = dateInLocalTime + " 00:00 " + timezone    
	if (settings.trace) {
		log.debug("generateTripStats>date at Midnight= ${dateAtMidnight}")
	}
	Date endDate = formatDate(dateAtMidnight) 

//	Generate stats for the past week

	Date startDate = (endDate -7)

	if (settings.trace) {
		log.debug("generateWeeklyTripStats>startDate in UTC =${startDate.format("yyyy-MM-dd HH:mm:ss", TimeZone.getTimeZone("UTC"))}" +
			",endDate in UTC =${endDate.format("yyyy-MM-dd HH:mm:ss", TimeZone.getTimeZone("UTC"))}")        	
	}
	getTrips(vehicleId,"", startDate,endDate, null)

	def tripsAvgAverageKmplInPeriod =  device.currentValue("tripsAvgAverageKmplInPeriod")
	def tripsAvgDistanceMInPeriod =  device.currentValue("tripsAvgDistanceMInPeriod")
	def tripsAvgDurationSInPeriod =  device.currentValue("tripsAvgDurationSInPeriod")
	def totalDistanceMInPeriod =  device.currentValue("totalDistanceMInPeriod")
	def tripsAvgFuelVolumeLInPeriod = device.currentValue("tripsAvgFuelVolumeLInPeriod")
	def tripsAvgCostUsdInPeriod =  device.currentValue("tripsAvgCostUsdInPeriod")
	def totalFuelVolumeLInPeriod =  device.currentValue("totalFuelVolumeLInPeriod")
	def totalCostUsdInPeriod =  device.currentValue("totalCostUsdInPeriod")
	def totalDurationSInPeriod =  device.currentValue("totalDurationSInPeriod")
	def totalNbTripsInPeriod=  device.currentValue("totalNbTripsInPeriod")
	def totalHardAccelsInPeriod= device.currentValue("totalHardAccelsInPeriod")
	def totalHardBrakesInPeriod = device.currentValue("totalHardBrakesInPeriod")
	def tripsAvgScoreSpeedingInPeriod = device.currentValue("tripsAvgScoreSpeedingInPeriod")
	def tripsAvgScoreEventsInPeriod = device.currentValue("tripsAvgScoreEventsInPeriod")
    
	float tripsAvgAverageKmpl =  (tripsAvgAverageKmplInPeriod)? tripsAvgAverageKmplInPeriod.toFloat():0
	float tripsAvgDistanceM =  (tripsAvgDistanceMInPeriod)?tripsAvgDistanceMInPeriod.toFloat():0
	float tripsAvgDurationS =  (tripsAvgDurationSInPeriod)?tripsAvgDurationSInPeriod.toFloat():0
	float totalDistanceM =  (totalDistanceMInPeriod)?totalDistanceMInPeriod.toFloat():0
	float tripsAvgFuelVolumeL =  (tripsAvgFuelVolumeLInPeriod)? tripsAvgFuelVolumeLInPeriod.toFloat():0
	float tripsAvgCostUsd = (tripsAvgCostUsdInPeriod)?tripsAvgCostUsdInPeriod.toFloat():0 
	float totalFuelVolumeL =  (totalFuelVolumeLInPeriod)? totalFuelVolumeLInPeriod.toFloat():0
	float totalCostUsd = (totalCostUsdInPeriod)?totalCostUsdInPeriod.toFloat():0 
	float totalDurationS = (totalDurationSInPeriod)?totalDurationSInPeriod.toFloat():0 
	int totalNbTrips = (totalNbTripsInPeriod)?totalNbTripsInPeriod.toInteger():0 
	long totalHardAccels= (totalHardAccelsInPeriod)?totalHardAccelsInPeriod.toLong():0
	long totalHardBrakes = (totalHardBrakesInPeriod)?totalHardBrakesInPeriod.toLong():0
	float tripsAvgScoreSpeeding= (tripsAvgScoreSpeedingInPeriod)? tripsAvgScoreSpeedingInPeriod.toFloat():0
	float tripsAvgScoreEvents = (tripsAvgScoreEventsInPeriod)?tripsAvgScoreEventsInPeriod.toFloat():0
    
	if (settings.localFuelCostPerVolUnit) {     
		tripsAvgCostUsd = tripsAvgFuelVolumeL * settings.localFuelCostPerVolUnit
		totalCostUsd = totalFuelVolumeL * settings.localFuelCostPerVolUnit
	}

	def dataStats  = [
    	'weeklyTripsAvgAverageKmpl':tripsAvgAverageKmpl,
		'weeklyTripsAvgDistanceM':tripsAvgDistanceM,
		'weeklyTripsAvgDurationS':tripsAvgDurationS,
		'weeklyTotalDistanceM': totalDistanceM,
		'weeklyTripsAvgFuelVolumeL':tripsAvgFuelVolumeL,
		'weeklyTripsAvgCostUsd':tripsAvgCostUsd,
		'weeklyTotalFuelVolumeL':totalFuelVolumeL,
		'weeklyTotalCostUsd':totalCostUsd,
		'weeklyTotalDurationS':totalDurationS,
		'weeklyTotalNbTrips':totalNbTrips.toString(),
		'weeklyTotalHardAccels':totalHardAccels.toString(),
		'weeklyTotalHardBrakes': totalHardBrakes.toString(),
		'weeklyTripsAvgScoreSpeeding':tripsAvgScoreSpeeding,
		'weeklyTripsAvgScoreEvents': tripsAvgScoreEvents
	]    
    
	generateEvent(dataStats)
    
}

void generateMonthlyTripStats(vehicleId) {

	String nowInLocalTime = new Date().format("yyyy-MM-dd HH:mm", location.timeZone)
   
//	Generate stats for the past month

	Calendar oneMonthAgoCal = new GregorianCalendar()
	oneMonthAgoCal.add(Calendar.MONTH, -1)
	Date oneMonthAgo = oneMonthAgoCal.getTime()
	Date endDate = new Date()
	if (settings.trace) {
		log.debug("generateMonthlyTripStats>startDate in UTC =${startDate.format("yyyy-MM-dd HH:mm:ss", TimeZone.getTimeZone("UTC"))}" +
			",endDate in UTC =${endDate.format("yyyy-MM-dd HH:mm:ss", TimeZone.getTimeZone("UTC"))}")        	
	}
	getTrips(vehicleId,"", startDate,endDate, null)

	def tripsAvgAverageKmplInPeriod =  device.currentValue("tripsAvgAverageKmplInPeriod")
	def tripsAvgDistanceMInPeriod =  device.currentValue("tripsAvgDistanceMInPeriod")
	def tripsAvgDurationSInPeriod =  device.currentValue("tripsAvgDurationSInPeriod")
	def totalDistanceMInPeriod =  device.currentValue("totalDistanceMInPeriod")
	def tripsAvgFuelVolumeLInPeriod = device.currentValue("tripsAvgFuelVolumeLInPeriod")
	def tripsAvgCostUsdInPeriod =  device.currentValue("tripsAvgCostUsdInPeriod")
	def totalFuelVolumeLInPeriod =  device.currentValue("totalFuelVolumeLInPeriod")
	def totalCostUsdInPeriod =  device.currentValue("totalCostUsdInPeriod")
	def totalDurationSInPeriod =  device.currentValue("totalDurationSInPeriod")
	def totalNbTripsInPeriod=  device.currentValue("totalNbTripsInPeriod")
	def totalHardAccelsInPeriod= device.currentValue("totalHardAccelsInPeriod")
	def totalHardBrakesInPeriod = device.currentValue("totalHardBrakesInPeriod")
	def tripsAvgScoreSpeedingInPeriod = device.currentValue("tripsAvgScoreSpeedingInPeriod")
	def tripsAvgScoreEventsInPeriod = device.currentValue("tripsAvgScoreEventsInPeriod")
    
	float tripsAvgAverageKmpl =  (tripsAvgAverageKmplInPeriod)? tripsAvgAverageKmplInPeriod.toFloat():0
	float tripsAvgDistanceM =  (tripsAvgDistanceMInPeriod)?tripsAvgDistanceMInPeriod.toFloat():0
	float tripsAvgDurationS =  (tripsAvgDurationSInPeriod)?tripsAvgDurationSInPeriod.toFloat():0
	float totalDistanceM =  (totalDistanceMInPeriod)?totalDistanceMInPeriod.toFloat():0
	float tripsAvgFuelVolumeL =  (tripsAvgFuelVolumeLInPeriod)? tripsAvgFuelVolumeLInPeriod.toFloat():0
	float tripsAvgCostUsd = (tripsAvgCostUsdInPeriod)?tripsAvgCostUsdInPeriod.toFloat():0 
	float totalFuelVolumeL =  (totalFuelVolumeLInPeriod)? totalFuelVolumeLInPeriod.toFloat():0
	float totalCostUsd = (totalCostUsdInPeriod)?totalCostUsdInPeriod.toFloat():0 
	float totalDurationS = (totalDurationSInPeriod)?totalDurationSInPeriod.toFloat():0 
	int totalNbTrips = (totalNbTripsInPeriod)?totalNbTripsInPeriod.toInteger():0 
	long totalHardAccels= (totalHardAccelsInPeriod)?totalHardAccelsInPeriod.toLong():0
	long totalHardBrakes = (totalHardBrakesInPeriod)?totalHardBrakesInPeriod.toLong():0
	float tripsAvgScoreSpeeding= (tripsAvgScoreSpeedingInPeriod)? tripsAvgScoreSpeedingInPeriod.toFloat():0
	float tripsAvgScoreEvents = (tripsAvgScoreEventsInPeriod)?tripsAvgScoreEventsInPeriod.toFloat():0

	if (settings.localCostPerVolumeUnit) {     
		tripsAvgCostUsd = tripsAvgFuelVolumeL * settings.localCostPerVolumeUnit
		totalCostUsd = totalFuelVolumeL * settings.localCostPerVolumeUnit
	}
    
   	
	def dataStats  = [
		'monthlyTripsAvgAverageKmpl':tripsAvgAverageKmpl,
		'monthlyTripsAvgDistanceM':tripsAvgDistanceM,
		'monthlyTripsAvgDurationS':tripsAvgDurationS,
		'monthlyTotalDistanceM': totalDistanceM,
		'monthlyTripsAvgFuelVolumeL':tripsAvgFuelVolumeL,
		'monthlyTripsAvgCostUsd':tripsAvgCostUsd,
		'monthlyTotalFuelVolumeL':totalFuelVolumeL,
		'monthlyTotalCostUsd':totalCostUsd,
		'monthlyTotalDurationS':totalDurationS,
		'monthlyTotalNbTrips':totalNbTrips.toString(),
		'monthlyTotalHardAccels':totalHardAccels.toString(),
		'monthlyTotalHardBrakes': totalHardBrakes.toString(),
		'monthlyTripsAvgScoreSpeeding':tripsAvgScoreSpeeding,
		'monthlyTripsAvgScoreEvents': tripsAvgScoreEvents
	]
	generateEvent(dataStats)
    
}
private def ISODateFormat(dateString) {
	def myTimezone
 	SimpleDateFormat ISO8601format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
	Date aDate = ISO8601format.parse(dateString.substring(0,19) + 'Z')
	return aDate
}

private def formatTimeInTimezone(dateTime, timezone='') {
	def myTimezone=(timezone)?TimeZone.getTimeZone(timezone):location.timeZone 
	String dateInLocalTime =new Date(dateTime).format("yyyy-MM-dd HH:mm:ss zzz", myTimezone)
	log.debug("formatDateInLocalTime>dateInLocalTime=$dateInLocalTime, timezone=$timezone")    
	return dateInLocalTime
}

private def formatDate(dateString) {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm zzz")
	Date aDate = sdf.parse(dateString)
	return aDate
}

private Date formatDateInGMTDate(dateInString, timezone='') {
	def myTimezone=(timezone)?TimeZone.getTimeZone(timezone):location.timeZone 
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
	dateFormat.setTimeZone(myTimezone)    
	Date DateInTimezone = dateFormat.parse(dateInString.substring(0,19))
	return DateInTimezone
}

private String formatDateInLocalTime(dateInString, timezone='') {
	def myTimezone=(timezone)?TimeZone.getTimeZone(timezone):location.timeZone 
	if ((dateInString==null) || (dateInString.trim()=="")) {
		return (new Date().format("yyyy-MM-dd HH:mm:ss", myTimezone))
	}    
	SimpleDateFormat ISODateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
	Date ISODate = ISODateFormat.parse(dateInString.substring(0,19) + 'Z')
	String dateInLocalTime =new Date(ISODate.getTime()).format("yyyy-MM-dd HH:mm:ss zzz", myTimezone)
	log.debug("formatDateInLocalTime>dateInString=$dateInString, dateInLocalTime=$dateInLocalTime")    
	return dateInLocalTime
}


// 	vehicleId - Id of the vehicle, by default the current one  
//	tripId - Id of the trip, could be a list of trips separated by commas, by default, all trips are selected 
//	startDateTime - start timestamp for the query
//	endDateTime - end timestamp for the query
//	eventTimestamp - event timestamp for generating events (ex. hard acceleration, hard brake, speeding, etc.)
//		if null, do not generate events
//	postData - indicates whether the data should be posted as is in the tripsData attribute
void getTrips(vehicleId,tripId,startDateTime,endDateTime, eventTimestamp,postData=false) {
	def AUTOMATIC_SUCCESS=200
	def MAX_TRIP_DATA=50    
	def AUTOMATIC_MAX_TRIP_LIMIT=250    
	float totalDistanceM=0, totalDurationS=0, totalAverageKmpl=0, totalCostUsd=0,totalFuelVolumeL=0
	long totalHardAccels=0, totalHardBrakes=0, totalScoreSpeeding=0, totalScoreEvents=0
	float tripsAvgDistanceM=0,tripsAvgDurationS,tripsAvgCostUsd=0,tripsAvgFuelVolumeL= 0
	float tripsAvgAverageKmpl=0,tripsAvgScoreSpeeding=0,tripsAvgScoreEvents=0
    
	int nbTrips=0
	def bodyReq=""
	def id    
	Double startTimestamp,endTimestamp    
    
	def tripsData=[]
	def tripsList=""
	vehicleId= determine_vehicle_id(vehicleId) 	    

	if (tripId) {
		bodyReq = tripId + '/'
	}    
//	Set the limit to 250 trips per page.    
	bodyReq=bodyReq + "?vehicle=" + vehicleId + "&limit=${AUTOMATIC_MAX_TRIP_LIMIT}"

	if (startDateTime) {
		startTimestamp = (startDateTime.getTime()/1000).toDouble().round()
		bodyReq = bodyReq+ "&started_at__gte=" + startTimestamp.intValue()      	// in epoch time in seconds
		if (endDateTime) {
			endTimestamp = (endDateTime.getTime()/1000).toDouble().round()
			bodyReq = bodyReq+ "&ended_at__lte=" + endTimestamp.intValue()     	
		}
	}            
	if (settings.trace) {
		log.debug "getTrips>bodyReq=${bodyReq}, tripId=$tripId" 
	}				                
	    
	int j=0        
	def statusCode=true
	while ((statusCode!= AUTOMATIC_SUCCESS) && (j++ <2)) { // retries once if api call fails
		api('trips', bodyReq) {resp->
			statusCode = resp.status        
			if (statusCode == AUTOMATIC_SUCCESS) {
				if (settings.trace) {
					log.debug "getTrips>resp.data=${resp.data}" 
				}				                
				if (!resp.data._metadata) {
					// just one trip was found                
					data?.trips?.results[0]=resp.data
					data.trips?._metadata?.count=1                    
				} else {
					data?.trips=resp.data
				}                    
				if (data.trips._metadata.count) {            
					for (i in 0..data.trips._metadata.count -1) {
						nbTrips++                    
						id= data.trips.results[i].id
						def startedAt=data.trips.results[i].started_at
						def endedAt=data.trips.results[i].ended_at
						def distanceM=data.trips.results[i].distance_m
						def durationS=data.trips.results[i].duration_s
						def vehicleURL=data.trips.results[i].vehicle
						def startLocation=data.trips.results[i].start_location
						def startAddress=data.trips.results[i].start_address
						def endLocation=data.trips.results[i].end_location
						def endAddress=data.trips.results[i].end_address
						def startTimezone=data.trips.results[i].start_timezone       		
						def endTimezone=data.trips.results[i].end_timezone       		
						def tags= data.trips.results[i].tags       		
						def fuelCostUsd=data.trips.results[i].fuel_cost_usd
						def fuelVolumeL=data.trips.results[i].fuel_volume_l                    
						def averageKmpl=data.trips.results[i].average_kmpl
						def hardAccels=data.trips.results[i].hard_accels
						def hardBrakes=data.trips.results[i].hard_brakes
						def scoreSpeeding=data.trips.results[i].score_speeding
						def scoreEvents=data.trips.results[i].score_events
                    
						if (averageKmpl) {
							totalAverageKmpl =totalAverageKmpl +averageKmpl.toFloat()                        
						}	
						if (distanceM) {
							totalDistanceM =totalDistanceM +distanceM.toFloat()
						}
						if (durationS) {
							totalDurationS =totalDurationS +durationS.toFloat()
						}
						if (fuelCostUsd) {
							totalCostUsd =totalCostUsd +fuelCostUsd.toFloat()
						}
						if (fuelVolumeL) {
							totalFuelVolumeL =totalFuelVolumeL +fuelVolumeL.toFloat()
						}
						if (hardAccels) {
							totalHardAccels =totalHardAccels + hardAccels.toInteger()
						}
						if (hardBrakes) {
							totalHardBrakes =totalHardBrakes + hardBrakes.toInteger()
						}
						if (scoreSpeeding) {
							totalScoreSpeeding =totalScoreSpeeding + scoreSpeeding.toInteger()
						}
						if (scoreEvents) {
							totalScoreEvents =totalScoreEvents + scoreEvents.toInteger()
						}
					                    
						if (postData) {
                    
							if (data.trips._metadata.count <= MAX_TRIP_DATA) {
								tripsData << data.trips.results[i] // to be transformed into Json later
							} else {
								log.error "getTrips>vehicleId=${vehicleId},too much data to post, count=${data.trips._metadata.count} trips, no posting it"
							}                        
						}
						tripsList = tripsList + id + ','
                        
						if (eventTimestamp) {
							// generate events when startedDate greater than eventTimestamp (to avoid generating the same events twice)
							Date startedDate=ISODateFormat(startedAt)
							if ((startedDate) && (startedDate.getTime() > eventTimestamp)) {                        
								generateVehicleEvents(data.trips.results[i])
							}                                                        
						}                        
						if (settings.trace) {
							log.debug "getTrips>tripId=${id},startedAt=${startedAt},endedAt=${endedAt}," +
								"distanceM=${distanceM},durationS=${durationS},vehicleURL=${vehicleURL},startLocation=${startLocation},startAddress=${startAddress}," +
								"endLocation=${endLocation},endAddress=${endAddress},startTimezone=${startTimezone},endTimezone=${endTimezone}," +       		
								"averageKmpl=${averageKmpl},fuelVolumeL=${fuelVolumeL},fuelCostUsd=${fuelCostUsd}," +
								"hardAccels=${hardAccels},hardBrakes=${hardBrakes},scoreSpeeding=${scoreSpeeding},scoreEvents=${scoreEvents}\n" +
								"vehicle_events=${data.trips.results[i].vehicle_events}"
							sendEvent name: "verboseTrace", value: "getTrips>tripId=${id},startedAt=${startedAt},endedAt=${endedAt}," +
								"distanceM=${distanceM},durationS=${durationS},vehicleURL=${vehicleURL},startLocation=${startLocation},startAddress=${startAddress}," +
								"endLocation=${endLocation},endAddress=${endAddress},startTimezone=${startTimezone},endTimezone=${endTimezone}," +       		
								"averageKmpl=${averageKmpl},fuelVolumeL=${fuelVolumeL},fuelCostUsd=${fuelCostUsd}," +
								"hardAccels=${hardAccels},hardBrakes=${hardBrakes},scoreSpeeding=${scoreSpeeding},scoreEvents=${scoreEvents}\n" +
								"vehicle_events=${data.trips.results[i].vehicle_events}"
						}
					} /* end for */
				} /* end if data.trips._metadata.count */                    
				sendEvent name: "verboseTrace", displayed: ((settings.trace)?: false), value:
					"getTrips>done for vehicleId=${vehicleId}"
			} else {
				log.error "getTrips>error=${statusCode.toString()} for vehicleId=${vehicleId}"
				sendEvent name: "verboseTrace", value:"getTrips>error=${statusCode.toString()} for vehicleId=${vehicleId}"
			} /* end if statusCode */               
		}  /* end api call */              
	} /* end while */

	def tripsDataJson=""
	if (tripsData != []) {
		tripsDataJson = new groovy.json.JsonBuilder(tripsData)
	}
	
	sendEvent(name: "tripsList", value: "${tripsList}", displayed: ((settings.trace)?: false))
	sendEvent(name: "totalDistanceMInPeriod", value: "${totalDistanceM.toString()}", displayed: ((settings.trace)?: false))
	sendEvent(name: "totalFuelVolumeLInPeriod", value: "${totalFuelVolumeL.toString()}", displayed: ((settings.trace)?: false))
	sendEvent(name: "totalCostUsdInPeriod", value: "${totalCostUsd.toString()}", displayed: ((settings.trace)?: false))
	sendEvent(name: "totalDurationSInPeriod", value: "${totalDurationS.toString()}", displayed: ((settings.trace)?: false))
	sendEvent(name: "totalAverageKmplInPeriod", value: "${totalAverageKmpl.toString()}", displayed: ((settings.trace)?: false))
	sendEvent(name: "totalNbTripsInPeriod", value: "${nbTrips.toString()}", displayed: ((settings.trace)?: false))
	sendEvent(name: "totalHardAccelsInPeriod", value:"${totalHardAccels.toString()}", displayed: ((settings.trace)?: false))
	sendEvent(name: "totalHardBrakesInPeriod", value: "${totalHardBrakes.toString()}", displayed: ((settings.trace)?: false))
	sendEvent(name: "totalScoreSpeedingInPeriod", value: "${totalScoreSpeeding.toString()}", displayed: ((settings.trace)?: false))
	sendEvent(name: "totalScoreEventsInPeriod", value: "${totalScoreEvents.toString()}", displayed: ((settings.trace)?: false))

	// Process Next Page
	if (data?.trips?._metadata?.next) {
		if (settings.trace) {
			log.debug "getTrips>vehicleId=${vehicleId},nextPage=${data.trips._metadata.next}"
		}                
		processTripsNextPage(vehicleId,nbTrips,data.trips._metadata)			            
	} else {

		if (nbTrips >0) {
			tripsAvgDistanceM = totalDistanceM / nbTrips
			tripsAvgDurationS = totalDurationS / nbTrips
			tripsAvgCostUsd = totalCostUsd / nbTrips
			tripsAvgFuelVolumeL = totalFuelVolumeL / nbTrips
			tripsAvgAverageKmpl = totalAverageKmpl / nbTrips                         
			tripsAvgScoreSpeeding =totalScoreSpeeding / nbTrips
			tripsAvgScoreEvents =totalScoreEvents / nbTrips
		}        
        
		if (settings.trace) {
			log.trace "getTrips>nbTrips=${nbTrips}, totalDistanceM=${totalDistanceM},tripsAvgDistanceM=${tripsAvgDistanceM}" 
			log.trace "getTrips>nbTrips=${nbTrips}, totalDurationS=${totalDurationS},tripsAvgDurationS=${tripsAvgDurationS}" 
			log.trace "getTrips>nbTrips=${nbTrips}, totalCostUsd=${totalCostUsd},tripsAvgCostUsd=${tripsAvgCostUsd}" 
			log.trace "getTrips>nbTrips=${nbTrips}, totalFuelVolumeL=${totalFuelVolumeL},tripsAvgFuelVolumeL=${tripsAvgFuelVolumeL}" 
			log.trace "getTrips>nbTrips=${nbTrips}, totalAverageKmpl=${totalAverageKmpl},tripsAvgAverageKmpl=${tripsAvgAverageKmpl}" 
			log.trace "getTrips>nbTrips=${nbTrips}, totalScoreSpeeding=${totalScoreSpeeding},tripsAvgScoreSpeeding=${tripsAvgScoreSpeeding}" 
			log.trace "getTrips>nbTrips=${nbTrips}, totalScoreEvents=${totalScoreEvents},tripsAvgScoreEvents=${tripsAvgScoreEvents}" 
		}					                
		sendEvent(name: "tripsData", value: "${tripsDataJson.toString()}",displayed: ((settings.trace)?: false))
		sendEvent(name: "tripsAvgAverageKmplInPeriod", value: "${tripsAvgAverageKmpl.toString()}",displayed: ((settings.trace)?: false))
		sendEvent(name: "tripsAvgDistanceMInPeriod", value: "${tripsAvgDistanceM.toString()}",displayed: ((settings.trace)?: false))
		sendEvent(name: "tripsAvgDurationSInPeriod", value: "${tripsAvgDurationS.toString()}",displayed: ((settings.trace)?: false))
		sendEvent(name: "tripsAvgCostUsdInPeriod", value: "${tripsAvgCostUsd.toString()}",displayed: ((settings.trace)?: false))
		sendEvent(name: "tripsAvgFuelVolumeLInPeriod", value: "${tripsAvgFuelVolumeL.toString()}",displayed: ((settings.trace)?: false))
		sendEvent(name: "tripsAvgScoreSpeedingInPeriod", value: "${tripsAvgScoreSpeeding.toString()}",displayed: ((settings.trace)?: false))
		sendEvent(name: "tripsAvgScoreEventsInPeriod", value: "${tripsAvgScoreEvents.toString()}",displayed: ((settings.trace)?: false))
		    
    }

}

// 	processTripsNextPage: used when number of trips exceeds Automatic page's query limit, called by getTrips() only
// 	vehicleId - Id of the vehicle, by default the current one  
//	tripCounter - indicate the current trip record # 
//	metadata - metadata record for the overall query
private def processTripsNextPage(vehicleId,tripCounter,metadata) {
	def AUTOMATIC_SUCCESS=200
	int nbTrips=tripCounter
	def id    
    
	def tripsList=device.currentValue("tripsList")

	def totalDistanceMInPeriod =  device.currentValue("totalDistanceMInPeriod")
	def totalFuelVolumeLInPeriod =  device.currentValue("totalFuelVolumeLInPeriod")
	def totalCostUsdInPeriod =  device.currentValue("totalCostUsdInPeriod")
	def totalDurationSInPeriod =  device.currentValue("totalDurationSInPeriod")
	def totalAverageKmplInPeriod =  device.currentValue("totalAverageKmplInPeriod")
	def totalHardAccelsInPeriod= device.currentValue("totalHardAccelsInPeriod")
	def totalHardBrakesInPeriod = device.currentValue("totalHardBrakesInPeriod")
	def totalScoreSpeedingInPeriod = device.currentValue("totalScoreSpeedingInPeriod")
	def totalScoreEventsInPeriod = device.currentValue("totalScoreEventsInPeriod")
    
	float totalFuelVolumeL =  (totalFuelVolumeLInPeriod)? totalFuelVolumeLInPeriod.toFloat():0
	float totalCostUsd = (totalCostUsdInPeriod)?totalCostUsdInPeriod.toFloat():0 
	float totalDistanceM =  (totalDistanceMInPeriod)?totalDistanceMInPeriod.toFloat():0
	float totalDurationS =  (totalDurationSInPeriod)?totalDurationSInPeriod.toFloat():0
	float totalAverageKmpl = (totalAverageKmplInPeriod)?totalAverageKmplInPeriod.toFloat():0
	long totalHardAccels= (totalHardAccelsInPeriod)?totalHardAccelsInPeriod.toLong():0
	long totalHardBrakes = (totalHardBrakesInPeriod)?totalHardBrakesInPeriod.toLong():0
	long totalScoreSpeeding = (totalScoreSpeedingInPeriod)? totalScoreSpeedingInPeriod.toLong():0
	long totalScoreEvents = (totalScoreEventsInPeriod)?totalScoreEventsInPeriod.toLong():0

	int j=0        
	def statusCode=true
	while ((statusCode!= AUTOMATIC_SUCCESS) && (j++ <2)) { // retries once if api call fails
		doRequest(metadata.next,null,'get') {resp->
			statusCode = resp.status        
			if (statusCode == AUTOMATIC_SUCCESS) {
 				data?.trips=resp.data
				if (settings.trace) {
					log.debug "processTripsNextPage>nbTrips=${nbTrips},metadata=${data.trips._metadata}" 
				}				                
                
				data.trips.results.each {
					nbTrips++                    
					id= it.id
					def startedAt=it.started_at
					def endedAt=it.ended_at
					def distanceM=it.distance_m
					def durationS=it.duration_s
					def vehicleURL=it.vehicle
					def startLocation=it.start_location
					def startAddress=it.start_address
					def endLocation=it.end_location
					def endAddress=it.end_address
					def startTimezone=it.start_timezone       		
					def endTimezone=it.end_timezone       		
					def tags= it.tags       		
					def fuelCostUsd=it.fuel_cost_usd
					def fuelVolumeL=it.fuel_volume_l                    
					def averageKmpl=it.average_kmpl
					def hardAccels=it.hard_accels
					def hardBrakes=it.hard_brakes
					def scoreSpeeding=it.score_speeding
					def scoreEvents=it.score_events

					if (averageKmpl) {
						totalAverageKmpl =totalAverageKmpl +averageKmpl.toFloat()                        
					}	
					if (distanceM) {
						totalDistanceM =totalDistanceM +distanceM.toFloat()
					}
					if (durationS) {
						totalDurationS =totalDurationS +durationS.toFloat()
					}
					if (fuelCostUsd) {
						totalCostUsd =totalCostUsd +fuelCostUsd.toFloat()
					}
					if (fuelVolumeL) {
						totalFuelVolumeL =totalFuelVolumeL +fuelVolumeL.toFloat()
					}
					if (hardAccels) {
						totalHardAccels =totalHardAccels + hardAccels.toInteger()
					}
					if (hardBrakes) {
						totalHardBrakes =totalHardBrakes + hardBrakes.toInteger()
					}
					if (scoreSpeeding) {
						totalScoreSpeeding =totalScoreSpeeding + scoreSpeeding.toInteger()
					}
					if (scoreEvents) {
						totalScoreEvents =totalScoreEvents + scoreEvents.toInteger()
					}
					                    
					tripsList = tripsList + id + ','                     
					if (settings.trace) {
						log.debug "processTripsNextPage>tripId=${id},startedAt=${startedAt},endedAt=${endedAt}," +
							"distanceM=${distanceM},durationS=${durationS},vehicleURL=${vehicleURL},startLocation=${startLocation},startAddress=${startAddress}," +
							"endLocation=${endLocation},endAddress=${endAddress},startTimezone=${startTimezone},endTimezone=${endTimezone}," +       		
							"averageKmpl=${averageKmpl},fuelVolumeL=${fuelVolumeL},fuelCostUsd=${fuelCostUsd}," +
							"hardAccels=${hardAccels},hardBrakes=${hardBrakes},scoreSpeeding=${scoreSpeeding},scoreEvents=${scoreEvents}\n" +
							"vehicle_events=${it.vehicle_events}"
						sendEvent name: "verboseTrace", value: "processTripsNextPage>tripId=${id},startedAt=${startedAt},endedAt=${endedAt}," +
							"distanceM=${distanceM},durationS=${durationS},vehicleURL=${vehicleURL},startLocation=${startLocation},startAddress=${startAddress}," +
							"endLocation=${endLocation},endAddress=${endAddress},startTimezone=${startTimezone},endTimezone=${endTimezone}," +       		
							"averageKmpl=${averageKmpl},fuelVolumeL=${fuelVolumeL},fuelCostUsd=${fuelCostUsd}," +
							"hardAccels=${hardAccels},hardBrakes=${hardBrakes},scoreSpeeding=${scoreSpeeding},scoreEvents=${scoreEvents}\n" +
							"vehicle_events=${it.vehicle_events}"
						}
                        
				} /* end each */                    
				sendEvent name: "verboseTrace", displayed: ((settings.trace)?: false), value:
					"getTrips>done for vehicleId=${vehicleId}"
			} else {
				log.error "getTrips>error=${statusCode.toString()} for vehicleId=${vehicleId}"
				sendEvent name: "verboseTrace", value:"getTrips>error=${statusCode.toString()} for vehicleId=${vehicleId}"
			} /* end if statusCode */               
		}  /* end api call */              
	} /* end while */

	sendEvent(name: "tripsList", value: "${tripsList.toString()}",displayed: ((settings.trace)?: false))
	sendEvent(name: "totalDistanceMInPeriod", value: "${totalDistanceM.toString()}",displayed: ((settings.trace)?: false))
	sendEvent(name: "totalFuelVolumeLInPeriod", value: "${totalFuelVolumeL.toString()}",displayed: ((settings.trace)?: false))
	sendEvent(name: "totalCostUsdInPeriod", value: "${totalCostUsd.toString()}",displayed: ((settings.trace)?: false))
	sendEvent(name: "totalDurationSInPeriod", value: "${totalDurationS.toString()}",displayed: ((settings.trace)?: false))
	sendEvent(name: "totalAverageKmplInPeriod", value: "${totalAverageKmpl.toString()}",displayed: ((settings.trace)?: false))
	sendEvent(name: "totalHardAccelsInPeriod", value:"${totalHardAccels.toString()}",displayed: ((settings.trace)?: false))
	sendEvent(name: "totalHardBrakesInPeriod", value: "${totalHardBrakes.toString()}",displayed: ((settings.trace)?: false))
	sendEvent(name: "totalScoreSpeedingInPeriod", value: "${totalScoreSpeeding.toString()}",displayed: ((settings.trace)?: false))
	sendEvent(name: "totalScoreEventsInPeriod", value: "${totalScoreEvents.toString()}",displayed: ((settings.trace)?: false))
	
    // Process Next Page if any (recursive loop)
	if (data.trips._metadata.next) {
		if (settings.trace) {
			log.debug "processTripsNextPage>vehicleId=${vehicleId},nextPage=${data.trips._metadata.next}"
		}                
		processTripsNextPage(vehicleId,nbTrips,data.trips._metadata)			            
	} else {
    
		float tripsAvgDistanceM = totalDistanceM / nbTrips
		float tripsAvgDurationS = totalDurationS / nbTrips
		float tripsAvgCostUsd = totalCostUsd / nbTrips
		float tripsAvgFuelVolumeL = totalFuelVolumeL / nbTrips
		float tripsAvgAverageKmpl = totalAverageKmpl / nbTrips                         
		float tripsAvgScoreSpeeding =totalScoreSpeeding / nbTrips
		float tripsAvgScoreEvents =totalScoreEvents / nbTrips
	
		if (settings.trace) {
			log.trace "processTripsNextPage>nbTrips=${nbTrips}, totalDistanceM=${totalDistanceM},tripsAvgDistanceM=${tripsAvgDistanceM}" 
			log.trace "processTripsNextPage>nbTrips=${nbTrips}, totalDurationS=${totalDurationS},tripsAvgDurationS=${tripsAvgDurationS}" 
			log.trace "processTripsNextPage>nbTrips=${nbTrips}, totalCostUsd=${totalCostUsd},tripsAvgCostUsd=${tripsAvgCostUsd}" 
			log.trace "processTripsNextPage>nbTrips=${nbTrips}, totalFuelVolumeL=${totalFuelVolumeL},tripsAvgFuelVolumeL=${tripsAvgFuelVolumeL}" 
			log.trace "processTripsNextPage>nbTrips=${nbTrips}, totalAverageKmpl=${totalAverageKmpl},tripsAvgAverageKmpl=${tripsAvgAverageKmpl}" 
			log.trace "processTripsNextPage>nbTrips=${nbTrips}, totalScoreSpeeding=${totalScoreSpeeding},tripsAvgScoreSpeeding=${tripsAvgScoreSpeeding}" 
			log.trace "processTripsNextPage>>nbTrips=${nbTrips}, totalScoreEvents=${totalScoreEvents},tripsAvgScoreEvents=${tripsAvgScoreEvents}" 
		}					                
        
		sendEvent(name: "tripsAvgAverageKmplInPeriod", value: "${tripsAvgAverageKmpl.toString()}",displayed: ((settings.trace)?: false))
		sendEvent(name: "tripsAvgDistanceMInPeriod", value: "${tripsAvgDistanceM.toString()}",displayed: ((settings.trace)?: false))
		sendEvent(name: "tripsAvgDurationSInPeriod", value: "${tripsAvgDurationS.toString()}",displayed: ((settings.trace)?: false))
		sendEvent(name: "tripsAvgCostUsdInPeriod", value: "${tripsAvgCostUsd.toString()}",displayed: ((settings.trace)?: false))
		sendEvent(name: "tripsAvgFuelVolumeLInPeriod", value: "${tripsAvgFuelVolumeL.toString()}",displayed: ((settings.trace)?: false))
		sendEvent(name: "tripsAvgScoreSpeedingInPeriod", value: "${tripsAvgScoreSpeeding.toString()}",displayed: ((settings.trace)?: false))
		sendEvent(name: "tripsAvgScoreEventsInPeriod", value: "${tripsAvgScoreEvents.toString()}",displayed: ((settings.trace)?: false))
		    
    }

}

// 	vehicleId - Id of the vehicle, by default, retrieve all vehicles under a user account 
//	postData - indicates whether the data should be posted as event
void getVehicles(vehicleId, postData=false) {
	def AUTOMATIC_SUCCESS=200
	def vehiclesData=[]
	def vehiclesList=""
	def bodyReq=""
	def statusCode=true
	int j=0        
	def id    

	if (vehicleId) {
		bodyReq = "${vehicleId}/" 
	}        
	if (settings.trace) {
		log.debug "getVehicles>bodyReq=${bodyReq}, vehicleId=$vehicleId" 
	}				                
    
	while ((statusCode!= AUTOMATIC_SUCCESS) && (j++ <2)) { // retries once if api call fails
		api('vehicles', bodyReq) {resp->
			statusCode= resp.status
			if (statusCode == AUTOMATIC_SUCCESS) {
				if (settings.trace) {
					log.debug "getVehicles>resp.data=${resp.data}" 
				}				                
				if (!resp.data._metadata) {
					// just one vehicle was found                
					data?.vehicles?.results[0]=resp.data
					data.vehicles?._metadata?.count=1                    
				} else {
					data?.vehicles=resp.data
				}     
                
				if (data.vehicles._metadata.count) {            
					for (i in 0..data.vehicles._metadata.count -1) {
						id = data.vehicles.results[i].id
						def make = data.vehicles.results[i].make
						def model = data.vehicles.results[i].model
						def submodel = data.vehicles.results[i].submodel
						def year = data.vehicles.results[i].year
						def color = data.vehicles.results[i].color
						def displayName = data.vehicles.results[i].display_name
						if (settings.trace) {
							sendEvent name: "verboseTrace", value:"getVehicles>vehicleId=${id},make=${make},model=${model}," +
								"submodel=${model},year=${year},color=${color},displayName=${displayName}"
						}
						if (postData) {
							if (settings.trace) {
								log.debug "getVehicles>adding ${data.vehicles.results[i]} to vehiclesData"
							}
							vehiclesData << data.vehicles.results[i]  // to be transformed into Json later
						}
						vehiclesList = vehiclesList + id + ','                     
					} /* end for vehicle */                
					sendEvent name: "verboseTrace", displayed: ((settings.trace)?: false), value:
						"getVehicles>done for vehicleId=${vehicleId}"
				} /* end if metadata.vehicles.count */         
			} else {
				log.error "getVehicles>error=${statusCode.toString()}"
				sendEvent name: "verboseTrace", value:"getVehicles>error=${statusCode.toString()} for vehicleId=${vehicleId}"
			}                
		}  /* end api call */              
	} /* end while */
	def vehiclesDataJson=""
 
	if (vehiclesData != []) {
    
		vehiclesDataJson = new groovy.json.JsonBuilder(vehiclesData)
	}
/*	
	if (settings.trace) {
		log.debug "getVehicles>vehiclesDataJson=${vehiclesDataJson}"
	}
*/    
	def vehiclesListEvents = [
		vehiclesData: "${vehiclesDataJson.toString()}",
		vehiclesList: "${vehiclesList.toString()}"
	]
/*    
	if (settings.trace) {
		log.debug "getVehicles>vehiclesListEvents to be sent= ${vehiclesListEvents}"
	}
*/    
	generateEvent(vehiclesListEvents)

}

private def getEventName(type) {

	def events = [
		'ignition:on': 'Ignition On',
		'ignition:off': 'Ignition Off',
		'trip:finished': 'Trip Completed',
		'notification:speeding': 'Speed Exceeded Threshold',
		'notification:hard_brake': 'Hard Brake',
		'notification:hard_accel': 'Hard Acceleration',
		'region:changed': 'Region Changed',
		'parking:changed': 'Parking Location Changed',
		'mil:on': 'MIL (check engine light) On',
		'mil:off': 'MIL (check engine light) Cleared',
		'hmi:interaction': 'Car Interaction',
		'location:updated': 'Location Updated',
		'trip_finished': 'Trip Completed',
		'speeding': 'Speed Exceeded Threshold',
		'hard_brake': 'Hard Brake',
		'hard_accel': 'Hard Acceleration',
		'region_changed': 'Region Changed',
		'mil_on': 'MIL On(check engine light)',
		'mil_off': 'MIL Cleared(check engine light)',
	]    

	def event
	try {
		event = events.getAt(type)
	} catch (any) {
		event = 'unknown'
	}          
	return event
}

// trip - trip record
void generateVehicleEvents(trip) {
    
	def vehicleId=determine_vehicle_id(trip.vehicle)
                
    
	if (trip.vehicle_events) {
		log.debug "generateVehicleEvents>vehicleId= ${vehicleId}"
    
		trip.vehicle_events.each {
			def eventType=it.type
			switch (eventType) {
				case 'speeding': 
					generateEventSpeeding(vehicleId,eventType,trip.id,it)
					break
				case 'hard_accel': 
					generateEventHardAccel(vehicleId,eventType,trip.id,it)
					break
				case 'hard_brake': 
					generateEventHardBrake(vehicleId,eventType, trip.id, it)
					break
				default:
					log.error "generateVehicleEvents>event type unknown:${it.type}, data=${it}"
			} /* end switch */            
		} /* end each loop */                
	} /* end if vehicle_events */               
	generateEventTripFinished(vehicleId,'trip_finished',trip.id,trip)

	log.debug "generateVehicleEvents>done for vehicleId ${vehicleId}"

}

// 	vehicleId - Id of the vehicle, by default the current one  
// 	eventFields - event record as generated by Automatic for RT events or in vehicle_events field when calling getTrip()
void generateVehicleRTEvents(vehicleId, eventFields) {
	def eventType=eventFields.type
    
	vehicleId=determine_vehicle_id(vehicle_id)
    
	if (eventFields) {
		log.debug "generateVehicleRTEvents>vehicleId= ${vehicleId}"
		switch (eventType) {
			case 'trip:finished': 
				generateEventTripFinished(vehicleId,eventType,eventFields.trip.id,eventFields)        
				break
			case 'notification:speeding': 
				generateEventSpeeding(vehicleId,eventType,'',eventFields)
				break
			case 'notification:hard_accel': 
				generateEventHardAccel(vehicleId,eventType,'',eventFields)
				break
			case 'notification:hard_brake': 
				generateEventHardBrake(vehicleId,eventType,'',eventFields)
				break
			case 'region:changed': 
				generateEventRegionChanged(vehicleId,eventType,eventFields)
				break
			case 'ignition:on': 
				generateEventIgnitionOn(vehicleId,eventType,eventFields)        
				break
			case 'ignition:off': 
				generateEventIgnitionOff(vehicleId,eventType,eventFields)        
				break
			case 'mil:on':
			case 'mil:off':
				generateEventMilOnOff(vehicleId,eventType,eventFields)        
				break
			default:
				log.error "generateVehicleRTEvents>event type unknown:${eventFields.eventType}, fields=${eventFields}"
            
		}                
	} /* end if eventFields */               
	log.debug "generateVehicleRTEvents>done for vehicleId ${vehicleId}"
}
// 	vehicleId - Id of the vehicle
//	eventType - type of events - see list in getEventName()  
//	tripId - Id of the trip 
// 	eventFields - event record as generated by Automatic for RT events or in vehicle_events field when calling getTrip()
private def generateEventTripFinished(vehicleId,eventType,tripId,eventFields) {
	Date previousEventDate 
    
	def type = getEventName(eventType)
	
	def vehicleEventData = [
		'eventType': eventType,    
		'eventVehicleId': vehicleId,    
		'eventTripId': tripId,
		'eventTripLocationAccuracyM': (eventType=='trip:finished')? eventFields.trip.end_location.accuracy_m.toString():
			(eventFields.end_location.accurancy_m)?eventFields.end_location.accurancy_m.toString():'',
		'eventTripLocationLat': (eventType=='trip:finished')? 
			eventFields.location.lat.toString(): (eventFields.end_location.lat)?eventFields.end_location.lat.toString():'',
		'eventTripLocationLon': (eventType=='trip:finished')? 
			eventFields.location.lon.toString(): (eventFields.end_location.lon)?eventFields.end_location.lon.toString():'',
		'eventTripDistanceM': (eventType=='trip:finished')?
			eventFields.trip.distance_m:eventFields.distance_m, 
		'eventTripAvgFuelMPG': (eventType=='trip:finished')? 
			getConsumptionInKmpl(eventFields.trip.average_mpg): eventFields.average_kmpl,
		'eventTripStartAddress': (eventType=='trip:finished')?
			eventFields.trip.start_location.name:eventFields.start_address.name,
		'eventTripEndAddress': (eventType=='trip:finished')?
			eventFields.trip.end_location.name:eventFields.end_address.name,
		'eventTripCreatedAt': (eventType=='trip:finished')?
			formatTimeInTimezone(eventFields.created_at,eventFields.time_zone):
			formatDateInLocalTime(eventFields.started_at),
		'eventTripStartedAt':(eventType=='trip:finished')?
			formatTimeInTimezone(eventFields.trip.start_time,eventFields.trip.start_time_zone):
			formatDateInLocalTime(eventFields.started_at,eventFields.start_timezone),
		'eventTripEndedAt':(eventType=='trip:finished')?
			formatTimeInTimezone(eventFields.trip.end_time,eventFields.trip.end_time_zone):
			formatDateInLocalTime(eventFields.ended_at,eventFields.end_timezone),
		'eventStartTimezone':(eventType=='trip:finished')? eventFields.trip.start_time_zone:eventFields.start_timezone,
		'eventEndTimezone':(eventType=='trip:finished')? eventFields.trip.end_time_zone:eventFields.end_timezone

	]
    
	generateEvent(vehicleEventData)

	boolean notPresent =  (device.currentValue("presence").toString() == 'not present')? true :false
	def currentEventCreatedAt = device.currentValue('eventTripEndedAt')
	def currentEventTimezone= device.currentValue('eventEndTimezone')
	Date currentEventDate= formatDateInGMTDate(currentEventCreatedAt, currentEventTimezone)

	//	Check if endAddress affects presence settings    
	if (settings.homeAddress) {
		String endAddress =  device.currentValue("eventTripEndAddress")
		// Only update if the current Event was ended after the last trip at Automatic    
		if (settings.trace) {
			log.debug "generateEventTripFinished>vehicleId ${vehicleId}, endAddress=${endAddress}, homeAddress=${settings.homeAddress}"
			log.debug "generateEventTripFinished>vehicleId ${vehicleId},previousTripTimeStamp=${state?.previousTripTimestamp},currentEventTimeStamp=${currentEventDate.getTime()}"
		}
		if ((state?.previousTripTimestamp) && ( state?.previousTripTimestamp <currentEventDate.getTime())) {
			state?.previousTripTimestamp=currentEventDate.getTime()
			if (endAddress) {
				if ((settings.homeAddress) && (endAddress.toLowerCase().contains(settings.homeAddress.toLowerCase()))) {
					if (notPresent) {
						if (settings.trace) {
							log.debug "generateEventTripFinished>vehicleId ${vehicleId}, about to send present"
							sendEvent name: "verboseTrace", value:
								"generateEventTripFinished>vehicleId ${vehicleId}, about to send present"
						}                            
						sendEvent(name: 'presence', value: 'present')        
						sendEvent(name: 'switch', value: 'on')        
					} else {
						if (settings.trace) {
							log.debug "generateEventTripFinished>vehicleId ${vehicleId}, already present"
							sendEvent name: "verboseTrace", value:
								"generateEventTripFinished>vehicleId ${vehicleId}, already present"
						}                            
					}                    
				} else {
					if (!notPresent) {
						if (settings.trace) {
							log.debug "generateEventTripFinished>vehicleId ${vehicleId}, about to send not present"
							sendEvent name: "verboseTrace", value:
								"generateEventTripFinished>vehicleId ${vehicleId}, about to send not present"
						}                            
						sendEvent(name: 'presence', value: 'not present')        
						sendEvent(name: 'switch', value: 'off')        
					} else {
						if (settings.trace) {
							log.debug "generateEventTripFinished>vehicleId ${vehicleId}, already not present"
							sendEvent name: "verboseTrace", value:
								"generateEventTripFinished>vehicleId ${vehicleId}, already not present"
						}                            
					}                    
				}                    
			} /* if endAddress */                
		} else {
			if (settings.trace) {
				log.debug "generateEventTripFinished>vehicleId ${vehicleId}, previousTripTimeStamp >= currentEventTimeStamp"	
				sendEvent name: "verboseTrace", value:
					"generateEventTripFinished>vehicleId ${vehicleId}, previousTripTimeStamp >= currentEventTimeStamp"
			}                
			if (!state?.previousTripTimestamp) {
				state?.previousTripTimestamp=currentEventDate.getTime()
			}        	
		}        
	} else {
		state?.previousTripTimestamp=currentEventDate.getTime()
		if (!notPresent) {
			if (settings.trace) {
				log.debug "generateEventTripFinished>vehicleId ${vehicleId}, no homeAddress, about to send not present"
				sendEvent name: "verboseTrace", value:
					"generateEventTripFinished>vehicleId ${vehicleId}, no homeAddress, about to send not present"
			}                
			sendEvent(name: 'presence', value: 'not present')        
			sendEvent(name: 'switch', value: 'off')        
		} else {
			if (settings.trace) {
				log.debug "generateEventTripFinished>vehicleId ${vehicleId}, no homeAddress, already not present"
				sendEvent name: "verboseTrace", value:
					"generateEventTripFinished>vehicleId ${vehicleId}, no homeAddress, already not present"
			}                
		}        
	}
}
    
// 	vehicleId - Id of the vehicle
//	eventType - type of events - see list in getEventName()  
//	tripId - Id of the trip 
// 	eventFields - event record as generated by Automatic for RT events or in vehicle_events field when calling getTrip() 
private def generateEventSpeeding(vehicleId,eventType,tripId,eventFields) {
    
	def type = getEventName(eventType)
	def vehicleEventData = [
		'eventType': eventType,    
		'eventTripId': tripId,
		'eventTripLocationLat': (eventType=='notification:speeding')? 
			eventFields.location.lat.toString(): '',
		'eventTripLocationLon': (eventType=='notification:speeding')? 
			eventFields.location.lon.toString(): '',
		'eventTripStartLocation':(eventType=='notification:speeding')?
			'' : eventFields.start_distance_m.toString(),
		'eventTripEndLocation': (eventType=='notification:speeding')? 
			'' :eventFields.end_distance_m.toString(),
		'eventTripSpeedKPH': (eventType=='notification:speeding')? 
			milesToKm(eventFields.speed_mph):eventFields.velocity_kph,
		'eventTripCreatedAt': (eventType=='notification:speeding')?
			formatTimeInTimezone(eventFields.created_at,eventFields.time_zone):
			formatDateInLocalTime(eventFields.created_at),
		'eventTripStartAddress': '',
		'eventTripEndAddress': '',
		'eventTripGForce': '',
		'eventTripDistanceM': '',
		'eventTripAvgFuelMPG': '',
		'eventTripLocationAccuracyM': (eventType=='notification:speeding')? eventFields.location.accuracy_m.toString():'',
		'eventTripStartedAt': 
			(eventType=='notification:speeding')?
				formatTimeInTimezone(eventFields.location.ts,eventFields.time_zone):
				formatDateInLocalTime(eventFields.started_at),
		'eventTripEndedAt': '',
		'eventTripCode': '',    
		'eventTripDescription': '',    
		'eventStartTimezone': (eventType=='notification:speeding') ?
 				eventFields.time_zone : '',
		'eventEndTimezone':''
	]		        

	generateEvent(vehicleEventData)

	boolean notPresent =  (device.currentValue("presence").toString() == 'not present')? true :false
	if ((eventType=='notification:speeding') && (!notPresent)) { // In RT, if present, set the presence to 'not present' as the vehicle is on the road
		def currentEventCreatedAt = device.currentValue('eventTripCreatedAt')
		def currentEventTimezone= device.currentValue('eventStartTimezone')
		Date currentEventDate= formatDateInGMTDate(currentEventCreatedAt, currentEventTimezone)
		// Only update if the current Event was created after the last trip at Automatic    
		if ((state?.previousTripTimestamp) && ( state?.previousTripTimestamp <currentEventDate.getTime())) {
			if (settings.trace) {
				log.debug 
				"generateEventSpeeding>vehicleId ${vehicleId},previous Trip timestamp=${state?.previousTripTimestamp},currentEventTimeStamp=${currentEventDate.getTime()}"+
				"about to send not present"
				sendEvent name: "verboseTrace", value:
					"generateEventSpeeding>vehicleId ${vehicleId},previous Trip timestamp=${state?.previousTripTimestamp},currentEventTimeStamp=${currentEventDate.getTime()}"+
					"about to send not present"
			}                
			sendEvent(name: 'presence', value: 'not present')        
			sendEvent(name: 'switch', value: 'off')        
		} else {
			if (settings.trace) {
				log.debug "generateEventSpeeding>vehicleId ${vehicleId}, previous trip is not set or greater than current one"
				sendEvent name: "verboseTrace", value:
					"generateEventSpeeding>vehicleId ${vehicleId}, previous trip is not set or greater than current one"
			}                
		}                    
	} else {
		if (settings.trace) {
			log.debug "generateEventSpeeding>vehicleId ${vehicleId}, ${eventType} and/or already not present"
			sendEvent name: "verboseTrace", value:
				"generateEventSpeeding>vehicleId ${vehicleId}, ${eventType} and/or already not present"
		}            
	}    
}    
// 	vehicleId - Id of the vehicle
//	eventType - type of events - see list in getEventName()  
//	tripId - Id of the trip 
// 	eventFields - event record as generated by Automatic for RT events or in vehicle_events field when calling getTrip() 
private def generateEventHardAccel(vehicleId,eventType,tripId,eventFields) {
	def type = getEventName(eventType)
	def vehicleEventData = [
		'eventType': eventType,    
		'eventTripId': tripId,
		'eventTripLocationLat': (eventType=='notification:hard_accel')? 
			eventFields.location.lat.toString(): eventFields.lat.toString(),
		'eventTripLocationLon': (eventType=='notification:hard_accel')? 
			eventFields.location.lon.toString():eventFields.lon.toString(),
		'eventTripGForce': eventFields.g_force.toString(),
		'eventTripCreatedAt': (eventType=='notification:hard_accel')?
			formatTimeInTimezone(eventFields.created_at,eventFields.time_zone):
			formatDateInLocalTime(eventFields.created_at),
		'eventTripStartLocation': '',
		'eventTripEndLocation': '',
		'eventTripStartAddress': '',
		'eventTripEndAddress': '',
		'eventTripDistanceM': '',
		'eventTripAvgFuelMPG': '',
		'eventTripSpeedKPH': '',
		'eventTripLocationAccuracyM': (eventType=='notification:hard_accel')? eventFields.location.accuracy_m.toString():'',
		'eventTripStartedAt': 
			(eventType=='notification:hard_accel')?
				formatTimeInTimezone(eventFields.location.ts,eventFields.time_zone):'',
		'eventTripEndedAt': '',
		'eventTripCode': '',    
		'eventTripDescription': '',    
		'eventStartTimezone': (eventType=='notification:hard_accel') ?
 				eventFields.time_zone : '',
		'eventEndTimezone':''
	]
	generateEvent(vehicleEventData)
	boolean notPresent =  (device.currentValue("presence").toString() == 'not present')? true :false
	if ((eventType=='notification:hard_accel') && (!notPresent)) { // In RT, if present, set the presence to 'not present' as the vehicle is on the road
		def currentEventCreatedAt = device.currentValue('eventTripCreatedAt')
		def currentEventTimezone= device.currentValue('eventStartTimezone')
		Date currentEventDate= formatDateInGMTDate(currentEventCreatedAt, currentEventTimezone)
		// Only update if the current Event was created after the last trip at Automatic    
		if ((state?.previousTripTimestamp) && ( state?.previousTripTimestamp <currentEventDate.getTime())) {
			if (settings.trace) {
				log.debug 
				"generateEventHardAccel>vehicleId ${vehicleId},previous Trip timestamp=${state?.previousTripTimestamp},currentEventTimeStamp=${currentEventDate.getTime()}"+
				"about to send not present"
				sendEvent name: "verboseTrace", value:
					"generateEventHardAccel>vehicleId ${vehicleId},previous Trip timestamp=${state?.previousTripTimestamp},currentEventTimeStamp=${currentEventDate.getTime()}" +
					"about to send not present"
			}                
			sendEvent(name: 'presence', value: 'not present')        
			sendEvent(name: 'switch', value: 'off')        
		} else {
			if (settings.trace) {
				log.debug "generateEventHardAccel>vehicleId ${vehicleId}, previous trip date is not set or greater than current one"
				sendEvent name: "verboseTrace", value:
					"generateEventHardAccel>vehicleId ${vehicleId}, previous trip date is not set or greater than current one"
			}                
		}                    
	} else {
		if (settings.trace) {
			log.debug "generateEventHardAccel>vehicleId ${vehicleId}, ${eventType} and/or already not present"
			sendEvent name: "verboseTrace", value:
				"generateEventHardAccel>vehicleId ${vehicleId}, ${eventType} and/or already not present"
		}            
	}       
}

// 	vehicleId - Id of the vehicle
//	eventType - type of events - see list in getEventName()  
//	tripId - Id of the trip 
// 	eventFields - event record as generated by Automatic for RT events or in vehicle_events field when calling getTrip() 
private def generateEventHardBrake(vehicleId,eventType,tripId,eventFields) {
	Date previousEventDate 

	def type = getEventName(eventType)
	def vehicleEventData = [
		'eventType': eventType,    
		'eventTripId': tripId,
		'eventTripLocationLat': (eventType=='notification:hard_brake')? 
			eventFields.location.lat.toString(): eventFields.lat.toString(),
		'eventTripLocationLon': (eventType=='notification:hard_brake')? 
			eventFields.location.lon.toString():eventFields.lon.toString(),
		'eventTripGForce': eventFields.g_force.toString(),
		'eventTripCreatedAt': (eventType=='notification:hard_brake')?
			formatTimeInTimezone(eventFields.created_at,eventFields.time_zone):
			formatDateInLocalTime(eventFields.created_at),
		'eventTripStartLocation': '',
		'eventTripEndLocation': '',
		'eventTripStartAddress': '',
		'eventTripEndAddress': '',
		'eventTripDistanceM': '',
		'eventTripAvgFuelMPG': '',
		'eventTripSpeedKPH': '',
		'eventTripLocationAccuracyM': (eventType=='notification:hard_brake')? eventFields.location.accuracy_m.toString():'',
		'eventTripStartedAt': 
			(eventType=='notification:hard_brake')?
				formatTimeInTimezone(eventFields.location.ts,eventFields.time_zone):'',
		'eventTripEndedAt': '',
		'eventTripCode': '',    
		'eventTripDescription': '',    
		'eventStartTimezone': (eventType=='notification:hard_brake') ?
 				eventFields.time_zone : '',
		'eventEndTimezone':''
	]
	generateEvent(vehicleEventData)
	boolean notPresent =  (device.currentValue("presence").toString() == 'not present')? true :false
	if ((eventType=='notification:hard_brake') && (!notPresent)) { // In RT, if present, set the presence to 'not present' as the vehicle is on the road
		def currentEventCreatedAt = device.currentValue('eventTripCreatedAt')
		def currentEventTimezone= device.currentValue('eventStartTimezone')
		Date currentEventDate= formatDateInGMTDate(currentEventCreatedAt,currentEventTimezone)
		// Only update if the current Event was created after the last trip at Automatic    
		if ((state?.previousTripTimestamp) && ( state?.previousTripTimestamp <currentEventDate.getTime())) {
			if (settings.trace) {
				log.debug 
				"generateEventHardBrake>vehicleId ${vehicleId},previous Trip timestamp=${state?.previousTripTimestamp},currentEventTimeStamp=${currentEventDate.getTime()}"+
				"about to send not present"
				sendEvent name: "verboseTrace", value:
					"generateEventHardBrake>vehicleId ${vehicleId},previous Trip timestamp=${state?.previousTripTimestamp},currentEventTimeStamp=${currentEventDate.getTime()}"+
					"about to send not present"
			}                
			sendEvent(name: 'presence', value: 'not present')        
			sendEvent(name: 'switch', value: 'off')        
		} else {
			if (settings.trace) {
				log.debug "generateEventHardBrake>vehicleId ${vehicleId}, previous trip date is not set or greater than current one"
				sendEvent name: "verboseTrace", value:
					"generateEventHardBrake>vehicleId ${vehicleId}, previous trip date is not set or greater than current one"
			}                
		}                    
	} else {
		if (settings.trace) {
			log.debug "generateEventHardBrake>vehicleId ${vehicleId}, ${eventType} and/or already not present"
			sendEvent name: "verboseTrace", value:
				"generateEventHardBrake>vehicleId ${vehicleId}, ${eventType} and/or already not present"
		}            
	}    
}

// 	vehicleId - Id of the vehicle
//	eventType - type of events - see list in getEventName()  
// 	eventFields - event record as generated by Automatic for RT events or in vehicle_events field when calling getTrip() 
private def generateEventIgnitionOn(vehicleId,eventType,eventFields) {
	Date previousEventDate 

	def type = getEventName(eventType)
	def vehicleEventData = [
		'eventType': eventType,    
		'eventTripId':'',
		'eventTripLocationLat':  
			eventFields.location?.lat?.toString(),
		'eventTripLocationLon':  
			eventFields.location?.lon?.toString(),
		'eventTripCreatedAt': 
			formatTimeInTimezone(eventFields.created_at,eventFields.time_zone),
		'eventTripStartLocation': '',
		'eventTripEndLocation': '',
		'eventTripStartAddress': '',
		'eventTripEndAddress': '',
		'eventTripDistanceM': '',
		'eventTripAvgFuelMPG': '',
		'eventTripSpeedKPH': '',
		'eventTripLocationAccuracyM': eventFields.location.accuracy_m.toString(),
		'eventTripStartedAt': 
			formatTimeInTimezone(eventFields.location.ts,eventFields.time_zone),
		'eventTripEndedAt': '',
		'eventTripCode': '',    
		'eventTripDescription': '',    
		'eventStartTimezone':eventFields.time_zone,
		'eventEndTimezone':''
	]
	generateEvent(vehicleEventData)
	boolean notPresent =  (device.currentValue("presence").toString() == 'not present')? true :false
	if (!notPresent) { // if present, set the presence to 'not present' as the vehicle is on the road
		def currentEventCreatedAt = device.currentValue('eventTripCreatedAt')
		def currentEventTimezone= device.currentValue('eventStartTimezone')
		Date currentEventDate= formatDateInGMTDate(currentEventCreatedAt, currentEventTimezone)
		// Only update if the current Event was created after the last trip at Automatic    
		if ((state?.previousTripTimestamp) && ( state?.previousTripTimestamp <currentEventDate.getTime())) {
			if (settings.trace) {
				log.debug 
				"generateEventIgnitionOn>vehicleId ${vehicleId},previous Trip timestamp=${state?.previousTripTimestamp},currentEventTimeStamp=${currentEventDate.getTime()}"+
				"about to send not present"
				sendEvent name: "verboseTrace", value:
					"generateEventIgnitionOn>vehicleId ${vehicleId},previous Trip timestamp=${state?.previousTripTimestamp},currentEventTimeStamp=${currentEventDate.getTime()}"+
					"about to send not present"
			}                
			sendEvent(name: 'presence', value: 'not present')        
			sendEvent(name: 'switch', value: 'off')        
		} else {
			if (settings.trace) {
				log.debug "generateEventIgnitionOn>vehicleId ${vehicleId}, previous trip date is not set or greater than current one"
				sendEvent name: "verboseTrace", value:
					"generateEventIgnitionOn>vehicleId ${vehicleId}, previous trip date is not set or greater than current one"
			}                
		}                    
	} else {
		if (settings.trace) {
			log.debug "generateEventIgnitionOn>vehicleId ${vehicleId}, ${eventType} and/or already not present"
			sendEvent name: "verboseTrace", value:
				"generateEventIgnitionOn>vehicleId ${vehicleId}, ${eventType} and/or already not present"
		}            
	}    
}

// 	vehicleId - Id of the vehicle
//	eventType - type of events - see list in getEventName()  
// 	eventFields - event record as generated by Automatic for RT events or in vehicle_events field when calling getTrip() 
private def generateEventIgnitionOff(vehicleId,eventType,eventFields) {
	def type = getEventName(eventType)
	def vehicleEventData = [
		'eventType': eventType,    
		'eventTripId':'',
		'eventTripLocationLat':  
			eventFields.location?.lat?.toString(),
		'eventTripLocationLon':  
			eventFields.location?.lon?.toString(),
		'eventTripCreatedAt': 
			formatTimeInTimezone(eventFields.created_at,eventFields.time_zone),
		'eventTripStartLocation': '',
		'eventTripEndLocation': '',
		'eventTripStartAddress': '',
		'eventTripEndAddress': '',
		'eventTripDistanceM': '',
		'eventTripAvgFuelMPG': '',
		'eventTripSpeedKPH': '',
		'eventTripLocationAccuracyM': eventFields.location.accuracy_m.toString(),
		'eventTripStartedAt': 
			formatTimeInTimezone(eventFields.location.ts,eventFields.time_zone),
		'eventTripEndedAt': '',
		'eventTripCode': '',    
		'eventTripDescription': '',    
		'eventStartTimezone':eventFields.time_zone,
		'eventEndTimezone':''
	]
	generateEvent(vehicleEventData)
    
}

// 	vehicleId - Id of the vehicle
//	eventType - type of events - see list in getEventName()  
//	tripId - Id of the trip 
// 	eventFields - event record as generated by Automatic for RT events or in vehicle_events field when calling getTrip() 
private def generateEventRegionChanged(vehicleId,eventType,tripId,eventFields) {
	def type = getEventName(eventType)	
	def vehicleEventData = [
		'eventType': eventType,    
		'eventVehicleId': vehicleId,    
		'eventTripCreatedAt': (eventType=='region:changed')?
			formatTimeInTimezone(eventFields.created_at,eventFields.time_zone):
			formatDateInLocalTime(eventFields.created_at),
		'eventTripId': tripId,    
		'eventTripLocationLat': (eventType=='region:changed')?
			eventFields.location.lat.toString(): eventFields.lat.toString(),
		'eventTripLocationLon': (eventType=='region:changed')? 
			eventFields.location.lon.toString():eventFields.lon.toString(),
		'eventTripRegionStatus': (eventType=='region:changed')?
			eventFields.region.status:eventFields.region.status,
		'eventTripRegionName': (eventType=='region:changed')?
			eventFields.region.name:eventFields.region.name,
		'eventTripRegionTag': (eventType=='region:changed')?
			eventFields.region.tag: eventFields.region.tag,        
		'eventTripStartLocation': '',
		'eventTripEndLocation': '',
		'eventTripStartAddress': '',
		'eventTripEndAddress': '',
		'eventTripDistanceM': '',
		'eventTripAvgFuelMPG': '',
		'eventTripSpeedKPH': '',
		'eventTripLocationAccuracyM': '',
		'eventTripStartedAt': (eventType=='region:changed')?
			formatTimeInTimezone(eventFields.location.ts,eventFields.time_zone):'',
		'eventTripEndedAt': '',
		'eventTripCode': '',    
		'eventTripDescription': '',    
		'eventStartTimezone': (eventType=='region:changed')?
 			eventFields.time_zone: '',
		'eventEndTimezone':''
	]
	generateEvent(vehicleEventData)
	boolean notPresent =  (device.currentValue("presence").toString() == 'not present')? true :false
	if ((eventType=='region:changed') && (!notPresent)) { // In RT, if present, set the presence to 'not present' as the vehicle is on the road
		def currentEventCreatedAt = device.currentValue('eventTripCreatedAt')
		def currentEventTimezone= device.currentValue('eventStartTimezone')
		Date currentEventDate= formatDateInGMTDate(currentEventCreatedAt, currentEventTimezone)
		// Only update if the current Event was created after the last trip at Automatic    
		if ((state?.previousTripTimestamp) && ( state?.previousTripTimestamp <currentEventDate.getTime())) {
			if (settings.trace) {
				log.debug 
				"generateEventRegionChanged>vehicleId ${vehicleId},previous Trip timestamp=${state?.previousTripTimestamp},currentEventTimeStamp=${currentEventDate.getTime()}"+
				"about to send not present"
				sendEvent name: "verboseTrace", value:
					"generateEventRegionChanged>vehicleId ${vehicleId},previous Trip timestamp=${state?.previousTripTimestamp},currentEventTimeStamp=${currentEventDate.getTime()}"+
					"about to send not present"
			}                
			sendEvent(name: 'presence', value: 'not present')        
			sendEvent(name: 'switch', value: 'off')        
		} else {
			if (settings.trace) {
				log.debug "generateEventRegionChanged>vehicleId ${vehicleId}, previous trip date is not set or greater than current one"
				sendEvent name: "verboseTrace", value:
					"generateEventRegionChanged>vehicleId ${vehicleId}, previous trip date is not set or greater than current one"
			}                
		}                    
	} else {
		if (settings.trace) {
			log.debug "generateEventRegionChanged>vehicleId ${vehicleId}, ${eventType} and/or already not present"
			sendEvent name: "verboseTrace", value:
				"generateEventRegionChanged>vehicleId ${vehicleId}, ${eventType} and/or already not present"
		}            
	}    
}

// 	vehicleId - Id of the vehicle
//	eventType - type of events - see list in getEventName()  
//	tripId - Id of the trip 
// 	eventFields - event record as generated by Automatic for RT events or in vehicle_events field when calling getTrip() 
private def generateEventMilOnOff(vehicleId,eventType,eventFields) {
	def type = getEventName(eventType)
	
	eventFields.dtcs.each {
		def vehicleEventData = [
			'eventVehicleId': vehicleId,    
			'eventTripId': '',    
			'eventTripCreatedAt': ((eventType=='mil:on') || (eventType=='mil:off')) ?
				formatTimeInTimezone(eventFields.created_at,eventFields.time_zone):
				formatDateInLocalTime(eventFields.created_at),
			'eventType': eventType,    
			'eventTripLocationLat': ((eventType=='mil:on') || (eventType=='mil:off')) ? 
				eventFields.location.lat.toString(): eventFields.lat.toString(),
			'eventTripLocationLon': ((eventType=='mil:on') || (eventType=='mil:off')) ? 
				eventFields.location.lon.toString():eventFields.lon.toString(),
			'eventTripCode': it.code,    
			'eventTripDescription': it.description,    
			'eventTripStartLocation': '',
			'eventTripEndLocation': '',
			'eventTripStartAddress': '',
			'eventTripEndAddress': '',
			'eventTripDistanceM': '',
			'eventTripAvgFuelMPG': '',
			'eventTripSpeedKPH': '',
			'eventTripLocationAccuracyM': ((eventType=='mil:on') || (eventType=='mil:off'))? 	
				eventFields.location.accuracy_m.toString():'',
			'eventTripStartedAt': ((eventType=='mil:on') || (eventType=='mil:off')) ?
				formatTimeInTimezone(eventFields.location.ts,eventFields.time_zone):'',
			'eventTripEndedAt': '',
			'eventStartTimezone':((eventType=='mil:on') || (eventType=='mil:off')) ?
 				eventFields.time_zone : '',
			'eventEndTimezone':''
		]
		generateEvent(vehicleEventData)
		boolean notPresent =  (device.currentValue("presence").toString() == 'not present')? true :false
		if (((eventType=='mil:on') || (eventType=='mil:off')) && (!notPresent)) { // In RT, if present, set the presence to 'not present' as the vehicle is on the road
			def currentEventCreatedAt = device.currentValue('eventTripCreatedAt')
			def currentEventTimezone= device.currentValue('eventStartTimezone')
			Date currentEventDate= formatDateInGMTDate(currentEventCreatedAt, currentEventTimezone)
			// Only update if the current Event was created after the last trip at Automatic    
			if ((state?.previousTripTimestamp) && ( state?.previousTripTimestamp <currentEventDate.getTime())) {
				if (settings.trace) {
					log.debug 
					"generateEventMilOnOff>vehicleId ${vehicleId},previous Trip timestamp=${state?.previousTripTimestamp},currentEventTimeStamp=${currentEventDate.getTime()}"+
					"about to send not present"
					sendEvent name: "verboseTrace", value:
						"generateEventMilOnOff>vehicleId ${vehicleId},previous Trip timestamp=${state?.previousTripTimestamp},currentEventTimeStamp=${currentEventDate.getTime()}"+
						"about to send not present"
				}                    
				sendEvent(name: 'presence', value: 'not present')        
				sendEvent(name: 'switch', value: 'off')        
			} else {
				if (settings.trace) {
					log.debug "generateEventMilOnOff>vehicleId ${vehicleId}, previous trip date is not set or greater than current one"
					sendEvent name: "verboseTrace", value:
						"generateEventMilOnOff>vehicleId ${vehicleId}, previous trip date is not set or greater than current one"
				}                    
			}                    
		} else {
			if (settings.trace) {
				log.debug "generateEventMilOnOff>vehicleId ${vehicleId}, ${eventType} and/or already not present"
				sendEvent name: "verboseTrace", value:
					"generateEventMilOnOff>vehicleId ${vehicleId}, ${eventType} and/or already not present"
			}                
		} 
 	} /* end each loop */   
}

void getCurrentUserInfo() {
	def AUTOMATIC_SUCCESS=200

	def statusCode=true
	int j=0        
	while ((statusCode != AUTOMATIC_SUCCESS) && (j++ <2)) { // retries once if api call fails

		if (settings.trace) {
			log.debug "getCurrentUserInfo>about to call api"
		}
		api('currentUserInfo', null) {resp ->
			statusCode = resp.status        
			if (statusCode == AUTOMATIC_SUCCESS) {
        			data?.user=resp.data
				if (settings.trace) {
					log.debug "getCurrentUserInfo>resp data = ${resp.data}" 
                
				}
				def userid = data.user.id
				def username = data.user.username
				def email = data.user.email
				def firstName = data.user.first_name  
				def lastName = data.user.last_name  
				if (settings.trace) {
					sendEvent name: "verboseTrace", value: "getCurrentUserInfo>userId=${userId},name=${username},email=${email},firstName=${firstName},lastName=${lastName}"
				}
				sendEvent name: "verboseTrace", displayed: ((settings.trace)?: false), value:
					"getCurrentUserInfo>done"
			} else {
				log.error "getCurrentUserInfo>error=${statusCode.toString()}"
				sendEvent name: "verboseTrace", value:"getCurrentUserInfo>error=${statusCode.toString()}"
			} /* end if statusCode */                
		}  /* end api call */              
	} /* end while */
}


private def refresh_tokens() {
        
	def method = 
	[
		headers: [
			'Content-Type': "application/json",
			'charset': "UTF-8"
			],
		uri: "${get_URI_ACCOUNT_ROOT()}/oauth/access_token?", 
		body: toQueryString([grant_type:"refresh_token",client_id:get_appKey(),client_secret:get_refresh_token()])
	]
	if (settings.trace) {
		log.debug "refresh_tokens> uri = ${method.uri}"
	}
	def successRefreshTokens = {resp ->
		if (settings.trace) {
			log.debug "refresh_tokens> response = ${resp.data}"
		}
		data.auth.access_token = resp.data.access_token
		data.auth.refresh_token = resp.data.refresh_token
		data.auth.expires_in = resp.data.expires_in
		data.auth.token_type = resp.data.token_type
		data.auth.scope = resp.data.scope
	}
	try {
		httpPostJson(method, successRefreshTokens)
	} catch (java.net.UnknownHostException e) {
		log.error "refresh_tokens> Unknown host - check the URL " + method.uri
		sendEvent name: "verboseTrace", value: "refresh_tokens> Unknown host"
		return false
	} catch (java.net.NoRouteToHostException t) {
		log.error "refresh_tokens> No route to host - check the URL " + method.uri
		sendEvent name: "verboseTrace", value: "refresh_tokens> No route to host"
		return false
	} catch (java.io.IOException e) {
		log.error "refresh_tokens>Authentication error, automatic servers cannot be reached at " + method.uri
		sendEvent name: "verboseTrace", value: "refresh_tokens>Authentication error, automatic servers cannot be reached at " + method.uri
		return false
	} catch (any) {
		log.error "refresh_tokens> general error " + method.uri
		sendEvent name: "verboseTrace", value:
			"refresh_tokens> general error at ${method.uri}"
		return false
	}
	def authexptime = new Date((now() + (365 * 24 * 60 * 1000))).getTime() // token expires in a year
	data.auth.authexptime = authexptime

	if (data.auth.sensorId) {		// Created by initalSetup, need to refresh Parent tokens and other children
		refreshParentTokens()
	}        
	if (settings.trace) {
		log.debug "refresh_tokens> data_auth.expires_in in time = ${authexptime}"
		sendEvent name: "verboseTrace", value:
			"refresh_tokens>expire in ${data.auth.expires_in} minutes"
	}
	return true
}

void refreshChildTokens(auth) {
	if (settings.trace) {
		log.debug "refreshChildTokens>begin token auth= $auth"
	}
	data.auth.access_token = auth.authToken
	data.auth.refresh_token = auth.refreshToken
	data.auth.expires_in = auth.expiresIn
	data.auth.token_type = auth.tokenType
	data.auth.scope = auth.scope
	data.auth.authexptime = auth.authexptime
	if (settings.trace) {
		log.debug "refreshChildTokens>end data.auth=$data.auth"
	}
}

private void refreshParentTokens() {
	if (settings.trace) {
		log.debug "refreshParentTokens>begin data.auth = ${data.auth}"
	}
	if (settings.trace) {
		log.debug "refreshParentTokens>auth=$auth, about to call parent.setParentAuthTokens"
	}         
	parent.setParentAuthTokens(data.auth)
	if (settings.trace) {
		log.debug "refreshParentTokens>end"
	}         
}

private void login() {
	if (data?.auth.vehicleId) {
    	// Created by initalSetup
		if (settings.trace) {
			log.debug "login> about to call refreshThisChildAuthTokens"
		}
		parent.refreshThisChildAuthTokens(this)
	} else { 
		if (settings.trace) {
			log.debug "login> about to call setAuthTokens, data auth=$data?.auth"
		}
		setAuthTokens()
	}    
	if (!isLoggedIn()) {
		if (settings.trace) {
			log.debug "login> no auth_token..., failed"
		}
		return
	}
}



void setAuthTokens() {
	def method = 
	[
		headers: [
			'charset': "UTF-8",
			'Content-Type': "application/x-www-form-urlencoded"
			],
		uri: "${get_URI_ACCOUNT_ROOT()}/oauth/access_token?",
		body: toQueryString([grant_type:"client_credentials",client_id:get_appKey(),client_secret:get_client_secret()])
	]
	def successTokens = {resp ->
		if (settings.trace) {
			log.debug "setAuthTokens> resp data= ${resp.data}"
		}
        
        
		data?.auth = resp.data
		data.auth.access_token = resp.data.access_token
		data.auth.expires_in = resp.data.expires_in
		data.auth.token_type = resp.data.token_type
		if (settings.trace) {
			log.debug "setAuthTokens> accessToken= ${data.auth.access_token}," +
				"tokenType=${data.auth.token_type}"
		}
	}
	try {
		httpPostJson(method, successTokens)

	} catch (java.net.UnknownHostException e) {
		log.error "setAuthTokens> Unknown host - check the URL " + method.uri
		sendEvent name: "verboseTrace", value: "setAuthTokens> Unknown host " +
			method.uri
		return
	} catch (java.net.NoRouteToHostException t) {
		log.error "setAuthTokens> No route to host - check the URL " + method.uri
		sendEvent name: "verboseTrace", value: "setAuthTokens> No route to host" +
			method.uri
		return
	} catch (java.io.IOException e) {
		log.error "setAuthTokens> Auth error, automatic servers cannot be reached at " +
			method.uri
		sendEvent name: "verboseTrace", value: "setAuthTokens> Auth error " +
			method.uri
		return
	}	        

	// determine token's expire time
	def authexptime = new Date((now() + (data.auth.expires_in * 365 * 24* 60 * 1000))).getTime()
	data.auth.authexptime = authexptime
	if (settings.trace) {
		log.debug "setAuthTokens> expires in ${data.auth.expires_in} minutes"
		log.debug "setAuthTokens> data_auth.expires_in in time = ${authexptime}"
		sendEvent name: "verboseTrace", value:
			"setAuthTokens>expire in ${data.auth.expires_in} minutes"
	}
}
private def isLoggedIn() {
	if (data.auth == null) {
		if (settings.trace) {
			log.debug "isLoggedIn> no data auth"
		}
		return false
	} else {
		if (data.auth.access_token == null) {
			if (settings.trace) {
				log.debug "isLoggedIn> no access token"
				return false
			}
		}
	}
	return true
}
private def isTokenExpired() {
	def buffer_time_expiration=5   // set a 5 min. buffer time before token expiration to avoid auth_err 
	def time_check_for_exp = now() + (buffer_time_expiration * 60 * 1000);
	if (settings.trace) {
		log.debug "isTokenExpired> check expires_in: ${data.auth.authexptime} > time check for exp: ${time_check_for_exp}"
	}
	if (data.auth.authexptime > time_check_for_exp) {
		if (settings.trace) {
			log.debug "isTokenExpired> not expired"
		}
		return false
	}
	if (settings.trace) {
		log.debug "isTokenExpired> expired"
	}
	return true
}

// Determine id from settings or initalSetup
private def determine_vehicle_id(vehicle_id) {
	def vehicleId
    
	if ((vehicle_id != null) && (vehicle_id.trim() != "")) {
		vehicleId = vehicle_id.trim()
	} else if ((settings.vehicleId != null) && (settings.vehicleId.trim()  != "")) {
		vehicleId = settings.vehicleId.trim()
		if (settings.trace) {
			log.debug "determine_vehicle_id> vehicleId = ${settings.vehicleId}"
		}
	} else if (data?.auth?.vehicleId != null) {
    
		state.appKey = get_appKey() 
		settings.vehicleId = data.auth.vehicleId
		vehicleId=settings.vehicleId
		if (settings.trace) {
			log.debug "determine_vehicle_id> vehicleId from data.auth= ${vehicleId}"
		}
    
	} else  {
		settings.vehicleId = data.vehicles.results[0].id
		vehicleId=settings.vehicleId
		if (settings.trace) {
			log.debug "determine_vehicle_id> sensorId from data.vehicles.results[0].id= ${vehicleId}"
		}
	}
	return vehicleId
}

// Get the appKey for authentication
private def get_appKey() {
	return state.appKey
}    


// Get the client's refresh token

private def get_refresh_token() {
	return data.auth.refresh_token
}    

// Called by My AutomaticServiceMgr for initial creation of a child Device
void initialSetup(device_client_id, auth_data, device_automatic_id) {

	settings.trace='true'
	
	if (settings.trace) {
		log.debug "initialSetup>begin"
		log.debug "initialSetup> device_automatic_Id = ${device_automatic_id}"
		log.debug "initialSetup> device_client_id = ${device_client_id}"
	}	
	state?.appKey= device_client_id
	settings.vehicleId = device_automatic_id
	data?.auth = settings    
	data.auth.access_token = auth_data.authToken
	data.auth.refresh_token = auth_data.refreshToken
	data.auth.expires_in = auth_data.expiresIn
	data.auth.token_type = auth_data.tokenType
	data.auth.authexptime= auth_data.authexptime
	state.lastEndDate= (new Date() -1).getTime()     
    
	if (settings.trace) {
		log.debug "initialSetup> settings = $settings"
		log.debug "initialSetup> data_auth = $data.auth"
		log.debug "initialSetup>end"
	}
	getCurrentUserInfo()
	getVehicles(device_automatic_id)
	poll()
}

def toQueryString(Map m) {
	return m.collect { k, v -> "${k}=${URLEncoder.encode(v.toString())}" }.sort().join("&")
}

private def get_URI_ROOT() {
	return "https://api.automatic.com"
}

private def get_URI_ACCOUNT_ROOT() {
	return "https://accounts.automatic.com"
}

