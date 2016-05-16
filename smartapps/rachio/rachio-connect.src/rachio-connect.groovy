import groovy.json.*
import java.text.SimpleDateFormat

definition(
        name: "Rachio (Connect)",
        namespace: "rachio",
        author: "Rachio",
        description: "Connect your Rachio Sprinkler Controller to SmartThings.",
        category: "SmartThings Labs",
        iconUrl: "https://s3-us-west-2.amazonaws.com/rachio-media/smartthings/rachio-st-icon1-60px.png",
        iconX2Url: "https://s3-us-west-2.amazonaws.com/rachio-media/smartthings/rachio-st-icon1-120px.png",
        iconX3Url: "https://s3-us-west-2.amazonaws.com/rachio-media/smartthings/rachio-st-icon1-120px.png",
        singleInstance: true 
)

{
    appSetting "clientId"
}

preferences {
    page(name: "auth", title: "Rachio", nextPage: "devicePage", content: "authPage", uninstall: true)
    page(name: "devicePage", title: "Rachio", content: "devicePage", install: true, uninstall: true)
}

mappings {
    path("/oauth/initialize") { action: [GET: "init"] }
    path("/oauth/callback") { action: [ GET: "callback" ] }
}

// Begin OAuth stuff
//Section2: page-related methods ---------------------------------------------------------------------------------------
def authPage()  {
    log.debug "authPage()"
    getAccessToken()

    def description = null
    def uninstallAllowed = false
    def oauthTokenProvided = false

    //This is 3rd party cloud accessToken
    if(state.authToken) { //after exchange access_token with 3rd party cloud
        description = "You are connected."
        uninstallAllowed = true
        oauthTokenProvided = true
    } else {
        description = "Click to enter Rachio Credentials"
    }

    //redirectUrl to be called back for code exchange
    def redirectUrl = "${serverUrl}/oauth/initialize?appId=${app?.id}&access_token=${state?.accessToken}&apiServerUrl=${shardUrl}"

    if (!oauthTokenProvided) {
        log.debug "no token"
        return dynamicPage(name: "auth", title: "Login", nextPage: null, uninstall: uninstallAllowed) {
            section(){
                paragraph "Tap below to log in to the Rachio service and authorize SmartThings access. Be sure to scroll down on page 2 and press the 'Allow' button."
                href url: redirectUrl, style: "embedded", required: true, title: "Rachio", description:description
            }
        }
    } else {
        log.debug "token!! ${authToken}"  //I don't recommend displaying this in the release version
        return dynamicPage(name: "auth", title: "Log In", nextPage: "devicePage", uninstall: uninstallAllowed) {
            section(){
                paragraph "Tap Next to continue to setup your sprinklers."
                href url: redirectUrl, style: "embedded", state: "complete", title: "Rachio", description: description
            }
        }
    }

}

// This method is called after "auth" page is done with Oauth2 authorization, then page "deviceList" with content of devicePage()
def devicePage() {
    //log.trace "devicePage().."
    // Step 1: get (list) of available devices associated with the login account.
    def devices = getRachioDevices()
    //log.debug "rachioDeviceList() device list: ${devices}"

    //step2: render the page for user to select which device
    return dynamicPage(name: "devicePage", title: "Select Your Devices", uninstall: true) {
        section("Device Selection:"){
            paragraph "Tap below to select the sprinkler controller to connect to SmartThings."
            input(name: "sprinklers", title: "Select Your Sprinkler", type: "enum", description: "Tap to Select", required: true, multiple: false, options: devices, submitOnChange: true)
            if(sprinklers) {
                def zones = getZoneIds()
                def installedDesc = state?.installed ? "(${zones?.size() ?: 0}) Zone devices are installed..." : "(${zones?.size() ?: 0}) Zone devices will be created..."
                paragraph installedDesc
            }
        }
        if(sprinklers) {
            section("Preferences:") {
                paragraph "Select the Duration time to be used for manual Zone Runs (This can be changed under each zones device page)"
                input(name: "defaultZoneTime", title: "Default Zone Runtime (Minutes)", type: "number", description: "Tap to Modify", required: false, defaultValue: 10, submitOnChange: true)
            }
        }
    }
}

