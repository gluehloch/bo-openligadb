package de.betoffice.openligadb.json;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "ResultID",
        "ResultName",
        "PointsTeam1",
        "PointsTeam2",
        "ResultOrderID",
        "ResultTypeID",
        "ResultDescription"
})
public class OLDBMatchResult {

    @JsonProperty("ResultID")
    private Integer resultID;
    @JsonProperty("ResultName")
    private String resultName;
    @JsonProperty("PointsTeam1")
    private Integer pointsTeam1;
    @JsonProperty("PointsTeam2")
    private Integer pointsTeam2;
    @JsonProperty("ResultOrderID")
    private Integer resultOrderID;
    @JsonProperty("ResultTypeID")
    private Integer resultTypeID;
    @JsonProperty("ResultDescription")
    private String resultDescription;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("ResultID")
    public Integer getResultID() {
        return resultID;
    }

    @JsonProperty("ResultID")
    public void setResultID(Integer resultID) {
        this.resultID = resultID;
    }

    @JsonProperty("ResultName")
    public String getResultName() {
        return resultName;
    }

    @JsonProperty("ResultName")
    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    @JsonProperty("PointsTeam1")
    public Integer getPointsTeam1() {
        return pointsTeam1;
    }

    @JsonProperty("PointsTeam1")
    public void setPointsTeam1(Integer pointsTeam1) {
        this.pointsTeam1 = pointsTeam1;
    }

    @JsonProperty("PointsTeam2")
    public Integer getPointsTeam2() {
        return pointsTeam2;
    }

    @JsonProperty("PointsTeam2")
    public void setPointsTeam2(Integer pointsTeam2) {
        this.pointsTeam2 = pointsTeam2;
    }

    @JsonProperty("ResultOrderID")
    public Integer getResultOrderID() {
        return resultOrderID;
    }

    @JsonProperty("ResultOrderID")
    public void setResultOrderID(Integer resultOrderID) {
        this.resultOrderID = resultOrderID;
    }

    @JsonProperty("ResultTypeID")
    public Integer getResultTypeID() {
        return resultTypeID;
    }

    @JsonProperty("ResultTypeID")
    public void setResultTypeID(Integer resultTypeID) {
        this.resultTypeID = resultTypeID;
    }

    @JsonProperty("ResultDescription")
    public String getResultDescription() {
        return resultDescription;
    }

    @JsonProperty("ResultDescription")
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
