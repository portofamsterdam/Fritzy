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
package nl.technolution.batty;

import com.google.common.base.Preconditions;

import nl.technolution.DeviceId;
import nl.technolution.protocols.efi.ActuatorInstruction;
import nl.technolution.protocols.efi.ActuatorInstructions;
import nl.technolution.protocols.efi.Instruction;
import nl.technolution.protocols.efi.InstructionRevoke;
import nl.technolution.protocols.efi.StorageInstruction;
import nl.technolution.protocols.efi.util.IResourceManager;

/**
 * Resource Manager of sunny
 */
public class BattyResourceManager implements IResourceManager {

    private final DeviceId deviceId;

    public BattyResourceManager(DeviceId deviceId) {
        this.deviceId = deviceId;
    }

    public DeviceId getDeviceId() {
        return deviceId;
    }

    @Override
    public void instruct(Instruction instruction) {
        Preconditions.checkArgument(instruction instanceof StorageInstruction, "Expected storage instruction");
        StorageInstruction storageInstruction = StorageInstruction.class.cast(instruction);
        ActuatorInstructions actuatorInstructions = storageInstruction.getActuatorInstructions();
        actuatorInstructions.getActuatorInstruction().forEach(this::handleActuatorInstruction);
    }

    @Override
    public void instructionRevoke(InstructionRevoke instructionRevoke) {
        // 
    }

    private void handleActuatorInstruction(ActuatorInstruction instruction) {

    }
}
