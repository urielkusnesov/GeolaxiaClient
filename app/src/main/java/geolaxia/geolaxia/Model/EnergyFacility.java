package geolaxia.geolaxia.Model;

import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by uriel on 28/10/2017.
 */

public class EnergyFacility extends Facility{
    protected int EnergyFacilityType;

    public EnergyFacility(){
        super();
    }

    public EnergyFacility(int id, int constructionTime, geolaxia.geolaxia.Model.Cost cost, int level, geolaxia.geolaxia.Model.Planet planet, int productivity, Date enableDate, int energyFacilityType) {
        super(id, constructionTime, cost, level, planet, productivity, enableDate);
        this.EnergyFacilityType = energyFacilityType;
    }

    public int getEnergyFacilityType() {
        return EnergyFacilityType;
    }

    public void setEnergyFacilityType(int energyFacilityType) {
        EnergyFacilityType = energyFacilityType;
    }

    public JSONObject toJSONObject(){
        return new JSONObject();
    }
}
