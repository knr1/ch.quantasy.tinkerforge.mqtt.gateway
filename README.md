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
TF/Manager/U/computer/I
---
address:
  hostName: localhost
  port: 4223
connect: true
```
or if you know a remote Tinkerforge stack reachable over an IP-address (e.g. the one in your 192.168.1.x network connected via WLAN-Brick extension as 192.168.1.77)

```
TF/Manager/U/computer/I
---
address:
  hostName: 192.168.1.77
  port: 4223
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
TF/LCD20x4/U/lcd/I/quickshot
---
backlight: true
```

**Measuring the temperature once per second on temperature bricklet (uid: red):**
```
TF/Temperature/U/red/I/quickshot
---
temperatureCallbackPeriod: 1000
```

**Detecting NFC-Tags once per second on NFCRFID bricklet (uid: ouu):**
```
TF/NfcRfid/U/ouu/I/quickshot
---
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
        TF/Manager/U/<id>/E/stack/address/added
            required: # this tag is not part of the data structure
             stackAddress: 
               required: # this tag is not part of the data structure
                 hostName: String <min: 1 max: 255>
                 hostName: String <regEx: \w+(\.\w+){0,3}>
                 port: Number <from: 1024 to: 65535>
             state: Boolean <true,false> 
             timeStamp: Number <from: 0 to: 9223372036854775807>

        TF/Manager/U/<id>/E/stack/address/removed
            required: # this tag is not part of the data structure
             stackAddress: 
               required: # this tag is not part of the data structure
                 hostName: String <min: 1 max: 255>
                 hostName: String <regEx: \w+(\.\w+){0,3}>
                 port: Number <from: 1024 to: 65535>
             state: Boolean <true,false> 
             timeStamp: Number <from: 0 to: 9223372036854775807>

        TF/Manager/U/<id>/I
            required: # this tag is not part of the data structure
             address: 
               required: # this tag is not part of the data structure
                 hostName: String <min: 1 max: 255>
                 hostName: String <regEx: \w+(\.\w+){0,3}>
                 port: Number <from: 1024 to: 65535>
             connect: Boolean <true,false> 
             timeStamp: Number <from: 0 to: 9223372036854775807>

        TF/Manager/U/<id>/S/connection
            required: # this tag is not part of the data structure
             timeStamp: Number <from: 0 to: 9223372036854775807>
             value: String <[online, offline]>

        TF/Manager/U/<id>/S/stack/address/<address>/connected
            required: # this tag is not part of the data structure
             timeStamp: Number <from: 0 to: 9223372036854775807>
             value: Boolean <true,false> 
   
     U
       pc
          S
            connection 
            ---
            value: online
            timeStamp: 348437585239834
```

### Connecting Master-Brick-1

As the description explains, we now have to tell TiMqWay where to look for the Master Bricks (Stacks). Hence, we want to attach Master-Brick-1 (say, its 
network-name is master-brick-1). Therefore the following message has to be sent to the following topic:
```
TF/Manager/U/pc/I
---
address:
  hostName: master-brick-1
  port: 4223
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
TF/LCD20x4/U/lcd/I
---
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
TF/Manager/U/pc/I
---
address:
  hostName: localhost
  port: 4223
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
TF/Temperature/U/red/I
---
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
All that is left is to write the orchestration/choreography, subscribing to the temperature of red and blue.
Then process the values and write them to lcd... as a publish to:
```
TF/LCD20x4/U/lcd/I
---
lines: 
  - line: 0
    position: 0
    text: "RED: 22°C
  - line: 1
    position: 0
    text: "BLUE: 18°C"
```

---

## API
Intent (I) is as straight forward and as concise as possible. You simply put into the intent what you want and omit the rest.
Event (E) is as fine granular as possible, so you can subscribe to your needs and will receive nothing more
Status (S) is as fine granular as possible, so you can subscribe to your needs and will receive nothing more


No message is lost! It is delivered as fast as possible. If the channel is slower than the message creation, the messages will be delivered as an array of messages. 
All required tags have to be provided in order to create a correct message, optional tags can be omitted.

As a special case, required tags with a default value do not have to be provided within an intent. They will be filled in implicitly by the service if not provided.

### Manager
```
TF/Manager/U/<id>/E/stack/address/added
   required: # this tag is not part of the data structure
     stackAddress:
       required: # this tag is not part of the data structure
         hostName: String <min: 1 max: 255>
         hostName: String <regEx: \w+(\.\w+){0,3}>
         port: Number <from: 1024 to: 65535> # default: 4223
     state: Boolean <true,false>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/Manager/U/<id>/E/stack/address/removed
   required: # this tag is not part of the data structure
     stackAddress:
       required: # this tag is not part of the data structure
         hostName: String <min: 1 max: 255>
         hostName: String <regEx: \w+(\.\w+){0,3}>
         port: Number <from: 1024 to: 65535> # default: 4223
     state: Boolean <true,false>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/Manager/U/<id>/I
   required: # this tag is not part of the data structure
     address:
       required: # this tag is not part of the data structure
         hostName: String <min: 1 max: 255>
         hostName: String <regEx: \w+(\.\w+){0,3}>
         port: Number <from: 1024 to: 65535> # default: 4223
     connect: Boolean <true,false>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/Manager/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/Manager/U/<id>/S/stack/address/<address>/connected
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Boolean <true,false>
   
```

### Accelerometer
```
TF/Accelerometer/U/<id>/E/acceleration
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     x: Number <from: -32768 to: 32767>
     y: Number <from: -32768 to: 32767>
     z: Number <from: -32768 to: 32767>
   
```
```
TF/Accelerometer/U/<id>/E/accelerationReached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     x: Number <from: -32768 to: 32767>
     y: Number <from: -32768 to: 32767>
     z: Number <from: -32768 to: 32767>
   
```
```
TF/Accelerometer/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     accelerationThreshold:
       required: # this tag is not part of the data structure
         option: String <[x, o, i, <, >]>
     callbackPeriod: Number <from: 0 to: 9223372036854775807>
     configuration:
       required: # this tag is not part of the data structure
         dataRate: String <OFF,Hz3,Hz6,Hz12,Hz25,Hz50,Hz100,Hz400,Hz800,Hz1600>
         filterBandwidth: String <Hz800,Hz400,Hz200,Hz50>
         fullScale: String <G2,G4,G6,G8,G16>
     debouncePeriod: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Accelerometer/U/<id>/S/acceleration/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Accelerometer/U/<id>/S/acceleration/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         option: String <[x, o, i, <, >]>
   
```
```
TF/Accelerometer/U/<id>/S/configuration
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         dataRate: String <OFF,Hz3,Hz6,Hz12,Hz25,Hz50,Hz100,Hz400,Hz800,Hz1600>
         filterBandwidth: String <Hz800,Hz400,Hz200,Hz50>
         fullScale: String <G2,G4,G6,G8,G16>
   
```
```
TF/Accelerometer/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/Accelerometer/U/<id>/S/debounce/period
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Accelerometer/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/Accelerometer/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/Accelerometer/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### AmbientLight
```
TF/AmbientLight/U/<id>/E/analogValue
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 4095>
   
```
```
TF/AmbientLight/U/<id>/E/analogValue/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 4095>
   
```
```
TF/AmbientLight/U/<id>/E/illuminance
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9000>
   
```
```
TF/AmbientLight/U/<id>/E/illuminance/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9000>
   
```
```
TF/AmbientLight/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     analogCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     analogValueThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 4095>
         min: Number <from: 0 to: 4095>
         option: String <[x, o, i, <, >]>
     debouncePeriod: Number <from: 0 to: 9223372036854775807>
     illuminanceCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     illuminanceThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 9000>
         min: Number <from: 0 to: 9000>
         option: String <[x, o, i, <, >]>
   
```
```
TF/AmbientLight/U/<id>/S/analogValue/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/AmbientLight/U/<id>/S/analogValue/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 4095>
         min: Number <from: 0 to: 4095>
         option: String <[x, o, i, <, >]>
   
```
```
TF/AmbientLight/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/AmbientLight/U/<id>/S/debounce/period
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/AmbientLight/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/AmbientLight/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/AmbientLight/U/<id>/S/illuminance/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/AmbientLight/U/<id>/S/illuminance/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/AmbientLight/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### AmbientLightV2
```
TF/AmbientLightV2/U/<id>/E/illuminance
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9000>
   
```
```
TF/AmbientLightV2/U/<id>/E/illuminance/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9000>
   
```
```
TF/AmbientLightV2/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     configuration:
       required: # this tag is not part of the data structure
         illuminanceRange: String <lx_unlimitted,lx_64000,lx_32000,lx_16000,lx_8000,lx_1300,lx_600>
         integrationTime: String <ms_50,ms_100,ms_150,ms_200,ms_250,ms_300,ms_350,ms_400>
     debouncePeriod: Number <from: 0 to: 9223372036854775807>
     illuminanceCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     illuminanceCallbackThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 100000>
         min: Number <from: 0 to: 100000>
         option: String <[x, o, i, <, >]>
   
```
```
TF/AmbientLightV2/U/<id>/S/configuration
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         illuminanceRange: String <lx_unlimitted,lx_64000,lx_32000,lx_16000,lx_8000,lx_1300,lx_600>
         integrationTime: String <ms_50,ms_100,ms_150,ms_200,ms_250,ms_300,ms_350,ms_400>
   
```
```
TF/AmbientLightV2/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/AmbientLightV2/U/<id>/S/debounce/period
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/AmbientLightV2/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/AmbientLightV2/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/AmbientLightV2/U/<id>/S/illuminance/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/AmbientLightV2/U/<id>/S/illuminance/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 100000>
         min: Number <from: 0 to: 100000>
         option: String <[x, o, i, <, >]>
   
```
```
TF/AmbientLightV2/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### AnalogInV2
```
TF/AnalogInV2/U/<id>/E/analogValue
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 4095>
   
```
```
TF/AnalogInV2/U/<id>/E/analogValue/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 4095>
   
```
```
TF/AnalogInV2/U/<id>/E/voltage
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: -2147483648 to: 2147483647>
   
```
```
TF/AnalogInV2/U/<id>/E/voltage/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: -2147483648 to: 2147483647>
   
```
```
TF/AnalogInV2/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     analogValueCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     analogValueCallbackThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 4095>
         min: Number <from: 0 to: 4095>
         option: String <[x, o, i, <, >]>
     debouncePeriod: Number <from: 0 to: 9223372036854775807>
     movingAverage: Number <from: 0 to: 9223372036854775807>
     voltageCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     voltageCallbackThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 42000>
         min: Number <from: 0 to: 42000>
         option: String <[x, o, i, <, >]>
   
```
```
TF/AnalogInV2/U/<id>/S/analogValue/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/AnalogInV2/U/<id>/S/analogValue/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 4095>
         min: Number <from: 0 to: 4095>
         option: String <[x, o, i, <, >]>
   
```
```
TF/AnalogInV2/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/AnalogInV2/U/<id>/S/debounce/period
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/AnalogInV2/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/AnalogInV2/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/AnalogInV2/U/<id>/S/movingAverage
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 1 to: 40>
   
```
```
TF/AnalogInV2/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/AnalogInV2/U/<id>/S/voltage/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/AnalogInV2/U/<id>/S/voltage/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 42000>
         min: Number <from: 0 to: 42000>
         option: String <[x, o, i, <, >]>
   
```

### AnalogOutV2
```
TF/AnalogOutV2/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     outputVoltage: Number <from: 0 to: 12000>
   
```
```
TF/AnalogOutV2/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/AnalogOutV2/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/AnalogOutV2/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/AnalogOutV2/U/<id>/S/outputVoltage
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 12000>
   
