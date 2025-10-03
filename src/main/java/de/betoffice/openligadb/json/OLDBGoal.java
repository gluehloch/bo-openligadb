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
        "goalID",
        "scoreTeam1",
        "scoreTeam2",
        "matchMinute",
        "goalGetterID",
        "goalGetterName",
        "isPenalty",
        "isOwnGoal",
        "isOvertime",
        "comment"
})
public class OLDBGoal {

    @JsonProperty("goalID")
    private Integer goalID;
    @JsonProperty("scoreTeam1")
    private Integer scoreTeam1;
    @JsonProperty("scoreTeam2")
    private Integer scoreTeam2;
    @JsonProperty("matchMinute")
    private Integer matchMinute;
    @JsonProperty("goalGetterID")
    private Integer goalGetterID;
    @JsonProperty("goalGetterName")
    private String goalGetterName;
    @JsonProperty("isPenalty")
    private Boolean isPenalty;
    @JsonProperty("isOwnGoal")
    private Boolean isOwnGoal;
    @JsonProperty("isOvertime")
    private Boolean isOvertime;
    @JsonProperty("comment")
    private Object comment;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("goalID")
    public Integer getGoalID() {
        return goalID;
    }

    @JsonProperty("goalID")
    public void setGoalID(Integer goalID) {
        this.goalID = goalID;
    }

    @JsonProperty("scoreTeam1")
    public Integer getScoreTeam1() {
        return scoreTeam1;
    }

    @JsonProperty("scoreTeam1")
    public void setScoreTeam1(Integer scoreTeam1) {
        this.scoreTeam1 = scoreTeam1;
    }

    @JsonProperty("scoreTeam2")
    public Integer getScoreTeam2() {
        return scoreTeam2;
    }

    @JsonProperty("scoreTeam2")
    public void setScoreTeam2(Integer scoreTeam2) {
        this.scoreTeam2 = scoreTeam2;
    }

    @JsonProperty("matchMinute")
    public Integer getMatchMinute() {
        return matchMinute;
    }

    @JsonProperty("matchMinute")
    public void setMatchMinute(Integer matchMinute) {
        this.matchMinute = matchMinute;
    }

    @JsonProperty("goalGetterID")
    public Integer getGoalGetterID() {
        return goalGetterID;
    }

    @JsonProperty("goalGetterID")
    public void setGoalGetterID(Integer goalGetterID) {
        this.goalGetterID = goalGetterID;
    }

    @JsonProperty("goalGetterName")
    public String getGoalGetterName() {
        return goalGetterName;
    }

    @JsonProperty("goalGetterName")
    public void setGoalGetterName(String goalGetterName) {
        this.goalGetterName = goalGetterName;
    }

    @JsonProperty("isPenalty")
    public Boolean getIsPenalty() {
        return isPenalty;
    }

    @JsonProperty("isPenalty")
    public void setIsPenalty(Boolean isPenalty) {
        this.isPenalty = isPenalty;
    }

    @JsonProperty("isOwnGoal")
    public Boolean getIsOwnGoal() {
        return isOwnGoal;
    }

    @JsonProperty("isOwnGoal")
    public void setIsOwnGoal(Boolean isOwnGoal) {
        this.isOwnGoal = isOwnGoal;
    }

    @JsonProperty("isOvertime")
    public Boolean getIsOvertime() {
        return isOvertime;
    }

    @JsonProperty("isOvertime")
    public void setIsOvertime(Boolean isOvertime) {
        this.isOvertime = isOvertime;
    }

    @JsonProperty("comment")
    public Object getComment() {
        return comment;
    }

    @JsonProperty("comment")
    public void setComment(Object comment) {
        this.comment = comment;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Goal{");
        sb.append("goalID=").append(goalID);
        sb.append(", scoreTeam1=").append(scoreTeam1);
        sb.append(", scoreTeam2=").append(scoreTeam2);
        sb.append(", matchMinute=").append(matchMinute);
        sb.append(", goalGetterID=").append(goalGetterID);
        sb.append(", goalGetterName='").append(goalGetterName).append('\'');
        sb.append(", isPenalty=").append(isPenalty);
        sb.append(", isOwnGoal=").append(isOwnGoal);
        sb.append(", isOvertime=").append(isOvertime);
        sb.append(", comment=").append(comment);
        sb.append(", additionalProperties=").append(additionalProperties);
        sb.append('}');
        return sb.toString();
    }
}
