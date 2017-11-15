package geolaxia.geolaxia.Model;

import java.util.Date;

/**
 * Created by uriel on 11/11/2017.
 */

public class Trader extends Ship {
    private int MaxCrystal;
    private int MaxMetal;
    private int MaxDarkMatter;
    private Planet Target;
    private Cost Load;

    public Trader(int id, String name, int constructinTime, geolaxia.geolaxia.Model.Cost cost, geolaxia.geolaxia.Model.Planet planet, int requiredLevel, Date enableDate, int attack, int defense, int darkMatterConsumption, int speed, int shipType, int maxCrystal, int maxMetal, int maxDarkMatter, Planet target, Cost load) {
        super(id, name, constructinTime, cost, planet, requiredLevel, enableDate, attack, defense, darkMatterConsumption, speed, shipType);
        this.MaxCrystal = maxCrystal;
        this.MaxMetal = maxMetal;
        this.MaxDarkMatter = maxDarkMatter;
        this.Target = target;
        this.Load = load;
    }

    public geolaxia.geolaxia.Model.Planet getTarget() {
        return Target;
    }

    public void setTarget(geolaxia.geolaxia.Model.Planet target) {
        Target = target;
    }

    public geolaxia.geolaxia.Model.Cost getLoad() {
        return Load;
    }

    public void setLoad(geolaxia.geolaxia.Model.Cost load) {
        Load = load;
    }

    public int getMaxCrystal() {
        return MaxCrystal;
    }

    public void setMaxCrystal(int maxCrystal) {
        MaxCrystal = maxCrystal;
    }

    public int getMaxMetal() {
        return MaxMetal;
    }

    public void setMaxMetal(int maxMetal) {
        MaxMetal = maxMetal;
    }

    public int getMaxDarkMatter() {
        return MaxDarkMatter;
    }

    public void setMaxDarkMatter(int maxDarkMatter) {
        MaxDarkMatter = maxDarkMatter;
    }
}
