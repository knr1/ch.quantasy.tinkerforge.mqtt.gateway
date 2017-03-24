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
         temperature --- timestamp: [0..9223372036854775807]\n value: [-2500..8500]\n
           reached --- timestamp: [0..9223372036854775807]\n value: [-2500..8500]\n
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
This logical service allows a user to add or remove a true tinkerforge stack. As soon a a stack is connected, the service takes care of the 
connected Bricks and Bricklets.
```  
     TF/ManagerService/I/stack/address/add
       hostName: <String>
       prot: [0..4223..65535]
```
```  
     TF/ManagerService/I/stack/address/remove
       hostName: <String>
       prot: [0..4223..65535]
```
```
     TF/ManagerService/S/stack/<hostName>/connected
        timestamp: [0..9223372036854775807]
        value: [true|false]
```
```
     TF/ManagerService/E/stack/address/connected
        - timestamp: [0..9223372036854775807]
          value:
            hostName: <String>
            prot: [0..4223..65535]
```
```
     TF/ManagerService/E/stack/address/disconnected
        - timestamp: [0..9223372036854775807]
          value:
            hostName: <String>
            prot: [0..4223..65536]
```
```
     TF/ManagerService/E/stack/address/added
        - timestamp: [0..9223372036854775807]
          value:
            hostName: <String>
            prot: [0..4223..65536]
```
```
     TF/ManagerService/E/stack/address/removed
        - timestamp: [0..9223372036854775807]
          value:
            hostName: <String>
            prot: [0..4223..65536]
```

### BrickDC
```
    TF/DC/<uid>/I/acceleration
        [0..2147483647]
```
```
    TF/DC/<uid>/I/driverMode
        [0|1]
```
```
    TF/DC/<uid>/I/enabled
        [true|false]
```
```
    TF/DC/<uid>/I/fullBrake
        [true|false]
```
```
    TF/DC/<uid>/I/minimumVoltage
        [6000..2147483647]
```
```
    TF/DC/<uid>/I/pwmFrequency
        [1..20000]
```
```
    TF/DC/<uid>/I/velocity/velocity
         [-32767..32767]
```
```
    TF/DC/<uid>/I/velocity/callbackPeriod
        [0..2147483647]
```
```     
    TF/DC/<uid>/S/acceleration
         [0..2147483647]
```
```     
    TF/DC/<uid>/S/driverMode
         [0|1]
```
```     
    TF/DC/<uid>/S/enabled
         [true|false]
```
```     
    TF/DC/<uid>/S/minimumVoltage
         [6..2147483647]
```
```     
    TF/DC/<uid>/S/pwmFrequency
         [1..20000]
```
```     
    TF/DC/<uid>/S/velocity/velocity
         [-32767..32767]
```
```     
    TF/DC/<uid>/S/velocityCallbackPeriod
         [0..2147483647]
```
```
     TF/DC/<uid>/E/fullBrake
        - timestamp: [0..9223372036854775807]
          value: [0..9223372036854775807]
```
```
    TF/DC/<uid>/E/underVoltage 
        - timestamp: [0..9223372036854775807]
          value: [0..2147483647]
```
```
    TF/DC/<uid>/E/velocity
        - timestamp: [0..9223372036854775807]
          value: [0..32767]
```
```
    TF/DC/<uid>/E/velocity/reached
         - timestamp: [0..9223372036854775807]
           value: [0..32767]
```
```     
    TF/DC/<uid>/E/emergencyShutdown
         - timestamp: [0..9223372036854775807]
           value: [0..9223372036854775807]
```

