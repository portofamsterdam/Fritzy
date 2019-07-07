/*
 * Fritzy 2.0 Token API
 * This api provides functionality to interact with an ethereum blockchain
 *
 * OpenAPI spec version: 0.0.1
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package nl.technolution.fritzy.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

/**
 * WebSimEvent
 */
public class WebSimEvent   {
  @JsonProperty("environment")
  private String environment = null;

  @JsonProperty("actor")
  private String actor = null;

  @JsonProperty("msg")
  private String msg = null;

  @JsonProperty("tag")
  private String tag = null;

  @JsonProperty("timestamp")
  private String timestamp = null;

  public WebSimEvent environment(String environment) {
    this.environment = environment;
    return this;
  }

  /**
   * Get environment
   * @return environment
   **/
  @JsonProperty("environment")
  @Schema(description = "")
  public String getEnvironment() {
    return environment;
  }

  public void setEnvironment(String environment) {
    this.environment = environment;
  }

  public WebSimEvent actor(String actor) {
    this.actor = actor;
    return this;
  }

  /**
   * Get actor
   * @return actor
   **/
  @JsonProperty("actor")
  @Schema(description = "")
  public String getActor() {
    return actor;
  }

  public void setActor(String actor) {
    this.actor = actor;
  }

  public WebSimEvent msg(String msg) {
    this.msg = msg;
    return this;
  }

  /**
   * Get msg
   * @return msg
   **/
  @JsonProperty("msg")
  @Schema(description = "")
  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public WebSimEvent tag(String tag) {
    this.tag = tag;
    return this;
  }

  /**
   * Get tag
   * @return tag
   **/
  @JsonProperty("tag")
  @Schema(description = "")
  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public WebSimEvent timestamp(String timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  /**
   * Get timestamp
   * @return timestamp
   **/
  @JsonProperty("timestamp")
  @Schema(description = "")
  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WebSimEvent simEvent = (WebSimEvent) o;
    return Objects.equals(this.environment, simEvent.environment) &&
        Objects.equals(this.actor, simEvent.actor) &&
        Objects.equals(this.msg, simEvent.msg) &&
        Objects.equals(this.tag, simEvent.tag) &&
        Objects.equals(this.timestamp, simEvent.timestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(environment, actor, msg, tag, timestamp);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WebSimEvent {\n");
    
    sb.append("    environment: ").append(toIndentedString(environment)).append("\n");
    sb.append("    actor: ").append(toIndentedString(actor)).append("\n");
    sb.append("    msg: ").append(toIndentedString(msg)).append("\n");
    sb.append("    tag: ").append(toIndentedString(tag)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}