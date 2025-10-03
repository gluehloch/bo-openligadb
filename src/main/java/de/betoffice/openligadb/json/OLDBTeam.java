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
        "teamId",
        "teamName",
        "shortName",
        "teamIconUrl",
        "teamGroupName"
})
public class OLDBTeam {

    @JsonProperty("teamId")
    private Integer teamId;
    @JsonProperty("teamName")
    private String teamName;
    @JsonProperty("shortName")
    private String shortName;
    @JsonProperty("teamIconUrl")
    private String teamIconUrl;
    @JsonProperty("teamGroupName")
    private Object teamGroupName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("teamId")
    public Integer getTeamId() {
        return teamId;
    }

    @JsonProperty("teamId")
    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    @JsonProperty("teamName")
    public String getTeamName() {
        return teamName;
    }

    @JsonProperty("teamName")
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @JsonProperty("shortName")
    public String getShortName() {
        return shortName;
    }

    @JsonProperty("shortName")
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @JsonProperty("teamIconUrl")
    public String getTeamIconUrl() {
        return teamIconUrl;
    }

    @JsonProperty("teamIconUrl")
    public void setTeamIconUrl(String teamIconUrl) {
        this.teamIconUrl = teamIconUrl;
    }

    @JsonProperty("teamGroupName")
    public Object getTeamGroupName() {
        return teamGroupName;
    }

    @JsonProperty("teamGroupName")
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
