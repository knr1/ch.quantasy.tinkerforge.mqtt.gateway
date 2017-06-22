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
Message: hostName: localhost
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
 [TiMqWay.jar]: <https://github.com/knr1/ch.quantasy.tinkerforge.mqtt.gateway/blob/master/dist/TiMqWay.jar>
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


## Common Hardware
Our setup runs at home. We have a local (wlan-)network and a connected computer called (UID: pc) (could be a PC, Notebook, RaspberryPi,...)


## Tinkerforge Hardware
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

TiMqWay itself is presented as a micro-service. It is called 'Manager' and is provided per computer. Hence, the instance of a Manager is represented as the computer-name.
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
     U
       pc
          S
            connection --- online
```

### Connecting Master-Brick-1

As the description explains, we now have to tell TiMqWay where to look for the Master Bricks (Stacks). Hence, we want to attach Master-Brick-1 (say, its 
network-name is master-brick-1). Therefore the following message has to be sent to the following topic:
```
Topic: TF/Manager/U/pc/I/stack/address/add
Message: hostName: master-brick-1
```

Looking into the mqtt-broker, the following can be seen:

```
TF
   Manager
     D
       #omitted for better readability
     U
       pc
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
     U  blue
         S
           connection --- online
           position --- "c"
           firmware --- 2-0-1
           hardware --- 1-1-0
   LCD20x4
     D
       #omitted for better readability
     U
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
Topic: TF/LCD20x4/U/lcd/I/backlight
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
     U
       blue
         S
           connection --- online
           position --- "c"
           firmware --- 2-0-1
           hardware --- 1-1-0
   LCD20x4
     D
       #omitted for better readability 
     U
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
Topic: TF/Manager/U/pc/I/stack/address/add
Message: hostName: localhost
```
The TiMqWay-manager now knows two stacks and manages one temperature device more
```
TF
   Manager
     D
       #omitted for better readability
     U
       pc
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
         temperature --- - timestamp: [0..9223372036854775807]\n  value: [-2500..8500]\n
           reached --- - timestamp: [0..9223372036854775807]\n  value: [-2500..8500]\n
       I
         debounce
           period --- [0..9223372036854775807]
         temperature
           callbackPeriod --- [0..9223372036854775807]
           threshold --- option: [x|o|i|<|>]\n min: [-2500..8500]\n max: [-2500..8500]
         mode -- mode:[Slow|Fast]
     U
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
     U
       lcd
         S
           connection --- online
           position --- "d"
           firmware --- 2-0-2
           hardware --- 1-2-0
```

If we want to have a temperature reading every second for `red`, we provide the following message to the following topic:
```
Topic: TF/Temperature/U/red/I/temperature/callbackPeriod
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
     U
       blue
         S
           connection --- online
           position --- "c"
           firmware --- 2-0-1
           hardware --- 1-1-0
     U
       red
         S
           connection --- online
           position --- "a"
           firmware --- 2-0-1
           hardware --- 1-1-0
           temperature
             callbackPeriod --- 1000
         E
           temperature --- - timestamp: 1465398254115  value: 2668 
   LCD20x4
     D
       #omitted for better readability
     U
       lcd
         S
           connection --- online
           position --- "d"
           firmware --- 2-0-2
           hardware --- 1-2-0
```
All that is left is to write a little agent, subscribing to the temperature of red and blue. Then process the values and write them to lcd... as a publish to
`TF/LCD20x4/U/lcd/I/writeLines`...

```
Topic: TF/LCD20x4/U/lcd/I/writeLines
Message: - line: 0
           position: 0
           text: "RED: 22°C"
         - line: 1
           position: 0
           text: "BLUE: 18°C"
```
## API
### Manager
This logical service allows a user to add or remove a true tinkerforge stack. As soon as a stack is connected, the service takes care of the 
connected Bricks and Bricklets.
```
TF/Manager/U/<id>/E/device/connected
   - timestamp: [0..9223372036854775807]
     value:
       hostName: <String>
       port: [0..4223..65536]
```
```
TF/Manager/U/<id>/E/device/disconnected
   - timestamp: [0..9223372036854775807]
     value:
       hostName: <String>
       port: [0..4223..65536]
```
```
TF/Manager/U/<id>/E/stack/address/added
   - timestamp: [0..9223372036854775807]
     value:
       hostName: <String>
       port: [0..4223..65536]
```
```
TF/Manager/U/<id>/E/stack/address/removed
   - timestamp: [0..9223372036854775807]
     value:
       hostName: <String>
       port: [0..4223..65536]
```
```
TF/Manager/U/<id>/I/stack/address/add
   hostName: <String>
    port: [0..4223..65536]
```
```
TF/Manager/U/<id>/I/stack/address/remove
   hostName: <String>
    port: [0..4223..65536]
```
```
TF/Manager/U/<id>/S/stack/address/<address>/connected
   [true|false]
```

### IMU
```
TF/IMU/U/<id>/E/acceleration
   - timestamp: [0..9223372036854775807]
     value:
        x: [-32768..32767]
        y: [-32768..32767]
        z: [-32768..32767]
```
```
TF/IMU/U/<id>/E/allData
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
TF/IMU/U/<id>/E/angularVelocity
   - timestamp: [0..9223372036854775807]
     value:
        x: [-32768..32767]
        y: [-32768..32767]
        z: [-32768..32767]
```
```
TF/IMU/U/<id>/E/magneticField
   - timestamp: [0..9223372036854775807]
     value:
        x: [-32768..32767]
        y: [-32768..32767]
        z: [-32768..32767]
```
```
TF/IMU/U/<id>/E/orientation
   - timestamp: [0..9223372036854775807]
     value:
        heading: [-32768..32767]
        roll: [-32768..32767]
        pitch: [-32768..32767]
```
```
TF/IMU/U/<id>/E/quaternion
   - timestamp: [0..9223372036854775807]
     value:
        w: [-32768..32767]
        x: [-32768..32767]
        y: [-32768..32767]
        z: [-32768..32767]
```
```
TF/IMU/U/<id>/I/LEDs/callbackPeriod
   true|false]
```
```
TF/IMU/U/<id>/I/acceleration/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMU/U/<id>/I/allData/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMU/U/<id>/I/angularVelocity/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMU/U/<id>/I/magneticField/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMU/U/<id>/I/orientation/calculation
   true|false]
```
```
TF/IMU/U/<id>/I/orientation/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMU/U/<id>/I/quaternion/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMU/U/<id>/I/statusLED/callbackPeriod
   true|false]
```
```
TF/IMU/U/<id>/S/LEDs/callbackPeriod
   [true|false]
```
```
TF/IMU/U/<id>/S/acceleration/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMU/U/<id>/S/allData/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMU/U/<id>/S/angularVelocity/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMU/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/IMU/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/IMU/U/<id>/S/magneticField/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMU/U/<id>/S/orientation/calculation
   [true|false]
```
```
TF/IMU/U/<id>/S/orientation/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMU/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/IMU/U/<id>/S/quaternion/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMU/U/<id>/S/statusLED/callbackPeriod
   [true|false]
```

### IMUV2
```
TF/IMUV2/U/<id>/E/acceleration
   - timestamp: [0..9223372036854775807]
     value:
       x: [-32768..32767]
       y: [-32768..32767]
       z: [-32768..32767]
```
```
TF/IMUV2/U/<id>/E/allData
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
TF/IMUV2/U/<id>/E/angularVelocity
   - timestamp: [0..9223372036854775807]
     value:
       x: [-32768..32767]
       y: [-32768..32767]
       z: [-32768..32767]
```
```
TF/IMUV2/U/<id>/E/gravityVector
   - timestamp: [0..9223372036854775807]
     value:
       x: [-32768..32767]
       y: [-32768..32767]
       z: [-32768..32767]
```
```
TF/IMUV2/U/<id>/E/linearAcceleration
   - timestamp: [0..9223372036854775807]
     value:
       x: [-32768..32767]
       y: [-32768..32767]
       z: [-32768..32767]
```
```
TF/IMUV2/U/<id>/E/magneticField
   - timestamp: [0..9223372036854775807]
     value:
       x: [-32768..32767]
       y: [-32768..32767]
       z: [-32768..32767]
```
```
TF/IMUV2/U/<id>/E/orientation
   - timestamp: [0..9223372036854775807]
     value:
       heading: [-32768..32767]
       roll: [-32768..32767]
       pitch: [-32768..32767]
```
```
TF/IMUV2/U/<id>/E/quaternion
   - timestamp: [0..9223372036854775807]
     value:
       w: [-32768..32767]
       x: [-32768..32767]
       y: [-32768..32767]
       z: [-32768..32767]
```
```
TF/IMUV2/U/<id>/E/temperature
   - timestamp: [0..9223372036854775807]
     value: [-128..127]
```
```
TF/IMUV2/U/<id>/I/LEDs/callbackPeriod
   true|false]
```
```
TF/IMUV2/U/<id>/I/acceleration/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/U/<id>/I/allData/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/U/<id>/I/angularVelocity/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/U/<id>/I/gravityVector/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/U/<id>/I/linearAcceleration/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/U/<id>/I/magneticField/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/U/<id>/I/orientation/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/U/<id>/I/quaternion/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/U/<id>/I/sensorFusionMode
   [0..2]
```
```
TF/IMUV2/U/<id>/I/statusLED
   true|false]
```
```
TF/IMUV2/U/<id>/I/temperature/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/U/<id>/S/LEDs/callbackPeriod
   [true|false]
```
```
TF/IMUV2/U/<id>/S/acceleration/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/U/<id>/S/allData/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/U/<id>/S/angularVelocity/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/IMUV2/U/<id>/S/gravityVector/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/IMUV2/U/<id>/S/linearAcceleration/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/U/<id>/S/magneticField/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/U/<id>/S/orientation/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/IMUV2/U/<id>/S/quaternion/callbackPeriod
   [0..9223372036854775807]
