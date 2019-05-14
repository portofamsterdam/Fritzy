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
package nl.technolution.sunny;

import nl.technolution.DeviceId;
import nl.technolution.protocols.efi.Instruction;
import nl.technolution.protocols.efi.InstructionRevoke;
import nl.technolution.protocols.efi.util.IResourceManager;

/**
 * Resource Manager of sunny
 */
public class SunnyResourceManager implements IResourceManager {

    private final DeviceId deviceId;

    public SunnyResourceManager(DeviceId deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public DeviceId getDeviceId() {
        return deviceId;
    }

    @Override
    public void instruct(Instruction instruction) {
        //
    }

    @Override
    public void instructionRevoke(InstructionRevoke instructionRevoke) {
        // 
    }
}
