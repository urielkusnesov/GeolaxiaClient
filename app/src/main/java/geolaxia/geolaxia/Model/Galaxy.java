package geolaxia.geolaxia.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by uriel on 13/5/2017.
 */

public class Galaxy implements Serializable{
    private int Id;
    private String Name;
    private ArrayList<SolarSystem> SolartSystems;

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

    public ArrayList<SolarSystem> getSolartSystems() {
        return SolartSystems;
    }

    public void setSolartSystems(ArrayList<SolarSystem> solartSystems) {
        SolartSystems = solartSystems;
    }
}
