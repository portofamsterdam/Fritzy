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
package nl.technolution.fritzy.webrelay;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;



/**
 * Class representing relay device. Can read it's current state and set the relay to preffered state
 */
public final class WebRelay {

    private final InetAddress address;

    /**
     * Constructor for {@link WebRelay} objects
     *
     * @param address where to find the relay
     */
    public WebRelay(InetAddress address) {
        this.address = address;
    }

    /**
     * Get state from relay
     * 
     * @return state information 
     * @throws IOException when device cannot be reached
     */
    public WebRelayState getState() throws IOException {
       return readResponse("GET /state.xml HTTP/1.1 \r\n\r\n");
    }
    
    /**
     * Set relay state 
     * @param state to set
     * @return state information 
     * @throws IOException when device cannot be reached
     */
    public WebRelayState setRelay(boolean state) throws IOException {
        return readResponse(String.format("GET /state.xml?relayState=%d HTTP/1.1 \r\n\r\n", state ? 1 : 0));
    }
    
    private  WebRelayState readResponse(String command) throws IOException {
        // Note UrlConnection complains about invalid response. This is easiest way to fix it
        try (Socket s = new Socket(address.getHostAddress(), 80);
                OutputStream out = s.getOutputStream();
                InputStream in = s.getInputStream()) {
            // Writh http request
            out.write(command.getBytes(StandardCharsets.UTF_8));
            out.flush();
            
            // Read XML response
            ByteBuffer bb = ByteBuffer.allocate(255);
            int c;
            while ((c = in.read()) != -1) {
                bb.put((byte)c);
            }
            return WebRelayState.parse(new String(bb.array(), StandardCharsets.UTF_8));
        }
    }
}
