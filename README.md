# TiMqWay
[Data-driven] [micro-service]s for the bricks and bricklets of the [Tinkerforge](tm) world, based on ch.quantasy.mqtt.gateway.
ch.quantasy.tinkerforge.mqtt.gateway

Please note, that this project depends on [https://github.com/knr1/ch.quantasy.mqtt.gateway]


The underlying idea of TiMqWay is a set of micro-services with auto discovery and data definition using the mqtt protocol, providing a data-driven
interface to the known Tinkerforge devices. This way, the implementation is agnostic to the programming-language and paradigm used for orchestration. Any language will do, as long as you master it.

What is left to do for the user of TiMqWay, is the orchestration / choreography of these services in order to fit the needs.


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
* **Description** Each micro-service class describes its abilities and provides a data definition via the description topic.
* **Status** Each micro-service instance describes its actual status via its specialized status topics.
* **Event** Each micro-service instance provides all events via its specialized event topics.As there might be more events available than the mqtt broker is able to handle, all events are always covered within an array. Hence, there might be 0,1 or multiple events within one message.
* **Intent** Each micro-service instance accepts _intentions_ via the intent topic. It is equivalent to the setter methods but allows _parallel_ and _concurrent_ 'requests'.


**Root topic** The root topic of TiMqWay, where all Tinkerforge micro-services can be reached: **TF/**.

**Data language** TiMqWay's data language used is **[YAML]**.

**No Connection between services** The services do not know each other and cannot 'learn' from other services (i.e. they are cohesive). What is needed is a software
that orchestrates / choreographs the services. This can be written in any programming language (e.g. Node-Red, Java, js, Swift, ...), as long as you can access the mqtt-broker used.

### Abstraction to Tinkerforge 
Each micro-service is an instance of a Tinkerforge-Device class, accessible by its UID. Hence, devices are not represented within their 'stack', but loosely coupled and class-wise so
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

Then, if you subscribe to TF/# you will immediately get the description info for the TiMqWay-Manager, which does all the discovery stuff for you.

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
Topic: TF/LCD20x4/U/lcd/I/quickshot
Message: backlight: true
```

**Measuring the temperature once per second on temperature bricklet (uid: red):**
```
Topic: TF/Temperature/U/red/I/quickshot
Message: temperatureCallbackPeriod: 1000
```

**Detecting NFC-Tags once per second on NFCRFID bricklet (uid: ouu):**
```
Topic: TF/NfcRfid/U/ouu/I/quickshot
Message: scanningCallbackPeriod: 1000
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
         address: 
          hostName: String <min: 1 max: 255>
          hostName: String <regEx: \w+(\.\w+){0,3}>
          port: Number <from: 1024 to: 65535>
          connect: Boolean <true,false> 
   
     U
       pc
          S
            connection --- online
```

### Connecting Master-Brick-1

As the description explains, we now have to tell TiMqWay where to look for the Master Bricks (Stacks). Hence, we want to attach Master-Brick-1 (say, its 
network-name is master-brick-1). Therefore the following message has to be sent to the following topic:
```
Topic: TF/Manager/U/pc/I
Message: address:
           hostName: master-brick-1
           connect: true
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
            #omitted for better readability
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
Topic: TF/LCD20x4/U/lcd/I
Message: backlight: true
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
           #omitted for better readability 

```
 
Now, let us connect the second master-brick (stack). This one is connected via USB, hence, its address is `localhost`:
```
Topic: TF/Manager/U/pc/I
Message: address:
          hostName: localhost
          connect: true
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
       red
         S
           connection --- online
           position --- "a"
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

If we want to have a temperature reading every second for `red`, we provide the following message to the following topic:
```
Topic: TF/Temperature/U/red/I
Message: temperatureCallbackPeriod: 1000
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
All that is left is to write the orchestration/choreography, subscribing to the temperature of red and blue. Then process the values and write them to lcd... as a publish to

```
TF/LCD20x4/U/lcd/I
   lines: Set <min: 0 max: 80>
```
```
Topic: TF/LCD20x4/U/lcd/I

Message:
  lines: 
    - line: 0
      position: 0
      text: "RED: 22°C"
    - line: 1
      position: 0
      text: "BLUE: 18°C"
```

## API


### Manager                                                                        
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
TF/Manager/U/<id>/I
   address: 
     hostName: String <min: 1 max: 255>
     hostName: String <regEx: \w+(\.\w+){0,3}>
     port: Number <from: 1024 to: 65535>
   connect: Boolean <true,false> 
   
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
TF/IMU/U/<id>/I
   accelerationCallbackPeriod: null
   accelerationCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   allDataCallbackPeriod: null
   allDataCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   angularVelocityCallbackPeriod: null
   angularVelocityCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   leds: null
   leds: Boolean <true,false> 
   magneticFieldCallbackPeriod: null
   magneticFieldCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   orientationCalculation: null
   orientationCalculation: Boolean <true,false> 
   orientationCallbackPeriod: null
   orientationCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   quaternionCallbackPeriod: null
   quaternionCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   statusLED: null
   statusLED: Boolean <true,false> 
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/IMUV2/U/<id>/I
   accelerationCallbackPeriod: null
   accelerationCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   allDataCallbackPeriod: null
   allDataCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   angularVelocityCallbackPeriod: null
   angularVelocityCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   gravityVectorCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   leds: null
   leds: Boolean <true,false> 
   linearAccelerationCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   magneticFieldCallbackPeriod: null
   magneticFieldCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   orientationCalculation: null
   orientationCalculation: Boolean <true,false> 
   orientationCallbackPeriod: null
   orientationCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   quaternionCallbackPeriod: null
   quaternionCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   sensorFusionMode: String <[0, 1, 2]>
   statusLED: null
   statusLED: Boolean <true,false> 
   temperatureCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/LCD16x2/U/<id>/I
   backlight: Boolean <true,false> 
   clearDisplay: Boolean <true,false> 
   customCharacters: Set <min: 0 max: 8>
   customCharacters: 
    index: Number <from: 0 to: 7>
    pixels: Set <min: 8 max: 8>
   lines: Set <min: 0 max: 32>
   lines: 
    line: Number <from: 0 to: 1>
    position: Number <from: 0 to: 15>
    text: String <min: 0 max: 16>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
```

### LCD20x4
```
TF/LCD20x4/U/<id>/E/button/pressed
   - timestamp: [0..9223372036854775807]
     value: [1..4]
   
```
```
TF/LCD20x4/U/<id>/E/button/released
   - timestamp: [0..9223372036854775807]
     value: [1..4]
   
```
```
TF/LCD20x4/U/<id>/I
   backlight: Boolean <true,false> 
   clearDisplay: Boolean <true,false> 
   customCharacters: Set <min: 0 max: 8>
   customCharacters: 
    index: Number <from: 0 to: 7>
    pixels: Set <min: 8 max: 8>
   defaultTextCounter: Number <from: -1 to: 2147483647>
   defaultTexts: Set <min: 0 max: 4>
   defaultTexts: 
    line: Number <from: 0 to: 3>
    text: String <min: 0 max: 20>
   lines: Set <min: 0 max: 80>
   lines: 
    line: Number <from: 0 to: 3>
    position: Number <from: 0 to: 19>
    text: String <min: 0 max: 20>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/Accelerometer/U/<id>/I
   accelerationThreshold: 
     option: String <[x, o, i, <, >]>
   callbackPeriod: Number <from: 0 to: 9223372036854775807>
   configuration: 
     DataRate: String <OFF,Hz3,Hz6,Hz12,Hz25,Hz50,Hz100,Hz400,Hz800,Hz1600>
     FilterBandwidth: String <Hz800,Hz400,Hz200,Hz50>
     FullScale: String <G2,G4,G6,G8,G16>
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/AmbientLight/U/<id>/I
   analogCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   analogValueThreshold: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   illuminanceCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   illuminanceThreshold: 
     max: Number <from: 0 to: 9000>
     min: Number <from: 0 to: 9000>
     option: String <[x, o, i, <, >]>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/AmbientLightV2/U/<id>/I
   analogValueThreshold: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   configuration: 
     IlluminanceRange: String <lx_unlimitted,lx_64000,lx_32000,lx_16000,lx_8000,lx_1300,lx_600>
     IntegrationTime: String <ms_50,ms_100,ms_150,ms_200,ms_250,ms_300,ms_350,ms_400>
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   illuminanceCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/AnalogInV2/U/<id>/I
   analogValueCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   analogValueCallbackThreshold: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   movingAverage: Number <from: 0 to: 9223372036854775807>
   voltageCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   voltageCallbackThreshold: 
     max: Number <from: 0 to: 42000>
     min: Number <from: 0 to: 42000>
     option: String <[x, o, i, <, >]>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/AnalogOutV2/U/<id>/I
   outputVoltage: Number <from: 0 to: 12000>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/Barometer/U/<id>/I
   airPressureCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   airPressureCallbackThreshold: 
     max: Number <from: 10000 to: 1200000>
     min: Number <from: 10000 to: 1200000>
     option: String <[x, o, i, <, >]>
   altitudeCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   altitudeCallbackThreshold: 
     option: String <[x, o, i, <, >]>
   averaging: 
     averagingPressure: Number <from: 0 to: 10>
     averagingTemperature: Number <from: 0 to: 255>
     movingAveragePressure: Number <from: 0 to: 25>
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/CO2/U/<id>/I
   co2ConcentrationCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   co2ConcentrationCallbackThreshold: 
     max: Number <from: 0 to: 10000>
     min: Number <from: 0 to: 10000>
     option: String <[x, o, i, <, >]>
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/Color/U/<id>/I
   colorCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   colorCallbackThreshold: 
     maxB: Number <from: 0 to: 65535>
     maxC: Number <from: 0 to: 65535>
     maxG: Number <from: 0 to: 65535>
     maxR: Number <from: 0 to: 65535>
     minB: Number <from: 0 to: 65535>
     minC: Number <from: 0 to: 65535>
     minG: Number <from: 0 to: 65535>
     minR: Number <from: 0 to: 65535>
     option: String <[x, o, i, <, >]>
   colorTemperatureCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   config: 
     Gain: String <x1,x4,x16,Hz60>
     IntegrationTime: String <ms2_4,ms24,ms101,ms154,ms700>
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   illuminanceCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   light: Boolean <true,false> 
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/DC/U/<id>/I
   acceleration: Number <from: 0 to: 2147483647>
   driveMode: String <[1, 2]>
   enable: Boolean <true,false> 
   fullBrake: Boolean <true,false> 
   minimumVoltage: Number <from: 6000 to: 2147483647>
   pwmFrequency: Number <from: 1 to: 20000>
   velocity: Number <from: -32767 to: 32767>
   velocityPeriod: Number <from: 0 to: 9223372036854775807>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/DistanceIR/U/<id>/I
   analogCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   analogValueCallbackThreshold: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   distanceCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   distanceCallbackThreshold: 
     max: Number <[from: 0 to: 0,from: 40 to: 300,from: 100 to: 800,from: 200 to: 1500]>
     min: Number <[from: 0 to: 0,from: 40 to: 300,from: 100 to: 800,from: 200 to: 1500]>
     option: String <[x, o, i, <, >]>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/DistanceUS/U/<id>/I
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   distanceCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   distanceCallbackThreshold: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   movingAverage: Number <from: 0 to: 100>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/DualButton/U/<id>/I
   ledState: 
     LEDState: String <AutoToggleOn,AutoToggleOff,On,Off>
     LEDState: String <AutoToggleOn,AutoToggleOff,On,Off>
   selectedLEDStates: Set <min: 0 max: 2>
   selectedLEDStates: 
    led: String <[1, 2]>
    LEDState: String <AutoToggleOn,AutoToggleOff,On,Off>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/DustDetector/U/<id>/I
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   dustDensityCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   dustDensityCallbackThreshold: 
     max: Number <from: 0 to: 500>
     min: Number <from: 0 to: 500>
     option: String <[x, o, i, <, >]>
   movingAverage: Number <from: 0 to: 100>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/GPS/U/<id>/I
   altitudeCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   coordinatesCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   dateTimeCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   motionCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   RestartType: String <hot,warm,cold,factoryReset>
   statusCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/GPSv2/U/<id>/I
   altitudeCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   coordinatesCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   dateTimeCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   FixLEDConfig: String <OFF,ON,HEARTBEAT,FIX,PPS>
   motionCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   RestartType: String <hot,warm,cold,factoryReset>
   statusCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   StatusLEDConfig: String <OFF,ON,HEARTBEAT,STATUS>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/HallEffect/U/<id>/I
   edgeCountCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   edgeCountInterrupt: Number <from: 0 to: 9223372036854775807>
   edgeCountReset: Boolean <true,false> 
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/Humidity/U/<id>/I
   analogCallbackPeriod: null
   analogCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   analogValueCallbackThreshold: null
   analogValueCallbackThreshold: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   debouncePeriod: null
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   humidityCallbackPeriod: null
   humidityCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   humidityCallbackThreshold: null
   humidityCallbackThreshold: 
     max: Number <from: 0 to: 1000>
     min: Number <from: 0 to: 1000>
     option: String <[x, o, i, <, >]>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/Joystick/U/<id>/I
   analogCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   analogValueCallbackThreshold: 
     maxX: Number <from: 0 to: 4095>
     maxY: Number <from: 0 to: 4095>
     minX: Number <from: 0 to: 4095>
     minY: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   calibrate: Boolean <true,false> 
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   positionCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   positionCallbackThreshold: 
     maxX: Number <from: -100 to: 100>
     maxY: Number <from: -100 to: 100>
     minX: Number <from: -100 to: 100>
     minY: Number <from: -100 to: 100>
     option: String <[x, o, i, <, >]>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/LaserRangeFinder/U/<id>/I
   configuration: 
     aquisitionCount: Number <from: 1 to: 255>
     measurementFrequency: Number <[from: 0 to: 0,from: 10 to: 500]>
     quickTermination: Boolean <true,false> 
     thresholdValue: Number <from: 0 to: 255>
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   distanceCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   distanceCallbackThreshold: 
     max: Number <from: 0 to: 4000>
     min: Number <from: 0 to: 4000>
     option: String <[x, o, i, <, >]>
   laserEnabled: Boolean <true,false> 
   mode: 
     Mode: String <distance,velocity_12_7,velocity_31_75,velocity_63_5,velocity_127>
   movingAverage: 
     averagingDistance: Number <from: 0 to: 30>
     averagingVelocity: Number <from: 0 to: 30>
   velocityCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   velocityCallbackThreshold: 
     max: Number <from: -127 to: 127>
     min: Number <from: -127 to: 127>
     option: String <[x, o, i, <, >]>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/LEDStrip/U/<id>/I
   config: null
   config: 
     ChannelMapping: String <BGR,BGRW,BGWR,BRG,BRGW,BRWG,BWGR,BWRG,GBR,GBRW,GBWR,GRB,GRBW,GRWB,GWBR,GWRB,RBG,RBGW,RBWG,RGB,RGBW,RGWB,RWBG,RWGB>
     ChipType: String <WS2801,WS2811,WS2812,WS2812RGBW,LPD8806,APA102>
     clockFrequencyOfICsInHz: Number <from: 10000 to: 2000000>
     frameDurationInMilliseconds: Number <from: 0 to: 9223372036854775807>
     numberOfLEDs: Number <from: 0 to: 320>
   LEDFrame: null
   LEDFrame: 
     channels: Arrays: <[min: 1 max: 4,min: 1 max: 320]>
     channels: Number <from: 0 to: 255>
   LEDFrames: null
   LEDFrames: Array <min: 1 max: 2147483647>
     LEDFrame: 
       channels: Arrays: <[min: 1 max: 4,min: 1 max: 320]>
       channels: Number <from: 0 to: 255>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/Line/U/<id>/I
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   reflectivityCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   reflectivityCallbackThreshold: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/LinearPoti/U/<id>/I
   analogCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   analogValueCallbackThreshold: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   positionCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   positionCallbackThreshold: 
     max: Number <from: 0 to: 100>
     min: Number <from: 0 to: 100>
     option: String <[x, o, i, <, >]>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/LoadCell/U/<id>/I
   configuration: 
     Gain: String <gain128X,gain64X,gain32X>
     Rate: String <rate10Hz,rate80Hz>
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   movingAverage: Number <from: 1 to: 40>
   statusLED: Boolean <true,false> 
   tare: Boolean <true,false> 
   weightCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   weightCallbackThreshold: 
     max: Number <from: -50001 to: 50001>
     min: Number <from: -50001 to: 50001>
     option: String <[x, o, i, <, >]>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/Master/U/<id>/I
   currentCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   currentCallbackThreshold: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   reset: Boolean <true,false> 
   stackVoltageCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   stackVoltageCallbackThreshold: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   statusLED: Boolean <true,false> 
   usbVoltageCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   usbVoltageCallbackThreshold: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/Moisture/U/<id>/I
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   moistureCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   moistureCallbackThreshold: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   movingAverage: Number <from: 0 to: 100>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/MotionDetector/U/<id>/I
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/MultiTouch/U/<id>/I
   electrodeConfig: Number <from: 0 to: 8191>
   recalibration: Boolean <true,false> 
   sensitivity: Number <from: 5 to: 201>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/NfcRfid/U/<id>/I
   nfcWrite: 
     tagID: String <regEx: [0-9A-F]{8}|[0-9A-F]{14}>
     value: Array <min: 0 max: 2147483647>
   scanningInterval: Number <from: 0 to: 9223372036854775807>
   tagID: String <regEx: [0-9A-F]{8}|[0-9A-F]{14}>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/PiezoSpeaker/U/<id>/I
   beepParameter: 
     duration: Number <from: 0 to: 2147483647>
     frequency: Number <from: 585 to: 7100>
   calibrate: Boolean <true,false> 
   morseCodeParameter: 
     frequency: Number <from: 585 to: 7100>
     string: String <regEx: [\.\s-]{1,60}>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/PTC/U/<id>/I
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   noiseReductionFilter: 
     Filter: String <Hz_50,Hz_60>
   resistanceCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   temperatureCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   wireMode: String <[2, 3, 4]>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/RealTimeClock/U/<id>/I
   alarmParameter: 
     day: Number <from: 1 to: 31>
     hour: Number <from: 0 to: 23>
     interval: Number <from: -1 to: 2147483647>
     minute: Number <from: 0 to: 59>
     month: Number <from: 1 to: 12>
     second: Number <from: 0 to: 59>
     WeekDay: String <monday,tuesday,wednesday,thursday,friday,saturday,sunday,disabled>
   dateTimeParameter: 
     centisecond: Number <from: 0 to: 10>
     day: Number <from: 1 to: 31>
     hour: Number <from: 0 to: 59>
     minute: Number <from: 0 to: 59>
     month: Number <from: 1 to: 12>
     second: Number <from: 0 to: 59>
     WeekDay: String <monday,tuesday,wednesday,thursday,friday,saturday,sunday>
     year: Number <from: 2000 to: 2099>
   offset: Number <from: -128 to: 127>
   period: Number <from: 0 to: 9223372036854775807>
   
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
    day: [1..31]
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/RemoteSwitch/U/<id>/I
   dimSocketBParameters: 
     address: Number <from: 0 to: 67108863>
     dimValue: Number <from: 0 to: 15>
     unit: Number <from: 0 to: 15>
   repeats: Number <from: 0 to: 32767>
   switchSocketAParameters: 
     houseCode: Number <from: 0 to: 31>
     receiverCode: Number <from: 0 to: 31>
     SwitchTo: String <switchOn,switchOff>
   switchSocketBParameters: 
     address: Number <from: 0 to: 67108863>
     SwitchTo: String <switchOn,switchOff>
     unit: Number <from: 0 to: 15>
   switchSocketCParameters: 
     deviceCode: Number <from: 1 to: 16>
     SwitchTo: String <switchOn,switchOff>
     systemCode: Number <from: 65 to: 80>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/RotaryEncoder/U/<id>/I
   countCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   countReset: Boolean <true,false> 
   countThreshold: 
     max: Number <from: -150 to: 150>
     min: Number <from: -150 to: 150>
     option: String <[x, o, i, <, >]>
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/RotaryPoti/U/<id>/I
   analogValueCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   analogValueCallbackThreshold: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   positionCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   positionCallbackThreshold: 
     max: Number <from: -150 to: 150>
     min: Number <from: -150 to: 150>
     option: String <[x, o, i, <, >]>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/SegmentDisplay4x7/U/<id>/I
   counterParameter: null
   counterParameter: 
     from: Number <from: -999 to: 9999>
     increment: Number <from: -999 to: 9999>
     length: Number <from: 0 to: 9223372036854775807>
     to: Number <from: -999 to: 9999>
   segments: null
   segments: 
     bits: Array <min: 4 max: 4>
     brightness: Number <from: 0 to: 7>
     colon: Boolean <true,false> 
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/Servo/U/<id>/I
   minimumVoltage: Number <from: 5000 to: 2147483647>
   outputVoltage: Number <from: 2000 to: 9000>
   servos: Set <min: 0 max: 7>
   servos: 
    acceleration: Number <from: 0 to: 65536>
    degree: 
      max: Number <from: -32767 to: 32767>
      min: Number <from: -32767 to: 32767>
    enabled: Boolean <true,false> 
    id: Number <from: 0 to: 6>
    period: Number <from: 1 to: 65536>
    position: Number <from: -32767 to: 32767>
    pulseWidth: 
      max: Number <from: -32767 to: 32767>
      min: Number <from: -32767 to: 32767>
    velocity: Number <from: 0 to: 65536>
   statusLED: Boolean <true,false> 
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/SolidState/U/<id>/I
   monoflopParameters: 
     period: Number <from: 0 to: 9223372036854775807>
     state: Boolean <true,false> 
   state: Boolean <true,false> 
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/SoundIntensity/U/<id>/I
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   intensityCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   intensityCallbackThreshold: 
     max: Number <from: 0 to: 10000>
     min: Number <from: 0 to: 10000>
     option: String <[x, o, i, <, >]>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/Temperature/U/<id>/I
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   mode: 
     Mode: String <Fast,Slow>
   resistanceCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   temperatureCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   temperatureThreshold: 
     max: Number <from: -2500 to: 8500>
     min: Number <from: -2500 to: 8500>
     option: String <[x, o, i, <, >]>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/TemperatureIR/U/<id>/I
   ambientTemperatureCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   ambientTemperatureCallbackThreshold: 
     max: Number <from: -400 to: 1250>
     min: Number <from: -400 to: 1250>
     option: String <[x, o, i, <, >]>
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   objectTemperatureCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   objectTemperatureCallbackThreshold: 
     max: Number <from: -700 to: 3800>
     min: Number <from: -700 to: 3800>
     option: String <[x, o, i, <, >]>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/ThermoCouple/U/<id>/I
   configuration: 
     Averaging: String <sample_1,sample_2,sample_4,sample_8,sample_16>
     Filter: String <Hz_50,Hz_60>
     Type: String <B,E,J,K,N,R,S,T,G8,G32>
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   temperatureCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   temperatureCallbackThreshold: 
     max: Number <from: -21000 to: 180000>
     min: Number <from: -21000 to: 180000>
     option: String <[x, o, i, <, >]>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/Tilt/U/<id>/I
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/UVLight/U/<id>/I
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   movingAverage: Number <from: 0 to: 100>
   uvLightCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   uvLightCallbackThreshold: 
     max: Number <from: 0 to: 328000>
     min: Number <from: 0 to: 328000>
     option: String <[x, o, i, <, >]>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
TF/VoltageCurrent/U/<id>/I
   calibration: 
     gainDivisor: Number <from: 1 to: 2147483647>
     gainMultiplier: Number <from: 1 to: 2147483647>
   configuration: 
     Averaging: String <AVERAGING_1,AVERAGING_4,AVERAGING_16,AVERAGING_64,AVERAGING_128,AVERAGING_256,AVERAGING_512,AVERAGING_1024>
     Conversion: String <CONVERSION_140us,CONVERSION_204us,CONVERSION_332us,CONVERSION_588us,CONVERSION_1100us,CONVERSION_2116us,CONVERSION_4156us,CONVERSION_8244us>
     Conversion: String <CONVERSION_140us,CONVERSION_204us,CONVERSION_332us,CONVERSION_588us,CONVERSION_1100us,CONVERSION_2116us,CONVERSION_4156us,CONVERSION_8244us>
   currentCalbackThreshold: 
     max: Number <from: 0 to: 36000>
     min: Number <from: 0 to: 36000>
     option: String <[x, o, i, <, >]>
   currentCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   powerCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   powerCallbackThreshold: 
     max: Number <from: 0 to: 720000>
     min: Number <from: 0 to: 720000>
     option: String <[x, o, i, <, >]>
   voltageCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   voltageCallbackThreshold: 
     max: Number <from: -5001 to: 5001>
     min: Number <from: -5001 to: 5001>
     option: String <[x, o, i, <, >]>
   
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
   String: <0,1,2,3,4,5,6,7,8,a,b,c,d>
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
    min: [-50001..50001]
    max: [-50001..50001]
```

