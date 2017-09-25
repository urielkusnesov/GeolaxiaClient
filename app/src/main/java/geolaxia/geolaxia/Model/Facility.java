package geolaxia.geolaxia.Model;

/**
 * Created by uriel on 24/9/2017.
 */

public class Facility {
    private int Id;
    private int ConstructionTime;
    private Cost Cost;
    private int Level;
    private Planet Planet;
    private int Productivity;

    public Facility(int id, int constructionTime, Cost cost, int level, Planet planet, int productivity){
        this.Id = id;
        this.ConstructionTime = constructionTime;
        this.Cost = cost;
        this.Level = level;
        this.Planet = planet;
        this.Productivity = productivity;
    }

    public int getProductivity() {
        return Productivity;
    }

    public void setProductivity(int productivity) {
        Productivity = productivity;
    }

    public Planet getPlanet() {
        return Planet;
    }

    public void setPlanet(Planet planet) {
        this.Planet = planet;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        this.Level = level;
    }

    public geolaxia.geolaxia.Model.Cost getCost() {
        return Cost;
    }

    public void setCost(geolaxia.geolaxia.Model.Cost cost) {
        Cost = cost;
    }

    public int getConstructionTime() {
        return ConstructionTime;
    }

    public void setConstructionTime(int constructionTime) {
        ConstructionTime = constructionTime;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
