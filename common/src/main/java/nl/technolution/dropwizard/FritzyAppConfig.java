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
package nl.technolution.dropwizard;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import nl.technolution.apis.ApiConfig;

/**
 * 
 */
public class FritzyAppConfig extends Configuration {

    @JsonProperty("environment")
    private String environment;

    @JsonProperty("apiConfig")
    private ApiConfig apiConfig;

    @JsonProperty("market")
    private MarketConfig market;

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public ApiConfig getApiConfig() {
        return apiConfig;
    }

    public void setApiConfig(ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
    }

    public MarketConfig getMarket() {
        return market;
    }

    public void setMarket(MarketConfig market) {
        this.market = market;
    }

}
