# TiMqWay
[Data-driven] [micro-service]s for the bricks and bricklets of the [Tinkerforge](tm) world, based on ch.quantasy.mqtt.gateway.
ch.quantasy.tinkerforge.mqtt.gateway


The underlying idea of TiMqWay is a set of micro-services with auto discovery and data definition using the mqtt protocol, providing a data-driven
interface to the known Tinkerforge devices. This way, the implementation is agnostic to the programming-language and paradigm used for orchestration. Any language will do, as long as you master it.


The programming paradigm is as follows: Minimum Requirements, Maximum Delivery
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
* **Event** Each micro-service instance provides all events via its specialized event topics.
* **Intent** Each micro-service instance accepts _intentions_ via the intent topic. It is equivalent to the setter methods but allows _parallel_ and _concurrent_ 'requests'.
As there might be more messages (i.e. Status / Events / Intents) available than the connection (mqtt broker plus network) is able to handle, the messages might be provided as an array of messages. No messige is lost!

**Root topic** The root topic of TiMqWay, where all Tinkerforge micro-services can be reached: **TF/**.

**Data language** TiMqWay's data language used is **[YAML]**.

**No Connection between services** The services do not know each other and cannot 'learn' from other services (i.e. they are cohesive). What is needed is a software
that orchestrates / choreographs the services. This can be written in any programming language (e.g. Node-Red, Java, js, Swift, ...), as long as you can access the mqtt-broker used.

## Installation
In order to run it, there are two ways: 
* **Users way** download the latest [TiMqWay.jar] and you are ready to control Tinkerforge via the MQTT-Gateway
* **Developers way** clone and build the project. Please note that it depends on [https://github.com/knr1/ch.quantasy.mqtt.gateway]


### Abstraction to Tinkerforge 
Each micro-service is an instance of a Tinkerforge-Device class, accessible by its UID. Hence, devices are not represented within their 'stack', but loosely coupled and class-wise so
 a device might even change its stack but is still accessible in MQTT at the same location (TF/device-class/UID).

 
## Usage
You need Java (7 or higher) and a running MQTT-Broker. You can start TiMqWay with the MQTT-Server-Parameter.

Then run the following command in order to use an MQTT-Broker which is already running on localhost
```sh
$ java -jar TiMqWay.jar tcp://127.0.0.1:1883
```
Or, if you do not have any MQTT-Broker ready, you can start it use an existing one at iot.eclipse.org:1883 (Not recommended as it is an open server everyone can read and write into)
```sh
$ java -jar TiMqWay.jar tcp://iot.eclipse.org:1883
```

Then, if you subscribe to TF/# you will immediately get the description info for the TiMqWay-Manager, which does all Tinkerforge stuff for you.

In order to interact with some specific Tinkerforge-Stack (e.g. the one you have connected via USB cable to the same machine as you run TiMqWay), the following message has to be sent to the following topic:
```
Topic
TF/Manager/U/computer/I

Message
address:
  hostName: localhost
connect: true
```
or if you know a remote Tinkerforge stack reachable over an IP-address (e.g. the one in your 192.168.1.x network connected via WLAN-Brick extension as 192.168.1.77)

```
Topic
TF/Manager/U/computer/I

Message
address:
  hostName: 192.168.1.77
connect: true
```

All connected Bricks/Bricklets should now be accessible via MQTT... Have fun.

 * [Quick shots](https://github.com/knr1/ch.quantasy.tinkerforge.mqtt.gateway#quick-shots)
 * [Example](https://github.com/knr1/ch.quantasy.tinkerforge.mqtt.gateway#example)
 * [API](https://github.com/knr1/ch.quantasy.tinkerforge.mqtt.gateway#api)

---

# Quick Shots
In the following, some one-liners are shown in order to demonstrate how easy it is to get information from TF.
In order to see who is calling, the intent will always end with '/quickshot' as the intending party (you can put anything (or nothing) there it helps in understanding who has sent an intent).
 
**Setting backlight of LCD-Display (uid: lcd):**
```
Topic
TF/LCD20x4/U/lcd/I/quickshot

Message
backlight: true
```

**Measuring the temperature once per second on temperature bricklet (uid: red):**
```
Topic
TF/Temperature/U/red/I/quickshot

Message
temperatureCallbackPeriod: 1000
```

**Detecting NFC-Tags once per second on NFCRFID bricklet (uid: ouu):**
```
Topic
TF/NfcRfid/U/ouu/I/quickshot

Message
scanningCallbackPeriod: 1000
```



## Tip
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
Topic
TF/Manager/U/pc/I

Message
address:
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
Topic
TF/LCD20x4/U/lcd/I

Message
backlight: true
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
Topic
TF/Manager/U/pc/I

Message
address:
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
Topic
TF/Temperature/U/red/I

Message
temperatureCallbackPeriod: 1000
```
Now, there is a reading every second, that will be promoted as an event to `TF/Temperature/red/E/temperature`

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
Topic
TF/LCD20x4/U/lcd/I

Message
lines: 
  - line: 0
    position: 0
    text: "RED: 22°C
  - line: 1
    position: 0
    text: "BLUE: 18°C"
```

## API
Intent (I) is as straight forward and as concise as possible. You simply put into the intent what you want and omit the rest.
Event (E) is as fine granular as possible, so you can subscribe to your needs and will receive nothing more
Status (S) is as fine granular as possible, so you can subscribe to your needs and will receive nothing more

No message is lost! It is delivered as fast as possible. If the channel is slower than the message creation, the messages will be delivered as an array of messages. 

### IMU
```
TF/IMU/U/<id>/E/acceleration
   timeStamp: Number <from: 0 to: 9223372036854775807>
   x: Number <from: -32768 to: 32767>
   y: Number <from: -32768 to: 32767>
   z: Number <from: -32768 to: 32767>
   
```
```
TF/IMU/U/<id>/E/allData
   acceleration: 
     timeStamp: Number <from: 0 to: 9223372036854775807>
     x: Number <from: -32768 to: 32767>
     y: Number <from: -32768 to: 32767>
     z: Number <from: -32768 to: 32767>
   angularVelocity: 
     timeStamp: Number <from: 0 to: 9223372036854775807>
     x: Number <from: -32768 to: 32767>
     y: Number <from: -32768 to: 32767>
     z: Number <from: -32768 to: 32767>
   magneticField: 
     timeStamp: Number <from: 0 to: 9223372036854775807>
     x: Number <from: -32768 to: 32767>
     y: Number <from: -32768 to: 32767>
     z: Number <from: -32768 to: 32767>
   temperature: 
     temperature: Number <from: -32768 to: 32767>
     timeStamp: Number <from: 0 to: 9223372036854775807>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMU/U/<id>/E/angularVelocity
   timeStamp: Number <from: 0 to: 9223372036854775807>
   x: Number <from: -32768 to: 32767>
   y: Number <from: -32768 to: 32767>
   z: Number <from: -32768 to: 32767>
   
```
```
TF/IMU/U/<id>/E/magneticField
   timeStamp: Number <from: 0 to: 9223372036854775807>
   x: Number <from: -32768 to: 32767>
   y: Number <from: -32768 to: 32767>
   z: Number <from: -32768 to: 32767>
   
```
```
TF/IMU/U/<id>/E/orientation
   heading: Number <from: -32768 to: 32767>
   pitch: Number <from: -32768 to: 32767>
   roll: Number <from: -32768 to: 32767>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMU/U/<id>/E/quaternion
   timeStamp: Number <from: 0 to: 9223372036854775807>
   w: Fraction <from: -1.0 to: 1.0>
   x: Fraction <from: -1.0 to: 1.0>
   y: Fraction <from: -1.0 to: 1.0>
   z: Fraction <from: -1.0 to: 1.0>
   
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
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMU/U/<id>/S/LEDs/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Boolean <true,false> 
   
```
```
TF/IMU/U/<id>/S/acceleration/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMU/U/<id>/S/allData/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMU/U/<id>/S/angularVelocity/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMU/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/IMU/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/IMU/U/<id>/S/magneticField/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMU/U/<id>/S/orientation/calculation
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Boolean <true,false> 
   
```
```
TF/IMU/U/<id>/S/orientation/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMU/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/IMU/U/<id>/S/quaternion/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMU/U/<id>/S/statusLED/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Boolean <true,false> 
   
```

### IMUV2
```
TF/IMUV2/U/<id>/E/acceleration
   timeStamp: Number <from: 0 to: 9223372036854775807>
   x: Number <from: -32768 to: 32767>
   y: Number <from: -32768 to: 32767>
   z: Number <from: -32768 to: 32767>
   
```
```
TF/IMUV2/U/<id>/E/allData
   acceleration: 
     timeStamp: Number <from: 0 to: 9223372036854775807>
     x: Number <from: -32768 to: 32767>
     y: Number <from: -32768 to: 32767>
     z: Number <from: -32768 to: 32767>
   angularVelocity: 
     timeStamp: Number <from: 0 to: 9223372036854775807>
     x: Number <from: -32768 to: 32767>
     y: Number <from: -32768 to: 32767>
     z: Number <from: -32768 to: 32767>
   gravityVector: 
     timeStamp: Number <from: 0 to: 9223372036854775807>
     x: Number <from: -32768 to: 32767>
     y: Number <from: -32768 to: 32767>
     z: Number <from: -32768 to: 32767>
   linearAcceleration: 
     timeStamp: Number <from: 0 to: 9223372036854775807>
     x: Number <from: -32768 to: 32767>
     y: Number <from: -32768 to: 32767>
     z: Number <from: -32768 to: 32767>
   magneticField: 
     timeStamp: Number <from: 0 to: 9223372036854775807>
     x: Number <from: -32768 to: 32767>
     y: Number <from: -32768 to: 32767>
     z: Number <from: -32768 to: 32767>
   quaternion: 
     timeStamp: Number <from: 0 to: 9223372036854775807>
     w: Fraction <from: -1.0 to: 1.0>
     x: Fraction <from: -1.0 to: 1.0>
     y: Fraction <from: -1.0 to: 1.0>
     z: Fraction <from: -1.0 to: 1.0>
   temperature: 
     temperature: Number <from: -32768 to: 32767>
     timeStamp: Number <from: 0 to: 9223372036854775807>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMUV2/U/<id>/E/angularVelocity
   timeStamp: Number <from: 0 to: 9223372036854775807>
   x: Number <from: -32768 to: 32767>
   y: Number <from: -32768 to: 32767>
   z: Number <from: -32768 to: 32767>
   
```
```
TF/IMUV2/U/<id>/E/gravityVector
   timeStamp: Number <from: 0 to: 9223372036854775807>
   x: Number <from: -32768 to: 32767>
   y: Number <from: -32768 to: 32767>
   z: Number <from: -32768 to: 32767>
   
```
```
TF/IMUV2/U/<id>/E/linearAcceleration
   timeStamp: Number <from: 0 to: 9223372036854775807>
   x: Number <from: -32768 to: 32767>
   y: Number <from: -32768 to: 32767>
   z: Number <from: -32768 to: 32767>
   
```
```
TF/IMUV2/U/<id>/E/magneticField
   timeStamp: Number <from: 0 to: 9223372036854775807>
   x: Number <from: -32768 to: 32767>
   y: Number <from: -32768 to: 32767>
   z: Number <from: -32768 to: 32767>
   
```
```
TF/IMUV2/U/<id>/E/orientation
   heading: Number <from: -32768 to: 32767>
   pitch: Number <from: -32768 to: 32767>
   roll: Number <from: -32768 to: 32767>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMUV2/U/<id>/E/quaternion
   timeStamp: Number <from: 0 to: 9223372036854775807>
   w: Fraction <from: -1.0 to: 1.0>
   x: Fraction <from: -1.0 to: 1.0>
   y: Fraction <from: -1.0 to: 1.0>
   z: Fraction <from: -1.0 to: 1.0>
   
```
```
TF/IMUV2/U/<id>/E/temperature
   temperature: Number <from: -32768 to: 32767>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMUV2/U/<id>/I
   accelerationCallbackPeriod: null
   accelerationCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   allDataCallbackPeriod: null
   allDataCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   angularVelocityCallbackPeriod: null
   angularVelocityCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   gravityVectorCallbackPeriod: null
   gravityVectorCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   leds: null
   leds: Boolean <true,false> 
   linearAccelerationCallbackPeriod: null
   linearAccelerationCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   magneticFieldCallbackPeriod: null
   magneticFieldCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   orientationCalculation: null
   orientationCalculation: Boolean <true,false> 
   orientationCallbackPeriod: null
   orientationCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   quaternionCallbackPeriod: null
   quaternionCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   sensorFusionMode: null
   sensorFusionMode: String <[0, 1, 2]>
   statusLED: null
   statusLED: Boolean <true,false> 
   temperatureCallbackPeriod: null
   temperatureCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMUV2/U/<id>/S/LEDs/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Boolean <true,false> 
   
```
```
TF/IMUV2/U/<id>/S/acceleration/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMUV2/U/<id>/S/allData/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMUV2/U/<id>/S/angularVelocity/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMUV2/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/IMUV2/U/<id>/S/gravityVector/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMUV2/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/IMUV2/U/<id>/S/linearAcceleration/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMUV2/U/<id>/S/magneticField/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMUV2/U/<id>/S/orientation/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMUV2/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/IMUV2/U/<id>/S/quaternion/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMUV2/U/<id>/S/sensorFusionMode
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2]>
   
```
```
TF/IMUV2/U/<id>/S/statusLED
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Boolean <true,false> 
   
```
```
TF/IMUV2/U/<id>/S/temperature/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```

### LCD16x2
```
TF/LCD16x2/U/<id>/E/button/pressed
   button: Number <from: 0 to: 2>
   pressed: Boolean <true,false> 
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/LCD16x2/U/<id>/E/button/released
   button: Number <from: 0 to: 2>
   pressed: Boolean <true,false> 
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/LCD16x2/U/<id>/I
   backlight: null
   backlight: Boolean <true,false> 
   clearDisplay: null
   clearDisplay: Boolean <true,false> 
   customCharacters: null
   customCharacters: Set <min: 0 max: 8>
   customCharacters: 
    index: Number <from: 0 to: 7>
    pixels: Array <min: 8 max: 8>
   lines: null
   lines: Set <min: 0 max: 32>
   lines: 
    line: Number <from: 0 to: 1>
    position: Number <from: 0 to: 15>
    text: String <min: 0 max: 16>
   parameters: null
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/LCD16x2/U/<id>/S/backlight
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Boolean <true,false> 
   
```
```
TF/LCD16x2/U/<id>/S/configParameters
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/LCD16x2/U/<id>/S/customCharacters
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Set <min: 0 max: 8>
   value: 
    index: Number <from: 0 to: 7>
    pixels: Array <min: 8 max: 8>
   
```
```
TF/LCD16x2/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/LCD16x2/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/LCD16x2/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### LCD20x4
```
TF/LCD20x4/U/<id>/E/button
   button: Number <from: 0 to: 3>
   pressed: Boolean <true,false> 
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/LCD20x4/U/<id>/I
   backlight: null
   backlight: Boolean <true,false> 
   clearDisplay: null
   clearDisplay: Boolean <true,false> 
   customCharacters: null
   customCharacters: Set <min: 0 max: 8>
   customCharacters: 
    index: Number <from: 0 to: 7>
    pixels: Array <min: 8 max: 8>
   defaultTextCounter: null
   defaultTextCounter: Number <from: -1 to: 2147483647>
   defaultTexts: null
   defaultTexts: Set <min: 0 max: 4>
   defaultTexts: 
    line: Number <from: 0 to: 3>
    text: String <min: 0 max: 20>
   lines: null
   lines: Set <min: 0 max: 80>
   lines: 
    line: Number <from: 0 to: 3>
    position: Number <from: 0 to: 19>
    text: String <min: 0 max: 20>
   parameters: null
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/LCD20x4/U/<id>/S/backlight
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Boolean <true,false> 
   
```
```
TF/LCD20x4/U/<id>/S/configParameters
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/LCD20x4/U/<id>/S/customCharacters
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Set <min: 0 max: 8>
   value: 
    index: Number <from: 0 to: 7>
    pixels: Array <min: 8 max: 8>
   
```
```
TF/LCD20x4/U/<id>/S/defaultText/counter
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: -1 to: 2147483647>
   
```
```
TF/LCD20x4/U/<id>/S/defaultText/texts
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Set <min: 0 max: 4>
   value: 
    line: Number <from: 0 to: 3>
    text: String <min: 0 max: 20>
   
```
```
TF/LCD20x4/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/LCD20x4/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/LCD20x4/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### RGBLEDButton
```
TF/RGBLEDButton/U/<id>/E/button
   state: String <PRESSED,RELEASED>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/RGBLEDButton/U/<id>/I
   color: null
   color: 
     blue: Number <from: 0 to: 255>
     green: Number <from: 0 to: 255>
     red: Number <from: 0 to: 255>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/RGBLEDButton/U/<id>/S/color
   color: 
     blue: Number <from: 0 to: 255>
     green: Number <from: 0 to: 255>
     red: Number <from: 0 to: 255>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/RGBLEDButton/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/RGBLEDButton/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/RGBLEDButton/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### <id>
```
TF/<id>/U/Accelerometer/E/acceleration
   timeStamp: Number <from: 0 to: 9223372036854775807>
   x: Number <from: -32768 to: 32767>
   y: Number <from: -32768 to: 32767>
   z: Number <from: -32768 to: 32767>
   
```
```
TF/<id>/U/Accelerometer/E/accelerationReached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   x: Number <from: -32768 to: 32767>
   y: Number <from: -32768 to: 32767>
   z: Number <from: -32768 to: 32767>
   
```
```
TF/<id>/U/Accelerometer/I
   accelerationThreshold: null
   accelerationThreshold: 
     option: String <[x, o, i, <, >]>
   callbackPeriod: null
   callbackPeriod: Number <from: 0 to: 9223372036854775807>
   configuration: null
   configuration: 
     dataRate: String <OFF,Hz3,Hz6,Hz12,Hz25,Hz50,Hz100,Hz400,Hz800,Hz1600>
     filterBandwidth: String <Hz800,Hz400,Hz200,Hz50>
     fullScale: String <G2,G4,G6,G8,G16>
   debouncePeriod: null
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/<id>/U/Accelerometer/S/acceleration/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/<id>/U/Accelerometer/S/acceleration/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     option: String <[x, o, i, <, >]>
   
```
```
TF/<id>/U/Accelerometer/S/configuration
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     dataRate: String <OFF,Hz3,Hz6,Hz12,Hz25,Hz50,Hz100,Hz400,Hz800,Hz1600>
     filterBandwidth: String <Hz800,Hz400,Hz200,Hz50>
     fullScale: String <G2,G4,G6,G8,G16>
   
```
```
TF/<id>/U/Accelerometer/S/debounce/period
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/<id>/U/Accelerometer/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/<id>/U/Accelerometer/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/<id>/U/Accelerometer/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### AmbientLight
```
TF/AmbientLight/U/<id>/E/analogValue
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 4095>
   
```
```
TF/AmbientLight/U/<id>/E/analogValue/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 4095>
   
```
```
TF/AmbientLight/U/<id>/E/illuminance
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9000>
   
```
```
TF/AmbientLight/U/<id>/E/illuminance/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9000>
   
```
```
TF/AmbientLight/U/<id>/I
   analogCallbackPeriod: null
   analogCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   analogValueThreshold: null
   analogValueThreshold: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   debouncePeriod: null
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   illuminanceCallbackPeriod: null
   illuminanceCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   illuminanceThreshold: null
   illuminanceThreshold: 
     max: Number <from: 0 to: 9000>
     min: Number <from: 0 to: 9000>
     option: String <[x, o, i, <, >]>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/AmbientLight/U/<id>/S/analogValue/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/AmbientLight/U/<id>/S/analogValue/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   
```
```
TF/AmbientLight/U/<id>/S/debounce/period
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/AmbientLight/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/AmbientLight/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/AmbientLight/U/<id>/S/illuminance/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/AmbientLight/U/<id>/S/illuminance/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/AmbientLight/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### AmbientLightV2
```
TF/AmbientLightV2/U/<id>/E/illuminance
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9000>
   
```
```
TF/AmbientLightV2/U/<id>/E/illuminance/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9000>
   
```
```
TF/AmbientLightV2/U/<id>/I
   configuration: null
   configuration: 
     illuminanceRange: String <lx_unlimitted,lx_64000,lx_32000,lx_16000,lx_8000,lx_1300,lx_600>
     integrationTime: String <ms_50,ms_100,ms_150,ms_200,ms_250,ms_300,ms_350,ms_400>
   debouncePeriod: null
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   illuminanceCallbackPeriod: null
   illuminanceCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   illuminanceCallbackThreshold: null
   illuminanceCallbackThreshold: 
     max: Number <from: 0 to: 100000>
     min: Number <from: 0 to: 100000>
     option: String <[x, o, i, <, >]>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/AmbientLightV2/U/<id>/S/configuration
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     illuminanceRange: String <lx_unlimitted,lx_64000,lx_32000,lx_16000,lx_8000,lx_1300,lx_600>
     integrationTime: String <ms_50,ms_100,ms_150,ms_200,ms_250,ms_300,ms_350,ms_400>
   
```
```
TF/AmbientLightV2/U/<id>/S/debounce/period
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/AmbientLightV2/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/AmbientLightV2/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/AmbientLightV2/U/<id>/S/illuminance/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/AmbientLightV2/U/<id>/S/illuminance/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: 0 to: 100000>
     min: Number <from: 0 to: 100000>
     option: String <[x, o, i, <, >]>
   
```
```
TF/AmbientLightV2/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### AnalogInV2
```
TF/AnalogInV2/U/<id>/E/analogValue
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 4095>
   
```
```
TF/AnalogInV2/U/<id>/E/analogValue/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 4095>
   
```
```
TF/AnalogInV2/U/<id>/E/voltage
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: -2147483648 to: 2147483647>
   
```
```
TF/AnalogInV2/U/<id>/E/voltage/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: -2147483648 to: 2147483647>
   
```
```
TF/AnalogInV2/U/<id>/I
   analogValueCallbackPeriod: null
   analogValueCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   analogValueCallbackThreshold: null
   analogValueCallbackThreshold: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   debouncePeriod: null
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   movingAverage: null
   movingAverage: Number <from: 0 to: 9223372036854775807>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   voltageCallbackPeriod: null
   voltageCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   voltageCallbackThreshold: null
   voltageCallbackThreshold: 
     max: Number <from: 0 to: 42000>
     min: Number <from: 0 to: 42000>
     option: String <[x, o, i, <, >]>
   
```
```
TF/AnalogInV2/U/<id>/S/analogValue/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/AnalogInV2/U/<id>/S/analogValue/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   
```
```
TF/AnalogInV2/U/<id>/S/debounce/period
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/AnalogInV2/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/AnalogInV2/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/AnalogInV2/U/<id>/S/movingAverage
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 1 to: 40>
   
```
```
TF/AnalogInV2/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/AnalogInV2/U/<id>/S/voltage/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/AnalogInV2/U/<id>/S/voltage/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: 0 to: 42000>
     min: Number <from: 0 to: 42000>
     option: String <[x, o, i, <, >]>
   
```

### AnalogOutV2
```
TF/AnalogOutV2/U/<id>/I
   outputVoltage: null
   outputVoltage: Number <from: 0 to: 12000>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/AnalogOutV2/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/AnalogOutV2/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/AnalogOutV2/U/<id>/S/outputVoltage
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 12000>
   
```
```
TF/AnalogOutV2/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### Barometer
```
TF/Barometer/U/<id>/E/airPressure
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 10000 to: 1200000>
   
```
```
TF/Barometer/U/<id>/E/airPressure/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 10000 to: 1200000>
   
```
```
TF/Barometer/U/<id>/E/altitude
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: -2147483648 to: 2147483647>
   
```
```
TF/Barometer/U/<id>/E/altitude/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: -2147483648 to: 2147483647>
   
```
```
TF/Barometer/U/<id>/I
   airPressureCallbackPeriod: null
   airPressureCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   airPressureCallbackThreshold: null
   airPressureCallbackThreshold: 
     max: Number <from: 10000 to: 1200000>
     min: Number <from: 10000 to: 1200000>
     option: String <[x, o, i, <, >]>
   altitudeCallbackPeriod: null
   altitudeCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   altitudeCallbackThreshold: null
   altitudeCallbackThreshold: 
     option: String <[x, o, i, <, >]>
   averaging: null
   averaging: 
     averagingPressure: Number <from: 0 to: 10>
     averagingTemperature: Number <from: 0 to: 255>
     movingAveragePressure: Number <from: 0 to: 25>
   debouncePeriod: null
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   referenceAirPressure: null
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Barometer/U/<id>/S/airPressure/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Barometer/U/<id>/S/airPressure/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: 10000 to: 1200000>
     min: Number <from: 10000 to: 1200000>
     option: String <[x, o, i, <, >]>
   
```
```
TF/Barometer/U/<id>/S/altitude/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Barometer/U/<id>/S/altitude/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     option: String <[x, o, i, <, >]>
   
```
```
TF/Barometer/U/<id>/S/averaging
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     averagingPressure: Number <from: 0 to: 10>
     averagingTemperature: Number <from: 0 to: 255>
     movingAveragePressure: Number <from: 0 to: 25>
   
```
```
TF/Barometer/U/<id>/S/debounce/period
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Barometer/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/Barometer/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/Barometer/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/Barometer/U/<id>/S/referenceAirPressure
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```

### CO2
```
TF/CO2/U/<id>/E/CO2Concentration
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 10000>
   
```
```
TF/CO2/U/<id>/E/CO2Concentration/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 10000>
   
```
```
TF/CO2/U/<id>/I
   co2ConcentrationCallbackPeriod: null
   co2ConcentrationCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   co2ConcentrationCallbackThreshold: null
   co2ConcentrationCallbackThreshold: 
     max: Number <from: 0 to: 10000>
     min: Number <from: 0 to: 10000>
     option: String <[x, o, i, <, >]>
   debouncePeriod: null
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/CO2/U/<id>/S/CO2Concentration/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/CO2/U/<id>/S/CO2Concentration/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: 0 to: 10000>
     min: Number <from: 0 to: 10000>
     option: String <[x, o, i, <, >]>
   
```
```
TF/CO2/U/<id>/S/debounce/period
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/CO2/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/CO2/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/CO2/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### Color
```
TF/Color/U/<id>/E/color
   blue: Number <from: 0 to: 65535>
   clear: Number <from: 0 to: 65535>
   green: Number <from: 0 to: 65535>
   red: Number <from: 0 to: 65535>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Color/U/<id>/E/color/reached
   blue: Number <from: 0 to: 65535>
   clear: Number <from: 0 to: 65535>
   green: Number <from: 0 to: 65535>
   red: Number <from: 0 to: 65535>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Color/U/<id>/E/illuminance
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 65535>
   
```
```
TF/Color/U/<id>/E/illuminance/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 65535>
   
```
```
TF/Color/U/<id>/I
   colorCallbackPeriod: null
   colorCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   colorCallbackThreshold: null
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
   colorTemperatureCallbackPeriod: null
   colorTemperatureCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   config: null
   config: 
     gain: String <x1,x4,x16,Hz60>
     integrationTime: String <ms2_4,ms24,ms101,ms154,ms700>
   debouncePeriod: null
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   illuminanceCallbackPeriod: null
   illuminanceCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   light: null
   light: Boolean <true,false> 
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Color/U/<id>/S/color/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Color/U/<id>/S/color/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     maxB: Number <from: 0 to: 65535>
     maxC: Number <from: 0 to: 65535>
     maxG: Number <from: 0 to: 65535>
     maxR: Number <from: 0 to: 65535>
     minB: Number <from: 0 to: 65535>
     minC: Number <from: 0 to: 65535>
     minG: Number <from: 0 to: 65535>
     minR: Number <from: 0 to: 65535>
     option: String <[x, o, i, <, >]>
   
```
```
TF/Color/U/<id>/S/debounce/period
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Color/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/Color/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/Color/U/<id>/S/illuminance/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Color/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### DC
```
TF/DC/U/<id>/E/emergencyShutdown
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/DC/U/<id>/E/fullBrake
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/DC/U/<id>/E/undervoltage
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 2147483647>
   
```
```
TF/DC/U/<id>/E/velocity
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 32767>
   
```
```
TF/DC/U/<id>/E/velocity/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 32767>
   
```
```
TF/DC/U/<id>/I
   acceleration: null
   acceleration: Number <from: 0 to: 2147483647>
   driveMode: null
   driveMode: String <[1, 2]>
   enable: null
   enable: Boolean <true,false> 
   fullBrake: null
   fullBrake: Boolean <true,false> 
   minimumVoltage: null
   minimumVoltage: Number <from: 6000 to: 2147483647>
   pwmFrequency: null
   pwmFrequency: Number <from: 1 to: 20000>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   velocity: null
   velocity: Number <from: -32767 to: 32767>
   velocityPeriod: null
   velocityPeriod: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/DC/U/<id>/S/acceleration
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 2147483647>
   
```
```
TF/DC/U/<id>/S/driverMode
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[1, 2]>
   
```
```
TF/DC/U/<id>/S/enabled
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Boolean <true,false> 
   
```
```
TF/DC/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/DC/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/DC/U/<id>/S/minimumVoltage
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 6000 to: 2147483647>
   
```
```
TF/DC/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/DC/U/<id>/S/pwmFrequency
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 1 to: 20000>
   
```
```
TF/DC/U/<id>/S/velocity/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/DC/U/<id>/S/velocity/velocity
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: -32767 to: 32767>
   
```

### DistanceIR
```
TF/DistanceIR/U/<id>/E/analogValue
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 4095>
   
```
```
TF/DistanceIR/U/<id>/E/analogValue/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 4095>
   
```
```
TF/DistanceIR/U/<id>/E/distance
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <[from: 40 to: 300,from: 100 to: 800,from: 200 to: 1500]>
   
```
```
TF/DistanceIR/U/<id>/E/distance/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <[from: 40 to: 300,from: 100 to: 800,from: 200 to: 1500]>
   
```
```
TF/DistanceIR/U/<id>/I
   analogCallbackPeriod: null
   analogCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   analogValueCallbackThreshold: null
   analogValueCallbackThreshold: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   debouncePeriod: null
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   distanceCallbackPeriod: null
   distanceCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   distanceCallbackThreshold: null
   distanceCallbackThreshold: 
     max: Number <[from: 0 to: 0,from: 40 to: 300,from: 100 to: 800,from: 200 to: 1500]>
     min: Number <[from: 0 to: 0,from: 40 to: 300,from: 100 to: 800,from: 200 to: 1500]>
     option: String <[x, o, i, <, >]>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/DistanceIR/U/<id>/S/analogValue/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/DistanceIR/U/<id>/S/analogValue/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   
```
```
TF/DistanceIR/U/<id>/S/debounce/period
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/DistanceIR/U/<id>/S/distance/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/DistanceIR/U/<id>/S/distance/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <[from: 0 to: 0,from: 40 to: 300,from: 100 to: 800,from: 200 to: 1500]>
     min: Number <[from: 0 to: 0,from: 40 to: 300,from: 100 to: 800,from: 200 to: 1500]>
     option: String <[x, o, i, <, >]>
   
```
```
TF/DistanceIR/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/DistanceIR/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/DistanceIR/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### DistanceUS
```
TF/DistanceUS/U/<id>/E/distance
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 4095>
   
```
```
TF/DistanceUS/U/<id>/E/distance/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 4095>
   
```
```
TF/DistanceUS/U/<id>/I
   debouncePeriod: null
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   distanceCallbackPeriod: null
   distanceCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   distanceCallbackThreshold: null
   distanceCallbackThreshold: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   movingAverage: null
   movingAverage: Number <from: 0 to: 100>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/DistanceUS/U/<id>/S/debounce/period
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/DistanceUS/U/<id>/S/distance/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/DistanceUS/U/<id>/S/distance/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   
```
```
TF/DistanceUS/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/DistanceUS/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/DistanceUS/U/<id>/S/movingAverage
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 100>
   
```
```
TF/DistanceUS/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### DualButton
```
TF/DualButton/U/<id>/E/stateChanged
   led1: String <AutoToggleOn,AutoToggleOff,On,Off>
   led2: String <AutoToggleOn,AutoToggleOff,On,Off>
   switch1: String <[0, 1]>
   switch2: String <[0, 1]>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/DualButton/U/<id>/I
   ledState: null
   ledState: 
     led1: String <AutoToggleOn,AutoToggleOff,On,Off>
     led2: String <AutoToggleOn,AutoToggleOff,On,Off>
   selectedLEDStates: null
   selectedLEDStates: Set <min: 0 max: 2>
   selectedLEDStates: 
    led: String <[1, 2]>
    state: String <AutoToggleOn,AutoToggleOff,On,Off>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/DualButton/U/<id>/S/LEDState
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     led1: String <AutoToggleOn,AutoToggleOff,On,Off>
     led2: String <AutoToggleOn,AutoToggleOff,On,Off>
   
```
```
TF/DualButton/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/DualButton/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/DualButton/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### DualRelay
```
TF/DualRelay/U/<id>/E/monoflopDone
   relay: String <[1, 2]>
   state: Boolean <true,false> 
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/DualRelay/U/<id>/I
   monoflopParameters: null
   monoflopParameters: Set <min: 0 max: 2>
   monoflopParameters: 
    period: Number <from: 0 to: 9223372036854775807>
    relay: String <[1, 2]>
    state: Boolean <true,false> 
   relayState: null
   relayState: 
     relay1: Boolean <true,false> 
     relay2: Boolean <true,false> 
   selectedRelayStates: null
   selectedRelayStates: Set <min: 0 max: 2>
   selectedRelayStates: 
    relay: String <[1, 2]>
    state: Boolean <true,false> 
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/DualRelay/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/DualRelay/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/DualRelay/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/DualRelay/U/<id>/S/state
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     relay1: Boolean <true,false> 
     relay2: Boolean <true,false> 
   
```

### DustDetector
```
TF/DustDetector/U/<id>/E/dustDensity
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 500>
   
```
```
TF/DustDetector/U/<id>/E/dustDensity/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 500>
   
```
```
TF/DustDetector/U/<id>/I
   debouncePeriod: null
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   dustDensityCallbackPeriod: null
   dustDensityCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   dustDensityCallbackThreshold: null
   dustDensityCallbackThreshold: 
     max: Number <from: 0 to: 500>
     min: Number <from: 0 to: 500>
     option: String <[x, o, i, <, >]>
   movingAverage: null
   movingAverage: Number <from: 0 to: 100>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/DustDetector/U/<id>/S/debounce/period
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/DustDetector/U/<id>/S/dustDensity/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/DustDetector/U/<id>/S/dustDensity/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: 0 to: 500>
     min: Number <from: 0 to: 500>
     option: String <[x, o, i, <, >]>
   
```
```
TF/DustDetector/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/DustDetector/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/DustDetector/U/<id>/S/movingAverage
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 100>
   
```
```
TF/DustDetector/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### GPS
```
TF/GPS/U/<id>/E/altitude
   altitude: Number <from: -2147483648 to: 2147483647>
   geoidalSeparation: Number <from: -2147483648 to: 2147483647>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/GPS/U/<id>/E/coordinates
   epe: Number <from: -2147483648 to: 2147483647>
   ew: String <[E, W]>
   hdop: Number <from: -2147483648 to: 2147483647>
   latitude: Number <from: -9223372036854775808 to: 9223372036854775807>
   longitude: Number <from: -9223372036854775808 to: 9223372036854775807>
   ns: String <[N, S]>
   pdop: Number <from: -2147483648 to: 2147483647>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   vdop: Number <from: -2147483648 to: 2147483647>
   
```
```
TF/GPS/U/<id>/E/dateTime
   date: Number <from: 100 to: 311299>
   time: Number <from: 0 to: 235959999>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/GPS/U/<id>/E/motion
   course: Number <from: 0 to: 36000>
   speed: Number <from: 0 to: 9223372036854775807>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/GPS/U/<id>/E/status
   fix: String <[1, 2, 3]>
   satellitesUsed: Number <from: 0 to: 32767>
   satellitesView: Number <from: 0 to: 32767>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/GPS/U/<id>/I
   altitudeCallbackPeriod: null
   altitudeCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   coordinatesCallbackPeriod: null
   coordinatesCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   dateTimeCallbackPeriod: null
   dateTimeCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   motionCallbackPeriod: null
   motionCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   restart: null
   restart: String <hot,warm,cold,factoryReset>
   statusCallbackPeriod: null
   statusCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/GPS/U/<id>/S/altitude/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/GPS/U/<id>/S/coordinates/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/GPS/U/<id>/S/dateTime/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/GPS/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/GPS/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/GPS/U/<id>/S/motion/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/GPS/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### GPSv2
```
TF/GPSv2/U/<id>/E/altitude
   altitude: Number <from: -2147483648 to: 2147483647>
   geoidalSeparation: Number <from: -2147483648 to: 2147483647>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/GPSv2/U/<id>/E/coordinates
   ew: String <[E, W]>
   latitude: Number <from: -9223372036854775808 to: 9223372036854775807>
   longitude: Number <from: -9223372036854775808 to: 9223372036854775807>
   ns: String <[N, S]>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/GPSv2/U/<id>/E/dateTime
   date: Number <from: 100 to: 311299>
   time: Number <from: 0 to: 235959999>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/GPSv2/U/<id>/E/motion
   course: Number <from: 0 to: 36000>
   speed: Number <from: 0 to: 9223372036854775807>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/GPSv2/U/<id>/E/status
   fix: Boolean <true,false> 
   satellitesView: Number <from: 0 to: 32767>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/GPSv2/U/<id>/I
   altitudeCallbackPeriod: null
   altitudeCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   coordinatesCallbackPeriod: null
   coordinatesCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   dateTimeCallbackPeriod: null
   dateTimeCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   fixLEDConfig: null
   fixLEDConfig: String <OFF,ON,HEARTBEAT,FIX,PPS>
   motionCallbackPeriod: null
   motionCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   restart: null
   restart: String <hot,warm,cold,factoryReset>
   statusCallbackPeriod: null
   statusCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   statusLEDConfig: null
   statusLEDConfig: String <OFF,ON,HEARTBEAT,STATUS>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/GPSv2/U/<id>/S/altitude/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/GPSv2/U/<id>/S/coordinates/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/GPSv2/U/<id>/S/dateTime/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/GPSv2/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/GPSv2/U/<id>/S/fix/led
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <OFF,ON,HEARTBEAT,FIX,PPS>
   
```
```
TF/GPSv2/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/GPSv2/U/<id>/S/motion/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/GPSv2/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/GPSv2/U/<id>/S/status/led
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <OFF,ON,HEARTBEAT,STATUS>
   
```

### HallEffect
```
TF/HallEffect/U/<id>/E/edgeCount
   count: Number <from: 0 to: 9223372036854775807>
   greater35Gauss: null
   greater35Gauss: Boolean <true,false> 
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/HallEffect/U/<id>/E/edgeCount/reset
   count: Number <from: 0 to: 9223372036854775807>
   greater35Gauss: null
   greater35Gauss: Boolean <true,false> 
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/HallEffect/U/<id>/I
   edgeCountCallbackPeriod: null
   edgeCountCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   edgeCountConfiguration: null
   edgeCountInterrupt: null
   edgeCountInterrupt: Number <from: 0 to: 9223372036854775807>
   edgeCountReset: null
   edgeCountReset: Boolean <true,false> 
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/HallEffect/U/<id>/S/configuration
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/HallEffect/U/<id>/S/edgeCount/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/HallEffect/U/<id>/S/edgeCount/interrupt
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/HallEffect/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/HallEffect/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/HallEffect/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### Humidity
```
TF/Humidity/U/<id>/E/analogValue
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 4095>
   
```
```
TF/Humidity/U/<id>/E/analogValue/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 4095>
   
```
```
TF/Humidity/U/<id>/E/humidity
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 1000>
   
```
```
TF/Humidity/U/<id>/E/humidity/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 1000>
   
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
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Humidity/U/<id>/S/analogValue/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Humidity/U/<id>/S/analogValue/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   
```
```
TF/Humidity/U/<id>/S/debounce/period
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Humidity/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/Humidity/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/Humidity/U/<id>/S/humidity/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Humidity/U/<id>/S/humidity/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: 0 to: 1000>
     min: Number <from: 0 to: 1000>
     option: String <[x, o, i, <, >]>
   
```
```
TF/Humidity/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### Joystick
```
TF/Joystick/U/<id>/E/analogValue
   timeStamp: Number <from: 0 to: 9223372036854775807>
   x: Number <from: 0 to: 4095>
   y: Number <from: 0 to: 4095>
   
```
```
TF/Joystick/U/<id>/E/analogValue/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   x: Number <from: 0 to: 4095>
   y: Number <from: 0 to: 4095>
   
```
```
TF/Joystick/U/<id>/E/calibrate
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Boolean <true,false> 
   
```
```
TF/Joystick/U/<id>/E/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   x: Number <from: -100 to: 100>
   y: Number <from: -100 to: 100>
   
```
```
TF/Joystick/U/<id>/E/position/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   x: Number <from: -100 to: 100>
   y: Number <from: -100 to: 100>
   
```
```
TF/Joystick/U/<id>/E/pressed
   pressed: Boolean <true,false> 
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Joystick/U/<id>/E/released
   pressed: Boolean <true,false> 
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Joystick/U/<id>/I
   analogCallbackPeriod: null
   analogCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   analogValueCallbackThreshold: null
   analogValueCallbackThreshold: 
     maxX: Number <from: 0 to: 4095>
     maxY: Number <from: 0 to: 4095>
     minX: Number <from: 0 to: 4095>
     minY: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   calibrate: null
   calibrate: Boolean <true,false> 
   debouncePeriod: null
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   positionCallbackPeriod: null
   positionCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   positionCallbackThreshold: null
   positionCallbackThreshold: 
     maxX: Number <from: -100 to: 100>
     maxY: Number <from: -100 to: 100>
     minX: Number <from: -100 to: 100>
     minY: Number <from: -100 to: 100>
     option: String <[x, o, i, <, >]>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Joystick/U/<id>/S/analogValue/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Joystick/U/<id>/S/analogValue/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     maxX: Number <from: 0 to: 4095>
     maxY: Number <from: 0 to: 4095>
     minX: Number <from: 0 to: 4095>
     minY: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   
```
```
TF/Joystick/U/<id>/S/debounce/period
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Joystick/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/Joystick/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/Joystick/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/Joystick/U/<id>/S/position/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Joystick/U/<id>/S/position/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     maxX: Number <from: -100 to: 100>
     maxY: Number <from: -100 to: 100>
     minX: Number <from: -100 to: 100>
     minY: Number <from: -100 to: 100>
     option: String <[x, o, i, <, >]>
   
```

### LaserRangeFinder
```
TF/LaserRangeFinder/U/<id>/E/distance
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 4000>
   
```
```
TF/LaserRangeFinder/U/<id>/E/distance/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 4000>
   
```
```
TF/LaserRangeFinder/U/<id>/E/velocity
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: -127 to: 127>
   
```
```
TF/LaserRangeFinder/U/<id>/E/velocity/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: -127 to: 127>
   
```
```
TF/LaserRangeFinder/U/<id>/I
   configuration: null
   configuration: 
     aquisitionCount: Number <from: 1 to: 255>
     measurementFrequency: Number <[from: 0 to: 0,from: 10 to: 500]>
     quickTermination: Boolean <true,false> 
     thresholdValue: Number <from: 0 to: 255>
   debouncePeriod: null
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   distanceCallbackPeriod: null
   distanceCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   distanceCallbackThreshold: null
   distanceCallbackThreshold: 
     max: Number <from: 0 to: 4000>
     min: Number <from: 0 to: 4000>
     option: String <[x, o, i, <, >]>
   laserEnabled: null
   laserEnabled: Boolean <true,false> 
   mode: null
   mode: 
     mode: String <distance,velocity_12_7,velocity_31_75,velocity_63_5,velocity_127>
   movingAverage: null
   movingAverage: 
     averagingDistance: Number <from: 0 to: 30>
     averagingVelocity: Number <from: 0 to: 30>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   velocityCallbackPeriod: null
   velocityCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   velocityCallbackThreshold: null
   velocityCallbackThreshold: 
     max: Number <from: -127 to: 127>
     min: Number <from: -127 to: 127>
     option: String <[x, o, i, <, >]>
   
```
```
TF/LaserRangeFinder/U/<id>/S/debounce/period
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/LaserRangeFinder/U/<id>/S/deviceConfiguration
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     aquisitionCount: Number <from: 1 to: 255>
     measurementFrequency: Number <[from: 0 to: 0,from: 10 to: 500]>
     quickTermination: Boolean <true,false> 
     thresholdValue: Number <from: 0 to: 255>
   
```
```
TF/LaserRangeFinder/U/<id>/S/deviceMode
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     mode: String <distance,velocity_12_7,velocity_31_75,velocity_63_5,velocity_127>
   
```
```
TF/LaserRangeFinder/U/<id>/S/distance/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/LaserRangeFinder/U/<id>/S/distance/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: 0 to: 4000>
     min: Number <from: 0 to: 4000>
     option: String <[x, o, i, <, >]>
   
```
```
TF/LaserRangeFinder/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/LaserRangeFinder/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/LaserRangeFinder/U/<id>/S/laser
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Boolean <true,false> 
   
```
```
TF/LaserRangeFinder/U/<id>/S/movingAverage
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     averagingDistance: Number <from: 0 to: 30>
     averagingVelocity: Number <from: 0 to: 30>
   
```
```
TF/LaserRangeFinder/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/LaserRangeFinder/U/<id>/S/sensorHardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     version: String <v1,v3>
   
```
```
TF/LaserRangeFinder/U/<id>/S/velocity/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/LaserRangeFinder/U/<id>/S/velocity/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: -127 to: 127>
     min: Number <from: -127 to: 127>
     option: String <[x, o, i, <, >]>
   
```

### LEDStrip
```
TF/LEDStrip/U/<id>/E/frame/rendered
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/LEDStrip/U/<id>/E/laging
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Boolean <true,false> 
   
```
```
TF/LEDStrip/U/<id>/I
   config: null
   config: 
     channelMapping: String <BGR,BGRW,BGWR,BRG,BRGW,BRWG,BWGR,BWRG,GBR,GBRW,GBWR,GRB,GRBW,GRWB,GWBR,GWRB,RBG,RBGW,RBWG,RGB,RGBW,RGWB,RWBG,RWGB>
     chipType: String <WS2801,WS2811,WS2812,WS2812RGBW,LPD8806,APA102>
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
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/LEDStrip/U/<id>/S/config
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     channelMapping: String <BGR,BGRW,BGWR,BRG,BRGW,BRWG,BWGR,BWRG,GBR,GBRW,GBWR,GRB,GRBW,GRWB,GWBR,GWRB,RBG,RBGW,RBWG,RGB,RGBW,RGWB,RWBG,RWGB>
     chipType: String <WS2801,WS2811,WS2812,WS2812RGBW,LPD8806,APA102>
     clockFrequencyOfICsInHz: Number <from: 10000 to: 2000000>
     frameDurationInMilliseconds: Number <from: 0 to: 9223372036854775807>
     numberOfLEDs: Number <from: 0 to: 320>
   
```
```
TF/LEDStrip/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/LEDStrip/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/LEDStrip/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### Line
```
TF/Line/U/<id>/E/reflectivity
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 4095>
   
```
```
TF/Line/U/<id>/E/reflectivity/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 4095>
   
```
```
TF/Line/U/<id>/I
   debouncePeriod: null
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   reflectivityCallbackPeriod: null
   reflectivityCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   reflectivityCallbackThreshold: null
   reflectivityCallbackThreshold: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Line/U/<id>/S/debounce/period
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Line/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/Line/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/Line/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/Line/U/<id>/S/reflectivity/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Line/U/<id>/S/reflectivity/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   
```

### LinearPoti
```
TF/LinearPoti/U/<id>/E/analogValue
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 4095>
   
```
```
TF/LinearPoti/U/<id>/E/analogValue/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 4095>
   
```
```
TF/LinearPoti/U/<id>/E/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 100>
   
```
```
TF/LinearPoti/U/<id>/E/position/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 100>
   
```
```
TF/LinearPoti/U/<id>/I
   analogCallbackPeriod: null
   analogCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   analogValueCallbackThreshold: null
   analogValueCallbackThreshold: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   debouncePeriod: null
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   positionCallbackPeriod: null
   positionCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   positionCallbackThreshold: null
   positionCallbackThreshold: 
     max: Number <from: 0 to: 100>
     min: Number <from: 0 to: 100>
     option: String <[x, o, i, <, >]>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/LinearPoti/U/<id>/S/analogValue/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/LinearPoti/U/<id>/S/analogValue/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   
```
```
TF/LinearPoti/U/<id>/S/debounce/period
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/LinearPoti/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/LinearPoti/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/LinearPoti/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/LinearPoti/U/<id>/S/position/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/LinearPoti/U/<id>/S/position/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: 0 to: 100>
     min: Number <from: 0 to: 100>
     option: String <[x, o, i, <, >]>
   
```

### LoadCell
```
TF/LoadCell/U/<id>/E/weight
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: -50001 to: 50001>
   
```
```
TF/LoadCell/U/<id>/E/weight/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: -50001 to: 50001>
   
```
```
TF/LoadCell/U/<id>/I
   calibrate: null
   configuration: null
   configuration: 
     gain: String <gain128X,gain64X,gain32X>
     rate: String <rate10Hz,rate80Hz>
   debouncePeriod: null
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   movingAverage: null
   movingAverage: Number <from: 1 to: 40>
   statusLED: null
   statusLED: Boolean <true,false> 
   tare: null
   tare: Boolean <true,false> 
   timeStamp: Number <from: 0 to: 9223372036854775807>
   weightCallbackPeriod: null
   weightCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   weightCallbackThreshold: null
   weightCallbackThreshold: 
     max: Number <from: -50001 to: 50001>
     min: Number <from: -50001 to: 50001>
     option: String <[x, o, i, <, >]>
   
```
```
TF/LoadCell/U/<id>/S/LED
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Boolean <true,false> 
   
```
```
TF/LoadCell/U/<id>/S/configuration
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     gain: String <gain128X,gain64X,gain32X>
     rate: String <rate10Hz,rate80Hz>
   
```
```
TF/LoadCell/U/<id>/S/debounce/period
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/LoadCell/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/LoadCell/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/LoadCell/U/<id>/S/movingAverage
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 1 to: 40>
   
```
```
TF/LoadCell/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/LoadCell/U/<id>/S/weight/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/LoadCell/U/<id>/S/weight/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: -50001 to: 50001>
     min: Number <from: -50001 to: 50001>
     option: String <[x, o, i, <, >]>
   
```

### Master
```
TF/Master/U/<id>/E/USB/voltage
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: -2147483648 to: 2147483647>
   
```
```
TF/Master/U/<id>/E/USB/voltage/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: -2147483648 to: 2147483647>
   
```
```
TF/Master/U/<id>/E/reset
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Master/U/<id>/E/stack/current
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 2147483647>
   
```
```
TF/Master/U/<id>/E/stack/current/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 2147483647>
   
```
```
TF/Master/U/<id>/E/stack/voltage
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: -2147483648 to: 2147483647>
   
```
```
TF/Master/U/<id>/E/stack/voltage/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: -2147483648 to: 2147483647>
   
```
```
TF/Master/U/<id>/I
   currentCallbackPeriod: null
   currentCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   currentCallbackThreshold: null
   currentCallbackThreshold: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   debouncePeriod: null
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   reset: null
   reset: Boolean <true,false> 
   stackVoltageCallbackPeriod: null
   stackVoltageCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   stackVoltageCallbackThreshold: null
   stackVoltageCallbackThreshold: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   statusLED: null
   statusLED: Boolean <true,false> 
   timeStamp: Number <from: 0 to: 9223372036854775807>
   usbVoltageCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   usbVoltageCallbackThreshold: null
   usbVoltageCallbackThreshold: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   
```
```
TF/Master/U/<id>/S/USB/voltage/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Master/U/<id>/S/USB/voltage/callbackThreshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   
```
```
TF/Master/U/<id>/S/debounce/period
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Master/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/Master/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/Master/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/Master/U/<id>/S/stack/current/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Master/U/<id>/S/stack/current/callbackThreshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   
```
```
TF/Master/U/<id>/S/stack/voltage/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Master/U/<id>/S/stack/voltage/callbackThreshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   
```
```
TF/Master/U/<id>/S/statusLED/enabled
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Boolean <true,false> 
   
```

### Moisture
```
TF/Moisture/U/<id>/E/moisture
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 4095>
   
```
```
TF/Moisture/U/<id>/E/moisture/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 4095>
   
```
```
TF/Moisture/U/<id>/I
   debouncePeriod: null
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   moistureCallbackPeriod: null
   moistureCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   moistureCallbackThreshold: null
   moistureCallbackThreshold: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   movingAverage: null
   movingAverage: Number <from: 0 to: 100>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Moisture/U/<id>/S/debounce/period
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Moisture/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/Moisture/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/Moisture/U/<id>/S/moisture/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Moisture/U/<id>/S/moisture/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   
```
```
TF/Moisture/U/<id>/S/movingAverage
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 100>
   
```
```
TF/Moisture/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### MotionDetector
```
TF/MotionDetector/U/<id>/E/eventDetectionCycleEnded
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/MotionDetector/U/<id>/E/motionDetected
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/MotionDetector/U/<id>/I
   
```
```
TF/MotionDetector/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/MotionDetector/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/MotionDetector/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### MotorizedLinearPoti
```
TF/MotorizedLinearPoti/U/<id>/E/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 100>
   
```
```
TF/MotorizedLinearPoti/U/<id>/E/position/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 100>
   
```
```
TF/MotorizedLinearPoti/U/<id>/I
   motorPosition: null
   motorPosition: 
     driveMode: String <FAST,SMOOTH>
     holdPosition: Boolean <true,false> 
     position: Number <from: 0 to: 100>
   positionCallbackConfiguration: null
   positionCallbackConfiguration: 
     max: Number <from: 0 to: 100>
     min: Number <from: 0 to: 100>
     option: String <[x, o, i, <, >]>
     period: Number <from: 0 to: 9223372036854775807>
     valueHasToChange: Boolean <true,false> 
   positionReachedCallbackConfiguration: Boolean <true,false> 
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/MotorizedLinearPoti/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/MotorizedLinearPoti/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/MotorizedLinearPoti/U/<id>/S/motor/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     driveMode: String <FAST,SMOOTH>
     holdPosition: Boolean <true,false> 
     position: Number <from: 0 to: 100>
   
```
```
TF/MotorizedLinearPoti/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/MotorizedLinearPoti/U/<id>/S/position/callbackConfiguration
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: 0 to: 100>
     min: Number <from: 0 to: 100>
     option: String <[x, o, i, <, >]>
     period: Number <from: 0 to: 9223372036854775807>
     valueHasToChange: Boolean <true,false> 
   
```
```
TF/MotorizedLinearPoti/U/<id>/S/position/reached/callbackConfiguration
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Boolean <true,false> 
   
```

### MultiTouch
```
TF/MultiTouch/U/<id>/E/recalibrated
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/MultiTouch/U/<id>/E/touchState
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 8191>
   
```
```
TF/MultiTouch/U/<id>/I
   electrodeConfig: null
   electrodeConfig: Number <from: 0 to: 8191>
   recalibration: null
   recalibration: Boolean <true,false> 
   sensitivity: null
   sensitivity: Number <from: 5 to: 201>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/MultiTouch/U/<id>/S/electrode/config
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 8191>
   
```
```
TF/MultiTouch/U/<id>/S/electrode/sensitivity
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 5 to: 201>
   
```
```
TF/MultiTouch/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/MultiTouch/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/MultiTouch/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### NfcRfid
```
TF/NfcRfid/U/<id>/E/tag/discovered
   id: String <regEx: [0-9A-F]{8-14}>
   latestDiscoveryTimeStamp: Number <from: 0 to: 9223372036854775807>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   type: String <MifareClassic,Type1,Type2>
   
```
```
TF/NfcRfid/U/<id>/E/tag/read
   id: String <regEx: [0-9A-F]{8-14}>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/NfcRfid/U/<id>/E/tag/vanished
   id: String <regEx: [0-9A-F]{8-14}>
   latestDiscoveryTimeStamp: Number <from: 0 to: 9223372036854775807>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   type: String <MifareClassic,Type1,Type2>
   
```
```
TF/NfcRfid/U/<id>/E/tag/written
   id: String <regEx: [0-9A-F]{8-14}>
   state: String <WritePageError,WritePageReady>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/NfcRfid/U/<id>/I
   nfcWrite: null
   nfcWrite: 
     tagID: String <regEx: [0-9A-F]{8}|[0-9A-F]{14}>
     value: Array <min: 0 max: 2147483647>
   scanningInterval: null
   scanningInterval: Number <from: 0 to: 9223372036854775807>
   tagID: null
   tagID: String <regEx: [0-9A-F]{8-14}>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/NfcRfid/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/NfcRfid/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/NfcRfid/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/NfcRfid/U/<id>/S/scanning/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```

### PiezoSpeaker
```
TF/PiezoSpeaker/U/<id>/E/calibrated
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/PiezoSpeaker/U/<id>/E/finished
   morseCodeParameter: null
   morseCodeParameter: 
     frequency: Number <from: 585 to: 7100>
     string: String <regEx: [\.\s-]{1,60}>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/PiezoSpeaker/U/<id>/E/started
   morseCodeParameter: null
   morseCodeParameter: 
     frequency: Number <from: 585 to: 7100>
     string: String <regEx: [\.\s-]{1,60}>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/PiezoSpeaker/U/<id>/I
   beepParameter: null
   beepParameter: 
     duration: Number <from: 0 to: 2147483647>
     frequency: Number <from: 585 to: 7100>
   calibrate: null
   calibrate: Boolean <true,false> 
   morseCodeParameter: null
   morseCodeParameter: 
     frequency: Number <from: 585 to: 7100>
     string: String <regEx: [\.\s-]{1,60}>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/PiezoSpeaker/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/PiezoSpeaker/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/PiezoSpeaker/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### PTC
```
TF/PTC/U/<id>/E/resistance
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 32767>
   
```
```
TF/PTC/U/<id>/E/resistance/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 32767>
   
```
```
TF/PTC/U/<id>/E/temperature
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: -24600 to: 84900>
   
```
```
TF/PTC/U/<id>/E/temperature/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: -24600 to: 84900>
   
```
```
TF/PTC/U/<id>/I
   debouncePeriod: null
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   noiseReductionFilter: null
   noiseReductionFilter: 
     filter: String <Hz_50,Hz_60>
   resistanceCallbackPeriod: null
   resistanceCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   resistanceThreshold: null
   temperatureCallbackPeriod: null
   temperatureCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   temperatureThreshold: null
   timeStamp: Number <from: 0 to: 9223372036854775807>
   wireMode: null
   wireMode: String <[2, 3, 4]>
   
```
```
TF/PTC/U/<id>/S/debounce/period
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/PTC/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/PTC/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/PTC/U/<id>/S/noiseReductionFilter
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     filter: String <Hz_50,Hz_60>
   
```
```
TF/PTC/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/PTC/U/<id>/S/resistance/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/PTC/U/<id>/S/resistance/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/PTC/U/<id>/S/temperature/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/PTC/U/<id>/S/temperature/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/PTC/U/<id>/S/wireMode
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[2, 3, 4]>
   
```

### RealTimeClock
```
TF/RealTimeClock/U/<id>/E/alarm
   alarm: Boolean <true,false> 
   dateTimeParameter: 
     centisecond: Number <from: 0 to: 10>
     day: Number <from: 1 to: 31>
     hour: Number <from: 0 to: 59>
     minute: Number <from: 0 to: 59>
     month: Number <from: 1 to: 12>
     second: Number <from: 0 to: 59>
     weekday: String <monday,tuesday,wednesday,thursday,friday,saturday,sunday>
     year: Number <from: 2000 to: 2099>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/RealTimeClock/U/<id>/E/dateTime
   alarm: Boolean <true,false> 
   dateTimeParameter: 
     centisecond: Number <from: 0 to: 10>
     day: Number <from: 1 to: 31>
     hour: Number <from: 0 to: 59>
     minute: Number <from: 0 to: 59>
     month: Number <from: 1 to: 12>
     second: Number <from: 0 to: 59>
     weekday: String <monday,tuesday,wednesday,thursday,friday,saturday,sunday>
     year: Number <from: 2000 to: 2099>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/RealTimeClock/U/<id>/I
   alarmParameter: null
   alarmParameter: 
     day: Number <from: 1 to: 31>
     hour: Number <from: 0 to: 23>
     interval: Number <from: -1 to: 2147483647>
     minute: Number <from: 0 to: 59>
     month: Number <from: 1 to: 12>
     second: Number <from: 0 to: 59>
     weekday: String <monday,tuesday,wednesday,thursday,friday,saturday,sunday,disabled>
   dateTimeCallbackPeriod: null
   dateTimeCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   dateTimeParameter: null
   dateTimeParameter: 
     centisecond: Number <from: 0 to: 10>
     day: Number <from: 1 to: 31>
     hour: Number <from: 0 to: 59>
     minute: Number <from: 0 to: 59>
     month: Number <from: 1 to: 12>
     second: Number <from: 0 to: 59>
     weekday: String <monday,tuesday,wednesday,thursday,friday,saturday,sunday>
     year: Number <from: 2000 to: 2099>
   offset: null
   offset: Number <from: -128 to: 127>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/RealTimeClock/U/<id>/S/alarm
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     day: Number <from: 1 to: 31>
     hour: Number <from: 0 to: 23>
     interval: Number <from: -1 to: 2147483647>
     minute: Number <from: 0 to: 59>
     month: Number <from: 1 to: 12>
     second: Number <from: 0 to: 59>
     weekday: String <monday,tuesday,wednesday,thursday,friday,saturday,sunday,disabled>
   
```
```
TF/RealTimeClock/U/<id>/S/dateTime
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     centisecond: Number <from: 0 to: 10>
     day: Number <from: 1 to: 31>
     hour: Number <from: 0 to: 59>
     minute: Number <from: 0 to: 59>
     month: Number <from: 1 to: 12>
     second: Number <from: 0 to: 59>
     weekday: String <monday,tuesday,wednesday,thursday,friday,saturday,sunday>
     year: Number <from: 2000 to: 2099>
   
```
```
TF/RealTimeClock/U/<id>/S/dateTime/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/RealTimeClock/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/RealTimeClock/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/RealTimeClock/U/<id>/S/offset
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: -128 to: 127>
   
```
```
TF/RealTimeClock/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### RemoteSwitch
```
TF/RemoteSwitch/U/<id>/E/switchingDone
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/RemoteSwitch/U/<id>/I
   dimSocketBParameters: null
   dimSocketBParameters: 
     address: Number <from: 0 to: 67108863>
     dimValue: Number <from: 0 to: 15>
     unit: Number <from: 0 to: 15>
   repeats: null
   repeats: Number <from: 0 to: 32767>
   switchSocketAParameters: null
   switchSocketAParameters: 
     houseCode: Number <from: 0 to: 31>
     receiverCode: Number <from: 0 to: 31>
     switchingValue: String <switchOn,switchOff>
   switchSocketBParameters: null
   switchSocketBParameters: 
     address: Number <from: 0 to: 67108863>
     switchingValue: String <switchOn,switchOff>
     unit: Number <from: 0 to: 15>
   switchSocketCParameters: null
   switchSocketCParameters: 
     deviceCode: Number <from: 1 to: 16>
     switchingValue: String <switchOn,switchOff>
     systemCode: Number <from: 65 to: 80>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/RemoteSwitch/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/RemoteSwitch/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/RemoteSwitch/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/RemoteSwitch/U/<id>/S/repeats
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 32767>
   
```

### RotaryEncoder
```
TF/RotaryEncoder/U/<id>/E/count
   reset: Boolean <true,false> 
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: -900 to: 900>
   
```
```
TF/RotaryEncoder/U/<id>/E/count/reached
   reset: Boolean <true,false> 
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: -900 to: 900>
   
```
```
TF/RotaryEncoder/U/<id>/E/count/reset
   reset: Boolean <true,false> 
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: -900 to: 900>
   
```
```
TF/RotaryEncoder/U/<id>/E/pressed
   pressed: Boolean <true,false> 
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/RotaryEncoder/U/<id>/E/released
   pressed: Boolean <true,false> 
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/RotaryEncoder/U/<id>/I
   countCallbackPeriod: null
   countCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   countReset: null
   countReset: Boolean <true,false> 
   countThreshold: null
   countThreshold: 
     max: Number <from: -150 to: 150>
     min: Number <from: -150 to: 150>
     option: String <[x, o, i, <, >]>
   debouncePeriod: null
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/RotaryEncoder/U/<id>/S/count/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/RotaryEncoder/U/<id>/S/count/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: -150 to: 150>
     min: Number <from: -150 to: 150>
     option: String <[x, o, i, <, >]>
   
```
```
TF/RotaryEncoder/U/<id>/S/debounce/period
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/RotaryEncoder/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/RotaryEncoder/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/RotaryEncoder/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### RotaryPoti
```
TF/RotaryPoti/U/<id>/E/analogValue
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 4095>
   
```
```
TF/RotaryPoti/U/<id>/E/analogValue/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 4095>
   
```
```
TF/RotaryPoti/U/<id>/E/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 100>
   
```
```
TF/RotaryPoti/U/<id>/E/position/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 100>
   
```
```
TF/RotaryPoti/U/<id>/I
   analogValueCallbackPeriod: null
   analogValueCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   analogValueCallbackThreshold: null
   analogValueCallbackThreshold: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   debouncePeriod: null
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   positionCallbackPeriod: null
   positionCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   positionCallbackThreshold: null
   positionCallbackThreshold: 
     max: Number <from: -150 to: 150>
     min: Number <from: -150 to: 150>
     option: String <[x, o, i, <, >]>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/RotaryPoti/U/<id>/S/analogValue/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/RotaryPoti/U/<id>/S/analogValue/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: 0 to: 4095>
     min: Number <from: 0 to: 4095>
     option: String <[x, o, i, <, >]>
   
```
```
TF/RotaryPoti/U/<id>/S/debounce/period
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/RotaryPoti/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/RotaryPoti/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/RotaryPoti/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/RotaryPoti/U/<id>/S/position/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/RotaryPoti/U/<id>/S/position/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: -150 to: 150>
     min: Number <from: -150 to: 150>
     option: String <[x, o, i, <, >]>
   
```

### SegmentDisplay4x7
```
TF/SegmentDisplay4x7/U/<id>/E/counterFinished
   counterParameters: null
   counterParameters: 
     from: Number <from: -999 to: 9999>
     increment: Number <from: -999 to: 9999>
     length: Number <from: 0 to: 9223372036854775807>
     to: Number <from: -999 to: 9999>
   finished: Boolean <true,false> 
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/SegmentDisplay4x7/U/<id>/E/counterStarted
   counterParameters: null
   counterParameters: 
     from: Number <from: -999 to: 9999>
     increment: Number <from: -999 to: 9999>
     length: Number <from: 0 to: 9223372036854775807>
     to: Number <from: -999 to: 9999>
   finished: Boolean <true,false> 
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
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
     bits: Number <from: 0 to: 128>
     brightness: Number <from: 0 to: 7>
     colon: Boolean <true,false> 
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/SegmentDisplay4x7/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/SegmentDisplay4x7/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/SegmentDisplay4x7/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/SegmentDisplay4x7/U/<id>/S/segments
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     bits: Array <min: 4 max: 4>
     bits: Number <from: 0 to: 128>
     brightness: Number <from: 0 to: 7>
     colon: Boolean <true,false> 
   
```

### Servo
```
TF/Servo/U/<id>/E/positionReached
   id: Number <from: 0 to: 6>
   position: Number <from: -32767 to: 32767>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Servo/U/<id>/E/undervoltage
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 32767>
   
```
```
TF/Servo/U/<id>/E/velocityReached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Servo/U/<id>/I
   minimumVoltage: null
   minimumVoltage: Number <from: 5000 to: 2147483647>
   outputVoltage: null
   outputVoltage: Number <from: 2000 to: 9000>
   servos: null
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
   statusLED: null
   statusLED: Boolean <true,false> 
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Servo/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/Servo/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/Servo/U/<id>/S/minimumVoltage
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 5000 to: 2147483647>
   
```
```
TF/Servo/U/<id>/S/outputVoltage
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 2000 to: 9000>
   
```
```
TF/Servo/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/Servo/U/<id>/S/servos
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Set <min: 0 max: 7>
   value: 
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
   
```
```
TF/Servo/U/<id>/S/statusLED
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Boolean <true,false> 
   
```

### SolidState
```
TF/SolidState/U/<id>/E/monoflopDone
   state: Boolean <true,false> 
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/SolidState/U/<id>/I
   monoflopParameters: null
   monoflopParameters: 
     period: Number <from: 0 to: 9223372036854775807>
     state: Boolean <true,false> 
   state: null
   state: Boolean <true,false> 
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/SolidState/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/SolidState/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/SolidState/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/SolidState/U/<id>/S/state
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Boolean <true,false> 
   
```

### SoundIntensity
```
TF/SoundIntensity/U/<id>/E/soundIntensity
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 10000>
   
```
```
TF/SoundIntensity/U/<id>/E/soundIntensity/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 10000>
   
```
```
TF/SoundIntensity/U/<id>/I
   debouncePeriod: null
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   intensityCallbackPeriod: null
   intensityCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   intensityCallbackThreshold: null
   intensityCallbackThreshold: 
     max: Number <from: 0 to: 10000>
     min: Number <from: 0 to: 10000>
     option: String <[x, o, i, <, >]>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/SoundIntensity/U/<id>/S/debounce/period
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/SoundIntensity/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/SoundIntensity/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/SoundIntensity/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/SoundIntensity/U/<id>/S/soundIntensity/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/SoundIntensity/U/<id>/S/soundIntensity/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: 0 to: 10000>
     min: Number <from: 0 to: 10000>
     option: String <[x, o, i, <, >]>
   
```

### Temperature
```
TF/Temperature/U/<id>/E/temperature
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: -2500 to: 8500>
   
```
```
TF/Temperature/U/<id>/E/temperature/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: -2500 to: 8500>
   
```
```
TF/Temperature/U/<id>/I
   debouncePeriod: null
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   mode: null
   mode: 
     mode: String <Fast,Slow>
   resistanceCallbackPeriod: null
   resistanceCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   temperatureCallbackPeriod: null
   temperatureCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   temperatureThreshold: null
   temperatureThreshold: 
     max: Number <from: -2500 to: 8500>
     min: Number <from: -2500 to: 8500>
     option: String <[x, o, i, <, >]>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Temperature/U/<id>/S/debounce/period
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Temperature/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/Temperature/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/Temperature/U/<id>/S/mode
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     mode: String <Fast,Slow>
   
```
```
TF/Temperature/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/Temperature/U/<id>/S/temperature/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Temperature/U/<id>/S/temperature/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: -2500 to: 8500>
     min: Number <from: -2500 to: 8500>
     option: String <[x, o, i, <, >]>
   
```

### TemperatureIR
```
TF/TemperatureIR/U/<id>/E/ambientTemperature
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: -400 to: 1250>
   
```
```
TF/TemperatureIR/U/<id>/E/ambientTemperature/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: -400 to: 1250>
   
```
```
TF/TemperatureIR/U/<id>/E/objectTemperature
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: -700 to: 3800>
   
```
```
TF/TemperatureIR/U/<id>/E/objectTemperature/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: -700 to: 3800>
   
```
```
TF/TemperatureIR/U/<id>/I
   ambientTemperatureCallbackPeriod: null
   ambientTemperatureCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   ambientTemperatureCallbackThreshold: null
   ambientTemperatureCallbackThreshold: 
     max: Number <from: -400 to: 1250>
     min: Number <from: -400 to: 1250>
     option: String <[x, o, i, <, >]>
   debouncePeriod: null
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   objectTemperatureCallbackPeriod: null
   objectTemperatureCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   objectTemperatureCallbackThreshold: null
   objectTemperatureCallbackThreshold: 
     max: Number <from: -700 to: 3800>
     min: Number <from: -700 to: 3800>
     option: String <[x, o, i, <, >]>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/TemperatureIR/U/<id>/S/ambientTemperature/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/TemperatureIR/U/<id>/S/ambientTemperature/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: -400 to: 1250>
     min: Number <from: -400 to: 1250>
     option: String <[x, o, i, <, >]>
   
```
```
TF/TemperatureIR/U/<id>/S/debounce/period
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/TemperatureIR/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/TemperatureIR/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/TemperatureIR/U/<id>/S/objectTemperature/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/TemperatureIR/U/<id>/S/objectTemperature/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: -700 to: 3800>
     min: Number <from: -700 to: 3800>
     option: String <[x, o, i, <, >]>
   
```
```
TF/TemperatureIR/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### ThermoCouple
```
TF/ThermoCouple/U/<id>/E/error
   openCircuit: Boolean <true,false> 
   timeStamp: Number <from: 0 to: 9223372036854775807>
   voltage: Boolean <true,false> 
   
```
```
TF/ThermoCouple/U/<id>/E/temperature
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: -21000 to: 180000>
   
```
```
TF/ThermoCouple/U/<id>/E/temperature/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: -21000 to: 180000>
   
```
```
TF/ThermoCouple/U/<id>/I
   configuration: null
   configuration: 
     averaging: String <sample_1,sample_2,sample_4,sample_8,sample_16>
     filter: String <Hz_50,Hz_60>
     type: String <B,E,J,K,N,R,S,T,G8,G32>
   debouncePeriod: null
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   temperatureCallbackPeriod: null
   temperatureCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   temperatureCallbackThreshold: null
   temperatureCallbackThreshold: 
     max: Number <from: -21000 to: 180000>
     min: Number <from: -21000 to: 180000>
     option: String <[x, o, i, <, >]>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/ThermoCouple/U/<id>/S/configuration
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     averaging: String <sample_1,sample_2,sample_4,sample_8,sample_16>
     filter: String <Hz_50,Hz_60>
     type: String <B,E,J,K,N,R,S,T,G8,G32>
   
```
```
TF/ThermoCouple/U/<id>/S/debounce/period
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/ThermoCouple/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/ThermoCouple/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/ThermoCouple/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/ThermoCouple/U/<id>/S/temperature/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/ThermoCouple/U/<id>/S/temperature/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: -21000 to: 180000>
     min: Number <from: -21000 to: 180000>
     option: String <[x, o, i, <, >]>
   
```

### Tilt
```
TF/Tilt/U/<id>/E/tiltState
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 2>
   
```
```
TF/Tilt/U/<id>/I
   
```
```
TF/Tilt/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/Tilt/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/Tilt/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### UVLight
```
TF/UVLight/U/<id>/E/uvLight
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 328000>
   
```
```
TF/UVLight/U/<id>/E/uvLight/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 328000>
   
```
```
TF/UVLight/U/<id>/I
   debouncePeriod: null
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   movingAverage: null
   movingAverage: Number <from: 0 to: 100>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   uvLightCallbackPeriod: null
   uvLightCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   uvLightCallbackThreshold: null
   uvLightCallbackThreshold: 
     max: Number <from: 0 to: 328000>
     min: Number <from: 0 to: 328000>
     option: String <[x, o, i, <, >]>
   
```
```
TF/UVLight/U/<id>/S/debounce/period
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/UVLight/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/UVLight/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/UVLight/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/UVLight/U/<id>/S/uvLight/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/UVLight/U/<id>/S/uvLight/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: 0 to: 328000>
     min: Number <from: 0 to: 328000>
     option: String <[x, o, i, <, >]>
   
```

### VoltageCurrent
```
TF/VoltageCurrent/U/<id>/E/current
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 36000>
   
```
```
TF/VoltageCurrent/U/<id>/E/current/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 36000>
   
```
```
TF/VoltageCurrent/U/<id>/E/power
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 720000>
   
```
```
TF/VoltageCurrent/U/<id>/E/power/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 720000>
   
```
```
TF/VoltageCurrent/U/<id>/E/voltage
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: -50001 to: 50001>
   
```
```
TF/VoltageCurrent/U/<id>/E/voltage/reached
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: -50001 to: 50001>
   
```
```
TF/VoltageCurrent/U/<id>/I
   calibration: null
   calibration: 
     gainDivisor: Number <from: 1 to: 2147483647>
     gainMultiplier: Number <from: 1 to: 2147483647>
   configuration: null
   configuration: 
     averaging: String <AVERAGING_1,AVERAGING_4,AVERAGING_16,AVERAGING_64,AVERAGING_128,AVERAGING_256,AVERAGING_512,AVERAGING_1024>
     currentConversionTime: String <CONVERSION_140us,CONVERSION_204us,CONVERSION_332us,CONVERSION_588us,CONVERSION_1100us,CONVERSION_2116us,CONVERSION_4156us,CONVERSION_8244us>
     voltageConversionTime: String <CONVERSION_140us,CONVERSION_204us,CONVERSION_332us,CONVERSION_588us,CONVERSION_1100us,CONVERSION_2116us,CONVERSION_4156us,CONVERSION_8244us>
   currentCalbackThreshold: null
   currentCalbackThreshold: 
     max: Number <from: 0 to: 36000>
     min: Number <from: 0 to: 36000>
     option: String <[x, o, i, <, >]>
   currentCallbackPeriod: null
   currentCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   debouncePeriod: null
   debouncePeriod: Number <from: 0 to: 9223372036854775807>
   powerCallbackPeriod: null
   powerCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   powerCallbackThreshold: null
   powerCallbackThreshold: 
     max: Number <from: 0 to: 720000>
     min: Number <from: 0 to: 720000>
     option: String <[x, o, i, <, >]>
   timeStamp: Number <from: 0 to: 9223372036854775807>
   voltageCallbackPeriod: null
   voltageCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   voltageCallbackThreshold: null
   voltageCallbackThreshold: 
     max: Number <from: -5001 to: 5001>
     min: Number <from: -5001 to: 5001>
     option: String <[x, o, i, <, >]>
   
```
```
TF/VoltageCurrent/U/<id>/S/calibration
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     gainDivisor: Number <from: 1 to: 2147483647>
     gainMultiplier: Number <from: 1 to: 2147483647>
   
```
```
TF/VoltageCurrent/U/<id>/S/configuration
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     averaging: String <AVERAGING_1,AVERAGING_4,AVERAGING_16,AVERAGING_64,AVERAGING_128,AVERAGING_256,AVERAGING_512,AVERAGING_1024>
     currentConversionTime: String <CONVERSION_140us,CONVERSION_204us,CONVERSION_332us,CONVERSION_588us,CONVERSION_1100us,CONVERSION_2116us,CONVERSION_4156us,CONVERSION_8244us>
     voltageConversionTime: String <CONVERSION_140us,CONVERSION_204us,CONVERSION_332us,CONVERSION_588us,CONVERSION_1100us,CONVERSION_2116us,CONVERSION_4156us,CONVERSION_8244us>
   
```
```
TF/VoltageCurrent/U/<id>/S/current/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/VoltageCurrent/U/<id>/S/current/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: 0 to: 36000>
     min: Number <from: 0 to: 36000>
     option: String <[x, o, i, <, >]>
   
```
```
TF/VoltageCurrent/U/<id>/S/debounce/period
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/VoltageCurrent/U/<id>/S/firmware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/VoltageCurrent/U/<id>/S/hardware
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Array <min: 0 max: 2147483647>
   value: Number <from: -32768 to: 32767>
   
```
```
TF/VoltageCurrent/U/<id>/S/position
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/VoltageCurrent/U/<id>/S/power/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/VoltageCurrent/U/<id>/S/power/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: 0 to: 720000>
     min: Number <from: 0 to: 720000>
     option: String <[x, o, i, <, >]>
   
```
```
TF/VoltageCurrent/U/<id>/S/voltage/callbackPeriod
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/VoltageCurrent/U/<id>/S/voltage/threshold
   timeStamp: Number <from: 0 to: 9223372036854775807>
   value: 
     max: Number <from: -5001 to: 5001>
     min: Number <from: -5001 to: 5001>
     option: String <[x, o, i, <, >]>
   
```