```
```
TF/AnalogOutV2/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### Barometer
```
TF/Barometer/U/<id>/E/airPressure
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 10000 to: 1200000>
   
```
```
TF/Barometer/U/<id>/E/airPressure/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 10000 to: 1200000>
   
```
```
TF/Barometer/U/<id>/E/altitude
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: -2147483648 to: 2147483647>
   
```
```
TF/Barometer/U/<id>/E/altitude/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: -2147483648 to: 2147483647>
   
```
```
TF/Barometer/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     airPressureCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     airPressureCallbackThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: 10000 to: 1200000>
         min: Number <from: 10000 to: 1200000>
         option: String <[x, o, i, <, >]>
     altitudeCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     altitudeCallbackThreshold:
       required: # this tag is not part of the data structure
         option: String <[x, o, i, <, >]>
     averaging:
       required: # this tag is not part of the data structure
         averagingPressure: Number <from: 0 to: 10>
         averagingTemperature: Number <from: 0 to: 255>
         movingAveragePressure: Number <from: 0 to: 25>
     debouncePeriod: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Barometer/U/<id>/S/airPressure/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Barometer/U/<id>/S/airPressure/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: 10000 to: 1200000>
         min: Number <from: 10000 to: 1200000>
         option: String <[x, o, i, <, >]>
   
```
```
TF/Barometer/U/<id>/S/altitude/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Barometer/U/<id>/S/altitude/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         option: String <[x, o, i, <, >]>
   
```
```
TF/Barometer/U/<id>/S/averaging
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         averagingPressure: Number <from: 0 to: 10>
         averagingTemperature: Number <from: 0 to: 255>
         movingAveragePressure: Number <from: 0 to: 25>
   
```
```
TF/Barometer/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/Barometer/U/<id>/S/debounce/period
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Barometer/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/Barometer/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/Barometer/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/Barometer/U/<id>/S/referenceAirPressure
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```

### CO2
```
TF/CO2/U/<id>/E/CO2Concentration
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 10000>
   
```
```
TF/CO2/U/<id>/E/CO2Concentration/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 10000>
   
```
```
TF/CO2/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     co2ConcentrationCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     co2ConcentrationCallbackThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 10000>
         min: Number <from: 0 to: 10000>
         option: String <[x, o, i, <, >]>
     debouncePeriod: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/CO2/U/<id>/S/CO2Concentration/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/CO2/U/<id>/S/CO2Concentration/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 10000>
         min: Number <from: 0 to: 10000>
         option: String <[x, o, i, <, >]>
   
```
```
TF/CO2/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/CO2/U/<id>/S/debounce/period
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/CO2/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/CO2/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/CO2/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### Color
```
TF/Color/U/<id>/E/color
   required: # this tag is not part of the data structure
     blue: Number <from: 0 to: 65535>
     clear: Number <from: 0 to: 65535>
     green: Number <from: 0 to: 65535>
     red: Number <from: 0 to: 65535>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/Color/U/<id>/E/color/reached
   required: # this tag is not part of the data structure
     blue: Number <from: 0 to: 65535>
     clear: Number <from: 0 to: 65535>
     green: Number <from: 0 to: 65535>
     red: Number <from: 0 to: 65535>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/Color/U/<id>/E/illuminance
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 65535>
   
```
```
TF/Color/U/<id>/E/illuminance/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 65535>
   
```
```
TF/Color/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     colorCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     colorCallbackThreshold:
       required: # this tag is not part of the data structure
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
       required: # this tag is not part of the data structure
         gain: String <x1,x4,x16,Hz60>
         integrationTime: String <ms2_4,ms24,ms101,ms154,ms700>
     debouncePeriod: Number <from: 0 to: 9223372036854775807>
     illuminanceCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     light: Boolean <true,false>
   
```
```
TF/Color/U/<id>/S/color/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Color/U/<id>/S/color/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
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
TF/Color/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/Color/U/<id>/S/debounce/period
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Color/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/Color/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/Color/U/<id>/S/illuminance/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Color/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### DC
```
TF/DC/U/<id>/E/emergencyShutdown
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/DC/U/<id>/E/fullBrake
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/DC/U/<id>/E/undervoltage
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 2147483647>
   
```
```
TF/DC/U/<id>/E/velocity
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 32767>
   
```
```
TF/DC/U/<id>/E/velocity/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 32767>
   
```
```
TF/DC/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
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
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 2147483647>
   
```
```
TF/DC/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/DC/U/<id>/S/driverMode
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[1, 2]>
   
```
```
TF/DC/U/<id>/S/enabled
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Boolean <true,false>
   
```
```
TF/DC/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/DC/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/DC/U/<id>/S/minimumVoltage
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 6000 to: 2147483647>
   
```
```
TF/DC/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/DC/U/<id>/S/pwmFrequency
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 1 to: 20000>
   
```
```
TF/DC/U/<id>/S/velocity/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/DC/U/<id>/S/velocity/velocity
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: -32767 to: 32767>
   
```

### DistanceIR
```
TF/DistanceIR/U/<id>/E/analogValue
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 4095>
   
```
```
TF/DistanceIR/U/<id>/E/analogValue/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 4095>
   
```
```
TF/DistanceIR/U/<id>/E/distance
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <[from: 40 to: 300,from: 100 to: 800,from: 200 to: 1500]>
   
```
```
TF/DistanceIR/U/<id>/E/distance/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <[from: 40 to: 300,from: 100 to: 800,from: 200 to: 1500]>
   
```
```
TF/DistanceIR/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     analogCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     analogValueCallbackThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 4095>
         min: Number <from: 0 to: 4095>
         option: String <[x, o, i, <, >]>
     debouncePeriod: Number <from: 0 to: 9223372036854775807>
     distanceCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     distanceCallbackThreshold:
       required: # this tag is not part of the data structure
         max: Number <[from: 0 to: 0,from: 40 to: 300,from: 100 to: 800,from: 200 to: 1500]>
         min: Number <[from: 0 to: 0,from: 40 to: 300,from: 100 to: 800,from: 200 to: 1500]>
         option: String <[x, o, i, <, >]>
   
```
```
TF/DistanceIR/U/<id>/S/analogValue/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/DistanceIR/U/<id>/S/analogValue/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 4095>
         min: Number <from: 0 to: 4095>
         option: String <[x, o, i, <, >]>
   
```
```
TF/DistanceIR/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/DistanceIR/U/<id>/S/debounce/period
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/DistanceIR/U/<id>/S/distance/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/DistanceIR/U/<id>/S/distance/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <[from: 0 to: 0,from: 40 to: 300,from: 100 to: 800,from: 200 to: 1500]>
         min: Number <[from: 0 to: 0,from: 40 to: 300,from: 100 to: 800,from: 200 to: 1500]>
         option: String <[x, o, i, <, >]>
   
```
```
TF/DistanceIR/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/DistanceIR/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/DistanceIR/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### DistanceUS
```
TF/DistanceUS/U/<id>/E/distance
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 4095>
   
```
```
TF/DistanceUS/U/<id>/E/distance/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 4095>
   
```
```
TF/DistanceUS/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     debouncePeriod: Number <from: 0 to: 9223372036854775807>
     distanceCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     distanceCallbackThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 4095>
         min: Number <from: 0 to: 4095>
         option: String <[x, o, i, <, >]>
     movingAverage: Number <from: 0 to: 100>
   
```
```
TF/DistanceUS/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/DistanceUS/U/<id>/S/debounce/period
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/DistanceUS/U/<id>/S/distance/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/DistanceUS/U/<id>/S/distance/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 4095>
         min: Number <from: 0 to: 4095>
         option: String <[x, o, i, <, >]>
   
```
```
TF/DistanceUS/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/DistanceUS/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/DistanceUS/U/<id>/S/movingAverage
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 100>
   
```
```
TF/DistanceUS/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### DualButton
```
TF/DualButton/U/<id>/E/stateChanged
   required: # this tag is not part of the data structure
     led1: String <AutoToggleOn,AutoToggleOff,On,Off>
     led2: String <AutoToggleOn,AutoToggleOff,On,Off>
     switch1: String <[0, 1]>
     switch2: String <[0, 1]>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/DualButton/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     ledState:
       required: # this tag is not part of the data structure
         led1: String <AutoToggleOn,AutoToggleOff,On,Off>
         led2: String <AutoToggleOn,AutoToggleOff,On,Off>
     selectedLEDStates: Set <min: 0 max: 2>
     selectedLEDStates:
      required: # this tag is not part of the data structure
        led: String <[1, 2]>
        state: String <AutoToggleOn,AutoToggleOff,On,Off>
   
```
```
TF/DualButton/U/<id>/S/LEDState
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         led1: String <AutoToggleOn,AutoToggleOff,On,Off>
         led2: String <AutoToggleOn,AutoToggleOff,On,Off>
   
```
```
TF/DualButton/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/DualButton/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/DualButton/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/DualButton/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### DualRelay
```
TF/DualRelay/U/<id>/E/monoflopDone
   required: # this tag is not part of the data structure
     relay: String <[1, 2]>
     state: Boolean <true,false>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/DualRelay/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     monoflopParameters: Set <min: 0 max: 2>
     monoflopParameters:
      required: # this tag is not part of the data structure
        period: Number <from: 0 to: 9223372036854775807>
        relay: String <[1, 2]>
        state: Boolean <true,false>
     relayState:
       required: # this tag is not part of the data structure
         relay1: Boolean <true,false>
         relay2: Boolean <true,false>
     selectedRelayStates: Set <min: 0 max: 2>
     selectedRelayStates:
      required: # this tag is not part of the data structure
        relay: String <[1, 2]>
        state: Boolean <true,false>
   
```
```
TF/DualRelay/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/DualRelay/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/DualRelay/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/DualRelay/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/DualRelay/U/<id>/S/state
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         relay1: Boolean <true,false>
         relay2: Boolean <true,false>
   
```

### DustDetector
```
TF/DustDetector/U/<id>/E/dustDensity
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 500>
   
```
```
TF/DustDetector/U/<id>/E/dustDensity/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 500>
   
```
```
TF/DustDetector/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     debouncePeriod: Number <from: 0 to: 9223372036854775807>
     dustDensityCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     dustDensityCallbackThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 500>
         min: Number <from: 0 to: 500>
         option: String <[x, o, i, <, >]>
     movingAverage: Number <from: 0 to: 100>
   
```
```
TF/DustDetector/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/DustDetector/U/<id>/S/debounce/period
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/DustDetector/U/<id>/S/dustDensity/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/DustDetector/U/<id>/S/dustDensity/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 500>
         min: Number <from: 0 to: 500>
         option: String <[x, o, i, <, >]>
   
```
```
TF/DustDetector/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/DustDetector/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/DustDetector/U/<id>/S/movingAverage
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 100>
   