```
```
TF/IMUV2/U/<id>/S/sensorFusionMode
   [0..2]
```
```
TF/IMUV2/U/<id>/S/statusLED
   [true|false]
```
```
TF/IMUV2/U/<id>/S/temperature/callbackPeriod
   [0..9223372036854775807]
```

### LCD16x2
```
TF/LCD16x2/U/<id>/I/backlight
   [true|false]
```
```
TF/LCD16x2/U/<id>/I/clearDisplay
   [true|false]
```
```
TF/LCD16x2/U/<id>/I/configParameters
   cursor: [true|false]
    blinking: [true|false]
```
```
TF/LCD16x2/U/<id>/I/customCharacters
   [index: [0..15]
    pixels: [[-32768..32767]]_[1..8]]
```
```
TF/LCD16x2/U/<id>/I/writeLines
   [line: [0..1]
    position: [0..15]
    text: [String]_[1..16]]
```
```
TF/LCD16x2/U/<id>/S/backlight
   [true|false]
```
```
TF/LCD16x2/U/<id>/S/configParameters
   cursor: [true|false]
    blinking: [true|false]
```
```
TF/LCD16x2/U/<id>/S/customCharacters
   [index: [0..15]
    pixels: [[-32768..32767]]_[1..8]]
```
```
TF/LCD16x2/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/LCD16x2/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/LCD16x2/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### LCD20x4
```
TF/LCD20x4/U/<id>/I/backlight
   [true|false]
```
```
TF/LCD20x4/U/<id>/I/clearDisplay
   [true|false]
```
```
TF/LCD20x4/U/<id>/I/configParameters
   cursor: [true|false]
    blinking: [true|false]
```
```
TF/LCD20x4/U/<id>/I/customCharacters
   [index: [0..15]
    pixels: [[-32768..32767]]_[1..8]]
```
```
TF/LCD20x4/U/<id>/I/defaultText/counter
   [-1..2147483647]
```
```
TF/LCD20x4/U/<id>/I/defaultText/texts
   [line: [0..3]
    text: [String]_[1..20]]
```
```
TF/LCD20x4/U/<id>/I/writeLines
   [line: [0..3]
    position: [0..18]
    text: [String]_[1..20]]
```
```
TF/LCD20x4/U/<id>/S/backlight
   [true|false]
```
```
TF/LCD20x4/U/<id>/S/configParameters
   cursor: [true|false]
    blinking: [true|false]
```
```
TF/LCD20x4/U/<id>/S/customCharacters
   [index: [0..15]
    pixels: [[-32768..32767]]_[1..8]]
```
```
TF/LCD20x4/U/<id>/S/defaultText/counter
   [-1..2147483647]
```
```
TF/LCD20x4/U/<id>/S/defaultText/texts
   [line: [0..3]
    text: [String]_[1..20]]
```
```
TF/LCD20x4/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/LCD20x4/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/LCD20x4/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### Accelerometer
```
TF/Accelerometer/U/<id>/E/acceleration
   - timestamp: [0..9223372036854775807]
     value:
       x: [-32768..32767]
       y: [-32768..32767]
       z: [-32768..32767]
   
```
```
TF/Accelerometer/U/<id>/E/acceleration/reached
   - timestamp: [0..9223372036854775807]
    value:
       x: [-32768..32767]
       y: [-32768..32767]
       z: [-32768..32767]
```
```
TF/Accelerometer/U/<id>/I/acceleration/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Accelerometer/U/<id>/I/acceleration/threshold
   option: [x|o|i|<|>]
    minX: [-32768..32767]
    minY: [-32768..32767]
    minZ: [-32768..32767]
    maxX: [-32768..32767]
    maxY: [-32768..32767]
    maxZ: [-32768..32767]
