package geolaxia.geolaxia.Model;

import java.util.Date;

/**
 * Created by uriel on 11/11/2017.
 */

public class Probe extends Ship {
    public Probe(int id, String name, int constructinTime, geolaxia.geolaxia.Model.Cost cost, geolaxia.geolaxia.Model.Planet planet, int requiredLevel, Date enableDate, int attack, int defense, int darkMatterConsumption, int speed, int shipType) {
        super(id, name, constructinTime, cost, planet, requiredLevel, enableDate, attack, defense, darkMatterConsumption, speed, shipType);
    }


}
