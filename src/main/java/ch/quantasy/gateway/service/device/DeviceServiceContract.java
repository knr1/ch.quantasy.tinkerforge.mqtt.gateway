/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.gateway.service.device;

import ch.quantasy.gateway.service.ServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.generic.GenericDevice;

/**
 *
 * @author reto
 */
public class DeviceServiceContract extends ServiceContract{

    public DeviceServiceContract(GenericDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public DeviceServiceContract(String id, String device) {
        super(device, id);

    }
}