// This was added to handle missing oauth on the smartapp and notifying the user of why it failed.
def getAccessToken() {
    try {
        if(!state?.accessToken) { 
            state?.accessToken = createAccessToken() 
            log.debug "created access token: ${state?.accessToken}"
        }
        else { return true }
    }
    catch (ex) {
        def msg = "Error: OAuth is not Enabled for the Rachio (Connect) application!!!.  Please click remove and Enable Oauth under the SmartApp App Settings in the IDE..."
        sendPush(msg)
        log.warn "getAccessToken Exception | $msg"
        return false
    }
}

//1. redirect SmartApp to prompt user to input his/her credentials on 3rd party cloud service
def init() {
    log.debug "init()"
    def stcid = getClientId()
    log.debug stcid
    def oauthParams = [
        response_type: "code",
        client_id: stcid,
        redirect_uri: callbackUrl
    ]
     
    def loc = "${appEndpoint}/oauth?${toQueryString(oauthParams)}"
    log.debug loc
    redirect(location: loc)

}

/*2. Obtain authorization_code, access_token, refresh_token to be used with API calls
    2.1 get authorization_code from 3rd party cloud service
    2.2 use authorization_code to get access_token, refresh_token, and expire from 3rd party cloud service
*/
def callback() {
    log.debug "callback()>> params.code ${params.code}"
    def appKey = !appSettings?.clientId ? "smartthings" : appSettings.clientId
    def tokenParams = [
            headers: ["Authorization": "Basic $appKey", "Content-Type": "application/x-www-form-urlencoded"],
            uri: "${apiEndpoint}/1/oauth/token_2_0",
            body: [
                grant_type:'authorization_code', 
                code:params.code, 
                redirect_uri: callbackUrl,
                client_id : getClientId(),
                client_secret: getClientSecret()
            ],
        ]
        
    try {
       httpPost(tokenParams) { resp ->
            state.authToken = resp?.data.access_token.toString()
            state.refreshToken = resp?.data.refresh_token.toString()
            state.authTokenExpiresIn = resp?.data.expires_in.toString()
            log.debug "Response: ${resp?.data}"
        }
    } catch (groovyx.net.http.HttpResponseException e) {
        log.error "Error: ${e?.statusCode}"
        log.debug "Response headers: ${e?.response?.allHeaders}";
        log.debug "Data: ${e?.response?.data}";
    }

    if (state.authToken) {
        success()
    } else {
        fail()
    }
}

def success() {
    def message = """
        <p>Your Rachio Account is now connected to SmartThings!</p>
        <p>Click 'Done' to finish setup.</p>
    """
    connectionStatus(message)
}

def fail() {
    def message = """
        <p>The connection could not be established!</p>
        <p>Click 'Done' to return to the menu.</p>
    """
    connectionStatus(message)
}

// End OAuth Stuff