```
```
TF/DustDetector/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### GPS
```
TF/GPS/U/<id>/E/altitude
   required: # this tag is not part of the data structure
     altitude: Number <from: -2147483648 to: 2147483647>
     geoidalSeparation: Number <from: -2147483648 to: 2147483647>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/GPS/U/<id>/E/coordinates
   required: # this tag is not part of the data structure
     epe: Number <from: -2147483648 to: 2147483647>
     ew: String <[E, W]>
     hdop: Number <from: -2147483648 to: 2147483647>
     latitude: Number <from: -9223372036854775808 to: 9223372036854775807>
     longitude: Number <from: -9223372036854775808 to: 9223372036854775807>
     ns: String <[N, S]>
     pdop: Number <from: -2147483648 to: 2147483647>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     vdop: Number <from: -2147483648 to: 2147483647>
   
```
```
TF/GPS/U/<id>/E/dateTime
   required: # this tag is not part of the data structure
     date: Number <from: 100 to: 311299>
     time: Number <from: 0 to: 235959999>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/GPS/U/<id>/E/motion
   required: # this tag is not part of the data structure
     course: Number <from: 0 to: 36000>
     speed: Number <from: 0 to: 9223372036854775807>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/GPS/U/<id>/E/status
   required: # this tag is not part of the data structure
     fix: String <[1, 2, 3]>
     satellitesUsed: Number <from: 0 to: 32767>
     satellitesView: Number <from: 0 to: 32767>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/GPS/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     altitudeCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     coordinatesCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     dateTimeCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     motionCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     restart: String <hot,warm,cold,factoryReset>
     statusCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/GPS/U/<id>/S/altitude/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/GPS/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/GPS/U/<id>/S/coordinates/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/GPS/U/<id>/S/dateTime/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/GPS/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/GPS/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/GPS/U/<id>/S/motion/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/GPS/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### GPSv2
```
TF/GPSv2/U/<id>/E/altitude
   required: # this tag is not part of the data structure
     altitude: Number <from: -2147483648 to: 2147483647>
     geoidalSeparation: Number <from: -2147483648 to: 2147483647>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/GPSv2/U/<id>/E/coordinates
   required: # this tag is not part of the data structure
     ew: String <[E, W]>
     latitude: Number <from: -9223372036854775808 to: 9223372036854775807>
     longitude: Number <from: -9223372036854775808 to: 9223372036854775807>
     ns: String <[N, S]>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/GPSv2/U/<id>/E/dateTime
   required: # this tag is not part of the data structure
     date: Number <from: 100 to: 311299>
     time: Number <from: 0 to: 235959999>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/GPSv2/U/<id>/E/motion
   required: # this tag is not part of the data structure
     course: Number <from: 0 to: 36000>
     speed: Number <from: 0 to: 9223372036854775807>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/GPSv2/U/<id>/E/status
   required: # this tag is not part of the data structure
     fix: Boolean <true,false>
     satellitesView: Number <from: 0 to: 32767>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/GPSv2/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     altitudeCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     coordinatesCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     dateTimeCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     fixLEDConfig: String <OFF,ON,HEARTBEAT,FIX,PPS>
     motionCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     restart: String <hot,warm,cold,factoryReset>
     statusCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     statusLEDConfig: String <OFF,ON,HEARTBEAT,STATUS>
   
```
```
TF/GPSv2/U/<id>/S/altitude/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/GPSv2/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/GPSv2/U/<id>/S/coordinates/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/GPSv2/U/<id>/S/dateTime/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/GPSv2/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/GPSv2/U/<id>/S/fix/led
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <OFF,ON,HEARTBEAT,FIX,PPS>
   
```
```
TF/GPSv2/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/GPSv2/U/<id>/S/motion/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/GPSv2/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/GPSv2/U/<id>/S/status/led
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <OFF,ON,HEARTBEAT,STATUS>
   
```

### HallEffect
```
TF/HallEffect/U/<id>/E/edgeCount
   required: # this tag is not part of the data structure
     count: Number <from: 0 to: 9223372036854775807>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     greater35Gauss: Boolean <true,false>
   
```
```
TF/HallEffect/U/<id>/E/edgeCount/reset
   required: # this tag is not part of the data structure
     count: Number <from: 0 to: 9223372036854775807>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     greater35Gauss: Boolean <true,false>
   
```
```
TF/HallEffect/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     edgeCountCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     edgeCountInterrupt: Number <from: 0 to: 9223372036854775807>
     edgeCountReset: Boolean <true,false>
   
```
```
TF/HallEffect/U/<id>/S/configuration
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/HallEffect/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/HallEffect/U/<id>/S/edgeCount/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/HallEffect/U/<id>/S/edgeCount/interrupt
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/HallEffect/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/HallEffect/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/HallEffect/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### Humidity
```
TF/Humidity/U/<id>/E/analogValue
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 4095>
   
```
```
TF/Humidity/U/<id>/E/analogValue/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 4095>
   
```
```
TF/Humidity/U/<id>/E/humidity
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 1000>
   
```
```
TF/Humidity/U/<id>/E/humidity/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 1000>
   
```
```
TF/Humidity/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     analogCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     analogValueCallbackThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 4095>
         min: Number <from: 0 to: 4095>
         option: String <[x, o, i, <, >]>
     debouncePeriod: Number <from: 0 to: 9223372036854775807>
     humidityCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     humidityCallbackThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 1000>
         min: Number <from: 0 to: 1000>
         option: String <[x, o, i, <, >]>
   
```
```
TF/Humidity/U/<id>/S/analogValue/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Humidity/U/<id>/S/analogValue/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 4095>
         min: Number <from: 0 to: 4095>
         option: String <[x, o, i, <, >]>
   
```
```
TF/Humidity/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/Humidity/U/<id>/S/debounce/period
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Humidity/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/Humidity/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/Humidity/U/<id>/S/humidity/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Humidity/U/<id>/S/humidity/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 1000>
         min: Number <from: 0 to: 1000>
         option: String <[x, o, i, <, >]>
   
```
```
TF/Humidity/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### IMU
```
TF/IMU/U/<id>/E/acceleration
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     x: Number <from: -32768 to: 32767>
     y: Number <from: -32768 to: 32767>
     z: Number <from: -32768 to: 32767>
   
```
```
TF/IMU/U/<id>/E/allData
   required: # this tag is not part of the data structure
     acceleration:
       required: # this tag is not part of the data structure
         timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
         x: Number <from: -32768 to: 32767>
         y: Number <from: -32768 to: 32767>
         z: Number <from: -32768 to: 32767>
     angularVelocity:
       required: # this tag is not part of the data structure
         timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
         x: Number <from: -32768 to: 32767>
         y: Number <from: -32768 to: 32767>
         z: Number <from: -32768 to: 32767>
     magneticField:
       required: # this tag is not part of the data structure
         timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
         x: Number <from: -32768 to: 32767>
         y: Number <from: -32768 to: 32767>
         z: Number <from: -32768 to: 32767>
     temperature:
       required: # this tag is not part of the data structure
         temperature: Number <from: -32768 to: 32767>
         timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/IMU/U/<id>/E/angularVelocity
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     x: Number <from: -32768 to: 32767>
     y: Number <from: -32768 to: 32767>
     z: Number <from: -32768 to: 32767>
   
```
```
TF/IMU/U/<id>/E/magneticField
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     x: Number <from: -32768 to: 32767>
     y: Number <from: -32768 to: 32767>
     z: Number <from: -32768 to: 32767>
   
```
```
TF/IMU/U/<id>/E/orientation
   required: # this tag is not part of the data structure
     heading: Number <from: -32768 to: 32767>
     pitch: Number <from: -32768 to: 32767>
     roll: Number <from: -32768 to: 32767>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/IMU/U/<id>/E/quaternion
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     w: Fraction <from: -1.0 to: 1.0>
     x: Fraction <from: -1.0 to: 1.0>
     y: Fraction <from: -1.0 to: 1.0>
     z: Fraction <from: -1.0 to: 1.0>
   
```
```
TF/IMU/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     accelerationCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     allDataCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     angularVelocityCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     leds: Boolean <true,false>
     magneticFieldCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     orientationCalculation: Boolean <true,false>
     orientationCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     quaternionCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     statusLED: Boolean <true,false>
   
```
```
TF/IMU/U/<id>/S/LEDs/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Boolean <true,false>
   
```
```
TF/IMU/U/<id>/S/acceleration/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMU/U/<id>/S/allData/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMU/U/<id>/S/angularVelocity/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMU/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/IMU/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/IMU/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/IMU/U/<id>/S/magneticField/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMU/U/<id>/S/orientation/calculation
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Boolean <true,false>
   
```
```
TF/IMU/U/<id>/S/orientation/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMU/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/IMU/U/<id>/S/quaternion/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMU/U/<id>/S/statusLED/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Boolean <true,false>
   
```

### IMUV2
```
TF/IMUV2/U/<id>/E/acceleration
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     x: Number <from: -32768 to: 32767>
     y: Number <from: -32768 to: 32767>
     z: Number <from: -32768 to: 32767>
   
```
```
TF/IMUV2/U/<id>/E/allData
   required: # this tag is not part of the data structure
     acceleration:
       required: # this tag is not part of the data structure
         timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
         x: Number <from: -32768 to: 32767>
         y: Number <from: -32768 to: 32767>
         z: Number <from: -32768 to: 32767>
     angularVelocity:
       required: # this tag is not part of the data structure
         timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
         x: Number <from: -32768 to: 32767>
         y: Number <from: -32768 to: 32767>
         z: Number <from: -32768 to: 32767>
     gravityVector:
       required: # this tag is not part of the data structure
         timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
         x: Number <from: -32768 to: 32767>
         y: Number <from: -32768 to: 32767>
         z: Number <from: -32768 to: 32767>
     linearAcceleration:
       required: # this tag is not part of the data structure
         timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
         x: Number <from: -32768 to: 32767>
         y: Number <from: -32768 to: 32767>
         z: Number <from: -32768 to: 32767>
     magneticField:
       required: # this tag is not part of the data structure
         timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
         x: Number <from: -32768 to: 32767>
         y: Number <from: -32768 to: 32767>
         z: Number <from: -32768 to: 32767>
     quaternion:
       required: # this tag is not part of the data structure
         timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
         w: Fraction <from: -1.0 to: 1.0>
         x: Fraction <from: -1.0 to: 1.0>
         y: Fraction <from: -1.0 to: 1.0>
         z: Fraction <from: -1.0 to: 1.0>
     temperature:
       required: # this tag is not part of the data structure
         temperature: Number <from: -32768 to: 32767>
         timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/IMUV2/U/<id>/E/angularVelocity
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     x: Number <from: -32768 to: 32767>
     y: Number <from: -32768 to: 32767>
     z: Number <from: -32768 to: 32767>
   
```
```
TF/IMUV2/U/<id>/E/gravityVector
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     x: Number <from: -32768 to: 32767>
     y: Number <from: -32768 to: 32767>
     z: Number <from: -32768 to: 32767>
   
```
```
TF/IMUV2/U/<id>/E/linearAcceleration
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     x: Number <from: -32768 to: 32767>
     y: Number <from: -32768 to: 32767>
     z: Number <from: -32768 to: 32767>
   
```
```
TF/IMUV2/U/<id>/E/magneticField
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     x: Number <from: -32768 to: 32767>
     y: Number <from: -32768 to: 32767>
     z: Number <from: -32768 to: 32767>
   
```
```
TF/IMUV2/U/<id>/E/orientation
   required: # this tag is not part of the data structure
     heading: Number <from: -32768 to: 32767>
     pitch: Number <from: -32768 to: 32767>
     roll: Number <from: -32768 to: 32767>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/IMUV2/U/<id>/E/quaternion
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     w: Fraction <from: -1.0 to: 1.0>
     x: Fraction <from: -1.0 to: 1.0>
     y: Fraction <from: -1.0 to: 1.0>
     z: Fraction <from: -1.0 to: 1.0>
   
```
```
TF/IMUV2/U/<id>/E/temperature
   required: # this tag is not part of the data structure
     temperature: Number <from: -32768 to: 32767>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/IMUV2/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     accelerationCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     allDataCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     angularVelocityCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     gravityVectorCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     leds: Boolean <true,false>
     linearAccelerationCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     magneticFieldCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     orientationCalculation: Boolean <true,false>
     orientationCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     quaternionCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     sensorFusionMode: String <[0, 1, 2]>
     statusLED: Boolean <true,false>
     temperatureCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMUV2/U/<id>/S/LEDs/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Boolean <true,false>
   
```
```
TF/IMUV2/U/<id>/S/acceleration/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMUV2/U/<id>/S/allData/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMUV2/U/<id>/S/angularVelocity/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMUV2/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/IMUV2/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/IMUV2/U/<id>/S/gravityVector/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMUV2/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/IMUV2/U/<id>/S/linearAcceleration/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMUV2/U/<id>/S/magneticField/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMUV2/U/<id>/S/orientation/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMUV2/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/IMUV2/U/<id>/S/quaternion/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/IMUV2/U/<id>/S/sensorFusionMode
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2]>
   
```
```
TF/IMUV2/U/<id>/S/statusLED
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Boolean <true,false>
   
