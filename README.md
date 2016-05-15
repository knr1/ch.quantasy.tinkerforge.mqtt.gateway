# TiMqWay
ch.quantasy.tinkerforge.mqtt.gateway
Provides an [MQTT] view to the [Tinkerforge](tm) world.
The underlying idea of TiMqWay is a self-explaining mqtt view which dynamically provides access to known Tinkerforge devices. 

### Ideology
Each Device provides the following interface:
* description Each device describes its abilites via the description topic.
* intent Each intention to a device is sent via its provided intent topic. 
* status Each device describes its actual status via its specialized status topics.
* event Each device provides all events via its specialied event topics.

Tha language used within this project is [YAML]. Hence, all you need to learn is how to publish and subscribe to MQTT and how to write YAML.

The root topic of TiMqWay is: TF

Devices are not represented within their 'stack', but loosly coupled so a device might even change its stack but is still accessible in MQTT at the same location (TF/device-type/UID).

### Installation
In order to start TiMqWay 
* Developers way: clone and build the project
* Users way: download the latest [TiMqWay.jar] and executed via
```sh
$ java -jar TiMqWay.jar
```
Thus, if you subscribe to TF/# you will immediately get the description info for the 'virtual' Manager.

In order to interact with some specific Tinkerforge-Stack, the following has to be sent to MQTT:
```sh
Topic: TF/Manager/stack/address/add
Message: localhost
```
or any other address IP or name will work, if there is an actual Tinkerforge stack accessible.


 [tinkerforge]:<http://www.tinkerforge.com/en>
 [MQTT]: <http://mqtt.org/>
 [TiMqWay.jar]: <https://prof.hti.bfh.ch/knr1/TiMqWay.jar>
