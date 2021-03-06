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
package nl.technolution.sunny.solaredge;

import java.net.InetAddress;
import java.util.EnumSet;
import java.util.Map;

import com.ghgande.j2mod.modbus.ModbusException;
import com.ghgande.j2mod.modbus.io.ModbusTransaction;
import com.ghgande.j2mod.modbus.msg.ReadMultipleRegistersRequest;
import com.ghgande.j2mod.modbus.msg.ReadMultipleRegistersResponse;
import com.ghgande.j2mod.modbus.net.TCPMasterConnection;
import com.ghgande.j2mod.modbus.util.ModbusUtil;
import com.google.common.collect.Maps;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.technolution.sunny.solaredge.sunspec.ESolarEdgeRegister;
import nl.technolution.sunny.solaredge.sunspec.UnsignedInteger;
import nl.technolution.sunny.solaredge.sunspec.UnsignedLong;
import nl.technolution.sunny.solaredge.sunspec.UnsignedShort;


/**
 * Class to maintain a connection to the solarEdge unit
 */
public class ModbusSession implements IModbusSession {

    private static final Logger LOG = LoggerFactory.getLogger(ModbusSession.class);

    private final Object lock = new Object();
    private final TCPMasterConnection connection;
    private final int unitId;

    private boolean isConnected = false;
    private boolean stopped = false;

    public ModbusSession(InetAddress address, int port, int unitId) {
        synchronized (lock) {
            this.connection = new TCPMasterConnection(address);
            this.connection.setPort(port);
            this.unitId = unitId;
        }
    }

    public int getPort() {
        return connection.getPort();
    }

    public InetAddress getIpAddress() {
        return connection.getAddress();
    }

    /**
     * Open connection to device
     */
    public void open() throws ModbusException {
        synchronized (lock) {
            try {
                connection.connect();
                // See MRV-661, writing the control mode register takes more than 2s so set default timeout to 5s
                connection.setTimeout(5000);
                isConnected = true;
            } catch (Exception e) {
                throw new ModbusException(e.getMessage(), e);
            }
        }
    }

    /**
     * Close connection to device
     */
    public void close() {
        synchronized (lock) {
            isConnected = false;
            connection.close();
        }
    }

    /**
     * Stop handling calls to solaredge device
     */
    public void stop() {
        stopped = true;
        close();
    }

    /**
     * indicates if connection to device is open
     */
    public boolean isOpen() {
        synchronized (lock) {
            return connection.isConnected() && isConnected;
        }
    }

    /**
     * reconnect to device.
     */
    private void reOpen() throws ModbusException {
        if (stopped) {
            throw new ModbusException("Session stopped, can't reopen!");
        } else {
            close();
            open();
        }
    }

    /**
     * Read a register from device
     * 
     * @param startAddress address to read
     * @param words size of register
     * @return contents of register
     * @throws ModbusException when data cannot be read
     */
    public byte[] readRegister(short startAddress, int words) throws ModbusException {
        synchronized (lock) {
            if (!isOpen()) {
                reOpen();
            }
            ModbusTransaction transaction = SolarEdgeUtils.createTransaction(connection);
            ReadMultipleRegistersRequest request = SolarEdgeUtils.createReadRequest(unitId);
            request.setReference(startAddress);
            request.setWordCount(words);
            request.setTransactionID(transaction.getTransactionID());
            transaction.setRequest(request);
            try {
                transaction.execute();
            } catch (ModbusException e) {
                close();
                throw e;
            }

            ReadMultipleRegistersResponse response = (ReadMultipleRegistersResponse)transaction.getResponse();
            byte[] bytes = SolarEdgeUtils.registersToBytes(response.getRegisters());
            if ((bytes.length) != (words * 2)) {
                close();
                throw new ModbusException(
                        String.format("Read %d bytes instead of expected %d bytes", bytes.length, words * 2));
            }
            for (int i = 0; i < words; i++) {
                LOG.trace("{}", String.format("Read 0x%04X: 0x%04X", (startAddress + i) & 0xFFFF,
                        ModbusUtil.registerToShort(ArrayUtils.subarray(bytes, i * 2, (i * 2) + 2))));
            }
            return bytes;
        }
    }

    /**
     * Read a register of a given type
     */
    public <T> T readRegister(ESolarEdgeRegister register, Class<T> type) throws ModbusException {
        return convertType(readRegister(register.getAddress(), register.getSize()), type);
    }

    /**
     * Read multiple registers of given types
     */
    public Map<ESolarEdgeRegister, SolarEdgeValue<?>> readMultipleRegisters(EnumSet<ESolarEdgeRegister> registers)
            throws ModbusException {
        Map<ESolarEdgeRegister, SolarEdgeValue<?>> values = Maps.newHashMap();

        // Retrieve the lowest & highest register from the enumset.
        ESolarEdgeRegister lowestRegister = null;
        ESolarEdgeRegister highestRegister = null;
        for (ESolarEdgeRegister register : registers) {
            if (lowestRegister == null || lowestRegister.getAddress() > register.getAddress()) {
                lowestRegister = register;
            }
            if (highestRegister == null || highestRegister.getAddress() < register.getAddress()) {
                highestRegister = register;
            }
        }

        if (lowestRegister == null || highestRegister == null) {
            return values;
        }

        // Calculate the number of words to read
        int words = (highestRegister.getAddress() + highestRegister.getSize()) - lowestRegister.getAddress();
        byte[] data = readRegister(lowestRegister.getAddress(), words);

        for (ESolarEdgeRegister register : registers) {
            // Calculate word address to byte address
            int address = (register.getAddress() - lowestRegister.getAddress()) * 2;
            // Calculate word length to byte length
            int length = register.getSize() * 2;
            byte[] bytes = ArrayUtils.subarray(data, address, address + length);

            Class<?> type = register.getType().getType();

            @SuppressWarnings({ "rawtypes", "unchecked" })
            SolarEdgeValue value = new SolarEdgeValue(type, convertType(bytes, type));

            values.put(register, value);
        }

        return values;
    }

    /**
     * Convert given data to given type
     * 
     * @param data to convert
     * @param type to convert to
     * @return instance of given type
     * @throws ModbusException when data cannot be converted
     */
    public <T> T convertType(byte[] data, Class<T> type) throws ModbusException {
        if (type == Float.class) {
            return type.cast(SolarEdgeUtils.registersToModiconFloat(data));
        } else if (type == Long.class) {
            return type.cast(SolarEdgeUtils.registersToSolarEdgeLong(data));
        } else if (type == UnsignedLong.class) {
            return type.cast(SolarEdgeUtils.registersToSolarEdgeUnsignedLong(data));
        } else if (type == Integer.class) {
            return type.cast(ModbusUtil.registersToInt(data));
        } else if (type == UnsignedInteger.class) {
            return type.cast(new UnsignedInteger(SolarEdgeUtils.registersToSolarEdgeInteger(data)));
        } else if (type == Short.class) {
            return type.cast(ModbusUtil.registerToShort(data));
        } else if (type == UnsignedShort.class) {
            return type.cast(new UnsignedShort(ModbusUtil.registerToShort(data)));
        } else if (type == String.class) {
            return type.cast(SolarEdgeUtils.registersToString(data));
        }
        throw new IllegalArgumentException("Unknown type: " + type.getName());
    }
}
