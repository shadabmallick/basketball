package com.sport.supernathral.DataModel;

public class ScoreData {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public String getMatch_team_id() {
        return match_team_id;
    }

    public void setMatch_team_id(String match_team_id) {
        this.match_team_id = match_team_id;
    }

    public String getTeamA_score() {
        return teamA_score;
    }

    public void setTeamA_score(String teamA_score) {
        this.teamA_score = teamA_score;
    }

    public String getTeamB_score() {
        return teamB_score;
    }

    public void setTeamB_score(String teamB_score) {
        this.teamB_score = teamB_score;
    }

    public String getDate_and_time() {
        return date_and_time;
    }

    public void setDate_and_time(String date_and_time) {
        this.date_and_time = date_and_time;
    }

    public String getWinner_team() {
        return winner_team;
    }

    public void setWinner_team(String winner_team) {
        this.winner_team = winner_team;
    }

    public String getDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(String delete_flag) {
        this.delete_flag = delete_flag;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(String entry_date) {
        this.entry_date = entry_date;
    }

    public String getModified_date() {
        return modified_date;
    }

    public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }

    private String id;
    private String match_id;
    private String match_team_id;
    private String teamA_score;
    private String teamB_score;
    private String date_and_time;
    private String winner_team;
    private String delete_flag;
    private String is_active;
    private String entry_date;
    private String modified_date;

}