def connectionStatus(message, redirectUrl = null) {
    def redirectHtml = ""
    if (redirectUrl) {
        redirectHtml = """
            <meta http-equiv="refresh" content="3; url=${redirectUrl}" />
        """
    }

    def html = """
        <!DOCTYPE html>
        <html>
        <head>
        <meta name="viewport" content="width=640">
        <title>Withings Connection</title>
        <style type="text/css">
                @font-face {
                        font-family: 'Swiss 721 W01 Thin';
                        src: url('https://s3.amazonaws.com/smartapp-icons/Partner/fonts/swiss-721-thin-webfont.eot');
                        src: url('https://s3.amazonaws.com/smartapp-icons/Partner/fonts/swiss-721-thin-webfont.eot?#iefix') format('embedded-opentype'),
                                 url('https://s3.amazonaws.com/smartapp-icons/Partner/fonts/swiss-721-thin-webfont.woff') format('woff'),
                                 url('https://s3.amazonaws.com/smartapp-icons/Partner/fonts/swiss-721-thin-webfont.ttf') format('truetype'),
                                 url('https://s3.amazonaws.com/smartapp-icons/Partner/fonts/swiss-721-thin-webfont.svg#swis721_th_btthin') format('svg');
                        font-weight: normal;
                        font-style: normal;
                }
                @font-face {
                        font-family: 'Swiss 721 W01 Light';
                        src: url('https://s3.amazonaws.com/smartapp-icons/Partner/fonts/swiss-721-light-webfont.eot');
                        src: url('https://s3.amazonaws.com/smartapp-icons/Partner/fonts/swiss-721-light-webfont.eot?#iefix') format('embedded-opentype'),
                                 url('https://s3.amazonaws.com/smartapp-icons/Partner/fonts/swiss-721-light-webfont.woff') format('woff'),
                                 url('https://s3.amazonaws.com/smartapp-icons/Partner/fonts/swiss-721-light-webfont.ttf') format('truetype'),
                                 url('https://s3.amazonaws.com/smartapp-icons/Partner/fonts/swiss-721-light-webfont.svg#swis721_lt_btlight') format('svg');
                        font-weight: normal;
                        font-style: normal;
                }
                .container {
                        width: 90%;
                        padding: 4%;
                        /*background: #eee;*/
                        text-align: center;
                }
                img {
                        vertical-align: middle;
                }
                p {
                        font-size: 2.2em;
                        font-family: 'Swiss 721 W01 Thin';
                        text-align: center;
                        color: #666666;
                        padding: 0 40px;
                        margin-bottom: 0;
                }
                span {
                        font-family: 'Swiss 721 W01 Light';
                }
        </style>
        ${redirectHtml}
        </head>
        <body>
                <div class="container">
                        <img src="https://rachio-media.s3.amazonaws.com/images/logo/rachio-logo-for-web-300px.png" width=\"150\" height=\"60\" />
                        <img src="https://s3.amazonaws.com/smartapp-icons/Partner/support/connected-device-icn%402x.png" alt="connected device icon" />
                        <img src="https://s3.amazonaws.com/smartapp-icons/Partner/support/st-logo%402x.png" alt="SmartThings logo" />
                        ${message}
                </div>
        </body>
        </html>
        """
    render contentType: 'text/html', data: html
}

def getRachioDevices() {
    log.trace "rachioDevicePage called getRachioDevices()"

    //Step1: GET account info "userId"
    state.userId = getUserId();
    if (!state?.userId) {
        log.error "No user Id found exiting"
        return
    }
    
    def userInfo = getUserInfo(state?.userId)
    state?.userInfo = "" //userInfo
    //log.debug "userInfo: ${userInfo}"
    
    //Step3: Obtain device information for a location
    def devices = [:]  

    userInfo.devices.each { sid ->
       state?.sid = sid?.id
       //log.info "systemId: ${sid.id}"
       def dni = sid?.id 
       
       devices[dni] = sid?.name
       //log.info "Found sprinkler with dni(locationId.gatewayId.systemId.zoneId): $dni and displayname: ${devices[dni]}"
    }
    log.info "getRachioDevices()>> sprinklers: $devices"

    state.devices = devices
    state.controllerId = devices
    return devices
}

def getUserInfo(userId) {
    //log.debug "getUserInfo ${userId}"
    return _httpGet("person/${userId}");
}

def getUserId() {
    //log.trace "getUserId()"
    def res = _httpGet("person/info");
    if (res) {
        return res?.id;
    }
    return null
}

def getZoneInfo(userId, zoneId) {
    //log.debug "getUserInfo ${userId}"
    return _httpGet("person/${userId}/${zoneId}");
}

def _httpGet(subUri) {
    //log.debug "httpGet"
    def userParams = [
            uri: "${apiEndpoint}/1/public/${subUri}", 
            headers: ["Authorization": "Bearer ${state.authToken}"]
    ]
    //log.debug "userparams ${userParams}"
    httpGet(userParams) { resp ->

        if(resp.status == 200) {
            //log.debug "data: ${resp.data}"
            return resp.data
        } else {
            log.debug "http status: ${resp.status}"

            //refresh the auth token
            if (resp.status == 500 && resp.data.status.code == 14) {
                log.debug "Storing the failed action to try later"
                data.action = "getRachioDevices"
                log.debug "Refreshing your auth_token!"
                //refreshAuthToken()
            } else {
                log.error "Authentication error, invalid authentication method, lack of credentials, etc."
            }
          return null
        }
    }

}

def getDisplayName(iroName,zname) {
    if(zname) {
        return "${iroName}:${zname}"
    } else {
        return "Rachio"
    }
}

