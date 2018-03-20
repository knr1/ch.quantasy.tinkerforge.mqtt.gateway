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
package ch.quantasy.gateway.service.tinkerforge;

import ch.quantasy.gateway.binding.tinkerforge.stepper.StepperServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.stepper.AllDataPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.stepper.DeviceSpeedRamp;
import ch.quantasy.gateway.binding.tinkerforge.stepper.DriveMode;
import ch.quantasy.gateway.binding.tinkerforge.stepper.DriveModeStatus;
import ch.quantasy.gateway.binding.tinkerforge.stepper.EnableStatus;
import ch.quantasy.gateway.binding.tinkerforge.stepper.MinimumVoltageStatus;
import ch.quantasy.gateway.binding.tinkerforge.stepper.RectificationStatus;
import ch.quantasy.gateway.binding.tinkerforge.stepper.StepMode;
import ch.quantasy.gateway.binding.tinkerforge.stepper.StepsStatus;
import ch.quantasy.gateway.binding.tinkerforge.stepper.TargetPositionStatus;
import ch.quantasy.gateway.binding.tinkerforge.stepper.CurrentPositionStatus;
import ch.quantasy.gateway.binding.tinkerforge.stepper.DecayStatus;
import ch.quantasy.gateway.binding.tinkerforge.stepper.MotorCurrentStatus;
import ch.quantasy.gateway.binding.tinkerforge.stepper.SpeedRampStatus;
import ch.quantasy.gateway.binding.tinkerforge.stepper.StepModeStatus;
import ch.quantasy.gateway.binding.tinkerforge.stepper.TimeBaseStatus;
import ch.quantasy.gateway.binding.tinkerforge.stepper.VelocityStatus;
import ch.quantasy.gateway.service.tinkerforge.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.stepper.StepperDevice;
import ch.quantasy.tinkerforge.device.stepper.StepperDeviceCallback;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class StepperService extends AbstractDeviceService<StepperDevice, StepperServiceContract> implements StepperDeviceCallback {

    public StepperService(StepperDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new StepperServiceContract(device));
    }

    @Override
    public void allDataPeriodChanged(Long allDataPeriod) {
        readyToPublishStatus(getContract().STATUS_ALL_DATA_PERIOD, new AllDataPeriodStatus(allDataPeriod));
    }

    @Override
    public void targetPositionChanged(Integer targetPosition) {
        readyToPublishStatus(getContract().STATUS_TARGET_POSITION, new TargetPositionStatus(targetPosition));
    }

    @Override
    public void driveModeChanged(DriveMode driverMode) {
        readyToPublishStatus(getContract().STATUS_DRIVE_MODE, new DriveModeStatus(driverMode));
    }

    @Override
    public void minimumVoltageChanged(Integer minimumVoltage) {
        readyToPublishStatus(getContract().STATUS_MINIMUM_VOLTAGE, new MinimumVoltageStatus(minimumVoltage));
    }

    @Override
    public void stepsChanged(Integer steps) {
        readyToPublishStatus(getContract().STATUS_STEPS, new StepsStatus(steps));
    }

    @Override
    public void syncRectChanged(Boolean syncRect) {
        readyToPublishStatus(getContract().STATUS_RECTIFICATION, new RectificationStatus(syncRect));
    }

    @Override
    public void enabledChanged(Boolean isEnabled) {
        readyToPublishStatus(getContract().STATUS_ENABLED, new EnableStatus(isEnabled));
    }

    @Override
    public void currentPositionChanged(Integer currentPosition) {
        readyToPublishStatus(getContract().STATUS_CURRENT_POSITION, new CurrentPositionStatus(currentPosition));
    }

    @Override
    public void decayChanged(Integer decay) {
        readyToPublishStatus(getContract().STATUS_DECAY, new DecayStatus(decay));
    }

    @Override
    public void maxVelocityChanged(Integer maxVelocity) {
        readyToPublishStatus(getContract().STATUS_MAX_VELOCITY, new VelocityStatus(maxVelocity));
    }

    @Override
    public void motorCurrentChanged(Integer motorCurrent) {
        readyToPublishStatus(getContract().STATUS_MOTOR_CURRENT, new MotorCurrentStatus(motorCurrent));
    }

    @Override
    public void speedRampChanged(DeviceSpeedRamp speedRamp) {
        readyToPublishStatus(getContract().STATUS_SPEED_RAMP, new SpeedRampStatus(speedRamp));
    }

    @Override
    public void stepModeChanged(StepMode stepMode) {
        readyToPublishStatus(getContract().STATUS_STEP_MODE, new StepModeStatus(stepMode));
    }

    @Override
    public void timeBaseChanged(Long timeBase) {
        readyToPublishStatus(getContract().STATUS_TIME_BASE, new TimeBaseStatus(timeBase));
    }

    @Override
    public void allData(int currentVelocity, int currentPosition, int remainingSteps, int stackVoltage, int externalVoltage, int currentConsumption) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void newState(short stateNew, short statePrevious) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void positionReached(int position) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void underVoltage(int voltage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