### BrickIMU
```
    TF/IMU/<uid>/I/acceleration/callbackPeriod
        [0..9223372036854775807]
```
```
    TF/IMU/<uid>/I/allData/callbackPeriod
        [0..9223372036854775807]
```
```
    TF/IMU/<uid>/I/angularVelocity/callbackPeriod
        [0..9223372036854775807]
```
```
    TF/IMU/<uid>/I/magneticField/callbackPeriod
        [0..9223372036854775807]
```
```
    TF/IMU/<uid>/I/orientation/callbackPeriod
        [0..9223372036854775807]
```
```
    TF/IMU/<uid>/I/quaternion/callbackPeriod
        [0..9223372036854775807]
```
```
    TF/IMU/<uid>/I/LEDs
        [true|false]
```
```
    TF/IMU/<uid>/I/statusLED
        [true|false]
```
```
    TF/IMU/<uid>/I/orientationCalculation
        [true|false]
```
```
    TF/IMU/<uid>/S/acceleration/callbackPeriod
        [0..9223372036854775807]
```
```
    TF/IMU/<uid>/S/allData/callbackPeriod
        [0..9223372036854775807]
```
```
    TF/IMU/<uid>/S/angularVelocity/callbackPeriod
        [0..9223372036854775807]
```
```
    TF/IMU/<uid>/S/magneticField/callbackPeriod
        [0..9223372036854775807]
```
```
    TF/IMU/<uid>/S/orientation/callbackPeriod
        [0..9223372036854775807]
```
```
    TF/IMU/<uid>/S/quaternion/callbackPeriod
        [0..9223372036854775807]
```
```
    TF/IMU/<uid>/S/LEDs
        [true|false]
```
```
    TF/IMU/<uid>/S/statusLED
        [true|false]
```
```
    TF/IMU/<uid>/S/orientationCalculation
        [true|false]
```
```
    TF/IMU/<uid>/E/acceleration
        - timestamp: <UNSIGNED_NUMBER_63>
          x: [-32768..32767]
          y: [-32768..32767]
          z: [-32768..32767]
```
```
    TF/IMU/<uid>/E/angularVelocity
        - timestamp: [0..9223372036854775807]
          x: [-32768..32767]
          y: [-32768..32767]
          z: [-32768..32767]
```
```
    TF/IMU/<uid>/E/magneticField
        - timestamp: [0..9223372036854775807]
          x: [-32768..32767]
          y: [-32768..32767]
          z: [-32768..32767]
```
```
    TF/IMU/<uid>/E/angularVelocity
        - timestamp: [0..9223372036854775807]
          heading: [-32768..32767]
          roll: [-32768..32767]
          pitch: [-32768..32767]
```
```
    TF/IMU/<uid>/E/quaternion
        - timestamp: [0..9223372036854775807]
          w: [-32768..32767]
          x: [-32768..32767]
          y: [-32768..32767]
          z: [-32768..32767]
```
```
    TF/IMU/<uid>/E/allData
        - timestamp: [0..9223372036854775807]
          acceleration:
              x: [-32768..32767]
              y: [-32768..32767]
              z: [-32768..32767]
          angularVelocity:
              x: [-32768..32767]
              y: [-32768..32767]
              z: [-32768..32767]
          magneticField:
              x: [-32768..32767]
              y: [-32768..32767]
              z: [-32768..32767]
          angularVelocity:
              heading: [-32768..32767]
              roll: [-32768..32767]
              pitch: [-32768..32767]
          quaternion:
              w: [-32768..32767]
              x: [-32768..32767]
              y: [-32768..32767]
              z: [-32768..32767]
```
### BrickIMUV2
```
    TF/IMU/<uid>/I/acceleration/callbackPeriod
        [0..9223372036854775807]
```
```
    TF/IMU/<uid>/I/allData/callbackPeriod
        [0..9223372036854775807]
```
```
    TF/IMU/<uid>/I/angularVelocity/callbackPeriod
        [0..9223372036854775807]
```
```
    TF/IMU/<uid>/I/magneticField/callbackPeriod
        [0..9223372036854775807]
```
```
    TF/IMU/<uid>/I/orientation/callbackPeriod
        [0..9223372036854775807]
```
```
    TF/IMU/<uid>/I/quaternion/callbackPeriod
        [0..9223372036854775807]
```
```
    TF/IMU/<uid>/I/LEDs
        [true|false]
```
```
    TF/IMU/<uid>/I/statusLED
        [true|false]
```
```
    TF/IMU/<uid>/I/sensorFusionMode
        [0..1]
```
```
    TF/IMU/<uid>/S/acceleration/callbackPeriod
        [0..9223372036854775807]
```
```
    TF/IMU/<uid>/S/allData/callbackPeriod
        [0..9223372036854775807]
```
```
    TF/IMU/<uid>/S/angularVelocity/callbackPeriod
        [0..9223372036854775807]
```
```
    TF/IMU/<uid>/S/magneticField/callbackPeriod
        [0..9223372036854775807]
```
```
    TF/IMU/<uid>/S/orientation/callbackPeriod
        [0..9223372036854775807]
```
```
    TF/IMU/<uid>/S/quaternion/callbackPeriod
        [0..9223372036854775807]
```
```
    TF/IMU/<uid>/S/temperature/callbackPeriod
        [0..9223372036854775807]
```
```
    TF/IMU/<uid>/S/LEDs
        [true|false]
```
```
    TF/IMU/<uid>/S/statusLED
        [true|false]
```
```
    TF/IMU/<uid>/S/sensorFustionMode
        [0..1]
```
```
    TF/IMU/<uid>/E/acceleration
        - timestamp: [0..9223372036854775807]
          x: [-32768..32767]
          y: [-32768..32767]
          z: [-32768..32767]
```
```
    TF/IMU/<uid>/E/angularVelocity
        - timestamp: [0..9223372036854775807]
          x: [-32768..32767]
          y: [-32768..32767]
          z: [-32768..32767]
```
```
    TF/IMU/<uid>/E/magneticField
        - timestamp: [0..9223372036854775807]
          x: [-32768..32767]
          y: [-32768..32767]
          z: [-32768..32767]
```
```
    TF/IMU/<uid>/E/angularVelocity
        - timestamp: [0..9223372036854775807]
          heading: [-32768..32767]
          roll: [-32768..32767]
          pitch: [-32768..32767]
```
```
    TF/IMU/<uid>/E/quaternion
        - timestamp: [0..9223372036854775807]
          w: [-32768..32767]
          x: [-32768..32767]
          y: [-32768..32767]
          z: [-32768..32767]
```
```
    TF/IMU/<uid>/E/temperature
        - timestamp: [0..9223372036854775807]
          value: [-128..127]
```
```
    TF/IMU/<uid>/E/allData
        - timestamp: [0..9223372036854775807]
          acceleration:
              x: [-32768..32767]
              y: [-32768..32767]
              z: [-32768..32767]
          angularVelocity:
              x: [-32768..32767]
              y: [-32768..32767]
              z: [-32768..32767]
          magneticField:
              x: [-32768..32767]
              y: [-32768..32767]
              z: [-32768..32767]
          angularVelocity:
              heading: [-32768..32767]
              roll: [-32768..32767]
              pitch: [-32768..32767]
          quaternion:
              w: [-32768..32767]
              x: [-32768..32767]
              y: [-32768..32767]
              z: [-32768..32767]
          temperature: [-128..127]
          calibrationStatus: [0..255]    
```
### BrickMaster
```
TF/Master/<uid>/I/debounce/period
    [0..9223372036854775807]
```
```
TF/Master/<uid>/I/stack/current/callbackPeriod
    [0..9223372036854775807]
```
```
TF/Master/<uid>/I/stack/current/callbackThreshold
    [0..9223372036854775807]
```
```
TF/Master/<uid>/I/stack/voltage/callbackPeriod
    [0..9223372036854775807]
```
```
TF/Master/<uid>/I/stack/voltage/callbackThreshold
    [0..9223372036854775807]
```
```
TF/Master/<uid>/I/usb/voltage/callbackPeriod
    [0..9223372036854775807]
```
```
TF/Master/<uid>/I/statusLED/enabled
    [true|false]
```

        publishDescription(getContract().EVENT_RESET, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().EVENT_STACK_VOLTAGE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]");
        publishDescription(getContract().EVENT_STACK_VOLTAGE_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]");

        publishDescription(getContract().EVENT_USB_VOLTAGE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]");
        publishDescription(getContract().EVENT_USB_VOLTAGE_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]");

        publishDescription(getContract().EVENT_STACK_CURRENT, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0.." + Integer.MAX_VALUE + "]");
        publishDescription(getContract().EVENT_STACK_CURRENT_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0.." + Integer.MAX_VALUE + "]");

        publishDescription(getContract().STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().STATUS_STACK_CURRENT_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().STATUS_STATUS_LED_ENABLED, "[true|false]");
        publishDescription(getContract().STATUS_CURRENT_CALLBACK_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..4095]\n max: [0..4095]");
        publishDescription(getContract().STATUS_STACK_VOLTAGE_CALLBACK_PERIOD, "[" + Long.MIN_VALUE + ".." + Long.MAX_VALUE + "]");
        publishDescription(getContract().STATUS_STACK_VOLTAGE_CALLBACK_THRESHOLD, "[option: [x|o|i|<|>]\n min: [0..4095]\n max: [0..4095]");

        publishDescription(getContract().STATUS_USB_VOLTAGE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");

* BrickServo
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
* BrickletGPS
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
* BrickletNFC (Type1 / Type2)
* BrickletPiezoSpeaker
* BrickletPTC
* BrickletRealtimeClock
* BrickletRemoteSwitch
* BrickletRotaryEncoder
* BrickletRotaryPoti
* BrickletSegmentDisplay4x7
* BrickletSolidStateRelay
* BrickletSoundIntensity
* BrickletTemperature
* BrickletTemperatureIR
* BrickletThermoCouple
* BrickletTilt
* BrickletUVLight
* BrickletVoltageCurrent

