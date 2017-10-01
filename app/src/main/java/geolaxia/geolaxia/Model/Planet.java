package geolaxia.geolaxia.Model;

import java.io.Serializable;

/**
 * Created by uriel on 13/5/2017.
 */

public class Planet implements Serializable{

    protected int Id;
    protected String Name;
    protected int Order;
    protected Player Conqueror;
    protected int Metal;
    protected int Crystal;
    protected int DarkMatter;
    protected int Energy;
    protected boolean IsOrigin;
    protected SolarSystem SolarSystem;
    protected int PositionX;
    protected int PositionY;
    protected int PositionZ;
    protected int PlanetType;

    public Planet(){

    }

    public Planet(int id, String name, int order, Player conqueror, int metal, int crystal, int darkMatter, int energy, boolean isOrigin,
                  SolarSystem solarSystem, int positionX, int positionY, int positionZ, int planetType){
        this.Id = id;
        this.Name = name;
        this.Order = order;
        this.Conqueror = conqueror;
        this.Metal = metal;
        this.Crystal = crystal;
        this.DarkMatter = darkMatter;
        this.Energy = energy;
        this.IsOrigin = isOrigin;
        this.SolarSystem = solarSystem;
        this.PositionX = positionX;
        this.PositionY = positionY;
        this.PositionZ = positionZ;
        this.PlanetType = planetType;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getOrder() {
        return Order;
    }

    public void setOrder(int order) {
        Order = order;
    }

    public Player getConqueror() {
        return Conqueror;
    }

    public void setConqueror(Player conqueror) {
        Conqueror = conqueror;
    }

    public int getMetal() {
        return Metal;
    }

    public void setMetal(int metal) {
        Metal = metal;
    }

    public int getCrystal() {
        return Crystal;
    }

    public void setCrystal(int crystal) {
        Crystal = crystal;
    }

    public int getDarkMatter() {
        return DarkMatter;
    }

    public void setDarkMatter(int darkMatter) {
        DarkMatter = darkMatter;
    }

    public int getEnergy() {
        return Energy;
    }

    public void setEnergy(int energy) {
        Energy = energy;
    }

    public boolean isOrigin() {
        return IsOrigin;
    }

    public void setOrigin(boolean origin) {
        IsOrigin = origin;
    }

    public SolarSystem getSolarSystem() {
        return SolarSystem;
    }

    public void setSolarSystem(SolarSystem solarSystem) {
        SolarSystem = solarSystem;
    }

    public int getPositionX() {
        return PositionX;
    }

    public void setPositionX(int positionX) {
        PositionX = positionX;
    }

    public int getPositionY() {
        return PositionY;
    }

    public void setPositionY(int positionY) {
        PositionY = positionY;
    }

    public int getPositionZ() {
        return PositionZ;
    }

    public void setPositionZ(int positionZ) {
        PositionZ = positionZ;
    }

    public int getPlanetType() {
        return PlanetType;
    }

    public void setPlanetType(int planetType) {
        PlanetType = planetType;
    }

    public int getImage(){
        return 0;
    }

    @Override
    public String toString()
    {
        return String.valueOf(Order) + " - " + Name;
    }
}
