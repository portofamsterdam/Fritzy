/*
 (C) COPYRIGHT TECHNOLUTION BV, GOUDA NL
| =======          I                   ==          I    =
|    I             I                    I          I
|    I   ===   === I ===  I ===   ===   I  I    I ====  I   ===  I ===
|    I  /   \ I    I/   I I/   I I   I  I  I    I  I    I  I   I I/   I
|    I  ===== I    I    I I    I I   I  I  I    I  I    I  I   I I    I
|    I  \     I    I    I I    I I   I  I  I   /I  \    I  I   I I    I
|    I   ===   === I    I I    I  ===  ===  === I   ==  I   ===  I    I
|                 +---------------------------------------------------+
+----+            |  +++++++++++++++++++++++++++++++++++++++++++++++++|
     |            |             ++++++++++++++++++++++++++++++++++++++|
     +------------+                          +++++++++++++++++++++++++|
                                                        ++++++++++++++|
                                                                 +++++|
 */
package nl.technolution.netty;

import nl.technolution.appliance.DeviceControllerApp;
import nl.technolution.appliance.DeviceId;

/**
 * Simulator for net Power
 */
public class Netty extends DeviceControllerApp<NettyConfig> {

    private DeviceId deviceId = null;

    @Override
    public DeviceId getDeviceId() {
        return deviceId;
    }

    @Override
    protected void initDevice(NettyConfig configuration) {
        this.deviceId = new DeviceId(configuration.getDevicveId());
    }

}
