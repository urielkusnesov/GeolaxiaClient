package geolaxia.geolaxia.Model;

/**
 * Created by uriel on 24/9/2017.
 */

public class CrystalMine extends Mine {
    public CrystalMine(Mine mine){
        super(mine.getId(), mine.getConstructionTime(), mine.getCost(), mine.getLevel(), mine.getPlanet(), mine.getProductivity(),
                mine.getEnergyConsumption(), mine.getMineType());
    }
}
