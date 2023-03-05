package de.betoffice.openligadb.json;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "resultID",
        "resultName",
        "pointsTeam1",
        "pointsTeam2",
        "resultOrderID",
        "resultTypeID",
        "resultDescription"
})
public class OLDBMatchResult {

    @JsonProperty("resultID")
    private Integer resultID;
    @JsonProperty("resultName")
    private String resultName;
    @JsonProperty("pointsTeam1")
    private Integer pointsTeam1;
    @JsonProperty("pointsTeam2")
    private Integer pointsTeam2;
    @JsonProperty("resultOrderID")
    private Integer resultOrderID;
    @JsonProperty("resultTypeID")
    private Integer resultTypeID;
    @JsonProperty("resultDescription")
    private String resultDescription;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("resultID")
    public Integer getResultID() {
        return resultID;
    }

    @JsonProperty("resultID")
    public void setResultID(Integer resultID) {
        this.resultID = resultID;
    }

    @JsonProperty("resultName")
    public String getResultName() {
        return resultName;
    }

    @JsonProperty("resultName")
    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    @JsonProperty("pointsTeam1")
    public Integer getPointsTeam1() {
        return pointsTeam1;
    }

    @JsonProperty("pointsTeam1")
    public void setPointsTeam1(Integer pointsTeam1) {
        this.pointsTeam1 = pointsTeam1;
    }

    @JsonProperty("pointsTeam2")
    public Integer getPointsTeam2() {
        return pointsTeam2;
    }

    @JsonProperty("pointsTeam2")
    public void setPointsTeam2(Integer pointsTeam2) {
        this.pointsTeam2 = pointsTeam2;
    }

    @JsonProperty("resultOrderID")
    public Integer getResultOrderID() {
        return resultOrderID;
    }

    @JsonProperty("resultOrderID")
    public void setResultOrderID(Integer resultOrderID) {
        this.resultOrderID = resultOrderID;
    }

    @JsonProperty("resultTypeID")
    public Integer getResultTypeID() {
        return resultTypeID;
    }

    @JsonProperty("resultTypeID")
    public void setResultTypeID(Integer resultTypeID) {
        this.resultTypeID = resultTypeID;
    }

    @JsonProperty("resultDescription")
    public String getResultDescription() {
        return resultDescription;
    }

    @JsonProperty("resultDescription")
    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
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
        final StringBuilder sb = new StringBuilder("MatchResult{");
        sb.append("resultID=").append(resultID);
        sb.append(", resultName='").append(resultName).append('\'');
        sb.append(", pointsTeam1=").append(pointsTeam1);
        sb.append(", pointsTeam2=").append(pointsTeam2);
        sb.append(", resultOrderID=").append(resultOrderID);
        sb.append(", resultTypeID=").append(resultTypeID);
        sb.append(", resultDescription='").append(resultDescription).append('\'');
        sb.append(", additionalProperties=").append(additionalProperties);
        sb.append('}');
        return sb.toString();
    }
}
