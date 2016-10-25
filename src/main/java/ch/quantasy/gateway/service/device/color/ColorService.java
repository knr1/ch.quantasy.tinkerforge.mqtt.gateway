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
package ch.quantasy.gateway.service.device.color;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.color.ColorDevice;
import ch.quantasy.tinkerforge.device.color.ColorDeviceCallback;
import ch.quantasy.tinkerforge.device.color.DeviceColorCallbackThreshold;
import ch.quantasy.tinkerforge.device.color.DeviceConfiguration;

import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class ColorService extends AbstractDeviceService<ColorDevice, ColorServiceContract> implements ColorDeviceCallback {

    public ColorService(ColorDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new ColorServiceContract(device));
        addDescription(getContract().INTENT_COLOR_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().INTENT_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().INTENT_IllUMINANCE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().INTENT_COLOR_CALLBACK_THRESHOLD, "option: [x|o|i|<|>]\n minR: [0..65535]\n maxR: [0..65535]\n minG: [0..65535]\n maxG: [0..65535]\n minB: [0..65535]\n maxB: [0..65535]");
        addDescription(getContract().INTENT_COLOR_TEMPERATURE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().EVENT_COLOR, "timestamp: [0.." + Long.MAX_VALUE + "]\n red: [0..65535]\n green: [0..65535]\n blue: [0..65535]\n clear: [0..65535]\n");
        addDescription(getContract().EVENT_ILLUMINANCE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..65535]\n");
        addDescription(getContract().EVENT_COLOR_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n red: [0..65535]\n green: [0..65535]\n blue: [0..65535]\n clear: [0..65535]\n");
        addDescription(getContract().EVENT_COLOR_TEMPERATURE_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..65535]\n");
        addDescription(getContract().STATUS_COLOR_TEMPERATURE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().STATUS_ILLUMINANCE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().STATUS_COLOR_THRESHOLD, "option: [x|o|i|<|>]\n minR: [0..65535]\n maxR: [0..65535]\n minG: [0..65535]\n maxG: [0..65535]\n minB: [0..65535]\n maxB: [0..65535]");
        addDescription(getContract().STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
    }

    @Override
    public void messageArrived(String string, byte[] payload) throws Exception {

        if (string.startsWith(getContract().INTENT_DEBOUNCE_PERIOD)) {
            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setDebouncePeriod(period);
        }
        if (string.startsWith(getContract().INTENT_COLOR_CALLBACK_PERIOD)) {
            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setColorCallbackPeriod(period);
        }
        if (string.startsWith(getContract().INTENT_IllUMINANCE_CALLBACK_PERIOD)) {
            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setIlluminanceCallbackPeriod(period);
        }
        if (string.startsWith(getContract().INTENT_COLOR_CALLBACK_THRESHOLD)) {
            DeviceColorCallbackThreshold threshold = getMapper().readValue(payload, DeviceColorCallbackThreshold.class);
            getDevice().setColorCallbackThreshold(threshold);
        }
        if (string.startsWith(getContract().INTENT_COLOR_TEMPERATURE_CALLBACK_PERIOD)) {
            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setColorTemperatureCallbackPeriod(period);
        }
        if (string.startsWith(getContract().INTENT_CONFIGURATION)) {
            DeviceConfiguration config = getMapper().readValue(payload, DeviceConfiguration.class);
            getDevice().setConfig(config);
        }
        if (string.startsWith(getContract().INTENT_LIGHT_STATE)) {
            Boolean state = getMapper().readValue(payload, Boolean.class);
            getDevice().setLight(state);
        }

    }

    @Override
    public void color(int i, int i1, int i2, int i3) {
        addEvent(getContract().EVENT_COLOR, new ColorEvent(i, i1, i2, i3));
    }

    @Override
    public void colorReached(int i, int i1, int i2, int i3) {
        addEvent(getContract().EVENT_COLOR_REACHED, new ColorEvent(i, i1, i2, i3));
    }

    @Override
    public void illuminance(long i) {
        addEvent(getContract().EVENT_ILLUMINANCE, new IlluminanceEvent(i));
    }

    @Override
    public void colorTemperature(int i) {
        addEvent(getContract().EVENT_COLOR_TEMPERATURE_REACHED, new ColorTemperatureEvent(i));
    }

    @Override
    public void colorTemperatureCallbackPeriodChanged(long period) {
        addStatus(getContract().STATUS_COLOR_TEMPERATURE_CALLBACK_PERIOD, period);
    }

    @Override
    public void configurationChanged(DeviceConfiguration config) {
        addStatus(getContract().STATUS_CONFIGURATION, config);
    }

    @Override
    public void lightStatusChanged(boolean isLightOn) {
        addStatus(getContract().STATUS_LIGHT_STATE, isLightOn);
    }

    @Override
    public void colorCallbackPeriodChanged(long period) {
        addStatus(getContract().STATUS_COLOR_TEMPERATURE_CALLBACK_PERIOD, period);
    }

    @Override
    public void illuminanceCallbackPeriodChanged(long period) {
        addStatus(getContract().STATUS_ILLUMINANCE_CALLBACK_PERIOD, period);
    }

    @Override
    public void debouncePeriodChanged(long period) {
        addStatus(getContract().STATUS_DEBOUNCE_PERIOD, period);
    }

    @Override
    public void colorCallbackThresholdChanged(DeviceColorCallbackThreshold threshold) {
        addStatus(getContract().STATUS_COLOR_THRESHOLD, threshold);
    }

    public static class ColorEvent {

        protected long timestamp;
        protected int red;
        protected int green;
        protected int blue;
        protected int clear;

        public ColorEvent(int red, int green, int blue, int clear) {
            this(red, green, blue, clear, System.currentTimeMillis());
        }

        public ColorEvent(int red, int green, int blue, int clear, long timestamp) {
            this.timestamp = timestamp;
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.clear = clear;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public int getBlue() {
            return blue;
        }

        public int getClear() {
            return clear;
        }

        public int getGreen() {
            return green;
        }

        public int getRed() {
            return red;
        }

    }

    public static class ColorTemperatureEvent {

        long timestamp;
        long value;

        public ColorTemperatureEvent(long value) {
            this(value, System.currentTimeMillis());
        }

        public ColorTemperatureEvent(long value, long timeStamp) {
            this.value = value;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public long getValue() {
            return value;
        }

    }

    public static class IlluminanceEvent {

        long timestamp;
        long value;

        public IlluminanceEvent(long value) {
            this(value, System.currentTimeMillis());
        }

        public IlluminanceEvent(long value, long timeStamp) {
            this.value = value;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public long getValue() {
            return value;
        }

    }

}
