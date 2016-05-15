/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.remoteSwitch;

/**
 *
 * @author reto
 */
public class DimSocketBParameters {
    
    public long address;
    public short unit;
    public short dimValue;

    public DimSocketBParameters() {
    }

    public DimSocketBParameters(long address, short unit, short dimValue) {
        this.address = address;
        this.unit = unit;
        this.dimValue = dimValue;
    }
    
}
