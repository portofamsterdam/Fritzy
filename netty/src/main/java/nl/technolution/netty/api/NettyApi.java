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
package nl.technolution.netty.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

import org.apache.commons.lang3.tuple.ImmutablePair;

import nl.technolution.DeviceId;
import nl.technolution.apis.netty.DeviceCapacity;
import nl.technolution.apis.netty.INettyApi;
import nl.technolution.apis.netty.OrderReward;
import nl.technolution.dropwizard.services.Services;
import nl.technolution.fritzy.wallet.IFritzyApiFactory;
import nl.technolution.fritzy.wallet.event.EventLogger;
import nl.technolution.netty.rewarder.IRewardService;
import nl.technolution.netty.supplylimit.IGridCapacityManager;

/**
 * 
 */
@Path("/netty")
@Produces(MediaType.APPLICATION_JSON)
public class NettyApi implements INettyApi {

    /**
     * Determine grid connection limit of device
     * 
     * @param deviceId to find limit for
     * @return limit in amps
     */
    @Override
    @GET
    @Timed
    @Path("capacity")
    @Produces(MediaType.APPLICATION_JSON)
    public DeviceCapacity getCapacity(@QueryParam("deviceId") String deviceId) {
        DeviceId id = new DeviceId(deviceId);
        IGridCapacityManager gridCapacityManager = Services.get(IGridCapacityManager.class);
        double gridConnectionLimit = gridCapacityManager.getGridConnectionLimit(id);
        double groupConnectionLimit = gridCapacityManager.getGroupConnectionLimit();
        logDeviceState(id, gridConnectionLimit, groupConnectionLimit);
        return new DeviceCapacity(gridConnectionLimit, groupConnectionLimit);
    }

    @Override
    public OrderReward getOrderReward(String taker, String orderHash) {
        return Services.get(IRewardService.class).calculateReward(taker, orderHash);
    }

    @Override
    public void claim(String txHash, String rewardId) {
        Services.get(IRewardService.class).claim(txHash, rewardId);
    }

    @SuppressWarnings("unchecked")
    private static void logDeviceState(DeviceId id, double gridConnectionLimit, double groupConnectionLimit) {
        EventLogger logger = new EventLogger(Services.get(IFritzyApiFactory.class).build());
        logger.logDeviceState(new ImmutablePair<String, Object>("connectionLimit", gridConnectionLimit),
                new ImmutablePair<String, Object>("gridConnectionLimit", groupConnectionLimit),
                new ImmutablePair<String, Object>("actor", id.getDeviceId()));
    }
}
