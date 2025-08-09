package de.betoffice.openligadb.json;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "groupName",
        "ggroupOrderID",
        "groupID"
})
public class OLDBGroup {

    @JsonProperty("groupName")
    private String groupName;
    @JsonProperty("groupOrderID")
    private Integer groupOrderID;
    @JsonProperty("groupID")
    private Integer groupID;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("groupName")
    public String getGroupName() {
        return groupName;
    }

    @JsonProperty("groupName")
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @JsonProperty("groupOrderID")
    public Integer getGroupOrderID() {
        return groupOrderID;
    }

    @JsonProperty("groupOrderID")
    public void setGroupOrderID(Integer groupOrderID) {
        this.groupOrderID = groupOrderID;
    }

    @JsonProperty("groupID")
    public Integer getGroupID() {
        return groupID;
    }

    @JsonProperty("groupID")
    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
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
        final StringBuilder sb = new StringBuilder("Group{");
        sb.append("groupName='").append(groupName).append('\'');
        sb.append(", groupOrderID=").append(groupOrderID);
        sb.append(", groupID=").append(groupID);
        sb.append(", additionalProperties=").append(additionalProperties);
        sb.append('}');
        return sb.toString();
    }
}
