package geolaxia.geolaxia.Model;

import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by uriel on 28/10/2017.
 */

public class EnergyCentral extends EnergyFacility {

    public EnergyCentral(EnergyFacility energyFacility) {
        super(energyFacility.getId(), energyFacility.getConstructionTime(), energyFacility.getCost(), energyFacility.getLevel(), energyFacility.getPlanet(), energyFacility.getProductivity(), energyFacility.getEnableDate(), energyFacility.getEnergyFacilityType());
    }

    public EnergyCentral(int id, int constructionTime, geolaxia.geolaxia.Model.Cost cost, int level, geolaxia.geolaxia.Model.Planet planet, int productivity, Date enableDate, int energyFacilityType) {
        super(id, constructionTime, cost, level, planet, productivity, enableDate, energyFacilityType);
    }

    @Override
    public JSONObject toJSONObject(){
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("ConstructionTime", String.valueOf(this.ConstructionTime));
        if (this.Cost != null) params.put("CostId", String.valueOf(this.Cost.getId()));
        params.put("Level", String.valueOf(this.Level));
        if (this.Planet != null) params.put("PlanetId", String.valueOf(this.Planet.getId()));
        params.put("Productivity", String.valueOf(this.Productivity));
        params.put("EnergyFacilityType", String.valueOf(this.EnergyFacilityType));
        return new JSONObject(params);
    }
}
