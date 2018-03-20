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
package ch.quantasy.tinkerforge.device.stepper;

import ch.quantasy.gateway.binding.tinkerforge.motorizedLinearPoti.DriveMode;
import ch.quantasy.gateway.binding.tinkerforge.stepper.DeviceSpeedRamp;
import ch.quantasy.gateway.binding.tinkerforge.stepper.StepMode;
import ch.quantasy.gateway.binding.tinkerforge.stepper.StepperIntent;
import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickStepper;

import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class StepperDevice extends GenericDevice<BrickStepper, StepperDeviceCallback, StepperIntent> {

    public StepperDevice(TinkerforgeStack stack, BrickStepper device) throws NotConnectedException, TimeoutException {
        super(stack, device, new StepperIntent());
    }

    @Override
    protected void addDeviceListeners(BrickStepper device) {
        device.addAllDataListener(super.getCallback());
        device.addNewStateListener(super.getCallback());
        device.addPositionReachedListener(super.getCallback());
        device.addUnderVoltageListener(super.getCallback());

    }

    @Override
    protected void removeDeviceListeners(BrickStepper device) {
        device.removeAllDataListener(super.getCallback());
        device.removeNewStateListener(super.getCallback());
        device.removePositionReachedListener(super.getCallback());
        device.removeUnderVoltageListener(super.getCallback());
    }

    @Override
    public void update(StepperIntent intent) {
        if (intent == null) {
            return;
        }
        if (!intent.isValid()) {
            return;
        }
        if (intent.allDataPeriod != null) {
            try {
                getDevice().setAllDataPeriod(intent.allDataPeriod);
                getIntent().allDataPeriod = getDevice().getAllDataPeriod();
                super.getCallback().allDataPeriodChanged(getIntent().allDataPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(StepperDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.currentPosition != null) {
            try {
                getDevice().setCurrentPosition(intent.currentPosition);
                getIntent().currentPosition = getDevice().getCurrentPosition();
                super.getCallback().currentPositionChanged(getIntent().currentPosition);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(StepperDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.decay != null) {
            try {
                getDevice().setDecay(intent.decay);
                getIntent().decay = getDevice().getDecay();
                super.getCallback().decayChanged(getIntent().decay);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(StepperDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.maxVelocity != null) {
            try {
                getDevice().setMaxVelocity(intent.maxVelocity);
                getIntent().maxVelocity = getDevice().getMaxVelocity();
                super.getCallback().maxVelocityChanged(getIntent().maxVelocity);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(StepperDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.motorCurrent != null) {
            try {
                getDevice().setMotorCurrent(intent.motorCurrent);
                getIntent().motorCurrent = getDevice().getMotorCurrent();
                super.getCallback().motorCurrentChanged(getIntent().motorCurrent);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(StepperDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.minimumVoltage != null) {
            try {
                getDevice().setMinimumVoltage(intent.minimumVoltage);
                getIntent().minimumVoltage = getDevice().getMinimumVoltage();
                super.getCallback().minimumVoltageChanged(getIntent().minimumVoltage);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(StepperDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.speedRamp != null) {
            try {
                getDevice().setSpeedRamping(intent.speedRamp.getAcceleration(), intent.speedRamp.getDeacceleration());
                getIntent().speedRamp = new DeviceSpeedRamp(getDevice().getSpeedRamping());
                super.getCallback().speedRampChanged(getIntent().speedRamp);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(StepperDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.stepMode != null) {
            try {
                getDevice().setStepMode(intent.stepMode.getValue());
                getIntent().stepMode = StepMode.getModeFor(getDevice().getStepMode());
                super.getCallback().stepModeChanged(getIntent().stepMode);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(StepperDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.steps != null) {
            try {
                getDevice().setSteps(intent.steps);
                getIntent().steps = getDevice().getSteps();
                super.getCallback().stepsChanged(getIntent().steps);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(StepperDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.syncRect != null) {
            try {
                getDevice().setSyncRect(intent.syncRect);
                getIntent().syncRect = getDevice().isSyncRect();
                super.getCallback().syncRectChanged(getIntent().syncRect);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(StepperDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.targetPosition != null) {
            try {
                getDevice().setTargetPosition(intent.targetPosition);
                getIntent().targetPosition = getDevice().getTargetPosition();
                super.getCallback().targetPositionChanged(getIntent().targetPosition);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(StepperDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.timeBase != null) {
            try {
                getDevice().setTimeBase(intent.timeBase);
                getIntent().timeBase = getDevice().getTimeBase();
                super.getCallback().timeBaseChanged(getIntent().timeBase);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(StepperDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.enable != null) {
            try {
                if (intent.enable) {
                    getDevice().enable();
                } else {
                    getDevice().disable();
                }
                getIntent().enable = getDevice().isEnabled();
                super.getCallback().enabledChanged(getIntent().enable);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(StepperDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.driveMode != null) {
            try {
                switch (intent.driveMode) {
                    case backward:
                        getDevice().driveBackward();
                        getIntent().driveMode = intent.driveMode;
                        break;
                    case forward:
                        getDevice().driveForward();
                        getIntent().driveMode = intent.driveMode;

                        break;
                    case stop:
                        getDevice().stop();
                        getIntent().driveMode = intent.driveMode;

                        break;
                    case fullBrake:
                        getDevice().fullBrake();
                        getIntent().driveMode = intent.driveMode;
                        break;
                    default:
                        break;
                }
                super.getCallback().driveModeChanged(getIntent().driveMode);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(StepperDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
