/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.gateway.tinkerforge;

import ch.quantasy.tinkerforge.factory.TinkerforgeStackFactory;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;

/**
 *
 * @author reto
 */
public interface TinkerforgeFactoryListener {
    public void stackAdded(TinkerforgeStack stack);
    public void stackRemoved(TinkerforgeStack stack);
}