```
```
TF/IMUV2/U/<id>/S/temperature/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```

### Joystick
```
TF/Joystick/U/<id>/E/analogValue
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     x: Number <from: 0 to: 4095>
     y: Number <from: 0 to: 4095>
   
```
```
TF/Joystick/U/<id>/E/analogValue/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     x: Number <from: 0 to: 4095>
     y: Number <from: 0 to: 4095>
   
```
```
TF/Joystick/U/<id>/E/calibrate
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Boolean <true,false>
   
```
```
TF/Joystick/U/<id>/E/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     x: Number <from: -100 to: 100>
     y: Number <from: -100 to: 100>
   
```
```
TF/Joystick/U/<id>/E/position/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     x: Number <from: -100 to: 100>
     y: Number <from: -100 to: 100>
   
```
```
TF/Joystick/U/<id>/E/pressed
   required: # this tag is not part of the data structure
     pressed: Boolean <true,false>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/Joystick/U/<id>/E/released
   required: # this tag is not part of the data structure
     pressed: Boolean <true,false>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/Joystick/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     analogCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     analogValueCallbackThreshold:
       required: # this tag is not part of the data structure
         maxX: Number <from: 0 to: 4095>
         maxY: Number <from: 0 to: 4095>
         minX: Number <from: 0 to: 4095>
         minY: Number <from: 0 to: 4095>
         option: String <[x, o, i, <, >]>
     calibrate: Boolean <true,false>
     debouncePeriod: Number <from: 0 to: 9223372036854775807>
     positionCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     positionCallbackThreshold:
       required: # this tag is not part of the data structure
         maxX: Number <from: -100 to: 100>
         maxY: Number <from: -100 to: 100>
         minX: Number <from: -100 to: 100>
         minY: Number <from: -100 to: 100>
         option: String <[x, o, i, <, >]>
   
```
```
TF/Joystick/U/<id>/S/analogValue/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Joystick/U/<id>/S/analogValue/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         maxX: Number <from: 0 to: 4095>
         maxY: Number <from: 0 to: 4095>
         minX: Number <from: 0 to: 4095>
         minY: Number <from: 0 to: 4095>
         option: String <[x, o, i, <, >]>
   
```
```
TF/Joystick/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/Joystick/U/<id>/S/debounce/period
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Joystick/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/Joystick/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/Joystick/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/Joystick/U/<id>/S/position/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Joystick/U/<id>/S/position/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         maxX: Number <from: -100 to: 100>
         maxY: Number <from: -100 to: 100>
         minX: Number <from: -100 to: 100>
         minY: Number <from: -100 to: 100>
         option: String <[x, o, i, <, >]>
   
```

### LaserRangeFinder
```
TF/LaserRangeFinder/U/<id>/E/distance
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 4000>
   
```
```
TF/LaserRangeFinder/U/<id>/E/distance/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 4000>
   
```
```
TF/LaserRangeFinder/U/<id>/E/velocity
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: -127 to: 127>
   
```
```
TF/LaserRangeFinder/U/<id>/E/velocity/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: -127 to: 127>
   
```
```
TF/LaserRangeFinder/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     configuration:
       required: # this tag is not part of the data structure
         aquisitionCount: Number <from: 1 to: 255>
         measurementFrequency: Number <[from: 0 to: 0,from: 10 to: 500]>
         quickTermination: Boolean <true,false>
         thresholdValue: Number <from: 0 to: 255>
     debouncePeriod: Number <from: 0 to: 9223372036854775807>
     distanceCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     distanceCallbackThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 4000>
         min: Number <from: 0 to: 4000>
         option: String <[x, o, i, <, >]>
     laserEnabled: Boolean <true,false>
     mode:
       required: # this tag is not part of the data structure
         mode: String <distance,velocity_12_7,velocity_31_75,velocity_63_5,velocity_127>
     movingAverage:
       required: # this tag is not part of the data structure
         averagingDistance: Number <from: 0 to: 30>
         averagingVelocity: Number <from: 0 to: 30>
     velocityCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     velocityCallbackThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: -127 to: 127>
         min: Number <from: -127 to: 127>
         option: String <[x, o, i, <, >]>
   
```
```
TF/LaserRangeFinder/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/LaserRangeFinder/U/<id>/S/debounce/period
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/LaserRangeFinder/U/<id>/S/deviceConfiguration
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         aquisitionCount: Number <from: 1 to: 255>
         measurementFrequency: Number <[from: 0 to: 0,from: 10 to: 500]>
         quickTermination: Boolean <true,false>
         thresholdValue: Number <from: 0 to: 255>
   
```
```
TF/LaserRangeFinder/U/<id>/S/deviceMode
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         mode: String <distance,velocity_12_7,velocity_31_75,velocity_63_5,velocity_127>
   
```
```
TF/LaserRangeFinder/U/<id>/S/distance/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/LaserRangeFinder/U/<id>/S/distance/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 4000>
         min: Number <from: 0 to: 4000>
         option: String <[x, o, i, <, >]>
   
```
```
TF/LaserRangeFinder/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/LaserRangeFinder/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/LaserRangeFinder/U/<id>/S/laser
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Boolean <true,false>
   
```
```
TF/LaserRangeFinder/U/<id>/S/movingAverage
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         averagingDistance: Number <from: 0 to: 30>
         averagingVelocity: Number <from: 0 to: 30>
   
```
```
TF/LaserRangeFinder/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/LaserRangeFinder/U/<id>/S/sensorHardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         version: String <v1,v3>
   
```
```
TF/LaserRangeFinder/U/<id>/S/velocity/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/LaserRangeFinder/U/<id>/S/velocity/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: -127 to: 127>
         min: Number <from: -127 to: 127>
         option: String <[x, o, i, <, >]>
   
```

### LCD16x2
```
TF/LCD16x2/U/<id>/E/button/pressed
   required: # this tag is not part of the data structure
     button: Number <from: 0 to: 2>
     pressed: Boolean <true,false>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/LCD16x2/U/<id>/E/button/released
   required: # this tag is not part of the data structure
     button: Number <from: 0 to: 2>
     pressed: Boolean <true,false>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/LCD16x2/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     backlight: Boolean <true,false>
     clearDisplay: Boolean <true,false>
     customCharacters: Set <min: 0 max: 8>
     customCharacters:
      required: # this tag is not part of the data structure
        index: Number <from: 0 to: 7>
        pixels: Array <min: 8 max: 8>
     lines: Set <min: 0 max: 32>
     lines:
      required: # this tag is not part of the data structure
        line: Number <from: 0 to: 1>
        position: Number <from: 0 to: 15>
        text: String <min: 0 max: 16>
   
```
```
TF/LCD16x2/U/<id>/S/backlight
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Boolean <true,false>
   
```
```
TF/LCD16x2/U/<id>/S/configParameters
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/LCD16x2/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/LCD16x2/U/<id>/S/customCharacters
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Set <min: 0 max: 8>
     value:
      required: # this tag is not part of the data structure
        index: Number <from: 0 to: 7>
        pixels: Array <min: 8 max: 8>
   
```
```
TF/LCD16x2/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/LCD16x2/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/LCD16x2/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### LCD20x4
```
TF/LCD20x4/U/<id>/E/button
   required: # this tag is not part of the data structure
     button: Number <from: 0 to: 3>
     pressed: Boolean <true,false>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/LCD20x4/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     backlight: Boolean <true,false>
     clearDisplay: Boolean <true,false>
     customCharacters: Set <min: 0 max: 8>
     customCharacters:
      required: # this tag is not part of the data structure
        index: Number <from: 0 to: 7>
        pixels: Array <min: 8 max: 8>
     defaultTextCounter: Number <from: -1 to: 2147483647>
     defaultTexts: Set <min: 0 max: 4>
     defaultTexts:
      required: # this tag is not part of the data structure
        line: Number <from: 0 to: 3>
        text: String <min: 0 max: 20>
     lines: Set <min: 0 max: 80>
     lines:
      required: # this tag is not part of the data structure
        line: Number <from: 0 to: 3>
        position: Number <from: 0 to: 19>
        text: String <min: 0 max: 20>
   
```
```
TF/LCD20x4/U/<id>/S/backlight
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Boolean <true,false>
   
```
```
TF/LCD20x4/U/<id>/S/configParameters
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/LCD20x4/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/LCD20x4/U/<id>/S/customCharacters
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Set <min: 0 max: 8>
     value:
      required: # this tag is not part of the data structure
        index: Number <from: 0 to: 7>
        pixels: Array <min: 8 max: 8>
   
```
```
TF/LCD20x4/U/<id>/S/defaultText/counter
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: -1 to: 2147483647>
   
```
```
TF/LCD20x4/U/<id>/S/defaultText/texts
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Set <min: 0 max: 4>
     value:
      required: # this tag is not part of the data structure
        line: Number <from: 0 to: 3>
        text: String <min: 0 max: 20>
   
```
```
TF/LCD20x4/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/LCD20x4/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/LCD20x4/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### LEDStrip
```
TF/LEDStrip/U/<id>/E/frame/rendered
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 2147483647>
   
```
```
TF/LEDStrip/U/<id>/E/laging
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Boolean <true,false>
   
```
```
TF/LEDStrip/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     config:
       required: # this tag is not part of the data structure
         channelMapping: String <BGR,BGRW,BGWR,BRG,BRGW,BRWG,BWGR,BWRG,GBR,GBRW,GBWR,GRB,GRBW,GRWB,GWBR,GWRB,RBG,RBGW,RBWG,RGB,RGBW,RGWB,RWBG,RWGB>
         chipType: String <WS2801,WS2811,WS2812,WS2812RGBW,LPD8806,APA102>
         clockFrequencyOfICsInHz: Number <from: 10000 to: 2000000>
         frameDurationInMilliseconds: Number <from: 0 to: 9223372036854775807>
         numberOfLEDs: Number <from: 0 to: 320>
     LEDFrame:
       required: # this tag is not part of the data structure
         channels: Arrays: <[min: 1 max: 4,min: 1 max: 320]>
       optional: # this tag is not part of the data structure
         durationInMillis: Number <from: 0 to: 2147483647>
     LEDFrames: Array <min: 1 max: 2147483647>
       LEDFrame:
         required: # this tag is not part of the data structure
           channels: Arrays: <[min: 1 max: 4,min: 1 max: 320]>
         optional: # this tag is not part of the data structure
           durationInMillis: Number <from: 0 to: 2147483647>
   
```
```
TF/LEDStrip/U/<id>/S/config
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         channelMapping: String <BGR,BGRW,BGWR,BRG,BRGW,BRWG,BWGR,BWRG,GBR,GBRW,GBWR,GRB,GRBW,GRWB,GWBR,GWRB,RBG,RBGW,RBWG,RGB,RGBW,RGWB,RWBG,RWGB>
         chipType: String <WS2801,WS2811,WS2812,WS2812RGBW,LPD8806,APA102>
         clockFrequencyOfICsInHz: Number <from: 10000 to: 2000000>
         frameDurationInMilliseconds: Number <from: 0 to: 9223372036854775807>
         numberOfLEDs: Number <from: 0 to: 320>
   
```
```
TF/LEDStrip/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/LEDStrip/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/LEDStrip/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/LEDStrip/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### LinearPoti
```
TF/LinearPoti/U/<id>/E/analogValue
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 4095>
   
```
```
TF/LinearPoti/U/<id>/E/analogValue/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 4095>
   
```
```
TF/LinearPoti/U/<id>/E/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 100>
   
```
```
TF/LinearPoti/U/<id>/E/position/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 100>
   
```
```
TF/LinearPoti/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     analogCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     analogValueCallbackThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 4095>
         min: Number <from: 0 to: 4095>
         option: String <[x, o, i, <, >]>
     debouncePeriod: Number <from: 0 to: 9223372036854775807>
     positionCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     positionCallbackThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 100>
         min: Number <from: 0 to: 100>
         option: String <[x, o, i, <, >]>
   
```
```
TF/LinearPoti/U/<id>/S/analogValue/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/LinearPoti/U/<id>/S/analogValue/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 4095>
         min: Number <from: 0 to: 4095>
         option: String <[x, o, i, <, >]>
   
```
```
TF/LinearPoti/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/LinearPoti/U/<id>/S/debounce/period
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/LinearPoti/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/LinearPoti/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/LinearPoti/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/LinearPoti/U/<id>/S/position/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/LinearPoti/U/<id>/S/position/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 100>
         min: Number <from: 0 to: 100>
         option: String <[x, o, i, <, >]>
   
```

