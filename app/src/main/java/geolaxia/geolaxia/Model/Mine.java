package geolaxia.geolaxia.Model;

/**
 * Created by uriel on 24/9/2017.
 */

public class Mine extends Facility {
    private int EnergyConsumption;
    private int MineType;

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
}
