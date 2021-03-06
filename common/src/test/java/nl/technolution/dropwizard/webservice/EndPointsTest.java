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
package nl.technolution.dropwizard.webservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.google.common.collect.Lists;

import org.junit.Test;

import nl.technolution.apis.ApiConfig;
import nl.technolution.apis.ApiConfigRecord;
import nl.technolution.apis.ApiEndpoints;
import nl.technolution.apis.EApiNames;
import nl.technolution.apis.netty.DeviceCapacity;
import nl.technolution.apis.netty.INettyApi;
import nl.technolution.apis.netty.OrderReward;

/**
 * Test registering endpoints
 */
public class EndPointsTest {

    /**
     * 
     */
    @Test
    public void testRegistrationofEndpoint() {
        NettyApiImpl endpoint = new NettyApiImpl();
        Endpoints.put(INettyApi.class, endpoint);
        assertEquals(endpoint, Endpoints.get(INettyApi.class));

    }

    /**
     * Test adding endpoint
     */
    @Test
    public void testGeneratedEndpoint() {
        String uri = "http://localhost:8080/nonUsedUrl";
        ApiConfigRecord cfgr = new ApiConfigRecord();
        cfgr.setName(EApiNames.NETTY.getName());
        cfgr.setUrl(uri);
        cfgr.setReadTimeout(1000);
        cfgr.setConnectTimeout(1000);
        ApiConfig cfg = new ApiConfig();
        cfg.setApis(Lists.newArrayList(cfgr));
        ApiEndpoints.register(cfg);
        INettyApi netty = Endpoints.get(INettyApi.class);
        assertNotNull(netty);
        // TODO: make this test work by running the endpoint.
        // netty.getCapacity("deviceId");
        // netty.getOrderReward("taker", "orderHash");
        // netty.claim("txHash", "rewardId");
    }

    private static class NettyApiImpl implements INettyApi {

        @Override
        public DeviceCapacity getCapacity(String deviceId) {
            return null;
        }

        @Override
        public void claim(String txHash, String rewardId) {
        }

        @Override
        public OrderReward getOrderReward(String taker, String orderHash) {
            //
            return null;
        }
    }
}
