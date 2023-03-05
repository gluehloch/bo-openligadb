package de.betoffice.openligadb.json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "matchID",
        "matchDateTime",
        "timeZoneID",
        "leagueId",
        "leagueName",
        "matchDateTimeUTC",
        "group",
        "team1",
        "team2",
        "lastUpdateDateTime",
        "matchIsFinished",
        "matchResults",
        "goals",
        "location",
        "numberOfViewers"
})
public class OLDBMatch {

    @JsonProperty("matchID")
    private Integer matchID;
    @JsonProperty("matchDateTime")
    private String matchDateTime;
    @JsonProperty("timeZoneID")
    private String timeZoneID;
    @JsonProperty("leagueId")
    private Integer leagueId;
    @JsonProperty("leagueName")
    private String leagueName;
    @JsonProperty("matchDateTimeUTC")
    private String matchDateTimeUTC;
    @JsonProperty("group")
    private OLDBGroup group;
    @JsonProperty("team1")
    private OLDBTeam team1;
    @JsonProperty("team2")
    private OLDBTeam team2;
    @JsonProperty("lastUpdateDateTime")
    private String lastUpdateDateTime;
    @JsonProperty("matchIsFinished")
    private Boolean matchIsFinished;
    @JsonProperty("matchResults")
    private List<OLDBMatchResult> matchResults = null;
    @JsonProperty("goals")
    private List<OLDBGoal> goals = null;
    @JsonProperty("location")
    private OLDBLocation location;
    @JsonProperty("numberOfViewers")
    private Object numberOfViewers;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("matchID")
    public Integer getMatchID() {
        return matchID;
    }

    @JsonProperty("matchID")
    public void setMatchID(Integer matchID) {
        this.matchID = matchID;
    }

    @JsonProperty("matchDateTime")
    public String getMatchDateTime() {
        return matchDateTime;
    }

    @JsonProperty("matchDateTime")
    public void setMatchDateTime(String matchDateTime) {
        this.matchDateTime = matchDateTime;
    }

    @JsonProperty("timeZoneID")
    public String getTimeZoneID() {
        return timeZoneID;
    }

    @JsonProperty("timeZoneID")
    public void setTimeZoneID(String timeZoneID) {
        this.timeZoneID = timeZoneID;
    }

    @JsonProperty("leagueId")
    public Integer getLeagueId() {
        return leagueId;
    }

    @JsonProperty("leagueId")
    public void setLeagueId(Integer leagueId) {
        this.leagueId = leagueId;
    }

    @JsonProperty("leagueName")
    public String getLeagueName() {
        return leagueName;
    }

    @JsonProperty("leagueName")
    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    @JsonProperty("matchDateTimeUTC")
    public String getMatchDateTimeUTC() {
        return matchDateTimeUTC;
    }

    @JsonProperty("matchDateTimeUTC")
    public void setMatchDateTimeUTC(String matchDateTimeUTC) {
        this.matchDateTimeUTC = matchDateTimeUTC;
    }

    @JsonProperty("group")
    public OLDBGroup getGroup() {
        return group;
    }

    @JsonProperty("group")
    public void setGroup(OLDBGroup group) {
        this.group = group;
    }

    @JsonProperty("team1")
    public OLDBTeam getTeam1() {
        return team1;
    }

    @JsonProperty("team1")
    public void setTeam1(OLDBTeam team1) {
        this.team1 = team1;
    }

    @JsonProperty("team2")
    public OLDBTeam getTeam2() {
        return team2;
    }

    @JsonProperty("team2")
    public void setTeam2(OLDBTeam team2) {
        this.team2 = team2;
    }

    @JsonProperty("lastUpdateDateTime")
    public String getLastUpdateDateTime() {
        return lastUpdateDateTime;
    }

    @JsonProperty("lastUpdateDateTime")
    public void setLastUpdateDateTime(String lastUpdateDateTime) {
        this.lastUpdateDateTime = lastUpdateDateTime;
    }

    @JsonProperty("matchIsFinished")
    public Boolean getMatchIsFinished() {
        return matchIsFinished;
    }

    @JsonProperty("matchIsFinished")
    public void setMatchIsFinished(Boolean matchIsFinished) {
        this.matchIsFinished = matchIsFinished;
    }

    @JsonProperty("matchResults")
    public List<OLDBMatchResult> getMatchResults() {
        return matchResults;
    }

    @JsonProperty("matchResults")
    public void setMatchResults(List<OLDBMatchResult> matchResults) {
        this.matchResults = matchResults;
    }

    @JsonProperty("goals")
    public List<OLDBGoal> getGoals() {
        return goals;
    }

    @JsonProperty("goals")
    public void setGoals(List<OLDBGoal> goals) {
        this.goals = goals;
    }

    @JsonProperty("location")
    public OLDBLocation getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(OLDBLocation location) {
        this.location = location;
    }

    @JsonProperty("numberOfViewers")
    public Object getNumberOfViewers() {
        return numberOfViewers;
    }

    @JsonProperty("numberOfViewers")
    public void setNumberOfViewers(Object numberOfViewers) {
        this.numberOfViewers = numberOfViewers;
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
        final StringBuilder sb = new StringBuilder("Match{");
        sb.append("matchID=").append(matchID);
        sb.append(", matchDateTime='").append(matchDateTime).append('\'');
        sb.append(", timeZoneID='").append(timeZoneID).append('\'');
        sb.append(", leagueId=").append(leagueId);
        sb.append(", leagueName='").append(leagueName).append('\'');
        sb.append(", matchDateTimeUTC='").append(matchDateTimeUTC).append('\'');
        sb.append(", group=").append(group);
        sb.append(", team1=").append(team1);
        sb.append(", team2=").append(team2);
        sb.append(", lastUpdateDateTime='").append(lastUpdateDateTime).append('\'');
        sb.append(", matchIsFinished=").append(matchIsFinished);
        sb.append(", matchResults=").append(matchResults);
        sb.append(", goals=").append(goals);
        sb.append(", location=").append(location);
        sb.append(", numberOfViewers=").append(numberOfViewers);
        sb.append(", additionalProperties=").append(additionalProperties);
        sb.append('}');
        return sb.toString();
    }
}
