/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device;

/**
 *
 * @author reto
 */
public interface TinkerforgeDeviceListener {
    public void connected(TinkerforgeDevice tinkerforgeDevice);
    public void reConnected(TinkerforgeDevice tinkerforgeDevice);
    public void disconnected(TinkerforgeDevice tinkerforgeDevice);

}
