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
package nl.technolution.fritzy.marketnegotiator;

import java.time.Instant;

import com.google.common.base.Preconditions;

import org.slf4j.Logger;

import nl.technolution.DeviceId;
import nl.technolution.Log;
import nl.technolution.fritzy.app.FritzyConfig;
import nl.technolution.protocols.efi.ActuatorInstruction;
import nl.technolution.protocols.efi.ActuatorInstructions;
import nl.technolution.protocols.efi.Instruction;
import nl.technolution.protocols.efi.InstructionRevoke;
import nl.technolution.protocols.efi.Measurement;
import nl.technolution.protocols.efi.Measurement.ElectricityMeasurement;
import nl.technolution.protocols.efi.StorageInstruction;
import nl.technolution.protocols.efi.StorageRegistration;
import nl.technolution.protocols.efi.StorageUpdate;
import nl.technolution.protocols.efi.util.Efi;
import nl.technolution.protocols.efi.util.ICustomerEnergyManager;
import nl.technolution.protocols.efi.util.IResourceManager;

/**
 * Resource Manager of Fritzy
 */
public class FritzyResourceManager implements IResourceManager {
    private static final Logger LOG = Log.getLogger();

    private final FritzyResourceHelper helper;
    private final FritzyController controller;

    private ICustomerEnergyManager<StorageRegistration, StorageUpdate> cem;
    private FritzyConfig config;

    private DeviceId deviceId;

    public FritzyResourceManager(FritzyConfig config, FritzyController controller) {
        this.helper = new FritzyResourceHelper(config);
        this.controller = controller;
        this.config = config;
        deviceId = new DeviceId(config.getDeviceId());
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
        // not supported (don't know what to do with it).
        LOG.warn("instructionRevoke recieved but not supported.");
    }

    private void handleActuatorInstruction(ActuatorInstruction instruction) {
        // NOTE assume one actuatorId exists
        EFritzyRunningMode fritzyInstruction = EFritzyRunningMode.fromRunningModeId(instruction.getRunningModeId());
        switch (fritzyInstruction) {
        case ON:
            controller.on();
            break;
        case OFF:
            controller.off();
            break;
        default:
            throw new IllegalStateException("Unknown instruction " + fritzyInstruction);
        }
    }

    /**
     * Evaluate current state and update CEM.
     */
    public void evaluate() {
        StorageInstruction instruction = (StorageInstruction)cem
                .flexibilityUpdate(helper.getFlexibilityUpdate(controller));
        if (instruction != null) {
            handleActuatorInstruction(instruction.getActuatorInstructions().getActuatorInstruction().get(0));
        }
    }

    /**
     * Register to a Customer Energy Manager.
     * 
     * @param cem to register to
     */
    public void registerCustomerEnergyManager(FritzyNegotiator cem) {
        this.cem = cem;
        cem.flexibilityRegistration(helper.getRegistration());
        cem.storageSystemDescription(helper.getStorageSystemDescription());
    }

    /**
     * Send measurement
     */
    public void sendMeasurement() {
        Measurement measurement = Efi.build(Measurement.class, deviceId);
        measurement.setMeasurementTimestamp(Efi.calendarOfInstant(Instant.now()));
        ElectricityMeasurement value = new ElectricityMeasurement();

        // No power measurement available so use current state and configured power
        if (controller.getState()) {
            value.setPower(config.getPower());
        } else {
            value.setPower(0);
        }

        measurement.setElectricityMeasurement(value);
        cem.measurement(measurement);
    }

    /**
     * @return
     */
    public DeviceId getDeviceId() {
        return deviceId;
    }
}
