package de.betoffice.openligadb.json;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "locationID",
        "locationCity",
        "locationStadium"
})
public class OLDBLocation {

    @JsonProperty("locationID")
    private Integer locationID;
    @JsonProperty("locationCity")
    private String locationCity;
    @JsonProperty("locationStadium")
    private String locationStadium;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("locationID")
    public Integer getLocationID() {
        return locationID;
    }

    @JsonProperty("locationID")
    public void setLocationID(Integer locationID) {
        this.locationID = locationID;
    }

    @JsonProperty("locationCity")
    public String getLocationCity() {
        return locationCity;
    }

    @JsonProperty("locationCity")
    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }

    @JsonProperty("locationStadium")
    public String getLocationStadium() {
        return locationStadium;
    }

    @JsonProperty("locationStadium")
    public void setLocationStadium(String locationStadium) {
        this.locationStadium = locationStadium;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("Location{");
        sb.append("locationID=").append(locationID);
        sb.append(", locationCity='").append(locationCity).append('\'');
        sb.append(", locationStadium='").append(locationStadium).append('\'');
        sb.append(", additionalProperties=").append(additionalProperties);
        sb.append('}');
        return sb.toString();
    }
}