//Section3: installed, updated, initialize methods
def installed() {
    log.debug "Installed with settings: ${settings}"
    initialize()
    state?.installed = true
}

def updated() {
    log.debug "Updated with settings: ${settings}"
    unsubscribe()
    initialize()
}

def initialize() {
    log.debug "initialize"
    unschedule()
    scheduler()
   
    addRemoveDevices()
    getRachioData()
    //send activity feeds to tell that device is connected
    def notificationMessage = "is connected to SmartThings"
    //sendActivityFeeds(notificationMessage)
    state.timeSendPush = null
    subscribe(app, onAppTouch)
    //pollHandler()
}

def onAppTouch(event) {
    poll()
}

def scheduler() {
    schedule("0 0/1 * * * ?", "getRachioData")
}

def getRachioData() {
    state?.zoneIds = getZoneIds()
    poll()
    //runIn(27, "updateRachioData", [overwrite: true]) //This used to increase the polling to every 30 seconds for testing.
}

def updateRachioData() {
    getRachioData()
}

def getZoneIds() {
    def zoneIds = [:]
    def devInfo = getDeviceInfo(state?.sid)
    state?.deviceInfo = null
    if(devInfo) {
        devInfo?.zones?.each { zone ->
           if(zone?.enabled == true) {
                //log.debug "zoneId: $zone.id"
                def adni = [zone?.id].join('.')
                zoneIds[adni] = zone?.name
            }
        }
    }
    return zoneIds
}

def zoneInfoState(val) {
    def zones = [:]
    def aZones = getDeviceInfo(state?.sid)
    aZones?.zones.each { zone ->
        //log.debug "zone: $zone"
        def zoneId = zone?.id
        def zoneData = zone
        val.each { zi ->
            if(zoneId == zi?.key) {
                def adni = [zoneId].join('.')
                zones[adni] = zoneData
            }
        }
    }
    return zones
}

def getDeviceMap() {
    def devs = [:]
    def aDevs = getDeviceInfo(state?.sid)
    aDevs?.each { dev ->
        //log.debug "zone: $zone"
        def devId = dev?.id
        def devData = dev
        def adni = [devId].join('.')
        devs[adni] = devData
    }
    return devs
}

def getZoneMap() {
    return zoneInfoState(state?.zoneIds)
}

def getZoneInfo(zoneId) {
    return _httpGet("zone/${zoneId}")
}

def getDeviceInfo(devId) {
    return _httpGet("device/${devId}")
}

def getCurSchedule(devId) {
    return _httpGet("device/${devId}/current_schedule")
}

def addRemoveDevices() {
    log.trace "addRemoveDevices..."
    try {
        def devsInUse = []
        //sprinklers is selected by user on DeviceList page
        def devices = state?.zoneIds?.collect { dni ->
            //log.debug "devices: $devices"
            //Check if the discovered sprinklers are already initiated with corresponding device types.
            def d = getChildDevice(dni?.key) 
            if(!d) {
                d = addChildDevice("rachio", getChildName(), dni?.key, null, [label: "Rachio - ${dni?.value}"])
                d.take()
                log.debug "created ${d?.displayName} with dni ${dni?.key}"
            } else {
                log.debug "found ${d?.displayName} with id ${dni?.key} already exists"
            }
            devsInUse += dni.key
            return d
        }
        def delete
        //log.debug "devicesInUse: ${devsInUse}"
        delete = getChildDevices().findAll { !devsInUse?.toString()?.contains(it?.deviceNetworkId) }

        if(delete?.size() > 0) {
            log.debug "delete: ${delete}, deleting ${delete.size()} devices"
            delete.each { deleteChildDevice(it.deviceNetworkId) }
        }
        //retVal = true
    } catch (ex) {
        if(ex instanceof physicalgraph.exception.ConflictException) {
            def msg = "Error: Can't Delete App because Devices are still in use in other Apps, Routines, or Rules.  Please double check before trying again."
            log.warn "addRemoveDevices Exception | $msg"
        }
        else if(ex instanceof physicalgraph.app.exception.UnknownDeviceTypeException) {
            def msg = "Error: Device Handlers are likely Missing or Not Published.  Please verify all device handlers are present before continuing."
            log.warn "addRemoveDevices Exception | $msg"
        }
        else { log.error "addRemoveDevices Exception: ${ex}" }
        //retVal = false
    }    

}

