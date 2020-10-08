package de.betoffice.openligadb.json;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "GroupName",
        "GroupOrderID",
        "GroupID"
})
public class Group {

    @JsonProperty("GroupName")
    private String groupName;
    @JsonProperty("GroupOrderID")
    private Integer groupOrderID;
    @JsonProperty("GroupID")
    private Integer groupID;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("GroupName")
    public String getGroupName() {
        return groupName;
    }

    @JsonProperty("GroupName")
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @JsonProperty("GroupOrderID")
    public Integer getGroupOrderID() {
        return groupOrderID;
    }

    @JsonProperty("GroupOrderID")
    public void setGroupOrderID(Integer groupOrderID) {
        this.groupOrderID = groupOrderID;
    }

    @JsonProperty("GroupID")
    public Integer getGroupID() {
        return groupID;
    }

    @JsonProperty("GroupID")
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
