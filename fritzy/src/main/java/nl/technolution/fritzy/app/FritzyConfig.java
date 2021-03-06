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
package nl.technolution.fritzy.app;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import nl.technolution.apis.ApiConfig;
import nl.technolution.apis.ApiConfigRecord;
import nl.technolution.apis.EApiNames;
import nl.technolution.dropwizard.FritzyAppConfig;
import nl.technolution.dropwizard.MarketConfig;
import nl.technolution.dropwizard.webservice.JacksonFactory;

/**
 * Configuration for Fritzy
 */
public class FritzyConfig extends FritzyAppConfig {

    /** EFI id of the device */
    @JsonProperty("deviceId")
    private String deviceId;

    /** host address of webrelay */
    @JsonProperty("host")
    private String host;

    /** Port of webrelay (default 80) */
    @JsonProperty("port")
    private int port;

    /** Serial port used to read temp sensor */
    @JsonProperty("serialPort")
    private String serialPort;

    /** stub the temperature sensor */
    @JsonProperty("stubTemparature")
    private boolean stubTemparature;

    /** stub the webrelay */
    @JsonProperty("stubRelay")
    private boolean stubRelay;

    /** lowest acceptable temperature */
    @JsonProperty("minTemp")
    private double minTemp;

    /** highest acceptable temperature */
    @JsonProperty("maxTemp")
    private double maxTemp;

    /** safety margin, when temperature at maxTemp + maxMargin Fritzy will be turned on regardless of market position */
    @JsonProperty("maxMargin")
    private int maxMargin;

    /**
     * The power consumption of Fritzy in Watt (in lieu of a power meter) used for calculating the amount of energy to
     * buy and to report consumption
     */
    @JsonProperty("power")
    private double power;

    /** The leakage rate of Fritzy in �C per second (when turned off) */
    @JsonProperty("leakageRate")
    private double leakageRate;

    /** The cooling speed of Fritzy in �C per second (when turned on) */
    @JsonProperty("coolingSpeed")
    private double coolingSpeed;

    /** offset in euro cent used at the start of the negation. First bid will be market - offset. */
    @JsonProperty("marketPriceStartOffset")
    private double marketPriceStartOffset;

    public FritzyConfig() {
        // Empty constructor
    }

    /**
     * Generate Fritzy config
     * 
     * @param args none
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonGenerationException
     */
    public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {

        ObjectMapper mapper = JacksonFactory.defaultMapper();
        FritzyConfig c = new FritzyConfig();
        c.deviceId = "fritzy";
        c.setEnvironment("live");

        c.host = "10.0.0.201";
        c.port = 80;
        c.serialPort = "/dev/ttyUSB0";
        c.stubTemparature = false;
        c.stubRelay = false;

        c.minTemp = 2;
        c.maxTemp = 2;

        c.power = 100;
        c.leakageRate = 0.000278d;
        c.coolingSpeed = 0.002222d;

        MarketConfig market = new MarketConfig(false, "http://82.196.13.251/api", "fritzy@fritzy.nl", "fritzy");
        c.setMarket(market);

        ApiConfig apiConfig = new ApiConfig();
        ApiConfigRecord netty = new ApiConfigRecord(EApiNames.NETTY.getName(), "http://netty:8080/", 5000, 5000);
        ApiConfigRecord exxy = new ApiConfigRecord(EApiNames.EXXY.getName(), "http://exxy:8080/", 5000, 5000);
        apiConfig.setApis(Lists.newArrayList(netty, exxy));
        c.setApiConfig(apiConfig);

        mapper.writerWithDefaultPrettyPrinter().writeValue(System.out, c);
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getSerialPort() {
        return serialPort;
    }

    public void setSerialPort(String serialPort) {
        this.serialPort = serialPort;
    }

    public boolean isStubTemparature() {
        return stubTemparature;
    }

    public void setStubTemparature(boolean stubTemparature) {
        this.stubTemparature = stubTemparature;
    }

    public boolean isStubRelay() {
        return stubRelay;
    }

    public void setStubRelay(boolean stubRelay) {
        this.stubRelay = stubRelay;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public int getMaxMargin() {
        return maxMargin;
    }

    public void setMaxMargin(int maxMargin) {
        this.maxMargin = maxMargin;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public double getLeakageRate() {
        return leakageRate;
    }

    public void setLeakageRate(double leakageRate) {
        this.leakageRate = leakageRate;
    }

    public double getCoolingSpeed() {
        return coolingSpeed;
    }

    public void setCoolingSpeed(double coolingSpeed) {
        this.coolingSpeed = coolingSpeed;
    }

    public double getMarketPriceStartOffset() {
        return marketPriceStartOffset;
    }

    public void setMarketPriceStartOffset(double marketPriceStartOffset) {
        this.marketPriceStartOffset = marketPriceStartOffset;
    }
}
