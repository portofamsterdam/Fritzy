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
package nl.technolution.batty.xstorage.types;

import com.google.common.base.Preconditions;

/**
 * 
 */
public class MachineInfo {

    // 0 Communication card firmware version
    private String communicationCardFirmwareVersion;
    // 1 Communication card MAC address
    private String macAddress;
    // 2 Inverter serial number
    private String inverterSerial;
    // 3 Inverter model name
    private String inverterModelName;
    // 4 Inverter phase.
    private String phase;
    // 5 Inverter firmware version
    private String inverterFirmwareVersion;
    // 6 Inverter VA rating
    private String inverterVARating;
    // 7 Inverter Nominal Vpv, unit 0.1V
    private String nominalVpv;

    /**
     * @param communicationCardFirmwareVersion
     * @param macAddress
     * @param inverterSerial
     * @param inverterModelName
     * @param phase
     * @param inverterFirmwareVersion
     * @param inverterVARating
     * @param nominalVpv
     */
    public static MachineInfo build(String communicationCardFirmwareVersion, String macAddress, String inverterSerial,
            String inverterModelName, String phase, String inverterFirmwareVersion, String inverterVARating,
            String nominalVpv) {
        MachineInfo info = new MachineInfo();
        info.communicationCardFirmwareVersion = communicationCardFirmwareVersion;
        info.macAddress = macAddress;
        info.inverterSerial = inverterSerial;
        info.inverterModelName = inverterModelName;
        info.phase = phase;
        info.inverterFirmwareVersion = inverterFirmwareVersion;
        info.inverterVARating = inverterVARating;
        info.nominalVpv = nominalVpv;
        return info;
    }

    /**
     * Build MachineInfo from result of webcall
     * 
     * @param result returned by API call
     * @return instance
     */
    public static MachineInfo fromData(String[] result) {
        Preconditions.checkArgument(result.length == 8);
        MachineInfo info = new MachineInfo();
        info.communicationCardFirmwareVersion = result[0];
        info.macAddress = result[1];
        info.inverterSerial = result[2];
        info.inverterModelName = result[3];
        info.phase = result[4];
        info.inverterFirmwareVersion = result[5];
        info.inverterVARating = result[6];
        info.nominalVpv = result[7];
        return info;
    }

    public String getCommunicationCardFirmwareVersion() {
        return communicationCardFirmwareVersion;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public String getInverterSerial() {
        return inverterSerial;
    }

    public String getInverterModelName() {
        return inverterModelName;
    }

    public String getPhase() {
        return phase;
    }

    public String getInverterFirmwareVersion() {
        return inverterFirmwareVersion;
    }

    public String getInverterVARating() {
        return inverterVARating;
    }

    public String getNominalVpv() {
        return nominalVpv;
    }

    @Override
    public String toString() {
        return "MachineInfo [communicationCardFirmwareVersion=" + communicationCardFirmwareVersion + ", macAddress=" +
                macAddress + ", inverterSerial=" + inverterSerial + ", inverterModelName=" + inverterModelName +
                ", phase=" + phase + ", inverterFirmwareVersion=" + inverterFirmwareVersion + ", inverterVARating=" +
                inverterVARating + ", nominalVpv=" + nominalVpv + "]";
    }
}
