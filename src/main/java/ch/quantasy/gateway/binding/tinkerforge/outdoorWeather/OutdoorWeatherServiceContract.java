/*
 * /*
 *  *   "TiMqWay"
 *  *
 *  *    TiMqWay(tm): A gateway to provide an MQTT-View for the Tinkerforge(tm) world (Tinkerforge-MQTT-Gateway).
 *  *
 *  *    Copyright (c) 2016 Bern University of Applied Sciences (BFH),
 *  *    Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *  *    Quellgasse 21, CH-2501 Biel, Switzerland
 *  *
 *  *    Licensed under Dual License consisting of:
 *  *    1. GNU Affero General Public License (AGPL) v3
 *  *    and
 *  *    2. Commercial license
 *  *
 *  *
 *  *    1. This program is free software: you can redistribute it and/or modify
 *  *     it under the terms of the GNU Affero General Public License as published by
 *  *     the Free Software Foundation, either version 3 of the License, or
 *  *     (at your option) any later version.
 *  *
 *  *     This program is distributed in the hope that it will be useful,
 *  *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *     GNU Affero General Public License for more details.
 *  *
 *  *     You should have received a copy of the GNU Affero General Public License
 *  *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *  *
 *  *
 *  *    2. Licensees holding valid commercial licenses for TiMqWay may use this file in
 *  *     accordance with the commercial license agreement provided with the
 *  *     Software or, alternatively, in accordance with the terms contained in
 *  *     a written agreement between you and Bern University of Applied Sciences (BFH),
 *  *     Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *  *     Quellgasse 21, CH-2501 Biel, Switzerland.
 *  *
 *  *
 *  *     For further information contact <e-mail: reto.koenig@bfh.ch>
 *  *
 *  *
 */
package ch.quantasy.gateway.binding.tinkerforge.outdoorWeather;

import ch.quantasy.gateway.binding.tinkerforge.DeviceServiceContract;
import ch.quantasy.mqtt.gateway.client.message.Message;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.outdoorWeather.OutdoorWeatherDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class OutdoorWeatherServiceContract extends DeviceServiceContract {
    public final String EVENT_TEMPERATURE;
    public final String EVENT_HUMIDITY;
    public final String EVENT_GUST_SPEED;
    public final String EVENT_WIND_SPEED;
    public final String EVENT_RAIN;
    public final String EVENT_WIND_DIRECTION;
    public final String EVENT_BATTERY_LOW;
    
   

    public OutdoorWeatherServiceContract(OutdoorWeatherDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public OutdoorWeatherServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.OutdoorWeather.toString());
    }

    public OutdoorWeatherServiceContract(String id, String device) {
        super(id, device, OutdoorWeatherIntent.class);
        EVENT_TEMPERATURE=EVENT+"/"+"temperature";
        EVENT_HUMIDITY=EVENT+"/"+"humidity";
        EVENT_GUST_SPEED=EVENT+"/"+"gustSpeed";
        EVENT_WIND_SPEED=EVENT+"/"+"windSpeed";
        EVENT_RAIN=EVENT+"/"+"rain";
        EVENT_WIND_DIRECTION=EVENT+"/"+"windDirection";
        EVENT_BATTERY_LOW=EVENT+"/"+"batteryLow";
    }

    @Override
    public void setServiceMessageTopics(Map<String, Class<? extends Message>> messageTopicMap) {
        messageTopicMap.put(EVENT_TEMPERATURE, TemperatureEvent.class);
        messageTopicMap.put(EVENT_HUMIDITY, HumidityEvent.class);
        messageTopicMap.put(EVENT_GUST_SPEED, WindSpeedEvent.class);
        messageTopicMap.put(EVENT_WIND_SPEED, WindSpeedEvent.class);
        messageTopicMap.put(EVENT_RAIN, RainEvent.class);
        messageTopicMap.put(EVENT_WIND_DIRECTION, WindDirectionEvent.class);
        messageTopicMap.put(EVENT_BATTERY_LOW, BatteryLowEvent.class);
        
        
 
    }

    
}
