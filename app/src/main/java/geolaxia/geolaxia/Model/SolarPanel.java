package geolaxia.geolaxia.Model;

import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by uriel on 28/10/2017.
 */

public class SolarPanel extends EnergyFacility {
    private int CloudyProd;
    private int SunnyProd;
    private int RainyProd;

    public SolarPanel(){
        super();
    }

    public SolarPanel(EnergyFacility energyFacility, int cloudyProd, int sunnyProd, int rainyProd) {
        super(energyFacility.getId(), energyFacility.getConstructionTime(), energyFacility.getCost(), energyFacility.getLevel(), energyFacility.getPlanet(), energyFacility.getProductivity(), energyFacility.getEnableDate(), energyFacility.getEnergyFacilityType());
        this.CloudyProd = cloudyProd;
        this.SunnyProd = sunnyProd;
        this.RainyProd = rainyProd;
    }

    public SolarPanel(int id, int constructionTime, geolaxia.geolaxia.Model.Cost cost, int level, geolaxia.geolaxia.Model.Planet planet, int productivity, Date enableDate, int cloudyProd, int sunnyProd, int rainyProd, int energyFacilityType) {
        super(id, constructionTime, cost, level, planet, productivity, enableDate, energyFacilityType);
        this.CloudyProd = cloudyProd;
        this.SunnyProd = sunnyProd;
        this.RainyProd = rainyProd;
    }

    public int getCloudyProd() {
        return CloudyProd;
    }

    public void setCloudyProd(int cloudyProd) {
        CloudyProd = cloudyProd;
    }

    public int getSunnyProd() {
        return SunnyProd;
    }

    public void setSunnyProd(int sunnyProd) {
        SunnyProd = sunnyProd;
    }

    public int getRainyProd() {
        return RainyProd;
    }

    public void setRainyProd(int rainyProd) {
        RainyProd = rainyProd;
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
        params.put("CloudyProd", String.valueOf(this.CloudyProd));
        params.put("SunnyProd", String.valueOf(this.SunnyProd));
        params.put("RainyProd", String.valueOf(this.RainyProd));
        return new JSONObject(params);
    }
}
