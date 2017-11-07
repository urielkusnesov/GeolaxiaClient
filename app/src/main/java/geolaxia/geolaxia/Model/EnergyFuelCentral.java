package geolaxia.geolaxia.Model;

import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by uriel on 28/10/2017.
 */

public class EnergyFuelCentral extends EnergyFacility{
    private int DarkMatterConsumption;

    public EnergyFuelCentral(EnergyFacility energyFacility, int darkMatterConsumption) {
        super(energyFacility.getId(), energyFacility.getConstructionTime(), energyFacility.getCost(), energyFacility.getLevel(), energyFacility.getPlanet(), energyFacility.getProductivity(), energyFacility.getEnableDate(), energyFacility.getEnergyFacilityType());
        this.DarkMatterConsumption = darkMatterConsumption;
    }

    public EnergyFuelCentral(int id, int constructionTime, geolaxia.geolaxia.Model.Cost cost, int level, geolaxia.geolaxia.Model.Planet planet, int productivity, Date enableDate, int darkMatterConsumption, int energyFacilityType) {
        super(id, constructionTime, cost, level, planet, productivity, enableDate, energyFacilityType);
        this.DarkMatterConsumption = darkMatterConsumption;
    }

    public int getDarkMatterConsumption() {
        return DarkMatterConsumption;
    }

    public void setDarkMatterConsumption(int darkMatterConsumption) {
        DarkMatterConsumption = darkMatterConsumption;
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
        params.put("DarkMatterConsumption", String.valueOf(this.DarkMatterConsumption));
        return new JSONObject(params);
    }
}
