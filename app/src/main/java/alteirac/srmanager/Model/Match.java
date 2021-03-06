package alteirac.srmanager.Model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jean on 06/05/2017.
 */

public class Match extends Entity {

    private Date date;
    private String location;
    private String referee;
    private Team team1;
    private Team team2;
    private ArrayList<Pub> pubs;

    public Match() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReferee() {
        return referee;
    }

    public void setReferee(String referee) {
        this.referee = referee;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public ArrayList<Pub> getPubs() {
        return pubs;
    }

    public void setPubs(ArrayList<Pub> pubs) {
        this.pubs = pubs;
    }
}
