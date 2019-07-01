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
package nl.technolution.dashboard;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nl.technolution.IJsonnable;

/**
 * Service for sending events to the dashboard
 */
public class EventService implements IEvent {

    private final ObjectMapper mapper = new ObjectMapper();

    private String actor;
    private String environment;

    @Override
    public void init(DashboardConfig config) {
        // TODO MKE: add actor and env to config
    }

    @Override
    public void log(EEventType tag, String event, IJsonnable data) {
        String str;
        try {
            str = data == null ? null : mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            // TODO MKE: handle exception
            throw new RuntimeException(e.getMessage(), e);
        }
        long timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        DashboardEvent dashboardEvent = new DashboardEvent(environment, timestamp, actor, tag.getTag(), event, str);
        // TODO MKE send event
    }

}
