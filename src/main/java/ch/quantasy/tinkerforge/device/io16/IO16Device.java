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
package ch.quantasy.tinkerforge.device.io16;

import ch.quantasy.gateway.message.intent.io16.DeviceConfiguration;
import ch.quantasy.gateway.message.intent.io16.DeviceInterrupt;
import ch.quantasy.gateway.message.intent.io16.DeviceMonoflopParameters;
import ch.quantasy.gateway.message.intent.io16.DevicePortAEdgeCountConfig;
import ch.quantasy.gateway.message.intent.io16.IO16Intent;
import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickletIO16;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.Collection;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class IO16Device extends GenericDevice<BrickletIO16, IO16DeviceCallback, IO16Intent> implements BrickletIO16.MonoflopDoneListener {

    private final SortedMap<String, DeviceMonoflopParameters> monoflopParametersMap;
    private final SortedMap<Short, DevicePortAEdgeCountConfig> edgeCountConfigMap;
    private final SortedMap<String, DeviceConfiguration> configurationMap;
    private SortedSet<DeviceInterrupt> deviceInterruptSet;
    private Long debouncePeriod;

    public IO16Device(TinkerforgeStack stack, BrickletIO16 device) throws NotConnectedException, TimeoutException {
        super(stack, device, new IO16Intent());
        this.monoflopParametersMap = new TreeMap<>();
        this.edgeCountConfigMap = new TreeMap<>();
        this.configurationMap = new TreeMap<>();
        this.deviceInterruptSet = new TreeSet<>();
    }

    @Override
    protected void addDeviceListeners(BrickletIO16 device) {
        device.addInterruptListener(super.getCallback());
        device.addMonoflopDoneListener(super.getCallback());
        device.addMonoflopDoneListener(this);

        setMonoflop(monoflopParametersMap.values());
        setConfiguration(configurationMap.values());
        addInterrupt(deviceInterruptSet);
        for (DevicePortAEdgeCountConfig edgeCountConfig : edgeCountConfigMap.values()) {
            setPortAEdgeCountConfig(edgeCountConfig);
        }

        if (debouncePeriod != null) {
            setDebouncePeriod(this.debouncePeriod);
        }

    }

    @Override
    protected void removeDeviceListeners(BrickletIO16 device) {
        device.removeInterruptListener(super.getCallback());
        device.removeMonoflopDoneListener(super.getCallback());
        device.removeMonoflopDoneListener(this);

    }

    @Override
    public void update(IO16Intent intent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setDebouncePeriod(Long period) {
        try {
            getDevice().setDebouncePeriod(period);
            this.debouncePeriod = getDevice().getDebouncePeriod();
            super.getCallback().debouncePeriodChanged(this.debouncePeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(IO16Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setPortAEdgeCountConfig(DevicePortAEdgeCountConfig config) {
        try {
            getDevice().setEdgeCountConfig(config.getPin(), config.getEdgeType().getValue(), config.getDebounce());
            this.edgeCountConfigMap.put(config.getPin(), new DevicePortAEdgeCountConfig(getDevice().getEdgeCountConfig(config.getPin())));
            super.getCallback().portAEdgeCountConfigChanged(new TreeSet(this.edgeCountConfigMap.values()));
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(IO16Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setMonoflop(Collection<DeviceMonoflopParameters> parameters) {
        try {
            for (DeviceMonoflopParameters parameter : parameters) {
                getDevice().setPortMonoflop(parameter.getPort(), (short) (1 << parameter.getPin()), (short) (parameter.getValue() << parameter.getPin()), parameter.getPeriod());
//                DeviceMonoflopParameters newParam = new DeviceMonoflopParameters(getDevice().getPortMonoflop(parameter.getPort(), parameter.getPin()));
//                this.monoflopParametersMap.put(parameter.getPortPin(), newParam);
            }
            super.getCallback().deviceMonoflopParametersChanged(new TreeSet(this.monoflopParametersMap.values()));
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(IO16Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setConfiguration(Collection<DeviceConfiguration> parameters) {
        try {
            for (DeviceConfiguration parameter : parameters) {
                getDevice().setPortConfiguration(parameter.getPort(), (short) (1 << parameter.getPin()), parameter.getDirection(), parameter.getValue());
            }

            List<DeviceConfiguration> newConfigurations = DeviceConfiguration.getDeviceConfiguration(getDevice().getPortConfiguration('a'));
            newConfigurations.addAll(DeviceConfiguration.getDeviceConfiguration(getDevice().getPortConfiguration('b')));
            for (DeviceConfiguration newConfig : newConfigurations) {
                this.configurationMap.put(newConfig.getPortPin(), newConfig);
            }
            super.getCallback().deviceConfigurationChanged(new TreeSet(this.configurationMap.values()));
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(IO16Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addInterrupt(Collection<DeviceInterrupt> interrupts) {
        try {

            SortedSet<DeviceInterrupt> newInterrupts = new TreeSet(deviceInterruptSet);
            newInterrupts.addAll(interrupts);
            short[] portInterrupts = DeviceInterrupt.getDeviceInterrupt(interrupts);
            getDevice().setPortInterrupt('a', portInterrupts[0]);
            getDevice().setPortInterrupt('b', portInterrupts[1]);
            newInterrupts.clear();
            newInterrupts.addAll(DeviceInterrupt.getDeviceInterrupt('a', getDevice().getPortInterrupt('a')));
            newInterrupts.addAll(DeviceInterrupt.getDeviceInterrupt('b', getDevice().getPortInterrupt('b')));
            this.deviceInterruptSet = newInterrupts;
            super.getCallback().interruptsChanged(new TreeSet(this.deviceInterruptSet));
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(IO16Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void removeInterrupt(Collection<DeviceInterrupt> interrupts) {
        try {
            for (DeviceInterrupt interrupt : interrupts) {
                getDevice().setPortInterrupt(interrupt.getPort(), (short) (1 << interrupt.getPin()));
            }

            List<DeviceConfiguration> newConfigurations = DeviceConfiguration.getDeviceConfiguration(getDevice().getPortConfiguration('a'));
            newConfigurations.addAll(DeviceConfiguration.getDeviceConfiguration(getDevice().getPortConfiguration('b')));
            for (DeviceConfiguration newConfig : newConfigurations) {
                this.configurationMap.put(newConfig.getPortPin(), newConfig);
            }
            super.getCallback().deviceConfigurationChanged(new TreeSet(this.configurationMap.values()));
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(IO16Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void monoflopDone(char port, short selectionMask, short valueMask) {
        for (int i = 0; i < 8; i++) {
            if ((selectionMask & (1 << i)) != 0) {
                DeviceConfiguration configuration = configurationMap.get("" + port + "" + i);
                if (configuration != null) {
                    configuration = new DeviceConfiguration(port, (short) i, configuration.getDirection(), (valueMask & (1 >> i)) != 0);
                    configurationMap.put(configuration.getPortPin(), configuration);
                    super.getCallback().deviceConfigurationChanged(new TreeSet(configurationMap.values()));

                }
            }
        }
    }

}
