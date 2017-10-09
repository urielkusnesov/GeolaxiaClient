package geolaxia.geolaxia.Model;

public class Cannon {
    private int Id;
    private int Attack;
    private int Defense;
    private int DarkMatterConsumption;
    private int Speed;
    private int ShipType;

    public Cannon(int id, int attack, int defense){
        this.Id = id;
        this.Attack = attack;
        this.Defense = defense;
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
}