//Section4: polling device info methods 

void poll() {
    log.trace "poll..."
    state?.zoneIds = getZoneIds()
    
    def devices = getChildDevices()
    devices.each { pollChild(it) }
}

//this method is called by (child) device type, to reply (Map) rachioData to the corresponding child
def pollChild(child) {
    //poll data from 3rd party cloud
    if (pollChildren(child)){ 
        //generate event for each (child) device type identified by different dni
    }
}

def pollChildren(child = null) {
    log.trace "pollChildren($child)..."
    //def result = false
    try {
        if(child) {
            def dni = child?.device?.deviceNetworkId
            def d = getChildDevice(dni)
            def zoneData = getZoneInfo(dni)
            def schedData = getCurSchedule(state?.sid)
            def rainDelay = getCurrentRainDelay(state?.sid)
            def data = ["data":zoneData, "schedData":schedData, "rainDelay":rainDelay, "devId":state?.sid]
            //log.debug "pollChild device: ${d} | zone: $zoneData"
            if (d) { 
                d.generateEvent(data) 
            }
        }
        else {
            def devices = state?.zoneIds
            state?.zoneIds?.each { dev ->
                def dni = dev?.key
                def d = getChildDevice(dni)
                def zoneData = getZoneInfo(dni)
                def schedData = getCurSchedule(state?.sid)
                def rainDelay = getCurrentRainDelay(state?.sid)
                def data = ["data":zoneData, "schedData":schedData, "rainDelay":rainDelay, "devId":state?.sid]
                //log.debug "pollChild device: ${d} | zone: $zoneData"
                if (d) { 
                    d.generateEvent(data) 
                }
            }
        }
    
    } catch(Exception e) {
        log.debug "exception polling children: " + e
//refreshAuthToken()
    }
    return result
}


def setValue(child, newValue) {
    def jsonRequestBody = '{"value":'+ newValue+'}'
    def result = sendJson(child, jsonRequestBody)
    return result
}

def sendJson(subUri, jsonBody) {
    //log.trace "Sending: ${jsonBody}"
    def returnStatus = false
    def cmdParams = [
            uri: "${apiEndpoint}/1/public/${subUri}",
            headers: ["Authorization": "Bearer ${state?.authToken}", "Content-Type": "application/json"],
            body: jsonBody
    ]

    try{
        httpPut(cmdParams) { resp ->
            if(resp.status == 201) {
                returnStatus = true
                // The runIn below will schedule a data poll for 4 seconds to update the devices with the latest changes
                // This is necessary because when it is called from the child device it runs on a different thread that does not have
                // the ability to create logs in the apps logs
                runIn(2, "poll", [overwrite: true])
            } else {
                //refresh the auth token
                if (resp.status == 401) {
                    log.debug "Refreshing your auth_token!"
                    refreshAuthToken()
                } else {
                    log.error "Authentication error, invalid authentication method, lack of credentials, etc."
                }
            }
        }
    } catch(Exception e) {
        log.error "sendJson Exception Error: ${e}"
        //refreshAuthToken()
    }
    return returnStatus
}

def refreshAuthToken() {
    log.debug "refreshAuthToken()"
    def appKey = "refreshToken"

    def notificationMessage = "Rachio is disconnected from SmartThings, because the access credential changed or was lost.  " +
            "Please go to the Rachio SmartApp and re-enter your account login credentials."

    def refreshParams = [
            method: 'POST',
            headers: ["Authorization": "Basic $appKey"],
            uri: "${apiEndpoint}/uri",
            body: [grant_type:'refresh_token', refresh_token:"${state?.refreshToken}"],
    ]

    try {
        httpPost(refreshParams) { resp ->
            if(resp?.status == 200) {
                log.debug "refreshAuthToken()>> Response: ${resp?.data}"
                if (resp?.data) {
                    state.refreshToken = resp?.data?.refresh_token?.toString()
                    state.authToken = resp?.data?.access_token?.toString()
                }

            } else {
                sendPushAndFeeds(notificationMessage)
            }
        }
    } catch (groovyx.net.http.HttpResponseException e) {
        log.error "refreshAuthToken() >> Error: e.statusCode ${e.statusCode}"
        def reAttemptPeriod = 300 
        if (e.statusCode != 401) { 
            runIn(reAttemptPeriod, "refreshAuthToken")
        } else if (e.statusCode == 401) { //refresh token is expired
            sendPushAndFeeds(notificationMessage)
            
        }
    }
}