```
```
TF/Accelerometer/U/<id>/I/configuration
   dataRate: [OFF|Hz3|Hz6|Hz12|Hz25|Hz50|Hz100|Hz400|Hz800|Hz1600]
    fullScale: [G2|G4|G6|G8|G16
    filterBandwidth: [Hz800|Hz400|Hz200|Hz50]
```
```
TF/Accelerometer/U/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/Accelerometer/U/<id>/S/acceleration/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Accelerometer/U/<id>/S/acceleration/threshold
   option: [x|o|i|<|>]
    minX: [-32768..32767]
    minY: [-32768..32767]
    minZ: [-32768..32767]
    maxX: [-32768..32767]
    maxY: [-32768..32767]
    maxZ: [-32768..32767]
```
```
TF/Accelerometer/U/<id>/S/configuration
   dataRate: [OFF|Hz3|Hz6|Hz12|Hz25|Hz50|Hz100|Hz400|Hz800|Hz1600]
    fullScale: [G2|G4|G6|G8|G16
    filterBandwidth: [Hz800|Hz400|Hz200|Hz50]
```
```
TF/Accelerometer/U/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/Accelerometer/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/Accelerometer/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/Accelerometer/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### AmbientLight
```
TF/AmbientLight/U/<id>/E/analogValue
   - timestamp: [0..9223372036854775807]
     value: [0..4095]
   
```
```
TF/AmbientLight/U/<id>/E/analogValue/reached
   - timestamp: [0..9223372036854775807]
     value: [0..4095]
   
```
```
TF/AmbientLight/U/<id>/E/illuminance
   - timestamp: [0..9223372036854775807]
     value: [0..9000]
   
```
```
TF/AmbientLight/U/<id>/E/illuminance/reached
   - timestamp: [0..9223372036854775807]
     value: [0..9000]
   
```
```
TF/AmbientLight/U/<id>/I/analogValue/callbackPeriod
   [0..9223372036854775807]
```
```
TF/AmbientLight/U/<id>/I/analogValue/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/AmbientLight/U/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/AmbientLight/U/<id>/I/illuminance/callbackPeriod
   [0..9223372036854775807]
```
```
TF/AmbientLight/U/<id>/I/illuminance/threshold
   option: [x|o|i|<|>]
    min: [0..9000]
    max: [0..9000]
```
```
TF/AmbientLight/U/<id>/S/analogValue/callbackPeriod
   [0..9223372036854775807]
```
```
TF/AmbientLight/U/<id>/S/analogValue/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/AmbientLight/U/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/AmbientLight/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/AmbientLight/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/AmbientLight/U/<id>/S/illuminance/callbackPeriod
   [0..9223372036854775807]
```
```
TF/AmbientLight/U/<id>/S/illuminance/threshold
   option: [x|o|i|<|>]
    min: [0..9000]
    max: [0..9000]
```
```
TF/AmbientLight/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### AmbientLightV2
```
TF/AmbientLightV2/U/<id>/E/illuminance
   - timestamp: [0..9223372036854775807]
     value: [0..100000]
   
```
```
TF/AmbientLightV2/U/<id>/E/illuminance/reached
   - timestamp: [0..9223372036854775807]
     value: [0..100000]
   
```
```
TF/AmbientLightV2/U/<id>/I/configuration
   illuminanceRange:[lx_unlimitted|lx_64000|lx_32000|lx_16000|lx_8000|lx_13000|lx_600]
    integrationTime: [ms_50|ms_100|ms_150|ms_200|ms_250|ms_300|ms_350|ms_400]
   
```
```
TF/AmbientLightV2/U/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/AmbientLightV2/U/<id>/I/illuminance/callbackPeriod
   [0..9223372036854775807]
```
```
TF/AmbientLightV2/U/<id>/I/illuminance/threshold
   option: [x|o|i|<|>]
    min: [0..100000]
    max: [0..100000]
```
```
TF/AmbientLightV2/U/<id>/S/configuration
   illuminanceRange:[lx_unlimitted|lx_64000|lx_32000|lx_16000|lx_8000|lx_13000|lx_600]
    integrationTime: [ms_50|ms_100|ms_150|ms_200|ms_250|ms_300|ms_350|ms_400]
   
```
```
TF/AmbientLightV2/U/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/AmbientLightV2/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/AmbientLightV2/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/AmbientLightV2/U/<id>/S/illuminance/callbackPeriod
   [0..9223372036854775807]
```
```
TF/AmbientLightV2/U/<id>/S/illuminance/threshold
   option: [x|o|i|<|>]
    min: [0..100000]
    max: [0..100000]
```
```
TF/AmbientLightV2/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### AnalogInV2
```
TF/AnalogInV2/U/<id>/E/analogValue
   - timestamp: [0..9223372036854775807]
     value: [0..4095]
   
```
```
TF/AnalogInV2/U/<id>/E/analogValue/reached
   - timestamp: [0..9223372036854775807]
     value: [0..4095]
   
```
```
TF/AnalogInV2/U/<id>/E/voltage
   - timestamp: [0..9223372036854775807]
     value: [-2147483648..2147483647]
   
```
```
TF/AnalogInV2/U/<id>/E/voltage/reached
   - timestamp: [0..42000]
     value: [0..42000]
   
```
```
TF/AnalogInV2/U/<id>/I/analogValue/callbackPeriod
   [0..9223372036854775807]
```
```
TF/AnalogInV2/U/<id>/I/analogValue/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/AnalogInV2/U/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/AnalogInV2/U/<id>/I/movingAverage
   [1..50]
```
```
TF/AnalogInV2/U/<id>/I/voltage/callbackPeriod
   [0..9223372036854775807]
```
```
TF/AnalogInV2/U/<id>/I/voltage/threshold
   option: [x|o|i|<|>]
    min: [0..42000]
    max: [0..42000]
```
```
TF/AnalogInV2/U/<id>/S/analogValue/callbackPeriod
   [0..9223372036854775807]
```
```
TF/AnalogInV2/U/<id>/S/analogValue/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/AnalogInV2/U/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/AnalogInV2/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/AnalogInV2/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/AnalogInV2/U/<id>/S/movingAverage
   [1..50]
```
```
TF/AnalogInV2/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/AnalogInV2/U/<id>/S/voltage/callbackPeriod
   [0..9223372036854775807]
```
```
TF/AnalogInV2/U/<id>/S/voltage/threshold
   option: [x|o|i|<|>]
    min: [0..42000]
    max: [0..42000]
```

### AnalogOutV2
```
TF/AnalogOutV2/U/<id>/I/outputVoltage
   [0..12000]
```
```
TF/AnalogOutV2/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/AnalogOutV2/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/AnalogOutV2/U/<id>/S/outputVoltage
   [0..12000]
```
```
TF/AnalogOutV2/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### Barometer
```
TF/Barometer/U/<id>/E/airPressure
   - timestamp: [0..9223372036854775807]
     value: [10000..1200000]
   
```
```
TF/Barometer/U/<id>/E/airPressure/reached
   - timestamp: [0..9223372036854775807]
     value: [10000..1200000]
   
```
```
TF/Barometer/U/<id>/E/altitude
   - timestamp: [0..9223372036854775807]
     value: [-2147483648..2147483647]
   
```
```
TF/Barometer/U/<id>/E/altitude/reached
   - timestamp: [0..9223372036854775807]
     value: [-2147483648..2147483647]
   
```
```
TF/Barometer/U/<id>/I/airPressure/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Barometer/U/<id>/I/airPressure/threshold
   option: [x|o|i|<|>]
    min: [10000..1200000]
    max: [10000..1200000]
```
```
TF/Barometer/U/<id>/I/altitude/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Barometer/U/<id>/I/altitude/threshold
   option: [x|o|i|<|>]
    min: [-2147483648..2147483647]
    max: [-2147483648..2147483647]
```
```
TF/Barometer/U/<id>/I/averaging
   averagingPressure: [0..10]
    averagingTemperature: [0..255]
    movingAveragePressure: [0..25]
```
```
TF/Barometer/U/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/Barometer/U/<id>/I/referenceAirPressure
   [-2147483648..2147483647]
```
```
TF/Barometer/U/<id>/S/airPressure/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Barometer/U/<id>/S/airPressure/threshold
   option: [x|o|i|<|>]
    min: [10000..1200000]
    max: [10000..1200000]
```
```
TF/Barometer/U/<id>/S/altitude/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Barometer/U/<id>/S/altitude/threshold
   option: [x|o|i|<|>]
    min: [-2147483648..2147483647]
    max: [-2147483648..2147483647]
```
```
TF/Barometer/U/<id>/S/averaging
   averagingPressure: [0..10]
    averagingTemperature: [0..255]
    movingAveragePressure: [0..25]
```
```
TF/Barometer/U/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/Barometer/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/Barometer/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/Barometer/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/Barometer/U/<id>/S/referenceAirPressure
   [-2147483648..2147483647]
```

### CO2
```
TF/CO2/U/<id>/E/CO2Concentration
   - timestamp: [0..9223372036854775807]
     value: [0..10000]
   
```
```
TF/CO2/U/<id>/E/CO2Concentration/reached
   - timestamp: [0..9223372036854775807]
     value: [0..10000]
   
```
```
TF/CO2/U/<id>/I/CO2Concentration/callbackPeriod
   [0..9223372036854775807]
```
```
TF/CO2/U/<id>/I/CO2Concentration/threshold
   option: [x|o|i|<|>]
    min: [0..10000]
    max: [0..10000]
```
```
TF/CO2/U/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/CO2/U/<id>/S/CO2Concentration/callbackPeriod
   [0..9223372036854775807]
```
```
TF/CO2/U/<id>/S/CO2Concentration/threshold
   option: [x|o|i|<|>]
    min: [0..10000]
    max: [0..10000]
```
```
TF/CO2/U/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/CO2/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/CO2/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/CO2/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### Color
```
TF/Color/U/<id>/E/color
   - timestamp: [0..9223372036854775807]
     value:
       red: [0..65535]
       green: [0..65535]
       blue: [0..65535]
       clear: [0..65535]
```
```
TF/Color/U/<id>/E/color/reached
   - timestamp: [0..9223372036854775807]
     value:
       red: [0..65535]
       green: [0..65535]
       blue: [0..65535]
       clear: [0..65535]
```
```
TF/Color/U/<id>/E/illuminance
   - timestamp: [0..9223372036854775807]
     value: [0..65535]
```
```
TF/Color/U/<id>/E/illuminance/reached
   - timestamp: [0..9223372036854775807]
     value: [0..65535]
   
```
```
TF/Color/U/<id>/I/color/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Color/U/<id>/I/color/threshold
   option: [x|o|i|<|>]
    minR: [0..65535]
    maxR: [0..65535]
    minG: [0..65535]
    maxG: [0..65535]
    minB: [0..65535]
    maxB: [0..65535]
```
```
TF/Color/U/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/Color/U/<id>/I/illuminance/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Color/U/<id>/I/illuminance/threshold
   [0..9223372036854775807]
```
```
TF/Color/U/<id>/S/color/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Color/U/<id>/S/color/threshold
   option: [x|o|i|<|>]
    minR: [0..65535]
    maxR: [0..65535]
    minG: [0..65535]
    maxG: [0..65535]
    minB: [0..65535]
    maxB: [0..65535]
```
```
TF/Color/U/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/Color/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/Color/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/Color/U/<id>/S/illuminance/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Color/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### DC
```
TF/DC/U/<id>/E/emergencyShutdown
   - timestamp: [0..9223372036854775807] 
     value: [0..9223372036854775807]
```
```
TF/DC/U/<id>/E/fullBrake
   - timestamp: [0..9223372036854775807] 
     value: [0..9223372036854775807]
```
```
TF/DC/U/<id>/E/undervoltage
   - timestamp: [0..9223372036854775807]
     value: [0..2147483647]
```
```
TF/DC/U/<id>/E/velocity
   - timestamp: [0..9223372036854775807]
     value: [0..32767]
```
```
TF/DC/U/<id>/E/velocity/reached
   - timestamp: [0..9223372036854775807]
     value: [0..32767]
```
```
TF/DC/U/<id>/I/acceleration
   [0..2147483647]
```
```
TF/DC/U/<id>/I/driverMode
   [0|1]
```
```
TF/DC/U/<id>/I/enabled
   [true|false]
```
```
TF/DC/U/<id>/I/fullBrake
   [true|false]
```
```
TF/DC/U/<id>/I/minimumVoltage
   [6000..2147483647]
```
```
TF/DC/U/<id>/I/pwmFrequency
   [1..20000]
```
```
TF/DC/U/<id>/I/velocity/callbackPeriod
   [0..2147483647]
```
```
TF/DC/U/<id>/I/velocity/velocity
   -32767..32767
```
```
TF/DC/U/<id>/S/acceleration
   [0..2147483647]
```
```
TF/DC/U/<id>/S/driverMode
   [0|1]
```
```
TF/DC/U/<id>/S/enabled
   [true|false]
```
```
TF/DC/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/DC/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/DC/U/<id>/S/minimumVoltage
   [6..2147483647]
```
```
TF/DC/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/DC/U/<id>/S/pwmFrequency
   [1..20000]
```
```
TF/DC/U/<id>/S/velocity/callbackPeriod
   [0..2147483647]
```
```
TF/DC/U/<id>/S/velocity/velocity
   -32767..32767
```

### DistanceIR
```
TF/DistanceIR/U/<id>/E/analogValue
   - timestamp: [0..9223372036854775807]
     value: [0..4095]
```
```
TF/DistanceIR/U/<id>/E/analogValue/reached
   - timestamp: [0..9223372036854775807]
     value: [0..4095]
```
```
TF/DistanceIR/U/<id>/E/distance
   - timestamp: [0..9223372036854775807]
     value: [[40..300]|[100..800]|[200..1500]]
```
```
TF/DistanceIR/U/<id>/E/distance/reached
   - timestamp: [0..9223372036854775807]
     value: [[40..300]|[100..800]|[200..1500]]
```
```
TF/DistanceIR/U/<id>/I/analogValue/callbackPeriod
   [0..9223372036854775807]
```
```
TF/DistanceIR/U/<id>/I/analogValue/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/DistanceIR/U/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/DistanceIR/U/<id>/I/distance/callbackPeriod
   [0..9223372036854775807]
```
```
TF/DistanceIR/U/<id>/I/distance/threshold
   option: [x|o|i|<|>]
    min: [[40..300]|[100..800]|[200..1500]]
    max: [[40..300]|[100..800]|[200..1500]]
```
```
TF/DistanceIR/U/<id>/S/analogValue/callbackPeriod
   [0..9223372036854775807]
```
```
TF/DistanceIR/U/<id>/S/analogValue/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/DistanceIR/U/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/DistanceIR/U/<id>/S/distance/callbackPeriod
   [0..9223372036854775807]
```
```
TF/DistanceIR/U/<id>/S/distance/threshold
   option: [x|o|i|<|>]
    min: [[40..300]|[100..800]|[200..1500]]
    max: [[40..300]|[100..800]|[200..1500]]
```
```
TF/DistanceIR/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/DistanceIR/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/DistanceIR/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### DistanceUS
```
TF/DistanceUS/U/<id>/E/distance
   - timestamp: [0..9223372036854775807]
     value: [[0..4095]
```
```
TF/DistanceUS/U/<id>/E/distance/reached
   - timestamp: [0..9223372036854775807]
     value: [0..4095]
```
```
TF/DistanceUS/U/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/DistanceUS/U/<id>/I/distance/callbackPeriod
   [0..9223372036854775807]
```
```
TF/DistanceUS/U/<id>/I/distance/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/DistanceUS/U/<id>/I/movingAverage
   [0..100]
```
```
TF/DistanceUS/U/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/DistanceUS/U/<id>/S/distance/callbackPeriod
   [0..9223372036854775807]
```
```
TF/DistanceUS/U/<id>/S/distance/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/DistanceUS/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/DistanceUS/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/DistanceUS/U/<id>/S/movingAverage
   [0..100]
```
```
TF/DistanceUS/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### DualButton
```
TF/DualButton/U/<id>/E/stateChanged
   - timestamp: [0..9223372036854775807]
     value:
       led1: [AutoToggleOn|AutoToggleOff|On|Off]
       led2: [AutoToggleOn|AutoToggleOff|On|Off]
      switch1: [0|1]
      switch2: [0|1]
```
```
TF/DualButton/U/<id>/I/LEDState
   leftLED: [AutoToggleOn|AutoToggleOff|On|Off]
    rightLED: [AutoToggleOn|AutoToggleOff|On|Off] 
```
```
TF/DualButton/U/<id>/I/selectedLEDState
   led: [AutoToggleOn|AutoToggleOff|On|Off]
```
```
TF/DualButton/U/<id>/S/LEDState
   led1: [AutoToggleOn|AutoToggleOff|On|Off]
    led2: [AutoToggleOn|AutoToggleOff|On|Off]
```
```
TF/DualButton/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/DualButton/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/DualButton/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### DustDetector
```
TF/DustDetector/U/<id>/E/dustDensity
   - timestamp: [0..9223372036854775807]
     value: [0..500]
```
```
TF/DustDetector/U/<id>/E/dustDensity/reached
   - timestamp: [0..9223372036854775807]
     value: [0..500]
```
```
TF/DustDetector/U/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/DustDetector/U/<id>/I/dustDensity/callbackPeriod
   [0..9223372036854775807]
```
```
TF/DustDetector/U/<id>/I/dustDensity/threshold
   option: [x|o|i|<|>]
    min: [0..500]
    max: [0..500]
```
```
TF/DustDetector/U/<id>/I/movingAverage
   [0..100]
```
```
TF/DustDetector/U/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/DustDetector/U/<id>/S/dustDensity/callbackPeriod
   [0..9223372036854775807]
```
```
TF/DustDetector/U/<id>/S/dustDensity/threshold
   option: [x|o|i|<|>]
    min: [0..500]
    max: [0..500]
```
```
TF/DustDetector/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/DustDetector/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/DustDetector/U/<id>/S/movingAverage
   [0..100]
```
```
TF/DustDetector/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### GPS
```
TF/GPS/U/<id>/E/altitude
   - timestamp: [0..9223372036854775807]
     value:
       altitude: [-2147483648..2147483647]
       geoidalSeparation: [-2147483648..2147483647]
```
```
TF/GPS/U/<id>/E/coordinates
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
TF/GPS/U/<id>/E/dateTime
   - timestamp: [0..9223372036854775807]
     value:
       date: [[d|dd]mmyy]
       time: [hhmmssxxx]
```
```
TF/GPS/U/<id>/E/motion
   - timestamp: [0..9223372036854775807]
     value:
       course: [0..36000]
       speed: [0..9223372036854775807]
```
```
TF/GPS/U/<id>/E/status
   - timestamp: [0..9223372036854775807]
     value:
       fix: [1|2|3]]
       satellitesView: [0..32767]
       satellitesUsed: [0..32767]
```
```
TF/GPS/U/<id>/I/altitude/callbackPeriod
   [0..9223372036854775807]
```
```
TF/GPS/U/<id>/I/coordinates/callbackPeriod
   [0..9223372036854775807]
```
```
TF/GPS/U/<id>/I/dateTime/callbackPeriod
   [0..9223372036854775807]
```
```
TF/GPS/U/<id>/I/motion/callbackPeriod
   [0..9223372036854775807]
```
```
TF/GPS/U/<id>/S/altitude/callbackPeriod
   [0..9223372036854775807]
```
```
TF/GPS/U/<id>/S/coordinates/callbackPeriod
   [0..9223372036854775807]
```
```
TF/GPS/U/<id>/S/dateTime/callbackPeriod
   [0..9223372036854775807]
```
```
TF/GPS/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/GPS/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/GPS/U/<id>/S/motion/callbackPeriod
   [0..9223372036854775807]
```
```
TF/GPS/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### GPSv2
```
TF/GPSv2/U/<id>/E/altitude
   - timestamp: [0..9223372036854775807]
    value:
      altitude: [-2147483648..2147483647]
      geoidalSeparation: [-2147483648..2147483647]
```
```
TF/GPSv2/U/<id>/E/coordinates
   - timestamp: [0..9223372036854775807]
    value:
     latitude: [-9223372036854775808..9223372036854775807]
      ns: ['N'|'S']
      longitude: [-9223372036854775808..9223372036854775807]
    ew: ['E'|'W']
```
```
TF/GPSv2/U/<id>/E/dateTime
   - timestamp: [0..9223372036854775807]
    value:
     date: [[d|dd]mmyy]
     time: [hhmmssxxx]
```
```
TF/GPSv2/U/<id>/E/motion
   - timestamp: [0..9223372036854775807]
    value:
     course: [0..36000]
     speed: [0..9223372036854775807]
```
```
TF/GPSv2/U/<id>/E/status
   - timestamp: [0..9223372036854775807]
    value:
     fix: [true|false]]
     satellitesView: [0..32767]
```
```
TF/GPSv2/U/<id>/I/altitude/callbackPeriod
   [0..9223372036854775807]
```
```
TF/GPSv2/U/<id>/I/coordinates/callbackPeriod
   [0..9223372036854775807]
```
```
TF/GPSv2/U/<id>/I/dateTime/callbackPeriod
   [0..9223372036854775807]
```
```
TF/GPSv2/U/<id>/I/fix/led
   [OFF|ON|HEARTBEAT|FIX|PPS]
```
```
TF/GPSv2/U/<id>/I/motion/callbackPeriod
   [0..9223372036854775807]
```
```
TF/GPSv2/U/<id>/I/status/led
   [OFF|ON|HEARTBEAT|STATUS]
```
```
TF/GPSv2/U/<id>/S/altitude/callbackPeriod
   [0..9223372036854775807]
```
```
TF/GPSv2/U/<id>/S/coordinates/callbackPeriod
   [0..9223372036854775807]
```
```
TF/GPSv2/U/<id>/S/dateTime/callbackPeriod
   [0..9223372036854775807]
```
```
TF/GPSv2/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/GPSv2/U/<id>/S/fix/led
   [OFF|ON|HEARTBEAT|FIX|PPS]
```
```
TF/GPSv2/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/GPSv2/U/<id>/S/motion/callbackPeriod
   [0..9223372036854775807]
```
```
TF/GPSv2/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/GPSv2/U/<id>/S/status/led
   [OFF|ON|HEARTBEAT|STATUS]
```

### HallEffect
```
TF/HallEffect/U/<id>/E/edgeCount
   - timestamp: [0..9223372036854775807]
     value:
       count: [0..9223372036854775807]
       greater35Gauss: [true|false]
```
```
TF/HallEffect/U/<id>/E/edgeCount/reset
   - timestamp: [0..9223372036854775807]
     value:    [0..9223372036854775807]
```
```
TF/HallEffect/U/<id>/I/configuration
   edgeType: [RISING|FALLING|BOTH]
    debounce: [0..100]
   
```
```
TF/HallEffect/U/<id>/I/edgeCount/callbackPeriod
   [0..9223372036854775807]
```
```
TF/HallEffect/U/<id>/I/edgeCount/interrupt
   [0..9223372036854775807]
```
```
TF/HallEffect/U/<id>/I/edgeCount/reset
   [true|false]
```
```
TF/HallEffect/U/<id>/S/configuration
   edgeType: [RISING|FALLING|BOTH]
    debounce: [0..100]
   
```
```
TF/HallEffect/U/<id>/S/edgeCount/callbackPeriod
   [0..9223372036854775807]
```
```
TF/HallEffect/U/<id>/S/edgeCount/interrupt
   [0..9223372036854775807]
```
```
TF/HallEffect/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/HallEffect/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/HallEffect/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### Humidity
```
TF/Humidity/U/<id>/E/analogValue
   - timestamp: [0..9223372036854775807]
     value: [0..4095]
```
```
TF/Humidity/U/<id>/E/analogValue/reached
   - timestamp: [0..9223372036854775807]
     value: [0..4095]
```
```
TF/Humidity/U/<id>/E/humidity
   - timestamp: [0..9223372036854775807]
     value: [0..1000]
```
```
TF/Humidity/U/<id>/E/humidity/reached
   - timestamp: [0..9223372036854775807]
     value: [0..1000]
```
```
TF/Humidity/U/<id>/I/analogValue/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Humidity/U/<id>/I/analogValue/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/Humidity/U/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/Humidity/U/<id>/I/humidity/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Humidity/U/<id>/I/humidity/threshold
   option: [x|o|i|<|>]
    min: [0..1000]
    max: [0..9000]
```
```
TF/Humidity/U/<id>/S/analogValue/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Humidity/U/<id>/S/analogValue/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/Humidity/U/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/Humidity/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/Humidity/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/Humidity/U/<id>/S/humidity/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Humidity/U/<id>/S/humidity/threshold
   option: [x|o|i|<|>]
    min: [0..1000]
    max: [0..1000]
```
```
TF/Humidity/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### Joystick
```
TF/Joystick/U/<id>/E/analogValue
   - timestamp: [0..9223372036854775807]
     value:
       x: [0..4095]
       y: [0..4095]
```
```
TF/Joystick/U/<id>/E/analogValue/reached
   - timestamp: [0..9223372036854775807]
     value:
       x: [0..4095]
       y: [0..4095]
```
```
TF/Joystick/U/<id>/E/calibrate
   [0..9223372036854775807]
```
```
TF/Joystick/U/<id>/E/position
   - timestamp: [0..9223372036854775807]
     value:
       x: [-100..100]
       y: [-100..100]
```
```
TF/Joystick/U/<id>/E/position/reached
   - timestamp: [0..9223372036854775807]
     value:
       x: [-100..100]
       y: [-100..100]
```
```
TF/Joystick/U/<id>/E/pressed
   [0..9223372036854775807]
```
```
TF/Joystick/U/<id>/E/released
   [0..9223372036854775807]
```
```
TF/Joystick/U/<id>/I/analogValue/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Joystick/U/<id>/I/analogValue/threshold
   option: [x|o|i|<|>]
    minX: [0..4095]
    maxX: [0..4095]
    minY: [0..4095]
    maxY: [0..4095]
```
```
TF/Joystick/U/<id>/I/calibrate
   [true|false]
```
```
TF/Joystick/U/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/Joystick/U/<id>/I/position/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Joystick/U/<id>/I/position/threshold
   option: [x|o|i|<|>]
    minX: [-100..100]
    maxX: [-100..100]
    minY: [-100..100]
    maxY: [-100..100]
```
```
TF/Joystick/U/<id>/S/analogValue/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Joystick/U/<id>/S/analogValue/threshold
   option: [x|o|i|<|>]
    minX: [0..4095]
    maxX: [0..4095]
    minY: [0..4095]
    maxY: [0..4095]
```
```
TF/Joystick/U/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/Joystick/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/Joystick/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/Joystick/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/Joystick/U/<id>/S/position/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Joystick/U/<id>/S/position/threshold
   option: [x|o|i|<|>]
    minX: [-100..100]
    maxX: [-100..100]
    minY: [-100..100]
    maxY: [-100..100]
```

### LaserRangeFinder
```
TF/LaserRangeFinder/U/<id>/E/distance
   - timestamp: [0..9223372036854775807]
     value: [0..4000]
```
```
TF/LaserRangeFinder/U/<id>/E/distance/reached
   - timestamp: [0..9223372036854775807]
     value: [0..4000]
```
```
TF/LaserRangeFinder/U/<id>/E/velocity
   - timestamp: [-127..9223372036854775807]
     value: [0..127]
```
```
TF/LaserRangeFinder/U/<id>/E/velocity/reached
   - timestamp: [0..9223372036854775807]
     value: [-127..127]
```
```
TF/LaserRangeFinder/U/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/LaserRangeFinder/U/<id>/I/deviceConfiguration
   acquisition: [1..255]
    quickTermination: [true|false]
    thresholdValue: [0..255]
    measurementFrequency: [0|10..500]
```
```
TF/LaserRangeFinder/U/<id>/I/deviceMode
   mode: [distance|velocity_12_7|velocity_31_75|velocity_63_5|velocity_127]
```
```
TF/LaserRangeFinder/U/<id>/I/distance/callbackPeriod
   [0..9223372036854775807]
```
```
TF/LaserRangeFinder/U/<id>/I/distance/threshold
   option: [x|o|i|<|>]
    min: [0..4000]
    max: [0..4000]
```
```
TF/LaserRangeFinder/U/<id>/I/laser
   [true|false]
```
```
TF/LaserRangeFinder/U/<id>/I/movingAverage
   averagingDistance:[0..30]
    averagingVelocity:[0..30]
```
```
TF/LaserRangeFinder/U/<id>/I/velocity/callbackPeriod
   [0..9223372036854775807]
```
```
TF/LaserRangeFinder/U/<id>/I/velocity/threshold
   option: [x|o|i|<|>]
    min: [-127..127]
    max: [-127..127]
```
```
TF/LaserRangeFinder/U/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/LaserRangeFinder/U/<id>/S/deviceConfiguration
   acquisition: [1..255]
    quickTermination: [true|false]
    thresholdValue: [0..255]
    measurementFrequency: [0|10..500]
```
```
TF/LaserRangeFinder/U/<id>/S/deviceMode
   mode: [distance|velocity_12_7|velocity_31_75|velocity_63_5|velocity_127]
```
```
TF/LaserRangeFinder/U/<id>/S/distance/callbackPeriod
   [0..9223372036854775807]
```
```
TF/LaserRangeFinder/U/<id>/S/distance/threshold
   option: [x|o|i|<|>]
    min: [0..4000]
    max: [0..4000]
```
```
TF/LaserRangeFinder/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/LaserRangeFinder/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/LaserRangeFinder/U/<id>/S/laser
   [true|false]
```
```
TF/LaserRangeFinder/U/<id>/S/movingAverage
   averagingDistance:[0..30]
    averagingVelocity:[0..30]
```
```
TF/LaserRangeFinder/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/LaserRangeFinder/U/<id>/S/sensorHardwareVersion
   [v1|v3]
```
```
TF/LaserRangeFinder/U/<id>/S/velocity/callbackPeriod
   [0..9223372036854775807]
```
```
TF/LaserRangeFinder/U/<id>/S/velocity/threshold
   option: [x|o|i|<|>]
    min: [-127..-127]
    max: [-127..127]
```

### LEDStrip
```
TF/LEDStrip/U/<id>/E/frame/rendered
   - timestamp: [0..9223372036854775807]
     value: [0..2147483647]
   
```
```
TF/LEDStrip/U/<id>/E/laging
   - timestamp: [0..9223372036854775807]
```
```
TF/LEDStrip/U/<id>/I/config
   chipType: [WS2801|WS2811|WS2812]
    frameDurationInMilliseconds: [0..9223372036854775807]
    clockFrequencyOfICsInHz: [10000..2000000]
    numberOfLEDs: [1..320]
    channelMapping: [rgb|rbg|grb|gbr|brg|bgr]
```
```
TF/LEDStrip/U/<id>/I/frame
   channels: {{[0..255],..,[0..255]}_numLEDs
    ...
    {[0..255],..,[0..255]}_numLEDs}_numChannels
```
```
TF/LEDStrip/U/<id>/I/multiFrames
   { channels: {{[0..255],..,[0..255]}_numLEDs
    ...
    {[0..255],..,[0..255]}_numLEDs}_numChannels }_*
```
```
TF/LEDStrip/U/<id>/S/config
   chipType: [WS2801|WS2811|WS2812|WS2812RGBW|LPD8806|APA102]
    frameDurationInMilliseconds: [0..9223372036854775807]
    clockFrequencyOfICsInHz: [10000..2000000]
    numberOfLEDs: [1..320]
    channelMapping: [rgb|rbg|grb|gbr|brg|bgr]
```
```
TF/LEDStrip/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/LEDStrip/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/LEDStrip/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### Line
```
TF/Line/U/<id>/E/reflectivity
   - timestamp: [0..9223372036854775807]
     value: [[0..4095]
```
```
TF/Line/U/<id>/E/reflectivity/reached
   - timestamp: [0..9223372036854775807]
     value: [0..4095]
```
```
TF/Line/U/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/Line/U/<id>/I/reflectivity/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Line/U/<id>/I/reflectivity/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/Line/U/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/Line/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/Line/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/Line/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/Line/U/<id>/S/reflectivity/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Line/U/<id>/S/reflectivity/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```

### LinearPoti
```
TF/LinearPoti/U/<id>/E/analogValue
   - timestamp: [0..9223372036854775807]
     value: [0..4095]
```
```
TF/LinearPoti/U/<id>/E/analogValue/reached
   - timestamp: [0..9223372036854775807]
     value: [0..4095]
```
```
TF/LinearPoti/U/<id>/E/position
   - timestamp: [0..9223372036854775807]
     value: [0..100]
```
```
TF/LinearPoti/U/<id>/E/position/reached
   - timestamp: [0..9223372036854775807]
     value: [0..100]
```
```
TF/LinearPoti/U/<id>/I/analogValue/callbackPeriod
   [0..9223372036854775807]
```
```
TF/LinearPoti/U/<id>/I/analogValue/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/LinearPoti/U/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/LinearPoti/U/<id>/I/position/callbackPeriod
   [0..9223372036854775807]
```
```
TF/LinearPoti/U/<id>/I/position/threshold
   option: [x|o|i|<|>]
    min: [0..100]
    max: [0..100]
```
```
TF/LinearPoti/U/<id>/S/analogValue/callbackPeriod
   [0..9223372036854775807]
```
```
TF/LinearPoti/U/<id>/S/analogValue/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/LinearPoti/U/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/LinearPoti/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/LinearPoti/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/LinearPoti/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/LinearPoti/U/<id>/S/position/callbackPeriod
   [0..9223372036854775807]
```
```
TF/LinearPoti/U/<id>/S/position/threshold
   option: [x|o|i|<|>]
    min: [0..100]
    max: [0..100]
```

### LoadCell
```
TF/LoadCell/U/<id>/E/weight
   - timestamp: [0..9223372036854775807]
     value: [-50001..50001]
```
```
TF/LoadCell/U/<id>/E/weight/reached
   - timestamp: [0..9223372036854775807]
     value: [-50001..50001]
```
```
TF/LoadCell/U/<id>/I/LED
   true|false]
```
```
TF/LoadCell/U/<id>/I/calibrate
   [0..50001]
```
```
TF/LoadCell/U/<id>/I/configuration
   gain:[gain128X|gain64X|gain32X]
    rate: [rate10Hz|rate80Hz]
```
```
TF/LoadCell/U/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/LoadCell/U/<id>/I/movingAverage
   [1..40]
```
```
TF/LoadCell/U/<id>/I/tare
   [true|false]
```
```
TF/LoadCell/U/<id>/I/weight/callbackPeriod
   [0..9223372036854775807]
```
```
TF/LoadCell/U/<id>/I/weight/threshold
   option: [x|o|i|<|>]
    min: [-50001..50001]
    max: [-50001..50001]
```
```
TF/LoadCell/U/<id>/S/LED
   [true|false]
```
```
TF/LoadCell/U/<id>/S/configuration
   gain:[gain128X|gain64X|gain32X]
    rate: [rate10Hz|rate80Hz]
```
```
TF/LoadCell/U/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/LoadCell/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/LoadCell/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/LoadCell/U/<id>/S/movingAverage
   [1..40]
```
```
TF/LoadCell/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/LoadCell/U/<id>/S/weight/callbackPeriod
   [0..9223372036854775807]
```
```
TF/LoadCell/U/<id>/S/weight/threshold
   option: [x|o|i|<|>]
    min: [0..50001]
    max: [-50001..50001]
```

### Master
```
TF/Master/U/<id>/E/USB/voltage
   - timestamp: [0..9223372036854775807]
     value: [-2147483648..2147483647]
```
```
TF/Master/U/<id>/E/USB/voltage/reached
   - timestamp: [0..9223372036854775807]
     value: [-2147483648..2147483647]
```
```
TF/Master/U/<id>/E/reset
   - timestamp: [0..9223372036854775807]
```
```
TF/Master/U/<id>/E/stack/current
   - timestamp: [0..9223372036854775807]
     value: [0..2147483647]
```
```
TF/Master/U/<id>/E/stack/current/reached
   - timestamp: [0..9223372036854775807]
     value: [0..2147483647]
```
```
TF/Master/U/<id>/E/stack/voltage
   - timestamp: [0..9223372036854775807]
     value: [-2147483648..2147483647]
```
```
TF/Master/U/<id>/E/stack/voltage/reached
   - timestamp: [0..9223372036854775807]
     value: [-2147483648..2147483647]
```
```
TF/Master/U/<id>/I/USB/voltage/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Master/U/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/Master/U/<id>/I/stack/current/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Master/U/<id>/I/stack/current/callbackThreshold
   [option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/Master/U/<id>/I/stack/voltage/callbackPeriod
   [-9223372036854775808..9223372036854775807]
```
```
TF/Master/U/<id>/I/stack/voltage/callbackThreshold
   [option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/Master/U/<id>/I/statusLED/enabled
   [true|false]
```
```
TF/Master/U/<id>/S/USB/voltage/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Master/U/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/Master/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/Master/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/Master/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/Master/U/<id>/S/stack/current/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Master/U/<id>/S/stack/current/callbackThreshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/Master/U/<id>/S/stack/voltage/callbackPeriod
   [-9223372036854775808..9223372036854775807]
```
```
TF/Master/U/<id>/S/stack/voltage/callbackThreshold
   [option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/Master/U/<id>/S/statusLED/enabled
   [true|false]
```

### Moisture
```
TF/Moisture/U/<id>/E/moisture
   - timestamp: [0..9223372036854775807]
     value: [0..4095]
```
```
TF/Moisture/U/<id>/E/moisture/reached
   - timestamp: [0..9223372036854775807]
     value: [0..4095]
```
```
TF/Moisture/U/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/Moisture/U/<id>/I/moisture/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Moisture/U/<id>/I/moisture/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/Moisture/U/<id>/I/movingAverage
   [0..100]
```
```
TF/Moisture/U/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/Moisture/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/Moisture/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/Moisture/U/<id>/S/moisture/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Moisture/U/<id>/S/moisture/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/Moisture/U/<id>/S/movingAverage
   [0..100]
```
```
TF/Moisture/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### MotionDetector
```
TF/MotionDetector/U/<id>/E/eventDetectionCycleEnded
   - timestamp: [0..9223372036854775807]
     value: true
```
```
TF/MotionDetector/U/<id>/E/motionDetected
   - timestamp: [0..9223372036854775807]
     value: true
```
```
TF/MotionDetector/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/MotionDetector/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/MotionDetector/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### MultiTouch
```
TF/MultiTouch/U/<id>/E/recalibrated
   - timestamp: [0..9223372036854775807]
     value: true
```
```
TF/MultiTouch/U/<id>/E/touchState
   - timestamp: [0..9223372036854775807]
     value: [0..8191]
```
```
TF/MultiTouch/U/<id>/I/electrode/config
   [0..8191]
```
```
TF/MultiTouch/U/<id>/I/electrode/sensitivity
   [0..8191]
```
```
TF/MultiTouch/U/<id>/I/recalibrate
   [true|false]
```
```
TF/MultiTouch/U/<id>/S/electrode/config
   [0..8191]
```
```
TF/MultiTouch/U/<id>/S/electrode/sensitivity
   [5..201]
```
```
TF/MultiTouch/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/MultiTouch/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/MultiTouch/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### NfcRfid
```
TF/NfcRfid/U/<id>/E/tag/discovered
   - timestamp: [0..9223372036854775807]
     value:
       id: [00..FF]_9
       type: [MifareClassic|Type1|Type2]
```
```
TF/NfcRfid/U/<id>/E/tag/read
   - timestamp: [0..9223372036854775807]
     value:
       id: [00..FF]_9
       value: [00..FF]_*
```
```
TF/NfcRfid/U/<id>/E/tag/vanished
   - timestamp: [0..9223372036854775807]
     value:
       id: [00..FF]_9
       type: [MifareClassic|Type1|Type2]
```
```
TF/NfcRfid/U/<id>/E/tag/written
   - timestamp: [0..9223372036854775807]
     value:
       id: [00..FF]_9
       state: [WritePageError|WritePageReady]
       value: [00..FF]_*
```
```
TF/NfcRfid/U/<id>/I/read
   [00..FF]_9
```
```
TF/NfcRfid/U/<id>/I/scanning/callbackPeriod
   [0..9223372036854775807]
```
```
TF/NfcRfid/U/<id>/I/write
   id: [00..FF]_9
     value: [00..FF]_*
```
```
TF/NfcRfid/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/NfcRfid/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/NfcRfid/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/NfcRfid/U/<id>/S/scanning/callbackPeriod
   [0..9223372036854775807]
```

### PiezoSpeaker
```
TF/PiezoSpeaker/U/<id>/E/calibrated
   - timestamp: [0..9223372036854775807]
     value: true
```
```
TF/PiezoSpeaker/U/<id>/E/finished
   - timestamp: [0..9223372036854775807]
     value: true
```
```
TF/PiezoSpeaker/U/<id>/E/started
   - timestamp: [0..9223372036854775807]
     value: true
```
```
TF/PiezoSpeaker/U/<id>/I/beep
   duration: [0..4294967295]
    frequency: [585..7100]
```
```
TF/PiezoSpeaker/U/<id>/I/calibrate
   [true|false]
```
```
TF/PiezoSpeaker/U/<id>/I/morse
   string: [.|-| |]_60
    frequency: [585..7100]
```
```
TF/PiezoSpeaker/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/PiezoSpeaker/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/PiezoSpeaker/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### PTC
```
TF/PTC/U/<id>/E/resistance
   - timestamp: [0..9223372036854775807]
     value: [0..32767]
```
```
TF/PTC/U/<id>/E/resistance/reached
   - timestamp: [0..9223372036854775807]
     value: [0..32767]
```
```
TF/PTC/U/<id>/E/temperature
   - timestamp: [0..9223372036854775807]
     value: [-24600..84900]
```
```
TF/PTC/U/<id>/E/temperature/reached
   - timestamp: [0..9223372036854775807]
     value: [-24600..84900]
```
```
TF/PTC/U/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/PTC/U/<id>/I/noiseReductionFilter
   filter: [Hz_50|Hz_60]
```
```
TF/PTC/U/<id>/I/resistance/callbackPeriod
   [0..9223372036854775807]
```
```
TF/PTC/U/<id>/I/resistance/threshold
   option: [x|o|i|<|>]
    min: [0..32767]
    max: [0..32767]
```
```
TF/PTC/U/<id>/I/temperature/callbackPeriod
   [0..9223372036854775807]
```
```
TF/PTC/U/<id>/I/temperature/threshold
   option: [x|o|i|<|>]
    min: [-24600..84900]
    max: [-24600..84900]
```
```
TF/PTC/U/<id>/I/wireMode
   [2|3|4]
```
```
TF/PTC/U/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/PTC/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/PTC/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/PTC/U/<id>/S/noiseReductionFilter
   filter: [Hz_50|Hz_60]
```
```
TF/PTC/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/PTC/U/<id>/S/resistance/callbackPeriod
   [0..9223372036854775807]
```
```
TF/PTC/U/<id>/S/resistance/threshold
   option: [x|o|i|<|>]
    min: [0..32767]
    max: [0..32767]
```
```
TF/PTC/U/<id>/S/temperature/callbackPeriod
   [0..9223372036854775807]
```
```
TF/PTC/U/<id>/S/temperature/threshold
   option: [x|o|i|<|>]
    min: [-24600..84900]
    max: [-24600..84900]
```
```
TF/PTC/U/<id>/S/wireMode
   [2|3|4]
```

### RealTimeClock
```
TF/RealTimeClock/U/<id>/E/alarm
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
TF/RealTimeClock/U/<id>/E/dateTime
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
TF/RealTimeClock/U/<id>/I/alarm
   month: [-1|1..12]
    day: [-1|1..31]
    hour: [-1|0..23]
    minute: [-1|0..59]
    second: [-1|0..59]
    weekday: [disabled|monday|tuesday|wednesday|thursday|friday|saturday|sunday]
    interval:[-1|0..2147483647]
```
```
TF/RealTimeClock/U/<id>/I/dateTime/callbackPeriod
   [0..9223372036854775807]
```
```
TF/RealTimeClock/U/<id>/I/dateTime/set
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
TF/RealTimeClock/U/<id>/I/offset
   [-128..127]
```
```
TF/RealTimeClock/U/<id>/S/alarm
   month: [-1|1..12]
    day: [-1|1..31]
    hour: [-1|0..23]
    minute: [-1|0..59]
    second: [-1|0..59]
    weekday: [disabled|monday|tuesday|wednesday|thursday|friday|saturday|sunday]
    interval:[-1|0..2147483647]
```
```
TF/RealTimeClock/U/<id>/S/dateTime
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
TF/RealTimeClock/U/<id>/S/dateTime/callbackPeriod
   [0..9223372036854775807]
```
```
TF/RealTimeClock/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/RealTimeClock/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/RealTimeClock/U/<id>/S/offset
   [-128..127]
```
```
TF/RealTimeClock/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### RemoteSwitch
```
TF/RemoteSwitch/U/<id>/E/switchingDone
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
TF/RemoteSwitch/U/<id>/I/dimSocketB
   address: [0..67108863]
    unit: [0..15]
    dimValue: [0..15]
```
```
TF/RemoteSwitch/U/<id>/I/repeats
   [0..32767]
```
```
TF/RemoteSwitch/U/<id>/I/switchSocketA
   houseCode: [0..31]
    receiverCode: [0..31]
    switchingValue: [switchOn|switchOff]
```
```
TF/RemoteSwitch/U/<id>/I/switchSocketB
   address: [0..67108863]
    unit: [0..15]
    switchingValue: [switchOn|switchOff]
```
```
TF/RemoteSwitch/U/<id>/I/switchSocketC
   systemCode: ['A'..'P']
    deviceCode: [1..16]
    switchingValue: [switchOn|switchOff]
```
```
TF/RemoteSwitch/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/RemoteSwitch/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/RemoteSwitch/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/RemoteSwitch/U/<id>/S/repeats
   [0..32767]
```

### RotaryEncoder
```
TF/RotaryEncoder/U/<id>/E/count
   - timestamp: [0..9223372036854775807]
     value: [0..9000]
```
```
TF/RotaryEncoder/U/<id>/E/count/reached
   - timestamp: [0..9223372036854775807]
     value: [-150..150]
```
```
TF/RotaryEncoder/U/<id>/E/count/reset
   - timestamp: [0..9223372036854775807]
     value: [-92233720368547758080..9223372036854775807]
```
```
TF/RotaryEncoder/U/<id>/E/pressed
   - timestamp: [0..9223372036854775807]
     value: true
```
```
TF/RotaryEncoder/U/<id>/E/released
   - timestamp: [0..9223372036854775807]
     value: true
```
```
TF/RotaryEncoder/U/<id>/I/count/callbackPeriod
   [0..9223372036854775807]
```
```
TF/RotaryEncoder/U/<id>/I/count/threshold
   option: [x|o|i|<|>]
    min: [-150..150]
    max: [-150..150]
```
```
TF/RotaryEncoder/U/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/RotaryEncoder/U/<id>/S/count/callbackPeriod
   [0..9223372036854775807]
```
```
TF/RotaryEncoder/U/<id>/S/count/threshold
   option: [x|o|i|<|>]
    min: [-150..150]
    max: [-150..150]
```
```
TF/RotaryEncoder/U/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/RotaryEncoder/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/RotaryEncoder/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/RotaryEncoder/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### RotaryPoti
```
TF/RotaryPoti/U/<id>/E/analogValue
   - timestamp: [0..9223372036854775807]
     value: [0..4095]
   
```
```
TF/RotaryPoti/U/<id>/E/analogValue/reached
   - timestamp: [0..9223372036854775807]
     value: [0..4095]
   
```
```
TF/RotaryPoti/U/<id>/E/position
   - timestamp: [0..9223372036854775807]
     value: [0..9000]
   
```
```
TF/RotaryPoti/U/<id>/E/position/reached
   - timestamp: [0..9223372036854775807]
     value: [-150..150]
   
```
```
TF/RotaryPoti/U/<id>/I/analogValue/callbackPeriod
   [0..9223372036854775807]
```
```
TF/RotaryPoti/U/<id>/I/analogValue/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/RotaryPoti/U/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/RotaryPoti/U/<id>/I/position/callbackPeriod
   [0..9223372036854775807]
```
```
TF/RotaryPoti/U/<id>/I/position/threshold
   option: [x|o|i|<|>]
    min: [-150..150]
    max: [-150..150]
```
```
TF/RotaryPoti/U/<id>/S/analogValue/callbackPeriod
   [0..9223372036854775807]
```
```
TF/RotaryPoti/U/<id>/S/analogValue/threshold
   option: [x|o|i|<|>]
    min: [0..4095]
    max: [0..4095]
```
```
TF/RotaryPoti/U/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/RotaryPoti/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/RotaryPoti/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/RotaryPoti/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/RotaryPoti/U/<id>/S/position/callbackPeriod
   [0..9223372036854775807]
```
```
TF/RotaryPoti/U/<id>/S/position/threshold
   option: [x|o|i|<|>]
    min: [-150..150]
    max: [-150..150]
```

### SegmentDisplay4x7
```
TF/SegmentDisplay4x7/U/<id>/E/counterFinished
   - timestamp: [0..9223372036854775807]
     value: true
```
```
TF/SegmentDisplay4x7/U/<id>/E/counterStarted
   - timestamp: [0..9223372036854775807]
     value: true
```
```
TF/SegmentDisplay4x7/U/<id>/I/counter
   from: [-999..9999]
    to: [-999..9999]
    increment: [-999..9999]
    lenght: [0..9223372036854775807]
```
```
TF/SegmentDisplay4x7/U/<id>/I/segments
   bits:[[0..128][0..128][0..128][0..128]]
    brightness: [0..7]
    colon: [true|false]
```
```
TF/SegmentDisplay4x7/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/SegmentDisplay4x7/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/SegmentDisplay4x7/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/SegmentDisplay4x7/U/<id>/S/segments
   bits:[[0..128][0..128][0..128][0..128]]
    brightness: [0..7]
    colon: [true|false]
```

### Servo
```
TF/Servo/U/<id>/E/positionReached
   - timestamp: [0..9223372036854775807]
     value:
       id: [0..6]
       position: [-32767..32767]
```
```
TF/Servo/U/<id>/E/undervoltage
   - timestamp: [0..9223372036854775807]
     value: [0..2147483647]
```
```
TF/Servo/U/<id>/E/velocityReached
   - timestamp: [0..9223372036854775807]
     value:
       id: [0..6]
       value: [0..32767]
```
```
TF/Servo/U/<id>/I/minimumVoltage
   [5000..2147483647]
```
```
TF/Servo/U/<id>/I/outputVoltage
   [2000..9000]
```
```
TF/Servo/U/<id>/I/servos
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
TF/Servo/U/<id>/I/statusLED
   [true|false]
```
```
TF/Servo/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/Servo/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/Servo/U/<id>/S/minimumVoltage
   [6..2147483647]
```
```
TF/Servo/U/<id>/S/outputVoltage
   [1..20000]
```
```
TF/Servo/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/Servo/U/<id>/S/servos
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
TF/Servo/U/<id>/S/statusLED
   [true|false]
```

### SolidState
```
TF/SolidState/U/<id>/E/monoflopDone
   - timestamp: [0..9223372036854775807]
     value: [true|false]
```
```
TF/SolidState/U/<id>/I/monoflop
   state: [true|false]
    period: [0..9223372036854775807]
```
```
TF/SolidState/U/<id>/I/state
   [true|false]
```
```
TF/SolidState/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/SolidState/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/SolidState/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/SolidState/U/<id>/S/state
   [true|false]
```

### SoundIntensity
```
TF/SoundIntensity/U/<id>/E/soundIntensity
   - timestamp: [0..9223372036854775807]
     value: [0..10000]
   
```
```
TF/SoundIntensity/U/<id>/E/soundIntensity/reached
   - timestamp: [0..9223372036854775807]
     value: [0..10000]
   
```
```
TF/SoundIntensity/U/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/SoundIntensity/U/<id>/I/soundIntensity/callbackPeriod
   [0..9223372036854775807]
```
```
TF/SoundIntensity/U/<id>/I/soundIntensity/threshold
   option: [x|o|i|<|>]
    min: [0..10000]
    max: [0..10000]
```
```
TF/SoundIntensity/U/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/SoundIntensity/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/SoundIntensity/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/SoundIntensity/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/SoundIntensity/U/<id>/S/soundIntensity/callbackPeriod
   [0..9223372036854775807]
```
```
TF/SoundIntensity/U/<id>/S/soundIntensity/threshold
   option: [x|o|i|<|>]
    min: [0..10000]
    max: [0..10000]
```

### Temperature
```
TF/Temperature/U/<id>/E/temperature
   - timestamp: [0..9223372036854775807]
     value: [-2500..8500]
```
```
TF/Temperature/U/<id>/E/temperature/reached
   - timestamp: [0..9223372036854775807]
     value: [-2500..8500]
```
```
TF/Temperature/U/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/Temperature/U/<id>/I/mode
   mode:[Fast|Slow]
```
```
TF/Temperature/U/<id>/I/temperature/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Temperature/U/<id>/I/temperature/threshold
   option: [x|o|i|<|>]
    min: [-2500..8500]
    max: [-2500..8500]
```
```
TF/Temperature/U/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/Temperature/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/Temperature/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/Temperature/U/<id>/S/mode
   mode:[Slow|Fast]
```
```
TF/Temperature/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/Temperature/U/<id>/S/temperature/callbackPeriod
   [0..9223372036854775807]
```
```
TF/Temperature/U/<id>/S/temperature/threshold
   option: [x|o|i|<|>]
    min: [-2500..8500]
    max: [-2500..8500]
```

### TemperatureIR
```
TF/TemperatureIR/U/<id>/E/ambientTemperature
   - timestamp: [0..9223372036854775807]
     value: [-400..1250]
```
```
TF/TemperatureIR/U/<id>/E/ambientTemperature/reached
   - timestamp: [0..9223372036854775807]
     value: [-400..1250]
```
```
TF/TemperatureIR/U/<id>/E/objectTemperature
   - timestamp: [0..9223372036854775807]
     value: [-700..3800]
```
```
TF/TemperatureIR/U/<id>/E/objectTemperature/reached
   - timestamp: [0..9223372036854775807]
     value: [-700..3800]
```
```
TF/TemperatureIR/U/<id>/I/ambientTemperature/callbackPeriod
   [0..9223372036854775807]
```
```
TF/TemperatureIR/U/<id>/I/ambientTemperature/threshold
   option: [x|o|i|<|>]
    min: [-400..1250]
    max: [-400..1250]
```
```
TF/TemperatureIR/U/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/TemperatureIR/U/<id>/I/objectTemperature/callbackPeriod
   [0..9223372036854775807]
```
```
TF/TemperatureIR/U/<id>/I/objectTemperature/threshold
   option: [x|o|i|<|>]
    min: [-700..3800]
    max: [-700..3800]
```
```
TF/TemperatureIR/U/<id>/S/ambientTemperature/callbackPeriod
   [0..9223372036854775807]
```
```
TF/TemperatureIR/U/<id>/S/ambientTemperature/threshold
   option: [x|o|i|<|>]
    min: [-400..1250]
    max: [-400..1250]
```
```
TF/TemperatureIR/U/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/TemperatureIR/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/TemperatureIR/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/TemperatureIR/U/<id>/S/objectTemperature/callbackPeriod
   [0..9223372036854775807]
```
```
TF/TemperatureIR/U/<id>/S/objectTemperature/threshold
   option: [x|o|i|<|>]
    min: [-700..3800]
    max: [-700..3800]
```
```
TF/TemperatureIR/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### ThermoCouple
```
TF/ThermoCouple/U/<id>/E/temperature
   - timestamp: [0..9223372036854775807]
     value: [-21000..180000]
```
```
TF/ThermoCouple/U/<id>/E/temperature/reached
   - timestamp: [0..9223372036854775807]
     value: [-21000..180000]
```
```
TF/ThermoCouple/U/<id>/I/configuration
   averaging:[sample_1|sample_2|sample_4|smaple_8|sample_16]
    type: [B|E|J|K|N|R|S|T|G8|G32]
    filter: [Hz_50|Hz_60]
```
```
TF/ThermoCouple/U/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/ThermoCouple/U/<id>/I/temperature/callbackPeriod
   [0..9223372036854775807]
```
```
TF/ThermoCouple/U/<id>/I/temperature/threshold
   option: [x|o|i|<|>]
    min: [-21000..180000]
    max: [-21000..180000]
```
```
TF/ThermoCouple/U/<id>/S/configuration
   averaging:[sample_1|sample_2|sample_4|smaple_8|sample_16]
    type: [B|E|J|K|N|R|S|T|G8|G32]
    filter: [Hz_50|Hz_60]
```
```
TF/ThermoCouple/U/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/ThermoCouple/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/ThermoCouple/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/ThermoCouple/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/ThermoCouple/U/<id>/S/temperature/callbackPeriod
   [0..9223372036854775807]
```
```
TF/ThermoCouple/U/<id>/S/temperature/threshold
   option: [x|o|i|<|>]
    min: [-21000..180000]
    max: [-21000..180000]
```

### Tilt
```
TF/Tilt/U/<id>/E/tiltState
   - timestamp: [0..9223372036854775807]
     value:  [0..2]
```
```
TF/Tilt/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/Tilt/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/Tilt/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```

### UVLight
```
TF/UVLight/U/<id>/E/uvLight
   - timestamp: [0..9223372036854775807]
     value: [0..328000]
```
```
TF/UVLight/U/<id>/E/uvLight/reached
   - timestamp: [0..9223372036854775807]
     value: [0..328000]
```
```
TF/UVLight/U/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/UVLight/U/<id>/I/uvLight/callbackPeriod
   [0..9223372036854775807]
```
```
TF/UVLight/U/<id>/I/uvLight/threshold
   option: [x|o|i|<|>]
    min: [0..328000]
    max: [0..328000]
```
```
TF/UVLight/U/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/UVLight/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/UVLight/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/UVLight/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/UVLight/U/<id>/S/uvLight/callbackPeriod
   [0..9223372036854775807]
```
```
TF/UVLight/U/<id>/S/uvLight/threshold
   option: [x|o|i|<|>]
    min: [0..328000]
    max: [0..328000]
```

### VoltageCurrent
```
TF/VoltageCurrent/U/<id>/E/current
   - timestamp: [0..9223372036854775807]
     value: [0..36000]
```
```
TF/VoltageCurrent/U/<id>/E/current/reached
   - timestamp: [0..9223372036854775807]
     value: [0..36000]
```
```
TF/VoltageCurrent/U/<id>/E/power
   - timestamp: [0..9223372036854775807]
     value: [0..720000]
```
```
TF/VoltageCurrent/U/<id>/E/power/reached
   - timestamp: [0..9223372036854775807]
     value: [0..720000]
```
```
TF/VoltageCurrent/U/<id>/E/voltage
   - timestamp: [0..9223372036854775807]
     value: [-50001..50001]
```
```
TF/VoltageCurrent/U/<id>/E/voltage/reached
   - timestamp: [0..9223372036854775807]
     value: [-50001..50001]
```
```
TF/VoltageCurrent/U/<id>/I/calibration
   gainMultiplier: [1..2147483647]
    gainDivisor: [1..2147483647]
```
```
TF/VoltageCurrent/U/<id>/I/configuration
   averaging: [AVERAGING_1|AVERAGING_4|AVERAGING_16|AVERAGING_64|AVERAGING_128|AVERAGING_256|AVERAGING_512|AVERAGING_1024]
    voltageConversionTime: [CONVERSION_140us|CONVERSION_204us|CONVERSION_332us|CONVERSION_588us|CONVERSION_1100us|CONVERSION_2116us|CONVERSION_4156us|CONVERSION_8244us]
    currentConversionTime: [CONVERSION_140us|CONVERSION_204us|CONVERSION_332us|CONVERSION_588us|CONVERSION_1100us|CONVERSION_2116us|CONVERSION_4156us|CONVERSION_8244us]
   
```
```
TF/VoltageCurrent/U/<id>/I/current/callbackPeriod
   [0..9223372036854775807]
```
```
TF/VoltageCurrent/U/<id>/I/current/threshold
   option: [x|o|i|<|>]
    min: [0..36000]
    max: [0..36000]
```
```
TF/VoltageCurrent/U/<id>/I/debounce/period
   [0..9223372036854775807]
```
```
TF/VoltageCurrent/U/<id>/I/power/callbackPeriod
   [0..9223372036854775807]
```
```
TF/VoltageCurrent/U/<id>/I/power/threshold
   option: [x|o|i|<|>]
    min: [0..720000]
    max: [0..720000]
```
```
TF/VoltageCurrent/U/<id>/I/voltage/callbackPeriod
   [0..9223372036854775807]
```
```
TF/VoltageCurrent/U/<id>/I/voltage/threshold
   option: [x|o|i|<|>]
    min: [-50001..50001]
    max: [-50001..50001]
```
```
TF/VoltageCurrent/U/<id>/S/calibration
   gainMultiplier: [1..2147483647]
    gainDivisor: [1..2147483647]
```
```
TF/VoltageCurrent/U/<id>/S/configuration
   averaging: [AVERAGING_1|AVERAGING_4|AVERAGING_16|AVERAGING_64|AVERAGING_128|AVERAGING_256|AVERAGING_512|AVERAGING_1024]
    voltageConversionTime: [CONVERSION_140us|CONVERSION_204us|CONVERSION_332us|CONVERSION_588us|CONVERSION_1100us|CONVERSION_2116us|CONVERSION_4156us|CONVERSION_8244us]
    currentConversionTime: [CONVERSION_140us|CONVERSION_204us|CONVERSION_332us|CONVERSION_588us|CONVERSION_1100us|CONVERSION_2116us|CONVERSION_4156us|CONVERSION_8244us]
   
```
```
TF/VoltageCurrent/U/<id>/S/current/callbackPeriod
   [0..9223372036854775807]
```
```
TF/VoltageCurrent/U/<id>/S/current/threshold
   option: [x|o|i|<|>]
    min: [0..36000]
    max: [0..36000]
```
```
TF/VoltageCurrent/U/<id>/S/debounce/period
   [0..9223372036854775807]
```
```
TF/VoltageCurrent/U/<id>/S/firmware
   [[-32768...32767]]_*
```
```
TF/VoltageCurrent/U/<id>/S/hardware
   [[-32768...32767]]_*
```
```
TF/VoltageCurrent/U/<id>/S/position
   [0|1|2|3|4|5|6|7|8|a|b|c|d]
```
```
TF/VoltageCurrent/U/<id>/S/power/callbackPeriod
   [0..9223372036854775807]
```
```
TF/VoltageCurrent/U/<id>/S/power/threshold
   option: [x|o|i|<|>]
    min: [0..720000]
    max: [0..720000]
```
```
TF/VoltageCurrent/U/<id>/S/voltage/callbackPeriod
   [0..9223372036854775807]
```
```
TF/VoltageCurrent/U/<id>/S/voltage/threshold
   option: [x|o|i|<|>]
    min: [0..50001]
    max: [-50001..50001]
```

