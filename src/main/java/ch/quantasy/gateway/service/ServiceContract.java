/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.gateway.service;

/**
 *
 * @author reto
 */
public class ServiceContract {

    public final String ROOT;
    public final String ID;
    public final String ID_TOPIC;
    public final String BASE;
    public final String BASE_TOPIC;
    public final String STATUS;
    public final String STATUS_CONNECTION;
    public final String OFFLINE;
    public final String ONLINE;

    public final String EVENT;
    public final String INTENT;
    public final String DESCRIPTION;

    public ServiceContract(String base, String id) {
        ROOT = "TF";
        BASE = base;
        BASE_TOPIC = ROOT + "/" + BASE;
        ID = id;
        if (ID != null) {
            ID_TOPIC = BASE_TOPIC + "/" + ID;
        } else {
            ID_TOPIC = BASE_TOPIC;
        }

        EVENT = ID_TOPIC + "/event";
        INTENT = ID_TOPIC + "/intent";
        STATUS = ID_TOPIC + "/status";
        DESCRIPTION=BASE_TOPIC+"/description";

        STATUS_CONNECTION = STATUS + "/connection";
        OFFLINE = "offline";
        ONLINE = "online";

    }
    
}
