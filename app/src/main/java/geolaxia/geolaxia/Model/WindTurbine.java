package geolaxia.geolaxia.Model;

import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by uriel on 28/10/2017.
 */

public class WindTurbine extends EnergyFacility {
    private int Threshold;
    private int BestProd;
    private int WorstProd;

    public WindTurbine() {

    }

    public WindTurbine(EnergyFacility energyFacility, int threshold, int bestProd, int worstProd) {
        super(energyFacility.getId(), energyFacility.getConstructionTime(), energyFacility.getCost(), energyFacility.getLevel(), energyFacility.getPlanet(), energyFacility.getProductivity(), energyFacility.getEnableDate(), energyFacility.getEnergyFacilityType());
        this.Threshold = threshold;
        this.BestProd = bestProd;
        this.WorstProd = worstProd;
    }

    public WindTurbine(int id, int constructionTime, geolaxia.geolaxia.Model.Cost cost, int level, geolaxia.geolaxia.Model.Planet planet, int productivity, Date enableDate, int threshold, int bestProd, int worstProd, int energyFacilityType) {
        super(id, constructionTime, cost, level, planet, productivity, enableDate, energyFacilityType);
        this.Threshold = threshold;
        this.BestProd = bestProd;
        this.WorstProd = worstProd;
    }

    public int getThreshold() {
        return Threshold;
    }

    public void setThreshold(int threshold) {
        Threshold = threshold;
    }

    public int getBestProd() {
        return BestProd;
    }

    public void setBestProd(int bestProd) {
        BestProd = bestProd;
    }

    public int getWorstProd() {
        return WorstProd;
    }

    public void setWorstProd(int worstProd) {
        WorstProd = worstProd;
    }

    @Override
    public JSONObject toJSONObject() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("ConstructionTime", String.valueOf(this.ConstructionTime));
        if (this.Cost != null) params.put("CostId", String.valueOf(this.Cost.getId()));
        params.put("Level", String.valueOf(this.Level));
        if (this.Planet != null) params.put("PlanetId", String.valueOf(this.Planet.getId()));
        params.put("Productivity", String.valueOf(this.Productivity));
        params.put("EnergyFacilityType", String.valueOf(this.EnergyFacilityType));
        params.put("Threshold", String.valueOf(this.Threshold));
        params.put("BestProd", String.valueOf(this.BestProd));
        params.put("WorstProd", String.valueOf(this.WorstProd));
        return new JSONObject(params);
    }
}