### Line
```
TF/Line/U/<id>/E/reflectivity
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 4095>
   
```
```
TF/Line/U/<id>/E/reflectivity/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 4095>
   
```
```
TF/Line/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     debouncePeriod: Number <from: 0 to: 9223372036854775807>
     reflectivityCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     reflectivityCallbackThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 4095>
         min: Number <from: 0 to: 4095>
         option: String <[x, o, i, <, >]>
   
```
```
TF/Line/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/Line/U/<id>/S/debounce/period
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Line/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/Line/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/Line/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/Line/U/<id>/S/reflectivity/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Line/U/<id>/S/reflectivity/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 4095>
         min: Number <from: 0 to: 4095>
         option: String <[x, o, i, <, >]>
   
```

### LoadCell
```
TF/LoadCell/U/<id>/E/weight
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: -50001 to: 50001>
   
```
```
TF/LoadCell/U/<id>/E/weight/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: -50001 to: 50001>
   
```
```
TF/LoadCell/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     configuration:
       required: # this tag is not part of the data structure
         gain: String <gain128X,gain64X,gain32X>
         rate: String <rate10Hz,rate80Hz>
     debouncePeriod: Number <from: 0 to: 9223372036854775807>
     movingAverage: Number <from: 1 to: 40>
     statusLED: Boolean <true,false>
     tare: Boolean <true,false>
     weightCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     weightCallbackThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: -50001 to: 50001>
         min: Number <from: -50001 to: 50001>
         option: String <[x, o, i, <, >]>
   
```
```
TF/LoadCell/U/<id>/S/LED
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Boolean <true,false>
   
```
```
TF/LoadCell/U/<id>/S/configuration
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         gain: String <gain128X,gain64X,gain32X>
         rate: String <rate10Hz,rate80Hz>
   
```
```
TF/LoadCell/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/LoadCell/U/<id>/S/debounce/period
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/LoadCell/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/LoadCell/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/LoadCell/U/<id>/S/movingAverage
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 1 to: 40>
   
```
```
TF/LoadCell/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/LoadCell/U/<id>/S/weight/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/LoadCell/U/<id>/S/weight/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: -50001 to: 50001>
         min: Number <from: -50001 to: 50001>
         option: String <[x, o, i, <, >]>
   
```

### Master
```
TF/Master/U/<id>/E/USB/voltage
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: -2147483648 to: 2147483647>
   
```
```
TF/Master/U/<id>/E/USB/voltage/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: -2147483648 to: 2147483647>
   
```
```
TF/Master/U/<id>/E/reset
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/Master/U/<id>/E/stack/current
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 2147483647>
   
```
```
TF/Master/U/<id>/E/stack/current/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 2147483647>
   
```
```
TF/Master/U/<id>/E/stack/voltage
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: -2147483648 to: 2147483647>
   
```
```
TF/Master/U/<id>/E/stack/voltage/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: -2147483648 to: 2147483647>
   
```
```
TF/Master/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     usbVoltageCallbackPeriod: Number <from: 0 to: 9223372036854775807>
   optional: # this tag is not part of the data structure
     currentCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     currentCallbackThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 4095>
         min: Number <from: 0 to: 4095>
         option: String <[x, o, i, <, >]>
     debouncePeriod: Number <from: 0 to: 9223372036854775807>
     reset: Boolean <true,false>
     stackVoltageCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     stackVoltageCallbackThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 4095>
         min: Number <from: 0 to: 4095>
         option: String <[x, o, i, <, >]>
     statusLED: Boolean <true,false>
     usbVoltageCallbackThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 4095>
         min: Number <from: 0 to: 4095>
         option: String <[x, o, i, <, >]>
   
```
```
TF/Master/U/<id>/S/USB/voltage/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Master/U/<id>/S/USB/voltage/callbackThreshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 4095>
         min: Number <from: 0 to: 4095>
         option: String <[x, o, i, <, >]>
   
```
```
TF/Master/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/Master/U/<id>/S/debounce/period
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Master/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/Master/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/Master/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/Master/U/<id>/S/stack/current/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Master/U/<id>/S/stack/current/callbackThreshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 4095>
         min: Number <from: 0 to: 4095>
         option: String <[x, o, i, <, >]>
   
```
```
TF/Master/U/<id>/S/stack/voltage/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Master/U/<id>/S/stack/voltage/callbackThreshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 4095>
         min: Number <from: 0 to: 4095>
         option: String <[x, o, i, <, >]>
   
```
```
TF/Master/U/<id>/S/statusLED/enabled
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Boolean <true,false>
   
```

### Moisture
```
TF/Moisture/U/<id>/E/moisture
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 4095>
   
```
```
TF/Moisture/U/<id>/E/moisture/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 4095>
   
```
```
TF/Moisture/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     debouncePeriod: Number <from: 0 to: 9223372036854775807>
     moistureCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     moistureCallbackThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 4095>
         min: Number <from: 0 to: 4095>
         option: String <[x, o, i, <, >]>
     movingAverage: Number <from: 0 to: 100>
   
```
```
TF/Moisture/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/Moisture/U/<id>/S/debounce/period
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Moisture/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/Moisture/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/Moisture/U/<id>/S/moisture/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Moisture/U/<id>/S/moisture/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 4095>
         min: Number <from: 0 to: 4095>
         option: String <[x, o, i, <, >]>
   
```
```
TF/Moisture/U/<id>/S/movingAverage
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 100>
   
```
```
TF/Moisture/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### MotionDetector
```
TF/MotionDetector/U/<id>/E/eventDetectionCycleEnded
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/MotionDetector/U/<id>/E/motionDetected
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/MotionDetector/U/<id>/I
   
```
```
TF/MotionDetector/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/MotionDetector/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/MotionDetector/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/MotionDetector/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### MotionDetectorV2
```
TF/MotionDetectorV2/U/<id>/E/eventDetectionCycleEnded
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/MotionDetectorV2/U/<id>/E/motionDetected
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/MotionDetectorV2/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     indicator:
       optional: # this tag is not part of the data structure
         bottom: Number <from: 0 to: 255>
         topLeft: Number <from: 0 to: 255>
         topRight: Number <from: 0 to: 255>
     sensitivity: Number <from: 0 to: 100>
   
```
```
TF/MotionDetectorV2/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/MotionDetectorV2/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/MotionDetectorV2/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/MotionDetectorV2/U/<id>/S/indicator
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       optional: # this tag is not part of the data structure
         bottom: Number <from: 0 to: 255>
         topLeft: Number <from: 0 to: 255>
         topRight: Number <from: 0 to: 255>
   
```
```
TF/MotionDetectorV2/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/MotionDetectorV2/U/<id>/S/sensitivity
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 100>
   
```

### MotorizedLinearPoti
```
TF/MotorizedLinearPoti/U/<id>/E/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 100>
   
```
```
TF/MotorizedLinearPoti/U/<id>/E/position/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 100>
   
```
```
TF/MotorizedLinearPoti/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     calibration: Boolean <true,false>
     motorPosition:
       required: # this tag is not part of the data structure
         driveMode: String <FAST,SMOOTH>
         holdPosition: Boolean <true,false>
         position: Number <from: 0 to: 100>
     positionCallbackConfiguration:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 100>
         min: Number <from: 0 to: 100>
         option: String <[x, o, i, <, >]>
         period: Number <from: 0 to: 9223372036854775807>
         valueHasToChange: Boolean <true,false>
     positionReachedCallbackConfiguration: Boolean <true,false>
   
```
```
TF/MotorizedLinearPoti/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/MotorizedLinearPoti/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/MotorizedLinearPoti/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/MotorizedLinearPoti/U/<id>/S/motor/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         driveMode: String <FAST,SMOOTH>
         holdPosition: Boolean <true,false>
         position: Number <from: 0 to: 100>
   
```
```
TF/MotorizedLinearPoti/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/MotorizedLinearPoti/U/<id>/S/position/callbackConfiguration
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 100>
         min: Number <from: 0 to: 100>
         option: String <[x, o, i, <, >]>
         period: Number <from: 0 to: 9223372036854775807>
         valueHasToChange: Boolean <true,false>
   
```
```
TF/MotorizedLinearPoti/U/<id>/S/position/reached/callbackConfiguration
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Boolean <true,false>
   
```

### MultiTouch
```
TF/MultiTouch/U/<id>/E/recalibrated
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/MultiTouch/U/<id>/E/touchState
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 8191>
   
```
```
TF/MultiTouch/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     electrodeConfig: Number <from: 0 to: 8191>
     recalibration: Boolean <true,false>
     sensitivity: Number <from: 5 to: 201>
   
```
```
TF/MultiTouch/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/MultiTouch/U/<id>/S/electrode/config
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 8191>
   
```
```
TF/MultiTouch/U/<id>/S/electrode/sensitivity
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 5 to: 201>
   
```
```
TF/MultiTouch/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/MultiTouch/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/MultiTouch/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### NfcRfid
```
TF/NfcRfid/U/<id>/E/tag/discovered
   required: # this tag is not part of the data structure
     id: String <regEx: [0-9A-F]{8-14}>
     latestDiscoveryTimeStamp: Number <from: 0 to: 9223372036854775807>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     type: String <MifareClassic,Type1,Type2>
   
```
```
TF/NfcRfid/U/<id>/E/tag/read
   required: # this tag is not part of the data structure
     id: String <regEx: [0-9A-F]{8-14}>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/NfcRfid/U/<id>/E/tag/vanished
   required: # this tag is not part of the data structure
     id: String <regEx: [0-9A-F]{8-14}>
     latestDiscoveryTimeStamp: Number <from: 0 to: 9223372036854775807>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     type: String <MifareClassic,Type1,Type2>
   
```
```
TF/NfcRfid/U/<id>/E/tag/written
   required: # this tag is not part of the data structure
     id: String <regEx: [0-9A-F]{8-14}>
     state: String <WritePageError,WritePageReady>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/NfcRfid/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     nfcWrite:
       required: # this tag is not part of the data structure
         tagID: String <regEx: [0-9A-F]{8}|[0-9A-F]{14}>
         value: Array <min: 0 max: 2147483647>
     scanningInterval: Number <from: 0 to: 9223372036854775807>
     tagID: String <regEx: [0-9A-F]{8-14}>
   
```
```
TF/NfcRfid/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/NfcRfid/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/NfcRfid/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/NfcRfid/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/NfcRfid/U/<id>/S/scanning/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```

### OutdoorWeather
```
TF/OutdoorWeather/U/<id>/E/batteryLow/<id>
   required: # this tag is not part of the data structure
     id: Number <from: 0 to: 255>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Boolean <true,false>
   
```
```
TF/OutdoorWeather/U/<id>/E/gustSpeed/<id>
   required: # this tag is not part of the data structure
     id: Number <from: 0 to: 255>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/OutdoorWeather/U/<id>/E/humidity/<id>
   required: # this tag is not part of the data structure
     id: Number <from: 0 to: 255>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 100>
   
```
```
TF/OutdoorWeather/U/<id>/E/rain/<id>
   required: # this tag is not part of the data structure
     id: Number <from: 0 to: 255>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/OutdoorWeather/U/<id>/E/temperature/<id>
   required: # this tag is not part of the data structure
     id: Number <from: 0 to: 255>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: -2147483648 to: 2147483647>
   
