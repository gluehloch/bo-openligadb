package de.betoffice.openligadb.json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "MatchID",
        "MatchDateTime",
        "TimeZoneID",
        "LeagueId",
        "LeagueName",
        "MatchDateTimeUTC",
        "Group",
        "Team1",
        "Team2",
        "LastUpdateDateTime",
        "MatchIsFinished",
        "MatchResults",
        "Goals",
        "Location",
        "NumberOfViewers"
})
public class OLDBMatch {

    @JsonProperty("MatchID")
    private Integer matchID;
    @JsonProperty("MatchDateTime")
    private String matchDateTime;
    @JsonProperty("TimeZoneID")
    private String timeZoneID;
    @JsonProperty("LeagueId")
    private Integer leagueId;
    @JsonProperty("LeagueName")
    private String leagueName;
    @JsonProperty("MatchDateTimeUTC")
    private String matchDateTimeUTC;
    @JsonProperty("Group")
    private OLDBGroup group;
    @JsonProperty("Team1")
    private OLDBTeam team1;
    @JsonProperty("Team2")
    private OLDBTeam team2;
    @JsonProperty("LastUpdateDateTime")
    private String lastUpdateDateTime;
    @JsonProperty("MatchIsFinished")
    private Boolean matchIsFinished;
    @JsonProperty("MatchResults")
    private List<OLDBMatchResult> matchResults = null;
    @JsonProperty("Goals")
    private List<OLDBGoal> goals = null;
    @JsonProperty("Location")
    private OLDBLocation location;
    @JsonProperty("NumberOfViewers")
    private Object numberOfViewers;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("MatchID")
    public Integer getMatchID() {
        return matchID;
    }

    @JsonProperty("MatchID")
    public void setMatchID(Integer matchID) {
        this.matchID = matchID;
    }

    @JsonProperty("MatchDateTime")
    public String getMatchDateTime() {
        return matchDateTime;
    }

    @JsonProperty("MatchDateTime")
    public void setMatchDateTime(String matchDateTime) {
        this.matchDateTime = matchDateTime;
    }

    @JsonProperty("TimeZoneID")
    public String getTimeZoneID() {
        return timeZoneID;
    }

    @JsonProperty("TimeZoneID")
    public void setTimeZoneID(String timeZoneID) {
        this.timeZoneID = timeZoneID;
    }

    @JsonProperty("LeagueId")
    public Integer getLeagueId() {
        return leagueId;
    }

    @JsonProperty("LeagueId")
    public void setLeagueId(Integer leagueId) {
        this.leagueId = leagueId;
    }

    @JsonProperty("LeagueName")
    public String getLeagueName() {
        return leagueName;
    }

    @JsonProperty("LeagueName")
    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    @JsonProperty("MatchDateTimeUTC")
    public String getMatchDateTimeUTC() {
        return matchDateTimeUTC;
    }

    @JsonProperty("MatchDateTimeUTC")
    public void setMatchDateTimeUTC(String matchDateTimeUTC) {
        this.matchDateTimeUTC = matchDateTimeUTC;
    }

    @JsonProperty("Group")
    public OLDBGroup getGroup() {
        return group;
    }

    @JsonProperty("Group")
    public void setGroup(OLDBGroup group) {
        this.group = group;
    }

    @JsonProperty("Team1")
    public OLDBTeam getTeam1() {
        return team1;
    }

    @JsonProperty("Team1")
    public void setTeam1(OLDBTeam team1) {
        this.team1 = team1;
    }

    @JsonProperty("Team2")
    public OLDBTeam getTeam2() {
        return team2;
    }

    @JsonProperty("Team2")
    public void setTeam2(OLDBTeam team2) {
        this.team2 = team2;
    }

    @JsonProperty("LastUpdateDateTime")
    public String getLastUpdateDateTime() {
        return lastUpdateDateTime;
    }

    @JsonProperty("LastUpdateDateTime")
    public void setLastUpdateDateTime(String lastUpdateDateTime) {
        this.lastUpdateDateTime = lastUpdateDateTime;
    }

    @JsonProperty("MatchIsFinished")
    public Boolean getMatchIsFinished() {
        return matchIsFinished;
    }

    @JsonProperty("MatchIsFinished")
    public void setMatchIsFinished(Boolean matchIsFinished) {
        this.matchIsFinished = matchIsFinished;
    }

    @JsonProperty("MatchResults")
    public List<OLDBMatchResult> getMatchResults() {
        return matchResults;
    }

    @JsonProperty("MatchResults")
    public void setMatchResults(List<OLDBMatchResult> matchResults) {
        this.matchResults = matchResults;
    }

    @JsonProperty("Goals")
    public List<OLDBGoal> getGoals() {
        return goals;
    }

    @JsonProperty("Goals")
    public void setGoals(List<OLDBGoal> goals) {
        this.goals = goals;
    }

    @JsonProperty("Location")
    public OLDBLocation getLocation() {
        return location;
    }

    @JsonProperty("Location")
    public void setLocation(OLDBLocation location) {
        this.location = location;
    }

    @JsonProperty("NumberOfViewers")
    public Object getNumberOfViewers() {
        return numberOfViewers;
    }

    @JsonProperty("NumberOfViewers")
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
