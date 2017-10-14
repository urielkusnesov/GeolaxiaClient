package geolaxia.geolaxia.Model;

import java.util.Date;

public class Cannon extends Military{
    private int Attack;
    private int Defense;

    public Cannon(int id, String name, int constructinTime, Cost cost, Planet planet, int requiredLevel, Date enableDate, int attack, int defense){
        super(id, name, constructinTime, cost, planet, requiredLevel, enableDate);
        this.Id = id;
        this.Attack = attack;
        this.Defense = defense;
    }

    public Cannon(int id, String name, int constructinTime, geolaxia.geolaxia.Model.Cost cost, geolaxia.geolaxia.Model.Planet planet, int requiredLevel, Date enableDate) {
        super(id, name, constructinTime, cost, planet, requiredLevel, enableDate);
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
}
