package geolaxia.geolaxia.Model;

/**
 * Created by uriel on 13/8/2017.
 */

public class ShipZ extends Ship {
    public ShipZ(Ship ship){
        super(ship.getId(), ship.getName(), ship.getConstructionTime(), ship.getCost(), ship.getPlanet(), ship.getRequiredLevel(),
                ship.getEnableDate(), ship.getAttack(), ship.getDefence(), ship.getDarkMatterConsumption(), ship.getSpeed(), ship.getShipType());
    }
}
