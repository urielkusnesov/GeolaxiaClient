package geolaxia.geolaxia.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by uriel on 13/5/2017.
 */

public class SolarSystem implements Serializable {
    private int Id;
    private String name;
    private Galaxy Galaxy;
    private ArrayList<Planet> Planets;

    public ArrayList<Planet> getPlanets() {
        return Planets;
    }

    public void setPlanets(ArrayList<Planet> planets) {
        Planets = planets;
    }

    public geolaxia.geolaxia.Model.Galaxy getGalaxy() {
        return Galaxy;
    }

    public void setGalaxy(geolaxia.geolaxia.Model.Galaxy galaxy) {
        Galaxy = galaxy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
