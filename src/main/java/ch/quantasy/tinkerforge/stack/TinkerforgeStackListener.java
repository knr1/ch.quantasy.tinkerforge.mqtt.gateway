/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.stack;

import ch.quantasy.tinkerforge.device.TinkerforgeDevice;

/**
 *
 * @author reto
 */
public interface TinkerforgeStackListener {

    /**
     * Called when the stack has been connected
     */
    public void connected(TinkerforgeStack stack);

    /**
     * Called when the stack has been disconnected
     */
    public void disconnected(TinkerforgeStack stack);

}
