# TiMqWay
ch.quantasy.tinkerforge.mqtt.gateway
Provides an [MQTT] view to the [Tinkerforge](tm) world.
The underlying idea of TiMqWay is a bunch of [micro-service]s, providing a self-explaining mqtt view for dynamic access to known Tinkerforge devices. 

<a href="https://github.com/knr1/ch.quantasy.tinkerforge.mqtt.gateway/blob/master/TinkerforgeSBAO.svg">
<img src="https://github.com/knr1/ch.quantasy.tinkerforge.mqtt.gateway/blob/master/TinkerforgeSBAO.svg.png" alt="SBAO-Diagram" />
</a>

In order to understand this micro-Service Based Agent oriented Design (SBAOD) and maybe to provide own services in this manner... 
there is a Tutorial that tries to get you into it: [https://github.com/knr1/ch.bfh.mobicomp.iot.mqtt.tutorial]


## Ideology
Each tinkerforge micro-service provides the following interface:
<a href="https://github.com/knr1/ch.quantasy.tinkerforge.mqtt.gateway/blob/master/TiMqWayService.svg">
<img src="https://github.com/knr1/ch.quantasy.tinkerforge.mqtt.gateway/blob/master/TiMqWayService.svg.png" alt="Service-Diagram" />
</a>
* **description** Each micro-service describes its abilities via the description topic.
* **status** Each micro-service describes its actual status via its specialized status topics.
* **event** Each micro-service provides all events via its specialized event topics. As there might be more events available than the mqtt broker is able to handle, all events are always covered within an array. Hence, there might be 0,1 or multiple events within one message.
* **intent** Each micro-service accepts _intentions_ via the intent topic. It is equivalent to the setter methods but allows _parallel_ and _concurrent_ 'requests'.


**Root topic** The root topic of TiMqWay, where all thinkerforge micro-services can be reached: **TF/**.

**Message lanaguage** TiMqWay's message language used is **[YAML]**.

**Connection between services** The services do not know each other and cannot 'learn' from other services. What is needed is a tiny piece of software
that glues the services together. It is no big deal, even though this glueing program is called **agent** that that
gets information from some services and puts information to other services. That is why it is called **Service-Based-Agent-Orientet**.
You can write that piece of program in any programming language, as long as you can access the mqtt-broker used.

### Tinkerforge abstraction 
Tinkerforge devices are not represented within their 'stack', but loosely coupled so a device might even change its stack but is still accessible in MQTT at the same location (TF/device-type/UID).


## Installation
In order to install TiMqWay 
* Developers way: clone and build the project
* Users way: download the latest [TiMqWay.jar]
 
## Usage
You need Java (7 or higher) and a running MQTT-Server. You can start TiMqWay with the MQTT-Server-Parameter.

Then run the following command in order to use an MQTT-Server at localhost
```sh
$ java -jar TiMqWay.jar tcp://127.0.0.1:1883
```
Or, if you do not have any MQTT-Broker ready, use an existing one at iot.eclipse.org:1883 (Not recommended as it is an open server everyone can read and write into)
```sh
$ java -jar TiMqWay.jar tcp://iot.eclipse.org:1883
```

Then, if you subscribe to TF/# you will immediately get the description info for the TiMqWay-Manager, which does all the nasty stuff for you.

In order to interact with some specific Tinkerforge-Stack (e.g. localhost), the following message has to be sent to the following topic:
```sh
Topic: TF/Manager/stack/address/add
Message: localhost
```
or any other address IP or name will work, if there is an actual Tinkerforge stack accessible.

### Tip
You might want to get an overview of TF using a graphical MQTT-Viewer i.e. [d3Viewer].

 [micro-service]:<https://en.wikipedia.org/wiki/Microservices>
 [tinkerforge]:<http://www.tinkerforge.com/en>
 [MQTT]: <http://mqtt.org/>
 [TiMqWay.jar]: <https://drive.google.com/open?id=0B9adEExqrkwQZVpwVktidnhJT0E>
 [d3Viewer]: <https://github.com/hardillb/d3-MQTT-Topic-Tree>
 [YAML]: <https://en.wikipedia.org/wiki/YAML>
 [https://github.com/knr1/ch.bfh.mobicomp.iot.mqtt.tutorial]: <https://github.com/knr1/ch.bfh.mobicomp.iot.mqtt.tutorial>

# Example
The following real-world example shall help to understand how to work with this TiMqWay.
<a href="https://github.com/knr1/ch.quantasy.tinkerforge.mqtt.gateway/blob/master/Example01.svg">
<img src="https://github.com/knr1/ch.quantasy.tinkerforge.mqtt.gateway/blob/master/Example01.svg.png" alt="Example-Diagram" />
</a>


##Common Hardware
Our setup runs at home. We have a local (wlan-)network and a connected computer (could be a PC, Notebook, RaspberryPi,...)


##Tinkerforge Hardware
In our setup, we are in possession of two Master-Bricks and three bricklets (2x TemperatureBricklet and 1x 20x4LCDBricklet).
They are connected in the following way:
* **Master-Brick-1** TemperatureBricklet (UID: blue), 20x4LCDBricklet (UID: lcd)
* **Master-Brick-2** TemperatureBricklet (UID: red)

The Master-Brick-1 is connected via WLAN-Brick-Extension to the local network (via AccessPoint)

The Master-Brick-2 is connected via USB to a PC (could also be the RED-Brick).

## MQTT
In order to provide the broker, either an external broker (e.g. tcp://iot.eclipse.org:1883) or some internal broker must be accessible.
In our setup, we put an MQTT-Broker (e.g. mosquitto or moquette) on our existing PC and start the MQTT-Broker

## TiMqWay
Now we start TiMqWay. This software can be installed anywhere, where it has access to all the Master-Bricks and to the MQTT-Broker.
Again, we put TiMqWay on our existing PC. It is a Java-program, hence a JVM must be installed. Start it by:

`java -jar TiMqWay.jar`.
 
## Working with TiMqWay

TiMqWay itself is presented as a micro-service. It is called 'Manager' and is provided as 'singleton'. Hence, no instance of a Manager is needed.
If you look into the provided topic within the MQTT-Broker, the Manager provides the following interface:
```
TF
   Manager
     description
       status
         connection ---[online|offline]         
         stack
           address
             <address>
               connected ---[true|false]
         device
           <address>
             <DeviceClass>
               <Instance> [true|false]
       event
         device
           connected --- <address>
           disconnected --- <address>
         stack
           address
             added --- <address>
             removed --- <removed>
       intent 
         stack
           address
             add --- <address>
             remove --- <address>
     status
       connection --- online
```

### Connecting Master-Brick-1

As the description explains, we now have to tell TiMqWay where to look for the Master Bricks (Stacks). Hence, we want to attach Master-Brick-1 (say, its 
network-name is master-brick-1). Therefore the following message has to be sent to the following topic:
```
Topic: TF/Manager/intent/stack/address/add
Message: master-brick-1
```

Looking into the mqtt-broker, the following can be seen:

```
TF
   Manager
     description
       #omitted for better readability
     status
       connection --- online
       device
         master-brick-1
           Temperature
             blue --- true
           LCD20x4
             lcd --- true
     event
       stack
         address
           added --- -hostname:"master-brick-1" port: 4223
     intent
       stack
         address
           add --- master-brick-1
   Temperature
     description
       #omitted for better readability
     blue
       status
         connection --- online
         position --- "c"
         firmware --- 2-0-1
         hardware --- 1-1-0
   LCD20x4
     description
       #omitted for better readability
     lcd
       status
         connection --- online
         position --- "d"
         firmware --- 2-0-2
         hardware --- 1-2-0
```
If you want to switch on the backlight of the device called 'lcd' which is an instance of the 20x4LCD class then you publish the following message to
the following topic:
```
Topic: TF/LCD20x4/lcd/intent/backlight
Message: true
```
What happenes is that the backlight is now switched to on at the specific 20x4LCD device. In the mqtt-broker, some topics changed as well.
```
TF
   Manager
     #omitted for better readability 
   Temperature
     blue
       status
         connection --- online
         position --- "c"
         firmware --- 2-0-1
         hardware --- 1-1-0
   LCD20x4
     description
       #omitted for better readability 
     lcd
       status
         connection --- online
         position --- "d"
         firmware --- 2-0-2
         hardware --- 1-2-0
         backlight --- true
       intent
         backlight --- true
```
 
Now, let us connect the second master-brick (stack). This one is connected via USB, hence, its address is `localhost`:
```
Topic: TF/Manager/intent/stack/address/add
Message: localhost
```
The TiMqWay-manager now knows two stacks and manages one temperature device more
```
TF
   Manager
     description
       #omitted for better readability
     status
       connection --- online
       device
         master-brick-1
           Temperature
             blue --- true
           LCD20x4
             lcd --- true
         localhost
           Temperature
             red --- true
     event
       stack
         address
           added --- -hostname:"localhost" port: 4223
     intent
       stack
         address
           add --- localhost
   Temperature
     description
       status
         connection ---[online|offline]
         position --- [0|1|2|3|4|5|6|7|8|a|b|c|d]
         firmware --- [-32768..32767]_*
         hardware --- [-32768..32767]_*
         temperature
           callbackPeriod --- [0..9223372036854775807]
           threshold --- option: [x|o|i|<|>]\n min: [-2500..8500]\n max: [-2500..8500]
           deounce --- [0..9223372036854775807]
           mode ---[Slow|Fast]
       event
         temperature --- timestamp: [0..9223372036854775807]\n value: [-2500..8500]\n
           reached --- timestamp: [0..9223372036854775807]\n value: [-2500..8500]\n
       intent
         debounce
           period --- [0..9223372036854775807]
         temperature
           callbackPeriod --- [0..9223372036854775807]
           threshold --- option: [x|o|i|<|>]\n min: [-2500..8500]\n max: [-2500..8500]
         mode -- mode:[Slow|Fast]
     blue
       status
         connection --- online
         position --- "c"
         firmware --- 2-0-1
         hardware --- 1-1-0
     red
       status
         connection --- online
         position --- "a"
         firmware --- 2-0-1
         hardware --- 1-1-0
   LCD20x4
     description
       status
         connection ---[online|offline]
         position --- [0|1|2|3|4|5|6|7|8|a|b|c|d]
         firmware --- [-32768..32767]_*
         hardware --- [-32768..32767]_*
         backlight --- [true|false]
         defaultText
           texts --- [line: [0..3]\n text: [String]_[1..20]]
           counter --- [-1..2147483647]
       intent
         backlight --- [true|false]
         clearDisplay --- [true|false]
         defaultText
           texts --- [line: [0..3]\n text: [String]_[1..20]]
           counter --- [-1..2147483647]
         writeLines --- [line: [0..3]\n position: [0..18]\n text: [String]_[1..20]]
     lcd
       status
         connection --- online
         position --- "d"
         firmware --- 2-0-2
         hardware --- 1-2-0
```

If we want to have a temperature reading every second for `red`, we provide the following message to the following topic:
```
Topic: TF/Temperature/red/intent/temperature/callbackPeriod
Message: 1000
```
Now, there is a reading every second, that will be promoted as an event to `TF/Temperature/red/event/temperature`

```
TF
   Manager
     #omitted for better readability
   Temperature
     description
       #omitted for better readability
     blue
       status
         connection --- online
         position --- "c"
         firmware --- 2-0-1
         hardware --- 1-1-0
     red
       status
         connection --- online
         position --- "a"
         firmware --- 2-0-1
         hardware --- 1-1-0
         temperature
           callbackPeriod --- 1000
       event
         temperature --- - timestamp: 1465398254115 value: 2668 
   LCD20x4
     description
       status
         connection ---[online|offline]
         position --- [0|1|2|3|4|5|6|7|8|a|b|c|d]
         firmware --- [-32768..32767]_*
         hardware --- [-32768..32767]_*
         backlight --- [true|false]
         defaultText
           texts --- [line: [0..3]\n text: [String]_[1..20]]
           counter --- [-1..2147483647]
       intent
         backlight --- [true|false]
         clearDisplay --- [true|false]
         defaultText
           texts --- [line: [0..3]\n text: [String]_[1..20]]
           counter --- [-1..2147483647]
         writeLines --- [line: [0..3]\n position: [0..18]\n text: [String]_[1..20]]
     lcd
       status
         connection --- online
         position --- "d"
         firmware --- 2-0-2
         hardware --- 1-2-0
```
All that is left is to write a little agent, subscribing to the temperature of red and blue. Then process the values and write them to lcd... as a publish to
`TF/LCD20x4/lcd/intent/writeLines`...

```
Topic: TF/LCD20x4/lcd/intent/writeLines
Message: - line: 0
           position: 0
           text: "RED: 22°C"
         - line: 1
           position: 0
           text: "BLUE: 18°C"
```
### Supported Device so far...
* BrickDC
* BrickIMU
* BrickIMUV2
* BrickletAccelerometer
* BrickletAmbientLight
* BrickletAmbientLightV2
* BrickletAnalogInV2
* BrickletAnalogOutV2
* BrickletBarometer
* BrickletCO2
* BrickletColor
* BrickletDistanceIR
* BrickletDistanceUS
* BrickletDualButton
* BrickletDualRelay
* BrickletDustDetector
* BrickletHallEffect
* BrickletHumidity
* BrickletJoystick
* BrickletLaserRangeFinder
* BrickletLCD16x2
* BrickletLCD20x4
* BrickletLEDStrip
* BrickletLine
* BrickletLinearPoti
* BrickletLoadCell
* BrickletMoisture
* BrickletMotionDetector
* BrickletMultiTouch
* BrickletPiezoSpeaker
* BrickletRemoteSwitch
* BrickletRotaryEncoder
* BrickletRotaryPoti
* BrickletSegmentDisplay4x7
* BrickletSolidStateRelay
* BrickletSoundIntensity
* BrickletTemperature
* BrickletTemperatureIR
* BrickletTilt
* BrickletUVLight