```
```
TF/OutdoorWeather/U/<id>/E/windDirection/<id>
   required: # this tag is not part of the data structure
     id: Number <from: 0 to: 255>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         direction: String <E,ENE,ERROR,ESE,N,NE,NNE,NNW,NW,S,SE,SSE,SSW,SW,W,WNW,WSW>
   
```
```
TF/OutdoorWeather/U/<id>/E/windSpeed/<id>
   required: # this tag is not part of the data structure
     id: Number <from: 0 to: 255>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/OutdoorWeather/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/OutdoorWeather/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/OutdoorWeather/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/OutdoorWeather/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/OutdoorWeather/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### ParticulateMatter
```
TF/ParticulateMatter/U/<id>/E/concentration
   required: # this tag is not part of the data structure
     pm10: Number <from: 0 to: 2147483647>
     pm100: Number <from: 0 to: 2147483647>
     pm25: Number <from: 0 to: 2147483647>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/ParticulateMatter/U/<id>/E/count
   required: # this tag is not part of the data structure
     greater03um: Number <from: 0 to: 2147483647>
     greater05um: Number <from: 0 to: 2147483647>
     greater100um: Number <from: 0 to: 2147483647>
     greater10um: Number <from: 0 to: 2147483647>
     greater25um: Number <from: 0 to: 2147483647>
     greater50um: Number <from: 0 to: 2147483647>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/ParticulateMatter/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     concentrationCallbackConfiguration:
       required: # this tag is not part of the data structure
         period: Number <from: 0 to: 9223372036854775807>
         valueHasToChange: Boolean <true,false>
     countCallbackConfiguration:
       required: # this tag is not part of the data structure
         period: Number <from: 0 to: 9223372036854775807>
         valueHasToChange: Boolean <true,false>
     enabled: Boolean <true,false>
   
```
```
TF/ParticulateMatter/U/<id>/S/concentration
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         period: Number <from: 0 to: 9223372036854775807>
         valueHasToChange: Boolean <true,false>
   
```
```
TF/ParticulateMatter/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/ParticulateMatter/U/<id>/S/count
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         period: Number <from: 0 to: 9223372036854775807>
         valueHasToChange: Boolean <true,false>
   
```
```
TF/ParticulateMatter/U/<id>/S/enabled
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Boolean <true,false>
   
```
```
TF/ParticulateMatter/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/ParticulateMatter/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/ParticulateMatter/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### PiezoSpeaker
```
TF/PiezoSpeaker/U/<id>/E/calibrated
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/PiezoSpeaker/U/<id>/E/finished
   required: # this tag is not part of the data structure
     morseCodeParameter:
       required: # this tag is not part of the data structure
         frequency: Number <from: 585 to: 7100>
         string: String <regEx: [\.\s-]{1,60}>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/PiezoSpeaker/U/<id>/E/started
   required: # this tag is not part of the data structure
     morseCodeParameter:
       required: # this tag is not part of the data structure
         frequency: Number <from: 585 to: 7100>
         string: String <regEx: [\.\s-]{1,60}>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/PiezoSpeaker/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     beepParameter:
       required: # this tag is not part of the data structure
         duration: Number <from: 0 to: 2147483647>
         frequency: Number <from: 585 to: 7100>
     calibrate: Boolean <true,false>
     morseCodeParameter:
       required: # this tag is not part of the data structure
         frequency: Number <from: 585 to: 7100>
         string: String <regEx: [\.\s-]{1,60}>
   
```
```
TF/PiezoSpeaker/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/PiezoSpeaker/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/PiezoSpeaker/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/PiezoSpeaker/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### PTC
```
TF/PTC/U/<id>/E/resistance
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 32767>
   
```
```
TF/PTC/U/<id>/E/resistance/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 32767>
   
```
```
TF/PTC/U/<id>/E/temperature
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: -24600 to: 84900>
   
```
```
TF/PTC/U/<id>/E/temperature/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: -24600 to: 84900>
   
```
```
TF/PTC/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     debouncePeriod: Number <from: 0 to: 9223372036854775807>
     noiseReductionFilter:
       required: # this tag is not part of the data structure
         filter: String <Hz_50,Hz_60>
     resistanceCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     temperatureCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     wireMode: String <[2, 3, 4]>
   
```
```
TF/PTC/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/PTC/U/<id>/S/debounce/period
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/PTC/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/PTC/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/PTC/U/<id>/S/noiseReductionFilter
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         filter: String <Hz_50,Hz_60>
   
```
```
TF/PTC/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/PTC/U/<id>/S/resistance/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/PTC/U/<id>/S/resistance/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/PTC/U/<id>/S/temperature/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/PTC/U/<id>/S/temperature/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/PTC/U/<id>/S/wireMode
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[2, 3, 4]>
   
```

### RealTimeClock
```
TF/RealTimeClock/U/<id>/E/alarm
   required: # this tag is not part of the data structure
     alarm: Boolean <true,false>
     dateTimeParameter:
       required: # this tag is not part of the data structure
         centisecond: Number <from: 0 to: 10>
         day: Number <from: 1 to: 31>
         hour: Number <from: 0 to: 59>
         minute: Number <from: 0 to: 59>
         month: Number <from: 1 to: 12>
         second: Number <from: 0 to: 59>
         weekday: String <monday,tuesday,wednesday,thursday,friday,saturday,sunday>
         year: Number <from: 2000 to: 2099>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/RealTimeClock/U/<id>/E/dateTime
   required: # this tag is not part of the data structure
     alarm: Boolean <true,false>
     dateTimeParameter:
       required: # this tag is not part of the data structure
         centisecond: Number <from: 0 to: 10>
         day: Number <from: 1 to: 31>
         hour: Number <from: 0 to: 59>
         minute: Number <from: 0 to: 59>
         month: Number <from: 1 to: 12>
         second: Number <from: 0 to: 59>
         weekday: String <monday,tuesday,wednesday,thursday,friday,saturday,sunday>
         year: Number <from: 2000 to: 2099>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/RealTimeClock/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     alarmParameter:
       required: # this tag is not part of the data structure
         day: Number <from: 1 to: 31>
         hour: Number <from: 0 to: 23>
         interval: Number <from: -1 to: 2147483647>
         minute: Number <from: 0 to: 59>
         month: Number <from: 1 to: 12>
         second: Number <from: 0 to: 59>
         weekday: String <monday,tuesday,wednesday,thursday,friday,saturday,sunday,disabled>
     dateTimeCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     dateTimeParameter:
       required: # this tag is not part of the data structure
         centisecond: Number <from: 0 to: 10>
         day: Number <from: 1 to: 31>
         hour: Number <from: 0 to: 59>
         minute: Number <from: 0 to: 59>
         month: Number <from: 1 to: 12>
         second: Number <from: 0 to: 59>
         weekday: String <monday,tuesday,wednesday,thursday,friday,saturday,sunday>
         year: Number <from: 2000 to: 2099>
     offset: Number <from: -128 to: 127>
   
```
```
TF/RealTimeClock/U/<id>/S/alarm
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         day: Number <from: 1 to: 31>
         hour: Number <from: 0 to: 23>
         interval: Number <from: -1 to: 2147483647>
         minute: Number <from: 0 to: 59>
         month: Number <from: 1 to: 12>
         second: Number <from: 0 to: 59>
         weekday: String <monday,tuesday,wednesday,thursday,friday,saturday,sunday,disabled>
   
```
```
TF/RealTimeClock/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/RealTimeClock/U/<id>/S/dateTime
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
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
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/RealTimeClock/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/RealTimeClock/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/RealTimeClock/U/<id>/S/offset
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: -128 to: 127>
   
```
```
TF/RealTimeClock/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### RemoteSwitch
```
TF/RemoteSwitch/U/<id>/E/switchingDone
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/RemoteSwitch/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     dimSocketBParameters:
       required: # this tag is not part of the data structure
         address: Number <from: 0 to: 67108863>
         dimValue: Number <from: 0 to: 15>
         unit: Number <from: 0 to: 15>
     repeats: Number <from: 0 to: 32767>
     switchSocketAParameters:
       required: # this tag is not part of the data structure
         houseCode: Number <from: 0 to: 31>
         receiverCode: Number <from: 0 to: 31>
         switchingValue: String <switchOn,switchOff>
     switchSocketBParameters:
       required: # this tag is not part of the data structure
         address: Number <from: 0 to: 67108863>
         switchingValue: String <switchOn,switchOff>
         unit: Number <from: 0 to: 15>
     switchSocketCParameters:
       required: # this tag is not part of the data structure
         deviceCode: Number <from: 1 to: 16>
         switchingValue: String <switchOn,switchOff>
         systemCode: String <[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P]>
   
```
```
TF/RemoteSwitch/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/RemoteSwitch/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/RemoteSwitch/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/RemoteSwitch/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/RemoteSwitch/U/<id>/S/repeats
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 32767>
   
```

### RemoteSwitchV2
```
TF/RemoteSwitchV2/U/<id>/E/switch/A
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/RemoteSwitchV2/U/<id>/E/switch/B
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/RemoteSwitchV2/U/<id>/E/switch/C
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/RemoteSwitchV2/U/<id>/E/switchingDone
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/RemoteSwitchV2/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     dimSocketBParameters:
       required: # this tag is not part of the data structure
         address: Number <from: 0 to: 67108863>
         dimValue: Number <from: 0 to: 15>
         unit: Number <from: 0 to: 15>
     remoteSwitchConfiguration:
       required: # this tag is not part of the data structure
         callbackEnabled: Boolean <true,false>
         minimumRepeats: Number <from: 0 to: 2147483647>
         remoteType: String <A,B,C>
     repeats: Number <from: 0 to: 2147483647>
     switchSocketAParameters:
       required: # this tag is not part of the data structure
         houseCode: Number <from: 0 to: 31>
         receiverCode: Number <from: 0 to: 31>
         switchingValue: String <switchOn,switchOff>
     switchSocketBParameters:
       required: # this tag is not part of the data structure
         address: Number <from: 0 to: 67108863>
         switchingValue: String <switchOn,switchOff>
         unit: Number <from: 0 to: 15>
     switchSocketCParameters:
       required: # this tag is not part of the data structure
         deviceCode: Number <from: 1 to: 16>
         switchingValue: String <switchOn,switchOff>
         systemCode: String <[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P]>
   
```
```
TF/RemoteSwitchV2/U/<id>/S/config
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         callbackEnabled: Boolean <true,false>
         minimumRepeats: Number <from: 0 to: 2147483647>
         remoteType: String <A,B,C>
   
```
```
TF/RemoteSwitchV2/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/RemoteSwitchV2/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/RemoteSwitchV2/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/RemoteSwitchV2/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/RemoteSwitchV2/U/<id>/S/repeats
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 2147483647>
   
```

### RGBLEDButton
```
TF/RGBLEDButton/U/<id>/E/button
   required: # this tag is not part of the data structure
     state: String <PRESSED,RELEASED>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/RGBLEDButton/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     color:
       required: # this tag is not part of the data structure
         blue: Number <from: 0 to: 255>
         green: Number <from: 0 to: 255>
         red: Number <from: 0 to: 255>
   
```
```
TF/RGBLEDButton/U/<id>/S/color
   required: # this tag is not part of the data structure
     color:
       required: # this tag is not part of the data structure
         blue: Number <from: 0 to: 255>
         green: Number <from: 0 to: 255>
         red: Number <from: 0 to: 255>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/RGBLEDButton/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/RGBLEDButton/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/RGBLEDButton/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/RGBLEDButton/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### RotaryEncoder
