package de.betoffice.openligadb.json;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "TeamId",
        "TeamName",
        "ShortName",
        "TeamIconUrl",
        "TeamGroupName"
})
public class Team1 {

    @JsonProperty("TeamId")
    private Integer teamId;
    @JsonProperty("TeamName")
    private String teamName;
    @JsonProperty("ShortName")
    private String shortName;
    @JsonProperty("TeamIconUrl")
    private String teamIconUrl;
    @JsonProperty("TeamGroupName")
    private Object teamGroupName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("TeamId")
    public Integer getTeamId() {
        return teamId;
    }

    @JsonProperty("TeamId")
    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    @JsonProperty("TeamName")
    public String getTeamName() {
        return teamName;
    }

    @JsonProperty("TeamName")
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @JsonProperty("ShortName")
    public String getShortName() {
        return shortName;
    }

    @JsonProperty("ShortName")
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @JsonProperty("TeamIconUrl")
    public String getTeamIconUrl() {
        return teamIconUrl;
    }

    @JsonProperty("TeamIconUrl")
    public void setTeamIconUrl(String teamIconUrl) {
        this.teamIconUrl = teamIconUrl;
    }

    @JsonProperty("TeamGroupName")
    public Object getTeamGroupName() {
        return teamGroupName;
    }

    @JsonProperty("TeamGroupName")
    public void setTeamGroupName(Object teamGroupName) {
        this.teamGroupName = teamGroupName;
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
        final StringBuilder sb = new StringBuilder("Team1{");
        sb.append("teamId=").append(teamId);
        sb.append(", teamName='").append(teamName).append('\'');
        sb.append(", shortName='").append(shortName).append('\'');
        sb.append(", teamIconUrl='").append(teamIconUrl).append('\'');
        sb.append(", teamGroupName=").append(teamGroupName);
        sb.append(", additionalProperties=").append(additionalProperties);
        sb.append('}');
        return sb.toString();
    }
}
