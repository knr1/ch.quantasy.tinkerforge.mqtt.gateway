# TiMqWay
[Data-driven] [micro-service]s for the bricks and bricklets of the [Tinkerforge](tm) world, based on ch.quantasy.mqtt.gateway.
ch.quantasy.tinkerforge.mqtt.gateway

Please note, that this project depends on [https://github.com/knr1/ch.quantasy.mqtt.gateway]


The underlying idea of TiMqWay is a set of self-explaining micro-services, providing a data-driven interface to the known Tinkerforge devices. This way,
 the implementation is agnostic to the programming-language and paradigm used for orchestration. Any language will do, as long as you master it.

What is left to do for the user of TiMqWay, is the orchestration and choreography of how these services in order to fit the needs.


 <!---<a href="https://github.com/knr1/ch.quantasy.tinkerforge.mqtt.gateway/blob/master/TinkerforgeSBAO.svg">--->
 <!---<img src="https://github.com/knr1/ch.quantasy.tinkerforge.mqtt.gateway/blob/master/TinkerforgeSBAO.svg.png" alt="SBAO-Diagram" />--->
 <!---</a>--->

 <!---In order to understand this micro-Service Based Agent oriented Design (SBAOD) and maybe to provide own services in this manner... --->
 <!---there is a Tutorial that tries to get you into it: [https://github.com/knr1/ch.bfh.mobicomp.iot.mqtt.tutorial]--->


## Ideology
Each Tinkerforge micro-service provides the following minimal contract:
<a href="https://github.com/knr1/ch.quantasy.tinkerforge.mqtt.gateway/blob/master/TiMqWayService.svg">
<img src="https://github.com/knr1/ch.quantasy.tinkerforge.mqtt.gateway/blob/master/TiMqWayService.svg.png" alt="Service-Diagram" />
</a>
* **Description** Each micro-service describes its abilities via the description topic.
* **Status** Each micro-service describes its actual status via its specialized status topics.
* **Event** Each micro-service provides all events via its specialized event topics. As there might be more events available than the mqtt broker is able to handle, all events are always covered within an array. Hence, there might be 0,1 or multiple events within one message.
* **Intent** Each micro-service accepts _intentions_ via the intent topic. It is equivalent to the setter methods but allows _parallel_ and _concurrent_ 'requests'.


**Root topic** The root topic of TiMqWay, where all thinkerforge micro-services can be reached: **TF/**.

**Data language** TiMqWay's data language used is **[YAML]**.

**No Connection between services** The services do not know each other and cannot 'learn' from other services (i.e. they are cohesive). What is needed is a software
that orchestrates the services. You can write the orchestration in any programming language (e.g. Node-Red, Java, js, Swift, ...), as long as you can access the mqtt-broker used.

### Abstraction to Tinkerforge 
Each micro-service is an instance of a Tinkerforge-Device class, accessible by its UID. Hence, devices are not represented within their 'stack', but loosely coupled so
 a device might even change its stack but is still accessible in MQTT at the same location (TF/device-class/UID).


## Installation
In order to install TiMqWay 
* **Developers way** clone and build the project. Please note that it depends on [https://github.com/knr1/ch.quantasy.mqtt.gateway]
* **Users way** download the latest [TiMqWay.jar]
 
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
Topic: TF/Manager/I/stack/address/add
Message: localhost
```
or any other address IP or name will work, if there is an actual Tinkerforge stack accessible.

### Quick Shots
In the following, some one-liners are shown in order to demonstrate how easy it is to get information from TF.
In order to see who is calling, the intent will always end with '/quickshot' as the intending party (you can put anything (or nothing) there).
 
**Setting backlight of LCD-Display (uid: lcd):**
```
Topic: TF/LCD20x4/lcd/I/backlight/quickshot
Message: true
```

**Measuring the temperature once per second on temperature bricklet (uid: red):**
```
Topic: TF/Temperature/red/I/temperature/callbackPeriod/quickshot
Message: 1000
```

**Detecting NFC-Tags once per second on NFCRFID bricklet (uid: ouu):**
```
Topic: TF/NfcRfid/ouu/I/scanning/callbackPeriod/quickshot
Message: 1000
```



### Tip
You might want to get an overview of TF using a graphical MQTT-Viewer i.e. [d3Viewer].

 [Data-driven]:<https://en.wikipedia.org/wiki/Data-driven_programming>
 [micro-service]:<https://en.wikipedia.org/wiki/Microservices>
 [tinkerforge]:<http://www.tinkerforge.com/en>
 [MQTT]: <http://mqtt.org/>
 [TiMqWay.jar]: <https://drive.google.com/open?id=0B9adEExqrkwQRHZqanpfOS1JYVU>
 [d3Viewer]: <https://github.com/hardillb/d3-MQTT-Topic-Tree>
 [YAML]: <https://en.wikipedia.org/wiki/YAML>
 [https://github.com/knr1/ch.bfh.mobicomp.iot.mqtt.tutorial]: <https://github.com/knr1/ch.bfh.mobicomp.iot.mqtt.tutorial>
 [https://github.com/knr1/ch.quantasy.mqtt.gateway]: <https://github.com/knr1/ch.quantasy.mqtt.gateway>
 [Tinkerforge-Forum-entry-23169]<http://www.tinkerunity.org/forum/index.php/topic,3809.msg23169.html#msg23169>

 #<http://martinfowler.com/articles/microservices.html>
 

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
     D
       S
         connection ---[online|offline]         
         stack
           address
             <address>
               connected ---[true|false]
         device
           <address>
             <DeviceClass>
               <Instance> [true|false]
       E
         device
           connected --- <address>
           disconnected --- <address>
         stack
           address
             added --- <address>
             removed --- <removed>
       I 
         stack
           address
             add --- <address>
             remove --- <address>
     S
       connection --- online
```

### Connecting Master-Brick-1

As the description explains, we now have to tell TiMqWay where to look for the Master Bricks (Stacks). Hence, we want to attach Master-Brick-1 (say, its 
network-name is master-brick-1). Therefore the following message has to be sent to the following topic:
```
Topic: TF/Manager/I/stack/address/add
Message: master-brick-1
```

Looking into the mqtt-broker, the following can be seen:

```
TF
   Manager
     D
       #omitted for better readability
     S
       connection --- online
       device
         master-brick-1
           Temperature
             blue --- true
           LCD20x4
             lcd --- true
     E
       stack
         address
           added --- -hostname:"master-brick-1" port: 4223
     I
       stack
         address
           add --- master-brick-1
   Temperature
     D
       #omitted for better readability
     blue
       S
         connection --- online
         position --- "c"
         firmware --- 2-0-1
         hardware --- 1-1-0
   LCD20x4
     D
       #omitted for better readability
     lcd
       S
         connection --- online
         position --- "d"
         firmware --- 2-0-2
         hardware --- 1-2-0
```
If you want to switch on the backlight of the device called 'lcd' which is an instance of the 20x4LCD class then you publish the following message to
the following topic:
```
Topic: TF/LCD20x4/lcd/I/backlight
Message: true
```
What happenes is that the backlight is now switched to on at the specific 20x4LCD device. In the mqtt-broker, some topics changed as well.
```
TF
   Manager
     #omitted for better readability 
   Temperature
     D
       #omitted for better readability 
     blue
       S
         connection --- online
         position --- "c"
         firmware --- 2-0-1
         hardware --- 1-1-0
   LCD20x4
     D
       #omitted for better readability 
     lcd
       S
         connection --- online
         position --- "d"
         firmware --- 2-0-2
         hardware --- 1-2-0
         backlight --- true
       I
         backlight --- true
```
 
Now, let us connect the second master-brick (stack). This one is connected via USB, hence, its address is `localhost`:
```
Topic: TF/Manager/I/stack/address/add
Message: localhost
```
The TiMqWay-manager now knows two stacks and manages one temperature device more
```
TF
   Manager
     D
       #omitted for better readability
     S
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
     E
       stack
         address
           added --- -hostname:"localhost" port: 4223
     I
       stack
         address
           add --- localhost
   Temperature
     D
       S
         connection ---[online|offline]
         position --- [0|1|2|3|4|5|6|7|8|a|b|c|d]
         firmware --- [-32768..32767]_*
         hardware --- [-32768..32767]_*
         temperature
           callbackPeriod --- [0..9223372036854775807]
           threshold --- option: [x|o|i|<|>]\n min: [-2500..8500]\n max: [-2500..8500]
           deounce --- [0..9223372036854775807]
           mode ---[Slow|Fast]
       E
         temperature --- - timestamp: [0..9223372036854775807]\n value: [-2500..8500]\n
           reached --- - timestamp: [0..9223372036854775807]\n value: [-2500..8500]\n
       I
         debounce
           period --- [0..9223372036854775807]
         temperature
           callbackPeriod --- [0..9223372036854775807]
           threshold --- option: [x|o|i|<|>]\n min: [-2500..8500]\n max: [-2500..8500]
         mode -- mode:[Slow|Fast]
     blue
       S
         connection --- online
         position --- "c"
         firmware --- 2-0-1
         hardware --- 1-1-0
     red
       S
         connection --- online
         position --- "a"
         firmware --- 2-0-1
         hardware --- 1-1-0
   LCD20x4
     D
       S
         connection ---[online|offline]
         position --- [0|1|2|3|4|5|6|7|8|a|b|c|d]
         firmware --- [-32768..32767]_*
         hardware --- [-32768..32767]_*
         backlight --- [true|false]
         defaultText
           texts --- [line: [0..3]\n text: [String]_[1..20]]
           counter --- [-1..2147483647]
       I
         backlight --- [true|false]
         clearDisplay --- [true|false]
         defaultText
           texts --- [line: [0..3]\n text: [String]_[1..20]]
           counter --- [-1..2147483647]
         writeLines --- [line: [0..3]\n position: [0..18]\n text: [String]_[1..20]]
     lcd
       S
         connection --- online
         position --- "d"
         firmware --- 2-0-2
         hardware --- 1-2-0
```

If we want to have a temperature reading every second for `red`, we provide the following message to the following topic:
```
Topic: TF/Temperature/red/I/temperature/callbackPeriod
Message: 1000
```
Now, there is a reading every second, that will be promoted as an event to `TF/Temperature/red/event/temperature`

```
TF
   Manager
     #omitted for better readability
   Temperature
     D
       #omitted for better readability
     blue
       S
         connection --- online
         position --- "c"
         firmware --- 2-0-1
         hardware --- 1-1-0
     red
       S
         connection --- online
         position --- "a"
         firmware --- 2-0-1
         hardware --- 1-1-0
         temperature
           callbackPeriod --- 1000
       E
         temperature --- - timestamp: 1465398254115 value: 2668 
   LCD20x4
     D
       S
         connection ---[online|offline]
         position --- [0|1|2|3|4|5|6|7|8|a|b|c|d]
         firmware --- [-32768..32767]_*
         hardware --- [-32768..32767]_*
         backlight --- [true|false]
         defaultText
           texts --- [line: [0..3]\n text: [String]_[1..20]]
           counter --- [-1..2147483647]
       I
         backlight --- [true|false]
         clearDisplay --- [true|false]
         defaultText
           texts --- [line: [0..3]\n text: [String]_[1..20]]
           counter --- [-1..2147483647]
         writeLines --- [line: [0..3]\n position: [0..18]\n text: [String]_[1..20]]
     lcd
       S
         connection --- online
         position --- "d"
         firmware --- 2-0-2
         hardware --- 1-2-0
```
All that is left is to write a little agent, subscribing to the temperature of red and blue. Then process the values and write them to lcd... as a publish to
`TF/LCD20x4/lcd/I/writeLines`...

```
Topic: TF/LCD20x4/lcd/I/writeLines
Message: - line: 0
           position: 0
           text: "RED: 22°C"
         - line: 1
           position: 0
           text: "BLUE: 18°C"
```
## API
### ManagerService
This logical service allows a user to add or remove a true tinkerforge stack. As soon as a stack is connected, the service takes care of the 
connected Bricks and Bricklets.
```
TF/Manager/E/device/connected
   - timestamp: [0..9223372036854775807]
     value:
       hostName: <String>
       port: [0..4223..65536]
```
```
TF/Manager/E/device/disconnected
   - timestamp: [0..9223372036854775807]
     value:
       hostName: <String>
       port: [0..4223..65536]
```
```
TF/Manager/E/stack/address/added
   - timestamp: [0..9223372036854775807]
     value:
       hostName: <String>
       port: [0..4223..65536]
```
```
TF/Manager/E/stack/address/removed
   - timestamp: [0..9223372036854775807]
     value:
       hostName: <String>
       port: [0..4223..65536]
```
```
TF/Manager/I/stack/address/add
   hostName: <String>
    port: [0..4223..65536]
```
```
TF/Manager/I/stack/address/remove
   hostName: <String>
    port: [0..4223..65536]
```
```
TF/Manager/S/stack/address/<address>/connected
   [true|false]
```

### IMU
```
TF/IMU/<id>/E/acceleration
   - timestamp: [0..9223372036854775807]
     value:
        x: [-32768..32767]
        y: [-32768..32767]
        z: [-32768..32767]
```
```
TF/IMU/<id>/E/allData
   - timestamp: [0..9223372036854775807]
     value:
        @acceleration
        @magneticField
        @angularVelocity
        @orientation
        @quaternion
        @linearAcceleration
        @gravityVector
        @temperature
        calibrationStatus: [0..255]
```
```
TF/IMU/<id>/E/angularVelocity
   - timestamp: [0..9223372036854775807]
     value:
        x: [-32768..32767]
        y: [-32768..32767]
        z: [-32768..32767]
```
```
TF/IMU/<id>/E/magneticField
   - timestamp: [0..9223372036854775807]
     value:
        x: [-32768..32767]
        y: [-32768..32767]
        z: [-32768..32767]
```
```
TF/IMU/<id>/E/orientation
   - timestamp: [0..9223372036854775807]
     value:
        heading: [-32768..32767]
        roll: [-32768..32767]
        pitch: [-32768..32767]
```
```
TF/IMU/<id>/E/quaternion
   - timestamp: [0..9223372036854775807]
     value:
        w: [-32768..32767]
        x: [-32768..32767]
        y: [-32768..32767]
        z: [-32768..32767]
```
```
TF/IMU/<id>/I/LEDs/callbackPeriod
   true|false]
```
```
TF/IMU/<id>/I/acceleration/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMU/<id>/I/allData/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMU/<id>/I/angularVelocity/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMU/<id>/I/magneticField/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMU/<id>/I/orientation/calculation
   true|false]
```
```
TF/IMU/<id>/I/orientation/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMU/<id>/I/quaternion/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMU/<id>/I/statusLED/callbackPeriod
   true|false]
```
```
TF/IMU/<id>/S/LEDs/callbackPeriod
   [true|false]
```
```
TF/IMU/<id>/S/acceleration/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMU/<id>/S/allData/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMU/<id>/S/angularVelocity/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMU/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/IMU/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/IMU/<id>/S/magneticField/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMU/<id>/S/orientation/calculation
   [true|false]
```
```
TF/IMU/<id>/S/orientation/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMU/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/IMU/<id>/S/quaternion/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMU/<id>/S/statusLED/callbackPeriod
   [true|false]
```

### IMUV2
```
TF/IMUV2/<id>/E/acceleration
   - timestamp: [0..9223372036854775807]
     value:
       x: [-32768..32767]
       y: [-32768..32767]
       z: [-32768..32767]
```
```
TF/IMUV2/<id>/E/allData
   - timestamp: [0..9223372036854775807]
     value:
       @acceleration
       @magneticField
       @angularVelocity
       @orientation
       @quaternion
       @linearAcceleration
       @gravityVector
       @temperature
       calibrationStatus: [0..255]
```
```
TF/IMUV2/<id>/E/angularVelocity
   - timestamp: [0..9223372036854775807]
     value:
       x: [-32768..32767]
       y: [-32768..32767]
       z: [-32768..32767]
```
```
TF/IMUV2/<id>/E/gravityVector
   - timestamp: [0..9223372036854775807]
     value:
       x: [-32768..32767]
       y: [-32768..32767]
       z: [-32768..32767]
```
```
TF/IMUV2/<id>/E/linearAcceleration
   - timestamp: [0..9223372036854775807]
     value:
       x: [-32768..32767]
       y: [-32768..32767]
       z: [-32768..32767]
```
```
TF/IMUV2/<id>/E/magneticField
   - timestamp: [0..9223372036854775807]
     value:
       x: [-32768..32767]
       y: [-32768..32767]
       z: [-32768..32767]
```
```
TF/IMUV2/<id>/E/orientation
   - timestamp: [0..9223372036854775807]
     value:
       heading: [-32768..32767]
       roll: [-32768..32767]
       pitch: [-32768..32767]
```
```
TF/IMUV2/<id>/E/quaternion
   - timestamp: [0..9223372036854775807]
     value:
       w: [-32768..32767]
       x: [-32768..32767]
       y: [-32768..32767]
       z: [-32768..32767]
```
```
TF/IMUV2/<id>/E/temperature
   - timestamp: [0..9223372036854775807]
     value: [-128..127]
```
```
TF/IMUV2/<id>/I/LEDs/callbackPeriod
   true|false]
```
```
TF/IMUV2/<id>/I/acceleration/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/<id>/I/allData/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/<id>/I/angularVelocity/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/<id>/I/gravityVector/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/<id>/I/linearAcceleration/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/<id>/I/magneticField/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/<id>/I/orientation/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/<id>/I/quaternion/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/<id>/I/sensorFusionMode
   [0..2]
```
```
TF/IMUV2/<id>/I/statusLED
   true|false]
```
```
TF/IMUV2/<id>/I/temperature/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/<id>/S/LEDs/callbackPeriod
   [true|false]
```
```
TF/IMUV2/<id>/S/acceleration/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/<id>/S/allData/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/<id>/S/angularVelocity/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/IMUV2/<id>/S/gravityVector/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/IMUV2/<id>/S/linearAcceleration/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/<id>/S/magneticField/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/<id>/S/orientation/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/IMUV2/<id>/S/quaternion/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/<id>/S/sensorFusionMode
   [0..2]
```
```
TF/IMUV2/<id>/S/statusLED
   [true|false]
```
```
TF/IMUV2/<id>/S/temperature/callbackPeriod
   [0..9223372036854775807]
```

### LCD16x2
```
TF/LCD16x2/<id>/I/backlight
   [true|false]
```
```
TF/LCD16x2/<id>/I/clearDisplay
   [true|false]
```
```
TF/LCD16x2/<id>/I/configParameters
   cursor: [true|false]
    blinking: [true|false]
```
```
TF/LCD16x2/<id>/I/customCharacters
   [index: [0..15]
    pixels: [[-32768..32767]]_[1..8]]
```
```
TF/LCD16x2/<id>/I/writeLines
   [line: [0..1]
    position: [0..15]
    text: [String]_[1..16]]
```
```
TF/LCD16x2/<id>/S/backlight
   [true|false]
```
```
TF/LCD16x2/<id>/S/configParameters
   cursor: [true|false]
    blinking: [true|false]
```
```
TF/LCD16x2/<id>/S/customCharacters
   [index: [0..15]
    pixels: [[-32768..32767]]_[1..8]]
```
```
TF/LCD16x2/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/LCD16x2/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/LCD16x2/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### LCD20x4
```
TF/LCD20x4/<id>/I/backlight
   [true|false]
```
```
TF/LCD20x4/<id>/I/clearDisplay
   [true|false]
```
```
TF/LCD20x4/<id>/I/configParameters
   cursor: [true|false]
    blinking: [true|false]
```
```
TF/LCD20x4/<id>/I/customCharacters
   [index: [0..15]
    pixels: [[-32768..32767]]_[1..8]]
```
```
TF/LCD20x4/<id>/I/defaultText/counter
   [-1..2147483647]
```
```
TF/LCD20x4/<id>/I/defaultText/texts
   [line: [0..3]
    text: [String]_[1..20]]
```
```
TF/LCD20x4/<id>/I/writeLines
   [line: [0..3]
    position: [0..18]
    text: [String]_[1..20]]
```
```
TF/LCD20x4/<id>/S/backlight
   [true|false]
```
```
TF/LCD20x4/<id>/S/configParameters
   cursor: [true|false]
    blinking: [true|false]
```
```
TF/LCD20x4/<id>/S/customCharacters
   [index: [0..15]
    pixels: [[-32768..32767]]_[1..8]]
```
```
TF/LCD20x4/<id>/S/defaultText/counter
   [-1..2147483647]
```
```
TF/LCD20x4/<id>/S/defaultText/texts
   [line: [0..3]
    text: [String]_[1..20]]
```
```
TF/LCD20x4/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/LCD20x4/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/LCD20x4/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### Accelerometer
```
TF/Accelerometer/<id>/E/acceleration
   - timestamp: [0..9223372036854775807]
     value:
       x: [-32768..32767]
       y: [-32768..32767]
       z: [-32768..32767]
   
```
```
TF/Accelerometer/<id>/E/acceleration/reached
   - timestamp: [0..9223372036854775807]
    value:
       x: [-32768..32767]
       y: [-32768..32767]
       z: [-32768..32767]
```
```
TF/Accelerometer/<id>/I/acceleration/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Accelerometer/<id>/I/acceleration/threshold
   option: [x|o|i|<|>]
    minX: [-32768..32767]
    minY: [-32768..32767]
    minZ: [-32768..32767]
    maxX: [-32768..32767]
    maxY: [-32768..32767]
    maxZ: [-32768..32767]
```
```
TF/Accelerometer/<id>/I/configuration
   dataRate: [OFF|Hz3|Hz6|Hz12|Hz25|Hz50|Hz100|Hz400|Hz800|Hz1600]
    fullScale: [G2|G4|G6|G8|G16
    filterBandwidth: [Hz800|Hz400|Hz200|Hz50]
```
```
TF/Accelerometer/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/Accelerometer/<id>/S/acceleration/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Accelerometer/<id>/S/acceleration/threshold
   option: [x|o|i|<|>]
    minX: [-32768..32767]
    minY: [-32768..32767]
    minZ: [-32768..32767]
    maxX: [-32768..32767]
    maxY: [-32768..32767]
    maxZ: [-32768..32767]
```
```
TF/Accelerometer/<id>/S/configuration
   dataRate: [OFF|Hz3|Hz6|Hz12|Hz25|Hz50|Hz100|Hz400|Hz800|Hz1600]
    fullScale: [G2|G4|G6|G8|G16
    filterBandwidth: [Hz800|Hz400|Hz200|Hz50]
```
```
TF/Accelerometer/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/Accelerometer/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/Accelerometer/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/Accelerometer/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### AmbientLight
```
TF/AmbientLight/<id>/E/analogValue
   - timestamp: [0..9223372036854775807]
     value: [0..4095]
   
```
```
TF/AmbientLight/<id>/E/analogValue/reached
   - timestamp: [0..9223372036854775807]
     value: [0..4095]
   
```
```
TF/AmbientLight/<id>/E/illuminance
   - timestamp: [0..9223372036854775807]
     value: [0..9000]
   
```
```
TF/AmbientLight/<id>/E/illuminance/reached
   - timestamp: [0..9223372036854775807]
     value: [0..9000]
   
```
```
TF/AmbientLight/<id>/I/analogValue/callbackPeriod
   [0..9223372036854775807]
```
```
TF/AmbientLight/<id>/I/analogValue/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/AmbientLight/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/AmbientLight/<id>/I/illuminance/callbackPeriod
   [0..9223372036854775807]
```
```
TF/AmbientLight/<id>/I/illuminance/threshold
   option: [x|o|i|<|>]
    min: [0..9000]
    max: [0..9000]
```
```
TF/AmbientLight/<id>/S/analogValue/callbackPeriod
   [0..9223372036854775807]
```
```
TF/AmbientLight/<id>/S/analogValue/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/AmbientLight/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/AmbientLight/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/AmbientLight/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/AmbientLight/<id>/S/illuminance/callbackPeriod
   [0..9223372036854775807]
```
```
TF/AmbientLight/<id>/S/illuminance/threshold
   option: [x|o|i|<|>]
    min: [0..9000]
    max: [0..9000]
```
```
TF/AmbientLight/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### AmbientLightV2
```
TF/AmbientLightV2/<id>/E/illuminance
   - timestamp: [0..9223372036854775807]
     value: [0..100000]
   
```
```
TF/AmbientLightV2/<id>/E/illuminance/reached
   - timestamp: [0..9223372036854775807]
     value: [0..100000]
   
```
```
TF/AmbientLightV2/<id>/I/configuration
   illuminanceRange:[lx_unlimitted|lx_64000|lx_32000|lx_16000|lx_8000|lx_13000|lx_600]
    integrationTime: [ms_50|ms_100|ms_150|ms_200|ms_250|ms_300|ms_350|ms_400]
   
```
```
TF/AmbientLightV2/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/AmbientLightV2/<id>/I/illuminance/callbackPeriod
   [0..9223372036854775807]
```
```
TF/AmbientLightV2/<id>/I/illuminance/threshold
   option: [x|o|i|<|>]
    min: [0..100000]
    max: [0..100000]
```
```
TF/AmbientLightV2/<id>/S/configuration
   illuminanceRange:[lx_unlimitted|lx_64000|lx_32000|lx_16000|lx_8000|lx_13000|lx_600]
    integrationTime: [ms_50|ms_100|ms_150|ms_200|ms_250|ms_300|ms_350|ms_400]
   
```
```
TF/AmbientLightV2/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/AmbientLightV2/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/AmbientLightV2/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/AmbientLightV2/<id>/S/illuminance/callbackPeriod
   [0..9223372036854775807]
```
```
TF/AmbientLightV2/<id>/S/illuminance/threshold
   option: [x|o|i|<|>]
    min: [0..100000]
    max: [0..100000]
```
```
TF/AmbientLightV2/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### AnalogInV2
```
TF/AnalogInV2/<id>/E/analogValue
   - timestamp: [0..9223372036854775807]
     value: [0..4095]
   
```
```
TF/AnalogInV2/<id>/E/analogValue/reached
   - timestamp: [0..9223372036854775807]
     value: [0..4095]
   
```
```
TF/AnalogInV2/<id>/E/voltage
   - timestamp: [0..9223372036854775807]
     value: [-2147483648..2147483647]
   
```
```
TF/AnalogInV2/<id>/E/voltage/reached
   - timestamp: [0..42000]
     value: [0..42000]
   
```
```
TF/AnalogInV2/<id>/I/analogValue/callbackPeriod
   [0..9223372036854775807]
```
```
TF/AnalogInV2/<id>/I/analogValue/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/AnalogInV2/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/AnalogInV2/<id>/I/movingAverage
   [1..50]
```
```
TF/AnalogInV2/<id>/I/voltage/callbackPeriod
   [0..9223372036854775807]
```
```
TF/AnalogInV2/<id>/I/voltage/threshold
   option: [x|o|i|<|>]
    min: [0..42000]
    max: [0..42000]
```
```
TF/AnalogInV2/<id>/S/analogValue/callbackPeriod
   [0..9223372036854775807]
```
```
TF/AnalogInV2/<id>/S/analogValue/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/AnalogInV2/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/AnalogInV2/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/AnalogInV2/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/AnalogInV2/<id>/S/movingAverage
   [1..50]
```
```
TF/AnalogInV2/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/AnalogInV2/<id>/S/voltage/callbackPeriod
   [0..9223372036854775807]
```
```
TF/AnalogInV2/<id>/S/voltage/threshold
   option: [x|o|i|<|>]
    min: [0..42000]
    max: [0..42000]
```

### AnalogOutV2
```
TF/AnalogOutV2/<id>/I/outputVoltage
   [0..12000]
```
```
TF/AnalogOutV2/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/AnalogOutV2/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/AnalogOutV2/<id>/S/outputVoltage
   [0..12000]
```
```
TF/AnalogOutV2/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### Barometer
```
TF/Barometer/<id>/E/airPressure
   - timestamp: [0..9223372036854775807]
     value: [10000..1200000]
   
```
```
TF/Barometer/<id>/E/airPressure/reached
   - timestamp: [0..9223372036854775807]
     value: [10000..1200000]
   
```
```
TF/Barometer/<id>/E/altitude
   - timestamp: [0..9223372036854775807]
     value: [-2147483648..2147483647]
   
```
```
TF/Barometer/<id>/E/altitude/reached
   - timestamp: [0..9223372036854775807]
     value: [-2147483648..2147483647]
   
```
```
TF/Barometer/<id>/I/airPressure/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Barometer/<id>/I/airPressure/threshold
   option: [x|o|i|<|>]
    min: [10000..1200000]
    max: [10000..1200000]
```
```
TF/Barometer/<id>/I/altitude/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Barometer/<id>/I/altitude/threshold
   option: [x|o|i|<|>]
    min: [-2147483648..2147483647]
    max: [-2147483648..2147483647]
```
```
TF/Barometer/<id>/I/averaging
   averagingPressure: [0..10]
    averagingTemperature: [0..255]
    movingAveragePressure: [0..25]
```
```
TF/Barometer/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/Barometer/<id>/I/referenceAirPressure
   [-2147483648..2147483647]
```
```
TF/Barometer/<id>/S/airPressure/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Barometer/<id>/S/airPressure/threshold
   option: [x|o|i|<|>]
    min: [10000..1200000]
    max: [10000..1200000]
```
```
TF/Barometer/<id>/S/altitude/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Barometer/<id>/S/altitude/threshold
   option: [x|o|i|<|>]
    min: [-2147483648..2147483647]
    max: [-2147483648..2147483647]
```
```
TF/Barometer/<id>/S/averaging
   averagingPressure: [0..10]
    averagingTemperature: [0..255]
    movingAveragePressure: [0..25]
```
```
TF/Barometer/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/Barometer/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/Barometer/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/Barometer/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/Barometer/<id>/S/referenceAirPressure
   [-2147483648..2147483647]
```

### CO2
```
TF/CO2/<id>/E/CO2Concentration
   - timestamp: [0..9223372036854775807]
     value: [0..10000]
   
```
```
TF/CO2/<id>/E/CO2Concentration/reached
   - timestamp: [0..9223372036854775807]
     value: [0..10000]
   
```
```
TF/CO2/<id>/I/CO2Concentration/callbackPeriod
   [0..9223372036854775807]
```
```
TF/CO2/<id>/I/CO2Concentration/threshold
   option: [x|o|i|<|>]
    min: [0..10000]
    max: [0..10000]
```
```
TF/CO2/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/CO2/<id>/S/CO2Concentration/callbackPeriod
   [0..9223372036854775807]
```
```
TF/CO2/<id>/S/CO2Concentration/threshold
   option: [x|o|i|<|>]
    min: [0..10000]
    max: [0..10000]
```
```
TF/CO2/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/CO2/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/CO2/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/CO2/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### Color
```
TF/Color/<id>/E/color
   - timestamp: [0..9223372036854775807]
     value:
       red: [0..65535]
       green: [0..65535]
       blue: [0..65535]
       clear: [0..65535]
```
```
TF/Color/<id>/E/color/reached
   - timestamp: [0..9223372036854775807]
     value:
       red: [0..65535]
       green: [0..65535]
       blue: [0..65535]
       clear: [0..65535]
```
```
TF/Color/<id>/E/illuminance
   - timestamp: [0..9223372036854775807]
     value: [0..65535]
```
```
TF/Color/<id>/E/illuminance/reached
   - timestamp: [0..9223372036854775807]
     value: [0..65535]
   
```
```
TF/Color/<id>/I/color/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Color/<id>/I/color/threshold
   option: [x|o|i|<|>]
    minR: [0..65535]
    maxR: [0..65535]
    minG: [0..65535]
    maxG: [0..65535]
    minB: [0..65535]
    maxB: [0..65535]
```
```
TF/Color/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/Color/<id>/I/illuminance/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Color/<id>/I/illuminance/threshold
   [0..9223372036854775807]
```
```
TF/Color/<id>/S/color/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Color/<id>/S/color/threshold
   option: [x|o|i|<|>]
    minR: [0..65535]
    maxR: [0..65535]
    minG: [0..65535]
    maxG: [0..65535]
    minB: [0..65535]
    maxB: [0..65535]
```
```
TF/Color/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/Color/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/Color/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/Color/<id>/S/illuminance/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Color/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### DC
```
TF/DC/<id>/E/emergencyShutdown
   - timestamp: [0..9223372036854775807] 
     value: [0..9223372036854775807]
```
```
TF/DC/<id>/E/fullBrake
   - timestamp: [0..9223372036854775807] 
     value: [0..9223372036854775807]
```
```
TF/DC/<id>/E/undervoltage
   - timestamp: [0..9223372036854775807]
     value: [0..2147483647]
```
```
TF/DC/<id>/E/velocity
   - timestamp: [0..9223372036854775807]
     value: [0..32767]
```
```
TF/DC/<id>/E/velocity/reached
   - timestamp: [0..9223372036854775807]
     value: [0..32767]
```
```
TF/DC/<id>/I/acceleration
   [0..2147483647]
```
```
TF/DC/<id>/I/driverMode
   [0|1]
```
```
TF/DC/<id>/I/enabled
   [true|false]
```
```
TF/DC/<id>/I/fullBrake
   [true|false]
```
```
TF/DC/<id>/I/minimumVoltage
   [6000..2147483647]
```
```
TF/DC/<id>/I/pwmFrequency
   [1..20000]
```
```
TF/DC/<id>/I/velocity/callbackPeriod
   [0..2147483647]
```
```
TF/DC/<id>/I/velocity/velocity
   -32767..32767
```
```
TF/DC/<id>/S/acceleration
   [0..2147483647]
```
```
TF/DC/<id>/S/driverMode
   [0|1]
```
```
TF/DC/<id>/S/enabled
   [true|false]
```
```
TF/DC/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/DC/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/DC/<id>/S/minimumVoltage
   [6..2147483647]
```
```
TF/DC/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/DC/<id>/S/pwmFrequency
   [1..20000]
```
```
TF/DC/<id>/S/velocity/callbackPeriod
   [0..2147483647]
```
```
TF/DC/<id>/S/velocity/velocity
   -32767..32767
```

### DistanceIR
```
TF/DistanceIR/<id>/E/analogValue
   - timestamp: [0..9223372036854775807]
     value: [0..4095]
```
```
TF/DistanceIR/<id>/E/analogValue/reached
   - timestamp: [0..9223372036854775807]
     value: [0..4095]
```
```
TF/DistanceIR/<id>/E/distance
   - timestamp: [0..9223372036854775807]
     value: [[40..300]|[100..800]|[200..1500]]
```
```
TF/DistanceIR/<id>/E/distance/reached
   - timestamp: [0..9223372036854775807]
     value: [[40..300]|[100..800]|[200..1500]]
```
```
TF/DistanceIR/<id>/I/analogValue/callbackPeriod
   [0..9223372036854775807]
```
```
TF/DistanceIR/<id>/I/analogValue/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/DistanceIR/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/DistanceIR/<id>/I/distance/callbackPeriod
   [0..9223372036854775807]
```
```
TF/DistanceIR/<id>/I/distance/threshold
   option: [x|o|i|<|>]
    min: [[40..300]|[100..800]|[200..1500]]
    max: [[40..300]|[100..800]|[200..1500]]
```
```
TF/DistanceIR/<id>/S/analogValue/callbackPeriod
   [0..9223372036854775807]
```
```
TF/DistanceIR/<id>/S/analogValue/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/DistanceIR/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/DistanceIR/<id>/S/distance/callbackPeriod
   [0..9223372036854775807]
```
```
TF/DistanceIR/<id>/S/distance/threshold
   option: [x|o|i|<|>]
    min: [[40..300]|[100..800]|[200..1500]]
    max: [[40..300]|[100..800]|[200..1500]]
```
```
TF/DistanceIR/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/DistanceIR/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/DistanceIR/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### DistanceUS
```
TF/DistanceUS/<id>/E/distance
   - timestamp: [0..9223372036854775807]
     value: [[0..4095]
```
```
TF/DistanceUS/<id>/E/distance/reached
   - timestamp: [0..9223372036854775807]
     value: [0..4095]
```
```
TF/DistanceUS/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/DistanceUS/<id>/I/distance/callbackPeriod
   [0..9223372036854775807]
```
```
TF/DistanceUS/<id>/I/distance/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/DistanceUS/<id>/I/movingAverage
   [0..100]
```
```
TF/DistanceUS/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/DistanceUS/<id>/S/distance/callbackPeriod
   [0..9223372036854775807]
```
```
TF/DistanceUS/<id>/S/distance/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/DistanceUS/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/DistanceUS/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/DistanceUS/<id>/S/movingAverage
   [0..100]
```
```
TF/DistanceUS/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### DualButton
```
TF/DualButton/<id>/E/stateChanged
   - timestamp: [0..9223372036854775807]
     value:
       led1: [AutoToggleOn|AutoToggleOff|On|Off]
       led2: [AutoToggleOn|AutoToggleOff|On|Off]
      switch1: [0|1]
      switch2: [0|1]
```
```
TF/DualButton/<id>/I/LEDState
   leftLED: [AutoToggleOn|AutoToggleOff|On|Off]
    rightLED: [AutoToggleOn|AutoToggleOff|On|Off] 
```
```
TF/DualButton/<id>/I/selectedLEDState
   led: [AutoToggleOn|AutoToggleOff|On|Off]
```
```
TF/DualButton/<id>/S/LEDState
   led1: [AutoToggleOn|AutoToggleOff|On|Off]
    led2: [AutoToggleOn|AutoToggleOff|On|Off]
```
```
TF/DualButton/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/DualButton/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/DualButton/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### DustDetector
```
TF/DustDetector/<id>/E/dustDensity
   - timestamp: [0..9223372036854775807]
     value: [0..500]
```
```
TF/DustDetector/<id>/E/dustDensity/reached
   - timestamp: [0..9223372036854775807]
     value: [0..500]
```
```
TF/DustDetector/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/DustDetector/<id>/I/dustDensity/callbackPeriod
   [0..9223372036854775807]
```
```
TF/DustDetector/<id>/I/dustDensity/threshold
   option: [x|o|i|<|>]
    min: [0..500]
    max: [0..500]
```
```
TF/DustDetector/<id>/I/movingAverage
   [0..100]
```
```
TF/DustDetector/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/DustDetector/<id>/S/dustDensity/callbackPeriod
   [0..9223372036854775807]
```
```
TF/DustDetector/<id>/S/dustDensity/threshold
   option: [x|o|i|<|>]
    min: [0..500]
    max: [0..500]
```
```
TF/DustDetector/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/DustDetector/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/DustDetector/<id>/S/movingAverage
   [0..100]
```
```
TF/DustDetector/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### GPS
```
TF/GPS/<id>/E/altitude
   - timestamp: [0..9223372036854775807]
     value:
       altitude: [-2147483648..2147483647]
       geoidalSeparation: [-2147483648..2147483647]
```
```
TF/GPS/<id>/E/coordinates
   - timestamp: [0..9223372036854775807]
     value:
       latitude: [-9223372036854775808..9223372036854775807]
       ns: ['N'|'S']
       longitude: [-9223372036854775808..9223372036854775807]
    ew: ['E'|'W']
    pdop: [-2147483648..2147483647]
    hdop: [-2147483648..2147483647]
       vdop: [-2147483648..2147483647]
        epe: [-2147483648..2147483647]
```
```
TF/GPS/<id>/E/dateTime
   - timestamp: [0..9223372036854775807]
     value:
       date: [[d|dd]mmyy]
       time: [hhmmssxxx]
```
```
TF/GPS/<id>/E/motion
   - timestamp: [0..9223372036854775807]
     value:
       course: [0..36000]
       speed: [0..9223372036854775807]
```
```
TF/GPS/<id>/E/status
   - timestamp: [0..9223372036854775807]
     value:
       fix: [1|2|3]]
       satellitesView: [0..32767]
       satellitesUsed: [0..32767]
```
```
TF/GPS/<id>/I/altitude/callbackPeriod
   [0..9223372036854775807]
```
```
TF/GPS/<id>/I/coordinates/callbackPeriod
   [0..9223372036854775807]
```
```
TF/GPS/<id>/I/dateTime/callbackPeriod
   [0..9223372036854775807]
```
```
TF/GPS/<id>/I/motion/callbackPeriod
   [0..9223372036854775807]
```
```
TF/GPS/<id>/S/altitude/callbackPeriod
   [0..9223372036854775807]
```
```
TF/GPS/<id>/S/coordinates/callbackPeriod
   [0..9223372036854775807]
```
```
TF/GPS/<id>/S/dateTime/callbackPeriod
   [0..9223372036854775807]
```
```
TF/GPS/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/GPS/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/GPS/<id>/S/motion/callbackPeriod
   [0..9223372036854775807]
```
```
TF/GPS/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### GPSv2
```
TF/GPSv2/<id>/E/altitude
   - timestamp: [0..9223372036854775807]
    value:
      altitude: [-2147483648..2147483647]
      geoidalSeparation: [-2147483648..2147483647]
```
```
TF/GPSv2/<id>/E/coordinates
   - timestamp: [0..9223372036854775807]
    value:
     latitude: [-9223372036854775808..9223372036854775807]
      ns: ['N'|'S']
      longitude: [-9223372036854775808..9223372036854775807]
    ew: ['E'|'W']
```
```
TF/GPSv2/<id>/E/dateTime
   - timestamp: [0..9223372036854775807]
    value:
     date: [[d|dd]mmyy]
     time: [hhmmssxxx]
```
```
TF/GPSv2/<id>/E/motion
   - timestamp: [0..9223372036854775807]
    value:
     course: [0..36000]
     speed: [0..9223372036854775807]
```
```
TF/GPSv2/<id>/E/status
   - timestamp: [0..9223372036854775807]
    value:
     fix: [true|false]]
     satellitesView: [0..32767]
```
```
TF/GPSv2/<id>/I/altitude/callbackPeriod
   [0..9223372036854775807]
```
```
TF/GPSv2/<id>/I/coordinates/callbackPeriod
   [0..9223372036854775807]
```
```
TF/GPSv2/<id>/I/dateTime/callbackPeriod
   [0..9223372036854775807]
```
```
TF/GPSv2/<id>/I/fix/led
   [OFF|ON|HEARTBEAT|FIX|PPS]
```
```
TF/GPSv2/<id>/I/motion/callbackPeriod
   [0..9223372036854775807]
```
```
TF/GPSv2/<id>/I/status/led
   [OFF|ON|HEARTBEAT|STATUS]
```
```
TF/GPSv2/<id>/S/altitude/callbackPeriod
   [0..9223372036854775807]
```
```
TF/GPSv2/<id>/S/coordinates/callbackPeriod
   [0..9223372036854775807]
```
```
TF/GPSv2/<id>/S/dateTime/callbackPeriod
   [0..9223372036854775807]
```
```
TF/GPSv2/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/GPSv2/<id>/S/fix/led
   [OFF|ON|HEARTBEAT|FIX|PPS]
```
```
TF/GPSv2/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/GPSv2/<id>/S/motion/callbackPeriod
   [0..9223372036854775807]
```
```
TF/GPSv2/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/GPSv2/<id>/S/status/led
   [OFF|ON|HEARTBEAT|STATUS]
```

### HallEffect
```
TF/HallEffect/<id>/E/edgeCount
   - timestamp: [0..9223372036854775807]
     value:
       count: [0..9223372036854775807]
       greater35Gauss: [true|false]
```
```
TF/HallEffect/<id>/E/edgeCount/reset
   - timestamp: [0..9223372036854775807]
     value:    [0..9223372036854775807]
```
```
TF/HallEffect/<id>/I/configuration
   edgeType: [RISING|FALLING|BOTH]
    debounce: [0..100]
   
```
```
TF/HallEffect/<id>/I/edgeCount/callbackPeriod
   [0..9223372036854775807]
```
```
TF/HallEffect/<id>/I/edgeCount/interrupt
   [0..9223372036854775807]
```
```
TF/HallEffect/<id>/I/edgeCount/reset
   [true|false]
```
```
TF/HallEffect/<id>/S/configuration
   edgeType: [RISING|FALLING|BOTH]
    debounce: [0..100]
   
```
```
TF/HallEffect/<id>/S/edgeCount/callbackPeriod
   [0..9223372036854775807]
```
```
TF/HallEffect/<id>/S/edgeCount/interrupt
   [0..9223372036854775807]
```
```
TF/HallEffect/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/HallEffect/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/HallEffect/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### Humidity
```
TF/Humidity/<id>/E/analogValue
   - timestamp: [0..9223372036854775807]
     value: [0..4095]
```
```
TF/Humidity/<id>/E/analogValue/reached
   - timestamp: [0..9223372036854775807]
     value: [0..4095]
```
```
TF/Humidity/<id>/E/humidity
   - timestamp: [0..9223372036854775807]
     value: [0..1000]
```
```
TF/Humidity/<id>/E/humidity/reached
   - timestamp: [0..9223372036854775807]
     value: [0..1000]
```
```
TF/Humidity/<id>/I/analogValue/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Humidity/<id>/I/analogValue/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/Humidity/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/Humidity/<id>/I/humidity/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Humidity/<id>/I/humidity/threshold
   option: [x|o|i|<|>]
    min: [0..1000]
    max: [0..9000]
```
```
TF/Humidity/<id>/S/analogValue/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Humidity/<id>/S/analogValue/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/Humidity/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/Humidity/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/Humidity/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/Humidity/<id>/S/humidity/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Humidity/<id>/S/humidity/threshold
   option: [x|o|i|<|>]
    min: [0..1000]
    max: [0..1000]
```
```
TF/Humidity/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### Joystick
```
TF/Joystick/<id>/E/analogValue
   - timestamp: [0..9223372036854775807]
     value:
       x: [0..4095]
       y: [0..4095]
```
```
TF/Joystick/<id>/E/analogValue/reached
   - timestamp: [0..9223372036854775807]
     value:
       x: [0..4095]
       y: [0..4095]
```
```
TF/Joystick/<id>/E/calibrate
   [0..9223372036854775807]
```
```
TF/Joystick/<id>/E/position
   - timestamp: [0..9223372036854775807]
     value:
       x: [-100..100]
       y: [-100..100]
```
```
TF/Joystick/<id>/E/position/reached
   - timestamp: [0..9223372036854775807]
     value:
       x: [-100..100]
       y: [-100..100]
```
```
TF/Joystick/<id>/E/pressed
   [0..9223372036854775807]
```
```
TF/Joystick/<id>/E/released
   [0..9223372036854775807]
```
```
TF/Joystick/<id>/I/analogValue/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Joystick/<id>/I/analogValue/threshold
   option: [x|o|i|<|>]
    minX: [0..4095]
    maxX: [0..4095]
    minY: [0..4095]
    maxY: [0..4095]
```
```
TF/Joystick/<id>/I/calibrate
   [true|false]
```
```
TF/Joystick/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/Joystick/<id>/I/position/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Joystick/<id>/I/position/threshold
   option: [x|o|i|<|>]
    minX: [-100..100]
    maxX: [-100..100]
    minY: [-100..100]
    maxY: [-100..100]
```
```
TF/Joystick/<id>/S/analogValue/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Joystick/<id>/S/analogValue/threshold
   option: [x|o|i|<|>]
    minX: [0..4095]
    maxX: [0..4095]
    minY: [0..4095]
    maxY: [0..4095]
```
```
TF/Joystick/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/Joystick/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/Joystick/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/Joystick/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/Joystick/<id>/S/position/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Joystick/<id>/S/position/threshold
   option: [x|o|i|<|>]
    minX: [-100..100]
    maxX: [-100..100]
    minY: [-100..100]
    maxY: [-100..100]
```

### LaserRangeFinder
```
TF/LaserRangeFinder/<id>/E/distance
   - timestamp: [0..9223372036854775807]
     value: [0..4000]
```
```
TF/LaserRangeFinder/<id>/E/distance/reached
   - timestamp: [0..9223372036854775807]
     value: [0..4000]
```
```
TF/LaserRangeFinder/<id>/E/velocity
   - timestamp: [-127..9223372036854775807]
     value: [0..127]
```
```
TF/LaserRangeFinder/<id>/E/velocity/reached
   - timestamp: [0..9223372036854775807]
     value: [-127..127]
```
```
TF/LaserRangeFinder/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/LaserRangeFinder/<id>/I/deviceConfiguration
   acquisition: [1..255]
    quickTermination: [true|false]
    thresholdValue: [0..255]
    measurementFrequency: [0|10..500]
```
```
TF/LaserRangeFinder/<id>/I/deviceMode
   mode: [distance|velocity_12_7|velocity_31_75|velocity_63_5|velocity_127]
```
```
TF/LaserRangeFinder/<id>/I/distance/callbackPeriod
   [0..9223372036854775807]
```
```
TF/LaserRangeFinder/<id>/I/distance/threshold
   option: [x|o|i|<|>]
    min: [0..4000]
    max: [0..4000]
```
```
TF/LaserRangeFinder/<id>/I/laser
   [true|false]
```
```
TF/LaserRangeFinder/<id>/I/movingAverage
   averagingDistance:[0..30]
    averagingVelocity:[0..30]
```
```
TF/LaserRangeFinder/<id>/I/velocity/callbackPeriod
   [0..9223372036854775807]
```
```
TF/LaserRangeFinder/<id>/I/velocity/threshold
   option: [x|o|i|<|>]
    min: [-127..127]
    max: [-127..127]
```
```
TF/LaserRangeFinder/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/LaserRangeFinder/<id>/S/deviceConfiguration
   acquisition: [1..255]
    quickTermination: [true|false]
    thresholdValue: [0..255]
    measurementFrequency: [0|10..500]
```
```
TF/LaserRangeFinder/<id>/S/deviceMode
   mode: [distance|velocity_12_7|velocity_31_75|velocity_63_5|velocity_127]
```
```
TF/LaserRangeFinder/<id>/S/distance/callbackPeriod
   [0..9223372036854775807]
```
```
TF/LaserRangeFinder/<id>/S/distance/threshold
   option: [x|o|i|<|>]
    min: [0..4000]
    max: [0..4000]
```
```
TF/LaserRangeFinder/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/LaserRangeFinder/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/LaserRangeFinder/<id>/S/laser
   [true|false]
```
```
TF/LaserRangeFinder/<id>/S/movingAverage
   averagingDistance:[0..30]
    averagingVelocity:[0..30]
```
```
TF/LaserRangeFinder/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/LaserRangeFinder/<id>/S/sensorHardwareVersion
   [v1|v3]
```
```
TF/LaserRangeFinder/<id>/S/velocity/callbackPeriod
   [0..9223372036854775807]
```
```
TF/LaserRangeFinder/<id>/S/velocity/threshold
   option: [x|o|i|<|>]
    min: [-127..-127]
    max: [-127..127]
```

### LEDStrip
```
TF/LEDStrip/<id>/E/frame/rendered
   - timestamp: [0..9223372036854775807]
     value: [0..2147483647]
   
```
```
TF/LEDStrip/<id>/E/laging
   - timestamp: [0..9223372036854775807]
```
```
TF/LEDStrip/<id>/I/config
   chipType: [WS2801|WS2811|WS2812]
    frameDurationInMilliseconds: [0..9223372036854775807]
    clockFrequencyOfICsInHz: [10000..2000000]
    numberOfLEDs: [1..320]
    channelMapping: [rgb|rbg|grb|gbr|brg|bgr]
```
```
TF/LEDStrip/<id>/I/frame
   channels: {{[0..255],..,[0..255]}_numLEDs
    ...
    {[0..255],..,[0..255]}_numLEDs}_numChannels
```
```
TF/LEDStrip/<id>/I/multiFrames
   { channels: {{[0..255],..,[0..255]}_numLEDs
    ...
    {[0..255],..,[0..255]}_numLEDs}_numChannels }_*
```
```
TF/LEDStrip/<id>/S/config
   chipType: [WS2801|WS2811|WS2812|WS2812RGBW|LPD8806|APA102]
    frameDurationInMilliseconds: [0..9223372036854775807]
    clockFrequencyOfICsInHz: [10000..2000000]
    numberOfLEDs: [1..320]
    channelMapping: [rgb|rbg|grb|gbr|brg|bgr]
```
```
TF/LEDStrip/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/LEDStrip/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/LEDStrip/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### Line
```
TF/Line/<id>/E/reflectivity
   - timestamp: [0..9223372036854775807]
     value: [[0..4095]
```
```
TF/Line/<id>/E/reflectivity/reached
   - timestamp: [0..9223372036854775807]
     value: [0..4095]
```
```
TF/Line/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/Line/<id>/I/reflectivity/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Line/<id>/I/reflectivity/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/Line/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/Line/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/Line/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/Line/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/Line/<id>/S/reflectivity/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Line/<id>/S/reflectivity/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```

### LinearPoti
```
TF/LinearPoti/<id>/E/analogValue
   - timestamp: [0..9223372036854775807]
     value: [0..4095]
```
```
TF/LinearPoti/<id>/E/analogValue/reached
   - timestamp: [0..9223372036854775807]
     value: [0..4095]
```
```
TF/LinearPoti/<id>/E/position
   - timestamp: [0..9223372036854775807]
     value: [0..100]
```
```
TF/LinearPoti/<id>/E/position/reached
   - timestamp: [0..9223372036854775807]
     value: [0..100]
```
```
TF/LinearPoti/<id>/I/analogValue/callbackPeriod
   [0..9223372036854775807]
```
```
TF/LinearPoti/<id>/I/analogValue/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/LinearPoti/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/LinearPoti/<id>/I/position/callbackPeriod
   [0..9223372036854775807]
```
```
TF/LinearPoti/<id>/I/position/threshold
   option: [x|o|i|<|>]
    min: [0..100]
    max: [0..100]
```
```
TF/LinearPoti/<id>/S/analogValue/callbackPeriod
   [0..9223372036854775807]
```
```
TF/LinearPoti/<id>/S/analogValue/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/LinearPoti/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/LinearPoti/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/LinearPoti/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/LinearPoti/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/LinearPoti/<id>/S/position/callbackPeriod
   [0..9223372036854775807]
```
```
TF/LinearPoti/<id>/S/position/threshold
   option: [x|o|i|<|>]
    min: [0..100]
    max: [0..100]
```

### LoadCell
```
TF/LoadCell/<id>/E/weight
   - timestamp: [0..9223372036854775807]
     value: [-50001..50001]
```
```
TF/LoadCell/<id>/E/weight/reached
   - timestamp: [0..9223372036854775807]
     value: [-50001..50001]
```
```
TF/LoadCell/<id>/I/LED
   true|false]
```
```
TF/LoadCell/<id>/I/calibrate
   [0..50001]
```
```
TF/LoadCell/<id>/I/configuration
   gain:[gain128X|gain64X|gain32X]
    rate: [rate10Hz|rate80Hz]
```
```
TF/LoadCell/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/LoadCell/<id>/I/movingAverage
   [1..40]
```
```
TF/LoadCell/<id>/I/tare
   [true|false]
```
```
TF/LoadCell/<id>/I/weight/callbackPeriod
   [0..9223372036854775807]
```
```
TF/LoadCell/<id>/I/weight/threshold
   option: [x|o|i|<|>]
    min: [-50001..50001]
    max: [-50001..50001]
```
```
TF/LoadCell/<id>/S/LED
   [true|false]
```
```
TF/LoadCell/<id>/S/configuration
   gain:[gain128X|gain64X|gain32X]
    rate: [rate10Hz|rate80Hz]
```
```
TF/LoadCell/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/LoadCell/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/LoadCell/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/LoadCell/<id>/S/movingAverage
   [1..40]
```
```
TF/LoadCell/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/LoadCell/<id>/S/weight/callbackPeriod
   [0..9223372036854775807]
```
```
TF/LoadCell/<id>/S/weight/threshold
   option: [x|o|i|<|>]
    min: [0..50001]
    max: [-50001..50001]
```

### Master
```
TF/Master/<id>/E/USB/voltage
   - timestamp: [0..9223372036854775807]
     value: [-2147483648..2147483647]
```
```
TF/Master/<id>/E/USB/voltage/reached
   - timestamp: [0..9223372036854775807]
     value: [-2147483648..2147483647]
```
```
TF/Master/<id>/E/reset
   - timestamp: [0..9223372036854775807]
```
```
TF/Master/<id>/E/stack/current
   - timestamp: [0..9223372036854775807]
     value: [0..2147483647]
```
```
TF/Master/<id>/E/stack/current/reached
   - timestamp: [0..9223372036854775807]
     value: [0..2147483647]
```
```
TF/Master/<id>/E/stack/voltage
   - timestamp: [0..9223372036854775807]
     value: [-2147483648..2147483647]
```
```
TF/Master/<id>/E/stack/voltage/reached
   - timestamp: [0..9223372036854775807]
     value: [-2147483648..2147483647]
```
```
TF/Master/<id>/I/USB/voltage/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Master/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/Master/<id>/I/stack/current/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Master/<id>/I/stack/current/callbackThreshold
   [option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/Master/<id>/I/stack/voltage/callbackPeriod
   [-9223372036854775808..9223372036854775807]
```
```
TF/Master/<id>/I/stack/voltage/callbackThreshold
   [option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/Master/<id>/I/statusLED/enabled
   [true|false]
```
```
TF/Master/<id>/S/USB/voltage/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Master/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/Master/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/Master/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/Master/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/Master/<id>/S/stack/current/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Master/<id>/S/stack/current/callbackThreshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/Master/<id>/S/stack/voltage/callbackPeriod
   [-9223372036854775808..9223372036854775807]
```
```
TF/Master/<id>/S/stack/voltage/callbackThreshold
   [option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/Master/<id>/S/statusLED/enabled
   [true|false]
```

### Moisture
```
TF/Moisture/<id>/E/moisture
   - timestamp: [0..9223372036854775807]
     value: [0..4095]
```
```
TF/Moisture/<id>/E/moisture/reached
   - timestamp: [0..9223372036854775807]
     value: [0..4095]
```
```
TF/Moisture/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/Moisture/<id>/I/moisture/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Moisture/<id>/I/moisture/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/Moisture/<id>/I/movingAverage
   [0..100]
```
```
TF/Moisture/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/Moisture/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/Moisture/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/Moisture/<id>/S/moisture/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Moisture/<id>/S/moisture/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/Moisture/<id>/S/movingAverage
   [0..100]
```
```
TF/Moisture/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### MotionDetector
```
TF/MotionDetector/<id>/E/eventDetectionCycleEnded
   - timestamp: [0..9223372036854775807]
     value: true
```
```
TF/MotionDetector/<id>/E/motionDetected
   - timestamp: [0..9223372036854775807]
     value: true
```
```
TF/MotionDetector/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/MotionDetector/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/MotionDetector/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### MultiTouch
```
TF/MultiTouch/<id>/E/recalibrated
   - timestamp: [0..9223372036854775807]
     value: true
```
```
TF/MultiTouch/<id>/E/touchState
   - timestamp: [0..9223372036854775807]
     value: [0..8191]
```
```
TF/MultiTouch/<id>/I/electrode/config
   [0..8191]
```
```
TF/MultiTouch/<id>/I/electrode/sensitivity
   [0..8191]
```
```
TF/MultiTouch/<id>/I/recalibrate
   [true|false]
```
```
TF/MultiTouch/<id>/S/electrode/config
   [0..8191]
```
```
TF/MultiTouch/<id>/S/electrode/sensitivity
   [5..201]
```
```
TF/MultiTouch/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/MultiTouch/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/MultiTouch/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### NfcRfid
```
TF/NfcRfid/<id>/E/tag/discovered
   - timestamp: [0..9223372036854775807]
     value:
       id: [00..FF]_9
       type: [MifareClassic|Type1|Type2]
```
```
TF/NfcRfid/<id>/E/tag/read
   - timestamp: [0..9223372036854775807]
     value:
       id: [00..FF]_9
       value: [00..FF]_*
```
```
TF/NfcRfid/<id>/E/tag/vanished
   - timestamp: [0..9223372036854775807]
     value:
       id: [00..FF]_9
       type: [MifareClassic|Type1|Type2]
```
```
TF/NfcRfid/<id>/E/tag/written
   - timestamp: [0..9223372036854775807]
     value:
       id: [00..FF]_9
       state: [WritePageError|WritePageReady]
       value: [00..FF]_*
```
```
TF/NfcRfid/<id>/I/read
   [00..FF]_9
```
```
TF/NfcRfid/<id>/I/scanning/callbackPeriod
   [0..9223372036854775807]
```
```
TF/NfcRfid/<id>/I/write
   id: [00..FF]_9
     value: [00..FF]_*
```
```
TF/NfcRfid/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/NfcRfid/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/NfcRfid/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/NfcRfid/<id>/S/scanning/callbackPeriod
   [0..9223372036854775807]
```

### PiezoSpeaker
```
TF/PiezoSpeaker/<id>/E/calibrated
   - timestamp: [0..9223372036854775807]
     value: true
```
```
TF/PiezoSpeaker/<id>/E/finished
   - timestamp: [0..9223372036854775807]
     value: true
```
```
TF/PiezoSpeaker/<id>/E/started
   - timestamp: [0..9223372036854775807]
     value: true
```
```
TF/PiezoSpeaker/<id>/I/beep
   duration: [0..4294967295]
    frequency: [585..7100]
```
```
TF/PiezoSpeaker/<id>/I/calibrate
   [true|false]
```
```
TF/PiezoSpeaker/<id>/I/morse
   string: [.|-| |]_60
    frequency: [585..7100]
```
```
TF/PiezoSpeaker/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/PiezoSpeaker/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/PiezoSpeaker/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### PTC
```
TF/PTC/<id>/E/resistance
   - timestamp: [0..9223372036854775807]
     value: [0..32767]
```
```
TF/PTC/<id>/E/resistance/reached
   - timestamp: [0..9223372036854775807]
     value: [0..32767]
```
```
TF/PTC/<id>/E/temperature
   - timestamp: [0..9223372036854775807]
     value: [-24600..84900]
```
```
TF/PTC/<id>/E/temperature/reached
   - timestamp: [0..9223372036854775807]
     value: [-24600..84900]
```
```
TF/PTC/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/PTC/<id>/I/noiseReductionFilter
   filter: [Hz_50|Hz_60]
```
```
TF/PTC/<id>/I/resistance/callbackPeriod
   [0..9223372036854775807]
```
```
TF/PTC/<id>/I/resistance/threshold
   option: [x|o|i|<|>]
    min: [0..32767]
    max: [0..32767]
```
```
TF/PTC/<id>/I/temperature/callbackPeriod
   [0..9223372036854775807]
```
```
TF/PTC/<id>/I/temperature/threshold
   option: [x|o|i|<|>]
    min: [-24600..84900]
    max: [-24600..84900]
```
```
TF/PTC/<id>/I/wireMode
   [2|3|4]
```
```
TF/PTC/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/PTC/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/PTC/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/PTC/<id>/S/noiseReductionFilter
   filter: [Hz_50|Hz_60]
```
```
TF/PTC/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/PTC/<id>/S/resistance/callbackPeriod
   [0..9223372036854775807]
```
```
TF/PTC/<id>/S/resistance/threshold
   option: [x|o|i|<|>]
    min: [0..32767]
    max: [0..32767]
```
```
TF/PTC/<id>/S/temperature/callbackPeriod
   [0..9223372036854775807]
```
```
TF/PTC/<id>/S/temperature/threshold
   option: [x|o|i|<|>]
    min: [-24600..84900]
    max: [-24600..84900]
```
```
TF/PTC/<id>/S/wireMode
   [2|3|4]
```

### RealTimeClock
```
TF/RealTimeClock/<id>/E/alarm
   - timestamp: [0..9223372036854775807]
     value:
       year: [2000..2099]
       month: [1..12]
       day: [1..31]
       hour: [0..23]
       minute: [0..59]
       second: [0..59]
       centisecond: [0..9]
       weekday: [monday|tuesday|wednesday|thursday|friday|saturday|sunday]
```
```
TF/RealTimeClock/<id>/E/dateTime
   - timestamp: [0..9223372036854775807]
     value:
       year: [2000..2099]
       month: [1..12]
       day: [1..31]
       hour: [0..23]
       minute: [0..59]
       second: [0..59]
       centisecond: [0..9]
       weekday: [monday|tuesday|wednesday|thursday|friday|saturday|sunday]
```
```
TF/RealTimeClock/<id>/I/alarm
   month: [-1|1..12]
    day: [-1|1..31]
    hour: [-1|0..23]
    minute: [-1|0..59]
    second: [-1|0..59]
    weekday: [disabled|monday|tuesday|wednesday|thursday|friday|saturday|sunday]
    interval:[-1|0..2147483647]
```
```
TF/RealTimeClock/<id>/I/dateTime/callbackPeriod
   [0..9223372036854775807]
```
```
TF/RealTimeClock/<id>/I/dateTime/set
   year: [2000..2099]
    month: [1..12]
    day:b[1..31]
    hour: [0..23]
    minute: [0..59]
    second: [0..59]
    centisecond: [0..9]
    weekday: [monday|tuesday|wednesday|thursday|friday|saturday|sunday]
```
```
TF/RealTimeClock/<id>/I/offset
   [-128..127]
```
```
TF/RealTimeClock/<id>/S/alarm
   month: [-1|1..12]
    day: [-1|1..31]
    hour: [-1|0..23]
    minute: [-1|0..59]
    second: [-1|0..59]
    weekday: [disabled|monday|tuesday|wednesday|thursday|friday|saturday|sunday]
    interval:[-1|0..2147483647]
```
```
TF/RealTimeClock/<id>/S/dateTime
   year: [2000..2099]
    month: [1..12]
    day: [1..31]
    hour: [0..23]
    minute: [0..59]
    second: [0..59]
    centisecond: [0..9]
    weekday: [monday|tuesday|wednesday|thursday|friday|saturday|sunday]
```
```
TF/RealTimeClock/<id>/S/dateTime/callbackPeriod
   [0..9223372036854775807]
```
```
TF/RealTimeClock/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/RealTimeClock/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/RealTimeClock/<id>/S/offset
   [-128..127]
```
```
TF/RealTimeClock/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### RemoteSwitch
```
TF/RemoteSwitch/<id>/E/switchingDone
   - timestamp: [0..9223372036854775807]
     value:
       [houseCode: [0..31]
       receiverCode:    [0..31]
       switchingValue: [ON|OFF] | address: [0..67108863]
       unit: [0..15]
       switchingValue: [ON|OFF] | systemCode: ['A'..'P']
       deviceCode: [1..16]
       switchingValue: [ON|OFF] | address:    [0..67108863]
       unit: [0..15]
       dimValue: [0..15]]
```
```
TF/RemoteSwitch/<id>/I/dimSocketB
   address: [0..67108863]
    unit: [0..15]
    dimValue: [0..15]
```
```
TF/RemoteSwitch/<id>/I/repeats
   [0..32767]
```
```
TF/RemoteSwitch/<id>/I/switchSocketA
   houseCode: [0..31]
    receiverCode: [0..31]
    switchingValue: [switchOn|switchOff]
```
```
TF/RemoteSwitch/<id>/I/switchSocketB
   address: [0..67108863]
    unit: [0..15]
    switchingValue: [switchOn|switchOff]
```
```
TF/RemoteSwitch/<id>/I/switchSocketC
   systemCode: ['A'..'P']
    deviceCode: [1..16]
    switchingValue: [switchOn|switchOff]
```
```
TF/RemoteSwitch/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/RemoteSwitch/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/RemoteSwitch/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/RemoteSwitch/<id>/S/repeats
   [0..32767]
```

### RotaryEncoder
```
TF/RotaryEncoder/<id>/E/count
   - timestamp: [0..9223372036854775807]
     value: [0..9000]
```
```
TF/RotaryEncoder/<id>/E/count/reached
   - timestamp: [0..9223372036854775807]
     value: [-150..150]
```
```
TF/RotaryEncoder/<id>/E/count/reset
   - timestamp: [0..9223372036854775807]
     value: [-92233720368547758080..9223372036854775807]
```
```
TF/RotaryEncoder/<id>/E/pressed
   - timestamp: [0..9223372036854775807]
     value: true
```
```
TF/RotaryEncoder/<id>/E/released
   - timestamp: [0..9223372036854775807]
     value: true
```
```
TF/RotaryEncoder/<id>/I/count/callbackPeriod
   [0..9223372036854775807]
```
```
TF/RotaryEncoder/<id>/I/count/threshold
   option: [x|o|i|<|>]
    min: [-150..150]
    max: [-150..150]
```
```
TF/RotaryEncoder/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/RotaryEncoder/<id>/S/count/callbackPeriod
   [0..9223372036854775807]
```
```
TF/RotaryEncoder/<id>/S/count/threshold
   option: [x|o|i|<|>]
    min: [-150..150]
    max: [-150..150]
```
```
TF/RotaryEncoder/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/RotaryEncoder/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/RotaryEncoder/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/RotaryEncoder/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### RotaryPoti
```
TF/RotaryPoti/<id>/E/analogValue
   - timestamp: [0..9223372036854775807]
     value: [0..4095]
   
```
```
TF/RotaryPoti/<id>/E/analogValue/reached
   - timestamp: [0..9223372036854775807]
     value: [0..4095]
   
```
```
TF/RotaryPoti/<id>/E/position
   - timestamp: [0..9223372036854775807]
     value: [0..9000]
   
```
```
TF/RotaryPoti/<id>/E/position/reached
   - timestamp: [0..9223372036854775807]
     value: [-150..150]
   
```
```
TF/RotaryPoti/<id>/I/analogValue/callbackPeriod
   [0..9223372036854775807]
```
```
TF/RotaryPoti/<id>/I/analogValue/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/RotaryPoti/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/RotaryPoti/<id>/I/position/callbackPeriod
   [0..9223372036854775807]
```
```
TF/RotaryPoti/<id>/I/position/threshold
   option: [x|o|i|<|>]
    min: [-150..150]
    max: [-150..150]
```
```
TF/RotaryPoti/<id>/S/analogValue/callbackPeriod
   [0..9223372036854775807]
```
```
TF/RotaryPoti/<id>/S/analogValue/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/RotaryPoti/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/RotaryPoti/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/RotaryPoti/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/RotaryPoti/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/RotaryPoti/<id>/S/position/callbackPeriod
   [0..9223372036854775807]
```
```
TF/RotaryPoti/<id>/S/position/threshold
   option: [x|o|i|<|>]
    min: [-150..150]
    max: [-150..150]
```

### SegmentDisplay4x7
```
TF/SegmentDisplay4x7/<id>/E/counterFinished
   - timestamp: [0..9223372036854775807]
     value: true
```
```
TF/SegmentDisplay4x7/<id>/E/counterStarted
   - timestamp: [0..9223372036854775807]
     value: true
```
```
TF/SegmentDisplay4x7/<id>/I/counter
   from: [-999..9999]
    to: [-999..9999]
    increment: [-999..9999]
    lenght: [0..9223372036854775807]
```
```
TF/SegmentDisplay4x7/<id>/I/segments
   bits:[[0..128][0..128][0..128][0..128]]
    brightness: [0..7]
    colon: [true|false]
```
```
TF/SegmentDisplay4x7/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/SegmentDisplay4x7/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/SegmentDisplay4x7/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/SegmentDisplay4x7/<id>/S/segments
   bits:[[0..128][0..128][0..128][0..128]]
    brightness: [0..7]
    colon: [true|false]
```

### Servo
```
TF/Servo/<id>/E/positionReached
   - timestamp: [0..9223372036854775807]
     value:
       id: [0..6]
       position: [-32767..32767]
```
```
TF/Servo/<id>/E/undervoltage
   - timestamp: [0..9223372036854775807]
     value: [0..2147483647]
```
```
TF/Servo/<id>/E/velocityReached
   - timestamp: [0..9223372036854775807]
     value:
       id: [0..6]
       value: [0..32767]
```
```
TF/Servo/<id>/I/minimumVoltage
   [5000..2147483647]
```
```
TF/Servo/<id>/I/outputVoltage
   [2000..9000]
```
```
TF/Servo/<id>/I/servos
   --- 
     {- 
     id: [0..6]
     [enabled: [true|false]|]
     [position: [-32767..32767]|]
     [acceleration: [0..65536]|]
     [velocity: [0..65535]|]
     [degree:
       min: [-32767..32767]
       max: [-32767..32767]|]
     [period: [1..65535]|]
     [pulseWidth:
       min: [1..65535]
       max: [1..65535]|]}_7
```
```
TF/Servo/<id>/I/statusLED
   [true|false]
```
```
TF/Servo/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/Servo/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/Servo/<id>/S/minimumVoltage
   [6..2147483647]
```
```
TF/Servo/<id>/S/outputVoltage
   [1..20000]
```
```
TF/Servo/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/Servo/<id>/S/servos
   --- 
     {- 
     id: [0..6]
     enabled: [true|false|null]
     position: [-32767..32767|null]
     acceleration: [0..65536|null]
     velocity: [0..65535|null]
     degree: [[
       min: [-32767..32767]
       max: [-32767..32767]
   ]|null]
     period: [1..65535|null]
     pulseWidth: [[
       min: [1..65535]
       max: [1..65535]]|null]}_7
```
```
TF/Servo/<id>/S/statusLED
   [true|false]
```

### SolidState
```
TF/SolidState/<id>/E/monoflopDone
   - timestamp: [0..9223372036854775807]
     value: [true|false]
```
```
TF/SolidState/<id>/I/monoflop
   state: [true|false]
    period: [0..9223372036854775807]
```
```
TF/SolidState/<id>/I/state
   [true|false]
```
```
TF/SolidState/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/SolidState/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/SolidState/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/SolidState/<id>/S/state
   [true|false]
```

### SoundIntensity
```
TF/SoundIntensity/<id>/E/soundIntensity
   - timestamp: [0..9223372036854775807]
     value: [0..10000]
   
```
```
TF/SoundIntensity/<id>/E/soundIntensity/reached
   - timestamp: [0..9223372036854775807]
     value: [0..10000]
   
```
```
TF/SoundIntensity/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/SoundIntensity/<id>/I/soundIntensity/callbackPeriod
   [0..9223372036854775807]
```
```
TF/SoundIntensity/<id>/I/soundIntensity/threshold
   option: [x|o|i|<|>]
    min: [0..10000]
    max: [0..10000]
```
```
TF/SoundIntensity/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/SoundIntensity/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/SoundIntensity/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/SoundIntensity/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/SoundIntensity/<id>/S/soundIntensity/callbackPeriod
   [0..9223372036854775807]
```
```
TF/SoundIntensity/<id>/S/soundIntensity/threshold
   option: [x|o|i|<|>]
    min: [0..10000]
    max: [0..10000]
```

### Temperature
```
TF/Temperature/<id>/E/temperature
   - timestamp: [0..9223372036854775807]
     value: [-2500..8500]
```
```
TF/Temperature/<id>/E/temperature/reached
   - timestamp: [0..9223372036854775807]
     value: [-2500..8500]
```
```
TF/Temperature/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/Temperature/<id>/I/mode
   mode:[Fast|Slow]
```
```
TF/Temperature/<id>/I/temperature/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Temperature/<id>/I/temperature/threshold
   option: [x|o|i|<|>]
    min: [-2500..8500]
    max: [-2500..8500]
```
```
TF/Temperature/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/Temperature/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/Temperature/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/Temperature/<id>/S/mode
   mode:[Slow|Fast]
```
```
TF/Temperature/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/Temperature/<id>/S/temperature/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Temperature/<id>/S/temperature/threshold
   option: [x|o|i|<|>]
    min: [-2500..8500]
    max: [-2500..8500]
```

### TemperatureIR
```
TF/TemperatureIR/<id>/E/ambientTemperature
   - timestamp: [0..9223372036854775807]
     value: [-400..1250]
```
```
TF/TemperatureIR/<id>/E/ambientTemperature/reached
   - timestamp: [0..9223372036854775807]
     value: [-400..1250]
```
```
TF/TemperatureIR/<id>/E/objectTemperature
   - timestamp: [0..9223372036854775807]
     value: [-700..3800]
```
```
TF/TemperatureIR/<id>/E/objectTemperature/reached
   - timestamp: [0..9223372036854775807]
     value: [-700..3800]
```
```
TF/TemperatureIR/<id>/I/ambientTemperature/callbackPeriod
   [0..9223372036854775807]
```
```
TF/TemperatureIR/<id>/I/ambientTemperature/threshold
   option: [x|o|i|<|>]
    min: [-400..1250]
    max: [-400..1250]
```
```
TF/TemperatureIR/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/TemperatureIR/<id>/I/objectTemperature/callbackPeriod
   [0..9223372036854775807]
```
```
TF/TemperatureIR/<id>/I/objectTemperature/threshold
   option: [x|o|i|<|>]
    min: [-700..3800]
    max: [-700..3800]
```
```
TF/TemperatureIR/<id>/S/ambientTemperature/callbackPeriod
   [0..9223372036854775807]
```
```
TF/TemperatureIR/<id>/S/ambientTemperature/threshold
   option: [x|o|i|<|>]
    min: [-400..1250]
    max: [-400..1250]
```
```
TF/TemperatureIR/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/TemperatureIR/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/TemperatureIR/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/TemperatureIR/<id>/S/objectTemperature/callbackPeriod
   [0..9223372036854775807]
```
```
TF/TemperatureIR/<id>/S/objectTemperature/threshold
   option: [x|o|i|<|>]
    min: [-700..3800]
    max: [-700..3800]
```
```
TF/TemperatureIR/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### ThermoCouple
```
TF/ThermoCouple/<id>/E/temperature
   - timestamp: [0..9223372036854775807]
     value: [-21000..180000]
```
```
TF/ThermoCouple/<id>/E/temperature/reached
   - timestamp: [0..9223372036854775807]
     value: [-21000..180000]
```
```
TF/ThermoCouple/<id>/I/configuration
   averaging:[sample_1|sample_2|sample_4|smaple_8|sample_16]
    type: [B|E|J|K|N|R|S|T|G8|G32]
    filter: [Hz_50|Hz_60]
```
```
TF/ThermoCouple/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/ThermoCouple/<id>/I/temperature/callbackPeriod
   [0..9223372036854775807]
```
```
TF/ThermoCouple/<id>/I/temperature/threshold
   option: [x|o|i|<|>]
    min: [-21000..180000]
    max: [-21000..180000]
```
```
TF/ThermoCouple/<id>/S/configuration
   averaging:[sample_1|sample_2|sample_4|smaple_8|sample_16]
    type: [B|E|J|K|N|R|S|T|G8|G32]
    filter: [Hz_50|Hz_60]
```
```
TF/ThermoCouple/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/ThermoCouple/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/ThermoCouple/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/ThermoCouple/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/ThermoCouple/<id>/S/temperature/callbackPeriod
   [0..9223372036854775807]
```
```
TF/ThermoCouple/<id>/S/temperature/threshold
   option: [x|o|i|<|>]
    min: [-21000..180000]
    max: [-21000..180000]
```

### Tilt
```
TF/Tilt/<id>/E/tiltState
   - timestamp: [0..9223372036854775807]
     value:  [0..2]
```
```
TF/Tilt/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/Tilt/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/Tilt/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### UVLight
```
TF/UVLight/<id>/E/uvLight
   - timestamp: [0..9223372036854775807]
     value: [0..328000]
```
```
TF/UVLight/<id>/E/uvLight/reached
   - timestamp: [0..9223372036854775807]
     value: [0..328000]
```
```
TF/UVLight/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/UVLight/<id>/I/uvLight/callbackPeriod
   [0..9223372036854775807]
```
```
TF/UVLight/<id>/I/uvLight/threshold
   option: [x|o|i|<|>]
    min: [0..328000]
    max: [0..328000]
```
```
TF/UVLight/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/UVLight/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/UVLight/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/UVLight/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/UVLight/<id>/S/uvLight/callbackPeriod
   [0..9223372036854775807]
```
```
TF/UVLight/<id>/S/uvLight/threshold
   option: [x|o|i|<|>]
    min: [0..328000]
    max: [0..328000]
```

### VoltageCurrent
```
TF/VoltageCurrent/<id>/E/current
   - timestamp: [0..9223372036854775807]
     value: [0..36000]
```
```
TF/VoltageCurrent/<id>/E/current/reached
   - timestamp: [0..9223372036854775807]
     value: [0..36000]
```
```
TF/VoltageCurrent/<id>/E/power
   - timestamp: [0..9223372036854775807]
     value: [0..720000]
```
```
TF/VoltageCurrent/<id>/E/power/reached
   - timestamp: [0..9223372036854775807]
     value: [0..720000]
```
```
TF/VoltageCurrent/<id>/E/voltage
   - timestamp: [0..9223372036854775807]
     value: [-50001..50001]
```
```
TF/VoltageCurrent/<id>/E/voltage/reached
   - timestamp: [0..9223372036854775807]
     value: [-50001..50001]
```
```
TF/VoltageCurrent/<id>/I/calibration
   gainMultiplier: [1..2147483647]
    gainDivisor: [1..2147483647]
```
```
TF/VoltageCurrent/<id>/I/configuration
   averaging: [AVERAGING_1|AVERAGING_4|AVERAGING_16|AVERAGING_64|AVERAGING_128|AVERAGING_256|AVERAGING_512|AVERAGING_1024]
    voltageConversionTime: [CONVERSION_140us|CONVERSION_204us|CONVERSION_332us|CONVERSION_588us|CONVERSION_1100us|CONVERSION_2116us|CONVERSION_4156us|CONVERSION_8244us]
    currentConversionTime: [CONVERSION_140us|CONVERSION_204us|CONVERSION_332us|CONVERSION_588us|CONVERSION_1100us|CONVERSION_2116us|CONVERSION_4156us|CONVERSION_8244us]
   
```
```
TF/VoltageCurrent/<id>/I/current/callbackPeriod
   [0..9223372036854775807]
```
```
TF/VoltageCurrent/<id>/I/current/threshold
   option: [x|o|i|<|>]
    min: [0..36000]
    max: [0..36000]
```
```
TF/VoltageCurrent/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/VoltageCurrent/<id>/I/power/callbackPeriod
   [0..9223372036854775807]
```
```
TF/VoltageCurrent/<id>/I/power/threshold
   option: [x|o|i|<|>]
    min: [0..720000]
    max: [0..720000]
```
```
TF/VoltageCurrent/<id>/I/voltage/callbackPeriod
   [0..9223372036854775807]
```
```
TF/VoltageCurrent/<id>/I/voltage/threshold
   option: [x|o|i|<|>]
    min: [-50001..50001]
    max: [-50001..50001]
```
```
TF/VoltageCurrent/<id>/S/calibration
   gainMultiplier: [1..2147483647]
    gainDivisor: [1..2147483647]
```
```
TF/VoltageCurrent/<id>/S/configuration
   averaging: [AVERAGING_1|AVERAGING_4|AVERAGING_16|AVERAGING_64|AVERAGING_128|AVERAGING_256|AVERAGING_512|AVERAGING_1024]
    voltageConversionTime: [CONVERSION_140us|CONVERSION_204us|CONVERSION_332us|CONVERSION_588us|CONVERSION_1100us|CONVERSION_2116us|CONVERSION_4156us|CONVERSION_8244us]
    currentConversionTime: [CONVERSION_140us|CONVERSION_204us|CONVERSION_332us|CONVERSION_588us|CONVERSION_1100us|CONVERSION_2116us|CONVERSION_4156us|CONVERSION_8244us]
   
```
```
TF/VoltageCurrent/<id>/S/current/callbackPeriod
   [0..9223372036854775807]
```
```
TF/VoltageCurrent/<id>/S/current/threshold
   option: [x|o|i|<|>]
    min: [0..36000]
    max: [0..36000]
```
```
TF/VoltageCurrent/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/VoltageCurrent/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/VoltageCurrent/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/VoltageCurrent/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/VoltageCurrent/<id>/S/power/callbackPeriod
   [0..9223372036854775807]
```
```
TF/VoltageCurrent/<id>/S/power/threshold
   option: [x|o|i|<|>]
    min: [0..720000]
    max: [0..720000]
```
```
TF/VoltageCurrent/<id>/S/voltage/callbackPeriod
   [0..9223372036854775807]
```
```
TF/VoltageCurrent/<id>/S/voltage/threshold
   option: [x|o|i|<|>]
    min: [0..50001]
    max: [-50001..50001]
```