```
TF/RotaryEncoder/U/<id>/E/count
   required: # this tag is not part of the data structure
     reset: Boolean <true,false>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: -900 to: 900>
   
```
```
TF/RotaryEncoder/U/<id>/E/count/reached
   required: # this tag is not part of the data structure
     reset: Boolean <true,false>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: -900 to: 900>
   
```
```
TF/RotaryEncoder/U/<id>/E/count/reset
   required: # this tag is not part of the data structure
     reset: Boolean <true,false>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: -900 to: 900>
   
```
```
TF/RotaryEncoder/U/<id>/E/pressed
   required: # this tag is not part of the data structure
     pressed: Boolean <true,false>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/RotaryEncoder/U/<id>/E/released
   required: # this tag is not part of the data structure
     pressed: Boolean <true,false>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/RotaryEncoder/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     countCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     countReset: Boolean <true,false>
     countThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: -150 to: 150>
         min: Number <from: -150 to: 150>
         option: String <[x, o, i, <, >]>
     debouncePeriod: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/RotaryEncoder/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/RotaryEncoder/U/<id>/S/count/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/RotaryEncoder/U/<id>/S/count/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: -150 to: 150>
         min: Number <from: -150 to: 150>
         option: String <[x, o, i, <, >]>
   
```
```
TF/RotaryEncoder/U/<id>/S/debounce/period
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/RotaryEncoder/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/RotaryEncoder/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/RotaryEncoder/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### RotaryPoti
```
TF/RotaryPoti/U/<id>/E/analogValue
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 4095>
   
```
```
TF/RotaryPoti/U/<id>/E/analogValue/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 4095>
   
```
```
TF/RotaryPoti/U/<id>/E/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 100>
   
```
```
TF/RotaryPoti/U/<id>/E/position/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 100>
   
```
```
TF/RotaryPoti/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     analogValueCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     analogValueCallbackThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 4095>
         min: Number <from: 0 to: 4095>
         option: String <[x, o, i, <, >]>
     debouncePeriod: Number <from: 0 to: 9223372036854775807>
     positionCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     positionCallbackThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: -150 to: 150>
         min: Number <from: -150 to: 150>
         option: String <[x, o, i, <, >]>
   
```
```
TF/RotaryPoti/U/<id>/S/analogValue/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/RotaryPoti/U/<id>/S/analogValue/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 4095>
         min: Number <from: 0 to: 4095>
         option: String <[x, o, i, <, >]>
   
```
```
TF/RotaryPoti/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/RotaryPoti/U/<id>/S/debounce/period
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/RotaryPoti/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/RotaryPoti/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/RotaryPoti/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/RotaryPoti/U/<id>/S/position/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/RotaryPoti/U/<id>/S/position/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: -150 to: 150>
         min: Number <from: -150 to: 150>
         option: String <[x, o, i, <, >]>
   
```

### SegmentDisplay4x7
```
TF/SegmentDisplay4x7/U/<id>/E/counterFinished
   required: # this tag is not part of the data structure
     finished: Boolean <true,false>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     counterParameters:
       required: # this tag is not part of the data structure
         from: Number <from: -999 to: 9999>
         increment: Number <from: -999 to: 9999>
         length: Number <from: 0 to: 9223372036854775807>
         to: Number <from: -999 to: 9999>
   
```
```
TF/SegmentDisplay4x7/U/<id>/E/counterStarted
   required: # this tag is not part of the data structure
     finished: Boolean <true,false>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     counterParameters:
       required: # this tag is not part of the data structure
         from: Number <from: -999 to: 9999>
         increment: Number <from: -999 to: 9999>
         length: Number <from: 0 to: 9223372036854775807>
         to: Number <from: -999 to: 9999>
   
```
```
TF/SegmentDisplay4x7/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     counterParameter:
       required: # this tag is not part of the data structure
         from: Number <from: -999 to: 9999>
         increment: Number <from: -999 to: 9999>
         length: Number <from: 0 to: 9223372036854775807>
         to: Number <from: -999 to: 9999>
     segments:
       required: # this tag is not part of the data structure
         bits: Array <min: 4 max: 4>
         bits: Number <from: 0 to: 128>
         brightness: Number <from: 0 to: 7>
         colon: Boolean <true,false>
   
```
```
TF/SegmentDisplay4x7/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/SegmentDisplay4x7/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/SegmentDisplay4x7/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/SegmentDisplay4x7/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/SegmentDisplay4x7/U/<id>/S/segments
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         bits: Array <min: 4 max: 4>
         bits: Number <from: 0 to: 128>
         brightness: Number <from: 0 to: 7>
         colon: Boolean <true,false>
   
```

### Servo
```
TF/Servo/U/<id>/E/positionReached
   required: # this tag is not part of the data structure
     id: Number <from: 0 to: 6>
     position: Number <from: -32767 to: 32767>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/Servo/U/<id>/E/undervoltage
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 32767>
   
```
```
TF/Servo/U/<id>/E/velocityReached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/Servo/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     minimumVoltage: Number <from: 5000 to: 2147483647>
     outputVoltage: Number <from: 2000 to: 9000>
     servos: Set <min: 0 max: 7>
     servos:
      required: # this tag is not part of the data structure
        acceleration: Number <from: 0 to: 65536>
        degree:
          required: # this tag is not part of the data structure
            max: Number <from: -32767 to: 32767>
            min: Number <from: -32767 to: 32767>
        enabled: Boolean <true,false>
        id: Number <from: 0 to: 6>
        period: Number <from: 1 to: 65536>
        position: Number <from: -32767 to: 32767>
        pulseWidth:
          required: # this tag is not part of the data structure
            max: Number <from: -32767 to: 32767>
            min: Number <from: -32767 to: 32767>
        velocity: Number <from: 0 to: 65536>
     statusLED: Boolean <true,false>
   
```
```
TF/Servo/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/Servo/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/Servo/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/Servo/U/<id>/S/minimumVoltage
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 5000 to: 2147483647>
   
```
```
TF/Servo/U/<id>/S/outputVoltage
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 2000 to: 9000>
   
```
```
TF/Servo/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/Servo/U/<id>/S/servos
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Set <min: 0 max: 7>
     value:
      required: # this tag is not part of the data structure
        acceleration: Number <from: 0 to: 65536>
        degree:
          required: # this tag is not part of the data structure
            max: Number <from: -32767 to: 32767>
            min: Number <from: -32767 to: 32767>
        enabled: Boolean <true,false>
        id: Number <from: 0 to: 6>
        period: Number <from: 1 to: 65536>
        position: Number <from: -32767 to: 32767>
        pulseWidth:
          required: # this tag is not part of the data structure
            max: Number <from: -32767 to: 32767>
            min: Number <from: -32767 to: 32767>
        velocity: Number <from: 0 to: 65536>
   
```
```
TF/Servo/U/<id>/S/statusLED
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Boolean <true,false>
   
```

### SolidState
```
TF/SolidState/U/<id>/E/monoflopDone
   required: # this tag is not part of the data structure
     state: Boolean <true,false>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/SolidState/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     monoflopParameters:
       required: # this tag is not part of the data structure
         period: Number <from: 0 to: 9223372036854775807>
         state: Boolean <true,false>
     state: Boolean <true,false>
   
```
```
TF/SolidState/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/SolidState/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/SolidState/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/SolidState/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/SolidState/U/<id>/S/state
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Boolean <true,false>
   
```

### SoundIntensity
```
TF/SoundIntensity/U/<id>/E/soundIntensity
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 10000>
   
```
```
TF/SoundIntensity/U/<id>/E/soundIntensity/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 10000>
   
```
```
TF/SoundIntensity/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     debouncePeriod: Number <from: 0 to: 9223372036854775807>
     intensityCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     intensityCallbackThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 10000>
         min: Number <from: 0 to: 10000>
         option: String <[x, o, i, <, >]>
   
```
```
TF/SoundIntensity/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/SoundIntensity/U/<id>/S/debounce/period
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/SoundIntensity/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/SoundIntensity/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/SoundIntensity/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/SoundIntensity/U/<id>/S/soundIntensity/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/SoundIntensity/U/<id>/S/soundIntensity/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 10000>
         min: Number <from: 0 to: 10000>
         option: String <[x, o, i, <, >]>
   
```

### SoundPressureLevel
```
TF/SoundPressureLevel/U/<id>/E/decibel
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 65535>
   
```
```
TF/SoundPressureLevel/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     configuration:
       required: # this tag is not part of the data structure
         fftSize: String <Size128,Size256,Size512,Size1024>
         weighting: String <A,B,C,D,Z,ITU_R_468>
     decibelCallbackConfiguration:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 65535>
         min: Number <from: 0 to: 65535>
         option: String <[x, o, i, <, >]>
         period: Number <from: 0 to: 9223372036854775807>
         valueHasToChange: Boolean <true,false>
     spectrumCallbackConfiguration: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/SoundPressureLevel/U/<id>/S/configuration
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         fftSize: String <Size128,Size256,Size512,Size1024>
         weighting: String <A,B,C,D,Z,ITU_R_468>
   
```
```
TF/SoundPressureLevel/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/SoundPressureLevel/U/<id>/S/decibel
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 65535>
         min: Number <from: 0 to: 65535>
         option: String <[x, o, i, <, >]>
         period: Number <from: 0 to: 9223372036854775807>
         valueHasToChange: Boolean <true,false>
   
```
```
TF/SoundPressureLevel/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/SoundPressureLevel/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/SoundPressureLevel/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/SoundPressureLevel/U/<id>/S/spectrum
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```

### Stepper
```
TF/Stepper/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     allDataPeriod: Number <from: 0 to: 9223372036854775807>
     currentPosition: Number <from: 0 to: 2147483647>
     decay: Number <from: 0 to: 65535>
     driveMode: String <forward,backward,stop,fullBrake>
     enable: Boolean <true,false>
     maxVelocity: Number <from: 0 to: 2147483647>
     minimumVoltage: Number <from: 6000 to: 2147483647>
     motorCurrent: Number <from: 100 to: 2291>
     speedRamp:
       required: # this tag is not part of the data structure
         acceleration: Number <from: 0 to: 2147483647>
         deacceleration: Number <from: 0 to: 2147483647>
     stepMode: String <fullStep,halfStep,quarterStep,eighthStep>
     steps: Number <from: 0 to: 2147483647>
     syncRect: Boolean <true,false>
     targetPosition: Number <from: 0 to: 9223372036854775807>
     timeBase: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Stepper/U/<id>/S/allDataPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Stepper/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/Stepper/U/<id>/S/currentPosition
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 2147483647>
   
```
```
TF/Stepper/U/<id>/S/decay
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 65535>
   
```
```
TF/Stepper/U/<id>/S/driveMode
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <forward,backward,stop,fullBrake>
   
```
```
TF/Stepper/U/<id>/S/enabled
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Boolean <true,false>
   
```
```
TF/Stepper/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/Stepper/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/Stepper/U/<id>/S/minimumVoltage
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 6000 to: 2147483647>
   
```
```
TF/Stepper/U/<id>/S/motorCurrent
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 100 to: 2291>
   
```
```
TF/Stepper/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/Stepper/U/<id>/S/rectification
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Boolean <true,false>
   
```
```
TF/Stepper/U/<id>/S/stepMode
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <fullStep,halfStep,quarterStep,eighthStep>
   
```
```
TF/Stepper/U/<id>/S/steps
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 2147483647>
   
```
```
TF/Stepper/U/<id>/S/targetPosition
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 2147483647>
   
```
```
TF/Stepper/U/<id>/S/timeBase
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Stepper/U/<id>/S/velocity/velocity
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 2147483647>
   
```
```
TF/Stepper/U/<id>/S/velocityMax
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 2147483647>
   
```

### Temperature
```
TF/Temperature/U/<id>/E/temperature
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: -2500 to: 8500>
   
```
```
TF/Temperature/U/<id>/E/temperature/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: -2500 to: 8500>
   
```
```
TF/Temperature/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     debouncePeriod: Number <from: 0 to: 9223372036854775807>
     mode:
       required: # this tag is not part of the data structure
         mode: String <Fast,Slow>
     temperatureCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     temperatureThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: -2500 to: 8500>
         min: Number <from: -2500 to: 8500>
         option: String <[x, o, i, <, >]>
   