//Section6: helper methods ---------------------------------------------------------------------------------------------

def toJson(Map m) {
    return new org.codehaus.groovy.grails.web.json.JSONObject(m).toString()
}

def toQueryString(Map m) {
    return m.collect { k, v -> "${k}=${URLEncoder.encode(v.toString())}" }.sort().join("&")
}

def epochToDt(val) {
    return formatDt(new Date(val))
}

def formatDt(dt) {
    def tf = new SimpleDateFormat("MMM d, yyyy - h:mm:ss a")
    if(location?.timeZone) { tf?.setTimeZone(location?.timeZone) }
    else {
        log.warn "SmartThings TimeZone is not found or is not set... Please Try to open your ST location and Press Save..."
        return null
    }
    return tf.format(dt)
}

def getDurationDesc(long secondsCnt) {
    int seconds = secondsCnt %60
    secondsCnt -= seconds
    long minutesCnt = secondsCnt / 60
    long minutes = minutesCnt % 60
    minutesCnt -= minutes
    long hoursCnt = minutesCnt / 60
    
    return "${minutes} min ${(seconds >= 0 && seconds < 10) ? "0${seconds}" : "${seconds}"} sec"
}

//Returns time differences is seconds
def GetTimeValDiff(timeVal) {
    try {
        def start = new Date(timeVal).getTime()
        def now = new Date().getTime()
        def diff = (int) (long) (now - start) / 1000
        //log.debug "diff: $diff"
        return diff
    }
    catch (ex) {
        log.error "GetTimeValDiff Exception: ${ex}"
        return 1000
    }
}

//def getChildName()               { return "Rachio New Iro" }
def getChildName()               { return "Rachio IRO2" }
def getServerUrl()               { return "https://graph.api.smartthings.com" }
def getShardUrl()                { return getApiServerUrl() }
def getCallbackUrl()             { return "https://graph.api.smartthings.com/oauth/callback"} 
def getAppEndpoint()             { return "https://app.rach.io"}
def getApiEndpoint()             { return "https://api.rach.io"}
def getClientId()                { return appSettings.clientId ? appSettings?.clientId : "smartthings" }
def getClientSecret()			 { return "b10c4f90-7952-4b35-a505-ab8ca3c80e41"}


def debugEventFromParent(child, message) {
    child.sendEvent("name":"debugEventFromParent", "value":message, "description":message, displayed: true, isStateChange: true)
}

//send both push notification and mobile activity feeds
def sendPushAndFeeds(notificationMessage){
    if (state.timeSendPush){
        if (now() - state.timeSendPush > 86400000){ 
            sendPush("Rachio " + notificationMessage)
            sendActivityFeeds(notificationMessage)
            state.timeSendPush = now()
        }
    } else {
        sendPush("Rachio " + notificationMessage)
        sendActivityFeeds(notificationMessage)
        state.timeSendPush = now()
    }
    state.authToken = null
}

def sendActivityFeeds(notificationMessage) {
    def devices = getChildDevices()
    devices.each { child ->
           //update(child)
        child.generateActivityFeedsEvent(notificationMessage) 
    }
}


def off(child, deviceId) {
    log.trace "off()..."
    child?.log("off()...")
    if(deviceId) {
        def jsonData = new JsonBuilder("id":deviceId)
        def res = sendJson("device/stop_water", jsonData.toString())
        if (res) {
            //child.sendEvent(name: 'contact', value: "off")
            //child.sendEvent(name: 'switch', value: "off")
            //child.sendEvent(name: 'watering', value: "off")
        }
        return res
    }
    return false 
}

def setRainDelay(child, delayVal) {
    if (delayVal) {
        def secondsPerDay = 24*60*60;
        def duration = delayVal * secondsPerDay;
        def jsonData = new JsonBuilder("id":child?.device?.deviceNetworkId, "duration":duration)
        def res = sendJson("device/rain_delay", jsonData?.toString())
        
        if (res) { child?.sendEvent(name: 'rainDelay', value: delayVal) }
        return res
    }
    return false 
}

