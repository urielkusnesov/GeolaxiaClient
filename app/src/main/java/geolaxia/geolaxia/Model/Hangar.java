package geolaxia.geolaxia.Model;

import java.util.Date;

/**
 * Created by uriel on 11/11/2017.
 */

public class Hangar extends Military {
    public Hangar(int id, String name, int constructinTime, geolaxia.geolaxia.Model.Cost cost, geolaxia.geolaxia.Model.Planet planet, int requiredLevel, Date enableDate) {
        super(id, name, constructinTime, cost, planet, requiredLevel, enableDate);
    }
}
