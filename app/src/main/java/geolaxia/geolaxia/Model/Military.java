package geolaxia.geolaxia.Model;

import java.util.Date;

/**
 * Created by uriel on 14/10/2017.
 */

public abstract class Military {
    protected int Id;
    protected String Name;
    protected int ConstructionTime;
    protected Cost Cost;
    protected Planet Planet;
    protected int RequiredLevel;
    protected Date EnableDate;

    public Military(int id, String name, int constructinTime, Cost cost, Planet planet, int requiredLevel, Date enableDate){
        this.Id = id;
        this.Name = name;
        this.ConstructionTime = constructinTime;
        this.Cost = cost;
        this.Planet = planet;
        this.RequiredLevel = requiredLevel;
        this.EnableDate = enableDate;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getConstructionTime() {
        return ConstructionTime;
    }

    public void setConstructionTime(int constructionTime) {
        ConstructionTime = constructionTime;
    }

    public geolaxia.geolaxia.Model.Cost getCost() {
        return Cost;
    }

    public void setCost(geolaxia.geolaxia.Model.Cost cost) {
        Cost = cost;
    }

    public geolaxia.geolaxia.Model.Planet getPlanet() {
        return Planet;
    }

    public void setPlanet(geolaxia.geolaxia.Model.Planet planet) {
        Planet = planet;
    }

    public int getRequiredLevel() {
        return RequiredLevel;
    }

    public void setRequiredLevel(int requiredLevel) {
        RequiredLevel = requiredLevel;
    }

    public Date getEnableDate() {
        return EnableDate;
    }

    public void setEnableDate(Date enableDate) {
        EnableDate = enableDate;
    }
}
