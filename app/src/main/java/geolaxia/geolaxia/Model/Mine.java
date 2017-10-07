package geolaxia.geolaxia.Model;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by uriel on 24/9/2017.
 */

public class Mine extends Facility {
    protected int EnergyConsumption;
    protected int MineType;

    public Mine(int id, int constructionTime, Cost cost, int level, Planet planet, int productivity, int energyConsumption, int mineType){
        super(id, constructionTime, cost, level, planet, productivity);
        this.EnergyConsumption = energyConsumption;
        this.MineType = mineType;
    }

    public int getEnergyConsumption() {
        return EnergyConsumption;
    }

    public void setEnergyConsumption(int energyConsumption) {
        EnergyConsumption = energyConsumption;
    }

    public int getMineType() {
        return MineType;
    }

    public void setMineType(int mineType) {
        MineType = mineType;
    }

    public JSONObject toJSONObject(){
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("ConstructionTime", String.valueOf(this.ConstructionTime));
        if (this.Cost != null) params.put("CostId", String.valueOf(this.Cost.getId()));
        params.put("Level", String.valueOf(this.Level));
        if (this.Planet != null) params.put("PlanetId", String.valueOf(this.Planet.getId()));
        params.put("Productivity", String.valueOf(this.Productivity));
        params.put("EnergyConsumption", String.valueOf(this.EnergyConsumption));
        params.put("MineType", String.valueOf(this.MineType));
        String ships = "";
        return new JSONObject(params);
    }
}