def isWatering(devId) {
    //log.debug "isWatering()..."
    def res = _httpGet("device/${devId}/current_schedule");
    def result = (res && res.status) ? true : false
    return result
}

def getCurrentRainDelay(devId) {
    //log.debug("getCurrentRainDelay($devId)...")
    def res = _httpGet("device/${devId}");
    
    // convert to configured rain delay to days.
    return res ? (int)(res.rainDelayExpirationDate - res.rainDelayStartDate)/(26*60*60*1000) : 0
}

def startZone(child, zoneNum, mins) {
    def res = false
    log.trace "startZone(ZoneName: ${child.device.displayName}, ZoneNumber: ${zoneNum}, Duration: ${mins})..."
    child?.log("startZone(${child.device.displayName}, ${zoneNum}, ${mins})...")
    def zoneId = child?.device?.deviceNetworkId
    if (zoneId && zoneNum && mins) {
        def duration = mins * 60
        def jsonData = new JsonBuilder("id":zoneId, "duration":duration.toInteger())
        log.debug "jsonData: $jsonData"
        sendJson("zone/start", jsonData.toString())
        res = true
    }
    return res
}

def runAllZones(child, mins) {
    def res = false
    //log.trace "runAllZones(ZoneName: ${child.device.displayName}, Duration: ${mins})..."
    child?.log("runAllZones(ZoneName: ${child.device.displayName}, Duration: ${mins})...")
    if (state?.zoneIds && mins) {
        def zoneData = []
        def sortNum = 1
        state?.zoneIds?.each { z ->
            zoneData << ["id":z.key, "duration":(mins * 60), "sortOrder": sortNum]
            sortNum = sortNum+1
        }
        def jsonData = new JsonBuilder("zones":zoneData)
        child?.log("jsonData: $jsonData")
        sendJson("zone/start_multiple", jsonData.toString())
        res = true
    }
    return res
}

def pauseScheduleRun(child) {
    log.trace "pauseScheduleRun..."
    def schedData = getCurSchedule(state?.sid)
    def schedRuleData = getScheduleRuleInfo(schedData?.scheduleRuleId)
    child?.log "schedRuleData: $schedRuleData"
    child?.log "Schedule Started on: ${epochToDt(schedRuleData?.startDate)} | Total Duration: ${getDurationDesc(schedRuleData?.totalDuration.toLong())}"
    
    if(schedRuleData) {
        def zones = schedRuleData?.zones?.sort { a , b -> a.sortOrder <=> b.sortOrder }
        zones?.each { zn ->
            
            child?.log "Zone#: ${zn?.zoneNumber} | Zone Duration: ${getDurationDesc(zn?.duration.toLong())} | Order#: ${zn?.sortOrder}"
            if(zn?.zoneId == schedData?.zoneId) {
                def zoneRunTime = "Elapsed Runtime: ${getDurationDesc(GetTimeValDiff(schedData?.zoneStartDate.toLong()))}"
                child?.log "Zone Started: ${epochToDt(schedData?.zoneStartDate)} | ${zoneRunTime} | Cycle Count: ${schedData?.cycleCount} | Cycling: ${schedData?.cycling}"
            }
        }
    }  
    
}

def getZones(device) {
    def res = _httpGet("device/${device.deviceNetworkId}")
    return !res ? null : res?.zones
}

def getScheduleRuleInfo(schedId) {
    def res = _httpGet("schedulerule/${schedId}")
    return res
}

def restoreZoneSched(runData) {
    def res = false
    //log.trace "restoreZoneSched( Data: ${runData})..."
    child?.log("restoreZoneSched( Data: ${runData})...")
    if (state?.zoneIds && mins) {
        def zoneData = []
        def sortNum = 1
        runData?.each { rd ->
            zoneData << ["id":rd.zoneId, "duration": rd?.duration, "sortOrder": rd?.sortNumber]
        }
        def jsonData = new JsonBuilder("zones":zoneData)
        child?.log("jsonData: $jsonData")
        sendJson("zone/start_multiple", jsonData.toString())
        res = true
    }
    return res
}