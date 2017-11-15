package geolaxia.geolaxia.Model;

import java.util.Date;

/**
 * Created by uriel on 13/8/2017.
 */

public class Ship extends Military{
    private int Attack;
    private int Defence;
    private int DarkMatterConsumption;
    private int Speed;
    private int ShipType;

    public Ship(int id, String name, int constructinTime, Cost cost, Planet planet, int requiredLevel, Date enableDate, int attack, int defence, int darkMatterConsumption, int speed, int shipType){
        super(id, name, constructinTime, cost, planet, requiredLevel, enableDate);
        this.Attack = attack;
        this.Defence = defence;
        this.DarkMatterConsumption = darkMatterConsumption;
        this.Speed = speed;
        this.ShipType = shipType;
    }

    public int getAttack() {
        return Attack;
    }

    public void setAttack(int attack) {
        Attack = attack;
    }

    public int getDefence() {
        return Defence;
    }

    public void setDefence(int defence) {
        Defence = defence;
    }

    public int getDarkMatterConsumption() {
        return DarkMatterConsumption;
    }

    public void setDarkMatterConsumption(int darkMatterConsumption) {
        DarkMatterConsumption = darkMatterConsumption;
    }

    public int getSpeed() {
        return Speed;
    }

    public void setSpeed(int speed) {
        Speed = speed;
    }

    public int getShipType() { return ShipType; }

    public void setShipType(int shipType) { this.ShipType = shipType; }
}
