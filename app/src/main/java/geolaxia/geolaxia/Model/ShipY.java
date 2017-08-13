package geolaxia.geolaxia.Model;

/**
 * Created by uriel on 13/8/2017.
 */

public class ShipY extends Ship {
    public ShipY(Ship ship){
        super(ship.getId(), ship.getAttack(), ship.getDefense(), ship.getDarkMatterConsumption(), ship.getSpeed(), ship.getShipType());
    }
}
