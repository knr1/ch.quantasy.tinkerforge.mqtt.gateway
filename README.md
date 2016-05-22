# TiMqWay
ch.quantasy.tinkerforge.mqtt.gateway
Provides an [MQTT] view to the [Tinkerforge](tm) world.
The underlying idea of TiMqWay is a self-explaining mqtt view which dynamically provides access to known Tinkerforge devices. 

### Ideology
Each Device provides the following interface:
* description Each device describes its abilities via the description topic.
* intent Each intention to a device is sent via its provided intent topic. 
* status Each device describes its actual status via its specialized status topics.
* event Each device provides all events via its specialized event topics. As there might be more events available than the mqtt broker is able to handle, all events are always covered within an array. Hence, there might be 0,1 or multiple events within one message.

The descriptive language used within this project is [YAML]. Hence, all you need to learn is how to publish and subscribe to MQTT and how to write YAML.

The root topic of TiMqWay is: TF

Devices are not represented within their 'stack', but loosly coupled so a device might even change its stack but is still accessible in MQTT at the same location (TF/device-type/UID).

### Installation
In order to install TiMqWay 
* Developers way: clone and build the project
* Users way: download the latest [TiMqWay.jar]
 
### Usage
You need Java (7 or higher) and a running MQTT-Server. You can start TiMqWay with the MQTT-Server-Parameter.

Then run the following command in order to use an MQTT-Server at localhost
```sh
$ java -jar TiMqWay.jar tcp://127.0.0.1:1883
```
Then run the following command in order to use an MQTT-Server at iot.eclipse.org:1883 (Not recommended as it is an open server everyone can read and write into)
```sh
$ java -jar TiMqWay.jar tcp://iot.eclipse.org:1883
```

Thus, if you subscribe to TF/# you will immediately get the description info for the 'virtual' Manager.

In order to interact with some specific Tinkerforge-Stack, the following has to be sent to MQTT:
```sh
Topic: TF/Manager/stack/address/add
Message: localhost
```
or any other address IP or name will work, if there is an actual Tinkerforge stack accessible.

### Tip
You might want to get an overview of TF using a graphical MQTT-Viewer i.e. [d3Viewer].

 [tinkerforge]:<http://www.tinkerforge.com/en>
 [MQTT]: <http://mqtt.org/>
 [TiMqWay.jar]: <https://prof.hti.bfh.ch/knr1/TiMqWay.jar>
 [d3Viewer]: <https://github.com/hardillb/d3-MQTT-Topic-Tree>
 [YAML]: <https://en.wikipedia.org/wiki/YAML>

### Example
Imagine, you have a running MQTT-Broker at 192.168.1.77
and start the TiMqWay with the argument 192.168.1.77 so it writes to the MQTT-Broker

Now there are...
* ... Stack1: one MasterBrick and one 4-7SegmentBricklet (uid: se1) at localhost
* ... Stack2: one MasterBrick and two CO2Bricklets (uid: tta uid: qwe) at 192.168.1.17

In order to know what is going on, you subscribe recursively to the following topic:
```sh
Topic: TF/#
```
In order to be able to use the bricklets, you need to connect the two stacks. Hence you publish the following message to the following topic at the MQTT-Broker
```sh
Topic: TF/Manager/stack/address/add
Message: localhost
```
Only when you receive the following message from the following topic
```sh
Topic: TF/Manager/event/stack/address/added
Message: --- - hostName: "localhost" port: 4223
```
then you proceed to add the second stack by publishing the following message to the following topic
```sh
Topic: TF/Manager/stack/address/add
Message: 192.168.1.17
```
Only when you receive the following message from the following topic
```sh
Topic: TF/Manager/event/stack/address/added
Message: --- - hostName: "192.168.1.17" port: 4223
```
then you are ready to access and manage the bricklets. The documentation for each Bricklet is given on the 'description' topic.

If you want to modify the behavior of the bricklet, write it to its intent state...

If you want to count from 0 to 999 with one step per second, using the SegmentDisplay4x7 (se1) you write the following message to the following topic:
```sh
Topic: TF/SegmentDisplay4x7/se1/intent/counter
Message: 
from: 0
to: 999
increment: 1
length: 1000
```
Please remember, it is a YAML structure, so you need the 'newline'.

Topic TF/SegmentDisplay4x7/se1/event/counterStarted will be updated with an array of number(s) (in this case usually only one number present, depending on the quality of the mqtt network throughput), representing the timestamp, when the counter started at Tinkerforge-Level.

After 999 seconds

Topic TF/SegmentDisplay4x7/se1/event/counterFinished will be updated with an array of number(s) (in this case usually only one number present, depending on the quality of the mqtt network throughput), representing the timestamp, when the counter finished at Tinkerforge-Level.


