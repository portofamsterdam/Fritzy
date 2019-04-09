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
package nl.technolution.protocols.efi.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;
import java.util.UUID;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import nl.technolution.DeviceId;
import nl.technolution.protocols.efi.EfiMessage;
import nl.technolution.protocols.efi.EfiMessage.Header;
import nl.technolution.protocols.efi.FlexibilityUpdate;

/**
 * Build Efi messages
 */
public final class Efi {

    // Cache DataTypeFactory because it is expensive to create and thread safe
    public static final DatatypeFactory DATATYPE_FACTORY;

    private static final ZoneId UTC_ZONE = ZoneId.of("UTC");

    private static final String EFI_VERSION = "2.0";

    static {
        // Static because it's a heavy operation and know that newXMLGregorianCalendar() routine is thread safe!
        try {
            DATATYPE_FACTORY = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            // Should'nt happen
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private Efi() {
        // hide constructor
    }

    /**
     * Build an EfiMessage with header etc.
     * 
     * @param type of Efi message to build
     * @return instance of type
     */
    public static <T extends EfiMessage> T build(Class<T> type, DeviceId deviceId) {

        Header header = new Header();
        header.setTimestamp(calendarOfInstant(Instant.now()));
        header.setEfiResourceId(deviceId.getDeviceId());

        try {
            T instance = type.newInstance();
            addSubTypeInfo(instance);
            instance.setEfiVersion(EFI_VERSION);
            instance.setHeader(header);
            return instance;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("All Efi message objects should have default no argument constructor");
        }
    }

    private static <T extends EfiMessage> void addSubTypeInfo(T instance) {
        if (instance instanceof FlexibilityUpdate) {
            FlexibilityUpdate flexUpdate = FlexibilityUpdate.class.cast(instance);
            flexUpdate.setFlexibilityUpdateId(UUID.randomUUID().toString());
        }
    }

    /**
     * Create a timestamp for XML messages.
     * 
     * @return calendar
     */
    public static XMLGregorianCalendar calendarOfInstant(Instant instant) {
        GregorianCalendar calendar = GregorianCalendar.from(ZonedDateTime.ofInstant(instant, UTC_ZONE));
        return DATATYPE_FACTORY.newXMLGregorianCalendar(calendar);
    }
}