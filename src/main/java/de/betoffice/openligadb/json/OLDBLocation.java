package de.betoffice.openligadb.json;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "LocationID",
        "LocationCity",
        "LocationStadium"
})
public class OLDBLocation {

    @JsonProperty("LocationID")
    private Integer locationID;
    @JsonProperty("LocationCity")
    private String locationCity;
    @JsonProperty("LocationStadium")
    private String locationStadium;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("LocationID")
    public Integer getLocationID() {
        return locationID;
    }

    @JsonProperty("LocationID")
    public void setLocationID(Integer locationID) {
        this.locationID = locationID;
    }

    @JsonProperty("LocationCity")
    public String getLocationCity() {
        return locationCity;
    }

    @JsonProperty("LocationCity")
    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }

    @JsonProperty("LocationStadium")
    public String getLocationStadium() {
        return locationStadium;
    }

    @JsonProperty("LocationStadium")
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
