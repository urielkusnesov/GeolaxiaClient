package geolaxia.geolaxia.Model;

/**
 * Created by uriel on 13/8/2017.
 */

public class Ship {
    private int Id;
    private int Attack;
    private int Defense;
    private int DarkMatterConsumption;
    private int Speed;
    private int ShipType;

    public Ship(int id, int attack, int defense, int darkMatterConsumption, int speed, int shipType){
        this.Id = id;
        this.Attack = attack;
        this.Defense = defense;
        this.DarkMatterConsumption = darkMatterConsumption;
        this.Speed = speed;
        this.ShipType = shipType;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getAttack() {
        return Attack;
    }

    public void setAttack(int attack) {
        Attack = attack;
    }

    public int getDefense() {
        return Defense;
    }

    public void setDefense(int defense) {
        Defense = defense;
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
