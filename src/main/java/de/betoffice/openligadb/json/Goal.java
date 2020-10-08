package de.betoffice.openligadb.json;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "GoalID",
        "ScoreTeam1",
        "ScoreTeam2",
        "MatchMinute",
        "GoalGetterID",
        "GoalGetterName",
        "IsPenalty",
        "IsOwnGoal",
        "IsOvertime",
        "Comment"
})
public class Goal {

    @JsonProperty("GoalID")
    private Integer goalID;
    @JsonProperty("ScoreTeam1")
    private Integer scoreTeam1;
    @JsonProperty("ScoreTeam2")
    private Integer scoreTeam2;
    @JsonProperty("MatchMinute")
    private Integer matchMinute;
    @JsonProperty("GoalGetterID")
    private Integer goalGetterID;
    @JsonProperty("GoalGetterName")
    private String goalGetterName;
    @JsonProperty("IsPenalty")
    private Boolean isPenalty;
    @JsonProperty("IsOwnGoal")
    private Boolean isOwnGoal;
    @JsonProperty("IsOvertime")
    private Boolean isOvertime;
    @JsonProperty("Comment")
    private Object comment;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("GoalID")
    public Integer getGoalID() {
        return goalID;
    }

    @JsonProperty("GoalID")
    public void setGoalID(Integer goalID) {
        this.goalID = goalID;
    }

    @JsonProperty("ScoreTeam1")
    public Integer getScoreTeam1() {
        return scoreTeam1;
    }

    @JsonProperty("ScoreTeam1")
    public void setScoreTeam1(Integer scoreTeam1) {
        this.scoreTeam1 = scoreTeam1;
    }

    @JsonProperty("ScoreTeam2")
    public Integer getScoreTeam2() {
        return scoreTeam2;
    }

    @JsonProperty("ScoreTeam2")
    public void setScoreTeam2(Integer scoreTeam2) {
        this.scoreTeam2 = scoreTeam2;
    }

    @JsonProperty("MatchMinute")
    public Integer getMatchMinute() {
        return matchMinute;
    }

    @JsonProperty("MatchMinute")
    public void setMatchMinute(Integer matchMinute) {
        this.matchMinute = matchMinute;
    }

    @JsonProperty("GoalGetterID")
    public Integer getGoalGetterID() {
        return goalGetterID;
    }

    @JsonProperty("GoalGetterID")
    public void setGoalGetterID(Integer goalGetterID) {
        this.goalGetterID = goalGetterID;
    }

    @JsonProperty("GoalGetterName")
    public String getGoalGetterName() {
        return goalGetterName;
    }

    @JsonProperty("GoalGetterName")
    public void setGoalGetterName(String goalGetterName) {
        this.goalGetterName = goalGetterName;
    }

    @JsonProperty("IsPenalty")
    public Boolean getIsPenalty() {
        return isPenalty;
    }

    @JsonProperty("IsPenalty")
    public void setIsPenalty(Boolean isPenalty) {
        this.isPenalty = isPenalty;
    }

    @JsonProperty("IsOwnGoal")
    public Boolean getIsOwnGoal() {
        return isOwnGoal;
    }

    @JsonProperty("IsOwnGoal")
    public void setIsOwnGoal(Boolean isOwnGoal) {
        this.isOwnGoal = isOwnGoal;
    }

    @JsonProperty("IsOvertime")
    public Boolean getIsOvertime() {
        return isOvertime;
    }

    @JsonProperty("IsOvertime")
    public void setIsOvertime(Boolean isOvertime) {
        this.isOvertime = isOvertime;
    }

    @JsonProperty("Comment")
    public Object getComment() {
        return comment;
    }

    @JsonProperty("Comment")
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