```
```
TF/Temperature/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/Temperature/U/<id>/S/debounce/period
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Temperature/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/Temperature/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/Temperature/U/<id>/S/mode
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         mode: String <Fast,Slow>
   
```
```
TF/Temperature/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/Temperature/U/<id>/S/temperature/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/Temperature/U/<id>/S/temperature/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: -2500 to: 8500>
         min: Number <from: -2500 to: 8500>
         option: String <[x, o, i, <, >]>
   
```

### TemperatureIR
```
TF/TemperatureIR/U/<id>/E/ambientTemperature
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: -400 to: 1250>
   
```
```
TF/TemperatureIR/U/<id>/E/ambientTemperature/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: -400 to: 1250>
   
```
```
TF/TemperatureIR/U/<id>/E/objectTemperature
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: -700 to: 3800>
   
```
```
TF/TemperatureIR/U/<id>/E/objectTemperature/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: -700 to: 3800>
   
```
```
TF/TemperatureIR/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     ambientTemperatureCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     ambientTemperatureCallbackThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: -400 to: 1250>
         min: Number <from: -400 to: 1250>
         option: String <[x, o, i, <, >]>
     debouncePeriod: Number <from: 0 to: 9223372036854775807>
     objectTemperatureCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     objectTemperatureCallbackThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: -700 to: 3800>
         min: Number <from: -700 to: 3800>
         option: String <[x, o, i, <, >]>
   
```
```
TF/TemperatureIR/U/<id>/S/ambientTemperature/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/TemperatureIR/U/<id>/S/ambientTemperature/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: -400 to: 1250>
         min: Number <from: -400 to: 1250>
         option: String <[x, o, i, <, >]>
   
```
```
TF/TemperatureIR/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/TemperatureIR/U/<id>/S/debounce/period
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/TemperatureIR/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/TemperatureIR/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/TemperatureIR/U/<id>/S/objectTemperature/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/TemperatureIR/U/<id>/S/objectTemperature/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: -700 to: 3800>
         min: Number <from: -700 to: 3800>
         option: String <[x, o, i, <, >]>
   
```
```
TF/TemperatureIR/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### ThermalImaging
```
TF/ThermalImaging/U/<id>/E/image/highContrast
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 4800 max: 4800>
   
```
```
TF/ThermalImaging/U/<id>/E/image/temperature
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 4800 max: 4800>
   
```
```
TF/ThermalImaging/U/<id>/E/statistics
   required: # this tag is not part of the data structure
     flatFieldCorrection: String <neverCommanded,complete,imminent,inProgress>
     imageResolution: String <from_0_to_655K,from_0_to_6553K>
     spotMeterStatistics:
       required: # this tag is not part of the data structure
         maximumTemperature: Number <from: 0 to: 65535>
         meanTemperature: Number <from: 0 to: 65535>
         minimumTemperature: Number <from: 1 to: 65535>
         pixelCount: Number <from: 1 to: 4800>
     temperatureState:
       required: # this tag is not part of the data structure
         ffcFocalPlainArrayTemperature: Number <from: 0 to: 65535>
         ffcHousingTemperature: Number <from: 0 to: 65535>
         focalPlainArrayTemperature: Number <from: 0 to: 65535>
         housingTemperature: Number <from: 0 to: 65535>
         overtemperature: Boolean <true,false>
         shutterLockout: Boolean <true,false>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   
```
```
TF/ThermalImaging/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     imageTransferConfig: String <none,contrast,temperature>
     resolution: String <from_0_to_655K,from_0_to_6553K>
     spotMeterConfig:
       required: # this tag is not part of the data structure
         columnEnd: Number <from: 1 to: 79>
         columnStart: Number <from: 0 to: 78>
         rowEnd: Number <from: 1 to: 59>
         rowStart: Number <from: 0 to: 58>
   
```
```
TF/ThermalImaging/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/ThermalImaging/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/ThermalImaging/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/ThermalImaging/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/ThermalImaging/U/<id>/S/resolution
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     value: String <from_0_to_655K,from_0_to_6553K>
   
```
```
TF/ThermalImaging/U/<id>/S/spotMeterConfig
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         columnEnd: Number <from: 1 to: 79>
         columnStart: Number <from: 0 to: 78>
         rowEnd: Number <from: 1 to: 59>
         rowStart: Number <from: 0 to: 58>
   
```
```
TF/ThermalImaging/U/<id>/S/transferConfig
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <none,contrast,temperature>
   
```

### ThermoCouple
```
TF/ThermoCouple/U/<id>/E/error
   required: # this tag is not part of the data structure
     openCircuit: Boolean <true,false>
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     voltage: Boolean <true,false>
   
```
```
TF/ThermoCouple/U/<id>/E/temperature
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: -21000 to: 180000>
   
```
```
TF/ThermoCouple/U/<id>/E/temperature/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: -21000 to: 180000>
   
```
```
TF/ThermoCouple/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     configuration:
       required: # this tag is not part of the data structure
         averaging: String <sample_1,sample_2,sample_4,sample_8,sample_16>
         filter: String <Hz_50,Hz_60>
         type: String <B,E,J,K,N,R,S,T,G8,G32>
     debouncePeriod: Number <from: 0 to: 9223372036854775807>
     temperatureCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     temperatureCallbackThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: -21000 to: 180000>
         min: Number <from: -21000 to: 180000>
         option: String <[x, o, i, <, >]>
   
```
```
TF/ThermoCouple/U/<id>/S/configuration
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         averaging: String <sample_1,sample_2,sample_4,sample_8,sample_16>
         filter: String <Hz_50,Hz_60>
         type: String <B,E,J,K,N,R,S,T,G8,G32>
   
```
```
TF/ThermoCouple/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/ThermoCouple/U/<id>/S/debounce/period
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/ThermoCouple/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/ThermoCouple/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/ThermoCouple/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/ThermoCouple/U/<id>/S/temperature/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/ThermoCouple/U/<id>/S/temperature/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: -21000 to: 180000>
         min: Number <from: -21000 to: 180000>
         option: String <[x, o, i, <, >]>
   
```

### Tilt
```
TF/Tilt/U/<id>/E/tiltState
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 2>
   
```
```
TF/Tilt/U/<id>/I
   
```
```
TF/Tilt/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/Tilt/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/Tilt/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/Tilt/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```

### UVLight
```
TF/UVLight/U/<id>/E/uvLight
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 328000>
   
```
```
TF/UVLight/U/<id>/E/uvLight/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 328000>
   
```
```
TF/UVLight/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     debouncePeriod: Number <from: 0 to: 9223372036854775807>
     movingAverage: Number <from: 0 to: 100>
     uvLightCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     uvLightCallbackThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 328000>
         min: Number <from: 0 to: 328000>
         option: String <[x, o, i, <, >]>
   
```
```
TF/UVLight/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/UVLight/U/<id>/S/debounce/period
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/UVLight/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/UVLight/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/UVLight/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/UVLight/U/<id>/S/uvLight/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/UVLight/U/<id>/S/uvLight/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 328000>
         min: Number <from: 0 to: 328000>
         option: String <[x, o, i, <, >]>
   
```

### VoltageCurrent
```
TF/VoltageCurrent/U/<id>/E/current
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 36000>
   
```
```
TF/VoltageCurrent/U/<id>/E/current/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 36000>
   
```
```
TF/VoltageCurrent/U/<id>/E/power
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 720000>
   
```
```
TF/VoltageCurrent/U/<id>/E/power/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 720000>
   
```
```
TF/VoltageCurrent/U/<id>/E/voltage
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: -50001 to: 50001>
   
```
```
TF/VoltageCurrent/U/<id>/E/voltage/reached
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: -50001 to: 50001>
   
```
```
TF/VoltageCurrent/U/<id>/I
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
   optional: # this tag is not part of the data structure
     calibration:
       required: # this tag is not part of the data structure
         gainDivisor: Number <from: 1 to: 2147483647>
         gainMultiplier: Number <from: 1 to: 2147483647>
     configuration:
       required: # this tag is not part of the data structure
         averaging: String <AVERAGING_1,AVERAGING_4,AVERAGING_16,AVERAGING_64,AVERAGING_128,AVERAGING_256,AVERAGING_512,AVERAGING_1024>
         currentConversionTime: String <CONVERSION_140us,CONVERSION_204us,CONVERSION_332us,CONVERSION_588us,CONVERSION_1100us,CONVERSION_2116us,CONVERSION_4156us,CONVERSION_8244us>
         voltageConversionTime: String <CONVERSION_140us,CONVERSION_204us,CONVERSION_332us,CONVERSION_588us,CONVERSION_1100us,CONVERSION_2116us,CONVERSION_4156us,CONVERSION_8244us>
     currentCalbackThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 36000>
         min: Number <from: 0 to: 36000>
         option: String <[x, o, i, <, >]>
     currentCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     debouncePeriod: Number <from: 0 to: 9223372036854775807>
     powerCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     powerCallbackThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 720000>
         min: Number <from: 0 to: 720000>
         option: String <[x, o, i, <, >]>
     voltageCallbackPeriod: Number <from: 0 to: 9223372036854775807>
     voltageCallbackThreshold:
       required: # this tag is not part of the data structure
         max: Number <from: -5001 to: 5001>
         min: Number <from: -5001 to: 5001>
         option: String <[x, o, i, <, >]>
   
```
```
TF/VoltageCurrent/U/<id>/S/calibration
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         gainDivisor: Number <from: 1 to: 2147483647>
         gainMultiplier: Number <from: 1 to: 2147483647>
   
```
```
TF/VoltageCurrent/U/<id>/S/configuration
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         averaging: String <AVERAGING_1,AVERAGING_4,AVERAGING_16,AVERAGING_64,AVERAGING_128,AVERAGING_256,AVERAGING_512,AVERAGING_1024>
         currentConversionTime: String <CONVERSION_140us,CONVERSION_204us,CONVERSION_332us,CONVERSION_588us,CONVERSION_1100us,CONVERSION_2116us,CONVERSION_4156us,CONVERSION_8244us>
         voltageConversionTime: String <CONVERSION_140us,CONVERSION_204us,CONVERSION_332us,CONVERSION_588us,CONVERSION_1100us,CONVERSION_2116us,CONVERSION_4156us,CONVERSION_8244us>
   
```
```
TF/VoltageCurrent/U/<id>/S/connection
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[online, offline]>
   
```
```
TF/VoltageCurrent/U/<id>/S/current/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/VoltageCurrent/U/<id>/S/current/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 36000>
         min: Number <from: 0 to: 36000>
         option: String <[x, o, i, <, >]>
   
```
```
TF/VoltageCurrent/U/<id>/S/debounce/period
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/VoltageCurrent/U/<id>/S/firmware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/VoltageCurrent/U/<id>/S/hardware
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Array <min: 0 max: 2147483647>
     value: Number <from: -32768 to: 32767>
   
```
```
TF/VoltageCurrent/U/<id>/S/position
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: String <[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d]>
   
```
```
TF/VoltageCurrent/U/<id>/S/power/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/VoltageCurrent/U/<id>/S/power/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: 0 to: 720000>
         min: Number <from: 0 to: 720000>
         option: String <[x, o, i, <, >]>
   
```
```
TF/VoltageCurrent/U/<id>/S/voltage/callbackPeriod
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value: Number <from: 0 to: 9223372036854775807>
   
```
```
TF/VoltageCurrent/U/<id>/S/voltage/threshold
   required: # this tag is not part of the data structure
     timeStamp: Number <from: 0 to: 9223372036854775807> # default: Current time in nano seconds
     value:
       required: # this tag is not part of the data structure
         max: Number <from: -5001 to: 5001>
         min: Number <from: -5001 to: 5001>
         option: String <[x, o, i, <, >]>
   
```
